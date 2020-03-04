/*-----------------------------------------------------------------------------------------------------------
IjsRateBasis.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 05/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 05/10/17  NIIT       IJS            keeps Rate basis  values
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsRateBasis {
    //##01 BEGIN
    Size_Type("Size/Type","S"),
    BKG_BL("BKG/BL","B"),
    Lump_Sum("Lump Sum" , "L");
    
    String rateBasisValue;
    String rateBasisCode;
    
    IjsRateBasis(String rateBasisValue, String rateBasisCode) {
        this.rateBasisCode = rateBasisCode;
        this.rateBasisValue = rateBasisValue;
    }
    public String getRateBasisValue() {
        return rateBasisValue;
    }
    public String getRateBasisCode() {
        return rateBasisCode;
    }
    //##01 END
}
