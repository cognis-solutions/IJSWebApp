/*-----------------------------------------------------------------------------------------------------------
IjsJOBookingBLSearchAction.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 02/11/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 02/11/17  NIIT       IJS            Booking/BL Search functionality 
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.common;

import java.util.List;

public class IjsJOSearchResults<T> {
    List<T> results;


    public void setResults(List<T> results) {
        this.results = results;
    }

    public List<T> getResults() {
        return results;
    }
}
