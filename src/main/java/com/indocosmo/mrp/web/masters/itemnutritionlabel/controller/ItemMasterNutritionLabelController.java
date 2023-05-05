package com.indocosmo.mrp.web.masters.itemnutritionlabel.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.itemnutritionlabel.dao.ItemMasterNutritionLabelDao;
import com.indocosmo.mrp.web.masters.itemnutritionlabel.model.ItemMasterNutritionLabel;
import com.indocosmo.mrp.web.masters.itemnutritionlabel.service.ItemMasterNutritionLabelService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Controller
@RequestMapping(value = "/itemnutritionlabel")
public class ItemMasterNutritionLabelController
extends ViewController<ItemMasterNutritionLabel, ItemMasterNutritionLabelDao, ItemMasterNutritionLabelService> {

	@Override
	public ItemMasterNutritionLabelService getService() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param itemCategory
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ItemMasterNutritionLabel itemMasterNutrition, HttpSession session, Model model)
			throws Exception {

		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();

		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_NUTRI");

		model.addAttribute("permission", permission);
		model.addAttribute("Settings", true);
		model.addAttribute("Products", true);

		model.addAttribute("itemnutritionlabelclass", true);

		return "/itemnutritionlabel/list";
	}

	@RequestMapping(value = "/formJsonData", method = RequestMethod.GET)
	public void getformJsonData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		final ItemMasterNutritionLabelService NutritionService = new ItemMasterNutritionLabelService(getCurrentContext());
		JsonArray dishData = NutritionService.GetDishList();

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("dishData", dishData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}


	@RequestMapping(value = "/ShowNutritionLabelInfo", method = RequestMethod.POST)
	public void getNutriLabelInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String jsonString)
			throws Exception {

		// Handle the data from the AngularJS controller

		JsonElement element = JsonParser.parseString(jsonString);
		JsonObject obj = element.getAsJsonObject();
		String data = obj.get("data").getAsString();
		JsonObject dataObj = JsonParser.parseString(data).getAsJsonObject();

		int dishid = dataObj.get("dishid").getAsInt();
		//int unitid = dataObj.get("unitid").getAsInt();
		//int size = dataObj.get("size").getAsInt();

		// Load Dish Info per 100 g
		final ItemMasterNutritionLabelService NutritionService = new ItemMasterNutritionLabelService(getCurrentContext());
		JsonArray NutriLabelInfo = NutritionService.ShowNutritionLabel(dishid);

		Float Dish_Weight = NutritionService.GetDishWeightById(dishid);
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		JsonArray modifiedArray = new JsonArray();

		for (JsonElement jsonElementNVL : NutriLabelInfo) {
			JsonObject jsonObjectNVL = jsonElementNVL.getAsJsonObject();
			Float NutrientValue = jsonObjectNVL.get("NutrientValue").getAsFloat();
			double R_NutrientValue = (NutrientValue * 100) / Dish_Weight;
			jsonObjectNVL.addProperty("NutrientValue", Double.parseDouble(decimalFormat.format(R_NutrientValue)));
			modifiedArray.add(jsonObjectNVL);
		}

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("NutriLabelInfo", modifiedArray);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}


	Integer decimalPlace;


	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}


	@RequestMapping(value = "/GenerateNutriLabel", method = RequestMethod.POST)
	public void generateNutriLabel(HttpServletResponse response,@RequestBody String jsonString) throws Exception {

		


		 Font green = new Font(Font.FontFamily.HELVETICA, 50, Font.BOLD, BaseColor.GREEN);
         Font blue = new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD, BaseColor.BLUE);
         Font black = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLACK);
        // Font bigblack = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, BaseColor.BLACK);
         Font blacksmall = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD, BaseColor.BLACK);
		//----------Table Header------------

		PdfPTable pdfTableHeadBorder = new PdfPTable(1);
		pdfTableHeadBorder.getDefaultCell().setPadding(0);
		pdfTableHeadBorder.setWidthPercentage(100);
		pdfTableHeadBorder.setHorizontalAlignment(Element.ALIGN_CENTER);


		PdfPCell cellheading = new PdfPCell(new Phrase(Chunk.NEWLINE));
		cellheading.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellheading.setVerticalAlignment(Element.ALIGN_CENTER);
		cellheading.setBackgroundColor(new BaseColor(245, 245, 245));
		cellheading.setFixedHeight(800);
		cellheading.setBorder(PdfPCell.NO_BORDER);


		PdfPTable pdfTableHeader = new PdfPTable(1);
		pdfTableHeader.getDefaultCell().setPadding(0);
		pdfTableHeader.setWidthPercentage(100);
		pdfTableHeader.setHorizontalAlignment(Element.ALIGN_CENTER);


		PdfPCell cellheader = new PdfPCell(new Phrase("NUTRITION FACTS", green));
		cellheader.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellheader.setVerticalAlignment(Element.ALIGN_CENTER);
		cellheader.setBorder(Rectangle.NO_BORDER);
		cellheader.setPaddingTop(0);
		cellheader.setPaddingBottom(0);
		cellheader.setPaddingLeft(0);
		cellheader.setPaddingRight(0);
		//cellheader.setFixedHeight(50);
		pdfTableHeader.addCell(cellheader);


		PdfPCell blankCell = new PdfPCell(new Phrase(Chunk.NEWLINE));
		blankCell.setBorder(PdfPCell.NO_BORDER);
		pdfTableHeader.addCell(blankCell);


		PdfPCell blankCell2 = new PdfPCell(new Phrase(Chunk.NEWLINE));
		blankCell2.setBorder(PdfPCell.NO_BORDER);
		blankCell2.setBackgroundColor(new BaseColor(0, 0, 0));
		pdfTableHeader.addCell(blankCell2);


		PdfPCell blankCell3 = new PdfPCell(new Phrase(Chunk.NEWLINE));
		blankCell3.setBorder(PdfPCell.NO_BORDER);
		pdfTableHeader.addCell(blankCell3);



		//        PdfPCell blankCellServe = new PdfPCell(new Phrase("Serving Size   - " + dishIngredients[0].getStandardWeight() + "gm (Minimum)", blacksmall));
		PdfPCell blankCellServe = new PdfPCell(new Phrase("Serving Size   - 100gm (Minimum)", blacksmall));
		blankCellServe.setBorder(PdfPCell.NO_BORDER);
		pdfTableHeader.addCell(blankCellServe);


		PdfPCell cellborder = new PdfPCell(new Phrase(Chunk.NEWLINE));
		cellborder.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellborder.setVerticalAlignment(Element.ALIGN_CENTER);
		//cellborder.setBackgroundColor(new BaseColor(255, 255, 255));
		cellborder.setFixedHeight(800);


		PdfPCell blankCell4 = new PdfPCell(new Phrase(Chunk.NEWLINE));
		blankCell4.setBorder(PdfPCell.NO_BORDER);
		pdfTableHeader.addCell(blankCell4);



		//Inner Table - Column

		PdfPTable pdfTableData = new PdfPTable(2);
		pdfTableData.getDefaultCell().setPadding(0);
		pdfTableData.setWidthPercentage(100);
		pdfTableData.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfTableData.setTotalWidth(500f);
		pdfTableData.setLockedWidth(true);
		float[] widths = new float[] {  250f, 250f };
		pdfTableData.setWidths(widths);

		
		//----Blank Line---

		PdfPCell blankCell5 = new PdfPCell(new Phrase(Chunk.NEWLINE));
		blankCell5.setBorder(PdfPCell.NO_BORDER);
		blankCell5.setColspan(2);
		blankCell5.setBackgroundColor(BaseColor.WHITE);
		blankCell5.setFixedHeight(5);
		pdfTableData.addCell(blankCell5);

		PdfPCell blankCell6 = new PdfPCell(new Phrase(Chunk.NEWLINE));
		blankCell6.setBorder(PdfPCell.NO_BORDER);
		blankCell6.setColspan(2);
		blankCell6.setFixedHeight(10);
		pdfTableData.addCell(blankCell6);


		PdfPCell cell = new PdfPCell(new Phrase("Nutritions", blue));
		//cell.BackgroundColor = new BaseColor(System.Drawing.Color.Gray);
		cell.setFixedHeight(50);
		cell.setBorder(PdfPCell.NO_BORDER);
		pdfTableData.addCell(cell);

		PdfPCell cell2 = new PdfPCell(new Phrase("Value for 100 gm", blue));
		//cell2.BackgroundColor = new BaseColor(System.Drawing.Color.Gray);
		cell2.setFixedHeight(50);
		cell2.setBorder(PdfPCell.NO_BORDER);
		pdfTableData.addCell(cell2);
		

		//----Blank Line---

		PdfPCell blankCell7 = new PdfPCell(new Phrase(Chunk.NEWLINE));
		blankCell7.setBorder(PdfPCell.NO_BORDER);
		blankCell7.setColspan(2);
		blankCell7.setBackgroundColor(BaseColor.BLACK);
		blankCell7.setFixedHeight(5);
		pdfTableData.addCell(blankCell7);
		
		PdfPCell blankCell8 = new PdfPCell(new Phrase(Chunk.NEWLINE));
		blankCell8.setBorder(PdfPCell.NO_BORDER);
		blankCell8.setColspan(2);
		blankCell8.setFixedHeight(10);
		pdfTableData.addCell(blankCell8);


		//---Start Nutrition loop---


		JsonElement element = JsonParser.parseString(jsonString);
		JsonObject obj = element.getAsJsonObject();
		int dishid = obj.get("dishid").getAsInt();

		// Load Dish Info per 100 g

		final ItemMasterNutritionLabelService NutritionService = new ItemMasterNutritionLabelService(getCurrentContext());
		JsonArray NutriLabelInfo = NutritionService.ShowNutritionLabel(dishid);

		Float Dish_Weight = NutritionService.GetDishWeightById(dishid);
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		JsonArray modifiedArray = new JsonArray();

		for (JsonElement jsonElementNVL : NutriLabelInfo) {
			JsonObject jsonObjectNVL = jsonElementNVL.getAsJsonObject();
			Float NutrientValue = jsonObjectNVL.get("NutrientValue").getAsFloat();
			double R_NutrientValue = (NutrientValue * 100) / Dish_Weight;
			jsonObjectNVL.addProperty("NutrientValue", Double.parseDouble(decimalFormat.format(R_NutrientValue)));
			modifiedArray.add(jsonObjectNVL);
		}

		

		for (JsonElement jsonElementNVL : modifiedArray) {
			JsonObject jsonObjectNVL = jsonElementNVL.getAsJsonObject();
			String NutrientsName = jsonObjectNVL.get("NutrientParam").getAsString();
			Float NutrientValue = jsonObjectNVL.get("NutrientValue").getAsFloat();
			String NutrientUnit = jsonObjectNVL.get("NutrientUnit").getAsString();


			PdfPCell celldata = new PdfPCell(new Phrase(NutrientsName , black));
			celldata.setFixedHeight(25);
			celldata.setBorder(PdfPCell.NO_BORDER);
			pdfTableData.addCell(celldata);

			PdfPCell celldata2 = new PdfPCell(new Phrase((NutrientValue) + " " +  NutrientUnit, black));
			celldata2.setFixedHeight(25);
			celldata2.setBorder(PdfPCell.NO_BORDER);
			pdfTableData.addCell(celldata2);


			PdfPCell blankCell9 = new PdfPCell(new Phrase(Chunk.NEWLINE));
			blankCell9.setBorder(PdfPCell.NO_BORDER);
			blankCell9.setColspan(4);
			pdfTableData.addCell(blankCell9);

		}

