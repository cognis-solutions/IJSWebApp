package com.rclgroup.dolphin.ijs.web.ui;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoExemptedCustSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoExemptedCustSearchVO;
import java.util.List;
import java.util.Map;

public class IjsJoExemptedCustUIM extends IjsBaseActionForm {
    //##01 BEGIN
    private IjsJoExemptedCustSearchParamVO searchParam;
    private IjsJoExemptedCustSearchParamVO custSaveList;
    private List<IjsJoExemptedCustSearchParamVO> custDelList; //For cancel etc
   

    private IjsJoExemptedCustSearchVO joSaveListAll;

   // private IjsSearchResult<?> searchResult;
    private IjsJoExemptedCustSearchVO maintainSearch;
    private List<String> maintainSearchList; //##02
    private Map<String, String> maintainSearchResult; //##02

   

    public IjsJoExemptedCustUIM() {
    }


//    public void setSearchResult(IjsSearchResult<?> searchResult) {
//        this.searchResult = searchResult;
//    }
//
//    public IjsSearchResult<?> getSearchResult() {
//        return searchResult;
//    }


   
    public void setMaintainSearch(IjsJoExemptedCustSearchVO maintainSearch) {
        this.maintainSearch = maintainSearch;
    }

    public IjsJoExemptedCustSearchVO getMaintainSearch() {
        return maintainSearch;
    }

    public void setMaintainSearchList(List<String> maintainSearchList) {
        this.maintainSearchList = maintainSearchList;
    }

    public List<String> getMaintainSearchList() {
        return maintainSearchList;
    }

    public void setMaintainSearchResult(Map<String, String> maintainSearchResult) {
        this.maintainSearchResult = maintainSearchResult;
    }

    public Map<String, String> getMaintainSearchResult() {
        return maintainSearchResult;
    }

      

    public void setJoSaveListAll(IjsJoExemptedCustSearchVO joSaveListAll) {
        this.joSaveListAll = joSaveListAll;
    }

    public IjsJoExemptedCustSearchVO getJoSaveListAll() {
        return joSaveListAll;
    }

    public void setCustSaveList(IjsJoExemptedCustSearchParamVO custSaveList) {
        this.custSaveList = custSaveList;
    }

    public IjsJoExemptedCustSearchParamVO getCustSaveList() {
        return custSaveList;
    }

    public void setCustDelList(List<IjsJoExemptedCustSearchParamVO> custDelList) {
        this.custDelList = custDelList;
    }

    public List<IjsJoExemptedCustSearchParamVO> getCustDelList() {
        return custDelList;
    }

    public void setSearchParam(IjsJoExemptedCustSearchParamVO searchParam) {
        this.searchParam = searchParam;
    }

    public IjsJoExemptedCustSearchParamVO getSearchParam() {
        return searchParam;
    }
}
