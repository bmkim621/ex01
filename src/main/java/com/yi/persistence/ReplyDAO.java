package com.yi.persistence;

import java.util.List;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;

public interface ReplyDAO {
	public List<ReplyVO> list(int bno);	//게시물에 해당하는 댓글 리스트를 모두 보여야 함.
	
	public void create(ReplyVO vo);
	
	public void update(ReplyVO vo);
	
	public void delete(int rno);	//해당 댓글을 삭제한다.
	
	public List<ReplyVO> listPage(Criteria cri, int bno);
	
	public int totalCount(int bno);
	
	//rno를 통해 bno를 가지고온다.
	public ReplyVO selectByRno(int rno);
}
