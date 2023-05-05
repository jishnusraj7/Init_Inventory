package com.indocosmo.mrp.web.production.production.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.production.production.dao.ProductionDao;
import com.indocosmo.mrp.web.production.production.model.Production;
import com.indocosmo.mrp.web.production.production.productiondetail.model.ProductionDetail;

public class ProductionService extends GeneralService<Production,ProductionDao> implements IProductionService{

	private ProductionDao productionDao;
public ProductionService(ApplicationContext context) {
		super(context);
		productionDao=new ProductionDao(getContext());
	}



	@Override
	public ProductionDao getDao() {
		return productionDao;
	}
	
	
	
	@Override
	public JsonArray getStockDispDtlData(Production stockDisp) throws Exception {
		
		return productionDao.getStockDispDtlData(stockDisp);
	}




	/**
	 * @param prodDate
	 * @return
	 */
	public JsonArray getProductionOrderData(String prodDate,String orderIds,HttpSession session)throws Exception {
		// TODO Auto-generated method stub
		return productionDao.getProductionOrderData(prodDate,orderIds,session);
	}
	
	public Production saveProdItem(Production stockIn) throws Exception {
		productionDao.save(stockIn);
		
		return stockIn;
	}

	public JsonArray getOnProductionIds(String prodDate)throws Exception {
		// TODO Auto-generated method stub
		return productionDao.getOnProductionIds(prodDate);
	}

	/**
	 * @param stockDisp
	 * @return
	 */
	public JsonArray getProductionDtlData(Production stockDisp) throws Exception {
		// TODO Auto-generated method stub
		return productionDao.getProductionDtlData(stockDisp);
	}




	/**
	 * @param id
	 * @param orderIdsString
	 */
	public void updateOrderHdr(Integer prod_id, String orderIdsString) throws Exception{
		// TODO Auto-generated method stub
		productionDao.updateOrderHdr(prod_id,orderIdsString);
	}




	/**
	 * @param id
	 * @return
	 */
	public JsonArray getOrderHdrIdsByProdId(Integer prodId) throws Exception{
		// TODO Auto-generated method stub
		return 	productionDao.getOrderHdrIdsByProdId(prodId);
	}
	
	public JsonArray getBomQty(int itemId) throws Exception{
		// TODO Auto-generated method stub
		return productionDao.getBomQty(itemId);
	}
	
	@Override
	public Integer delete(String  where) throws Exception {
          where = "id="+where;
		
		return super.delete(where);

		}

	/**
	 * @param stockId
	 * @param bomQty
	 * @return
	 */
	public Integer updateBomQty(Integer stockId, Double bomQty,boolean isLiteVersion) {
		// TODO Auto-generated method stub
		return productionDao.updateBomQty(stockId,bomQty,isLiteVersion);
	}
	@Override
	public Integer updateStatus(Integer id) throws Exception {
		return productionDao.updateStatus(id);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.dailyproduction.service.IDailyPlanningService#updateProDBomItems(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public int updateProDBomItems(Integer dtl_id, Integer roundVal,
			Integer source_dep, Integer dest_dep, Integer stock_hdr_id)
			throws Exception {
		// TODO Auto-generated method stub
		return productionDao.updateProDBomItems(dtl_id, roundVal, source_dep, dest_dep, stock_hdr_id);
	}



	/**
	 * @param parseInt
	 */
	public void upDateProductionOrderData(int parseInt) throws Exception{
		// TODO Auto-generated method stub
	    productionDao.upDateProductionOrderData(parseInt);
	}



	public JsonArray getOnProductionIdsShopwise(String currentDate , String prod_upto, String custId) throws Exception {

	
		// TODO Auto-generated method stub
		return productionDao.getOnProductionIdsShopwise(currentDate,prod_upto,custId);
	}



	public JsonArray getProductionOrderDataShopwise(String currentDate , String proDate , String custId, Integer request_type, HttpSession session) throws Exception {
	
		// TODO Auto-generated method stub
		return productionDao.getProductionOrderDataShopwise(currentDate,proDate,custId,request_type,session);
	}



	public JsonArray getPending(String currentDate , String custId) throws Exception {
	
		// TODO Auto-generated method stub
		return productionDao.getPending(currentDate,custId);
	}



	public JsonArray getPendingStockItem(String currentDate) throws Exception{
	
		// TODO Auto-generated method stub
		return productionDao.getPendingStockItem(currentDate);
	}



	public JsonArray getVerifiedTotalItems(String delevery_date , String custId, String deptId, String checkedItemArray,
			String timeslotArray) throws Exception{
	
		// TODO Auto-generated method stub
		return productionDao.getVerifiedTotalItems(delevery_date,custId,deptId,checkedItemArray,timeslotArray);
	}
	
	public JsonArray getVerifiedTotalItems1(String delevery_date , String custId, String deptId, String checkedItemArray)
			throws Exception{
		
		// TODO Auto-generated method stub
		return productionDao.getVerifiedTotalItems1(delevery_date,custId,deptId,checkedItemArray);
	}
	

	public JsonArray getShopOrderProcess(String closing_date , String customer_id , String stock_item_id ,
			String time_slot) throws Exception{
	
		// TODO Auto-generated method stub
		return productionDao.getShopOrderProcess(closing_date,customer_id,stock_item_id,time_slot);
		
		
	}



	public JsonArray getProductionList(String stock_item_id, String time_slot_id , String department_id, String production_date) throws Exception{
	
		// TODO Auto-generated method stub
		return productionDao.getProductionList(stock_item_id, time_slot_id , department_id, production_date);
	}

