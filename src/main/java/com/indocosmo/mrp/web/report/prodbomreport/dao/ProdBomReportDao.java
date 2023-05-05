package com.indocosmo.mrp.web.report.prodbomreport.dao;

import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.enumDepartmentType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;

public class ProdBomReportDao extends GeneralDao<ProdBomReportModel> implements IProdBomReportDao {
	
	public ProdBomReportDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ProdBomReportModel getNewModelInstance() {
	
		return new ProdBomReportModel();
	}
	
	@Override
	protected String getTable() {
	
		return "";
	}
	
	@Override
	public JsonArray getProductionMonthData(ProdBomReportModel prod , HttpSession session) throws Exception {
	
/*		String sql = "";
		
		sql="SELECT  "
				+ "	A.stock_item_id, A.stock_item_code, A.stock_item_name,  "
				+ "	IFNULL(C.txn_date, '"+prod.getStartdate()+"') AS txn_date, A.item_category_id, A.uomcode,  "
				+ "	IFNULL(B.cost_price,IFNULL(C.cost_price,0)) AS cost_price, "
				+ "	IFNULL((IFNULL(B.openinqty,0) - IFNULL(B1.openoutqty,0)),0) AS opening, "
				+ "	IFNULL(C.inqty, 0) AS inqty,  "
				+ "	IFNULL(C.outqty, 0) AS bom_qty,   "
				+ "	IFNULL((C.inqty - C.outqty),0) AS diff_qty,  "
				+ "	IFNULL(C.department_id,B.department_id) AS department_id  "
				+ "	FROM  "				
				+ "	(SELECT  "
				+ "			stock_item.id AS stock_item_id,stock_item.`code` AS stock_item_code, stock_item.`name` AS stock_item_name,  "
				+ "			stock_item.item_category_id, uoms.`code` AS uomcode  "
				+ "			FROM  "
				+ "			stock_item INNER JOIN uoms ON stock_item.uom_id = uoms.id  "
				+ "			INNER JOIN item_category ON stock_item.item_category_id = item_category.id "
				+ "			WHERE  "
				+ "			stock_item.is_deleted = 0 AND stock_item.item_category_id = "+enumDepartmentType.FINISHED_GOODS.getenumDepartmentTypeId()+") A  "
				+ "			LEFT JOIN  "
				+ "			(SELECT vs.department_id, vs.stock_item_id, vs.cost_price, "
				+ "			SUM(vs.in_qty) AS openinqty "
				+ "			FROM 	"
				+ "			vw_stock_register vs  "
				+ "			WHERE 	 "
				+ "			vs.txn_date < '"+prod.getStartdate()+"' AND vs.department_id = "+prod.getDepartment_id()+" AND vs.trans_type = "+transactionType.STOCKOUT.gettransactionTypeId()+" "
				+ "			GROUP BY vs.stock_item_id, vs.department_id ) B  "
				+ "			ON A.stock_item_id = B.stock_item_id  "
				+ "			LEFT JOIN "
				+ "			(SELECT vs.department_id, vs.stock_item_id, vs.cost_price, "
				+ "			SUM(vs.out_qty) AS openoutqty "
				+ "			FROM 	 "
				+ "			vw_stock_register vs  "
				+ "			WHERE 	 "
				+ "			vs.txn_date < '"+prod.getStartdate()+"' AND vs.department_id = "+prod.getDepartment_id()+" AND vs.trans_type = "+transactionType.PRODUCTION.gettransactionTypeId()+" "
				+ "			GROUP BY vs.stock_item_id, vs.department_id ) B1 "
				+ "			ON A.stock_item_id = B1.stock_item_id  "
				+ "			LEFT JOIN "
				+ "			(SELECT vs.txn_date, vs.department_id, vs.trans_type, vs.cost_price, vs.stock_item_id, "
				+ "		SUM(vs.in_qty) AS inqty,0 AS outqty "
				+ "		FROM "
				+ "		vw_stock_register vs 	"
				+ "		WHERE "
				+ "		MONTH (vs.txn_date) = "+prod.getMonthNo()+"  AND YEAR (vs.txn_date) = "+prod.getYear()+" AND (vs.department_id = "+prod.getDepartment_id()+") 	 "
				+ "		AND (vs.trans_type = "+transactionType.STOCKOUT.gettransactionTypeId()+")  "
				+ "		AND vs.in_qty > 0 	 "
				+ "		GROUP BY "
				+ "		vs.department_id,vs.stock_item_id,vs.txn_date "
										
				+ "		UNION 	"
										
				+ "		SELECT 		"
				+ "		vs.txn_date,vs.department_id,vs.trans_type,vs.cost_price,vs.stock_item_id,0 AS inqty, "
				+ "		SUM(vs.out_qty) AS outqty  "
				+ "		FROM 	"
				+ "		vw_stock_register vs 	 "
				+ "		WHERE 		 "
				+ "		MONTH (vs.txn_date) = "+prod.getMonthNo()+" AND YEAR (vs.txn_date) = "+prod.getYear()+" "
				+ "		AND (vs.department_id = "+prod.getDepartment_id()+") 	 "
				+ "		AND (vs.trans_type = "+transactionType.PRODUCTION.gettransactionTypeId()+")   "	
				+ "		GROUP BY 		"
				+ "		vs.department_id,vs.stock_item_id,vs.txn_date) C  "
				+ "		ON A.stock_item_id = C.stock_item_id  "
				+ "		WHERE 	"
				+ "		1 = 1 "
				+ "		AND (IFNULL(C.inqty, 0) > 0 	 "
				+ "		OR IFNULL(C.outqty, 0) > 0 )  "
				+ "		ORDER BY A.stock_item_name, C.txn_date;";

	*/	
		String sql="CALL usp_prod_bom_month_report("+prod.getDepartment_id()+", '"+prod.getStartdate()+"' ,"+prod.getMonthNo()+","+prod.getYear()+")";

	
		
		
	return getTableRowsAsJson(sql);
	}
	
