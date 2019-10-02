/*-----------------------------------------------------------------------------------------------------------
IjsContractLookupResult.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 07/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            keeps IjsContractLookupResult  
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.common;

import java.util.List;

public class IjsContractLookupResult<T> {
    //## 01 BEGIN
    List<?> result;


    public void setResult(List<?> list) {
        this.result = list;
    }

    public List<?> getResult() {
        return result;
    }
    //## 01 END
}
