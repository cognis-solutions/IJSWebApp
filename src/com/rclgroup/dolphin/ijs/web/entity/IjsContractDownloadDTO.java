package com.rclgroup.dolphin.ijs.web.entity;

import com.rclgroup.dolphin.ijs.web.vo.IjsContractOogSetupVO;

import java.util.List;


public class IjsContractDownloadDTO {
    public IjsContractDownloadDTO() {
    }
    private String vendorCode;  
    private String vendorName;  
    private String contractId;
    private Integer routingId;
    private String contractStartDate;
    private String contractEndDate;   
    private String transMode;
    private String status;
    private String paymentFsc;
    private String currency;
    private Integer priority;  
    private String fromLocType;
    private String toLocType;
    private String fromLocation;
    private String toLocation;
    private String fromTerminal;
    private String toTerminal;
    private Integer days;
    private Integer hours;
    private Integer distance;
    private String uom;
    private String exempted;
    
    //cost rate
     private String rateStartDate;
     private String rateEndDate;
     private String service;
     private String vesselCode;
     private String mtOrLaden;
     private String rateBasis;
     private String eqCatq;
     private String rateStatus;
     private String chargeCode;
     private String exemptedCustomer;
     private String term;
     private String calcMethod;
     private String eqType;
     private Integer upto;
     private String contractUom;
     private String impOrExp;
     private String splHandling;
     private String contractCurrency;
     private String portClassCode;
     private String imdgDetails;
     private String oogSetup;
     private String splCostByBlOrBooking;
     private Double rate20;
     private Double rate40;
     private Double rate45;
     private String lumpSum;
     private String errorMsg;
     private String perTrip;
     private Integer portAndImdgSeqNum;
     private Integer oogSetupSeqNum;//##02
     private Integer costRateSequenceNum;//##02
     private String mot;//##02
     private Integer eqTypeSeqNum;//##03
     private Integer termSeqNum;//##03
     private List<IjsContractOogSetupVO> oogSetUpList;//##03
     private String detailSeqNum;
     private String spCustomers;
     private String pTerm;
     private String filterBy;


    public String getpTerm() {
		return pTerm;
	}

	public void setpTerm(String pTerm) {
		this.pTerm = pTerm;
	}

	public String getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
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

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setRoutingId(Integer routingId) {
        this.routingId = routingId;
    }

