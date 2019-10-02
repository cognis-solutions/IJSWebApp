/*-----------------------------------------------------------------------------------------------------------
IjsContractLookupJdbcDao.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            lookup  dao class for Terminal, Depot,Haulage lookup's
02 07/09/17  NIIT       IJS            lookup  dao class for Vendor lookup
03 05/10/17  NIIT       IJS            lookup  procedures for  classes country
04 02/11/17  NIIT       IJS            lookup  procedures for  service vessel voyage and direction
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractLookupDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.vo.IjsCntrCurrencyLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractCountryLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractDepotLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractHaulageLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractPointLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractPortLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractServiceLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractTerminalLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVendorLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVesselLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsCustomerLookupVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;

import com.rclgroup.dolphin.ijs.web.vo.jo.IjsJOSrvVeslVoyaDirLookUpVO;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;

import java.math.BigDecimal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class IjsContractLookupJdbcDao extends IjsBaseDao implements IjsContractLookupDao {
    //##01 BEGIN
    private IjsContractTerminalLookUpProcedure ijsContractTerminalLookUpProcedure;
    private IjsContractDepotLookUpProcedure ijsContractDepotLookUpProcedure;
    private IjsContractHaulagePointLookUpProcedure ijsContractHaulagePointLookUpProcedure;
    private IjsContractPointLookUpProcedure ijsContractPointLookUpProcedure;
    private IjsContractPortLookUpProcedure ijsContractPortLookUpProcedure;
    
    //##01 END
    //##02 BEGIN
    private IjsContractCountryLookUpProcedure ijsContractCountryLookUpProcedure;
    private IjsContractVendorLookUpProcedure ijsContractVendorLookUpProcedure;
    //##02 END
    //##03 BEGIN
    private IjsCntrServiceLookUpProcedure ijsCntrServiceLookUpProcedure;
    private IjsCntrCurrencyLookUpProcedure ijsCntrCurrencyLookUpProcedure;
    //##04 BEGIN
    private IjsJOBkgBLSrvVeslLookupProce ijsJOBkgBLSrvVeslLookupProce;
    private IjsJOBkgBLSrvVeslLookupCntProce ijsJOBkgBLSrvVeslLookupProceCnt;
    private IjsCustomerUpProcedure ijsCustomerUpProcedure;
    private IjsCntrVesselLookUpProcedure ijsCntrVesselLookUpProcedure;
    
    
    
    
    public void initDao() throws Exception {
        //##01 BEGIN
        super.initDao();
        ijsContractTerminalLookUpProcedure = new IjsContractTerminalLookUpProcedure(getJdbcTemplate()
            , new IjsContractTerminalLookUpRowMapper());
        ijsContractDepotLookUpProcedure = new IjsContractDepotLookUpProcedure(getJdbcTemplate()
            , new IjsContractDepotLookUpRowMapper());
        ijsContractHaulagePointLookUpProcedure = new IjsContractHaulagePointLookUpProcedure(getJdbcTemplate()
            , new IjsContractHaulagePointLookUpRowMapper());
        ijsContractPointLookUpProcedure = new IjsContractPointLookUpProcedure(getJdbcTemplate()
            , new IjsContractPointLookUpRowMapper());
        ijsContractPortLookUpProcedure = new  IjsContractPortLookUpProcedure(getJdbcTemplate()
        , new IjsContractPortLookUpRowMapper());
        //##01 END
        //##02 BEGIN
        ijsContractCountryLookUpProcedure = new IjsContractCountryLookUpProcedure(getJdbcTemplate()
            , new IjsContractCountryLookUpRowMapper());
        ijsContractVendorLookUpProcedure = new IjsContractVendorLookUpProcedure(getJdbcTemplate()
            , new IjsContractVendorLookUpRowMapper());
        //##02 END
        //##03 BEGIN
         ijsCntrServiceLookUpProcedure = new IjsCntrServiceLookUpProcedure(getJdbcTemplate()
            , new IjsCntrServiceLookUpRowMapper());
        ijsCntrCurrencyLookUpProcedure = new IjsCntrCurrencyLookUpProcedure(getJdbcTemplate()
            , new IjsCntrCurrencyLookUpRowMapper());
        //##04 BEGIN
         ijsJOBkgBLSrvVeslLookupProce = new IjsJOBkgBLSrvVeslLookupProce(getJdbcTemplate()
            , new IjsJOBkgBLSrvVeslLookupRowMapper());
        ijsJOBkgBLSrvVeslLookupProceCnt = new IjsJOBkgBLSrvVeslLookupCntProce(getJdbcTemplate());
        
        //##005
        ijsCustomerUpProcedure = new IjsCustomerUpProcedure(getJdbcTemplate()
           , new IjsCustomerLookUpRowMapper());
        
        ijsCntrVesselLookUpProcedure = new IjsCntrVesselLookUpProcedure(getJdbcTemplate()
           , new IjsCntrVesselLookUpRowMapper());
           
           
        
    }
    public IjsContractLookupJdbcDao() {
    }
    //##01 BEGIN
    /**
     * getLookupList method for getting lookup results
     * @param lookupName
     * @param userInfo
     * @param ijsLookupParamVO
     * @return
     * @throws IJSException
     */
    public List<?> getLookupList(String lookupName,String userInfo,
                                 IjsLookupParamVO ijsLookupParamVO) throws IJSException {
        if (IjsActionMethod.SEARCH_TERMINAL.getAction().equals(lookupName)) {
           List<IjsContractTerminalLookUpVO> list = ijsContractTerminalLookUpProcedure.getTerminalList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new  IJSException(errorCode);
            }
            return list;
        }
        else if (IjsActionMethod.SEARCH_DEPOT.getAction().equals(lookupName)) {
           List<IjsContractDepotLookUpVO> list = ijsContractDepotLookUpProcedure.getDepotList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new  IJSException(errorCode);
            }
            return list;
        }
        else if (IjsActionMethod.SEARCH_HAULAGE.getAction().equals(lookupName)) {
            List<IjsContractHaulageLookUpVO> list = ijsContractHaulagePointLookUpProcedure.getHaulagePointList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
             String errorCode = null;
             if (list == null || list.isEmpty()) {
                 errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
             }
             if (errorCode != null) {
                 throw new  IJSException(errorCode);
             }
             return list;
        }
        else if (IjsActionMethod.SEARCH_DOOR.getAction().equals(lookupName)) {
 
            List<IjsContractPointLookUpVO> list = ijsContractPointLookUpProcedure.getDoorPointList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
             String errorCode = null;
             if (list == null || list.isEmpty()) {
                 errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
             }
             if (errorCode != null) {
                 throw new  IJSException(errorCode);
             }
             return list;
        }
        else if (IjsActionMethod.SEARCH_PORT.getAction().equals(lookupName)) {
            List<IjsContractPortLookUpVO> results = ijsContractPortLookUpProcedure.getPortLookUpList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
            String errorCode = null;
            if (results == null || results.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new  IJSException(errorCode);
            }
            return results;
        }
        //##01 END
        //##02 BEGIN
        else if (IjsActionMethod.SEARCH_COUNTRY.getAction().equals(lookupName)) {
            List<IjsContractCountryLookUpVO> list = ijsContractCountryLookUpProcedure.getCountryList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
             String errorCode = null;
             if (list == null || list.isEmpty()) {
                 errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
             }
             if (errorCode != null) {
                 throw new  IJSException(errorCode);
             }
             return list;
        }
        else if (IjsActionMethod.SEARCH_VENDOR.getAction().equals(lookupName)) {
            List<IjsContractHaulageLookUpVO> list = ijsContractVendorLookUpProcedure.getVendorList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new  IJSException(errorCode);
            }
            return list;
        }
        //##02 END
         //##03 BEGIN
        else if (IjsActionMethod.SEARCH_SERVICE.getAction().equals(lookupName)) {
            List<IjsContractServiceLookUpVO> list = ijsCntrServiceLookUpProcedure.getServiceList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new  IJSException(errorCode);
            }
            return list;
        }
        else if (IjsActionMethod.SEARCH_CURRENCY.getAction().equals(lookupName)) {
            List<IjsCntrCurrencyLookUpVO> list = ijsCntrCurrencyLookUpProcedure.getCurrencyList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new  IJSException(errorCode);
            }
            return list;
        }
        //##03 END
        //##04 BEGIN
        else if (IjsActionMethod.SEARCH_JOB_ORDER_SERVICE.getAction().equals(lookupName)) {
            List<IjsJOSrvVeslVoyaDirLookUpVO> results = ijsJOBkgBLSrvVeslLookupProce.getLookupResults(ijsLookupParamVO, userInfo);
            String errorCode = null;
            if (results == null || results.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new  IJSException(errorCode);
            }
            return results;
        } 
        else if (IjsActionMethod.CUSTOMER_LOOKUP_SERVICE.getAction().equals(lookupName)) {
            List<IjsCustomerLookupVO> results = ijsCustomerUpProcedure.getCustLookUpList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
            String errorCode = null;
            if (results == null || results.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new  IJSException(errorCode);
            }
            return results;
        }
        else if (IjsActionMethod.SEARCH_VESSEL.getAction().equals(lookupName)) {
            List<IjsContractVesselLookUpVO> list = ijsCntrVesselLookUpProcedure.getVesselList(ijsLookupParamVO.getFindIn()
                , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(), userInfo);
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new  IJSException(errorCode);
            }
            return list;
        }
        
        return null;
       
    }

    public int getLookUpCount(String lookupName, String userInfo, 
                              IjsLookupParamVO ijsLookupParamVO) {
        //ijsJOBkgBLSrvVeslLookupProceCnt.getLookupResultsCount(ijsLookupParamVO, userInfo);
        if( IjsActionMethod.SEARCH_JOB_ORDER_SERVICE.getAction().equals(lookupName)){
            return ijsJOBkgBLSrvVeslLookupProceCnt.getLookupResultsCount(ijsLookupParamVO, userInfo);
        }
        else if(IjsActionMethod.CUSTOMER_LOOKUP_SERVICE.getAction().equals(lookupName)){
            //TODO
            return ijsJOBkgBLSrvVeslLookupProceCnt.getLookupResultsCount(ijsLookupParamVO, userInfo);
             
        }
        
        return 0;
        
        
    }
    //##01 BEGIN
    protected class IjsContractTerminalLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_TERMINAL_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_CNTR_TERMINAL";
        IjsContractTerminalLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_TERMINAL_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_terminal_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }
        private List<IjsContractTerminalLookUpVO> getTerminalList(String findIn, 
                                                               String findFor,
                                                               String wildCard,
                                                               String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in",RutDatabase.stringToDb(findIn).toUpperCase());
            inParameters.put("p_i_v_find_for",RutDatabase.stringToDb(findFor).toUpperCase());
            inParameters.put("p_i_v_wild_card",RutDatabase.stringToDb(wildCard).toUpperCase());
            inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
            
            outMap = execute(inParameters);
            
            return (List<IjsContractTerminalLookUpVO>) outMap.get("p_o_v_ijs_terminal_list");                                              
        }
    }
    private class IjsContractTerminalLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int i) {
            IjsContractTerminalLookUpVO terminalModel = new IjsContractTerminalLookUpVO();
            try {
                terminalModel.setFsc(resultSet.getString("TERMINAL_FSC"));
                terminalModel.setPort(resultSet.getString("PORT"));
                terminalModel.setStatus(resultSet.getString("STATUS"));
                terminalModel.setTerminal(resultSet.getString("TERMINAL"));
                terminalModel.setTerminalName(resultSet.getString("TERMINAL_NAME"));
                terminalModel.setCountryName(resultSet.getString("COUNTRY_NAME"));
                terminalModel.setCurrencyCode(resultSet.getString("CURRENCY"));
                
            } catch (SQLException e) {
                // TO-DO
                e.printStackTrace();
            }
            return terminalModel;
        }
    }
    protected class IjsContractDepotLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_DEPOT_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_CNTR_DEPOT";
        IjsContractDepotLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_DEPOT_LOOK_UP);
            
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_for", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_depot_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }

        private List<IjsContractDepotLookUpVO> getDepotList(String findIn, 
                                                               String findFor, 
                                                               String wildCard,
                                                               String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in",RutDatabase.stringToDb(findIn).toUpperCase());
            inParameters.put("p_i_v_for",RutDatabase.stringToDb(findFor).toUpperCase());
            inParameters.put("p_i_v_wild_card",RutDatabase.stringToDb(wildCard).toUpperCase());
            inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
            
            
            outMap = execute(inParameters);
            return (List<IjsContractDepotLookUpVO>)outMap.get("p_o_v_ijs_depot_list");
        }
    }
    private class IjsContractDepotLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsContractDepotLookUpVO depotModel = new IjsContractDepotLookUpVO();

            try {
                depotModel.setDepot(resultSet.getString("DEPOT"));
                depotModel.setDepotName(resultSet.getString("DEPOT_NAME"));
                depotModel.setDepotPort(resultSet.getString("DEPOT_PORT"));
                depotModel.setFsc(resultSet.getString("DEPOT_FSC"));
                depotModel.setPointCode(resultSet.getString("POINT_CODE"));
                depotModel.setStatus(resultSet.getString("STATUS"));
                depotModel.setCountryName(resultSet.getString("COUNTRY_NAME"));
                depotModel.setCurrencyCode(resultSet.getString("CURRENCY"));
            } catch (SQLException e) {
                // TO-DO
                e.printStackTrace();
            }
            return depotModel;
        }
    }
    
    
    protected class IjsContractHaulagePointLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_HAULAGE_POINT_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_CNTR_HULAGE_POINT";
        IjsContractHaulagePointLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_HAULAGE_POINT_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_haulage_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }

        private List<IjsContractHaulageLookUpVO> getHaulagePointList(String findIn, 
                                                                     String findFor,
                                                                     String wildCard,
                                                                     String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in",RutDatabase.stringToDb(findIn).toUpperCase());
            inParameters.put("p_i_v_find_for",RutDatabase.stringToDb(findFor).toUpperCase());
            inParameters.put("p_i_v_wild_card",RutDatabase.stringToDb(wildCard).toUpperCase());
            inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            
            return (List<IjsContractHaulageLookUpVO>) outMap.get("p_o_v_ijs_haulage_list");
        }
    }
    private class IjsContractHaulagePointLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsContractHaulageLookUpVO haulagePointModel = new IjsContractHaulageLookUpVO();

            try {
                haulagePointModel.setHaulageLocationCode(resultSet.getString("H_LOCATION_CODE"));
                haulagePointModel.setHulageLocationName(resultSet.getString("H_LOCATION_NAME"));
                haulagePointModel.setInlandPoint(resultSet.getString("H_INLAND_POINT"));
                haulagePointModel.setStatus(resultSet.getString("H_RECORD_STATUS"));
                haulagePointModel.setCountryName(resultSet.getString("COUNTRY_NAME"));
                haulagePointModel.setFsc(resultSet.getString("FSC"));
                haulagePointModel.setCurrencyCode(resultSet.getString("CURRENCY"));
            } catch (SQLException e) {
                // TO-DO
                 e.printStackTrace();
            }
            return haulagePointModel;
        }
    }
    protected class IjsContractPointLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_POINT_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_CNTR_POINT";
        IjsContractPointLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_POINT_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_point_mapping_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }

        private List<IjsContractPointLookUpVO> getDoorPointList(String findIn, 
                                                               String findFor,
                                                               String wildCard,
                                                               String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in",RutDatabase.stringToDb(findIn).toUpperCase());
            inParameters.put("p_i_v_find_for",RutDatabase.stringToDb(findFor).toUpperCase());
            inParameters.put("p_i_v_wild_card",RutDatabase.stringToDb(wildCard).toUpperCase());
            inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            
            return (List<IjsContractPointLookUpVO>) outMap.get("p_o_v_ijs_point_mapping_list");                                              
        }
 
    }
    private class IjsContractPointLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsContractPointLookUpVO pointLookUpModel = new IjsContractPointLookUpVO();
            try {
                pointLookUpModel.setPoingName(resultSet.getString("POINT_NAME"));
                pointLookUpModel.setPointCode(resultSet.getString("POINT_CODE"));
                pointLookUpModel.setCountryCode(resultSet.getString("COUNTRY_CODE"));
                pointLookUpModel.setStateCode(resultSet.getString("STATE_CODE"));
                pointLookUpModel.setStatus(resultSet.getString("ZONE_CODE"));
                pointLookUpModel.setZoneCode(resultSet.getString("STATUS"));
                pointLookUpModel.setCountryName(resultSet.getString("COUNTRY_NAME"));
                pointLookUpModel.setFsc(resultSet.getString("FSC"));
                pointLookUpModel.setCurrencyCode(resultSet.getString("CURRENCY"));
                
            } catch (SQLException e) {
                //TO-DO                
                e.printStackTrace();
            }
            return pointLookUpModel;
        }
    }
    protected class IjsContractPortLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_PORT_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_CNTR_PORT";
        IjsContractPortLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_PORT_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_port_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }

        private List<IjsContractPortLookUpVO> getPortLookUpList(String findIn, 
                                                               String findFor,
                                                               String wildCard,
                                                               String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in",RutDatabase.stringToDb(findIn).toUpperCase());
            inParameters.put("p_i_v_find_for",RutDatabase.stringToDb(findFor).toUpperCase());
            inParameters.put("p_i_v_wild_card",RutDatabase.stringToDb(wildCard).toUpperCase());
            inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            
            return (List<IjsContractPortLookUpVO>) outMap.get("p_o_v_ijs_port_list");                                              
        }
    }
    private class IjsContractPortLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsContractPortLookUpVO portLookUpModel = new IjsContractPortLookUpVO();
            try {
                portLookUpModel.setPortCode(resultSet.getString("PORT_CODE"));
                portLookUpModel.setPortName(resultSet.getString("PORT_NAME"));
                portLookUpModel.setCountry(resultSet.getString("COUNTRY_CODE"));
                portLookUpModel.setState(resultSet.getString("STATE_CODE"));
                portLookUpModel.setStatus(resultSet.getString("PORT_STATUS"));
                portLookUpModel.setZone(resultSet.getString("ZONE_CODE"));
                portLookUpModel.setType(resultSet.getString("PORT_TYPE"));
                
            } catch (SQLException e) {
                //TO-DO                
                e.printStackTrace();
            }
            return portLookUpModel;
        }
    }
    protected class IjsContractCountryLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_POINT_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_CNTR_COUNTRY";
        IjsContractCountryLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_POINT_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_country_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }

        private List<IjsContractCountryLookUpVO> getCountryList(String findIn, 
                                                               String findFor,
                                                               String wildCard,
                                                               String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in",RutDatabase.stringToDb(findIn).toUpperCase());
            inParameters.put("p_i_v_find_for",RutDatabase.stringToDb(findFor).toUpperCase());
            inParameters.put("p_i_v_wild_card",RutDatabase.stringToDb(wildCard).toUpperCase());
            inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            
            return (List<IjsContractCountryLookUpVO>) outMap.get("p_o_v_ijs_country_list");                                              
        }
    
    }
    private class IjsContractCountryLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsContractCountryLookUpVO countryModel = new IjsContractCountryLookUpVO();
            try {
                countryModel.setCountryCode(resultSet.getString("COUNTRY_CODE"));
                countryModel.setCountryName(resultSet.getString("COUNTRY_NAME"));
                countryModel.setStatus(resultSet.getString("COUNTRY_STATUS"));
                
            } catch (SQLException e) {
                //TO-DO                
                e.printStackTrace();
            }
            return countryModel;
        }
    }
    //##01 END
    //##02 BEGIN
    protected class IjsContractVendorLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_VENDOR_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_CNTR_VENDOR";
        IjsContractVendorLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_VENDOR_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_vendor_mapping_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }

        private List<IjsContractHaulageLookUpVO> getVendorList(String findIn, 
                                                                     String findFor,
                                                                     String wildCard,
                                                                     String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in",RutDatabase.stringToDb(findIn).toUpperCase());
            if("VCVDTY".equalsIgnoreCase(findIn)){
                inParameters.put("p_i_v_find_for",IjsHelper.getVendorTypeCd(findFor.trim()));
            }else{
                inParameters.put("p_i_v_find_for",RutDatabase.stringToDb(findFor).toUpperCase());
            }
            
            inParameters.put("p_i_v_wild_card",RutDatabase.stringToDb(wildCard).toUpperCase());
            inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            
            return (List<IjsContractHaulageLookUpVO>) outMap.get("p_o_v_ijs_vendor_mapping_list");
        }
    }

    private class IjsContractVendorLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsContractVendorLookUpVO vendorModel = new IjsContractVendorLookUpVO();
            try {
                vendorModel.setCity(resultSet.getString("VENDOR_CITY"));
                vendorModel.setCountry(resultSet.getString("VENDOR_COUNTRY"));
                vendorModel.setState(resultSet.getString("VENDOR_STATE"));
                vendorModel.setVendorName(resultSet.getString("VENDOR_NAME"));
                vendorModel.setVendorType(IjsHelper.getVendorValue( resultSet.getString("VENDOR_TYPE")));
                vendorModel.setVenodrCode(resultSet.getString("VENDOR_CODE"));
                vendorModel.setZipcode(resultSet.getString("VENDOR_ZIPCODE"));
                
            } catch (SQLException e) {
                //TO-DO                
                e.printStackTrace();
            }
            return vendorModel;
        }
    }
    //##02 END
    
    //##03 BEGIN
    protected class IjsCntrServiceLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_SERVICE_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_CNTR_SERVICE";
        IjsCntrServiceLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_SERVICE_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_service_mapping_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
            }

            private List<IjsContractServiceLookUpVO> getServiceList(String findIn,
                                                                     String findFor,
                                                                     String wildCard,
                                                                     String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in",RutDatabase.stringToDb(findIn).toUpperCase());
            inParameters.put("p_i_v_find_for",RutDatabase.stringToDb(findFor).toUpperCase());
            inParameters.put("p_i_v_wild_card",RutDatabase.stringToDb(wildCard).toUpperCase());
            inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            
            return (List<IjsContractServiceLookUpVO>) outMap.get("p_o_v_ijs_service_mapping_list");
            }
    }
    private class IjsCntrServiceLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsContractServiceLookUpVO serviceLookUp = new IjsContractServiceLookUpVO();
            try {
                serviceLookUp.setDescription(resultSet.getString("DESCRIPTON"));
                serviceLookUp.setRemarks(resultSet.getString("RMARKS"));
                serviceLookUp.setServiceCode(resultSet.getString("SERVICE_CODE"));
                serviceLookUp.setStatus(resultSet.getString("STATUS"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return serviceLookUp;
        }
    }
    
    protected class IjsCntrCurrencyLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_CURRENCY_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_CNTR_CURRENCY";
        IjsCntrCurrencyLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_CURRENCY_LOOK_UP);
                declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
                declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
                declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
                declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
                declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
                declareParameter(new SqlOutParameter("p_o_v_ijs_currency_list", OracleTypes.CURSOR, rowMapper));
            compile();
            }

            private List<IjsCntrCurrencyLookUpVO> getCurrencyList(String getFindIn, String getFindFor, String getWildCard, String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
                inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
                inParameters.put("p_i_v_find_in" , RutDatabase.stringToDb(getFindIn).toUpperCase());
                inParameters.put("p_i_v_find_for" , RutDatabase.stringToDb(getFindFor).toUpperCase());
                inParameters.put("p_i_v_wild_card" , RutDatabase.stringToDb(getWildCard).toUpperCase());
            outMap = execute(inParameters);
            return (List<IjsCntrCurrencyLookUpVO>) outMap.get("p_o_v_ijs_currency_list");
            }
    }
        
        
    private class IjsCntrCurrencyLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsCntrCurrencyLookUpVO currencyLookUp = new IjsCntrCurrencyLookUpVO();
            try {
                currencyLookUp.setCurrencyCode(resultSet.getString("currency_code"));
                currencyLookUp.setCurrencyName(resultSet.getString("currency_name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return currencyLookUp;
        }
    }
    //##04 BEGIN
    protected class IjsJOBkgBLSrvVeslLookupProce extends StoredProcedure {
        private static final String SQL_RLTD_IJS_JO_SERV_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_JO_SRV_VES_VOY_DATA";
        IjsJOBkgBLSrvVeslLookupProce(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_JO_SERV_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_sort_by", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_order_by", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_row_start", Types.NUMERIC, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_row_end", Types.NUMERIC, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_is_for_count", Types.VARCHAR, rowMapper));
            //declareParameter(new SqlOutParameter("p_o_v_total_count", Types.NUMERIC, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_jo_srv_vesl_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }
        private List<IjsJOSrvVeslVoyaDirLookUpVO> getLookupResults(IjsLookupParamVO ijsLookupParamVO, String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in" , RutDatabase.stringToDb(ijsLookupParamVO.getFindIn()).toUpperCase());
            inParameters.put("p_i_v_find_for" , RutDatabase.stringToDb(ijsLookupParamVO.getFindFor()).toUpperCase());
            inParameters.put("p_i_v_wild_card" , RutDatabase.stringToDb(ijsLookupParamVO.getWildCard()).toUpperCase());
            inParameters.put("p_i_v_sort_by" , RutDatabase.stringToDb(ijsLookupParamVO.getSortBy()).toUpperCase());
            inParameters.put("p_i_v_order_by" , RutDatabase.stringToDb(ijsLookupParamVO.getOrderBy()).toUpperCase());
            inParameters.put("p_i_v_row_start" , ijsLookupParamVO.getRowStart());
            inParameters.put("p_i_v_row_end" , ijsLookupParamVO.getRowEnd());
            inParameters.put("p_i_v_is_for_count" , "NO");
            inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            return (List<IjsJOSrvVeslVoyaDirLookUpVO>)outMap.get("p_o_v_ijs_jo_srv_vesl_list");
        }
    }
    private class IjsJOBkgBLSrvVeslLookupRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsJOSrvVeslVoyaDirLookUpVO lookUpVO = new IjsJOSrvVeslVoyaDirLookUpVO();

            try {
                lookUpVO.setVessel(resultSet.getString("VESSEL"));
                lookUpVO.setVoyage(resultSet.getString("VOYAGE"));
                lookUpVO.setService(resultSet.getString("SERVICE"));
                lookUpVO.setDirection(resultSet.getString("DIRECTION"));
            } catch (SQLException e) {
                // TO-DO
                e.printStackTrace();
            }
            return lookUpVO;
        }
    }
    
    protected class IjsJOBkgBLSrvVeslLookupCntProce extends StoredProcedure {
        private static final String SQL_RLTD_IJS_JO_SERV_LOOK_UP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_JO_SRV_VESL_VOYA_DIR";
        IjsJOBkgBLSrvVeslLookupCntProce(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_RLTD_IJS_JO_SERV_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_sort_by", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_order_by", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_row_start", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_row_end", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_is_for_count", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_total_count", Types.NUMERIC));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
