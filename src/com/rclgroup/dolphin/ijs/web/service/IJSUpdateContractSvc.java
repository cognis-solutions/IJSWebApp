package com.rclgroup.dolphin.ijs.web.service;

import com.rclgroup.dolphin.ijs.web.dao.impl.IJSResultContainerUpdateDaoImpl;
import com.rclgroup.dolphin.ijs.web.dao.impl.IJSUpdateContractDaoImpl;

public class IJSUpdateContractSvc {

	private IJSUpdateContractDaoImpl daoImpl = null;
	
	public IJSUpdateContractSvc(IJSUpdateContractDaoImpl dao) {
		this.daoImpl=dao;
		System.out.println("IJSUpdateContractSvc constructor.."+this.daoImpl);
	}

	public void runTimerForDeleteAndExpireFalgQuery() {
		System.out.println("runTimerForDeleteAndExpireFalgQuery() started in IJSUpdateContractSvc class...");
		daoImpl.addFalgForDeleteAndExpireQuery();
		
	}
	
		

}
