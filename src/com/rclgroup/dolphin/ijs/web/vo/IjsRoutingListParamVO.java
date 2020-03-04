package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsRoutingListParamVO {
    private String routingId;
    private String contractId;
    private String days;
    private String hours;
    private String fromLocation;
    private String toLocation;
    private String fromLocType;
    private String toLocType;
    private String fromTerminal;
    private String toTerminal;
    private String currency;
    private String legType;
    private String priority;
    private String vendorCode;
    private String transMode;
    private String contSize;
    private String contrType;
    private String contrNum;
    private List<IjsJORoutingLookUpVO> list;
	private List<String> eqpList;
    private List<IjsEQDetailVO> eqDetails;
    private IjsProcessNewSaveVO vendors;
    List<IjsJOSummaryParamVO> lstJOSummaryParam;
    


 





	public List<IjsJOSummaryParamVO> getLstJOSummaryParam() {
		return lstJOSummaryParam;
	}

	public void setLstJOSummaryParam(List<IjsJOSummaryParamVO> lstJOSummaryParam) {
		this.lstJOSummaryParam = lstJOSummaryParam;
	}

	public IjsProcessNewSaveVO getVendors() {
		return vendors;
	}

	public void setVendors(IjsProcessNewSaveVO vendors) {
		this.vendors = vendors;
	}

	public List<String> getEqpList() {
		return eqpList;
	}

	public void setEqpList(List<String> eqpList) {
		this.eqpList = eqpList;
	}

	public List<IjsEQDetailVO> getEqDetails() {
		return eqDetails;
	}

	public void setEqDetails(List<IjsEQDetailVO> eqDetails) {
		this.eqDetails = eqDetails;
	}

	public List<IjsJORoutingLookUpVO> getList() {
		return list;
	}

	public void setList(List<IjsJORoutingLookUpVO> list) {
		this.list = list;
	}

	public String getContSize() {
		return contSize;
	}

	public void setContSize(String contSize) {
		this.contSize = contSize;
	}

	public String getContrType() {
		return contrType;
	}

	public void setContrType(String contrType) {
		this.contrType = contrType;
	}

	public String getContrNum() {
		return contrNum;
	}

	public void setContrNum(String contrNum) {
		this.contrNum = contrNum;
	}

	public void setRoutingId(String routingId) {
        this.routingId = routingId;
    }

    public String getRoutingId() {
        return routingId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
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

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
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

	@Override
	public String toString() {
		return "IjsRoutingListParamVO [routingId=" + routingId + ", contractId=" + contractId + ", days=" + days
				+ ", hours=" + hours + ", fromLocation=" + fromLocation + ", toLocation=" + toLocation
				+ ", fromLocType=" + fromLocType + ", toLocType=" + toLocType + ", fromTerminal=" + fromTerminal
				+ ", toTerminal=" + toTerminal + ", currency=" + currency + ", legType=" + legType + ", priority="
				+ priority + ", vendorCode=" + vendorCode + ", transMode=" + transMode + ", contSize=" + contSize
				+ ", contrType=" + contrType + ", contrNum=" + contrNum + ", list=" + list + ", eqpList=" + eqpList
				+ ", eqDetails=" + eqDetails + "]";
	}




}
