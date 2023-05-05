package com.indocosmo.mrp.web.masters.billparameters.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "bill_params")
public class BillParameters extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	 
	@Column(name = "show_tax_summary")
	private Integer show_tax_summary;
	
	@Column(name = "show_item_tax")
	private Integer show_item_tax=0;
	
	@Column(name = "created_by")
	private Integer created_by ;

	@Column(name = "created_at")
	private String created_at ;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	/*@Column(name="last_sync_at")
	private Timestamp last_sync_at;*/

	@Column(name = "bill_hdr1")
	private String bill_hdr1;

	@Column(name = "bill_hdr2")
	private String bill_hdr2;

	@Column(name = "bill_hdr3")
	private String bill_hdr3;

	@Column(name = "bill_hdr4")
	private String bill_hdr4;

	@Column(name = "bill_hdr5")
	private String bill_hdr5;

	@Column(name = "bill_hdr6")
	private String bill_hdr6;

	@Column(name = "bill_hdr7")
	private String bill_hdr7;

	@Column(name = "bill_hdr8")
	private String bill_hdr8;

	@Column(name = "bill_hdr9")
	private String bill_hdr9;

	@Column(name = "bill_hdr10")
	private String bill_hdr10;

	@Column(name = "bill_footer1")
	private String bill_footer1;

	@Column(name = "bill_footer2")
	private String bill_footer2;

	@Column(name = "bill_footer3")
	private String bill_footer3;

	@Column(name = "bill_footer4")
	private String bill_footer4;

	@Column(name = "bill_footer5")
	private String bill_footer5;

	@Column(name = "bill_footer6")
	private String bill_footer6;

	@Column(name = "bill_footer7")
	private String bill_footer7;

	@Column(name = "bill_footer8")
	private String bill_footer8;

	@Column(name = "bill_footer9")
	private String bill_footer9;

	@Column(name = "bill_footer10")
	private String bill_footer10;

	/**
	 * @return the bill_footer1
	 */
	public String getBill_footer1() {
		return bill_footer1;
	}

	/**
	 * @return the bill_footer10
	 */
	public String getBill_footer10() {
		return bill_footer10;
	}

	/**
	 * @return the bill_footer2
	 */
	public String getBill_footer2() {
		return bill_footer2;
	}

	/**
	 * @return the bill_footer3
	 */
	public String getBill_footer3() {
		return bill_footer3;
	}

	/**
	 * @return the bill_footer4
	 */
	public String getBill_footer4() {
		return bill_footer4;
	}

	/**
	 * @return the bill_footer5
	 */
	public String getBill_footer5() {
		return bill_footer5;
	}

	/**
	 * @return the bill_footer6
	 */
	public String getBill_footer6() {
		return bill_footer6;
	}

	/**
	 * @return the bill_footer7
	 */
	public String getBill_footer7() {
		return bill_footer7;
	}

	/**
	 * @return the bill_footer8
	 */
	public String getBill_footer8() {
		return bill_footer8;
	}

	/**
	 * @return the bill_footer9
	 */
	public String getBill_footer9() {
		return bill_footer9;
	}

	/**
	 * @return the bill_hdr1
	 */
	public String getBill_hdr1() {
		return bill_hdr1;
	}

	/**
	 * @return the bill_hdr10
	 */
	public String getBill_hdr10() {
		return bill_hdr10;
	}

	/**
	 * @return the bill_hdr2
	 */
	public String getBill_hdr2() {
		return bill_hdr2;
	}

	/**
	 * @return the bill_hdr3
	 */
	public String getBill_hdr3() {
		return bill_hdr3;
	}

	/**
	 * @return the bill_hdr4
	 */
	public String getBill_hdr4() {
		return bill_hdr4;
	}

	/**
	 * @return the bill_hdr5
	 */
	public String getBill_hdr5() {
		return bill_hdr5;
	}

	/**
	 * @return the bill_hdr6
	 */
	public String getBill_hdr6() {
		return bill_hdr6;
	}

	/**
	 * @return the bill_hdr7
	 */
	public String getBill_hdr7() {
		return bill_hdr7;
	}

	/**
	 * @return the bill_hdr8
	 */
	public String getBill_hdr8() {
		return bill_hdr8;
	}

	/**
	 * @return the bill_hdr9
	 */
	public String getBill_hdr9() {
		return bill_hdr9;
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
	 * @return the show_item_tax
	 */
	public Integer getShow_item_tax() {
		return show_item_tax;
	}

	/**
	 * @return the show_tax_summary
	 */
	public Integer getShow_tax_summary() {
		return show_tax_summary;
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
	 * @param bill_footer1 the bill_footer1 to set
	 */
	public void setBill_footer1(String bill_footer1) {
		this.bill_footer1 = bill_footer1;
	}

	/**
	 * @param bill_footer10 the bill_footer10 to set
	 */
	public void setBill_footer10(String bill_footer10) {
		this.bill_footer10 = bill_footer10;
	}

	/**
	 * @param bill_footer2 the bill_footer2 to set
	 */
	public void setBill_footer2(String bill_footer2) {
		this.bill_footer2 = bill_footer2;
	}

	/**
	 * @param bill_footer3 the bill_footer3 to set
	 */
	public void setBill_footer3(String bill_footer3) {
		this.bill_footer3 = bill_footer3;
	}

	/**
	 * @param bill_footer4 the bill_footer4 to set
	 */
	public void setBill_footer4(String bill_footer4) {
		this.bill_footer4 = bill_footer4;
	}

	/**
	 * @param bill_footer5 the bill_footer5 to set
	 */
	public void setBill_footer5(String bill_footer5) {
		this.bill_footer5 = bill_footer5;
	}

	/**
	 * @param bill_footer6 the bill_footer6 to set
	 */
	public void setBill_footer6(String bill_footer6) {
		this.bill_footer6 = bill_footer6;
	}

	/**
	 * @param bill_footer7 the bill_footer7 to set
	 */
	public void setBill_footer7(String bill_footer7) {
		this.bill_footer7 = bill_footer7;
	}
	
	/**
	 * @param bill_footer8 the bill_footer8 to set
	 */
	public void setBill_footer8(String bill_footer8) {
		this.bill_footer8 = bill_footer8;
	}

	/**
	 * @param bill_footer9 the bill_footer9 to set
	 */
	public void setBill_footer9(String bill_footer9) {
		this.bill_footer9 = bill_footer9;
	}

	/**
	 * @param bill_hdr1 the bill_hdr1 to set
	 */
	public void setBill_hdr1(String bill_hdr1) {
		this.bill_hdr1 = bill_hdr1;
	}

	/**
	 * @param bill_hdr10 the bill_hdr10 to set
	 */
	public void setBill_hdr10(String bill_hdr10) {
		this.bill_hdr10 = bill_hdr10;
	}
	
	/**
	 * @param bill_hdr2 the bill_hdr2 to set
	 */
	public void setBill_hdr2(String bill_hdr2) {
		this.bill_hdr2 = bill_hdr2;
	}

	/**
	 * @param bill_hdr3 the bill_hdr3 to set
	 */
	public void setBill_hdr3(String bill_hdr3) {
		this.bill_hdr3 = bill_hdr3;
	}
	
	/**
	 * @param bill_hdr4 the bill_hdr4 to set
	 */
	public void setBill_hdr4(String bill_hdr4) {
		this.bill_hdr4 = bill_hdr4;
	}

	/**
	 * @param bill_hdr5 the bill_hdr5 to set
	 */
	public void setBill_hdr5(String bill_hdr5) {
		this.bill_hdr5 = bill_hdr5;
	}
	
	/**
	 * @param bill_hdr6 the bill_hdr6 to set
	 */
	public void setBill_hdr6(String bill_hdr6) {
		this.bill_hdr6 = bill_hdr6;
	}

	/**
	 * @param bill_hdr7 the bill_hdr7 to set
	 */
	public void setBill_hdr7(String bill_hdr7) {
		this.bill_hdr7 = bill_hdr7;
	}


	/**
	 * @param bill_hdr8 the bill_hdr8 to set
	 */
	public void setBill_hdr8(String bill_hdr8) {
		this.bill_hdr8 = bill_hdr8;
	}

	/**
	 * @param bill_hdr9 the bill_hdr9 to set
	 */
	public void setBill_hdr9(String bill_hdr9) {
		this.bill_hdr9 = bill_hdr9;
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
	@Counter(module="bill_param", key="bill_param")
	public void setId(Integer id) {
		this.id = id;
	}

	
	
	/**
	 * @param show_item_tax the show_item_tax to set
	 */
	public void setShow_item_tax(Integer show_item_tax) {
		this.show_item_tax = show_item_tax;
	}

	/**
	 * @param show_tax_summary the show_tax_summary to set
	 */
	public void setShow_tax_summary(Integer show_tax_summary) {
		this.show_tax_summary = show_tax_summary;
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
