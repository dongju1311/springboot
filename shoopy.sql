/** 데이터베이스 생성 **/
create database shoppy;

/** 데이터베이스 열기 **/
use shoppy;
select database();

/** 테이블 목록 확인 **/
show tables;

/** member 테이블 생성 **/
create table member(
	id		varchar(50) primary key,
    pwd		varchar(50) not null,
    name 	varchar(20) not null,
    phone 	char(13),
	email 	varchar(50) not null,
    mdate	date
);
show tables;
desc member;

select * from member;
select count(id) from member where id='test';
select count(*) from member where id='test' and pwd='1234';
