package com.indocosmo.mrp.web.report.stockoutreport;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockoutreport.model.StockoutReportModel;
import com.itextpdf.text.Phrase;

@SuppressWarnings("deprecation")
public class StockOutReportSummaryExcel extends AbstractExcelView {

	Integer decimalPlace;

	public String getNumberWithDecimal(Double value) {

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
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StockoutReportModel stockList = (StockoutReportModel) model
				.get("stockoutSummaryView");
		Report report = (Report) model.get("reportName");

		decimalPlace = report.getDecimalPlace();
		HSSFSheet sheet = workbook.createSheet(report.getReportName());

		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Font tophdr1 = workbook.createFont();
		tophdr1.setFontHeightInPoints((short) 12);
		tophdr1.setColor(IndexedColors.BLACK.getIndex());
		tophdr1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle1 = workbook.createCellStyle();
		tophdrCellStyle1.setFont(tophdr1);
		tophdrCellStyle1.setWrapText(true);
		tophdrCellStyle1.setAlignment(CellStyle.ALIGN_CENTER);

		Font tophdr = workbook.createFont();
		tophdr.setFontHeightInPoints((short) 9);
		tophdr.setColor(IndexedColors.BLACK.getIndex());
		tophdr.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle tophdrCellStyle = workbook.createCellStyle();
		tophdrCellStyle.setFont(tophdr);
		tophdrCellStyle.setWrapText(true);
		tophdrCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font reprName = workbook.createFont();
		reprName.setFontName("Liberation Sans");
		;
		reprName.setBoldweight((short) 14);
		reprName.setFontHeightInPoints((short) 16);
		reprName.setBoldweight(Font.BOLDWEIGHT_BOLD);
		reprName.setColor(IndexedColors.BLACK.getIndex());
		reprName.setUnderline(Font.U_SINGLE);
		CellStyle rptNameCellStyle = workbook.createCellStyle();
		rptNameCellStyle.setFont(reprName);
		rptNameCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font contentFont = workbook.createFont();
		contentFont.setFontName("Liberation Sans");
		;
		contentFont.setFontHeightInPoints((short) 8);
		contentFont.setColor(IndexedColors.BLACK.getIndex());
		CellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setFont(contentFont);

		CellStyle contentCellStyle1 = workbook.createCellStyle();
		contentCellStyle1.setFont(contentFont);
		contentCellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);

		CellStyle contentCellStyle2 = workbook.createCellStyle();
		contentCellStyle2.setFont(tophdr);
		contentCellStyle2.setAlignment(CellStyle.ALIGN_RIGHT);

		Font headcontentFont = workbook.createFont();

