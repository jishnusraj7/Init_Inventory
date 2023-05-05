
function generateReorderItemGrid(gridRef) {

	var jsonObj = [];

	var columnLength = $(gridRef + " tr td").length;

	// fetch all td
	$(gridRef + ' td').each(function() {

		item = {}
		item["data"] = $(this).html();
		jsonObj.push(item);
	});

	$(gridRef)
			.dataTable(
					{
						"ajax" : '../ViewStockDetails/json',
						bProcessing : true,
						"pagingType" : "simple",
						"lengthChange" : false,
						"bInfo" : false,
						"pageLength" : 4,
						"bFilter" : false,
						"sDom" : '<"row view-filter"<"col-sm-12"<"pull-left"l><"pull-right"f><"clearfix">>>t<"row view-pager col-sm-12"<""<"text-right"ip>>>',
						"columnDefs" : [
								{
									"targets" : [ 0 ],
									"visible" : false,
									"searchable" : false,
									"sortable" : false
								},
								{
									"targets" : [ 2 ],
									"sWidth" : "35.1667px",
									"sortable" : false,
									"render" : function(data, type, row) {
										if (data == '') {
											data = 0;
										}
										return '<span class="badge bg-important">'
												+ data + 'kg</span>';
									},
								},
								{
									"targets" : [ 3 ],
									"sWidth" : "35.1667px",
									"sortable" : false,
									"render" : function(data, type, row) {
										if (data == '') {
											data = 0;
										}
										return '<span class="badge bg-info">'
												+ data + 'kg</span>';
									},
								}, ],
						columns : jsonObj,
						'language' : {
							"sInfo" : "_END_ / _TOTAL_",
							"sInfoEmpty" : "0/0",
							"lengthMenu" : "Show _MENU_ ",
							"search" : '<div class="left-inner-addon "><i class="fa fa-search"></i>_INPUT_<div>',
							"searchPlaceholder" : "Search...",
							"paginate" : {
								"previous" : '<i class="fa fa-angle-left"></i>',
								"next" : '<i class="fa fa-angle-right"></i>',
							},
							"infoFiltered" : "Filtered from _MAX_",
						},

					});
}
