create table regions
(
    id          bigint auto_increment
        primary key,
    sido        varchar(255) not null,
    sigungu     varchar(255) not null,
    upmyeondong varchar(255) not null,
    latitude    double       not null,
    longitude   double       not null
);

