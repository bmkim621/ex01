package com.yi.service;

import java.util.List;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

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
	
	//게시물 총 갯수
	public int totalCount();
	
	//검색
	public List<BoardVO> listSearch(SearchCriteria cri);
	
	public int searchTotalCount(SearchCriteria cri);
	
}
