/*-----------------------------------------------------------------------------------------------------------
IjsJOBookingBLSearchAction.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            Booking/BL Search functionality 
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.action.jo;

import com.google.gson.Gson;
import com.rclgroup.dolphin.ijs.web.action.IjsBaseAction;
import com.rclgroup.dolphin.ijs.web.constants.jo.IjsJOActionMethod;
import com.rclgroup.dolphin.ijs.web.dao.jo.IjsJOBookingBLSearchDao;
import com.rclgroup.dolphin.ijs.web.entity.jo.IjsJOBookingBLSearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.jo.IjsJOBookingBLSearchSvc;
import com.rclgroup.dolphin.ijs.web.ui.jo.IjsJOBookingBLSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;
import java.io.IOException;

import java.util.ArrayList;
import java.util.*;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IjsJOBookingBLSearchAction extends IjsBaseAction{
    
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        super.execute(mapping,form,request,response);
        
        System.out.println("[IjsContractSearchAction] execute:");
        return null;
    }
    
    public IjsJOBookingBLSearchAction() {
    }
    /**
     * unmarshalJsonRequestToJava method to convert json request into java object
     * @param request
     * @throws Exception
     */
    public void unmarshalJsonRequestToJava(HttpServletRequest request) throws Exception {
        try {
            System.out.println("[IjsContractSearchAction] unmarshalJsonRequestToJava:");
            String joSearchJson=getJSONDataFromRequest(request);
            setActionForm(new IjsJOBookingBLSearchUIM());
            IjsJOBookingBLSearchUIM actionForm=(IjsJOBookingBLSearchUIM)getActionForm();
            String jsonStr = new Gson().toJson(actionForm);
            System.out.println("jsonStr in unmarshalling:" +jsonStr);
             IjsJOBookingBLSearchUIM searchUim= new Gson().fromJson(joSearchJson,IjsJOBookingBLSearchUIM.class);
             if(searchUim!=null){
                 setActionForm(searchUim);
             }else{
                 setActionForm(new IjsJOBookingBLSearchUIM());
             }
        }
        catch(Exception e){
            throw e;
        }
        
    }
    /**
     * performAction method to perform action 
     * @param request
     */
    public void performAction(HttpServletRequest request,HttpServletResponse response) {
    Map<String,String> map= new HashMap<String,String>();
        System.out.println("[IjsContractSearchAction] performAction:");
        IjsJOBookingBLSearchUIM actionForm=(IjsJOBookingBLSearchUIM)getActionForm();
        IjsJOBookingBLSearchSvc joSearchSvc=new IjsJOBookingBLSearchSvc(
            (IjsJOBookingBLSearchDao)getDao("ijsJOBookingBLSearchDao"));
        HttpSession session = request.getSession(true);
        IjsUserInfoVO userInfo=(IjsUserInfoVO)session.getAttribute("userInfo");
        if(userInfo==null){
            userInfo=loadUserData(request,response);
            session.setAttribute("userInfo",userInfo);
        }
        try {
            if(IjsJOActionMethod.JO_SEARCH.getAction().equals(actionForm.getAction())) {
                setActionForm(joSearchSvc.searchContract(actionForm
                    ,userInfo!=null?userInfo.getUserId():"", request));
                actionForm.setUserInfo(loadUserData(request,response));
            }
        }
        catch(IJSException ie){
            actionForm.setErrorCode(ie.getMessage());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * marshalJavaToJson method for converting java object into json format
     * @param response
     * @throws IOException
     */
    public void marshalJavaToJson(HttpServletResponse response) throws IOException {
        System.out.println("[IjsContractSearchAction] marshalJavaToJson:");
        IjsJOBookingBLSearchUIM actionForm=(IjsJOBookingBLSearchUIM)getActionForm();
        String jsonStr = null;
        if(IjsJOActionMethod.JO_SEARCH.getAction().equals(actionForm.getAction())) {
            if(actionForm.getSearchResults()!=null && actionForm.getSearchResults().getResults() != null){
                jsonStr = new Gson().toJson(actionForm.getSearchResults().getResults());  
            }else if(actionForm.getErrorCode()!=null){
                jsonStr = new Gson().toJson(actionForm); 
            }
        }
        if (jsonStr != null) {
            System.out.println("jsonStr:" +jsonStr);
            response.getWriter().write(jsonStr);
        }
        else {
            actionForm.setErrorCode("Something went wrong");
            String error = new Gson().toJson(actionForm);
            response.getWriter().write(error);
        }
    }
    

}
