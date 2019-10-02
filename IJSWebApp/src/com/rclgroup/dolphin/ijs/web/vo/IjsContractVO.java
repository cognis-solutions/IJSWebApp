 /*-----------------------------------------------------------------------------------------------------------
IjsContractVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            value object for IJS contract
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.vo;

import java.util.ArrayList;
import java.util.List;

public class IjsContractVO {
    private String vendorCode; 
    private String vendorName;  
    private String contractId;
    private Integer routingId;
    private String startDate;
    private String endDate;   
    private String transMode;
    private String status;
    private String paymentFsc;
    private String currency;
    private Integer priority;  
    private String fromLocType;
    private String fromLocation;
    private String fromTerminal;
    private String toLocType;
    private String toLocation;
    private String toTerminal;
    private boolean invalidContract;
    private String locType;
    
    List<IjsLocationVO> locations;
    //for purchase term
    private String purchaseTerm;
    
	private int days;
    private int hours;
    private int distance;
    private String uom;
    private String remarks;
    private boolean exempted;
    
    List<IjsRateVO> lstCostRate;
    List<IjsRateVO> lstBillRate;
    private List<String> term = new ArrayList();
    private String termVal;
    private String detailSeqNum;
    
    private String rate20;
    private String rate40;
    private String rate45;
    private String billingRate20;
    private String billingRate40;
    private String billingRate45;
    private String costCurrency;
    private String billingCurrency;
    
    IjsBaseMessageVO msgDupRoute;
    IjsBaseMessageVO msgSuccessVO;
    

    //for purchase term
    public String getPurchaseTerm() {
		return purchaseTerm;
	}

	public void setPurchaseTerm(String purchaseTerm) {
		this.purchaseTerm = purchaseTerm;
	}

    public void setLstCostRate(List<IjsRateVO> lstCostRate) {
        this.lstCostRate = lstCostRate;
    }

    public List<IjsRateVO> getLstCostRate() {
        return lstCostRate;
    }

    public void setLstBillRate(List<IjsRateVO> lstBillRate) {
        this.lstBillRate = lstBillRate;
    }

    public List<IjsRateVO> getLstBillRate() {
        return lstBillRate;
    }


    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
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

    public void setDays(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getUom() {
        return uom;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setExempted(boolean exempted) {
        this.exempted = exempted;
    }

    public boolean isExempted() {
        return exempted;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setFromLocType(String fromLocType) {
        this.fromLocType = fromLocType;
    }

    public String getFromLocType() {
        return fromLocType;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromTerminal(String fromTerminal) {
        this.fromTerminal = fromTerminal;
    }

    public String getFromTerminal() {
        return fromTerminal;
    }

    public void setToLocType(String toLocType) {
        this.toLocType = toLocType;
    }

    public String getToLocType() {
        return toLocType;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToTerminal(String toTerminal) {
        this.toTerminal = toTerminal;
    }

    public String getToTerminal() {
        return toTerminal;
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

    public void setTerm(List<String> term) {
        this.term = term;
    }

    public List<String> getTerm() {
        return term;
    }

    public void setTermVal(String termVal) {
        this.termVal = termVal;
    }

    public String getTermVal() {
        return termVal;
    }

    public void setDetailSeqNum(String detailSeqNum) {
        this.detailSeqNum = detailSeqNum;
    }

    public String getDetailSeqNum() {
        return detailSeqNum;
    }

    public void setRate20(String rate20) {
        this.rate20 = rate20;
    }

    public String getRate20() {
        return rate20;
    }

    public void setRate40(String rate40) {
        this.rate40 = rate40;
    }

    public String getRate40() {
        return rate40;
    }

    public void setRate45(String rate45) {
        this.rate45 = rate45;
    }

    public String getRate45() {
        return rate45;
    }

    public void setLocations(List<IjsLocationVO> locations) {
        this.locations = locations;
    }

    public List<IjsLocationVO> getLocations() {
        return locations;
    }


    public void setMsgDupRoute(IjsBaseMessageVO msgDupRoute) {
        this.msgDupRoute = msgDupRoute;
    }

    public IjsBaseMessageVO getMsgDupRoute() {
        return msgDupRoute;
    }


    public void setMsgSuccessVO(IjsBaseMessageVO msgSuccessVO) {
        this.msgSuccessVO = msgSuccessVO;
    }

    public IjsBaseMessageVO getMsgSuccessVO() {
        return msgSuccessVO;
    }

	public boolean isInvalidContract() {
		return invalidContract;
	}

	public void setInvalidContract(boolean invalidContract) {
		this.invalidContract = invalidContract;
	}

	public String getLocType() {
		return locType;
	}

	public void setLocType(String locType) {
		this.locType = locType;
	}

	public String getBillingRate20() {
		return billingRate20;
	}

	public void setBillingRate20(String billingRate20) {
		this.billingRate20 = billingRate20;
	}

	public String getBillingRate40() {
		return billingRate40;
	}

	public void setBillingRate40(String billingRate40) {
		this.billingRate40 = billingRate40;
	}

	public String getBillingRate45() {
		return billingRate45;
	}

	public void setBillingRate45(String billingRate45) {
		this.billingRate45 = billingRate45;
	}

	public String getCostCurrency() {
		return costCurrency;
	}

	public void setCostCurrency(String costCurrency) {
		this.costCurrency = costCurrency;
	}

	public String getBillingCurrency() {
		return billingCurrency;
	}

	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}

	
    
}
