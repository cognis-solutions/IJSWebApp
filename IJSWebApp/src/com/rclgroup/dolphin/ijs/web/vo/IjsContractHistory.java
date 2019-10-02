/*-----------------------------------------------------------------------------------------------------------
IjsContractHistory.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 05/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 05/10/17  NIIT       IJS            value object for contract history screen
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractHistory {
    public IjsContractHistory() {
    }
    
    private String activity;
    private String activityDate;
    private String activityUser;
    private double activitySeq;


    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityUser(String activityUser) {
        this.activityUser = activityUser;
    }

    public String getActivityUser() {
        return activityUser;
    }

	public double getActivitySeq() {
		return activitySeq;
	}

	public void setActivitySeq(double activitySeq) {
		this.activitySeq = activitySeq;
	}
    
}
