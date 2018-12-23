drop table if exists monthStat;
CREATE TABLE monthStat (
  telenumber varchar(12) NOT NULL,
  callDuration int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (telenumber)
) ENGINE=InnoDB DEFAULT CHARSET=utf8