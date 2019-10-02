/*-----------------------------------------------------------------------------------------------------------
IjsContractEqTypeVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 20/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 20/10/17  NIIT       IJS            value object for equipment type codes
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractEqTypeVO {
    private String eqType;
    private String description;


    public void setEqType(String eqType) {
        this.eqType = eqType;
    }

    public String getEqType() {
        return eqType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
