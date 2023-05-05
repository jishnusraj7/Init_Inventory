package com.indocosmo.mrp.web.report.nonmoving;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.nonmoving.model.NonMovingStock;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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

public class NonMovingReportbuilder extends AbstractITextPdfView {

	Integer decimalPlace;
	
	public String getNumberWithDecimal(Double value) {
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}
	
	@Override
	public Document newDocument() {
		return new Document(PageSize.A4,36, 36, 120, 80);
	}
	
	String dateFormat;
	
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		NonMovingStock itemstock =   (NonMovingStock) model.get("listcurrentStock");
		Report  report =   (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
		int count=0;
		dateFormat = report.getDateFormat();
		Font font1=FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD);
		//Font font3=FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD);

		HttpSession session=request.getSession(false);
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 12,Font.BOLD);
		String enddate =itemstock.getEnddate();

		DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
		fromFormat.setLenient(false);
		DateFormat toFormat = new SimpleDateFormat(dateFormat);
		toFormat.setLenient(false);
		Date date = fromFormat.parse(enddate);
		System.out.println(toFormat.format(date));
		String enddate2=toFormat.format(date);

		String Header1="WITHIN "+itemstock.getDays()+" DAYS AS ON  "+enddate2;

		PdfPTable table25 = new PdfPTable(1);
		table25.setWidthPercentage(100.0f);
		table25.setSpacingBefore(2);
		table25.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPCell cell25 = new PdfPCell();
		cell25.setBorder(Rectangle.NO_BORDER);
		cell25.setPhrase(new Phrase(Header1,SUBFONT));

		cell25.setHorizontalAlignment(Element.ALIGN_CENTER);

		table25.addCell(cell25);
		doc.add(table25);

		if(itemstock.getItem_category_id() != 0)
		{
			PdfPTable table18 = new PdfPTable(1);
			table18.setWidthPercentage(100.0f);

			PdfPCell cell18 = new PdfPCell();
			cell18.setBackgroundColor(new BaseColor(255, 255, 255));
			cell18.setPadding(5);
			table18.getDefaultCell().setPaddingBottom(10.0f);
			table18.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			table18.getDefaultCell().setBorder(Rectangle.BOTTOM);
			table18.getDefaultCell().setBorderWidthBottom(2.0f);
			/*cell18.setPhrase(new Phrase("ITEM CATEGORY :", font1));
			cell18.setHorizontalAlignment(Element.ALIGN_LEFT);
			table18.addCell(cell18);
			cell18.setPhrase(new Phrase(itemstock.getCategoryName(), font3));
			cell18.setHorizontalAlignment(Element.ALIGN_LEFT);
			//table18.addCell(cell18);*/	
			Phrase phrase = new Phrase();
			phrase.add( new Chunk("ITEM CATEGORY :", FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD)));
			phrase.add(new Chunk(itemstock.getCategoryName(),FontFactory.getFont(FontFactory.HELVETICA,10)));
			cell18.setPhrase(phrase);
			table18.addCell(phrase);
			doc.add(table18);
		}

		PdfPTable table23 = new PdfPTable(1);	
		table23.setWidthPercentage(100.0f);	

		PdfPCell cell23 = new PdfPCell();
		cell23.setBackgroundColor(new BaseColor(255, 255, 255));
		cell23.setPadding(5);
		table23.getDefaultCell().setPaddingBottom(10.0f);
		table23.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
		table23.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table23.getDefaultCell().setBorderWidthBottom(2.0f);

		cell23.setPhrase(new Phrase(itemstock.getDepartment_name(), font1));
		cell23.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell23.setBorderColorBottom(new BaseColor(255, 255, 255));
		cell23.setBorder(Rectangle.NO_BORDER);
		table23.addCell(cell23);

		doc.add(table23);
		//if(itemstock.getItemstockReportType()){
		PdfPTable table = new PdfPTable(4);
		table.setWidths(new float[] {.8f,1f,3f,2f});
		table.setWidthPercentage(100.0f);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(new BaseColor(255, 255, 255));
		cell.setPadding(5);
		table.getDefaultCell().setPaddingBottom(10.0f);
		table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
		table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table.getDefaultCell().setBorderWidthBottom(2.0f);
		cell.setPhrase(new Phrase("SI#", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("ITEM CODE", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("ITEM NAME", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("CURRENT STOCK", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		for (NonMovingStock itemstock1 : itemstock.getItemstock()) 
		{
			count=count+1;
			String sino=""+count;
			cell.setPhrase(new Phrase(sino, font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell.setPhrase(new Phrase(itemstock1.getCode(), font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			cell.setPhrase(new Phrase(itemstock1.getName().toString(), font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			//String curr_stock=itemstock1.getCurrent_stock();
			Double currentStock=round(itemstock1.getCurrent_stock(),systemSettings.getDecimal_places());
			cell.setPhrase(new Phrase(getNumberWithDecimal(currentStock), font));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

		}
		if(count<11)
		{
			for(int i=count;i<=11;i++){
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

		doc.add(table);
	}
	
	public double round(double value, int places) {
		
	    if (places < 0) throw new IllegalArgumentException();
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
