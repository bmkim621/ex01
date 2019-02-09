package com.yi.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;

@Repository
public class ReplyDaoImpl implements ReplyDAO {

	@Autowired
	private SqlSession sqlSession;
	private static final String namespace = "com.yi.mapper.ReplyMapper";
	
	@Override
	public List<ReplyVO> list(int bno) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + ".list", bno);
	}

	@Override
	public void create(ReplyVO vo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".create", vo);
	}

	@Override
	public void update(ReplyVO vo) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace + ".update", vo);
	}

	@Override
	public void delete(int rno) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + ".delete", rno);
	}

	@Override
	public List<ReplyVO> listPage(Criteria cri, int bno) {
		// TODO Auto-generated method stub
		//매개변수가 2개니까 map 써서 보냄(selectList에는 매개변수 1개만 사용해야되므로) map(key, value) : key(대체로 String 사용), Object(부모인 Object사용)
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("cri", cri);
		
		return sqlSession.selectList(namespace + ".listPage", map);
	}

	@Override
	public int totalCount(int bno) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".totalCount", bno);
	}

}
