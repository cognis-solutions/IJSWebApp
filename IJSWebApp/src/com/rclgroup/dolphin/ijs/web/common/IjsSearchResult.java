/*-----------------------------------------------------------------------------------------------------------
IjsSearchResult.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            keeps IJS search result 
02 05/10/17  NIIT       IJS            changed generic type to support any type of results related to contract
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.common;

import java.util.List;

public class IjsSearchResult<T> {
    // ##01 BEGIN
    List<T> result;//##02


    public void setResult(List<T> result) {//##02
        this.result = result;
    }

    public List<T> getResult() {//##02
        return result;
    }
    // ##01 END
}
