package com.indocosmo.mrp.web.report.departmentwisereport;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
import com.indocosmo.mrp.web.report.departmentwisereport.model.DepartmentWiseReport;
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
 * 
 * @gana 31220
 */
public class DepartmentWiseReportDesign extends AbstractITextPdfView {
	String companyName;

	String dateFormat;

	Integer decimalPlace;

	String currency;

	@Override
	public Document newDocument() {
		return new Document(PageSize.A4, 36, 36, 120, 80);
	}

	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DepartmentWiseReport departmentWiseStock = (DepartmentWiseReport) model.get("listInvoice");
		Report report = (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();
		int count = 0;		
		String stockDate = "";
		String sino="";
		String Header1 = "";

		Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);

		PdfPTable table23 = new PdfPTable(1);
		table23.setWidthPercentage(100.0f);

		if(departmentWiseStock.getDepartmentWiseReport().isEmpty()) {

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
		}else {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date=dateFormat.parse(departmentWiseStock.getStockDate());
			stockDate=dateFormat.format(date);
			Header1 = stockDate.toUpperCase();

			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100.0f);
			table.setSpacingBefore(2);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

			/*PdfPCell cellCategory = new PdfPCell();
			cellCategory.setBackgroundColor(new BaseColor(255, 255, 255));
			cellCategory.setPadding(5);

			table.getDefaultCell().setPaddingBottom(10.0f);
			table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setBorderWidthBottom(2.0f);
			cellCategory.setPhrase(new Phrase(departmentWiseStock.getItem_category_name(), font));
            document.add(table);*/
            
			PdfPTable dataTable=new PdfPTable(7);
			dataTable.setWidths(new float[] { .4f, 1.5f, .6f, .7f, .7f, .6f, .6f});

			dataTable.setWidthPercentage(100.0f);

			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(6);
			cellspan.setColspan(7);

			cellspan.setPhrase(new Phrase(departmentWiseStock.getItem_category_name(), font));
			cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
			dataTable.addCell(cellspan);



			PdfPCell dataCell = new PdfPCell();
			dataCell.setBackgroundColor(new BaseColor(255, 255, 255));
			dataCell.setPadding(5);

			dataTable.getDefaultCell().setPaddingBottom(10.0f);
			dataTable.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			dataTable.getDefaultCell().setBorder(Rectangle.BOTTOM);
			dataTable.getDefaultCell().setBorderWidthBottom(2.0f);
			dataCell.setPhrase(new Phrase("SI#", font1));
			dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dataTable.addCell(dataCell);
			dataCell.setPhrase(new Phrase("ITEM", font1));
			dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dataTable.addCell(dataCell);
			dataCell.setPhrase(new Phrase("OPENING STOCK", font1));
			dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dataTable.addCell(dataCell);
			dataCell.setPhrase(new Phrase("PURCHASE", font1));
			dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dataTable.addCell(dataCell);
			dataCell.setPhrase(new Phrase("TOTAL", font1));
			dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dataTable.addCell(dataCell);
			dataCell.setPhrase(new Phrase("ISSUE", font1));
			dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dataTable.addCell(dataCell);
			dataCell.setPhrase(new Phrase("CLOSING STOCK", font1));
			dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			dataTable.addCell(dataCell);

			double total=0.0;
			double closing=0.0;


			for (DepartmentWiseReport departmentStock : departmentWiseStock.getDepartmentWiseReport()) {



				count = count + 1;
				sino = "" + count;

				dataCell.setPhrase(new Phrase(sino, font));
				dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				dataTable.addCell(dataCell);

				dataCell.setPhrase(new Phrase(departmentStock.getStockItemName().toString(), font));
				dataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				dataTable.addCell(dataCell);

				dataCell.setPhrase(new Phrase(getNumberWithDecimal((departmentStock.getOpeningStock())), font));
				dataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				dataTable.addCell(dataCell);

				dataCell.setPhrase(new Phrase(getNumberWithDecimal((departmentStock.getStockIn())), font));
				dataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				dataTable.addCell(dataCell);

				total=departmentStock.getOpeningStock()+departmentStock.getStockIn();
				dataCell.setPhrase(new Phrase(getNumberWithDecimal((total)), font));
				dataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				dataTable.addCell(dataCell);

				dataCell.setPhrase(new Phrase(getNumberWithDecimal((departmentStock.getStockOut())), font));
				dataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				dataTable.addCell(dataCell);

				closing=total-departmentStock.getStockOut();
				dataCell.setPhrase(new Phrase(getNumberWithDecimal(closing), font));
				dataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				dataTable.addCell(dataCell);

			}
			document.add(dataTable);


		}
	}
}

