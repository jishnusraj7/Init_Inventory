package com.indocosmo.mrp.web.masters.users.dao;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.PosPasswordUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.systemsettings.dao.SystemSettingsDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;


/**
 * @author jo
 *
 */
/**
 * @author jo
 *
 */
/**
 * @author jo
 *
 */
public class UsersDao extends GeneralDao<Users> implements IUsersDao {

	private SystemSettingsDao systemSettingsDao;


	public UsersDao(ApplicationContext context) {
		super(context);
		systemSettingsDao=new SystemSettingsDao(getContext());

	}





	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Users getNewModelInstance() {

		return new Users();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "users";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.users.dao.IUsersDao#login(com.indocosmo.mrp.web.masters.users.model.Users)
	 */
	public Users  login(Users user) throws Exception {

		CachedRowSet resultSet;
		CachedRowSet err_resultSet;


		try {
			if(user.getPassword()!=null && user.getName()!=null)
			{




				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date dateobj = new Date();
				String todayDate = df.format(dateobj);  

				String sql="select * from users where name = '"+ user.getName() +"' and password like '%"+PosPasswordUtil.encrypt(user.getPassword())+"%'"
						+ " and is_active='1' and is_deleted='0'  AND (IFNULL(valid_to,'"+todayDate+"') >= '"+todayDate+"' ) ";		

				resultSet = dbHelper.executeSQLForRowset(sql);
				if (resultSet.next()) {
					// System.out.println(resultSet.getInt("id"));

					user.setId(resultSet.getInt("id"));
					user.setCode(resultSet.getString("code"));
					user.setName(resultSet.getString("name"));
					user.setLastLoginDate(resultSet.getString("lastlogin_date"));
					user.setPassword(resultSet.getString("password"));
					user.setUserGroupId(resultSet.getInt("user_group_id"));
					user.setIsAdmin(resultSet.getInt("is_admin"));

					SystemSettings systemSettings = systemSettingsDao.getSystemData();
					if(systemSettings.getId()!=null)
					{
					String foramtedLoginDate = getCurrentDateTimeFormat(resultSet.getString("lastlogin_date"),systemSettings);
					user.setLoginDate(foramtedLoginDate);
					}
					else
					{
						user.setLoginDate(resultSet.getString("lastlogin_date"));	
					}
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					String lastLgDate = dateFormat.format(date);
					String update_date_sql="update users set lastlogin_date='"+lastLgDate+"' where id= '"+ user.getId() +"' and is_active='1' and is_deleted='0' ";
					dbHelper.executeSQL(update_date_sql);
					//user.setDepartmentId(resultSet.getString("department_id"));

				}  

				else
				{

					String err_sql="select * from users where name = '"+ user.getName() +"' and is_active='1' and is_deleted='0'  AND (IFNULL(valid_to,'"+todayDate+"') >= '"+todayDate+"' ) ";	
					err_resultSet=dbHelper.executeSQLForRowset(err_sql);
					if (err_resultSet.next()) {
						user.setErr_msg(1);
						/*user.setErrUname(user.getName());*/
					} else {
						user.setErr_msg(2);


					}

				}

			}

			else if ((user.getPassword() == "") && (user.getName() != "")) {
				user.setErr_msg(3);

			} else if ((user.getName() == "") && (user.getPassword() != "")) {
				user.setErr_msg(4);

			} else if ((user.getName() == "") && (user.getPassword() == "")) {
				user.setErr_msg(5);
			}

		}  

		catch (Exception e)  {
			e.printStackTrace();
		}

		return user;
	}

	/**
	 * @param date
	 * @param settings
	 * @return
	 */
	public String getCurrentDateTimeFormat(String date,SystemSettings settings) {

		final String dateSeparator = settings.getDate_separator();
		final String dateFormat = settings.getDate_format();
		final String timeFormat = settings.getTime_format();
		String dateF= date.split("\\s")[0];
		String timeF = date.split("\\s")[1].split("\\.")[0];
		String splitDateF[]=dateF.split("-");
		String year=splitDateF[0];
		String month=splitDateF[1];
		String day=splitDateF[2];
		String convertedFormat="";
		String convertedTimeFormat="";
		String amPm="";
		String SplitTime[]=timeF.split(":");
		String hr=SplitTime[0];
		Integer hour=Integer.parseInt(hr);
		if(hour>12 && hour<24)
		{
			hour=hour-12;
			amPm="PM";

		}
		else if(hour==12)
		{
			hour=hour;
			amPm="PM";
		}
		else if(hour<12)
		{
			amPm="AM";
		}
		else if(hour==24)
		{
			hour=hour-12;
			amPm="AM";
		}
		String min=SplitTime[1];
		String sec=SplitTime[2];
		if(timeFormat.matches("0"))
		{
			convertedTimeFormat=hour+":"+min+":"+sec+" "+amPm;
		}

		else if(timeFormat.matches("1"))
		{
			convertedTimeFormat=hr+":"+min+":"+sec;

		}
		else if(timeFormat.matches("2"))
		{
			convertedTimeFormat=hour+"."+min+" "+amPm;

		}
		else if(timeFormat.matches("3"))
		{
			convertedTimeFormat=hour+"h"+min+" "+amPm;

		}

		if(dateFormat.matches("0"))
		{
			convertedFormat=day+dateSeparator+month+dateSeparator+year+" "+convertedTimeFormat;

		}
		else if(dateFormat.matches("1"))
		{
			convertedFormat=month+dateSeparator+day+dateSeparator+year+" "+convertedTimeFormat;
		}
		else if(dateFormat.matches("2"))
		{
			convertedFormat=year+dateSeparator+month+dateSeparator+day+" "+convertedTimeFormat;
		}
		return convertedFormat;

	}




	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#isCodeExist(java.lang.String)
	 */
	@Override
	public Integer isCodeExist(String code) throws Exception {
		final String sql= "SELECT COUNT(code) AS  row_count FROM "+getTable()+" WHERE code='"+code+"'  AND (is_deleted=0 OR is_deleted IS NULL) LIMIT 1";

		return excuteSQLForRowCount(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(TBL.is_deleted, 0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(TBL.is_deleted, 0) = 0) and (TBL.code like '%"+searchCriteria+"%' or TBL.name like '%"+searchCriteria+"%' or TBL.usergroup_name like '%"+searchCriteria+"%' or TBL.card_no  like '%"+searchCriteria+"%' or TBL.isActive like '"+searchCriteria+"%')";
		}
		//String sql = "SELECT usr.id, usr.card_no,usr.`code`,usr.`password`,usr.name,usr.email,usr.`user_group_id`,usr.`is_active`,usrgrp.`name` AS usergroup_name FROM "+getTable()+" usr LEFT JOIN user_groups usrgrp ON usr.user_group_id = usrgrp.id  "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		String sql ="SELECT TBL.id,TBL.card_no,TBL.`code`,TBL.`password`,"
				+ "TBL. name,TBL.email,TBL.`user_group_id`,TBL.`isActive`,TBL.`usergroup_name`,TBL.`is_deleted`,TBL.`quetableId`"
				+ " FROM(SELECT usr.id as id,usr.card_no,usr.`code`,usr.`password`,usr. NAME,usr.email,usr.is_deleted,usr.`user_group_id`,"
				+ "usrgrp.`name` AS usergroup_name,IFNULL(sync_queue.id, '') AS quetableId,(CASE (usr.is_active)WHEN 0 THEN 'inactive' WHEN 1 THEN 'active' ELSE '' END) AS isActive"
				+ " FROM users usr LEFT JOIN sync_queue ON (usr.id = sync_queue.record_id AND sync_queue.table_name = 'users') LEFT JOIN user_groups usrgrp ON usr.user_group_id = usrgrp.id WHERE "
				+ "IFNULL(usrgrp.is_deleted, 0) = 0 && IFNULL(usr.is_deleted, 0) = 0) TBL "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		return getTableRowsAsJson(sql);
	}

	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(TBL.is_deleted, 0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(TBL.is_deleted, 0) = 0) and (TBL.code like '%"+searchCriteria+"%' or TBL.name like '%"+searchCriteria+"%' or TBL.usergroup_name like '%"+searchCriteria+"%' or TBL.card_no  like '%"+searchCriteria+"%' or TBL.isActive like '"+searchCriteria+"%')";
		}
		//String sqlCount="SELECT count(usr.id) as row_count from "+getTable()+" usr LEFT JOIN user_groups usrgrp ON usr.user_group_id = usrgrp.id "+wherePart+"";
		String sqlCount ="SELECT  count(TBL.id) as row_count FROM(SELECT usr.id,usr.card_no,usr.`code`,usr.`password`,usr. NAME,usr.email,usr.is_deleted,usr.`user_group_id`,"
				+ "usrgrp.`name` AS usergroup_name,(CASE (usr.is_active) WHEN 0 THEN 'inactive' WHEN 1 THEN 'active' ELSE '' END) AS isActive"
				+ " FROM users usr LEFT JOIN user_groups usrgrp ON usr.user_group_id = usrgrp.id WHERE "
				+ "IFNULL(usrgrp.is_deleted, 0) = 0 && IFNULL(usr.is_deleted, 0) = 0) TBL "+wherePart+"";


		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}




	public JsonArray getEditDetails(int id) throws Exception
	{
		final String sql="SELECT * from "+getTable()+" WHERE id="+id+"  and is_deleted='0' ";

		return getTableRowsAsJson(sql);
	}

	public JsonArray checkPassword(int id) throws Exception
	{
		final String sql="SELECT * from "+getTable()+" WHERE id="+id+"  and is_deleted='0' ";

		return getTableRowsAsJson(sql);
	}

	public Integer updatepassword(int id,String pass) throws Exception
	{
		final String sql="update users set password='"+pass+"' where id="+id+"";
		Integer status=0;
		beginTrans();
		try{

			status = executeSQL(sql);
			endTrans();
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}

		return status;
	}





	public void updateToken(String tokenId , String exprtime, Integer id) throws Exception{
	
		final String sql="update users set token_id='"+tokenId+"' , token_expire_time='"+exprtime+"'  where id="+id+"";
		executeSQL(sql);
		
			}



	

}
