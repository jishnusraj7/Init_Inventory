package com.indocosmo.mrp.utils.core;



import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Reflection utility functions
 * @author jojesh-13.2
 *
 */
public class ReflectionUtil {
	
	/**
	 * No need to create the ob ject of this class. All functions are static
	 */
	private ReflectionUtil(){};
	

	/**
	 * @param cls
	 * @return
	 */
	public static ArrayList<Field> getAllFileds(Class<?> cls){

		ArrayList<Field> fields=new ArrayList<Field>();
		while(cls!=null){
			
			for(Field field : cls.getDeclaredFields()){
				fields.add(field);
			}
			cls=cls.getSuperclass();
		}
		
		return fields;
	}
	
	public static HashMap<String, PropertyDescriptor> getPropertyDescriptors(Class<?> cls) throws IntrospectionException{

		HashMap<String, PropertyDescriptor> propertyDescriptors=new HashMap<String, PropertyDescriptor>();
		
		for(PropertyDescriptor propertyDescriptor : 
		    Introspector.getBeanInfo(cls).getPropertyDescriptors()){
			propertyDescriptors.put(propertyDescriptor.getName(),propertyDescriptor);
		}
		
		return propertyDescriptors;
	}
	
	


	
	/**
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static  <T> Class<T> getGenericType() throws InstantiationException, IllegalAccessException {
		@SuppressWarnings("serial")
		List<T> myFoo = new ArrayList<T>(){};
		Type mySuperclass = myFoo.getClass().getGenericSuperclass();
		Type tType = ((ParameterizedType)mySuperclass).getActualTypeArguments()[0];
		return (Class<T>) tType.getClass().newInstance();
	}
	
	
//	/**
//	 * @param field
//	 * @param paramVal
//	 * @return
//	 */
//	public static Object getFiledValue(Field field, String paramVal){
//		
//		final String valueString=paramVal;
//		Object value=null;
//		
//		if (field.getType()==Double.class)
//			value=Double.parseDouble(valueString);
//		else if (field.getType()==Integer.class)
//			value=Integer.parseInt(valueString);
//		else if (field.getType()==Boolean.class)
//			value=Boolean.parseBoolean(valueString);
//		else if (field.getType()==Float.class)
//			value=Float.parseFloat(valueString);
//		else
//			value=valueString;
//		
//		return value;
//	}
	

}
