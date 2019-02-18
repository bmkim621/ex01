package com.yi.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.LoginDTO;
import com.yi.domain.MemberVO;
import com.yi.service.MemberService;

@Controller
@RequestMapping("/user/")  
public class userController {
	//로그로 확인하기
	private static final Logger logger = LoggerFactory.getLogger(userController.class);
	
	//서비스 주입받기
	@Autowired
	private MemberService service;
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public void loginGet() {
		logger.info("==========> Login GET ");
	}
	
	//interceptor 사용 시 get인지 post인지 모르니까 이름 뒤에 get, post 붙여서 구분되도록 한다.
	@RequestMapping(value = "loginPost", method = RequestMethod.POST)
	public void loginPost(String userid, String userpw, Model model) {
		logger.info("==========> Login POST ");
		
		MemberVO vo = service.read(userid, userpw);
		
		//회원이 없는 경우 -> 로그인 화면으로 이동한다.
		if(vo == null) {
			logger.info("loginPOST Return ....... ");
			
			return;
		}
		
		//이름, 아이디
		LoginDTO dto = new LoginDTO();
		dto.setUserid(vo.getUserid());
		dto.setUsername(vo.getUsername());
		
		//회원이 있는 경우 -> 실어서 보낸다.
		model.addAttribute("memberVO", dto);	
	}
	
	
	// ===== 로그아웃 =====
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logOutGET(HttpSession session) {
		logger.info("==========> Logout GET ");
		//로그아웃 => 세션 날리기
		session.invalidate();
		
		//로그아웃 후 홈화면으로 이동하기
		return "redirect:/sBoard/list";
	}
}
