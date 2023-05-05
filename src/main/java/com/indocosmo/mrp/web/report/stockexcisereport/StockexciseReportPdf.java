package com.indocosmo.mrp.web.report.stockexcisereport;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockexcisereport.model.StockExciseReport;
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
 *  Done by anandu on 21-01-2020 
 *  
 */

public class StockexciseReportPdf extends AbstractITextPdfView {

	String companyName;

	String dateFormat;

	Integer decimalPlace;

	String currency;


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.report.common.AbstractITextPdfView#newDocument(java.util.Map)
	 * 
	 */
	public Document newDocument(Map<String, Object> model) {

		StockExciseReport stock = (StockExciseReport) model.get("exciseInvoice");

		if (stock.getOption() == 1) {
			return new Document(PageSize.A4, 36, 36, 120, 30);
		}

		else {
			return new Document(PageSize.A4, 36, 36, 120, 30);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.report.common.AbstractITextPdfView#buildPdfDocument(
	 * java.util.Map, com.itextpdf.text.Document, com.itextpdf.text.pdf.PdfWriter,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 */
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Report report = (Report) model.get("reportName");
		companyName = report.getCompanyName();
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		currency = report.getCurrency();

		StockExciseReport stock = (StockExciseReport) model.get("exciseInvoice");

		Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Font fontContent = FontFactory.getFont(FontFactory.HELVETICA, 9);
		Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
		final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 11, Font.BOLD);
		Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 8);


		String Header1 = "";

