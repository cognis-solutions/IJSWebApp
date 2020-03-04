package com.rclgroup.dolphin.ijs.web.vo;

public class IjsEQDetailVO {
	private String eqType;
	private String eqSize;
	private String eqNumber;
	
	
	private String type;
	private String size;
	private String eqpNum;
	
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getEqpNum() {
		return eqpNum;
	}
	public void setEqpNum(String eqpNum) {
		this.eqpNum = eqpNum;
	}
	public String getEqType() {
		return eqType;
	}
	public void setEqType(String eqType) {
		this.eqType = eqType;
	}
	public String getEqSize() {
		return eqSize;
	}
	public void setEqSize(String eqSize) {
		this.eqSize = eqSize;
	}
	public String getEqNumber() {
		return eqNumber;
	}
	public void setEqNumber(String eqNumber) {
		this.eqNumber = eqNumber;
	}
	@Override
	public String toString() {
		return "IjsEQDetailVO [eqType=" + eqType + ", eqSize=" + eqSize + ", eqNumber=" + eqNumber + ", type=" + type
				+ ", size=" + size + ", eqpNum=" + eqpNum + "]";
	}
	
}
