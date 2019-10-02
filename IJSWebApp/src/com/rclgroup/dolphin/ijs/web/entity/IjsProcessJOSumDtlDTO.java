package com.rclgroup.dolphin.ijs.web.entity;

import java.util.List;


public class IjsProcessJOSumDtlDTO {
   List<IjsProcessJOSumContDtlDTO> jobOrders = null;
   IjsProcessJOSummarySearchDTO joSummery = null;
   String bokingBL;
   String errorCode;

    public void setJobOrders(List<IjsProcessJOSumContDtlDTO> jobOrders) {
        this.jobOrders = jobOrders;
    }

    public List<IjsProcessJOSumContDtlDTO> getJobOrders() {
        return jobOrders;
    }

    public void setJoSummery(IjsProcessJOSummarySearchDTO joSummery) {
        this.joSummery = joSummery;
    }

    public IjsProcessJOSummarySearchDTO getJoSummery() {
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
