package com.indocosmo.mrp.web.report.common;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.indocosmo.mrp.web.report.common.model.Report;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This class is a work around for working with iText 5.x in Spring.
 * The code here is almost identical to the AbstractPdfView class.
 *
 */
public abstract class AbstractITextPdfView extends AbstractView {
	public AbstractITextPdfView() {
		setContentType("application/pdf");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		// Apply preferences and build metadata.
		
		Report  report =   (Report) model.get("reportName");

		
		
		
		InvoiceFooter event = new InvoiceFooter();
		event.setHeader(report.getReportName());
		event.setReportType(report.getReportType());
		event.setCurrName(report.getCurrUserName());
		event.setCompanyName(report.getCompanyName());
		event.setCompanyAddress(report.getCompanyAddress());
		event.setDateFormat(report.getDateFormat());
		
		Document document = null;

	if(report.getDocType()!=null){	
	 document = newDocument(model);
	}else{
		document = newDocument();
		
	}

		
		PdfWriter writer = newWriter(document, baos);
	


		
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

	
/*	public Document newDocument() {
		return new Document(PageSize.A4,36, 36, 150, 80);
	}*/
	
	
	
	public Document newDocument(Map<String, Object> model) {
		return new Document(PageSize.A4.rotate(),36, 36, 50, 30);
	}
	
	public Document newDocument() {
		return new Document(PageSize.A4.rotate(),36, 36, 50, 30);
	}
	
	protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
		return PdfWriter.getInstance(document, os);
	}

	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
			throws DocumentException {

		writer.setViewerPreferences(getViewerPreferences());
	}

	protected int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}

	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
	}

	protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

}