/*-----------------------------------------------------------------------------------------------------------
IjsContractLookupDao.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            lookup  dao class for Terminal, Depot,Haulage lookup's
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.dao;

import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;

import java.util.List;

public interface IjsContractLookupDao {

    List<?> getLookupList(String lookupName, String userInfo , IjsLookupParamVO ijsLookupParamVO)  throws IJSException ;

    public int getLookUpCount(String lookupName, String userInfo, 
                              IjsLookupParamVO ijsLookupParamVO);
}
