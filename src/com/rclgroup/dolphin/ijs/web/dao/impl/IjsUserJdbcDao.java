 /*-----------------------------------------------------------------------------------------------------------
IjsUserJdbcDao.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            find user details
-----------------------------------------------------------------------------------------------------------*/
 package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsMessageCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractRateDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsUserDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractDownloadDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractSearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsUserDTO;

import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsContractRateSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractRateUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractHistory;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsExcelUploadTemplateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsVendorDetails;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;
import com.rclgroup.dolphin.ijs.web.util.RutString;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class IjsUserJdbcDao extends IjsBaseDao implements IjsUserDao{
    //##01 BEGIN
     private GetIjsUserStoredProcedure getIjsUserStoredProcedure;//##01
    

     
     private IjsContractRateDao contractRateDao;
     //##04 END
     public void initDao() throws Exception {
         //##01 BEGIN
         super.initDao();
         getIjsUserStoredProcedure = new GetIjsUserStoredProcedure(getJdbcTemplate() , new IjsUserSearchRowMapper());
         //##01 END
         
             
     }

    public IjsUserDTO getUserInfo(String user) {
        return getIjsUserStoredProcedure.getUserInfo(user);
    }

    //##01 BEGIN
     
      protected class GetIjsUserStoredProcedure extends StoredProcedure {
          /** Stored Procedure name */
          private static final String SQL_PCR_IJS_CNTR_COMMON = 
              "PCR_IJS_CNTR_COMMON.PRR_IJS_GET_USR_INFO";
          GetIjsUserStoredProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
              super(jdbcTemplate, SQL_PCR_IJS_CNTR_COMMON);
              declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
              declareParameter(new SqlOutParameter("p_io_v_err_cd", Types.VARCHAR, rowMapper));
              declareParameter(new SqlOutParameter("p_o_v_user_info",OracleTypes.CURSOR, rowMapper));
              declareParameter(new SqlOutParameter("p_o_v_authorize", Types.VARCHAR, rowMapper));
              //declareParameter(new SqlOutParameter("p_o_v_fsc_curr", Types.VARCHAR, rowMapper));
              declareParameter(new SqlOutParameter("p_o_v_usr_type", Types.VARCHAR, rowMapper));
              compile();
              
          }
          public IjsUserDTO getUserInfo(String userId) {
              Map outMap = new HashMap();
              Map inParameters = new HashMap();
              IjsUserDTO userDto=null;
              List<IjsUserDTO> userDtos=null;
              String authorize=null;
              
              String usr_type=null;
              inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userId));
              outMap = execute(inParameters);
              userDtos=(List<IjsUserDTO>)outMap.get("p_o_v_user_info");
              authorize=(String)outMap.get("p_o_v_authorize");
              
              usr_type=(String)outMap.get("p_o_v_usr_type");
              if(userDtos!=null){
                  userDto= userDtos.get(0);
                  userDto.setUserType(IjsHelper.getAuthorizationType(authorize));
                  
                  userDto.setUserAuthType(usr_type);
              }
             
              return userDto;
          }
      }
     private class IjsUserSearchRowMapper  implements RowMapper{

          public Object mapRow(ResultSet rs, int row) {
              IjsUserDTO user = new IjsUserDTO();
              try {
                  user.setPrsnLogId(RutString.trim(rs.getString("PRSN_LOG_ID")));
                  user.setPersonCd(RutString.trim(rs.getString("PERSON_CD")));
                  user.setDescr(RutString.trim(rs.getString("DESCR")));
                  
                  user.setFscCode(RutString.trim(rs.getString("FSC_CODE")));
                  user.setFscName(RutString.trim(rs.getString("FSC_NAME")));
                  user.setFscLvl1(RutString.trim(rs.getString("FSC_LVL1")));
                  user.setFscLvl2(RutString.trim(rs.getString("FSC_LVL2")));
                  user.setFscLvl3(RutString.trim(rs.getString("FSC_LVL3")));
                  user.setDeptCode(RutString.trim(rs.getString("DEPT_CODE")));
                  user.setTitleCode(RutString.trim(rs.getString("TITLE_CODE")));
                  
                  user.setIsGroupAuth(RutString.trim(rs.getString("IS_GROUP_AUTH")));
                  user.setEmailId1(RutString.trim(rs.getString("EMAIL_ID1")));
                  user.setEmailId2(RutString.trim(rs.getString("EMAIL_ID2")));
                  
                  user.setFscCurr(RutString.trim(rs.getString("FSC_CURR")));
                     
              } catch (SQLException e) {
                  // TODO
                  e.printStackTrace();
              }
              return user;
          }
      }
     
             //##01 END
 }