drop table if exists statistics;
CREATE TABLE statistics (
  telenumber varchar(12) NOT NULL,
  callDuration int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (telenumber)
) ENGINE=InnoDB DEFAULT CHARSET=utf8