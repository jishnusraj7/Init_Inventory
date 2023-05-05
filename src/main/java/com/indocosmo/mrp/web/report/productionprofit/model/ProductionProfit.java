package  com.indocosmo.mrp.web.report.productionprofit.model;

import javax.persistence.Entity;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;


@Entity
public class ProductionProfit extends GeneralModelBase{
	
	private String startdate;
	
	private String enddate;

	/**
	 * @return the startdate
	 */
	public String getStartdate() {
		return startdate;
	}

	/**
	 * @param startdate the startdate to set
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	/**
	 * @return the enddate
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @param enddate the enddate to set
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	
	
}
