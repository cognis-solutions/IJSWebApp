package com.rclgroup.dolphin.ijs.web.vo;

public class IjsMaintainSaveVO {
	
	
	private String  JoNumber ;
	private String  orderDate;
	private String  createdBy;
	private String  vendorID;
	private String  jobOrdType;
	private String  vendorName;
	private String  detailType;
	private String  detailVersion;
	private String	fromLoaction;
	private String	toLocation; 
	private String	fromTerminal;
	private String  toTerminal;
	private String	fromLocType; 
	private String  toLocType;
	private String  amount;
	private String	amountNum; 
	private String  amountUSD;
	private String  currency; 
	private String	routingId;
	private String	contractId; 
	private String  status; 
	private String  FSC;
	private String  reasonCode;
	private String  transMode;
	private String	adhoc_yn; 
	private String	bk_bl_ad;
	private String	user_type;
	private String	SOCorCOC;
	private String  priority;
	public String getJoNumber() {
		return JoNumber;
	}
	public void setJoNumber(String joNumber) {
		JoNumber = joNumber;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getVendorID() {
		return vendorID;
	}
	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}
	public String getJobOrdType() {
		return jobOrdType;
	}
	public void setJobOrdType(String jobOrdType) {
		this.jobOrdType = jobOrdType;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getDetailType() {
		return detailType;
	}
	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}
	public String getDetailVersion() {
		return detailVersion;
	}
	public void setDetailVersion(String detailVersion) {
		this.detailVersion = detailVersion;
	}
	public String getFromLoaction() {
		return fromLoaction;
	}
	public void setFromLoaction(String fromLoaction) {
		this.fromLoaction = fromLoaction;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public String getFromTerminal() {
		return fromTerminal;
	}
	public void setFromTerminal(String fromTerminal) {
		this.fromTerminal = fromTerminal;
	}
	public String getToTerminal() {
		return toTerminal;
	}
	public void setToTerminal(String toTerminal) {
		this.toTerminal = toTerminal;
	}
	public String getFromLocType() {
		return fromLocType;
	}
	public void setFromLocType(String fromLocType) {
		this.fromLocType = fromLocType;
	}
	public String getToLocType() {
		return toLocType;
	}
	public void setToLocType(String toLocType) {
		this.toLocType = toLocType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmountNum() {
		return amountNum;
	}
	public void setAmountNum(String amountNum) {
		this.amountNum = amountNum;
	}
	public String getAmountUSD() {
		return amountUSD;
	}
	public void setAmountUSD(String amountUSD) {
		this.amountUSD = amountUSD;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getRoutingId() {
		return routingId;
	}
	public void setRoutingId(String routingId) {
		this.routingId = routingId;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFSC() {
		return FSC;
	}
	public void setFSC(String fSC) {
		FSC = fSC;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getTransMode() {
		return transMode;
	}
	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}
	public String getAdhoc_yn() {
		return adhoc_yn;
	}
	public void setAdhoc_yn(String adhoc_yn) {
		this.adhoc_yn = adhoc_yn;
	}
	public String getBk_bl_ad() {
		return bk_bl_ad;
	}
	public void setBk_bl_ad(String bk_bl_ad) {
		this.bk_bl_ad = bk_bl_ad;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getSOCorCOC() {
		return SOCorCOC;
	}
	public void setSOCorCOC(String sOCorCOC) {
		SOCorCOC = sOCorCOC;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	@Override
	public String toString() {
		return "IjsMaintainSaveVO [JoNumber=" + JoNumber + ", orderDate=" + orderDate + ", createdBy=" + createdBy
				+ ", vendorID=" + vendorID + ", jobOrdType=" + jobOrdType + ", vendorName=" + vendorName
				+ ", detailType=" + detailType + ", detailVersion=" + detailVersion + ", fromLoaction=" + fromLoaction
				+ ", toLocation=" + toLocation + ", fromTerminal=" + fromTerminal + ", toTerminal=" + toTerminal
				+ ", fromLocType=" + fromLocType + ", toLocType=" + toLocType + ", amount=" + amount + ", amountNum="
				+ amountNum + ", amountUSD=" + amountUSD + ", currency=" + currency + ", routingId=" + routingId
				+ ", contractId=" + contractId + ", status=" + status + ", FSC=" + FSC + ", reasonCode=" + reasonCode
				+ ", transMode=" + transMode + ", adhoc_yn=" + adhoc_yn + ", bk_bl_ad=" + bk_bl_ad + ", user_type="
				+ user_type + ", SOCorCOC=" + SOCorCOC + ", priority=" + priority + "]";
	} 
	
	
	
	

}
