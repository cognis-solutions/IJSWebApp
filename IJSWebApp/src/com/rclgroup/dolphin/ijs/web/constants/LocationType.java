/*-----------------------------------------------------------------------------------------------------------
LocationType.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            Location types for IJS 
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.constants;

public enum LocationType {
    //##01 BEGIN
    DOOR("Door","D"),TERMINAL("Terminal","P"),HAULAGE("Haulage","H"),DEPOT("Depot","Y");
    String locType;
    String locCode;
    
    LocationType(String locType,String locCode){
        this.locType=locType;
        this.locCode=locCode;
    }
    public String getLocType(){
        return locType;
    }
    
    public String getLocCode(){
        return locCode;
    }
    //##01 END
}
