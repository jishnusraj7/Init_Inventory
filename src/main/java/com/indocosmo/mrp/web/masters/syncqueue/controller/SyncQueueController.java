package com.indocosmo.mrp.web.masters.syncqueue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.syncqueue.dao.SyncQueueDao;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;

@Controller
@RequestMapping(value="/syncqueue")
public class SyncQueueController extends ViewController<SyncQueue, SyncQueueDao, SyncQueueService> {

	@Override
	public SyncQueueService getService() {
		
		return new SyncQueueService(getCurrentContext());
	}
	
	
	
}
