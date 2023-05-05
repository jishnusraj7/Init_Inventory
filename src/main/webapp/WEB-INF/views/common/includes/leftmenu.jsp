

<!-- Left side column. contains the sidebar -->

<%-- <%
	String company = request.getContextPath();
%> --%>


<aside class="main-sidebar" style="position: fixed;">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<c:if test="${(Settings==true) ? true : false}">


			<ul class="sidebar-menu" id="settings_sub_menu">

				<c:if test="${genrlPer}">
					<li class="treeview ${General ? 'active' : ' '}" ><a href="#"> <i
							class="fa fa-file-text"></i> <span>GENERAL</span> <i
							class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu menu-open" >

							<c:if
								test="${cmpnyPer.getCanView() && cmpnyPer.getIsViewApplicable()}">
								<li id="companyprofile" class="${Companyprofileclass ? 'active' : ' '}"><a
									href="<%=company%>/companyprofile/list"><i
										class="fa fa-cogs"></i>Company Profile</a></li>
							</c:if>
							<c:if
								test="${sysSetPer.getCanView() && sysSetPer.getIsViewApplicable()}">
								<li id="systemsettings_left_menu" class="${systemsettingsclass ? 'active' : ' '}" ><a
									href="<%=company%>/systemsettings/list"><i
										class="fa fa-cogs"></i>System Settings</a></li>
							</c:if>
							<!-- <li><a href="#"><i class="fa fa-cogs"></i>Unit Of
							Measurements</a></li> -->
						</ul></li>
				</c:if>




				<c:if test="${invntryPer}">

					<li class="treeview ${settings ? 'active' : ' '}"><a href="#"> <i class="fa fa-cog"></i>
							<span>SETUP</span> <i class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu menu-open" >


							<c:if
								test="${supPer.getCanView() && supPer.getIsViewApplicable()}">
								<li id="supplier_left_menu" class="${supplierclass ? 'active' : ' '}"><a
									href="<%=company%>/supplier/list"><i class="fa fa-truck"></i>Supplier</a></li>
							</c:if>
							
							<c:if
								test="${supPer.getCanView() && supPer.getIsViewApplicable()}">
								<li id="supplier_left_menu" class="${timeslotclass ? 'active' : ' '}"><a
									href="<%=company%>/timeslot/list"><i class="fa fa-clock-o"></i>Time slot</a></li>
							</c:if>
							
							
							
							<c:if
								test="${setarea.getCanView() && setarea.getIsViewApplicable()}">
								<li id="area_code_left_menu" class="${areacodeclass ? 'active' : ' '}"><a
									href="<%=company%>/areacode/list"><i class="fa fa-globe"></i>Area
										Code</a></li>
							</c:if>


							<c:if
								test="${depPer.getCanView() && depPer.getIsViewApplicable()}">
								<li id="department_left_menu" class="${departmentclass ? 'active' : ' '}"><a
									href="<%=company%>/department/list"><i class="fa fa-users"></i>Department</a></li>
							</c:if>
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${shopPer.getCanView() && shopPer.getIsViewApplicable()}">
									<li id="shops_left_menu" class="${shopsclass ? 'active' : ' '}" ><a href="<%=company%>/shops/list"><i
											class="fa fa-building-o"></i>Shop information</a></li>
								</c:if>
							</c:if>
							<c:if
								test="${saledep.getCanView() && saledep.getIsViewApplicable()}">
								<li id="sales_department_left_menu" class="${salesdepartclass ? 'active' : ' '}"><a
									href="<%=company%>/salesdepartment/list"><i
										class="fa fa-users"></i>Sales Department</a></li>
							</c:if>



							<%-- 		<c:if test="${itmCatPer.getCanView() && itmCatPer.getIsViewApplicable()}">
					<li id="item_category_left_menu"><a
						href="<%=company%>/itemcategory/list"><i class="fa fa-cogs"></i>Item
							Category</a></li></c:if> --%>


							<c:if
								test="${KitchPer.getCanView() && KitchPer.getIsViewApplicable()}">
								<li id="kitchen_left_menu" class="${kitchenclass ? 'active' : ' '}"><a
									href="<%=company%>/kitchen/list"><i class="fa fa-cutlery"></i>kitchen
								</a></li>
							</c:if>

							<c:if test="${(COMPANY_SESSION_INFO.id==0) ? false : true}">
								<c:if
									test="${setTerminal.getCanView() && setTerminal.getIsViewApplicable()}">
									<li id="terminal_left_menu" class="${terminalclass ? 'active' : ' '}"><a
										href="<%=company%>/terminal/list"><i class="fa fa-laptop"></i>Terminal
									</a></li>
								</c:if>

							</c:if>
							
							<c:if
								test="${pftCatPer.getCanView() && pftCatPer.getIsViewApplicable()}">
								<li id="profit_category_left_menu"  class="${profitclass ? 'active' : ' '}"  ><a
									href="<%=company%>/profitcategory/list"><i
										class="fa fa-percent"></i>Profit Category</a></li>
							</c:if>
							<%-- <c:if test="${itmPer.getCanView() && itmPer.getIsViewApplicable()}">
					<li id="item_master_left_menu"><a href="<%=company%>/itemmaster/list"><i
							class="fa fa-shopping-basket"></i>Item</a></li></c:if>
				<c:if test="${itemClassPer.getCanView() && itemClassPer.getIsViewApplicable()}">
					<li id="item_class_left_menu"><a href="<%=company%>/itemclass/list"><i
							class="fa fa-cogs"></i>Item Class</a></li></c:if>
				<c:if test="${itmPer.getCanView() && itmPer.getIsViewApplicable()}">
					<li id="item_master_list_left_menu"><a href="<%=company%>/itemslist/list"><i
							class="fa fa-cogs"></i>Set Selling Items</a></li></c:if> --%>
							<c:if
								test="${uomPer.getCanView() && uomPer.getIsViewApplicable()}">
								<li id="uom_left_menu" class="${uomclass ? 'active' : ' '}"  ><a href="<%=company%>/uom/list"><i
										class="fa fa-balance-scale"></i>Uom</a></li>
							</c:if>

							<c:if
								test="${curPer.getCanView() && curPer.getIsViewApplicable()}">
								<li id="currency_details_left_menu" class="${currencyclass ? 'active' : ' '}"  ><a
									href="<%=company%>/currencydetails/list"><i
										class="fa fa-money"></i>Currency Details</a></li>
							</c:if>


							<%-- 		<c:if test="${setUg.getCanView() && setUg.getIsViewApplicable()}">		
					<li id="user_group_left_menu"><a href="<%=company%>/usergroup/list"><i
							class="fa fa-users"></i>User Group</a></li></c:if>
							
				<c:if test="${setUsr.getCanView() && setUsr.getIsViewApplicable()}">		
					<li id="users_left_menu"><a href="<%=company%>/list"><i
							class="fa fa-user-plus"></i>Users</a></li></c:if> --%>

							<c:if
								test="${setRound.getCanView() && setRound.getIsViewApplicable()}">
								<li id="rounding_left_menu"  class="${roundingclass ? 'active' : ' '}"  ><a
									href="<%=company%>/rounding/list"><i class="fa fa-circle-o"></i>Rounding</a></li>
							</c:if>
							<%-- <c:if test="${(setOpn.canView == 1 && setOpn.isViewApplicable == 1)? true :false }">
					<li id="openingstock_left_menu"><a
						href="/mrp/OpeningStock/list"><i class="fa fa-cogs"></i>Opening
							Stock</a></li></c:if>	 --%>
							<c:if
								test="${settax.getCanView() && settax.getIsViewApplicable()}">
								<li id="tax_left_menu" class="${taxclass ? 'active' : ' '}"><a href="<%=company%>/tax/list"><i
										class="fa fa-book"></i>Tax</a></li>
							</c:if>
							<c:if test="${(COMPANY_SESSION_INFO.id==0) ? true : false}">
								<c:if
									test="${shopPer.getCanView() && shopPer.getIsViewApplicable()}">
									<li id="shops_left_menu" class="${shopsclass ? 'active' : ' '}" ><a href="<%=company%>/shops/list"><i
											class="fa fa-building-o"></i>Shops</a></li>
								</c:if>
							</c:if>
							
							
								<c:if
									test="${supPer.getCanView() && supPer.getIsViewApplicable()}">
									<li id="shops_left_menu" class="${prodcostclass ? 'active' : ' '}" ><a href="<%=company%>/prodcost/list"><i
											class="fa fa-money"></i>Production Cost</a></li>
								</c:if>
							
							<%-- <c:if test="${settax.getCanView() && settax.getIsViewApplicable()}">
					<li id="table_location_left_menu"><a
						href="<%=company%>/tablelocation/list"><i class="fa fa-th-large"></i>Table Location</a></li></c:if> --%>


						</ul></li>
				</c:if>





				<li class="treeview ${Products ? 'active' : ' '} "><a href="#"> <i
						class="fa fa fa-product-hunt"></i> <span>PRODUCTS</span> <i
						class="fa fa-angle-left pull-right"></i>
				</a>
					<ul class="treeview-menu menu-open" >

						<c:if
							test="${menu.getCanView() && menu.getIsViewApplicable()}">
							<li id="menu_left_menu" class="${menuclass ? 'active' : ' '}"  > <a href="<%=company%>/menu/list"><i
									class="fa fa-book"></i>Menu</a></li>
						</c:if>


						<c:if
							test="${itmPer.getCanView() && itmPer.getIsViewApplicable()}">
							<li id="item_master_left_menu" class="${itemmaster ? 'active' : ' '}" ><a
								href="<%=company%>/itemmaster/list"><i
									class="fa fa-shopping-basket"></i>Item</a></li>
						</c:if>
						
						<c:if
							test="${itmPer.getCanView() && itmPer.getIsViewApplicable()}">
							<li id="item_master_left_menu" class="${itemmasterBom ? 'active' : ' '}" ><a
								href="<%=company%>/bom/list"><i
									class="fa fa-shopping-basket"></i>Item BOM</a></li>
						</c:if>
						
						
						<%-- <c:if test="${itemClassPer.getCanView() && itemClassPer.getIsViewApplicable()}">
					<li id="item_class_left_menu"><a href="<%=company%>/itemclass/list"><i
							class="fa fa-cogs"></i>Item Class</a></li></c:if> --%>
						
						 <c:if test="${(version==0) ? true : false}">
						
						
						<c:if
							test="${itmPer.getCanView() && itmPer.getIsViewApplicable()}">
							<li id="item_master_list_left_menu" class="${itemmasterlistclass ? 'active' : ' '}" ><a
								href="<%=company%>/itemslist/list"><i class="fa fa-cogs"></i>Item
									Settings</a></li>
						</c:if>
						
						</c:if>
						
						<c:if
								test="${Choices.getCanView() && Choices.getIsViewApplicable()}">
								<li id="choices_left_menu"  class="${choicesclass ? 'active' : ' '}" ><a
									href="<%=company%>/choice/list"><i class="fa fa-houzz"></i>Choices
								</a></li>
							</c:if>


