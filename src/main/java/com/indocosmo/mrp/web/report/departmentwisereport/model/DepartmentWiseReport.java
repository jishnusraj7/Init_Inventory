package com.indocosmo.mrp.web.report.departmentwisereport.model;
import java.util.Date;
import java.util.List;

/*
 * 
 * @gana 0311220
 */
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
import com.indocosmo.mrp.web.report.stockregisterreport.model.StockRegisterReport;
/*
 * 
 * @gana 031120
 */
@Entity
public class DepartmentWiseReport extends GeneralModelBase{

	private String stockItemId;
	private double openingStock;
	private double stockIn;
	private double stockOut;
	private int itemCategoryId;
	private int departmentId;
	private String stockItemName;
	
	@Transient
	private String stockDate;
	
	@Transient
	private List<DepartmentWiseReport> departmentWiseReport;
	
	@Transient
	private String item_category_name;
	
	@Transient
	private String departmentName;
	
	public List<DepartmentWiseReport> getDepartmentWiseReport() {
		return departmentWiseReport;
	}
	public void setDepartmentWiseReport(List<DepartmentWiseReport> departmentWiseReport) {
		this.departmentWiseReport = departmentWiseReport;
	}
	public double getOpeningStock() {
		return openingStock;
	}
	public void setOpeningStock(double openingStock) {
		this.openingStock = openingStock;
	}
	public double getStockIn() {
		return stockIn;
	}
	public void setStockIn(double stockIn) {
		this.stockIn = stockIn;
	}
	public double getStockOut() {
		return stockOut;
	}
	public void setStockOut(double stockOut) {
		this.stockOut = stockOut;
	}
	public String getStockItemId() {
		return stockItemId;
	}
	public void setStockItemId(String stockItemId) {
		this.stockItemId = stockItemId;
	}
	public int getItemCategoryId() {
		return itemCategoryId;
	}
	public void setItemCategoryId(int itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getStockDate() {
		return stockDate;
	}
	public void setStockDate(String stockDate) {
		this.stockDate = stockDate;
	}
	public String getStockItemName() {
		return stockItemName;
	}
	public void setStockItemName(String stockItemName) {
		this.stockItemName = stockItemName;
	}
	public String getItem_category_name() {
		return item_category_name;
	}
	public void setItem_category_name(String item_category_name) {
		this.item_category_name = item_category_name;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	
	
}
