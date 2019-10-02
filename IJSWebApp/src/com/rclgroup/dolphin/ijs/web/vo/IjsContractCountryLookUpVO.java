/*-----------------------------------------------------------------------------------------------------------
IjsContractCountryLookUpVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 07/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            value object for country lookup screen
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractCountryLookUpVO {
    private String countryCode;
    private String countryName;
    private String status;


    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
