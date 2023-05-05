package com.indocosmo.mrp.utils.war;

import org.springframework.stereotype.Component;

@Component 
public class WarUtils {
	
	
	public static Double getWarRate(Double inQty, Double inQtyRate,Double previousQty,Double priviousQtyRate ) throws Exception {
	
		return ((inQty *inQtyRate)+(previousQty*priviousQtyRate))/(inQty+previousQty);
	}

	public static Double getWarRateNew(Double currentStockPrice, Double reqPrice) {
		// TODO Auto-generated method stub
		return ((currentStockPrice+reqPrice)/2);
	}

}
