create sequence albums_seq start with 1 increment by 50;
create sequence countries_seq start with 1 increment by 50;
create sequence persons_seq start with 1 increment by 50;
create sequence photographers_seq start with 1 increment by 50;
create sequence photos_seq start with 1 increment by 50;

create table album_photos
(
    position integer not null,
    album_id bigint  not null,
    photo_id bigint  not null,
    primary key (position, album_id)
);

create table albums
(
    album_type varchar(1) default 'D' check (album_type IN ('D', 'P')) not null,
    id         bigint                                                  not null,
    owner_id   bigint                                                  not null,
    album_name varchar(64)                                             not null,
    primary key (id)
);

create table countries
(
    country_code     integer check ((country_code <= 9999) and (country_code >= 1)),
    iso2code         varchar(2)  not null,
    top_level_domain varchar(4)  not null,
    id               bigint      not null,
    country_name     varchar(64) not null,
    primary key (id)
);

create table persons
(
    id         bigint       not null,
    nick_name  varchar(16)  not null,
    last_name  varchar(32)  not null,
    first_name varchar(64)  not null,
    username   varchar(64)  not null,
    password   varchar(128) not null,
    primary key (id)
);

create table photographer_emails
(
    photographer_id bigint      not null,
    email           varchar(64) not null,
    primary key (photographer_id, email)
);

create table photographers
(
    mobile_area_code     integer      not null,
    mobile_country_code  integer      not null,
    studio_area_code     integer,
    studio_country_code  integer,
    country_id           bigint,
    id                   bigint       not null,
    mobile_serial_number varchar(16)  not null,
    studio_serial_number varchar(16),
    zip_code             varchar(16)  not null,
    last_name            varchar(32)  not null,
    city                 varchar(64)  not null,
    first_name           varchar(64)  not null,
    street_number        varchar(64)  not null,
    username             varchar(64)  not null,
    password             varchar(128) not null,
    primary key (id)
);

create table photos
(
    height              integer,
    latitude            float(53),
    longitude           float(53),
    orientation         varchar(1) check (orientation in ('L', 'P', 'S')),
    width               integer,
    creation_time_stamp timestamp(6) not null,
    id                  bigint       not null,
    photographer_id     bigint,
    photo_name          varchar(64),
    description         varchar(256),
    primary key (id)
);

alter table if exists album_photos
    add constraint FK_album_photos_2_photos foreign key (photo_id) references photos;
alter table if exists album_photos
    add constraint FK_album_photos_2_albums foreign key (album_id) references albums;
alter table if exists albums
    add constraint FK_albums_2_photographers foreign key (owner_id) references photographers;
alter table if exists photographer_emails
    add constraint FK_photographer_emails_2_photographers foreign key (photographer_id) references photographers;
alter table if exists photographers
    add constraint FK_address_2_countries foreign key (country_id) references countries;
alter table if exists photos
    add constraint FK_photos_2_photographers foreign key (photographer_id) references photographers;
alter table if exists persons
    add column person_key varchar(40) not null;
