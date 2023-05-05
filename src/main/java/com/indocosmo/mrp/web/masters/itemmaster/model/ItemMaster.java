package com.indocosmo.mrp.web.masters.itemmaster.model;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;

@Entity
@Table(name = "mrp_stock_item")
public class ItemMaster extends GeneralModelBase {
	
	@Column(name = "alternative_name")
	private String alternativeName;
	
	@Column(name = "alternative_name_to_print")
	private String alternativeNameToPrint;
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "bg_color")
	private String bgColor;
	
	@Transient
	private String bomQty;
	
	@Column(name = "choice_ids")
	private String choiceIds;
	
	@Column(name = "hsn_code")
	private String hsn_code;
	
	
	
	public String getHsn_code() {
	
		return hsn_code;
	}



	
	public void setHsn_code(String hsn_code) {
	
		this.hsn_code = hsn_code;
	}

	@Column(name = "code")
	private String code;
	
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "fg_color")
	private String fgColor;
	
	@Column(name = "is_barcode_print")
	private Integer is_barcode_print = 1;
	
	@Column(name = "pack_uom_id")
	private Integer pack_uom_id = 1;
	
	@Column(name = "whls_price")
	private Double whls_price;
	
	@Column(name = "is_semi_finished")
	private Integer is_semi_finished = 0;
	
	@Column(name = "is_finished")
	private Integer is_finished = 0;
	
	@Column(name = "sales_margin")
	private Integer sales_margin = 0;
	
	@Column(name = "is_sales_margin_percent")
	private Integer is_sales_margin_percent = 0;
	
	/*@Column(name = "tax_percentage")
	private Double tax_percentage = 0.0;*/
	
	
	
	
	public Integer getIs_valid() {
	
		return is_valid;
	}




	
	public void setIs_valid(Integer is_valid) {
	
		this.is_valid = is_valid;
	}

	@Column(name = "is_valid")
	private Integer is_valid;
	
	
	@Column(name = "is_whls_price_pc")
	private Integer is_whls_price_pc=0;
	
	@Pk
	@Id(generationType = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Column(name = "input_tax_id")
	private Integer inputTaxId;
	
	@Column(name = "is_active")
	private Boolean is_active;
	
	@Column(name = "is_batch")
	private Boolean isBatch;
	
	@Column(name = "is_combo_item")
	private Boolean isComboItem;
	
	@Column(name = "is_deleted")
	private Integer isDeleted = 0;
	
	@Column(name = "is_manufactured")
	private Boolean isManufactured;
	
	@Column(name = "is_open")
	private Boolean isOpen;
	
	@Column(name = "is_require_weighing")
	private String isRequireWeighing;
	
	@Column(name = "is_sellable")
	private Boolean isSellable;
	
	@Column(name = "is_synchable")
	private Boolean isSynchable = true;
	
	@Transient
	private ArrayList<ItemMasterBom> itemBomDetails;
	
	@Column(name = "item_category_id")
	private Integer itemCategoryId;
	
	@Column(name = "last_sync_at")
	private Timestamp last_sync_at;
	
	@Column(name = "lead_time")
	private Integer leadTime = 0;
	
	@Column(name = "max_stock")
	private Double maxStock = 0.0;
	
	@Column(name = "min_stock")
	private Double minStock = 0.0;
	
	@Column(name = "movement_method")
	private Integer movementMethod;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "name_to_print")
	private String nameToPrint;
	
	@Column(name = "barcode")
	private String barcode;
	
	@Column(name = "sys_sale_flag")
	private Integer sysSaleFlag = 1;
	
	@Column(name = "output_tax_id")
	private Integer outputTaxId;
	
	@Column(name = "pack_contains")
	private Integer packContains = 0;
	
	@Column(name = "pref_supplier_id")
	private Integer prefSupplierId;
	
	@Column(name = "profit_category_id")
	private Integer profitCategoryId;
	
	@Column(name = "shelf_life")
	private Integer shelfLife = 0;
	
	@Column(name = "std_purchase_qty")
	private Double stdPurchaseQty = 0.0;
	
	@Column(name = "sub_class_id")
	private Integer subClassId;
	
	@Column(name = "uom_id")
	private Integer uomId;
	
	@Column(name = "kitchen_id")
	private Integer kitchenId;
	
	@Column(name = "valuation_method")
	private Integer valuationMethod;
	
	@Column(name = "is_group_item")
	private Integer isGroupItem;
	
	@Column(name = "tax_calculation_method")
	private Integer taxCalculationMethod;
	
	@Column(name = "taxation_based_on")
	private Integer taxationBasedOn;
	
	@Column(name = "output_tax_id_table_service")
	private Integer outputTaxIdTableService;
	
	@Column(name = "output_tax_id_home_service")
	private Integer outputTaxIdHomeService;
	
	@Column(name = "output_tax_id_take_away_service")
	private Integer outputTaxIdTakeAwayService;
	
	@Column(name = "attrib1_name")
	private String attrib1Name;
	
	@Column(name = "attrib1_options")
	private String attrib1Options;
	
	@Column(name = "attrib2_name")
	private String attrib2Name;
	
	@Column(name = "attrib2_options")
	private String attrib2Options;
	
	@Column(name = "attrib3_name")
	private String attrib3Name;
	
	@Column(name = "attrib3_options")
	private String attrib3Options;
	
	@Column(name = "attrib4_name")
	private String attrib4Name;
	
	@Column(name = "attrib4_options")
	private String attrib4Options;
	
	@Column(name = "attrib5_name")
	private String attrib5Name;
	
	@Column(name = "attrib5_options")
	private String attrib5Options;
	
	@Column(name = "fixed_price")
	private Double fixedPrice;
	
	@Column(name = "item_cost")
	private Double itemCost;
	
	// new added
	
	@Transient
	private String uomCode;
	
	@Transient
	private String itemClass;
	
	@Transient
	private String ChoicesName;
	
	@Transient
	private String kitchenName;
	
	@Transient
	private String outPutTaxName;
	
	@Transient
	private List<ItemMaster> itmExcelData;
	
	@Column(name = "group_item_id")
	private Integer groupItemId;
	
	@Column(name = "is_hot_item_1")
	private Integer is_hot_item_1=0;
	
	@Column(name = "hot_item_1_display_order")
	private Integer hot_item_1_display_order;
	
	@Column(name = "is_hot_item_2")
	private Integer is_hot_item_2=0;
	
	
	@Column(name = "hot_item_2_display_order")
	private Integer hot_item_2_display_order;
	
	
	@Column(name = "is_hot_item_3")
	private Integer is_hot_item_3=0;
	
	
	@Column(name = "hot_item_3_display_order")
	private Integer hot_item_3_display_order;
	

	@Column(name = "display_order")
	private Integer display_order;
	
	
	
	
	
	
	
	
	public Integer getDisplay_order() {
	
		return display_order;
	}


	
	public void setDisplay_order(Integer display_order) {
	
		this.display_order = display_order;
	}


	public Integer getIs_hot_item_1() {
	
		return is_hot_item_1;
	}

	
	
	/**
	 * @return the is_semi_finished
	 */
	public Integer getIs_semi_finished() {
	
		return is_semi_finished;
	}




	
	/**
	 * @return the is_finished
	 */
	public Integer getIs_finished() {
	
		return is_finished;
	}




	
	/**
	 * @return the sales_margin
	 */
	public Integer getSales_margin() {
	
		return sales_margin;
	}




	
	/**
	 * @return the is_sales_margin_percent
	 */
	public Integer getIs_sales_margin_percent() {
	
		return is_sales_margin_percent;
	}




	
	/**
	 * @return the tax_percentage
	 *//*
	public Double getTax_percentage() {
	
		return tax_percentage;
	}
*/



	
	/**
	 * @param is_semi_finished the is_semi_finished to set
	 */
	public void setIs_semi_finished(Integer is_semi_finished) {
	
		this.is_semi_finished = is_semi_finished;
	}




	
	/**
	 * @param is_finished the is_finished to set
	 */
	public void setIs_finished(Integer is_finished) {
	
		this.is_finished = is_finished;
	}




	
	/**
	 * @param sales_margin the sales_margin to set
	 */
	public void setSales_margin(Integer sales_margin) {
	
		this.sales_margin = sales_margin;
	}




	
	/**
	 * @param is_sales_margin_percent the is_sales_margin_percent to set
	 */
	public void setIs_sales_margin_percent(Integer is_sales_margin_percent) {
	
		this.is_sales_margin_percent = is_sales_margin_percent;
	}




	
	/**
	 * @param tax_percentage the tax_percentage to set
	 *//*
	public void setTax_percentage(Double tax_percentage) {
	
		this.tax_percentage = tax_percentage;
	}
*/



	public void setIs_hot_item_1(Integer is_hot_item_1) {
	
		this.is_hot_item_1 = is_hot_item_1;
	}

	
	public Integer getHot_item_1_display_order() {
	
		return hot_item_1_display_order;
	}

	
	public void setHot_item_1_display_order(Integer hot_item_1_display_order) {
	
		this.hot_item_1_display_order = hot_item_1_display_order;
	}

	
	public Integer getIs_hot_item_2() {
	
		return is_hot_item_2;
	}

	
	public void setIs_hot_item_2(Integer is_hot_item_2) {
	
		this.is_hot_item_2 = is_hot_item_2;
	}

	
	public Integer getHot_item_2_display_order() {
	
		return hot_item_2_display_order;
	}

	
	public void setHot_item_2_display_order(Integer hot_item_2_display_order) {
	
		this.hot_item_2_display_order = hot_item_2_display_order;
	}

	
	public Integer getIs_hot_item_3() {
	
		return is_hot_item_3;
	}

	
	public void setIs_hot_item_3(Integer is_hot_item_3) {
	
		this.is_hot_item_3 = is_hot_item_3;
	}

	
	public Integer getHot_item_3_display_order() {
	
		return hot_item_3_display_order;
	}

	
	public void setHot_item_3_display_order(Integer hot_item_3_display_order) {
	
		this.hot_item_3_display_order = hot_item_3_display_order;
	}

	@Column(name = "item_thumb")
	private String itemThumb;
	
	/**
	 * @return the alternativeName
	 */
	public String getAlternativeName() {
	
		return alternativeName;
	}
	
	/**
	 * @return the alternativeNameToPrint
	 */
	public String getAlternativeNameToPrint() {
	
		return alternativeNameToPrint;
	}
	
	/**
	 * @return the attrib1Name
	 */
	public String getAttrib1Name() {
	
		return attrib1Name;
	}
	
	/**
	 * @return the attrib1Options
	 */
	public String getAttrib1Options() {
	
		return attrib1Options;
	}
	
	/**
	 * @return the attrib2Name
	 */
	public String getAttrib2Name() {
	
		return attrib2Name;
	}
	
	/**
	 * @return the attrib2Options
	 */
	public String getAttrib2Options() {
	
		return attrib2Options;
	}
	
	/**
	 * @return the attrib3Name
	 */
	public String getAttrib3Name() {
	
		return attrib3Name;
	}
	
	/**
	 * @return the attrib3Options
	 */
	public String getAttrib3Options() {
	
		return attrib3Options;
	}
	
	/**
	 * @return the attrib4Name
	 */
	public String getAttrib4Name() {
	
		return attrib4Name;
	}
	
	/**
	 * @return the attrib4Options
	 */
	public String getAttrib4Options() {
	
		return attrib4Options;
	}
	
	/**
	 * @return the attrib5Name
	 */
	public String getAttrib5Name() {
	
		return attrib5Name;
	}
	
	/**
	 * @return the attrib5Options
	 */
	public String getAttrib5Options() {
	
		return attrib5Options;
	}
	
	/**
	 * @return the barcode
	 */
	public String getBarcode() {
	
		return barcode;
	}
	
	/**
	 * @return the bgColor
	 */
	public String getBgColor() {
	
		return bgColor;
	}
	
	/**
	 * @return the bomQty
	 */
	public String getBomQty() {
	
		return bomQty;
	}
	
	/**
	 * @return the choiceIds
	 */
	public String getChoiceIds() {
	
		return choiceIds;
	}
	
	/**
	 * @return the choicesName
	 */
	public String getChoicesName() {
	
		return ChoicesName;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
	
		return code;
	}
	
	public String getCreated_at() {
	
		return created_at;
	}
	
	public Integer getCreated_by() {
	
		return created_by;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
	
		return description;
	}
	
	/**
	 * @return the fgColor
	 */
	public String getFgColor() {
	
		return fgColor;
	}
	
	/**
	 * @return the fixedPrice
	 */
	public Double getFixedPrice() {
	
		return fixedPrice;
	}
	
	public Integer getGroupItemId() {
	
		return groupItemId;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
	
		return id;
	}
	
	/**
	 * @return the inputTaxId
	 */
	public Integer getInputTaxId() {
	
		return inputTaxId;
	}
	
	/**
	 * @return the is_active
	 */
	public Boolean getIs_active() {
	
		return is_active;
	}
	
	public Integer getIs_barcode_print() {
	
		return is_barcode_print;
	}
	
	public Integer getIs_whls_price_pc() {
	
		return is_whls_price_pc;
	}
	
	/**
	 * @return the isBatch
	 */
	public Boolean getIsBatch() {
	
		return isBatch;
	}
	
	/**
	 * @return the isComboItem
	 */
	public Boolean getIsComboItem() {
	
		return isComboItem;
	}
	
	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
	
		return isDeleted;
	}
	
	/**
	 * @return the isGroupItem
	 */
	public Integer getIsGroupItem() {
	
		return isGroupItem;
	}
	
	/**
	 * @return the isManufactured
	 */
	public Boolean getIsManufactured() {
	
		return isManufactured;
	}
	
	/**
	 * @return the isOpen
	 */
	public Boolean getIsOpen() {
	
		return isOpen;
	}
	
	/**
	 * @return the isRequireWeighing
	 */
	public String getIsRequireWeighing() {
	
		return isRequireWeighing;
	}
	
	/**
	 * @return the isSellable
	 */
	public Boolean getIsSellable() {
	
		return isSellable;
	}
	
	/**
	 * @return the isSynchable
	 */
	public Boolean getIsSynchable() {
	
		return isSynchable;
	}
	
	/**
	 * @return the itemBomDetails
	 */
	public ArrayList<ItemMasterBom> getItemBomDetails() {
	
		return itemBomDetails;
	}
	
	/**
	 * @return the itemCategoryId
	 */
	public Integer getItemCategoryId() {
	
		return itemCategoryId;
	}
	
	/**
	 * @return the itemClass
	 */
	public String getItemClass() {
	
		return itemClass;
	}
	
	/**
	 * @return the itemCost
	 */
	public Double getItemCost() {
	
		return itemCost;
	}
	
	/**
	 * @return the itemThumb
	 */
	public String getItemThumb() {
	
		return itemThumb;
	}
	
	/**
	 * @return the itmExcelData
	 */
	public List<ItemMaster> getItmExcelData() {
	
		return itmExcelData;
	}
	
	/**
	 * @return the kitchenId
	 */
	public Integer getKitchenId() {
	
		return kitchenId;
	}
	
	/**
	 * @return the kitchenName
	 */
	public String getKitchenName() {
	
		return kitchenName;
	}
	
	/**
	 * @return the last_sync_at
	 */
	public Timestamp getLast_sync_at() {
	
		return last_sync_at;
	}
	
	/**
	 * @return the leadTime
	 */
	public Integer getLeadTime() {
	
		return leadTime;
	}
	
	/**
	 * @return the maxStock
	 */
	public Double getMaxStock() {
	
		return maxStock;
	}
	
	/**
	 * @return the minStock
	 */
	public Double getMinStock() {
	
		return minStock;
	}
	
	/**
	 * @return the movementMethod
	 */
	public Integer getMovementMethod() {
	
		return movementMethod;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
	
		return name;
	}
	
	/**
	 * @return the nameToPrint
	 */
	public String getNameToPrint() {
	
		return nameToPrint;
	}
	
	/**
	 * @return the outputTaxId
	 */
	public Integer getOutputTaxId() {
	
		return outputTaxId;
	}
	
	/**
	 * @return the outputTaxIdHomeService
	 */
	public Integer getOutputTaxIdHomeService() {
	
		return outputTaxIdHomeService;
	}
	
	/**
	 * @return the outputTaxIdTableService
	 */
	public Integer getOutputTaxIdTableService() {
	
		return outputTaxIdTableService;
	}
	
	/**
	 * @return the outputTaxIdTakeAwayService
	 */
	public Integer getOutputTaxIdTakeAwayService() {
	
		return outputTaxIdTakeAwayService;
	}
	
	/**
	 * @return the outPutTaxName
	 */
	public String getOutPutTaxName() {
	
		return outPutTaxName;
	}
	
	public Integer getPack_uom_id() {
	
		return pack_uom_id;
	}
	
	/**
	 * @return the packContains
	 */
	public Integer getPackContains() {
	
		return packContains;
	}
	
	/**
	 * @return the prefSupplierId
	 */
	public Integer getPrefSupplierId() {
	
		return prefSupplierId;
	}
	
	/**
	 * @return the profitCategoryId
	 */
	public Integer getProfitCategoryId() {
	
		return profitCategoryId;
	}
	
	/**
	 * @return the shelfLife
	 */
	public Integer getShelfLife() {
	
		return shelfLife;
	}
	
	/**
	 * @return the stdPurchaseQty
	 */
	public Double getStdPurchaseQty() {
	
		return stdPurchaseQty;
	}
	
	/**
	 * @return the subClassId
	 */
	public Integer getSubClassId() {
	
		return subClassId;
	}
	
	/**
	 * @return the sysSaleFlag
	 */
	public Integer getSysSaleFlag() {
	
		return sysSaleFlag;
	}
	
	/**
	 * @return the taxationBasedOn
	 */
	public Integer getTaxationBasedOn() {
	
		return taxationBasedOn;
	}
	
	/**
	 * @return the taxCalculationMethod
	 */
	public Integer getTaxCalculationMethod() {
	
		return taxCalculationMethod;
	}
	
	/**
	 * @return the uomCode
	 */
	public String getUomCode() {
	
		return uomCode;
	}
	
	/**
	 * @return the uomId
	 */
	public Integer getUomId() {
	
		return uomId;
	}
	
	/* @Transient private ArrayList<ItemMasterBatch> itemBatchDetails; */
	
	/**
	 * @return the valuationMethod
	 */
	public Integer getValuationMethod() {
	
		return valuationMethod;
	}
	
	public Double getWhls_price() {
	
		return whls_price;
	}
	
	/**
	 * @param alternativeName
	 *            the alternativeName to set
	 */
	public void setAlternativeName(String alternativeName) {
	
		this.alternativeName = alternativeName;
	}
	
	/**
	 * @param alternativeNameToPrint
	 *            the alternativeNameToPrint to set
	 */
	public void setAlternativeNameToPrint(String alternativeNameToPrint) {
	
		this.alternativeNameToPrint = alternativeNameToPrint;
	}
	
	/**
	 * @param attrib1Name
	 *            the attrib1Name to set
	 */
	public void setAttrib1Name(String attrib1Name) {
	
		this.attrib1Name = attrib1Name;
	}
	
	/**
	 * @param attrib1Options
	 *            the attrib1Options to set
	 */
	public void setAttrib1Options(String attrib1Options) {
	
		this.attrib1Options = attrib1Options;
	}
	
	/**
	 * @param attrib2Name
	 *            the attrib2Name to set
	 */
	public void setAttrib2Name(String attrib2Name) {
	
		this.attrib2Name = attrib2Name;
	}
	
	/**
	 * @param attrib2Options
	 *            the attrib2Options to set
	 */
	public void setAttrib2Options(String attrib2Options) {
	
		this.attrib2Options = attrib2Options;
	}
	
	/**
	 * @param attrib3Name
	 *            the attrib3Name to set
	 */
	public void setAttrib3Name(String attrib3Name) {
	
		this.attrib3Name = attrib3Name;
	}
	
	/**
	 * @param attrib3Options
	 *            the attrib3Options to set
	 */
	public void setAttrib3Options(String attrib3Options) {
	
		this.attrib3Options = attrib3Options;
	}
	
	/**
	 * @param attrib4Name
	 *            the attrib4Name to set
	 */
	public void setAttrib4Name(String attrib4Name) {
	
		this.attrib4Name = attrib4Name;
	}
	
	/**
	 * @param attrib4Options
	 *            the attrib4Options to set
	 */
	public void setAttrib4Options(String attrib4Options) {
	
		this.attrib4Options = attrib4Options;
	}
	
	/**
	 * @param attrib5Name
	 *            the attrib5Name to set
	 */
	public void setAttrib5Name(String attrib5Name) {
	
		this.attrib5Name = attrib5Name;
	}
	
	/**
	 * @param attrib5Options
	 *            the attrib5Options to set
	 */
	public void setAttrib5Options(String attrib5Options) {
	
		this.attrib5Options = attrib5Options;
	}
	
	/**
	 * @param barcode
	 *            the barcode to set
	 */
	public void setBarcode(String barcode) {
	
		this.barcode = barcode;
	}
	
	/**
	 * @param bgColor
	 *            the bgColor to set
	 */
	public void setBgColor(String bgColor) {
	
		this.bgColor = bgColor;
	}
	
	/**
	 * @param bomQty
	 *            the bomQty to set
	 */
	public void setBomQty(String bomQty) {
	
		this.bomQty = bomQty;
	}
	
	/**
	 * @param choiceIds
	 *            the choiceIds to set
	 */
	public void setChoiceIds(String choiceIds) {
	
		this.choiceIds = choiceIds;
	}
	
	/**
	 * @param choicesName
	 *            the choicesName to set
	 */
	public void setChoicesName(String choicesName) {
	
		ChoicesName = choicesName;
	}
	
	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
	
		this.code = code;
	}
	
	public void setCreated_at(String created_at) {
	
		this.created_at = created_at;
	}
	
	public void setCreated_by(Integer created_by) {
	
		this.created_by = created_by;
	}
	
	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
	
		this.description = description;
	}
	
	/**
	 * @param fgColor
	 *            the fgColor to set
	 */
	public void setFgColor(String fgColor) {
	
		this.fgColor = fgColor;
	}
	
	/**
	 * @param fixedPrice
	 *            the fixedPrice to set
	 */
	public void setFixedPrice(Double fixedPrice) {
	
		this.fixedPrice = fixedPrice;
	}
	
	public void setGroupItemId(Integer groupItemId) {
	
		this.groupItemId = groupItemId;
	}
	
	/**
	 * @param id
	 *            the id to set
	 */
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "item", key = "item")
	public void setId(Integer id) {
	
		this.id = id;
	}
	
	/**
	 * @param inputTaxId
	 *            the inputTaxId to set
	 */
	public void setInputTaxId(Integer inputTaxId) {
	
		this.inputTaxId = inputTaxId;
	}
	
	/**
	 * @param is_active
	 *            the is_active to set
	 */
	public void setIs_active(Boolean is_active) {
	
		this.is_active = is_active;
	}
	
	public void setIs_barcode_print(Integer is_barcode_print) {
	
		this.is_barcode_print = is_barcode_print;
	}
	
	public void setIs_whls_price_pc(Integer is_whls_price_pc) {
	
		this.is_whls_price_pc = is_whls_price_pc;
	}
	
	/**
	 * @param isBatch
	 *            the isBatch to set
	 */
	public void setIsBatch(Boolean isBatch) {
	
		this.isBatch = isBatch;
	}
	
	/**
	 * @param isComboItem
	 *            the isComboItem to set
	 */
	public void setIsComboItem(Boolean isComboItem) {
	
		this.isComboItem = isComboItem;
	}
	
	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
	
		this.isDeleted = isDeleted;
	}
	
	/**
	 * @param isGroupItem
	 *            the isGroupItem to set
	 */
	public void setIsGroupItem(Integer isGroupItem) {
	
		this.isGroupItem = isGroupItem;
	}
	
	/**
	 * @param isManufactured
	 *            the isManufactured to set
	 */
	public void setIsManufactured(Boolean isManufactured) {
	
		this.isManufactured = isManufactured;
	}
	
	/**
	 * @param isOpen
	 *            the isOpen to set
	 */
	public void setIsOpen(Boolean isOpen) {
	
		this.isOpen = isOpen;
	}
	
	/**
	 * @param isRequireWeighing
	 *            the isRequireWeighing to set
	 */
	public void setIsRequireWeighing(String isRequireWeighing) {
	
		this.isRequireWeighing = isRequireWeighing;
	}
	
	/**
	 * @param isSellable
	 *            the isSellable to set
	 */
	public void setIsSellable(Boolean isSellable) {
	
		this.isSellable = isSellable;
	}
	
	/**
	 * @param isSynchable
	 *            the isSynchable to set
	 */
	public void setIsSynchable(Boolean isSynchable) {
	
		this.isSynchable = isSynchable;
	}
	
	/**
	 * @param itemBomDetails
	 *            the itemBomDetails to set
	 */
	public void setItemBomDetails(ArrayList<ItemMasterBom> itemBomDetails) {
	
		this.itemBomDetails = itemBomDetails;
	}
	
	/**
	 * @param itemCategoryId
	 *            the itemCategoryId to set
	 */
	public void setItemCategoryId(Integer itemCategoryId) {
	
		this.itemCategoryId = itemCategoryId;
	}
	
	/**
	 * @param itemClass
	 *            the itemClass to set
	 */
	public void setItemClass(String itemClass) {
	
		this.itemClass = itemClass;
	}
	
	/*
	*//**
	 * @return the itemBatchDetails
	 */
	/* public ArrayList<ItemMasterBatch> getItemBatchDetails() { return
	 * itemBatchDetails; } *//**
	 * @param itemBatchDetails
	 *            the itemBatchDetails to set
	 */
	/* public void setItemBatchDetails(ArrayList<ItemMasterBatch>
	 * itemBatchDetails) { this.itemBatchDetails = itemBatchDetails; } */
	
	/**
	 * @param itemCost
	 *            the itemCost to set
	 */
	public void setItemCost(Double itemCost) {
	
		this.itemCost = itemCost;
	}
	
	/**
	 * @param itemThumb
	 *            the itemThumb to set
	 */
	public void setItemThumb(String itemThumb) {
	
		this.itemThumb = itemThumb;
	}
	
	/**
	 * @param itmExcelData
	 *            the itmExcelData to set
	 */
	public void setItmExcelData(List<ItemMaster> itmExcelData) {
	
		this.itmExcelData = itmExcelData;
	}
	
	/**
	 * @param kitchenId
	 *            the kitchenId to set
	 */
	public void setKitchenId(Integer kitchenId) {
	
		this.kitchenId = kitchenId;
	}
	
	/**
	 * @param kitchenName
	 *            the kitchenName to set
	 */
	public void setKitchenName(String kitchenName) {
	
		this.kitchenName = kitchenName;
	}
	
	/**
	 * @param last_sync_at
	 *            the last_sync_at to set
	 */
	public void setLast_sync_at(Timestamp last_sync_at) {
	
		this.last_sync_at = last_sync_at;
	}
	
	/**
	 * @param leadTime
	 *            the leadTime to set
	 */
	public void setLeadTime(Integer leadTime) {
	
		this.leadTime = leadTime;
	}
	
	/**
	 * @param maxStock
	 *            the maxStock to set
	 */
	public void setMaxStock(Double maxStock) {
	
		this.maxStock = maxStock;
	}
	
	/**
	 * @param minStock
	 *            the minStock to set
	 */
	public void setMinStock(Double minStock) {
	
		this.minStock = minStock;
	}
	
	/**
	 * @param movementMethod
	 *            the movementMethod to set
	 */
	public void setMovementMethod(Integer movementMethod) {
	
		this.movementMethod = movementMethod;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
	
		this.name = name;
	}
	
	/**
	 * @param nameToPrint
	 *            the nameToPrint to set
	 */
	public void setNameToPrint(String nameToPrint) {
	
		this.nameToPrint = nameToPrint;
	}
	
	/**
	 * @param outputTaxId
	 *            the outputTaxId to set
	 */
	public void setOutputTaxId(Integer outputTaxId) {
	
		this.outputTaxId = outputTaxId;
	}
	
	/**
	 * @param outputTaxIdHomeService
	 *            the outputTaxIdHomeService to set
	 */
	public void setOutputTaxIdHomeService(Integer outputTaxIdHomeService) {
	
		this.outputTaxIdHomeService = outputTaxIdHomeService;
	}
	
	/**
	 * @param outputTaxIdTableService
	 *            the outputTaxIdTableService to set
	 */
	public void setOutputTaxIdTableService(Integer outputTaxIdTableService) {
	
		this.outputTaxIdTableService = outputTaxIdTableService;
	}
	
	/**
	 * @param outputTaxIdTakeAwayService
	 *            the outputTaxIdTakeAwayService to set
	 */
	public void setOutputTaxIdTakeAwayService(Integer outputTaxIdTakeAwayService) {
	
		this.outputTaxIdTakeAwayService = outputTaxIdTakeAwayService;
	}
	
	/**
	 * @param outPutTaxName
	 *            the outPutTaxName to set
	 */
	public void setOutPutTaxName(String outPutTaxName) {
	
		this.outPutTaxName = outPutTaxName;
	}
	
	public void setPack_uom_id(Integer pack_uom_id) {
	
		this.pack_uom_id = pack_uom_id;
	}
	
	/**
	 * @param packContains
	 *            the packContains to set
	 */
	public void setPackContains(Integer packContains) {
	
		this.packContains = packContains;
	}
	
	/**
	 * @param prefSupplierId
	 *            the prefSupplierId to set
	 */
	public void setPrefSupplierId(Integer prefSupplierId) {
	
		this.prefSupplierId = prefSupplierId;
	}
	
	/**
	 * @param profitCategoryId
	 *            the profitCategoryId to set
	 */
	public void setProfitCategoryId(Integer profitCategoryId) {
	
		this.profitCategoryId = profitCategoryId;
	}
	
	/**
	 * @param shelfLife
	 *            the shelfLife to set
	 */
	public void setShelfLife(Integer shelfLife) {
	
		this.shelfLife = shelfLife;
	}
	
	/**
	 * @param stdPurchaseQty
	 *            the stdPurchaseQty to set
	 */
	public void setStdPurchaseQty(Double stdPurchaseQty) {
	
		this.stdPurchaseQty = stdPurchaseQty;
	}
	
	/**
	 * @param subClassId
	 *            the subClassId to set
	 */
	public void setSubClassId(Integer subClassId) {
	
		this.subClassId = subClassId;
	}
	
	/**
	 * @param sysSaleFlag
	 *            the sysSaleFlag to set
	 */
	public void setSysSaleFlag(Integer sysSaleFlag) {
	
		this.sysSaleFlag = sysSaleFlag;
	}
	
	/**
	 * @param taxationBasedOn
	 *            the taxationBasedOn to set
	 */
	public void setTaxationBasedOn(Integer taxationBasedOn) {
	
		this.taxationBasedOn = taxationBasedOn;
	}
	
	/**
	 * @param taxCalculationMethod
	 *            the taxCalculationMethod to set
	 */
	public void setTaxCalculationMethod(Integer taxCalculationMethod) {
	
		this.taxCalculationMethod = taxCalculationMethod;
	}
	
	/**
	 * @param uomCode
	 *            the uomCode to set
	 */
	public void setUomCode(String uomCode) {
	
		this.uomCode = uomCode;
	}
	
	/**
	 * @param uomId
	 *            the uomId to set
	 */
	public void setUomId(Integer uomId) {
	
		this.uomId = uomId;
	}
	
	/**
	 * @param valuationMethod
	 *            the valuationMethod to set
	 */
	public void setValuationMethod(Integer valuationMethod) {
	
		this.valuationMethod = valuationMethod;
	}
	
	public void setWhls_price(Double whls_price) {
	
		this.whls_price = whls_price;
	}
}
