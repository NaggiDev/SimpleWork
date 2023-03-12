create or replace table study_schema.product
(
    ID           int auto_increment
        primary key,
    PRODUCT_NAME varchar(20)  not null,
    PRICE        double       not null,
    YEAR         timestamp    not null,
    URL          varchar(500) null
);


