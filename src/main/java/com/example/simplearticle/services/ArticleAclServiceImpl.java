package com.example.simplearticle.services;

import com.example.simplearticle.models.Article;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleAclServiceImpl implements ArticleAclService {
    private final MutableAclService mutableAclService;

    public ArticleAclServiceImpl(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }

    @Transactional
    @Override
    public void createAclForArticle(Long articleId, String username) {
        ObjectIdentity objectIdentity =
                new ObjectIdentityImpl(Article.class, articleId);

        MutableAcl acl = mutableAclService.createAcl(objectIdentity);

        Sid owner = new PrincipalSid(username);

        acl.setOwner(owner);

        acl.insertAce(0, BasePermission.READ, owner, true);
        acl.insertAce(1, BasePermission.WRITE, owner, true);
        acl.insertAce(2, BasePermission.DELETE, owner, true);
        acl.insertAce(3, BasePermission.ADMINISTRATION, owner, true);

        mutableAclService.updateAcl(acl);
    }
}
