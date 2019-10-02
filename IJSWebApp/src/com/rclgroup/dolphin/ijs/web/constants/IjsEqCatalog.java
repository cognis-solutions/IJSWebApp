/*-----------------------------------------------------------------------------------------------------------
IjsEqCatalog.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 05/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 05/10/17  NIIT       IJS            keeps IjsEqCatalog values
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsEqCatalog {
    //##01 BEGIN
    Chassis_trailer("Chassis/trailer", "S"),
    Box("Box","B"),
    Genset("Genset","G"),
    ASTERISK("*","*");
    
    IjsEqCatalog(String catalogValue, String catalogCode) {
        this.catalogCode = catalogCode;
        this.catalogValue = catalogValue;
    }
    
    String catalogCode;
    String catalogValue;

    public String getCatalogCode() {
        return catalogCode;
    }

    public String getCatalogValue() {
        return catalogValue;
    }
    //##01 END
}
