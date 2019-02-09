-- 테스트
select * from tbl_member;
select * from tbl_board;

update tbl_board
set viewcnt = viewcnt + 1
where bno = 5;

select count(bno) from tbl_board;

insert into tbl_board (title, content, writer)
(select title, content, writer from tbl_board);