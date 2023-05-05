<div class="div_position" id="stockout_type_form_div" ng-show="show_type_form">
<div class="position_tittle">
		<h3>Select Stockout Type</h3>
	</div>
	<form class="form-horizontal frm_div_stock_in" id="stockout_type_form">
		<div class="">
			<md-radio-group ng-model="stockout_type"> 
					<c:forEach items="${stockouttypes}" var="stockouttypes">
							<md-radio-button id="rad_${stockouttypes.getStockOutTypeId()}" value="${stockouttypes.getStockOutTypeId()}" class="md-primary" >${stockouttypes.getName()}</md-radio-button> 
					</c:forEach>
			</md-radio-group>
		</div>
	</form>
</div>

