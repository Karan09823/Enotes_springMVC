package com.Note.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Note.entity.Notes;
import com.Note.entity.User;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {
	
	//data work is done by orm ,here we learn about
	//spring orm therefore we use hibernate template by injecting it into UserDaoImp class
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	
	public int saveUser(User user) {
		int i=(Integer)hibernateTemplate.save(user);
		return i;
	}

	
	public User login(String email, String password) {
		//there is no already made other opration than CRUD
		//customize
		String sql="from User where email=:em and password=:pwd";
		
		User user=(User)hibernateTemplate.execute(s->{
			Query q=s.createQuery(sql);
			q.setString("em",email);
			q.setString("pwd",password);
			return q.uniqueResult();
			
			
		});
		return user;
	}
	
	
	
	///operations related to notes

    @Override
	public int saveNotes(Notes notes) {
		 int i=(Integer)hibernateTemplate.save(notes);
		return i;
	}

    @Override
	public List<Notes> getNotesByUser(User user) {
		String sql="from Notes where user=:us";
		
		List<Notes>list=hibernateTemplate.execute(s->{
			Query q=s.createQuery(sql);
			q.setEntity("us", user);
			return q.getResultList();
			
		});
		
		return list;
	}

    @Override
	public Notes getNotesById(int id) {
		Notes n=hibernateTemplate.get(Notes.class, id);
		
		 
		return n;
	}

    @Override
	public void deleteNote(int id) {
		// first get the object then delete it
		// delete is of void type
		Notes n=hibernateTemplate.get(Notes.class, id);
		hibernateTemplate.delete(n);
		
	}

    @Override
	public void updateNotes(Notes n) {
		//update is of void type
		hibernateTemplate.update(n);
		
	}

}
