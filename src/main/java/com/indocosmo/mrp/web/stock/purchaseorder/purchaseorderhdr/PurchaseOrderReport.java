package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.model.PO_dtl;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.model.PO_hdr;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PurchaseOrderReport extends AbstractITextPdfView {
	String companyName;
	String dateFormat;
	Integer decimalPlace;
	String currency;

	@Override
	public Document newDocument() {

		return new Document(PageSize.A4,36, 36, 80, 80);
	}

	
	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		
		return bd.toString();
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		Report  report =   (Report) model.get("reportName");
		companyName = report.getCompanyName();
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		currency = report.getCurrency();

		PO_hdr  pohdr =   (PO_hdr) model.get("listInvoice");
		
		
		Font font1=FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD);
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.BLACK);
		Font fontchk = FontFactory.getFont(FontFactory.HELVETICA,9);
		fontchk.setColor(BaseColor.WHITE);
		PdfPTable table1 = new PdfPTable(4);
		table1.setWidthPercentage(100.0f);
		table1.setWidths(new float[] {1.5f,1.5f,1.5f,1.5f});
		table1.setSpacingBefore(10);
		PdfPCell cell1 = new PdfPCell();
		cell1.setBackgroundColor(new BaseColor(255, 255, 255));
		cell1.setPadding(5);
		table1.getDefaultCell().setPaddingBottom(10.0f);
		table1.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
		table1.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table1.getDefaultCell().setBorderWidthBottom(2.0f);
		cell1.setPhrase(new Phrase("PO NO:", font1));
		table1.addCell(cell1);
		cell1.setPhrase(new Phrase(pohdr.getPo_number(), font));
		table1.addCell(cell1);
		cell1.setPhrase(new Phrase("Print Date", font1));
		table1.addCell(cell1);
		DateFormat dateFormat1 = new SimpleDateFormat(dateFormat);
		cell1.setPhrase(new Phrase(dateFormat1.format(new Date()), font));
		table1.addCell(cell1);
		cell1.setPhrase(new Phrase("PO Date", font1));
		table1.addCell(cell1);
		cell1.setPhrase(new Phrase(pohdr.getPo_date(), font));
		table1.addCell(cell1);
		cell1.setPhrase(new Phrase("Supplier", font1));
		table1.addCell(cell1);
		cell1.setPhrase(new Phrase(pohdr.getSupplier_name(), font));
		table1.addCell(cell1);
		doc.add(table1);	
		PdfPTable table18 = new PdfPTable(2);
		table18.setWidthPercentage(100.0f);
		table18.setWidths(new float[] {2f,2f});
		table18.setSpacingBefore(0);

		PdfPCell cell18 = new PdfPCell();
		cell18.setBackgroundColor(new BaseColor(255, 255, 255));
		cell18.setPadding(5);
		table18.getDefaultCell().setPaddingBottom(10.0f);
		table18.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
		table18.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table18.getDefaultCell().setBorderWidthBottom(2.0f);
		cell18.setPhrase(new Phrase("Billing Address", font1));
		table18.addCell(cell18);	
		cell18.setPhrase(new Phrase("Shipping Address", font1));
		table18.addCell(cell18);
		String Billing=pohdr.getBilling_address();
		if(Billing==null){Billing="    ";}
		cell18.setPhrase(new Phrase(Billing, font));
		table18.addCell(cell18);
		String Shipping=pohdr.getShipping_address();
		if(Shipping==null){Shipping="   ";}
		cell18.setPhrase(new Phrase(Shipping, font));
		table18.addCell(cell18);	
		doc.add(table18);	

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {0.7f,3.0f,1.0f,1.0f,1.5f});
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(new BaseColor(255, 255, 255));
		cell.setPadding(5);
		table.getDefaultCell().setPaddingBottom(10.0f);
		table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
		table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table.getDefaultCell().setBorderWidthBottom(2.0f);
		cell.setPhrase(new Phrase("SINO", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("Particulars", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("Quantity", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("Rate", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("Amount", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		int count=0;
		for (PO_dtl po_dtl : pohdr.getPo_dtl()) {
			count=count+1;
			String sino=""+count;
			cell.setPhrase(new Phrase(sino, font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell.setPhrase(new Phrase(po_dtl.getStock_item_name(), font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell.setPhrase(new Phrase(po_dtl.getQty().toString(), font));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			cell.setPhrase(new Phrase(po_dtl.getUnit_price().toString(), font));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			cell.setPhrase(new Phrase(getNumberWithDecimal(po_dtl.getAmount()), font));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
		}



		if(count<11){
			for(int i=count;i<=11;i++){
				cell.setPhrase(new Phrase(" "));
				table.addCell(cell);
				cell.setPhrase(new Phrase(" "));
				table.addCell(cell);
				cell.setPhrase(new Phrase(" "));
				table.addCell(cell);
				cell.setPhrase(new Phrase(" "));
				table.addCell(cell);
				cell.setPhrase(new Phrase(" "));
				table.addCell(cell);
			}
		}

		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);
		cell.setPhrase(new Phrase(""));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Total:", font1));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);

		cell.setPhrase(new Phrase(getNumberWithDecimal(pohdr.getTotal_amount()), font1));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);




		doc.add(table);








		PdfPTable table19 = new PdfPTable(2);
		table19.setWidthPercentage(100.0f);
		table19.setWidths(new float[] {2f,2f});
		table19.setSpacingBefore(10);

		PdfPCell cell19 = new PdfPCell();
		cell19.setBackgroundColor(new BaseColor(255, 255, 255));
		cell19.setPadding(5);
		table19.getDefaultCell().setPaddingBottom(10.0f);
		table19.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
		table19.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table19.getDefaultCell().setBorderWidthBottom(2.0f);

		cell19.setPhrase(new Phrase("Remarks", font1));
		table19.addCell(cell19);	

		cell19.setPhrase(new Phrase("Terms", font1));
		table19.addCell(cell19);
		String Remarks=pohdr.getRemarks();
		if(Remarks==null){
			Remarks="      ";
		}
		cell19.setPhrase(new Phrase(Remarks, font));
		table19.addCell(cell19);
		String Terms=pohdr.getTerms();
		if(Terms==null){
			Terms="   ";
		}
		cell19.setPhrase(new Phrase(Terms, font));
		table19.addCell(cell19);	
		doc.add(table19);





		PdfPTable table10 = new PdfPTable(2);

		table10.setWidthPercentage(100.0f);
		PdfPCell cell10 = new PdfPCell();
		cell10.setBorder(Rectangle.NO_BORDER);

		cell10.setPhrase(new Phrase("  "));
		table10.addCell(cell10);
		cell10.setPhrase(new Phrase("  "));
		table10.addCell(cell10);

		cell10.setPhrase(new Phrase(""));
		table10.addCell(cell10);
		cell10.setPhrase(new Phrase("For "+pohdr.getCommpanyName()));
		cell10.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table10.addCell(cell10);
		cell10.setPhrase(new Phrase("  "));
		table10.addCell(cell10);
		cell10.setPhrase(new Phrase("  "));
		table10.addCell(cell10);
		cell10.setPhrase(new Phrase("  "));
		table10.addCell(cell10);
		cell10.setPhrase(new Phrase("  "));
		table10.addCell(cell10);
		cell10.setPhrase(new Phrase(""));
		table10.addCell(cell10);
		cell10.setPhrase(new Phrase("Authorized Signature"));
		cell10.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table10.addCell(cell10);
		doc.add(table10);



		if(pohdr.getPo_status()==1){
			ColumnText.showTextAligned(writer.getDirectContent(),
					Element.ALIGN_LEFT, new Phrase("Duplicate Copy"),
					doc.leftMargin(),8,0); 
		}

	}





}
