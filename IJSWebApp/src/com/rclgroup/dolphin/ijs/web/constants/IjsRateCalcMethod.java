/*-----------------------------------------------------------------------------------------------------------
IjsRateCalcMethod.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            keeps Ijs Contract Rate caluclation methods
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsRateCalcMethod {
    
    Tier_UOM("Tier*UOM" , "1") ,
    Fix_UOM("Fix*UOM" , "2") ,
    Tier_Amount("Tier Amount" , "3") , 
    Fix_Amount("Fix Amount" , "4");
    
    String calcMethodType;
    String calcMethodValue;
    IjsRateCalcMethod(String  calcMethodType , String calcMethodValue) {
        this.calcMethodType = calcMethodType;
        this.calcMethodValue = calcMethodValue;
    }
    
    public String getCalcMethodType() {
        return calcMethodType;
    }
    public String getCalcMethodValue() {
        return calcMethodValue;
    }
}
