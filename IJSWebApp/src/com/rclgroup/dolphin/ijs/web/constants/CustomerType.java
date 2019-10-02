package com.rclgroup.dolphin.ijs.web.constants;

public enum CustomerType {
    //##01 BEGIN
    SHIPPER("Shipper", "S"),
    CONSIGNEE("Consignee", "C")
    ;
    String custType;
    String custCode;

    CustomerType(String custType, String custCode) {
        this.custType = custType;
        this.custCode = custCode;
    }

    public String getCustType() {
        return custType;
    }

    public String getCustCode() {
        return custCode;
    }
    //##01 END
}
