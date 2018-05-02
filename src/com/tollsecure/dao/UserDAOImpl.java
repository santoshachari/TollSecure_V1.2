package com.tollsecure.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tollsecure.entity.User;

@Repository
public class UserDAOImpl implements UserDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean saveUser(User theUser) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		currentSession.saveOrUpdate(theUser);
		
		return true;
	}

	@Override
	public List<User> checkUserPassword(User theUser) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		
		//check whether the first name and last name exist before
		Query<User> theQuery = 
				currentSession.createQuery("from User where userFirstName='"+ theUser.getUserFirstName() +"' and userLastName='"+ theUser.getUserLastName() +"' and userPassword='"+ theUser.getUserPassword() +"' and statusFlag='ACTIVE'", User.class);
		
		//get the result set
		List<User> users = theQuery.getResultList();
		
		return users;

	}

	@Override
	public List<User> getAllUsers(Integer tollPlazaId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//get all users for tollPlaza id
		Query<User> theQuery = 
				currentSession.createQuery("from User where tollPlazaId='"+tollPlazaId+"'  and statusFlag='ACTIVE'", User.class);
		
		List<User> users = theQuery.getResultList();
		
		return users;
	}

	@Override
	public List<User>  getUserFromId(Integer userId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<User> theQuery = 
				currentSession.createQuery("from User where userId='"+userId+"'", User.class);
		
		List<User> theUser = theQuery.getResultList();
		
		return theUser;
	}

	@Override
	public List<User> getAllOperators(Integer tollPlazaId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//get all users for tollPlaza id
		Query<User> theQuery = 
				currentSession.createQuery("from User where tollPlazaId='"+tollPlazaId+"' and userRole='Operator'  and statusFlag='ACTIVE'", User.class);
		
		List<User> users = theQuery.getResultList();
		
		return users;
	}

	@Override
	public List<User> checkUser(User theUser) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
				
		//check whether the first name and last name exist before
		Query<User> theQuery = 
				currentSession.createQuery("from User where userFirstName='"+ theUser.getUserFirstName() +"' and userLastName='"+ theUser.getUserLastName()+"' and statusFlag='ACTIVE'", User.class);
		
		//get the result set
		List<User> users = theQuery.getResultList();
				
		return users;
	}

	@Override
	public List<User> checkUserPasswordForUpdate(User theUser) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		
		//check whether the first name and last name exist before
		Query<User> theQuery = 
				currentSession.createQuery("from User where userFirstName='"+ theUser.getUserFirstName() +"' and userLastName='"+ theUser.getUserLastName() +"' and userPassword='"+ theUser.getUserPassword() +"'", User.class);
		
		//get the result set
		List<User> users = theQuery.getResultList();
		
		return users;
	}

	@Override
	public List<User> getAllExceptOperators(Integer tollPlazaId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//not checking toll plaza id here
		Query<User> theQuery = 
				currentSession.createQuery("from User where (userRole='Admin' or (userRole= 'Supervisor' and tollPlazaId='"+tollPlazaId+"')) and statusFlag='ACTIVE'", User.class);
		
		List<User> users = theQuery.getResultList();
		
		return users;
	}

}
