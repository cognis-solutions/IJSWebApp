package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsJoEnquiryVO {
    private String JoNumber;
    private String orderDate;
    private String approveDate;
    private Integer createdBy;
    private String vendorID;
    private String jobOrdType;
    private String vendorName;
    private String detailType;
    private String detailVersion;
    private String fromLoaction;
    private String toLocation;
    private String fromTerminal;
    private String toTerminal;
    private String startDate;
    private String completeDate;
    private int amount;
    private int amountUSD;
    private String currency;
    private String routingId;
    private String status;
    private String FSC;
    private List<String> contDetailJO;


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

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getCreatedBy() {
        return createdBy;
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmountUSD(int amountUSD) {
        this.amountUSD = amountUSD;
    }

    public int getAmountUSD() {
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

    public void setContDetailJO(List<String> contDetailJO) {
        this.contDetailJO = contDetailJO;
    }

    public List<String> getContDetailJO() {
        return contDetailJO;
    }
}
