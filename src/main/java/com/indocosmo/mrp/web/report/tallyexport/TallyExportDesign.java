package com.indocosmo.mrp.web.report.tallyexport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
import com.indocosmo.mrp.web.report.tallyexport.model.TallyExport;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

/*
 * @gana 08042020
 */
@Repository
public class TallyExportDesign extends HttpServlet{

	public String getTallyExport(TallyExport tallyExportModel, Properties prop) throws IOException {

		List<TallyExport> tallyExport =tallyExportModel.getTallyExport();
		StringBuilder stringBuiler=new StringBuilder();
		StringBuilder stringCredit=new StringBuilder();
		Properties properties=null;
		String xmlTally=null;
		String xmlHead=null;
		String xmlBody=null;
		String xmlNet=null;
		String xmlTax=null;
		String xmlTot=null;
		String xmlFoot=null;
		String xmlData=null;
		String xmlCredHead=null;
		String xmlCredBody=null;
		String xmlCredNet=null;
		String xmlCredTax=null;
		String xmlCredTot=null;
		String xmlCredFoot=null;
		String xmlCredData=null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String filePath=prop.getProperty("tallyExportPath");
		String tallyPath = filePath+"\\tally"+dateFormat.format(today).toString()+".txt";

     
        if(tallyExport.size()!=0) {
		for(TallyExport tallyData:tallyExport) {

			if(tallyData.getOption().equals("0") || tallyData.getOption().equals("1") || tallyData.getOption().equals("ALL")) {
 
				// for reciept
				xmlHead="\n<ENVELOPE>"+
						"\n\t<HEADER>"+
						"\n\t\t<TALLYREQUEST>Import Data</TALLYREQUEST>"+
						"\n\t</HEADER>"+
						"\n\t<BODY>"+
						"\n\t\t<IMPORTDATA>"+
						"\n\t\t\t<REQUESTDESC>"+
						"\n\t\t\t\t<REPORTNAME>Vouchers</REPORTNAME>"+
						"\n\t\t\t</REQUESTDESC>";

				xmlBody="\n\t<REQUESTDATA>"+
						"\n\t<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"+
						"\n\t\t<VOUCHER VCHTYPE=\"Receipt\" ACTION=\"Create\" OBJVIEW=\"Accounting Voucher View\">"+
						"\n\t\t\t<DATE>"+tallyData.getRecievedDate()+"</DATE>"+
						"\n\t\t\t\t <NARRATION>Purchase from "+tallyData.getSupplierName()+" on "+tallyData.getRecievedDate()+"</NARRATION>"+
						"\n\t\t\t\t\t<VOUCHERTYPENAME>Reciept</VOUCHERTYPENAME>"+
						"\n\t\t\t\t\t\t<VOUCHERNUMBER>"+tallyData.getSupplierDocNo()+"</VOUCHERNUMBER>"+
						"\n\t\t\t\t\t\t\t<PARTYLEDGERNAME>"+tallyData.getSupplierName()+"</PARTYLEDGERNAME>"+
						"\n\t\t\t<CSTFORMISSUETYPE/>"+
						"\n\t\t\t<CSTFORMRECVTYPE/>"+
						"\n\t\t\t<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"+
						"\n\t\t\t<VCHGSTCLASS/>"+
						"\n\t\t\t<EFFECTIVEDATE>"+tallyData.getRecievedDate()+"</EFFECTIVEDATE>"+
						"\n\t\t\t<HASCASHFLOW>Yes</HASCASHFLOW>";

				xmlTot="\n<ALLLEDGERENTRIES.LIST>"+
						"\n\t<LEDGERNAME>Purchase</LEDGERNAME>"+
						"\n\t<GSTCLASS/>"+
						"\n\t<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"+
						"\n\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"+
						"\n\t<AMOUNT>-"+tallyData.getGrandTotal()+"</AMOUNT>"+
						"\n</ALLLEDGERENTRIES.LIST>";

				xmlNet="\n<ALLLEDGERENTRIES.LIST>"+
						"\n\t<LEDGERNAME>NET AMOUNT</LEDGERNAME>"+
						"\n\t<GSTCLASS/>"+
						"\n\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"+
						"\n\t<AMOUNT>"+tallyData.getNetTotal()+"</AMOUNT>"+
						"\n</ALLLEDGERENTRIES.LIST>";

				if(tallyData.getTaxTotal()!=0.0) {
					xmlTax="\n<ALLLEDGERENTRIES.LIST>"+
							"\n\t<LEDGERNAME>TAX AMOUNT</LEDGERNAME>"+
							"\n\t<GSTCLASS/>"+
							"\n\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"+
							"\n\t<AMOUNT>"+tallyData.getTaxTotal()+"</AMOUNT>"+
							"\n</ALLLEDGERENTRIES.LIST>";
				}else {
					xmlTax="";
				}
				xmlFoot="\n\t\t</VOUCHER>"+
						"\n\t </TALLYMESSAGE>"+
						"\n\t</REQUESTDATA>"+
						"\n\t</IMPORTDATA>"+
						"\n\t</BODY>"+
						"\n</ENVELOPE>";

				xmlData=xmlHead+xmlBody+xmlTot+xmlNet+xmlTax+xmlFoot;
				stringBuiler=stringBuiler.append(xmlData);
				

			}

			if(tallyData.getOption().equals("0") || tallyData.getOption().equals("ALL")) {

				if(tallyData.getPaymentMode()==0) {
				xmlCredHead="\n<ENVELOPE>"+
						"\n\t<HEADER>"+
						"\n\t\t<TALLYREQUEST>Import Data</TALLYREQUEST>"+
						"\n\t</HEADER>"+
						"\n\t<BODY>"+
						"\n\t\t<IMPORTDATA>"+
						"\n\t\t\t<REQUESTDESC>"+
						"\n\t\t\t\t<REPORTNAME>Vouchers</REPORTNAME>"+
						"\n\t\t\t</REQUESTDESC>";


				xmlCredBody="\n\t<REQUESTDATA>"+
						"\n\t<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"+
						"\n\t\t<VOUCHER VCHTYPE=\"Payment\" ACTION=\"Create\" OBJVIEW=\"Accounting Voucher View\">"+
						"\n\t\t\t<DATE>"+tallyData.getRecievedDate()+"</DATE>"+
						"\n\t\t\t\t <NARRATION>Cash paid to "+tallyData.getSupplierName()+" on "+tallyData.getRecievedDate()+"</NARRATION>"+
						"\n\t\t\t\t\t<VOUCHERTYPENAME>Payment</VOUCHERTYPENAME>"+
						"\n\t\t\t\t\t\t<VOUCHERNUMBER>"+tallyData.getSupplierDocNo()+"</VOUCHERNUMBER>"+
						"\n\t\t\t\t\t\t\t<PARTYLEDGERNAME>"+tallyData.getSupplierName()+"</PARTYLEDGERNAME>"+
						"\n\t\t\t<CSTFORMISSUETYPE/>"+
						"\n\t\t\t<CSTFORMRECVTYPE/>"+
						"\n\t\t\t<PERSISTEDVIEW>Accounting Voucher View</PERSISTEDVIEW>"+
						"\n\t\t\t<VCHGSTCLASS/>"+
						"\n\t\t\t<EFFECTIVEDATE>"+tallyData.getRecievedDate()+"</EFFECTIVEDATE>"+
						"\n\t\t\t<HASCASHFLOW>Yes</HASCASHFLOW>";				

				xmlCredNet="\n<ALLLEDGERENTRIES.LIST>"+
						"\n\t<LEDGERNAME>NET AMOUNT</LEDGERNAME>"+
						"\n\t<GSTCLASS/>"+
						"\n\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"+
						"\n\t<AMOUNT>"+tallyData.getNetTotal()+"</AMOUNT>"+
						"\n</ALLLEDGERENTRIES.LIST>";

				if(tallyData.getTaxTotal()!=0.0) {
					xmlCredTax="\n<ALLLEDGERENTRIES.LIST>"+
							"\n\t<LEDGERNAME>TAX AMOUNT</LEDGERNAME>"+
							"\n\t<GSTCLASS/>"+
							"\n\t<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"+
							"\n\t<AMOUNT>"+tallyData.getTaxTotal()+"</AMOUNT>"+
							"\n</ALLLEDGERENTRIES.LIST>";
				}else {
					xmlCredTax="";
				}
				xmlCredTot="\n<ALLLEDGERENTRIES.LIST>"+
						"\n\t<LEDGERNAME>Cash</LEDGERNAME>"+
						"\n\t<GSTCLASS/>"+
						"\n\t<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"+
						"\n\t<ISPARTYLEDGER>Yes</ISPARTYLEDGER>"+
						"\n\t<AMOUNT>-"+tallyData.getGrandTotal()+"</AMOUNT>"+
						"\n</ALLLEDGERENTRIES.LIST>";

				xmlCredFoot="\n\t\t</VOUCHER>"+
						"\n\t </TALLYMESSAGE>"+
						"\n\t</REQUESTDATA>"+
						"\n\t</IMPORTDATA>"+
						"\n\t</BODY>"+
						"\n</ENVELOPE>";

				xmlCredData=xmlCredHead+xmlCredBody+xmlCredNet+xmlCredTax+xmlCredTot+xmlCredFoot;
				stringBuiler=stringBuiler.append(xmlCredData);

			}
			}
		}

		Files.write(Paths.get(tallyPath), stringBuiler.toString().getBytes());
		
		return tallyPath;
		
        }else {
        	
        }
		return null;
		
	}

  
}
