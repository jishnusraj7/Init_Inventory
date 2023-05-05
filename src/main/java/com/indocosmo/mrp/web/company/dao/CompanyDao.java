package com.indocosmo.mrp.web.company.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;

public class CompanyDao extends GeneralDao<Company> implements ICompanyDao {

	
	public CompanyDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Company getNewModelInstance() {
		
		return new Company();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {
		
		return "company";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT id,code,name,description,database_server,database_name,database_user,database_password,database_port FROM " + getTable()+" WHERE (is_system=0 OR is_system IS NULL)and(business_type=1)";

		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getList()
	 */
	@Override
	public List<Company> getList() throws Exception {
		/*String SQL="SELECT id,code,name,description,is_system,database_server,"
				+ "database_name,database_user,database_password,database_port from "+ getTable() + " "
				+ "WHERE id!= 0 and (is_system=0 OR is_system IS NULL)and(business_type=1)";*/
		String SQL="SELECT id,code,name,description,is_system,database_server,business_type,"
				+ "database_name,database_user,database_password,database_port from "+ getTable() + " ";
			/*	+ "WHERE id!= 0 and (is_system=0 OR is_system IS NULL)";*/

		return buildItemList(SQL);
	}

	public Integer getHqId() throws Exception {
		Integer hqId=0;
		String SQL="SELECT id from "+ getTable() + " WHERE (business_type=1) and (is_system=1)";
		CachedRowSet rs= getRowSet(SQL);
try{
		if(rs!=null){
			while(rs.next()){		
			hqId=rs.getInt("id");
			}
		}
}
catch(Exception e)
{
	e.printStackTrace();
}
		return hqId;
	}
	
	/*public Company getCompany(Integer cmpid) throws Exception{
		
		CachedRowSet resultSet;
		String SQL="SELECT id,code,name,description,database_server,database_name,database_user,database_password,database_port from "+ getTable() + " WHERE id='"+cmpid+"' and (is_system=0 OR is_system IS NULL)";

		resultSet = dbHelper.executeSQLForRowset(SQL);
		
		Company company=new Company();
		if (resultSet.next()) {
		
			company.setId(resultSet.getInt("id"));
			company.setCode(resultSet.getString("code"));
			company.setName(resultSet.getString("name"));
			company.setDescription(resultSet.getString("description"));
			company.setDatabaseServer(resultSet.getString("database_server"));
			company.setDatabaseName(resultSet.getString("database_name"));
			company.setDatabaseUser(resultSet.getString("database_user"));
			company.setDatabasePassword(resultSet.getString("database_password"));
			company.setDatabasePort(resultSet.getInt("database_port"));
			
		}
		
		return company;
	}*/

	
}
