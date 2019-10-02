package com.rclgroup.dolphin.ijs.web.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.RequestProcessor;
/**
 * 
 * @author Ashish.1.Rawat
 * validate active user session
 */
public class IjsRequestProcessor extends RequestProcessor {
	private static final String LOAD_USER_ACTION="/loadUser.do";
	private static final String SESSION_EXPIRE_ACTION="/sessionExpiryError.do";
	private static final String USER_INFO="userInfo";
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN="Access-Control-Allow-Origin";
	private static final String ACCESS_CONTROL_ALLOW_HEADERS="Access-Control-Allow-Headers";
	private static final String ACCESS_CONTROL_EXPOSE_HEADERS="Access-Control-Expose-Headers";
	private static final String ASTRIC="*";
	private static final String ORIGIN="Origin, Content-Type,Accept";
	private static final String UTF="UTF-8";
	@Override
	protected boolean processPreprocess(HttpServletRequest request,
			HttpServletResponse response) {

		
		HttpSession session = request.getSession(false);
		request.getSession(true).getAttribute("userInfo");
		response.setCharacterEncoding(UTF);
	    response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ASTRIC);
	    response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS,ACCESS_CONTROL_ALLOW_ORIGIN);
	    response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS,ORIGIN);
		if (LOAD_USER_ACTION.equals(request.getServletPath())
				|| SESSION_EXPIRE_ACTION.equals(request.getServletPath()))
			return true;
		if (session != null && session.getAttribute(USER_INFO) != null)
			return true;
		else {
			try {
			//	request.getRequestDispatcher(SESSION_EXPIRE_ACTION).forward(
			//			request, response);
				System.out.println("<<<<<<<<<<TRY ENTERED>>>");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return true;
	}
}
