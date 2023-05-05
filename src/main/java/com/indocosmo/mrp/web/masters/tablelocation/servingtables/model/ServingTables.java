package com.indocosmo.mrp.web.masters.tablelocation.servingtables.model;

import java.sql.Timestamp;

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
@Table(name="serving_tables")
public class ServingTables extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;	

	@Column(name = "description")
	private String description;
	
	@Column(name = "is_deleted")
	private Integer isDeleted=0;
	
	@Column(name = "covers")
	private Long covers;
	
	@Column(name = "serving_table_location_id")
	private Integer serving_table_location_id;
	
	@Column(name = "status")
	private Integer status=0;
	
	@Column(name = "layout_image")
	private Integer layout_image;
	
	
	@Column(name="last_sync_at")
	private Timestamp lastSyncAt;
	
	@Column(name = "created_by")
	private Integer created_by;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "row_position")
	private Integer row_position;
	
	@Column(name = "column_position")
	private Integer column_position;
	
	

	@Column(name = "updated_at")
	private String updated_at;
	
	@Transient
	private String servingTablesList;
	
	@Transient
	private String image;
	
	@Transient
	private Integer height;
	
	@Transient
	private String row_position1;
	
	@Transient
	private String column_position1;
	
	@Transient
	private Integer width;
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="serving_tables", key="serving_tables")
	public void setId(Integer id) {
	
		this.id=id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	

	/**
	 * @return the lastSyncAt
	 */
	public Timestamp getLastSyncAt() {
		return lastSyncAt;
	}

	/**
	 * @param lastSyncAt the lastSyncAt to set
	 */
	public void setLastSyncAt(Timestamp lastSyncAt) {
		this.lastSyncAt = lastSyncAt;
	}

	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
		return created_by;
	}

	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}

	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
		return updated_by;
	}

	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}

	/**
	 * @return the row_position
	 */
	public Integer getRow_position() {
		return row_position;
	}

	/**
	 * @param row_position the row_position to set
	 */
	public void setRow_position(Integer row_position) {
		this.row_position = row_position;
	}

	/**
	 * @return the column_position
	 */
	public Integer getColumn_position() {
		return column_position;
	}

	/**
	 * @param column_position the column_position to set
	 */
	public void setColumn_position(Integer column_position) {
		this.column_position = column_position;
	}

	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}

	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the serving_table_location_id
	 */
	public Integer getServing_table_location_id() {
		return serving_table_location_id;
	}

	/**
	 * @param serving_table_location_id the serving_table_location_id to set
	 */
	public void setServing_table_location_id(Integer serving_table_location_id) {
		this.serving_table_location_id = serving_table_location_id;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the layout_image
	 */
	public Integer getLayout_image() {
		return layout_image;
	}

	/**
	 * @param layout_image the layout_image to set
	 */
	public void setLayout_image(Integer layout_image) {
		this.layout_image = layout_image;
	}

	/**
	 * @return the servingTablesList
	 */
	public String getServingTablesList() {
		return servingTablesList;
	}

	/**
	 * @param servingTablesList the servingTablesList to set
	 */
	public void setServingTablesList(String servingTablesList) {
		this.servingTablesList = servingTablesList;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getHeight() {
		return height;
	}

	/**
	 * @return the row_position1
	 */
	public String getRow_position1() {
		return row_position1;
	}

	/**
	 * @param row_position1 the row_position1 to set
	 */
	public void setRow_position1(String row_position1) {
		this.row_position1 = row_position1;
	}

	/**
	 * @return the column_position1
	 */
	public String getColumn_position1() {
		return column_position1;
	}

	/**
	 * @param column_position1 the column_position1 to set
	 */
	public void setColumn_position1(String column_position1) {
		this.column_position1 = column_position1;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the covers
	 */
	public Long getCovers() {
		return covers;
	}

	/**
	 * @param covers the covers to set
	 */
	public void setCovers(Long covers) {
		this.covers = covers;
	}

	
	
	
}
