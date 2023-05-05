package com.indocosmo.mrp.web.report.nonmoving.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.report.nonmoving.model.NonMovingStock;


public class NonMovingStockDao extends GeneralDao<NonMovingStock> implements INonMovingStockDao{

	public NonMovingStockDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public NonMovingStock getNewModelInstance() {

		return new NonMovingStock();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "vw_itemstock";
	}



	/**
	 * @param itemstock
	 * @return
	 * @throws Exception
	 */
	public List<NonMovingStock> getList(NonMovingStock itemstock) throws Exception {

		String sql=	"";
		String itemCatQuery="";
		if(itemstock.getItem_category_id() != 0)
		{
			itemCatQuery="AND (vw_itemstock.item_category_id = "+itemstock.getItem_category_id()+" OR vw_itemstock.parent_id = "+itemstock.getItem_category_id()+")";

		}
		DepartmentService departmentService=new DepartmentService(super.getContext());
		int department_id = departmentService.getIsSystemDepartment().getId();


		sql="SELECT "
				+ "* "
				+ "FROM "
				+ "vw_itemstock "
				+ "WHERE "
				+ "stock_item_id NOT IN (select DISTINCT mrp_stock_register_dtl.stock_item_id "
				+ "FROM "
				+ "mrp_stock_register_hdr "
				+ "INNER JOIN mrp_stock_register_dtl ON mrp_stock_register_hdr.id = mrp_stock_register_dtl.stock_register_hdr_id "
				+ "WHERE "
				+ "mrp_stock_register_hdr.txn_date BETWEEN '"+itemstock.getStartdate()+"' AND '"+itemstock.getEnddate()+"' "
				+ "AND trans_type ="+transactionType.STOCKOUT.gettransactionTypeId()+") "
				+ "AND vw_itemstock.current_stock>0 and vw_itemstock.department_id="+department_id+" "+itemCatQuery+" "
				+ "ORDER BY vw_itemstock.`name`;";

		return buildItemList(sql);
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql="SELECT "
				+ "* "
				+ "FROM "
				+ ""+getTable() ;

		return getTableRowsAsJson(sql);
	}

}
