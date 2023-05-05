package com.indocosmo.mrp.web.report.bomratecomparison;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.report.bomratecomparison.model.BomRateComparison;
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

public class BomRateComparisonReport extends AbstractITextPdfView {

	@Override
	public Document newDocument() {
		return new Document(PageSize.A4,36, 36, 120, 80);
	}
	public String getRateWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
		
		JsonArray bomRateData = (JsonArray) model.get("bomRateComparisonView");
		BomRateComparison bomRate = (BomRateComparison) model.get("bomrate");
		Report  report =   (Report) model.get("reportName");
		
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 9);
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);

		JsonArray bomRatecmprData = (JsonArray) bomRateData.get(0);
		
		if (bomRatecmprData.size() != 0) {
			
			
			PdfPTable table2 = new PdfPTable(5);
			table2.setWidths(new float[] { .8f, 3f, 1.5f, 1.5f, 1.5f });
			table2.setWidthPercentage(100.0f);
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
			cell2.setPhrase(new Phrase("BOM RATE", font1));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("PURCHASE RATE", font1));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("RATE DIFFERENCE", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			
			
			int count = 0;
			for (int i = 0; i < bomRatecmprData.size(); i++) {

				count++;
				String sino = "" + count;
				
				JsonObject ratedata = (JsonObject) bomRatecmprData.get(i);
				
				PdfPCell cell3 = new PdfPCell();
				cell3.setBackgroundColor(new BaseColor(255, 255, 255));
				cell3.setPadding(5);
				table2.getDefaultCell().setPaddingBottom(10.0f);
				table2.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
				table2.getDefaultCell().setBorder(Rectangle.BOTTOM);
				table2.getDefaultCell().setBorderWidthBottom(2.0f);
				
				cell3.setPhrase(new Phrase(sino,font2));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell3);
				
				cell3.setPhrase(new Phrase(ratedata.get("stockName").getAsString(),font2));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell3);
				
				cell3.setPhrase(new Phrase(getRateWithDecimal(ratedata.get("new_bom_rate").getAsDouble()),font2));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell3);
				
				if(ratedata.get("new_purchase_rate").equals("0.00")){
					
					cell3.setPhrase(new Phrase(getRateWithDecimal(ratedata.get("purchase_rate").getAsDouble()),font2));
					cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell3);
				}else{
					cell3.setPhrase(new Phrase(getRateWithDecimal(ratedata.get("new_purchase_rate").getAsDouble()),font2));
					cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell3);
					
				}
				

				cell3.setPhrase(new Phrase(getRateWithDecimal(ratedata.get("new_diff_value").getAsDouble()),font2));
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell3);
				
			}
			
			doc.add(table2);
			
			
		}else {
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

}
