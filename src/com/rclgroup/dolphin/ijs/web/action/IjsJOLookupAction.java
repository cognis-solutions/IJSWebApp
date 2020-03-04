package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
//import com.rclgroup.dolphin.ijs.web.dao.IjsContractLookupDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsJOLookupDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
//import com.rclgroup.dolphin.ijs.web.service.IjsContractLookupSvc;
//import com.rclgroup.dolphin.ijs.web.ui.IjsContractLookupUIM;
import com.rclgroup.dolphin.ijs.web.service.IjsJOLookupSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsJOLookupUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IjsJOLookupAction extends IjsBaseAction {

    //## 01 BEGIN

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        super.execute(mapping, form, request, response);
        return null;
    }

    /**
     * unmarshalJsonRequestToJava method is for unmarshaling json object to java object
     * @param request .
     * @throws Exception ex.
     */
    public void unmarshalJsonRequestToJava(HttpServletRequest request) throws Exception {
        try {
            String ijsSearchJson = request.getParameter("data");
            setActionForm(new IjsJOLookupUIM());
            IjsJOLookupUIM actionForm = 
                (IjsJOLookupUIM)getActionForm();
            String testJson = new Gson().toJson(actionForm);
         //   System.out.println("Request : " + ijsSearchJson);
            IjsJOLookupUIM lookupUim = 
                new Gson().fromJson(ijsSearchJson, IjsJOLookupUIM.class);
            if (lookupUim != null) {
                setActionForm(lookupUim);
            } else {
                setActionForm(new IjsJOLookupUIM());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * performAction method will bring the results based on given criteria.
     * @param request
     * @throws IJSException
     */
    public void performAction(HttpServletRequest request,HttpServletResponse response) {
        IjsJOLookupUIM actionForm = 
            (IjsJOLookupUIM)getActionForm();
            
        IjsJOLookupSvc lookupSvc = 
            new IjsJOLookupSvc((IjsJOLookupDao)getDao("joLookupDao"), 
                                     actionForm.getAction().toString());
        HttpSession session = request.getSession();
        IjsUserInfoVO userInfo = 
            (IjsUserInfoVO)session.getAttribute("userInfo");
        if (userInfo == null) {
            userInfo = loadUserData(request,response);
            session.setAttribute("userInfo", userInfo);

        }

        try {
            
            if (IjsActionMethod.DELETE_BKG_BL_POPUP.getAction().equals(actionForm.getAction())) {
                   setActionForm(lookupSvc.updateBkgBlLookupList(actionForm.getIjsLookupParamVO(), 
                                                    actionForm.getAction(), 
                                                    userInfo != null ? 
                                                    userInfo.getUserId() : "", session));
                   
            }else if(IjsActionMethod.BOOKING_BL_DELETE_LUMPSUM.getAction().equals(actionForm.getAction())){
            	List<String> lumpsumIds=actionForm.getIjsLookupParamVO().getJoLumpsumIds();
            	setActionForm(lookupSvc.deleteLumpsum(lumpsumIds,userInfo != null ? 
                        userInfo.getUserId() : ""));
            }
            else{
            
            IjsJOLookupUIM newActionForm = 
                lookupSvc.getLookupList(actionForm.getIjsLookupParamVO(), 
                                        actionForm.getAction(), 
                                        userInfo != null ? 
                                        userInfo.getUserId() : "", session);
            if (newActionForm.getLookupSearchResult().getResult() != null)
//                System.out.println("Lookup result list is :" + 
//                                   newActionForm.getLookupSearchResult().getResult().size());
            setActionForm(newActionForm);
                        }
            
        } catch (IJSException ie) {
            actionForm.setErrorCode(ie.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * marshalJavaToJson will convert java objects into json format
     * @param response .
     * @throws IOException ex.
     */
    public void marshalJavaToJson(HttpServletResponse response) throws IOException {
        IjsJOLookupUIM actionForm = 
            (IjsJOLookupUIM)getActionForm();
        String jsonStr = null;
        if (actionForm.getLookupSearchResult() != null && 
            actionForm.getLookupSearchResult().getResult() != null && 
            actionForm.getLookupSearchResult().getResult().size() > 0 && !IjsActionMethod.SEARCH_JOB_ORDER_SERVICE.getAction().equals(actionForm.getAction())) {
            jsonStr = 
                    new Gson().toJson(actionForm.getLookupSearchResult().getResult());

        } else if (actionForm.getLookupSearchResult() != null && 
                   actionForm.getLookupSearchResult().getResult() != null && 
                   actionForm.getLookupSearchResult().getResult().size() > 0 && IjsActionMethod.SEARCH_JOB_ORDER_SERVICE.getAction().equals(actionForm.getAction())) {
            jsonStr = new Gson().toJson(actionForm);

        } else if(IjsActionMethod.BOOKING_BL_DELETE_LUMPSUM.getAction().equals(actionForm.getAction())){
        	jsonStr = new Gson().toJson(actionForm);
        }
        else if (actionForm.getErrorCode() != null) {
            jsonStr = new Gson().toJson(actionForm);
        }
        response.getWriter().write(jsonStr);
        System.out.println("JSON data  : " + jsonStr);
    }
    //##01 END
}
