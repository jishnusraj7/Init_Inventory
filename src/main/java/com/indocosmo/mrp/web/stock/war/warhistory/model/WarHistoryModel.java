package com.indocosmo.mrp.web.stock.war.warhistory.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

/**
 * @author Udhayan G
 *
 * Nov 1, 2021
 */

@Entity
@Table(name ="mrp_dept_stock_item_history")
public class WarHistoryModel extends GeneralModelBase{
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "dept_stock_item_history_id")
	private Integer deptStockHistoryId;
	
	@Column(name = "dept_stock_item_id")
	private Integer deptStockId;
	
	@Column( name ="dept_id")
	private Integer deptId;
	
	@Column( name ="stock_id")
	private Integer stockId;
	
	@Column( name ="previous_rate")
	private Double previousRate = 0.00;
	
	@Column( name ="previous_qty")
	private Double previousQty = 0.00;
	
	@Column(name = "in_rate")
	private Double inRate = 0.00;
	
	@Column(name = "in_qty")
	private Double inQty = 0.00;
	
	@Column(name = "war_rate")
	private Double warRate = 0.00;
	
	@Column(name = "created_date")
	private String createdDate;
	
	@Column(name = "updated_date")
	private String updatedDdate;
	
	@Column(name = "is_deleted")
	private int isDeleted;

	public Integer getDeptStockId() {
		return deptStockId;
	}

	public void setDeptStockId(Integer deptStockId) {
		this.deptStockId = deptStockId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Double getPreviousRate() {
		return previousRate;
	}

	public void setPreviousRate(Double previousRate) {
		this.previousRate = previousRate;
	}

	public Double getPreviousQty() {
		return previousQty;
	}

	public void setPreviousQty(Double previousQty) {
		this.previousQty = previousQty;
	}

	public Double getInRate() {
		return inRate;
	}

	public void setInRate(Double inRate) {
		this.inRate = inRate;
	}

	public Double getInQty() {
		return inQty;
	}

	public void setInQty(Double inQty) {
		this.inQty = inQty;
	}

	public Double getWarRate() {
		return warRate;
	}

	public void setWarRate(Double warRate) {
		this.warRate = warRate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDdate() {
		return updatedDdate;
	}

	public void setUpdatedDdate(String updatedDdate) {
		this.updatedDdate = updatedDdate;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getDeptStockHistoryId() {
		return deptStockHistoryId;
	}

	public void setDeptStockHistoryId(Integer deptStockHistoryId) {
		this.deptStockHistoryId = deptStockHistoryId;
	}

	@Override
	public String toString() {
		return "WarHistoryModel [deptStockHistoryId=" + deptStockHistoryId + ", deptStockId=" + deptStockId
				+ ", deptId=" + deptId + ", stockId=" + stockId + ", previousRate=" + previousRate + ", previousQty="
				+ previousQty + ", inRate=" + inRate + ", inQty=" + inQty + ", warRate=" + warRate + ", createdDate="
				+ createdDate + ", updatedDdate=" + updatedDdate + ", isDeleted=" + isDeleted + ", getDeptStockId()="
				+ getDeptStockId() + ", getDeptId()=" + getDeptId() + ", getStockId()=" + getStockId()
				+ ", getPreviousRate()=" + getPreviousRate() + ", getPreviousQty()=" + getPreviousQty()
				+ ", getInRate()=" + getInRate() + ", getInQty()=" + getInQty() + ", getWarRate()=" + getWarRate()
				+ ", getCreatedDate()=" + getCreatedDate() + ", getUpdatedDdate()=" + getUpdatedDdate()
				+ ", getIsDeleted()=" + getIsDeleted() + ", getDeptStockHistoryId()=" + getDeptStockHistoryId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
