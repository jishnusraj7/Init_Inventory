package  com.indocosmo.mrp.web.report.bomratecomparison.model;

import javax.persistence.Entity;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;


@Entity
public class BomRateComparison extends GeneralModelBase{
	
	private String pdfExcel;
	public String itemcategory;
	public String stockitem;
	
	public String getPdfExcel() {
		return pdfExcel;
	}
	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}
	public String getItemcategory() {
		return itemcategory;
	}
	public void setItemcategory(String itemcategory) {
		this.itemcategory = itemcategory;
	}
	public String getStockitem() {
		return stockitem;
	}
	public void setStockitem(String stockitem) {
		this.stockitem = stockitem;
	}
	
}
