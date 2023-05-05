package com.indocosmo.mrp.web.masters.creditcard.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;

@Entity
@Table(name = "bank_card_types")
public class CreditCard extends MasterModelBase{
	@Override
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="credit_card", key="credit_card")
	public void setId(Integer id) {
	
		super.setId(id);
		
		
	}
	
	@Column(name = "is_valid")
	private Integer is_valid;
	
	@Column(name = "is_refundable")
	private Integer is_refundable;
	
	
	@Column(name = "alternative_refund_method")
	private Integer alternative_refund_method;
	
	@Transient
	private List<CreditCard> itmExcelData;
	
	/**
	 * @return the itmExcelData
	 */
	public List<CreditCard> getItmExcelData() {
		return itmExcelData;
	}

	/**
	 * @param itmExcelData the itmExcelData to set
	 */
	public void setItmExcelData(List<CreditCard> itmExcelData) {
		this.itmExcelData = itmExcelData;
	}

	/**
	 * @return the is_valid
	 */
	public Integer getIs_valid() {
		return is_valid;
	}

	/**
	 * @return the is_refundable
	 */
	public Integer getIs_refundable() {
		return is_refundable;
	}

	/**
	 * @return the alternative_refund_method
	 */
	public Integer getAlternative_refund_method() {
		return alternative_refund_method;
	}

	/**
	 * @return the account_code
	 */
	public String getAccount_code() {
		return account_code;
	}

	/**
	 * @return the iin_prefix_range_from
	 */
	public Long getIin_prefix_range_from() {
		return iin_prefix_range_from;
	}

	/**
	 * @return the iin_prefix_range_to
	 */
	public Long getIin_prefix_range_to() {
		return iin_prefix_range_to;
	}

	/**
	 * @param is_valid the is_valid to set
	 */
	public void setIs_valid(Integer is_valid) {
		this.is_valid = is_valid;
	}

	/**
	 * @param is_refundable the is_refundable to set
	 */
	public void setIs_refundable(Integer is_refundable) {
		this.is_refundable = is_refundable;
	}

	/**
	 * @param alternative_refund_method the alternative_refund_method to set
	 */
	public void setAlternative_refund_method(Integer alternative_refund_method) {
		this.alternative_refund_method = alternative_refund_method;
	}

	/**
	 * @param account_code the account_code to set
	 */
	public void setAccount_code(String account_code) {
		this.account_code = account_code;
	}

	/**
	 * @param iin_prefix_range_from the iin_prefix_range_from to set
	 */
	public void setIin_prefix_range_from(Long iin_prefix_range_from) {
		this.iin_prefix_range_from = iin_prefix_range_from;
	}

	/**
	 * @param iin_prefix_range_to the iin_prefix_range_to to set
	 */
	public void setIin_prefix_range_to(Long iin_prefix_range_to) {
		this.iin_prefix_range_to = iin_prefix_range_to;
	}

	@Column(name = "account_code")
	private String account_code;
	
	
	@Column(name = "iin_prefix_range_from")
	private Long iin_prefix_range_from;
	
	@Column(name = "iin_prefix_range_to")
	private Long iin_prefix_range_to;
	
	
	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;

	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
		return created_by;
	}

	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}

	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
		return updated_by;
	}

	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}

	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}

	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
}
