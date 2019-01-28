package com.yi.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;

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

}
