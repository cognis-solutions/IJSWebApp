 /*-----------------------------------------------------------------------------------------------------------
IjsRateVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 21/09/17  NIIT       IJS            value object for IJS contract rate(cost) search screen
02 05/10/17  NIIT       IJS            changed upto parameter datatype and added two parameters
03 20/10/17  NIIT       IJS            added two parameters
04 25/01/18  NIIT       IJS            added Vessel codes
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsRateVO {
    public IjsRateVO() {
    }
    private String startDate;
    private String endDate;
    private String service;
    private String mtOrLaden;
    private String rateBasis;
    private String eqCatq;
    private String rateStatus;
    private String chargeCode;
    private String term;
    private String calcMethod;
    private String eqType;
    
    private String uom;
    private String impOrExp;
    private String splHandling;
    private String currency;
    private String portClassCode;  
    private String imdgDetails;  
    private String oogSetup;  
    private String splCostByBlOrBooking;
    
    private String lumpSum;
    private String errorMsg;
    private String perTrip;
    private String mot;//##02
    private String detailSeqNumbers;
   // private List<IjsContractSplCostByBlOrBkVO> splCostByBlOrBooking;
    //private List<IjsContractSplCostByBlOrBkVO> splCostByBlOrBookingList;
    
    private List<IjsContractOogSetupVO> oogSetUpList;//##03  //TODO ASHISH REMOVE THIS
    private String billDtlSeqNos;//MD
    private String costDtlSeqNos;//MD
    private String vesselCodes;//##04
    private List<IjsCostImdgPortClassVO> imDgList;//##03  //TODO ASHISH REMOVE THIS
    private List<IjsCostImdgPortClassVO> portClassList;//##03  //TODO ASHISH REMOVE THIS
    private String  operation;
    private List<String> eqTypeList;
    private boolean errorAllreadySet;
    private boolean editMode;
    
    private String rate20;
    private String rate40;
    private String rate45;
    private String effectiveBillingRate20;
    private String effectiveBillingRate40;
    private String effectiveBillingRate45;
    private String effectiveCostRate20;
    private String effectiveCostRate40;
    private String effectiveCostRate45;
    private int upto;
    private int vesselSeq;
    private int portAndImdgSeqNum;
    private int oogSetupSeqNum;//##02
    private int costRateSequenceNum;//##02
    private int costRateDetailSeqNum;
    private int eqTypeSeqNum;//##03
    private int termSeqNum;//##03
    private String spCustomers;
    private String portCode;
    private String imdgCode;
    private String oogCode;
    private String exchangeError;
    

//      private String rate20;
//      private String rate40;
//      private String rate45;
//      private String upto;
//      private String vesselSeq;
//      private String portAndImdgSeqNum;
//      private String oogSetupSeqNum;//##02
//      private String costRateSequenceNum;//##02
//      private String costRateDetailSeqNum;
//      private String eqTypeSeqNum;//##03
//      private String termSeqNum;//##03


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

    public void setService(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setMtOrLaden(String mtOrLaden) {
        this.mtOrLaden = mtOrLaden;
    }

    public String getMtOrLaden() {
        return mtOrLaden;
    }

    public void setRateBasis(String rateBasis) {
        this.rateBasis = rateBasis;
    }

    public String getRateBasis() {
        return rateBasis;
    }

    public void setEqCatq(String eqCatq) {
        this.eqCatq = eqCatq;
    }

    public String getEqCatq() {
        return eqCatq;
    }

    public void setRateStatus(String rateStatus) {
        this.rateStatus = rateStatus;
    }

    public String getRateStatus() {
        return rateStatus;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setCalcMethod(String calcMethod) {
        this.calcMethod = calcMethod;
    }

    public String getCalcMethod() {
        return calcMethod;
    }

    public void setEqType(String eqType) {
        this.eqType = eqType;
    }

    public String getEqType() {
        return eqType;
    }

    

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getUom() {
        return uom;
    }

    public void setImpOrExp(String impOrExp) {
        this.impOrExp = impOrExp;
    }

    public String getImpOrExp() {
        return impOrExp;
    }

    public void setSplHandling(String splHandling) {
        this.splHandling = splHandling;
    }

    public String getSplHandling() {
        return splHandling;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setPortClassCode(String portClassCode) {
        this.portClassCode = portClassCode;
    }

    public String getPortClassCode() {
        return portClassCode;
    }

    public void setImdgDetails(String imdgDetails) {
        this.imdgDetails = imdgDetails;
    }

    public String getImdgDetails() {
        return imdgDetails;
    }

    public void setOogSetup(String oogSetup) {
        this.oogSetup = oogSetup;
    }

    public String getOogSetup() {
        return oogSetup;
    }

    public void setSplCostByBlOrBooking(String splCostByBlOrBooking) {
        this.splCostByBlOrBooking = splCostByBlOrBooking;
    }

    public String getSplCostByBlOrBooking() {
        return splCostByBlOrBooking;
    }

    

    public void setLumpSum(String lumpSum) {
        this.lumpSum = lumpSum;
    }

    public String getLumpSum() {
        return lumpSum;
    }

    

    public String getErrorMsg() {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public void setPerTrip(String perTrip) {
        this.perTrip = perTrip;
    }

    public String getPerTrip() {
        return perTrip;
    }

    
    //##02 END

    public void setMot(String mot) {
        this.mot = mot;
    }

    public String getMot() {
        return mot;
    }

    

    public void setOogSetUpList(List<IjsContractOogSetupVO> oogSetUpList) {
        this.oogSetUpList = oogSetUpList;
    }

    public List<IjsContractOogSetupVO> getOogSetUpList() {
        return oogSetUpList;
    }

    

    public void setDetailSeqNumbers(String detailSeqNumbers) {
        this.detailSeqNumbers = detailSeqNumbers;
    }

    public String getDetailSeqNumbers() {
        return detailSeqNumbers;
    }


//    public void setSplCostByBlOrBooking(List<IjsContractSplCostByBlOrBkVO> splCostByBlOrBooking) {
//        this.splCostByBlOrBooking = splCostByBlOrBooking;
//    }
//
//    public List<IjsContractSplCostByBlOrBkVO> getSplCostByBlOrBooking() {
//        return splCostByBlOrBooking;
//    }
//
//    public void setSplCostByBlOrBookingList(List<IjsContractSplCostByBlOrBkVO> splCostByBlOrBookingList) {
//        this.splCostByBlOrBookingList = splCostByBlOrBookingList;
//    }
//
//    public List<IjsContractSplCostByBlOrBkVO> getSplCostByBlOrBookingList() {
//        return splCostByBlOrBookingList;
//    }


    public void setBillDtlSeqNos(String billDtlSeqNos) {
        this.billDtlSeqNos = billDtlSeqNos;
    }

    public String getBillDtlSeqNos() {
        return billDtlSeqNos;
    }

    public void setCostDtlSeqNos(String costDtlSeqNos) {
        this.costDtlSeqNos = costDtlSeqNos;
    }

    public String getCostDtlSeqNos() {
        return costDtlSeqNos;
    }

    public void setVesselCodes(String vesselCodes) {
        this.vesselCodes = vesselCodes;
    }

    public String getVesselCodes() {
        return vesselCodes;
    }

    

    public void setImDgList(List<IjsCostImdgPortClassVO> imDgList) {
        this.imDgList = imDgList;
    }

    public List<IjsCostImdgPortClassVO> getImDgList() {
        return imDgList;
    }

    public void setPortClassList(List<IjsCostImdgPortClassVO> portClassList) {
        this.portClassList = portClassList;
    }

    public List<IjsCostImdgPortClassVO> getPortClassList() {
        return portClassList;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setEqTypeList(List<String> eqTypeList) {
        this.eqTypeList = eqTypeList;
    }

    public List<String> getEqTypeList() {
        return eqTypeList;
    }

    public void setErrorAllreadySet(boolean errorAllreadySet) {
        this.errorAllreadySet = errorAllreadySet;
    }

    public boolean isErrorAllreadySet() {
        return errorAllreadySet;
    }
    
    
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
    
    public void setUpto(int upto) {
        this.upto = upto;
    }

    public int getUpto() {
        return upto;
    }

    public void setCostRateSequenceNum(int costRateSequenceNum) {
        this.costRateSequenceNum = costRateSequenceNum;
    }

    public int getCostRateSequenceNum() {
        return costRateSequenceNum;
    }
    //##02 BEGIN
    public void setPortAndImdgSeqNum(int portAndImdgSeqNum) {
        this.portAndImdgSeqNum = portAndImdgSeqNum;
    }

    public int getPortAndImdgSeqNum() {
        return portAndImdgSeqNum;
    }

    public void setOogSetupSeqNum(int oogSetupSeqNum) {
        this.oogSetupSeqNum = oogSetupSeqNum;
    }

    public int getOogSetupSeqNum() {
        return oogSetupSeqNum;
    }
    
    public void setEqTypeSeqNum(int eqTypeSeqNum) {
        this.eqTypeSeqNum = eqTypeSeqNum;
    }

    public int getEqTypeSeqNum() {
        return eqTypeSeqNum;
    }

    public void setTermSeqNum(int termSeqNum) {
        this.termSeqNum = termSeqNum;
    }

    public int getTermSeqNum() {
        return termSeqNum;
    }
    
    public void setCostRateDetailSeqNum(int costRateDetailSeqNum) {
        this.costRateDetailSeqNum = costRateDetailSeqNum;
    }

    public int getCostRateDetailSeqNum() {
        return costRateDetailSeqNum;
    }
    
    public void setVesselSeq(int vesselSeq) {
        this.vesselSeq = vesselSeq;
    }

    public int getVesselSeq() {
        return vesselSeq;
    }

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public String getSpCustomers() {
		return spCustomers;
	}

	public void setSpCustomers(String spCustomers) {
		this.spCustomers = spCustomers;
	}

	public String getEffectiveBillingRate20() {
		return effectiveBillingRate20;
	}

	public void setEffectiveBillingRate20(String effectiveBillingRate20) {
		this.effectiveBillingRate20 = effectiveBillingRate20;
	}

	public String getEffectiveBillingRate40() {
		return effectiveBillingRate40;
	}

	public void setEffectiveBillingRate40(String effectiveBillingRate40) {
		this.effectiveBillingRate40 = effectiveBillingRate40;
	}

	public String getEffectiveBillingRate45() {
		return effectiveBillingRate45;
	}

	public void setEffectiveBillingRate45(String effectiveBillingRate45) {
		this.effectiveBillingRate45 = effectiveBillingRate45;
	}

	public String getEffectiveCostRate20() {
		return effectiveCostRate20;
	}

	public void setEffectiveCostRate20(String effectiveCostRate20) {
		this.effectiveCostRate20 = effectiveCostRate20;
	}

	public String getEffectiveCostRate40() {
		return effectiveCostRate40;
	}

	public void setEffectiveCostRate40(String effectiveCostRate40) {
		this.effectiveCostRate40 = effectiveCostRate40;
	}

	public String getEffectiveCostRate45() {
		return effectiveCostRate45;
	}

	public void setEffectiveCostRate45(String effectiveCostRate45) {
		this.effectiveCostRate45 = effectiveCostRate45;
	}

	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public String getImdgCode() {
		return imdgCode;
	}

	public void setImdgCode(String imdgCode) {
		this.imdgCode = imdgCode;
	}

	public String getOogCode() {
		return oogCode;
	}

	public void setOogCode(String oogCode) {
		this.oogCode = oogCode;
	}

	public String getExchangeError() {
		return exchangeError;
	}

	public void setExchangeError(String exchangeError) {
		this.exchangeError = exchangeError;
	}


}
