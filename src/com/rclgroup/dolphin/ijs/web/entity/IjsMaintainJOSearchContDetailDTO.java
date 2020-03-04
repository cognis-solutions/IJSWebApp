package com.rclgroup.dolphin.ijs.web.entity;

import java.util.List;

public class IjsMaintainJOSearchContDetailDTO extends IjsBaseDTO {
    private String equiptState;
    private String eqNumber;
    private String BkgOrBLNo;
    private Integer contSize;
    private String contPercent;
    private String contWeight;
    private String contType;
    private String contEmptyOrLaden;
    private String SOCorCOC;
    private String startDate;
    private String endDate;
    private String currency;
    private String amount;
    private String amountUSD;
    private String special_handling;
    private String DGorRForOG;
    private String portClass;
    private String imdgClass;
    private String unno;
    private String variant;
    private String flashPoint;
    private String tempFrom;
    private String tempTo;
    private String oh;
    private String olf;
    private String owl;
    private String owr;
    private String ola;
    private String amountSize;
    private String amountSizeUSD;
    private String amountLumpsum;
    private String amountLumpsumUSD;
    private String amountBkgBl;
    private String amountBkgBlUSD;
    private String rateBasis;
    private String lumpsumCostId;

    
    public void setBkgOrBLNo(String bkgOrBLNo) {
        this.BkgOrBLNo = bkgOrBLNo;
    }

    public String getBkgOrBLNo() {
        return BkgOrBLNo;
    }

    public void setContSize(Integer contSize) {
        this.contSize = contSize;
    }

    public Integer getContSize() {
        return contSize;
    }

    public void setContPercent(String contPercent) {
        this.contPercent = contPercent;
    }

    public String getContPercent() {
        return contPercent;
    }

    public void setContType(String contType) {
        this.contType = contType;
    }

    public String getContType() {
        return contType;
    }

    public void setContEmptyOrLaden(String contEmptyOrLaden) {
        this.contEmptyOrLaden = contEmptyOrLaden;
    }

    public String getContEmptyOrLaden() {
        return contEmptyOrLaden;
    }

    public void setSOCorCOC(String sOCorCOC) {
        this.SOCorCOC = sOCorCOC;
    }

    public String getSOCorCOC() {
        return SOCorCOC;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setDGorRForOG(String dGorRForOG) {
        this.DGorRForOG = dGorRForOG;
    }

    public String getDGorRForOG() {
        return DGorRForOG;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmountUSD(String amountUSD) {
        this.amountUSD = amountUSD;
    }

    public String getAmountUSD() {
        return amountUSD;
    }

    public void setEquiptState(String equiptState) {
        this.equiptState = equiptState;
    }

    public String getEquiptState() {
        return equiptState;
    }

    public void setEqNumber(String eqNumber) {
        this.eqNumber = eqNumber;
    }

    public String getEqNumber() {
        return eqNumber;
    }

    public void setSpecial_handling(String special_handling) {
        this.special_handling = special_handling;
    }

    public String getSpecial_handling() {
        return special_handling;
    }

    public void setPortClass(String portClass) {
        this.portClass = portClass;
    }

    public String getPortClass() {
        return portClass;
    }

    public void setImdgClass(String imdgClass) {
        this.imdgClass = imdgClass;
    }

    public String getImdgClass() {
        return imdgClass;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public String getUnno() {
        return unno;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getVariant() {
        return variant;
    }

    public void setFlashPoint(String flashPoint) {
        this.flashPoint = flashPoint;
    }

    public String getFlashPoint() {
        return flashPoint;
    }

    public void setTempFrom(String tempFrom) {
        this.tempFrom = tempFrom;
    }

    public String getTempFrom() {
        return tempFrom;
    }

    public void setOh(String oh) {
        this.oh = oh;
    }

    public String getOh() {
        return oh;
    }

    public void setOlf(String olf) {
        this.olf = olf;
    }

    public String getOlf() {
        return olf;
    }

    public void setOwl(String owl) {
        this.owl = owl;
    }

    public String getOwl() {
        return owl;
    }

    public void setOwr(String owr) {
        this.owr = owr;
    }

    public String getOwr() {
        return owr;
    }

    public void setOla(String ola) {
        this.ola = ola;
    }

    public String getOla() {
        return ola;
    }

    public void setTempTo(String tempTo) {
        this.tempTo = tempTo;
    }

    public String getTempTo() {
        return tempTo;
    }

	public String getContWeight() {
		return contWeight;
	}

	public void setContWeight(String contWeight) {
		this.contWeight = contWeight;
	}

	public String getAmountSize() {
		return amountSize;
	}

	public void setAmountSize(String amountSize) {
		this.amountSize = amountSize;
	}

	public String getAmountSizeUSD() {
		return amountSizeUSD;
	}

	public void setAmountSizeUSD(String amountSizeUSD) {
		this.amountSizeUSD = amountSizeUSD;
	}

	public String getAmountLumpsum() {
		return amountLumpsum;
	}

	public void setAmountLumpsum(String amountLumpsum) {
		this.amountLumpsum = amountLumpsum;
	}

	public String getAmountLumpsumUSD() {
		return amountLumpsumUSD;
	}

	public void setAmountLumpsumUSD(String amountLumpsumUSD) {
		this.amountLumpsumUSD = amountLumpsumUSD;
	}

	public String getAmountBkgBl() {
		return amountBkgBl;
	}

	public void setAmountBkgBl(String amountBkgBl) {
		this.amountBkgBl = amountBkgBl;
	}

	public String getAmountBkgBlUSD() {
		return amountBkgBlUSD;
	}

	public void setAmountBkgBlUSD(String amountBkgBlUSD) {
		this.amountBkgBlUSD = amountBkgBlUSD;
	}

	public String getRateBasis() {
		return rateBasis;
	}

	public void setRateBasis(String rateBasis) {
		this.rateBasis = rateBasis;
	}

	public String getLumpsumCostId() {
		return lumpsumCostId;
	}

	public void setLumpsumCostId(String lumpsumCostId) {
		this.lumpsumCostId = lumpsumCostId;
	}


}
