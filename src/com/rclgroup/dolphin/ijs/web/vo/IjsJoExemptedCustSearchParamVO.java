package com.rclgroup.dolphin.ijs.web.vo;

public class IjsJoExemptedCustSearchParamVO {
    private String contractId;
    private Integer routingNumber;
    private Integer costRateSequenceNum;
    private String costRateDetailSeqNum;
    private String detailSeqNumbers;
    
    //for add/edit/delete
    private String custId;
    private String costCustId;
    private String custName;
    private String custType;
    private String status;
    private String remark;

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    

    public void setCostRateDetailSeqNum(String costRateDetailSeqNum) {
        this.costRateDetailSeqNum = costRateDetailSeqNum;
    }

    public String getCostRateDetailSeqNum() {
        return costRateDetailSeqNum;
    }

    public void setDetailSeqNumbers(String detailSeqNumbers) {
        this.detailSeqNumbers = detailSeqNumbers;
    }

    public String getDetailSeqNumbers() {
        return detailSeqNumbers;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCostCustId(String costCustId) {
        this.costCustId = costCustId;
    }

    public String getCostCustId() {
        return costCustId;
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

    public void setRoutingNumber(Integer routingNumber) {
        this.routingNumber = routingNumber;
    }

    public Integer getRoutingNumber() {
        return routingNumber;
    }

    public void setCostRateSequenceNum(Integer costRateSequenceNum) {
        this.costRateSequenceNum = costRateSequenceNum;
    }

    public Integer getCostRateSequenceNum() {
        return costRateSequenceNum;
    }
}
