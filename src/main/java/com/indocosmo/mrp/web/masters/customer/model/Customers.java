package com.indocosmo.mrp.web.masters.customer.model;

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
@Table(name = "customers")
public class Customers extends GeneralModelBase {
	
	@Pk
	@Id(generationType = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "is_deleted")
	private Integer isDeleted = 0;
	
	@Column(name = "customer_type")
	private Integer customer_type;
	
	@Column(name = "is_valid")
	private Integer is_valid;
	
	
	
	public Integer getShop_id() {
	
		return shop_id;
	}


	
	public void setShop_id(Integer shop_id) {
	
		this.shop_id = shop_id;
	}

	@Column(name = "shop_id")
	private Integer shop_id;
	
	
	
	
	
	@Column(name = "card_no")
	private String card_no;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "fax")
	private String fax;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "is_ar")
	private Integer is_ar;
	
	@Column(name = "ar_code")
	private String ar_code;
	
	@Column(name = "zip_code")
	private String zip_code;
	
	@Column(name = "accumulated_points")
	private Long accumulated_points;
	
	@Column(name = "redeemed_points")
	private Long redeemed_points;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "updated_by")
	private Integer update_by;
	
	@Column(name = "updated_at")
	private String update_at;

	
	
	@Column(name = "cst_no")
	private String cst_no;

	
	@Column(name = "license_no")
	private String license_no;

	
	@Column(name = "tin")
	private String tin;

	
	@Transient
	private List<Customers> custExcelData;

	
	/**
	 * @return the accumulated_points
	 */
	public Long getAccumulated_points() {
	
		return accumulated_points;
	}

	
	/**
	 * @return the address
	 */
	public String getAddress() {
	
		return address;
	}

	/**
	 * @return the ar_code
	 */
	public String getAr_code() {
	
		return ar_code;
	}
	
	/**
	 * @return the card_no
	 */
	public String getCard_no() {
	
		return card_no;
	}
	
	/**
	 * @return the city
	 */
	public String getCity() {
	
		return city;
	}


	
	
	
	/**
	 * @return the code
	 */
	public String getCode() {
	
		return code;
	}
	
	/**
	 * @return the country
	 */
	public String getCountry() {
	
		return country;
	}
	
	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
	
		return created_at;
	}
	
	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
	
		return created_by;
	}
	
	public String getCst_no() {
	
		return cst_no;
	}
	
	/**
	 * @return the custExcelData
	 */
	public List<Customers> getCustExcelData() {
	
		return custExcelData;
	}
	
	/**
	 * @return the customer_type
	 */
	public Integer getCustomer_type() {
	
		return customer_type;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
	
		return email;
	}
	
	/**
	 * @return the fax
	 */
	public String getFax() {
	
		return fax;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
	
		return id;
	}
	
	/**
	 * @return the is_ar
	 */
	public Integer getIs_ar() {
	
		return is_ar;
	}
	
	/**
	 * @return the is_valid
	 */
	public Integer getIs_valid() {
	
		return is_valid;
	}
	
	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
	
		return isDeleted;
	}
	
	public String getLicense_no() {
	
		return license_no;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
	
		return name;
	}
	
	/**
	 * @return the phone
	 */
	public String getPhone() {
	
		return phone;
	}
	
	/**
	 * @return the redeemed_points
	 */
	public Long getRedeemed_points() {
	
		return redeemed_points;
	}
	
	/**
	 * @return the state
	 */
	public String getState() {
	
		return state;
	}
	
	/**
	 * @return the street
	 */
	public String getStreet() {
	
		return street;
	}
	
	public String getTin() {
	
		return tin;
	}
	
	/**
	 * @return the update_at
	 */
	public String getUpdate_at() {
	
		return update_at;
	}
	
	/**
	 * @return the update_by
	 */
	public Integer getUpdate_by() {
	
		return update_by;
	}
	
	/**
	 * @return the zip_code
	 */
	public String getZip_code() {
	
		return zip_code;
	}
	
	/**
	 * @param accumulated_points
	 *            the accumulated_points to set
	 */
	public void setAccumulated_points(Long accumulated_points) {
	
		this.accumulated_points = accumulated_points;
	}
	
	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
	
		this.address = address;
	}
	
	/**
	 * @param ar_code
	 *            the ar_code to set
	 */
	public void setAr_code(String ar_code) {
	
		this.ar_code = ar_code;
	}
	
	/**
	 * @param card_no
	 *            the card_no to set
	 */
	public void setCard_no(String card_no) {
	
		this.card_no = card_no;
	}
	
	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
	
		this.city = city;
	}
	
	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
	
		this.code = code;
	}
	
	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
	
		this.country = country;
	}
	
	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreated_at(String created_at) {
	
		this.created_at = created_at;
	}
	
	/**
	 * @param created_by
	 *            the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
	
		this.created_by = created_by;
	}
	
	public void setCst_no(String cst_no) {
	
		this.cst_no = cst_no;
	}
	
	/**
	 * @param custExcelData
	 *            the custExcelData to set
	 */
	public void setCustExcelData(List<Customers> custExcelData) {
	
		this.custExcelData = custExcelData;
	}
	
	/**
	 * @param customer_type
	 *            the customer_type to set
	 */
	public void setCustomer_type(Integer customer_type) {
	
		this.customer_type = customer_type;
	}
	
	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
	
		this.email = email;
	}
	
	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
	
		this.fax = fax;
	}
	
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "customers", key = "customers")
	public void setId(Integer id) {
	
		this.id = id;
	}
	
	/**
	 * @param is_ar
	 *            the is_ar to set
	 */
	public void setIs_ar(Integer is_ar) {
	
		this.is_ar = is_ar;
	}
	
	/**
	 * @param is_valid
	 *            the is_valid to set
	 */
	public void setIs_valid(Integer is_valid) {
	
		this.is_valid = is_valid;
	}
	
	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
	
		this.isDeleted = isDeleted;
	}
	
	public void setLicense_no(String license_no) {
	
		this.license_no = license_no;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
	
		this.name = name;
	}
	
	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
	
		this.phone = phone;
	}
	
	/**
	 * @param redeemed_points
	 *            the redeemed_points to set
	 */
	public void setRedeemed_points(Long redeemed_points) {
	
		this.redeemed_points = redeemed_points;
	}
	
	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
	
		this.state = state;
	}
	
	/**
	 * @param street
	 *            the street to set
	 */
	public void setStreet(String street) {
	
		this.street = street;
	}
	
	public void setTin(String tin) {
	
		this.tin = tin;
	}
	
	/**
	 * @param update_at
	 *            the update_at to set
	 */
	public void setUpdate_at(String update_at) {
	
		this.update_at = update_at;
	}
	
	/**
	 * @param update_by
	 *            the update_by to set
	 */
	public void setUpdate_by(Integer update_by) {
	
		this.update_by = update_by;
	}
	
	/**
	 * @param zip_code
	 *            the zip_code to set
	 */
	public void setZip_code(String zip_code) {
	
		this.zip_code = zip_code;
	}
	
}
