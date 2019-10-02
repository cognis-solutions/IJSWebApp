package com.rclgroup.dolphin.ijs.web.vo;

public class IjsReasonCodeLookUpVO {
    private String ReasonCode;
    private String Description;
    private String Status;


    public void setReasonCode(String reasonCode) {
        this.ReasonCode = reasonCode;
    }

    public String getReasonCode() {
        return ReasonCode;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getDescription() {
        return Description;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getStatus() {
        return Status;
    }
}
