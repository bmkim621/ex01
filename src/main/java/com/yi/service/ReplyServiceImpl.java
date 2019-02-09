package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;
import com.yi.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO dao;
	
	@Override
	public List<ReplyVO> list(int bno) {
		// TODO Auto-generated method stub
		return dao.list(bno);
	}

	@Override
	public void create(ReplyVO vo) {
		// TODO Auto-generated method stub
		dao.create(vo);
	}

	@Override
	public void update(ReplyVO vo) {
		// TODO Auto-generated method stub
		dao.update(vo);
	}

	@Override
	public void delete(int rno) {
		// TODO Auto-generated method stub
		dao.delete(rno);
	}

	@Override
	public List<ReplyVO> listPage(Criteria cri, int bno) {
		// TODO Auto-generated method stub
		return dao.listPage(cri, bno);
	}

	@Override
	public int totalCount(int bno) {
		// TODO Auto-generated method stub
		return dao.totalCount(bno);
	}

}
