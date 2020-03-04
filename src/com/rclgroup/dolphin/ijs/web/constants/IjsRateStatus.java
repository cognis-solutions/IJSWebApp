/*-----------------------------------------------------------------------------------------------------------
IjsRateStatus.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 05/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 05/10/17  NIIT       IJS            keeps Rate status  values
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsRateStatus {
    APPROVED("Approved","A"),
    OPEN("Open","O"),
    REJECTED("Rejected","R"),
    PENDING("Pending","P");
    
    String rateStatus;
    String rateStatusCode;
    
    IjsRateStatus(String rateStatus, String rateCode) {
        this.rateStatus = rateStatus;
        this.rateStatusCode = rateCode;
    }
    
    public String getRateStatus() {
        return rateStatus;
    }
    public String getRateStatusCode() {
        return rateStatusCode;
    }
}
