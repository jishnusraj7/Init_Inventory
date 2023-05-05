package com.indocosmo.mrp.web.masters.syncqueue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="sync_queue")
public class SyncQueue extends GeneralModelBase {
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer  id;
	
	@Column(name="table_name")
	private String tableName;
	
	@Column(name="record_id")
	private Integer recordId;
	
	
	@Column(name="shop_id")
	private Integer shopId;

	@Column(name="origin")
	private String origin;
	
	
	@Column(name="crud_action")
	private String crudAction;
	
	@Column(name="publishing_date")
	private String publishingDate;
	
	@Column(name="error_msg")
	private String errorMsg;
	
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @return the recordId
	 */
	public Integer getRecordId() {
		return recordId;
	}

	/**
	 * @return the shopId
	 */
	public Integer getShopId() {
		return shopId;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	

	/**
	 * @return the publishingDate
	 */
	public String getPublishingDate() {
		return publishingDate;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @return the syncStatus
	 */
	public Integer getSyncStatus() {
		return syncStatus;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	

	/**
	 * @return the crudAction
	 */
	public String getCrudAction() {
		return crudAction;
	}

	/**
	 * @param crudAction the crudAction to set
	 */
	public void setCrudAction(String crudAction) {
		this.crudAction = crudAction;
	}

	/**
	 * @param publishingDate the publishingDate to set
	 */
	public void setPublishingDate(String publishingDate) {
		this.publishingDate = publishingDate;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @param syncStatus the syncStatus to set
	 */
	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	@Column(name="sync_status")
	private Integer syncStatus;
	
	
	
	/**
	 * @return the id
	 */
	
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="sync_queue", key="sync_queue")
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
