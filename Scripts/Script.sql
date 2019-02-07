-- book_ex
DROP SCHEMA IF EXISTS book_ex;

-- coffee_management
CREATE SCHEMA book_ex;

-- table
CREATE TABLE book_ex.tbl_board(
	bno int not null auto_increment,
	title varchar(200) not null,
	content text null,
	writer varchar(50) not null,
	regdate timestamp not null default now(),
	viewcnt int default 0,
	primary key (bno)
);

-- table
create table book_ex.tbl_member(
	userid varchar(50) not null,
	userpw varchar(50) not null,
	username varchar(50) not null,
	email varchar(100),
	regdate timestamp default now(),
	updatedate timestamp default now(),
	primary key(userid)
);

-- ex02 프로젝트 댓글 테이블
create table book_ex.tbl_reply(
	rno int not null auto_increment,
	bno int not null default 0,
	replytext varchar(1000) not null,
	replyer varchar(50) not null,
	regdate timestamp not null default now(),
	updatedate timestamp not null default now(),
	primary key (rno)
);

-- tbl_reply에 외래키 추가
alter table book_ex.tbl_reply add constraint fk_board foreign key (bno) references tbl_board(bno);