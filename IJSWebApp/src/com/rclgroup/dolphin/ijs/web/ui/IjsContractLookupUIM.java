 /*-----------------------------------------------------------------------------------------------------------
IjsContractLookupUIM.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            keeps records for IJS contract Lookups screen
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.ui;

import com.rclgroup.dolphin.ijs.web.common.IjsContractLookupResult;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;

public class IjsContractLookupUIM  extends IjsBaseActionForm {
    //##01 BEGIN
    private IjsLookupParamVO ijsLookupParam;
    private IjsContractLookupResult<?> lookupSearchResult;
    private int totalRecords;
    public IjsContractLookupUIM() {
    }


    public void setIjsLookupParamVO(IjsLookupParamVO ijsLookupParam) {
        this.ijsLookupParam = ijsLookupParam;
    }

    public IjsLookupParamVO getIjsLookupParamVO() {
        return ijsLookupParam;
    }


    public void setLookupSearchResult(IjsContractLookupResult<?> lookupSearchResult) {
        this.lookupSearchResult = lookupSearchResult;
    }

    public IjsContractLookupResult<?> getLookupSearchResult() {
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
