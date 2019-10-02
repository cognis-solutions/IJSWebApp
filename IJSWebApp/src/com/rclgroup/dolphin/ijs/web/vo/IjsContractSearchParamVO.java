 /*-----------------------------------------------------------------------------------------------------------
IjsContractSearchParamVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            value object for IJS contract search screen
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractSearchParamVO {
    private String findIn;
    private String findValue;
    private String vendorCode;
    private String location;
    private String contractNumber; //Roomy
    private String fromLocType;
    private String fromLocation;
    private String fromTerminal;
    private String toLocType;
    private String toLocation;
    private String toTerminal;
    private String countryCode;
    private String status;      //purchase term
    private String priority;    //Roomy
    private String purchaseTerm;	//purchase Term
   private String filterBy;		//for filter of empty rate and laden rate

    
	
    public String getFilterBy() { return filterBy; }

	 public void setFilterBy(String filterBy) { this.filterBy = filterBy; }
	 

	public String getPurchaseTerm() {
		return purchaseTerm;
	}

	public void setPurchaseTerm(String purchaseTerm) {
		this.purchaseTerm = purchaseTerm;
	}

	public void setFindIn(String findIn) {
        this.findIn = findIn;
    }

    public String getFindIn() {
        return findIn;
    }

    public void setFindValue(String findValue) {
        this.findValue = findValue;
    }

    public String getFindValue() {
        return findValue;
    }


    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

//    public void setContractRouting(String contractRouting) {
//        this.contractRouting = contractRouting;
//    }
//
//    public String getContractRouting() {
//        return contractRouting;
//    }


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

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }
}
