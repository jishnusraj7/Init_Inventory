package com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="mrp_user_group_functions")
public class UserGroupPermission extends GeneralModelBase{
	
	
	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
	
		return created_by;
	}


	
	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
	
		return created_at;
	}


	
	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
	
		this.created_by = created_by;
	}


	
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
	
		this.created_at = created_at;
	}


	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Pk
	@Column(name = "user_group_id")
	private Integer userGroupId;
	
	@Pk
	@Column(name="system_function_id")
	private Integer sysdefPermissionId;

	@Column(name="can_view")
	private Boolean canView;
	
	@Column(name="can_add")
	private Boolean canAdd;
	
	@Column(name="can_edit")
	private Boolean canEdit;
	
	@Column(name="can_delete")
	private Boolean canDelete;
	
	@Column(name="can_export")
	private Boolean canExport;
	
	@Column(name="can_execute")
	private Boolean canExecute;
	
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	
	
	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
	
		return updated_by;
	}



	
	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
	
		return updated_at;
	}



	
	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
	
		this.updated_by = updated_by;
	}



	
	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
	
		this.updated_at = updated_at;
	}



	/**
	 * @return the canExecute
	 */
	public Boolean getCanExecute() {
		return canExecute;
	}


	/**
	 * @param canExecute the canExecute to set
	 */
	public void setCanExecute(Boolean canExecute) {
		this.canExecute = canExecute;
	}


	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="user_group_permission", key="user_group_permission")
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the userGroupId
	 */
	public Integer getUserGroupId() {
		return userGroupId;
	}


	/**
	 * @param userGroupId the userGroupId to set
	 */
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}


	/**
	 * @return the sysdefPermissionId
	 */
	public Integer getSysdefPermissionId() {
		return sysdefPermissionId;
	}


	/**
	 * @param sysdefPermissionId the sysdefPermissionId to set
	 */
	public void setSysdefPermissionId(Integer sysdefPermissionId) {
		this.sysdefPermissionId = sysdefPermissionId;
	}


	/**
	 * @return the canView
	 */
	public Boolean getCanView() {
		return canView;
	}


	/**
	 * @param canView the canView to set
	 */
	public void setCanView(Boolean canView) {
		this.canView = canView;
	}


	/**
	 * @return the canAdd
	 */
	public Boolean getCanAdd() {
		return canAdd;
	}


	/**
	 * @param canAdd the canAdd to set
	 */
	public void setCanAdd(Boolean canAdd) {
		this.canAdd = canAdd;
	}


	/**
	 * @return the canEdit
	 */
	public Boolean getCanEdit() {
		return canEdit;
	}


	/**
	 * @param canEdit the canEdit to set
	 */
	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}


	/**
	 * @return the canDelete
	 */
	public Boolean getCanDelete() {
		return canDelete;
	}


	/**
	 * @param canDelete the canDelete to set
	 */
	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}


	/**
	 * @return the canExport
	 */
	public Boolean getCanExport() {
		return canExport;
	}


	/**
	 * @param canExport the canExport to set
	 */
	public void setCanExport(Boolean canExport) {
		this.canExport = canExport;
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	

}