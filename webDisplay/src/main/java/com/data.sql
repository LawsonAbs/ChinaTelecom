//0. 电信数据分析数据库
create database mydatabase character set utf8;

//1. 用户基本信息表
drop table if EXISTS user;
create table user(
 teleNumber varchar(12) not null primary key
 ,name varchar(20) not null
)engine=innodb,charset=utf8;


insert into user values ('17802596779','李雁');
insert into user values ('18907263863','卫艺');
insert into user values ('19188980695','仰莉');
insert into user values ('13320266126','陶欣悦');
insert into user values ('19048828124','施梅梅');
insert into user values ('13653454072','金虹霖');
insert into user values ('13135279938','魏明艳');
insert into user values ('18281704261','华贞');
insert into user values ('17035534749','华啟倩');
insert into user values ('19834669675','仲采绿');
insert into user values ('19417467461','卫丹');
insert into user values ('19772366326','戚丽红');
insert into user values ('18283449398','何翠柔');
insert into user values ('16005439091','钱溶艳');
insert into user values ('14924565399','钱琳');
insert into user values ('14218140347','缪静欣');
insert into user values ('17782151215','焦秋菊');
insert into user values ('17340248510','吕访琴');
insert into user values ('19961057493','沈丹');
insert into user values ('19724655139','褚美丽');

