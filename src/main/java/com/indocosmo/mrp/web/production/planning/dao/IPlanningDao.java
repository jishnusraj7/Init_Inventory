package com.indocosmo.mrp.web.production.planning.dao;

import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.production.planning.model.Planning;



public interface IPlanningDao extends IGeneralDao<Planning>{

	public JsonArray getOrderDataAsJson() throws Exception;
	JsonArray getOrderAsJsonByDate(String selectedDate ,String iscustomer,HttpSession session) throws Exception;

	JsonArray getOrderDataAsJsonByDate(String selectedDate ,String iscustomer,HttpSession session) throws Exception;
	
	public JsonArray getShopOrderNo() throws Exception ;
	
	public JsonArray getCustomerOrderNo() throws Exception ;

	JsonArray getOrderItemTotal(String selectedDate ,String iscustomer,HttpSession session) throws Exception;

	JsonArray getOrderDataAsJsonByDateCat(String ordrid,HttpSession session) throws Exception;
	
	public int updateStatus(Integer id) throws Exception;
}
