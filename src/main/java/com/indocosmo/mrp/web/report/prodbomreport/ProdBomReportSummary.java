package com.indocosmo.mrp.web.report.prodbomreport;

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

public class ProdBomReportSummary extends AbstractITextPdfView {
	
	String companyName;
	
	String dateFormat;
	
	Integer decimalPlace;
	
	String currency;
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.common.AbstractITextPdfView#newDocument(java.util.Map)
	 */
	@Override
	public Document newDocument() {
		return new Document(PageSize.A4,36, 36, 120, 80);
	}

	
	/* @Override public Document newDocument() { return new
	 * Document(PageSize.A4.rotate(),36, 36, 80, 80); } */
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model , Document doc , PdfWriter writer ,
			HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
		
		JsonArray orderhdrDateData1 = (JsonArray) model.get("prodBomSummaryView");
		ProdBomReportModel prod = (ProdBomReportModel) model.get("prodbom");
		Report  report =   (Report) model.get("reportName");
		dateFormat = report.getDateFormat();
		decimalPlace=report.getDecimalPlace();
		
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 9);
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
		JsonArray orderhdrDateData = (JsonArray) orderhdrDateData1.get(0);
		
		if (orderhdrDateData.size() != 0) {
			
			String orderdate = "BETWEEN " + getDateWithSystemFormat(prod.getStartdate()) + " AND " + getDateWithSystemFormat(prod.getEnddate()) + "";
			
			PdfPTable table25 = new PdfPTable(1);
			table25.setWidthPercentage(100.0f);
			table25.setSpacingBefore(2);
			table25.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			
			PdfPCell cell25 = new PdfPCell();
			cell25.setBorder(Rectangle.NO_BORDER);
			cell25.setPhrase(new Phrase(orderdate, font));
			
			cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
			table25.getDefaultCell().setPaddingBottom(10.0f);
			table25.addCell(cell25);
			doc.add(table25);
			
			PdfPTable table2 = new PdfPTable(7);
			table2.setWidths(new float[] { .8f, 2.3f,1f, 0.75f, 0.75f, 0.75f, 0.75f });
			table2.setWidthPercentage(100.0f);
			table2.setPaddingTop(15.0f);
			table2.setSpacingBefore(5);
			
			PdfPCell cell2 = new PdfPCell();
			cell2.setBackgroundColor(new BaseColor(255, 255, 255));
			cell2.setPadding(5);
			table2.getDefaultCell().setPaddingBottom(10.0f);
			table2.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			table2.getDefaultCell().setBorder(Rectangle.BOTTOM);
			table2.getDefaultCell().setBorderWidthBottom(2.0f);
			cell2.setPhrase(new Phrase("SI#", font1));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("ITEM", font1));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			
			cell2.setPhrase(new Phrase("Txn Date", font1));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			
			
			
			cell2.setPhrase(new Phrase("Opening", font1));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("In Qty", font1));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("Out Qty", font1));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("Balance", font1));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			
			int count = 0;
			String stockitemname = "0";
			int flagName=0;
				
		
			for (int i = 0; i < orderhdrDateData.size(); i++) {
				
				
				if(!stockitemname.equals(((JsonObject)orderhdrDateData.get(i)).get("stock_item_id").getAsString()))
				{
					flagName=1;
					count++;
				}
				String sino = "" + count;
				JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);
				if(flagName==1)
				{
				    cell2.setPhrase(new Phrase(sino,font2));
				}else
				{
					cell2.setPhrase(new Phrase(" ", font2));
				}
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);
				
				if(flagName==1)
				{
				cell2.setPhrase(new Phrase(orderdata.get("stock_item_name").getAsString(),font2));
				}else
				{
				cell2.setPhrase(new Phrase(" ", font2));
				}
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase(getDateWithSystemFormat(orderdata.get("txn_date").getAsString()),font2));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell2);
				
				
				
				cell2.setPhrase(new Phrase((orderdata.get("opening") != null && orderdata.get("opening").getAsString()
						.length() != 0) ? getNumberWithDecimal(orderdata.get("opening").getAsDouble()) : "",font2));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase((orderdata.get("inqty") != null && orderdata.get("inqty").getAsString()
						.length() != 0) ? getNumberWithDecimal(orderdata.get("inqty").getAsDouble()) : "",font2));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase((orderdata.get("bom_qty") != null && orderdata.get("bom_qty").getAsString()
						.length() != 0) ? getNumberWithDecimal(orderdata.get("bom_qty").getAsDouble()) : "",font2));
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase(getNumberWithDecimal(orderdata.get("diff_qty").getAsDouble()),font2));
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				stockitemname = ((JsonObject)orderhdrDateData.get(i)).get("stock_item_id").getAsString();
				flagName=0;
			}
			
			doc.add(table2);
			
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
				doc.add(table89);
		}
	}
	
	public String getDateWithSystemFormat(String date) throws Exception {

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		final String systemDateFormat = dateFormat;
		DateFormat formatter = new SimpleDateFormat(systemDateFormat);
		final String dateWithSystemFormat = formatter.format(date1);
		return dateWithSystemFormat;
	}

	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}
}
