package com.indocosmo.mrp.web.report.purchasereturnreport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
import com.indocosmo.mrp.web.report.purchasereturnreport.model.PurchaseReturnReport;
import com.indocosmo.mrp.web.report.stockoutreport.model.StockoutReportModel;
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
/*
 * @gana 02042020
 */

public class PurchaseReturnReportDesign extends AbstractITextPdfView{

	String companyName;
	String dateFormat;
	Integer decimalPlace;
	String currency;

	@Override
	public Document newDocument() {
		return new Document(PageSize.A4, 36, 36, 120, 80);
	}

	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		PurchaseReturnReport  returnStockList =   (PurchaseReturnReport) model.get("purchaseReturnView");
		Report  report =   (Report) model.get("reportName");
		companyName = report.getCompanyName();
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		currency = report.getCurrency();

		Font font = FontFactory.getFont(FontFactory.HELVETICA,9);
		Font font1=FontFactory.getFont(FontFactory.HELVETICA,11,Font.BOLD);
		int count=0;
		String sino = "";
		final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 11,Font.BOLD);
		String Header1="BETWEEN  "+returnStockList.getStartDate()+"  AND  "+returnStockList.getEndDate();

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100.0f);
		table.setSpacingBefore(2);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPCell cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase(Header1,SUBFONT));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		document.add(table);

		if(!returnStockList.getPurchaseReturnReportData().isEmpty()) {

			PdfPTable tableRow = new PdfPTable(7);
			tableRow.setWidths(new float[] { .8f, 3f, 1f, 1f,1.5f,1f,1f });
			tableRow.setWidthPercentage(100.0f);

			PdfPCell cellRow = new PdfPCell();
			cellRow.setBackgroundColor(new BaseColor(255, 255, 255));
			cellRow.setPadding(5);
			tableRow.getDefaultCell().setPaddingBottom(10.0f);
			tableRow.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			tableRow.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableRow.getDefaultCell().setBorderWidthBottom(2.0f);
			cellRow.setPhrase(new Phrase("SI#", font1));
			cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableRow.addCell(cellRow);
			cellRow.setPhrase(new Phrase("ITEM", font1));
			cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableRow.addCell(cellRow);
			cellRow.setPhrase(new Phrase("UNIT", font1));
			cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableRow.addCell(cellRow);
			cellRow.setPhrase(new Phrase("DATE", font1));
			cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableRow.addCell(cellRow);
			cellRow.setPhrase(new Phrase("SUPPLIER", font1));
			cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableRow.addCell(cellRow);
			cellRow.setPhrase(new Phrase("REF NO", font1));
			cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableRow.addCell(cellRow);
			cellRow.setPhrase(new Phrase("RETURN QTY", font1));
			cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableRow.addCell(cellRow);

			for (PurchaseReturnReport purchaseReturnReportData : returnStockList.getPurchaseReturnReportData()) {

				count = count + 1;
				sino = "" + count;
               
				cellRow.setPhrase(new Phrase(sino, font));
				cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableRow.addCell(cellRow);
				
				cellRow.setPhrase(new Phrase(purchaseReturnReportData.getStock_item_name(), font));
				cellRow.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableRow.addCell(cellRow);
				
				cellRow.setPhrase(new Phrase(purchaseReturnReportData.getUomcode(), font));
				cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableRow.addCell(cellRow);
				
				cellRow.setPhrase(new Phrase(purchaseReturnReportData.getReturn_date(), font));
				cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableRow.addCell(cellRow);
				
				cellRow.setPhrase(new Phrase(purchaseReturnReportData.getSupplier_name(), font));
				cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableRow.addCell(cellRow);
				
				cellRow.setPhrase(new Phrase(purchaseReturnReportData.getReturn_no(), font));
				cellRow.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableRow.addCell(cellRow);
				
				cellRow.setPhrase(new Phrase(getNumberWithDecimal(purchaseReturnReportData.getReturn_qty()), font));
				cellRow.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tableRow.addCell(cellRow);
			}
			document.add(tableRow);
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
			cell89.setPhrase(new Phrase("No Items Available ", font1));
			cell89.setHorizontalAlignment(Element.ALIGN_CENTER);
			table89.addCell(cell89);
			document.add(table89);

		}
	}

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




}
