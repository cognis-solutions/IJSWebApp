package com.rclgroup.dolphin.ijs.web.entity;

import com.rclgroup.dolphin.ijs.web.vo.IjsContractOogSetupVO;

import java.util.List;

public class IjsMaintainJoDownloadDTO {
    public IjsMaintainJoDownloadDTO() {
    }
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
    private String fromLocType;
    private String toLocType;
    private String fromTerminal;
    private String toTerminal;
    private String startDate;
    private String completeDate;
    private String amount;
    private String amountUSD;
    private String currency;
    private String routingId;
    private String status;
    private String FSC;
    private String reasonCode;
    private String transMode;
    private String adhoc_yn;
    private String bk_bl_ad;
    private String afs_v;
    private String afs_voy_num;
    private String create_dt;
    private String issue_dt;
    private String activity_dt;
    private String purchase_term;
    private String contract_id;
    private String priority;
    private String approval_dt;
    private String approval_id;
    private String equiptState;
    private String eqNumber;
    private String BkgOrBLNo;
    private Integer contSize;
    private String contPercent;
    private String contType;
    private String contEmptyOrLaden;
    private String SOCorCOC;
    private String endDate;
    private String special_handling;
    private String DGorRForOG;
    private String portClass;
    private String imdgClass;
    private String unno;
    private String variant;
    private String flashPoint;
    private String tempFrom;
    private String tempTo;
    private String oh;
    private String olf;
    private String owl;
    private String owr;
    private String ola;
    private String sizeTypeAmt;
    private String sizeTypeUSDAmt;
    private String bkgBlAmt;
    private String bkgBlUSDAmt;
    private String lumpsumAmt;
    private String lumpsumUSDAmt;
    
