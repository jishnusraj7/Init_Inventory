/**
 * 
 */
package com.indocosmo.mrp.utils.core.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;


/**
 * @author jojesh-13.2
 *
 */

@Target(value={ElementType.METHOD,ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public abstract @interface Id  {

	/**
	 * @return
	 */
	public GenerationType generationType() default GenerationType.IDENTITY;

}