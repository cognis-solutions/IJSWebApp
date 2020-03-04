 /*-----------------------------------------------------------------------------------------------------------
IjsContractVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            value object for IJS contract search
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.vo;

public class IjsSearchParamVO<T> {
    private String transMode;
    private String dateRange;
    private String startDate;
    private String endDate;
    private String userId;
    private String sortIn;
    private String orderBy;
    
    private T searchScreenParam;

    
    
    public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSortIn() {
		return sortIn;
	}

	public void setSortIn(String sortIn) {
		this.sortIn = sortIn;
	}

	public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public String getTransMode() {
        return transMode;
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

    public void setSearchScreenParam(T searchScreenParam) {
        this.searchScreenParam = searchScreenParam;
    }

    public T getSearchScreenParam() {
        return searchScreenParam;
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
}
