package com.yi.persistence;

import java.util.List;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

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

	//=============== 게시물 총 갯수 ====================
	public int totalCount();
	
	//==================== 검색 ====================
	public List<BoardVO> listSearch(SearchCriteria cri);
	
	public int searchTotalCount(SearchCriteria cri);
	
	//댓글 작성 시 글제목 옆에 댓글 수도 같이 증가되도록
	public void updateReplyCnt(int bno, int amount);
}
