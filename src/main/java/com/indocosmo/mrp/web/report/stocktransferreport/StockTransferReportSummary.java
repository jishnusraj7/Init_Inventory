package com.indocosmo.mrp.web.report.stocktransferreport;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stocktransferreport.model.StockTransferReportModel;
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


public class StockTransferReportSummary extends AbstractITextPdfView {

	
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
	
	@Override
	public Document newDocument() {
			return new Document(PageSize.A4,36, 36, 120, 80);
	}

	
	@Override
	protected void buildPdfDocument(Map<String, Object> model , Document document , PdfWriter writer ,
			HttpServletRequest request , HttpServletResponse response) throws Exception 
	{
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA,9);
		Font font=FontFactory.getFont(FontFactory.HELVETICA,11,Font.BOLD);
		Font font2=FontFactory.getFont(FontFactory.HELVETICA,9,Font.BOLD);

		JsonArray itemData=(JsonArray)model.get("stocktransferSummaryView");

		StockTransferReportModel stocktransfer =(StockTransferReportModel)model.get("stocktransfer");
		Report  report =   (Report) model.get("reportName");

		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		boolean is_ExternalTransfer=false;

		if(itemData.size()!=0)
		{
			HashMap< String, List<Object> > itemDataMap = new HashMap<String, List<Object>>();
			for (int i = 0; i < itemData.size(); i++) 
			{
				JsonObject orderdata = (JsonObject) itemData.get(i);
				if(orderdata.get("transfer_type").getAsInt()==0){
					is_ExternalTransfer=true;
					if (itemDataMap.containsKey(orderdata.get("dest_company_name").getAsString())){

						List<Object> jsonList= itemDataMap.get(orderdata.get("dest_company_name").getAsString());
						jsonList.add(orderdata);
						itemDataMap.put(orderdata.get("dest_company_name").getAsString(), jsonList);

					}
					else{

						List<Object> jsonList=new  ArrayList<Object>();
						jsonList.add(orderdata);
						itemDataMap.put(orderdata.get("dest_company_name").getAsString(), jsonList);

					}
				}else
				{
					if (itemDataMap.containsKey(orderdata.get("dest_department_name").getAsString())){

						List<Object> jsonList= itemDataMap.get(orderdata.get("dest_department_name").getAsString());
						jsonList.add(orderdata);
						itemDataMap.put(orderdata.get("dest_department_name").getAsString(), jsonList);
					}
					else{
						List<Object> jsonList=new  ArrayList<Object>();
						jsonList.add(orderdata);
						itemDataMap.put(orderdata.get("dest_department_name").getAsString(), jsonList);
					}
				}
			}
			if(!is_ExternalTransfer){
				String proddate="Internal Stock Transfer Between "+getDateWithSystemFormat(stocktransfer.getStartdate())+" AND "+getDateWithSystemFormat(stocktransfer.getEnddate())+"";

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

				Set set = itemDataMap.entrySet();
				Iterator iterator = set.iterator();
				while (iterator.hasNext()) 
				{
					Map.Entry entry = (Map.Entry) iterator.next();
					String departMentName = (String) entry.getKey();

					PdfPTable table2 = new PdfPTable(5);
					table2.setWidths(new float[] {.7f, 1.5f,.7f, .6f, .6f});

					table2.setWidthPercentage(100.0f);

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
					cellspan.setColspan(5);

					cellspan.setPhrase(new Phrase(departMentName,font));
					cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cellspan);

					cell2.setPhrase(new Phrase("DATE" ,font2));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);

					cell2.setPhrase(new Phrase("ITEM NAME" ,font2));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);


					cell2.setPhrase(new Phrase("QTY" ,font2));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);

					cell2.setPhrase(new Phrase("UNIT PRICE" ,font2));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);

					cell2.setPhrase(new Phrase("AMOUNT" ,font2));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);

					@SuppressWarnings("unchecked")
					List<Object> jsonList =(List<Object>) entry.getValue();
					for (int i = 0; i < jsonList.size(); i++) {

						JsonObject orderdata = (JsonObject) itemData.get(i);
						//String stock_transfer_date=orderdata.get("stock_transfer_date").getAsString();
						cell2.setPhrase(new Phrase(getDateWithSystemFormat(orderdata.get("stock_transfer_date").getAsString()),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase(orderdata.get("stock_item_name").getAsString(),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase(getNumberWithDecimal(orderdata.get("issued_qty").getAsDouble()),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase(getRateWithDecimal(orderdata.get("cost_price").getAsDouble()),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase(getRateWithDecimal(orderdata.get("amount").getAsDouble()),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell2);



					}
					document.add(table2);

				}
			}
			else{

				String proddate="External Stock Transfer Between "+getDateWithSystemFormat(stocktransfer.getStartdate())
						+" AND "+getDateWithSystemFormat(stocktransfer.getEnddate())+"";

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

				Set set = itemDataMap.entrySet();
				Iterator iterator = set.iterator();
				while (iterator.hasNext()) {

					Map.Entry entry = (Map.Entry) iterator.next();
					String destCompanyName = (String) entry.getKey();

					PdfPTable table2 = new PdfPTable(5);
					table2.setWidths(new float[] {.7f, 1.5f,.7f, .6f, .6f});

					table2.setWidthPercentage(100.0f);

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
					cellspan.setColspan(5);

					cellspan.setPhrase(new Phrase(destCompanyName,font));
					cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cellspan);

					cell2.setPhrase(new Phrase("DATE" ,font2));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);

					cell2.setPhrase(new Phrase("ITEM NAME" ,font2));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);

					cell2.setPhrase(new Phrase("QTY" ,font2));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);

					cell2.setPhrase(new Phrase("UNIT PRICE" ,font2));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);

					cell2.setPhrase(new Phrase("AMOUNT" ,font2));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell2);

					@SuppressWarnings("unchecked")
					List<Object> jsonList =(List<Object>) entry.getValue();
					for (int i = 0; i < jsonList.size(); i++) {

						JsonObject orderdata = (JsonObject) itemData.get(i);
						//String stock_transfer_date=orderdata.get("stock_transfer_date").getAsString();
						cell2.setPhrase(new Phrase(getDateWithSystemFormat(orderdata.get("stock_transfer_date").getAsString()),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase(orderdata.get("stock_item_name").getAsString(),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase(getNumberWithDecimal(orderdata.get("issued_qty").getAsDouble()),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase(getRateWithDecimal(orderdata.get("cost_price").getAsDouble()),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell2);

						cell2.setPhrase(new Phrase(getRateWithDecimal(orderdata.get("amount").getAsDouble()),font1));	
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell2);
					}
					document.add(table2);

				}
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
			document.add(table89);
		}
	}
}
