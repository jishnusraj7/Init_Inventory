package com.indocosmo.mrp.web.production.production;

import java.math.BigDecimal;
import java.util.Map;

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



public class MarkFinishedPdfView extends AbstractITextPdfView{

	Integer decimalPlace;
	
	@Override
	public Document newDocument() {
			return new Document(PageSize.A4.rotate(),36, 36, 120, 80);
	}
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		
		JsonArray itemData = (JsonArray)model.get("productionListView");
		Report  report =   (Report) model.get("reportName");
		
		decimalPlace = report.getDecimalPlace();
		Font font=FontFactory.getFont(FontFactory.HELVETICA,11,Font.BOLD);
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA,9);
		
		PdfPTable table = new PdfPTable(6);
		table.setWidths(new float[] {.8f,4.5f,1f,1.5f,1.5f,1.5f});
		table.setWidthPercentage(100.0f);
		
		PdfPCell cellspan = new PdfPCell();
		cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
		cellspan.setPadding(6);
		cellspan.setColspan(6);
		JsonObject departmentData = (JsonObject) itemData.get(0);
		cellspan.setPhrase(new Phrase(departmentData.get("department_name").getAsString(),font));
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
		
		cell.setPhrase(new Phrase("UNIT", font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
			
		cell.setPhrase(new Phrase("PRODUCED QTY", font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("DAMAGED QTY", font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("REMARKS", font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		int count = 0;
		for (int i = 0; i < itemData.size(); i++) {
			JsonObject markedData = (JsonObject) itemData.get(i);
			count=count+1;
		    
			cell.setPhrase(new Phrase("" + count, font1));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(markedData.get("stock_item_name").getAsString(), font1));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(markedData.get("uomcode").getAsString(), font1));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
					
			cell.setPhrase(new Phrase(getNumberWithDecimal(markedData.get("prod_qty").getAsDouble()), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal((markedData.get("damageqty").getAsString() != null && 
					markedData.get("damageqty").getAsString().length() != 0) ? markedData.get("damageqty").getAsDouble() : 0.00), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(markedData.get("remarks").getAsString(), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
		}
		document.add(table);
	}

	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}
	
}
