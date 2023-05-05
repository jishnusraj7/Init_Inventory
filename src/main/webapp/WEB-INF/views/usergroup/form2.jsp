<div class="" id="user_group_form2_div" ng-show="show_form2">

	<form class="form-horizontal frm_div_stock_in" id="user_group_form2">

		<div class="form-group">
 
 
	 

<table class="table">


  <thead >
    <tr>
      <th>#</th>
<!--       <th>Code</th>
 -->      <th>Name</th>
      <th ><span>View  </span>
          <input id="isview" name="isview" class="checkbox" type="checkbox" 
                                data-ng-model="isview"                          
                                ng-click="toggleAll(1,isview)"  > 
      
       </th>
      <th><span>Add </span>
          <input id="isadd" name="isadd" class="checkbox" type="checkbox" 
                                data-ng-model="isadd" 
                                ng-click="toggleAll(2,isadd)"  >
      
       </th>
      <th><span>Edit</span>
          <input id="isedit" name="isedit" class="checkbox" type="checkbox" 
                                data-ng-model="isedit"  
                                ng-click="toggleAll(3,isedit)"  >
      
      </th>
      <th><span>Delete</span>
          <input id="isdelete" name="isdelete" class="checkbox" type="checkbox" 
                                data-ng-model="isdelete"  
                                ng-click="toggleAll(4,isdelete)"  >
      </th>
      <th><span>Export</span>
          <input id="isexport" name="isexport" class="checkbox" type="checkbox" 
                                data-ng-model="isexport"  
                                ng-click="toggleAll(5,isexport)"  >
      
      </th>
      <th><span>Execute</span>
          <input id="isexecute" name="isexecute" class="checkbox" type="checkbox" 
                                data-ng-model="isexecute"  
                                ng-click="toggleAll(6,isexecute)"  > 
      </th>
    </tr>
  </thead>
  
  
  <tbody ng-repeat="(key, value) in groupPermissions | groupBy: 'systemGroup'" ng-if="key!='MAIN'"  > <!-- ng-if="key!='MAIN'" -->
  
     
    <tr style="background:#505c62;color:#ffffff">
      <th scope="row">#</th>
      <th>{{ key }}</th>
<!--       <th>Name</th>
 -->      <th>   
           <input id="grup_view" name="grup_view" class="checkbox" type="checkbox" 
           data-ng-model="groupVariable[key].canView" ng-checked="groupVariable[key].canView"  ng-change="viewChange(groupVariable[key],$index,'g')" >
       </th>
      <th>
           <input id="grup_add" name="grup_add" class="checkbox" type="checkbox" 
           data-ng-model="groupVariable[key].canAdd" ng-checked="groupVariable[key].canAdd" ng-change="addChange(groupVariable[key],$index,'g')">
      
      </th>
      <th>
           <input id="grup_edit" name="grup_edit" class="checkbox" type="checkbox" 
           data-ng-model="groupVariable[key].canEdit" ng-checked="groupVariable[key].canEdit" ng-change="editChange(groupVariable[key],$index,'g')">
      </th>
      <th> <input id="grup_delete" name="grup_delete" class="checkbox" type="checkbox" 
           data-ng-model="groupVariable[key].canDelete" ng-checked="groupVariable[key].canDelete" ng-change="deleteChange(groupVariable[key],$index,'g')">
           </th>
      <th> 
           <input id="grup_export" name="grup_export" class="checkbox" type="checkbox" 
           data-ng-model="groupVariable[key].canExport" ng-checked="groupVariable[key].canExport" ng-change="exportChange(groupVariable[key],$index,'g')">
      </th>
      <th> 
      
         <input id="grup_execute" name="grup_execute" class="checkbox" type="checkbox" 
           data-ng-model="groupVariable[key].canExecute" ng-checked="groupVariable[key].canExecute" ng-change="executeChange(groupVariable[key],$index,'g')">
      </th>
    </tr>
    
    <tr  ng-repeat="groupPermission in value" >
    
     
    
      <th scope="row">{{ $index+1}}</th>
<!--       <td>{{ groupPermission.code }}</td>
 -->      <td>{{ groupPermission.name }}<div ng-class="{permitshop:groupPermission.permittedAt=='2',permitHq:groupPermission.permittedAt=='1'}">{{groupPermission.permittedName}}</div></td>
       
      <th> 
        <input id="isview" name="isview" class="checkbox" type="checkbox" data-ng-model="groupPermission.canView" 
          ng-checked="groupPermission.canView" ng-disabled="!groupPermission.isViewApplicable"      ng-change="viewChange(groupPermission,$index,'r')"    >
      </th>
      <th> 
         <input id="canadd" name="canadd" class="checkbox" type="checkbox" 
         data-ng-model="groupPermission.canAdd" ng-disabled="!groupPermission.isAddApplicable"     ng-checked="groupPermission.canAdd" ng-change="addChange(groupPermission,$index,'r')" >
      </th>
      
      <th> 
         <input id="canedit" name="canedit" class="checkbox" type="checkbox" 
         data-ng-model="groupPermission.canEdit" ng-disabled="!groupPermission.isEditApplicable"    ng-checked="groupPermission.canEdit" ng-change="editChange(groupPermission,$index,'r')" >
      </th>
      <th>
          <input id="candelete" name="candelete" class="checkbox" type="checkbox" 
          data-ng-model="groupPermission.canDelete" ng-disabled="!groupPermission.isDeleteApplicable"     ng-checked="groupPermission.canDelete" ng-change="deleteChange(groupPermission,$index,'r')">
      </th>
      <th>
      <input id="canexport" name="canexport" class="checkbox" type="checkbox" 
          data-ng-model="groupPermission.canExport" ng-disabled="!groupPermission.isExportApplicable"    ng-checked="groupPermission.canExport" ng-change="exportChange(groupPermission,$index,'r')">
      </th>
      <th> 
           <input id="canexecute" name="canexecute" class="checkbox" type="checkbox" 
          data-ng-model="groupPermission.canExecute" ng-disabled="!groupPermission.isExecuteApplicable"   ng-checked="groupPermission.canExecute" ng-change="executeChange(groupPermission,$index,'r')">
      </th>
       
    </tr>
     
    
  </tbody>
</table>
      
		</div>

	</form>
</div>