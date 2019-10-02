/*-----------------------------------------------------------------------------------------------------------
TransportMode.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            Transport modes for IJS 
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.constants;

public enum TransportMode {
    //##01 BEGIN
    TRUCK("Truck","01","T"),RAIL("Rail","03","R"),FEEDER("Feeder","02","F"),BARGE("Barge","04","B");
    String transMode;
    String transCode;
    String transCodeX;
    TransportMode(String transMode,String transCode,String transCodeX){
        this.transMode=transMode;
        this.transCode=transCode;
        this.transCodeX=transCodeX;
    }
    public String getTransMode(){
        return transMode;
    }
    
    public String getTransCode(){
        return transCode;
    }
    public String getTransCodeX(){
        return transCodeX;
    }
    //##01 END
}
