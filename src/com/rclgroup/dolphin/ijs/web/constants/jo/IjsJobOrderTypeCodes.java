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
package com.rclgroup.dolphin.ijs.web.constants.jo;

public enum IjsJobOrderTypeCodes {
    EXPORT("Export", "E"),IMPORT("Import", "I"),SEA_LEG("Sea Leg", "S"),INTER_TERMINAL("Inter Terminal","T"),
    ADHOC_EMPTY("Ad-Hoc Empty","A"), ADHOC_LADEN("Ad-Hoc Laden","L");
    
    String joType;
    String joCode;
    IjsJobOrderTypeCodes(String joType, String joCode) {
        this.joCode = joCode;
        this.joType = joType;
    }
    
    public String getJoType() {
        return joType;
    }
    public String getJoCode() {
        return joCode;
    }
}
