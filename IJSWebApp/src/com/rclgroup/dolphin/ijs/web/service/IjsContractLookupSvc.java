/*-----------------------------------------------------------------------------------------------------------
IjsContractLookupSvc.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            lookup  srv class for Terminal, Depot,Haulage lookup's and thire tranformation
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.service;

import com.rclgroup.dolphin.ijs.web.common.IjsContractLookupResult;
import com.rclgroup.dolphin.ijs.web.common.IjsPaginationInfo;
import com.rclgroup.dolphin.ijs.web.common.IjsPaginationUtil;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractLookupDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractSearchDao;

import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractLookupUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;

import java.util.List;

import javax.servlet.http.HttpSession;

public class IjsContractLookupSvc {
    IjsContractLookupDao contractLookupDao = null;
    String lookUpName = null;
    public IjsContractLookupSvc(IjsContractLookupDao contractLookupDao, String lookUpName) {
        this.contractLookupDao = contractLookupDao;
        this.lookUpName = lookUpName;
    }

    public IjsContractLookupUIM getLookupList(IjsLookupParamVO ijsLookupParamVO, 
                              String lookupName, String userInfo, HttpSession session) throws IJSException {
        if (!IjsActionMethod.SEARCH_JOB_ORDER_SERVICE.getAction().equals(lookupName)) {
//          if (!IjsActionMethod.SEARCH_JOB_ORDER_SERVICE.getAction().equals(lookupName) && 
//              !IjsActionMethod.CUSTOMER_LOOKUP_SERVICE.getAction().equals(lookupName)) {
            return transform(contractLookupDao.getLookupList(lookupName , userInfo, ijsLookupParamVO));    
        }
//         else if (!IjsActionMethod.CUSTOMER_LOOKUP_SERVICE.getAction().equals(lookupName)) {
//            Integer totalCount = (Integer)session.getAttribute("totalResultsForCust");
//            if (totalCount == null  || ijsLookupParamVO.isRequestChanged()) {
//                totalCount = contractLookupDao.getLookUpCount(lookupName , userInfo, ijsLookupParamVO);
//                System.out.println("Total records found for the given search criteria :-  " + totalCount);
//                session.setAttribute("totalResultsForCust", totalCount);
//            }
//            if (ijsLookupParamVO.getPageNo() == 0 ) {
//                throw new IJSException("Invalid Page Number");
//            }
//            return getLookUpResults(ijsLookupParamVO, lookupName, userInfo, session, totalCount.intValue());
//         }
         else {
            Integer totalCount = (Integer)session.getAttribute("totalResultsForSVV");
            if (totalCount == null  || ijsLookupParamVO.isRequestChanged()) {
                // TODO
                totalCount = contractLookupDao.getLookUpCount(lookupName , userInfo, ijsLookupParamVO);
                System.out.println("Total records found for the given search criteria :-  " + totalCount);
                session.setAttribute("totalResultsForSVV", totalCount);
            }
            List<?> list = null;
            IjsPaginationInfo sessionInfo = (IjsPaginationInfo)session.getAttribute("paginatoinInfo");
            if (ijsLookupParamVO.getPageNo() == 0 ) {
                throw new IJSException("Invalid Page Number");
            }
            // if session contains list
            if (sessionInfo != null && !ijsLookupParamVO.isRequestChanged()) {
                System.out.println("********   Inside Session block    *******");
                list = IjsPaginationUtil.populateResults(sessionInfo, ijsLookupParamVO.getPageNo());
                if (list != null && list.size() > 0) {
                    return transformServVeslResults(list, totalCount, lookUpName);
                }
                else {
                    System.out.println("********   Inside Session block but exceeded limit session contains   *******");    
                    return getLookUpResults(ijsLookupParamVO, lookupName, userInfo, session,totalCount);
                }
            }
            // for 1st time request
            else {
                return getLookUpResults(ijsLookupParamVO, lookupName, userInfo, session, totalCount.intValue());
            }
        }
    }

  

    private IjsContractLookupUIM transform(List<?> list) {
        IjsContractLookupUIM ijsContractLookupUIM= new IjsContractLookupUIM();
        IjsContractLookupResult<Object> searchResult = new IjsContractLookupResult<Object>();
        searchResult.setResult(list);
        ijsContractLookupUIM.setLookupSearchResult(searchResult);
        //ijsContractLookupUIM.setTotalRecords(totalRecords);
        return ijsContractLookupUIM;
    }

    private IjsContractLookupUIM getLookUpResults(IjsLookupParamVO ijsLookupParamVO, 
                                                  String lookUpName, 
                                                  String userInfo, 
                                                  HttpSession session,
                                                  int totalRecords) throws IJSException {
        IjsPaginationInfo paginationInfo = IjsPaginationUtil
            .populatingPaginationInfo(ijsLookupParamVO.getPageNo());
        ijsLookupParamVO.setRowStart(IjsPaginationUtil.getRecordStart(ijsLookupParamVO.getPageNo(),ijsLookupParamVO.getNoOfRecPerPage()));
        ijsLookupParamVO.setRowEnd(IjsPaginationUtil.getRecordEnd(ijsLookupParamVO.getPageNo(),ijsLookupParamVO.getNoOfRecPerPage(),totalRecords));
        System.out.println("********   Inside getLookUpResults() :-- Method for getting records  *******");    
        List<?> resultList = contractLookupDao.getLookupList(lookUpName , userInfo, ijsLookupParamVO);
        System.out.println("List size return from DB :-   " + resultList.size() + " In between of " 
            + ijsLookupParamVO.getRowStart()
            + " - " + ijsLookupParamVO.getRowEnd());
//        paginationInfo.setResults(resultList);
//        paginationInfo.setOrderBy(ijsLookupParamVO.getOrderBy());
//        session.setAttribute("paginatoinInfo" , paginationInfo);
//        List<?> subList = IjsPaginationUtil.populateResults(paginationInfo, ijsLookupParamVO.getPageNo());
        
        return transformServVeslResults(resultList, totalRecords,lookUpName);
        
    }

    private <T> IjsContractLookupUIM transformServVeslResults(List<?> list, Integer totalCount , String action) {
        IjsContractLookupUIM ijsContractLookupUIM= new IjsContractLookupUIM();
        ijsContractLookupUIM.setAction(action);
        IjsContractLookupResult<?> searchResult = new IjsContractLookupResult<T>();
        searchResult.setResult(list);
        ijsContractLookupUIM.setLookupSearchResult(searchResult);
        ijsContractLookupUIM.setTotalRecords(totalCount);
        return ijsContractLookupUIM;
    }
}
