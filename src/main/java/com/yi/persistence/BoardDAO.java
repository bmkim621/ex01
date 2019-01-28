package com.yi.persistence;

import java.util.List;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;

public interface BoardDAO {
	public void insert(BoardVO vo);
	
	public BoardVO read(int bno);
	
	//제목, 내용만 수정되도록 한다.
	public void update(BoardVO vo);
	
	public void delete(int bno);
	
	public List<BoardVO> listAll();
	
	//조회수
	public void increaseViewCnt(int bno);
	
	//==================== 페이지 ========================
	public List<BoardVO> listPage(int page);
	
	public List<BoardVO> listCriteria(Criteria cri);

	
}
