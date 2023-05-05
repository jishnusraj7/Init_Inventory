package com.indocosmo.mrp.web.masters.masterimport.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.itemcategory.model.ItemCategory;
import com.indocosmo.mrp.web.masters.itemcategory.service.ItemCategoryService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.ItemMasterBatchService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service.ItemMasterBomService;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.masterimport.dao.MasterImportDao;
import com.indocosmo.mrp.web.masters.masterimport.model.MasterImport;
import com.indocosmo.mrp.web.masters.masterimport.service.MasterImportService;
import com.indocosmo.mrp.web.masters.profitcategory.model.ProfitCategory;
import com.indocosmo.mrp.web.masters.profitcategory.service.ProfitCategoryService;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;
import com.indocosmo.mrp.web.masters.supplier.service.SupplierService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/masterimport")
public class MasterImportController extends ViewController<MasterImport,MasterImportDao,MasterImportService>{	
	public static final Logger logger=Logger.getLogger(MasterImportController.class);

	@Override
	public MasterImportService getService() {

		return new MasterImportService(getCurrentContext());
	}

	@RequestMapping(value = "/list")
	public String getList(HttpSession session,Model model)throws Exception {
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_MST");
		model.addAttribute("permission", permission);
		return "/masterimport/list";
	}

	@RequestMapping(value="/getNewDataToImport")
	public void getNewDataToImportAsJson(HttpServletRequest request, HttpServletResponse response) {
		JsonArray data = new JsonArray();
		String module = request.getParameter("module");
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final ItemCategoryService itemCategoryService= new ItemCategoryService(getCurrentContext());
		//final CurrencyDetailsService currencyDetailsService= new CurrencyDetailsService(getCurrentContext());
		final ProfitCategoryService profitCategoryService = new ProfitCategoryService(getCurrentContext());
		final SupplierService supplierService = new SupplierService(getCurrentContext());
		//final UomService uomService= new UomService(getCurrentContext());
		//final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		//final TaxHdrService taxHdrService= new TaxHdrService(getCurrentContext()); 

		try{
			switch(module){
			case "item":
				data = itemMasterService.getHqitemMasterImportList();
				break;
			case "itemCategory":
				data = itemCategoryService.getHqItemCategoryImportList();
				break;
			/*case "currency":
				data = currencyDetailsService.getHqCurrencyDetailsImportList();
				break;*/
			case "profitCategory":
				data = profitCategoryService.getHqProfitcategoryImportList();
				break;
			case "supplier":
				data = supplierService.getSupplierImportList();
				break;
		/*	case "uom":
				data = uomService.getUomImportList();
				break;*/
			/*case "department":
				data = departmentService.getDepartmentImportList();
				break;*/
			/*case "tax":
				data = taxHdrService.getTaxImportList();
				break;*/
			default:
				throw new Exception("invalid Argument");
			}
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(data.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method : getNewDataToImportAsJson " + Throwables.getStackTraceAsString(e));
		}
	} 


	@RequestMapping(value="/getUpdatedDataToImport")
	public void getUpdatedDataToImportAsJson(HttpServletRequest request, HttpServletResponse response) {
		JsonArray data = new JsonArray();
		String module = request.getParameter("module");
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final ItemCategoryService itemCategoryService= new ItemCategoryService(getCurrentContext());
		//final CurrencyDetailsService currencyDetailsService= new CurrencyDetailsService(getCurrentContext());
		final ProfitCategoryService profitCategoryService = new ProfitCategoryService(getCurrentContext());
		final SupplierService supplierService = new SupplierService(getCurrentContext());
		//final UomService uomService= new UomService(getCurrentContext());
		//final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		//final TaxHdrService taxHdrService= new TaxHdrService(getCurrentContext()); 

		try{
			switch(module){
			case "item":
				data = itemMasterService.getHqitemMasterImportUpdatedList();
				break;
			case "itemCategory":
				data = itemCategoryService.getHqItemCategoryImportUpdatedList();
				break;
			/*case "currency":
				data = currencyDetailsService.getHqCurrencyDetailsImportUpdatedList();
				break;*/
			case "profitCategory":
				data = profitCategoryService.getHqProfitcategoryImportUpdatedList();
				break;
			case "supplier":
				data = supplierService.getSupplierImportUpdatedList();
				break;
			/*case "uom":
				data = uomService.getUomImportUpdatedList();
				break;*/
		/*	case "department":
				data = departmentService.getDepartmentImportUpdatedList();
				break;*/
			/*case "tax":
				data = taxHdrService.getTaxImportUpdatedList();
				break;*/
			default:
				throw new Exception("invalid Argument");
			}
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(data.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Method : getUpdatedDataToImportAsJson " + Throwables.getStackTraceAsString(e));
		}
	} 



	@RequestMapping(value="/import")
	public void importData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String status="success";
		final MasterImportService masterImportService = new MasterImportService(getCurrentContext());
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final ItemMasterBomService itemMasterBomService = new ItemMasterBomService(getCurrentContext());
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(getCurrentContext());
		final ItemCategoryService itemCategoryService= new ItemCategoryService(getCurrentContext());
		//final CurrencyDetailsService currencyDetailsService= new CurrencyDetailsService(getCurrentContext());
		final ProfitCategoryService profitCategoryService = new ProfitCategoryService(getCurrentContext());
		final SupplierService supplierService = new SupplierService(getCurrentContext());
		//final UomService uomService= new UomService(getCurrentContext());
		//final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		//final TaxHdrService taxHdrService= new TaxHdrService(getCurrentContext()); 
		//final TaxDtlService taxDtlService= new TaxDtlService(getCurrentContext()); 
		final String DB_TXN_POINT = "masterImportService";
		try{
			//new data
			List<ItemMaster> item = itemMasterService.getDataToImport();
			List<ItemMasterBom> itemBom = itemMasterBomService.getDataToImport();
			List<ItemMasterBatch> itemBatch = itemMasterBatchService.getDataToImport();
			List<ItemCategory> itemcategory = itemCategoryService.getDataToImport();
		//	List<CurrencyDetails> currency = currencyDetailsService.getDataToImport();
			List<ProfitCategory> profitCategory= profitCategoryService.getDataToImport();
			List<Supplier> supplier = supplierService.getDataToImport();
			//List<Uom> uom= uomService.getDataToImport();
			//List<Department> department = departmentService.getDataToImport();
			//List<TaxHdr> taxHdr=taxHdrService.getDataToImport();
		//	List<TaxDtl> taxDtl=taxDtlService.getDataToImport();

			masterImportService.beginTrans(DB_TXN_POINT);
			if(item.size()!=0){
				itemMasterService.saveHqData(item);
			}
			if(itemBom.size()!=0){
				itemMasterBomService.saveHqData(itemBom);
			}
			if(itemBatch.size()!=0){
				itemMasterBatchService.saveHqData(itemBatch);
			}
			if(itemcategory.size()!=0){
				itemCategoryService.saveHqData(itemcategory);
			}
			/*if(currency.size()!=0){
				currencyDetailsService.saveHqData(currency);
			}*/
			if(profitCategory.size()!=0){
				profitCategoryService.saveHqData(profitCategory);
			}
			if(supplier.size()!=0){
				supplierService.saveHqData(supplier);
			}
			/*if(uom.size()!=0){
				uomService.saveHqData(uom);
			}*/
			/*if(department.size()!=0){
				departmentService.saveHqData(department);
			}*/
			/*if(taxHdr.size()!=0){
				taxHdrService.saveHqData(taxHdr);
			}
			if(taxDtl.size()!=0){
				taxDtlService.saveHqData(taxDtl);
			}*/


			//updated data

			List<ItemCategory> itemcategoryUpdated = itemCategoryService.getUpdatedDataToImport();
			//List<CurrencyDetails> currencyUpdated = currencyDetailsService.getUpdatedDataToImport();
			List<ProfitCategory> profitCategoryUpdated = profitCategoryService.getUpdatedDataToImport();
			List<Supplier> supplierUpdated = supplierService.getUpdatedDataToImport();
		//	List<Uom> uomUpdated = uomService.getUpdatedDataToImport();
			//List<Department> departmentUpdated = departmentService.getUpdatedDataToImport();


			if(itemcategoryUpdated.size()!=0){
				itemCategoryService.save(itemcategoryUpdated);
			}
			/*if(currencyUpdated.size()!=0){
				currencyDetailsService.save(currencyUpdated);
			}*/
			if(profitCategoryUpdated.size()!=0){
				profitCategoryService.save(profitCategoryUpdated);
			}
			if(supplierUpdated.size()!=0){
				supplierService.save(supplierUpdated);
			}
			/*if(uomUpdated.size()!=0){
				uomService.save(uomUpdated);
			}*/
			/*if(departmentUpdated.size()!=0){
				departmentService.save(departmentUpdated);
			}*/

		//	List<TaxHdr> taxHdrUpdated =taxHdrService.getUpdatedDataToImport();
			/*if(taxHdrUpdated.size()!=0){
				taxHdrService.save(taxHdrUpdated);
			}*/
			/*for(TaxHdr txHdr :taxHdrUpdated){
				List<TaxDtl> taxDtlUpdated =taxDtlService.getUpdatedTaxDtlToImport(txHdr.getId());
				if(taxDtlUpdated.size()!=0){
					taxDtlService.save(taxDtlUpdated);
				}
			}*/

			List<ItemMaster> itemUpdated = itemMasterService.getUpdatedDataToImport();
			if(itemUpdated.size()!=0){
				itemMasterService.save(itemUpdated);
			}

			for(ItemMaster itemMst : itemUpdated){
				List<ItemMasterBom> itemBomUpdated = itemMasterBomService.getUpdatedBomListToImport(itemMst.getId());
				List<ItemMasterBatch> itemBatchUpdated = itemMasterBatchService.getUpdatedBatchListToImport(itemMst.getId());
				if(itemBomUpdated.size()!=0){
					itemMasterBomService.save(itemBomUpdated);
				}
				if(itemBatchUpdated.size()!=0){
					itemMasterBatchService.save(itemBatchUpdated);
				}
			}
			masterImportService.endTrans(DB_TXN_POINT);
		}catch(Exception e){
			status="error";
			e.printStackTrace();
			masterImportService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method : importData " + Throwables.getStackTraceAsString(e));
		}
		/*Gson g = new Gson();
		status="status:"+status;*/
		//return g.toJson(status);
		response.getWriter().println(status);
	}


}