//		
//		PdfPCell blankCell20 = new PdfPCell(new Phrase(Chunk.NEWLINE));
//		blankCell20.setBorder(PdfPCell.NO_BORDER);
//		blankCell20.setColspan(4);
//		pdfTableData.addCell(blankCell20);



		cellborder.addElement(pdfTableData);
		pdfTableHeader.addCell(cellborder);


		cellheading.addElement(pdfTableHeader);
		pdfTableHeadBorder.addCell(cellheading);
		
		// Create a new PDF document
		Document document = new Document(PageSize.A4, 20f, 20f, 20f, 20f);
		// Create a new PDF writer
 		PdfWriter.getInstance(document, response.getOutputStream());
		// Open the document
		document.open();
		// Add content to the document
		
		document.add(pdfTableHeadBorder);

		// Close the document
		document.close();

		// Set the response headers
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=NutrientLabel.pdf");
	}

	@RequestMapping(value = "/NutritionValueApi", method = RequestMethod.POST)
	public void getNutritionValue(HttpServletRequest request, HttpServletResponse response, @RequestBody String jsonString)
			throws Exception {

		// Handle the data from the AngularJS controller

		JsonElement element = JsonParser.parseString(jsonString);
		JsonObject obj = element.getAsJsonObject();
		String data = obj.get("data").getAsString();
		JsonObject dataObj = JsonParser.parseString(data).getAsJsonObject();

		int dishid = dataObj.get("dishid").getAsInt();
		//int unitid = dataObj.get("unitid").getAsInt();
		//int size = dataObj.get("size").getAsInt();

		// Load Dish Info per 100 g
		final ItemMasterNutritionLabelService NutritionService = new ItemMasterNutritionLabelService(getCurrentContext());
		JsonArray NutriLabelInfo = NutritionService.ShowNutritionLabel(dishid);

		Float Dish_Weight = NutritionService.GetDishWeightById(dishid);
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		//		JsonArray modifiedArray = new JsonArray();
		//
		//		for (JsonElement jsonElementNVL : NutriLabelInfo) {
		//			JsonObject jsonObjectNVL = jsonElementNVL.getAsJsonObject();
		//			Float NutrientValue = jsonObjectNVL.get("NutrientValue").getAsFloat();
		//			double R_NutrientValue = (NutrientValue * 100) / Dish_Weight;
		//			jsonObjectNVL.addProperty("NutrientValue", Double.parseDouble(decimalFormat.format(R_NutrientValue)));
		//			modifiedArray.add(jsonObjectNVL);
		//		}

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("NutriLabelInfo", NutriLabelInfo);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}


}
