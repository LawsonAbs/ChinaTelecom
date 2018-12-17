//0. 电信数据分析数据库
create database mydatabase character set utf8;

//1. 用户基本信息表
create table user(
 teleNumber varchar(12) not null primary key
 ,name varchar(20) not null
);


//2. 通话记录分析表
create table statistics (
 teleNumber varchar(12) not null
,yearmonth varchar(20) not null
,callDuration int not null
)

//3. 亲密度分析结果表
create table intimacy (
 caller varchar(12) not null
 ,callee varchar(12) not null
,totalTime int not null
)

//4.插入数据
insert into statistics values
(18907263863,201812,3000),
(18907263863,201811,2900),
(18907263863,201810,2400),
(18907263863,201809,1300),
(18907263863,201808,1100),
(18907263863,201807,945) ,
(18907263863,201806,321) ,
(18907263863,201805,103) ,
(18907263863,201804,834) ,
(18907263863,201803,321) ,
(18907263863,201802,4249),
(18907263863,201801,1024);

insert into intimacy values
(18907263863,13234567893,523),
(18907263863,17802596779,20 ),
(18907263863,19188980695,32 ),
(18907263863,13320266126,13 ),
(18907263863,19048828124,289),
(18907263863,13653454072,182),
(18907263863,13135279938,121),
(18907263863,18281704261,3  ),
(18907263863,17035534749,5  ),
(18907263863,19834669675,2  ),
(18907263863,19417467461,35 ),
(18907263863,19772366326,56 ),
(18907263863,18283449398,2  ),
(18907263863,16005439091,7  ),
(18907263863,14924565399,23 ),
(18907263863,14218140347,14 );



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

