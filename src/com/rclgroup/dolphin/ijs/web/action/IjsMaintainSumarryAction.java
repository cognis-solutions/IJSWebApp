
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
import com.rclgroup.dolphin.ijs.web.dao.IjsMaintainSumaarryDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsProcessJOBkgBLSearchDao;
import com.rclgroup.dolphin.ijs.web.dao.impl.IJSProcessJONewSaveDaoImpl;
import com.rclgroup.dolphin.ijs.web.service.IJSProcessJONewSaveSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsJOLookupSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsJOUploadSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsMaintainSumaarrySvc;
import com.rclgroup.dolphin.ijs.web.service.IjsProcessJOBkgBLSearchSvc;
import com.rclgroup.dolphin.ijs.web.ui.IJSProcessJONewSaveUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsJOLookupUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsMaintainSumaarryUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsProcessJOBkgBLSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

public class IjsMaintainSumarryAction extends IjsBaseAction {

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
																							
		/*
		 * try {
		 * 
		 * String ijsSaveJson = getJSONDataFromRequest(request);
		 * 
		 * setActionForm(new IJSProcessJONewSaveUIM());
		 * 
		 * IJSProcessJONewSaveUIM saveUim = new Gson().fromJson(ijsSaveJson,
		 * IJSProcessJONewSaveUIM.class);
		 * 
		 * if (saveUim != null) { setActionForm(saveUim); } else { setActionForm(new
		 * IJSProcessJONewSaveUIM()); } }catch(Exception e) { throw e; }
		 */
		
		
		try {
		 String ijsSearchJson = request.getParameter("data");
         setActionForm(new IjsMaintainSumaarryUIM());
         IjsMaintainSumaarryUIM actionForm = 
             (IjsMaintainSumaarryUIM)getActionForm();
         String testJson = new Gson().toJson(actionForm);
      //   System.out.println("Request : " + ijsSearchJson);
         IjsMaintainSumaarryUIM lookupUim = 
             new Gson().fromJson(ijsSearchJson, IjsMaintainSumaarryUIM.class);
         if (lookupUim != null) {
             setActionForm(lookupUim);
         } else {
             setActionForm(new IjsMaintainSumaarryUIM());
         }
     } catch (Exception e) {
         throw e;
     }
 }
	

	@Override public void performAction(HttpServletRequest request,
		  HttpServletResponse response) throws Exception { 
		// TODO Auto-generated method stub
		  
		  
		  //IJSProcessJONewSaveUIM actionForm = (IJSProcessJONewSaveUIM)getActionForm();
		 
		IjsMaintainSumaarryUIM actionForm = 
		            (IjsMaintainSumaarryUIM)getActionForm();
		IjsMaintainSumaarrySvc saveSvc = 
		            new IjsMaintainSumaarrySvc((IjsMaintainSumaarryDao)getDao("IjsMaintainSumaarryDao")
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
		  IjsMaintainSumaarryUIM newActionForm = 
        		  saveSvc.jOSumarry(actionForm.getJoSummary(),
                                               lstrSessionId,
                                               userInfo != null ? 
                                               userInfo.getUserId() : 
                                               "");
         
          setActionForm(newActionForm);
           
		  
		  }

	@Override
	public void marshalJavaToJson(HttpServletResponse response) throws Exception { // TODO Auto-generated method stub
																					//
		IjsMaintainSumaarryUIM actionForm = 
		            (IjsMaintainSumaarryUIM)getActionForm();;

		String jsonStr = new Gson().toJson(actionForm.getLookupSearchResult().getResult());
		response.getWriter().write(jsonStr);
		//System.out.println("RESPONCE>>>>" + jsonStr);
		System.out.println("JSON data  : " + jsonStr);

	}

}