		if (stock.getOption() == 1) {

			Header1 = "BETWEEN  " + stock.getStartdate() + "  AND  " + stock.getEnddate();
		}
		
		Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        String currDate= formatter.format(date);
        
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD);	
		PdfPTable table25 = new PdfPTable(2);
		table25.setWidths(new float[] { 2.8f,1.2f });
		table25.setWidthPercentage(100.0f);
		table25.setSpacingBefore(2);
		table25.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPCell cell25 = new PdfPCell();
		cell25.setBorder(Rectangle.NO_BORDER);
		cell25.setPhrase(new Phrase(Header1, SUBFONT));
		cell25.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table25.addCell(cell25);
		
		cell25 = new PdfPCell(new Phrase(currDate, dateFont));
		cell25.setBorder(Rectangle.NO_BORDER);
		cell25.setHorizontalAlignment(Element.ALIGN_RIGHT);
		//cell25.setRowspan(2);
		table25.addCell(cell25);
		doc.add(table25);
		if(stock.getStockExcise().isEmpty()) {
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
			cell89.setPhrase(new Phrase("No Items Available ", SUBFONT));
			cell89.setHorizontalAlignment(Element.ALIGN_CENTER);
			table89.addCell(cell89);
			doc.add(table89);
		}
		else {
	

		PdfPTable table;
		table = new PdfPTable(12);
		table.setWidths(new float[] { 2.5f,1.5f, 1.5f, 1.7f, 1.5f, 1.7f, 1.5f, 1.7f, 1.5f, 1.7f, 1.5f, 1.7f });

		table.setWidthPercentage(100.0f);
		//table.addCell(cell25);
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(new BaseColor(255, 255, 255));
		cell.setPadding(15);
		table.setSpacingBefore(10);

		table.getDefaultCell().setPaddingBottom(15.0f);
		table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
		table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table.getDefaultCell().setBorderWidthBottom(3.0f);
		
		cell = new PdfPCell(new Phrase("Name", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Btl Size (ml)", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Opening Stock", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Purchase", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Total", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Sales", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Closing Stock", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Btl", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Ltr", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Btl", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Ltr", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Btl", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Ltr", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Btl", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Ltr", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Btl", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Ltr", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		
		int temp1=0, temp2=0, temp3=0, temp4=0, temp5=0, temp6=0, temp7=0, temp8=0, temp9=0, temp10=0;
		BigDecimal sum, sum1, sum2, sum3, sum4, sum5, sum6, sum7, sum8, sum9;
		sum  = BigDecimal.valueOf(temp1);
		sum1  = BigDecimal.valueOf(temp2); 
		sum2  = BigDecimal.valueOf(temp3); 
		sum3  = BigDecimal.valueOf(temp4); 
		sum4  = BigDecimal.valueOf(temp5); 
		sum5  = BigDecimal.valueOf(temp6);
		sum6  = BigDecimal.valueOf(temp7);
		sum7  = BigDecimal.valueOf(temp8);
		sum8  = BigDecimal.valueOf(temp9);
		sum9  = BigDecimal.valueOf(temp10);
		
		for (StockExciseReport stkreg : stock.getStockExcise()) {

			BigDecimal btlDecimal = new BigDecimal(stkreg.getBtlSize());
			cell.setPhrase(new Phrase(stkreg.getStockitemName(), font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			if(stkreg.getStockitemName().trim().equalsIgnoreCase("LIQUOR")){
				cell.setPhrase(new Phrase("N/A", font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				BigDecimal opening = new BigDecimal(stkreg.getOpening());
				opening = opening.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(opening.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal openingUnit = new BigDecimal(stkreg.getOpening_unit());
				openingUnit = openingUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(openingUnit.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal InQty = new BigDecimal(stkreg.getInQty());
				InQty = InQty.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(InQty.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal InQtyUnit = new BigDecimal(stkreg.getInqty_unit());
				InQtyUnit = InQtyUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(InQtyUnit.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal total;
				total = InQty.add(opening);
				total = total.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(total.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal totalUnit;
				totalUnit = InQtyUnit.add(openingUnit);
				totalUnit = totalUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(totalUnit.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal OutQty = new BigDecimal(stkreg.getOutQty());
				OutQty = OutQty.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(OutQty.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal OutQtyUnit = new BigDecimal(stkreg.getOutqty_unit());
				OutQtyUnit = OutQtyUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(OutQtyUnit.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal CLStock;
				CLStock = total.subtract(OutQty);
				CLStock = CLStock.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(CLStock.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal CLStockUnit;
				CLStockUnit = totalUnit.subtract(OutQtyUnit);
				CLStockUnit = CLStockUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(CLStockUnit.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				
				
				table.addCell(cell);
				
				sum= sum.add(opening);
				sum1 = sum1.add(openingUnit);
				sum2= sum2.add(InQty);
				sum3= sum3.add(InQtyUnit);
				sum4= sum4.add(total);
				sum5= sum5.add(totalUnit);
				sum6= sum6.add(OutQty);
				sum7= sum7.add(OutQtyUnit);
				sum8= sum8.add(CLStock);
				sum9= sum9.add(CLStockUnit);
				
			}else{
				String string = String.valueOf(Double.parseDouble(stkreg.getBtlSize().trim())*1000);
				String[] parts = string.split("\\.");
				String part1 = parts[0]; // 004
				cell.setPhrase(new Phrase(stkreg.getBtlSize()!=null?part1:"", font));
				//cell.setPhrase(new Phrase((String.valueOf(Float.parseFloat(stkreg.getBtlSize().trim())*1000.00))!=null?String.valueOf(Float.parseFloat(stkreg.getBtlSize().trim())*1000.00):"0", font));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				BigDecimal opening = new BigDecimal(stkreg.getOpening());
				opening = opening.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(opening.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal openingUnit = new BigDecimal(stkreg.getOpening_unit());
				openingUnit = openingUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(String.valueOf((opening.multiply(btlDecimal)).setScale(2, BigDecimal.ROUND_DOWN)), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal InQty = new BigDecimal(stkreg.getInQty());
				InQty = InQty.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(InQty.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal InQtyUnit = new BigDecimal(stkreg.getInqty_unit());
				InQtyUnit = InQtyUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(String.valueOf((InQty.multiply(btlDecimal)).setScale(2, BigDecimal.ROUND_DOWN)), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal total;
				total = InQty.add(opening);
				total = total.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(total.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal totalUnit;
				totalUnit = InQtyUnit.add(openingUnit);
				totalUnit = totalUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(String.valueOf((total.multiply(btlDecimal)).setScale(3, BigDecimal.ROUND_DOWN)), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal OutQty = new BigDecimal(stkreg.getOutQty());
				
				
				BigDecimal ml=new BigDecimal(part1);
				BigDecimal totaLtr=total.multiply(ml);
				BigDecimal remLtr=totaLtr.subtract(OutQty.multiply(new BigDecimal("1000")));
				BigDecimal remBtl=remLtr.divide(ml,MathContext.DECIMAL128);
				System.out.println("ml========="+ml);
				System.out.println("totaLtr========="+totaLtr);
				System.out.println("remLtr========="+remLtr);
				System.out.println("remBtl========="+remBtl);
				OutQty=total.subtract(remBtl);
				OutQty = OutQty.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(OutQty.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal OutQtyUnit = new BigDecimal(stkreg.getOutQty());
				OutQtyUnit = OutQtyUnit.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(OutQtyUnit.toString(), fontContent));
			//	cell.setPhrase(new Phrase(String.valueOf((OutQty.multiply(btlDecimal)).setScale(2, BigDecimal.ROUND_DOWN)), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal CLStock;
				CLStock = total.subtract(OutQty);
				
				
				CLStock = CLStock.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(CLStock.toString(), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				BigDecimal CLStockUnit;
				
				CLStockUnit = (total.multiply(btlDecimal)).subtract(OutQtyUnit);
				CLStockUnit = CLStockUnit.setScale(3, BigDecimal.ROUND_DOWN);
				//cell.setPhrase(new Phrase(String.valueOf((OutQty.multiply(btlDecimal)).setScale(2, BigDecimal.ROUND_DOWN)), fontContent));
				cell.setPhrase(new Phrase(String.valueOf(CLStockUnit), fontContent));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
			}
			

			
			
			
			
		
			
		}
	
				
				cell.setPhrase(new Phrase("Total", fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

			
				sum = sum.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(sum.toString(), fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				
				sum1 = sum1.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(sum1.toString(), fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				
				sum2 = sum2.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(sum2.toString(), fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				
				sum3 = sum3.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(sum3.toString(), fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				
				sum4 = sum4.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(sum4.toString(), fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				
				sum5 = sum5.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(sum5.toString(), fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				
				sum6 = sum6.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(sum6.toString(), fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				
				sum7 = sum7.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(sum7.toString(), fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				
				sum8 = sum8.setScale(2, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(sum8.toString(), fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);

				
				sum9 = sum9.setScale(3, BigDecimal.ROUND_DOWN);
				cell.setPhrase(new Phrase(sum9.toString(), fontTotal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				
	
		

		doc.add(table);

	  }
	}
}