package com.indocosmo.mrp.utils.war;

import org.springframework.beans.factory.annotation.Autowired;

public class Test {


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WarUtils warUtils = null;
		Double inQty=20.0;
		 Double inQtyRate=30.0;
		 Double previousQty=10.0;
		 Double priviousQtyRate=50.0;
		 	
		 Double war ;
		try {
			war = warUtils.getWarRate(inQty, inQtyRate, previousQty, priviousQtyRate);

			System.out.println("war======================"+war);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
