create database test1
create table member
(
code int primary key generated always as identity(start with 1,increment by 1),
username char(20) not null unique,
name char(20) not null,
e_password char(100) not null,
k_password char(100) not null
);
create table friend
(
friend_code int not null ,
member_code int not null ,
primary key(friend_code,member_code),
foreign key(member_code) references member,
foreign key(friend_code) references member
);
create table friend_request 
(
sent_by int,
sent_to int,
primary key(sent_by,sent_to),
foreign key(sent_by) references member,
foreign key (sent_to) references member
);
create table message ( code BIGINT primary key generated always as identity (start with 1,increment by 1),
message_date date not null,
message_time time not null,
from_code int not null, to_code int not null, 
message varchar(500) not null, status char(1) not null, 
foreign key(from_code) references member, 
foreign key(to_code) references member 
); 
create table notification ( code BIGINT primary key generated always as identity (start with 1,increment by 1), 
notification_date date not null, 
notification_time time not null, 
member_code int not null, 
entity_code int not null, 
notification_type int not null, 
foreign key(member_code) references member 
);
create table closed_account
(
code int not null unique,
foreign key(code) references member 
);
