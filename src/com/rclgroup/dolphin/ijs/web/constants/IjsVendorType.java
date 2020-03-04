/*-----------------------------------------------------------------------------------------------------------
IjsVendorType.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 07/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 05/10/17  NIIT       IJS            for mapping vendor code and vendortype
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsVendorType {
    SUSBSIDIARY("Trade Vendors - Subsidiary","S"),
    ASSOCIATES("Trade Vendors - Associates","A"),
    THIRD_PARTY("Trade Vendors – 3 Party","p"),
    PARTNERS("Non Trade Vendors - Partners","T"),
    NON_SUSIDIARY("Non Trade Vendors - Subsidiary","U"),
    NON_ASSOCIATE("Non Trade Vendors - Associate","I"),
    OTHERS("Non Trade Vendors - Others","O");
    
    
    String vendorValue;
    String vendorCode;
    
    IjsVendorType(String vendorValue, String vendorCode) {
        this.vendorCode = vendorCode;
        this.vendorValue = vendorValue;
    }
    public String getVendorValue() {
        return vendorValue;
    }
    public String getVendorCode() {
        return vendorCode;
    }
}
