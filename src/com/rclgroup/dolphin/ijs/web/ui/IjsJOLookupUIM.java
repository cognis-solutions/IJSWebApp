package com.rclgroup.dolphin.ijs.web.ui;

//import com.rclgroup.dolphin.ijs.web.common.IjsContractLookupResult;
import com.rclgroup.dolphin.ijs.web.common.IjsJOLookupResult;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;

public class IjsJOLookupUIM extends IjsBaseActionForm {
    //##01 BEGIN
    private IjsLookupParamVO ijsLookupParam;
    private IjsJOLookupResult<?> lookupSearchResult;
    private int totalRecords;

    public IjsJOLookupUIM() {
    }


    public void setIjsLookupParamVO(IjsLookupParamVO ijsLookupParam) {
        this.ijsLookupParam = ijsLookupParam;
    }

    public IjsLookupParamVO getIjsLookupParamVO() {
        return ijsLookupParam;
    }


    public void setLookupSearchResult(IjsJOLookupResult<?> lookupSearchResult) {
        this.lookupSearchResult = lookupSearchResult;
    }

    public IjsJOLookupResult<?> getLookupSearchResult() {
        return lookupSearchResult;
    }
    //##01 END

    public void setIjsLookupParam(IjsLookupParamVO ijsLookupParam) {
        this.ijsLookupParam = ijsLookupParam;
    }

    public IjsLookupParamVO getIjsLookupParam() {
        return ijsLookupParam;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalRecords() {
        return totalRecords;
    }
}
