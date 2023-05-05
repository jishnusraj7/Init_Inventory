package com.indocosmo.mrp.web.report.profitsummary;

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
	public String getRateWithDecimal(Double value) {
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		return bd.toString();
	}
	
	public String getDateWithSystemFormat(String date) throws Exception {
    	
		  Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);  
  	final String systemDateFormat =dateFormat;
  	DateFormat formatter = new SimpleDateFormat(systemDateFormat);
  	final String dateWithSystemFormat = formatter.format(date1);
  	
			
		return dateWithSystemFormat;
	}

	public Document newDocument(Map<String, Object> model) {
		
		Report  report =   (Report) model.get("reportName");
		
		if (report.getDocType()==1) {
			return new Document(PageSize.A4, 36, 36, 120, 30);
		}
		
		else {
			return new Document(PageSize.A4, 36, 36, 120, 30);
			
		}
	}

	
	@Override
	protected void buildPdfDocument(Map<String, Object> model , Document doc , PdfWriter writer ,
			HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA,9);
		
		Font font3= FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);

		
		
		
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
			
		if(report.getDocType() ==1)	
		{
			PdfPTable table2 = new PdfPTable(8);
			table2.setWidths(new float[] { .8f, 3f, 1.5f, 1.5f, 1.5f,1.5f,1.5f,1.5f });
			table2.setWidthPercentage(100.0f);
			table2.setSpacingBefore(5);
			
			PdfPCell cell2 = new PdfPCell();
			cell2.setBackgroundColor(new BaseColor(255, 255, 255));
			cell2.setPadding(5);
			table2.getDefaultCell().setPaddingBottom(10.0f);
			table2.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			table2.getDefaultCell().setBorder(Rectangle.BOTTOM);
			table2.getDefaultCell().setBorderWidthBottom(2.0f);
			cell2.setPhrase(new Phrase("SI#", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("Shop", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("Material", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("Labour", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("Other", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("Total Cost", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("Sales", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			cell2.setPhrase(new Phrase("Profit", font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cell2);
			
			Double mateTotal = 0.0;
			Double labTotal=0.0;
			Double othTotal=0.0;
			Double totcostTotal=0.0;
			Double salesTotal=0.0;
			Double profitTotal=0.0;
			int count = 0;
			for (int i = 0; i < orderhdrDateData.size(); i++) {
				count++;
				String sino = "" + count;
				
				JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);
				cell2.setPhrase(new Phrase(sino,font1));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell2);
				
				String shop_name1=orderdata.get("shop_name").getAsString().toLowerCase();
				if(shop_name1.length() !=0)
				{
				shop_name1=shop_name1.substring(0,1).toUpperCase()+shop_name1.substring(1);
				}
				cell2.setPhrase(new Phrase(shop_name1,font1));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell2);
				
				cell2.setPhrase(new Phrase((orderdata.get("material_cost") != null && orderdata.get("material_cost").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("material_cost").getAsDouble()) : "",font1));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				
				mateTotal+=orderdata.get("material_cost").getAsDouble();
				
				
				cell2.setPhrase(new Phrase((orderdata.get("labour_cost") != null && orderdata.get("labour_cost").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("labour_cost").getAsDouble()): "",font1));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				labTotal+=orderdata.get("labour_cost").getAsDouble();
				
				cell2.setPhrase(new Phrase((orderdata.get("other_cost") != null && orderdata.get("other_cost").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("other_cost").getAsDouble()) : "",font1));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				othTotal+=orderdata.get("other_cost").getAsDouble();
				
				cell2.setPhrase(new Phrase((orderdata.get("total_cost") != null && orderdata.get("total_cost").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("total_cost").getAsDouble()) : "",font1));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				totcostTotal+=orderdata.get("total_cost").getAsDouble();
				
				cell2.setPhrase(new Phrase((orderdata.get("total_sales_amount") != null && orderdata.get("total_sales_amount").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("total_sales_amount").getAsDouble()) : "",font1));
				
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				salesTotal+=orderdata.get("total_sales_amount").getAsDouble();
				
				cell2.setPhrase(new Phrase((orderdata.get("profit") != null && orderdata.get("profit").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("profit").getAsDouble()) : "",font1));
				cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell2);
				
				profitTotal+=orderdata.get("profit").getAsDouble();
			
				
			}
			
						
			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(5);
			cellspan.setColspan(2);
			cellspan.setPhrase(new Phrase("Total:", font3));
			cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			table2.addCell(cellspan);
			
			cell2.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(mateTotal.toString())), font2));
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell2);
			
			cell2.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(labTotal.toString())), font2));
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell2);
			
			cell2.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(othTotal.toString())), font2));
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell2);
			
			cell2.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(totcostTotal.toString())), font2));
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell2);

			cell2.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(salesTotal.toString())), font2));
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell2);

			cell2.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(profitTotal.toString())), font2));
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell2);
			doc.add(table2);
		}
		else if(report.getDocType() == 2)
		{
			String proddate;
			String stkName;
			PdfPTable table21 = new PdfPTable(9);
			table21.setWidths(new float[] {.4f, 1.5f, 1f, 1f, .7f, .6f, .6f, .6f, .6f});
			
			table21.setWidthPercentage(100.0f);
			table21.setSpacingBefore(5);
			PdfPCell cell21 = new PdfPCell();
			cell21.setBackgroundColor(new BaseColor(255, 255, 255));
			cell21.setPadding(5);
			table21.getDefaultCell().setPaddingBottom(10.0f);
			table21.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
			table21.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table21.getDefaultCell().setBorderWidthBottom(2.0f);
			/*if(!(report.getIs_customer().equals("3"))){*/
			JsonObject orderdata5 = (JsonObject) orderhdrDateData.get(0);
		 proddate=orderdata5.get("prod_date").getAsString();
		 stkName=orderdata5.get("stock_item_name").getAsString();
				 
				 
			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(6);
			cellspan.setColspan(9);
			
			cellspan.setPhrase(new Phrase(getDateWithSystemFormat(orderdata5.get("prod_date").getAsString()),font));
			cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
		table21.addCell(cellspan);
			
			
			cell21.setBackgroundColor(new BaseColor(255, 255, 255));
			cell21.setPadding(6);
			table21.getDefaultCell().setPaddingBottom(10.0f);
			table21.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			table21.getDefaultCell().setBorder(Rectangle.BOTTOM);
			table21.getDefaultCell().setBorderWidthBottom(2.0f);
			cell21.setPhrase(new Phrase("SI#",font ));	
			cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			table21.addCell(cell21);
			cell21.setPhrase(new Phrase("ITEM", font));
			cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			table21.addCell(cell21);
			cell21.setPhrase(new Phrase("Material", font));
			cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			table21.addCell(cell21);
			cell21.setPhrase(new Phrase("Labour", font));
			cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			table21.addCell(cell21);
			cell21.setPhrase(new Phrase("Other", font));
			cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			table21.addCell(cell21);
			cell21.setPhrase(new Phrase("Total Cost", font));
			cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			table21.addCell(cell21);
			cell21.setPhrase(new Phrase("Sales", font));
			cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			table21.addCell(cell21);
			cell21.setPhrase(new Phrase("Damage", font));
			cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			table21.addCell(cell21);
			cell21.setPhrase(new Phrase("Profit", font));
			cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			table21.addCell(cell21);
			
			
			int count = 0;
			int frt=0;
			
			 String sino="";
			 
			 
			    Double mateTotal = 0.0;
				Double labTotal=0.0;
				Double othTotal=0.0;
				Double totcostTotal=0.0;
				Double salesTotal=0.0;
				Double damageTotal=0.0;
				Double profitTotal=0.0;

		
			for (int i = 0; i < orderhdrDateData.size(); i++) {
	
				int f=0;
				frt=0;
				JsonObject orderdata = (JsonObject) orderhdrDateData.get(i);
				if(i==0)
				{
					frt=1;
				}
				count++;
				sino=""+count;
				/*if(!(report.getIs_customer().equals("3"))){*/
					if(!((orderdata.get("prod_date").getAsString()).equals(proddate))){
						
						
						
						PdfPCell cellspan2 = new PdfPCell();
						cellspan2.setBackgroundColor(new BaseColor(255, 255, 255));
						cellspan2.setPadding(5);
						cellspan2.setColspan(2);
						cellspan2.setPhrase(new Phrase("Total:", font3));
						cellspan2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						
						table21.addCell(cellspan2);
						
						cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(mateTotal.toString())), font2));
						cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table21.addCell(cell21);
						
						cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(labTotal.toString())), font2));
						cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table21.addCell(cell21);
						
						cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(othTotal.toString())), font2));
						cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table21.addCell(cell21);
						
						cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(totcostTotal.toString())), font2));
						cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table21.addCell(cell21);

						cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(salesTotal.toString())), font2));
						cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table21.addCell(cell21);
						
						
						cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(damageTotal.toString())), font2));
						cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table21.addCell(cell21);


						cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(profitTotal.toString())), font2));
						cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table21.addCell(cell21);


						
						
						
						
						
						
						
						f=1;
						
						PdfPCell cellspan1 = new PdfPCell();
						cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
						cellspan1.setPadding(6);
						cellspan1.setColspan(9);
						
						cellspan1.setPhrase(new Phrase(getDateWithSystemFormat(orderdata.get("prod_date").getAsString()),font));
						cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
					table21.addCell(cellspan1);
					proddate=orderdata.get("prod_date").getAsString();
					 
					
						
						cell21.setPhrase(new Phrase("SI#",font ));	
						cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
						table21.addCell(cell21);
						
						cell21.setPhrase(new Phrase("ITEM" ,font));	
						cell21.setHorizontalAlignment(Element.ALIGN_LEFT);
						table21.addCell(cell21);
						
						cell21.setPhrase(new Phrase("Material" ,font));	
						cell21.setHorizontalAlignment(Element.ALIGN_LEFT);
						table21.addCell(cell21);
					
						cell21.setPhrase(new Phrase("Labour" ,font));	
						cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
						table21.addCell(cell21);
						
						cell21.setPhrase(new Phrase("Other" ,font));	
						cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
						table21.addCell(cell21);
						
						
						
						cell21.setPhrase(new Phrase("Total Cost" ,font));	
						cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
						table21.addCell(cell21);
						
						cell21.setPhrase(new Phrase("Sales" ,font));	
						cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
						table21.addCell(cell21);
						
						cell21.setPhrase(new Phrase("Damage", font));
						cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
						table21.addCell(cell21);
						
						cell21.setPhrase(new Phrase("Profit" ,font));	
						cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
						table21.addCell(cell21);
						
						
						   mateTotal = 0.0;
						   labTotal=0.0;
						   othTotal=0.0;
						   totcostTotal=0.0;
						   salesTotal=0.0;
						   damageTotal=0.0;
						   profitTotal=0.0;

						
						
						
						
					//}
					} 
					if(f==1)
					{
						count=0;
						sino="";
						count++;
						sino=""+count;
					}
					cell21.setPhrase(new Phrase(sino,font1 ));
					cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
					table21.addCell(cell21);
					
						
						cell21.setPhrase(new Phrase(orderdata.get("stock_item_name").getAsString(),font1));	
						cell21.setHorizontalAlignment(Element.ALIGN_LEFT);
						table21.addCell(cell21);
					
				cell21.setPhrase(new Phrase((orderdata.get("material_cost") != null && orderdata.get("material_cost").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("material_cost").getAsDouble()) : "",font1));	
				cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table21.addCell(cell21);
				
				
				 mateTotal+=orderdata.get("material_cost").getAsDouble();
				
				cell21.setPhrase(new Phrase((orderdata.get("labour_cost") != null && orderdata.get("labour_cost").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("labour_cost").getAsDouble()) : "",font1));	
				cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table21.addCell(cell21);
				
				
				labTotal+=orderdata.get("labour_cost").getAsDouble();
				  
				
				cell21.setPhrase(new Phrase((orderdata.get("other_cost") != null && orderdata.get("other_cost").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("other_cost").getAsDouble()) : "",font1));	
				cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table21.addCell(cell21);
				
				    othTotal+=orderdata.get("other_cost").getAsDouble();
				   
				
				cell21.setPhrase(new Phrase((orderdata.get("total_cost") != null && orderdata.get("total_cost").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("total_cost").getAsDouble()) : "",font1));	
				cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table21.addCell(cell21);
				
				   totcostTotal+=orderdata.get("total_cost").getAsDouble();
				  
				
				cell21.setPhrase(new Phrase((orderdata.get("total_sales_amount") != null && orderdata.get("total_sales_amount").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("total_sales_amount").getAsDouble()) : "",font1));	
				cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table21.addCell(cell21);
				
				   salesTotal+=orderdata.get("total_sales_amount").getAsDouble();
				   
				
				cell21.setPhrase(new Phrase((orderdata.get("damage_cost") != null && orderdata.get("damage_cost").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("damage_cost").getAsDouble()) : "",font1));	
				cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table21.addCell(cell21);
				
				
				   damageTotal+=orderdata.get("damage_cost").getAsDouble();

				
				cell21.setPhrase(new Phrase((orderdata.get("profit") != null && orderdata.get("profit").getAsString()
						.length() != 0) ? getRateWithDecimal(orderdata.get("profit").getAsDouble()) : "",font1));	
				cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table21.addCell(cell21);
				
				   profitTotal+=orderdata.get("profit").getAsDouble();

				
			
				
			
				
			
				
			
				
				
				
				
			
			}
			
			
			PdfPCell cellspan2 = new PdfPCell();
			cellspan2.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan2.setPadding(5);
			cellspan2.setColspan(2);
			cellspan2.setPhrase(new Phrase("Total:", font3));
			cellspan2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			table21.addCell(cellspan2);
			
			cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(mateTotal.toString())), font2));
			cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table21.addCell(cell21);
			
			cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(labTotal.toString())), font2));
			cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table21.addCell(cell21);
			
			cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(othTotal.toString())), font2));
			cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table21.addCell(cell21);
			
			cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(totcostTotal.toString())), font2));
			cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table21.addCell(cell21);

			cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(salesTotal.toString())), font2));
			cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table21.addCell(cell21);
			
			
			cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(damageTotal.toString())), font2));
			cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table21.addCell(cell21);


			cell21.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(profitTotal.toString())), font2));
			cell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table21.addCell(cell21);

			
			
			
			doc.add(table21);
			
		}
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
	
	/**
	 * @param value
	 * @param places
	 * @return
	 */
	public double round(double value , int places) {
	
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}
