package com.seoniproject.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seoniproject.entity.Pass;
import com.seoniproject.entity.PassConfiguration;

@Repository
public class PassDAOImpl implements PassDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
		
	@Override
	public PassConfiguration getPassConfiguration(String plazaId, String effectiveTo, String vehicleClass,
			String passType) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		effectiveTo = effectiveTo + " "+"00:00:00";
		
		Query<PassConfiguration> theQuery = 
				currentSession.createQuery("from PassConfiguration where tollPlazaId='"+plazaId+"' and effectiveTo>='"+effectiveTo+"' and vehicleClass='"+vehicleClass+"' and passType='"+passType+"' and statusFlag='ACTIVE'", PassConfiguration.class);
	
		//while inserting make sure that you get one or zero results here
		List<PassConfiguration> passConfiguration = theQuery.getResultList();
		
		if (passConfiguration.size()==0) return null;
		
		return passConfiguration.get(0);
	}

	@Override
	public List<PassConfiguration> getAllActivePassConfigurations(String tollPlazaId, String effectiveTo1,
			String vehicleClass, String passType) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		effectiveTo1 = effectiveTo1 + " "+"00:00:00";
		
		Query<PassConfiguration> theQuery = 
				currentSession.createQuery("from PassConfiguration where tollPlazaId='"+tollPlazaId+"' and effectiveTo>='"+effectiveTo1+"' and vehicleClass='"+vehicleClass+"' and passType='"+passType+"' and statusFlag='ACTIVE'", PassConfiguration.class);
	
		//while inserting make sure that you get one or zero results here
		List<PassConfiguration> passConfiguration = theQuery.getResultList();
		
		return passConfiguration;
	}

	@Override
	public void saveOrUpdate(PassConfiguration passConf) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(passConf);
	}

	@Override
	public Pass getSavedOne(String vehicleNumber) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Pass> theQuery = 
				currentSession.createQuery("from Pass where vehicleNumber='"+vehicleNumber+"' order by passId desc", Pass.class);
		theQuery.setMaxResults(1);
		
		List<Pass> pass = theQuery.getResultList();
		
		if (pass.size()==0) return null;
		
		else return pass.get(0);
	}

	@Override
	public List<Pass> getPasses(String vehicleNumber) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Pass> theQuery = 
				currentSession.createQuery("from Pass where vehicleNumber='"+vehicleNumber+"' and cancellationCode is null", Pass.class);
		
		List<Pass> pass = theQuery.getResultList();
		
	    return pass;
	}

	@Override
	public Pass lastPassForTheVehicle(String vehicleNumber) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		//here there are many cases
		//get the pass for the vehicle number reverse order pick the first one
		Query<Pass> theQuery = 
					currentSession.createQuery("from Pass where vehicleNumber='"+vehicleNumber+"' and cancellationCode is null order by passId desc", Pass.class);
		//we only check the last pass for that vehicle number 
		theQuery.setMaxResults(1);	
		List<Pass> passIfValid = theQuery.getResultList();
		
		// if a pass does not exist
		if (passIfValid.isEmpty()) {
			System.out.println(">> pass does not exist for this vehicle number");
			return null;
		} else {
			return passIfValid.get(0);
		}
	}

}
















