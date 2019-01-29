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

select * from book_ex.tbl_board;

insert into book_ex.tbl_board (title , content, writer)
(select title , content, writer from book_ex.tbl_board);