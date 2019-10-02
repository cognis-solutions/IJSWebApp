/*-----------------------------------------------------------------------------------------------------------
IjsContractRateResult.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            keeps Ijs Contract Rate Result
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.common;

import java.util.List;

public class IjsContractRateResult<IjsRateVO> {
    // ##01 BEGIN
    List<IjsRateVO> results;


    public void setResults(List<IjsRateVO> results) {
        this.results = results;
    }

    public List<IjsRateVO> getResults() {
        return results;
    }
    // ##01 END
}
