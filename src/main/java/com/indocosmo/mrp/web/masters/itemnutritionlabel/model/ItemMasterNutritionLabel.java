package com.indocosmo.mrp.web.masters.itemnutritionlabel.model;

import javax.persistence.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="stock_item_nutrition")
public class ItemMasterNutritionLabel extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer Dishid;
	private String DishName;
	private String IngredientName;
	private Double MarketQty;
	private Double RTC;
	private Double RTS;
	private Double Unit;
	
}
