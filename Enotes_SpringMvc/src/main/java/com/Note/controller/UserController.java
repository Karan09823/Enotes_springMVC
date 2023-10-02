package com.Note.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Note.dao.UserDao;
import com.Note.entity.Notes;
import com.Note.entity.User;

//after logging in after that only user
//can perform all crud operations
//Interceptor concept

@Controller
@RequestMapping("/user/")
public class UserController {
	
	///requirement of userdao
	@Autowired
	public  UserDao userDao;
	
	@RequestMapping("/addNotes")
	public String addNotes() {
		return "add_notes";
	}
	
	
	//controller to view ,therefore use of Model with function
	//.addAttribute(),.modelandview()
	
	@RequestMapping("/viewNotes")
	public String viewNotes(HttpSession session,Model m) {
		//first get the user
		User us=(User)session.getAttribute("userObj");
		
		//getting notes by users
		List<Notes> notes=userDao.getNotesByUser(us);
		
		//now sending data from controller to view i.e notes
		m.addAttribute("list", notes);
		
;		return "view_notes";
	}
	
	//updating the notes
	@RequestMapping(path="/updateNotes",method=RequestMethod.POST)
	public String updateNotes(@ModelAttribute Notes n,HttpSession session) {
		//get the user
		User us=(User)session.getAttribute("userObj");
		//set the notes of a user
		n.setUser(us);
		n.setDate(LocalDateTime.now().toString());
	
		//update the notes
		userDao.updateNotes(n);
		
		//showing message
		session.setAttribute("msg", "Notes Updated sucessfully");
		
		return "redirect:/user/view_notes";
	}
	
	
	//from controller to view
	@RequestMapping("/editNotes")
	public String editNotes(@RequestParam("id")int id,Model m) {
		Notes n=userDao.getNotesById(id);
		m.addAttribute("notes", n);
		
		return "edit_notes";
	}
	
	/// inside user logout option is available
	//logout function
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("userObj");
		
		session.setAttribute("msg", "Logout sucessfully");
		
		
		return "redirect:/login";
	}
	
	//saving the notes
	
	@RequestMapping(path="/saveNotes",method=RequestMethod.POST)
	public String saveNotes(@ModelAttribute Notes n,HttpSession session) {
		
		User us=(User)session.getAttribute("userObj");
		n.setUser(us);
		n.setDate(LocalDateTime.now().toString());
		
		userDao.saveNotes(n);
		session.setAttribute("msg", "Notes added sucessfully");
		
		return "redirect:/user/add_notes";
		
	}
	
	///deleting the notes
	
	@RequestMapping("/deleteNotes")
	public String deleteNotes(@RequestParam("id") int id,HttpSession session) {
		
		userDao.deleteNote(id);
		session.setAttribute("msg", "Notes Deleted sucesfully");
		return "redirect:/user/view_notes";
	}
	

}
