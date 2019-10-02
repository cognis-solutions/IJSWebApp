/*-----------------------------------------------------------------------------------------------------------
IjsContractRateAction.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 07/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            Contract Rate Action for getting contract cost rate functionality 
02 05/10/17  NIIT       IJS            add, edit,approve,delete,reject cost Rate   functionality added
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractLookupDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractRateDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsContractLookupSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsContractRateSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractLookupUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractRateUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsCostRateSetupVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IjsContractRateAction extends IjsBaseAction {

    
    //## 01 BEGIN
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        super.execute(mapping,form,request,response);
        return null;
    }
    /**
     * unmarshalJsonRequestToJava method converts json to java object
     * @param request
     */
    public void unmarshalJsonRequestToJava(HttpServletRequest request) throws Exception{
        String ijsSearchJson=getJSONDataFromRequest(request);
        setActionForm(new IjsContractRateUIM());
       //System.out.println("unmarshal json str:"+ijsSearchJson);
        IjsContractRateUIM rateUim= new Gson().fromJson(ijsSearchJson,IjsContractRateUIM.class);
        if (rateUim != null) {
            setActionForm(rateUim);
        }
        else {
            setActionForm(new IjsContractRateUIM());
        }
    }
    /**
     * performAction method is interacts with service and gets results
     * @param request
     */
    public void performAction(HttpServletRequest request,HttpServletResponse response) {
        IjsContractRateUIM actionForm = (IjsContractRateUIM)getActionForm();
        IjsContractRateSvc rateSvc= new IjsContractRateSvc(
            (IjsContractRateDao)getDao("contractRateDao"));
        HttpSession session=request.getSession();
        IjsUserInfoVO userInfo=(IjsUserInfoVO)session.getAttribute("userInfo");
        if(userInfo==null){
            userInfo=loadUserData(request,response);
            session.setAttribute("userInfo",userInfo);
        }
        try {
            if (IjsActionMethod.SEARCH_RATES.getAction().equals(actionForm.getAction())) {
                setActionForm(rateSvc.getContractRateList(actionForm,
                    userInfo.getUserId(), actionForm.getAction()));    
            }
            //##02 BEGIN
            else if (IjsActionMethod.ADD_COST_RATE.getAction().equals(actionForm.getAction())
                || IjsActionMethod.EDIT_COST_RATE.getAction().equals(actionForm.getAction())) {
               setActionForm(rateSvc.saveOrUpdateCostRate(actionForm,
                    userInfo.getUserId())); 
            }
            else if (IjsActionMethod.APPROVE_COST_RATE.getAction().equals(actionForm.getAction())
                || IjsActionMethod.DELETE_COST_RATE.getAction().equals(actionForm.getAction())
                || IjsActionMethod.REJECT_COST_RATE.getAction().equals(actionForm.getAction())) {
               setActionForm(rateSvc.costRateMaintainance(actionForm.getCntrRateSeqList(),
                    userInfo.getUserId(), actionForm.getAction(), actionForm)); 
            }
            //##02 END
            //##03 BEGIN
             else if (IjsActionMethod.APPROVE_BILLING_RATE.getAction().equals(actionForm.getAction())
                 || IjsActionMethod.DELETE_BILLING_RATE.getAction().equals(actionForm.getAction())
                 || IjsActionMethod.REJECT_BILLING_RATE.getAction().equals(actionForm.getAction())) {
                setActionForm(rateSvc.billingRateMaintainance(actionForm.getCntrRateSeqList(),
                     userInfo.getUserId(), actionForm.getAction(), actionForm)); 
             }
            else if (IjsActionMethod.ADD_BILLING_RATE.getAction().equals(actionForm.getAction())
                || IjsActionMethod.EDIT_BILLING_RATE.getAction().equals(actionForm.getAction())) {
               setActionForm(rateSvc.saveOrUpdateBillingRate(actionForm,
                    userInfo.getUserId())); 
            }
            
            else if (IjsActionMethod.ADD_EDIT_COST_RATE_LIST.getAction().equals(actionForm.getAction())) {
               setActionForm(rateSvc.saveOrUpdateCostRateList(actionForm,
                    userInfo.getUserId())); 
            }
            
            else if (IjsActionMethod.ADD_BILLING_RATE_LIST.getAction().equals(actionForm.getAction())){
               setActionForm(rateSvc.saveOrUpdateBillingRateList(actionForm,userInfo.getUserId())); 
            }else if (IjsActionMethod.SAVE_OOG_SETUP.getAction().equals(actionForm.getAction())){
                setActionForm(rateSvc.saveOrUpdateOOGSetupList(actionForm.getCostRateSetup().getTerminalDepotCode(),actionForm.getCostRateSetup().getOogSetUpList(),userInfo.getUserId())); 
            }else if (IjsActionMethod.SEARCH_OOG_SETUP.getAction().equals(actionForm.getAction())){
                setActionForm(rateSvc.searchOOGSetup(actionForm.getCostRateSetup().getTerminalDepotCode(),userInfo.getUserId())); 
            }else if (IjsActionMethod.SAVE_PORT_SETUP.getAction().equals(actionForm.getAction())){
            	IjsCostRateSetupVO rateSetupVO=actionForm.getCostRateSetup();
                setActionForm(rateSvc.saveOrUpdatePortImdgSetup(rateSetupVO.getPortImdgType(),rateSetupVO.getTerminalDepotCode(),rateSetupVO.getPortList(),userInfo.getUserId())); 
            }else if (IjsActionMethod.SAVE_IMDG_SETUP.getAction().equals(actionForm.getAction())){
            	IjsCostRateSetupVO rateSetupVO=actionForm.getCostRateSetup();
                setActionForm(rateSvc.saveOrUpdatePortImdgSetup(rateSetupVO.getPortImdgType(),rateSetupVO.getTerminalDepotCode(),rateSetupVO.getImdgList(),userInfo.getUserId())); 
            }else if (IjsActionMethod.SEARCH_PORT_SETUP.getAction().equals(actionForm.getAction())||
            		IjsActionMethod.SEARCH_IMDG_SETUP.getAction().equals(actionForm.getAction())){
            	IjsCostRateSetupVO rateSetupVO=actionForm.getCostRateSetup();
                setActionForm(rateSvc.searchPortImdgSetup(rateSetupVO.getPortImdgType(),rateSetupVO.getTerminalDepotCode(),userInfo.getUserId())); 
            }
            
            
        } catch (IJSException e) {
            actionForm.setErrorCode(e.getMessage());
        }
    }
    /**
     * marshalJavaToJson method converts java object to json format
     * @param response
     * @throws IOException
     */
    public void marshalJavaToJson(HttpServletResponse response) throws IOException {
        IjsContractRateUIM actionForm=(IjsContractRateUIM)getActionForm();
        String jsonStr = null;
        //##01 BEGIN
        if (actionForm != null && actionForm.getAction().equals(IjsActionMethod.SEARCH_RATES.getAction())
            && actionForm.getRateResults() != null
            && actionForm.getRateResults().getResults() != null && actionForm.getRateResults().getResults().size() > 0)  {
            jsonStr = new Gson().toJson(actionForm);
        }
        //##01 END
        //##02 BEGIN
        else if (actionForm != null && actionForm.getAction().equals(IjsActionMethod.APPROVE_COST_RATE.getAction())
            || actionForm.getAction().equals(IjsActionMethod.REJECT_COST_RATE.getAction())
            || actionForm.getAction().equals(IjsActionMethod.DELETE_COST_RATE.getAction())) {
            jsonStr = new Gson().toJson(actionForm);
        }
        else if (IjsActionMethod.ADD_COST_RATE.getAction().equals(actionForm.getAction())
            || IjsActionMethod.EDIT_COST_RATE.getAction().equals(actionForm.getAction())) {
            jsonStr = new Gson().toJson(actionForm);
        }
        else if (IjsActionMethod.APPROVE_BILLING_RATE.getAction().equals(actionForm.getAction())
            || IjsActionMethod.DELETE_BILLING_RATE.getAction().equals(actionForm.getAction())
            || IjsActionMethod.REJECT_BILLING_RATE.getAction().equals(actionForm.getAction())) {
            jsonStr = new Gson().toJson(actionForm);
        }
        else if (IjsActionMethod.ADD_BILLING_RATE.getAction().equals(actionForm.getAction())
            || IjsActionMethod.EDIT_BILLING_RATE.getAction().equals(actionForm.getAction())) {
            jsonStr = new Gson().toJson(actionForm);
        }
        
        else if (IjsActionMethod.ADD_EDIT_COST_RATE_LIST.getAction().equals(actionForm.getAction())) {
            jsonStr = new Gson().toJson(actionForm);
        }else if (IjsActionMethod.SAVE_OOG_SETUP.getAction().equals(actionForm.getAction())
        		|| IjsActionMethod.SEARCH_OOG_SETUP.getAction().equals(actionForm.getAction())
        		||IjsActionMethod.SAVE_PORT_SETUP.getAction().equals(actionForm.getAction()) ||
        		IjsActionMethod.SAVE_IMDG_SETUP.getAction().equals(actionForm.getAction()) || 
        		IjsActionMethod.SEARCH_PORT_SETUP.getAction().equals(actionForm.getAction())||
        		IjsActionMethod.SEARCH_IMDG_SETUP.getAction().equals(actionForm.getAction())) {
            jsonStr = new Gson().toJson(actionForm.getCostRateSetup());
        }else if (IjsActionMethod.SAVE_PORT_SETUP.getAction().equals(actionForm.getAction()) ||
        		IjsActionMethod.SAVE_IMDG_SETUP.getAction().equals(actionForm.getAction())){
        	jsonStr = new Gson().toJson(actionForm.getCostRateSetup());
        }
        
        //##02 END
        else {
            jsonStr = new Gson().toJson(actionForm);
        }
        //System.out.println("Rate result json : " + jsonStr);
        response.getWriter().write(jsonStr);;
    }
    //##01 END

    private IjsRateVO getMockRateVO(IjsRateVO ijsRateVO) {
        ijsRateVO.setCalcMethod("1");
        ijsRateVO.setChargeCode("123");
        ijsRateVO.setCostRateSequenceNum(3);
        ijsRateVO.setCurrency("INR");
        ijsRateVO.setEndDate("10/04/2017");
        ijsRateVO.setEqCatq("S");
        ijsRateVO.setEqType("GP");
        ijsRateVO.setImdgDetails("IMDG");
        ijsRateVO.setImpOrExp("IMP");
        ijsRateVO.setLumpSum("Lump sum");
        ijsRateVO.setMtOrLaden("MT");
        ijsRateVO.setOogSetup("OOG");
        ijsRateVO.setPerTrip("true");
        ijsRateVO.setPortClassCode("PCC");
        ijsRateVO.setRate20("20");
        ijsRateVO.setRate40("40");
        ijsRateVO.setRate45("45");
        ijsRateVO.setRateBasis("S");
        ijsRateVO.setRateStatus("A");
        ijsRateVO.setService("SERV");
        //ijsRateVO.setSplCostByBlOrBooking("spl");
        ijsRateVO.setSplHandling("Spl");
        ijsRateVO.setStartDate("10/04/2017");
        ijsRateVO.setTerm("term");
        ijsRateVO.setUom("KG");
        ijsRateVO.setUpto(100);
        return ijsRateVO;
    }
    
   
}
