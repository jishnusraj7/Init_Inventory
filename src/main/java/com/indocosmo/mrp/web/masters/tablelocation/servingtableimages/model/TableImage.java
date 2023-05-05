package com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="serving_table_images")
public class TableImage extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@Column(name = "image")
	private String image;	
	
	@Column(name = "height")
	private Integer height;	
	
	@Column(name = "width")
	private Integer width;	

	
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="serving_table_images", key="serving_table_images")
	public void setId(Integer id) {
	
		this.id=id;
	}



	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}



	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}



	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}



	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}



	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}



	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	
	
}
