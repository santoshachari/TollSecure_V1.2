package com.tollsecure.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tollsecure.entity.TollPlaza;

@Repository
public class TollPlazaDAOImpl implements TollPlazaDAO {
	
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<TollPlaza> getAllTollPlazas() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//query to get all the toll plazas
		Query<TollPlaza> theQuery = 
				currentSession.createQuery("from TollPlaza", TollPlaza.class);
		
		//get the list
		List<TollPlaza> theTollPlazas = theQuery.getResultList();
		
		// TODO Auto-generated method stub
		return theTollPlazas;
	}

	@Override
	public List<TollPlaza> getTollPlaza(int theId) {
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//query to get a particular till plaza
		Query<TollPlaza> theQuery = 
				currentSession.createQuery("from TollPlaza where tollPlazaId='"+theId+"'", TollPlaza.class);
		
		//get the element
		List<TollPlaza> theTollPlaza = theQuery.getResultList();

		
		return theTollPlaza;
	}

	@Override
	public void saveTollPlaza(TollPlaza theTollPlaza) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//save or update
		currentSession.saveOrUpdate(theTollPlaza);
		
	}

	@Override
	public void deletePlaza(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		//delete query
		Query theQuery = 
				currentSession.createQuery("delete from TollPlaza where tollPlazaId =:theTollPlazaId");
		theQuery.setParameter("theTollPlazaId", theId);
		
		theQuery.executeUpdate();
	}

	@Override
	public List<TollPlaza> getTollPlazaFromName(String tollPlazaName) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//query to get a particular till plaza
		Query<TollPlaza> theQuery = 
					currentSession.createQuery("from TollPlaza where tollPlazaName='"+tollPlazaName+"'", TollPlaza.class);
		
		List<TollPlaza> theTollPlazas = theQuery.getResultList();
		
		return theTollPlazas;
	}

}
