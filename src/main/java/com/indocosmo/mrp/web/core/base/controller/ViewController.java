package com.indocosmo.mrp.web.core.base.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.WebUtil;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;

/**
 * @author jojesh-13.2
 * 
 * Controller base for all view pages
 *
 * @param <T> The model class for the controller
 */
@Controller
@Qualifier("ViewController")
public abstract class ViewController<T extends GeneralModelBase,D extends GeneralDao<T>, S extends GeneralService<T,D>> extends BaseController<T,D,S> {
	public static final Logger logger=Logger.getLogger(ViewController.class);
	/**
	 * Get all the records in the table as JSON 
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="json")
	public void getTableRowsAsJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonArray data;
		try{
			data = getService().getJsonArray();
			JsonObject jsonResponse = new JsonObject();		
			jsonResponse.add("data", data);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method : getTableRowsAsJson " + Throwables.getStackTraceAsString(e));
		}
		
	}
	
	
	
	@RequestMapping(value="jsonDropdown")
	public void getDropdownDataAsJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonArray data;
		try{
			data = getService().getDropdownArray();
			JsonObject jsonResponse = new JsonObject();		
			jsonResponse.add("data", data);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method : getDropdownDataAsJson " + Throwables.getStackTraceAsString(e));
		}
		
	}
	
	
	/**
	 * Get all the records in the table as JSON 
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="getDataTableData")
	public void getDataTableDataAsJson(@ModelAttribute DataTableCriterias datatableParameters,HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonObject jsonResponse=new JsonObject();
		try{
		jsonResponse = getService().getTableJsonArray(datatableParameters);	
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method : DataAsJson "+this+ "  " + Throwables.getStackTraceAsString(e));
			//throw new CustomException();
		}

	}
	
	
	
	@RequestMapping(value="getExportData")
	public void getExportData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonObject jsonResponse=new JsonObject();
		try{
		jsonResponse = getService().getExportData();	
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method : DataAsJson "+this+ "  " + Throwables.getStackTraceAsString(e));
			//throw new CustomException();
		}

	}
	
	

	@RequestMapping(value = "ExcelReport")
	public ModelAndView generateExcel(@ModelAttribute Report report , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {
	
		JsonArray excelData = new JsonArray();

		try {
			
			JsonObject row=new JsonObject();
			row.addProperty("ColoumnList",report.getColoumnList());
			row.addProperty("reportname", report.getReportName());
			excelData.add(getService().getExcelJsonListData(report.getColoumnList(),report.getOrderList()));
			excelData.add(row);

		}
		catch (Exception e) {
			
			throw e;
		}
		
		return new ModelAndView("excelReportView", "reportData", excelData);
	}
	

	
	
	/**
	 * Method to save the  data, Both insert and update are handled
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	public void setData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try
		{
		saveData(request);
		response.getWriter().print("1");
		}
		catch(Exception e)
		{
			logger.error("Method : save "+this+ "  " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	protected T saveData(HttpServletRequest request) throws Exception {
		
		T item=getNewModelInstance();
		
		setModelItem(request, item);
		getService().save(item);
		
		return item;
		
	}
	
	/**
	 * Method to delete the record
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		final String id=request.getParameter("id");
		Integer is_deleted = 0;
		
		try
		{
		  is_deleted = getService().delete(id);	
		  if (is_deleted == 0) {
			response.getWriter().print("0");
		  }else{
			response.getWriter().print("1");
		  }
		}
		catch(Exception e)
		{
			logger.error("Method : delete "+this+ "  " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	/**
	 * set the model object's value from the httpServlet request.
	 * @param request
	 * @param item
	 * @return
	 * @throws Exception
	 */
	protected T setModelItem(HttpServletRequest request, T item) throws Exception  {
		
		for(Field field : ReflectionUtil.getAllFileds(item.getClass())){

			/**
			 * Column annotation
			 * Gets the column from bean
			 */
			Column annotationColumn = (Column)field.getDeclaredAnnotation(Column.class);
			
			if(annotationColumn!=null){

				final String fieldName=annotationColumn.name();
				 String valueString=request.getParameter(fieldName);
				
				valueString = setCurrentDateTime(request, fieldName,
						valueString);

				if(valueString!=null){
					
					Object value=WebUtil.pageFieldToModelValue(field,valueString);
					
					if(value!=null){
						
						field.setAccessible(true);
						field.set(item,value);
					}
				}
				
			}
			
		}
		
		return item;
	}


	public String setCurrentDateTime(HttpServletRequest request,
			final String fieldName, String valueString) {
		final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
		final String dateTime = currentDateFormat.getCurrentDateTime(); 
		Users userDtls = (Users) httpSession.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
		
		if(request.getParameter("id") == null || request.getParameter("id")==""){
			if(fieldName.equals("created_at")){
				valueString = dateTime;
			}
			if(fieldName.equals("created_by")){
				valueString = userDtls.getId().toString();
			}
		}else{
			if(fieldName.equals("updated_at")){
				valueString = dateTime;
			}
			if(fieldName.equals("updated_by")){
				valueString = userDtls.getId().toString();
			}
		}
		return valueString;
	}
	
	
	/**
	 * Gets the new object for the model class used in this controller.
	 * Should be overridden in the extended master class
	 * @return
	 */
	public T getNewModelInstance(){
		
		return getService().getDao().getNewModelInstance();
		
	}
	
	
	/**
	 * @param datatableParameters
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/jsonData", method = RequestMethod.GET)
	public @ResponseBody void getJosnData(@ModelAttribute DataTableCriterias datatableParameters,HttpServletResponse response,HttpServletRequest request) throws Exception
	{	
		
		List<String> tableFields = new ArrayList<String>();
		tableFields.add("code");
		tableFields.add("name");
		
		JsonArray data = getService().getDataTable(datatableParameters,tableFields);
		JsonObject jsonResponse = new JsonObject();
		
		long recordTotal = getService().getTableTotalRecord();
		jsonResponse.addProperty("draw", datatableParameters.getDraw());
		jsonResponse.addProperty("recordsTotal", data.size());
		jsonResponse.addProperty("recordsFiltered", recordTotal);
		jsonResponse.add("data", data);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "codeexisting")
	public void isCodeExist(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Integer isCodeExisting = 0;
		final String code = request.getParameter("code");
		final Integer rowCount = getService().isCodeExist(code);
		
		if(rowCount > 0){
			isCodeExisting = 1;
		}
		
		response.getWriter().print(isCodeExisting);
	}
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/getLastCostPrice")
	public void getItemLastCostPrice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		final Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		final Double lastCostPrice = getService().getItemLastCostPrice(itemId);
		
		response.getWriter().println(lastCostPrice);
	}
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/getAverageCostPrice")
	public void getItemAverageCostPrice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		final Integer itemId = Integer.parseInt(request.getParameter("itemId"));
		final Double avgCostPrice = getService().getItemAverageCostPrice(itemId);
		
		response.getWriter().println(avgCostPrice);
	}
	
	/**
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getCurrentDate") 
	public void getCurrentDate(HttpServletResponse response) throws Exception{
		
		GettingCurrentDateTime currentDateTime = new GettingCurrentDateTime(getCurrentContext());
		response.getWriter().println(currentDateTime.getCurrentDate());
		
	}
	
	@RequestMapping(value="/errorpage")
	public String getErrorPage(){
		return "exception/exception";
	}
}
