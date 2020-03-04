 /*-----------------------------------------------------------------------------------------------------------
IjsUserSvc.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            service returns user information
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.service;

import com.rclgroup.dolphin.ijs.web.dao.IjsUserDao;

import com.rclgroup.dolphin.ijs.web.entity.IjsUserDTO;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

public class IjsUserSvc {
    private IjsUserDao userDao=null;
    public IjsUserSvc(IjsUserDao userDao) {
        this.userDao=userDao;
    }
    
    public IjsUserInfoVO findUserInfo(String userName){
        IjsUserDTO userDto=new IjsUserDTO();
        userDto=userDao.getUserInfo(userName);
        return (userDto!=null?transformDtoToVO(userDto):null);
       
    }
    
    private IjsUserInfoVO transformDtoToVO(IjsUserDTO userDto){
        IjsUserInfoVO userInfo=new IjsUserInfoVO();
        userInfo.setDeptCode(userDto.getDeptCode());
        userInfo.setDescr(userDto.getDescr());
        userInfo.setEmailId1(userDto.getEmailId1());
        userInfo.setEmailId2(userDto.getEmailId2());
        userInfo.setFscCode(userDto.getFscCode());
        userInfo.setFscLvl1(userDto.getFscLvl1());
        userInfo.setFscLvl2(userDto.getFscLvl2());
        userInfo.setFscLvl3(userDto.getFscLvl3());
        userInfo.setFscName(userDto.getFscName());
        userInfo.setIsGroupAuth(userDto.getIsGroupAuth());
        userInfo.setPersonCd(userDto.getPersonCd());
        userInfo.setUserId(userDto.getPrsnLogId());
        userInfo.setTitleCode(userDto.getTitleCode());
        userInfo.setUserType(userDto.getUserType());
        
        userInfo.setFscCurr(userDto.getFscCurr());
        userInfo.setUserAuthType(userDto.getUserAuthType());
        
	return userInfo;
    }
}
