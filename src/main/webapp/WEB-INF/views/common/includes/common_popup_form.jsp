<!-- Button trigger modal -->
<style>

 #DataTables_Table_1_wrapper  .dataTables_scrollBody {
    margin-top: 40px !important;
    overflow: inherit  !important; 

}


 #DataTables_Table_1_wrapper .dataTables_scrollHeadInner thead {
    position: fixed !important; 
        width: 375px !important; 
        
        z-index: 0;
} 


    


md-radio-button .md-off, md-radio-button .md-on {

    width: 18px !important; 
    height: 18px !important; 

}

md-radio-group {
    font-size: 14px !important; 
}


</style>

<div ng-controller="ExcellReportController as item">



<!-- Modal -->
<div class="modal fade" id="importDataModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-lg " role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Export Data</h4>
			</div>
		
				<div class="modal-body col-md-12">
					<div class="col-md-6">
						<input name="export_keyword" type="text" id="export_keyword"
							class="rdinputtext" style="display: none;"><input
							type="text" id="order" name="order[]" value="id"
							style="display: none;"><input type="text" name="tbl_name"
							id="tbl_name" value="reasons" style="display: none;">
						<div id="example2_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-6"></div>
								<div class="col-sm-6">
								<!-- 	<div id="example2_filter" class="dataTables_filter">
										<label><div class="left-inner-addon">
												<i class="fa fa-search"></i><input type="search"
													class="form-control input-sm" placeholder="Search..."
													aria-controls="example2">
												<div></div>
											</div></label>
									</div> -->
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12" style="overflow-y: scroll; height: 300px;width: 419px;">


									<table datatable="" dt-options="item.dtOptions1"
										dt-columns="item.dtColumns1" dt-instance="item.dtInstance1"
										class=" table dataClass"  ></table>


								</div>
							</div>
							<div class="row">
								<div class="col-sm-5"></div>
								<div class="col-sm-7"></div>
							</div>
						</div>
					</div>
					<div class="col-md-6"   style="overflow-y: scroll; height: 300px;" >
						<input type="hidden" name="tbl_columns_count"
							id="tbl_columns_count" value="5"> <input type="hidden"
							name="tbl_name" id="tbl_name" value="reasons"> <input
							type="hidden" name="tbl_columns" id="tbl_columns"
							value="tbl_columns">
						<table class="table table-bordered table-striped" id="sorttable">
							<thead>
								<tr>
									<th><i class="fa fa-arrow-circle-o-down"
										aria-hidden="true"></i> SORT BY</th>
									<th><i class="fa fa-arrow-circle-o-down"
										aria-hidden="true"></i> ORDER BY</th>
									<th style="cursor: pointer;"><i class="fa fa-plus-circle"
										aria-hidden="true" id="createsortrow" ng-click="addRow()"></i></th>
								</tr>
							</thead>
							<tbody >


								<tr ng:repeat="item in DisplayListRow.items">

									<td style="width: 150px;"><select class=" form-control  "
										ng-model="item.display"
										ng-options="v.SI as v.COLUMN_NAME for v in displayList">
									</select></td>

									<td style="width: 100px; height: 10px;">
										 <md-radio-group ng-model="item.order" layout="row">
										<md-radio-button value="1" class="md-raised">ASC</md-radio-button> <md-radio-button class="md-raised"
											value="2">DESC</md-radio-button> </md-radio-group>

									</td>

									<td style="width: 10px;"><a href
										ng:click="removeRow($index)" class="btn btn-small"><i
											class="fa fa-minus "></i> </a></td>
								</tr>




							</tbody>
						</table>
					
				</div>


			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					ng-click="exportDatatoExcel()">Export</button>
			</div>
		</div>
	</div>
</div>	</div>