//            declareParameter(new SqlOutParameter("p_o_v_ijs_jo_srv_vesl_list", 
//                                                 OracleTypes.CURSOR));
            compile();
        }
        
        private int getLookupResultsCount(IjsLookupParamVO ijsLookupParamVO, 
                                           String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in" , RutDatabase.stringToDb(ijsLookupParamVO.getFindIn()).toUpperCase());
            inParameters.put("p_i_v_find_for" , RutDatabase.stringToDb(ijsLookupParamVO.getFindFor()).toUpperCase());
            inParameters.put("p_i_v_wild_card" , RutDatabase.stringToDb(ijsLookupParamVO.getWildCard()).toUpperCase());
            inParameters.put("p_i_v_sort_by" , RutDatabase.stringToDb(ijsLookupParamVO.getSortBy()).toUpperCase());
            inParameters.put("p_i_v_order_by" , RutDatabase.stringToDb(ijsLookupParamVO.getOrderBy()).toUpperCase());
            inParameters.put("p_i_v_row_start" , ijsLookupParamVO.getRowStart());
            inParameters.put("p_i_v_row_end" , ijsLookupParamVO.getRowEnd());
            inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
            inParameters.put("p_i_v_is_for_count" ,  "YES");
            
            outMap = execute(inParameters);
            BigDecimal count = (BigDecimal)outMap.get("p_o_v_total_count");
            System.out.print("COUNT of Records------------------->"+count);
            
            return count.intValue();
        }
    }
    
    
    //##04 END;
    
    //##05 START
     protected class IjsCustomerUpProcedure extends StoredProcedure {
         private static final String SQL_RLTD_IJS_PORT_LOOK_UP =  "PCR_IJS_CNTR_EXM_CUST.PRR_IJS_CNT_GET_EXM_CST";
         IjsCustomerUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
             super(jdbcTemplate, SQL_RLTD_IJS_PORT_LOOK_UP);
             declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
             declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
             declareParameter(new SqlOutParameter("p_o_v_ijs_cust_list", 
                                                  OracleTypes.CURSOR, 
                                                  rowMapper));
             compile();
         }

         private List<IjsCustomerLookupVO> getCustLookUpList(String findIn, 
                                                                String findFor,
                                                                String wildCard,
                                                                String userInfo) {
             Map outMap = new HashMap();
             Map inParameters = new HashMap();
             inParameters.put("p_i_v_find_in",RutDatabase.stringToDb(findIn).toUpperCase());
             inParameters.put("p_i_v_find_for",RutDatabase.stringToDb(findFor).toUpperCase());
             inParameters.put("p_i_v_wild_card",RutDatabase.stringToDb(wildCard).toUpperCase());
             inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
             outMap = execute(inParameters);
             
             return (List<IjsCustomerLookupVO>) outMap.get("p_o_v_ijs_cust_list");                                              
         }
     }
     
     private class IjsCustomerLookUpRowMapper implements RowMapper {

         public Object mapRow(ResultSet resultSet, int row) {
             IjsCustomerLookupVO customerLookUpModel = new IjsCustomerLookupVO();
             try {
                 customerLookUpModel.setCustCode(resultSet.getString("CUST_CODE"));
                 customerLookUpModel.setCustName(resultSet.getString("NAME"));
                 customerLookUpModel.setCustCodeFI(resultSet.getString("FICC"));
                 customerLookUpModel.setCity(resultSet.getString("CITY"));
                 customerLookUpModel.setCountry(resultSet.getString("COUNTRY"));
                 customerLookUpModel.setStatus(resultSet.getString("STATUS"));
                 
             } catch (SQLException e) {
                 //TO-DO                
                 e.printStackTrace();
             }
             return customerLookUpModel;
         }
     }
    //##05 END
     protected class IjsCntrVesselLookUpProcedure extends StoredProcedure {
         private static final String SQL_PRR_IJS_VESSEL_LOOKUP =  "PCR_IJS_CNTR_COMMON.PRR_IJS_VESSEL_LOOKUP";
         IjsCntrVesselLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
             super(jdbcTemplate, SQL_PRR_IJS_VESSEL_LOOKUP);
             declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR, rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
             declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
             declareParameter(new SqlOutParameter("p_o_v_ijs_vessel_mapping_list", 
                                                  OracleTypes.CURSOR, 
                                                  rowMapper));
             compile();
             }

             private List<IjsContractVesselLookUpVO> getVesselList(String findIn,
                                                                      String findFor,
                                                                      String wildCard,
                                                                      String userInfo) {
             Map outMap = new HashMap();
             Map inParameters = new HashMap();
             inParameters.put("p_i_v_find_in",RutDatabase.stringToDb(findIn).toUpperCase());
             inParameters.put("p_i_v_find_for",RutDatabase.stringToDb(findFor).toUpperCase());
             inParameters.put("p_i_v_wild_card",RutDatabase.stringToDb(wildCard).toUpperCase());
             inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
             outMap = execute(inParameters);
             
             return (List<IjsContractVesselLookUpVO>) outMap.get("p_o_v_ijs_vessel_mapping_list");
             }
     }
    private class IjsCntrVesselLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsContractVesselLookUpVO vesselLookUp = new IjsContractVesselLookUpVO();
            try {
                vesselLookUp.setVesselCode(resultSet.getString("VESSEL_CODE"));
                vesselLookUp.setVesselName(resultSet.getString("VESSEL_NAME"));
                vesselLookUp.setOperatorCode(resultSet.getString("OPERATOR_CODE"));
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return vesselLookUp;
        }
    }
    
    
    
    
    
    
}


