/*package com.indocosmo.mrp.web.report.profitsummary;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;
import com.indocosmo.mrp.web.report.profitsummary.model.ProfitSummaryModel;
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

public class ProfitSummaryReport extends AbstractITextPdfView {
	
	String companyName;
	
	String dateFormat;
	
	Integer decimalPlace;
	
	String currency;
	
	public String getNumberWithDecimal(Double value) {
	
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		
		return bd.toString();
	}
	
	public String getDateWithSystemFormat(String date) throws Exception {
    	
		  Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);  
  	final String systemDateFormat =dateFormat;
  	DateFormat formatter = new SimpleDateFormat(systemDateFormat);
  	final String dateWithSystemFormat = formatter.format(date1);
  	
			
		return dateWithSystemFormat;
	}

	@Override
	public Document newDocument() {
		return new Document(PageSize.A4,36, 36, 80, 80);
	}

	
	@Override
	protected void buildPdfDocument(Map<String, Object> model , Document doc , PdfWriter writer ,
			HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA,9);
		//Font font1 = FontFactory.getFont(FontFactory.HELVETICA,12);
		//Font font2=FontFactory.getFont(FontFactory.HELVETICA,11,Font.BOLD);
		
		JsonArray orderhdrDateData1 = (JsonArray) model.get("profitSummaryView");
		//ProdBomReportModel prod = (ProdBomReportModel) model.get("prodbom");
		ProfitSummaryModel profit=(ProfitSummaryModel)model.get("profitSum");
		Report  report =   (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		//dateFormat = report.getDateFormat();
		
		JsonArray orderhdrDateData = (JsonArray) orderhdrDateData1.get(0);
		
		if (orderhdrDateData.size() != 0) {
			
			String orderdate = "BETWEEN " + getDateWithSystemFormat(profit.getStartdate()) + " AND " + getDateWithSystemFormat(profit.getEnddate()) + "";
			
			PdfPTable table25 = new PdfPTable(1);
			table25.setWidthPercentage(100.0f);
			table25.setSpacingBefore(2);
			table25.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			
			PdfPCell cell25 = new PdfPCell();
			cell25.setBorder(Rectangle.NO_BORDER);
			cell25.setPhrase(new Phrase(orderdate, font));
			
			cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			table25.addCell(cell25);
			doc.add(table25);
			
			PdfPTable table2 = new PdfPTable(8);
			table2.setWidths(new float[] { .8f, 3f, 1.5f, 1.5f, 1.5f,1.5f,1.5f,1.5f });
			table2.setWidthPercentage(100.0f);
			
			PdfPCell cell2 = new PdfPCell();
			cell2.setBackgroundColor(new BaseColor(255, 255, 255));
			cell2.setPadding(5);
			table2.getDefaultCell().setPaddingBottom(10.0f);
			table2.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			table2.getDefaultCell().setBorder(Rectangle.BOTTOM);
			table2.getDefaultCell().setBorderWidthBottom(2.0f);
			cell2.setPhrase(new Phrase("SI NO", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("SHOP", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("MATERIAL", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("LABOUR", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("OTHER", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("TOTAL COST", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("SALES", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("PROFIT", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			
			
			int count = 0;
			for (int i = 0; i < orderhdrDateData.size(); i++) {
				count++;
				String sino = "" + count;
				
				JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);
				cell2.setPhrase(new Phrase(sino));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);
				
				String shop_name1=orderdata.get("shop_name").getAsString().toLowerCase();
				if(shop_name1.length() !=0)
				{
				shop_name1=shop_name1.substring(0,1).toUpperCase()+shop_name1.substring(1);
				}
				cell2.setPhrase(new Phrase(shop_name1));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase((orderdata.get("material_cost") != null && orderdata.get("material_cost").getAsString()
						.length() != 0) ? orderdata.get("material_cost").getAsString() : ""));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase((orderdata.get("labour_cost") != null && orderdata.get("labour_cost").getAsString()
						.length() != 0) ? orderdata.get("labour_cost").getAsString() : ""));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase((orderdata.get("other_cost") != null && orderdata.get("other_cost").getAsString()
						.length() != 0) ? orderdata.get("other_cost").getAsString() : ""));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase((orderdata.get("total_cost") != null && orderdata.get("total_cost").getAsString()
						.length() != 0) ? orderdata.get("total_cost").getAsString() : ""));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase((orderdata.get("total_sales_amount") != null && orderdata.get("total_sales_amount").getAsString()
						.length() != 0) ? orderdata.get("total_sales_amount").getAsString() : ""));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase((orderdata.get("profit") != null && orderdata.get("profit").getAsString()
						.length() != 0) ? orderdata.get("profit").getAsString() : ""));
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
			
				
			}
			
			doc.add(table2);
			
		}
		else
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
				cell89.setPhrase(new Phrase("No Items Available ", font1));
				cell89.setHorizontalAlignment(Element.ALIGN_CENTER);
				table89.addCell(cell89);
				doc.add(table89);
	      
				
		}
		
	}
	
	*//**
	 * @param value
	 * @param places
	 * @return
	 *//*
	public double round(double value , int places) {
	
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}
*/