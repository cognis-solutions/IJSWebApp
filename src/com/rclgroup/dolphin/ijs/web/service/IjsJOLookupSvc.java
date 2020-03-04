package com.rclgroup.dolphin.ijs.web.service;

//import com.rclgroup.dolphin.ijs.web.common.IjsContractLookupResult;
import com.rclgroup.dolphin.ijs.web.common.IjsJOLookupResult;
import com.rclgroup.dolphin.ijs.web.common.IjsPaginationInfo;
import com.rclgroup.dolphin.ijs.web.common.IjsPaginationUtil;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractLookupDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsJOLookupDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
//import com.rclgroup.dolphin.ijs.web.ui.IjsContractLookupUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsJOLookupUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;


import java.util.List;

import javax.servlet.http.HttpSession;

public class IjsJOLookupSvc {
    IjsJOLookupDao joLookupDao = null;
    String lookUpName = null;

    public IjsJOLookupSvc(IjsJOLookupDao joLookupDao, 
                          String lookUpName) {
        this.joLookupDao = joLookupDao;
        this.lookUpName = lookUpName;
    }

    public IjsJOLookupUIM getLookupList(IjsLookupParamVO ijsLookupParamVO, 
                                              String lookupName, 
                                              String userInfo, 
                                              HttpSession session) throws IJSException {
        if (IjsActionMethod.SEARCH_ROUTE_LIST.getAction().equals(lookupName) || IjsActionMethod.SEARCH_EQUIPMENT.getAction().equals(lookupName)
            || IjsActionMethod.SEARCH_CONTAINER.getAction().equals(lookupName)|| IjsActionMethod.SEARCH_DGIMDG.getAction().equals(lookupName)
            || IjsActionMethod.SEARCH_REASON_CODE.getAction().equals(lookupName) || IjsActionMethod.SEARCH_BL_BKG_POPUP.getAction().equals(lookupName)
            || IjsActionMethod.SEARCH_JO_LOG_POPUP.getAction().equals(lookupName) || IjsActionMethod.SEARCH_FSC_LIST.getAction().equals(lookupName)
            || IjsActionMethod.SEARCH_REASON_CODE.getAction().equals(lookupName)
            ) {
            return transform(joLookupDao.getLookupList(lookupName, 
                                                             userInfo, 
                                                        ijsLookupParamVO));
        }
        else if(IjsActionMethod.SEARCH_EQUIPMENT.getAction().equals(lookupName))
        {
            Integer totalCount = 
                (Integer)session.getAttribute("totalResultsForSVV");
            if (totalCount == null || ijsLookupParamVO.isRequestChanged()) {
                // TODO
                totalCount = 
                        joLookupDao.getLookUpCount(lookupName, userInfo, ijsLookupParamVO);System.out.println("Total records found for the given search criteria :-  " + 
                                                   totalCount);
                session.setAttribute("totalResultsForSVV", totalCount);
            }
            List<?> list = null;
            IjsPaginationInfo sessionInfo = 
                (IjsPaginationInfo)session.getAttribute("paginatoinInfo");
            if (ijsLookupParamVO.getPageNo() == 0) {
                throw new IJSException("Invalid Page Number");
            }
            // if session contains list
            if (sessionInfo != null && !ijsLookupParamVO.isRequestChanged()) {
                System.out.println("********   Inside Session block    *******");
                list = IjsPaginationUtil.populateResults(sessionInfo, 
                                                         ijsLookupParamVO.getPageNo());
                if (list != null && list.size() > 0) {
                    return transformServVeslResults(list, totalCount, 
                                                    lookUpName);
                } else {
                    System.out.println("********   Inside Session block but exceeded limit session contains   *******");
                    return getLookUpResults(ijsLookupParamVO, lookupName, 
                                            userInfo, session, totalCount);
                }
            }
            // for 1st time request
            else {
                return getLookUpResults(ijsLookupParamVO, lookupName, userInfo, 
                                        session, totalCount.intValue());
            }
        }
        else{
            return transform(joLookupDao.getLookupList(lookupName, 
                                                             userInfo, 
                                                        ijsLookupParamVO));
        }
    }
  

    private <T> IjsJOLookupUIM transform(List<?> list) {
        IjsJOLookupUIM ijsJOLookupUIM = new IjsJOLookupUIM();
        IjsJOLookupResult<?> searchResult = 
            new IjsJOLookupResult<T>();
        searchResult.setResult(list);
        ijsJOLookupUIM.setLookupSearchResult(searchResult);
        //ijsContractLookupUIM.setTotalRecords(totalRecords);
        return ijsJOLookupUIM;
    }

    private IjsJOLookupUIM getLookUpResults(IjsLookupParamVO ijsLookupParamVO, 
                                                  String lookUpName, 
                                                  String userInfo, 
                                                  HttpSession session, 
                                                  int totalRecords) throws IJSException {
        IjsPaginationInfo paginationInfo = IjsPaginationUtil.populatingPaginationInfo(ijsLookupParamVO.getPageNo());
        ijsLookupParamVO.setRowStart(paginationInfo.getRecordStart());
        ijsLookupParamVO.setRowEnd(paginationInfo.getRecordEnd());
        System.out.println("********   Inside getLookUpResults() :-- Method for getting records  *******");
        List<?> resultList = 
            joLookupDao.getLookupList(lookUpName, userInfo, ijsLookupParamVO);System.out.println("List size return from DB :-   " + 
        resultList.size() + " In between of " + 
                  ijsLookupParamVO.getRowStart() + " - " + 
                  ijsLookupParamVO.getRowEnd());
        paginationInfo.setResults(resultList);
        paginationInfo.setOrderBy(ijsLookupParamVO.getOrderBy());
        session.setAttribute("paginatoinInfo", paginationInfo);
        List<?> subList = IjsPaginationUtil.populateResults(paginationInfo, 
                                                            ijsLookupParamVO.getPageNo());

        return transformServVeslResults(subList, totalRecords, lookUpName);

    }

    private IjsJOLookupUIM transformServVeslResults(List<?> list, 
                                                          Integer totalCount, 
                                                          String action) {
        IjsJOLookupUIM ijsJOLookupUIM = new IjsJOLookupUIM();
        ijsJOLookupUIM.setAction(action);
        IjsJOLookupResult<Object> searchResult = 
            new IjsJOLookupResult<Object>();
        searchResult.setResult(list);
        ijsJOLookupUIM.setLookupSearchResult(searchResult);
        ijsJOLookupUIM.setTotalRecords(totalCount);
        return ijsJOLookupUIM;
    }
    
    
    
    public IjsJOLookupUIM updateBkgBlLookupList(IjsLookupParamVO ijsLookupParamVO, 
                                              String action, 
                                              String userInfo,
                                              HttpSession session) throws IJSException {
        String lsterrorCd = 
            joLookupDao.updateBkgBl(ijsLookupParamVO,userInfo, action);
        
        return transform(lsterrorCd,action);
    }
    public IjsJOLookupUIM deleteLumpsum(List<String> lumpsumCostIds, String userInfo) throws IJSException {
			String lsterrorCd = 
			joLookupDao.deleteLumpsum(lumpsumCostIds, userInfo);
			
			return transform(lsterrorCd,IjsActionMethod.BOOKING_BL_DELETE_LUMPSUM.getAction());
	}
    
    private IjsJOLookupUIM transform(String result, String action) {
        IjsJOLookupUIM ijsJOLookupUIM = new IjsJOLookupUIM();
        ijsJOLookupUIM.setErrorCode(result);
        ijsJOLookupUIM.setAction(action);
        return ijsJOLookupUIM;

    }
}