	@Override
	public JsonArray getProductionData(ProdBomReportModel prod,HttpSession session) throws Exception {
		
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		
		
		String Sql="";
		
		
		//Sql="SELECT * FROM vw_prod_bom WHERE prod_date BETWEEN '"+startdate+"'  and '"+enddate+"'";	
		/*  Sql="SELECT "
	   		+ "t1.item_id AS stock_item_id, "
			+ " IFNULL(ROUND(t1.bom_qty,"+systemSettings.getDecimal_places()+"),0) AS bom_sum,"
			+ " t1.item_name AS stock_item_name, "
			+ " IFNULL(ROUND(t2.actual_qty,"+systemSettings.getDecimal_places()+"),0) AS out_qty, "
			+ " ABS(ROUND(IFNULL(t2.actual_qty,0)-IFNULL(t1.bom_qty ,0),"+systemSettings.getDecimal_places()+")) AS diff_qty"
			+ " FROM "
			+ "(SELECT  "
			+ "bom.bom_item_id AS item_id,bom.bom_item_name AS item_name,sum(bom.bom_qty) AS bom_qty "
			+ "FROM vw_prod_bom bom "
			+ "WHERE bom.prod_date BETWEEN '"+prod.getStartdate()+"' AND '"+prod.getEnddate()+"' GROUP BY bom.bom_item_id, "
			+ "bom.source_department_id HAVING bom.source_department_id="+prod.getDepartment_id()+""
			+ ") "
			+ "AS t1 "
			+ "LEFT JOIN "
			+ "(SELECT "
			+ "dtl.stock_item_id AS item_id,sum(dtl.in_qty) AS actual_qty FROM stock_register_dtl dtl "
			+ "INNER JOIN stock_register_hdr hdr ON hdr.id=dtl.stock_register_hdr_id "
			+ "WHERE trans_type="+transactionType.STOCKOUT.gettransactionTypeId()+" "
			+ " AND department_id="+prod.getDepartment_id()+" "
			+ "AND hdr.txn_date BETWEEN '"+prod.getStartdate()+"' AND '"+prod.getEnddate()+"' GROUP BY dtl.stock_item_id"
			+ ") "
			+ "AS t2 "
			+ "ON t1.item_id=t2.item_id ORDER BY t1.item_name";*/
		
		/*Sql="SELECT "
				+ "A.stock_item_id, "
				+ "A.stock_item_code, "
				+ "A.stock_item_name, "
				+ "IFNULL(C.txn_date, '"+prod.getStartdate()+"') AS txn_date, "
				+ "A.item_category_id, "
				+ "A.uomcode, "
				+ "IFNULL( "
				+ "	B.cost_price, "
				+ "	IFNULL(C.cost_price, 0) "
				+ ") AS cost_price, "
				+ "IFNULL(B.opening, 0) AS opening, "
				+ "IFNULL(C.inqty, 0) AS inqty, "
				+ "IFNULL(C.outqty, 0) AS bom_qty, "
				+ "IFNULL((C.inqty - C.outqty),0) AS diff_qty, "
				+ "IFNULL( "
				+ "	C.department_id, "
				+ "	B.department_id "
				+ ") AS department_id "
				+ "FROM "
				+ "( "
				+ "SELECT "
				+ "stock_item.id AS stock_item_id,"
				+ "stock_item.`code` AS stock_item_code, "
				+ "stock_item.`name` AS stock_item_name, "
				+ "stock_item.item_category_id, "
				+ "uoms.`code` AS uomcode "
				+ "FROM "
				+ "stock_item "
				+ "INNER JOIN uoms ON stock_item.uom_id = uoms.id "
				+ "INNER JOIN item_category ON stock_item.item_category_id = item_category.id "
				+ "WHERE "
				+ "	stock_item.is_deleted = 0 AND stock_item.item_category_id = "+enumDepartmentType.FINISHED_GOODS.getenumDepartmentTypeId()+" "
				+ ") A "
				+ "LEFT JOIN ( "
				+ "SELECT "
				+ "	vs.department_id, "
				+ "	vs.stock_item_id, "
				+ "	vs.cost_price, "
				+ "	( "
				+ "		SUM(vs.in_qty) - SUM(vs.out_qty) "
				+ "	) opening "
				+ "FROM "
				+ "	vw_stock_register vs "
				+ "WHERE "
				+ "	vs.txn_date < '"+prod.getStartdate()+"' "
				+ "AND (vs.department_id = "+prod.getDepartment_id()+") "
				+ "GROUP BY "
				+ "	vs.stock_item_id, "
				+ "	vs.department_id "
				+ ") B ON A.stock_item_id = B.stock_item_id "
				+ "LEFT JOIN ( "
				+ "	SELECT "
				+ "		vs.txn_date, "
				+ "		vs.department_id, "
				+ "		vs.trans_type, "
				+ "		vs.cost_price, "
				+ "		vs.stock_item_id, "
				+ "		SUM(vs.in_qty) AS inqty, "
				+ "		0 AS outqty		 "
				+ "	FROM "
				+ "		vw_stock_register vs "
				+ "	WHERE "
				+ "		vs.txn_date BETWEEN '"+prod.getStartdate()+"' "
				+ "	AND '"+prod.getEnddate()+"' "
				+ "	AND (vs.department_id = "+prod.getDepartment_id()+") "
				+ "	AND (vs.trans_type = 4) AND vs.in_qty > 0 "
				+ "	GROUP BY "
				+ "	vs.department_id, "
				+ "		vs.stock_item_id, "
				+ "		vs.txn_date "
				+ "UNION "
				+ "	SELECT "
				+ "		vs.txn_date, "
				+ "		vs.department_id, "
				+ "		vs.trans_type, "
				+ "		vs.cost_price, "
				+ "		vs.stock_item_id, "
				+ "		0 AS inqty, "
				+ "		SUM(vs.out_qty) AS outqty	 "	
				+ "	FROM "
				+ "		vw_stock_register vs "
				+ "	WHERE "
				+ "		vs.txn_date BETWEEN '"+prod.getStartdate()+"' "
				+ "	AND '"+prod.getEnddate()+"' "
				+ "	AND (vs.department_id = "+prod.getDepartment_id()+") "
				+ "	AND (vs.trans_type = "+transactionType.PRODUCTION.gettransactionTypeId()+")  "
				+ "	GROUP BY "
				+ "		vs.department_id, "
				+ "		vs.stock_item_id, "
				+ "		vs.txn_date "
				+ ") C ON A.stock_item_id = C.stock_item_id "
				+ "WHERE "
				+ "	1 = 1 "
				+ "AND ( "
				+ "IFNULL(B.opening, 0) > 0 "
				+ "	OR IFNULL(C.inqty, 0) > 0 "
				+ "	OR IFNULL(C.outqty, 0) > 0 "
				+ ") "
				+ "ORDER BY "
				+ "	A.stock_item_name, "
				+ "	C.txn_date;"; 
		 */
		
		/*Sql="SELECT  "
				+ "	A.stock_item_id, A.stock_item_code, A.stock_item_name,  "
				+ "	IFNULL(C.txn_date, '"+prod.getStartdate()+"') AS txn_date, A.item_category_id, A.uomcode,  "
				+ "	IFNULL(B.cost_price,IFNULL(C.cost_price,0)) AS cost_price, "
				+ "	IFNULL((IFNULL(B.openinqty,0) - IFNULL(B1.openoutqty,0)),0) AS opening, "
				+ "	IFNULL(C.inqty, 0) AS inqty,  "
				+ "	IFNULL(C.outqty, 0) AS bom_qty,   "
				+ "	IFNULL((C.inqty - C.outqty),0) AS diff_qty,  "
				+ "	IFNULL(C.department_id,B.department_id) AS department_id  "
				+ "	FROM  "				
				+ "	(SELECT  "
				+ "			stock_item.id AS stock_item_id,stock_item.`code` AS stock_item_code, stock_item.`name` AS stock_item_name,  "
				+ "			stock_item.item_category_id, uoms.`code` AS uomcode  "
				+ "			FROM  "
				+ "			stock_item INNER JOIN uoms ON stock_item.uom_id = uoms.id  "
				+ "			INNER JOIN item_category ON stock_item.item_category_id = item_category.id "
				+ "			WHERE  "
				+ "			stock_item.is_deleted = 0 AND stock_item.item_category_id = "+enumDepartmentType.FINISHED_GOODS.getenumDepartmentTypeId()+") A  "
				+ "			LEFT JOIN  "
				+ "			(SELECT vs.department_id, vs.stock_item_id, vs.cost_price, "
				+ "			SUM(vs.in_qty) AS openinqty "
				+ "			FROM 	"
				+ "			vw_stock_register vs  "
				+ "			WHERE 	 "
				+ "			vs.txn_date < '"+prod.getStartdate()+"' AND vs.department_id = "+prod.getDepartment_id()+" AND vs.trans_type = "+transactionType.STOCKOUT.gettransactionTypeId()+" "
				+ "			GROUP BY vs.stock_item_id, vs.department_id ) B  "
				+ "			ON A.stock_item_id = B.stock_item_id  "
				+ "			LEFT JOIN "
				+ "			(SELECT vs.department_id, vs.stock_item_id, vs.cost_price, "
				+ "			SUM(vs.out_qty) AS openoutqty "
				+ "			FROM 	 "
				+ "			vw_stock_register vs  "
				+ "			WHERE 	 "
				+ "			vs.txn_date < '"+prod.getStartdate()+"' AND vs.department_id = "+prod.getDepartment_id()+" AND vs.trans_type = "+transactionType.PRODUCTION.gettransactionTypeId()+" "
				+ "			GROUP BY vs.stock_item_id, vs.department_id ) B1 "
				+ "			ON A.stock_item_id = B1.stock_item_id  "
				+ "			LEFT JOIN "
				+ "			(SELECT vs.txn_date, vs.department_id, vs.trans_type, vs.cost_price, vs.stock_item_id, "
				+ "		SUM(vs.in_qty) AS inqty,0 AS outqty "
				+ "		FROM "
				+ "		vw_stock_register vs 	"
				+ "		WHERE "
				+ "		vs.txn_date BETWEEN '"+prod.getStartdate()+"' AND '"+prod.getEnddate()+"' AND (vs.department_id = "+prod.getDepartment_id()+") 	 "
				+ "		AND (vs.trans_type = "+transactionType.STOCKOUT.gettransactionTypeId()+")  "
				+ "		AND vs.in_qty > 0 	 "
				+ "		GROUP BY "
				+ "		vs.department_id,vs.stock_item_id,vs.txn_date "
										
				+ "		UNION 	"
										
				+ "		SELECT 		"
				+ "		vs.txn_date,vs.department_id,vs.trans_type,vs.cost_price,vs.stock_item_id,0 AS inqty, "
				+ "		SUM(vs.out_qty) AS outqty  "
				+ "		FROM 	"
				+ "		vw_stock_register vs 	 "
				+ "		WHERE 		 "
				+ "		vs.txn_date BETWEEN '"+prod.getStartdate()+"' AND '"+prod.getEnddate()+"'  "
				+ "		AND (vs.department_id = "+prod.getDepartment_id()+") 	 "
				+ "		AND (vs.trans_type = "+transactionType.PRODUCTION.gettransactionTypeId()+")   "	
				+ "		GROUP BY 		"
				+ "		vs.department_id,vs.stock_item_id,vs.txn_date) C  "
				+ "		ON A.stock_item_id = C.stock_item_id  "
				+ "		WHERE 	"
				+ "		1 = 1 "
				+ "		AND (IFNULL(C.inqty, 0) > 0 	 "
				+ "		OR IFNULL(C.outqty, 0) > 0 )  "
				+ "		ORDER BY A.stock_item_name, C.txn_date;";*/
		
		String sql="CALL usp_prod_bom_date_report("+prod.getDepartment_id()+", '"+prod.getStartdate()+"' ,'"+prod.getEnddate()+"')";

		return getTableRowsAsJson(sql);
		
		
		
	}
	
	
public JsonArray getDepartmentJson() throws Exception{
		
		final String sql="SELECT id,code,name from mrp_department WHERE (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL)";
		
		return getTableRowsAsJson(sql);
	}
}
