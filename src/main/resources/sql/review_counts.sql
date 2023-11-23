create table review_counts
(
    id                    bigint auto_increment
        primary key,
    cheap_fee_count       int    not null,
    good_facility_count   int    not null,
    good_management_count int    not null,
    kindness_count        int    not null,
    lovely_teaching_count int    not null,
    reviewers_count       int    not null,
    academies_id          bigint null,
    constraint UK_ldmvwueivq8pedp7lwaelqtcm
        unique (academies_id),
    constraint FKb2qr8ddo7ays4mivuqdr6mwf2
        foreign key (academies_id) references academies (id)
);

