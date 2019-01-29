package com.yi.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;

@Controller
@RequestMapping("/sBoard/*")
public class SearchBoardController {
	//로그
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	//주입받기(DI, Dipendency Injection)
	//@Autowired = @Inject 기능 같음.
	@Inject
	private BoardService service;
	
	
	// url주소 : sBoard/list?page=10
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(SearchCriteria cri, Model model) {
		logger.info("list ----- GET");
		List<BoardVO> list = service.listSearch(cri); // cri 넣기 위해서 Model 필요

		// 페이지 하단 부분
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.searchTotalCount(cri)); // totalCount어디에서? mapper에서 처리해야함.

		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("cri", cri);
	}
	
	
	//게시글 등록
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public void registerGet() {
		logger.info("register ----- GET");
	}
	
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo, Model model) {
		logger.info("register ----- POST");
		
		service.regist(vo);
		model.addAttribute("result", "success");
		
		//board폴더 안에 success.jsp 파일이 만들어져야하므로. => /board/success로 하면 success화면에서 F5누르면 계속 같은 데이터 추가되기 때문에 redirect 시켜야 함.
		return "redirect:/sBoard/list";
	}
	
	
	//글 읽기
	@RequestMapping(value = "readPage", method = RequestMethod.GET)
	public void readPage(@RequestParam("bno") int bno, SearchCriteria cri, Model model) {
		logger.info("readPage ----- GET");
		
		BoardVO vo = service.read(bno);
		service.increaseViewCnt(bno);
		
		model.addAttribute("boardVO", vo);
		model.addAttribute("cri", cri);
	}
	
	//글 읽기 눌렀을 때 List로 이동하기
	@RequestMapping(value = "listPage", method = RequestMethod.GET)
	public void listPage(SearchCriteria cri, Model model) {
		logger.info("listPage ----- GET");
		List<BoardVO> list = service.listSearch(cri); //cri 넣기 위해서 Model 필요
		
		//페이지 하단 부분
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.searchTotalCount(cri));	//totalCount어디에서? mapper에서 처리해야함.
		
		logger.info("list ----- "  + list);
		logger.info("pageMaker ----- "  + pageMaker.toString());
		logger.info("cri ----- "  + cri);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("cri", cri);
	}	
	
}
