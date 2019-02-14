-- book_ex
DROP SCHEMA IF EXISTS book_ex;

-- coffee_management
CREATE SCHEMA book_ex;

use book_ex;

-- table
CREATE TABLE tbl_board(
	bno int not null auto_increment,
	title varchar(200) not null,
	content text null,
	writer varchar(50) not null,
	regdate timestamp not null default now(),
	viewcnt int default 0,
	primary key (bno)
);

-- table
create table tbl_member(
	userid varchar(50) not null,
	userpw varchar(50) not null,
	username varchar(50) not null,
	email varchar(100),
	regdate timestamp default now(),
	updatedate timestamp default now(),
	primary key(userid)
);

-- ex02 프로젝트 댓글 테이블
create table tbl_reply(
	rno int not null auto_increment,
	bno int not null default 0,
	replytext varchar(1000) not null,
	replyer varchar(50) not null,
	regdate timestamp not null default now(),
	updatedate timestamp not null default now(),
	primary key (rno)
);

-- tbl_reply에 외래키 추가
alter table tbl_reply add constraint fk_board foreign key (bno) references tbl_board(bno);

-- 글 제목 옆에 나오는 댓글 수
alter table tbl_board add column replycnt int default 0;

-- 댓글 수 일치시키기
update tbl_board set replycnt = (select count(rno) from tbl_reply where bno = tbl_board.bno);
-- 댓글 테스트 확인
select * from tbl_board order by bno desc;


-- 파일 업로드 관련
create table tbl_attach(
	fullName varchar(150) not null,
	bno int not null,
	regdate timestamp default now(),
	primary key (fullName)
);

alter table tbl_attach add constraint fk_board_attach
foreign key (bno) references tbl_board (bno);
