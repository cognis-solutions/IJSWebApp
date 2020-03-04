package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsJOLookupDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
//import com.rclgroup.dolphin.ijs.web.vo.IjsContractVendorLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsBkGBlLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContainerLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsDgImdgLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsEquipmetLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOLogLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOFSCLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJORoutingLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupFeildVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamFilterVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsReasonCodeLookUpVO;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;
import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.util.RutString;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;


public class IjsJOLookupJdbcDao extends IjsBaseDao implements IjsJOLookupDao {
    private IjsJOLookupJdbcDao.IjsEquipmentLookUpProcedure ijsEquipmentLookUpProcedure;
    private IjsJOLookupJdbcDao.IjsJOEquipBrowserookupCntProce ijsJOEquipBroserLookupProceCnt;
    private IjsJOLookupJdbcDao.IjsJOContainerLookUpProcedure  ijsJOContainerLookUpProcedure ;
    private IjsJOLookupJdbcDao.IjsJODgImdgLookUpProcedure  ijsJODgImdgLookUpProcedure ;
    
    private IjsJOLookupJdbcDao.IjsJORoutingLookUpProcedure ijsJORoutingLookUpProcedure;
    private IjsJOLookupJdbcDao.IjsJOReasonCodeLookUpProcedure ijsJOReasonCodeLookUpProcedure;
    private IjsJOLookupJdbcDao.IjsJOBkgBLLookUpProcedure ijsJOBkgBlLookUpProcedure;
    private IjsJOLookupJdbcDao.IjsJOLogLookUpProcedure ijsJOLogLookUpProcedure;
    private IjsJOLookupJdbcDao.IjsJOFSCLookUpProcedure ijsJOFSCLookUpProcedure;
    private IjsJOLookupJdbcDao.IjsDeleteBkgBlStoredProcedure ijsDeleteBkgBlStoredProcedure; 
    private IjsDeleteLumpsumStoredProcedure ijsDeleteLumpsumStoredProcedure;
    
    

    public void initDao() throws Exception {
        //##01 BEGIN
        super.initDao();

        ijsEquipmentLookUpProcedure = 
                new IjsJOLookupJdbcDao.IjsEquipmentLookUpProcedure(getJdbcTemplate(), 
                                                                              new IjsJOLookupJdbcDao.IjsEquipmentLookUpRowMapper());
        
        ijsJOEquipBroserLookupProceCnt = new IjsJOLookupJdbcDao.IjsJOEquipBrowserookupCntProce(getJdbcTemplate());
                                                                      
        
                
        ijsJORoutingLookUpProcedure = 
                new IjsJOLookupJdbcDao.IjsJORoutingLookUpProcedure(getJdbcTemplate(), 
                                                                              new IjsJOLookupJdbcDao.IjsJORoutingLookUpRowMapper());
                                                                              
        ijsJOContainerLookUpProcedure =  new IjsJOLookupJdbcDao.IjsJOContainerLookUpProcedure(getJdbcTemplate(), 
                                                                              new IjsJOLookupJdbcDao.IjsJOContainerLookUpRowMapper());      
                                                                              
        ijsJODgImdgLookUpProcedure    =  new IjsJOLookupJdbcDao.IjsJODgImdgLookUpProcedure(getJdbcTemplate(), 
                                                                              new IjsJOLookupJdbcDao.IjsJODgImdgLookUpRowMapper()); 
                                                                              
        ijsJOReasonCodeLookUpProcedure = new IjsJOLookupJdbcDao.IjsJOReasonCodeLookUpProcedure(getJdbcTemplate(), 
                                                                              new IjsJOLookupJdbcDao.IjsJOReasonCodeLookUpRowMapper());   
                                                                              
        ijsJOBkgBlLookUpProcedure =    new IjsJOLookupJdbcDao.IjsJOBkgBLLookUpProcedure(getJdbcTemplate(), 
                                                                              new IjsJOLookupJdbcDao.IjsJOBkgBlLookUpRowMapper());
                                                                              
        ijsJOLogLookUpProcedure =    new IjsJOLookupJdbcDao.IjsJOLogLookUpProcedure(getJdbcTemplate(), 
                                                                              new IjsJOLookupJdbcDao.IjsJOLogLookUpRowMapper());                                                                              
                                                                              
                                                                              
        ijsJOFSCLookUpProcedure = 
                new IjsJOLookupJdbcDao.IjsJOFSCLookUpProcedure(getJdbcTemplate(), 
                                                                              new IjsJOLookupJdbcDao.IjsJOFSCLookUpRowMapper());
                                                                              
        ijsDeleteBkgBlStoredProcedure = 
                new IjsJOLookupJdbcDao.IjsDeleteBkgBlStoredProcedure(getJdbcTemplate(), 
                                                                              new IjsJOLookupJdbcDao.IjsJOBkgBlLookUpRowMapper());
        ijsDeleteLumpsumStoredProcedure=new IjsDeleteLumpsumStoredProcedure(getJdbcTemplate());
    }

    public IjsJOLookupJdbcDao() {
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
    public List<?> getLookupList(String lookupName, String userInfo, 
                                 IjsLookupParamVO ijsLookupParamVO) throws IJSException {
        if (IjsActionMethod.SEARCH_EQUIPMENT.getAction().equals(lookupName)) {
            List<IjsEquipmetLookUpVO> list = ijsEquipmentLookUpProcedure.getEquipmentList(ijsLookupParamVO.getFindForList(), ijsLookupParamVO.getFindIn(),ijsLookupParamVO.getContractId());
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new IJSException(errorCode);
            }
            return list;
        } else         if (IjsActionMethod.SEARCH_ROUTE_LIST.getAction().equals(lookupName)) {
            List<IjsJORoutingLookUpVO> list = 
                ijsJORoutingLookUpProcedure.getRoutingList(ijsLookupParamVO.getFindIn(), 
                                                           ijsLookupParamVO.getFindForLoc(), 
                                                           ijsLookupParamVO.getFindForTerminal(), 
                                                           ijsLookupParamVO.getFindForLocType(), 
                                                           ijsLookupParamVO.getFindForSaleDateOrJobOrdDate(),
                                                           ijsLookupParamVO.getFindForVendorCode(),
                                                          IjsHelper.getTransCode(ijsLookupParamVO.getTransMode()) ,
                                                           ijsLookupParamVO.getJoType(),
                                                           ijsLookupParamVO.getSameVendorInSearch(),
                                                           ijsLookupParamVO.getWildCard(), 
                                                           userInfo);
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new IJSException(errorCode);
            }
            return list;
        }
        
