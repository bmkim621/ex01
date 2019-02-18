package com.yi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yi.domain.LoginDTO;

public class AuthInterceptor implements HandlerInterceptor {
	//로그로 확인하기
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		//키 ==> login
		LoginDTO dto = (LoginDTO) session.getAttribute("login");
		
		//로그인 키가 있다 => null 아님, 로그인 키가 없다 => null
		if(dto != null) {
			logger.info("session : " + dto);
		}
		if(dto == null) {	//로그인 하지 않은 상태 -> 로그인 화면으로 이동시킨다.
			saveDest(request);
			response.sendRedirect(request.getContextPath() + "/user/login");
			
			return false;	//register(remove, modify 등등..) controller로 가는 것을 막는다.
		}
		
		//return false로 하면 아예 넘어가지 않기 때문에 리턴값 꼭 true로 해주기!
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}
	
	//login 화면으로 이동 전에 원래 가고자 하는 path를 저장해뒀다가 로그인이 되면 그 곳으로 이동되도록 한다.
	//검색어(목적지 정보) 기억하게 하는 함수(여기에서만 사용할 것임)
	private void saveDest(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		
		if(query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		
		//get인 경우에만 이 메서드를 사용한다.
		if(request.getMethod().equalsIgnoreCase("get")) {
			logger.info("dest : " + (uri + query));
			
			request.getSession().setAttribute("dest", uri + query);
		}
	}

}
