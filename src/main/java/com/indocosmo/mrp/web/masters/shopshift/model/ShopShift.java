package com.indocosmo.mrp.web.masters.shopshift.model;

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
@Table(name = "shop_shifts")
public class ShopShift extends MasterModelBase {


	
	@Transient
	private List<ShopShift> shopshiftExcelData;
	
	
	


	public List<ShopShift> getShopshiftExcelData() {
		return shopshiftExcelData;
	}
	public void setShopshiftExcelData(List<ShopShift> shopshiftExcelData) {
		this.shopshiftExcelData = shopshiftExcelData;
	}
	@Column(name = "allow_unscheduled_unpaid_breaks")
	private Integer allow_unscheduled_unpaid_breaks;

	@Column(name = "allowed_time_after_end")
	private String allowed_time_after_end;


	@Column(name = "allowed_time_after_start")
	private String allowed_time_after_start;

	@Column(name = "allowed_time_before_end")
	private String allowed_time_before_end;

	@Column(name = "allowed_time_before_start")
	private String allowed_time_before_start;
	
	
	
	@Column(name = "created_at")
	private String created_at;
	
	
	@Column(name = "created_by")
	private Integer created_by;
	@Column(name = "end_time")
	private String end_time;
	@Column(name = "interval_end_time")
	private String interval_end_time;
	@Column(name = "interval_is_payable")
	private Integer interval_is_payable;
	@Column(name = "interval_start_time")
	private String interval_start_time;
	@Column(name = "is_active")
	private Integer is_active;

	@Column(name = "is_synchable")
	private Integer is_synchable;
	@Column(name = "minimum_overtime_limit")
	private String minimum_overtime_limit;
	@Column(name = "overtime_is_payable")
	private Integer overtime_is_payable;
	@Column(name = "publish_status")
	private Integer publish_status=0;
	@Column(name = "shift_type")
	private Integer shift_type;
	@Column(name = "start_time")
	private String start_time;
	@Column(name = "total_hours")
	private String total_hours;
	@Column(name = "updated_at")
	private String updated_at;
	@Column(name = "updated_by")
	private Integer updated_by;
	public Integer getAllow_unscheduled_unpaid_breaks() {
		return allow_unscheduled_unpaid_breaks;
	}
	public String getAllowed_time_after_end() {
		return allowed_time_after_end;
	}
	public String getAllowed_time_after_start() {
		return allowed_time_after_start;
	}
	public String getAllowed_time_before_end() {
		return allowed_time_before_end;
	}
	public String getAllowed_time_before_start() {
		return allowed_time_before_start;
	}
	public String getCreated_at() {
		return created_at;
	}
	public Integer getCreated_by() {
		return created_by;
	}
	public String getEnd_time() {
		return end_time;
	}
	public String getInterval_end_time() {
		return interval_end_time;
	}
	public Integer getInterval_is_payable() {
		return interval_is_payable;
	}
	public String getInterval_start_time() {
		return interval_start_time;
	}
	public Integer getIs_active() {
		return is_active;
	}
	
	public Integer getIs_synchable() {
		return is_synchable;
	}
	public String getMinimum_overtime_limit() {
		return minimum_overtime_limit;
	}
	public Integer getOvertime_is_payable() {
		return overtime_is_payable;
	}
	public Integer getPublish_status() {
		return publish_status;
	}
	public Integer getShift_type() {
		return shift_type;
	}
	public String getStart_time() {
		return start_time;
	}
	public String getTotal_hours() {
		return total_hours;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public Integer getUpdated_by() {
		return updated_by;
	}
	public void setAllow_unscheduled_unpaid_breaks(
			Integer allow_unscheduled_unpaid_breaks) {
		this.allow_unscheduled_unpaid_breaks = allow_unscheduled_unpaid_breaks;
	}
	public void setAllowed_time_after_end(String allowed_time_after_end) {
		this.allowed_time_after_end = allowed_time_after_end;
	}
	public void setAllowed_time_after_start(String allowed_time_after_start) {
		this.allowed_time_after_start = allowed_time_after_start;
	}
	public void setAllowed_time_before_end(String allowed_time_before_end) {
		this.allowed_time_before_end = allowed_time_before_end;
	}
	public void setAllowed_time_before_start(String allowed_time_before_start) {
		this.allowed_time_before_start = allowed_time_before_start;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	@Override
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "shopshift", key = "shopshift")
	public void setId(Integer id) {
		super.setId(id);
	}

	public void setInterval_end_time(String interval_end_time) {
		this.interval_end_time = interval_end_time;
	}


	public void setInterval_is_payable(Integer interval_is_payable) {
		this.interval_is_payable = interval_is_payable;
	}

	public void setInterval_start_time(String interval_start_time) {
		this.interval_start_time = interval_start_time;
	}

	public void setIs_active(Integer is_active) {
		this.is_active = is_active;
	}

	public void setIs_synchable(Integer is_synchable) {
		this.is_synchable = is_synchable;
	}
	public void setMinimum_overtime_limit(String minimum_overtime_limit) {
		this.minimum_overtime_limit = minimum_overtime_limit;
	}
	public void setOvertime_is_payable(Integer overtime_is_payable) {
		this.overtime_is_payable = overtime_is_payable;
	}
	public void setPublish_status(Integer publish_status) {
		this.publish_status = publish_status;
	}
	public void setShift_type(Integer shift_type) {
		this.shift_type = shift_type;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public void setTotal_hours(String total_hours) {
		this.total_hours = total_hours;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}






}
