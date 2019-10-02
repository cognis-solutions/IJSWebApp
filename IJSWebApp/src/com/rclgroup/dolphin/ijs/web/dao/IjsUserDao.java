/*-----------------------------------------------------------------------------------------------------------
IjsUserDao.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            find user details
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.dao;

import com.rclgroup.dolphin.ijs.web.entity.IjsUserDTO;

public interface IjsUserDao{
    public IjsUserDTO getUserInfo(String user);
}
