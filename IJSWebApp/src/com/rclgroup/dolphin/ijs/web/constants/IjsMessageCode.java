/*-----------------------------------------------------------------------------------------------------------
IjsMessageCode.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 21/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 21/09/17  NIIT       IJS            keeps the ijs message code 
02 05/10/17  NIIT       IJS            added message codes for update contract, suspend , delete contracts
                                        and cost rate related messages

-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsMessageCode {
    //##01 BEGIN
    IJS_COMM_INSERTED("IJS_MSG_1001"),
    //##01 END
    //##02 BEGIN
    IJS_COMM_UPDATE("IJS_MSG_1002"),
    IJS_COMM_DELETE("IJS_MSG_1003"),
    IJS_CNTR_DATE_EXPIRED("IJS_MSG_1004"),
    IJS_CNTR_SUSPENDED("IJS_MSG_1005"),
    IJS_CNTR_COST_RATE_APPROVE("IJS_MSG_1006"),
    IJS_CNTR_COST_RATE_DELETE("IJS_MSG_1007"),
    IJS_CNTR_COST_RATE_REJECTED("IJS_MSG_1008"),
    IJS_CNTR_COST_RATE_ADDED("IJS_MSG_1009"),
    IJS_CNTR_COST_RATE_UPDATED("IJS_MSG_1010"),
    //##02 END
    //##03 BEGIN
     IJS_CNTR_BILL_RATE_APPROVE("IJS_MSG_1011"),
     IJS_CNTR_BILL_RATE_DELETE("IJS_MSG_1012"),
     IJS_CNTR_BILL_RATE_REJECTED("IJS_MSG_1013"),
     IJS_CNTR_BILL_RATE_ADDED("IJS_MSG_1014"),
     IJS_CNTR_BILL_RATE_UPDATED("IJS_MSG_1015");
    String msgCode;
    
    IjsMessageCode(String msgCode) {
        this.msgCode = msgCode;
    }
    public String getMsgCode() {
        return msgCode;
    }
}
