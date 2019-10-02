/*-----------------------------------------------------------------------------------------------------------
IjsJOBookingBLSearchVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            Booking/BL Search Value Object 
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.vo.jo;

public class IjsJOBookingBLSearchVO {
    private String transportType;
    private String bookingType;
    private String bkgBLNumber;
    private String jobOrderType;
    private String vendor;
    private String service;
    private String vessel;
    private String voyage;
    private String startDate;
    private String endDate;
    private String loadPort;
    private String dischargePort;
    private String dateRange;
    private String fromLocType;
    private String fromLocation;
    private String fromTerminal;
    private String toLocType;
    private String toLocation;
    private String toTerminal;
    private String reasonCode;
    private int pageNo;
    private int recordStart;
    private int recordEnd;
    private String orderBy;
    private String sortBy;
    
    
    public void setJobOrderType(String jobOrderType) {
        this.jobOrderType = jobOrderType;
    }

    public String getJobOrderType() {
        return jobOrderType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getDateRange() {
        return dateRange;
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

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setBkgBLNumber(String bkgBLNumber) {
        this.bkgBLNumber = bkgBLNumber;
    }

    public String getBkgBLNumber() {
        return bkgBLNumber;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor() {
        return vendor;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }

    public String getVessel() {
        return vessel;
    }

    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    public String getVoyage() {
        return voyage;
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

    public void setLoadPort(String loadPort) {
        this.loadPort = loadPort;
    }

    public String getLoadPort() {
        return loadPort;
    }

    public void setDischargePort(String dischargePort) {
        this.dischargePort = dischargePort;
    }

    public String getDischargePort() {
        return dischargePort;
    }

    public void setRecordStart(int recordStart) {
        this.recordStart = recordStart;
    }

    public int getRecordStart() {
        return recordStart;
    }

    public void setRecordEnd(int recordEnd) {
        this.recordEnd = recordEnd;
    }

    public int getRecordEnd() {
        return recordEnd;
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
}
