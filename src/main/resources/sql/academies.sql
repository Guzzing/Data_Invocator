create table academies
(
    id                bigint auto_increment
        primary key,
    updated_date      date                                       null,
    area_of_expertise varchar(255)                               null,
    contact           varchar(255)                               null,
    name              varchar(255)                               not null,
    shuttle           enum ('AVAILABLE', 'FREE', 'NEED_INQUIRE') null,
    full_address      varchar(255)                               not null,
    latitude          double                                     not null,
    longitude         double                                     not null,
    max_education_fee bigint                                     null
);

