package com.tollsecure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tollsecure.dao.UserDAO;
import com.tollsecure.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional
	public boolean saveUser(User theUser) {
		return userDAO.saveUser(theUser);
	}

	@Override
	@Transactional
	public List<User> checkUserPassword(User theUser) {
		// TODO Auto-generated method stub
		return userDAO.checkUserPassword(theUser);
	}

	@Override
	@Transactional
	public List<User> getAllUsers(Integer tollPlazaId) {
		// TODO Auto-generated method stub
		return userDAO.getAllUsers(tollPlazaId);
	}

	@Override
	@Transactional
	public List<User>  getUserFromId(Integer userId) {
		// TODO Auto-generated method stub
		return userDAO.getUserFromId(userId);
	}

	@Override
	@Transactional
	public List<User> getAllOperators(Integer tollPlazaId) {
		// TODO Auto-generated method stub
		return userDAO.getAllOperators(tollPlazaId);
	}

	@Override
	@Transactional
	public List<User> checkUser(User theUser) {
		// TODO Auto-generated method stub
		return userDAO.checkUser(theUser);
	}

	@Override
	@Transactional
	public List<User> checkUserPasswordForUpdate(User theUser) {
		// TODO Auto-generated method stub
		return userDAO.checkUserPasswordForUpdate(theUser);
	}

	@Override
	@Transactional
	public List<User> getAllExceptOperators(Integer tollPlazaId) {
		// TODO Auto-generated method stub
		return userDAO.getAllExceptOperators(tollPlazaId);
	}

}
