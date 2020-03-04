package com.rclgroup.dolphin.ijs.web.vo;

public class IjsProcessJoLocVendorVO 
{

	private String fromLoc;
	private String toLoc;
	private String hasBundle;
	public String getFromLoc() {
		return fromLoc;
	}
	public void setFromLoc(String fromLoc) {
		this.fromLoc = fromLoc;
	}
	public String getToLoc() {
		return toLoc;
	}
	public void setToLoc(String toLoc) {
		this.toLoc = toLoc;
	}
	public String getHasBundle() {
		return hasBundle;
	}
	public void setHasBundle(String hasBundle) {
		this.hasBundle = hasBundle;
	}
	@Override
	public String toString() {
		return "IjsProcessJoLocVendorVO [fromLoc=" + fromLoc + ", toLoc=" + toLoc + ", hasBundle=" + hasBundle + "]";
	}
	
	
	
}
