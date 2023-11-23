create table lessons
(
    id           bigint auto_increment
        primary key,
    capacity     int          null,
    curriculum   varchar(255) null,
    duration     varchar(255) null,
    subject      varchar(255) null,
    total_fee    bigint       null,
    academies_id bigint       null,
    constraint FKes7xopihaburv8e9ugsr0tvqd
        foreign key (academies_id) references academies (id)
);

