package com.rclgroup.dolphin.ijs.web.service;

import com.rclgroup.dolphin.ijs.web.dao.IjsCommonDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;

public class IjsCommonSvc {
    IjsCommonDao commonDao=null;
    public IjsCommonSvc(IjsCommonDao commonDao) {
    this.commonDao=commonDao;
    }
    
    public String getIjsViewJoUrl(String userId) throws IJSException {
        return commonDao.getViewJOUrl(userId);
    }
}