    private List<IjsMaintainJOSearchContDetailDTO> contDetailJO;

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

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
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

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonCode() {
        return reasonCode;
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

    public void setContDetailJO(List<IjsMaintainJOSearchContDetailDTO> contDetailJO) {
        this.contDetailJO = contDetailJO;
    }

    public List<IjsMaintainJOSearchContDetailDTO> getContDetailJO() {
        return contDetailJO;
    }

    public void setAfs_v(String afs_v) {
        this.afs_v = afs_v;
    }

    public String getAfs_v() {
        return afs_v;
    }

    public void setAfs_voy_num(String afs_voy_num) {
        this.afs_voy_num = afs_voy_num;
    }

    public String getAfs_voy_num() {
        return afs_voy_num;
    }

    public void setCreate_dt(String create_dt) {
        this.create_dt = create_dt;
    }

    public String getCreate_dt() {
        return create_dt;
    }

    public void setIssue_dt(String issue_dt) {
        this.issue_dt = issue_dt;
    }

    public String getIssue_dt() {
        return issue_dt;
    }

    public void setActivity_dt(String activity_dt) {
        this.activity_dt = activity_dt;
    }

    public String getActivity_dt() {
        return activity_dt;
    }

    public void setPurchase_term(String purchase_term) {
        this.purchase_term = purchase_term;
    }

    public String getPurchase_term() {
        return purchase_term;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setApproval_dt(String approval_dt) {
        this.approval_dt = approval_dt;
    }

    public String getApproval_dt() {
        return approval_dt;
    }

    public void setApproval_id(String approval_id) {
        this.approval_id = approval_id;
    }

    public String getApproval_id() {
        return approval_id;
    }

    public void setEquiptState(String equiptState) {
        this.equiptState = equiptState;
    }

    public String getEquiptState() {
        return equiptState;
    }

    public void setEqNumber(String eqNumber) {
        this.eqNumber = eqNumber;
    }

    public String getEqNumber() {
        return eqNumber;
    }

    public void setBkgOrBLNo(String bkgOrBLNo) {
        this.BkgOrBLNo = bkgOrBLNo;
    }

    public String getBkgOrBLNo() {
        return BkgOrBLNo;
    }

    public void setContSize(Integer contSize) {
        this.contSize = contSize;
    }

    public Integer getContSize() {
        return contSize;
    }

    public void setContPercent(String contPercent) {
        this.contPercent = contPercent;
    }

    public String getContPercent() {
        return contPercent;
    }

    public void setContType(String contType) {
        this.contType = contType;
    }

    public String getContType() {
        return contType;
    }

    public void setContEmptyOrLaden(String contEmptyOrLaden) {
        this.contEmptyOrLaden = contEmptyOrLaden;
    }

    public String getContEmptyOrLaden() {
        return contEmptyOrLaden;
    }

    public void setSOCorCOC(String sOCorCOC) {
        this.SOCorCOC = sOCorCOC;
    }

    public String getSOCorCOC() {
        return SOCorCOC;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setSpecial_handling(String special_handling) {
        this.special_handling = special_handling;
    }

    public String getSpecial_handling() {
        return special_handling;
    }

    public void setDGorRForOG(String dGorRForOG) {
        this.DGorRForOG = dGorRForOG;
    }

    public String getDGorRForOG() {
        return DGorRForOG;
    }

    public void setPortClass(String portClass) {
        this.portClass = portClass;
    }

    public String getPortClass() {
        return portClass;
    }

    public void setImdgClass(String imdgClass) {
        this.imdgClass = imdgClass;
    }

    public String getImdgClass() {
        return imdgClass;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public String getUnno() {
        return unno;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getVariant() {
        return variant;
    }

    public void setFlashPoint(String flashPoint) {
        this.flashPoint = flashPoint;
    }

    public String getFlashPoint() {
        return flashPoint;
    }

    public void setTempFrom(String tempFrom) {
        this.tempFrom = tempFrom;
    }

    public String getTempFrom() {
        return tempFrom;
    }

    public void setTempTo(String tempTo) {
        this.tempTo = tempTo;
    }

    public String getTempTo() {
        return tempTo;
    }

    public void setOh(String oh) {
        this.oh = oh;
    }

    public String getOh() {
        return oh;
    }

    public void setOlf(String olf) {
        this.olf = olf;
    }

    public String getOlf() {
        return olf;
    }

    public void setOwl(String owl) {
        this.owl = owl;
    }

    public String getOwl() {
        return owl;
    }

    public void setOwr(String owr) {
        this.owr = owr;
    }

    public String getOwr() {
        return owr;
    }

    public void setOla(String ola) {
        this.ola = ola;
    }

    public String getOla() {
        return ola;
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

	public String getSizeTypeAmt() {
		return sizeTypeAmt;
	}

	public void setSizeTypeAmt(String sizeTypeAmt) {
		this.sizeTypeAmt = sizeTypeAmt;
	}

	public String getSizeTypeUSDAmt() {
		return sizeTypeUSDAmt;
	}

	public void setSizeTypeUSDAmt(String sizeTypeUSDAmt) {
		this.sizeTypeUSDAmt = sizeTypeUSDAmt;
	}

	public String getBkgBlAmt() {
		return bkgBlAmt;
	}

	public void setBkgBlAmt(String bkgBlAmt) {
		this.bkgBlAmt = bkgBlAmt;
	}

	public String getBkgBlUSDAmt() {
		return bkgBlUSDAmt;
	}

	public void setBkgBlUSDAmt(String bkgBlUSDAmt) {
		this.bkgBlUSDAmt = bkgBlUSDAmt;
	}

	public String getLumpsumAmt() {
		return lumpsumAmt;
	}

	public void setLumpsumAmt(String lumpsumAmt) {
		this.lumpsumAmt = lumpsumAmt;
	}

	public String getLumpsumUSDAmt() {
		return lumpsumUSDAmt;
	}

	public void setLumpsumUSDAmt(String lumpsumUSDAmt) {
		this.lumpsumUSDAmt = lumpsumUSDAmt;
	}
    
}
