create table `role`(
    id bigint not null auto_increment,
    name varchar (100) not null,

    primary key (id)
);

Alter table `user`
ADD COLUMN user_role_id BIGINT,
ADD CONSTRAINT fk_user_role
foreign key (user_role_id) references `role`(id)