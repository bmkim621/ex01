package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;
import com.yi.persistence.BoardDAO;
import com.yi.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO dao;
	
	//댓글 수 증가시키는 dao
	@Autowired
	private BoardDAO boardDao;
	
	@Override
	public List<ReplyVO> list(int bno) {
		// TODO Auto-generated method stub
		return dao.list(bno);
	}

	@Override
	@Transactional
	public void create(ReplyVO vo) {
		// TODO Auto-generated method stub
		dao.create(vo);
		//댓글 추가할 때 글 제목 옆에 댓글 수도 같이 올라가도록 한다. 댓글 1개 작성할 때 1증가하므로 amount = 1
		boardDao.updateReplyCnt(vo.getBno(), 1);
	}

	@Override
	public void update(ReplyVO vo) {
		// TODO Auto-generated method stub
		dao.update(vo);
	}

	@Override
	@Transactional
	public void delete(int rno) {
		// TODO Auto-generated method stub
		//rno를 이용하여 bno 값을 가져온다.
		ReplyVO vo = dao.selectByRno(rno);
		//rno를 이용하여 bno 가지고 온 다음에 삭제가 이루어져야 함. 삭제 먼저 일어나면 삭제된 bno를 가지고 올 수 없으므로.
		dao.delete(rno);
		//댓글 삭제할 때 글 제목 옆에 댓글 수도 같이 감소시킨다. 댓글 1개 작성할 때 1 감소하므로 amount = -1
		boardDao.updateReplyCnt(vo.getBno(), -1);
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
