package com.indocosmo.mrp.web.masters.users.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.users.dao.UsersDao;
import com.indocosmo.mrp.web.masters.users.model.Users;


public class UsersService extends GeneralService<Users,UsersDao> implements IUsersService{
	private UsersDao userDao;

	public UsersService(ApplicationContext context) {
		super(context);
		userDao=new UsersDao(getContext());
	}


	@Override
	public UsersDao getDao() {
		// TODO Auto-generated method stub
		return userDao;
	}
	
	

	@Override
	public Integer delete(String where) throws Exception {
		where = "id="+where;
		return super.delete(where);
	}
	
	public Users login(Users user) throws Exception {
		// TODO Auto-generated method stub
		return userDao.login(user);
	}

	
	
	public JsonArray getEditDetails(int id) throws Exception{
		return userDao.getEditDetails(id);
		
	}

	public JsonArray checkPassword(int id) throws Exception{
		return userDao.checkPassword(id);
		
	}
	
	
	public Integer updatepassword(int id,String pass) throws Exception{
		return userDao.updatepassword(id,pass);
	}


	public void updateToken(String tokenId , String exprtime, Integer id) throws Exception{
		
		 userDao.updateToken( tokenId ,  exprtime,id);
	
		
	}


	
	
	




	

}
