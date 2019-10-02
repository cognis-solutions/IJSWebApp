package com.rclgroup.dolphin.ijs.web.entity;

import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;

import java.util.ArrayList;
import java.util.List;

public class IjsContractCostDTO extends IjsBaseDTO {
    private String rate20;
    private String rate40;
    private String rate45;
    private String splHandling;
    private String effectiveDate;
    private String expiryDate;
    private String currency;
    private String mtLaden;

    public void setRate20(String rate20) {
        this.rate20 = rate20;
    }

    public String getRate20() {
        return rate20;
    }

    public void setRate40(String rate40) {
        this.rate40 = rate40;
    }

    public String getRate40() {
        return rate40;
    }

    public void setRate45(String rate45) {
        this.rate45 = rate45;
    }

    public String getRate45() {
        return rate45;
    }

    public void setSplHandling(String splHandling) {
        this.splHandling = splHandling;
    }

    public String getSplHandling() {
        return splHandling;
    }

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMtLaden() {
		return mtLaden;
	}

	public void setMtLaden(String mtLaden) {
		this.mtLaden = mtLaden;
	}
    
}
