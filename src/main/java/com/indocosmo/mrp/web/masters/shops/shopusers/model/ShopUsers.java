package com.indocosmo.mrp.web.masters.shops.shopusers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="shop_users")
public class ShopUsers extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="shop_users", key="shop_users")
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "shop_id")
	private Integer shop_id;
	
	@Column(name = "user_id")
	private Integer user_id;
	
	@Column(name = "user_group_id")
	private Integer user_group_id;
	
	@Column(name = "publish_status")
	private Integer publish_status=0;


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
	 * @return the user_id
	 */
	public Integer getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the user_group_id
	 */
	public Integer getUser_group_id() {
		return user_group_id;
	}

	/**
	 * @param user_group_id the user_group_id to set
	 */
	public void setUser_group_id(Integer user_group_id) {
		this.user_group_id = user_group_id;
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
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	
	
}
