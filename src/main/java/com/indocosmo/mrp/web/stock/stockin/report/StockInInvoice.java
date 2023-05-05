package com.indocosmo.mrp.web.stock.stockin.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class StockInInvoice extends PdfPageEventHelper {

	PdfTemplate total;
	String header;
	Integer ReportType;
	Integer count = 0;
	String CurrName;
	String CompanyName;
	String dateFormat;
	Integer decimalPlace;
	String currency;
	
	final String rptTitle = "STOCK IN";
	final int rptHeight = 30;
	final int rptWidth = 16;
	final int pageWidth = 400;
	
	final Font pageNumberFont = new Font(Font.getFamily("TIMES_ROMAN"), 9,Font.NORMAL);	
	final Font subFont = new Font(Font.getFamily("TIMES_ROMAN"), 12,Font.BOLD);
	
	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		
		document.addTitle(rptTitle);
		total = writer.getDirectContent().createTemplate(rptHeight, rptWidth);
	}

	
	public void setCompanyName(String CompanyName){
		
		this.CompanyName=CompanyName;
	}
	
	public void setCurrName(String CurrName) {
		
		this.CurrName = CurrName;
	}

	public void setHeader(String header) {
		
		this.header = header;
	}

	public void setReportType(Integer ReportType) {
		
		this.ReportType = ReportType;
	}
	
	public void setDateFormat(String dateFormat) {
		
		this.dateFormat = dateFormat;
	}
	
	public void setDecimalPlace(Integer decimalPlace) {
		
		this.decimalPlace = decimalPlace;
	}
	
	public void setCurrency(String currency) {
		
		this.currency = currency;
	}

	@Override
	public void onStartPage(PdfWriter writer, Document document) {

		if (count == 0) {
			
			count = count + 1;			
			Phrase p = new Phrase(header, subFont);
			ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER,p,(document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 20, 0);
		}
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		
		PdfPTable table = new PdfPTable(2);		
		try {
			
			table.setWidths(new int[] {13,1});
			table.setTotalWidth(pageWidth);
			table.setWidthPercentage(100.0f);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), pageNumberFont));
			
			PdfPCell cell1 = new PdfPCell(Image.getInstance(total));
			cell1.setBorder(Rectangle.NO_BORDER);
			
			table.addCell(cell1);
			table.writeSelectedRows(0, -1, document.rightMargin() - 10, 20,writer.getDirectContent());			
		}
		catch (DocumentException de) {
			
			throw new ExceptionConverter(de);
		}
	}

	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		
		ColumnText.showTextAligned(total, Element.ALIGN_LEFT, 
				new Phrase(String.valueOf(writer.getPageNumber()),pageNumberFont), 2,5, 0);
	}
}
