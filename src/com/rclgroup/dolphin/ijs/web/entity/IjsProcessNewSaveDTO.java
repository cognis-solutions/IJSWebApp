
  package com.rclgroup.dolphin.ijs.web.entity;
  
  public class IjsProcessNewSaveDTO extends IjsBaseDTO {
  
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
	    private String bargeValue;
	    private String purchaseTerm;
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
		public String getDays() {
			return days;
		}
		public void setDays(String days) {
			this.days = days;
		}
		public String getHours() {
			return hours;
		}
		public void setHours(String hours) {
			this.hours = hours;
		}
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		public String getUom() {
			return uom;
		}
		public void setUom(String uom) {
			this.uom = uom;
		}
		public String getFromLocation() {
			return fromLocation;
		}
		public void setFromLocation(String fromLocation) {
			this.fromLocation = fromLocation;
		}
		public String getToLocation() {
			return toLocation;
		}
		public void setToLocation(String toLocation) {
			this.toLocation = toLocation;
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
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getLegType() {
			return legType;
		}
		public void setLegType(String legType) {
			this.legType = legType;
		}
		public int getPriority() {
			return priority;
		}
		public void setPriority(int priority) {
			this.priority = priority;
		}
		public String getVendorCode() {
			return vendorCode;
		}
		public void setVendorCode(String vendorCode) {
			this.vendorCode = vendorCode;
		}
		public String getTransMode() {
			return transMode;
		}
		public void setTransMode(String transMode) {
			this.transMode = transMode;
		}
		public String getSameVendorInSearch() {
			return sameVendorInSearch;
		}
		public void setSameVendorInSearch(String sameVendorInSearch) {
			this.sameVendorInSearch = sameVendorInSearch;
		}
		public String getBargeValue() {
			return bargeValue;
		}
		public void setBargeValue(String bargeValue) {
			this.bargeValue = bargeValue;
		}
		public String getPurchaseTerm() {
			return purchaseTerm;
		}
		public void setPurchaseTerm(String purchaseTerm) {
			this.purchaseTerm = purchaseTerm;
		}
		@Override
		public String toString() {
			return "IjsProcessNewSaveDTO [routingId=" + routingId + ", contractId=" + contractId + ", days=" + days
					+ ", hours=" + hours + ", distance=" + distance + ", uom=" + uom + ", fromLocation=" + fromLocation
					+ ", toLocation=" + toLocation + ", fromLocType=" + fromLocType + ", toLocType=" + toLocType
					+ ", fromTerminal=" + fromTerminal + ", toTerminal=" + toTerminal + ", currency=" + currency
					+ ", legType=" + legType + ", priority=" + priority + ", vendorCode=" + vendorCode + ", transMode="
					+ transMode + ", sameVendorInSearch=" + sameVendorInSearch + ", bargeValue=" + bargeValue
					+ ", purchaseTerm=" + purchaseTerm + "]";
		}
  
  
  
  
  }
 