package com.indocosmo.mrp.config.bean;

import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.CombineMode;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.DailyproductionView;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.ImplementationMode;


public class SystemSettings {
	
	
	private ImplementationMode implementationMode;
	private String salesUrl;
    private CombineMode combineModePurchase;
    private DailyproductionView dailyproductionView;
	
	
	
	
	/**
	 * @return the dailyproductionView
	 */
	public DailyproductionView getDailyproductionView() {
	
		return dailyproductionView;
	}




	
	/**
	 * @param dailyproductionView the dailyproductionView to set
	 */
	public void setDailyproductionView(DailyproductionView dailyproductionView) {
	
		this.dailyproductionView = dailyproductionView;
	}




	/**
	 * @return the combineModePurchase
	 */
	public CombineMode getCombineModePurchase() {
	
		return combineModePurchase;
	}



	
	/**
	 * @param combineModePurchase the combineModePurchase to set
	 */
	public void setCombineModePurchase(CombineMode combineModePurchase) {
	
		this.combineModePurchase = combineModePurchase;
	}



	/**
	 * @return the salesUrl
	 */
	public String getSalesUrl() {
	
		return salesUrl;
	}


	
	/**
	 * @param salesUrl the salesUrl to set
	 */
	public void setSalesUrl(String salesUrl) {
	
		this.salesUrl = salesUrl;
	}


	/**
	 * @return the implementationMode
	 */
	public ImplementationMode getImplementationMode() {
	
		return implementationMode;
	}

	
	/**
	 * @param implementationMode the implementationMode to set
	 */
	public void setImplementationMode(ImplementationMode implementationMode) {
	
		this.implementationMode = implementationMode;
	}
}
