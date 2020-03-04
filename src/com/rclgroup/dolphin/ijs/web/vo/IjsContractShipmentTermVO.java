/*-----------------------------------------------------------------------------------------------------------
IjsContractShipmentTermVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 20/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 20/10/17  NIIT       IJS            value object for term codes
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractShipmentTermVO {
    private String termCode;
    private String description;


    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
