package com.indocosmo.mrp.web.report.common;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;


public class InvoiceFooter extends PdfPageEventHelper {
	PdfTemplate total;
	String header;
	Integer ReportType;
	Integer count=0;
	String CurrName;
	String CompanyName;
	String companyAddress;
	String DateFormat;


	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		total=writer.getDirectContent().createTemplate(30,16);
	}


	public void setCompanyName(String CompanyName){
		this.CompanyName=CompanyName;
	}

	public void setCompanyAddress(String companyAddress){
		this.companyAddress = companyAddress;
	}
	
	public void setCurrName(String CurrName){
		this.CurrName=CurrName;
	}
	public void setDateFormat(String DateFormat){
		this.DateFormat=DateFormat;
	}

	public void setHeader(String header){
		this.header=header;
	}

	public void setReportType(Integer ReportType){
		this.ReportType=ReportType;
	}

	@Override
	public void onStartPage(PdfWriter writer, Document document) {

		final Font HEADFONT = new Font(Font.getFamily("TIMES_ROMAN"), 14, Font.BOLD);
		final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 10, Font.NORMAL);
		//final Font USUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 10, Font.BOLD);

		if(count==0){
			count=count+1;
//			Phrase p = new Phrase(header,SUBFONT);
//			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, p,
//					(document.right() - document.left()) / 2 + document.leftMargin(),document.top() + 40, 0);
			
			Phrase headPhrase = new Phrase(CompanyName,SUBFONT);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, headPhrase,
					(document.right() - document.left()) / 2 + document.leftMargin(),document.top() + 70, 0);
			
			String[] parts = companyAddress.split("\\r\\n");
			int yfloat = 60;
			for (int k = 0; k < parts.length; k++) {
			    Phrase addressPhrase = new Phrase("", SUBFONT);
			    addressPhrase.add(parts[k]);
			    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, addressPhrase,
						(document.right() - document.left()) / 2 + document.leftMargin(),document.top()+yfloat , 0);
			    yfloat -= 10;
			}
			
			Phrase p = new Phrase(header,HEADFONT);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, p,
					(document.right() - document.left()) / 2 + document.leftMargin(),document.top() + 5, 0);
		}

		DateFormat dateFormat = new SimpleDateFormat(DateFormat);
		
		Phrase p1 = new Phrase("Printed On: "+dateFormat.format(new Date()),SUBFONT);
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, p1,
				document.right() -105,
				document.bottom() - 20, 0);
	}


	@Override
	public void onEndPage(PdfWriter writer,Document document) {
		PdfPTable table =new PdfPTable(7);
		try{table.setWidths(new int []{12,10,6,8,8,12,2});
		if(ReportType==1){
			table.setTotalWidth(800);



		}else if(ReportType==0){
			table.setTotalWidth(542);
		}else if(ReportType==2){
			table.setTotalWidth(360);}	

		table.setWidthPercentage(100.0f);
		table.setLockedWidth(true);
		table.getDefaultCell().setFixedHeight(20);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		PdfPCell cell1 =new PdfPCell();
		/*if(ReportType==1){
			cell1.setPhrase(new Phrase(CompanyName));
		}else if(ReportType==0 || ReportType==2 )
		{*/cell1.setPhrase(new Phrase(" "));/*}*/

		cell1.setBorder(Rectangle.NO_BORDER);
		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell1);

		/*
		if(ReportType==1){cell1.setPhrase(new Phrase("Printed On:"));

		}else if(ReportType==0 || ReportType==2 )

//		{*/cell1.setPhrase(new Phrase(" "));/*}*/

cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
cell1.setBorder(Rectangle.NO_BORDER);
table.addCell(cell1);
DateFormat dateFormat = new SimpleDateFormat(DateFormat);


/*	if(ReportType==1){cell1.setPhrase(new Phrase(dateFormat.format(new Date())));
		}else if(ReportType==0 || ReportType==2 ){*/cell1.setPhrase(new Phrase(" "));/*}*/

		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell1.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell1);


		/*	if(ReportType==1){
			cell1.setPhrase(new Phrase("By:"));
		}else if(ReportType==0 || ReportType==2 ){*/cell1.setPhrase(new Phrase(" "));/*}      
		 */
		cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell1.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell1);


		/*	if(ReportType==1){
			cell1.setPhrase(new Phrase(CurrName));
		}else if(ReportType==0 || ReportType==2 ){*/cell1.setPhrase(new Phrase(" "));/*} */ 





		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell1.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell1);

		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(String.format("Page %d of", writer.getPageNumber()));    		
		PdfPCell cell =new PdfPCell(Image.getInstance(total));
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);

		if(ReportType==1){
			table.writeSelectedRows(0, -1, document.rightMargin()-10, 580, writer.getDirectContent());


		}else if(ReportType==0 || ReportType==2 )

		{
			table.writeSelectedRows(0, -1, document.rightMargin()-10, 820, writer.getDirectContent());
		}



		}catch(DocumentException de){
			throw new ExceptionConverter(de);
		}
	}
	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {

		ColumnText.showTextAligned(total,
				Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber())),
				2,2,0); 
	}

}