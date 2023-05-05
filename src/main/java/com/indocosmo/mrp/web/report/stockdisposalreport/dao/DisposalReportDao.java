package com.indocosmo.mrp.web.report.stockdisposalreport.dao;

import java.sql.ResultSet;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.report.productionreport.model.ProductionReportModel;
import com.indocosmo.mrp.web.report.stockdisposalreport.model.DisposalReportModel;
import com.sun.rowset.CachedRowSetImpl;


public class DisposalReportDao extends GeneralDao<DisposalReportModel> implements IDisposalReportDao{

	public DisposalReportDao(ApplicationContext context) {

		super(context);
	
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public DisposalReportModel getNewModelInstance() {

		return new DisposalReportModel();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "";
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.productionreport.dao.IProductionReportDao#getProductionItemTotal(java.lang.String, java.lang.String, java.lang.String, javax.servlet.http.HttpSession)
	 */
	@Override
	public JsonArray getProductionItemTotal(String startdate,String enddate,String option,HttpSession session) throws Exception {

		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		String Sql="";		

	
		return getTableRowsAsJson(Sql);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.productionreport.dao.IProductionReportDao#getProductionData(java.lang.String, java.lang.String, java.lang.String, javax.servlet.http.HttpSession)
	 */
	@Override
	public JsonArray getDisposalDateData(String stock_item_id,String companyId,DisposalReportModel disposal,String option,HttpSession session) throws Exception {

		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		String start_date=disposal.getStartdate();
		String end_date=disposal.getEnddate();
		String transQuery="";
		if((!disposal.getTransType().equals("0")) && disposal.getTransType() !="")
		{
		 transQuery=" AND dtl.reason="+disposal.getTransType()+"";
		}
		String sql="SELECT hdr.department_id,dep.name as department_name,dtl.stock_item_id,"
				+ "dtl.stock_item_code,hdr.disposal_date,dtl.stock_item_name,sum(dtl.dispose_qty) as disposeqty,"
				+ "(case(dtl.reason) when 1 then 'DAMAGE' WHEN 2 then 'WASTAGE' "
				+ "WHEN 3 then 'EXPIRY' else '' end )as disposalreason "
				+ "from mrp_stock_disposal_hdr hdr "
				+ "left join mrp_stock_disposal_dtl dtl on dtl.stock_disposal_hdr_id=hdr.id LEFT "
				+ "JOIN mrp_department dep on dep.id=hdr.department_id "
				+ " where hdr.disposal_date between '"+start_date+"' and '"+end_date+"'"
				+ " AND hdr.department_id="+disposal.getDeptId()+" "+transQuery+" "
				+ "group by department_id,stock_item_id,hdr.disposal_date,"
				+ "dtl.reason ORDER BY department_id,disposalreason,stock_item_name";
		return getTableRowsAsJson(sql);
	}

	

	public CachedRowSet executeSQLForStordProc(ResultSet rSet) throws Exception {

		CachedRowSet crs = null;

		if (rSet != null) {

			crs = new CachedRowSetImpl();
			crs.populate(rSet);
			rSet.close();
		}

		return crs;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.stockdisposalreport.dao.IDisposalReportDao#getDisposalMonthData(com.indocosmo.mrp.web.report.stockdisposalreport.model.DisposalReportModel, javax.servlet.http.HttpSession)
	 */
	@Override
	public JsonArray getDisposalMonthData(DisposalReportModel disposal,
			HttpSession session) throws Exception {


		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		String stkItmCon="";
		String deptQury="";
		String reasonQuery="";
		String deptQuery="";
		if((!disposal.getTransType().equals("0")) && disposal.getTransType() !="")
		{
			reasonQuery=" AND dtl.reason="+disposal.getTransType()+"";
		}
		if(!disposal.getDeptId().equals(""))
		{
			deptQuery=" AND hdr.department_id="+disposal.getDeptId()+"";
		}
		
		String sql="SELECT "
				+ "(case(dtl.reason) when 1 then 'DAMAGE' when 2 then 'WASTAGE'"
				+ " when 3 then 'EXPIRY' else '' end )as reason,"
				+ "dtl.stock_item_id,dtl.stock_item_code,dtl.stock_item_name,"
				+ "sum(dtl.dispose_qty) as disposal_qty "
				+ "from mrp_stock_disposal_hdr hdr "
				+ "left join mrp_stock_disposal_dtl dtl on hdr.id=dtl.stock_disposal_hdr_id " 
				+ "left join mrp_department dep on dep.id=hdr.department_id "
				+ "where month(hdr.disposal_date)="+disposal.getMonthNo()+" and "
				+ "year(hdr.disposal_date)="+disposal.getYear()+" "+reasonQuery+" "
				+ " "+deptQuery+" GROUP BY dtl.stock_item_id,reason"
				+ " order by reason,dtl.stock_item_name";
		return getTableRowsAsJson(sql);
	
	}
}
