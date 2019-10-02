package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsJoExemptedCustSearchVO {
    private String custId;
    private String costCustId;
    private String custName;
    private String custType;
    private String status;
    private String remark;
    private String routingId;
    private String cost_seq_no;
    private String detailSeqNumbers;
    


    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getCustType() {
        return custType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setCostCustId(String costcustId) {
        this.costCustId = costcustId;
    }

    public String getCostCustId() {
        return costCustId;
    }

    public void setRoutingId(String routingId) {
        this.routingId = routingId;
    }

    public String getRoutingId() {
        return routingId;
    }

    public void setCost_seq_no(String cost_seq_no) {
        this.cost_seq_no = cost_seq_no;
    }

    public String getCost_seq_no() {
        return cost_seq_no;
    }

	public String getDetailSeqNumbers() {
		return detailSeqNumbers;
	}

	public void setDetailSeqNumbers(String detailSeqNumbers) {
		this.detailSeqNumbers = detailSeqNumbers;
	}
    
    
}
