create database ssm-redis;
use ssm-redis;
/*==============================================================*/
/* Table: 红包表                                        */
/*==============================================================*/
create table T_RED_PACKET
(
   id                   int(12)                        not null auto_increment,
   user_id              int(12)                        not null,
   amount               decimal(16,2)                  not null,
   send_date            timestamp                      not null,
   total                int(12)                        not null,
   unit_amount          decimal(12)                    not null,
   stock                int(12)                        not null,
   version              int(12) default 0              not null,
   note                 varchar(256)                    null,
   primary key clustered (id)
);

/*==============================================================*/
/* Table: 用户抢红包表                                                */
/*==============================================================*/
create table T_USER_RED_PACKET
(
   id                   int(12)                        not null auto_increment,
   red_packet_id        int(12)                        not null,
   user_id              int(12)                        not null,
   amount               decimal(16,2)                  not null,
   grab_time            timestamp                      not null,
   note                 varchar(256)                   null,
    primary key clustered (id)
);




/**
* 插入一个20万元金额，2万个小红包，每个10元的红包数据
*/
insert into T_RED_PACKET(user_id, amount, send_date, total, unit_amount, stock, note)
 values(1, 200000.00, now(), 20000, 10.00, 20000,'20万元金额，2万个小红包，每个10元');

/**
 * 可重复测试脚本
 */
-- UPDATE T_RED_PACKET t SET t.stock = 0, t.version = 0 WHERE id = 1;
-- TRUNCATE TABLE t_user_red_packet;