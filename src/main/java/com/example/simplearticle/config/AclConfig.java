package com.example.simplearticle.config;

import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
public class AclConfig {
    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
    }

    @Bean
    public SpringCacheBasedAclCache aclCache(
            PermissionGrantingStrategy permissionGrantingStrategy,
            AclAuthorizationStrategy aclAuthorizationStrategy
    ) {
        return new SpringCacheBasedAclCache(
                new ConcurrentMapCache("aclCache"),
                permissionGrantingStrategy,
                aclAuthorizationStrategy
        );
    }

    @Bean
    public LookupStrategy lookupStrategy(
            DataSource dataSource,
            SpringCacheBasedAclCache aclCache,
            AclAuthorizationStrategy aclAuthorizationStrategy,
            PermissionGrantingStrategy permissionGrantingStrategy
    ) {
        return new BasicLookupStrategy(
                dataSource,
                aclCache,
                aclAuthorizationStrategy,
                permissionGrantingStrategy
        );
    }

    @Bean
    public JdbcMutableAclService aclService(
            DataSource dataSource,
            LookupStrategy lookupStrategy,
            SpringCacheBasedAclCache aclCache
    ) {
        //return new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
        JdbcMutableAclService aclService =
                new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);

        aclService.setClassIdentityQuery(
                "select currval(pg_get_serial_sequence('acl_class', 'id'))"
        );
        aclService.setSidIdentityQuery(
                "select currval(pg_get_serial_sequence('acl_sid', 'id'))"
        );

        return aclService;
    }

    @Bean
    public PermissionEvaluator permissionEvaluator(JdbcMutableAclService aclService) {
        return new AclPermissionEvaluator(aclService);
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(
            PermissionEvaluator permissionEvaluator
    ) {
        DefaultMethodSecurityExpressionHandler handler =
                new DefaultMethodSecurityExpressionHandler();

        handler.setPermissionEvaluator(permissionEvaluator);
        return handler;
    }
}
