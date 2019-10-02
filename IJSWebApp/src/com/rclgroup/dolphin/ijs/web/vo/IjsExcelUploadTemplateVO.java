/*-----------------------------------------------------------------------------------------------------------
IjsExcelUploadTemplateVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 20/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 20/10/17  NIIT       IJS            keeps ijs IjsExcelUploadTemplateVO 
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsExcelUploadTemplateVO {
    private IjsContractVO ijsContractVO;
    private List<IjsRateVO> ijsCostRateVOList;
    private List<IjsRateVO> ijsBillingRateVOList;
    private boolean isCostRateUpload;
    private boolean billingRateUpload;
    private IjsContractUploadVO contractUploadVO;
    


    public void setIjsContractVO(IjsContractVO ijsContractVO) {
        this.ijsContractVO = ijsContractVO;
    }

    public IjsContractVO getIjsContractVO() {
        return ijsContractVO;
    }

   

    public void setIsCostRateUpload(boolean isCostRateUpload) {
        this.isCostRateUpload = isCostRateUpload;
    }

    public boolean isIsCostRateUpload() {
        return isCostRateUpload;
    }


    public void setIjsCostRateVOList(List<IjsRateVO> ijsCostRateVOList) {
        this.ijsCostRateVOList = ijsCostRateVOList;
    }

    public List<IjsRateVO> getIjsCostRateVOList() {
        return ijsCostRateVOList;
    }

    public void setIjsBillingRateVOList(List<IjsRateVO> ijsBillingRateVOList) {
        this.ijsBillingRateVOList = ijsBillingRateVOList;
    }

    public List<IjsRateVO> getIjsBillingRateVOList() {
        return ijsBillingRateVOList;
    }

	public IjsContractUploadVO getContractUploadVO() {
		return contractUploadVO;
	}

	public void setContractUploadVO(IjsContractUploadVO contractUploadVO) {
		this.contractUploadVO = contractUploadVO;
	}

	public boolean isBillingRateUpload() {
		return billingRateUpload;
	}

	public void setBillingRateUpload(boolean billingRateUpload) {
		this.billingRateUpload = billingRateUpload;
	}
	
    
}
