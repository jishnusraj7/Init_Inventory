package com.indocosmo.mrp.utils.core;

import java.lang.reflect.Field;

/**
 * @author jojesh-13.2
 *
 */
public class WebUtil {

	/**
	 * No need to create instance
	 */
	private WebUtil() {}
	
	
	public static Object pageFieldToModelValue(Field field, String valueString){
		
		Object value=null;

		if(valueString!=null){

			if (!valueString.trim().equals("") && field.getType()==Double.class)
				value=Double.parseDouble(valueString);
			else if (!valueString.trim().equals("") && field.getType()==Integer.class)
				value=Integer.parseInt(valueString);
			else if (!valueString.trim().equals("") && field.getType()==Boolean.class)
				value=valueString.equals("1");
			else if (!valueString.trim().equals("") && field.getType()==Float.class)
				value=Float.parseFloat(valueString);
			else if(!valueString.trim().equals("") && field.getType()==Long.class)
				value=Long.parseLong(valueString);
			else if (field.getType()==String.class)
				value=valueString;
		}

		return value;
	}

}
