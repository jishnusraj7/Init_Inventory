
package com.indocosmo.mrp.web.report.stockdisposalreport;

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
import com.indocosmo.mrp.web.report.productionreport.model.ProductionReportModel;
import com.indocosmo.mrp.web.report.stockdisposalreport.model.DisposalReportModel;
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

public class DisposalReportSummary extends AbstractITextPdfView {
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



		Font font1 = FontFactory.getFont(FontFactory.HELVETICA,9);
		Font font=FontFactory.getFont(FontFactory.HELVETICA,11,Font.BOLD);
		Font font2=FontFactory.getFont(FontFactory.HELVETICA,9,Font.BOLD);

		JsonArray orderhdrDateData1=(JsonArray)model.get("disposalReportView");


		Report  report =   (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		DisposalReportModel prod =(DisposalReportModel)model.get("prod");


		JsonArray orderhdrDateData=(JsonArray) orderhdrDateData1.get(0);
		//JsonArray totalorderhdrDateData=(JsonArray) orderhdrDateData1.get(1);



		if(orderhdrDateData.size()!=0){
	

				JsonObject json = (JsonObject) orderhdrDateData.get(0);
				String orderdate="BETWEEN "+getDateWithSystemFormat(prod.getStartdate())+" AND "+getDateWithSystemFormat(prod.getEnddate())+"";
				String department_code1="";
				String stkName="";
				String reason="";

				
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
				
				JsonObject orderdata5 = (JsonObject) orderhdrDateData.get(0);
				department_code1=orderdata5.get("department_name").getAsString();
				stkName=orderdata5.get("stock_item_name").getAsString();
				reason=orderdata5.get("disposalreason").getAsString();
				
				PdfPTable table23 = new PdfPTable(2);
				
				table23.setWidthPercentage(100.0f);
				
				PdfPCell cell23 = new PdfPCell();
				cell23.setBackgroundColor(new BaseColor(255, 255, 255));
				cell23.setPadding(5);
				table23.getDefaultCell().setPaddingBottom(10.0f);
				table23.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
				table23.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table23.getDefaultCell().setBorderWidthBottom(2.0f);
				
				cell23.setPhrase(new Phrase(department_code1, font));
				cell23.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell23.setBorderColorBottom(new BaseColor(255, 255, 255));
				cell23.setBorder(Rectangle.NO_BORDER);
				table23.addCell(cell23);
				
				cell23.setPhrase(new Phrase("", font1));
				cell23.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell23.setBorderColorBottom(new BaseColor(255, 255, 255));
				cell23.setBorder(Rectangle.NO_BORDER);
				table23.addCell(cell23);
				
				doc.add(table23);


				PdfPTable table2 = new PdfPTable(4);
				table2.setWidths(new float[] {1f, 1.5f, 1f, 1f});

				table2.setWidthPercentage(100.0f);
				table25.setSpacingBefore(0);
				PdfPCell cell2 = new PdfPCell();
				cell2.setBackgroundColor(new BaseColor(255, 255, 255));
				cell2.setPadding(5);
				table2.getDefaultCell().setPaddingBottom(10.0f);
				table2.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
				table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table2.getDefaultCell().setBorderWidthBottom(2.0f);

				PdfPCell cellspan = new PdfPCell();
				cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan.setPadding(6);
				cellspan.setColspan(4);

			
				cellspan.setPhrase(new Phrase(orderdata5.get("disposalreason").getAsString(),font));
				cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cellspan);
				
				cell2.setPhrase(new Phrase("#",font ));	
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);

			/*	cell2.setPhrase(new Phrase("REASON" ,font2));	
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell2);*/

				cell2.setPhrase(new Phrase("ITEM NAME" ,font2));	
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell2);


				cell2.setPhrase(new Phrase("DISPOSAL DATE" ,font2));	
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell2);

				cell2.setPhrase(new Phrase("DISPOSAL QTY" ,font2));	
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);
				
				int frt=0;

				String sino="";
				int count=0;
				for (int i = 0; i < orderhdrDateData.size(); i++) {

					int f=0;
					frt=0;
					JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);
					if(i==0)
					{
						frt=1;
					}

					
					if(!((orderdata.get("disposalreason").getAsString()).equals(reason))){

						f=1;

						PdfPCell cellspan1 = new PdfPCell();
						cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
						cellspan1.setPadding(6);
						cellspan1.setColspan(4);

						cellspan1.setPhrase(new Phrase(orderdata.get("disposalreason").getAsString(),font));
						cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cellspan1);
						//department_code1=orderdata.get("department_name").getAsString();
						reason=orderdata.get("disposalreason").getAsString();

						count=0;

						cell2.setPhrase(new Phrase("#",font2 ));	
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell2);
						
					/*	cell2.setPhrase(new Phrase("REASON" ,font2));	
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell2);*/

						cell2.setPhrase(new Phrase("ITEM NAME" ,font2));	
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase("DISPOSAL DATE" ,font2));	
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase("DISPOSAL QTY" ,font2));	
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell2);

						
						
						//}
					} 

					
						count++;
						sino=""+count;
						reason=orderdata.get("disposalreason").getAsString();

						cell2.setPhrase(new Phrase(sino,font1 ));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell2);

					/*	cell2.setPhrase(new Phrase(reason,font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell2);*/

						
					
				
					cell2.setPhrase(new Phrase(orderdata.get("stock_item_name").getAsString(),font1));	
					cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell2); 
					
					cell2.setPhrase(new Phrase(getDateWithSystemFormat(orderdata.get("disposal_date").getAsString()),font1));	
					cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell2);


					cell2.setPhrase(new Phrase(getNumberWithDecimal(orderdata.get("disposeqty").getAsDouble()),font1));	
					cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell2);
				}	
				
				doc.add(table2);
			




			

		
				}


		//for no items available	
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

	
	
}}



