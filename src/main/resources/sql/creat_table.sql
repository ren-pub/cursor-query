createdatabase cursor_test;

create table career
(
    id        int auto_increment comment '主键'
        primary key,
    career_en varchar(50) default '' not null comment '职业 英文名',
    career_ch varchar(50) default '' not null comment '职业 中文名'
) comment '职业信息表';

create table order_detail
(
    id       int auto_increment comment '主键'
        primary key,
    order_id int          default 0  not null comment '订单id',
    address  varchar(100) default '' not null comment '收货地址',
    user     varchar(20)  default '' not null comment '收货人'
) comment '订单-测试详情表';

create table order_main
(
    id         int auto_increment comment '主键'
        primary key,
    order_no   varchar(50) default '' not null comment '订单编号',
    creat_time bigint      default 0  not null comment '创建时间'
) comment '订单-测试表';

create table sex
(
    id   int auto_increment comment '主键'
        primary key,
    sex  tinyint default 1   not null comment '性别',
    name char(2) default '1' not null comment '性别 1 男性 0女性'
) comment '性别表';

create table user
(
    id     int auto_increment comment '主键'
        primary key,
    a      varchar(50) default '------' not null comment 'a',
    b      varchar(50) default '------' not null comment 'b',
    c      varchar(50) default '------' not null comment 'c',
    d      varchar(50) default '------' not null comment 'd',
    e      varchar(50) default '------' not null comment 'e',
    f      varchar(50) default '------' not null comment 'f',
    g      varchar(50) default '------' not null comment 'g',
    h      varchar(50) default '------' not null comment 'h',
    i      varchar(50) default '------' not null comment 'i',
    j      varchar(50) default '------' not null comment 'j',
    k      varchar(50) default '------' not null comment 'k',
    age    int         default 30       not null comment '用户年纪',
    sex    tinyint     default 1        not null comment '用户性别',
    career varchar(50) default ''       not null comment 'engineer'
) comment '用户-测试表';

