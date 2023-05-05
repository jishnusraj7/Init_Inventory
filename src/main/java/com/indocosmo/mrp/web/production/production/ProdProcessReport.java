package com.indocosmo.mrp.web.production.production;

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


public class ProdProcessReport extends AbstractITextPdfView {

	
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
	protected void buildPdfDocument(Map<String, Object> model , Document document , PdfWriter writer ,
			HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		// TODO Auto-generated method stub
		
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA,9);
		Font font=FontFactory.getFont(FontFactory.HELVETICA,11,Font.BOLD);
		Font font2=FontFactory.getFont(FontFactory.HELVETICA,9,Font.BOLD);
		
		JsonArray itemData=(JsonArray)model.get("prodprocessreportView");
		Report  report =   (Report) model.get("reportName");

		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		
		if(itemData.size()!=0)
		{
		
		PdfPTable table1 = new PdfPTable(3);
		table1.setWidths(new float[] {.7f, 1f,.7f});

		table1.setWidthPercentage(100.0f);
		
		PdfPCell cell1 = new PdfPCell();
		cell1.setBackgroundColor(new BaseColor(255, 255, 255));
		cell1.setPadding(5);
		table1.getDefaultCell().setPaddingBottom(10.0f);
		table1.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
		table1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table1.getDefaultCell().setBorderWidthBottom(2.0f);
		
		
		cell1.setPhrase(new Phrase("FROM",font ));	
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table1.addCell(cell1);


		cell1.setPhrase(new Phrase("REQUEST NO" ,font2));	
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table1.addCell(cell1);

		
		cell1.setPhrase(new Phrase("REQUEST DATE" ,font2));	
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table1.addCell(cell1);
		
		document.add(table1);
	
		JsonObject materialdata = (JsonObject) itemData.get(0);
		String department = materialdata.get("department_name").getAsString();
		String req_no = materialdata.get("req_no").getAsString();
		String req_date = materialdata.get("req_date").getAsString();
		
		PdfPTable table1a = new PdfPTable(3);
		table1a.setWidths(new float[] {.7f, 1f,.7f});

		table1a.setWidthPercentage(100.0f);
		
		PdfPCell cell1a = new PdfPCell();
		cell1a.setBackgroundColor(new BaseColor(255, 255, 255));
		cell1a.setPadding(5);
		table1a.getDefaultCell().setPaddingBottom(10.0f);
		table1a.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
		table1a.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table1a.getDefaultCell().setBorderWidthBottom(2.0f);
		
		cell1a.setPhrase(new Phrase(department,font1 ));	
		cell1a.setHorizontalAlignment(Element.ALIGN_CENTER);
		table1a.addCell(cell1a);


		cell1a.setPhrase(new Phrase(req_no ,font1));	
		cell1a.setHorizontalAlignment(Element.ALIGN_CENTER);
		table1a.addCell(cell1a);

		
		cell1a.setPhrase(new Phrase(req_date ,font1));	
		cell1a.setHorizontalAlignment(Element.ALIGN_CENTER);
		table1a.addCell(cell1a);
		
		document.add(table1a);
		
		PdfPTable table2 = new PdfPTable(3);
		table2.setWidths(new float[] {.7f, 1f,.7f});

		table2.setWidthPercentage(100.0f);
		
		
		PdfPCell cell2 = new PdfPCell();
		cell2.setBackgroundColor(new BaseColor(255, 255, 255));
		cell2.setPadding(5);
		table2.getDefaultCell().setPaddingBottom(10.0f);
		table2.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
		table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table2.getDefaultCell().setBorderWidthBottom(2.0f);
		
		
		cell2.setPhrase(new Phrase("#",font ));	
		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell2);


