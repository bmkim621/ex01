package com.yi.ex01;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardDAOTest {

	@Autowired
	private BoardDAO dao;
	
/*	@Test
	public void test01InsertBoard() {
		
		BoardVO vo = new BoardVO();
		vo.setTitle("테스트");
		vo.setContent("안녕하세요!!");
		vo.setWriter("김아무개");
		
		dao.insert(vo);
	}
	
	@Test
	public void test02Read() {
		BoardVO vo = dao.read(5);
		System.out.println(vo);
	}
	
	@Test
	public void test03update() {
		
		BoardVO vo = new BoardVO();
		vo.setTitle("수정 테스트 제목");
		vo.setContent("수정 테스트입니다!");
		vo.setBno(5);
		
		dao.update(vo);
		test02Read();
	}
	
	@Test
	public void test04ListAll() {
		List<BoardVO> list = dao.listAll();
		
		for(BoardVO vo : list) {
			System.out.println(vo);
		}
	}*/
	
	/*@Test
	public void test05Delete() {
		dao.delete(5);
	}*/
	
	
	//페이지 구현 테스트
	//@Test
	public void testListPage() {
		int page = 3;
		dao.listPage(page);
	}
	
	//페이지 구현 테스트2
	//@Test
	public void testListCriteria() {
		Criteria cri = new Criteria();
		cri.setPage(2);
		cri.setPerPageNum(20);
		
		dao.listCriteria(cri);
	}
	
	//페이지 갯수
	@Test
	public void testTotalCount() {
		int i = dao.totalCount();
		System.out.println("게시물 총 갯수는 = " + i);
	}
}
