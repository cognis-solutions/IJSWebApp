package com.rclgroup.dolphin.ijs.web.Timer;

import java.util.TimerTask;

import com.rclgroup.dolphin.ijs.web.action.TimerServlet;
import com.rclgroup.dolphin.ijs.web.service.IJSUpdateContractSvc;


public class IJSUpdateContract extends TimerTask{

	@Override
	public void run() {
		System.out.println("Timer method started IJSUpdateContract class..."+TimerServlet.dao);
		IJSUpdateContractSvc ucde=new IJSUpdateContractSvc(TimerServlet.dao);
		ucde.runTimerForDeleteAndExpireFalgQuery();
		
	}
}
	
