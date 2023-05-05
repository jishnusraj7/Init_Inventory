package com.indocosmo.mrp.web.masters.users.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "users")

public class Users extends GeneralModelBase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	
	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "card_no")
	private Integer card_no;

	@Column(name = "updated_at")
	private String updated_at;

	@Column(name = "valid_from")
	private String valid_from;

	@Column(name = "valid_to")
	private String valid_to;
	
	@Column(name = "employee_id")
	private Integer employee_id;
	
	
	
	
	public Integer getEmployee_id() {
	
		return employee_id;
	}


	
	public void setEmployee_id(Integer employee_id) {
	
		this.employee_id = employee_id;
	}

	@Column(name = "user_group_id")
	private Integer userGroupId;
	
	@Column(name="password")
	private String password;

	@Column(name="name")
	private String name;

	@Column(name="code")
	private String code;

	@Column(name="lastlogin_date")
	private String lastLoginDate;

	@Transient
	private String loginDate;


	
	@Column(name="email")
	private String email;

	@Column(name="is_active")
	private Integer isActive;

	@Column(name="is_admin")
	private Integer isAdmin=0;

	@Transient
	private Integer err_msg;

	@Transient
	private Company company;

	@Transient
	private Integer cmpnyType=0;

	@Transient
	private Integer companyId;

	/**
	 * @return the card_no
	 */
	public Integer getCard_no() {
		return card_no;
	}


	/**
	 * @return the cmpnyType
	 */
	public Integer getCmpnyType() {
		return cmpnyType;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}
	
	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
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

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @return the err_msg
	 */
	public Integer getErr_msg() {
		return err_msg;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	
	
	/**
	 * @return the isActive
	 */
	public Integer getIsActive() {
		return isActive;
	}

	/**
	 * @return the isAdmin
	 */
	public Integer getIsAdmin() {
		return isAdmin;
	}
	
	/**
	 * @return the lastLoginDate
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @return the loginDate
	 */
	public String getLoginDate() {
		return loginDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}
	
	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
		return updated_by;
	}
	
	/*@Transient
	private String errUname;
	
	@Transient
	private String errpass;
	*/
	

	/**
	 * @return the errUname
	 */
	/*public String getErrUname() {
		return errUname;
	}

	*//**
	 * @param errUname the errUname to set
	 *//*
	public void setErrUname(String errUname) {
		this.errUname = errUname;
	}*/

	/**
	 * @return the errpass
	 */
	/*public String getErrpass() {
		return errpass;
	}

	*//**
	 * @param errpass the errpass to set
	 *//*
	public void setErrpass(String errpass) {
		this.errpass = errpass;
	}*/

	/**
	 * @return the userGroupId
	 */
	public Integer getUserGroupId() {
		return userGroupId;
	}

	/**
	 * @return the valid_from
	 */
	public String getValid_from() {
		return valid_from;
	}

	/**
	 * @return the valid_to
	 */
	public String getValid_to() {
		return valid_to;
	}

	/**
	 * @param card_no the card_no to set
	 */
	public void setCard_no(Integer card_no) {
		this.card_no = card_no;
	}

	/**
	 * @param cmpnyType the cmpnyType to set
	 */
	public void setCmpnyType(Integer cmpnyType) {
		this.cmpnyType = cmpnyType;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	 * @param err_msg the err_msg to set
	 */
	public void setErr_msg(Integer err_msg) {
		this.err_msg = err_msg;
	}

	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="users", key="users")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @param loginDate the loginDate to set
	 */
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}

	/**
	 * @param userGroupId the userGroupId to set
	 */
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}

	/**
	 * @param valid_from the valid_from to set
	 */
	public void setValid_from(String valid_from) {
		this.valid_from = valid_from;
	}

	/**
	 * @param valid_to the valid_to to set
	 */
	public void setValid_to(String valid_to) {
		this.valid_to = valid_to;
	}



	@Override
	public String toString() {
		return "Users [id=" + id + ", created_by=" + created_by + ", created_at=" + created_at + ", updated_by="
				+ updated_by + ", card_no=" + card_no + ", updated_at=" + updated_at + ", valid_from=" + valid_from
				+ ", valid_to=" + valid_to + ", employee_id=" + employee_id + ", userGroupId=" + userGroupId
				+ ", password=" + password + ", name=" + name + ", code=" + code + ", lastLoginDate=" + lastLoginDate
				+ ", loginDate=" + loginDate + ", email=" + email + ", isActive=" + isActive + ", isAdmin=" + isAdmin
				+ ", err_msg=" + err_msg + ", company=" + company + ", cmpnyType=" + cmpnyType + ", companyId="
				+ companyId + "]";
	}

	/**
	 * @return the companyId
	 */
	
	

}
