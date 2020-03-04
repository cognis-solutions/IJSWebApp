package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.dao.impl.IjsJoEnquiryJdbcDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsJoEnquirySvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsJoEnquiryUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsBkgBLScreenSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoEnquiryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoEnquiryVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IjsJoEnquiryAction extends IjsBaseAction {
    List<IjsJoEnquiryUIM> resultList;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        super.execute(mapping, form, request, response);
        return null;
    }

    /**
     * unmarshalJsonRequestToJava method converting json to java object
     * @param request
     * @throws Exception
     */
    public void unmarshalJsonRequestToJava(HttpServletRequest request) throws Exception {
        // unmarshal json to java pojo
        try {
            System.out.println("[IjsContractSearchAction] unmarshalJsonRequestToJava:");
            String ijsSearchJson = getJSONDataFromRequest(request);
          //  System.out.println("input data for search ---->" + ijsSearchJson);
            setActionForm(new IjsJoEnquiryUIM());
            IjsJoEnquiryUIM uim = new IjsJoEnquiryUIM();
            IjsJoEnquiryUIM actionForm = 
                (IjsJoEnquiryUIM)getActionForm();
            String jsonStr = new Gson().toJson(actionForm);
          //  System.out.println("jsonStr in unmarshalling:" + jsonStr);
            IjsJoEnquiryUIM searchUim = 
                new Gson().fromJson(ijsSearchJson, 
                                    IjsJoEnquiryUIM.class);
            if (searchUim != null) {
                setActionForm(searchUim);
            } else {
                setActionForm(new IjsJoEnquiryUIM());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * marshalJavaToJson method converting java object to json
     * @param response
     * @throws Exception
     */
    public void marshalJavaToJson(HttpServletResponse response) throws Exception {
        System.out.println("[IjsMaintainJOSearchUIM] marshalJavaToJson:");
        IjsJoEnquiryUIM actionForm = 
            (IjsJoEnquiryUIM)getActionForm();
        String jsonStr = null;
        if (IjsActionMethod.PROCESS_JO_SEARCH_BOOKING_BL.getAction().equals(actionForm.getAction())) {
            if (actionForm.getSearchResult() != null && 
                actionForm.getSearchResult().getResult() != null) {
                jsonStr = 
                        new Gson().toJson(actionForm.getSearchResult().getResult());
            } else if (actionForm.getErrorCode() != null) {
                jsonStr = new Gson().toJson(actionForm);
            }
            response.getWriter().write(jsonStr);
        }
        //## 02 END

        //##04 BEGIN
        else if (IjsActionMethod.EDIT.getAction().equals(actionForm.getAction())) {
            //TODO EDIT Contract
            if (actionForm.getErrorCode() != null) {
                jsonStr = new Gson().toJson(actionForm);
                response.getWriter().write(jsonStr);
            }
        } else if (IjsActionMethod.DELETE.getAction().equals(actionForm.getAction())) {
            //TODO DELETE Contract
            if (actionForm.getJoEnquirySearchResult() != null && 
                actionForm.getJoEnquirySearchResult().size() > 0) {
                jsonStr = new Gson().toJson(actionForm);
                response.getWriter().write(jsonStr);
            }
        } else {
            actionForm = new IjsJoEnquiryUIM();
            actionForm.setErrorCode("Error on downloading");
            jsonStr = new Gson().toJson(actionForm);
            response.getWriter().write(jsonStr);
        }
        //System.out.println("jsonStr:" + jsonStr);

        // response.getWriter().write(jsonStr);
    }


    /**
     * performAction method processing request to service  and getting the result from service
     * @param request
     */
    public void performAction(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("[IjsMaintainJoSearchAction] performAction:");
        IjsJoEnquiryUIM actionForm = 
            (IjsJoEnquiryUIM)getActionForm();
        IjsJoEnquirySvc BkgBLSearchSvc = 
            new IjsJoEnquirySvc((IjsJoEnquiryJdbcDao)getDao("joEnquiryDao"));
        HttpSession session = request.getSession();
        IjsUserInfoVO userInfo = 
            (IjsUserInfoVO)session.getAttribute("userInfo");
        //contractTemplateDownload
        if (userInfo == null) {
            userInfo = loadUserData(request,response);
            session.setAttribute("userInfo", userInfo);
        }

        try {
            if (IjsActionMethod.LOAD.getAction().equals(actionForm.getAction())) {
                actionForm.setUserInfo(loadUserData(request,response));
            }
            // 02 START
            else if (IjsActionMethod.PROCESS_JO_SEARCH_BOOKING_BL.getAction().equals(actionForm.getAction())) {
                System.out.println("[IjsContractSearchAction] SEARCH:");
                IjsJoEnquiryUIM newActionForm = 
                    BkgBLSearchSvc.searchBookingBL(actionForm.getJoEnquiryParam(), 
                                                   userInfo != null ? 
                                                   userInfo.getUserId() : "");
                newActionForm.setAction("maintainJoSearch");
                setActionForm(newActionForm);

            }
            // 02 END
            // 03 START
            else if (IjsActionMethod.NEW.getAction().equals(actionForm.getAction()) || IjsActionMethod.EDIT.getAction().equals(actionForm.getAction())) {
                //TO-DO NEW  or EDIT Contract
                setActionForm(BkgBLSearchSvc.saveOrUpdateContract(actionForm.getJoEnquirySearch(), 
                                                                  userInfo != 
                                                                  null ? 
                                                                  userInfo.getUserId() : 
                                                                  "", 
                                                                  actionForm.getAction()));
            }

        } catch (IJSException ie) {
            actionForm.setErrorCode(ie.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String path = new IjsJoEnquiryAction().getServlet().getServletContext().getRealPath("/");System.out.println("Pathe " + 
                                                                                                                    path);
    }

    public void setResultList(List<IjsJoEnquiryUIM> resultList) {
        this.resultList = resultList;
    }

    public List<IjsJoEnquiryUIM> getResultList() {
        return resultList;
    }

    private void getmockIjsContractSearchVO(IjsJoEnquiryUIM actionForm) {
        IjsJoEnquiryVO searchVO1 = new IjsJoEnquiryVO();

        actionForm.setJoEnquirySearch(searchVO1);
        actionForm.setAction("search");
        IjsBkgBLScreenSearchParamVO<IjsJoEnquiryParamVO> contractParam = 
            new IjsBkgBLScreenSearchParamVO<IjsJoEnquiryParamVO>();
        IjsJoEnquiryParamVO contractParams = 
            new IjsJoEnquiryParamVO();
        //contractParams.setFindIn("VendorName");
        //contractParams.setFindValue("MUMBAI PORT");
        contractParam.setTransType("Truck");
        contractParam.setStartDate("09/08/2017");
        contractParam.setEndDate("15/08/2017");
        contractParam.setProcessJoParam(contractParams);
        //        /actionForm.setProcessJoParam(contractParam);

    }

    private void getMockIjsContractSearchVOs(IjsJoEnquiryUIM actionForm) {
        IjsJoEnquiryVO searchVO1 = new IjsJoEnquiryVO();
        IjsJoEnquiryVO searchVO2 = new IjsJoEnquiryVO();
        IjsJoEnquiryVO searchVO3 = new IjsJoEnquiryVO();
        IjsSearchResult<IjsJoEnquiryVO> result = 
            new IjsSearchResult<IjsJoEnquiryVO>();
        List<IjsJoEnquiryVO> lstContarcts = 
            new ArrayList<IjsJoEnquiryVO>();
        //FIRST RECORD

        lstContarcts.add(searchVO3);
        //END OF delete below code
        result.setResult(lstContarcts);
        actionForm.setSearchResult(result);
    }

    private void getMockIjsContractSearchParams(IjsJoEnquiryUIM actionForm) {
        IjsBkgBLScreenSearchParamVO<IjsJoEnquiryParamVO> searchParam = 
            new IjsBkgBLScreenSearchParamVO<IjsJoEnquiryParamVO>();
        IjsJoEnquiryParamVO contractParam = 
            new IjsJoEnquiryParamVO();
        //contractParam.setFindIn("Vendor");
        //contractParam.setFindValue("VEND13");
        searchParam.setProcessJoParam(contractParam);
        searchParam.setStartDate("10/08/2017");
        searchParam.setEndDate("15/09/2017");
        searchParam.setTransType("truck;");
        //actionForm.setProcessJoParam(searchParam);
    }

    private void getMockIjsNewContract(IjsJoEnquiryUIM actionForm) {
        IjsJoEnquiryVO searchVO1 = new IjsJoEnquiryVO();

        actionForm.setJoEnquirySearch(searchVO1);
    }
    // 01 END
}
