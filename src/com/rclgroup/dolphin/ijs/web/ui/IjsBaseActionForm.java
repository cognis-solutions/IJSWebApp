 /*-----------------------------------------------------------------------------------------------------------
IjsBaseActionForm.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            keeps common properties for UI
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.ui;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;


public class IjsBaseActionForm {
    //##01 BEGIN
    private IjsUserInfoVO userInfo;
    private IjsSearchResult<?> searchResult;
    private String action;
    private String errorCode;
    private String errorDesc;
    public IjsBaseActionForm() {
    }
    public void setUserInfo(IjsUserInfoVO userInfo) {
        this.userInfo = userInfo;
    }

    public IjsUserInfoVO getUserInfo() {
        return userInfo;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
    
	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public IjsSearchResult<?> getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(IjsSearchResult<?> searchResult) {
		this.searchResult = searchResult;
	}
	
}
