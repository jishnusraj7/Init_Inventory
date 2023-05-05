package com.indocosmo.mrp.web.masters.users.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.users.model.Users;


public interface IUsersDao extends IGeneralDao<Users>{

	public Users login(Users user) throws Exception;

	public JsonArray getEditDetails(int id) throws Exception;
	public JsonArray checkPassword(int id) throws Exception;

	public Integer updatepassword(int id,String pass) throws Exception;
	
	
}
