package com.rclgroup.dolphin.ijs.web.vo;

public class IjsJORoutingLookUpVO {

    private String routingId;
    private String contractId;
    private String days;
    private String hours;
    private String distance;
    private String uom;
    private String fromLocation;
    private String toLocation;
    private String fromLocType;
    private String toLocType;
    private String fromTerminal;
    private String toTerminal;
    private String currency;
    private String legType;
    private int priority;
    private String vendorCode;
    private String transMode;
    private String sameVendorInSearch;
    //Nikash
    private String bargeValue;
    private String purchaseTerm;


    public String getPurchaseTerm() {
		return purchaseTerm;
	}

	public void setPurchaseTerm(String purchaseTerm) {
		this.purchaseTerm = purchaseTerm;
	}

	public String getBargeValue() {
		return bargeValue;
	}

	public void setBargeValue(String bargeValue) {
		this.bargeValue = bargeValue;
	}

	public void setRoutingId(String routingId) {
        this.routingId = routingId;
    }

    public String getRoutingId() {
        return routingId;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDays() {
        return days;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getHours() {
        return hours;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getUom() {
        return uom;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getFromLocation() {
        return fromLocation;
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

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setLegType(String legType) {
        this.legType = legType;
    }

    public String getLegType() {
        return legType;
    }

    public void setToLocType(String toLocType) {
        this.toLocType = toLocType;
    }

    public String getToLocType() {
        return toLocType;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public String getTransMode() {
        return transMode;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

	public String getSameVendorInSearch() {
		return sameVendorInSearch;
	}

	public void setSameVendorInSearch(String sameVendorInSearch) {
		this.sameVendorInSearch = sameVendorInSearch;
	}

	@Override
	public String toString() {
		return "IjsJORoutingLookUpVO [routingId=" + routingId + ", contractId=" + contractId + ", days=" + days
				+ ", hours=" + hours + ", distance=" + distance + ", uom=" + uom + ", fromLocation=" + fromLocation
				+ ", toLocation=" + toLocation + ", fromLocType=" + fromLocType + ", toLocType=" + toLocType
				+ ", fromTerminal=" + fromTerminal + ", toTerminal=" + toTerminal + ", currency=" + currency
				+ ", legType=" + legType + ", priority=" + priority + ", vendorCode=" + vendorCode + ", transMode="
				+ transMode + ", sameVendorInSearch=" + sameVendorInSearch + ", bargeValue=" + bargeValue
				+ ", purchaseTerm=" + purchaseTerm + "]";
	}
    
}
