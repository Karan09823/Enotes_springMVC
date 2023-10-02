package com.Note.dao;

import java.util.List;

import com.Note.entity.Notes;
import com.Note.entity.User;

public interface UserDao {
	
	//saveuser with return type int
	public int saveUser(User user);
	
	//login gives the User,which is identified by email,password
	public User login(String email,String password);
	
	/////operations related to notes
	
	
	//saving the notes of a user
	public int saveNotes(Notes notes);
	
	//list of notes of show to the user
	public List<Notes> getNotesByUser(User user);
	
	//getting notes by id
	public Notes getNotesById(int id);
	
	//deleting the notes
	public void deleteNote(int id);
	
	//updating the notes
	public void updateNotes(Notes n);

}
