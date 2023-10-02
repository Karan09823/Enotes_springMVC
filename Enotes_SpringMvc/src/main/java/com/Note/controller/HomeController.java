package com.Note.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Note.dao.UserDao;
import com.Note.entity.User;

@Controller
public class HomeController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/home")
	public String home() {
		
		return "home";
		
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	

	//after registering,view to controller(POST mapping)
	//use of @ModelAttrinute
	//for showing using httpsession as it store information for a particular time
	@RequestMapping(path="/registerUser",method=RequestMethod.POST)
	public String registerUser(@ModelAttribute User user,HttpSession session) {
		//saving using userDao
		userDao.saveUser(user);//important
		
		session.setAttribute("msg", "Registered Sucessfully ");
		return"redirect:/register";
	}
	
	//use of @RequestParam as we need only email and password to verify
	@RequestMapping(path="/loginUser" ,method=RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email,@RequestParam("password")String password,HttpSession session) {
		
		//login using userdao
		User user=userDao.login(email, password);
		
		if(user!=null) {
			session.setAttribute("userObj", user);
			return "home";//************
		}else {
			session.setAttribute("msg", "Invalid mail or password !" );
			return "redirect:/login";
			
		}
		
	}

}
