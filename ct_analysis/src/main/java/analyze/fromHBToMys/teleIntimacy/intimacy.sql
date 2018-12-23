-- 亲密用户信息表，用于展现call1 的用户在year 年时的亲密用户
drop table if exists intimacy;
create table intimacy(
call1 varchar(12) not null primary key,
call2 varchar(12) not null ,
year varchar(6) not null,
callDuration int not null DEFAULT  0
);

create unique index uidx_call1_call2_year on intimacy(call1,call2,year);