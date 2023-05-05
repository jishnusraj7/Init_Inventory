package com.indocosmo.mrp.web.report.tallyexport.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.report.tallyexport.model.TallyExport;
/*
 * 
 * @gana
 */
public class TallyExportDaoImp extends GeneralDao<TallyExport> implements ITallyExportDao{

	private TallyExport tallyExport;
	
	public TallyExportDaoImp(ApplicationContext context) {
		super(context);
	}

	@Override
	public TallyExport getNewModelInstance() {
		return new TallyExport();
	}

	@Override
	protected String getTable() {
		return "mrp_stock_in_hdr";
	}

	public List<TallyExport> getTallyData(TallyExport tallyExport) {
		List<TallyExport> tallyExportList=null;
		String selectSql=null;
		String tallySql=null;
		String paymentSql=null;
		TallyExport tallyExportModel=new TallyExport();
		
		 selectSql="select * from "+getTable()+" WHERE "+getTable()+".received_date  BETWEEN '"+tallyExport.getStartDate()+"' AND '"+tallyExport.getEndDate()+"'";
		if(tallyExport.getOption().equals("ALL")) {			
			tallySql=selectSql;
		}else {
			paymentSql=" AND "+getTable()+".payment_mode="+tallyExport.getOption()+"";
			tallySql=selectSql+paymentSql;
		}
		try {
			CachedRowSet rowSet=getRowSet(tallySql);
			if(rowSet!=null) {		
				tallyExportList=new ArrayList<TallyExport>();
				while(rowSet.next()) {
					
					tallyExportModel=getNewModelInstance();
					DBUtil.setModelFromSRS(rowSet, tallyExportModel);
					tallyExportModel.setOption(tallyExport.getOption());
					tallyExportList.add(tallyExportModel);
					
				}
			}
		} catch (Exception e) {
			throw new CustomException();
		}
		return tallyExportList;
	}

}
