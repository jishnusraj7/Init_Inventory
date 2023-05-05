package com.indocosmo.mrp.web.masters.itemnutrition.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/*import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;*/
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.itemnutrition.dao.ItemMasterNutritionDao;
import com.indocosmo.mrp.web.masters.itemnutrition.model.ItemMasterNutrition;
import com.indocosmo.mrp.web.masters.itemnutrition.service.ItemMasterNutritionService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@RequestMapping(value = "/itemnutrition")
public class ItemMasterNutritionController
		extends ViewController<ItemMasterNutrition, ItemMasterNutritionDao, ItemMasterNutritionService> {

	@Override
	public ItemMasterNutritionService getService() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param itemCategory
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ItemMasterNutrition itemMasterNutrition, HttpSession session, Model model)
			throws Exception {

		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();

		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_NUTRI");

		model.addAttribute("permission", permission);
		model.addAttribute("Settings", true);
		model.addAttribute("Products", true);

		model.addAttribute("itemnutritionclass", true);

		return "/itemnutrition/list";
	}

	@RequestMapping(value = "/formJsonData", method = RequestMethod.GET)
	public void getformJsonData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		final ItemMasterNutritionService NutritionService = new ItemMasterNutritionService(getCurrentContext());
		JsonArray dishData = NutritionService.getMastersRowJson();

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("dishData", dishData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "/formJsonDataUOM", method = RequestMethod.GET)
	public void getformJsonDataUOM(HttpServletRequest request, HttpServletResponse response) throws Exception {

		final ItemMasterNutritionService NutritionService = new ItemMasterNutritionService(getCurrentContext());
		JsonArray dishDataUOM = NutritionService.getMastersRowJsonUOM();

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("dishDataUOM", dishDataUOM);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/*
	 * @RequestMapping(value = "/ShowNutritionInfo", method = RequestMethod.POST)
	 * public ResponseEntity<String> handleButtonClick(@RequestBody String
	 * jsonString) {
	 * 
	 * // Handle the data from the AngularJS controller
	 * 
	 * JsonElement element = JsonParser.parseString(jsonString); JsonObject obj =
	 * element.getAsJsonObject(); String data = obj.get("data").getAsString();
	 * JsonObject dataObj = JsonParser.parseString(data).getAsJsonObject();
	 * 
	 * int dishid = dataObj.get("dishid").getAsInt(); int unitid =
	 * dataObj.get("unitid").getAsInt(); int size = dataObj.get("size").getAsInt();
	 * 
	 * // Load Dish Info
	 * 
	 * List<ItemMasterNutrition> NutriInfo = LoadCollectionDataTwo(dishid, unitid,
	 * size);
	 * 
	 * // Calculation JsonFactory factory = new JsonFactory(); ObjectMapper mapper =
	 * new ObjectMapper(factory); ArrayNode json = mapper.createArrayNode();
	 * 
	 * 
	 * String IngredientName = "", Unit = "Gram", ServeUnitName = ""; double DW = 0;
	 * double DW_New = 0, ScrappageRate = 0, WeightChangeRate = 0, Quantity = 0;
	 * double RTC_Factor = 0, RTS_Factor = 0; double Changed_Market_TotalQty = 0,
	 * Changed_RTC_TotalQty = 0, Changed_RTS_TotalQty = 0; double StandardWeight =
	 * 0, StandardWeight1 = 0, StandardWeight2 = 0, ServeCount = 0, ServeCount1 = 0,
	 * ServeCount2 = 0; int count = 1;
	 * 
	 * for (Object objnew1 : NutriInfo) {
	 * 
	 * Map<String, Object> map = (Map<String, Object>) objnew1;
	 * 
	 * DW = (Double) map.get("DishWeight");
	 * 
	 * IngredientName = (String) map.get("IngredientName");
	 * 
	 * ScrappageRate = (Double) map.get("ScrappageRate"); WeightChangeRate =
	 * (Double) map.get("WeightChangeRate"); Quantity = (Double)
	 * map.get("Quantity");
	 * 
	 * if (ScrappageRate == 0) ScrappageRate = 100; if (WeightChangeRate == 0)
	 * WeightChangeRate = 100;
	 * 
	 * DW_New = size / (DW / Quantity);
	 * 
	 * RTC_Factor = ScrappageRate / 100; RTS_Factor = WeightChangeRate / 100;
	 * 
	 * int decimalPlaces = 2;
	 * 
	 * Changed_Market_TotalQty = Math.round(DW_New * Math.pow(10, decimalPlaces)) /
	 * Math.pow(10, decimalPlaces); Changed_RTC_TotalQty = Math.round((DW_New *
	 * RTC_Factor) * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
	 * Changed_RTS_TotalQty = Math.round((DW_New * RTS_Factor) * Math.pow(10,
	 * decimalPlaces)) / Math.pow(10, decimalPlaces);
	 * 
	 * // Convert to Json
	 * 
	 * ObjectNode row = mapper.createObjectNode();
	 * 
	 * row.put("SlNo", count); row.put("IngredientName", IngredientName);
	 * row.put("Market Value", Changed_Market_TotalQty); row.put("RTC",
	 * Changed_RTC_TotalQty); row.put("RTS", Changed_RTS_TotalQty); row.put("Unit",
	 * Unit);
	 * 
	 * row.put("ServeUnitName", ServeUnitName); row.put("ServeCount", ServeCount);
	 * row.put("StandardWeight", StandardWeight); row.put("ServeCount1",
	 * ServeCount1); row.put("StandardWeight1", StandardWeight1);
	 * row.put("ServeCount2", ServeCount2); row.put("StandardWeight2",
	 * StandardWeight2);
	 * 
	 * // Add the object node to the JSON array json.add(row);
	 * 
	 * count++; }
	 * 
	 * 
	 * 
	 * String jsonString1 = null; try { jsonString1 =
	 * mapper.writeValueAsString(json); } catch (JsonProcessingException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * JsonParser parser = new JsonParser(); JsonArray jsonArray =
	 * parser.parse(jsonString1).getAsJsonArray();
	 * 
	 * 
	 * //return jsonArray; return new ResponseEntity<String>("Data received",
	 * HttpStatus.OK);
	 * 
	 * }
	 */

	private List<ItemMasterNutrition> LoadCollectionDataTwo(int dishid, int unitid, int size) {
		List<ItemMasterNutrition> itemMasterNutrition = new ArrayList<>();

		final ItemMasterNutritionService NutritionService = new ItemMasterNutritionService(getCurrentContext());
		JsonArray dishDataUOM = null;

		try {
			dishDataUOM = NutritionService.GetIngredientDisplayList(dishid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new Gson();
		Type listType = new TypeToken<List<Object>>() {
		}.getType();

		itemMasterNutrition = gson.fromJson(dishDataUOM, listType);

		return itemMasterNutrition;
	}

	@RequestMapping(value = "/ShowNutritionInfo", method = RequestMethod.POST)
	public void getNutriInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String jsonString)
			throws Exception {

		// Handle the data from the AngularJS controller

		JsonElement element = JsonParser.parseString(jsonString);
		JsonObject obj = element.getAsJsonObject();
		String data = obj.get("data").getAsString();
		JsonObject dataObj = JsonParser.parseString(data).getAsJsonObject();

		int dishid = dataObj.get("dishid").getAsInt();
		int unitid = dataObj.get("unitid").getAsInt();
		int size = dataObj.get("size").getAsInt();

		// Load Dish Info

		List<ItemMasterNutrition> NutriInfo = LoadCollectionDataTwo(dishid, unitid, size);

		// Calculation
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		ArrayNode json = mapper.createArrayNode();

		String IngredientName = "", Unit = "Gram", ServeUnitName = "";
		double DW = 0;
		double DW_New = 0, ScrappageRate = 0, WeightChangeRate = 0, Quantity = 0;
		double RTC_Factor = 0, RTS_Factor = 0;
		double Changed_Market_TotalQty = 0, Changed_RTC_TotalQty = 0, Changed_RTS_TotalQty = 0;
		double StandardWeight = 0, StandardWeight1 = 0, StandardWeight2 = 0, ServeCount = 0, ServeCount1 = 0,
				ServeCount2 = 0;
		int count = 1;

		for (Object objnew1 : NutriInfo) {

			Map<String, Object> map = (Map<String, Object>) objnew1;

			DW = (Double) map.get("DishWeight");

			IngredientName = (String) map.get("IngredientName");

			ScrappageRate = (Double) map.get("ScrappageRate");
			WeightChangeRate = (Double) map.get("WeightChangeRate");
			Quantity = (Double) map.get("Quantity");

			if (ScrappageRate == 0)
				ScrappageRate = 100;
			if (WeightChangeRate == 0)
				WeightChangeRate = 100;

			DW_New = size / (DW / Quantity);

			RTC_Factor = ScrappageRate / 100;
			RTS_Factor = WeightChangeRate / 100;

			int decimalPlaces = 2;

			Changed_Market_TotalQty = Math.round(DW_New * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
			Changed_RTC_TotalQty = Math.round((DW_New * RTC_Factor) * Math.pow(10, decimalPlaces))
					/ Math.pow(10, decimalPlaces);
			Changed_RTS_TotalQty = Math.round((DW_New * RTS_Factor) * Math.pow(10, decimalPlaces))
					/ Math.pow(10, decimalPlaces);

			// Convert to Json

			ObjectNode row = mapper.createObjectNode();

			row.put("SlNo", count);
			row.put("IngredientName", IngredientName);
			row.put("MarketQty", Changed_Market_TotalQty);
			row.put("RTC", Changed_RTC_TotalQty);
			row.put("RTS", Changed_RTS_TotalQty);
			row.put("Unit", Unit);

			row.put("ServeUnitName", ServeUnitName);
			row.put("ServeCount", ServeCount);
			row.put("StandardWeight", StandardWeight);
			row.put("ServeCount1", ServeCount1);
			row.put("StandardWeight1", StandardWeight1);
			row.put("ServeCount2", ServeCount2);
			row.put("StandardWeight2", StandardWeight2);

			// Add the object node to the JSON array
			json.add(row);

			count++;
		}

		String jsonString1 = null;
		try {
			jsonString1 = mapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonParser parser = new JsonParser();
		JsonArray jsonArray = parser.parse(jsonString1).getAsJsonArray();

		// return jsonArray;
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("jsonArray", jsonArray);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "/GenerateReport", method = RequestMethod.GET)
	public void NutriInfoGenerateReport(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String jsonString) throws Exception {

		JsonElement element = JsonParser.parseString(jsonString);
		JsonObject obj = element.getAsJsonObject();
		String data = obj.get("data").getAsString();
		JsonObject dataObj = JsonParser.parseString(data).getAsJsonObject();

		int dishid = dataObj.get("dishid").getAsInt();
		int unitid = dataObj.get("unitid").getAsInt();
		int size = dataObj.get("size").getAsInt();

		List<ItemMasterNutrition> NutriInfo = LoadCollectionDataTwo(dishid, unitid, size);

		// Calculation
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		ArrayNode json = mapper.createArrayNode();

		String IngredientName = "", Unit = "Gram", ServeUnitName = "";
		double DW = 0;
		double DW_New = 0, ScrappageRate = 0, WeightChangeRate = 0, Quantity = 0;
		double RTC_Factor = 0, RTS_Factor = 0;
		double Changed_Market_TotalQty = 0, Changed_RTC_TotalQty = 0, Changed_RTS_TotalQty = 0;
		double StandardWeight = 0, StandardWeight1 = 0, StandardWeight2 = 0, ServeCount = 0, ServeCount1 = 0,
				ServeCount2 = 0;
		int count = 1;

		for (Object objnew1 : NutriInfo) {

			Map<String, Object> map = (Map<String, Object>) objnew1;

			DW = (Double) map.get("DishWeight");

			IngredientName = (String) map.get("IngredientName");

			ScrappageRate = (Double) map.get("ScrappageRate");
			WeightChangeRate = (Double) map.get("WeightChangeRate");
			Quantity = (Double) map.get("Quantity");

			if (ScrappageRate == 0)
				ScrappageRate = 100;
			if (WeightChangeRate == 0)
				WeightChangeRate = 100;

			DW_New = size / (DW / Quantity);

			RTC_Factor = ScrappageRate / 100;
			RTS_Factor = WeightChangeRate / 100;

			int decimalPlaces = 2;

			Changed_Market_TotalQty = Math.round(DW_New * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
			Changed_RTC_TotalQty = Math.round((DW_New * RTC_Factor) * Math.pow(10, decimalPlaces))
					/ Math.pow(10, decimalPlaces);
			Changed_RTS_TotalQty = Math.round((DW_New * RTS_Factor) * Math.pow(10, decimalPlaces))
					/ Math.pow(10, decimalPlaces);

			/*
			 * PdfPTable pdfTableHeaderLogo = new PdfPTable(1);
			 * pdfTableHeaderLogo.getDefaultCell().setPadding(50);
			 * pdfTableHeaderLogo.setWidthPercentage(100);
			 * pdfTableHeaderLogo.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * 
			 * Font red = new Font(FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.RED); Font
			 * blue = new Font(FontFamily.HELVETICA, 12, Font.UNDERLINE, BaseColor.BLUE);
			 * Font white = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
			 * 
			 * String imageURL = System.getProperty("user.dir") + "\\Images\\PinkTheme.png";
			 * Image imageHeader = Image.getInstance(imageURL);
			 * 
			 * imageHeader.scaleToFit(140f, 120f); imageHeader.setSpacingBefore(10f);
			 * imageHeader.setSpacingAfter(1f);
			 * imageHeader.setAlignment(Element.ALIGN_RIGHT);
			 * 
			 * PdfPCell cellHeadingLogo = new PdfPCell(imageHeader);
			 * cellHeadingLogo.setBorder(Rectangle.NO_BORDER);
			 * cellHeadingLogo.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cellHeadingLogo.setVerticalAlignment(Element.ALIGN_CENTER);
			 * cellHeadingLogo.setBackgroundColor(new BaseColor(java.awt.Color.DARK_GRAY));
			 * pdfTableHeaderLogo.addCell(cellHeadingLogo);
			 * 
			 * // Table Header
			 * 
			 * PdfPTable pdfTableHead = new PdfPTable(1);
			 * pdfTableHead.getDefaultCell().setPadding(0);
			 * pdfTableHead.setWidthPercentage(100);
			 * pdfTableHead.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * 
			 * PdfPCell cellHeading = new PdfPCell(new Phrase("INGREDIENT DETAILS", red));
			 * cellHeading.setBorder(Rectangle.NO_BORDER);
			 * cellHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cellHeading.setVerticalAlignment(Element.ALIGN_CENTER);
			 * cellHeading.setBackgroundColor(new BaseColor(java.awt.Color.YELLOW_GREEN));
			 * pdfTableHead.addCell(cellHeading);
			 * 
			 * // Blank Page
			 * 
			 * PdfPCell blankCell1 = new PdfPCell(new Phrase(Chunk.NEWLINE));
			 * blankCell1.setBorder(Rectangle.NO_BORDER); pdfTableHead.addCell(blankCell1);
			 * 
			 * PdfPCell cellHeadingTwo = new PdfPCell(new Phrase("Ingredients Needed For " +
			 * txtSize.getText() + " " + cboUOM.getText().trim() + " of " +
			 * cboDish.getText() + "" + "(" + "" + "" + "Serving Size for " +
			 * dishIngredients[0].getStandardWeight() + " g" + "" + ")", blue));
			 * cellHeadingTwo.setBorder(Rectangle.NO_BORDER);
			 * cellHeadingTwo.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cellHeadingTwo.setVerticalAlignment(Element.ALIGN_CENTER);
			 * pdfTableHead.addCell(cellHeadingTwo);
			 * 
			 * // Blank Page
			 * 
			 * PdfPCell blankCell = new PdfPCell(new Phrase(Chunk.NEWLINE));
			 * blankCell.setBorder(Rectangle.NO_BORDER); pdfTableHead.addCell(blankCell);
			 * 
			 * // Table Entries
			 * 
			 * PdfPTable pdfTable = new PdfPTable(6);
			 * pdfTable.getDefaultCell().setPadding(5); pdfTable.setTotalWidth(500f);
			 * pdfTable.setLockedWidth(true); float[] widths = new float[] { 40f, 150f, 90f,
			 * 90f, 80f, 50f }; pdfTable.setWidths(widths);
			 * 
			 * PdfPCell cell = new PdfPCell(new Phrase("SlNo", white));
			 * cell.setBackgroundColor(new BaseColor(java.awt.Color.GRAY));
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); cell.setFixedHeight(25);
			 * pdfTable.addCell(cell);
			 * 
			 * PdfPCell cell2 = new PdfPCell(new Phrase("Ingredient", white));
			 * cell2.setBackgroundColor(new BaseColor(java.awt.Color.GRAY));
			 * cell2.setHorizontalAlignment(Element.ALIGN_CENTER); cell2.setFixedHeight(25);
			 * pdfTable.addCell(cell2);
			 * 
			 * PdfPCell cell3 = new PdfPCell(new Phrase("Market Quantity", white));
			 * cell3.setBackgroundColor(new BaseColor(java.awt.Color.GRAY));
			 * cell3.setHorizontalAlignment(Element.ALIGN_CENTER); cell3.setFixedHeight(25);
			 * pdfTable.addCell(cell3);
			 * 
			 * PdfPCell cell4 = new PdfPCell(new Phrase
			 */

		}

	}

	private static final String FILE_NAME = "/tmp/itext.pdf";

	@RequestMapping(value = "/GenerateNutriLabel", method = RequestMethod.POST)
	public void NutriInfoGenerateLabel(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String jsonString) throws Exception {

		// created PDF document instance
		Document doc = new Document();
		try {
			// generate a PDF at the specified location
			PdfWriter writer = PdfWriter.getInstance(doc,
					new FileOutputStream("C:\\Users\\Anubhav\\Desktop\\Java PDF\\Motivation.pdf"));
			System.out.println("PDF created.");
			// opens the PDF
			doc.open();
			// adds paragraph to the PDF file
			doc.add(new Paragraph("If you're offered a seat on a rocket ship, don't ask what seat! Just get on."));
			// close the PDF file
			doc.close();
			// closes the writer
			writer.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
