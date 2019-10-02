/*-----------------------------------------------------------------------------------------------------------
IjsContractPointLookUpVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 07/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            value object for point lookup screen
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractPointLookUpVO {
    private String pointCode;
    private String poingName;
    private String countryCode ;
    private String zoneCode;
    private String stateCode;
    private String status;
    private String countryName;
    private String fsc;
    private String currencyCode;


    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getPointCode() {
        return pointCode;
    }

    public void setPoingName(String poingName) {
        this.poingName = poingName;
    }

    public String getPoingName() {
        return poingName;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateCode() {
        return stateCode;
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
