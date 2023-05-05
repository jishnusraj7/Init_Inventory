package com.indocosmo.mrp.web.production.planning;

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

public class ProdReport extends AbstractITextPdfView {
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
			return new Document(PageSize.A4,36, 36, 120, 80);
	}
	 

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		Font font=FontFactory.getFont(FontFactory.HELVETICA,11,Font.BOLD);
		
		Font font1=FontFactory.getFont(FontFactory.HELVETICA,9);

		JsonArray orderhdrDateData1=(JsonArray)model.get("prodView");
			Report  report =   (Report) model.get("reportName");

			decimalPlace = report.getDecimalPlace();
			dateFormat = report.getDateFormat();
			
				JsonArray orderhdrDateData=(JsonArray) orderhdrDateData1.get(0);
				JsonArray totalorderhdrDateData=(JsonArray) orderhdrDateData1.get(1);
				
				
				
				if(orderhdrDateData.size()!=0){
					JsonObject json = (JsonObject) orderhdrDateData.get(0);
			String orderdate=getDateWithSystemFormat(json.get("closing_date").getAsString());
		 
			
			PdfPTable table1 = new PdfPTable(3);
			table1.setWidths(new float[] {1.7f,3f,6f});
			table1.setWidthPercentage(100.0f);
			table1.setSpacingBefore(10);
			PdfPCell cell1 = new PdfPCell();
			cell1.setBackgroundColor(new BaseColor(255, 255, 255));
			cell1.setPadding(5);
			table1.getDefaultCell().setPaddingBottom(10.0f);
			table1.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
			table1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table1.getDefaultCell().setBorderWidthBottom(2.0f);
			cell1.setPhrase(new Phrase("Closing Date:",font ));
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell1);
			cell1.setPhrase(new Phrase(orderdate ,font));
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell1);
			if(report.getIs_customer().equals("2")){
			cell1.setPhrase(new Phrase("SHOPS ORDERS",font ));
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table1.addCell(cell1);}
			else if(report.getIs_customer().equals("1")){
				cell1.setPhrase(new Phrase("CUSTOMERS ORDERS",font ));
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell1);
			}else if(report.getIs_customer().equals("3")){
				cell1.setPhrase(new Phrase("SUMMARY",font ));
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell1);
			}
			
			else if(report.getIs_customer().equals("0")){
				cell1.setPhrase(new Phrase("ALL ORDERS",font ));
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell1);
			}
			
			doc.add(table1);
			
			
			if(!(report.getIs_customer().equals("1"))){
			
			int tableSize=3;
			if(!(report.getIs_customer().equals("3"))){
			JsonObject orderdata1 = (JsonObject) orderhdrDateData.get(0);
			String shop_code=orderdata1.get("orderfrom").getAsString();
			for (int i = 0; i < orderhdrDateData.size(); i++) {
				JsonObject orderdata2 = (JsonObject) orderhdrDateData.get(i);

				
			if(!((orderdata2.get("orderfrom").getAsString()).equals(shop_code))){
				tableSize++;
				shop_code=orderdata2.get("orderfrom").getAsString();
			}
			
			}
			}
			
			String shop_code1="";
			PdfPTable table2 = new PdfPTable(3);
			table2.setWidths(new float[] {.2f,3f,1f});
			
			table2.setWidthPercentage(100.0f);
			table1.setSpacingBefore(0);
			PdfPCell cell2 = new PdfPCell();
			cell2.setBackgroundColor(new BaseColor(255, 255, 255));
			cell2.setPadding(5);
			table2.getDefaultCell().setPaddingBottom(10.0f);
			table2.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
			table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table2.getDefaultCell().setBorderWidthBottom(2.0f);
			if(!(report.getIs_customer().equals("3"))){
			JsonObject orderdata5 = (JsonObject) orderhdrDateData.get(0);
		 shop_code1=orderdata5.get("orderfrom").getAsString();
			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(5);
			cellspan.setColspan(3);
			
			cellspan.setPhrase(new Phrase(orderdata5.get("orderfrom").getAsString(),font));
			cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
		table2.addCell(cellspan);
			}
			
			
			cell2.setPhrase(new Phrase("#",font ));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			
			cell2.setPhrase(new Phrase("ITEM NAME" ,font));	
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell2);
		/*	 if(report.getIs_customer().equals("3")){*/
			cell2.setPhrase(new Phrase("QUANTITY" ,font));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);/*}else{
				
				JsonObject orderdata5 = (JsonObject) orderhdrDateData.get(0);
				String shop_code1=orderdata5.get("orderfrom").getAsString();
				cell2.setPhrase(new Phrase(shop_code1,font));	
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);
				for (int i = 0; i < orderhdrDateData.size(); i++) {
					JsonObject orderdata6 = (JsonObject) orderhdrDateData.get(i);

					
				if(!((orderdata6.get("orderfrom").getAsString()).equals(shop_code1))){
					shop_code1=orderdata6.get("orderfrom").getAsString();

					cell2.setPhrase(new Phrase(orderdata6.get("orderfrom").getAsString(),font));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);
				}
				
				}
				
				
				
				
				
				
			
			}*/
			

		
			
		int count=0;
			for (int i = 0; i < orderhdrDateData.size(); i++) {
				JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);

				if(!(report.getIs_customer().equals("3"))){
				if(!((orderdata.get("orderfrom").getAsString()).equals(shop_code1))){
					
					PdfPCell cellspan1 = new PdfPCell();
					cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
					cellspan1.setPadding(5);
					cellspan1.setColspan(3);
					
					cellspan1.setPhrase(new Phrase(orderdata.get("orderfrom").getAsString(),font));
					cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cellspan1);
				 shop_code1=orderdata.get("orderfrom").getAsString();
				 count=0;
					}}
				
				 count++;
				 String sino=""+count;
				
				cell2.setPhrase(new Phrase(sino ,font1));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase(orderdata.get("stock_item_name").getAsString(),font1));	
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase(orderdata.get("qty").getAsString(),font1));	
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
					
			
			}
		 
			doc.add(table2);
			}
			else{
				
				
				PdfPTable table2 = new PdfPTable(3);
				table2.setWidths(new float[] {.2f,3f,1f});
				table2.setWidthPercentage(100.0f);
				table1.setSpacingBefore(0);
				PdfPCell cell2 = new PdfPCell();
				cell2.setBackgroundColor(new BaseColor(255, 255, 255));
				cell2.setPadding(5);
				table2.getDefaultCell().setPaddingBottom(10.0f);
				table2.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
				table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table2.getDefaultCell().setBorderWidthBottom(2.0f);
			 
				JsonObject orderdata5 = (JsonObject) orderhdrDateData.get(0);
				String shop_code1=orderdata5.get("orderfrom").getAsString();
			

				PdfPCell cellspan = new PdfPCell();
				cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan.setPadding(5);
				cellspan.setColspan(3);
				
				cellspan.setPhrase(new Phrase(orderdata5.get("orderfrom").getAsString(),font));
				cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cellspan);
				
			cell2.setPhrase(new Phrase("#",font ));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			
			cell2.setPhrase(new Phrase("ITEM NAME" ,font));	
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell2);

			cell2.setPhrase(new Phrase("QUANTITY" ,font));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
				
				
				int count=0;
				for (int i = 0; i < orderhdrDateData.size(); i++) {
					 count++;
					 String sino=""+count;
					
					JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);
					
					if(!((orderdata.get("orderfrom").getAsString()).equals(shop_code1))){
						count=0;
						count++;
						 sino=""+count;
						shop_code1=orderdata.get("orderfrom").getAsString();
				
						PdfPCell cellspan1 = new PdfPCell();
						cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
						cellspan1.setPadding(5);
						cellspan1.setColspan(3);
						
						cellspan1.setPhrase(new Phrase(orderdata.get("orderfrom").getAsString() ,font));
						cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cellspan1);
					
					cell2.setPhrase(new Phrase("#",font ));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);
					
					cell2.setPhrase(new Phrase("ITEM NAME" ,font));	
					cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell2);
		
					cell2.setPhrase(new Phrase("QUANTITY" ,font));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);
					
					
					}
					
					cell2.setPhrase(new Phrase(sino,font1 ));
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);
					
					cell2.setPhrase(new Phrase(orderdata.get("stock_item_name").getAsString(),font1));	
					cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell2);
					
					cell2.setPhrase(new Phrase(orderdata.get("qty").getAsString(),font1));	
					cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell2);
					
				}
			 
				doc.add(table2);
				
				
			}
			
			if(!(report.getIs_customer().equals("3"))){
			
			PdfPTable table2 = new PdfPTable(3);
			table2.setWidths(new float[] {.2f,3f,1f});
			table2.setWidthPercentage(100.0f);
			table1.setSpacingBefore(0);
			PdfPCell cell2 = new PdfPCell();
			cell2.setBackgroundColor(new BaseColor(255, 255, 255));
			cell2.setPadding(5);
			table2.getDefaultCell().setPaddingBottom(10.0f);
			table2.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
			table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table2.getDefaultCell().setBorderWidthBottom(2.0f);
		 
			PdfPCell cellspan1 = new PdfPCell();
			cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan1.setPadding(5);
			cellspan1.setColspan(3);
			
			cellspan1.setPhrase(new Phrase("SUMMARY",font));
			cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table2.addCell(cellspan1);
		
			
			cell2.setPhrase(new Phrase("#",font ));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			
			cell2.setPhrase(new Phrase("ITEM NAME" ,font));	
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell2);

			cell2.setPhrase(new Phrase("TOTAL" ,font));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			
			
			
			int count=0;
			for (int i = 0; i < totalorderhdrDateData.size(); i++) {
				 count++;
				 String sino=""+count;
				
				JsonObject orderdata = (JsonObject) totalorderhdrDateData.get(i);
				cell2.setPhrase(new Phrase(sino ,font1));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase(orderdata.get("stock_item_name").getAsString(),font1));	
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase(orderdata.get("qty").getAsString(),font1));	
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
			}
		 
			doc.add(table2);
			}
			
				}
	}


}