package com.indocosmo.mrp.web.masters.supplier.model;

import java.sql.Timestamp;
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
@Table(name = "mrp_supplier")
public class Supplier extends GeneralModelBase{




	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Transient
	private List<Supplier> supExcelData;

	@Transient
	private String ColoumnList;
	

	@Transient
	private String OrderList;

	@Transient
	private String ReportName;

	public String getReportName() {
		return ReportName;
	}

	public void setReportName(String reportName) {
		ReportName = reportName;
	}



	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;
	
	
	@Column(name = "abbreviation")
	private String abbreviation;



	
	@Column(name = "address")
	private String address;



	
	@Column(name = "city")
	private String city;



	
	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;
	
	
	@Column(name = "pin_code")
	private String pin_code;

	@Column(name = "tin_no")
	private String tin_no;	

	@Column(name = "contact_person")
	private String contact_person;

	@Column(name = "contact_no")
	private String contact_no;

	@Column(name = "contact_email")
	private String contact_email;

	@Column(name = "back_office_ref")
	private String back_office_ref;

	@Column(name = "comments")
	private String comments;

	@Column(name = "is_active")
	private Integer is_active;

	@Column(name = "is_deleted")
	private Integer is_delete=0;

	@Column(name="last_sync_at")
	private Timestamp last_sync_at;

	/**
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the back_office_ref
	 */
	public String getBack_office_ref() {
		return back_office_ref;
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

	public String getColoumnList() {
	
		return ColoumnList;
	}
	
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}


	/**
	 * @return the contact_email
	 */
	public String getContact_email() {
		return contact_email;
	}



	/**
	 * @return the contact_no
	 */
	public String getContact_no() {
		return contact_no;
	}



	/**
	 * @return the contact_person
	 */
	public String getContact_person() {
		return contact_person;
	}



	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}






	/**
	 * @return the is_active
	 */
	public Integer getIs_active() {
		return is_active;
	}



	/**
	 * @return the is_delete
	 */
	public Integer getIs_delete() {
		return is_delete;
	}



	public Timestamp getLast_sync_at() {
		return last_sync_at;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}





	public String getOrderList() {
	
		return OrderList;
	}


	/**
	 * @return the pin_code
	 */
	public String getPin_code() {
		return pin_code;
	}





	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}


	public List<Supplier> getSupExcelData() {
		return supExcelData;
	}


	/**
	 * @return the tin_no
	 */
	public String getTin_no() {
		return tin_no;
	}

	/**
	 * @param abbreviation the abbreviation to set
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * @param back_office_ref the back_office_ref to set
	 */
	public void setBack_office_ref(String back_office_ref) {
		this.back_office_ref = back_office_ref;
	}


	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	public void setColoumnList(String coloumnList) {
	
		ColoumnList = coloumnList;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @param contact_email the contact_email to set
	 */
	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	/**
	 * @param contact_no the contact_no to set
	 */
	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}


	/**
	 * @param contact_person the contact_person to set
	 */
	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}


	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="supplier", key="supplier")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param is_active the is_active to set
	 */
	public void setIs_active(Integer is_active) {
		this.is_active = is_active;
	}


	/**
	 * @param is_delete the is_delete to set
	 */
	public void setIs_delete(Integer is_delete) {
		this.is_delete = is_delete;
	}

	public void setLast_sync_at(Timestamp last_sync_at) {
		this.last_sync_at = last_sync_at;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	public void setOrderList(String orderList) {
	
		OrderList = orderList;
	}


	/**
	 * @param pin_code the pin_code to set
	 */
	public void setPin_code(String pin_code) {
		this.pin_code = pin_code;
	}


	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}



	public void setSupExcelData(List<Supplier> supExcelData) {
		this.supExcelData = supExcelData;
	}



	/**
	 * @param tin_no the tin_no to set
	 */
	public void setTin_no(String tin_no) {
		this.tin_no = tin_no;
	}


}