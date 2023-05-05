<div class="" id="department_form_div" ng-show="show_form">
		<div class="col-md-12 spanerrors spanerrorstab1" id="alert_required" style="display: none;color: red;" > *Fill the required field*</div>

	<form class="form-horizontal" id="department_form">
		
		<div class="">
			<div class="row">
				<div class="col-md-12">
					<md-content> <md-tabs md-dynamic-height
						md-border-bottom md-selected="selectedIndexTab"> <md-tab
						label="GENERAL "> <md-content class="md-padding">
					<div class="col-md-12 employeetabsdiv" id="employeetabs1div"
						style="display: block;">

						<div class="col-md-12 addmargin" id="form_div_code">

							<div class="col-md-4 addstyle">
								<label  class="control-label code-lbl-font-size" ><spring:message code="common.code"></spring:message> <span class="mandatory">*</span></label>
							</div>
							<div class="col-md-4">
								<div class="input-group">
									<input class="form-control required" ng-disabled="disable_code" ng-change="isCodeExistis(formData.code)"
										ng-model="formData.code" capitalize  type="text" id="code" name="code"
										placeholder="Code">
								
								<span class="input-group-addon" id="form_div_code_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Code cannot be left blank"></i></span>
							</div></div>
							<div class="col-sm-4">
		<span ng-bind="existing_code" class="existing_code_lbl"
				ng-hide="hide_code_existing_er"></span>
	</div>
						</div>

					

					<div class="col-md-12 addmargin" id="form_div_name">
						<div class="col-md-4 addstyle">
							<label  class="control-label code-lbl-font-size"><spring:message
			code="common.name"></spring:message> <span class="mandatory">*</span></label>
						</div>
						<div class="col-md-4"><div class="input-group">
							<input maxlength="50" id="name" ng-disabled="disable_all" ng-model="formData.name"
								name="name" class="form-control required" placeholder="Name">
								<span class="input-group-addon" id="form_div_name_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Name cannot be left blank"></i></span>
							</div></div>
						</div>
						
					

					<div class="col-md-12 addmargin">
						<div class="col-md-4 addstyle">
							<label><spring:message
			code="common.description"></spring:message></label>
						</div>
						<div class="col-md-4">
							<textarea maxlength="250" ng-disabled="disable_all" ng-model="formData.description"
								name="description" id="description" class="form-control"></textarea>
						</div>
					</div>

					<div class="col-md-12 addmargin" id="form_div_shift_type">
						<div class="col-md-4 addstyle">
							<label>Shift Type <span class="mandatory">*</span></label>
						</div>
						<div class="col-md-4"><div class="input-group">
							<select name="shift_type" id="shift_type" class="form-control required" ng-model="formData.shift_type" 
								ng-disabled="disable_all">
								<option value="">Select</option>
								<option value="1">Continues</option>
								<option value="2">Split shift</option>


							</select><span class="input-group-addon" id="form_div_shift_type_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Shift Type cannot be left blank"></i></span>
							</div>
						</div>
						
					</div>
					<div class="col-md-12 addmargin">
						<div class="col-md-4 addstyle">
							<label>Allow Unscheduled Unpaid Breaks</label>
						</div>
						<div class="col-md-6">
							<div class="">

								<md-checkbox type="checkbox" 
									ng-true-value="true" ng-false-value="false" ng-model="formData.allow_unscheduled_unpaid_breaks"
									ng-disabled="disable_all" aria-label="setasinactive"
									class="chck_box_div"></md-checkbox>
								<!-- <label id="check1">
                            <div class="icheckbox_flat-green" aria-checked="false" aria-disabled="false" style="position: relative;"><input type="checkbox" class="flat-red" name="allow_unscheduled_unpaid_breaks" id="allow_unscheduled_unpaid_breaks" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins></div>                       
                        </label> -->
							</div>
						</div>
					</div>
					<div class="col-md-12 addmargin">
						<div class="col-md-4 addstyle">
							<label>Overtime Is Payable</label>
						</div>
						<div class="col-md-6">
							<div class="">

								<md-checkbox type="checkbox" 
									ng-true-value="true" ng-false-value="false" ng-model="formData.overtime_is_payable"
									ng-disabled="disable_all" aria-label="setasinactive"
									class="chck_box_div"></md-checkbox>
								<!--                         <label id="overtime">
                            <div class="icheckbox_flat-green" aria-checked="false" aria-disabled="false" style="position: relative;"><input type="checkbox" class="flat-red" name="overtime_is_payable" id="overtime_is_payable" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins></div>
                           
                        </label> -->
							</div>
						</div>
					</div>
					<div class="col-md-12 addmargin" id="minimum_overtime_limit_row"
						 ng-show="formData.overtime_is_payable">
						<div class="col-md-4 addstyle">
							<label>Minimum Overtime Limit&nbsp;&nbsp;</label>
						</div>
						<div class="col-md-2">
							<select name="minimum_overtime_limit[]" ng-disabled="disable_all"    
								id="minimum_overtime_limit1" class="form-control">
								<option value="00">00</option>
								<option value="01">01</option>
								<option value="02">02</option>
								<option value="03">03</option>
								<option value="04">04</option>
								<option value="05">05</option>
								<option value="06">06</option>
								<option value="07">07</option>
								<option value="08">08</option>
								<option value="09">09</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
								<option value="13">13</option>
								<option value="14">14</option>
								<option value="15">15</option>
								<option value="16">16</option>
								<option value="17">17</option>
								<option value="18">18</option>
								<option value="19">19</option>
								<option value="20">20</option>
								<option value="21">21</option>
								<option value="22">22</option>
								<option value="23">23</option>

							</select>
						</div>
						<div class="col-md-2">
							<select name="minimum_overtime_limit[]" ng-disabled="disable_all"
								id="minimum_overtime_limit2" class="form-control"  ng-model="formData.minimum_overtime_limit2">
								<option value="00">00</option>
								<option value="01">01</option>
								<option value="02">02</option>
								<option value="03">03</option>
								<option value="04">04</option>
								<option value="05">05</option>
								<option value="06">06</option>
								<option value="07">07</option>
								<option value="08">08</option>
								<option value="09">09</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
								<option value="13">13</option>
								<option value="14">14</option>
								<option value="15">15</option>
								<option value="16">16</option>
								<option value="17">17</option>
								<option value="18">18</option>
								<option value="19">19</option>
								<option value="20">20</option>
								<option value="21">21</option>
								<option value="22">22</option>
								<option value="23">23</option>
								<option value="24">24</option>
								<option value="25">25</option>
								<option value="26">26</option>
								<option value="27">27</option>
								<option value="28">28</option>
								<option value="29">29</option>
								<option value="30">30</option>
								<option value="31">31</option>
								<option value="32">32</option>
								<option value="33">33</option>
								<option value="34">34</option>
								<option value="35">35</option>
								<option value="36">36</option>
								<option value="37">37</option>
								<option value="38">38</option>
								<option value="39">39</option>
								<option value="40">40</option>
								<option value="41">41</option>
								<option value="42">42</option>
								<option value="43">43</option>
								<option value="44">44</option>
								<option value="45">45</option>
								<option value="46">46</option>
								<option value="47">47</option>
								<option value="48">48</option>
								<option value="49">49</option>
								<option value="50">50</option>
								<option value="51">51</option>
								<option value="52">52</option>
								<option value="53">53</option>
								<option value="54">54</option>
								<option value="55">55</option>
								<option value="56">56</option>
								<option value="57">57</option>
								<option value="58">58</option>
								<option value="59">59</option>

							</select>
						</div>

					</div>

					<div class="col-md-12 addmargin">
						<div class="col-md-4 addstyle">
							<label>Is Active&nbsp;&nbsp;</label>
						</div>
						<div class="col-md-6">
							<div class="">
								<md-checkbox type="checkbox" 
									ng-true-value="true" ng-false-value="false" ng-model="formData.is_active"
									ng-disabled="disable_all" aria-label="setasinactive"
									class="chck_box_div"></md-checkbox>

								<!--  <label id="check2">
                   <div class="icheckbox_flat-green" aria-checked="false" aria-disabled="false" style="position: relative;"><input type="checkbox" class="flat-red" name="is_active" id="is_active" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins></div>
                </label> -->

							</div>
						</div>

					</div>
				</div>


				</md-content>
				</md-tab>
				<md-tab label="TIME SHEDULE"> <md-content
					class="md-padding">


				<div class="col-md-12 employeetabsdiv" id="employeetabs2div"
					style="display: block;">

					<div class="col-md-12 addmargin">

						<div class="col-md-4">
							<div class="col-md-12" >
								<label>Start Time <span class="mandatory">*</span>
								</label> <label><span class="spanerrors spanerrorstab2"
									id="alert_start_time"><span><i
											class="fa fa-exclamation-circle" aria-hidden="true"
											title="Start time cannot be left blank"></i></span></span></label> 
							</div>
							<div class="col-md-6" id="form_div_start_time">
							<div class="input-group">
								<select name="start_time[]" id="start_time" 
									ng-disabled="disable_all" class="form-control required">
									<option value="">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>

								</select>	<span class="input-group-addon" id="form_div_start_time_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Start Time  cannot be left blank"></i></span>
							</div>							</div>
							
							<div class="col-md-6">
								<select name="start_time[]" id="start_time_min"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start_time_min" >
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>

								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="col-md-12">
								<label>Lenience Duration Before Shift Start Time </label>
							</div>
							<div class="col-md-6">
								<select name="before_start[]" id="start3"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start3">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>

								</select>
							</div>
							<div class="col-md-6">
								<select name="before_start[]" id="start4"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start4">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>

								</select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="col-md-12">
								<label>Lenience Duration After Shift Start Time</label>
							</div>
							<div class="col-md-6">
								<select name="after_start[]" id="start5"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start5">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>

								</select>
							</div>
							<div class="col-md-6">
								<select name="after_start[]" id="start6"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start6">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>
								</select>
							</div>
							<div id="alertafter_start"
								class="errordiv col-md-12 spanerrors spanerrorstab2">Allowed
								time exceeds the time limit</div>
						</div>

					</div>

					<div class="col-md-12 addmargin extramargin"></div>
					<div class="col-md-12 addmargin">

						<div class="col-md-4">
							<div class="col-md-12" >
								<label>End Time <span class="mandatory">*</span>
								</label> <label><span class="spanerrors spanerrorstab2"
									id="alert_end_time"><span><i
											class="fa fa-exclamation-circle" aria-hidden="true"
											title="End time cannot be left blank"></i></span></span></label>
							</div>
							<div class="col-md-6" >
							<div class="input-group" id="form_div_end_time">
								<select name="end_time[]" id="end_time"
									ng-disabled="disable_all" class="form-control required" >
									<option value="">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>

								</select><span class="input-group-addon" id="form_div_end_time_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="End Time  cannot be left blank"></i></span>
							</div>							</div>
						
							<div class="col-md-6">
								<select name="end_time[]" id="end_time_min"
									ng-disabled="disable_all" class="form-control" ng-model="formData.end_time_min">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>

								</select>
							</div>
						</div>

						<div class="col-md-4">
							<div class="col-md-12">
								<label>Lenience Duration Before Shift End Time </label>
							</div>
							<div class="col-md-6">
								<select name="before_end[]" id="start13"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start13">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>

								</select>
							</div>
							<div class="col-md-6">
								<select name="before_end[]" id="start14"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start14">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>

								</select>
							</div>
							<div id="alertbefore_end"
								class="errordiv spanerrors spanerrorstab2">Allowed time
								before start must be less than start time</div>
						</div>

						<div class="col-md-4">
							<div class="col-md-12">
								<label>Lenience Duration After Shift End Time</label>
							</div>
							<div class="col-md-6">
								<select name="after_end[]" id="start15"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start15">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>

								</select>
							</div>
							<div class="col-md-6">
								<select name="after_end[]" id="start16"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start16">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>

								</select>
							</div>
					<div id="alertafter_end" class="errordiv spanerrors spanerrorstab2" style="display: none;"></div>
						</div>

					</div>
					<div class="col-md-12 addmargin extramargin"></div>
					<div class="col-md-12 addmargin">

						<div class="col-md-4">
							<div class="col-md-12">
								<label>Interval Start Time</label>
							</div>

							<div class="col-md-6">
								<select name="interval_start_time[]" id="start7"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start7">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>

								</select>
							</div>

							<div class="col-md-6">
								<select name="interval_start_time[]" id="start8"
									ng-disabled="disable_all" class="form-control"  ng-model="formData.start8">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>

								</select>
							</div>

						</div>



						<div class="col-md-4">
							<div class="col-md-12">
								<label>Interval End Time</label>
							</div>
							<div class="col-md-6">
								<select name="interval_end_time[]" id="start9"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start9">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>

								</select>
							</div>
							<div class="col-md-6">
								<select name="interval_end_time[]" id="start10"
									ng-disabled="disable_all" class="form-control" ng-model="formData.start10">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>

								</select>
							</div>
						</div>

						<div class="col-md-4">
							<div class="col-md-12">
								<label>Interval Is Payable</label>
							</div>
							<div class="form-group col-md-12">

								<md-checkbox type="checkbox"  ng-model="formData.interval_is_payable"
									ng-true-value="true" ng-false-value="false"
									ng-disabled="disable_all" aria-label="setasinactive"
									class="chck_box_div"></md-checkbox>
								<!--    <label id="check3">
                    <div class="icheckbox_flat-green" aria-checked="false" aria-disabled="false" style="position: relative;"><input type="checkbox" class="flat-red" name="interval_is_payable" id="interval_is_payable" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins></div> 
                    &nbsp;&nbsp;Interval Is Payable
                  </label> -->
							</div>
						</div>

					</div>
				</div>

<div id="alertbefore_start" class="errordiv spanerrors spanerrorstab2" style="display: none;"></div>


				</md-content> </md-tab>

				</md-tabs>
				</md-content>

			</div>
		</div>
</div>


</form>
</div>
