
package com.rclgroup.dolphin.ijs.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.dao.IJSProcessNewSaveDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsCommonDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsJOLookupDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsProcessJOBkgBLSearchDao;
import com.rclgroup.dolphin.ijs.web.dao.impl.IJSProcessJONewSaveDaoImpl;
import com.rclgroup.dolphin.ijs.web.service.IJSProcessJONewSaveSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsJOLookupSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsJOUploadSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsProcessJOBkgBLSearchSvc;
import com.rclgroup.dolphin.ijs.web.ui.IJSProcessJONewSaveUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsJOLookupUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsProcessJOBkgBLSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

public class ProcessJONewSaveAction extends IjsBaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.execute(mapping, form, request, response);

		return null;
	}

	/**
	 * unmarshalJsonRequestToJava method converting json to java object
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void unmarshalJsonRequestToJava(HttpServletRequest request) throws Exception {  
		//unmarshal json to java pojo
																							
		
		
		try {
		 String ijsSearchJson = request.getParameter("data");
         setActionForm(new IJSProcessJONewSaveUIM());
         IJSProcessJONewSaveUIM actionForm = 
             (IJSProcessJONewSaveUIM)getActionForm();
         String testJson = new Gson().toJson(actionForm);
         IJSProcessJONewSaveUIM lookupUim = 
             new Gson().fromJson(ijsSearchJson, IJSProcessJONewSaveUIM.class);
         if (lookupUim != null) {
             setActionForm(lookupUim);
         } else {
             setActionForm(new IJSProcessJONewSaveUIM());
         }
     } catch (Exception e) {
         throw e;
     }
 }
	

	@Override public void performAction(HttpServletRequest request,
		  HttpServletResponse response) throws Exception { 
		// TODO Auto-generated method stub
		  
		  
		  //IJSProcessJONewSaveUIM actionForm = (IJSProcessJONewSaveUIM)getActionForm();
		 
		 IJSProcessJONewSaveUIM actionForm = 
		            (IJSProcessJONewSaveUIM)getActionForm();
		 IJSProcessJONewSaveSvc saveSvc = 
		            new IJSProcessJONewSaveSvc((IJSProcessNewSaveDao)getDao("processJONewSaveDao")
		                                     );
		/*
		 * IJSProcessJONewSaveSvc saveSvc = new
		 * IJSProcessJONewSaveSvc((IJSProcessNewSaveDao)getDao( "processJONewSaveDao"));
		 */ 
		  // IjsJOUploadSvc uploadSvc=new IjsJOUploadSvc((IjsCommonDao)getDao("commonDao")); 
		  HttpSession session = request.getSession(); 
		  IjsUserInfoVO userInfo = (IjsUserInfoVO)session.getAttribute("userInfo"); 
		  if (userInfo == null) { userInfo = loadUserData(request,response);
		  session.setAttribute("userInfo", userInfo); }
		  
		  String lstrSessionId = session.getId(); System.out.println("performDone");
		  
           if (IjsActionMethod.ADHOC_JO_SUMMARY.getAction().equals(actionForm.getAction())) {
              
              
              IJSProcessJONewSaveUIM newActionForm = 
            		  saveSvc.jOCostCalcAdhoc(actionForm.getRoutingList().getVendors(),actionForm.getRoutingList(),
            				  actionForm.getRoutingList().getLstJOSummaryParam(),actionForm.getAction(),
                                                   lstrSessionId,
                                                                                              userInfo != null ? 
                                                   userInfo.getUserId() : 
                                                   "");
              newActionForm.setAction("joSummeryAdhoc");
              setActionForm(newActionForm);
           }else if(IjsActionMethod.SEARCH_ROUTE_LIST.getAction().equals(actionForm.getAction()))
           {
        	   IJSProcessJONewSaveUIM newActionForm=
              saveSvc.getNewSaveJson(actionForm.getIjsLookupParam(),actionForm.getIjsLookupParam().getLstJOSummaryParam(),
                  actionForm.getAction(), 
                  userInfo != null ? 
                  userInfo.getUserId() : "", session );
        	   
        	   newActionForm.setAction("lookupRouteList");
               setActionForm(newActionForm);
           }
		  
		  }

	@Override
	public void marshalJavaToJson(HttpServletResponse response) throws Exception { // TODO Auto-generated method stub
																					//
		IJSProcessJONewSaveUIM actionForm = 
		            (IJSProcessJONewSaveUIM)getActionForm();;

		String jsonStr = new Gson().toJson(actionForm.getLookupSearchResult().getResult());
		response.getWriter().write(jsonStr);
		//System.out.println("RESPONCE>>>>" + jsonStr);
		System.out.println("JSON data  : " + jsonStr);

	}

}
