package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsProcessJOBkgBLSearchVO {
    private String bkgOrBLNumber;
    private String bkgOrBLType;
    private String transportMode;
    private String bookingStatus;
    private String fromTerminal;
    private String toTerminal;
    private String service;
    private String vessel;
    private String voyage;
    private String direction;
    private String startDate;
    private String endDate;
    private String cntSize;
    private String cntType;
    private String cntSplHandling;
    private int cntSplHandCount;
    private int ladenCntTotal;
    private int ladenCntAvailable;
    private int ladenCntInJO;
    private int emptyCntTotal;
    private int emptyCntAvailable;
    private int emptyCntInJO;
    private String fromLocation;
    private String toLocation;
    private String routingNumber;
    private String vendorCode;
    private String fromLocationTyp;
    private String toLocationTyp;
    private String specialHandlingCode;
    private String priority ;
    private int dgCount;
    private int oogCount;
    //chandu
    private int numContainer;

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

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
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

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
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

    public void setCntSplHandCount(int cntSplHandCount) {
        this.cntSplHandCount = cntSplHandCount;
    }

    public int getCntSplHandCount() {
        return cntSplHandCount;
    }

    public void setLadenCntTotal(int ladenCntTotal) {
        this.ladenCntTotal = ladenCntTotal;
    }

    public int getLadenCntTotal() {
        return ladenCntTotal;
    }

    public void setLadenCntAvailable(int ladenCntAvailable) {
        this.ladenCntAvailable = ladenCntAvailable;
    }

    public int getLadenCntAvailable() {
        return ladenCntAvailable;
    }

    public void setLadenCntInJO(int ladenCntInJO) {
        this.ladenCntInJO = ladenCntInJO;
    }

    public int getLadenCntInJO() {
        return ladenCntInJO;
    }

    public void setEmptyCntTotal(int emptyCntTotal) {
        this.emptyCntTotal = emptyCntTotal;
    }

    public int getEmptyCntTotal() {
        return emptyCntTotal;
    }

    public void setEmptyCntAvailable(int emptyCntAvailable) {
        this.emptyCntAvailable = emptyCntAvailable;
    }

    public int getEmptyCntAvailable() {
        return emptyCntAvailable;
    }

    public void setEmptyCntInJO(int emptyCntInJO) {
        this.emptyCntInJO = emptyCntInJO;
    }

    public int getEmptyCntInJO() {
        return emptyCntInJO;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setFromLocationTyp(String fromLocationTyp) {
        this.fromLocationTyp = fromLocationTyp;
    }

    public String getFromLocationTyp() {
        return fromLocationTyp;
    }

    public void setToLocationTyp(String toLocationTyp) {
        this.toLocationTyp = toLocationTyp;
    }

    public String getToLocationTyp() {
        return toLocationTyp;
    }

    public void setSpecialHandlingCode(String specialHandlingCode) {
        this.specialHandlingCode = specialHandlingCode;
    }

    public String getSpecialHandlingCode() {
        return specialHandlingCode;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

	public int getDgCount() {
		return dgCount;
	}

	public void setDgCount(int dgCount) {
		this.dgCount = dgCount;
	}

	public int getOogCount() {
		return oogCount;
	}

	public void setOogCount(int oogCount) {
		this.oogCount = oogCount;
	}

	public int getNumContainer() {
		return numContainer;
	}

	public void setNumContainer(int numContainer) {
		this.numContainer = numContainer;
	}
    
}
