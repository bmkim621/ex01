package com.yi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yi.controller.userController;
import com.yi.domain.LoginDTO;
import com.yi.domain.MemberVO;

//로그인 여부만 판단하는 인터셉트
public class LoginInterceptor implements HandlerInterceptor {
	//로그로 확인하기
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	private static final String LOGIN = "login";	//Session에 저장할 때 사용하는 key를 login으로 사용할 예정!

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("==========> Login preHandle");
		
		//return false로 하면 아예 넘어가지 않기 때문에 리턴값 꼭 true로 해주기!
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		logger.info("==========> Login postHandle");
		
		//컨트롤러에서 model에서 실어서 보낸 key인 memberVO 꺼내오기
		LoginDTO dto = (LoginDTO) modelAndView.getModel().get("memberVO");
		
		//회원이 로그인 시도한 경우 => 로그인 한 다음에 list로 이동하기
		if(dto != null) {
			HttpSession session = request.getSession();
			//로그인 시에 dto 전체를 가지고 와야 됨.
			session.setAttribute(LOGIN, dto);
			
			//리스트에 dest에 있는 검색어 정보들을 꺼내온다.
			Object dest = session.getAttribute("dest");
			String path = dest != null ? (String) dest : request.getContextPath() + "/sBoard/list";
			
			response.sendRedirect(path);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("==========> Login afterCompletion");

	}

}
