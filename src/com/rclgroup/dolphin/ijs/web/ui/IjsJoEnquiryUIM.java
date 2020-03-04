package com.rclgroup.dolphin.ijs.web.ui;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoEnquiryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoEnquiryVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;

import java.util.List;
import java.util.Map;

public class IjsJoEnquiryUIM extends IjsBaseActionForm {
    //##01 BEGIN
    private IjsJoEnquiryParamVO joEnquiryParam;
    //private IjsSearchResult<?> searchResult;
    private IjsJoEnquiryVO joEnquirySearch;
    private List<String> joEnquirySearchList; //##02
    private Map<String, String> joEnquirySearchResult; //##02

    public IjsJoEnquiryUIM() {
    }


    public void setJoEnquiryParam(IjsJoEnquiryParamVO joEnquiryParam) {
        this.joEnquiryParam = joEnquiryParam;
    }

    public IjsJoEnquiryParamVO getJoEnquiryParam() {
        return joEnquiryParam;
    }

//    public void setSearchResult(IjsSearchResult<?> searchResult) {
//        this.searchResult = searchResult;
//    }
//
//    public IjsSearchResult<?> getSearchResult() {
//        return searchResult;
//    }

    public void setJoEnquirySearch(IjsJoEnquiryVO joEnquirySearch) {
        this.joEnquirySearch = joEnquirySearch;
    }

    public IjsJoEnquiryVO getJoEnquirySearch() {
        return joEnquirySearch;
    }

    public void setJoEnquirySearchList(List<String> joEnquirySearchList) {
        this.joEnquirySearchList = joEnquirySearchList;
    }

    public List<String> getJoEnquirySearchList() {
        return joEnquirySearchList;
    }

    public void setJoEnquirySearchResult(Map<String, String> joEnquirySearchResult) {
        this.joEnquirySearchResult = joEnquirySearchResult;
    }

    public Map<String, String> getJoEnquirySearchResult() {
        return joEnquirySearchResult;
    }
}
