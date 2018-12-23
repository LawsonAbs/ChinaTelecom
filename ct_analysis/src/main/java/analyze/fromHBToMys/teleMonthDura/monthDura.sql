drop table if exists monthStat;
create table monthStat(
teleNumber varchar(12) not null ,
yearMonth varchar(6) not null ,
callDuration int not null DEFAULT  0,
primary key(teleNumber,yearMonth)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;