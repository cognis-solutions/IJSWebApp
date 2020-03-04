/*-----------------------------------------------------------------------------------------------------------
IjsContractRateUIM.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 07/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS             keep the cost rate results for cost rate screen
02 05/10/17  NIIT       IJS             adding the cost rate functionality information
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.ui;

import java.util.List;

import com.rclgroup.dolphin.ijs.web.common.IjsContractRateResult;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractEqTypeVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsCostRateSetupVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;

public class IjsContractRateUIM extends IjsBaseActionForm{
    //##01 BEGIN
    private int routingNumber;
    private IjsContractRateResult<IjsRateVO> rateResults;
    private List<IjsRateVO> ijsRateList;
    private IjsRateVO ijsRateVO;
    //##02 BEGIN
    private List<Integer> cntrRateSeqList;
    private List<String> successMgsList;
    private List<String> errorMsgList;
    //##02 END
     //##03 BEGIN
    private String contractNumber;
    private IjsContractRateResult<IjsRateVO> billingRateResults;
    private List<IjsContractEqTypeVO> eqTypeList;
   // private List<IjsContractShipmentTermVO> termCodesList;
    private String detailSeqNumbers;
    private String paymentFSC;
    //CR#04 Start
    private IjsCostRateSetupVO costRateSetup;
    private String rateType;
    private boolean dgSetUp;
    private boolean imdgSetUp;
   
    //CR#04 END
    
    
    
    
    public IjsContractRateUIM() {
    }

    public void setRateResults(IjsContractRateResult<IjsRateVO> rateResults) {
        this.rateResults = rateResults;
    }

    public IjsContractRateResult<IjsRateVO> getRateResults() {
        return rateResults;
    }

    public void setRoutingNumber(int routingNumber) {
        this.routingNumber = routingNumber;
    }

    public int getRoutingNumber() {
        return routingNumber;
    }

    public void setIjsRateVO(IjsRateVO ijsRateVO) {
        this.ijsRateVO = ijsRateVO;
    }

    public IjsRateVO getIjsRateVO() {
        return ijsRateVO;
    }
    //##01 END
    //##02 BEGIN
    public void setCntrRateSeqList(List<Integer> cntrRateSeqList) {
        this.cntrRateSeqList = cntrRateSeqList;
    }

    public List<Integer> getCntrRateSeqList() {
        return cntrRateSeqList;
    }

    public void setSuccessMgsList(List<String> successMgsList) {
        this.successMgsList = successMgsList;
    }

    public List<String> getSuccessMgsList() {
        return successMgsList;
    }

    public void setErrorMsgList(List<String> errorMsgList) {
        this.errorMsgList = errorMsgList;
    }

    public List<String> getErrorMsgList() {
        return errorMsgList;
    }
    //##02 END

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setBillingRateResults(IjsContractRateResult<IjsRateVO> billingRateResults) {
        this.billingRateResults = billingRateResults;
    }

    public IjsContractRateResult<IjsRateVO> getBillingRateResults() {
        return billingRateResults;
    }

    public void setEqTypeList(List<IjsContractEqTypeVO> eqTypeList) {
        this.eqTypeList = eqTypeList;
    }

    public List<IjsContractEqTypeVO> getEqTypeList() {
        return eqTypeList;
    }

    public void setDetailSeqNumbers(String detailSeqNumbers) {
        this.detailSeqNumbers = detailSeqNumbers;
    }

    public String getDetailSeqNumbers() {
        return detailSeqNumbers;
    }

    public void setIjsRateList(List<IjsRateVO> ijsRateList) {
        this.ijsRateList = ijsRateList;
    }

    public List<IjsRateVO> getIjsRateList() {
        return ijsRateList;
    }

	public String getPaymentFSC() {
		return paymentFSC;
	}

	public void setPaymentFSC(String paymentFSC) {
		this.paymentFSC = paymentFSC;
	}

	public IjsCostRateSetupVO getCostRateSetup() {
		return costRateSetup;
	}

	public void setCostRateSetup(IjsCostRateSetupVO costRateSetup) {
		this.costRateSetup = costRateSetup;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public boolean isDgSetUp() {
		return dgSetUp;
	}

	public void setDgSetUp(boolean dgSetUp) {
		this.dgSetUp = dgSetUp;
	}

	public boolean isImdgSetUp() {
		return imdgSetUp;
	}

	public void setImdgSetUp(boolean imdgSetUp) {
		this.imdgSetUp = imdgSetUp;
	}
    
}
