package com.indocosmo.mrp.web.masters.prodcost.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;
import com.indocosmo.mrp.web.masters.menu.model.Menu;

@Entity
@Table(name ="mrp_prod_costcalc_params")
public class ProductionCost extends MasterModelBase{
	@Column(name = "cost_type")
	private String cost_type;
	
	@Override
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="prod_costcalc_params", key="prod_costcalc_params")
	
	
	
	public void setId(Integer id) {
	
		super.setId(id);
	}
	
	@Transient
	private List<ProductionCost> dptExcelData;
	
	
	
	@Transient
	private String cost_typ_name;
	
	@Transient
	private String descrptn;

	/**
	 * @return the dptExcelData
	 */
	public List<ProductionCost> getDptExcelData() {
		return dptExcelData;
	}
	/**
	 * @param dptExcelData the dptExcelData to set
	 */
	public void setDptExcelData(List<ProductionCost> dptExcelData) {
		this.dptExcelData = dptExcelData;
	}
	
	
	/**
	 * @return the cost_type
	 */
	public String getCost_type() {
		return cost_type;
	}

	/**
	 * @param cost_type the cost_type to set
	 */
	public void setCost_type(String cost_type) {
		this.cost_type = cost_type;
	}
	/**
	 * @return the cost_typ_name
	 */
	public String getCost_typ_name() {
		return cost_typ_name;
	}
	/**
	 * @param cost_typ_name the cost_typ_name to set
	 */
	public void setCost_typ_name(String cost_typ_name) {
		this.cost_typ_name = cost_typ_name;
	}
	/**
	 * @return the descrptn
	 */
	public String getDescrptn() {
		return descrptn;
	}
	/**
	 * @param descrptn the descrptn to set
	 */
	public void setDescrptn(String descrptn) {
		this.descrptn = descrptn;
	}
	
	
}
