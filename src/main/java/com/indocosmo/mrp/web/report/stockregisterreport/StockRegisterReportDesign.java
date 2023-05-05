package com.indocosmo.mrp.web.report.stockregisterreport;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockregisterreport.model.StockRegisterReport;
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

public class StockRegisterReportDesign extends AbstractITextPdfView {
	
	String companyName;
	
	String dateFormat;
	
	Integer decimalPlace;
	
	String currency;
	
	Integer docType;
	
	/**
	 * @param value
	 * @return
	 */
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
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.common.AbstractITextPdfView#newDocument(java.util.Map)
	 */
	public Document newDocument(Map<String, Object> model) {
	
		StockRegisterReport stock = (StockRegisterReport) model.get("listInvoice");
		
		if (stock.getOption() == 1) {
			return new Document(PageSize.A4, 36, 36, 120, 30);
		}
		
		else {
			return new Document(PageSize.A4.rotate(), 36, 36, 120, 30);
			
		}
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.common.AbstractITextPdfView#buildPdfDocument(java.util.Map, com.itextpdf.text.Document, com.itextpdf.text.pdf.PdfWriter, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void buildPdfDocument(Map<String, Object> model , Document doc , PdfWriter writer ,
			HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		Report report = (Report) model.get("reportName");
		companyName = report.getCompanyName();
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		currency = report.getCurrency();
		
		StockRegisterReport stock = (StockRegisterReport) model.get("listInvoice");
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 9);
		final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 11, Font.BOLD);
		int count = 0;		
		String dateFrom = "";
		String dateTo = "";
		/*
		 * int year = Calendar.getInstance().get(Calendar.YEAR); */
		
	if(stock.getStockRegister().isEmpty())
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
		cell89.setPhrase(new Phrase("No Items Available ", SUBFONT));
		cell89.setHorizontalAlignment(Element.ALIGN_CENTER);
		table89.addCell(cell89);
		doc.add(table89);
	}
		
	 if (stock.getOption() == 0) {
			SimpleDateFormat monthName = new SimpleDateFormat("MM");
			Date date1 = monthName.parse(stock.getStartdate());
			Date date2 = monthName.parse(stock.getEnddate());
			SimpleDateFormat month = new SimpleDateFormat("MMMM");
			
			dateFrom = month.format(date1);
			dateTo = month.format(date2);
		}
		
		DateFormat dateFormat1 = new SimpleDateFormat(dateFormat);
		String Header1 = "";
		
		if (stock.getOption() == 1) {
			
			Header1 = "BETWEEN  " + stock.getStartdate() + "  AND  " + stock.getEnddate();
		}
		else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date yrrdate = new Date();
			yrrdate = df.parse(stock.getStartdate());
			
			LocalDate yrdate = yrrdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int year = yrdate.getYear();
			
			Header1 = dateTo.toUpperCase() + " " + year;
			
		}
		
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
		
		PdfPTable table25 = new PdfPTable(1);
		table25.setWidthPercentage(100.0f);
		table25.setSpacingBefore(2);
		table25.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		
		PdfPCell cell25 = new PdfPCell();
		cell25.setBorder(Rectangle.NO_BORDER);
		cell25.setPhrase(new Phrase(Header1, SUBFONT));
		
		cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		table25.addCell(cell25);
		if(!stock.getStockRegister().isEmpty())
		doc.add(table25);
		
		PdfPTable table23 = new PdfPTable(2);
		
		table23.setWidthPercentage(100.0f);
		
		PdfPCell cell23 = new PdfPCell();
		cell23.setBackgroundColor(new BaseColor(255, 255, 255));
		cell23.setPadding(5);
		table23.getDefaultCell().setPaddingBottom(10.0f);
		table23.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
		table23.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table23.getDefaultCell().setBorderWidthBottom(2.0f);
		
		cell23.setPhrase(new Phrase(stock.getDepartment_name(), font1));
		cell23.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell23.setBorderColorBottom(new BaseColor(255, 255, 255));
		cell23.setBorder(Rectangle.NO_BORDER);
		table23.addCell(cell23);
		
		cell23.setPhrase(new Phrase(stock.getItem_category_name(), font1));
		cell23.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell23.setBorderColorBottom(new BaseColor(255, 255, 255));
		cell23.setBorder(Rectangle.NO_BORDER);
		table23.addCell(cell23);
		if(!stock.getStockRegister().isEmpty())
		doc.add(table23);
		
		PdfPTable table;
		if (stock.getOption() == 1) {
			table = new PdfPTable(8);
			table.setWidths(new float[] { .4f, 1.5f, .6f, .7f, .7f, .6f, .6f, .6f });
			
		}
		else {
			table = new PdfPTable(11);
			table.setWidths(new float[] { .4f, 2f, .5f, .8f, .8f, .8f, .8f, .8f, .8f, .9f, .8f });
			
		}
		table.setWidthPercentage(100.0f);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(new BaseColor(255, 255, 255));
		cell.setPadding(5);
		table.setSpacingBefore(5);
		
		table.getDefaultCell().setPaddingBottom(10.0f);
		table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
		table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table.getDefaultCell().setBorderWidthBottom(2.0f);
		
		cell.setPhrase(new Phrase("SI#", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("ITEM", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("Unit", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		if (stock.getOption() == 1) {
			
			cell.setPhrase(new Phrase("Date", font1));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		cell.setPhrase(new Phrase("Opening", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		if (stock.getOption() != 1) {
			cell.setPhrase(new Phrase("Value", font1));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		cell.setPhrase(new Phrase("Stock In", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		if (stock.getOption() != 1) {
			cell.setPhrase(new Phrase("Value", font1));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		
		cell.setPhrase(new Phrase("Stock Out", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		if (stock.getOption() != 1) {
			cell.setPhrase(new Phrase("Value", font1));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		
		cell.setPhrase(new Phrase("Balance", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		if (stock.getOption() != 1) {
			cell.setPhrase(new Phrase("Value", font1));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		
		table.setHeaderRows(1);
		
		String stockitemname = "0";
		
		int f = 0;
		int firstRowOpening = 0;
		int firstRow = 0;
		if (!stock.getStockRegister().isEmpty()) {
			stockitemname = stock.getStockRegister().get(0).getStock_item_id();
		}
		
		String transtype = "";
		Double balance = 0.0;
		Double total = 0.0;
		Double openingValue = 0.0;
		Double inqtyValue = 0.0;
		Double outqtyValue = 0.0;
		Double balanceValue = 0.0;
		Double stockintotal = 0.0;
		Double stockouttotal = 0.0;
		Double totalstockinItem = 0.0;
		Double totalstockoutItem = 0.0;
		Double curropen = 0.0;
		for (StockRegisterReport stkreg : stock.getStockRegister()) {
			
			if (stock.getOption() == 1) {
				
				if (!(stkreg.getStock_item_id().equals(stockitemname)))
				
				{
					f = 0;
					firstRowOpening = 0;
					stockitemname = stkreg.getStock_item_id();
					
				}
				
				if ((stkreg.getStock_item_id().equals(stockitemname)) && f == 1) {
					
					cell.setPhrase(new Phrase(" ", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell.setPhrase(new Phrase(" ", font));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell.setPhrase(new Phrase(" ", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
				else {
					if ((stkreg.getStock_item_id().equals(stockitemname)) && f == 0)
					
					{
						if (firstRow == 0) {
							firstRow = 1;
						}
						else if (firstRow == 1) {
							
							PdfPCell cellspan = new PdfPCell();
							cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
							cellspan.setPadding(5);
							cellspan.setColspan(4);
							cellspan.setPhrase(new Phrase("Total:", font1));
							cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
							
							table.addCell(cellspan);
							cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(curropen.toString())),
									font2));
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							table.addCell(cell);
							cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(totalstockinItem
									.toString())), font2));
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							table.addCell(cell);
							
							cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(totalstockoutItem
									.toString())), font2));
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							table.addCell(cell);
							
							cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(balance.toString())),
									font2));
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							table.addCell(cell);
							
							curropen = 0.0;
							totalstockoutItem = 0.0;
							totalstockinItem = 0.0;
						}
						
						count++;
						String sino = "" + count;
						cell.setPhrase(new Phrase(sino, font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						f = 1;
						cell.setPhrase(new Phrase(stkreg.getStockitemName(), font));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell.setPhrase(new Phrase(stkreg.getUomcode(), font));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
					}
				}
				
			}
			else {
				
				count++;
				String sino = "" + count;
				cell.setPhrase(new Phrase(sino, font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				f = 1;
				cell.setPhrase(new Phrase(stkreg.getStockitemName(), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell.setPhrase(new Phrase(stkreg.getUomcode(), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			
			
			String open = stkreg.getOpening();
			String inqty = stkreg.getInQty();
			String outqty = stkreg.getOutQty();
			if (open == null || open == "") {
				open = "0.0";
			}
			if (inqty == null || inqty == "") {
				inqty = "0.0";
			}
			if (outqty == null || outqty == "") {
				outqty = "0.0";
			}
			
			if (stock.getOption() == 1) {
				
				cell.setPhrase(new Phrase(stkreg.getTxnDate(), font));
				table.addCell(cell);
				
				if (stkreg.getStock_item_id().equals(stockitemname) && firstRowOpening == 1) {
					
					cell.setPhrase(new Phrase(" ", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
				else if (stkreg.getStock_item_id().equals(stockitemname) && firstRowOpening == 0)
				
				{
					firstRowOpening = 1;
					curropen = Double.parseDouble(open.toString());
					
					cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(open.toString())), font));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
					
				}
			}
			else {
				cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(open.toString())), font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
			}
			
			stockintotal += Double.parseDouble(inqty);
			stockouttotal += Double.parseDouble(outqty);
			balance = (Double.parseDouble(open) + Double.parseDouble(inqty)) - (Double.parseDouble(outqty));
			total = total + balance;
			
			
			
			if (stock.getOption() != 1) {
				cell.setPhrase(new Phrase(getRateWithDecimal(
						Double.parseDouble(open) * Double.parseDouble(stkreg.getCost_price())).toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
			}
			openingValue = openingValue + Double.parseDouble(open) * Double.parseDouble(stkreg.getCost_price());
			totalstockinItem += Double.parseDouble(inqty.toString());
			totalstockoutItem += Double.parseDouble(outqty.toString());
			cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(inqty.toString())), font));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			if (stock.getOption() != 1) {
				cell.setPhrase(new Phrase(getRateWithDecimal(
						Double.parseDouble(inqty) * Double.parseDouble(stkreg.getCost_price())).toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
			}
			
			inqtyValue = inqtyValue + Double.parseDouble(inqty) * Double.parseDouble(stkreg.getCost_price());
			cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(outqty.toString())), font));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			outqtyValue = outqtyValue + Double.parseDouble(outqty) * Double.parseDouble(stkreg.getCost_price());
			
			if (stock.getOption() != 1) {
				cell.setPhrase(new Phrase(getRateWithDecimal(
						Double.parseDouble(outqty) * Double.parseDouble(stkreg.getCost_price())).toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
			}
			balanceValue = balanceValue + Double.parseDouble(balance.toString())
					* Double.parseDouble(stkreg.getCost_price());
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(balance.toString())), font));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			if (stock.getOption() != 1) {
				cell.setPhrase(new Phrase(
						getRateWithDecimal(
								Double.parseDouble(balance.toString()) * Double.parseDouble(stkreg.getCost_price()))
								.toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
			}
			
		}
		
		
		if (stock.getOption() == 1) {
			
		}
		else {
			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(5);
			cellspan.setColspan(3);
			cellspan.setPhrase(new Phrase("Total:", font1));
			cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellspan);
		}
		
		
		
		if (stock.getOption() != 1) {
			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(5);
			cellspan.setColspan(2);
			
			cellspan.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(openingValue.toString())), font2));
			cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellspan);
			
			cellspan.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(inqtyValue.toString())), font2));
			cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellspan);
			
			cellspan.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(outqtyValue.toString())), font2));
			cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellspan);
			
			cellspan.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(balanceValue.toString())), font2));
			cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cellspan);
			
			
		}
		
		if (stock.getOption() == 1) {
			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(5);
			cellspan.setColspan(4);
			cellspan.setPhrase(new Phrase("Total:", font1));
			cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			table.addCell(cellspan);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(curropen.toString())), font2));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(totalstockinItem.toString())), font2));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(totalstockoutItem.toString())), font2));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(balance.toString())), font2));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
		}
		if(!stock.getStockRegister().isEmpty())
		doc.add(table);
		
	}
	
}