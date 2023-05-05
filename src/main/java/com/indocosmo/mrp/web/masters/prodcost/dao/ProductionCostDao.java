package com.indocosmo.mrp.web.masters.prodcost.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.prodcost.model.ProductionCost;

public class ProductionCostDao extends MasterBaseDao<ProductionCost> implements IProductionCostDao {

	public ProductionCostDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public ProductionCost getNewModelInstance() {

		return new ProductionCost();
	}

	
	@Override
	public String getTable() {

		return "mrp_prod_costcalc_params";
	}

	 

	@Override
	public Integer delete(String where) throws Exception {

		return super.delete(where);
	}

	

	@Override
	public JsonArray getMastersRowJson() throws Exception {
		
		final String sql = "SELECT "
				            + "id,code,name,cost_type_name "
				            + "FROM "
				            + "(SELECT id,code,name, "
				            + "(CASE(cost_type) "
				            /*+ "WHEN 0 THEN 'Materials' "*/
				            + "WHEN 1 THEN 'Labour'  "
				            + "WHEN 2 THEN 'Others' "
				            + "ELSE ''  END) AS cost_type_name  "
				            + "FROM "+getTable()+" WHERE (IFNULL(is_deleted,0) = 0))tbl1 ";

		return getTableRowsAsJson(sql);
	}

	
	
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,
			String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		String sql="";
		
		if (wherePart == null || wherePart == "") {

			wherePart = "WHERE (IFNULL(is_deleted,0) = 0)";
		} else {

			wherePart = "WHERE " + "(IFNULL(is_deleted,0) = 0) "
						+ "AND (code LIKE '%" + searchCriteria + "%' "
						+ "OR name LIKE '%" + searchCriteria + "%' "
						+ "OR description LIKE '%" + searchCriteria + "%' "
						+ "OR `name` LIKE '%" + searchCriteria + "%' "
						+ "OR `cost_type_name` LIKE '%" + searchCriteria + "%')";
			}
		if(context.getCurrentHttpSession().getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
		{
				sql = "SELECT "
						+ "id,code,name,description,cost_type,is_deleted,is_system,last_sync_at,cost_type_name,quetableId "
						+ "FROM "
						+ " (SELECT "
						+ " getTab.id AS id,`code`,IFNULL(sync_queue.id, '') AS quetableId,`name`,description,cost_type,"
						+ " is_deleted,is_system,last_sync_at,"
						+ " (CASE(cost_type) "
						/*+ "WHEN 0 THEN 'Materials' "*/
						+ "WHEN 1 THEN 'Labour' "
						+ "WHEN 2 THEN 'Others' "
						+ " ELSE '' "
						+ " END) AS cost_type_name "
						+ "FROM "
						+ getTable()
						+ " "
						+ " getTab "
						+ "LEFT JOIN sync_queue ON (getTab.id = sync_queue.record_id AND sync_queue.table_name = '"
						+ getTable() + "'))" + "TBL " + wherePart + " " + sortPart
						+ " LIMIT " + limitRows + " OFFSET " + offset + "";
				
		}
		else
		{
			sql = "SELECT "
					+ "id,code,name,description,cost_type,is_deleted,is_system,last_sync_at,cost_type_name "
					+ "FROM "
					+ " (SELECT "
					+ " getTab.id AS id,`code`,`name`,description,cost_type,"
					+ " is_deleted,is_system,last_sync_at,"
					+ " (CASE(cost_type) "
					/*+ "WHEN 0 THEN 'Materials' "*/
					+ "WHEN 1 THEN 'Labour' "
					+ "WHEN 2 THEN 'Others' "
					+ " ELSE '' "
					+ " END) AS cost_type_name "
					+ "FROM "
					+ getTable()
					+ " "
					+ " getTab )" + "TBL " + wherePart + " " + sortPart
					+ " LIMIT " + limitRows + " OFFSET " + offset + "";
			
		}
					
							return getTableRowsAsJson(sql);
	}

	

	 
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,
			String searchCriteria, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {
		
		String sqlCount="";
		
		if (wherePart == null || wherePart == "") {

			wherePart = "WHERE (IFNULL(is_deleted,0) = 0)";
		} else {
		
			wherePart = "WHERE "
						+ "(IFNULL(is_deleted,0) = 0) "
						+ "AND (code LIKE '%" + searchCriteria + "%' "
						+ "OR name LIKE '%" + searchCriteria + "%' "
						+ "OR description LIKE '%" + searchCriteria + "%' "
						+ "OR `name` like '%" + searchCriteria + "%' "
						+ "OR `cost_type_name` LIKE '%" + searchCriteria + "%')";
		}
		
		sqlCount = "SELECT "
					+ "COUNT(id) as row_count "
					+ "FROM "
					+ " (SELECT	id,`code`,`name`,description,cost_type,is_deleted,is_system,last_sync_at,"
					+ " (CASE(cost_type) " 
					/*+ "WHEN 0 THEN 'Materials' "*/
					+ "WHEN 1 THEN 'Labour' "
					+ "WHEN 2 THEN 'Others' "
					+ "ELSE '' "
					+ " END) AS cost_type_name " 
					+ "FROM " + getTable() + ")TBL "
					+ wherePart + "";

		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}

	

	 
	public List<ProductionCost> getProdCostData() throws Exception {

		List<ProductionCost> dptList = null;

		final String SQL = "SELECT code,name  FROM  "+getTable()+"  WHERE (IFNULL(is_deleted,0) =0) ";

		CachedRowSet rs = getRowSet(SQL);
		ProductionCost department;
		
		if (rs != null) {
			
			dptList = new ArrayList<ProductionCost>();
		
			while (rs.next()) {
			
				department = getNewModelInstance();
				department.setCode(rs.getString("code"));
				department.setName(rs.getString("name"));
				dptList.add(department);
			}
		}

		return dptList;
	}
	
	public List<ProductionCost> getExcelData() throws Exception {
		List<ProductionCost> costList=null;
	
			String SQL="SELECT id,code,name,description,cost_type,cost_typ_name,is_deleted "
					+ "FROM (SELECT id,code,name,description,cost_type,is_deleted,"
					+ "(CASE(cost_type)"
					/*+ "WHEN 0 THEN 'Materials' "*/
					+ "WHEN 1 THEN 'Labour' "
					+ "WHEN 2 THEN 'Others' "
					+ "ELSE '' END) AS cost_typ_name "
					+ "FROM mrp_prod_costcalc_params where (IFNULL(is_deleted,0) =0))tbl1";
								
		CachedRowSet rs= getRowSet(SQL);
		ProductionCost prod;
		if(rs!=null){
			costList=new ArrayList<ProductionCost>();
			while(rs.next()){		
				prod=getNewModelInstance();
				DBUtil.setModelFromSRS(rs, prod);
				prod.setCost_typ_name(rs.getString("cost_typ_name"));
				prod.setDescription(rs.getString("description"));
				costList.add(prod);
			}
		}	

		return costList;
	}

}
