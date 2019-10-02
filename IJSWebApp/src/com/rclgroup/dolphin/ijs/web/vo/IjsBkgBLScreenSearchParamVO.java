package com.rclgroup.dolphin.ijs.web.vo;

public class IjsBkgBLScreenSearchParamVO<T> {
    private String transType;
    private String dateRange;
    private String startDate;
    private String endDate;
    private String userId;
    private T processJoParam;

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransType() {
        return transType;
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


    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setProcessJoParam(T processJoParam) {
        this.processJoParam = processJoParam;
    }

    public T getProcessJoParam() {
        return processJoParam;
    }
}
