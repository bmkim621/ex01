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
		logger.info("======> list ----- GET");
		List<BoardVO> list = service.listSearch(cri); // cri 넣기 위해서 Model 필요

		// 페이지 하단 부분
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.searchTotalCount(cri)); // totalCount어디에서? mapper에서 처리해야함.

		logger.info("list = " + list);
		logger.info("page = " + pageMaker.getCri().getPage());
		logger.info("cri = " + cri);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("cri", cri);
	}
	
	
	//게시글 등록
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public void registerGet() {
		logger.info("======> register ----- GET");
	}
	
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo, Model model) {
		logger.info("======> register ----- POST");
		
		service.regist(vo);
		model.addAttribute("result", "success");
		
		//board폴더 안에 success.jsp 파일이 만들어져야하므로. => /board/success로 하면 success화면에서 F5누르면 계속 같은 데이터 추가되기 때문에 redirect 시켜야 함.
		return "redirect:/sBoard/list";
	}
	
	
	//글 읽기
	@RequestMapping(value = "readPage", method = RequestMethod.GET)
	public void readPage(@RequestParam("bno") int bno, SearchCriteria cri, Model model) {
		logger.info("======> readPage ----- GET");
		
		BoardVO vo = service.read(bno);
		service.increaseViewCnt(bno);
		
		logger.info("BoardVO = " + vo);
		logger.info("cri = " + cri);
		logger.info("page = " + cri.getPage());
		 
		model.addAttribute("boardVO", vo);
		model.addAttribute("cri", cri);
		
	}
	
	//삭제
	@RequestMapping(value = "removePage", method = RequestMethod.POST)
	public String removePage(@RequestParam("bno") int bno, SearchCriteria cri, Model model) {
		logger.info("======> removePage ----- POST");
		logger.info("페이지번호 = " + cri.getPage());
		logger.info("검색종류 = " + cri.getSearchType());
		logger.info("검색어 = " + cri.getKeyword());
		
		service.remove(bno);
		// redirect 때는 cri 객체 전체를 전달할 수 없음. 하나씩 심어서 보내야함. ex) "page", cri.getPage()
		model.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/sBoard/list?page=" + cri.getPage() + "&searchType=" + cri.getSearchType();
	}	
	
	
	
	//수정
	//글 수정할 때 수정해야 할 부분 받아오기
	@RequestMapping(value = "modifyPage", method = RequestMethod.GET)
	public void modifyPage(@RequestParam("bno") int bno, SearchCriteria cri, Model model) {
		logger.info("======> modifyPage ----- GET");
		
		BoardVO vo = service.read(bno);
			
		model.addAttribute("boardVO", vo);
		model.addAttribute("cri", cri);
	}
		
	//글 수정하고 나서 페이지 있는 목록으로 돌아가기
	@RequestMapping(value = "modifyPage", method = RequestMethod.POST)
	public String modifyPage(BoardVO vo, @RequestParam("bno") int bno, SearchCriteria cri, Model model) {
		logger.info("======> modifyPage ----- POST");
		logger.info("bno = " + bno);
		logger.info("페이지번호 = " + cri.getPage());
		logger.info("검색종류 = " + cri.getSearchType());
		logger.info("검색어 = " + cri.getKeyword());
		
		service.modify(vo);
		// redirect 때는 cri 객체 전체를 전달할 수 없음. 하나씩 심어서 보내야함. ex) "page", cri.getPage()
		model.addAttribute("keyword", cri.getKeyword());

		return "redirect:/sBoard/readPage?page=" + cri.getPage() + "&bno=" + vo.getBno() + "&page=" + cri.getPage() + "&searchType=" + cri.getSearchType();
	}

	
}
