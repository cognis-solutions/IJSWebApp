package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsMaintainJOSearchVO {
    private String JoNumber;
    private String orderDate;
    private String approveDate;
    private String createdBy;
    private String vendorID;
    private String jobOrdType;
    private String vendorName;
    private String detailType;
    private String detailVersion;
    private String fromLoaction;
    private String toLocation;
    private String fromTerminal;
    private String toTerminal;
    private String fromLocType;
    private String toLocType;
    private String startDate;
    private String completeDate;
    private String amount;
    private double amountNum;
    private String amountUSD   ;
    private String currency;
    private String routingId;
    private String contractId;
    private String status;
    private String FSC;
    private String reasonCode;
    private String transMode;
    private String adhoc_yn;
    private String bk_bl_ad;
    private String user_type;
    private String SOCorCOC;
    private String priority;
    private String equiptState;
    private String eqNumber;
    private String BkgOrBLNo;
    private String contPercent;
    private String contWeight;
    private String amountBkgBl;
    private String amountLumpsum;
    private List<IjsMaintainJOSearchContDetailVO> contDetailJO;
    private List<IjsMaintainJOSearchVO> joSaveFscList;
    private List<IjsMaintainJOSearchVO> joRemoveCntrList;
    private List<IjsMaintainJOSearchVO> joReplaceCntrList;
    private List<IjsMaintainJOSearchVO> joChangeVendorList;
    
    private List<IjsMaintainJOSearchVO> joNewCntrList;
    private IjsMaintainSaveVO rowDataObj;
    

	//For Save
    private String contNoToDelete;
    private String oldContainerNo;
    private String oldContNoReplace;
    private String newContNoReplace;
    private String contEmptyOrLaden;
    private String fsc;
    private Integer contSize;
    private String contType;
    //nikash
    private String barge;


    public IjsMaintainSaveVO getRowDataObj() {
		return rowDataObj;
	}

	public void setRowDataObj(IjsMaintainSaveVO rowDataObj) {
		this.rowDataObj = rowDataObj;
	}

	public String getBarge() {
		return barge;
	}

	public void setBarge(String barge) {
		this.barge = barge;
	}

	public void setJoNumber(String joNumber) {
        this.JoNumber = joNumber;
    }

    public String getJoNumber() {
        return JoNumber;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveDate() {
        return approveDate;
    }

   

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setJobOrdType(String jobOrdType) {
        this.jobOrdType = jobOrdType;
    }

    public String getJobOrdType() {
        return jobOrdType;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailVersion(String detailVersion) {
        this.detailVersion = detailVersion;
    }

    public String getDetailVersion() {
        return detailVersion;
    }

    public void setFromLoaction(String fromLoaction) {
        this.fromLoaction = fromLoaction;
    }

    public String getFromLoaction() {
        return fromLoaction;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getToLocation() {
        return toLocation;
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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmountUSD(String amountUSD) {
        this.amountUSD = amountUSD;
    }

    public String getAmountUSD() {
        return amountUSD;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setRoutingId(String routingId) {
        this.routingId = routingId;
    }

    public String getRoutingId() {
        return routingId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setFSC(String fSC) {
        this.FSC = fSC;
    }

    public String getFSC() {
        return FSC;
    }



    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setContDetailJO(List<IjsMaintainJOSearchContDetailVO> contDetailJO) {
        this.contDetailJO = contDetailJO;
    }

    public List<IjsMaintainJOSearchContDetailVO> getContDetailJO() {
        return contDetailJO;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setFromLocType(String fromLocType) {
        this.fromLocType = fromLocType;
    }

    public String getFromLocType() {
        return fromLocType;
    }

    public void setToLocType(String toLocType) {
        this.toLocType = toLocType;
    }

    public String getToLocType() {
        return toLocType;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public String getTransMode() {
        return transMode;
    }

    public void setAdhoc_yn(String adhoc_yn) {
        this.adhoc_yn = adhoc_yn;
    }

    public String getAdhoc_yn() {
        return adhoc_yn;
    }

    public void setBk_bl_ad(String bk_bl_ad) {
        this.bk_bl_ad = bk_bl_ad;
    }

    public String getBk_bl_ad() {
        return bk_bl_ad;
    }

    public void setContNoToDelete(String contNoToDelete) {
        this.contNoToDelete = contNoToDelete;
    }

    public String getContNoToDelete() {
        return contNoToDelete;
    }

    public void setOldContNoReplace(String oldContNoReplace) {
        this.oldContNoReplace = oldContNoReplace;
    }

    public String getOldContNoReplace() {
        return oldContNoReplace;
    }

    public void setNewContNoReplace(String newContNoReplace) {
        this.newContNoReplace = newContNoReplace;
    }

    public String getNewContNoReplace() {
        return newContNoReplace;
    }

    public void setContEmptyOrLaden(String contEmptyOrLaden) {
        this.contEmptyOrLaden = contEmptyOrLaden;
    }

    public String getContEmptyOrLaden() {
        return contEmptyOrLaden;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setFsc(String fsc) {
        this.fsc = fsc;
    }

    public String getFsc() {
        return fsc;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setJoSaveFscList(List<IjsMaintainJOSearchVO> joSaveFscList) {
        this.joSaveFscList = joSaveFscList;
    }

    public List<IjsMaintainJOSearchVO> getJoSaveFscList() {
        return joSaveFscList;
    }

    public void setJoRemoveCntrList(List<IjsMaintainJOSearchVO> joRemoveCntrList) {
        this.joRemoveCntrList = joRemoveCntrList;
    }

    public List<IjsMaintainJOSearchVO> getJoRemoveCntrList() {
        return joRemoveCntrList;
    }

    public void setJoReplaceCntrList(List<IjsMaintainJOSearchVO> joReplaceCntrList) {
        this.joReplaceCntrList = joReplaceCntrList;
    }

    public List<IjsMaintainJOSearchVO> getJoReplaceCntrList() {
        return joReplaceCntrList;
    }

    public void setJoChangeVendorList(List<IjsMaintainJOSearchVO> joChangeVendorList) {
        this.joChangeVendorList = joChangeVendorList;
    }

    public List<IjsMaintainJOSearchVO> getJoChangeVendorList() {
        return joChangeVendorList;
    }

    public void setContSize(Integer contSize) {
        this.contSize = contSize;
    }

    public Integer getContSize() {
        return contSize;
    }

    public void setContType(String contType) {
        this.contType = contType;
    }

    public String getContType() {
        return contType;
    }

    public void setSOCorCOC(String sOCorCOC) {
        this.SOCorCOC = sOCorCOC;
    }

    public String getSOCorCOC() {
        return SOCorCOC;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

	public double getAmountNum() {
		return amountNum;
	}

	public void setAmountNum(double amountNum) {
		this.amountNum = amountNum;
	}

	public String getOldContainerNo() {
		return oldContainerNo;
	}

	public void setOldContainerNo(String oldContainerNo) {
		this.oldContainerNo = oldContainerNo;
	}

	public List<IjsMaintainJOSearchVO> getJoNewCntrList() {
		return joNewCntrList;
	}

	public void setJoNewCntrList(List<IjsMaintainJOSearchVO> joNewCntrList) {
		this.joNewCntrList = joNewCntrList;
	}

	public String getEquiptState() {
		return equiptState;
	}

	public void setEquiptState(String equiptState) {
		this.equiptState = equiptState;
	}

	public String getEqNumber() {
		return eqNumber;
	}

	public void setEqNumber(String eqNumber) {
		this.eqNumber = eqNumber;
	}

	public String getBkgOrBLNo() {
		return BkgOrBLNo;
	}

	public void setBkgOrBLNo(String bkgOrBLNo) {
		BkgOrBLNo = bkgOrBLNo;
	}

	public String getContPercent() {
		return contPercent;
	}

	public void setContPercent(String contPercent) {
		this.contPercent = contPercent;
	}

	public String getContWeight() {
		return contWeight;
	}

	public void setContWeight(String contWeight) {
		this.contWeight = contWeight;
	}

	public String getAmountBkgBl() {
		return amountBkgBl;
	}

	public void setAmountBkgBl(String amountBkgBl) {
		this.amountBkgBl = amountBkgBl;
	}

	public String getAmountLumpsum() {
		return amountLumpsum;
	}

	public void setAmountLumpsum(String amountLumpsum) {
		this.amountLumpsum = amountLumpsum;
	}
	
    
}
