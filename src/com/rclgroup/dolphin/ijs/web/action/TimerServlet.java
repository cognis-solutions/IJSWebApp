package com.rclgroup.dolphin.ijs.web.action;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.rclgroup.dolphin.ijs.web.Timer.IJSUpdateContract;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.impl.IJSUpdateContractDaoImpl;
import com.rclgroup.dolphin.ijs.web.factory.IjsDAOFactory;


public class TimerServlet extends HttpServlet{
		public static	IJSUpdateContractDaoImpl dao;
		
		@Override
		public void init() throws ServletException {

		super.init();
		System.out.println("init () started...");
		this.getServletContext();
					dao = (IJSUpdateContractDaoImpl)IjsDAOFactory.getInstance().getDao("ijsUpdateContractDao",getServletContext());
		 System.out.println(" dao object... "+dao.getJdbcTemplate());
		 Timer time = new Timer(); // Instantiate Timer Object
	     IJSUpdateContract st = new IJSUpdateContract(); // Instantiate SheduledTask class
	     long numberOfDays = 24*60*60*1000;
	      // long numberOfDays = 60*100;
		   time.schedule(st, 0, numberOfDays);
		}
		
	   

}
