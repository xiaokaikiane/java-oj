DROP TABLE IF EXISTS oj_table;
create table oj_table(
id int primary key auto_increment,
title varchar(50),
level varchar(50),
description text,
templateCode text,
testCode text
);