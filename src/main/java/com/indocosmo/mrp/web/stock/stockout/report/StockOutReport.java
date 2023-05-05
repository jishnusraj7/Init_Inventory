package com.indocosmo.mrp.web.stock.stockout.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.stock.stockout.model.StockOut;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.model.StockOutDetail;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class StockOutReport extends AbstractITextPdfView{
	
	String companyName;
	String dateFormat;
	Integer decimalPlace;
	String currency;
	Integer combine_mode;

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.common.AbstractITextPdfView#newDocument()
	 */
	@Override
	public Document newDocument() {
		
		Document document = new Document(PageSize.A5,15,15,50,60);
		document.addTitle("STOCK OUT");
		
		return document;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.common.AbstractITextPdfView#buildPdfDocument(java.util.Map, com.itextpdf.text.Document, com.itextpdf.text.pdf.PdfWriter, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void buildPdfDocument(Map<String, Object> model,Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final Font dataContentFont = new Font(Font.getFamily("TIMES_ROMAN"),10,Font.NORMAL);
		StockOut  stockOut =   (StockOut) model.get("invoiceData");
		//Font font = FontFactory.getFont(FontFactory.HELVETICA);
		final Font font = new Font(Font.getFamily("TIMES_ROMAN"),12,Font.BOLD);
		Font fontchk = FontFactory.getFont(FontFactory.HELVETICA,9);
		fontchk.setColor(BaseColor.WHITE);
		
		PdfPTable headerTable = new PdfPTable(3);
		headerTable.setWidthPercentage(100.0f);
		headerTable.setWidths(new float[] {1.725f,1.25f,0.975f});
		headerTable.setSpacingBefore(0);
		PdfPCell headerCell = new PdfPCell();
		headerCell.setBackgroundColor(new BaseColor(255, 255, 255));
		headerCell.setPadding(5);
		setCellBorder(.5f,.5f,.5f,.5f,headerCell);
		headerTable.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
		headerTable.getDefaultCell().setBorderWidthBottom(.5f);
		
		headerCell.setPhrase(new Phrase("FROM", font));
		headerTable.addCell(headerCell);
		
		setCellBorder(.5f,.5f,.5f,0f,headerCell);
		headerCell.setPhrase(new Phrase("Goods Issue No", font));
		headerTable.addCell(headerCell);
		
		setCellBorder(.5f,.5f,.5f,0f,headerCell);
		headerCell.setPhrase(new Phrase("Dated On", font));
		headerTable.addCell(headerCell);
		
		setCellBorder(0f,.5f,.5f,.5f,headerCell);
		headerCell.setPhrase(new Phrase(stockOut.getSrcDepartmentName(), dataContentFont));
		headerTable.addCell(headerCell);
		
		setCellBorder(0f,.5f,.5f,0f,headerCell);
		headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		headerCell.setPhrase(new Phrase(stockOut.getStockTransfeNo(), dataContentFont));
		headerTable.addCell(headerCell);
		
		setCellBorder(0f,.5f,.5f,0f,headerCell);
		headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		headerCell.setPhrase(new Phrase(stockOut.getStockTransferDate(), dataContentFont));
		headerTable.addCell(headerCell);
		
		setCellBorder(0f,.5f,.5f,.5f,headerCell);
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setPhrase(new Phrase("TO", font));
		headerTable.addCell(headerCell);
		
		setCellBorder(0f,.5f,.5f,0f,headerCell);
		headerCell.setPhrase(new Phrase("", font));
		headerTable.addCell(headerCell);
		
		setCellBorder(0f,.5f,.5f,0f,headerCell);
		headerCell.setPhrase(new Phrase("Request Date", font));
		headerTable.addCell(headerCell);
		
		setCellBorder(0f,.5f,.5f,.5f,headerCell);
		headerCell.setPhrase(new Phrase(stockOut.getDestDepartmentName(), dataContentFont));
		headerTable.addCell(headerCell);
		
		
		setCellBorder(0f,.5f,.5f,0f,headerCell);
		headerCell.setPhrase(new Phrase("", dataContentFont));
		headerTable.addCell(headerCell);
		
		setCellBorder(0f,.5f,.5f,0f,headerCell);
		headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		headerCell.setPhrase(new Phrase(stockOut.getStockRequestDate(), dataContentFont));
		headerTable.addCell(headerCell);
		document.add(headerTable);
		
		//separate_mode
		
		if(combine_mode==0)
		{
		
		PdfPTable listTable = new PdfPTable(4);
		listTable.setWidthPercentage(100.0f);
		listTable.setWidths(new float[] {1.475f,0.65f,0.4f,0.825f});
		listTable.setSpacingBefore(0);
		PdfPCell listCell = new PdfPCell();
		listCell.setPadding(6.5f);
		listCell.setBackgroundColor(new BaseColor(255, 255, 255));
		listTable.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
		listTable.getDefaultCell().setBorderWidthBottom(1f);
		
		setCellBorder(.5f,.5f,.5f,.5f,listCell);
		listCell.setPhrase(new Phrase("Particulars", font));
		listTable.addCell(listCell);
		
		setCellBorder(.5f,.5f,.5f,0f,listCell);
		listCell.setPhrase(new Phrase("Quantity", font));
		listTable.addCell(listCell);
		
		setCellBorder(.5f,.5f,.5f,0f,listCell);
		listCell.setPhrase(new Phrase("Rate", font));
		listTable.addCell(listCell);
		
		setCellBorder(.5f,.5f,.5f,0f,listCell);
		listCell.setPhrase(new Phrase("Amount", font));
		listTable.addCell(listCell);
		
		listTable.setHeaderRows(1);
		listTable.setSkipFirstHeader(false);
		
		setCellBorder(0f,.5f,.5f,.5f,listCell);
		int count=0;
		for (StockOutDetail stk_dtl : stockOut.getStockOutDetails()) {
			count=count+1;
			
			listCell.setPhrase(new Phrase(stk_dtl.getStockItemName(), dataContentFont));
			listCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			listTable.addCell(listCell);
			setCellBorder(0f,.5f,.5f,0f,listCell);
			listCell.setPhrase(new Phrase(getNumberWithDecimal(stk_dtl.getDeliveredQty()), dataContentFont));
			listCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			listTable.addCell(listCell);
			listCell.setPhrase(new Phrase(getNumberWithDecimal(stk_dtl.getUnitPrice()), dataContentFont));
			listCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			listTable.addCell(listCell);
			listCell.setPhrase(new Phrase(getNumberWithDecimal(stk_dtl.getAmount()), dataContentFont));
			listCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			listTable.addCell(listCell);
			
			setCellBorder(0f,.5f,.5f,.5f,listCell);
		}
		
		if(count<11){
			for(int i=count;i<=10;i++){
				
				setCellBorder(0f,.5f,.5f,.5f,listCell);
				listCell.setPhrase(new Phrase(" "));
				listTable.addCell(listCell);
				setCellBorder(0f,.5f,.5f,0f,listCell);
				listCell.setPhrase(new Phrase(" "));
				listTable.addCell(listCell);
				listCell.setPhrase(new Phrase(" "));
				listTable.addCell(listCell);
				listCell.setPhrase(new Phrase(" "));
				listTable.addCell(listCell);
				
			}
		}

		setCellBorder(0f,.5f,.5f,.5f,listCell);
		listCell.setColspan(2);
		listCell.setPhrase(new Phrase("Total ("+currency+"):", font));
		listCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		listTable.addCell(listCell);

		setCellBorder(0f,.5f,.5f,0f,listCell);
		listCell.setPhrase(new Phrase(getNumberWithDecimal(stockOut.getTotal_amount()), font));

		listCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		listTable.addCell(listCell);
		document.add(listTable);
		
		}
		else
		{

			
			PdfPTable listTable = new PdfPTable(3);
			listTable.setWidthPercentage(100.0f);
			listTable.setWidths(new float[] {1.5f,1.5f,1.5f});
			listTable.setSpacingBefore(0);
			PdfPCell listCell = new PdfPCell();
			listCell.setPadding(6.5f);
			listCell.setBackgroundColor(new BaseColor(255, 255, 255));
			listTable.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
			listTable.getDefaultCell().setBorderWidthBottom(1f);
			
			setCellBorder(.5f,.5f,.5f,.5f,listCell);
			listCell.setPhrase(new Phrase("Particulars", font));
			listTable.addCell(listCell);
			
			setCellBorder(.5f,.5f,.5f,0f,listCell);
			listCell.setPhrase(new Phrase("Requested Qty", font));
			listTable.addCell(listCell);
			
			setCellBorder(.5f,.5f,.5f,0f,listCell);
			listCell.setPhrase(new Phrase("Delivered Qty", font));
			listTable.addCell(listCell);
			
			
			
			listTable.setHeaderRows(1);
			listTable.setSkipFirstHeader(false);
			
			setCellBorder(0f,.5f,.5f,.5f,listCell);
			int count=0;
			for (StockOutDetail stk_dtl : stockOut.getStockOutDetails()) {
				count=count+1;
				
				listCell.setPhrase(new Phrase(stk_dtl.getStockItemName(), dataContentFont));
				listCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				listTable.addCell(listCell);
				setCellBorder(0f,.5f,.5f,0f,listCell);
				listCell.setPhrase(new Phrase(getNumberWithDecimal(stk_dtl.getRequestQty()), dataContentFont));
				listCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				listTable.addCell(listCell);
				listCell.setPhrase(new Phrase(getNumberWithDecimal(stk_dtl.getDeliveredQty()), dataContentFont));
				listCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				listTable.addCell(listCell);
				
				setCellBorder(0f,.5f,.5f,.5f,listCell);
			}
			
			if(count<11){
				for(int i=count;i<=10;i++){
					
					setCellBorder(0f,.5f,.5f,.5f,listCell);
					listCell.setPhrase(new Phrase(" "));
					listTable.addCell(listCell);
					setCellBorder(0f,.5f,.5f,0f,listCell);
					listCell.setPhrase(new Phrase(" "));
					listTable.addCell(listCell);
					listCell.setPhrase(new Phrase(" "));
					listTable.addCell(listCell);
					
					
				}
			}

		document.add(listTable);
			
			
			
		}
		
		PdfPTable footerTable = new PdfPTable(1);
		footerTable.setWidthPercentage(100.0f);
		footerTable.setWidths(new float[] {2f});
		footerTable.setSpacingBefore(0);
		PdfPCell footerCell = new PdfPCell();
		footerCell.setPadding(7);
		footerCell.setBackgroundColor(new BaseColor(255, 255, 255));
		footerTable.getDefaultCell().setBorderColorBottom(new BaseColor(0, 168, 179));
		footerTable.getDefaultCell().setBorderWidthBottom(1f);
		setCellBorder(0f,0f,0f,0f,footerCell);
		footerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		footerCell.setPhrase(new Phrase("For "+companyName,dataContentFont));
		footerTable.addCell(footerCell);
		footerCell.setPaddingTop(35);
		setCellBorder(0f,0f,0f,0f,footerCell);
		footerCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		footerCell.setPhrase(new Phrase("Authroized Sign",dataContentFont));
		footerTable.addCell(footerCell);

		document.add(footerTable);
		
		final Font dublicateCopyFont = new Font(Font.getFamily("TIMES_ROMAN"),9,Font.NORMAL);
		if(stockOut.getReqStatus()==4){
			ColumnText.showTextAligned(writer.getDirectContent(),
					Element.ALIGN_LEFT, new Phrase("Duplicate Copy",dublicateCopyFont),
					13,8,0); 
		}
		
	}
	
	/**
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 * @param listCell
	 */
	public void setCellBorder(float top,float right,float bottom,float left,PdfPCell listCell){
		listCell.setBorderWidthTop(top);
		listCell.setBorderWidthRight(right);
		listCell.setBorderWidthBottom(bottom);
		listCell.setBorderWidthLeft(left);
	}
	
	/**
	 * @param value
	 * @return
	 */
	public String getNumberWithDecimal(Double value) {
		value = ((!value.equals("") && !value.equals("")) ? value : 0.00);
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		
		return bd.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.report.common.AbstractITextPdfView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
			// IE workaround: write into byte array first.
			ByteArrayOutputStream baos = createTemporaryOutputStream();

			// Apply preferences and build metadata.
			Document document = newDocument();
			PdfWriter writer = newWriter(document, baos);
			Report  report =   (Report) model.get("reportName");
			companyName = report.getCompanyName();
			decimalPlace = report.getDecimalPlace();
			dateFormat = report.getDateFormat();
			currency = report.getCurrency();
			combine_mode=report.getCombine_mode();

			Invoice event = new Invoice();
			event.setCompanyName(report.getCompanyName());
			event.setHeader(report.getReportName());
			event.setReportType(report.getReportType());
			event.setCurrName(report.getCurrUserName());
			event.setDateFormat(report.getDateFormat());
			event.setDecimalPlace(report.getDecimalPlace());
			event.setCurrency(report.getCurrency());
			writer.setPageEvent(event);
	        
			prepareWriter(model, writer, request);
			buildPdfMetadata(model, document, request);

			// Build PDF document.
			document.open();
			buildPdfDocument(model, document, writer, request, response);
			document.close();

			// Flush to HTTP response.
			writeToResponse(response, baos);
	}
	
	

}