	/*public JsonArray getProductionList(String stock_item_id , String time_slot_id , String department_id, String stock_item_array) throws Exception{
		
		// TODO Auto-generated method stub
		return productionDao.getProductionList(stock_item_id ,time_slot_id ,department_id,stock_item_array);
	}*/

	public void updateOrderDtl(ArrayList<ProductionDetail> productionItemsList, Integer timeslot_id, String prod_date) throws Exception{
	
		// TODO Auto-generated method stub
		 productionDao.updateOrderDtl(productionItemsList,timeslot_id,prod_date);
		
	}



	public JsonArray getOrderDetailsProd(String production_date , String time_slot_id , String stock_item_id) throws Exception{
	
		// TODO Auto-generated method stub
		return productionDao.getOrderDetailsProd(production_date,time_slot_id,stock_item_id);
	}



	public JsonArray getOrderDetailsDailyprd(String dtl_id) throws Exception{
	
		// TODO Auto-generated method stub
		return productionDao.getOrderDetailsDailyprd(dtl_id);
	}



	public JsonArray getReportData(String production_date , String dept_id , String time_slot_id) throws Exception{
	
		// TODO Auto-generated method stub
		return productionDao.getReportData(production_date,dept_id,time_slot_id);
	}

	public JsonArray getmaterialReportData(String req_no) throws Exception{
		
		// TODO Auto-generated method stub
		return productionDao.getmaterialReportData(req_no);
	}

	public String getDateCheckExistPrd(String production_date) throws Exception{
	
		// TODO Auto-generated method stub
		return productionDao.getDateCheckExistPrd(production_date);
	}



	public Integer saveIntoSalesStockin(Production dailyProd) throws Exception{
	
		// TODO Auto-generated method stub
		return productionDao.saveIntoSalesStockin(dailyProd);
	}



	public void saveSaleStockInDetail(ArrayList<ProductionDetail> productionItemsList , Integer sale_stock_in_hdr_id) throws Exception{
	
		// TODO Auto-generated method stub
		productionDao.saveSaleStockInDetail(productionItemsList,sale_stock_in_hdr_id);
	}



	public JsonArray getPendingMaterial(String department) throws Exception{
		
		// TODO Auto-generated method stub
		return productionDao.getPendingMaterial(department);
	}



	public JsonArray getVerifiedTotalItemsWithDepartment(String delevery_date, String custId, String deptId,
			String checkedItemArray, String timeslotArray) throws Exception {
		// TODO Auto-generated method stub
		return productionDao.getVerifiedTotalItemsWithDepartment(delevery_date,custId,deptId,checkedItemArray,timeslotArray);
	}
	
	public void updateIssuedQty(String req_date,String issuedQty,int i)throws Exception {
		
		try{
		LinkedHashMap<Integer,Double> itemMap = productionDao.updateIssuedQty(req_date, issuedQty,i);
		
		  Set<Integer> keys = itemMap.keySet();
		  Double prodQty=Double.valueOf(issuedQty);
		    
		    // printing the elements of LinkedHashMap
		    for (Integer key : keys) {
		    	
		        System.out.println(key + " -- "
		                           + itemMap.get(key));
		        Double currentIssuedQty=productionDao.getcurrentIssuedqty(key);
		        System.out.println("currentIssuedQty===========>"+currentIssuedQty);
		        if(itemMap.get(key)>=prodQty){		        	
		        	//if(currentIssuedQty<=prodQty){		        		
		        		Double prodQtys= itemMap.get(key)-currentIssuedQty;	
		        		 DecimalFormat df = new DecimalFormat("###.#######");
		        	     System.out.println("if=========Diff Val  : "+df.format(prodQtys));
		        		
		        		prodQty=prodQty-prodQtys;
		        		if(prodQty<0){
		        			// val.add(lhm.get(key)+a);
		        			 productionDao.updateissuedValue(key,itemMap.get(key)+prodQty);
		        		}else{
		        			// val.add(ab+Double.valueOf(df.format(prodQtys)));
		        			 productionDao.updateissuedValue(key,currentIssuedQty+Double.valueOf(df.format(prodQtys)));
		        		}
		        		prodQtys=0.00;		        		
		        	/*}
		        	else{
		        		
		        	}*/
		        }
		        else{			        			        	
		        	if(currentIssuedQty<=prodQty){	
		        		 Double diffValue= itemMap.get(key)-currentIssuedQty;
		        		Double prodQtys= prodQty-currentIssuedQty;	
		        		DecimalFormat df = new DecimalFormat("###.#######");
		        	     System.out.println("else========Diff Val  : "+df.format(diffValue));
		        	    // val.add(ab+Double.valueOf(df.format(diffValue)));
		        		productionDao.updateissuedValue(key,currentIssuedQty+Double.valueOf(df.format(diffValue)));
		        		if (currentIssuedQty!=0.0){
		        			Double quantityDiff=itemMap.get(key)-currentIssuedQty;		        			
		        			if(quantityDiff>0){
		        				prodQty=prodQty-quantityDiff;
		        			}
		        		}
		        		else{
		        			prodQty=prodQty-itemMap.get(key);
		        		}

		        		prodQtys=0.00;	

		        	}

		        }
		        if(prodQty<=0.0){
		        	System.out.println("brk==============>"+prodQty);
		        	break;
		        }
		        
		    }
		}catch(Exception e){
			
			System.out.println("Exception==============>"+e);
			
		}
				
	}


	
}
