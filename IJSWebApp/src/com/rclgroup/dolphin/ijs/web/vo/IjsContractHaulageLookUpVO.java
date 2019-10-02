/*-----------------------------------------------------------------------------------------------------------
IjsContractHaulageLookUpVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 07/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            value object for Haulage lookup  screen
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractHaulageLookUpVO {
    private String haulageLocationCode;
    private String inlandPoint ;
    private String hulageLocationName;
    private String status;
    private String countryName;
    private String fsc;
    private String currencyCode;

    public void setHaulageLocationCode(String haulageLocationCode) {
        this.haulageLocationCode = haulageLocationCode;
    }

    public String getHaulageLocationCode() {
        return haulageLocationCode;
    }

    public void setInlandPoint(String inlandPoint) {
        this.inlandPoint = inlandPoint;
    }

    public String getInlandPoint() {
        return inlandPoint;
    }

    public void setHulageLocationName(String hulageLocationName) {
        this.hulageLocationName = hulageLocationName;
    }

    public String getHulageLocationName() {
        return hulageLocationName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setFsc(String fsc) {
        this.fsc = fsc;
    }

    public String getFsc() {
        return fsc;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
