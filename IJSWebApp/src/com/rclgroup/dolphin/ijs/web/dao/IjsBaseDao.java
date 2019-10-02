/*-----------------------------------------------------------------------------------------------------------
IjsBaseDao.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            common functionality to find the dao 
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.dao;

import com.rclgroup.dolphin.ijs.web.common.IjsStandardSQLErrorCodesTranslator;
import com.rclgroup.dolphin.ijs.web.ui.IjsUserUIM;

import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public abstract class IjsBaseDao extends NamedParameterJdbcDaoSupport{
   
   protected void initDao() throws Exception {
       super.initDao();
       IjsStandardSQLErrorCodesTranslator translator = new IjsStandardSQLErrorCodesTranslator(); 
       translator.setDataSource(getDataSource()); 
       JdbcTemplate jdbcTemplate = getJdbcTemplate();
       jdbcTemplate.setExceptionTranslator(translator);
       setJdbcTemplate(jdbcTemplate);
   }
}
