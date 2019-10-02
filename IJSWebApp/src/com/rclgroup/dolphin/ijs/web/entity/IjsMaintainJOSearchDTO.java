package com.rclgroup.dolphin.ijs.web.entity;

import java.util.List;

public class IjsMaintainJOSearchDTO extends IjsBaseDTO {
    private String JoNumber;
    private String orderDate;
    private String approveDate;
    private String createdBy;
    private String vendorID;
    private String jobOrdType;
    private String vendorName;
    private String detailType;
    private String detailVersion;
    private String fromLoaction;
    private String toLocation;
    private String fromLocType;
    private String toLocType;
    private String fromTerminal;
    private String toTerminal;
    private String startDate;
    private String completeDate;
    private double amount;
    private double amountUSD   ;
    private String currency;
    private String routingId;
    private String contractId;
    private String status;
    private String FSC;
    private String reasonCode;
    private String transMode;
    private String adhoc_yn;
    private String bk_bl_ad;
    private String user_type;//HQ,Location or others (HQ/LOC/OTH)
    private String SOCorCOC;
    private String priority;
    private List<IjsMaintainJOSearchContDetailDTO> contDetailJO;


    public void setJoNumber(String joNumber) {
        this.JoNumber = joNumber;
    }

    public String getJoNumber() {
        return JoNumber;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveDate() {
        return approveDate;
    }

    

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setJobOrdType(String jobOrdType) {
        this.jobOrdType = jobOrdType;
    }

    public String getJobOrdType() {
        return jobOrdType;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailVersion(String detailVersion) {
        this.detailVersion = detailVersion;
    }

    public String getDetailVersion() {
        return detailVersion;
    }

    public void setFromLoaction(String fromLoaction) {
        this.fromLoaction = fromLoaction;
    }

    public String getFromLoaction() {
        return fromLoaction;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setFromTerminal(String fromTerminal) {
        this.fromTerminal = fromTerminal;
    }

    public String getFromTerminal() {
        return fromTerminal;
    }

    public void setToTerminal(String toTerminal) {
        this.toTerminal = toTerminal;
    }

    public String getToTerminal() {
        return toTerminal;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmountUSD(double amountUSD) {
        this.amountUSD = amountUSD;
    }

    public double getAmountUSD() {
        return amountUSD;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setRoutingId(String routingId) {
        this.routingId = routingId;
    }

    public String getRoutingId() {
        return routingId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setFSC(String fSC) {
        this.FSC = fSC;
    }

    public String getFSC() {
        return FSC;
    }


    

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setContDetailJO(List<IjsMaintainJOSearchContDetailDTO> contDetailJO) {
        this.contDetailJO = contDetailJO;
    }

    public List<IjsMaintainJOSearchContDetailDTO> getContDetailJO() {
        return contDetailJO;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setFromLocType(String fromLocType) {
        this.fromLocType = fromLocType;
    }

    public String getFromLocType() {
        return fromLocType;
    }

    public void setToLocType(String toLocType) {
        this.toLocType = toLocType;
    }

    public String getToLocType() {
        return toLocType;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public String getTransMode() {
        return transMode;
    }

    public void setAdhoc_yn(String adhoc_yn) {
        this.adhoc_yn = adhoc_yn;
    }

    public String getAdhoc_yn() {
        return adhoc_yn;
    }

    public void setBk_bl_ad(String bk_bl_ad) {
        this.bk_bl_ad = bk_bl_ad;
    }

    public String getBk_bl_ad() {
        return bk_bl_ad;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setSOCorCOC(String sOCorCOC) {
        this.SOCorCOC = sOCorCOC;
    }

    public String getSOCorCOC() {
        return SOCorCOC;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }
}
