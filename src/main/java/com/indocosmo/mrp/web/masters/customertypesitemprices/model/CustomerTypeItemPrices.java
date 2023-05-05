package com.indocosmo.mrp.web.masters.customertypesitemprices.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "customers_type_item_prices")
public class CustomerTypeItemPrices extends GeneralModelBase {
	
	

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;


	
	
	@Column(name = "is_synchable")
	private Integer is_synchable=1;

	@Column(name = "is_deleted")
	private Integer is_deleted=0;
	
	@Column(name = "sale_item_id")
	private Integer sale_item_id;
	
	
	@Column(name = "publish_status")
	private Integer publish_status=1;
	
	@Column(name = "customer_type_id")
	private Integer customer_type_id;
	
	
	@Column(name = "is_percentage")
	private Integer is_percentage;
	
	@Column(name = "price_variance_pc")
	private Double price_variance_pc;
	

	@Transient
	private List<CustomerTypeItemPrices> custExcelData;
	
	
	public Integer getIs_synchable() {
	
		return is_synchable;
	}

	
	public void setIs_synchable(Integer is_synchable) {
	
		this.is_synchable = is_synchable;
	}

	
	public Integer getIs_deleted() {
	
		return is_deleted;
	}

	
	public void setIs_deleted(Integer is_deleted) {
	
		this.is_deleted = is_deleted;
	}

	
	public Integer getSale_item_id() {
	
		return sale_item_id;
	}

	
	public void setSale_item_id(Integer sale_item_id) {
	
		this.sale_item_id = sale_item_id;
	}

	
	public Integer getPublish_status() {
	
		return publish_status;
	}

	
	public void setPublish_status(Integer publish_status) {
	
		this.publish_status = publish_status;
	}

	
	public Integer getCustomer_type_id() {
	
		return customer_type_id;
	}

	
	public void setCustomer_type_id(Integer customer_type_id) {
	
		this.customer_type_id = customer_type_id;
	}

	
	public Integer getIs_percentage() {
	
		return is_percentage;
	}

	
	public void setIs_percentage(Integer is_percentage) {
	
		this.is_percentage = is_percentage;
	}

	
	public Double getPrice_variance_pc() {
	
		return price_variance_pc;
	}

	
	public void setPrice_variance_pc(Double price_variance_pc) {
	
		this.price_variance_pc = price_variance_pc;
	}

	
	public List<CustomerTypeItemPrices> getCustExcelData() {
	
		return custExcelData;
	}

	
	public void setCustExcelData(List<CustomerTypeItemPrices> custExcelData) {
	
		this.custExcelData = custExcelData;
	}

	
	public Integer getId() {
	
		return id;
	}


	
	
	
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "customer_types_item_prices", key = "customer_types_item_prices")
	public void setId(Integer id) {
		this.id = id;
	}
	
}