		headcontentFont.setFontHeightInPoints((short) 9);
		headcontentFont.setColor(IndexedColors.BLACK.getIndex());
		headcontentFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle subheaderCellStyle = workbook.createCellStyle();
		subheaderCellStyle.setFont(headcontentFont);
		subheaderCellStyle.setWrapText(true);
		subheaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		
		CellStyle subheaderCellStyle1 = workbook.createCellStyle();
		subheaderCellStyle1.setFont(headcontentFont);
		subheaderCellStyle1.setWrapText(true);
		CellRangeAddress cellRangeAddress0;
		CellRangeAddress cellRangeAddress1;
		CellRangeAddress cellRangeAddress2;
		CellRangeAddress cellRangeAddress3;

		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);
		if (stockList.getOption() != 1) {
			sheet.setColumnWidth(6, 5000);
			cellRangeAddress0 = new CellRangeAddress(0, 0, 0, 6);
			cellRangeAddress1 = new CellRangeAddress(1, 1, 0, 6);
			cellRangeAddress2 = new CellRangeAddress(2, 2, 0, 6);
			cellRangeAddress3 = new CellRangeAddress(3, 3, 0, 6);
		} else {
			cellRangeAddress0 = new CellRangeAddress(0, 0, 0, 5);
			cellRangeAddress1 = new CellRangeAddress(1, 1, 0, 5);
			cellRangeAddress2 = new CellRangeAddress(2, 2, 0, 5);
			cellRangeAddress3 = new CellRangeAddress(3, 3, 0, 5);
		}

		HSSFRow companyRow = sheet.createRow(0);
		HSSFCell companyCell = companyRow.createCell(0);
		companyRow.setHeightInPoints(18);
		companyCell.setCellValue(report.getCompanyName());
		companyCell.setCellStyle(tophdrCellStyle1);
		sheet.addMergedRegion(cellRangeAddress0);

		HSSFRow addressRow = sheet.createRow(1);
		HSSFCell Addcell = addressRow.createCell(0);
		addressRow.setHeightInPoints(50);
		Addcell.setCellValue(report.getCompanyAddress());
		Addcell.setCellStyle(tophdrCellStyle);
		sheet.addMergedRegion(cellRangeAddress1);

		HSSFRow nameRow = sheet.createRow(2);
		nameRow.setHeightInPoints(35);
		HSSFCell cellname = nameRow.createCell(0);
		cellname.setCellValue(report.getReportName());
		cellname.setCellStyle(rptNameCellStyle);
		sheet.addMergedRegion(cellRangeAddress2);
		
		
		String Header1="BETWEEN  "+stockList.getStartdate()+"  AND  "+stockList.getEnddate();
		
		HSSFRow rangeRaw = sheet.createRow(3);
		rangeRaw.setHeightInPoints(18);
		HSSFCell cellRange = rangeRaw.createCell(0);
		cellRange.setCellValue(Header1);
		cellRange.setCellStyle(tophdrCellStyle);
		sheet.addMergedRegion(cellRangeAddress3);

		String cat_name = "";
		String trs_no = "";

		if (stockList.getStockoutreportData().isEmpty()) {
			HSSFRow tableData = sheet.createRow(4);
			tableData.setHeightInPoints(25);
			HSSFCell cellTotal = tableData.createCell(0);
			cellTotal.setCellStyle(tophdrCellStyle);
			cellTotal.setCellValue("NO DATA AVAILABLE");
			if (stockList.getOption() != 1) {
				sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 6));
			}else{
				sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
			}
		} else if (stockList.getOption() == 1) {

			Double trs_total = 0.0;
			Double grand_total = 0.0;

			trs_no = "";
			cat_name = "";
			int count = 4;
			int siNo = 1;

			for (StockoutReportModel stockoutdtl : stockList
					.getStockoutreportData()) {

				//grand_total += stockoutdtl.getAmount();
				grand_total += stockoutdtl.getIssued_qty()*stockoutdtl.getWar_rate();
				if (!(stockoutdtl.getStock_transfer_no()).equals(trs_no)) {
					if (count != 4) {
						HSSFRow totalRaw = sheet.createRow(count++);
						HSSFCell total = totalRaw.createCell(0);
						total.setCellStyle(contentCellStyle2);
						total.setCellValue("Total");
						sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 0, 4));

						HSSFCell total1 = totalRaw.createCell(5);
						total1.setCellStyle(contentCellStyle2);
						total1.setCellValue(getRateWithDecimal(trs_total));
						
					}
					
					trs_total = 0.0;
					sheet.addMergedRegion(new CellRangeAddress(count++, count++, 0, 5));

					HSSFRow tableheading = sheet.createRow(count++);
					tableheading.setHeightInPoints(25);
					HSSFCell date = tableheading.createCell(0);
					date.setCellStyle(subheaderCellStyle1);
					date.setCellValue("DATE  :  " +stockoutdtl.getStock_transfer_date());
					sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 0, 2));

					HSSFCell refno = tableheading.createCell(3);
					refno.setCellStyle(subheaderCellStyle1);
					refno.setCellValue("REF NO  :  " +stockoutdtl.getStock_transfer_no() );
					sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 3, 5));

					HSSFRow tableheading2 = sheet.createRow(count++);
					tableheading2.setHeightInPoints(25);
					HSSFCell srcDept = tableheading2.createCell(0);
					srcDept.setCellStyle(subheaderCellStyle1);
					srcDept.setCellValue("SRC.DEPARTMENT  :  " +stockoutdtl.getSource_department_name());
					sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 0, 2));

					HSSFCell destDept = tableheading2.createCell(3);
					destDept.setCellStyle(subheaderCellStyle1);
					destDept.setCellValue("DEST.DEPARTMENT  :  " +stockoutdtl.getDest_department_name());
					sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 3, 5));

					HSSFRow tableheading3 = sheet.createRow(count++);
					tableheading3.setHeightInPoints(25);
					HSSFCell si = tableheading3.createCell(0);
					si.setCellStyle(subheaderCellStyle);
					si.setCellValue("SI#");

					HSSFCell name = tableheading3.createCell(1);
					name.setCellStyle(subheaderCellStyle);
					name.setCellValue("Item Name");

					HSSFCell unit = tableheading3.createCell(2);
					unit.setCellStyle(subheaderCellStyle);
					unit.setCellValue("Unit");

					HSSFCell qty = tableheading3.createCell(3);
					qty.setCellStyle(subheaderCellStyle);
					qty.setCellValue("Delivered Qty");

					HSSFCell rate = tableheading3.createCell(4);
					rate.setCellStyle(subheaderCellStyle);
					rate.setCellValue("Rate");

					HSSFCell amount = tableheading3.createCell(5);
					amount.setCellStyle(subheaderCellStyle);
					amount.setCellValue("Amount");

				}

				if (!stockoutdtl.getItm_cat_name().matches(cat_name)) {

					HSSFRow catName = sheet.createRow(count++);
					HSSFCell si = catName.createCell(0);
					si.setCellStyle(subheaderCellStyle1);
					si.setCellValue(stockoutdtl.getItm_cat_name());
					sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 0, 5));
				}

				HSSFRow data = sheet.createRow(count++);
				HSSFCell si = data.createCell(0);
				si.setCellStyle(contentCellStyle);
				si.setCellValue(""+siNo++);

				HSSFCell name = data.createCell(1);
				name.setCellStyle(contentCellStyle);
				name.setCellValue(stockoutdtl.getStock_item_name());

				HSSFCell unit = data.createCell(2);
				unit.setCellStyle(contentCellStyle);
				unit.setCellValue(stockoutdtl.getUomname());

				HSSFCell qty = data.createCell(3);
				qty.setCellStyle(contentCellStyle1);
				qty.setCellValue(getNumberWithDecimal(stockoutdtl.getIssued_qty()));

				HSSFCell rate = data.createCell(4);
				rate.setCellStyle(contentCellStyle1);
				//rate.setCellValue(getRateWithDecimal(stockoutdtl.getCost_price()));
				rate.setCellValue(getRateWithDecimal(stockoutdtl.getWar_rate()));


				HSSFCell amount = data.createCell(5);
				amount.setCellStyle(contentCellStyle1);
				//amount.setCellValue(getRateWithDecimal(stockoutdtl.getAmount()));
				amount.setCellValue(getRateWithDecimal(stockoutdtl.getIssued_qty()*stockoutdtl.getWar_rate()));
				//trs_total += stockoutdtl.getAmount();
				trs_total=trs_total+stockoutdtl.getIssued_qty()*stockoutdtl.getWar_rate();
				trs_no = stockoutdtl.getStock_transfer_no();
				cat_name = stockoutdtl.getItm_cat_name();
			}

			HSSFRow totalRaw = sheet.createRow(count++);
			HSSFCell total = totalRaw.createCell(0);
			total.setCellStyle(contentCellStyle2);
			total.setCellValue("Total");
			sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 0, 4));

			HSSFCell total1 = totalRaw.createCell(5);
			total1.setCellStyle(contentCellStyle2);
			total1.setCellValue(getRateWithDecimal(trs_total));

			HSSFRow grTotalRaw = sheet.createRow(count++);
			HSSFCell grTotal = grTotalRaw.createCell(0);
			grTotal.setCellStyle(contentCellStyle2);
			grTotal.setCellValue("Grand Total");
			sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 0, 4));

			HSSFCell grTotal1 = grTotalRaw.createCell(5);
			grTotal1.setCellStyle(contentCellStyle2);
			grTotal1.setCellValue(getRateWithDecimal(grand_total));

		} else if (stockList.getOption() == 0) {

			int count = 4;
			String stockItmId = "0";

			Double stoknSum = 0.0;
			Double valueSum = 0.0;
			Double grand_stoknSum = 0.0;
			Double grand_valueSum = 0.0;
			int siNo = 1;

			HSSFRow tableheading3 = sheet.createRow(count++);
			tableheading3.setHeightInPoints(25);
			HSSFCell si = tableheading3.createCell(0);
			si.setCellStyle(subheaderCellStyle);
			si.setCellValue("SI#");

			HSSFCell name = tableheading3.createCell(1);
			name.setCellStyle(subheaderCellStyle);
			name.setCellValue("Item");

			HSSFCell unit = tableheading3.createCell(2);
			unit.setCellStyle(subheaderCellStyle);
			unit.setCellValue("Unit");

			HSSFCell date = tableheading3.createCell(3);
			date.setCellStyle(subheaderCellStyle);
			date.setCellValue("Date");

			HSSFCell ref = tableheading3.createCell(4);
			ref.setCellStyle(subheaderCellStyle);
			ref.setCellValue("Ref No");

			HSSFCell stock = tableheading3.createCell(5);
			stock.setCellStyle(subheaderCellStyle);
			stock.setCellValue("Stock Out");

			HSSFCell value = tableheading3.createCell(6);
			value.setCellStyle(subheaderCellStyle);
			value.setCellValue("value");

			if (!stockList.getStockoutreportData().isEmpty()) {
				stockItmId = stockList.getStockoutreportData().get(0)
						.getStockItemId();

			}

			for (StockoutReportModel stockindtl : stockList
					.getStockoutreportData()) {
				if (!(stockindtl.getStockItemId() == stockItmId)) {
					stockItmId = stockindtl.getStockItemId();
					HSSFRow totalRaw = sheet.createRow(count++);
					HSSFCell total = totalRaw.createCell(0);
					total.setCellStyle(contentCellStyle2);
					total.setCellValue("Total:");
					sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 0, 4));

					HSSFCell total1 = totalRaw.createCell(5);
					total1.setCellStyle(contentCellStyle2);
					total1.setCellValue(getNumberWithDecimal(
							Double.parseDouble(stoknSum.toString())).toString());

					HSSFCell total2 = totalRaw.createCell(6);
					total2.setCellStyle(contentCellStyle2);
					total2.setCellValue(getRateWithDecimal(
							Double.parseDouble(valueSum.toString())).toString());
					stoknSum = 0.0;
					valueSum = 0.0;
				}

				HSSFRow data = sheet.createRow(count++);
				HSSFCell si1 = data.createCell(0);
				si1.setCellStyle(contentCellStyle);
				si1.setCellValue(""+siNo++);

				HSSFCell name1 = data.createCell(1);
				name1.setCellStyle(contentCellStyle);
				name1.setCellValue(stockindtl.getStock_item_name());

				HSSFCell unit1 = data.createCell(2);
				unit1.setCellStyle(contentCellStyle);
				unit1.setCellValue(stockindtl.getUomname());

				HSSFCell date1 = data.createCell(3);
				date1.setCellStyle(contentCellStyle);
				date1.setCellValue(stockindtl.getStock_transfer_date());

				HSSFCell ref1 = data.createCell(4);
				ref1.setCellStyle(contentCellStyle);
				ref1.setCellValue(stockindtl.getStock_transfer_no());

				HSSFCell stock1 = data.createCell(5);
				stock1.setCellStyle(contentCellStyle1);
				stock1.setCellValue(getNumberWithDecimal(stockindtl
						.getIssued_qty()));

				HSSFCell value1 = data.createCell(6);
				value1.setCellStyle(contentCellStyle1);
				value1.setCellValue(getRateWithDecimal(
						Double.parseDouble(stockindtl.getIssued_qty()
								.toString())
								* Double.parseDouble(stockindtl.getCost_price()
										.toString())).toString());

				stoknSum = stoknSum + stockindtl.getIssued_qty();
				valueSum = valueSum
						+ (stockindtl.getIssued_qty() * stockindtl
								.getCost_price());
				grand_stoknSum += stockindtl.getIssued_qty();
				/*grand_valueSum += ((stockindtl.getIssued_qty()) * (stockindtl
						.getCost_price()));*/
				
				grand_valueSum += ((stockindtl.getIssued_qty())*(stockindtl.getWar_rate()));
			}

			HSSFRow totalRaw = sheet.createRow(count++);
			HSSFCell total = totalRaw.createCell(0);
			total.setCellStyle(contentCellStyle2);
			total.setCellValue("Total:");
			sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 0, 4));

			HSSFCell total1 = totalRaw.createCell(5);
			total1.setCellStyle(contentCellStyle2);
			total1.setCellValue(getNumberWithDecimal(stoknSum));

			HSSFCell total2 = totalRaw.createCell(6);
			total2.setCellStyle(contentCellStyle2);
			total2.setCellValue(getRateWithDecimal(valueSum));

			HSSFRow GrdTotalRaw = sheet.createRow(count++);
			HSSFCell GrTotal = GrdTotalRaw.createCell(0);
			GrTotal.setCellStyle(contentCellStyle2);
			GrTotal.setCellValue(" Grand Total:");
			sheet.addMergedRegion(new CellRangeAddress(count-1, count-1, 0, 4));

			HSSFCell GrTotal1 = GrdTotalRaw.createCell(5);
			GrTotal1.setCellStyle(contentCellStyle2);
			GrTotal1.setCellValue(getNumberWithDecimal(grand_stoknSum));

			HSSFCell GrTotal2 = GrdTotalRaw.createCell(6);
			GrTotal2.setCellStyle(contentCellStyle2);
			GrTotal2.setCellValue(getRateWithDecimal(grand_valueSum));
		}

	}

}
