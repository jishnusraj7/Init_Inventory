package com.indocosmo.mrp.web.masters.shops.shopdepartments.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="shop_departments")
public class ShopDepartment extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "shop_id")
	private Integer shop_id;
	
	@Column(name = "department_id")
	private Integer department_id;
	
	@Column(name = "publish_status")
	private Integer publish_status;
	
	@Column(name = "is_deleted")
	private Integer is_deleted;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="shop_departments", key="shop_departments")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the shop_id
	 */
	public Integer getShop_id() {
		return shop_id;
	}

	/**
	 * @param shop_id the shop_id to set
	 */
	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}

	/**
	 * @return the department_id
	 */
	public Integer getDepartment_id() {
		return department_id;
	}

	/**
	 * @param department_id the department_id to set
	 */
	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}

	/**
	 * @return the publish_status
	 */
	public Integer getPublish_status() {
		return publish_status;
	}

	/**
	 * @param publish_status the publish_status to set
	 */
	public void setPublish_status(Integer publish_status) {
		this.publish_status = publish_status;
	}

	/**
	 * @return the is_deleted
	 */
	public Integer getIs_deleted() {
		return is_deleted;
	}

	/**
	 * @param is_deleted the is_deleted to set
	 */
	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}


	
	

}
