package com.rclgroup.dolphin.ijs.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.rclgroup.dolphin.ijs.web.dao.IJSProcessNewSaveDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractDateValidateDao;
import com.rclgroup.dolphin.ijs.web.service.IJSProcessJONewSaveSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsContractDateValidateSvc;
import com.rclgroup.dolphin.ijs.web.ui.IJSProcessJONewSaveUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

public class IjsContractDateValidateAction extends IjsBaseAction {

	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.execute(mapping, form, request, response);

		return null;
	}
	
	
	@Override
	public void unmarshalJsonRequestToJava(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		
	      try{
	            //System.out.println("[IjsContractSearchAction] unmarshalJsonRequestToJava:");
	            String ijsSearchJson=getJSONDataFromRequest(request);
	           // System.out.println("input data for search ---->" + ijsSearchJson);
	            setActionForm(new IjsContractSearchUIM());
	            IjsContractSearchUIM uim =  new IjsContractSearchUIM();
	            //IjsContractSearchUIM actionForm=(IjsContractSearchUIM)getActionForm();
	          // String jsonStr = new Gson().toJson(actionForm);
	          // System.out.println("jsonStr in unmarshalling:" +jsonStr);
	            IjsContractSearchUIM searchUim= new Gson().fromJson(ijsSearchJson,IjsContractSearchUIM.class);
	 //IjsContractSearchUIM searchUim= getMockContractSave();
	            if(searchUim!=null){
	                setActionForm(searchUim);
	            }else{
	                setActionForm(new IjsContractSearchUIM());
	            }
	        }catch(Exception e){
	            throw e;
	        }
	    
	 
		
	}

	@Override
	public void performAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		
		IjsContractSearchUIM actionForm = 
		            (IjsContractSearchUIM)getActionForm();
		IjsContractDateValidateSvc saveSvc = 
		            new IjsContractDateValidateSvc((IjsContractDateValidateDao)getDao("ContractDateValidateDao"));
		
	
		 HttpSession session = request.getSession(); 
		  IjsUserInfoVO userInfo = (IjsUserInfoVO)session.getAttribute("userInfo"); 
		  if (userInfo == null) { userInfo = loadUserData(request,response);
		  session.setAttribute("userInfo", userInfo); }
		  
		  String lstrSessionId = session.getId(); System.out.println("performDone");
	
		  
		  IjsContractSearchUIM newActionForm = 
          		  saveSvc.dateValidate(actionForm.getContract(), 
                                                 lstrSessionId,
                                                                                            userInfo != null ? 
                                                 userInfo.getUserId() : 
                                                 "");
            
            setActionForm(newActionForm);
		  
	}

	@Override
	public void marshalJavaToJson(HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		IjsContractSearchUIM actionForm = 
	            (IjsContractSearchUIM)getActionForm();;

	String jsonStr = new Gson().toJson(actionForm.getSearchResult().getResult());
	response.getWriter().write(jsonStr);
	//System.out.println("RESPONCE>>>>" + jsonStr);
	System.out.println("JSON data  : " + jsonStr);

}
		
	

}
