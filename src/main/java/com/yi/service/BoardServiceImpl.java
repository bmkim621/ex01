package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;
import com.yi.persistence.BoardDAO;

//주입받기 위해서 Service에서는 @Service 붙이기
@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO dao;
	
	@Override
	@Transactional	//서로 다른 테이블이 같은 connection을 사용하기 위해
	public void regist(BoardVO vo) {
		// TODO Auto-generated method stub
		dao.insert(vo);
		
		//첨부파일
		List<String> files = vo.getFiles();	//String 배열
		
		if(files == null || files.size() == 0) {	//첨부파일 올리지 않는 경우
			return;
		}
		
		//첨부파일 있는 경우
		for(String fullname : files) {
			dao.addAttach(fullname);
		}
	}


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)	//읽기만 가능
	public BoardVO read(int bno) {
		// TODO Auto-generated method stub
		BoardVO vo = dao.read(bno);
		vo.setFiles(dao.getAttach(bno));
		
		return dao.read(bno);
	}

	@Override
	public void modify(BoardVO vo) {
		// TODO Auto-generated method stub
		//삭제하고싶은 파일의 이름을 이용하여 삭제한다.(지워야 할 것)
		dao.update(vo);
	}

	@Override
	@Transactional
	public void remove(int bno) {
		// TODO Auto-generated method stub
		//getAttach에서 bno에 해당하는 파일명 받아오기
		List<String> filename = dao.getAttach(bno);
		//attach 테이블에 있는 거 삭제한 다음에 게시물 삭제
		dao.delAttach(bno);		
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() {
		// TODO Auto-generated method stub
		return dao.listAll();
	}

	@Override
	public void increaseViewCnt(int bno) {
		// TODO Auto-generated method stub
		dao.increaseViewCnt(bno);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) {
		// TODO Auto-generated method stub
		return dao.listCriteria(cri);
	}

	//게시물 총 갯수
	@Override
	public int totalCount() {
		// TODO Auto-generated method stub
		return dao.totalCount();
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) {
		// TODO Auto-generated method stub
		return dao.listSearch(cri);
	}

	@Override
	public int searchTotalCount(SearchCriteria cri) {
		// TODO Auto-generated method stub
		return dao.searchTotalCount(cri);
	}


	//삭제하고 싶은 파일이 담겨져 있음
	@Override
	@Transactional
	public void modifyFile(BoardVO vo, String[] delFiles, List<String> addImages) {
		// TODO Auto-generated method stub
		
		//삭제할 파일이 있을 경우에만
		if(delFiles != null) {
			for(String file : delFiles) {
				dao.deleteAttachByFullName(vo.getBno(), file);
			}
		}
		
		//새로운 파일 등록하는 경우
		for(String file : addImages) {
			dao.addAttachByBno(file, vo.getBno());
		}
		
		dao.update(vo);
	}

}
