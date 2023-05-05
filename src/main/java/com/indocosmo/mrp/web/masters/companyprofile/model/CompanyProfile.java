package com.indocosmo.mrp.web.masters.companyprofile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "company_profile")
public class CompanyProfile extends GeneralModelBase {
	
	@Pk
	@Id(generationType = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Integer company_id;
	
	/**
	 * @return the company_id
	 */
	public Integer getCompany_id() {
	
		return company_id;
	}
	
	/**
	 * @param company_id
	 *            the company_id to set
	 */
	
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "system_params", key = "system_params")
	public void setCompany_id(Integer company_id) {
	
		this.company_id = company_id;
	}
	
	@Column(name = "company_name")
	private String company_name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "tin_no")
	private String tin_no;
	
	@Column(name = "billing_address")
	private String billing_address;
	
	@Column(name = "shipping_address")
	private String shipping_address;
	
	/**
	 * @return the company_name
	 */
	public String getCompany_name() {
	
		return company_name;
	}
	
	/**
	 * @param company_name
	 *            the company_name to set
	 */
	public void setCompany_name(String company_name) {
	
		this.company_name = company_name;
	}
	
	/**
	 * @return the address
	 */
	public String getAddress() {
	
		return address;
	}
	
	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
	
		this.address = address;
	}
	
	/**
	 * @return the tin_no
	 */
	public String getTin_no() {
	
		return tin_no;
	}
	
	/**
	 * @param tin_no
	 *            the tin_no to set
	 */
	public void setTin_no(String tin_no) {
	
		this.tin_no = tin_no;
	}
	
	/**
	 * @return the billing_address
	 */
	public String getBilling_address() {
	
		return billing_address;
	}
	
	/**
	 * @param billing_address
	 *            the billing_address to set
	 */
	public void setBilling_address(String billing_address) {
	
		this.billing_address = billing_address;
	}
	
	/**
	 * @return the shipping_address
	 */
	public String getShipping_address() {
	
		return shipping_address;
	}
	
	/**
	 * @param shipping_address
	 *            the shipping_address to set
	 */
	public void setShipping_address(String shipping_address) {
	
		this.shipping_address = shipping_address;
	}
	
}
