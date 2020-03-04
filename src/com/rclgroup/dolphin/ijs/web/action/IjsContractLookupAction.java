/*-----------------------------------------------------------------------------------------------------------
IjsContractLookupAction.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 02/11/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 024/11/17  NIIT       IJS            lookup  action class for Terminal, Depot,Haulage lookup's
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;

import com.rclgroup.dolphin.ijs.web.dao.IjsContractLookupDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractSearchDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsContractLookupSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsContractSearchSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractLookupUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractSearchUIM;

import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
public class IjsContractLookupAction extends IjsBaseAction {

    //## 01 BEGIN

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        super.execute(mapping,form,request,response);
        return null;
    }
    /**
     * unmarshalJsonRequestToJava method is for unmarshaling json object to java object
     * @param request .
     * @throws Exception ex.
     */
    public void unmarshalJsonRequestToJava(HttpServletRequest request)  throws Exception {
        try{
            String ijsSearchJson=request.getParameter("data");
            setActionForm(new IjsContractLookupUIM());
            IjsContractLookupUIM actionForm = (IjsContractLookupUIM)getActionForm();
            String testJson = new Gson().toJson(actionForm);
          //  System.out.println("Request : " + ijsSearchJson);
            IjsContractLookupUIM lookupUim= new Gson().fromJson(ijsSearchJson,IjsContractLookupUIM.class);
            if (lookupUim != null) {
                setActionForm(lookupUim);
            }
            else {
                setActionForm(new IjsContractLookupUIM());
            }
        }
        catch(Exception e) {
            throw e;
        }
    }
    /**
     * performAction method will bring the results based on given criteria.
     * @param request
     * @throws IJSException
     */
    public void performAction(HttpServletRequest request,HttpServletResponse response) {
        IjsContractLookupUIM actionForm = (IjsContractLookupUIM)getActionForm();
        IjsContractLookupSvc lookupSvc= new IjsContractLookupSvc((IjsContractLookupDao)getDao("contractLookupDao"), actionForm.getAction().toString());
        HttpSession session=request.getSession();
        IjsUserInfoVO userInfo=(IjsUserInfoVO)session.getAttribute("userInfo");
        if(userInfo==null){
            userInfo=loadUserData(request,response);
            session.setAttribute("userInfo",userInfo);
            
        }

        try {
            IjsContractLookupUIM newActionForm = lookupSvc.getLookupList(actionForm.getIjsLookupParamVO()
                , actionForm.getAction(),userInfo!=null?userInfo.getUserId():"", session);
            if (newActionForm.getLookupSearchResult().getResult() != null)
               // System.out.println("Lookup result list is :" + newActionForm.getLookupSearchResult().getResult().size());
            setActionForm(newActionForm);
        }
        catch(IJSException ie){
            actionForm.setErrorCode(ie.getMessage());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * marshalJavaToJson will convert java objects into json format
     * @param response.
     * @throws IOException ex.
     */
    public void marshalJavaToJson(HttpServletResponse response) throws IOException {
        IjsContractLookupUIM actionForm=(IjsContractLookupUIM)getActionForm();
        String jsonStr = null;
        if (actionForm.getLookupSearchResult() != null && actionForm.getLookupSearchResult().getResult() != null
            && actionForm.getLookupSearchResult().getResult().size() > 0 
            && !IjsActionMethod.SEARCH_JOB_ORDER_SERVICE.getAction().equals(actionForm.getAction())) {
            jsonStr = new Gson().toJson(actionForm.getLookupSearchResult().getResult());
            
        }
        else if (actionForm.getLookupSearchResult() != null && actionForm.getLookupSearchResult().getResult() != null
            && actionForm.getLookupSearchResult().getResult().size() > 0 
            && IjsActionMethod.SEARCH_JOB_ORDER_SERVICE.getAction().equals(actionForm.getAction())) {
            jsonStr = new Gson().toJson(actionForm);
            
        }
        else if (actionForm.getLookupSearchResult() != null && actionForm.getLookupSearchResult().getResult() != null
            && actionForm.getLookupSearchResult().getResult().size() > 0 
            && IjsActionMethod.CUSTOMER_LOOKUP_SERVICE.getAction().equals(actionForm.getAction())) {
            jsonStr = new Gson().toJson(actionForm);
            
        }
        else if (actionForm.getErrorCode() != null) {
            jsonStr = new Gson().toJson(actionForm);
        }
        response.getWriter().write(jsonStr);
        //System.out.println("JSON data  : " + jsonStr);
    }
    //##01 END
}
