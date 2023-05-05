package com.indocosmo.mrp.web.core.nodata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.report.tallyexport.model.TallyExport;

@Controller
@RequestMapping("/nodata")
public class NoDataAvailable {

	@RequestMapping(value = "/list")
	public String getData(@ModelAttribute TallyExport tallyExport, Model model) throws Exception {
		//model.addAttribute("Report", true);
		return "nodata/list";
	}

}
