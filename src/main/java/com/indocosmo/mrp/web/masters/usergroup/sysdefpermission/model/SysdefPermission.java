package com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "vw_mrp_system_functions")
public class SysdefPermission extends GeneralModelBase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Pk
	@Column(name="id")
    private Integer id;
	
	@Column(name="code")
    private String code;
	
	@Column(name="name")
    private String name;
	
	@Column(name="description")
    private String description;
	
	@Column(name="system_group")
    private String systemGroup;
	
	
	


	
	

	/*@Column(name="permitted_name")
    private String permittedName;*/
	
	
	@Column(name="permitted_at")
    private String permittedAt;
	
	
	
	/**
	 * @return the permittedAt
	 */
	public String getPermittedAt() {
	
		return permittedAt;
	}



	
	/**
	 * @param permittedAt the permittedAt to set
	 */
	public void setPermittedAt(String permittedAt) {
	
		this.permittedAt = permittedAt;
	}

	@Column(name="is_view_applicable")
    private Boolean isViewApplicable;
	
	@Column(name="is_add_applicable")
    private Boolean isAddApplicable;
	
	@Column(name="is_edit_applicable")
    private Boolean isEditApplicable;
	
	@Column(name="is_execute_applicable")
    private Boolean isExecuteApplicable;
	
	@Column(name="is_delete_applicable")
    private Boolean isDeleteApplicable;
	
	@Column(name="is_export_applicable")
    private Boolean isExportApplicable;
	
	@Transient
    private Integer userGroupId;
	
	@Transient
	private Boolean canView;
	
	@Transient
	private Integer usergrouppermissionId;
	
	
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

	@Transient
	private Boolean canAdd;
	
	@Transient
	private Boolean canEdit;
	
	@Transient
	private Boolean canDelete;
	
	
	@Transient
	private String created_at;
	
	@Transient
	private Integer created_by;
	
	
	
	@Transient
	private Boolean canExport;
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	private Boolean canExecute;


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the systemGroup
	 */
	public String getSystemGroup() {
		return systemGroup;
	}

	/**
	 * @param systemGroup the systemGroup to set
	 */
	public void setSystemGroup(String systemGroup) {
		this.systemGroup = systemGroup;
	}

	
	

	/**
	 * @return the isViewApplicable
	 */
	public Boolean getIsViewApplicable() {
		return isViewApplicable;
	}

	/**
	 * @param isViewApplicable the isViewApplicable to set
	 */
	public void setIsViewApplicable(Boolean isViewApplicable) {
		this.isViewApplicable = isViewApplicable;
	}

	/**
	 * @return the isAddApplicable
	 */
	public Boolean getIsAddApplicable() {
		return isAddApplicable;
	}

	/**
	 * @param isAddApplicable the isAddApplicable to set
	 */
	public void setIsAddApplicable(Boolean isAddApplicable) {
		this.isAddApplicable = isAddApplicable;
	}

	/**
	 * @return the isEditApplicable
	 */
	public Boolean getIsEditApplicable() {
		return isEditApplicable;
	}

	/**
	 * @param isEditApplicable the isEditApplicable to set
	 */
	public void setIsEditApplicable(Boolean isEditApplicable) {
		this.isEditApplicable = isEditApplicable;
	}

	/**
	 * @return the isExecuteApplicable
	 */
	public Boolean getIsExecuteApplicable() {
		return isExecuteApplicable;
	}

	/**
	 * @param isExecuteApplicable the isExecuteApplicable to set
	 */
	public void setIsExecuteApplicable(Boolean isExecuteApplicable) {
		this.isExecuteApplicable = isExecuteApplicable;
	}

	/**
	 * @return the isDeleteApplicable
	 */
	public Boolean getIsDeleteApplicable() {
		return isDeleteApplicable;
	}

	/**
	 * @param isDeleteApplicable the isDeleteApplicable to set
	 */
	public void setIsDeleteApplicable(Boolean isDeleteApplicable) {
		this.isDeleteApplicable = isDeleteApplicable;
	}

	/**
	 * @return the isExportApplicable
	 */
	public Boolean getIsExportApplicable() {
		return isExportApplicable;
	}

	/**
	 * @param isExportApplicable the isExportApplicable to set
	 */
	public void setIsExportApplicable(Boolean isExportApplicable) {
		this.isExportApplicable = isExportApplicable;
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
	 * @return the usergrouppermissionId
	 */
	public Integer getUsergrouppermissionId() {
		return usergrouppermissionId;
	}

	/**
	 * @param usergrouppermissionId the usergrouppermissionId to set
	 */
	public void setUsergrouppermissionId(Integer usergrouppermissionId) {
		this.usergrouppermissionId = usergrouppermissionId;
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
	
	
	
	

}