    public Integer getRoutingId() {
        return routingId;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public String getTransMode() {
        return transMode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPaymentFsc(String paymentFsc) {
        this.paymentFsc = paymentFsc;
    }

    public String getPaymentFsc() {
        return paymentFsc;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
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

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getFromLocation() {
        return fromLocation;
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

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getDays() {
        return days;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getHours() {
        return hours;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getUom() {
        return uom;
    }

    public void setExempted(String exempted) {
        this.exempted = exempted;
    }

    public String isExempted() {
        return exempted;
    }

    public void setRateStartDate(String rateStartDate) {
        this.rateStartDate = rateStartDate;
    }

    public String getRateStartDate() {
        return rateStartDate;
    }

    public void setRateEndDate(String rateEndDate) {
        this.rateEndDate = rateEndDate;
    }

    public String getRateEndDate() {
        return rateEndDate;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setMtOrLaden(String mtOrLaden) {
        this.mtOrLaden = mtOrLaden;
    }

    public String getMtOrLaden() {
        return mtOrLaden;
    }

    public void setRateBasis(String rateBasis) {
        this.rateBasis = rateBasis;
    }

    public String getRateBasis() {
        return rateBasis;
    }

    public void setEqCatq(String eqCatq) {
        this.eqCatq = eqCatq;
    }

    public String getEqCatq() {
        return eqCatq;
    }

    public void setRateStatus(String rateStatus) {
        this.rateStatus = rateStatus;
    }

    public String getRateStatus() {
        return rateStatus;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setCalcMethod(String calcMethod) {
        this.calcMethod = calcMethod;
    }

    public String getCalcMethod() {
        return calcMethod;
    }

    public void setEqType(String eqType) {
        this.eqType = eqType;
    }

    public String getEqType() {
        return eqType;
    }

    public void setUpto(Integer upto) {
        this.upto = upto;
    }

    public Integer getUpto() {
        return upto;
    }

    public void setContractUom(String contractUom) {
        this.contractUom = contractUom;
    }

    public String getContractUom() {
        return contractUom;
    }

    public void setImpOrExp(String impOrExp) {
        this.impOrExp = impOrExp;
    }

    public String getImpOrExp() {
        return impOrExp;
    }

    public void setSplHandling(String splHandling) {
        this.splHandling = splHandling;
    }

    public String getSplHandling() {
        return splHandling;
    }

    public void setContractCurrency(String contractCurrency) {
        this.contractCurrency = contractCurrency;
    }

    public String getContractCurrency() {
        return contractCurrency;
    }

    public void setPortClassCode(String portClassCode) {
        this.portClassCode = portClassCode;
    }

    public String getPortClassCode() {
        return portClassCode;
    }

    public void setImdgDetails(String imdgDetails) {
        this.imdgDetails = imdgDetails;
    }

    public String getImdgDetails() {
        return imdgDetails;
    }

    public void setOogSetup(String oogSetup) {
        this.oogSetup = oogSetup;
    }

    public String getOogSetup() {
        return oogSetup;
    }

    public void setSplCostByBlOrBooking(String splCostByBlOrBooking) {
        this.splCostByBlOrBooking = splCostByBlOrBooking;
    }

    public String getSplCostByBlOrBooking() {
        return splCostByBlOrBooking;
    }

    public void setRate20(Double rate20) {
        this.rate20 = rate20;
    }

    public Double getRate20() {
        return rate20;
    }

    public void setRate40(Double rate40) {
        this.rate40 = rate40;
    }

    public Double getRate40() {
        return rate40;
    }

    public void setRate45(Double rate45) {
        this.rate45 = rate45;
    }

    public Double getRate45() {
        return rate45;
    }

    public void setLumpSum(String lumpSum) {
        this.lumpSum = lumpSum;
    }

    public String getLumpSum() {
        return lumpSum;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setPerTrip(String perTrip) {
        this.perTrip = perTrip;
    }

    public String getPerTrip() {
        return perTrip;
    }

    public void setPortAndImdgSeqNum(Integer portAndImdgSeqNum) {
        this.portAndImdgSeqNum = portAndImdgSeqNum;
    }

    public Integer getPortAndImdgSeqNum() {
        return portAndImdgSeqNum;
    }

    public void setOogSetupSeqNum(Integer oogSetupSeqNum) {
        this.oogSetupSeqNum = oogSetupSeqNum;
    }

    public Integer getOogSetupSeqNum() {
        return oogSetupSeqNum;
    }

    public void setCostRateSequenceNum(Integer costRateSequenceNum) {
        this.costRateSequenceNum = costRateSequenceNum;
    }

    public Integer getCostRateSequenceNum() {
        return costRateSequenceNum;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    public String getMot() {
        return mot;
    }

    public void setEqTypeSeqNum(Integer eqTypeSeqNum) {
        this.eqTypeSeqNum = eqTypeSeqNum;
    }

    public Integer getEqTypeSeqNum() {
        return eqTypeSeqNum;
    }

    public void setTermSeqNum(Integer termSeqNum) {
        this.termSeqNum = termSeqNum;
    }

    public Integer getTermSeqNum() {
        return termSeqNum;
    }

    public void setOogSetUpList(List<IjsContractOogSetupVO> oogSetUpList) {
        this.oogSetUpList = oogSetUpList;
    }

    public List<IjsContractOogSetupVO> getOogSetUpList() {
        return oogSetUpList;
    }

    public void setExemptedCustomer(String exemptedCustomer) {
        this.exemptedCustomer = exemptedCustomer;
    }

    public String getExemptedCustomer() {
        return exemptedCustomer;
    }

    public String getExempted() {
        return exempted;
    }

    public void setDetailSeqNum(String detailSeqNum) {
        this.detailSeqNum = detailSeqNum;
    }

    public String getDetailSeqNum() {
        return detailSeqNum;
    }

	public String getSpCustomers() {
		return spCustomers;
	}

	public void setSpCustomers(String spCustomers) {
		this.spCustomers = spCustomers;
	}

	public String getVesselCode() {
		return vesselCode;
	}

	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}
    
}
