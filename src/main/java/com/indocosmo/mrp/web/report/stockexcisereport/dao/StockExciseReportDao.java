package com.indocosmo.mrp.web.report.stockexcisereport.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.stockexcisereport.model.StockExciseReport;
import com.sun.rowset.CachedRowSetImpl;

/*
 * 
 *  Done by anandu on 21-01-2020 
 *  
 */

public class StockExciseReportDao extends GeneralDao<StockExciseReport> implements IStockExciseReportDao {

	public StockExciseReportDao(ApplicationContext context) {

		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockExciseReport getNewModelInstance() {

		return new StockExciseReport();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#buildItemList(java.lang.String)
	 *
	 */
	protected List<StockExciseReport> buildItemList(String sql) throws Exception {

		List<StockExciseReport> list = null;
		CachedRowSet rs = getRowSet(sql);

		if (rs != null) {

			list = new ArrayList<StockExciseReport>();

			while (rs.next()) {

				StockExciseReport item = getNewModelInstance();
				DBUtil.setModelFromSRS(rs, item);
				list.add(item);
			}
		}

		return list;
	}

	/**
	 * @param stkregrpt
	 * @return
	 * @throws Exception
	 */
	public List<StockExciseReport> getReportResult(StockExciseReport stkregrpt) throws Exception {

		List<StockExciseReport> stkregrptlist = null;
System.out.println("stkregrpt.getCategory()=============="+stkregrpt.getCategory());
System.out.println("stkregrpt.getSuperClass()============="+stkregrpt.getSuperClass());
System.out.println("stkregrpt.getStartdate()============="+stkregrpt.getStartdate());
System.out.println("stkregrpt.getEnddate()============="+stkregrpt.getEnddate());
		if (stkregrpt.getOption() == 1) {
			Connection con = null;
			CallableStatement st = null;
			CallableStatement stOpening=null;
			CallableStatement stInQty=null;
			CallableStatement stOutQty=null;
			con = this.dbHelper.getConnection();

			
			st = con.prepareCall("{call usp_excise_register_date_report(?,?,?,?)}");
			st.setString(1, stkregrpt.getStartdate());
			st.setString(2, stkregrpt.getEnddate());
		
			st.setInt(3, stkregrpt.getCategory());
			st.setString(4, stkregrpt.getSuperClass());

			ResultSet rs1 = st.executeQuery();
			CachedRowSet rs = executeSQLForStordProc(rs1);

			if (rs != null) {

				stkregrptlist = new ArrayList<StockExciseReport>();

				while (rs.next()) {

					StockExciseReport stockExcisereport = new StockExciseReport();
					stockExcisereport.setStockitemName(rs.getString("stock_item_name"));
					stockExcisereport.setOpening(rs.getString("opening"));				
					stockExcisereport.setInQty(rs.getString("inQty"));					
					stockExcisereport.setOutQty(rs.getString("outqty"));				
					stockExcisereport.setOutqty_unit(rs.getString("outqty_unit"));
					stockExcisereport.setInqty_unit(rs.getString("inqty_unit"));
					stockExcisereport.setOpening_unit(rs.getString("opening_unit"));
					stockExcisereport.setBtlSize(rs.getString("btl_size")!=null?rs.getString("btl_size"):"");
					//stockExcisereport.setUomcode(rs.getString("uomcode")!=null?rs.getString("uomcode"):null);
					stkregrptlist.add(stockExcisereport);
					/*//to get opening unit
					if(stkregrpt.getCategory()!=1) {
					stOpening=con.prepareCall("{call sp_convertUom(?,?,?,?)}");
					
					stOpening.setString(1,rs.getString("uomcode"));
					stOpening.setString(2,"LTR");
					stOpening.setString(3,rs.getString("opening"));
					stOpening.execute();
					stockExcisereport.setOpening_unit(String.valueOf(stOpening.getDouble("param_value")));
					
					
					//to get inqty
					stInQty=con.prepareCall("{call sp_convertUom(?,?,?,?)}");
					stInQty.setString(1,rs.getString("uomcode"));
					stInQty.setString(2,"LTR");
					stInQty.setString(3,rs.getString("inQty"));
					stInQty.execute();
					stockExcisereport.setInqty_unit(String.valueOf(stInQty.getDouble("param_value")));
					
					//to get outQty
					stOutQty=con.prepareCall("{call sp_convertUom(?,?,?,?)}");
					stOutQty.setString(1,rs.getString("uomcode"));
					stOutQty.setString(2,"LTR");
					stOutQty.setString(3,rs.getString("outqty"));
					stOutQty.execute();
					stockExcisereport.setOutqty_unit(String.valueOf(stOutQty.getString("param_value")));
					stkregrptlist.add(stockExcisereport);
					}else {
						stockExcisereport.setOutqty_unit(rs.getString("outqty_unit"));
						stockExcisereport.setInqty_unit(rs.getString("inqty_unit"));
						stockExcisereport.setOpening_unit(rs.getString("opening_unit"));
						stkregrptlist.add(stockExcisereport);
					}*/
					
				}

			}

		} 
		return stkregrptlist;
	}



	/**
	 * @param rSet
	 * @return
	 * @throws Exception
	 */
	public CachedRowSet executeSQLForStordProc(ResultSet rSet) throws Exception {

		CachedRowSet crs = null;

		if (rSet != null) {

			crs = new CachedRowSetImpl();
			crs.populate(rSet);
			rSet.close();
		}

		return crs;
	}

	public String getBtlSize(String uomcode) throws Exception {
		
		System.out.println("uomcode======================>"+uomcode);
		Connection con = null;
		CallableStatement st = null;
		CallableStatement stOpening=null;
		CallableStatement stInQty=null;
		CallableStatement stOutQty=null;
		con = this.dbHelper.getConnection();

		
		st = con.prepareCall("{call sp_getBaseUomValue(?,?,?)}");
		st.setString(1, uomcode);
		st.registerOutParameter(3, Types.VARCHAR);

		 st.executeQuery();
		//CachedRowSet rs = executeSQLForStordProc(rs1);
		 System.out.println("sp_getBaseUomValue======================>"+st.getString(3));
		/*if (rs != null) {

			stkregrptlist = new ArrayList<StockExciseReport>();

			while (rs.next()) {

				StockExciseReport stockExcisereport = new StockExciseReport();
				stockExcisereport.setStockitemName(rs.getString("stock_item_name"));
				stockExcisereport.setOpening(rs.getString("opening"));				
				stockExcisereport.setInQty(rs.getString("inQty"));					
				stockExcisereport.setOutQty(rs.getString("outqty"));				
				stockExcisereport.setOutqty_unit(rs.getString("outqty_unit"));
				stockExcisereport.setInqty_unit(rs.getString("inqty_unit"));
				stockExcisereport.setOpening_unit(rs.getString("opening_unit"));
				stockExcisereport.setBtlSize(rs.getString("btl_size")!=null?rs.getString("btl_size"):"");
				stkregrptlist.add(stockExcisereport);
				
				
			}

		}
*/
	
		// TODO Auto-generated method stub
		return st.getString(3);
	}
}