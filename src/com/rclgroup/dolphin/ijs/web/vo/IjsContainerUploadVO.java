package com.rclgroup.dolphin.ijs.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;

public class IjsContainerUploadVO {
    private String resultMessage;
    private String fileName;
    private boolean isFailed;
    private String action;
    private String folderPath;
    private String addhocType;
    private String contractId;
    private  List<IjsBaseMessageVO<String>> msgVOs;
    private IjsSearchResult<IjsEquipmetLookUpVO> searchResult;
    private String errorCode;
    private List<String> contractsID;

    
    
    
    
    public boolean isFailed() {
		return isFailed;
	}

	public void setFailed(boolean isFailed) {
		this.isFailed = isFailed;
	}

	public List<String> getContractsID() {
		return contractsID;
	}

	public void setContractsID(List<String> contractsID) {
		this.contractsID = contractsID;
	}

	public IjsContainerUploadVO() {
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setIsFailed(boolean isFailed) {
        this.isFailed = isFailed;
    }

    public boolean isIsFailed() {
        return isFailed;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setAddhocType(String addhocType) {
        this.addhocType = addhocType;
    }

    public String getAddhocType() {
        return addhocType;
    }

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public List<IjsBaseMessageVO<String>> getMsgVOs() {
		return msgVOs;
	}

	public void setMsgVOs(List<IjsBaseMessageVO<String>> msgVOs) {
		this.msgVOs = msgVOs;
	}

	public IjsSearchResult<IjsEquipmetLookUpVO> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(IjsSearchResult<IjsEquipmetLookUpVO> searchResult) {
		this.searchResult = searchResult;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
    
}