        else if (IjsActionMethod.SEARCH_CONTAINER.getAction().equals(lookupName)) {
            List<IjsContainerLookUpVO> list = 
            ijsJOContainerLookUpProcedure.getContainerList(ijsLookupParamVO.getComponentType(),ijsLookupParamVO.getFindList());
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new IJSException(errorCode);
            }
            return list;
        }
        
        else if (IjsActionMethod.SEARCH_DGIMDG.getAction().equals(lookupName)) {
            List<IjsDgImdgLookUpVO> list = 
            ijsJODgImdgLookUpProcedure.getDgImdgList(ijsLookupParamVO.getFindList());
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new IJSException(errorCode);
            }
            return list;
        }
        
        else if (IjsActionMethod.SEARCH_REASON_CODE.getAction().equals(lookupName)) {
            List<IjsReasonCodeLookUpVO> list = 
            ijsJOReasonCodeLookUpProcedure.getReasonCodeList(ijsLookupParamVO);
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new IJSException(errorCode);
            }
            return list;
        }
        
        else if (IjsActionMethod.SEARCH_BL_BKG_POPUP.getAction().equals(lookupName)) {
            List<IjsBkGBlLookUpVO> list = 
            ijsJOBkgBlLookUpProcedure.getBkgBlList(ijsLookupParamVO.getFindList());
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new IJSException(errorCode);
            }
            return list;
        } else if (IjsActionMethod.SEARCH_FSC_LIST.getAction().equals(lookupName)) {
            List<IjsJOFSCLookUpVO> list = 
                ijsJOFSCLookUpProcedure.getFSCList(ijsLookupParamVO.getFindIn(), 
                                                               ijsLookupParamVO.getFindFor(), 
                                                               ijsLookupParamVO.getWildCard(), 
                                                               userInfo);
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new IJSException(errorCode);
            }
            return list;
        }
        else if (IjsActionMethod.SEARCH_JO_LOG_POPUP.getAction().equals(lookupName)) {
            List<IjsJOLogLookUpVO> list = 
            ijsJOLogLookUpProcedure.getJoLogList(ijsLookupParamVO);
            String errorCode = null;
            if (list == null || list.isEmpty()) {
                errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
            }
            if (errorCode != null) {
                throw new IJSException(errorCode);
            }
            return list;
        }
        //##02 END
        
        return null;

    }

    public int getLookUpCount(String lookupName, String userInfo, 
                              IjsLookupParamVO ijsLookupParamVO) {
        //ijsJOBkgBLSrvVeslLookupProceCnt.getLookupResultsCount(ijsLookupParamVO, userInfo);
        return ijsJOEquipBroserLookupProceCnt.getLookupResultsCount(ijsLookupParamVO, 
                                                                     userInfo);
    }
       
    public String updateBkgBl(IjsLookupParamVO ijsLookupParamVO, String userId, String action) {
        String errorCode =
                    ijsDeleteBkgBlStoredProcedure.updateBkgBl(ijsLookupParamVO,userId,action);                                  
                    
        return errorCode;
    }




    //##02 BEGIN

    protected class IjsEquipmentLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_EQUIPMENT_LOOK_UP = "PCR_IJS_CNTR_COMMON.PRR_IJS_EQUIP_LOOKUP";

        IjsEquipmentLookUpProcedure(JdbcTemplate jdbcTemplate, 
                                         RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_EQUIPMENT_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_contract_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_equip_mapping_list", OracleTypes.CURSOR, rowMapper));
            compile();
        }

            private List<IjsEquipmetLookUpVO> getEquipmentList(List<IjsLookupParamFilterVO> findForList, String findIn,String contractId) {

            String findFor = "";
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in", RutDatabase.stringToDb(findIn).toUpperCase());
            
            for(IjsLookupParamFilterVO ijsLookupParamFilterVO : findForList){
                findFor = findFor + ijsLookupParamFilterVO.toString();
            }
            System.out.println(findFor);
            if (!findFor.equals("")){
            findFor = findFor.substring(0, findFor.lastIndexOf(" "));
            System.out.println(findFor);
            System.out.println(RutDatabase.stringToDb(findFor).toUpperCase());
            }
            
            
            inParameters.put("p_i_v_find_for", RutDatabase.stringToDb(findFor).toUpperCase());
            inParameters.put("p_i_v_contract_id", RutDatabase.stringToDb(contractId).toUpperCase());
            
//            inParameters.put("p_i_v_wild_card", RutDatabase.stringToDb(wildCard).toUpperCase());
//            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);

            return (List<IjsEquipmetLookUpVO>)outMap.get("p_o_v_ijs_equip_mapping_list");
        }
    }

    private class IjsEquipmentLookUpRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsEquipmetLookUpVO vendorModel = 
                new IjsEquipmetLookUpVO();
            try {
                vendorModel.setEqpNum(resultSet.getString("EQUIP_NO"));
                vendorModel.setCatg(resultSet.getString("CATEGOTY"));
                vendorModel.setSize(resultSet.getString("EQ_SIZE"));
                vendorModel.setType(resultSet.getString("EQ_TYPE"));
                vendorModel.setPoint(resultSet.getString("POINT"));
                vendorModel.setActivity(resultSet.getString("ACTIVITY"));
                vendorModel.setActivityDate(resultSet.getString("ACTIVITY_DATE"));
                vendorModel.setActivityTime(resultSet.getString("ACTIVITY_TIME"));
                vendorModel.setTerminal(resultSet.getString("TERMINAL"));
                vendorModel.setService(resultSet.getString("SERVICE"));
                vendorModel.setVessel(resultSet.getString("VESSEL"));
                vendorModel.setVoyage(resultSet.getString("VOYAGE"));
                vendorModel.setDirection(resultSet.getString("DIRECTION"));
                vendorModel.setOrigin(resultSet.getString("ORIGIN"));
                vendorModel.setPol(resultSet.getString("POL"));
                vendorModel.setPot(resultSet.getString("POT"));
                vendorModel.setPod(resultSet.getString("POD"));
                vendorModel.setDestination(resultSet.getString("DESTINATION"));
                vendorModel.setOwner(resultSet.getString("OWNER"));
                vendorModel.setBooking(resultSet.getString("BOOKING"));
                vendorModel.setBl(resultSet.getString("BL"));
              System.out.println(">>>>>>>>>>"+vendorModel);
            } catch (SQLException e) {
                //TO-DO                
                e.printStackTrace();
            }
            return vendorModel;
        }
    }
    //##02 END

   
    protected class IjsJOEquipBrowserookupCntProce extends StoredProcedure {
        private static final String SQL_RLTD_IJS_JO_EQUIP_LOOK_UP = 
            "PCR_IJS_CNTR_COMMON.PRR_IJS_JO_SRV_VESL_VOYA_DIR";

        IjsJOEquipBrowserookupCntProce(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_RLTD_IJS_JO_EQUIP_LOOK_UP);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR));
            //declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR));
            //declareParameter(new SqlInOutParameter("p_i_v_sort_by", Types.VARCHAR));
            //declareParameter(new SqlInOutParameter("p_i_v_order_by", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_row_start", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_row_end", Types.NUMERIC));
            //declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_is_for_count", Types.VARCHAR));
            
            declareParameter(new SqlOutParameter("p_o_v_total_count", Types.NUMERIC));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_ijs_equip_mapping_list", OracleTypes.CURSOR));
            compile();
        }

        private int getLookupResultsCount(IjsLookupParamVO ijsLookupParamVO, 
                                          String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in", RutDatabase.stringToDb(ijsLookupParamVO.getFindIn()).toUpperCase());
            inParameters.put("p_i_v_find_for", RutDatabase.stringToDb(ijsLookupParamVO.getFindFor()).toUpperCase());
            
            //inParameters.put("p_i_v_wild_card", RutDatabase.stringToDb(ijsLookupParamVO.getWildCard()).toUpperCase());
            //inParameters.put("p_i_v_sort_by", RutDatabase.stringToDb(ijsLookupParamVO.getSortBy()).toUpperCase());
            //inParameters.put("p_i_v_order_by", RutDatabase.stringToDb(ijsLookupParamVO.getOrderBy()).toUpperCase());
            
            inParameters.put("p_i_v_row_start", ijsLookupParamVO.getRowStart());
            inParameters.put("p_i_v_row_end", ijsLookupParamVO.getRowEnd());
            
            //inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
            
            inParameters.put("p_i_v_is_for_count", "YES");

            outMap = execute(inParameters);
            BigDecimal count = (BigDecimal)outMap.get("p_o_v_total_count");

            return count.intValue();
        }
    }
    //##04 END;
    
    //BEGIN:MADHURI
    
     protected class IjsJORoutingLookUpProcedure extends StoredProcedure {
         private static final String SQL_RLTD_IJS_ROUTING_LOOK_UP = 
             "PCR_IJS_CNTR_COMMON.PRR_IJS_ROUTING_LOOKUP";

         IjsJORoutingLookUpProcedure(JdbcTemplate jdbcTemplate, 
                                          RowMapper rowMapper) {
             super(jdbcTemplate, SQL_RLTD_IJS_ROUTING_LOOK_UP);
             System.out.println(rowMapper);
             declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, 
                         rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_find_for_loc", Types.VARCHAR, 
                         rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_find_for_terminal", Types.VARCHAR, 
                         rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_find_for_loc_type", Types.VARCHAR, 
                         rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_find_for_date", Types.VARCHAR, 
                         rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_find_for_vendor_cd", Types.VARCHAR, 
                         rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_trans_mode", Types.VARCHAR, 
                     rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_jo_type", Types.VARCHAR, 
                     rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_same_vendor_in_search", Types.VARCHAR, 
                     rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
             declareParameter(new SqlOutParameter("p_o_v_ijs_vendor_mapping_list", OracleTypes.CURSOR, 
                         rowMapper));
             compile();
         }

         private List<IjsJORoutingLookUpVO> getRoutingList(String findIn, 
                                                                String findForLoc,
                                                                String findForTerminal,
                                                                String findForLocType,
                                                                String findForSaleDateOrJobOrdDate,
                                                                String findForVendorCode,
                                                                String transMode,
                                                                String joType,
                                                                String sameVendorInSearch,
                                                                String wildCard, 
                                                                String userInfo) {
             Map outMap = new HashMap();
             Map inParameters = new HashMap();
             inParameters.put("p_i_v_find_in", RutDatabase.stringToDb(findIn).toUpperCase());
             inParameters.put("p_i_v_find_for_loc", RutDatabase.stringToDb(findForLoc).toUpperCase());
             inParameters.put("p_i_v_find_for_terminal", RutDatabase.stringToDb(findForTerminal).toUpperCase());
             inParameters.put("p_i_v_find_for_loc_type", RutDatabase.stringToDb(findForLocType).toUpperCase());
             inParameters.put("p_i_v_find_for_date", RutDatabase.stringToDb(findForSaleDateOrJobOrdDate));
             inParameters.put("p_i_v_find_for_vendor_cd", RutDatabase.stringToDb(findForVendorCode));
             inParameters.put("p_i_v_trans_mode", RutDatabase.stringToDb(transMode));
             inParameters.put("p_i_v_jo_type", RutDatabase.stringToDb(joType));
             inParameters.put("p_i_v_same_vendor_in_search", RutDatabase.stringToDb(sameVendorInSearch));
             inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo));
