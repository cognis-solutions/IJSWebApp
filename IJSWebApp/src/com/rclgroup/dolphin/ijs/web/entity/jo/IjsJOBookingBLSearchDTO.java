/*-----------------------------------------------------------------------------------------------------------
IjsJOBookingBLSearchResultsDTO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 02/11/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 02/11/17  NIIT       IJS            Booking/BL Search functionality 
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.entity.jo;

public class IjsJOBookingBLSearchDTO {
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
    }

