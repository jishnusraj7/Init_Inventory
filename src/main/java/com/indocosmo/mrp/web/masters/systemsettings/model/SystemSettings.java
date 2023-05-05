package com.indocosmo.mrp.web.masters.systemsettings.model;

import java.io.Serializable;

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
@Table(name = "system_params")
public class SystemSettings extends GeneralModelBase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Pk
	@Id(generationType = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Transient
	private Integer default_currency_id;
	
	@Column(name = "date_format")
	private String date_format;
	
	@Column(name = "date_separator")
	private String date_separator;
	
	@Column(name = "time_format")
	private String time_format;
	
	@Column(name = "time_zone")
	private String time_zone;
	
	@Column(name = "week_start")
	private Integer week_start;
	
	@Column(name = "decimal_places")
	private Integer decimal_places;
	
	@Column(name = "financial_month")
	private Integer financial_month;
	
	@Column(name = "smtp_server")
	private String smtp_server;
	
	@Column(name = "smtp_port")
	private String smtp_port;
	
	@Column(name = "smtp_password")
	private String smtp_password;
	
	@Column(name = "smtp_mailid")
	private String smtp_mailid;
	
	@Column(name = "sms_web_service")
	private String sms_web_service;
	
	@Column(name = "sms_userid")
	private String sms_userid;
	
	@Column(name = "sms_password")
	private String sms_password;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Column(name = "is_direct_stock_entry")
	private Integer is_direct_stock_entry;
	
	@Column(name = "default_customer_type")
	private String default_customer_type;
	
	/**
	 * @return the is_direct_stock_entry
	 */
	public Integer getIs_direct_stock_entry() {
	
		return is_direct_stock_entry;
	}
	
	/**
	 * @return the default_customer_type
	 */
	public String getDefault_customer_type() {
	
		return default_customer_type;
	}
	
	/**
	 * @param is_direct_stock_entry
	 *            the is_direct_stock_entry to set
	 */
	public void setIs_direct_stock_entry(Integer is_direct_stock_entry) {
	
		this.is_direct_stock_entry = is_direct_stock_entry;
	}
	
	/**
	 * @param default_customer_type
	 *            the default_customer_type to set
	 */
	public void setDefault_customer_type(String default_customer_type) {
	
		this.default_customer_type = default_customer_type;
	}
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
	
		return serialVersionUID;
	}
	
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
	 * @param created_by
	 *            the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
	
		this.created_by = created_by;
	}
	
	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreated_at(String created_at) {
	
		this.created_at = created_at;
	}
	
	/**
	 * @param updated_by
	 *            the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
	
		this.updated_by = updated_by;
	}
	
	/**
	 * @param updated_at
	 *            the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
	
		this.updated_at = updated_at;
	}
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	/**
	 * @return the smtp_server
	 */
	public String getSmtp_server() {
	
		return smtp_server;
	}
	
	/**
	 * @param smtp_server
	 *            the smtp_server to set
	 */
	public void setSmtp_server(String smtp_server) {
	
		this.smtp_server = smtp_server;
	}
	
	/**
	 * @return the smtp_port
	 */
	public String getSmtp_port() {
	
		return smtp_port;
	}
	
	/**
	 * @param smtp_port
	 *            the smtp_port to set
	 */
	public void setSmtp_port(String smtp_port) {
	
		this.smtp_port = smtp_port;
	}
	
	/**
	 * @return the smtp_password
	 */
	public String getSmtp_password() {
	
		return smtp_password;
	}
	
	/**
	 * @param smtp_password
	 *            the smtp_password to set
	 */
	public void setSmtp_password(String smtp_password) {
	
		this.smtp_password = smtp_password;
	}
	
	/**
	 * @return the smtp_mailid
	 */
	public String getSmtp_mailid() {
	
		return smtp_mailid;
	}
	
	/**
	 * @param smtp_mailid
	 *            the smtp_mailid to set
	 */
	public void setSmtp_mailid(String smtp_mailid) {
	
		this.smtp_mailid = smtp_mailid;
	}
	
	/**
	 * @return the sms_web_service
	 */
	public String getSms_web_service() {
	
		return sms_web_service;
	}
	
	/**
	 * @param sms_web_service
	 *            the sms_web_service to set
	 */
	public void setSms_web_service(String sms_web_service) {
	
		this.sms_web_service = sms_web_service;
	}
	
	/**
	 * @return the sms_userid
	 */
	public String getSms_userid() {
	
		return sms_userid;
	}
	
	/**
	 * @param sms_userid
	 *            the sms_userid to set
	 */
	public void setSms_userid(String sms_userid) {
	
		this.sms_userid = sms_userid;
	}
	
	/**
	 * @return the sms_password
	 */
	public String getSms_password() {
	
		return sms_password;
	}
	
	/**
	 * @param sms_password
	 *            the sms_password to set
	 */
	public void setSms_password(String sms_password) {
	
		this.sms_password = sms_password;
	}
	
	@Transient
	private String currencySymbol;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
	
		return id;
	}
	
	/**
	 * @param id
	 *            the id to set
	 */
	
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "system_params", key = "system_params")
	public void setId(Integer id) {
	
		this.id = id;
	}
	
	/**
	 * @return the default_currency_id
	 */
	public Integer getDefault_currency_id() {
	
		return default_currency_id;
	}
	
	/**
	 * @param default_currency_id
	 *            the default_currency_id to set
	 */
	public void setDefault_currency_id(Integer default_currency_id) {
	
		this.default_currency_id = default_currency_id;
	}
	
	/**
	 * @return the date_separator
	 */
	public String getDate_separator() {
	
		return date_separator;
	}
	
	/**
	 * @param date_separator
	 *            the date_separator to set
	 */
	public void setDate_separator(String date_separator) {
	
		this.date_separator = date_separator;
	}
	
	/**
	 * @return the date_format
	 */
	public String getDate_format() {
	
		return date_format;
	}
	
	/**
	 * @param date_format
	 *            the date_format to set
	 */
	public void setDate_format(String date_format) {
	
		this.date_format = date_format;
	}
	
	/**
	 * @return the time_format
	 */
	public String getTime_format() {
	
		return time_format;
	}
	
	/**
	 * @param time_format
	 *            the time_format to set
	 */
	public void setTime_format(String time_format) {
	
		this.time_format = time_format;
	}
	
	/**
	 * @return the time_zone
	 */
	public String getTime_zone() {
	
		return time_zone;
	}
	
	/**
	 * @param time_zone
	 *            the time_zone to set
	 */
	public void setTime_zone(String time_zone) {
	
		this.time_zone = time_zone;
	}
	
	/**
	 * @return the week_start
	 */
	public Integer getWeek_start() {
	
		return week_start;
	}
	
	/**
	 * @param week_start
	 *            the week_start to set
	 */
	public void setWeek_start(Integer week_start) {
	
		this.week_start = week_start;
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
	 * @return the financial_month
	 */
	public Integer getFinancial_month() {
	
		return financial_month;
	}
	
	/**
	 * @param financial_month
	 *            the financial_month to set
	 */
	public void setFinancial_month(Integer financial_month) {
	
		this.financial_month = financial_month;
	}
	
	/**
	 * @return the currencySymbol
	 */
	public String getCurrencySymbol() {
	
		return currencySymbol;
	}
	
	/**
	 * @param currencySymbol
	 *            the currencySymbol to set
	 */
	public void setCurrencySymbol(String currencySymbol) {
	
		this.currencySymbol = currencySymbol;
	}
	
}
