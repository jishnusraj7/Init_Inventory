package com.indocosmo.mrp.web.report.productionreport;

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


public class ProdBalanceReportSummary extends AbstractITextPdfView{
	
	
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
	protected void buildPdfDocument(Map<String, Object> model , Document doc , PdfWriter writer ,
			HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		// TODO Auto-generated method stub
		
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA,9);
		Font font=FontFactory.getFont(FontFactory.HELVETICA,11,Font.BOLD);
		Font font2=FontFactory.getFont(FontFactory.HELVETICA,9,Font.BOLD);
		
		JsonArray orderhdrDateData1=(JsonArray)model.get("prodBalSummaryView");


		Report  report =   (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		ProductionReportModel prod =(ProductionReportModel)model.get("prod");
		
		
		JsonArray orderhdrDateData=(JsonArray) orderhdrDateData1.get(0);
		
		if(orderhdrDateData.size()!=0){

				//JsonObject json = (JsonObject) orderhdrDateData.get(0);
				String orderdate="DATE: "+getDateWithSystemFormat(prod.getStartdate())+"";
				String shop_code1="";

				PdfPTable table1 = new PdfPTable(3);
				table1.setWidths(new float[] {3f,8f,6f});
				table1.setWidthPercentage(100.0f);
				table1.setSpacingBefore(10);
				PdfPCell cell1 = new PdfPCell();
				cell1.setBackgroundColor(new BaseColor(255, 255, 255));
				cell1.setPadding(5);
				table1.getDefaultCell().setPaddingBottom(10.0f);
				table1.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
				table1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table1.getDefaultCell().setBorderWidthBottom(2.0f);
				cell1.setPhrase(new Phrase("   ",font ));
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase(orderdate ,font));
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell1);
				if(report.getIs_customer().equals("2")){
					cell1.setPhrase(new Phrase("SHOP ORDERS",font ));
					cell1.setBorder(Rectangle.NO_BORDER);
					cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table1.addCell(cell1);}
				else if(report.getIs_customer().equals("1")){
					cell1.setPhrase(new Phrase("CUSTOMERS ORDERS",font ));
					cell1.setBorder(Rectangle.NO_BORDER);
					cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table1.addCell(cell1);
				}

				doc.add(table1);



				PdfPTable table2 = new PdfPTable(3);
				table2.setWidths(new float[] {1f, 2.5f, 1.5f});

				table2.setWidthPercentage(100.0f);
				table1.setSpacingBefore(0);
				PdfPCell cell2 = new PdfPCell();
				cell2.setBackgroundColor(new BaseColor(255, 255, 255));
				cell2.setPadding(5);
				table2.getDefaultCell().setPaddingBottom(10.0f);
				table2.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
				table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table2.getDefaultCell().setBorderWidthBottom(2.0f);
				/*if(!(report.getIs_customer().equals("3"))){*/
				JsonObject orderdata5 = (JsonObject) orderhdrDateData.get(0);
				shop_code1=orderdata5.get("orderfrom").getAsString();


				PdfPCell cellspan = new PdfPCell();
				cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan.setPadding(6);
				cellspan.setColspan(3);

				cellspan.setPhrase(new Phrase(orderdata5.get("orderfrom").getAsString(),font));
				cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cellspan);


				cell2.setPhrase(new Phrase("#",font ));	
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);


				cell2.setPhrase(new Phrase("ITEM NAME" ,font2));	
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell2);


			
				cell2.setPhrase(new Phrase("BALANCE QTY" ,font2));	
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);

				



				String sino="";
				int count=0;
				for (int i = 0; i < orderhdrDateData.size(); i++) {

					JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);
					
					if(!((orderdata.get("orderfrom").getAsString()).equals(shop_code1))){


						PdfPCell cellspan1 = new PdfPCell();
						cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
						cellspan1.setPadding(6);
						cellspan1.setColspan(3);

						cellspan1.setPhrase(new Phrase(orderdata.get("orderfrom").getAsString(),font));
						cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cellspan1);
						shop_code1=orderdata.get("orderfrom").getAsString();

						count=0;

						cell2.setPhrase(new Phrase("#",font2 ));	
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase("ITEM NAME" ,font2));	
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell2);

						
						cell2.setPhrase(new Phrase("BALANCE QTY" ,font2));	
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell2);

											} 

						count++;
						sino=""+count;

						cell2.setPhrase(new Phrase(sino,font1 ));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell2);



						cell2.setPhrase(new Phrase(orderdata.get("stock_item_name").getAsString(),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell2);
					




					

					cell2.setPhrase(new Phrase(getNumberWithDecimal(orderdata.get("balanceqty").getAsDouble()),font1));	
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

		
	}
	
}
