package com.indocosmo.mrp.web.masters.currencydetails.model;

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
@Table(name = "currencies")
public class CurrencyDetails extends GeneralModelBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.model.MasterModelBase#setId(java.lang
	 * .Integer)
	 */
	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="currency", key="currency")
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "symbol")
	private String symbol;

	
	@Transient
	private List<CurrencyDetails> currencyExcelData;
	
	
	public List<CurrencyDetails> getCurrencyExcelData() {
		return currencyExcelData;
	}

	public void setCurrencyExcelData(List<CurrencyDetails> currencyExcelData) {
		this.currencyExcelData = currencyExcelData;
	}

	@Column(name = "fraction_name")
	private String fraction_name;

	@Column(name = "fraction_symbol")
	private String fraction_symbol;

	@Column(name = "decimal_places")
	private Integer decimal_places = 0;

	@Column(name = "exchange_rate")
	private Double exchangeRate;

	@Column(name = "exchange_rate_at")
	private String exchangeRateAt;

	@Column(name = "rounding_id")
	private Integer rounding_id = 0;

	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;

	@Column(name = "updated_at")
	private String updated_at;
	
	
	@Column(name = "is_base_currency")
	private Integer is_base_currency;



	/**
	 * @return the is_base_currency
	 */
	public Integer getIs_base_currency() {
		return is_base_currency;
	}

	/**
	 * @param is_base_currency the is_base_currency to set
	 */
	public void setIs_base_currency(Integer is_base_currency) {
		this.is_base_currency = is_base_currency;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the fraction_name
	 */
	public String getFraction_name() {
		return fraction_name;
	}

	/**
	 * @param fraction_name
	 *            the fraction_name to set
	 */
	public void setFraction_name(String fraction_name) {
		this.fraction_name = fraction_name;
	}

	/**
	 * @return the fraction_symbol
	 */
	public String getFraction_symbol() {
		return fraction_symbol;
	}

	/**
	 * @param fraction_symbol
	 *            the fraction_symbol to set
	 */
	public void setFraction_symbol(String fraction_symbol) {
		this.fraction_symbol = fraction_symbol;
	}

	/**
	 * @return the decimal_places
	 */
	public Integer getDecimal_places() {
		return decimal_places;
	}

	/**
	 * @param decimal_places
	 *            the decimal_places to set
	 */
	public void setDecimal_places(Integer decimal_places) {
		this.decimal_places = decimal_places;
	}

	/**
	 * @return the exchangeRate
	 */
	public Double getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param exchangeRate
	 *            the exchangeRate to set
	 */
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * @return the exchangeRateAt
	 */
	public String getExchangeRateAt() {
		return exchangeRateAt;
	}

	/**
	 * @param exchangeRateAt
	 *            the exchangeRateAt to set
	 */
	public void setExchangeRateAt(String exchangeRateAt) {
		this.exchangeRateAt = exchangeRateAt;
	}

	/**
	 * @return the rounding_id
	 */
	public Integer getRounding_id() {
		return rounding_id;
	}

	/**
	 * @param rounding_id
	 *            the rounding_id to set
	 */
	public void setRounding_id(Integer rounding_id) {
		this.rounding_id = rounding_id;
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
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	
}
