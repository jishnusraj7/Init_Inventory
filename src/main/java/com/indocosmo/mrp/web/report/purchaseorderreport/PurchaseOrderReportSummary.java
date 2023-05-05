
package com.indocosmo.mrp.web.report.purchaseorderreport;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.purchaseordersType;
import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.purchaseorderreport.model.PurchaseOrderReportModel;
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

public class PurchaseOrderReportSummary extends AbstractITextPdfView {
	String companyName;
	String dateFormat;
	Integer decimalPlace;
	String currency;

	public String getRateWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}

	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}

	@Override
	public Document newDocument() {
		return new Document(PageSize.A4, 36, 36, 80, 80);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		PurchaseOrderReportModel poList = (PurchaseOrderReportModel) model.get("poSummaryView");
		Report report = (Report) model.get("reportName");
		companyName = report.getCompanyName();
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		currency = report.getCurrency();

		Font font = FontFactory.getFont(FontFactory.HELVETICA, 9);
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);

		int count = 0;

		final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 11, Font.BOLD);
		String Header1 = "BETWEEN  " + poList.getStartdate() + "  AND  " + poList.getEnddate();

		PdfPTable table25 = new PdfPTable(1);
		table25.setWidthPercentage(100.0f);
		table25.setSpacingBefore(2);
		table25.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPCell cell25 = new PdfPCell();
		cell25.setBorder(Rectangle.NO_BORDER);
		cell25.setPhrase(new Phrase(Header1, SUBFONT));

		cell25.setHorizontalAlignment(Element.ALIGN_CENTER);

		table25.addCell(cell25);
		doc.add(table25);

		String grno = "";
		if (!poList.getPurchasreportData().isEmpty()) {
			grno = poList.getPurchasreportData().get(0).getPo_number();
		} else {
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
		String stockItmName = "";

		Double total = 0.0;
		int i = 0;
		int f = 0;
		for (PurchaseOrderReportModel stockindtl : poList.getPurchasreportData()) {

			if (!(stockindtl.getItm_cat_name().matches(stockItmName)))

			{
				f = 0;
				stockItmName = stockindtl.getItm_cat_name();

			}

			PdfPCell cellspan = new PdfPCell();
			cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan.setPadding(5);
			cellspan.setColspan(5);

			if (!(stockindtl.getPo_number()).equals(grno)) {
				i = 0;
				grno = stockindtl.getPo_number();
			}

			if (stockindtl.getPo_number() == grno && i == 0) {
				i = 1;
				count = 0;

				PdfPTable table1 = new PdfPTable(4);
				table1.setWidths(new float[] { .7f, 1.5f, .6f, 1.5f });
				table1.setWidthPercentage(100.0f);
				table1.setSpacingBefore(0);

				PdfPCell cell1 = new PdfPCell();
				cell1.setBackgroundColor(new BaseColor(255, 255, 255));
				cell1.setPadding(5);
				cell1.setBorder(Rectangle.NO_BORDER);
				table1.getDefaultCell().setPaddingBottom(10.0f);
				table1.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
				table1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table1.getDefaultCell().setBorderWidthBottom(2.0f);
				table1.setSpacingBefore(10);

				PdfPTable table = new PdfPTable(5);
				table.setWidths(new float[] { .8f, 3f, 1.3f, 1.3f, 1.3f });
				table.setWidthPercentage(100.0f);
				table.setSpacingBefore(0);

				PdfPCell cell = new PdfPCell();

				cell.setBackgroundColor(new BaseColor(255, 255, 255));
				cell.setPadding(5);
				table.getDefaultCell().setPaddingBottom(10.0f);
				table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
				table.getDefaultCell().setBorder(Rectangle.BOTTOM);
				table.getDefaultCell().setBorderWidthBottom(2.0f);

				cell1.setPhrase(new Phrase("Date:", font1));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase(stockindtl.getPo_date(), font));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase("PO NO:", font1));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase(stockindtl.getPo_number(), font));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase("SUPPLIER:", font1));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase(stockindtl.getSupplier_name(), font));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase(" ", font1));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);

				purchaseordersType[] list = purchaseordersType.values();
				/*
				 * 
				 * cell1.setPhrase(new
				 * Phrase((list[stockindtl.getPo_status()].getPurchaseorderTypeName()), font));
				 * cell1.setHorizontalAlignment(Element.ALIGN_LEFT); table1.addCell(cell1);
				 */

				cell1.setPhrase(new Phrase(" ", font));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);

				doc.add(table1);

				cell.setPhrase(new Phrase("SI#", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell.setPhrase(new Phrase("Item Name", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell.setPhrase(new Phrase("Qty", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell.setPhrase(new Phrase("Rate", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell.setPhrase(new Phrase("Amount", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				doc.add(table);
				f = 0;
			}
			PdfPTable table9 = new PdfPTable(5);
			table9.setWidths(new float[] { .8f, 3f, 1.3f, 1.3f, 1.3f });
			table9.setWidthPercentage(100.0f);
			table9.setSpacingBefore(0);

			PdfPCell cell9 = new PdfPCell();
			cell9.setBackgroundColor(new BaseColor(255, 255, 255));
			cell9.setBorderColorBottom(new BaseColor(0, 0, 0));
			cell9.setPadding(5);
			table9.getDefaultCell().setPaddingBottom(10.0f);
			table9.getDefaultCell().setBorderColorBottom(new BaseColor(0, 0, 0));
			table9.getDefaultCell().setBorder(Rectangle.BOTTOM);
			table9.getDefaultCell().setBorderWidthBottom(2.0f);

			// span column
			PdfPCell cellspan1 = new PdfPCell();
			cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
			cellspan1.setPadding(5);
			cellspan1.setColspan(5);

			if (!(stockindtl.getItm_cat_name().matches(stockItmName))) {
				f = 1;
				stockItmName = stockindtl.getItm_cat_name();

			}

			if (stockindtl.getItm_cat_name().matches(stockItmName) && f == 0)

			{
				cellspan1.setPhrase(new Phrase(stockindtl.getItm_cat_name(), font));
				cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table9.addCell(cellspan1);
				f = 1;

			}

			count = count + 1;
			String sino = "" + count;
			cell9.setPhrase(new Phrase(sino, font));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.addCell(cell9);

			cell9.setPhrase(new Phrase(stockindtl.getStock_item_name(), font));
			cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
			table9.addCell(cell9);

			cell9.setPhrase(new Phrase(getNumberWithDecimal(stockindtl.getReceived_qty()), font));
			cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table9.addCell(cell9);

			cell9.setPhrase(new Phrase(getRateWithDecimal(stockindtl.getUnit_price()), font));
			cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table9.addCell(cell9);

			cell9.setPhrase(
					new Phrase(getNumberWithDecimal(stockindtl.getReceived_qty() * stockindtl.getUnit_price()), font));
			cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table9.addCell(cell9);
			total = total + stockindtl.getTotal_amount();

			doc.add(table9);

		}
	}
}

/*
 * package com.indocosmo.mrp.web.report.purchaseorderreport;
 * 
 * import java.math.BigDecimal; import java.util.Map;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.
 * purchaseordersType; import
 * com.indocosmo.mrp.web.report.common.AbstractITextPdfView; import
 * com.indocosmo.mrp.web.report.common.model.Report; import
 * com.indocosmo.mrp.web.report.purchaseorderreport.model.
 * PurchaseOrderReportModel; import com.itextpdf.text.BaseColor; import
 * com.itextpdf.text.Document; import com.itextpdf.text.Element; import
 * com.itextpdf.text.Font; import com.itextpdf.text.FontFactory; import
 * com.itextpdf.text.PageSize; import com.itextpdf.text.Phrase; import
 * com.itextpdf.text.Rectangle; import com.itextpdf.text.pdf.ColumnText; import
 * com.itextpdf.text.pdf.PdfPCell; import com.itextpdf.text.pdf.PdfPTable;
 * import com.itextpdf.text.pdf.PdfWriter;
 * 
 * public class PurchaseOrderReportSummary extends AbstractITextPdfView { String
 * companyName; String dateFormat; Integer decimalPlace; String currency;
 * 
 * 
 * public String getNumberWithDecimal(Double value) {
 * 
 * BigDecimal bd = new BigDecimal(value); bd = bd.setScale(decimalPlace,
 * BigDecimal.ROUND_HALF_UP);
 * 
 * return bd.toString(); }
 * 
 * @Override public Document newDocument() { return new
 * Document(PageSize.A4.rotate(),36, 36, 150, 160); }
 * 
 * 
 * @Override protected void buildPdfDocument(Map<String, Object> model, Document
 * doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse
 * response) throws Exception {
 * 
 * 
 * PurchaseOrderReportModel poList = (PurchaseOrderReportModel)
 * model.get("poSummaryView"); Report report = (Report) model.get("reportName");
 * companyName = report.getCompanyName(); decimalPlace =
 * report.getDecimalPlace(); dateFormat = report.getDateFormat(); currency =
 * report.getCurrency();
 * 
 * Font font = FontFactory.getFont(FontFactory.HELVETICA); Font
 * font1=FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD);
 * 
 * 
 * 
 * int count=0;
 * 
 * final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 12,Font.BOLD);
 * String
 * Header1="BETWEEN  "+poList.getStartdate()+"  AND  "+poList.getEnddate();
 * 
 * 
 * PdfPTable table25 = new PdfPTable(1); table25.setWidthPercentage(100.0f);
 * table25.setSpacingBefore(2);
 * table25.getDefaultCell().setBorder(Rectangle.NO_BORDER);
 * 
 * PdfPCell cell25 = new PdfPCell(); cell25.setBorder(Rectangle.NO_BORDER);
 * cell25.setPhrase(new Phrase(Header1,SUBFONT));
 * 
 * cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
 * 
 * table25.addCell(cell25); doc.add(table25);
 * 
 * 
 * 
 * PdfPTable table = new PdfPTable(7); table.setWidths(new float[]
 * {.8f,1.2f,2f,2f,1.2f,1.3f,1.5f}); table.setWidthPercentage(100.0f);
 * table.setSpacingBefore(0);
 * 
 * PdfPCell cell = new PdfPCell(); cell.setBackgroundColor(new BaseColor(255,
 * 255, 255)); cell.setPadding(5);
 * table.getDefaultCell().setPaddingBottom(10.0f);
 * table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
 * table.getDefaultCell().setBorder(Rectangle.BOTTOM);
 * table.getDefaultCell().setBorderWidthBottom(2.0f); cell.setPhrase(new
 * Phrase("SINO", font1)); cell.setHorizontalAlignment(Element.ALIGN_CENTER);
 * table.addCell(cell); cell.setPhrase(new Phrase("Date", font1));
 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(cell);
 * cell.setPhrase(new Phrase("Item Name", font1));
 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(cell);
 * cell.setPhrase(new Phrase("Supplier", font1));
 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(cell);
 * cell.setPhrase(new Phrase("PO Number", font1));
 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(cell);
 * cell.setPhrase(new Phrase("Status", font1));
 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(cell);
 * cell.setPhrase(new Phrase("Amount", font1));
 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(cell);
 * Double total=0.0; for (PurchaseOrderReportModel po_dtl :
 * poList.getPurchasreportData()) { count=count+1; String sino=""+count;
 * cell.setPhrase(new Phrase(sino, font));
 * cell.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(cell);
 * cell.setPhrase(new Phrase(po_dtl.getPo_date(), font));
 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
 * cell.setPhrase(new Phrase(po_dtl.getStock_item_name(), font));
 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
 * cell.setPhrase(new Phrase(po_dtl.getSupplier_name().toString(), font));
 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
 * cell.setPhrase(new Phrase(po_dtl.getPo_number().toString(), font));
 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
 * purchaseordersType[] list= purchaseordersType.values();
 * 
 * 
 * cell.setPhrase(new
 * Phrase((list[po_dtl.getPo_status()].getPurchaseorderTypeName()), font));
 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
 * cell.setPhrase(new Phrase(getNumberWithDecimal(po_dtl.getTotal_amount()),
 * font)); cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
 * table.addCell(cell); total=total+po_dtl.getTotal_amount(); } if(count<10){
 * for(int i=count;i<=7;i++){ cell.setPhrase(new Phrase(" "));
 * table.addCell(cell); cell.setPhrase(new Phrase(" ")); table.addCell(cell);
 * cell.setPhrase(new Phrase(" ")); table.addCell(cell); cell.setPhrase(new
 * Phrase(" ")); table.addCell(cell); cell.setPhrase(new Phrase(" "));
 * table.addCell(cell); cell.setPhrase(new Phrase(" ")); table.addCell(cell);
 * cell.setPhrase(new Phrase(" ")); table.addCell(cell);
 * 
 * } } cell.setPhrase(new Phrase(" ")); table.addCell(cell); cell.setPhrase(new
 * Phrase("")); table.addCell(cell); cell.setPhrase(new Phrase(""));
 * table.addCell(cell); cell.setPhrase(new Phrase("")); table.addCell(cell);
 * cell.setPhrase(new Phrase("")); table.addCell(cell);
 * 
 * cell.setPhrase(new Phrase("Grand Total ("+currency+"):", font1));
 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(cell);
 * 
 * cell.setPhrase(new Phrase(getNumberWithDecimal(total), font1));
 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(cell);
 * 
 * doc.add(table);
 * 
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * }
 */