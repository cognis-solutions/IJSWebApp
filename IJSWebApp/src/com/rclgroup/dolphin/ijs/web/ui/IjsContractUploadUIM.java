/*-----------------------------------------------------------------------------------------------------------
IjsContractUploadVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 20/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 20/10/17  NIIT       IJS            For uploading contracts 
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.ui;

import com.rclgroup.dolphin.ijs.web.vo.IjsContractUploadVO;

public class IjsContractUploadUIM extends IjsBaseActionForm{
    private IjsContractUploadVO uploadVO;


    public void setUploadVO(IjsContractUploadVO uploadVO) {
        this.uploadVO = uploadVO;
    }

    public IjsContractUploadVO getUploadVO() {
        return uploadVO;
    }
}
