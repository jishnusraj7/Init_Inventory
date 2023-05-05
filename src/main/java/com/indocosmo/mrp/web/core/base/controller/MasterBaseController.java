package com.indocosmo.mrp.web.core.base.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;

/**
 * @author jojesh-13.2
 * 
 * Controller base for all masters
 *
 * @param <T> The model class for the controller
 */
@Controller
@Qualifier("MasterBaseController")
public abstract class MasterBaseController<T extends MasterModelBase, D extends MasterBaseDao<T>, S extends MasterBaseService<T,D>> extends ViewController<T,D,S> {
	


}
