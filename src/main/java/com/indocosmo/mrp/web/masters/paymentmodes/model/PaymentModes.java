package com.indocosmo.mrp.web.masters.paymentmodes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "payment_modes")
public class PaymentModes extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	 
	@Column(name = "can_pay_by_cash")
	private Integer can_pay_by_cash;
	
	@Column(name = "can_pay_by_company")
	private Integer can_pay_by_company;
	
	@Column(name = "created_by")
	private Integer created_by ;

	@Column(name = "created_at")
	private String created_at ;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	

	@Column(name = "can_pay_by_vouchers")
	private Integer can_pay_by_vouchers;
	
	@Column(name = "can_pay_by_card")
	private Integer can_pay_by_card;
	
	@Column(name = "can_cash_refundable")
	private Integer can_cash_refundable;
	
	@Column(name = "can_card_refundable")
	private Integer can_card_refundable;
	
	@Column(name = "can_company_refundable")
	private Integer can_company_refundable;
	
	@Column(name = "can_voucher_refundable")
	private Integer can_voucher_refundable;
	
	@Column(name = "can_cash_round")
	private Integer can_cash_round;
	
	@Column(name = "can_company_round")
	private Integer can_company_round;
	
	@Column(name = "can_voucher_round")
	private Integer can_voucher_round;
	
	@Column(name = "can_card_round")
	private Integer can_card_round;
	
	
	
	


	@Column(name = "cash_account_no")
	private String cash_account_no;

	@Column(name = "cash_refund_account_no")
	private String cash_refund_account_no;

	/**
	 * @return the can_card_refundable
	 */
	public Integer getCan_card_refundable() {
		return can_card_refundable;
	}

	/**
	 * @return the can_card_round
	 */
	public Integer getCan_card_round() {
		return can_card_round;
	}

	/**
	 * @return the can_cash_refundable
	 */
	public Integer getCan_cash_refundable() {
		return can_cash_refundable;
	}

	/**
	 * @return the can_cash_round
	 */
	public Integer getCan_cash_round() {
		return can_cash_round;
	}

	/**
	 * @return the can_company_refundable
	 */
	public Integer getCan_company_refundable() {
		return can_company_refundable;
	}

	/**
	 * @return the can_company_round
	 */
	public Integer getCan_company_round() {
		return can_company_round;
	}

	/**
	 * @return the can_pay_by_card
	 */
	public Integer getCan_pay_by_card() {
		return can_pay_by_card;
	}

	/**
	 * @return the can_pay_by_cash
	 */
	public Integer getCan_pay_by_cash() {
		return can_pay_by_cash;
	}

	/**
	 * @return the can_pay_by_company
	 */
	public Integer getCan_pay_by_company() {
		return can_pay_by_company;
	}

	/**
	 * @return the can_pay_by_vouchers
	 */
	public Integer getCan_pay_by_vouchers() {
		return can_pay_by_vouchers;
	}

	/**
	 * @return the can_voucher_refundable
	 */
	public Integer getCan_voucher_refundable() {
		return can_voucher_refundable;
	}

	/**
	 * @return the can_voucher_round
	 */
	public Integer getCan_voucher_round() {
		return can_voucher_round;
	}

	/**
	 * @return the cash_account_no
	 */
	public String getCash_account_no() {
		return cash_account_no;
	}

	/**
	 * @return the cash_refund_account_no
	 */
	public String getCash_refund_account_no() {
		return cash_refund_account_no;
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
	 * @return the id
	 */
	public Integer getId() {
		return id;
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

	/**
	 * @param can_card_refundable the can_card_refundable to set
	 */
	public void setCan_card_refundable(Integer can_card_refundable) {
		this.can_card_refundable = can_card_refundable;
	}

	/**
	 * @param can_card_round the can_card_round to set
	 */
	public void setCan_card_round(Integer can_card_round) {
		this.can_card_round = can_card_round;
	}

	/**
	 * @param can_cash_refundable the can_cash_refundable to set
	 */
	public void setCan_cash_refundable(Integer can_cash_refundable) {
		this.can_cash_refundable = can_cash_refundable;
	}

	/**
	 * @param can_cash_round the can_cash_round to set
	 */
	public void setCan_cash_round(Integer can_cash_round) {
		this.can_cash_round = can_cash_round;
	}

	/**
	 * @param can_company_refundable the can_company_refundable to set
	 */
	public void setCan_company_refundable(Integer can_company_refundable) {
		this.can_company_refundable = can_company_refundable;
	}

	/**
	 * @param can_company_round the can_company_round to set
	 */
	public void setCan_company_round(Integer can_company_round) {
		this.can_company_round = can_company_round;
	}

	/**
	 * @param can_pay_by_card the can_pay_by_card to set
	 */
	public void setCan_pay_by_card(Integer can_pay_by_card) {
		this.can_pay_by_card = can_pay_by_card;
	}

	/**
	 * @param can_pay_by_cash the can_pay_by_cash to set
	 */
	public void setCan_pay_by_cash(Integer can_pay_by_cash) {
		this.can_pay_by_cash = can_pay_by_cash;
	}

	/**
	 * @param can_pay_by_company the can_pay_by_company to set
	 */
	public void setCan_pay_by_company(Integer can_pay_by_company) {
		this.can_pay_by_company = can_pay_by_company;
	}

	/**
	 * @param can_pay_by_vouchers the can_pay_by_vouchers to set
	 */
	public void setCan_pay_by_vouchers(Integer can_pay_by_vouchers) {
		this.can_pay_by_vouchers = can_pay_by_vouchers;
	}

	/**
	 * @param can_voucher_refundable the can_voucher_refundable to set
	 */
	public void setCan_voucher_refundable(Integer can_voucher_refundable) {
		this.can_voucher_refundable = can_voucher_refundable;
	}

	/**
	 * @param can_voucher_round the can_voucher_round to set
	 */
	public void setCan_voucher_round(Integer can_voucher_round) {
		this.can_voucher_round = can_voucher_round;
	}

	/**
	 * @param cash_account_no the cash_account_no to set
	 */
	public void setCash_account_no(String cash_account_no) {
		this.cash_account_no = cash_account_no;
	}

	/**
	 * @param cash_refund_account_no the cash_refund_account_no to set
	 */
	public void setCash_refund_account_no(String cash_refund_account_no) {
		this.cash_refund_account_no = cash_refund_account_no;
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
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="payment_modes", key="payment_modes")
	public void setId(Integer id) {
		this.id = id;
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

	
	
	
	
}
