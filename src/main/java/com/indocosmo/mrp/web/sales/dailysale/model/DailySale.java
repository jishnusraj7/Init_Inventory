package com.indocosmo.mrp.web.sales.dailysale.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;

@Entity
@Table(name = "uoms")
public class DailySale extends MasterModelBase {

	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.model.MasterModelBase#setId(java.lang.Integer)
	 */
	@Override
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="uom", key="uom")
	public void setId(Integer id) {
	
		super.setId(id);
	}
}
