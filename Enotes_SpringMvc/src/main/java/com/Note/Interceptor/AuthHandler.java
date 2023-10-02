package com.Note.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.Note.entity.User;

public class AuthHandler implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		User user=(User)request.getSession().getAttribute("userObj");
		
		if(user!=null) {
			return true;
		}else {
			response.getWriter().print("<h1>Please login !</h1>");
			return false;
		}
		
		
	}

}