<c:if
							test="${setsaleitmch.getCanView() && setsaleitmch.getIsViewApplicable()}">
							<li id="combo_contenets_left_menu" class="${combocontentsclass ? 'active' : ' '}" ><a
								href="<%=company%>/combocontents/list"><i
									class="fa fa-shopping-cart"></i>Combo Contents</a></li>
						</c:if>





						<c:if
							test="${setsaleitmch.getCanView() && setsaleitmch.getIsViewApplicable()}">
							<li id="sale_item_choices_left_menu" class="${saleitemchoicesclass ? 'active' : ' '}" ><a
								href="<%=company%>/saleitemchoices/list"><i
									class="fa fa-houzz"></i>Sale Item Choices</a></li>
						</c:if>


						<c:if
							test="${itmCatPer.getCanView() && itmCatPer.getIsViewApplicable()}">
							<li id="item_category_left_menu" class="${itemcategoryclass ? 'active' : ' '}" ><a
								href="<%=company%>/itemcategory/list"><i class="fa fa-tags"></i>Item
									Category</a></li>
						</c:if>
						<c:if
							test="${itemClassPer.getCanView() && itemClassPer.getIsViewApplicable()}">
							<li id="item_class_left_menu" class="${itemclass ? 'active' : ' '}" ><a
								href="<%=company%>/itemclass/list"><i
									class="fa fa-cart-plus"></i>Item Class</a></li>
						</c:if>

					</ul></li>

              <c:if test="${(version==0) ? true : false}">



				<%-- 	<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">	
			<li class="treeview"><a href="#"> <i class="fa fa-wrench"></i>
					<span>Tools</span>   <i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu menu-open">
					<li id="master_import_left_menu"><a
						href="<%=company%>/masterimport/list"><i class="fa fa-cogs"></i>Master Import</a></li>
			</ul>
			</li>
			</c:if> --%>
				<c:if test="${invntryPer}">
					<li class="treeview ${Customer ? 'active' : ' '}"><a href="#"> <i class="fa fa-user"></i>
							<span>CUSTOMER</span> <i class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu menu-open" >
							<c:if
								test="${customerTypePer.getCanView() && customerTypePer.getIsViewApplicable()}">
								<li id="customertypes_leftmenu" class="${customertypeclass ? 'active' : ' '}" ><a
									href="<%=company%>/customertypes/list"><i
										class="fa fa-user-secret"></i>Customer Types </a></li>
							</c:if>
							<c:if
								test="${customersPer.getCanView() && customersPer.getIsViewApplicable()}">
								<li id="customers_leftmenu" class="${customerclass ? 'active' : ' '}" ><a
									href="<%=company%>/customers/list"><i class="fa fa-users"></i>Customers</a></li>
							</c:if>
							<!-- <li><a href="#"><i class="fa fa-cogs"></i>Unit Of
							Measurements</a></li> -->
						</ul></li>
				</c:if>



				<li class="treeview ${Promotions ? 'active' : ' '}"><a href="#"> <i class="fa fa-money"></i>
						<span>PROMOTIONS</span> <i class="fa fa-angle-left pull-right"></i>
				</a>
					<ul class="treeview-menu menu-open">
						<c:if
							test="${discountPer.getCanView() && discountPer.getIsViewApplicable()}">
							<li id="discounts_leftmenu" class="${discountsclass ? 'active' : ' '}" ><a
								href="<%=company%>/discounts/list"><i class="fa fa-inr"></i>Discount
							</a></li>
						</c:if>
						<c:if
							test="${vouchersPer.getCanView() && vouchersPer.getIsViewApplicable()}">
							<li id="vouchers_leftmenu" class="${vouchersclass ? 'active' : ' '}"><a
								href="<%=company%>/vouchers/list"><i
									class="fa fa-credit-card"></i>Vouchers</a></li>
						</c:if>
						<c:if
							test="${customersPer.getCanView() && customersPer.getIsViewApplicable()}">
							<li id="reasons_leftmenu" class="${reasonsclass ? 'active' : ' '}" ><a
								href="<%=company%>/reasons/list"><i class="fa fa-archive"></i>Reasons</a></li>
						</c:if>
						<!-- <li><a href="#"><i class="fa fa-cogs"></i>Unit Of
							Measurements</a></li> -->
					</ul></li>





				<c:if test="${payPer}">
					<li class="treeview ${Payment ? 'active' : ' '} "><a href="#"> <i
							class="fa fa-credit-card"></i> <span>PAYMENT</span> <i
							class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu menu-open" >
							<c:if
								test="${crdcard.getCanView() && crdcard.getIsViewApplicable()}">
								<li id="credit_card_leftmenu" class="${paymentclass ? 'active' : ' '}"  ><a
									href="<%=company%>/creditcard/list"><i
										class="fa fa-credit-card"></i>Credit Card </a></li>
							</c:if>


						</ul></li>
				</c:if>






				<li class="treeview ${Users ? 'active' : ' '} "><a href="#"> <i class="fa  fa-users"></i>
						<span>USERS</span> <i class="fa fa-angle-left pull-right"></i>
				</a>
					<ul class="treeview-menu menu-open" >

						<c:if test="${setUg.getCanView() && setUg.getIsViewApplicable()}">
							<li id="user_group_left_menu" class="${usergroupclass ? 'active' : ' '}"><a
								href="<%=company%>/usergroup/list"><i class="fa fa-users"></i>User
									Group</a></li>
						</c:if>

						<c:if
							test="${setUsr.getCanView() && setUsr.getIsViewApplicable()}">
							<li id="users_left_menu" class="${usersclass ? 'active' : ' '}" ><a href="<%=company%>/list"><i
									class="fa fa-user-plus"></i>Users</a></li>
						</c:if>



					</ul></li>


				<li class="treeview ${People ? 'active' : ' '}  "><a href="#"> <i class="fa fa-user"></i>
						<span>PEOPLE</span> <i class="fa fa-angle-left pull-right"></i>
				</a>
					<ul class="treeview-menu menu-open">
						<c:if
							test="${setEmployeeCatPer.getCanView() && setEmployeeCatPer.getIsViewApplicable()}">
							<li id="employeecategory_left_menu" class="${employeecatclass ? 'active' : ' '}" ><a
								href="<%=company%>/employeecategory/list"><i
									class="fa fa-user-plus"></i>Employee Category</a></li>
						</c:if>
						<%-- <c:if test="${itemClassPer.getCanView() && itemClassPer.getIsViewApplicable()}">
					<li id="item_class_left_menu"><a href="<%=company%>/itemclass/list"><i
							class="fa fa-cogs"></i>Item Class</a></li></c:if> --%>
						<c:if
							test="${setEmployee.getCanView() && setEmployee.getIsViewApplicable()}">
							<li id="employee_list_left_menu" class="${employeeclass ? 'active' : ' '}" ><a
								href="<%=company%>/employee/list"><i class="fa fa-users"></i>Employee</a></li>
						</c:if>

						<c:if
							test="${setShopShiftPer.getCanView() && setShopShiftPer.getIsViewApplicable()}">
							<li id="shopshift_left_menu" class="${shopshiftclass ? 'active' : ' '}" ><a
								href="<%=company%>/shopshift/list"><i class="fa fa-clock-o"></i>Shop
									Shift</a></li>
						</c:if>


					</ul></li>

				<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
					<li class="treeview  ${Serving ? 'active' : ' '}   "><a href="#"> <i
							class="fa fa-th-large"></i> <span>SERVING</span> <i
							class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu menu-open" > 
							<c:if
								test="${servingTablePer.getCanView() && servingTablePer.getIsViewApplicable()}">
								<li id="table_location_left_menu" class="${tablelocationclass ? 'active' : ' '}" ><a
									href="<%=company%>/tablelocation/list"><i
										class="fa fa-th-large"></i>Table Location</a></li>
							</c:if>



						</ul></li>
				</c:if>


          </c:if>

			</ul>
		</c:if>

		<c:if test="${(Store==true) ? true : false}">
			<ul class="sidebar-menu" id="store_sub_menu">
			
			<c:if test="${(combineMode==0)?true:false }">
				<li class="treeview ${Purchase ? 'active' : ' '}"><a href="#"> <i
						class="fa fa-shopping-cart"></i> <span>PURCHASE</span> <i
						class="fa fa-angle-left pull-right"></i>
				</a>
					<ul class="treeview-menu menu-open">

						<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
							<c:if
								test="${strPreqPer.getCanView() && strPreqPer.getIsViewApplicable()}">
								<li id="pr_left_menu" class="${prclass ? 'active' : ' '}"><a
									href="<%=company%>/purchaserequesthdr/list"> <i
										class="fa fa-cart-plus"></i> <span>Purchase Request to HQ</span>
								</a></li>
							</c:if>
						</c:if>
						<c:if test="${(COMPANY_SESSION_INFO.id==0) ? true : false}">

							<c:if
								test="${strPreqPer1.getCanView() && strPreqPer1.getIsViewApplicable()}">
								<li id="rpr_left_menu" class="${rprclass ? 'active' : ' '}" ><a
									href="<%=company%>/remoterequesthdr/list"> <i
										class="fa fa-cart-arrow-down"></i> <span>Remote Request</span>
								</a></li>

							</c:if>
						</c:if>

						<c:if
							test="${strProdPer.getCanView() && strProdPer.getIsViewApplicable()}">
							<li id="po_left_menu" class="${poclass ? 'active' : ' '}"><a
								href="<%=company%>/purchaseorderhdr/list"> <i
									class="fa fa-file"></i> <span>Purchase Order</span>
							</a></li>
						</c:if>

					</ul></li></c:if>

				<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
					<li class="treeview ${Stock ? 'active' : ' '}"><a href="#"> <i
							class="fa fa-shopping-basket"></i> <span>STOCK</span> <i
							class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu menu-open">

			              <c:if test="${(combineMode==0)?true:false }">
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${strInPer.getCanView() && strInPer.getIsViewApplicable()}">
									<li id="stockin_left_menu" class="${stockinclass ? 'active' : ' '}" ><a
										href="<%=company%>/stockin/list"> <i
											class="fa fa-level-down"></i> <span>Stock In</span>
									</a></li>
								</c:if>
							</c:if>
							</c:if>
							
							   <c:if test="${(combineMode==1)?true:false }">
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${strInPer.getCanView() && strInPer.getIsViewApplicable()}">
									<li id="stockin_left_menu" class="${stockinclass ? 'active' : ' '}" ><a
										href="<%=company%>/stockin/list"> <i
											class="fa fa-level-down"></i> <span>Purchase Order</span>
									</a></li>
								</c:if>
								<c:if
									test="${strInPer.getCanView() && strInPer.getIsViewApplicable()}">
									<li id="stockrequest_left_menu" class="${stockoutclass0 ? 'active' : ' '}"  ><a
										href="<%=company%>/stockout/list?isStockOut=0"> <i
											class="fa fa-share"></i> <span>Stock Request</span>
									</a></li>
								</c:if>
							</c:if>
							</c:if>
							
							
							
							
						 <c:if test="${(combineMode==0)?true:false }">
							
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${strOutPer.getCanView() && strOutPer.getIsViewApplicable()}">
									<li id="stockrequest_left_menu" class="${stockoutclass0 ? 'active' : ' '}"  ><a
										href="<%=company%>/stockout/list?isStockOut=0"> <i
											class="fa fa-share"></i> <span>Stock Request</span>
									</a></li>
								</c:if>
							</c:if>
							</c:if>
							
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${strOutPer.getCanView() && strOutPer.getIsViewApplicable()}">
									<li id="stockout_left_menu" class="${stockoutclass1 ? 'active' : ' '}"><a
										href="<%=company%>/stockout/list?isStockOut=1"> <i
											class="fa fa-reply"></i> <span>Stock Out</span>
									</a></li>
								</c:if>
							</c:if>
							
							
							<%-- <c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${strtransPer.getCanView() && strtransPer.getIsViewApplicable()}">
									<li id="stocktranfer_left_menu" class="${stocktranferclass ? 'active' : ' '}"><a
										href="<%=company%>/StockTranfer/list"> <i
											class="fa fa-reply"></i> <span>Stock Transfer</span>
									</a></li>
								</c:if>
							</c:if> --%>
							
							
						<%-- 	<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${strAdjustPer.getCanView() && strAdjustPer.getIsViewApplicable()}">
									<li id="stockadjustment_left_menu" class="${stockadjstclass ? 'active' : ' '}" ><a
										href="<%=company%>/stockadjustment/list"> <i
											class="fa fa-refresh"></i> <span>Stock Adjustments</span>
									</a></li>
								</c:if>
							</c:if> --%>
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${strDisposePer.getCanView() && strDisposePer.getIsViewApplicable()}">
									<li id="stockdisposal_left_menu" class="${stockdispclass ? 'active' : ' '}"><a
										href="<%=company%>/stockdisposal/list"> <i
											class="fa fa-recycle"></i> <span>Stock Disposal</span>
									</a></li>
								</c:if>
							</c:if>
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${strSummaryPer.getCanView() && strSummaryPer.getIsViewApplicable()}">
									<li id="stockdisposal_left_menu" class="${stocksummaryclass ? 'active' : ' '}"><a
										href="<%=company%>/stocksummary/list"> <i
											class="fa fa-recycle"></i> <span>Stock Summary</span>
									</a></li>
								</c:if>
							</c:if>

						</ul></li>


				</c:if>


			</ul>
		</c:if>
		
		
		
			<c:if test="${(Production==true) ? true : false}">
			<ul class="sidebar-menu" id="production_sub_menu">
			
				<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
					<li class="treeview ${Production ? 'active' : ' '}"><a href="#"> <i
							class="fa fa-shopping-basket"></i> <span>PRODUCTION</span> <i
							class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu menu-open">

			              <c:if test="${(combineMode==1)?true:false }">
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${setProdOrdr.getCanView() && setProdOrdr.getIsViewApplicable()}">
									<li id="planning_left_menu" class="${planningclass ? 'active' : ' '}" ><a
										href="<%=company%>/planning/list">  <i class="fa fa-building-o"></i>
										 <span>Production Order</span>
									</a></li>
								</c:if>
							</c:if>
						
							
							  
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${setProdProcess.getCanView() && setProdProcess.getIsViewApplicable()}">
									<li id="planning_left_menu_daily_prod" class="${productionprocessingclass ? 'active' : ' '}" ><a
										href="<%=company%>/production/listP"> <i
							class="fa fa-industry"></i> <span>Production Processing</span>
									</a></li>
								</c:if>
								<c:if
									test="${prdDlyPer.getCanView() && prdDlyPer.getIsViewApplicable()}">
									<li id="planning_left_menu_daily_prod" class="${dailyproductionclass ? 'active' : ' '}"  ><a
										href="<%=company%>/production/list"> <i
							class="fa fa-industry"></i> <span>Daily Production</span>
									</a></li>
								</c:if>
							</c:if>
							</c:if>
							
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${prdDlyPer.getCanView() && prdDlyPer.getIsViewApplicable()}">
									<li id="bomanalysis_left_menu" class="${bomanalysis ? 'active' : ' '}"><a
										href="<%=company%>/bomanalysis/list"> <i
							class="fa fa-files-o"></i> <span>Bom Analysis</span>
									</a></li>
								</c:if>
							</c:if>
							
							
                    	<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${strtransPer.getCanView() && strtransPer.getIsViewApplicable()}">
									<li id="stocktranfer_left_menu" class="${stocktranferclass ? 'active' : ' '}" ><a
										href="<%=company%>/StockTranfer/list"><i class="fa fa-reply"></i>
										 <span>Stock Transfer</span>
									</a></li>
								</c:if>
							</c:if>
							<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
								<c:if
									test="${strTakingPer.getCanView() && strTakingPer.getIsViewApplicable()}">
									<li id="stocktaking_left_menu" class="${stocktakingclass ? 'active' : ' '}"><a
										href="<%=company%>/stocktaking/list">  <i
								class="fa fa-clipboard"></i> <span>Stock Taking</span>
									</a></li>
								</c:if>
							</c:if>

						</ul></li>


				</c:if>


			</ul>
		</c:if>
		
		
		

		
		<c:if test="${(Sale==true) ? true : false}">
			<ul class="sidebar-menu" id="sale_sub_menu">
				<c:if
					test="${salDlyPer.getCanView() && salDlyPer.getIsViewApplicable()}">
					<li id="dailysale_left_menu"><a href="#"> <i
							class="fa fa-file-text"></i> <span>Daily Sale</span>
					</a></li>
				</c:if>
			</ul>
		</c:if>
		<c:if test="${(Report==true) ? true : false}">
			<ul class="sidebar-menu" id="report_sub_menu">
				<c:if test="${repStk.getCanView() && repStk.getIsViewApplicable()}">
					<li id="currentstock_left_menu"><a
						href="<%=company%>/itemstock/currentstock"> <i
							class="fa fa-file-text"></i> <span>STOCK REPORTS</span>
					</a></li>
				</c:if>
				<c:if test="${repPrd.getCanView() && repPrd.getIsViewApplicable()}">
					<li id="productionreport_left_menu"><a
						href="<%=company%>/productionreport/list"> <i
							class="fa fa-file-text"></i> <span>PRODUCTION REPORTS</span>
					</a></li>
				</c:if>
				
				
			 <c:if test="${(version==0) ? true : false}">
				
				
				<c:if
					test="${repSale.getCanView() && repSale.getIsViewApplicable()}">
					<li><a href="#"> <i class="fa fa-file-text"></i> <span>SALES
								REPORTS</span>
					</a></li>
				</c:if>
				<c:if test="${repOth.getCanView() && repOth.getIsViewApplicable()}">
					<li><a href="#"> <i class="fa fa-file-text"></i> <span>OTHER
								REPORTS</span>
					</a></li>
				</c:if>
				
				</c:if>
				
			</ul>
		</c:if>
		<!-- 	<ul class="sidebar-menu" id="sale_sub_menu">


		</ul> -->
		<c:if test="${(Accounts==true) ? true : false}">
			<ul class="sidebar-menu" id="account_sub_menu">
				<c:if
					test="${accDlyExpPer.getCanView() && accDlyExpPer.getIsViewApplicable()}">
					<li id="dailyexpenses_left_menu"><a
						href="<%=company%>/dailyexpenses/list"> <i
							class="fa fa-file-text"></i> <span>Daily Expenses</span> <i
							class="fa fa-angle-left pull-right"></i>
					</a></li>
				</c:if>
				<c:if
					test="${accExpDatPer.getCanView() && accExpDatPer.getIsViewApplicable()}">
					<li><a href="#"> <i class="fa fa-file-text"></i> <span>Export
								ACC. data</span>

					</a></li>
				</c:if>

			</ul>
		</c:if>

	</section>
	<!-- /.sidebar -->
</aside>