		cell2.setPhrase(new Phrase("ITEM NAME" ,font2));	
		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell2);

		
		cell2.setPhrase(new Phrase("QUANTITY" ,font2));	
		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell2);
		
		document.add(table2);
		
		PdfPTable table2a = new PdfPTable(3);
		table2a.setWidths(new float[] {.7f, 1f,.7f});

		table2a.setWidthPercentage(100.0f);
		
		String sino="";	
		int count=0;
		
		for (int i = 0; i < itemData.size(); i++) {
			
			JsonObject reqmaterialdata = (JsonObject) itemData.get(i);
			
			count++;
			sino=""+count;
			
			
			
		PdfPCell cell2a = new PdfPCell();
		cell2a.setBackgroundColor(new BaseColor(255, 255, 255));
		cell2a.setPadding(5);
		table2a.getDefaultCell().setPaddingBottom(10.0f);
		table2a.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
		table2a.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table2a.getDefaultCell().setBorderWidthBottom(2.0f);
		
		
		cell2a.setPhrase(new Phrase(sino,font1 ));	
		cell2a.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2a.addCell(cell2a);


		cell2a.setPhrase(new Phrase(reqmaterialdata.get("stock_item_name").getAsString() ,font1));	
		cell2a.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2a.addCell(cell2a);

		
		cell2a.setPhrase(new Phrase(reqmaterialdata.get("request_qty").getAsString() ,font1));	
		cell2a.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2a.addCell(cell2a);
		}
		document.add(table2a);
		
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
			cell89.setPhrase(new Phrase("No Items Available ", font));
			cell89.setHorizontalAlignment(Element.ALIGN_CENTER);
			table89.addCell(cell89);
			document.add(table89);
			
		}
/*		
		if(itemData.size()!=0)
		{
			Integer department_id=-1;

			JsonObject json = (JsonObject) itemData.get(0);
			String proddate="DATE:"+getDateWithSystemFormat(json.get("production_date").getAsString());	
		

			
			PdfPTable table25 = new PdfPTable(1);
			table25.setWidthPercentage(100.0f);
			table25.setSpacingBefore(2);
			table25.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			
			PdfPCell cell25 = new PdfPCell();
			cell25.setBorder(Rectangle.NO_BORDER);
			cell25.setPhrase(new Phrase(proddate, font));
			
			cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			table25.addCell(cell25);
			document.add(table25);

			
			PdfPTable table2 = new PdfPTable(4);
			table2.setWidths(new float[] {.4f, 1.5f,.7f, .6f});

			table2.setWidthPercentage(100.0f);
			
			PdfPCell cell2 = new PdfPCell();
			cell2.setBackgroundColor(new BaseColor(255, 255, 255));
			cell2.setPadding(5);
			table2.getDefaultCell().setPaddingBottom(10.0f);
			table2.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
			table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table2.getDefaultCell().setBorderWidthBottom(2.0f);
			
			
			JsonObject orderdata5 = (JsonObject) itemData.get(0);
			department_id=orderdata5.get("department_id").getAsInt();
			
			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(6);
			cellspan.setColspan(4);

			cellspan.setPhrase(new Phrase(orderdata5.get("department_name").getAsString(),font));
			cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cellspan);
			
			
			cell2.setPhrase(new Phrase("#",font ));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);


			cell2.setPhrase(new Phrase("ITEM NAME" ,font2));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);


			cell2.setPhrase(new Phrase("UNIT" ,font2));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);

			cell2.setPhrase(new Phrase("REQUIRED QTY" ,font2));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);

			
			String sino="";	
			int count=0;


			for (int i = 0; i < itemData.size(); i++) {
				
			JsonObject orderdata = (JsonObject) itemData.get(i);
			
			if(!((orderdata.get("department_id").getAsInt())==department_id)){
				count=0;
				PdfPCell cellspan1 = new PdfPCell();
				cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan1.setPadding(6);
				cellspan1.setColspan(9);

				cellspan1.setPhrase(new Phrase(orderdata.get("department_name").getAsString(),font));
				cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cellspan1);
				department_id=orderdata.get("department_id").getAsInt();

				//count=0;

				cell2.setPhrase(new Phrase("#",font ));	
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);


				cell2.setPhrase(new Phrase("ITEM NAME" ,font2));	
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);


				cell2.setPhrase(new Phrase("UNIT" ,font2));	
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);

				cell2.setPhrase(new Phrase("REQUIRED QTY" ,font2));	
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);
				
				
				
			}
			count++;
			sino=""+count;
			
			
			cell2.setPhrase(new Phrase(sino,font1 ));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);



			cell2.setPhrase(new Phrase(orderdata.get("bom_item_name").getAsString(),font1));	
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell2);
			
			cell2.setPhrase(new Phrase(orderdata.get("uomcode").getAsString(),font1));	
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell2);


			cell2.setPhrase(new Phrase(getNumberWithDecimal(orderdata.get("required_qty").getAsDouble()),font1));	
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell2);

			


			
			
			
			
			
			
			
			
		}
			
			document.add(table2);
		
	
	
	
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
			cell89.setPhrase(new Phrase("No Items Available ", font));
			cell89.setHorizontalAlignment(Element.ALIGN_CENTER);
			table89.addCell(cell89);
			document.add(table89);
			
		}*/
		
	}
	
}
