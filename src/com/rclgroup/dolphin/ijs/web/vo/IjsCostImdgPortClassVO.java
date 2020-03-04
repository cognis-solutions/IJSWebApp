package com.rclgroup.dolphin.ijs.web.vo;

public class IjsCostImdgPortClassVO {
	private String terminalDepotCd;
    private String imdgClass;
    private String portClass;
    private String excludeUnno;
    private String includeUnno;
    private String action;
    private boolean usedByContract;
    private String oldPortImdgCode;
    private String portImdgCode;
    private String errorMsgCd;
    private Integer portImdgSeqNo;
    private int seqNo; 

    public void setImdgClass(String imdgClass) {
        this.imdgClass = imdgClass;
    }

    public String getImdgClass() {
        return imdgClass;
    }

    public void setExcludeUnno(String excludeUnno) {
        this.excludeUnno = excludeUnno;
    }

    public String getExcludeUnno() {
        return excludeUnno;
    }

    public void setIncludeUnno(String includeUnno) {
        this.includeUnno = includeUnno;
    }

    public String getIncludeUnno() {
        return includeUnno;
    }

	public String getPortClass() {
		return portClass;
	}

	public void setPortClass(String portClass) {
		this.portClass = portClass;
	}

	public String getTerminalDepotCd() {
		return terminalDepotCd;
	}

	public void setTerminalDepotCd(String terminalDepotCd) {
		this.terminalDepotCd = terminalDepotCd;
	}

	public boolean isUsedByContract() {
		return usedByContract;
	}

	public void setUsedByContract(boolean usedByContract) {
		this.usedByContract = usedByContract;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPortImdgCode() {
		return portImdgCode;
	}

	public void setPortImdgCode(String portImdgCode) {
		this.portImdgCode = portImdgCode;
	}

	public String getOldPortImdgCode() {
		return oldPortImdgCode;
	}

	public void setOldPortImdgCode(String oldPortImdgCode) {
		this.oldPortImdgCode = oldPortImdgCode;
	}

	public String getErrorMsgCd() {
		return errorMsgCd;
	}

	public void setErrorMsgCd(String errorMsgCd) {
		this.errorMsgCd = errorMsgCd;
	}

	public Integer getPortImdgSeqNo() {
		return portImdgSeqNo;
	}

	public void setPortImdgSeqNo(Integer portImdgSeqNo) {
		this.portImdgSeqNo = portImdgSeqNo;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}


}
