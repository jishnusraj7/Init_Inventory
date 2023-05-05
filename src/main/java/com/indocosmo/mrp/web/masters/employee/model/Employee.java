package com.indocosmo.mrp.web.masters.employee.model;

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
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;
import com.indocosmo.mrp.web.masters.currencydetails.model.CurrencyDetails;

@Entity
@Table(name = "employees")
public class Employee  extends GeneralModelBase {
	
	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="employee", key="employee")
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	@Transient
	private List<Employee> employeeExcelData;
	
	
	public List<Employee> getEmployeeExcelData() {
		return employeeExcelData;
	}
	public void setEmployeeExcelData(List<Employee> employeeExcelData) {
		this.employeeExcelData = employeeExcelData;
	}



	@Column(name = "address")
	private String address;
	
	@Column(name = "card_no")
	private String card_no;
	
	@Column(name = "code")
	private String code;
	
	
	@Column(name = "cost_per_hour")
	private Double cost_per_hour;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Column(name = "department_id")
	private Integer department_id;
	
	@Column(name = "dob")
	private String dob;
	
	@Column(name = "doj")
	private String doj;
	
	
	@Column(name = "email")
	private String email;
	
	


	@Column(name = "employee_category_id")
	private Integer employee_category_id;
	
	@Column(name = "f_name")
	private String f_name;
	
	
	@Column(name = "fax")
	private String fax;

	@Column(name = "is_deleted")
	private Integer is_deleted=0;
	@Column(name = "is_synchable")
	private Integer is_synchable=1;
	@Column(name = "l_name")
	private String l_name;
	@Column(name = "loc_address")
	private String loc_address;
	@Column(name = "loc_country")
	private String loc_country;
	@Column(name = "loc_fax")
	private String loc_fax;
	@Column(name = "loc_phone")
	private String loc_phone;
	@Column(name = "loc_zip_code")
	private String loc_zip_code;
	@Column(name = "m_name")
	private String m_name;
	@Column(name = "over_time_pay_rate")
	private Double over_time_pay_rate;
	@Column(name = "phone")
	private String phone;
	@Column(name = "publish_status")
	private Integer publish_status=0;
	@Column(name = "sex")	
	private String sex;
	@Column(name = "status")
	private Integer status;
	@Column(name = "updated_at")
	private String updated_at;
	@Column(name = "updated_by")
	private Integer updated_by;
	@Column(name = "wage_type")
	private Integer wage_type;
	@Column(name = "work_permit")
	private String work_permit;
	@Column(name = "zip_code")
	private String zip_code;
	public String getAddress() {
		return address;
	}
	public String getCard_no() {
		return card_no;
	}
	public String getCode() {
		return code;
	}
	public Double getCost_per_hour() {
		return cost_per_hour;
	}
	public String getCountry() {
		return country;
	}
	public String getCreated_at() {
		return created_at;
	}
	public Integer getCreated_by() {
		return created_by;
	}
	public Integer getDepartment_id() {
		return department_id;
	}
	public String getDob() {
		return dob;
	}
	public String getDoj() {
		return doj;
	}
	public String getEmail() {
		return email;
	}
	public Integer getEmployee_category_id() {
		return employee_category_id;
	}
	public String getF_name() {
		return f_name;
	}
	public String getFax() {
		return fax;
	}
	public Integer getId() {
		return id;
	}
	public Integer getIs_deleted() {
		return is_deleted;
	}
	public Integer getIs_synchable() {
		return is_synchable;
	}
	public String getL_name() {
		return l_name;
	}
	public String getLoc_address() {
		return loc_address;
	}
	public String getLoc_country() {
		return loc_country;
	}
	public String getLoc_fax() {
		return loc_fax;
	}
	public String getLoc_phone() {
		return loc_phone;
	}
	public String getLoc_zip_code() {
		return loc_zip_code;
	}
	public String getM_name() {
		return m_name;
	}
	public Double getOver_time_pay_rate() {
		return over_time_pay_rate;
	}
	public String getPhone() {
		return phone;
	}
	public Integer getPublish_status() {
		return publish_status;
	}
	public String getSex() {
		return sex;
	}
	public Integer getStatus() {
		return status;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public Integer getUpdated_by() {
		return updated_by;
	}
	public Integer getWage_type() {
		return wage_type;
	}
	public String getWork_permit() {
		return work_permit;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setCost_per_hour(Double cost_per_hour) {
		this.cost_per_hour = cost_per_hour;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}
	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setEmployee_category_id(Integer employee_category_id) {
		this.employee_category_id = employee_category_id;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	
	
	
	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}
	
	public void setIs_synchable(Integer is_synchable) {
		this.is_synchable = is_synchable;
	}
	
	public void setL_name(String l_name) {
		this.l_name = l_name;
	}
	
	public void setLoc_address(String loc_address) {
		this.loc_address = loc_address;
	}
	
	public void setLoc_country(String loc_country) {
		this.loc_country = loc_country;
	}
	
	public void setLoc_fax(String loc_fax) {
		this.loc_fax = loc_fax;
	}
	
	public void setLoc_phone(String loc_phone) {
		this.loc_phone = loc_phone;
	}
	
	public void setLoc_zip_code(String loc_zip_code) {
		this.loc_zip_code = loc_zip_code;
	}
	
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	
	public void setOver_time_pay_rate(Double over_time_pay_rate) {
		this.over_time_pay_rate = over_time_pay_rate;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setPublish_status(Integer publish_status) {
		this.publish_status = publish_status;
	}
	
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}
	
	public void setWage_type(Integer wage_type) {
		this.wage_type = wage_type;
	}
	
	public void setWork_permit(String work_permit) {
		this.work_permit = work_permit;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	
	
	

	
	
	
}
