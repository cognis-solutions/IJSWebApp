/*-----------------------------------------------------------------------------------------------------------
IjsContractUploadVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 20/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 20/10/17  NIIT       IJS            For uploading  
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;
import java.util.Map;

public class IjsContractUploadVO {
    private String resultMessage;
    private String fileName;
    private boolean isFailed;
    private String action;
    private String folderPath;
    private List<String> failedContracts;
    private List<String> updatedContracts;
    private List<String> newContracts;
    private List<String> partialSuccessful;
    private Map<String,String> errorMap;
    private List contractsID;
    
    
    
    
    public List getContractsID() {
		return contractsID;
	}

	public void setContractsID(List contractsID) {
		this.contractsID = contractsID;
	}

	public IjsContractUploadVO() {
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

	public List<String> getFailedContracts() {
		return failedContracts;
	}

	public void setFailedContracts(List<String> failedContracts) {
		this.failedContracts = failedContracts;
	}

	public List<String> getUpdatedContracts() {
		return updatedContracts;
	}

	public void setUpdatedContracts(List<String> updatedContracts) {
		this.updatedContracts = updatedContracts;
	}

	public List<String> getNewContracts() {
		return newContracts;
	}

	public void setNewContracts(List<String> newContracts) {
		this.newContracts = newContracts;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}

	public List<String> getPartialSuccessful() {
		return partialSuccessful;
	}

	public void setPartialSuccessful(List<String> partialSuccessful) {
		this.partialSuccessful = partialSuccessful;
	}
	
    
}
