package com.indocosmo.mrp.web.report.stockinreport;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockinreport.model.StockinReportModel;
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

public class StockinReportSummary extends AbstractITextPdfView {
	String companyName;
	String dateFormat;
	Integer decimalPlace;
	String currency;


	public String getNumberWithDecimal(Double value) 
	{
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}
	public String getRateWithDecimal(Double value) {
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		return bd.toString();
	}

	@Override
	public Document newDocument() {
		return new Document(PageSize.A4,36, 36, 120, 80);
	}
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		StockinReportModel  stockList =   (StockinReportModel) model.get("stockinSummaryView");
		Report  report =   (Report) model.get("reportName");
		companyName = report.getCompanyName();
		decimalPlace = report.getDecimalPlace();
		dateFormat = report.getDateFormat();
		currency = report.getCurrency();

		Font font = FontFactory.getFont(FontFactory.HELVETICA,9);
		Font font1=FontFactory.getFont(FontFactory.HELVETICA,11,Font.BOLD);

		int count=0;
		final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 11,Font.BOLD);
		String Header1="BETWEEN  "+stockList.getStartdate()+"  AND  "+stockList.getEnddate();

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


		int msg=1;
		String grno="";
		String stockItmName="";
		if(!stockList.getPurchasreportData().isEmpty())
		{
			grno=stockList.getPurchasreportData().get(0).getGrn_no();
		}
		else{
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
			msg=0;
		}	

		if(stockList.getOption()==1)
		{
			Double total=0.0;
			Double grand_total=0.0;
			int i=0;
			int f=0;

			if(!stockList.getPurchasreportData().isEmpty())
			{
				stockItmName=stockList.getPurchasreportData().get(0).getItm_cat_name();
			}
			for (StockinReportModel stockindtl : stockList.getPurchasreportData()) {

				grand_total +=stockindtl.getAmount();

				PdfPTable table12 = new PdfPTable(2);
				table12.setWidths(new float[] {2.6f,.5f});
				table12.setWidthPercentage(100.0f);
				table12.setSpacingBefore(0);

				if(!(stockindtl.getItm_cat_name().matches(stockItmName)))
				{
					f=0;
					stockItmName=stockindtl.getItm_cat_name();


				}
				PdfPTable table9 = new PdfPTable(6);
				table9.setWidths(new float[] {.8f,2.5f,.8f,1.3f,1.3f,1.3f});
				table9.setWidthPercentage(100.0f);
				table9.setSpacingBefore(0);

				PdfPCell cell9 = new PdfPCell();
				cell9.setBackgroundColor(new BaseColor(255, 255, 255));
				cell9.setPadding(5);
				table9.getDefaultCell().setPaddingBottom(10.0f);
				table9.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
				table9.getDefaultCell().setBorder(Rectangle.RECTANGLE);
				table9.getDefaultCell().setBorderWidthBottom(2.0f);
				//span column
				PdfPCell cellspan = new PdfPCell();
				cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan.setPadding(5);
				cellspan.setColspan(6);

				if(!(stockindtl.getGrn_no()).equals(grno))
				{
					i=0;
					grno=stockindtl.getGrn_no();
					PdfPCell cellspan1 = new PdfPCell();
					cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
					cellspan1.setPadding(5);
					//cellspan1.setColspan(5);
					cellspan1.setPhrase(new Phrase("Total:", font1));
					cellspan1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table12.addCell(cellspan1);

					cellspan1.setPhrase(new Phrase(getRateWithDecimal(total), font1));
					cellspan1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table12.addCell(cellspan1);
					doc.add(table12);
				}

				if(stockindtl.getGrn_no()==grno && i==0)
				{i=1;
				count=0;
				total=0.0;

				PdfPTable table1 = new PdfPTable(4);
				table1.setWidths(new float[] {.7f,1.5f,.6f,1.5f});
				table1.setWidthPercentage(100.0f);
				table1.setSpacingBefore(0);

				PdfPCell cell1 = new PdfPCell();
				cell1.setBackgroundColor(new BaseColor(255, 255, 255));
				cell1.setPadding(5);
				cell1.setBorder(Rectangle.NO_BORDER);

				table1.getDefaultCell().setPaddingBottom(10.0f);
				table1.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
				table1.getDefaultCell().setBorder(Rectangle.BOTTOM);
				table1.getDefaultCell().setBorderWidthBottom(2.0f);
				table1.setSpacingBefore(20);

				PdfPTable table = new PdfPTable(6);
				table.setWidths(new float[] {.8f,2.5f,.8f,1.3f,1.3f,1.3f});
				table.setWidthPercentage(100.0f);
				table.setSpacingBefore(0);

				PdfPCell cell = new PdfPCell();

				cell.setBackgroundColor(new BaseColor(255, 255, 255));
				cell.setPadding(5);			
				table.getDefaultCell().setPaddingBottom(10.0f);
				table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
				table.getDefaultCell().setBorder(Rectangle.RECTANGLE);
				cell.setBorderColor(new BaseColor(0, 0, 0));

				cell1.setPhrase(new Phrase("Date:", font1));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase(stockindtl.getReceived_date(), font));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase("GRNO:", font1));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase(stockindtl.getGrn_no(), font));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase("Suppier:", font1));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase(stockindtl.getSupplier_name(), font));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);
				cell1.setPhrase(new Phrase(" ", font1));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);

				/*stockInStatus[] list= stockInStatus.values();
				cell1.setPhrase(new Phrase((list[stockindtl.getStatus()].getStockInStatusName()), font));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell1);*/
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

				cell.setPhrase(new Phrase("Unit", font1));
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

				cell.setPhrase(new Phrase("itm_cat_name", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				doc.add(table);
				f=0;
				}	

				if(!(stockindtl.getItm_cat_name().matches(stockItmName)))
				{
					f=1;
					stockItmName=stockindtl.getItm_cat_name();
				}

				if(stockindtl.getItm_cat_name().matches(stockItmName) && f==0)
				{
					cellspan.setPhrase(new Phrase(stockindtl.getItm_cat_name(), font));
					cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
					table9.addCell(cellspan);
					f=1;
				}
				count=count+1;
				String sino=""+count;
				cell9.setPhrase(new Phrase(sino, font));
				cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
				table9.addCell(cell9);

				cell9.setPhrase(new Phrase(stockindtl.getStock_item_name(), font));
				cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
				table9.addCell(cell9);

				cell9.setPhrase(new Phrase(stockindtl.getUomname(), font));
				cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
				table9.addCell(cell9);

				cell9.setPhrase(new Phrase(getNumberWithDecimal(stockindtl.getReceived_qty()), font));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);

				cell9.setPhrase(new Phrase(getRateWithDecimal(stockindtl.getUnit_price()), font));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);

				cell9.setPhrase(new Phrase(getRateWithDecimal(stockindtl.getAmount()), font));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);

				cell9.setPhrase(new Phrase(stockindtl.getItm_cat_name(), font));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);

				total=total+stockindtl.getAmount();
				doc.add(table9);
			}	

			if(msg!=0)
			{
				PdfPTable table12 = new PdfPTable(2);
				table12.setWidths(new float[] {2.6f,.5f});
				table12.setWidthPercentage(100.0f);
				table12.setSpacingBefore(0);

				PdfPCell cellspan1 = new PdfPCell();
				cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan1.setPadding(5);
				//cellspan1.setColspan(5);
				cellspan1.setPhrase(new Phrase("Total:", font1));
				cellspan1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table12.addCell(cellspan1);

				cellspan1.setPhrase(new Phrase(getRateWithDecimal(total), font1));
				cellspan1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table12.addCell(cellspan1);

				PdfPCell cellspan2 = new PdfPCell();		
				cellspan2.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan2.setPadding(5);
				//cellspan1.setColspan(5);
				cellspan2.setPhrase(new Phrase("Grand Total:", font1));
				cellspan2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table12.addCell(cellspan2);
				cellspan2.setPhrase(new Phrase(getRateWithDecimal(grand_total), font1));
				cellspan2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table12.addCell(cellspan2);
				doc.add(table12);	
			}

		}

		if(stockList.getOption()==0)
		{
			String stockItmId="0";	
			PdfPTable table9 = new PdfPTable(8);
			table9.setWidths(new float[] {.5f,2.2f,.8f,1.2f,1f,1.4f,.8f,1f});
			table9.setWidthPercentage(100.0f);
			table9.setSpacingBefore(2);

			PdfPCell cell9 = new PdfPCell();
			cell9.setBackgroundColor(new BaseColor(255, 255, 255));
			cell9.setPadding(5);
			table9.getDefaultCell().setPaddingBottom(10.0f);
			table9.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
			table9.getDefaultCell().setBorder(Rectangle.RECTANGLE);
			table9.getDefaultCell().setBorderWidthBottom(2.0f);

			cell9.setPhrase(new Phrase("SI:", font1));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.addCell(cell9);

			cell9.setPhrase(new Phrase("Item:", font1));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.addCell(cell9);

			cell9.setPhrase(new Phrase("Unit", font1));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.addCell(cell9);

			cell9.setPhrase(new Phrase("Date", font1));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.addCell(cell9);

			cell9.setPhrase(new Phrase("Ref No", font1));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.addCell(cell9);

			cell9.setPhrase(new Phrase("Supplier", font1));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.addCell(cell9);

			cell9.setPhrase(new Phrase("Stock In", font1));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.addCell(cell9);

			cell9.setPhrase(new Phrase("Value", font1));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.addCell(cell9);

			int f=0;
			Double stoknSum=0.0;
			Double valueSum=0.0;
			Double grandstoknSum=0.0;
			Double grandvalueSum=0.0;

			if(!stockList.getPurchasreportData().isEmpty())
			{
				stockItmId=stockList.getPurchasreportData().get(0).getStock_item_id();
			}
			int sino=0;
			for (StockinReportModel stockindtl : stockList.getPurchasreportData()) {			
				grandstoknSum += stockindtl.getReceived_qty();
				grandvalueSum += (stockindtl.getReceived_qty()*stockindtl.getUnit_price());
				if(!(stockindtl.getStock_item_id()==stockItmId))
				{
					f=0;
					stockItmId=stockindtl.getStock_item_id();
					PdfPCell cellspan = new PdfPCell();
					cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
					cellspan.setPadding(5);
					cellspan.setColspan(6);
					cellspan.setPhrase(new Phrase("Total:", font1));
					cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table9.addCell(cellspan);	 

					cell9.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(stoknSum.toString())).toString(), font1));
					cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table9.addCell(cell9);
					cell9.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(valueSum.toString())).toString(), font1));
					cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table9.addCell(cell9);
					stoknSum=0.0;
					valueSum=0.0;
				}
				if(f==1)
				{
					f=1;
					cell9.setPhrase(new Phrase("", font));
					cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
					table9.addCell(cell9);

					cell9.setPhrase(new Phrase("", font));
					cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
					table9.addCell(cell9);

					cell9.setPhrase(new Phrase("", font));
					cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
					table9.addCell(cell9);
				}
				else
				{
					if(f==0)
					{

						sino++;
						String sino1=""+sino;
						cell9.setPhrase(new Phrase(sino1, font));
						cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
						table9.addCell(cell9);

						cell9.setPhrase(new Phrase(stockindtl.getStock_item_name(), font));
						cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
						table9.addCell(cell9);

						cell9.setPhrase(new Phrase(stockindtl.getUomname(), font));
						cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
						table9.addCell(cell9);
						f=1;
					}
				}
				cell9.setPhrase(new Phrase(stockindtl.getReceived_date(), font));
				cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
				table9.addCell(cell9);
				cell9.setPhrase(new Phrase(stockindtl.getGrn_no(), font));
				cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
				table9.addCell(cell9);

				cell9.setPhrase(new Phrase(stockindtl.getSupplier_name(), font));
				cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
				table9.addCell(cell9);

				cell9.setPhrase(new Phrase(getNumberWithDecimal(stockindtl.getReceived_qty()), font));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);

				stoknSum=stoknSum+stockindtl.getReceived_qty();
				cell9.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(stockindtl.getReceived_qty().toString()) * Double.parseDouble(stockindtl.getUnit_price().toString())).toString(), font));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);

				valueSum=valueSum+(stockindtl.getReceived_qty()*stockindtl.getUnit_price());
			}
			if(msg!=0)
			{
				PdfPCell cellspan = new PdfPCell();
				cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan.setPadding(5);
				cellspan.setColspan(6);
				cellspan.setPhrase(new Phrase("Total:", font1));
				cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cellspan);	 

				cell9.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(stoknSum.toString())).toString(), font1));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);
				cell9.setPhrase(new Phrase(getRateWithDecimal(Double.parseDouble(valueSum.toString())).toString(), font1));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);

				/*PdfPCell cellspan1 = new PdfPCell();
				cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan1.setPadding(5);
				cellspan1.setColspan(7);
				cellspan1.setPhrase(new Phrase("Grand Total:", font1));
				cellspan1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cellspan1);	 
		
				cell9.setPhrase(new Phrase(getNumberWithDecimal(grandstoknSum), font1));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);
				cell9.setPhrase(new Phrase(getNumberWithDecimal(grandvalueSum), font1));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);*/

				PdfPCell cellspan1 = new PdfPCell();
				cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan1.setPadding(5);
				cellspan1.setColspan(6);
				cellspan1.setPhrase(new Phrase("Grand Total:", font1));
				cellspan1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cellspan1);	 

				cell9.setPhrase(new Phrase(getNumberWithDecimal(grandstoknSum), font1));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);
				cell9.setPhrase(new Phrase(getRateWithDecimal(grandvalueSum), font1));
				cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table9.addCell(cell9);
				doc.add(table9);
			}
		}
}
}
