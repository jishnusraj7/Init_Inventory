package com.indocosmo.mrp.web.production.production;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ProductVerifiedOrderReport extends AbstractITextPdfView{
	
	String companyName;
	String companyAddress;
	String dateFormat;
	Integer decimalPlace;
	String currency;
	
	
	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}

	@Override
	public Document newDocument() {
			return new Document(PageSize.A4,36, 36, 120, 80);
	}
	
	
	public String getDateWithSystemFormat(String date) throws Exception {

		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		final String systemDateFormat =dateFormat;
		DateFormat formatter = new SimpleDateFormat(systemDateFormat);
		final String dateWithSystemFormat = formatter.format(date1);


		return dateWithSystemFormat;
	}
	
	
	

	@SuppressWarnings("rawtypes")
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		
		
		
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA,9);
		Font font=FontFactory.getFont(FontFactory.HELVETICA,11,Font.BOLD);
	//	Font font2=FontFactory.getFont(FontFactory.HELVETICA,9,Font.BOLD);
		
		JsonArray itemData=(JsonArray)model.get("printAndSendToProductionView");
		Report  report =   (Report) model.get("reportName");
		
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		companyAddress = report.getCompanyAddress();
		if(itemData.size()!=0)
		{
			

			HashMap< String, List<Object> > itemDataMap = new HashMap<String, List<Object>>();

			for (int i = 0; i < itemData.size(); i++) {
				
				JsonObject orderdata = (JsonObject) itemData.get(i);

				if (itemDataMap.containsKey(orderdata.get("department").getAsString())){

				List<Object> jsonList= itemDataMap.get(orderdata.get("department").getAsString());
				
				jsonList.add(orderdata);
			
				itemDataMap.put(orderdata.get("department").getAsString(), jsonList);

				}else{

				List<Object> jsonList=new  ArrayList<Object>();
				jsonList.add(orderdata);
				itemDataMap.put(orderdata.get("department").getAsString(), jsonList);

				}
			
		}
			
			
			Set set = itemDataMap.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				
				
				
			Map.Entry entry = (Map.Entry) iterator.next();
			String departMentName = (String) entry.getKey();
		

			
			
			PdfPTable table = new PdfPTable(4);
			table.setWidths(new float[] {.8f,3f,1f,2f});
			table.setWidthPercentage(100.0f);
			
			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(6);
			cellspan.setColspan(4);

			cellspan.setPhrase(new Phrase(departMentName,font));
			cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cellspan);

			
			
			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(new BaseColor(255, 255, 255));
			cell.setPadding(5);
			table.getDefaultCell().setPaddingBottom(10.0f);
			table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			table.getDefaultCell().setBorder(Rectangle.BOTTOM);
			table.getDefaultCell().setBorderWidthBottom(2.0f);
			cell.setPhrase(new Phrase("SI #", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell.setPhrase(new Phrase("ITEM NAME", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell.setPhrase(new Phrase("TOTAL QTY", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell.setPhrase(new Phrase("REMARKS", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			String sino="";
			int count=0;
			//int toatalcnt=0;
			
			@SuppressWarnings("unchecked")
			List<Object> jsonList =(List<Object>) entry.getValue();
			
			
			for (int i = 0; i < jsonList.size(); i++) {
				
				JsonObject orderdata = (JsonObject) jsonList.get(i);
				
				
				count=count+1;
			    sino=""+count;
			    
				cell.setPhrase(new Phrase(sino, font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell.setPhrase(new Phrase(orderdata.get("stock_item_name").getAsString(), font1));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell.setPhrase(new Phrase(getNumberWithDecimal(orderdata.get("qty").getAsDouble()), font1));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				
				
				cell.setPhrase(new Phrase(orderdata.get("remarks").getAsString(), font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				table.addCell(cell);
			
				
			}
			document.add(table);
			
			}
						
	
}else
  {
	
	PdfPTable table89 = new PdfPTable(1);
	table89.setWidthPercentage(100.0f);
	table89.setSpacingBefore(10);

	PdfPCell cell89 = new PdfPCell();

	cell89.setBackgroundColor(new BaseColor(255, 255, 255));
	cell89.setPadding(5);			
	table89.getDefaultCell().setPaddingBottom(10.0f);
	table89.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
	table89.getDefaultCell().setBorder(Rectangle.BOTTOM);
	table89.getDefaultCell().setBorderWidthBottom(2.0f);
	cell89.setPhrase(new Phrase("No Items Available ", font));
	cell89.setHorizontalAlignment(Element.ALIGN_CENTER);
	table89.addCell(cell89);
	document.add(table89);
	
}
		
	
	}

}
