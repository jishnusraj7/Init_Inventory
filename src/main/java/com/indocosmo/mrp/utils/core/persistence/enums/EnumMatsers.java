package com.indocosmo.mrp.utils.core.persistence.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class EnumMatsers {

	/*
	 * Enum For Store Location Types
	 */
	public enum storeLocationType {

		MATERIAL_STORE(0,"Material Department"), 
		PRODUCTION_UNIT(1,"Production Department");
		/*SALES_OUTLET(2,"Sales Department");*/


		private static final Map<Integer, storeLocationType> STORELOCATIONTYPEMAP = new HashMap <Integer,storeLocationType>(); 

		private int storeLocTypeId;
		private String storeLocTypeName;

		/**
		 * @param storeLocTypeId
		 * @param storeLocTypeName
		 */
		private storeLocationType(int storeLocTypeId,String storeLocTypeName){

			this.storeLocTypeId=storeLocTypeId; 
			this.storeLocTypeName=storeLocTypeName;
		}

		/**
		 * @return
		 */
		public int getStoreLocid() {

			return storeLocTypeId;
		}

		/**
		 * @return
		 */
		public String getName() {

			return storeLocTypeName;
		}

		/**
		 * @return
		 */
		public static Map<Integer, storeLocationType> getstoreLocTypemap() {

			return STORELOCATIONTYPEMAP;
		}

		static{
			for(storeLocationType type:EnumSet.allOf(storeLocationType.class))

				STORELOCATIONTYPEMAP.put(type.getStoreLocid(),type);
		}  
	}

	/*
	 * Enum For StockIn Type
	 */
	public enum stockInType {

		DC(0,"DC"), 
		INVOICE(1,"Invoice"), 
		STORE_TO_STORE(2,"Store to Store"),
		DIRECT_PURCHASE(3,"Direct Purchase");


		private static final Map<Integer, stockInType> STOCKINTYPEMAP = new HashMap <Integer,stockInType>(); 

		private int stockInTypeId;
		private String stockInTypeName;

		/**
		 * @param storeLocTypeId
		 * @param storeLocTypeName
		 */
		private stockInType(int stockInTypeId,String stockInTypeName){

			this.stockInTypeId=stockInTypeId; 
			this.stockInTypeName=stockInTypeName;
		}

		/**
		 * @return
		 */
		public int getStockInTypeId() {

			return stockInTypeId;
		}

		/**
		 * @return
		 */
		public String getName() {

			return stockInTypeName;
		}

		/**
		 * @return
		 */
		public static Map<Integer, stockInType> getstockInTypemap() {

			return STOCKINTYPEMAP;
		}

		static{
			for(stockInType type:EnumSet.allOf(stockInType.class))

				STOCKINTYPEMAP.put(type.getStockInTypeId(),type);
		}  
	}

	public enum enumitemType {

		PURCHASED(0,"Purchased"), 
		MANUFACTURED(1,"Manufactured");
		


		private static final Map<Integer, enumitemType> ENUMITEMTYPEMAP = new HashMap <Integer,enumitemType>(); 

		private Integer enumitemTypeId;
		private String enumitemTypeName;

		/**
		 * @param storeLocTypeId
		 * @param storeLocTypeName
		 */
		private enumitemType(Integer enumitemTypeId,String enumitemTypeName){

			this.enumitemTypeId=enumitemTypeId; 
			this.enumitemTypeName=enumitemTypeName;
		}

		/**
		 * @return
		 */
		public Integer getenumitemTypeId() {

			return enumitemTypeId;
		}

		/**
		 * @return
		 */
		public String getenumitemTypeName() {

			return enumitemTypeName;
		}

		/**
		 * @return
		 */
		public static Map<Integer, enumitemType> getenumitemTypemap() {

			return ENUMITEMTYPEMAP;
		}

		static{
			for(enumitemType type:EnumSet.allOf(enumitemType.class))

				ENUMITEMTYPEMAP.put(type.getenumitemTypeId(),type);
		}  
	}

	public enum transactionType {

		OPENINGSTOCK(0,"Opening Stock"), 
		PURCHASE(1,"Purchase"),
		SALE(2,"Sale"), 
		STOCKIN(3,"Stock IN"),
		STOCKOUT(4,"Stock OUT"), 
		RETURN(5,"Return"),
		PRODUCTION(6,"Production"),
		DISPOSAL(7,"Disposal"),
		ADJUSTMENT(8,"Adjustment"),
		BOMADJUSTMENT(9,"BOMadjustment"),
		STOCKTRANSFER(10,"StockTransfer");

		private static final Map<Integer, transactionType> TRANSACTIONTYPEMAP = new HashMap <Integer,transactionType>(); 

		private int transactionTypeId;
		private String transactionTypeName;

		/**
		 * @param storeLocTypeId
		 * @param storeLocTypeName
		 */
		private transactionType(int transactionTypeId,String transactionTypeName){

			this.transactionTypeId=transactionTypeId; 
			this.transactionTypeName=transactionTypeName;
		}

		/**
		 * @return
		 */
		public int gettransactionTypeId() {

			return transactionTypeId;
		}

		/**
		 * @return
		 */
		public String gettransactionTypeName() {

			return transactionTypeName;
		}

		/**
		 * @return
		 */
		public static Map<Integer, transactionType> gettransactionTypemap() {

			return TRANSACTIONTYPEMAP;
		}

		static{
			for(transactionType type:EnumSet.allOf(transactionType.class))

				TRANSACTIONTYPEMAP.put(type.gettransactionTypeId(),type);
		}  
	}
	
	
	

	public enum enumValuationMethod {

		AVERAGE_COST(0,"Average Cost"), 
		LAST_PURTCHASED(1,"Last Purchased");


		private static final Map<Integer, enumValuationMethod> ENUMVALUATIONMETHODMAP = new HashMap <Integer,enumValuationMethod>(); 

		private int enumValuationMethodId;
		private String enumValuationMethodName;


		private enumValuationMethod(int enumValuationMethodId,String enumValuationMethodName){

			this.enumValuationMethodId=enumValuationMethodId; 
			this.enumValuationMethodName=enumValuationMethodName;
		}


		public int getenumValuationMethodId() {

			return enumValuationMethodId;
		}


		public String getenumValuationMethodName() {

			return enumValuationMethodName;
		}

		/**
		 * @return
		 */
		public static Map<Integer, enumValuationMethod> getenumValuationMethodmap() {

			return ENUMVALUATIONMETHODMAP;
		}

		static{
			for(enumValuationMethod type:EnumSet.allOf(enumValuationMethod.class))

				ENUMVALUATIONMETHODMAP.put(type.getenumValuationMethodId(),type);
		}  
	}



	public enum purchaseordersType{

		NEW(0,"NEW"), 
		PRINTED(1,"PRINTED"), 
		CLOSED(2,"CLOSED"),
		PROCESSING(3,"PROCESSING");
		/*	PRINTED(3,"PRINTED"),
		RECEIVED(4,"RECEIVED"),
		CLOSED(5,"CLOSED");*/
		private static final Map<Integer, purchaseordersType> PURCHASEORDERMAP = new HashMap <Integer,purchaseordersType>();

		private int purchaseorderTypeId;
		/**
		 * @return the purchaseorderTypeId
		 */
		public int getPurchaseorderTypeId() {
			return purchaseorderTypeId;
		}



		/**
		 * @return the purchaseorderTypeName
		 */
		public String getPurchaseorderTypeName() {
			return purchaseorderTypeName;
		}

		private String purchaseorderTypeName;


		private purchaseordersType(int purchaseorderTypeId,String purchaseorderTypeName){

			this.purchaseorderTypeId=purchaseorderTypeId; 
			this.purchaseorderTypeName=purchaseorderTypeName;
		}



		/**
		 * @return the purchaseordermap
		 */
		public static Map<Integer, purchaseordersType> getPurchaseordermap() {
			return PURCHASEORDERMAP;
		} 

		static{
			for(purchaseordersType type:EnumSet.allOf(purchaseordersType.class))

				PURCHASEORDERMAP.put(type.getPurchaseorderTypeId(),type);
		}

	} 

	public enum paymentMode{

		CASH(0,"CASH"),
		CREDIT(1,"CREDIT");

		private static final Map<Integer, paymentMode> PAYMENTORDERMAP = new HashMap <Integer,paymentMode>();

        private int paymentModeTypeId;
        private String paymentModeTypeName;
        
		private paymentMode(int paymentModeTypeId, String paymentModeTypeName) {
			this.paymentModeTypeId = paymentModeTypeId;
			this.paymentModeTypeName = paymentModeTypeName;
		}


		public static Map<Integer, paymentMode> getPaymentordermap() {
			return PAYMENTORDERMAP;
		}


		public int getPaymentModeTypeId() {
			return paymentModeTypeId;
		}

		public String getPaymentModeTypeName() {
			return paymentModeTypeName;
		}
        
		static{
			for(paymentMode type:EnumSet.allOf(paymentMode.class))

				PAYMENTORDERMAP.put(type.getPaymentModeTypeId(),type);
		}
		
	}
	public enum remotrequestType{

		NEW(0,"NEW"), 
		PROCESSING(1,"PROCESSING"),
		FINISHED(2,"FINISHED"),
		REJECTED(3,"REJECTED");


		private static final Map<Integer, remotrequestType> REMOTEREQUESTMAP = new HashMap <Integer,remotrequestType>();

		private int remotrequestTypeId;
		/**
		 * @return the purchaseorderTypeId
		 */
		public int getRemotrequestTypeId() {
			return remotrequestTypeId;
		}



		/**
		 * @return the purchaseorderTypeName
		 */
		public String getRemotrequestTypeName() {
			return remotrequestTypeName;
		}

		private String remotrequestTypeName;


		private remotrequestType(int remotrequestTypeId,String remotrequestTypeName){

			this.remotrequestTypeId=remotrequestTypeId; 
			this.remotrequestTypeName=remotrequestTypeName;
		}



		/**
		 * @return the purchaseordermap
		 */
		public static Map<Integer, remotrequestType> getremotrequestmap() {
			return REMOTEREQUESTMAP;
		} 

		static{
			for(remotrequestType type:EnumSet.allOf(remotrequestType.class))

				REMOTEREQUESTMAP.put(type.getRemotrequestTypeId(),type);
		}

	} 



	public enum purchaserequestType{

		NEW(0,"NEW"), 
		SUBMITTED(1,"SUBMITTED");

		private static final Map<Integer, purchaserequestType> PURCHASEREQUESTMAP = new HashMap <Integer,purchaserequestType>();

		private int purchaserequestTypeId;
		/**
		 * @return the purchaseorderTypeId
		 */
		public int getPurchaserequestTypeId() {
			return purchaserequestTypeId;
		}



		/**
		 * @return the purchaseorderTypeName
		 */
		public String getPurchaserequestTypeName() {
			return purchaserequestTypeName;
		}

		private String purchaserequestTypeName;


		private purchaserequestType(int purchaserequestTypeId,String purchaserequestTypeName){

			this.purchaserequestTypeId=purchaserequestTypeId; 
			this.purchaserequestTypeName=purchaserequestTypeName;
		}



		/**
		 * @return the purchaseordermap
		 */
		public static Map<Integer, purchaserequestType> getPurchaserequestmap() {
			return PURCHASEREQUESTMAP;
		} 

		static{
			for(purchaserequestType type:EnumSet.allOf(purchaserequestType.class))

				PURCHASEREQUESTMAP.put(type.getPurchaserequestTypeId(),type);
		}

	} 





	public enum stockOutType{

		WITHIN_THE_STORE(0,"Within The Store"), 
		STORE_TO_STORE(1,"Store to Store");

		private static final Map<Integer, stockOutType> STOCKOUTTYPEMAP = new HashMap <Integer,stockOutType>(); 

		private int stockOutTypeId;
		private String stockOutTypeName;


		/**
		 * @param stockOutTypeId
		 * @param stockOutTypeName
		 */
		private stockOutType(int stockOutTypeId,String stockOutTypeName){

			this.stockOutTypeId=stockOutTypeId; 
			this.stockOutTypeName=stockOutTypeName;
		}

		/**
		 * @return
		 */
		public int getStockOutTypeId() {

			return stockOutTypeId;
		}

		/**
		 * @return
		 */
		public String getName() {

			return stockOutTypeName;
		}

		/**
		 * @return
		 */
		public static Map<Integer, stockOutType> getstockInTypemap() {

			return STOCKOUTTYPEMAP;
		}

		static{
			for(stockOutType type:EnumSet.allOf(stockOutType.class))

				STOCKOUTTYPEMAP.put(type.getStockOutTypeId(),type);
		}  
	}






	public enum stockInStatus{

		PENDING(0,"PENDING"), 
		FINALIZED(1,"FINALIZED"), 
		PRINTED(2,"PRINTED");


		private static final Map<Integer, stockInStatus> STOCKINSTATUS = new HashMap <Integer,stockInStatus>();

		private int stockInStatusId;

		/**
		 * @return
		 */
		public int getStockInStatusId() {

			return stockInStatusId;
		}

		/**
		 * @return
		 */
		public String getStockInStatusName() {

			return stockInStatusName;
		}

		private String stockInStatusName;


		/**
		 * @param stockOutStatusId
		 * @param stockOutStatusName
		 */
		private stockInStatus(int stockInStatusId,String stockInStatusName){

			this.stockInStatusId=stockInStatusId; 
			this.stockInStatusName=stockInStatusName;
		}

		/**
		 * @return
		 */
		public static Map<Integer, stockInStatus> getStockInStatusmap() {

			return STOCKINSTATUS;
		} 

		static{
			for(stockInStatus type:EnumSet.allOf(stockInStatus.class))

				STOCKINSTATUS.put(type.getStockInStatusId(),type);
		}

	} 




	public enum stockOutStatus{

		NEW(0,"NEW"), 
		APPROVED(1,"APPROVED"), 
		REJECTED(2,"REJECTED"),
		ISSUED(3,"PRINTED"),
		PRINTED (4,"RECEIVED");

		private static final Map<Integer, stockOutStatus> STOCKOUTSTATUS = new HashMap <Integer,stockOutStatus>();

		private int stockOutStatusId;

		/**
		 * @return
		 */
		public int getStockOutStatusId() {

			return stockOutStatusId;
		}

		/**
		 * @return
		 */
		public String getStockOutStatusName() {

			return stockOutStatusName;
		}

		private String stockOutStatusName;


		/**
		 * @param stockOutStatusId
		 * @param stockOutStatusName
		 */
		private stockOutStatus(int stockOutStatusId,String stockOutStatusName){

			this.stockOutStatusId=stockOutStatusId; 
			this.stockOutStatusName=stockOutStatusName;
		}

		/**
		 * @return
		 */
		public static Map<Integer, stockOutStatus> getStockOutStatusmap() {

			return STOCKOUTSTATUS;
		} 

		static{
			for(stockOutStatus type:EnumSet.allOf(stockOutStatus.class))

				STOCKOUTSTATUS.put(type.getStockOutStatusId(),type);
		}

	} 


	public enum subheaderMenus{
		MAIN_SETT(0,"SETTINGS"),
		MAIN_STR(1,"STORE"),
		MAIN_PRD(2,"PRODUCTION"),
		MAIN_SALES(3,"SALES"),
		MAIN_ACC(4,"ACCOUNTS"),
		MAIN_RPRT(5,"REPORTS");
		private static final Map<Integer, subheaderMenus> SUBHEADERMENUS=new HashMap<Integer,subheaderMenus>();
		private int subheaderMenusId;
		/**
		 * @return the subheaderMenusId
		 */
		public int getSubheaderMenusId() {
			return subheaderMenusId;
		}

		public String getSubheaderMenusName() {
			return subheaderMenusName;
		}

		public static Map<Integer, subheaderMenus> getSubheadermenus() {
			return SUBHEADERMENUS;
		}
		private String subheaderMenusName;

		private subheaderMenus(int subheaderMenusId,String subheaderMenusName){

			this.subheaderMenusId=subheaderMenusId; 
			this.subheaderMenusName=subheaderMenusName;
		}

		static{
			for(subheaderMenus type:EnumSet.allOf(subheaderMenus.class))

				SUBHEADERMENUS.put(type.getSubheaderMenusId(),type);
		}


	}

	/*
	 * Enum For Department Type
	 */

	public enum enumDepartmentType {
		FINISHED_GOODS(1,"FINISHED_GOODS"),
		DEP_STORE(2,"General store"), 
		DEP_PRODUCTION(0,"Production department");
		

		private static final Map<Integer, enumDepartmentType> ENUM_DEPARTMENT_TYPEMAP = new HashMap <Integer,enumDepartmentType>(); 

		private Integer enumDepartmentTypeId;
		private String enumDepartmentTypeName;

		/**
		 * @param enumDepartmentTypeId
		 * @param enumDepartmentTypeName
		 */
		private enumDepartmentType(Integer  enumDepartmentTypeId,String enumDepartmentTypeName){
			this.enumDepartmentTypeId=enumDepartmentTypeId;
			this.enumDepartmentTypeName=enumDepartmentTypeName; 

		}

		/**
		 * @return
		 */
		public Integer getenumDepartmentTypeId() {

			return enumDepartmentTypeId;
		}

		/**
		 * @return
		 */
		public String getenumDepartmentTypeName() {

			return enumDepartmentTypeName;
		}

		/**
		 * @return
		 */
		public static Map<Integer, enumDepartmentType> getenumDepartmentTypemap() {

			return ENUM_DEPARTMENT_TYPEMAP;
		}

		static{
			for(enumDepartmentType type:EnumSet.allOf(enumDepartmentType.class))

				ENUM_DEPARTMENT_TYPEMAP.put(type.getenumDepartmentTypeId(),type);
		}  
	}
	/*
	 * Enum For Production Cost Types
	 */
	public enum productionCostType {

		/*MATERIAL_TYPE(0,"Materials"), */
		LABOUR_TYPE(1,"Labour"), 
		OTHERS(2,"Others");

		private static final Map<Integer, productionCostType> PRODUCTIONCOSTYPEMAP = new HashMap <Integer,productionCostType>(); 

		private int prodCostTypeId;
		private String prodCostTypeName;

		/**
		 * @param prodCostTypeId
		 * @param prodCostTypeId
		 */
		private productionCostType(int prodCostTypeId,String prodCostTypeName){

			this.prodCostTypeId=prodCostTypeId; 
			this.prodCostTypeName=prodCostTypeName;
		}

		/**
		 * @return
		 */
		public int getProdCostTypeid() {

			return prodCostTypeId;
		}

		/**
		 * @return
		 */
		public String getName() {

			return prodCostTypeName;
		}

		/**
		 * @return
		 */
		public static Map<Integer, productionCostType> getProdCostTypemap() {

			return PRODUCTIONCOSTYPEMAP;
		}

		static{
			for(productionCostType type:EnumSet.allOf(productionCostType.class))

				PRODUCTIONCOSTYPEMAP.put(type.getProdCostTypeid(),type);
		}  
	}



	public enum ImplementationMode {
		FULL(0,"mode_full"),
		LITE(1,"mode_lite");
		

		private static final Map<Integer, ImplementationMode> IMPLEMENTATIONMODEEMAP = new HashMap <Integer,ImplementationMode>(); 

		private int implementationModeId;
		private String columnName;

		/**
		 * @param prodCostTypeId
		 * @param prodCostTypeId
		 */
		private ImplementationMode(int implementationModeId,String columnName){

			this.implementationModeId=implementationModeId; 
			this.columnName=columnName;
		}
		/**
		 * @return
		 */
		public int getImplementationModeId() {

			return implementationModeId;
		}

		/**
		 * @return
		 */
		public String getColumnName() {

			return columnName;
		}
		
		public static ImplementationMode get(int id) {
			
			return IMPLEMENTATIONMODEEMAP.get(id);
		}

		/**
		 * @return
		 */
		public static Map<Integer, ImplementationMode> getImplementationModemap() {

			return IMPLEMENTATIONMODEEMAP;
		}

		static{
			for(ImplementationMode type:EnumSet.allOf(ImplementationMode.class))

				IMPLEMENTATIONMODEEMAP.put(type.getImplementationModeId(),type);
		}  
	}
	//enum for implementation mode
	
	public enum CombineMode{
		
		COMBINE(1,"purchase_combine"),
		SEPARATE(0,"purchase_separate");
		
		private static final Map<Integer, CombineMode> COMBINEMODEEMAP = new HashMap <Integer,CombineMode>(); 
		private int combineModeId;
		private String columnName;
		
		
		private CombineMode(int combineModeId,String columnName){

			this.combineModeId=combineModeId; 
			this.columnName=columnName;
		}

		
		/**
		 * @return the combinemodeemap
		 */
		public static Map<Integer, CombineMode> getCombinemodeemap() {
		
			return COMBINEMODEEMAP;
		}
		
		/**
		 * @return the combineModeId
		 */
		public int getCombineModeId() {
		
			return combineModeId;
		}
		
		/**
		 * @return the columnName
		 */
		public String getColumnName() {
		
			return columnName;
		}
		
		
      public static CombineMode get(int id) {
			
			return COMBINEMODEEMAP.get(id);
		}

		
		static{
			for(CombineMode type:EnumSet.allOf(CombineMode.class))

				COMBINEMODEEMAP.put(type.getCombineModeId(),type);
		}  

		
	}
	
	//enum for dailyproduction view
	
	
	
	
public enum DailyproductionView{
		
		ITEMWISE(0,"itemwise_view"),
		SHOPWISE(1,"shopwise_view");
		
		private static final Map<Integer, DailyproductionView> DAILYPRODUCTIONVIEWMAP = new HashMap <Integer,DailyproductionView>(); 
		private int DailyproductionViewId;
		private String columnName;
		
		
		private DailyproductionView(int DailyproductionViewId,String columnName){

			this.DailyproductionViewId=DailyproductionViewId; 
			this.columnName=columnName;
		}

		
				
		
      
		/**
		 * @return the dailyproductionviewmap
		 */
		public static Map<Integer, DailyproductionView> getDailyproductionviewmap() {
		
			return DAILYPRODUCTIONVIEWMAP;
		}




		
		/**
		 * @return the dailyproductionViewId
		 */
		public int getDailyproductionViewId() {
		
			return DailyproductionViewId;
		}




		
		/**
		 * @return the columnName
		 */
		public String getColumnName() {
		
			return columnName;
		}


	public static DailyproductionView get(int id) {
			
			return DAILYPRODUCTIONVIEWMAP.get(id);
		}

		
		static{
			for(DailyproductionView type:EnumSet.allOf(DailyproductionView.class))

				DAILYPRODUCTIONVIEWMAP.put(type.getDailyproductionViewId(),type);
		}  

		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * Enum For Stock Disposal Reasons
	 */
	public enum stockDisposalReasonType {

		/*MATERIAL_TYPE(0,"Materials"), */
		DAMAGE(1,"Damage"), 
		WASTAGE(2,"Wastage"),
	    EXPIRY(3,"Expiry");

		private static final Map<Integer, stockDisposalReasonType> 
		         DISPOSALREASONTYPEMAP = new HashMap <Integer,stockDisposalReasonType>(); 

		private int disposalReasonTypeId;
		private String disposalReasonTypeName;

		/**
		 * @param disposalReasonTypeId
		 * @param disposalReasonTypeId
		 */
		private stockDisposalReasonType(int disposalReasonTypeId,String disposalReasonTypeName){

			this.disposalReasonTypeId=disposalReasonTypeId; 
			this.disposalReasonTypeName=disposalReasonTypeName;
		}

		
		/**
		 * @return the disposalReasonTypeId
		 */
		public int getDisposalReasonTypeId() {
			return disposalReasonTypeId;
		}

		/**
		 * @return the disposalReasonTypeName
		 */
		public String getDisposalReasonTypeName() {
			return disposalReasonTypeName;
		}


		/**
		 * @return the disposalreasontypemap
		 */
		public static Map<Integer, stockDisposalReasonType> getDisposalreasontypemap() {
			return DISPOSALREASONTYPEMAP;
		}


		static{
			for(stockDisposalReasonType type:EnumSet.allOf(stockDisposalReasonType.class))

				DISPOSALREASONTYPEMAP.put(type.getDisposalReasonTypeId(),type);
		}  
		
	}
	
	/*
	 * Enum For Production Order Status
	 */
	public enum productionOrderStatusType {

		/*MATERIAL_TYPE(0,"Materials"), */
		PENDING(0,"Pending"), 
		FINALIZED(1,"Finalized"),
	    ON_PRODUCTION(2,"On_Production"),
	    FINISH_PRODUCTION(3,"Finish Production"),
		DELIVERED(4,"Delivered");
		
		private static final Map<Integer, productionOrderStatusType> 
		         PRODUCTIONORDERSTATUSMAP = new HashMap <Integer,productionOrderStatusType>(); 

		private int productionOrderStatusId;
		private String productionOrderStatusName;

		/**
		 * @param productionOrderStatusId
		 * @param productionOrderStatusId
		 */
		private productionOrderStatusType(int productionOrderStatusId,String productionOrderStatusName){

			this.productionOrderStatusId=productionOrderStatusId; 
			this.productionOrderStatusName=productionOrderStatusName;
		}


		/**
		 * @return the productionOrderStatusId
		 */
		public int getProductionOrderStatusId() {
			return productionOrderStatusId;
		}

		/**
		 * @return the productionOrderStatusName
		 */
		public String getProductionOrderStatusName() {
			return productionOrderStatusName;
		}

		/**
		 * @return the productionorderstatusmap
		 */
		public static Map<Integer, productionOrderStatusType> getProductionorderstatusmap() {
			return PRODUCTIONORDERSTATUSMAP;
		}



		static{
			for(productionOrderStatusType type:EnumSet.allOf(productionOrderStatusType.class))

				PRODUCTIONORDERSTATUSMAP.put(type.getProductionOrderStatusId(),type);
		}  
		
	}
	
	
	
	public enum Hq {
		HQ(0,"HQ");
	
		

		private static final Map<Integer, Hq> HQMAP = new HashMap <Integer,Hq>(); 

		private int hqId;
		private String columnName;

		/**
		 * @param prodCostTypeId
		 * @param prodCostTypeId
		 */
		private Hq(int hqId,String columnName){

			this.hqId=hqId; 
			this.columnName=columnName;
		}
		/**
		 * @return
		 */
		public int getHqId() {

			return hqId;
		}

		/**
		 * @return
		 */
		public String getColumnName() {

			return columnName;
		}
		
		public static Hq get(int id) {
			
			return HQMAP.get(id);
		}

		/**
		 * @return
		 */
		public static Map<Integer, Hq> getHqmap() {

			return HQMAP;
		}

		static{
			for(Hq type:EnumSet.allOf(Hq.class))

				HQMAP.put(type.getHqId(),type);
		}  
	}


	
}
