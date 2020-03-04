package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsCostRateSetupVO {
	
	private String terminalDepotCode;
	private String portImdgType;
	private List<IjsContractOogSetupVO> oogSetUpList;
	private List<IjsCostImdgPortClassVO> portList; 
	private List<IjsCostImdgPortClassVO> imdgList; 
	private List<String> portCodeList;
	private List<String> imdgCodeList;
	private List<String> oogCodeList;
	public List<IjsContractOogSetupVO> getOogSetUpList() {
		return oogSetUpList;
	}
	public void setOogSetUpList(List<IjsContractOogSetupVO> oogSetUpList) {
		this.oogSetUpList = oogSetUpList;
	}
	public String getTerminalDepotCode() {
		return terminalDepotCode;
	}
	public void setTerminalDepotCode(String terminalDepotCode) {
		this.terminalDepotCode = terminalDepotCode;
	}
	
	public List<String> getPortCodeList() {
		return portCodeList;
	}
	public void setPortCodeList(List<String> portCodeList) {
		this.portCodeList = portCodeList;
	}
	public List<String> getImdgCodeList() {
		return imdgCodeList;
	}
	public void setImdgCodeList(List<String> imdgCodeList) {
		this.imdgCodeList = imdgCodeList;
	}
	public List<String> getOogCodeList() {
		return oogCodeList;
	}
	public void setOogCodeList(List<String> oogCodeList) {
		this.oogCodeList = oogCodeList;
	}
	public List<IjsCostImdgPortClassVO> getPortList() {
		return portList;
	}
	public void setPortList(List<IjsCostImdgPortClassVO> portList) {
		this.portList = portList;
	}
	public List<IjsCostImdgPortClassVO> getImdgList() {
		return imdgList;
	}
	public void setImdgList(List<IjsCostImdgPortClassVO> imdgList) {
		this.imdgList = imdgList;
	}
	public String getPortImdgType() {
		return portImdgType;
	}
	public void setPortImdgType(String portImdgType) {
		this.portImdgType = portImdgType;
	}
	
}
