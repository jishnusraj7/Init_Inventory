package com.indocosmo.mrp.web.masters.envsettings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.envsettings.dao.EnvSerttingsDao;
import com.indocosmo.mrp.web.masters.envsettings.model.EnvSerttings;
import com.indocosmo.mrp.web.masters.envsettings.service.EnvSerttingsService;

@Controller
@RequestMapping("/EnvSerttings")
public class EnvSerttingsController extends ViewController<EnvSerttings, EnvSerttingsDao, EnvSerttingsService>{

	@Override
	public EnvSerttingsService getService() {
		return new EnvSerttingsService(getCurrentContext());
	}

}
