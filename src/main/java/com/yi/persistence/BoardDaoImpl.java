package com.yi.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

@Repository
public class BoardDaoImpl implements BoardDAO {

	@Autowired
	private SqlSession sqlSession;
	private static final String namespace = "com.yi.mapper.BoardMapper";
	
	
	@Override
	public void insert(BoardVO vo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insert", vo);
	}

	@Override
	public BoardVO read(int bno) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".read", bno);
	}

	@Override
	public void update(BoardVO vo) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace + ".update", vo);
	}

	@Override
	public void delete(int bno) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + ".delete", bno);
	}

	@Override
	public List<BoardVO> listAll() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + ".listAll");
	}

	@Override
	public void increaseViewCnt(int bno) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace + ".increaseViewCnt", bno);
	}

	@Override
	public List<BoardVO> listPage(int page) {
		// TODO Auto-generated method stub
		if(page < 0) {
			page = 1;
		}
		page = (page - 1) * 10;
		
		return sqlSession.selectList(namespace + ".listPage", page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + ".listCriteria", cri);
	}

	@Override
	public int totalCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".totalCount");
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + ".listSearch", cri);
	}

	@Override
	public int searchTotalCount(SearchCriteria cri) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".searchTotalCount", cri);
	}

	@Override
	public void updateReplyCnt(int bno, int amount) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("amount", amount);
		
		sqlSession.update(namespace + ".updateReplyCnt", map);
	}

	@Override
	public void addAttach(String fullname) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".addAttach", fullname);
	}

	@Override
	public List<String> getAttach(int bno) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + ".getAttach", bno);
	}

	@Override
	public void delAttach(int bno) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + ".delAttach", bno);
	}

	@Override
	public void deleteAttachByFullName(int bno, String fullname) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("fullname", fullname);
		
		sqlSession.delete(namespace + ".deleteAttachByFullName", map);
	}

	@Override
	public void addAttachByBno(String fullname, int bno) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("fullname", fullname);
		map.put("bno", bno);
		
		sqlSession.insert(namespace + ".addAttachByBno", map);
	}

}
