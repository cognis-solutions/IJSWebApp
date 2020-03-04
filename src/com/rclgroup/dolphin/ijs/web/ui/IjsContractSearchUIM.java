 /*-----------------------------------------------------------------------------------------------------------
IjsBaseActionForm.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            keeps records for IJS contract search screen
02 05/10/17  NIIT       IJS            added contractsList and contractsResult propterties
03 20/10/17  NIIT       IJS            added upload contractsResult propterties
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.ui;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.vo.IjsBaseMessageVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsCntrCurrencyLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractShipmentTermVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractTransportModeVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractUploadVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class IjsContractSearchUIM extends IjsBaseActionForm {
    //##01 BEGIN
    private IjsSearchParamVO<IjsContractSearchParamVO> contractParam;
    //private IjsSearchResult<?> searchResult;
    private IjsContractVO contract;
    private List<String> contractsList;//##02
    private String contractsResult;//##02
    private String uploadResultMessage;//##03
    String [] filesTobeDownload=new String[3];
    private IjsContractUploadVO uploadVO;
    private List<IjsContractShipmentTermVO> termList;
    private List<IjsContractTransportModeVO> transportModeList;
    private String vendorCode;
    private String paymentFscCode;
    private String rateType;
    private List<IjsCntrCurrencyLookUpVO> fscCurrencyList;
    private String location;
    private String locationType;
    private String fscCode;
    private String fscCurrencyCode;
    private String vendorName;
    private String terminal;
    private String terminalValid;
    private String nextGPPTime;
    private IjsBaseMessageVO msgDupRoute;
    private IjsBaseMessageVO msgSuccessVO;
    private String contractId;
    
    
    public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getNextGPPTime() {
		return nextGPPTime;
	}

	public void setNextGPPTime(String nextGPPTime) {
		this.nextGPPTime = nextGPPTime;
	}

	public IjsContractSearchUIM() {
    }

//    public void setSearchResult(IjsSearchResult<?> searchResult) {
//        this.searchResult = searchResult;
//    }
//
//    public IjsSearchResult<?> getSearchResult() {
//        return searchResult;
//    }

    public void setContract(IjsContractVO contract) {
        this.contract = contract;
    }

    public IjsContractVO getContract() {
        return contract;
    }

    public void setContractParam(IjsSearchParamVO<IjsContractSearchParamVO> contractParam) {
        this.contractParam = contractParam;
    }

    public IjsSearchParamVO<IjsContractSearchParamVO> getContractParam() {
        return contractParam;
    }
    //##01 END
    //##02 BEGIN
    public void setContractsList(List<String> contractsList) {
        this.contractsList = contractsList;
    }

    public List<String> getContractsList() {
        return contractsList;
    }

    public void setContractsResult(String contractsResult) {
        this.contractsResult = contractsResult;
    }

    public String getContractsResult() {
        return contractsResult;
    }
    //##02 END


    public void setUploadVO(IjsContractUploadVO uploadVO) {
        this.uploadVO = uploadVO;
    }

    public IjsContractUploadVO getUploadVO() {
        return uploadVO;
    }

    public void setUploadResultMessage(String uploadResultMessage) {
        this.uploadResultMessage = uploadResultMessage;
    }

    public String getUploadResultMessage() {
        return uploadResultMessage;
    }

    public void setFilesTobeDownload(String[] filesTobeDownload) {
        this.filesTobeDownload = filesTobeDownload;
    }

    public String[] getFilesTobeDownload() {
        return filesTobeDownload;
    }

    public void setTermList(List<IjsContractShipmentTermVO> termList) {
        this.termList = termList;
    }

    public List<IjsContractShipmentTermVO> getTermList() {
        return termList;
    }

    public void setTransportModeList(List<IjsContractTransportModeVO> transportModeList) {
        this.transportModeList = transportModeList;
    }

    public List<IjsContractTransportModeVO> getTransportModeList() {
        return transportModeList;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setPaymentFscCode(String paymentFscCode) {
        this.paymentFscCode = paymentFscCode;
    }

    public String getPaymentFscCode() {
        return paymentFscCode;
    }

    public void setFscCurrencyList(List<IjsCntrCurrencyLookUpVO> fscCurrencyList) {
        this.fscCurrencyList = fscCurrencyList;
    }

    public List<IjsCntrCurrencyLookUpVO> getFscCurrencyList() {
        return fscCurrencyList;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setFscCurrencyCode(String fscCurrencyCode) {
        this.fscCurrencyCode = fscCurrencyCode;
    }

    public String getFscCurrencyCode() {
        return fscCurrencyCode;
    }

    public void setFscCode(String fscCode) {
        this.fscCode = fscCode;
    }

    public String getFscCode() {
        return fscCode;
    }


    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminalValid(String terminalValid) {
        this.terminalValid = terminalValid;
    }

    public String getTerminalValid() {
        return terminalValid;
    }

	public IjsBaseMessageVO getMsgDupRoute() {
		return msgDupRoute;
	}

	public void setMsgDupRoute(IjsBaseMessageVO msgDupRoute) {
		this.msgDupRoute = msgDupRoute;
	}

	public IjsBaseMessageVO getMsgSuccessVO() {
		return msgSuccessVO;
	}

	public void setMsgSuccessVO(IjsBaseMessageVO msgSuccessVO) {
		this.msgSuccessVO = msgSuccessVO;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

    
}
