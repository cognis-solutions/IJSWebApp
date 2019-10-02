 /*-----------------------------------------------------------------------------------------------------------
IjsDAOFactory.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            singleton returns dao object
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.factory;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;

import javax.servlet.ServletContext;


public class IjsDAOFactory {
   
    private static IjsDAOFactory INSTANCE =new IjsDAOFactory();
    private IjsDAOFactory() {
    }
    
    public static IjsDAOFactory getInstance(){
        return INSTANCE;
    }
    
    public IjsBaseDao getDao(String daoName,ServletContext servletContext){
        Object object = IjsHelper.getApplicationContext(servletContext).getBean(daoName);
        return (IjsBaseDao)object;
    }
   
}
