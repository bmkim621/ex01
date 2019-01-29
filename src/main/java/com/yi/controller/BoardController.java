package com.yi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.service.BoardService;

//Controller로 인식시키기 위해
@Controller
//BoardController 안에 있는 모든 url Command 앞에 board/가 자동으로 붙는다. ex) board/listAll
@RequestMapping("/board/*")
public class BoardController {
	
	//주입받기(DI, Dipendency Injection)
	@Autowired
	private BoardService service;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	//리스트 보기
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	public void listAll(Model model) {
		logger.info("listAll ----- GET");
		
		//void로 만들면 value와 같은 이름으로 파일 만들어짐. => 앞에 /는 폴더로 인식하기 때문에 board/listAll => 즉 board 폴더 안에 listAll.jsp 파일을 만든다.
		List<BoardVO> list = service.listAll();
		model.addAttribute("list", list);
	}
	
	//페이지 구현하기 => 반드시 GET 방식으로
	@RequestMapping(value = "listCri", method = RequestMethod.GET)
	public void listCri(Criteria cri, Model model) {
		logger.info("listCri ----- GET");
		List<BoardVO> list = service.listCriteria(cri); //cri 넣기 위해서 Model 필요
		
		model.addAttribute("list", list);
	}
	
	//페이지 구현하기 => 반드시 GET 방식으로
	@RequestMapping(value = "listPage", method = RequestMethod.GET)
	public void listPage(Criteria cri, Model model) {
		logger.info("listPage ----- GET");
		List<BoardVO> list = service.listCriteria(cri); //cri 넣기 위해서 Model 필요
		
		//페이지 하단 부분
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalCount());	//totalCount어디에서? mapper에서 처리해야함.
		
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
	}
	
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	//게시글 등록
	public void registerGet() {
		logger.info("register ----- GET");
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo, Model model) {
		logger.info("register ----- POST");
		
		//주입받은 서비스 사용하기
		//BoardVO는 어디서 받아오지? => form에서 받아오기(name값이 boardVO에 필드명이랑 같으니까 통째로 받아오기)
		//jsp에 어떤 걸 보낼 때 Model사용.
		service.regist(vo);
		model.addAttribute("result", "success");
		
		
		//board폴더 안에 success.jsp 파일이 만들어져야하므로. => /board/success로 하면 success화면에서 F5누르면 계속 같은 데이터 추가되기 때문에 redirect 시켜야 함.
		return "redirect:/board/listAll";
	}
	
	
	
	
	//글 읽기 => a태그 글 읽는 것은 get 방식
	@RequestMapping(value = "read", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, Criteria cri, Model model) {
		//spring은 알아서 String -> int로 바꿔줌. 확실하게 하기 위해서 @RequestParam 붙이는 것이 좋음.
		// 예) @RequestParam을 사용하면 bno 뒤에 값은 무조건 int로 받아야 함.(아니면 에러) board/read?bno=1
		BoardVO vo = service.read(bno);
		
		//글 읽을 때 조회수 증가시키기
		service.increaseViewCnt(bno);
		
		//이 vo를 jsp로 전달해준다. => Model 사용한다.
		model.addAttribute("boardVO", vo);
		model.addAttribute("cri", cri);
	}
	
	//글 읽기해서 ListAll 버튼 눌렀을 때
	@RequestMapping(value = "readPage", method = RequestMethod.GET)
	public void readPage(@RequestParam("bno") int bno, Criteria cri, Model model) {
		logger.info("readPage ----- GET");
		
		BoardVO vo = service.read(bno);
		service.increaseViewCnt(bno);
		
		model.addAttribute("boardVO", vo);
		model.addAttribute("cri", cri);
	}
	
	
	
	//글 삭제 => post 방식으로. 어떻게? 안보이는 form 태그에서 hidden으로 번호 실어서 보냄.
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, Criteria cri) {
		logger.info("remove ----- POST");
		service.remove(bno);
		
		return "redirect:/board/listAll";
	}
	
	//글 삭제한 뒤 페이지 이동하기.
	@RequestMapping(value = "removePage", method = RequestMethod.POST)
	public String removePage(@RequestParam("bno") int bno, Criteria cri) {
		logger.info("removePage ----- POST");
		logger.info("page ----- " + cri.getPage());
		
		return "redirect:/board/listPage?page=" + cri.getPage();
	}
	
	
	
	//글 수정할 때 수정해야 할 부분 받아오기
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public void modify(@RequestParam("bno") int bno, Model model) {
		logger.info("modify ----- GET");
		
		BoardVO vo = service.read(bno);
		
		model.addAttribute("boardVO", vo);
	}
	
	//글 수정하기
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modify(BoardVO vo, @RequestParam("bno") int bno, Model model) {
		logger.info("modify ----- POST");
		logger.info("bno = " + bno);
		service.modify(vo);
		logger.info("vo = " + vo.toString());
		
		return "redirect:/board/listAll";
	}
	
	//글 수정할 때 수정해야 할 부분 받아오기
	@RequestMapping(value = "modifyPage", method = RequestMethod.GET)
	public void modifyPage(@RequestParam("bno") int bno, Criteria cri, Model model) {
		logger.info("modifyPage ----- GET");
		
		BoardVO vo = service.read(bno);
			
		model.addAttribute("boardVO", vo);
		model.addAttribute("cri", cri);
	}
		
	//글 수정하고 나서 페이지 있는 목록으로 돌아가기
	@RequestMapping(value = "modifyPage", method = RequestMethod.POST)
	public String modifyPage(BoardVO vo, @RequestParam("bno") int bno, Criteria cri, Model model) {
		logger.info("modifyPage ----- POST");
		logger.info("bno = " + bno);
		logger.info("page = " + cri.getPage());
		
		service.modify(vo);

		return "redirect:/board/readPage?page=" + cri.getPage() + "&bno=" + vo.getBno();
	}
}
