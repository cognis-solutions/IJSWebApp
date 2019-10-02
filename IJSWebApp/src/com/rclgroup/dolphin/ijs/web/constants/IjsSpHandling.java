package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsSpHandling {
    NORMAL("Normal", "N"),
    REEFER_DG("Reefer DG" , "RDG"),
    OOG_DG("OOG DG" , "ODG"),
    RF("RF" , "RF"),
    OOG("OOG" , "0G"),
    DG("DG" , "D1"),
    DOOR_AJAR("Door Ajar" , "DA"),
    OPEN_DOOR("Open Door" , "OD"),
    BBK("BBK" , "BBK");
    
    IjsSpHandling(String spHandlingValue , String spHandlingCd) {
        this.spHandlingCd = spHandlingCd;
        this.spHandlingValue = spHandlingValue;
    }
    
    String spHandlingValue;
    String spHandlingCd;

    public String getSpHandlingValue() {
        return spHandlingValue;
    }

    public String getSpHandlingCd() {
        return spHandlingCd;
    }
}
