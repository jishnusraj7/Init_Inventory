package com.indocosmo.mrp.web.report.departmentwisereport.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.departmentwisereport.model.DepartmentWiseReport;
import com.sun.rowset.CachedRowSetImpl;
/*
 * 
 * @gana 110320
 */
public class DepartmentWiseReportDaoImp extends GeneralDao<DepartmentWiseReport> implements DepartmentWiseReportDao{

	public DepartmentWiseReportDaoImp(ApplicationContext context) {
		super(context);
	}

	@Override
	public DepartmentWiseReport getNewModelInstance() {
		return new DepartmentWiseReport();
	}

	@Override
	protected String getTable() {
		return "";
	}

	/**gana**/
	public List<DepartmentWiseReport> getDepartmentWiseReport(DepartmentWiseReport departmentWiseReport) {
	
		Connection con = null;
		CallableStatement callableStatement = null;
		List<DepartmentWiseReport> departmentWiseReportList=null;
		try {
			con = this.dbHelper.getConnection();
			callableStatement = con.prepareCall("{call usp_department_date_report(?,?,?,?)}");
			callableStatement.setString(1,departmentWiseReport.getStockItemId());
			callableStatement.setInt(2,departmentWiseReport.getItemCategoryId());
			callableStatement.setInt(3, departmentWiseReport.getDepartmentId());
			callableStatement.setString(4,departmentWiseReport.getStockDate());
			
			ResultSet resultSet=callableStatement.executeQuery();
			CachedRowSet cachedRowSet = executeSQLForStordProc(resultSet);
			if(cachedRowSet!=null) {
				departmentWiseReportList=new ArrayList<DepartmentWiseReport>();
				while(cachedRowSet.next()) {
					
					DepartmentWiseReport departmentReportData=new DepartmentWiseReport();
					departmentReportData.setStockItemId(cachedRowSet.getString("stock_item_id"));
					departmentReportData.setStockItemName(cachedRowSet.getString("stock_item_name"));
					departmentReportData.setOpeningStock(cachedRowSet.getDouble("opening"));
					departmentReportData.setStockIn(cachedRowSet.getDouble("inqty"));
					departmentReportData.setStockOut(cachedRowSet.getDouble("outqty"));
					//adding to list
					departmentWiseReportList.add(departmentReportData);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departmentWiseReportList;
	}
	
	/**
	 * @param rSet
	 * @return
	 * @throws Exception
	 */
	protected CachedRowSet executeSQLForStordProc(ResultSet rSet) throws Exception {

		CachedRowSet crs = null;

		if (rSet != null) {

			crs = new CachedRowSetImpl();
			crs.populate(rSet);
			rSet.close();
		}

		return crs;
	}
}
