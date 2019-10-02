package com.rclgroup.dolphin.ijs.web.common;

import java.util.List;

public class IjsPaginationInfo {
    private List<?> results;
    private int recordStart;
    private int recordEnd;
    private String orderBy;
    
    
    public IjsPaginationInfo() {
    }

    public void setResults(List<?> results) {
        this.results = results;
    }

    public List<?> getResults() {
        return results;
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
}
