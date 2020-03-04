package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;


public class IjsProcessJOSumContDtlVO {
    private String bkgOrBLNumber;
    private String bkgOrBLType;
    private String jobOrder;
    
    private String vendorCode;
    private String vendorName;
    private String contractNumber;
    private String joDate;
    private String mtOrLaden;
    private String type;
    private String OOG;
    private String days;
    private String hours;
    private String amount;
    private String rate;
    
    private String quantity;
    private String currency;
    private String amountUsd;
    private String paymentFSC;
    private String priority;
    
    private String routingId;
    private String size;
    private String slNumber;
    private String rateBasis;
    //CR#04 START
    private String lumpsumId;
    //CR#04 END


    public void setBkgOrBLNumber(String bkgOrBLNumber) {
        this.bkgOrBLNumber = bkgOrBLNumber;
    }

    public String getBkgOrBLNumber() {
        return bkgOrBLNumber;
    }

    public void setBkgOrBLType(String bkgOrBLType) {
        this.bkgOrBLType = bkgOrBLType;
    }

    public String getBkgOrBLType() {
        return bkgOrBLType;
    }

    public void setJobOrder(String jobOrder) {
        this.jobOrder = jobOrder;
    }

    public String getJobOrder() {
        return jobOrder;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setJoDate(String joDate) {
        this.joDate = joDate;
    }

    public String getJoDate() {
        return joDate;
    }

    public void setMtOrLaden(String mtOrLaden) {
        this.mtOrLaden = mtOrLaden;
    }

    public String getMtOrLaden() {
        return mtOrLaden;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setOOG(String oOG) {
        this.OOG = oOG;
    }

    public String getOOG() {
        return OOG;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDays() {
        return days;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getHours() {
        return hours;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAmountUsd(String amountUsd) {
        this.amountUsd = amountUsd;
    }

    public String getAmountUsd() {
        return amountUsd;
    }

    public void setPaymentFSC(String paymentFSC) {
        this.paymentFSC = paymentFSC;
    }

    public String getPaymentFSC() {
        return paymentFSC;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setRoutingId(String routingId) {
        this.routingId = routingId;
    }

    public String getRoutingId() {
        return routingId;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSlNumber(String slNumber) {
        this.slNumber = slNumber;
    }

    public String getSlNumber() {
        return slNumber;
    }

	public String getRateBasis() {
		return rateBasis;
	}

	public void setRateBasis(String rateBasis) {
		this.rateBasis = rateBasis;
	}
	
	public String getLumpsumId() {
		return lumpsumId;
	}

	public void setLumpsumId(String lumpsumId) {
		this.lumpsumId = lumpsumId;
	}
    
}
