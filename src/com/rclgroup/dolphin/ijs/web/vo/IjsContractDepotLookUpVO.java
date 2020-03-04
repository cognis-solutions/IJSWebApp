/*-----------------------------------------------------------------------------------------------------------
IjsContractDepotLookUpVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 07/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            value object for Depot lookup screen
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractDepotLookUpVO {
    private String depot;
    private String depotName;
    private String depotPort;
    private String fsc;
    private String pointCode;
    private String status;
    private String countryName;
    private String currencyCode;

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotPort(String depotPort) {
        this.depotPort = depotPort;
    }

    public String getDepotPort() {
        return depotPort;
    }

    public void setFsc(String fsc) {
        this.fsc = fsc;
    }

    public String getFsc() {
        return fsc;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getPointCode() {
        return pointCode;
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

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