//             inParameters.put("p_i_v_wild_card", RutDatabase.stringToDb(wildCard).toUpperCase());
//             inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
             outMap = execute(inParameters);

             return (List<IjsJORoutingLookUpVO>)outMap.get("p_o_v_ijs_vendor_mapping_list");
         }
     }
     
    private class IjsJORoutingLookUpRowMapper implements RowMapper {

            public Object mapRow(ResultSet resultSet, int row) {
                IjsJORoutingLookUpVO routingModel = 
                    new IjsJORoutingLookUpVO();
                try {
                    routingModel.setRoutingId(resultSet.getString("routingId"));
                    routingModel.setContractId(resultSet.getString("contract_id"));
                    routingModel.setDays(resultSet.getString("days"));
                    routingModel.setHours(resultSet.getString("hours"));
                    routingModel.setDistance(resultSet.getString("distance"));
                    routingModel.setUom(resultSet.getString("uom"));
                    routingModel.setFromLocation(resultSet.getString("fromLocation"));
                    routingModel.setToLocation(resultSet.getString("toLocation"));
                    routingModel.setFromLocType(IjsHelper.getLocationType(resultSet.getString("fromLocType")));
                    routingModel.setToLocType(IjsHelper.getLocationType(resultSet.getString("toLocType")));
                    routingModel.setFromTerminal(resultSet.getString("fromTerminal"));
                    routingModel.setToTerminal(resultSet.getString("toTerminal"));
                    routingModel.setCurrency(resultSet.getString("currency"));
                    routingModel.setLegType(IjsHelper.getTransMode(resultSet.getString("transMode")));//legType
                    
                    String priority=RutDatabase.stringToDb(resultSet.getString("priority"));
                    routingModel.setPriority("".equals(priority)?0:Integer.parseInt(priority));
                    routingModel.setVendorCode(resultSet.getString("vendorCode"));
                    routingModel.setTransMode(IjsHelper.getTransMode(resultSet.getString("transMode")));
                  
                } catch (SQLException e) {
                    //TO-DO                
                    e.printStackTrace();
                }
                return routingModel;
            }
        }
   //END:MADHURI
   
   //FSC
    protected class IjsJOFSCLookUpProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_FSC_LOOK_UP = 
            "PCR_IJS_CNTR_COMMON.PRR_IJS_FSC_LOOKUP";

        IjsJOFSCLookUpProcedure(JdbcTemplate jdbcTemplate, 
                                         RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_FSC_LOOK_UP);
            System.out.println(rowMapper);
            declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR,
                             rowMapper));
    //             declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR,
    //                         rowMapper));
    //             declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR,
    //                         rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_vendor_mapping_list", OracleTypes.CURSOR, 
                        rowMapper));
            compile();
        }

        private List<IjsJOFSCLookUpVO> getFSCList(String findIn, 
                                                               String findFor, 
                                                               String wildCard, 
                                                               String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_find_in", RutDatabase.stringToDb(findIn).toUpperCase());
            inParameters.put("p_i_v_find_for", RutDatabase.stringToDb(findFor).toUpperCase());
            inParameters.put("p_i_v_wild_card", RutDatabase.stringToDb(wildCard).toUpperCase());
    //             inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);

            return (List<IjsJOFSCLookUpVO>)outMap.get("p_o_v_ijs_vendor_mapping_list");
        }
    }
    
    private class IjsJOFSCLookUpRowMapper implements RowMapper {

            public Object mapRow(ResultSet resultSet, int row) {
                IjsJOFSCLookUpVO fscModel = 
                    new IjsJOFSCLookUpVO();
                try {
                    fscModel.setSlNo(resultSet.getString("rownum"));
                    fscModel.setDescription(resultSet.getString("fsc_desc"));
                    fscModel.setFSCCode(resultSet.getString("fsc_code"));
                    fscModel.setCompanyName(resultSet.getString("company"));
                    fscModel.setCity(resultSet.getString("city"));
                    fscModel.setState(resultSet.getString("state"));
                    fscModel.setCountry(resultSet.getString("country"));
                    fscModel.setStatus(resultSet.getString("status"));
                  
                } catch (SQLException e) {
                    //TO-DO                
                    e.printStackTrace();
                }
                return fscModel;
            }
        }
   //FSC
    
    
    //BEGIN: Added by Tanveer for Container Lookup
    
     protected class IjsJOContainerLookUpProcedure extends StoredProcedure {
         private static final String SQL_RLTD_IJS_CONTAINER_LOOK_UP = 
             "PCR_IJS_CNTR_COMMON.PRR_IJS_CONTAINER_LOOKUP";
         

         IjsJOContainerLookUpProcedure(JdbcTemplate jdbcTemplate, 
                                          RowMapper rowMapper) {
             super(jdbcTemplate, SQL_RLTD_IJS_CONTAINER_LOOK_UP);
                 declareParameter(new SqlInOutParameter("p_i_v_bookBl_no", Types.VARCHAR,rowMapper));
                 declareParameter(new SqlInOutParameter("p_i_v_bkg_bl_type", Types.VARCHAR,rowMapper));
                 declareParameter(new SqlInOutParameter("p_i_v_job_type", Types.VARCHAR,rowMapper));
                 declareParameter(new SqlInOutParameter("p_i_v_cont_type", Types.VARCHAR,rowMapper));                             
                 declareParameter(new SqlInOutParameter("p_i_v_search_type", Types.VARCHAR,rowMapper));                             
                 declareParameter(new SqlInOutParameter("p_i_v_cont_size", Types.VARCHAR,rowMapper));
                 declareParameter(new SqlInOutParameter("p_i_v_cont_sp_handle", Types.VARCHAR,rowMapper));
                 
             declareParameter(new SqlInOutParameter("p_i_v_cont_frm_loc", Types.VARCHAR,rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_cont_to_loc", Types.VARCHAR,rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_cont_frm_trm", Types.VARCHAR,rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_cont_to_trm", Types.VARCHAR,rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_cont_frm_mode", Types.VARCHAR,rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_cont_to_mode", Types.VARCHAR,rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_cntType", Types.VARCHAR,rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_component_type", Types.VARCHAR,rowMapper));
             declareParameter(new SqlOutParameter("p_o_v_ijs_cntr_mapping_list", OracleTypes.CURSOR, 
                         rowMapper));
             compile();
         }

   private List<IjsContainerLookUpVO> getContainerList(String componentType,List<IjsLookupFeildVO> ijsLookupParamVO) {
             Map outMap = new HashMap();
             Map inParameters = new HashMap();
             String joType=RutDatabase.getValue(ijsLookupParamVO,"jobType");
             if("SEALEG".equals(joType)){
            	 joType = "S";
             }else if("ETR".equals(joType)){
            	 joType = "O";
             }else if("ITR".equals(joType)){
            	 joType = "I";
             }else if("IT".equals(joType)){
            	 joType = "T";
             }
            // getContainerListValidation(ijsLookupParamVO);
             inParameters.put("p_i_v_bookBl_no", RutDatabase.stringToDb( RutDatabase.getValue(ijsLookupParamVO,"bkgBlNumber")) .toUpperCase());
             inParameters.put("p_i_v_bkg_bl_type", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"bookType")).toUpperCase());
             inParameters.put("p_i_v_job_type", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"jobType")).toUpperCase());
             inParameters.put("p_i_v_cont_type", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"contType")).toUpperCase());
             inParameters.put("p_i_v_search_type", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"searchType")).toUpperCase());
             inParameters.put("p_i_v_cont_size", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"cntSize")).toUpperCase());
             inParameters.put("p_i_v_cont_sp_handle", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"cntSplHandling")).toUpperCase());
                 
             inParameters.put("p_i_v_cont_frm_loc", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"fromLocation")).toUpperCase());
             inParameters.put("p_i_v_cont_to_loc", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"toLocation")).toUpperCase());
             inParameters.put("p_i_v_cont_frm_trm", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"fromTerminal")).toUpperCase());
             inParameters.put("p_i_v_cont_to_trm", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"toTerminal")).toUpperCase());
             inParameters.put("p_i_v_cont_frm_mode", RutDatabase.stringToDb(IjsHelper.getLocationCode(RutDatabase.getValue(ijsLookupParamVO,"fromMode"))).toUpperCase());
             inParameters.put("p_i_v_cont_to_mode", RutDatabase.stringToDb(IjsHelper.getLocationCode(RutDatabase.getValue(ijsLookupParamVO,"toMode"))).toUpperCase());
             inParameters.put("p_i_v_cntType",  RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"cntType")).toUpperCase());
             inParameters.put("p_i_v_component_type",  RutDatabase.stringToDb(componentType).toUpperCase());
             
             outMap = execute(inParameters);
             

             return (List<IjsContainerLookUpVO>)outMap.get("p_o_v_ijs_cntr_mapping_list");
         }
		/*
		 * private static final String SELECT_VALIDATION_QUERY = " SELECT " +
		 * "   CASE  WHEN count(*)  =0 THEN  'false' else 'true' end as found" +
		 * "  FROM vasapps.booking_voyage_routing_dtl where " +
		 * "interchange_carrier = ? AND load_port= ? AND " +
		 * "discharge_port= ? AND from_terminal= ? " + "AND to_terminal= ? ;";
		 * 
		 * private List<IjsContainerLookUpVO>
		 * getContainerListValidation(List<IjsLookupFeildVO> ijsLookupParamVO) {
		 * 
		 * Map outMap = new HashMap(); Map inParameters = new HashMap();
		 * 
		 * inParameters.put("searchvendorCode", RutDatabase.stringToDb(
		 * RutDatabase.getValue(ijsLookupParamVO,"searchvendorCode")) .toUpperCase());
		 * inParameters.put("searchfromLocation", RutDatabase.stringToDb(
		 * RutDatabase.getValue(ijsLookupParamVO,"searchfromLocation")) .toUpperCase());
		 * inParameters.put("searchtoLocation", RutDatabase.stringToDb(
		 * RutDatabase.getValue(ijsLookupParamVO,"searchtoLocation")) .toUpperCase());
		 * inParameters.put("searchfromTerminal", RutDatabase.stringToDb(
		 * RutDatabase.getValue(ijsLookupParamVO,"searchfromTerminal")) .toUpperCase());
		 * inParameters.put("searchtoTerminal", RutDatabase.stringToDb(
		 * RutDatabase.getValue(ijsLookupParamVO,"searchtoTerminal")) .toUpperCase());
		 * String searchvendorCode = (String) inParameters.get("searchvendorCode");
		 * String searchfromLocation = (String) inParameters.get("searchfromLocation");
		 * String searchtoLocation = (String) inParameters.get("searchtoLocation");
		 * String searchfromTerminal = (String) inParameters.get("searchfromTerminal");
		 * String searchtoTerminal = (String) inParameters.get("searchtoTerminal");
		 * 
		 * 
		 * getJdbcTemplate().query(SELECT_VALIDATION_QUERY, new Object[] {
		 * searchvendorCode, searchfromLocation, searchtoLocation, searchfromTerminal,
		 * searchtoTerminal}, new IJSRowMapper());
		 * 
		 * System.out.println("...");
		 * 
		 * 
		 * return null;
		 * 
		 * }
		 */
   
 }
     
	/*
	 * private class IJSRowMapper implements RowMapper {
	 * 
	 * @Override public Object mapRow(ResultSet arg0, int arg1) throws SQLException
	 * { // TODO Auto-generated method stub return null; }
	 * 
	 * }
	 */
     
     
     
    private class IjsJOContainerLookUpRowMapper implements RowMapper {

            public Object mapRow(ResultSet resultSet, int row) {
                IjsContainerLookUpVO containerVO = 
                    new IjsContainerLookUpVO();
                try {
                    containerVO.setContainer(resultSet.getString("CONTAINER_NO"));
                   containerVO.setContainerWeight(resultSet.getString("NET_WEIGHT"));
                   //containerVO.setContainerWeight("Hello");
                } catch (SQLException e) {
                    //TO-DO                
                    e.printStackTrace();
                }
                return containerVO;
            }
        }
    //END: Added by Tanveer for Container Lookup
    
    
     //BEGIN: Added by Tanveer for DG/IMDG Lookup
     
      protected class IjsJODgImdgLookUpProcedure extends StoredProcedure {
          private static final String SQL_RLTD_IJS_DGIMDG_LOOK_UP = 
              "PCR_IJS_CNTR_COMMON.PRR_IJS_DGIMDG_LOOKUP";

          IjsJODgImdgLookUpProcedure(JdbcTemplate jdbcTemplate, 
                                           RowMapper rowMapper) {
              super(jdbcTemplate, SQL_RLTD_IJS_DGIMDG_LOOK_UP);
              declareParameter(new SqlInOutParameter("p_i_v_bookBl_no", Types.VARCHAR,rowMapper));
              declareParameter(new SqlInOutParameter("p_i_v_bkg_bl_type", Types.VARCHAR,rowMapper));
              declareParameter(new SqlInOutParameter("p_i_v_cont_sp_handle", Types.VARCHAR,rowMapper));
              
              declareParameter(new SqlOutParameter("p_o_v_ijs_dgimdg_mapping_list", OracleTypes.CURSOR, 
                          rowMapper));
              compile();
          }

     //         private List<IjsContainerLookUpVO> getContainerList(String findIn,
     //                                                                String findFor,
     //                                                                String wildCard,
     //                                                                String userInfo) {
           private List<IjsDgImdgLookUpVO> getDgImdgList(List<IjsLookupFeildVO> ijsLookupParamVO) {
              Map outMap = new HashMap();
              Map inParameters = new HashMap();
             
              inParameters.put("p_i_v_bookBl_no", RutDatabase.stringToDb( RutDatabase.getValue(ijsLookupParamVO,"bkgBlNumber")) .toUpperCase());
              inParameters.put("p_i_v_bkg_bl_type", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"bookType")).toUpperCase());
              inParameters.put("p_i_v_cont_sp_handle", RutDatabase.stringToDb(RutDatabase.getValue(ijsLookupParamVO,"spHandlingType")).toUpperCase());
             
              outMap = execute(inParameters);

              return (List<IjsDgImdgLookUpVO>)outMap.get("p_o_v_ijs_dgimdg_mapping_list");
          }
      }
      
     private class IjsJODgImdgLookUpRowMapper implements RowMapper {

             public Object mapRow(ResultSet resultSet, int row) {
                 IjsDgImdgLookUpVO dgImdgVO = 
                     new IjsDgImdgLookUpVO();
                 try {
                     //dgImdgVO.setFlashPoint(resultSet.getString("CONTAINER_NO"));
                     dgImdgVO.setUNNO(resultSet.getString("UNNO"));
                     dgImdgVO.setVariant(resultSet.getString("VARIANT"));
                     dgImdgVO.setIMDGClass(resultSet.getString("IMDGCLASS"));
                     dgImdgVO.setPortClass(resultSet.getString("PORTCLASS"));
//                     dgImdgVO.setResiduesOnly(resultSet.getString("CONTAINER_NO"));
//                     dgImdgVO.setFumigationOnly(resultSet.getString("CONTAINER_NO"));
                     
                     
                   
                 } catch (SQLException e) {
                     //TO-DO                
                     e.printStackTrace();
                 }
                 return dgImdgVO;
             }
         }
     //END: Added by Tanveer for DG/IMDG Lookup
     
      //BEGIN: Added by Tanveer for Reason Code Lookup
      
       protected class IjsJOReasonCodeLookUpProcedure extends StoredProcedure {
           private static final String SQL_RLTD_IJS_REASON_LOOK_UP = 
               "PCR_IJS_CNTR_COMMON.PRR_IJS_REASON_CODE_LOOKUP";

           IjsJOReasonCodeLookUpProcedure(JdbcTemplate jdbcTemplate, 
                                            RowMapper rowMapper) {
               super(jdbcTemplate, SQL_RLTD_IJS_REASON_LOOK_UP);
                   declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR,
                               rowMapper));
                   declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR,
                               rowMapper));
                   declareParameter(new SqlInOutParameter("p_i_v_wild_card", Types.VARCHAR,
                               rowMapper));
      
               declareParameter(new SqlOutParameter("p_o_v_ijs_rsn_cd_mapping_list", OracleTypes.CURSOR, 
                           rowMapper));
               compile();
           }

               private List<IjsReasonCodeLookUpVO> getReasonCodeList(IjsLookupParamVO ijsLookupParamVO) {
//            private List<IjsReasonCodeLookUpVO> getReasonCodeList() {
               Map outMap = new HashMap();
               Map inParameters = new HashMap();
                   inParameters.put("p_i_v_find_in", RutDatabase.stringToDb(ijsLookupParamVO.getFindIn()).toUpperCase());
                   inParameters.put("p_i_v_find_for", RutDatabase.stringToDb(ijsLookupParamVO.getFindFor()).toUpperCase());
                   inParameters.put("p_i_v_wild_card", RutDatabase.stringToDb(ijsLookupParamVO.getWildCard()).toUpperCase());
      
               outMap = execute(inParameters);

               return (List<IjsReasonCodeLookUpVO>)outMap.get("p_o_v_ijs_rsn_cd_mapping_list");
           }
       }
       
      private class IjsJOReasonCodeLookUpRowMapper implements RowMapper {

              public Object mapRow(ResultSet resultSet, int row) {
                  IjsReasonCodeLookUpVO reasonCodeVO = 
                      new IjsReasonCodeLookUpVO();
                  try {
                      reasonCodeVO.setReasonCode(resultSet.getString("REASONCODE"));
                      reasonCodeVO.setDescription(resultSet.getString("DESCIPTION"));
                      reasonCodeVO.setStatus(resultSet.getString("STATUS"));
                  } catch (SQLException e) {
                      //TO-DO                
                      e.printStackTrace();
                  }
                  return reasonCodeVO;
              }
          }
      //END: Added by Tanveer for Reason Code Lookup
      
       //BEGIN: Added by Tanveer for Booking BL Lookup
       
        protected class IjsJOBkgBLLookUpProcedure extends StoredProcedure {
            private static final String SQL_RLTD_BKG_BL_LOOK_UP = 
                //"PCR_IJS_CNTR_COMMON_TANVEER.PRR_IJS_BOOK_BL_LOOKUP";
            "PCR_IJS_CNTR_COMMON.PRR_IJS_BOOK_BL_LOOKUP";

            IjsJOBkgBLLookUpProcedure(JdbcTemplate jdbcTemplate, 
                                             RowMapper rowMapper) {
                super(jdbcTemplate, SQL_RLTD_BKG_BL_LOOK_UP);
                
                    declareParameter(new SqlInOutParameter("p_i_v_book_bl_no", Types.VARCHAR, rowMapper));
       
                declareParameter(new SqlOutParameter("p_o_v_ijs_bl_bkg_mapping_list", OracleTypes.CURSOR, 
                            rowMapper));
                compile();
            }

       
             private List<IjsBkGBlLookUpVO> getBkgBlList(List<IjsLookupFeildVO> ijsLookupParamVO) {
                Map outMap = new HashMap();
                Map inParameters = new HashMap();
                
                inParameters.put("p_i_v_book_bl_no", RutDatabase.stringToDb( RutDatabase.getValue(ijsLookupParamVO,"bkgOrBLNo")) .toUpperCase());
       
                outMap = execute(inParameters);

                return (List<IjsBkGBlLookUpVO>)outMap.get("p_o_v_ijs_bl_bkg_mapping_list");
            }
        }
        
       private class IjsJOBkgBlLookUpRowMapper implements RowMapper {

               public Object mapRow(ResultSet resultSet, int row) {
                   IjsBkGBlLookUpVO bkgBlVO = 
                       new IjsBkGBlLookUpVO();
                   try {
                	   String amount=resultSet.getString("AMOUNT");
                	   String amountUSD=resultSet.getString("AMOUNT_USD");
                	   String afsService=resultSet.getString("ASF_SERVICE");
                	   String afsVessel=resultSet.getString("ASF_VESSEL");
                	   String afsVoyage=resultSet.getString("ASF_VOY_NUM");
                	   String preService=resultSet.getString("PRE_SERVICE");
                	   String preVessel=resultSet.getString("PRE_VESSEL");
                	   String preVoyage=resultSet.getString("PRE_VOY_NUM");
                	   String carrierService=resultSet.getString("CARRIER_SERVICE");
                	   String carrierVessel=resultSet.getString("CARRIER_VESSEL");
                	   String carrierVoyage=resultSet.getString("CARRIER_VOY_NUM");
                       String bookingBLType=resultSet.getString("BKG_BL_TYPE");
                       
                       String service=null;
                       String vessel=null;
                       String voyage=null;
                       if("BOOKING".equalsIgnoreCase(bookingBLType)){
                    	   service=afsService;
                    	   vessel=afsVessel;
                    	   voyage=afsVoyage;
                    	   if(service==null){
                    		   service=carrierService;
                        	   vessel=carrierVessel;
                        	   voyage=carrierVoyage; 
                    	   }
                       }else if("BL".equalsIgnoreCase(bookingBLType)){
                    	   service=preService;
                    	   vessel=preVessel;
                    	   voyage=preVoyage;
                    	   
                       }else{
                    	   service=afsService;
                    	   vessel=afsVessel;
                    	   voyage=afsVoyage;
                       }
                       
                       bkgBlVO.setBookingBL(resultSet.getString("FK_BKG_BL_NUM"));
                       bkgBlVO.setSOCCOC(resultSet.getString("SOC_COC"));
                       bkgBlVO.setStatus(resultSet.getString("STATUS"));
                       bkgBlVO.setType(bookingBLType);
                       bkgBlVO.setService(service);
                       bkgBlVO.setVessel(vessel);
                       bkgBlVO.setVoyage(voyage);
                       bkgBlVO.setETA(resultSet.getString("ETA"));
                       bkgBlVO.setETD(resultSet.getString("ETD"));
                       bkgBlVO.setSize("0".equals(resultSet.getString("CONTAINER_SIZE"))?"**":resultSet.getString("CONTAINER_SIZE"));
                       bkgBlVO.setTotalContainer(resultSet.getString("QTY"));
                       bkgBlVO.setCurrency(resultSet.getString("CURRENCY"));
                       bkgBlVO.setAmount(amount!=null?RutFormatting.getStringToDecimalFormat(amount,null):"0.0");
                       bkgBlVO.setAmountUSD(amountUSD!=null?RutFormatting.getStringToDecimalFormat(amountUSD,null):"0.0");
                       
                       bkgBlVO.setVesselDir(resultSet.getString("ASF_VESSEL_DIR"));
                       bkgBlVO.setContainerType(resultSet.getString("CONTAINER_TYPE"));
                       bkgBlVO.setSpecialHandling(resultSet.getString("SPECIAL_HANDLING"));
                       bkgBlVO.setHeaderId(resultSet.getString("HEADER_ID"));
                       bkgBlVO.setDetailId(resultSet.getString("DETAIL_ID"));
                       bkgBlVO.setCostId(resultSet.getString("COST_ID"));
                     
                   } catch (SQLException e) {
                       //TO-DO                
                       e.printStackTrace();
                   }
                   return bkgBlVO;
               }
           }
       //END: Added by Tanveer for Booking BL Lookup


        //BEGIN: Added by Tanveer for JO Log Lookup
        
         protected class IjsJOLogLookUpProcedure extends StoredProcedure {
             private static final String SQL_RLTD_JO_LOG_LOOK_UP = 
                 "PCR_IJS_CNTR_COMMON.PRR_IJS_JO_LOG_LOOKUP";

             IjsJOLogLookUpProcedure(JdbcTemplate jdbcTemplate, 
                                              RowMapper rowMapper) {
                 super(jdbcTemplate, SQL_RLTD_JO_LOG_LOOK_UP);
                 
                     declareParameter(new SqlInOutParameter("p_i_v_jo_no", Types.VARCHAR, rowMapper));
        
                 declareParameter(new SqlOutParameter("p_o_v_ijs_jo_log_mapping_list", OracleTypes.CURSOR, 
                             rowMapper));
                 compile();
             }

        
              private List<IjsJOLogLookUpVO> getJoLogList(IjsLookupParamVO ijsLookupParamVO) {
                 Map outMap = new HashMap();
                 Map inParameters = new HashMap();
                 inParameters.put("p_i_v_jo_no", RutDatabase.stringToDb(ijsLookupParamVO.getFindFor() ) .toUpperCase());
                 outMap = execute(inParameters);
                 return (List<IjsJOLogLookUpVO>)outMap.get("p_o_v_ijs_jo_log_mapping_list");
             }
         }
         
        private class IjsJOLogLookUpRowMapper implements RowMapper {

                public Object mapRow(ResultSet resultSet, int row) {
                    IjsJOLogLookUpVO joLogVO = 
                        new IjsJOLogLookUpVO();
                    try {
                        joLogVO.setSR(resultSet.getString("JO_NUM_HISTORY_ID"));
                        joLogVO.setActivity(resultSet.getString("ACTIVITY"));
                        joLogVO.setDate(resultSet.getString("ACTIVITY_DATE"));
                        joLogVO.setUser(resultSet.getString("LOGGED_IN_USER"));
                        
                      
                    } catch (SQLException e) {
                        //TO-DO                
                        e.printStackTrace();
                    }
                    return joLogVO;
                }
            }
        //END: Added by Tanveer for JO Log Lookup
        
         //BEGIN: Added by Tanveer for BKG BL delete
         protected class IjsDeleteBkgBlStoredProcedure extends StoredProcedure{
         
         
             private static final String PRR_IJS_BL_BKG_DEL = 
                 "PCR_IJS_CNTR_COMMON.PRR_IJS_BKG_BL_DEL";
             //"PCR_IJS_CNTR_COMMON_TANVEER.PRR_IJS_BKG_BL_DEL";

             IjsDeleteBkgBlStoredProcedure(JdbcTemplate jdbcTemplate, 
                                         RowMapper rowMapper) {
                 super(jdbcTemplate, PRR_IJS_BL_BKG_DEL);
                 
                 declareParameter(new SqlInOutParameter("p_i_v_jo_number", Types.VARCHAR,rowMapper));
                 declareParameter(new SqlInOutParameter("p_i_v_hdr_id", Types.VARCHAR,rowMapper));
                 declareParameter(new SqlInOutParameter("p_i_v_dtl_id", Types.VARCHAR,rowMapper));
                 declareParameter(new SqlInOutParameter("p_i_v_cntr_size", Types.VARCHAR,rowMapper));
                 
                 declareParameter(new SqlInOutParameter("p_i_v_cntr_type", Types.VARCHAR,rowMapper));
                 declareParameter(new SqlInOutParameter("p_i_v_sp_handling", Types.VARCHAR,rowMapper));
                 declareParameter(new SqlInOutParameter("p_i_v_bl_bkg_number", Types.VARCHAR,rowMapper));
                 declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
                 compile();

             }
             
                 public String updateBkgBl(IjsLookupParamVO ijsLookupParamVO, String userId, String action) {
                     Map outMap = new HashMap();
                     Map inParameters = new HashMap();
                     String joNumber =  ijsLookupParamVO.getFindIn();
                     ArrayList<IjsBkGBlLookUpVO> deleteForList = (ArrayList<IjsBkGBlLookUpVO>)ijsLookupParamVO.getDeleteFor();
                     
                     for( IjsBkGBlLookUpVO ijsBkGBlLookUpVO : deleteForList ){
                                 inParameters.put("p_i_v_jo_number", RutDatabase.stringToDb(joNumber));
                                 inParameters.put("p_i_v_hdr_id", RutDatabase.stringToDb(ijsBkGBlLookUpVO.getHeaderId()));
                                 inParameters.put("p_i_v_dtl_id", RutDatabase.stringToDb(ijsBkGBlLookUpVO.getDetailId()));
                                 inParameters.put("p_i_v_cntr_size", RutDatabase.stringToDb(ijsBkGBlLookUpVO.getSize()));
                                 inParameters.put("p_i_v_cntr_type", RutDatabase.stringToDb(ijsBkGBlLookUpVO.getContainerType()));
                                 inParameters.put("p_i_v_sp_handling", RutDatabase.stringToDb(ijsBkGBlLookUpVO.getSpecialHandling()));
                                 inParameters.put("p_i_v_bl_bkg_number", RutDatabase.stringToDb(ijsBkGBlLookUpVO.getBookingBL()));
                                 
                                 outMap = execute(inParameters);
                             }
                     System.out.println((String)outMap.get("p_o_v_err_cd"));
                     return (String)outMap.get("p_o_v_err_cd") == null ? "IJS_MSG_1021" : (String)outMap.get("p_o_v_err_cd");
                 }
         }

		@Override
		public String deleteLumpsum(List<String> joLumpsumCostIds, String userId) throws IJSException {
			int joSize=joLumpsumCostIds.size();
			int count=1;
			String jobOrders=null;
			String costIds=null;
			String errorCode=null;
			for(String joLumpsum:joLumpsumCostIds){
				String jobOrder=joLumpsum.substring(0,joLumpsum.indexOf(":"));
				costIds=joLumpsum.substring(joLumpsum.indexOf(":")+1);
				if(count==1){
					jobOrders=jobOrder;
				}else{
					jobOrders=jobOrders+","+jobOrder;
				}
				count++;
				errorCode= ijsDeleteLumpsumStoredProcedure.deleteLumpsum(jobOrder,costIds,jobOrders, userId);
			}
			return errorCode;
		}
		protected class IjsDeleteLumpsumStoredProcedure extends StoredProcedure{
	         
	         
            private static final String PRR_IJS_DELETE_LUMPSUM = 
                "PCR_IJS_MAINTAIN_JO_ALL.PRR_IJS_DELETE_LUMPSUM";
            	IjsDeleteLumpsumStoredProcedure(JdbcTemplate jdbcTemplate) {
                super(jdbcTemplate, PRR_IJS_DELETE_LUMPSUM);
                declareParameter(new SqlInOutParameter("p_i_v_jo_order_num", Types.VARCHAR));
                declareParameter(new SqlInOutParameter("p_i_v_jo_cost_id", Types.VARCHAR));
                declareParameter(new SqlInOutParameter("p_i_v_component_type", Types.VARCHAR));
                declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
                declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
                compile();
            }
            
            public String deleteLumpsum(String jobOrderNum,String joCostId, String userId, String action) {
                Map outMap = new HashMap();
                Map inParameters = new HashMap();
                inParameters.put("p_i_v_jo_order_num", RutDatabase.stringToDb(jobOrderNum));
                inParameters.put("p_i_v_jo_cost_id", RutDatabase.stringToDb(joCostId));
                inParameters.put("p_i_v_component_type", RutDatabase.stringToDb("JO_BOOKING"));
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId));
                outMap = execute(inParameters);
                System.out.println((String)outMap.get("p_o_v_err_cd"));
                return (String)outMap.get("p_o_v_err_cd") == null ? "IJS_MSG_1021" : (String)outMap.get("p_o_v_err_cd");
            }
        } 
       
}
