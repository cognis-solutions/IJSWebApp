/*-----------------------------------------------------------------------------------------------------------
IjsActionCode.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 21/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 21/09/17  NIIT       IJS            keeps Ijs Action codes
02 05/10/17  NIIT       IJS            keeps Ijs cost rate Action codes
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsActionCode {
    //## 01 BEGIN
    NEW("N"),EDIT("U"),COPY("C"),DELETE("D"),REVERT("R"),PENDING("P"),WAIT_LIST("W"),ENTRY("E"),NEWCHANGE("NC"),EDITCHANGE("EC"),
    //##02 BEGIN
    COST_APPROVE("CA"),
    COST_DELETE("CD"),
    COST_REJECT("CR");
    //##02 END
    String actionCode;
    
    IjsActionCode(String actionCode) {
        this.actionCode = actionCode;
    }
    public String getActionCode() {
        return actionCode;
    }
    //## 01 END
}
