create table acl_sid (
                         id bigserial not null primary key,
                         principal boolean not null,
                         sid varchar(100) not null,
                         constraint unique_acl_sid unique (sid, principal)
);

create table acl_class (
                           id bigserial not null primary key,
                           class varchar(255) not null,
                           class_id_type varchar(255),
                           constraint unique_acl_class unique (class)
);

create table acl_object_identity (
                                     id bigserial primary key,
                                     object_id_class bigint not null,
                                     object_id_identity varchar(36) not null,
                                     parent_object bigint,
                                     owner_sid bigint,
                                     entries_inheriting boolean not null,
                                     constraint unique_acl_object_identity unique (object_id_class, object_id_identity),
                                     constraint foreign_acl_class foreign key(object_id_class) references acl_class(id),
                                     constraint foreign_acl_sid foreign key(owner_sid) references acl_sid(id),
                                     constraint foreign_acl_object_parent foreign key(parent_object) references acl_object_identity(id)
);

create table acl_entry (
                           id bigserial primary key,
                           acl_object_identity bigint not null,
                           ace_order int not null,
                           sid bigint not null,
                           mask int not null,
                           granting boolean not null,
                           audit_success boolean not null,
                           audit_failure boolean not null,
                           constraint unique_acl_entry unique (acl_object_identity, ace_order),
                           constraint foreign_acl_object_identity foreign key (acl_object_identity)
                               references acl_object_identity(id),
                           constraint foreign_acl_entry_sid foreign key (sid)
                               references acl_sid(id)
);