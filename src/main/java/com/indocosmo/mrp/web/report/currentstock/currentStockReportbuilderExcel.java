package com.indocosmo.mrp.web.report.currentstock;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;


public class currentStockReportbuilderExcel extends AbstractExcelView {

	Integer decimalPlace;



	public String getNumberWithDecimal(Double value) {

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model , HSSFWorkbook workbook , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {

		// TODO Auto-generated method stub

		ItemStock  itemstock =   (ItemStock) model.get("listcurrentStock");

		Report report = (Report) model.get("reportName");

		decimalPlace = report.getDecimalPlace();
		HSSFSheet sheet = workbook.createSheet(report.getReportName());
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		// protected
		/*
		 * String password="123"; sheet.protectSheet(password);
		 */


		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		// headerFont.setBoldweight((short) 14);;
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);


		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		/*headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);*/
		
		
		Font tophdr1 = workbook.createFont();
		tophdr1.setFontHeightInPoints((short)12);
		tophdr1.setColor(IndexedColors.BLACK.getIndex());
		tophdr1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle1 = workbook.createCellStyle();
		tophdrCellStyle1.setFont(tophdr1);
		tophdrCellStyle1.setWrapText(true);
		tophdrCellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
		

		Font tophdr = workbook.createFont();
		tophdr.setFontHeightInPoints((short) 9);
		tophdr.setColor(IndexedColors.BLACK.getIndex());
		tophdr.setBoldweight(tophdr.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle = workbook.createCellStyle();
		tophdrCellStyle.setFont(tophdr);
		tophdrCellStyle.setWrapText(true);
		tophdrCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle tophdrCellCategory = workbook.createCellStyle();
		tophdrCellCategory.setFont(tophdr);
		tophdrCellCategory.setWrapText(true);
		tophdrCellCategory.setAlignment(CellStyle.ALIGN_LEFT);

		Font reprName=workbook.createFont();
		reprName.setFontName("Liberation Sans");;
		reprName.setBoldweight((short) 14);
		reprName.setFontHeightInPoints((short) 16);
		reprName.setBoldweight(Font.BOLDWEIGHT_BOLD);
		reprName.setColor(IndexedColors.BLACK.getIndex());
		reprName.setUnderline(Font.U_SINGLE);
		CellStyle rptNameCellStyle = workbook.createCellStyle();
		rptNameCellStyle.setFont(reprName);
		rptNameCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		/* rptNameCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
         rptNameCellStyle.setBorderTop(CellStyle.BORDER_THIN);
         rptNameCellStyle.setBorderRight(CellStyle.BORDER_THIN);
         rptNameCellStyle.setBorderLeft(CellStyle.BORDER_THIN);*/


		Font contentFont=workbook.createFont();
		contentFont.setFontName("Liberation Sans");;
		// contentFont.setBoldweight((short) 9);
		contentFont.setFontHeightInPoints((short) 9);
		contentFont.setColor(IndexedColors.BLACK.getIndex());
		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setFont(contentFont);
		contentCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle.setBorderLeft(CellStyle.BORDER_THIN);



		CellStyle contentCellStyle1 = workbook.createCellStyle();
		contentCellStyle1.setFont(contentFont);
		contentCellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);

		CellStyle contentCellStyleUnit = workbook.createCellStyle();
		contentCellStyleUnit.setFont(contentFont);
		contentCellStyleUnit.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyleUnit.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyleUnit.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyleUnit.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyleUnit.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle contentCellStyle2= workbook.createCellStyle();
		contentCellStyle2.setFont(contentFont);
		contentCellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderTop(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderRight(CellStyle.BORDER_THIN);
		contentCellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_CENTER);

		Font headcontentFont=workbook.createFont();

		headcontentFont.setFontHeightInPoints((short) 9);
		headcontentFont.setColor(IndexedColors.BLACK.getIndex());
		headcontentFont.setBoldweight(headcontentFont.BOLDWEIGHT_BOLD);

		CellStyle subheaderCellStyle = workbook.createCellStyle();
		subheaderCellStyle.setFont(headcontentFont);
		subheaderCellStyle.setWrapText(true);
		subheaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		subheaderCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		subheaderCellStyle.setBorderLeft(CellStyle.BORDER_THIN);



		List<ItemStock> itemstockini2 = itemstock.getItemstock();
		if(itemstockini2.size()!=0)

		{


			if(itemstock.getItemstockReportType()){
				//set column width

				sheet.setColumnWidth(0, 3000);
				sheet.setColumnWidth(1, 6000);
				sheet.setColumnWidth(2, 10000);
				sheet.setColumnWidth(3, 6000);
				sheet.setColumnWidth(4, 6000);

				String  depId="";
				String depat_name="";
				List<ItemStock> itemstockini = itemstock.getItemstock();
				depId=itemstockini.get(0).getDepartment_id();

				depat_name=itemstockini.get(0).getDepartment_name();


				/*HSSFRow nameRow = sheet.createRow(0);
				nameRow.setHeightInPoints(35);
				HSSFCell cellname = nameRow.createCell(0);
				cellname.setCellValue(report.getReportName());
				cellname.setCellStyle(rptNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress( 0, 0,0,3));
				*/
				
				
				
				HSSFRow companyRow = sheet.createRow(0);
				HSSFCell companyCell = companyRow.createCell(0);
				companyRow.setHeightInPoints(18);
				companyCell.setCellValue(report.getCompanyName());
				companyCell.setCellStyle(tophdrCellStyle1);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

				HSSFRow addressRow = sheet.createRow(1);
				HSSFCell Addcell = addressRow.createCell(0);
				addressRow.setHeightInPoints(50);
				Addcell.setCellValue(report.getCompanyAddress());
				Addcell.setCellStyle(tophdrCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));

				HSSFRow nameRow = sheet.createRow(2);
				nameRow.setHeightInPoints(35);
				HSSFCell cellname = nameRow.createCell(0);
				cellname.setCellValue(report.getReportName());
				cellname.setCellStyle(rptNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));



				// Create a Row
				HSSFRow headerRow = sheet.createRow(3);



				HSSFCell cell1 = headerRow.createCell(0);
				headerRow.setHeightInPoints(18);
				cell1.setCellValue(depat_name);
				cell1.setCellStyle(headerCellStyle);

				// Merges the cells
				CellRangeAddress merge1 = new CellRangeAddress(1, 1, 0, 4);
				sheet.addMergedRegion(merge1);

				/*
				 * sheet.addMergedRegion(new CellRangeAddress( 0, //first row (0-based) 0,
				 * //last row (0-based) 0, //first column (0-based) 3 //last column (0-based)
				 * ));
				 */

				HSSFRow tableheading = sheet.createRow(4);
				tableheading.setHeightInPoints(25);
				HSSFCell cellSi = tableheading.createCell(0);
				cellSi.setCellStyle(subheaderCellStyle);
				cellSi.setCellValue("SI");

				HSSFCell cellcode = tableheading.createCell(1);
				cellcode.setCellStyle(subheaderCellStyle);
				cellcode.setCellValue("Item Code");

				HSSFCell cellName = tableheading.createCell(2);
				cellName.setCellStyle(subheaderCellStyle);
				cellName.setCellValue("Item Name");
				
				/*
				 * 
				 *  Done by anandu on 30-01-2020
				 *   
				 */

				HSSFCell cellUnit = tableheading.createCell(3);
				cellUnit.setCellStyle(subheaderCellStyle);
				cellUnit.setCellValue("Unit");

				HSSFCell cellStock = tableheading.createCell(4);
				cellStock.setCellStyle(subheaderCellStyle);
				cellStock.setCellValue("Current Stock");

				String sino = "";
				int count = 0;
				// int toatalcnt = 0;

				int i=5;

				for (ItemStock itemstock1 : itemstock.getItemstock()) {



					if(!((itemstock1.getDepartment_id()).equals(depId))){

						depat_name=itemstock1.getDepartment_name();
						HSSFRow headerRow1 = sheet.createRow(i);
						headerRow1.setHeightInPoints(18);
						HSSFCell cell2 = headerRow1.createCell(0);

						cell2.setCellValue(depat_name);
						cell2.setCellStyle(headerCellStyle);

						// Merges the cells
						CellRangeAddress merge2 = new CellRangeAddress(i, i, 0, 4);
						sheet.addMergedRegion(merge1);
						HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, merge2, sheet, workbook);
						HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, merge2, sheet, workbook);
						HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, merge2, sheet, workbook);
						HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, merge2, sheet, workbook);

						// sheet.addMergedRegion(new CellRangeAddress(i,i, 0,3));
						i++;

						HSSFRow tableheading1 = sheet.createRow(i);
						tableheading1.setHeightInPoints( 25);
						i++;
						HSSFCell cellSi1 = tableheading1.createCell(0);
						cellSi1.setCellStyle(subheaderCellStyle);
						cellSi1.setCellValue("SI");

						HSSFCell cellcode1 = tableheading1.createCell(1);
						cellcode1.setCellStyle(subheaderCellStyle);
						cellcode1.setCellValue("Item Code");

						HSSFCell cellName1 = tableheading1.createCell(2);
						cellName1.setCellStyle(subheaderCellStyle);
						cellName1.setCellValue("Item Name");

						HSSFCell cellUnit1 = tableheading1.createCell(3);
						cellUnit1.setCellStyle(subheaderCellStyle);
						cellUnit1.setCellValue("Unit");

						HSSFCell cellStock1 = tableheading1.createCell(4);
						cellStock1.setCellStyle(subheaderCellStyle);
						cellStock1.setCellValue("Current Stock");

						count=0;
						depId=itemstock1.getDepartment_id();


					}



					count=count+1;
					sino=""+count;

					HSSFRow tableheading1 = sheet.createRow(i);
					i++;
					HSSFCell cellSi1 = tableheading1.createCell(0);
					cellSi1.setCellStyle(contentCellStyle2);
					cellSi1.setCellValue(sino);

					HSSFCell cellcode1 = tableheading1.createCell(1);
					cellcode1.setCellStyle(contentCellStyle);
					cellcode1.setCellValue(itemstock1.getCode());

					HSSFCell cellName1 = tableheading1.createCell(2);
					cellName1.setCellStyle(contentCellStyle);
					cellName1.setCellValue(itemstock1.getName().toString());

					String unit = itemstock1.getUnit();

					HSSFCell cellUnit1 = tableheading1.createCell(3);
					cellUnit1.setCellStyle(contentCellStyleUnit);
					cellUnit1.setCellValue(unit);

					String curr_stock = itemstock1.getCurrent_stock();
					if (curr_stock == null) {
						curr_stock = "0";
					}

					HSSFCell cellStock1 = tableheading1.createCell(4);
					cellStock1.setCellStyle(contentCellStyle1);
					cellStock1.setCellValue(getNumberWithDecimal(Double.parseDouble(curr_stock)));




				}





			}

			else {
				sheet.setColumnWidth(0, 2000);
				sheet.setColumnWidth(1, 7000);
				sheet.setColumnWidth(2, 3200);
				sheet.setColumnWidth(3, 3200);
				sheet.setColumnWidth(4, 4500);
				sheet.setColumnWidth(5, 4500);
				sheet.setColumnWidth(6, 4500);

				String  depId="";
				String depat_name="";

				/*HSSFRow nameRow = sheet.createRow(0);
				nameRow.setHeightInPoints(35);
				HSSFCell cellname = nameRow.createCell(0);
				cellname.setCellValue(report.getReportName());
				cellname.setCellStyle(rptNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress( 0, 0,0,5));*/
				
				HSSFRow companyRow = sheet.createRow(0);
				HSSFCell companyCell = companyRow.createCell(0);
				companyRow.setHeightInPoints(18);
				companyCell.setCellValue(report.getCompanyName());
				companyCell.setCellStyle(tophdrCellStyle1);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

				HSSFRow addressRow = sheet.createRow(1);
				HSSFCell Addcell = addressRow.createCell(0);
				addressRow.setHeightInPoints(50);
				Addcell.setCellValue(report.getCompanyAddress());
				Addcell.setCellStyle(tophdrCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

				HSSFRow nameRow = sheet.createRow(2);
				nameRow.setHeightInPoints(35);
				HSSFCell cellname = nameRow.createCell(0);
				cellname.setCellValue(report.getReportName());
				cellname.setCellStyle(rptNameCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));




				HSSFRow headerRow = sheet.createRow(3);
				headerRow.setHeightInPoints(15);
				HSSFCell cell1 = headerRow.createCell(0);
				cell1.setCellValue(itemstock.getCategoryName());
				cell1.setCellStyle(tophdrCellCategory);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));

				/*// Merges the cells
	        CellRangeAddress merge1 = new CellRangeAddress(0,0, 0,5);
	        sheet.addMergedRegion(merge1);
	        HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
	        HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
	        HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, merge1, sheet, workbook);
	        HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, merge1, sheet, workbook);*/



				List<ItemStock> itemstockini = itemstock.getItemstock();

				depId = itemstockini.get(0).getDepartment_id();
				// ItemStock itmInt=itemstockini.get(0);
				depat_name = itemstockini.get(0).getDepartment_name();


				HSSFRow headerRow1 = sheet.createRow(4);
				HSSFCell cellh = headerRow1.createCell(0);
				headerRow1.setHeightInPoints(18);
				cellh.setCellValue(depat_name);
				cellh.setCellStyle(headerCellStyle);
				//  sheet.addMergedRegion(new CellRangeAddress( 1, 1,0,5));

				// Merges the cells
				CellRangeAddress merge3 = new CellRangeAddress(4, 4, 0, 6);
				sheet.addMergedRegion(merge3);
				HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, merge3, sheet, workbook);
				HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, merge3, sheet, workbook);
				HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, merge3, sheet, workbook);
				HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, merge3, sheet, workbook);


				HSSFRow tableheading = sheet.createRow(5);
				tableheading.setHeightInPoints(25);
				HSSFCell cellSi = tableheading.createCell(0);
				cellSi.setCellStyle(subheaderCellStyle);
				cellSi.setCellValue("SI");



				HSSFCell cellName = tableheading.createCell(1);
				cellName.setCellStyle(subheaderCellStyle);
				cellName.setCellValue("ITEM NAME");

				HSSFCell cellUntHead = tableheading.createCell(2);
				cellUntHead.setCellStyle(subheaderCellStyle);
				cellUntHead.setCellValue("UNIT");

				HSSFCell cellStock = tableheading.createCell(3);
				cellStock.setCellStyle(subheaderCellStyle);
				cellStock.setCellValue("CURRENT STOCK");

				HSSFCell cellminStock = tableheading.createCell(4);
				cellminStock.setCellStyle(subheaderCellStyle);
				cellminStock.setCellValue("MIN STOCK");

				HSSFCell cellmaxStock = tableheading.createCell(5);
				cellmaxStock.setCellStyle(subheaderCellStyle);
				cellmaxStock.setCellValue("MAX STOCK");

				HSSFCell cellStd = tableheading.createCell(6);
				cellStd.setCellStyle(subheaderCellStyle);
				cellStd.setCellValue("STD PURCHASE QTY");

				String sino = "";
				int count = 0;
				// int toatalcnt=0;
				int i = 6;
				for (ItemStock itemstock1 : itemstockini) {

					if(!((itemstock1.getDepartment_id()).equals(depId))){
						depat_name=itemstock1.getDepartment_name();

						HSSFRow headerRow2 = sheet.createRow(i);
						headerRow2.setHeightInPoints(18);
						// headerRow2.setHeight((short) 25);
						HSSFCell cellh2 = headerRow2.createCell(0);
						cellh2.setCellValue(depat_name);
						cellh2.setCellStyle(headerCellStyle);

						// Merges the cells
						CellRangeAddress merge2 = new CellRangeAddress(i, i, 0, 6);
						sheet.addMergedRegion(merge2);
						HSSFRegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, merge2, sheet, workbook);
						HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, merge2, sheet, workbook);
						HSSFRegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, merge2, sheet, workbook);
						HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, merge2, sheet, workbook);
						// sheet.addMergedRegion(new CellRangeAddress( i, i,0,5));
						i++;

						HSSFRow tableheading1 = sheet.createRow(i);
						tableheading1.setHeightInPoints(25);
						i++;
						HSSFCell cellSi1 = tableheading1.createCell(0);
						cellSi1.setCellStyle(subheaderCellStyle);
						cellSi1.setCellValue("SI");



						HSSFCell cellName1 = tableheading1.createCell(1);
						cellName1.setCellStyle(subheaderCellStyle);
						cellName1.setCellValue("ITEM NAME");

						HSSFCell cellUnitHed1 = tableheading1.createCell(2);
						cellUnitHed1.setCellStyle(subheaderCellStyle);
						cellUnitHed1.setCellValue("UNIT");

						HSSFCell cellStock1 = tableheading1.createCell(3);
						cellStock1.setCellStyle(subheaderCellStyle);
						cellStock1.setCellValue("CURRENT STOCK");

						HSSFCell cellminStock1 = tableheading1.createCell(4);
						cellminStock1.setCellStyle(subheaderCellStyle);
						cellminStock1.setCellValue("MIN STOCK");

						HSSFCell cellmaxStock1 = tableheading1.createCell(5);
						cellmaxStock1.setCellStyle(subheaderCellStyle);
						cellmaxStock1.setCellValue("MAX STOCK");

						HSSFCell cellStd1 = tableheading1.createCell(6);
						cellStd1.setCellStyle(subheaderCellStyle);
						cellStd1.setCellValue("STD PURCHASE QTY");


						count=0;
						depId=itemstock1.getDepartment_id();


					}

					count=count+1;
					sino=""+count;

					HSSFRow tableheading1 = sheet.createRow(i);
					i++;
					HSSFCell cellSi1 = tableheading1.createCell(0);
					cellSi1.setCellStyle(contentCellStyle2);
					cellSi1.setCellValue(sino);



					HSSFCell cellName1 = tableheading1.createCell(1);
					cellName1.setCellStyle(contentCellStyle);
					cellName1.setCellValue(itemstock1.getName());

					String unit = itemstock1.getUnit();
					
					HSSFCell cellUnit1 = tableheading1.createCell(2);
					cellUnit1.setCellStyle(contentCellStyleUnit);
					cellUnit1.setCellValue(unit);

					String curr_stock = itemstock1.getCurrent_stock();
					if (curr_stock == null) {
						curr_stock = "0";
					}

					HSSFCell cellStock1 = tableheading1.createCell(3);
					cellStock1.setCellStyle(contentCellStyle1);
					cellStock1.setCellValue(curr_stock);

					HSSFCell cellminStock1 = tableheading1.createCell(4);
					cellminStock1.setCellStyle(contentCellStyle1);
					cellminStock1.setCellValue(itemstock1.getMin_stock());

					HSSFCell cellmaxStock1 = tableheading1.createCell(5);
					cellmaxStock1.setCellStyle(contentCellStyle1);
					cellmaxStock1.setCellValue(itemstock1.getMax_stock());

					HSSFCell cellStd1 = tableheading1.createCell(6);
					cellStd1.setCellStyle(contentCellStyle1);
					cellStd1.setCellValue(itemstock1.getStd_purchase_qty());



				}









			}

		} else{
			HSSFRow companyRow = sheet.createRow(0);
			HSSFCell companyCell = companyRow.createCell(0);
			companyRow.setHeightInPoints(18);
			companyCell.setCellValue(report.getCompanyName());
			companyCell.setCellStyle(tophdrCellStyle1);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

			HSSFRow addressRow = sheet.createRow(1);
			HSSFCell Addcell = addressRow.createCell(0);
			addressRow.setHeightInPoints(50);
			Addcell.setCellValue(report.getCompanyAddress());
			Addcell.setCellStyle(tophdrCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

			HSSFRow nameRow = sheet.createRow(2);
			nameRow.setHeightInPoints(35);
			HSSFCell cellname = nameRow.createCell(0);
			cellname.setCellValue(report.getReportName());
			cellname.setCellStyle(rptNameCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));

			HSSFRow tableData = sheet.createRow(3);
			tableData.setHeightInPoints(25);

			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(tophdrCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
		}

		Date today1 = new Date();
		SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMMyy"); // without separators
		String stringToday3 = sdf3.format(today1);




		String headerResponse = "attachment;filename=";
		headerResponse = headerResponse.concat(report.getReportName().toLowerCase().replaceAll("\\s","")+stringToday3+".xls");
		response.addHeader("Content-disposition", headerResponse);


	}

}
