package com.tollsecure.dao;

import java.util.List;

import com.tollsecure.entity.User;


public interface UserDAO {
	public boolean saveUser(User theUser);

	public List<User> checkUserPassword(User theUser);

	public List<User> getAllUsers(Integer tollPlazaId);

	public List<User>  getUserFromId(Integer userId);

	public List<User> getAllOperators(Integer tollPlazaId);

	public List<User> checkUser(User theUser);

	public List<User> checkUserPasswordForUpdate(User theUser);

	public List<User> getAllExceptOperators(Integer tollPlazaId);

}
