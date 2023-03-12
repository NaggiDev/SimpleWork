create or replace table study_schema.product
(
    ID           int auto_increment
        primary key,
    PRODUCT_NAME varchar(20)  not null,
    PRICE        double       not null,
    YEAR         timestamp    not null,
    URL          varchar(500) null,
    DESCRIPTION  varchar(500) null
)
    auto_increment = 4;

create or replace table study_schema.user_privilege
(
    USER_PRIVILEGE_ID int auto_increment
        primary key,
    USER_ROLE         varchar(20)  not null,
    DESCRIPTION       varchar(100) null
);

create or replace table study_schema.user_account
(
    ID             int auto_increment
        primary key,
    USER_NAME      varchar(10) not null,
    PASSWORD       varchar(16) null,
    USER_PRIVILEGE int         null comment 'ánh xạ quyền tài khoản sang bảng user_privilege',
    ACCOUNT_STATUS int         null comment 'trạng thái tài khoản 1. hoạt động  2. không hoạt động 3. hủy/khóa
',
    constraint USER_ACCOUNT_user_privilege_USER_PRIVILEGE_ID_fk
        foreign key (USER_PRIVILEGE) references study_schema.user_privilege (USER_PRIVILEGE_ID)
);

