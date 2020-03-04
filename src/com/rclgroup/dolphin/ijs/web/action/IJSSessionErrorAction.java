package com.rclgroup.dolphin.ijs.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
/**
 * 
 * @author Ashish.1.Rawat
 * sends session expiry message to UI
 */
public class IJSSessionErrorAction extends IjsBaseAction {
	private static final String USER_SESSION_EXPIRED="userSessionExpired";
	 @Override
	    public ActionForward execute(ActionMapping mapping, ActionForm form, 
	                                 HttpServletRequest request, 
	                                 HttpServletResponse response) throws Exception {
	        super.execute(mapping,form,request,response);
	        return null;
	    }
    public void unmarshalJsonRequestToJava(HttpServletRequest request) {
    	//NA
    }

    public void performAction(HttpServletRequest request, 
                              HttpServletResponse response) {
    	//NA
    }

    public void marshalJavaToJson(HttpServletResponse response)throws Exception {
        String jsonStr = null;
       
        jsonStr = new Gson().toJson(USER_SESSION_EXPIRED);
        try {
            response.getWriter().write(jsonStr);
        } catch (IOException e) {
            System.out.println("Exception in marshalling");
            throw e;
        }
    }
}
