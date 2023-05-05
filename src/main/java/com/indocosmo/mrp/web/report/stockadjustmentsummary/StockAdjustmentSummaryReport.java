package com.indocosmo.mrp.web.report.stockadjustmentsummary;

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
import com.indocosmo.mrp.web.report.stockadjustmentsummary.model.StockAdjustmentReport;
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

public class StockAdjustmentSummaryReport extends AbstractITextPdfView{

	String dateFormat;	
	Integer decimalPlace;
	
	@Override
	public Document newDocument() {
		return new Document(PageSize.A4,36, 36, 120, 80);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);

		JsonArray stockAdjustmentArray = (JsonArray) model.get("stockAdjustmentData");
		StockAdjustmentReport stockAdjustmentReport = (StockAdjustmentReport) model.get("stockAdjustmentReport");
		Report  report = (Report) model.get("reportName");
		dateFormat = report.getDateFormat();
		decimalPlace = report.getDecimalPlace();

		Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
		Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

		if (stockAdjustmentArray.size() != 0) {
			
			HashMap< String, List<Object> > StockadjustmentDataMap = new HashMap<String, List<Object>>();
			for (int i = 0; i < stockAdjustmentArray.size(); i++) 
			{
				JsonObject adjustData = (JsonObject) stockAdjustmentArray.get(i);
					if (StockadjustmentDataMap.containsKey(adjustData.get("department_name").getAsString())){

						List<Object> jsonList= StockadjustmentDataMap.get(adjustData.get("department_name").getAsString());
						jsonList.add(adjustData);
						StockadjustmentDataMap.put(adjustData.get("department_name").getAsString(), jsonList);

					}
					else{

						List<Object> jsonList=new  ArrayList<Object>();
						jsonList.add(adjustData);
						StockadjustmentDataMap.put(adjustData.get("department_name").getAsString(), jsonList);

					}
			}
			
			String subHeadString = "BETWEEN " + getDateWithSystemFormat(stockAdjustmentReport.getStartdate()) + " AND " 
						+ getDateWithSystemFormat(stockAdjustmentReport.getEnddate()) + "";
			
			PdfPTable headerTable = new PdfPTable(1);
			headerTable.setWidthPercentage(100.0f);
			headerTable.setSpacingBefore(2);
			headerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			
			PdfPCell headerCell = new PdfPCell();
			headerCell.setBorder(Rectangle.NO_BORDER);
			headerCell.setPhrase(new Phrase(subHeadString, headerFont));

			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			headerTable.addCell(headerCell);
			document.add(headerTable);
			
			Set set = StockadjustmentDataMap.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) 
			{
				Map.Entry entry = (Map.Entry) iterator.next();
				String departMentName = (String) entry.getKey();

				PdfPTable mainTable = new PdfPTable(5);
				mainTable.setWidths(new float[] {.5f, 1.5f,.8f, .8f, .6f});

				mainTable.setWidthPercentage(100.0f);

				PdfPCell tableDataCell = new PdfPCell();
				tableDataCell.setBackgroundColor(new BaseColor(255, 255, 255));
				tableDataCell.setPadding(5);
				mainTable.getDefaultCell().setPaddingBottom(10.0f);
				mainTable.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
				mainTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				mainTable.getDefaultCell().setBorderWidthBottom(2.0f);

				PdfPCell cellspan = new PdfPCell();
				cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan.setPadding(6);
				cellspan.setColspan(5);

				cellspan.setPhrase(new Phrase(departMentName,tableHeaderFont));
				cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
				mainTable.addCell(cellspan);

				tableDataCell.setPhrase(new Phrase("DATE" ,tableHeaderFont));	
				tableDataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				mainTable.addCell(tableDataCell);

				tableDataCell.setPhrase(new Phrase("ITEM NAME" ,tableHeaderFont));	
				tableDataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				mainTable.addCell(tableDataCell);


				tableDataCell.setPhrase(new Phrase("SYSTEM STOCK" ,tableHeaderFont));	
				tableDataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				mainTable.addCell(tableDataCell);

				tableDataCell.setPhrase(new Phrase("ACTUAL STOCK" ,tableHeaderFont));	
				tableDataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				mainTable.addCell(tableDataCell);

				tableDataCell.setPhrase(new Phrase("VARIANCE" ,tableHeaderFont));	
				tableDataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				mainTable.addCell(tableDataCell);

				JsonObject json = (JsonObject) stockAdjustmentArray.get(0);
				String stock_adjustment_date = json.get("adjust_date").getAsString();
				int dateRepeat = 0;
				List<Object> jsonList = (List<Object>) entry.getValue();
				for (int i = 0; i < jsonList.size(); i++) {

					JsonObject stockAdjustData = (JsonObject) jsonList.get(i);
					
					/* remove repeating adjust date */
					if(!stock_adjustment_date.equals(stockAdjustData.get("adjust_date").getAsString())){
						dateRepeat = 0;
						stock_adjustment_date = stockAdjustData.get("adjust_date").getAsString();
					}
					
					if(stock_adjustment_date.equals(stockAdjustData.get("adjust_date").getAsString()) && dateRepeat == 1){
						tableDataCell.setPhrase(new Phrase("",normalFont));	
						tableDataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
						mainTable.addCell(tableDataCell);
					}
					else{
						if(stock_adjustment_date.equals(stockAdjustData.get("adjust_date").getAsString()) && dateRepeat == 0){
							tableDataCell.setPhrase(new Phrase(getDateWithSystemFormat(stockAdjustData.get("adjust_date").getAsString()),normalFont));	
							tableDataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
							mainTable.addCell(tableDataCell);
							dateRepeat = 1;
						}
					}

					tableDataCell.setPhrase(new Phrase(stockAdjustData.get("stock_item_name").getAsString(),normalFont));	
					tableDataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					mainTable.addCell(tableDataCell);

					tableDataCell.setPhrase(new Phrase(getNumberWithDecimal(stockAdjustData.get("system_qty").getAsDouble()),normalFont));	
					tableDataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					mainTable.addCell(tableDataCell);

					tableDataCell.setPhrase(new Phrase(getRateWithDecimal(stockAdjustData.get("actual_qty").getAsDouble()),normalFont));	
					tableDataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					mainTable.addCell(tableDataCell);

					tableDataCell.setPhrase(new Phrase(getRateWithDecimal(stockAdjustData.get("diff_qty").getAsDouble()),normalFont));	
					tableDataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					mainTable.addCell(tableDataCell);
				}
				document.add(mainTable);
			}
		}else{
			PdfPTable emptyTable = new PdfPTable(1);
			emptyTable.setWidthPercentage(100.0f);
			emptyTable.setSpacingBefore(10);

			PdfPCell messageCell = new PdfPCell();

			messageCell.setBackgroundColor(new BaseColor(255, 255, 255));
			messageCell.setPadding(5);			
			emptyTable.getDefaultCell().setPaddingBottom(10.0f);
			emptyTable.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			emptyTable.getDefaultCell().setBorder(Rectangle.BOTTOM);
			emptyTable.getDefaultCell().setBorderWidthBottom(2.0f);
			messageCell.setPhrase(new Phrase("No Items Available ", normalFont));
			messageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			emptyTable.addCell(messageCell);
			document.add(emptyTable);
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

	public String getRateWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}

}
