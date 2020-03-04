package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;


public class IjsProcessJOSumDtlVO {
	
    List<IjsProcessJOSumContDtlVO> jobOrders = null;
    IjsProcessJOSummarySearchVO joSummery = null;
    String bokingBL;
    String errorCode;

    public void setJobOrders(List<IjsProcessJOSumContDtlVO> jobOrders) {
        this.jobOrders = jobOrders;
    }

    public List<IjsProcessJOSumContDtlVO> getJobOrders() {
        return jobOrders;
    }

    public void setJoSummery(IjsProcessJOSummarySearchVO joSummery) {
        this.joSummery = joSummery;
    }

    public IjsProcessJOSummarySearchVO getJoSummery() {
        return joSummery;
    }

	public String getBokingBL() {
		return bokingBL;
	}

	public void setBokingBL(String bokingBL) {
		this.bokingBL = bokingBL;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
    
}
