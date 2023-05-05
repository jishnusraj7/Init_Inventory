<div class="box-header rgt_btn_header" id="div_btn_add">
	<div class="pull-right">
		<div class="btn-group">
		<!-- 	<button class="btn  btn-sm btn-info" type="button" name="btnTools"
				data-toggle="dropdown" id="btnTools">
				<i class="fa fa-wrench" aria-hidden="true"></i> Tools
			</button> -->
		<div class="dropdown-menu tool_btn_drpdwn_menu">
				<ul>
					<c:if test="${canExport}">
						<li><a class="dropdown-item" href="#">Export Data</a></li>
					</c:if>
				<%-- 	<c:if test="${CanExcute}">
						<li><a class="dropdown-item" href="#" data-toggle="modal"
							ng-click="openmodal();" data-target="#importDataModal">Import
								Data</a></li>
					</c:if> --%>
				</ul>
			</div>
		</div> 
		
		<c:if test="${CanExport}">
		<!-- <button class="btn  btn-sm btn-primary  " type="button"
			name="btnExport" id="btnExport"   ng-click="fun_exprt_excel()">
			Export
		</button> -->
		<button class="btn  btn-sm btn-primary  " type="button"
			name="btnExport" id="btnExport"  data-toggle="modal" data-target="#importDataModal" >
			Export
		</button>
	
	
		</c:if>
		
		&nbsp;&nbsp;
		<c:if test="${canAdd}">
		
		
		
		
		<button class="btn  btn-sm btnBack "  style="display: none;" type="button" name="btnBack"
			id="btnBackprod" ng-click="fun_backTo_table()">
			<i class="fa fa-caret-left" aria-hidden="true"></i><i
				class="fa fa-caret-left" aria-hidden="true"></i> Back
		</button> 
		<!-- <button ng-click="fun_go_prod()" type="button"
			class="btn btn-success btn-lg  summarybtn" ng-show="showSummary" style="font-size : 20px">
             TOPRODUCTION 
       		</button> -->
		 <button ng-click="fun_show_summary()" type="button"
			class="btn btn-success btn-lg  summarybtn" ng-show="showSummary"
			><i class="fa fa-book" aria-hidden="true"></i>
             Summary 
       		</button>
 		<button class="btn  btn-sm btn-primary add_btn " type="button"
			name="btnAdd" id="btnAdd" ng-click="fun_show_form()">
			<i class="fa fa-plus" aria-hidden="true"></i> Add
		</button>
		</c:if>


	</div>
</div>
<div class="box-header rgt_header_btn" id="div_btn_new">
	<div class="pull-right">

		<button class="btn  btn-sm btnBackr" type="button" name="btnBack"
			id="btnBack" ng-click="fun_backTo_table()">
			<i class="fa fa-caret-left" aria-hidden="true"></i><i
				class="fa fa-caret-left" aria-hidden="true"></i> Back
		</button>
		<button
			class="btn  btn-sm btn-danger discard_btn discard_button btnDiscard"
			type="button" name="btnDiscard" id="btnDiscard"
			ng-click="fun_discard_form(formData)">
			<i class="fa fa-recycle" aria-hidden="true"></i> Discard
		</button>

		<button class="btn  btn-sm btn-primary save_button savebtn"
			type="button" name="btnSave" id="btnSave" ng-click="fun_save_form()">
			<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
		</button>


	</div>
</div>
<!-- <div ng-hide="saveAlertMessage" class="alert-box">Record saved successfully!!!</div>  
 -->
<div class="box-header btn_header_top" id="div_btn_edit">


	<div class="btn-group position_prev_next_btn_form" id="item_master_tbl_div">
		<button type="button" class="btn btn-primary btn_prev"
			id="prev_{{row_id}}" ng-click="prev_formData($event)"
			ng-disabled="disable_prev_btn">
			<i class="fa fa-angle-left"></i><i class="fa fa-angle-left"></i> Prev
		</button>
		<button type="button" class="btn btn-primary btn_next"
			id="next_{{row_id}}" ng-click="next_formData($event)"
			ng-disabled="disable_next_btn">
			Next <i class="fa fa-angle-right"></i><i class="fa fa-angle-right"></i>
		</button>
	</div>


	<div class="pull-right">

		 <button class="btn  btn-sm btnBack " type="button" name="btnBack"
			id="btnBack" ng-click="fun_backTo_table()">
			<i class="fa fa-caret-left" aria-hidden="true"></i><i
				class="fa fa-caret-left" aria-hidden="true"></i> Back
		</button> 
		<c:if test="${CanDelete}">
		<button class="btn  btn-sm btn-danger btnDiscard" type="button"
			name="btnDelete" id="btnDelete" ng-click="fun_delete_form()">
			<i class="fa fa-trash-o"></i> Delete
		</button>
		</c:if>
		<c:if test="${canEdit}">
		<button class="btn  btn-sm btn-info savebtn" type="button"
			name="btnEdit" id="btnEdit" ng-click="fun_edit_form()">
			<i class="fa fa-edit"></i> Edit
		</button>
		</c:if>


	</div>
</div>