package com.yi.service;

import java.util.List;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;

public interface BoardService {
	public void regist(BoardVO vo);
	
	public BoardVO read(int bno);
	
	public void modify(BoardVO vo);
	
	public void remove(int bno);
	
	public List<BoardVO> listAll();
	
	//조회수
	public void increaseViewCnt(int bno);
	
	//페이지
	public List<BoardVO> listCriteria(Criteria cri);
}
