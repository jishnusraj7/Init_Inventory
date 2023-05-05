package com.indocosmo.mrp.web.report.currentstock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.indocosmo.mrp.web.report.common.AbstractITextPdfView;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
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

public class currentStockReportbuilder extends AbstractITextPdfView {
	Integer decimalPlace;
	
	@Override
	public Document newDocument() {
		return new Document(PageSize.A4,36, 36, 120, 80);
	}
	
	public String getNumberWithDecimal(Double value) {
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, 
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		ItemStock  itemstock =   (ItemStock) model.get("listcurrentStock");
		Report report = (Report) model.get("reportName");
		decimalPlace = report.getDecimalPlace();

		Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Font font1=FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD);	

		PdfPTable table23 = new PdfPTable(1);
		table23.setWidthPercentage(100.0f);
		List<ItemStock> itemstockini2 = itemstock.getItemstock();
		if(itemstockini2.size()!=0)
		{
			if(itemstock.getItemstockReportType())
			{
				String  depId="";
				String depat_name="";
				List<ItemStock> itemstockini = itemstock.getItemstock();
				depId=itemstockini.get(0).getDepartment_id();
				//ItemStock itmInt=itemstockini.get(0);
				depat_name=itemstockini.get(0).getDepartment_name();

				PdfPTable table = new PdfPTable(4);
				table.setWidths(new float[] { .8f, 3f, 1f, 1f });
				table.setWidthPercentage(100.0f);

				PdfPCell cellspan = new PdfPCell();
				cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
				cellspan.setPadding(6);
				cellspan.setColspan(4);

				cellspan.setPhrase(new Phrase(depat_name,font));
				cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cellspan);

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
				/*cell.setPhrase(new Phrase("ITEM CODE", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);*/
				cell.setPhrase(new Phrase("ITEM NAME", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				/*
				 * 
				 *  Done by anandu on 30-01-2020
				 *   
				 */

				cell.setPhrase(new Phrase("UNIT", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell.setPhrase(new Phrase("CURRENT STOCK", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				String sino="";
				int count=0;
				//int toatalcnt=0;
				//ArrayList<ItemStock> sortList = new ArrayList<ItemStock>();
				for (ItemStock itemstock1 : itemstock.getItemstock()) {

					if(!((itemstock1.getDepartment_id()).equals(depId))){

						depat_name=itemstock1.getDepartment_name();
						PdfPCell cellspan1 = new PdfPCell();
						cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
						cellspan1.setPadding(6);
						cellspan1.setColspan(4);

						cellspan1.setPhrase(new Phrase(depat_name,font));
						cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cellspan1);

						cell.setPhrase(new Phrase("SI#", font1));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);

						/*cell.setPhrase(new Phrase("ITEM CODE2222", font1));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);*/

						cell.setPhrase(new Phrase("ITEM NAME", font1));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);

						cell.setPhrase(new Phrase("UNIT", font1));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);

						cell.setPhrase(new Phrase("CURRENT STOCK", font1));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);

						count=0;
						depId=itemstock1.getDepartment_id();
					}

					count=count+1;
					sino=""+count;

					cell.setPhrase(new Phrase(sino, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					/*cell.setPhrase(new Phrase(itemstock1.getCode(), font));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);*/

					cell.setPhrase(new Phrase(itemstock1.getName().toString(), font));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					String unit = itemstock1.getUnit();

					cell.setPhrase(new Phrase(unit, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					String curr_stock=itemstock1.getCurrent_stock();
					if(curr_stock==null){
						curr_stock="0";
					}
					cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(curr_stock)), font));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);

				}
				/*if(toatalcnt<11){
					for(int i=toatalcnt;i<=11;i++){
						cell.setPhrase(new Phrase(" "));
						table.addCell(cell);
						cell.setPhrase(new Phrase(" "));
						table.addCell(cell);
						cell.setPhrase(new Phrase(" "));
						table.addCell(cell);
						cell.setPhrase(new Phrase(" "));
						table.addCell(cell);
					}
				} */		
				doc.add(table);
			}
			else{

				String  depId="";
				String depat_name="";
				PdfPCell cell23 = new PdfPCell();
				cell23.setBackgroundColor(new BaseColor(255, 255, 255));
				cell23.setPadding(5);
				table23.getDefaultCell().setPaddingBottom(10.0f);
				table23.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
				table23.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table23.getDefaultCell().setBorderWidthBottom(2.0f);

				cell23.setPhrase(new Phrase(itemstock.getCategoryName(), font1));
				cell23.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell23.setBorderColorBottom(new BaseColor(255, 255, 255));
				cell23.setBorder(Rectangle.NO_BORDER);
				table23.addCell(cell23);
				doc.add(table23);

				List<ItemStock> itemstockini = itemstock.getItemstock();
				depId=itemstockini.get(0).getDepartment_id();
				//ItemStock itmInt=itemstockini.get(0);
				depat_name=itemstockini.get(0).getDepartment_name();

				PdfPTable table = new PdfPTable(7);
				table.setWidths(new float[] { .8f, 2.4f, 1.7f, 1.7f, 1.7f, 1.7f, 2f });
				table.setWidthPercentage(100.0f);

				PdfPCell cell = new PdfPCell();
				cell.setBackgroundColor(new BaseColor(255, 255, 255));
				cell.setPadding(5);
				table.getDefaultCell().setPaddingBottom(10.0f);
				table.getDefaultCell().setBorderColorBottom(new BaseColor(255, 255, 255));
				table.getDefaultCell().setBorder(Rectangle.BOTTOM);
				table.getDefaultCell().setBorderWidthBottom(2.0f);

				PdfPCell cellspan = new PdfPCell();
				cellspan.setBackgroundColor(new BaseColor(255, 255, 255));
				System.out.println("sfhsdjhfsjd=======");
				cellspan.setPadding(6);
				cellspan.setColspan(7);

				cellspan.setPhrase(new Phrase(depat_name,font));
				cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cellspan);

				cell.setPhrase(new Phrase("SI#", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell.setPhrase(new Phrase("ITEM NAME", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell.setPhrase(new Phrase("UNIT", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell.setPhrase(new Phrase("CURRENT STOCK", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell.setPhrase(new Phrase("MIN STOCK", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);	

				cell.setPhrase(new Phrase("MAX STOCK", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell.setPhrase(new Phrase("STD PURCHASE QTY", font1));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				String sino="";
				int count=0;
				//int toatalcnt=0;
				for (ItemStock itemstock1 : itemstockini) {
					if(itemstock1.getDepartment_id() != null )
					{
						if(!((itemstock1.getDepartment_id()).equals(depId))){
							depat_name=itemstock1.getDepartment_name();
							PdfPCell cellspan1 = new PdfPCell();
							cellspan1.setBackgroundColor(new BaseColor(255, 255, 255));
							System.out.println("change=======");
							cellspan1.setPadding(6);
							cellspan1.setColspan(7);

							cellspan1.setPhrase(new Phrase(depat_name,font));
							cellspan1.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cellspan1);

							cell.setPhrase(new Phrase("SI#", font1));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(cell);

							cell.setPhrase(new Phrase("ITEM NAME", font1));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(cell);

							cell.setPhrase(new Phrase("UNIT", font1));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(cell);

							cell.setPhrase(new Phrase("CURRENT STOCK", font1));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(cell);

							cell.setPhrase(new Phrase("MIN STOCK", font1));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(cell);	

							cell.setPhrase(new Phrase("MAX STOCK", font1));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(cell);

							cell.setPhrase(new Phrase("STD PURCHASE QTY", font1));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(cell);
							count=0;
							depId=itemstock1.getDepartment_id();
						}
					}
					count=count+1;
					sino=""+count;
					cell.setPhrase(new Phrase(sino, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell.setPhrase(new Phrase(itemstock1.getName().toString(), font));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					String unit = itemstock1.getUnit();

					cell.setPhrase(new Phrase(unit, font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					String curr_stock = itemstock1.getCurrent_stock();
					if (curr_stock == null) {
						curr_stock = "0";
					}
					cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(curr_stock)), font));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);

					cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(itemstock1.getMin_stock())), font));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
					cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(itemstock1.getMax_stock())), font));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);

					cell.setPhrase(new Phrase(getNumberWithDecimal(Double.parseDouble(itemstock1.getStd_purchase_qty())), font));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
				}

				/*if(toatalcnt<11){
					for(int i=toatalcnt;i<=11;i++){
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
						cell.setPhrase(new Phrase(" "));
						table.addCell(cell);
					}
				} */
				doc.add(table);
			}
		}
		else
		{
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
	}
}
