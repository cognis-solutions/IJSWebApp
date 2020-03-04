package com.rclgroup.dolphin.ijs.web.vo;

import java.util.HashMap;
public class IjsMaintainJOSearchParamVO {
    private String componentType;
    private String jobOrdTyp;
    private String jobOrdSts;
    private String jobOrdNo;
    private String vendorCode;
    private String countryCode;
    private String dateRange;
    private String toLocType;
    private String toLocation;
    private String toTerminal;
    private String fromLocType;
    private String fromLocation;
    private String fromTerminal;
    private String bookingOrBlType;
    private String bookingOrBlValue;
    private String routContractOrContType;
    private String routContractOrContValue;
    private String joCostTyp;
    private String joCostValue;
    private String serviceVal;
    private String vesselVal;
    private String voyageVal;
    private String legType;
    private String paymentFSC;
    private String SocOrCoc;
    private int rowStart;
    private int rowEnd;
    private int totalCount;
    private int pageNo;
    private int noOfRecPerPage;
    private boolean requestChanged;
    private String orderBy;
    private String orderType;
    //nikash
    private boolean checkBoxJoCreation;
    
    

  

	public boolean isCheckBoxJoCreation() {
		return checkBoxJoCreation;
	}

	public void setCheckBoxJoCreation(boolean checkBoxJoCreation) {
		this.checkBoxJoCreation = checkBoxJoCreation;
	}

	public void setJobOrdTyp(String jobOrdTyp) {
        this.jobOrdTyp = jobOrdTyp;
    }

    public String getJobOrdTyp() {
        return jobOrdTyp;
    }

    public void setJobOrdSts(String jobOrdSts) {
        this.jobOrdSts = jobOrdSts;
    }

    public String getJobOrdSts() {
        return jobOrdSts;
    }

    public void setJobOrdNo(String jobOrdNo) {
        this.jobOrdNo = jobOrdNo;
    }

    public String getJobOrdNo() {
        return jobOrdNo;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }


    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getDateRange() {
        return dateRange;
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

    public void setBookingOrBlType(String bookingOrBlType) {
        this.bookingOrBlType = bookingOrBlType;
    }

    public String getBookingOrBlType() {
        return bookingOrBlType;
    }

    public void setBookingOrBlValue(String bookingOrBlValue) {
        this.bookingOrBlValue = bookingOrBlValue;
    }

    public String getBookingOrBlValue() {
        return bookingOrBlValue;
    }

    public void setRoutContractOrContType(String routContractOrContType) {
        this.routContractOrContType = routContractOrContType;
    }

    public String getRoutContractOrContType() {
        return routContractOrContType;
    }

    public void setRoutContractOrContValue(String routContractOrContValue) {
        this.routContractOrContValue = routContractOrContValue;
    }

    public String getRoutContractOrContValue() {
        return routContractOrContValue;
    }

    public void setJoCostTyp(String joCostTyp) {
        this.joCostTyp = joCostTyp;
    }

    public String getJoCostTyp() {
        return joCostTyp;
    }

    public void setJoCostValue(String joCostValue) {
        this.joCostValue = joCostValue;
    }

    public String getJoCostValue() {
        return joCostValue;
    }

    public void setServiceVal(String serviceVal) {
        this.serviceVal = serviceVal;
    }

    public String getServiceVal() {
        return serviceVal;
    }

    public void setVesselVal(String vesselVal) {
        this.vesselVal = vesselVal;
    }

    public String getVesselVal() {
        return vesselVal;
    }

    public void setVoyageVal(String voyageVal) {
        this.voyageVal = voyageVal;
    }

    public String getVoyageVal() {
        return voyageVal;
    }

    public void setLegType(String legType) {
        this.legType = legType;
    }

    public String getLegType() {
        return legType;
    }

    public void setPaymentFSC(String paymentFSC) {
        this.paymentFSC = paymentFSC;
    }

    public String getPaymentFSC() {
        return paymentFSC;
    }


    public void setSocOrCoc(String socOrCoc) {
        this.SocOrCoc = socOrCoc;
    }

    public String getSocOrCoc() {
        return SocOrCoc;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setRowStart(int rowStart) {
        this.rowStart = rowStart;
    }

    public int getRowStart() {
        return rowStart;
    }

    public void setRowEnd(int rowEnd) {
        this.rowEnd = rowEnd;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageNo() {
       // return 2;
        return pageNo;
    }

    public void setRequestChanged(boolean requestChanged) {
        this.requestChanged = requestChanged;
    }

    public boolean isRequestChanged() {
        return requestChanged;
    }

    public void setNoOfRecPerPage(int noOfRecPerPage) {
        this.noOfRecPerPage = noOfRecPerPage;
    }

    public int getNoOfRecPerPage() {
        return noOfRecPerPage;
    }

	public String getToTerminal() {
		return toTerminal;
	}

	public void setToTerminal(String toTerminal) {
		this.toTerminal = toTerminal;
	}

	public String getFromTerminal() {
		return fromTerminal;
	}

	public void setFromTerminal(String fromTerminal) {
		this.fromTerminal = fromTerminal;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		return "IjsMaintainJOSearchParamVO [componentType=" + componentType + ", jobOrdTyp=" + jobOrdTyp
				+ ", jobOrdSts=" + jobOrdSts + ", jobOrdNo=" + jobOrdNo + ", vendorCode=" + vendorCode
				+ ", countryCode=" + countryCode + ", dateRange=" + dateRange + ", toLocType=" + toLocType
				+ ", toLocation=" + toLocation + ", toTerminal=" + toTerminal + ", fromLocType=" + fromLocType
				+ ", fromLocation=" + fromLocation + ", fromTerminal=" + fromTerminal + ", bookingOrBlType="
				+ bookingOrBlType + ", bookingOrBlValue=" + bookingOrBlValue + ", routContractOrContType="
				+ routContractOrContType + ", routContractOrContValue=" + routContractOrContValue + ", joCostTyp="
				+ joCostTyp + ", joCostValue=" + joCostValue + ", serviceVal=" + serviceVal + ", vesselVal=" + vesselVal
				+ ", voyageVal=" + voyageVal + ", legType=" + legType + ", paymentFSC=" + paymentFSC + ", SocOrCoc="
				+ SocOrCoc + ", rowStart=" + rowStart + ", rowEnd=" + rowEnd + ", totalCount=" + totalCount
				+ ", pageNo=" + pageNo + ", noOfRecPerPage=" + noOfRecPerPage + ", requestChanged=" + requestChanged
				+ ", orderBy=" + orderBy + ", orderType=" + orderType + ", checkBoxJoCreation=" + checkBoxJoCreation
				+ "]";
	}


  
	
	
}
