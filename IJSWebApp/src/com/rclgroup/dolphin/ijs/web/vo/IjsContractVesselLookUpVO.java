package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractVesselLookUpVO {
    private String vesselCode;
    private String vesselName;
    private String operatorCode;


    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorCode() {
        return operatorCode;
    }
}
