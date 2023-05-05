package com.indocosmo.mrp.web.masters.users.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.users.dao.UsersDao;
import com.indocosmo.mrp.web.masters.users.model.Users;


public interface IUsersService extends IGeneralService<Users,UsersDao> {
	
	public Users login(Users user) throws Exception;
	
	public JsonArray getEditDetails(int id) throws Exception;
	public JsonArray checkPassword(int id) throws Exception;
    
	public Integer updatepassword(int id,String pass) throws Exception;
	

}
