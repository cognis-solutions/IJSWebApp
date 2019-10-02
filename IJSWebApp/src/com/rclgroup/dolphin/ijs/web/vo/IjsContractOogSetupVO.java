package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractOogSetupVO {
	private String action;
    private String oogSetupCode;
    private String oldOogSetupCode;
    private String minOverLength;
    private String maxOverLength;
    private String minOverWidth;
    private String maxOverWidth;
    private String minOverHeight;
    private String maxOverHeight;
    private boolean usedByContract;
    private String errorMsg;


    public void setOogSetupCode(String oogSetupCode) {
        this.oogSetupCode = oogSetupCode;
    }

    public String getOogSetupCode() {
        return oogSetupCode;
    }

    public void setMinOverLength(String minOverLength) {
        this.minOverLength = minOverLength;
    }

    public String getMinOverLength() {
        return minOverLength;
    }

    public void setMaxOverLength(String maxOverLength) {
        this.maxOverLength = maxOverLength;
    }

    public String getMaxOverLength() {
        return maxOverLength;
    }

    public void setMinOverWidth(String minOverWidth) {
        this.minOverWidth = minOverWidth;
    }

    public String getMinOverWidth() {
        return minOverWidth;
    }

    public void setMaxOverWidth(String maxOverWidth) {
        this.maxOverWidth = maxOverWidth;
    }

    public String getMaxOverWidth() {
        return maxOverWidth;
    }

    public void setMinOverHeight(String minOverHeight) {
        this.minOverHeight = minOverHeight;
    }

    public String getMinOverHeight() {
        return minOverHeight;
    }

    public void setMaxOverHeight(String maxOverHeight) {
        this.maxOverHeight = maxOverHeight;
    }

    public String getMaxOverHeight() {
        return maxOverHeight;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getOldOogSetupCode() {
		return oldOogSetupCode;
	}

	public void setOldOogSetupCode(String oldOogSetupCode) {
		this.oldOogSetupCode = oldOogSetupCode;
	}
    
}
