/*-----------------------------------------------------------------------------------------------------------
IjsCalcMethod.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 05/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 05/10/17  NIIT       IJS            keeps IjsCalcMethod values
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsCalcMethod {
    //##01 BEGIN
    Tier_UOM("Tier*UOM", "1"),
    Fix_UOM("Fix*UOM" , "2"),
    Tier_Amount("Tier Amount" , "3"),
    Fix_Amount("Fix Amount" , "4");
    
    IjsCalcMethod(String calcValue , String calcType) {
        this.calcType = calcType;
        this.calcValue = calcValue;
    }
    
    String calcValue;
    String calcType;

    public String getCalcValue() {
        return calcValue;
    }

    public String getCalcType() {
        return calcType;
    }
    //##01 END
}
