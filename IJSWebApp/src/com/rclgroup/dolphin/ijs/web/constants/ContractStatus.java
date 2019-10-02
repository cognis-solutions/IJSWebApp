/*-----------------------------------------------------------------------------------------------------------
ContractStatus.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            keeps Contract status 
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.constants;

public enum ContractStatus {
    //## 01 BEGIN
    ENTRY("Entry","E"),ACTIVE("Active","A"),SUSPENDED("Suspend","S"),PENDING("Pending","P");
    String status;
    String statusCode;
    
    ContractStatus(String status,String statusCode){
        this.status=status;
        this.statusCode=statusCode;
    }
    public String getStatus(){
        return status;
    }
    
    public String getStatusCode(){
        return statusCode;
    }
    //## 01 END
}
