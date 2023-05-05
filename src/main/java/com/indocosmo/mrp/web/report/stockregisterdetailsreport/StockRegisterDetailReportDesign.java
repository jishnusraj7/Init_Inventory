package com.indocosmo.mrp.web.report.stockregisterdetailsreport;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockregisterdetailsreport.model.StockRegisterDetailReport;
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

public class StockRegisterDetailReportDesign extends AbstractITextPdfView{

	String companyName;

	String dateFormat;

	String reportHeader;

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

	public Document newDocument(Map<String, Object> model) {

		return new Document(PageSize.A4, 36, 36, 120, 30);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {/*
		// TODO Auto-generated method stub

		Report report = (Report) model.get("reportName");
		companyName = report.getCompanyName();
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		currency = report.getCurrency();

		StockRegisterDetailReport stockRegisterDetailsReport=(StockRegisterDetailReport) model.get("listInvoice");
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 9);
		final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 11, Font.BOLD);
		int count = 0;		
		String dateFrom = "";
		String dateTo = "";

		if(stockRegisterDetailsReport.getStockRegisterDetailsReport().isEmpty()) {

			PdfPTable pdfTable=new PdfPTable(1);
			pdfTable.setWidthPercentage(100.0f);
			pdfTable.setSpacingBefore(10);

			PdfPCell pdfCell=new PdfPCell();
			pdfCell.setBackgroundColor(new BaseColor(255, 255, 255));
			pdfCell.setPadding(5);
			pdfTable.getDefaultCell().setPaddingBottom(10.0f);
			pdfTable.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			pdfTable.getDefaultCell().setBorder(Rectangle.BOTTOM);
			pdfTable.getDefaultCell().setBorderWidthBottom(2.0f);
			pdfCell.setPhrase(new Phrase("No Items Available ", SUBFONT));
			pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdfTable.addCell(pdfCell);
			document.add(pdfTable);
		}

		DateFormat dateFormat1 = new SimpleDateFormat(dateFormat);
		reportHeader = "BETWEEN  " + stockRegisterDetailsReport.getStartdate() + "  AND  " + stockRegisterDetailsReport.getEnddate();

		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);

		PdfPTable stockTable=new PdfPTable(1);
		stockTable.setWidthPercentage(100.0f);
		stockTable.setSpacingBefore(2);
		stockTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPCell stockCell = new PdfPCell();
		stockCell.setBorder(Rectangle.NO_BORDER);
		stockCell.setPhrase(new Phrase(reportHeader, SUBFONT));

		stockCell.setHorizontalAlignment(Element.ALIGN_CENTER);

		stockTable.addCell(stockCell);

		if(!stockRegisterDetailsReport.getStockRegisterDetailsReport().isEmpty())
			document.add(stockTable);

		PdfPTable stockDataTable = new PdfPTable(2);

		stockDataTable.setWidthPercentage(100.0f);

		PdfPCell stockDataCell = new PdfPCell();
		stockDataCell.setBackgroundColor(new BaseColor(255, 255, 255));
		stockDataCell.setPadding(5);
		stockDataTable.getDefaultCell().setPaddingBottom(10.0f);
		stockDataTable.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
		stockDataTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		stockDataTable.getDefaultCell().setBorderWidthBottom(2.0f);

		stockDataCell.setPhrase(new Phrase(stockRegisterDetailsReport.getDepartment_name(), font1));
		stockDataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		stockDataCell.setBorderColorBottom(new BaseColor(255, 255, 255));
		stockDataCell.setBorder(Rectangle.NO_BORDER);
		stockDataTable.addCell(stockDataCell);

		stockDataCell.setPhrase(new Phrase(stockRegisterDetailsReport.getItem_category_name(), font1));
		stockDataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		stockDataCell.setBorderColorBottom(new BaseColor(255, 255, 255));
		stockDataCell.setBorder(Rectangle.NO_BORDER);
		stockDataTable.addCell(stockDataCell);
		if(!stockRegisterDetailsReport.getStockRegisterDetailsReport().isEmpty())
			document.add(stockDataTable);

		PdfPTable stockItemTable=new PdfPTable(8);
		stockItemTable.setWidths(new float[] { .4f, 1.5f, .6f, .7f, .7f, .6f, .6f, .6f });
		stockItemTable.setWidthPercentage(100.0f);

		PdfPCell stockItemCell = new PdfPCell();
		stockItemCell.setBackgroundColor(new BaseColor(255, 255, 255));
		stockItemCell.setPadding(5);
		stockItemTable.setSpacingBefore(5);

		stockItemTable.getDefaultCell().setPaddingBottom(10.0f);
		stockItemTable.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
		stockItemTable.getDefaultCell().setBorder(Rectangle.BOTTOM);
		stockItemTable.getDefaultCell().setBorderWidthBottom(2.0f);

		stockItemCell.setPhrase(new Phrase("SI#", font1));
		stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		stockItemTable.addCell(stockItemCell);
		stockItemCell.setPhrase(new Phrase("ITEM", font1));
		stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		stockItemTable.addCell(stockItemCell);
		stockItemCell.setPhrase(new Phrase("Unit", font1));
		stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		stockItemTable.addCell(stockItemCell);
		stockItemCell.setPhrase(new Phrase("Date", font1));
		stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		stockItemTable.addCell(stockItemCell);
		stockItemCell.setPhrase(new Phrase("Opening", font1));
		stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		stockItemTable.addCell(stockItemCell);
		stockItemCell.setPhrase(new Phrase("Value", font1));
		stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		stockItemTable.addCell(stockItemCell);
		stockItemCell.setPhrase(new Phrase("Stock In", font1));
		stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		stockItemTable.addCell(stockItemCell);
		stockItemCell.setPhrase(new Phrase("Stock Out", font1));
		stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		stockItemTable.addCell(stockItemCell);

		stockItemTable.setHeaderRows(1);

		String stockitemname = "0";

		int f = 0;
		int firstRowOpening = 0;
		int firstRow = 0;

		if (!stockRegisterDetailsReport.getStockRegisterDetailsReport().isEmpty()) {
			stockitemname = stockRegisterDetailsReport.getStockRegisterDetailsReport().get(0).getStock_item_id();
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

		for (StockRegisterDetailReport stockDetailReport : stockRegisterDetailsReport.getStockRegisterDetailsReport()){

			if (!(stockDetailReport.getStock_item_id().equals(stockitemname)))

			{
				f = 0;
				firstRowOpening = 0;
				stockitemname = stockDetailReport.getStock_item_id();

			}

			if ((stockDetailReport.getStock_item_id().equals(stockitemname)) && f == 1) {

				stockItemCell.setPhrase(new Phrase(" ", font));
				stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				stockItemTable.addCell(stockItemCell);
				stockItemCell.setPhrase(new Phrase(" ", font));
				stockItemCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				stockItemTable.addCell(stockItemCell);
				stockItemCell.setPhrase(new Phrase(" ", font));
				stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				stockItemTable.addCell(stockItemCell);
			}
			else {
				if ((stockDetailReport.getStock_item_id().equals(stockitemname)) && f == 0)

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

						stockItemTable.addCell(cellspan);
						stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(curropen.toString())),
								font2));
						stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						stockItemTable.addCell(stockItemCell);
						stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(totalstockinItem
								.toString())), font2));
						stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						stockItemTable.addCell(stockItemCell);

						stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(totalstockoutItem
								.toString())), font2));
						stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						stockItemTable.addCell(stockItemCell);

						stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(balance.toString())),
								font2));
						stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						stockItemTable.addCell(stockItemCell);

						curropen = 0.0;
						totalstockoutItem = 0.0;
						totalstockinItem = 0.0;
					}

					count++;
					String sino = "" + count;
					stockItemCell.setPhrase(new Phrase(sino, font));
					stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					stockItemTable.addCell(stockItemCell);
					f = 1;
					stockItemCell.setPhrase(new Phrase(stockDetailReport.getStockitemName(), font));
					stockItemCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					stockItemTable.addCell(stockItemCell);
					stockItemCell.setPhrase(new Phrase(stockDetailReport.getUomcode(), font));
					stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					stockItemTable.addCell(stockItemCell);
				}
			}


			String open = stockDetailReport.getOpening();
			String inqty = stockDetailReport.getInQty();
			String outqty = stockDetailReport.getOutQty();
			if (open == null || open == "") {
				open = "0.0";
			}
			if (inqty == null || inqty == "") {
				inqty = "0.0";
			}
			if (outqty == null || outqty == "") {
				outqty = "0.0";
			}

			stockItemCell.setPhrase(new Phrase(stockDetailReport.getTxnDate(), font));
			stockItemTable.addCell(stockItemCell);

			if (stockDetailReport.getStock_item_id().equals(stockitemname) && firstRowOpening == 1) {

				stockItemCell.setPhrase(new Phrase(" ", font));
				stockItemCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				stockItemTable.addCell(stockItemCell);
			}
			else if (stockDetailReport.getStock_item_id().equals(stockitemname) && firstRowOpening == 0)

			{
				firstRowOpening = 1;
				curropen = Double.parseDouble(open.toString());

				stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(open.toString())), font));
				stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				stockItemTable.addCell(stockItemCell);

			}

			stockintotal += Double.parseDouble(inqty);
			stockouttotal += Double.parseDouble(outqty);
			balance = (Double.parseDouble(open) + Double.parseDouble(inqty)) - (Double.parseDouble(outqty));
			total = total + balance;

			openingValue = openingValue + Double.parseDouble(open) * Double.parseDouble(stockDetailReport.getCost_price());
			totalstockinItem += Double.parseDouble(inqty.toString());
			totalstockoutItem += Double.parseDouble(outqty.toString());
			stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(inqty.toString())), font));
			stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			stockItemTable.addCell(stockItemCell);

			inqtyValue = inqtyValue + Double.parseDouble(inqty) * Double.parseDouble(stockDetailReport.getCost_price());
			stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(outqty.toString())), font));
			stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			stockItemTable.addCell(stockItemCell);
			outqtyValue = outqtyValue + Double.parseDouble(outqty) * Double.parseDouble(stockDetailReport.getCost_price());

			balanceValue = balanceValue + Double.parseDouble(balance.toString())
			* Double.parseDouble(stockDetailReport.getCost_price());

			stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(balance.toString())), font));
			stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			stockItemTable.addCell(stockItemCell);
			
			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(5);
			cellspan.setColspan(4);
			cellspan.setPhrase(new Phrase("Total:", font1));
			cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			stockItemTable.addCell(cellspan);
			
			stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(curropen.toString())), font2));
			stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			stockItemTable.addCell(stockItemCell);
			
			stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(totalstockinItem.toString())), font2));
			stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			stockItemTable.addCell(stockItemCell);
			
			stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(totalstockoutItem.toString())), font2));
			stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			stockItemTable.addCell(stockItemCell);
			
			stockItemCell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(balance.toString())), font2));
			stockItemCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			stockItemTable.addCell(stockItemCell);
			
			if(!stockRegisterDetailsReport.getStockRegisterDetailsReport().isEmpty())
				document.add(stockItemTable);
		}
	*/}

}
