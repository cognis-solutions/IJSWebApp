package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsProcessJOBkgBLSearchParamVO {
//    private String findIn;
//    private String findValue;
//    private String vendorCode;
//    private String location;
//    private String contractRouting;
//    private String fromLocType;
//    private String fromLocation;
//    private String fromTerminal;
//    private String toLocType;
//    private String toLocation;
//    private String toTerminal;
//    private String countryCode;
    
    private String transMode;
    private String bookingType;
    private String processJoType;
    private String vendorCode;
    private String bookingVal;
    private String serviceVal;
    private String vesselVal;
    private String voyageVal;
    private String dateRange;
    private String pptdhVal;
    private String processJoCOCType; 
    private String fromLocType;
    private String toLocType;
    private String fromLocation;
    private String toLocation;
    private String routingNumber;
    private String cntSize;
    private String cntType;
    private String cntSplHandling;
    private List<IjsProcessJOBkgBLSearchVO> llstBkgBLResult;
    private String orderBy;
    private String sortBy;
    private int rowStart;
    private int rowEnd;
    private int totalCount;
    private int pageNo;
    private String fromTerminal;
    private String toTerminal;

    
    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getToLocation() {
        return toLocation;
    }
    
    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getFromLocation() {
        return fromLocation;
    }
    
    public void setFromLocType(String fromLocType) {
        this.fromLocType = fromLocType;
    }

    public String getToLocType() {
        return toLocType;
    }
    
    public void setToLocType(String toLocType) {
        this.toLocType = toLocType;
    }

    public String getFromLocType() {
        return fromLocType;
    }
    
    public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public String getTransMode() {
        return transMode;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setProcessJoType(String processJoType) {
        this.processJoType = processJoType;
    }

    public String getProcessJoType() {
        return processJoType;
    }


    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setBookingVal(String bookingVal) {
        this.bookingVal = bookingVal;
    }

    public String getBookingVal() {
        return bookingVal;
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

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setPptdhVal(String pptdhVal) {
        this.pptdhVal = pptdhVal;
    }

    public String getPptdhVal() {
        return pptdhVal;
    }
    
    
    
    public void setProcessJoCOCType(String processJoCOCType) {
        this.processJoCOCType = processJoCOCType;
    }

    public String getProcessJoCOCType() {
        return processJoCOCType;
    }

    public void setLlstBkgBLResult(List<IjsProcessJOBkgBLSearchVO> llstBkgBLResult) {
        this.llstBkgBLResult = llstBkgBLResult;
    }

    public List<IjsProcessJOBkgBLSearchVO> getLlstBkgBLResult() {
        return llstBkgBLResult;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setCntSize(String cntSize) {
        this.cntSize = cntSize;
    }

    public String getCntSize() {
        return cntSize;
    }

    public void setCntType(String cntType) {
        this.cntType = cntType;
    }

    public String getCntType() {
        return cntType;
    }

    public void setCntSplHandling(String cntSplHandling) {
        this.cntSplHandling = cntSplHandling;
    }

    public String getCntSplHandling() {
        return cntSplHandling;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
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
        return pageNo;
    }

	public String getFromTerminal() {
		return fromTerminal;
	}

	public void setFromTerminal(String fromTerminal) {
		this.fromTerminal = fromTerminal;
	}

	public String getToTerminal() {
		return toTerminal;
	}

	public void setToTerminal(String toTerminal) {
		this.toTerminal = toTerminal;
	}
    
}
