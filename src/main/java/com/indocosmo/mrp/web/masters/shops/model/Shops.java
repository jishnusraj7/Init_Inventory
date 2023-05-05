package com.indocosmo.mrp.web.masters.shops.model;

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
@Table(name ="shop")
public class Shops extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;	

	@Column(name = "description")
	private String description;
	
	@Column(name = "is_deleted")
	private Integer isDeleted=0;
	
	@Column(name = "area_id")
	private Long area_id;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	
	@Column(name = "phone")
	private String phone;
	

	
	@Column(name = "email")
	private String email;
	
	

	@Column(name = "email_subscribe")
	private Integer email_subscribe;
	
	@Column(name = "cst_no")
	private String cst_no;
	
	@Transient
	private Integer is_customer;
	
	
	public String getPhone() {
	
		return phone;
	}








	
	public void setPhone(String phone) {
	
		this.phone = phone;
	}








	
	public String getEmail() {
	
		return email;
	}








	
	public void setEmail(String email) {
	
		this.email = email;
	}








	
	public Integer getEmail_subscribe() {
	
		return email_subscribe;
	}








	
	public void setEmail_subscribe(Integer email_subscribe) {
	
		this.email_subscribe = email_subscribe;
	}















	@Column(name = "country")
	private String country;
	
	@Column(name = "zip_code")
	private String zip_code;
	
	@Column(name = "company_license_no")
	private String company_license_no;
	
	@Column(name = "company_tax_no")
	private String company_tax_no;
	
	@Column(name = "service_type")
	private Integer service_type=1;
	
	@Column(name = "is_synchable")
	private Integer is_synchable=1;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	@Transient
	private List<Shops> shopsExcelData;
	
	
	@Transient
	private String deptName;
	
	
	@Transient
	private Integer[] depList;
	
	
	@Transient
	private String db_database;
	

	@Transient
	private Integer compnyId;
	
	@Transient
	private Boolean is_hq;
	
	
	
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="shop", key="shop")
	public void setId(Integer id) {
		this.id = id;
	}








	/**
	 * @return the area_id
	 */
	public Long getArea_id() {
		return area_id;
	}









	/**
	 * @param area_id the area_id to set
	 */
	public void setArea_id(Long area_id) {
		this.area_id = area_id;
	}









	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}









	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}









	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}









	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}









	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}









	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}









	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}









	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}









	/**
	 * @return the zip_code
	 */
	public String getZip_code() {
		return zip_code;
	}









	/**
	 * @param zip_code the zip_code to set
	 */
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}









	/**
	 * @return the company_license_no
	 */
	public String getCompany_license_no() {
		return company_license_no;
	}









	/**
	 * @param company_license_no the company_license_no to set
	 */
	public void setCompany_license_no(String company_license_no) {
		this.company_license_no = company_license_no;
	}









	/**
	 * @return the company_tax_no
	 */
	public String getCompany_tax_no() {
		return company_tax_no;
	}









	/**
	 * @param company_tax_no the company_tax_no to set
	 */
	public void setCompany_tax_no(String company_tax_no) {
		this.company_tax_no = company_tax_no;
	}









	/**
	 * @return the service_type
	 */
	public Integer getService_type() {
		return service_type;
	}









	/**
	 * @param service_type the service_type to set
	 */
	public void setService_type(Integer service_type) {
		this.service_type = service_type;
	}









	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
		return created_by;
	}









	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}









	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}









	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}









	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
		return updated_by;
	}









	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}









	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}









	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}









	/**
	 * @return the depList
	 */
	public Integer[] getDepList() {
		return depList;
	}









	/**
	 * @param depList the depList to set
	 */
	public void setDepList(Integer[] depList) {
		this.depList = depList;
	}









	/**
	 * @return the db_database
	 */
	public String getDb_database() {
		return db_database;
	}









	/**
	 * @param db_database the db_database to set
	 */
	public void setDb_database(String db_database) {
		this.db_database = db_database;
	}









	/**
	 * @return the compnyId
	 */
	public Integer getCompnyId() {
		return compnyId;
	}









	/**
	 * @param compnyId the compnyId to set
	 */
	public void setCompnyId(Integer compnyId) {
		this.compnyId = compnyId;
	}









	







	/**
	 * @return the is_hq
	 */
	public Boolean getIs_hq() {
		return is_hq;
	}








	/**
	 * @param is_hq the is_hq to set
	 */
	public void setIs_hq(Boolean is_hq) {
		this.is_hq = is_hq;
	}








	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}








	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}








	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}








	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}








	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}








	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}








	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}








	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}








	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}








	/**
	 * @return the is_synchable
	 */
	public Integer getIs_synchable() {
		return is_synchable;
	}








	/**
	 * @param is_synchable the is_synchable to set
	 */
	public void setIs_synchable(Integer is_synchable) {
		this.is_synchable = is_synchable;
	}








	/**
	 * @return the shopsExcelData
	 */
	public List<Shops> getShopsExcelData() {
		return shopsExcelData;
	}








	/**
	 * @param shopsExcelData the shopsExcelData to set
	 */
	public void setShopsExcelData(List<Shops> shopsExcelData) {
		this.shopsExcelData = shopsExcelData;
	}








	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}








	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}









	/**
	 * @return the cst_no
	 */
	public String getCst_no() {
		return cst_no;
	}









	/**
	 * @param cst_no the cst_no to set
	 */
	public void setCst_no(String cst_no) {
		this.cst_no = cst_no;
	}









	/**
	 * @return the is_customer
	 */
	public Integer getIs_customer() {
		return is_customer;
	}









	/**
	 * @param is_customer the is_customer to set
	 */
	public void setIs_customer(Integer is_customer) {
		this.is_customer = is_customer;
	}









	
	
}
