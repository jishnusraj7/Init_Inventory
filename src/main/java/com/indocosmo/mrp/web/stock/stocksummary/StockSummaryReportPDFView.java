package com.indocosmo.mrp.web.stock.stocksummary;

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

public class StockSummaryReportPDFView extends AbstractITextPdfView{

	Integer decimalPlace;

	@Override
	public Document newDocument() {
		return new Document(PageSize.A4.rotate(),36, 36, 120, 80);
	}
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JsonArray stockSummaryData = (JsonArray)model.get("stockSummaryData");
		Report  report =  (Report) model.get("reportName");
		String fromDate = (String) model.get("fromDate");
		String toDate = (String) model.get("toDate");
		String department_name = (String) model.get("department");
		decimalPlace = report.getDecimalPlace();
		
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA,9);
		Font font = FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD);
		final Font SUBFONT = FontFactory.getFont(FontFactory.HELVETICA,9,Font.BOLD);
		
		String Header1="BETWEEN  "+ fromDate +"  AND  "+ toDate;

		PdfPTable headerTable = new PdfPTable(1);
		headerTable.setWidthPercentage(100.0f);
		headerTable.setSpacingBefore(2);
		headerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPCell cellHeader = new PdfPCell();
		cellHeader.setBorder(Rectangle.NO_BORDER);
		cellHeader.setPhrase(new Phrase(Header1,font));

		cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);

		headerTable.addCell(cellHeader);
		document.add(headerTable);
		
		PdfPTable table = new PdfPTable(12);
		table.setWidths(new float[] {.8f,4.5f,2f,2f,2f,2f,2f,2f,2f,2f,2f,2f});
		table.setWidthPercentage(100.0f);
		
		PdfPCell cellspan = new PdfPCell();
		cellspan.setPadding(12);
		cellspan.setColspan(12);
		
		cellspan.setPhrase(new Phrase(department_name, font));
		cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cellspan);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(new BaseColor(255, 255, 255));
		cell.setPadding(5);
		
		table.getDefaultCell().setPaddingBottom(10.0f);
		table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
		table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table.getDefaultCell().setBorderWidthBottom(2.0f);
		
		cell.setPhrase(new Phrase("#", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("ITEM", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("OPENING", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
			
		cell.setPhrase(new Phrase("STOCKIN", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("STOCKOUT", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("RETURN STOCK", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("PRODUCTION", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("BOM OUT", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("TRANSFER", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("ADJUSMENT", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
			
		cell.setPhrase(new Phrase("DISPOSAL", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("CLOSING", SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		int count = 0;
		for (int i = 0; i < stockSummaryData.size(); i++) {
			JsonObject stockSummary = (JsonObject) stockSummaryData.get(i);
			count=count+1;
		    
			cell.setPhrase(new Phrase("" + count, font1));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(stockSummary.get("stock_item_name").getAsString(), font1));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(stockSummary.get("opening_stock").getAsDouble()), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(stockSummary.get("stock_in").getAsDouble()), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(stockSummary.get("stock_out").getAsDouble()), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(stockSummary.get("return_stock").getAsDouble()), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(stockSummary.get("production").getAsDouble()), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(stockSummary.get("stock_out_BOM").getAsDouble()), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(stockSummary.get("stock_transfer").getAsDouble()), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(stockSummary.get("stock_adjustment").getAsDouble()), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(stockSummary.get("stock_disposal").getAsDouble()), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(getCurrentStock(stockSummary)), font1));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
		}
		document.add(table);
	}

		private Double getCurrentStock(JsonObject stockSummary) {
		
			double currentStock = 0.0;
			double total_stock_in = stockSummary.get("opening_stock").getAsDouble() + stockSummary.get("stock_in").getAsDouble() 
					+ stockSummary.get("production").getAsDouble() + stockSummary.get("stock_adjustment").getAsDouble();
			
			double total_stock_out = stockSummary.get("stock_out").getAsDouble() + stockSummary.get("stock_out_BOM").getAsDouble()
					+ stockSummary.get("stock_transfer").getAsDouble() + stockSummary.get("stock_disposal").getAsDouble();
			
			currentStock = total_stock_in - total_stock_out;
			return currentStock;
	}

		public String getNumberWithDecimal(Double value) {

			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
			return bd.toString();
		}
		
}
	
	
