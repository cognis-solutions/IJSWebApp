package com.rclgroup.dolphin.ijs.web.session;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsProcessJOBkgBLSearchDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.factory.IjsDAOFactory;
import com.rclgroup.dolphin.ijs.web.service.IjsProcessJOBkgBLSearchSvc;
/**
 * 
 * @author Ashish.1.Rawat
 * listner to clean up data from jo temporary tables in case session is expired
 */
@WebListener
public class IjsSessionListener implements HttpSessionListener{
	 @Override
	  public void sessionCreated(HttpSessionEvent se) {
	      //System.out.println("-- HttpSessionListener#sessionCreated invoked --");
	      HttpSession session = se.getSession();
	      //session.setMaxInactiveInterval(1800);//in seconds
	  }

	  @Override
	  public void sessionDestroyed(HttpSessionEvent se) {
		 System.out.println("-- HttpSessionListener#sessionDestroyed invoked --");
		  HttpSession session=se.getSession();
		  removeSessionAttributes(session);
		  IjsProcessJOBkgBLSearchSvc bkgBLSearchSvc = 
		            new IjsProcessJOBkgBLSearchSvc((IjsProcessJOBkgBLSearchDao)getDao("ProcessJOBkgBLSearchDao",session.getServletContext()));
		  session.invalidate();
		  try {
			bkgBLSearchSvc.resetJO(session.getId(), "");
		} catch (IJSException e) {
			e.printStackTrace();
		}
		  
	  }
	  public IjsBaseDao getDao(String requestedDao,ServletContext sc){
	       return IjsDAOFactory.getInstance().getDao(requestedDao,sc);
	    }
	  private void removeSessionAttributes(HttpSession session){
		  if(session!=null){
			
			  if(session.getAttribute("userInfo")!=null){
				  session.removeAttribute("userInfo");
			  }
			  if(session.getAttribute("totalResultsForSVV")!=null){
				  session.removeAttribute("totalResultsForSVV");
			  }
			  if(session.getAttribute("paginatoinInfo")!=null){
				  session.removeAttribute("paginatoinInfo");
			  }
			  if(session.getAttribute("resultCountForJobOrders")!=null){
				  session.removeAttribute("resultCountForJobOrders");
			  }
			 
		  }
		  
	  }
}
