package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsMaintainJOSearchDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractDownloadDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsMaintainJOSearchContDetailDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsMaintainJOSearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsMaintainJoDownloadDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class IjsMaintainJOSearchJdbcDao extends IjsBaseDao implements IjsMaintainJOSearchDao {
    private IjsMaintainJOSearchJdbcDao.GetIjsSearchStoredProcedure getIjsSearchStoredProcedure; //##01
    private IjsMaintainJOSearchJdbcDao.GetIjsSearchStoredProcedureContDtl ijsSearchStoredProcedureContDtl; //##01
    private IjsMaintainJOSearchJdbcDao.IjsCompleteJOStoredProcedure ijsCompleteJOStoredProcedure; //##03
    private IjsMaintainJOSearchJdbcDao.IjsApproveCancelRejStoredProcedure ijsApproveCancelRejStoredProcedure; //##03
    private GetIjsMaintainDownloadStoredProcedure ijsMaintainDownloadStoredProcedure;//##05
    private IjsMaintainJOSearchJdbcDao.IjsSaveFSCStoredProcedure ijSaveFSCStoredProcedure; //##03
    private IjsMaintainJOSearchJdbcDao.IjsRemoveEqJOStoredProcedure ijsRemoveEqJOStoredProcedure; //##03
    private IjsMaintainJOSearchJdbcDao.IjsReplaceEqJOStoredProcedure ijsReplaceEqJOStoredProcedure; //##03
    private IjsMaintainJOSearchJdbcDao.GetIjsDownloadStoredProcedureContDtl ijsDownloadStoredProcedureContDtl;
    private IjsMaintainJOSearchJdbcDao.IjsChangeVendorStoredProcedure ijsChangeVendorStoredProcedure;
    private IjsMaintainJOSearchJdbcDao.IjsCostCalcStoredProcedure ijsCostCalcStoredProcedure;
    private GetIjsSearchCountStoredProcedure getIjsSearchCountStoredProcedure;
    private IjsDeleteLumpsumStoredProcedure ijsDeleteLumpsumStoredProcedure;
    private GetIjsMaintainDownloadStoredProcedure ijsMaintainDownloadCountStoredProcedure;//##05
    private static final String ERROR_CD="ERROR_CD" ;
    private static final String DOWNLOAD_LIMIT="DOWNLOAD_LIMIT" ;
    private static final String DOWNLOAD_CONTAINER="DOWNLOAD_CONTAINER" ;
    public void initDao() throws Exception {
        //##01 BEGIN
        super.initDao();
        getIjsSearchStoredProcedure = 
                new IjsMaintainJOSearchJdbcDao.GetIjsSearchStoredProcedure(getJdbcTemplate(), 
                                                                               new IjsMaintainJOSearchJdbcDao.IjsMaintainJOSearchRowMapper());
        ijsSearchStoredProcedureContDtl = 
                    new IjsMaintainJOSearchJdbcDao.GetIjsSearchStoredProcedureContDtl(getJdbcTemplate(), 
                                                                                   new IjsMaintainJOSearchJdbcDao.IjsMaintainJOSearchRowMapperContDtl());
        //##01 END
        ijSaveFSCStoredProcedure = 
                new IjsMaintainJOSearchJdbcDao.IjsSaveFSCStoredProcedure(getJdbcTemplate(),
                                         new IjsMaintainJOSearchJdbcDao.IjsMaintainJOSearchRowMapper()); //##03
                                                                                
        ijsCompleteJOStoredProcedure = 
                new IjsMaintainJOSearchJdbcDao.IjsCompleteJOStoredProcedure(getJdbcTemplate(),
                                          new IjsMaintainJOSearchJdbcDao.IjsMaintainJOSearchRowMapper()); //##03
                                          
       ijsApproveCancelRejStoredProcedure = 
               new IjsMaintainJOSearchJdbcDao.IjsApproveCancelRejStoredProcedure(getJdbcTemplate(),
                                         new IjsMaintainJOSearchJdbcDao.IjsMaintainJOSearchRowMapper()); //##03
                                                                                
        ijsRemoveEqJOStoredProcedure = 
               new IjsMaintainJOSearchJdbcDao.IjsRemoveEqJOStoredProcedure(getJdbcTemplate(),
                                         new IjsMaintainJOSearchJdbcDao.IjsMaintainJOSearchRowMapper()); //##03                                                                       
         
         
        ijsReplaceEqJOStoredProcedure = 
                 new IjsMaintainJOSearchJdbcDao.IjsReplaceEqJOStoredProcedure(getJdbcTemplate(),
                                           new IjsMaintainJOSearchJdbcDao.IjsMaintainJOSearchRowMapper()); //##03 
                                           
        ijsChangeVendorStoredProcedure = 
                 new IjsMaintainJOSearchJdbcDao.IjsChangeVendorStoredProcedure(getJdbcTemplate(),
                                           new IjsMaintainJOSearchJdbcDao.IjsMaintainJOSearchRowMapper()); //##03
                                           
        ijsCostCalcStoredProcedure = 
                 new IjsMaintainJOSearchJdbcDao.IjsCostCalcStoredProcedure(getJdbcTemplate(),
                                           new IjsMaintainJOSearchJdbcDao.IjsMaintainJOSearchRowMapper()); //##03  
                                                                                  
        ijsMaintainDownloadStoredProcedure = new GetIjsMaintainDownloadStoredProcedure(getJdbcTemplate() 
             ,  new IjsMaintainJOSearchJdbcDao.IjsMaintainJODownloadRowMapper());
        
        ijsDownloadStoredProcedureContDtl = 
                        new IjsMaintainJOSearchJdbcDao.GetIjsDownloadStoredProcedureContDtl(getJdbcTemplate(), 
                                                     new IjsMaintainJOSearchJdbcDao.IjsMaintainJODownloadRowMapperContDtl());
            
        getIjsSearchCountStoredProcedure  =new GetIjsSearchCountStoredProcedure(getJdbcTemplate());      
        ijsDeleteLumpsumStoredProcedure=new IjsDeleteLumpsumStoredProcedure(getJdbcTemplate());
        ijsMaintainDownloadCountStoredProcedure=new GetIjsMaintainDownloadStoredProcedure(getJdbcTemplate());
        
        }
    //##01 BEGIN

    /**
     * findContracts method for getting contracts based on search criteria
     * @param transMode
     * @param dateRange
     * @param userId
     * @param searchParam
     * @return
     * @throws IJSException
     */
    public List<IjsMaintainJOSearchDTO> findJobOrder(String userId, 
                                                          IjsMaintainJOSearchParamVO searchParam) throws IJSException {
    	System.out.println("inside findJobOrder:");
        List<IjsMaintainJOSearchDTO> lstJobOrders =
            getIjsSearchStoredProcedure.getIjsJoMaintenaceSearchList(userId, 
                                                                 searchParam);
        
            for(int i=0;i< lstJobOrders.size();i++){
                IjsMaintainJOSearchDTO joDtl = lstJobOrders.get(i);
                List<IjsMaintainJOSearchContDetailDTO> contDtlVo =  ijsSearchStoredProcedureContDtl.getIjsContractSearchListContDtl(userId, 
                                                                    searchParam,lstJobOrders.get(i).getJoNumber());
                joDtl.setContDetailJO(contDtlVo);
            }                                                     
               
        if (lstJobOrders == null || lstJobOrders.isEmpty()) {
            IJSException ijsException = new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
            throw ijsException;
        }
        return lstJobOrders;
    }
    
    /**
     * findContracts method for getting contracts based on search criteria
     * @param transMode
     * @param dateRange
     * @param userId
     * @param searchParam
     * @return
     * @throws IJSException
     */
    public List<IjsMaintainJoDownloadDTO> findJobOrderToDownload(String userId, 
                                                          IjsMaintainJOSearchParamVO searchParam) throws IJSException {
    	
        List<IjsMaintainJoDownloadDTO> lstMaintainDownload =
            ijsMaintainDownloadStoredProcedure.getIjsJOMaintenanceSearchDownloadList(userId, 
                                                                 searchParam);
        
        if (lstMaintainDownload == null || lstMaintainDownload.isEmpty()) {
            IJSException ijsException = new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
            throw ijsException;
        }
        return lstMaintainDownload;
    }


    //##01 END
    //##03 BEGIN

    /**
     * saveOrUpdateContract for saving contract
     * @param ijsContractVO
     * @param userInfo
     * @param action
     * @return
     * @throws IJSException
     */
    public String completeJO(List<IjsMaintainJOSearchVO> searchVo,String userId,String action) throws IJSException {
        String errorCode;
        
        if(("completeJo").equalsIgnoreCase(action)) {
            errorCode =
                        ijsCompleteJOStoredProcedure.completeJO(searchVo,userId,action);
        } else {
            errorCode =
                        ijsApproveCancelRejStoredProcedure.approveRejCancelJO(searchVo,userId,action);
        }
        
        if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10006")) {
            
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10006.getErrorCode());
        } else if (errorCode != null && 
                   errorCode.contains("DB_IJS_CNTR_EX_10007")) {
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10007.getErrorCode());
        } else if (errorCode != null && 
                   errorCode.contains("DB_IJS_CNTR_EX_10008")) {
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10008.getErrorCode());
        }
        return errorCode;
    }
    //##03 END
    //##04 BEGIN

     /**
      * saveOrUpdateContract for saving contract
      * @param ijsContractVO
      * @param userInfo
      * @param action
      * @return
      * @throws IJSException
      */
     public String saveFSC(List<IjsMaintainJOSearchVO> searchVo,String userId,String action) throws IJSException {
         
         //to save routing and fsc
         String errorCode =
                     ijSaveFSCStoredProcedure.saveFSC(searchVo,userId,action);
                     
         //String errorCode = (String)outMap.get("p_o_v_err_cd");
         if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10006")) {
             
             throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10006.getErrorCode());
         } else if (errorCode != null && 
                    errorCode.contains("DB_IJS_CNTR_EX_10007")) {
             throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10007.getErrorCode());
         } else if (errorCode != null && 
                    errorCode.contains("DB_IJS_CNTR_EX_10008")) {
             throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10008.getErrorCode());
         }
         return errorCode;
     }
     
    /**
     * saveOrUpdateContract for saving contract
     * @param ijsContractVO
     * @param userInfo
     * @param action
     * @return
     * @throws IJSException
     */
    public String changeVendor(List<IjsMaintainJOSearchVO> searchVo,String userId,String action) throws IJSException {
        
        //to save routing and fsc
        String errorCode = ijsCostCalcStoredProcedure.costCalculation(searchVo,userId,action);
                    
        if(errorCode!=null && errorCode.equalsIgnoreCase("MSG"))  {     
            errorCode =
                        ijsChangeVendorStoredProcedure.changeVendor(searchVo,userId,action);
        }
                    
        if(errorCode != null){
        	if(errorCode.equalsIgnoreCase("MSG")){
        		errorCode=IjsErrorCode.DB_IJS_MAINT_JO_EX_20005.getErrorCode();
        	}else if (errorCode.contains("DB_IJS_CNTR_EX_10006")) {
                 throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10006.getErrorCode());
             } else if (errorCode.contains("DB_IJS_CNTR_EX_10007")) {
                 throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10007.getErrorCode());
             } else if (errorCode.contains("DB_IJS_CNTR_EX_10008")) {
                 throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10008.getErrorCode());
             } else if (errorCode.contains("DB_IJS_MAINT_JO_EX_20001")) {
                 throw new IJSException(IjsErrorCode.DB_IJS_MAINT_JO_EX_20001.getErrorCode());
             } else if (errorCode.contains("DB_IJS_MAINT_JO_EX_20002")) {
                 throw new IJSException(IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.getErrorCode());
             } else if (errorCode.contains("DB_IJS_MAINT_JO_EX_20003")) {
              throw new IJSException(IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.getErrorCode());
             } else if (errorCode.contains("DB_IJS_MAINT_JO_EX_20004")) {
               throw new IJSException(IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.getErrorCode());
             }	
        }
       
        return errorCode;
    }
     
    /**
     * saveOrUpdateContract for saving contract
     * @param ijsContractVO
     * @param userInfo
     * @param action
     * @return
     * @throws IJSException
     */
    public String removeEquipmentJO(List<IjsMaintainJOSearchVO> searchVo,String userId,String action) throws IJSException {
        String errorCode =
                    ijsRemoveEqJOStoredProcedure.removeEq(searchVo,userId,action);
        
        //String errorCode = (String)outMap.get("p_o_v_err_cd");
//        if (errorCode != null && errorCode.contains("DB_IJS_MAINT_JO_EX_20007")) {
//            
//            throw new IJSException(IjsErrorCode.DB_IJS_MAINT_JO_EX_20007.getErrorCode());
//        } 
            //else if (errorCode != null && 
//                   errorCode.contains("DB_IJS_CNTR_EX_10007")) {
//            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10007.getErrorCode());
//        } else if (errorCode != null && 
//                   errorCode.contains("DB_IJS_CNTR_EX_10008")) {
//            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10008.getErrorCode());
//        }
        return errorCode;
    }
    
    /**
     * saveOrUpdateContract for saving contract
     * @param ijsContractVO
     * @param userInfo
     * @param action
     * @return
     * @throws IJSException
     */
    public String replaceEquipmentJO(List<IjsMaintainJOSearchVO> searchVo,String userId,String action) throws IJSException {
        String errorCode =
                    ijsReplaceEqJOStoredProcedure.replaceEq(searchVo,userId,action);
        
        //String errorCode = (String)outMap.get("p_o_v_err_cd");
        if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10006")) {
            
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10006.getErrorCode());
        } else if (errorCode != null && 
                   errorCode.contains("DB_IJS_CNTR_EX_10007")) {
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10007.getErrorCode());
        } else if (errorCode != null && 
                   errorCode.contains("DB_IJS_CNTR_EX_10008")) {
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10008.getErrorCode());
        }
        return errorCode;
    }

    public int getTotalResultCountForJO(String userInfo, 
                                        IjsMaintainJOSearchParamVO mainatainJoParam) {
          return getIjsSearchCountStoredProcedure.getIjsJoMaintenanceSearchCount(userInfo,mainatainJoParam);
    }


    //##05 END
    //##01 BEGIN

    protected class GetIjsSearchStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_MAINTAIN_JO_SEARCH = 
            "PCR_IJS_MAINTAIN_JO_ALL.MAINTAIN_JO_SEARCH";

        GetIjsSearchStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, SQL_MAINTAIN_JO_SEARCH);
            
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_sts", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vendor_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_country_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_loc_type", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_loc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_terminal", Types.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc_type", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_dt", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_dt", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bl_or_book_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bl_or_book_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_rout_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_contract_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_container_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_cost_val_from", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_cost_val_to", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_service_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vessel_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_voyage_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_leg_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_payment_fsc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_soc_coc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_component_type", Types.VARCHAR, 
                        rowMapper));
                        
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR,
                       rowMapper));
            declareParameter(new SqlOutParameter("p_io_v_err_cd", Types.VARCHAR, 
                        rowMapper));

            declareParameter(new SqlOutParameter("p_o_v_user_type", Types.VARCHAR, 
                        rowMapper));//used for user authorisation
            declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_row_start", OracleTypes.NUMBER, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_row_end", OracleTypes.NUMBER, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_order_by", OracleTypes.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_order_type", OracleTypes.VARCHAR, 
                    rowMapper));
            compile();

        }

        public List<IjsMaintainJOSearchDTO> getIjsJoMaintenaceSearchList(String userId, 
                                                                         IjsMaintainJOSearchParamVO searchParam) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            System.out.println("inside getIjsContractSearchList:");
            String lSocCoc=searchParam.getSocOrCoc();
             inParameters.put("p_i_v_job_ord_typ", RutDatabase.stringToDb(searchParam.getJobOrdTyp() != null ? searchParam.getJobOrdTyp().toUpperCase() : ""));
             inParameters.put("p_i_v_job_ord_sts", RutDatabase.stringToDb(searchParam.getJobOrdSts() != null ? searchParam.getJobOrdSts().toUpperCase() : ""));
             inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(searchParam.getJobOrdNo() != null ? searchParam.getJobOrdNo().toUpperCase() : ""));
             inParameters.put("p_i_v_vendor_cd", RutDatabase.stringToDb(searchParam.getVendorCode() != null ? searchParam.getVendorCode().toUpperCase() : ""));
             inParameters.put("p_i_v_country_cd", RutDatabase.stringToDb(searchParam.getCountryCode() != null ? searchParam.getCountryCode().toUpperCase() : ""));
             inParameters.put("p_i_v_from_loc_type", RutDatabase.stringToDb(searchParam.getFromLocType() != null ? IjsHelper.getLocationCode(searchParam.getFromLocType()) : ""));
             inParameters.put("p_i_v_from_loc", RutDatabase.stringToDb(searchParam.getFromLocation() != null ? searchParam.getFromLocation().toUpperCase() : ""));
             inParameters.put("p_i_v_from_terminal", RutDatabase.stringToDb(searchParam.getFromTerminal() != null ? searchParam.getFromTerminal().toUpperCase() : ""));
             inParameters.put("p_i_v_to_loc_type", RutDatabase.stringToDb(searchParam.getToLocType() != null ? IjsHelper.getLocationCode(searchParam.getToLocType()):""));
             inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(searchParam.getToLocation() != null ? searchParam.getToLocation().toUpperCase() : ""));
             inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(searchParam.getToTerminal() != null ? searchParam.getToTerminal().toUpperCase() : ""));
             inParameters.put("p_i_v_from_dt", RutDatabase.stringToDb(searchParam.getDateRange() != null ? searchParam.getDateRange().substring(0,searchParam.getDateRange().indexOf("-")) : ""));
             inParameters.put("p_i_v_to_dt", RutDatabase.stringToDb(searchParam.getDateRange() != null ? searchParam.getDateRange().substring(searchParam.getDateRange().indexOf("-")+1,(searchParam.getDateRange().length())): ""));
             inParameters.put("p_i_v_bl_or_book_typ", RutDatabase.stringToDb(searchParam.getBookingOrBlType()!= null ? searchParam.getBookingOrBlType().toUpperCase() : ""));
             inParameters.put("p_i_v_bl_or_book_val", RutDatabase.stringToDb(searchParam.getBookingOrBlValue() != null ? searchParam.getBookingOrBlValue().toUpperCase() :""));
             inParameters.put("p_i_v_rout_val", RutDatabase.stringToDb(("Routing").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue().toUpperCase() : ""));
             inParameters.put("p_i_v_contract_val", RutDatabase.stringToDb(("Contract").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue().toUpperCase() : ""));
             inParameters.put("p_i_v_container_val", RutDatabase.stringToDb(("Container").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue().toUpperCase() : ""));
             inParameters.put("p_i_v_jo_cost_val_from", RutDatabase.stringToDb(("JOCostFrom").equalsIgnoreCase(searchParam.getJoCostTyp()) ? searchParam.getJoCostValue() : ""));
             inParameters.put("p_i_v_jo_cost_val_to", RutDatabase.stringToDb(("JOCostTo").equalsIgnoreCase(searchParam.getJoCostTyp()) ? searchParam.getJoCostValue() : ""));
             inParameters.put("p_i_v_service_val", RutDatabase.stringToDb(searchParam.getServiceVal() != null ? searchParam.getServiceVal().toUpperCase() :""));
             inParameters.put("p_i_v_vessel_val", RutDatabase.stringToDb(searchParam.getVesselVal() != null ? searchParam.getVesselVal().toUpperCase() :""));
             inParameters.put("p_i_v_voyage_val", RutDatabase.stringToDb(searchParam.getVoyageVal() != null ? searchParam.getVoyageVal().toUpperCase() :""));
             inParameters.put("p_i_v_leg_typ", RutDatabase.stringToDb(searchParam.getLegType() != null ? IjsHelper.getTransCode(searchParam.getLegType()):""));
             inParameters.put("p_i_v_payment_fsc", RutDatabase.stringToDb(searchParam.getPaymentFSC() != null ? searchParam.getPaymentFSC().toUpperCase() :""));
             inParameters.put("p_i_v_soc_coc", RutDatabase.stringToDb(lSocCoc));
             inParameters.put("p_i_v_component_type", RutDatabase.stringToDb(searchParam.getComponentType()!=null?searchParam.getComponentType().toUpperCase():""));
             inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId.toUpperCase()));
             inParameters.put("p_i_v_row_start", searchParam.getRowStart());
             inParameters.put("p_i_v_row_end", searchParam.getRowEnd());
             inParameters.put("p_i_v_order_by", searchParam.getOrderBy());
             inParameters.put("p_i_v_order_type", searchParam.getOrderType());
             outMap = execute(inParameters);
             return (List<IjsMaintainJOSearchDTO>)outMap.get("p_o_v_ijs_mapping_list");
        }
    }
    
    protected class GetIjsSearchCountStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_MAINTAIN_JO_SEARCH_COUNT = 
            "PCR_IJS_MAINTAIN_JO_ALL.MAINTAIN_JO_SEARCH_COUNT";

        GetIjsSearchCountStoredProcedure(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_MAINTAIN_JO_SEARCH_COUNT);
            
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_typ", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_sts", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_vendor_cd", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_country_cd", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_from_loc_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_from_loc", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_from_terminal", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_from_dt", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_to_dt", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_bl_or_book_typ", Types.VARCHAR)); 
            declareParameter(new SqlInOutParameter("p_i_v_bl_or_book_val", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_rout_val", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_contract_val", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_container_val", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_jo_cost_val_from", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_jo_cost_val_to", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_service_val", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_vessel_val", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_voyage_val", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_leg_typ", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_payment_fsc", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_soc_coc", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_component_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_io_v_err_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_user_type", Types.VARCHAR));//used for user authorisation
            declareParameter(new SqlOutParameter("p_o_v_ijs_search_count", Types.NUMERIC));
            compile();

        }

        public int getIjsJoMaintenanceSearchCount(String userId, IjsMaintainJOSearchParamVO searchParam) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            String lSocCoc=searchParam.getSocOrCoc();
             inParameters.put("p_i_v_job_ord_typ", RutDatabase.stringToDb(searchParam.getJobOrdTyp() != null ? searchParam.getJobOrdTyp().toUpperCase() : ""));
             inParameters.put("p_i_v_job_ord_sts", RutDatabase.stringToDb(searchParam.getJobOrdSts() != null ? searchParam.getJobOrdSts().toUpperCase() : ""));
             inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(searchParam.getJobOrdNo() != null ? searchParam.getJobOrdNo().toUpperCase() : ""));
             inParameters.put("p_i_v_vendor_cd", RutDatabase.stringToDb(searchParam.getVendorCode() != null ? searchParam.getVendorCode().toUpperCase() : ""));
             inParameters.put("p_i_v_country_cd", RutDatabase.stringToDb(searchParam.getCountryCode() != null ? searchParam.getCountryCode().toUpperCase() : ""));
             inParameters.put("p_i_v_from_loc_type", RutDatabase.stringToDb(searchParam.getFromLocType() != null ? IjsHelper.getLocationCode(searchParam.getFromLocType()) : ""));
             inParameters.put("p_i_v_from_loc", RutDatabase.stringToDb(searchParam.getFromLocation() != null ? searchParam.getFromLocation().toUpperCase() : ""));
             inParameters.put("p_i_v_from_terminal", RutDatabase.stringToDb(searchParam.getFromTerminal() != null ? searchParam.getFromTerminal().toUpperCase() : ""));
             inParameters.put("p_i_v_to_loc_type", RutDatabase.stringToDb(searchParam.getToLocType() != null ? IjsHelper.getLocationCode(searchParam.getToLocType()):""));
             inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(searchParam.getToLocation() != null ? searchParam.getToLocation().toUpperCase() : ""));
             inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(searchParam.getToTerminal() != null ? searchParam.getToTerminal().toUpperCase() : ""));
             inParameters.put("p_i_v_from_dt", RutDatabase.stringToDb(searchParam.getDateRange() != null ? searchParam.getDateRange().substring(0,searchParam.getDateRange().indexOf("-")) : ""));
             inParameters.put("p_i_v_to_dt", RutDatabase.stringToDb(searchParam.getDateRange() != null ? searchParam.getDateRange().substring(searchParam.getDateRange().indexOf("-")+1,(searchParam.getDateRange().length())): ""));
             inParameters.put("p_i_v_bl_or_book_typ", RutDatabase.stringToDb(searchParam.getBookingOrBlType()!= null ? searchParam.getBookingOrBlType().toUpperCase() : ""));
             inParameters.put("p_i_v_bl_or_book_val", RutDatabase.stringToDb(searchParam.getBookingOrBlValue() != null ? searchParam.getBookingOrBlValue().toUpperCase() :""));
             inParameters.put("p_i_v_rout_val", RutDatabase.stringToDb(("Routing").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue().toUpperCase() : ""));
             inParameters.put("p_i_v_contract_val", RutDatabase.stringToDb(("Contract").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue().toUpperCase() : ""));
             inParameters.put("p_i_v_container_val", RutDatabase.stringToDb(("Container").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue().toUpperCase() : ""));
             inParameters.put("p_i_v_jo_cost_val_from", RutDatabase.stringToDb(("JOCostFrom").equalsIgnoreCase(searchParam.getJoCostTyp()) ? searchParam.getJoCostValue() : ""));
             inParameters.put("p_i_v_jo_cost_val_to", RutDatabase.stringToDb(("JOCostTo").equalsIgnoreCase(searchParam.getJoCostTyp()) ? searchParam.getJoCostValue() : ""));
             inParameters.put("p_i_v_service_val", RutDatabase.stringToDb(searchParam.getServiceVal() != null ? searchParam.getServiceVal().toUpperCase() :""));
             inParameters.put("p_i_v_vessel_val", RutDatabase.stringToDb(searchParam.getVesselVal() != null ? searchParam.getVesselVal().toUpperCase() :""));
             inParameters.put("p_i_v_voyage_val", RutDatabase.stringToDb(searchParam.getVoyageVal() != null ? searchParam.getVoyageVal().toUpperCase() :""));
             inParameters.put("p_i_v_leg_typ", RutDatabase.stringToDb(searchParam.getLegType() != null ? IjsHelper.getTransCode(searchParam.getLegType()):""));
             inParameters.put("p_i_v_payment_fsc", RutDatabase.stringToDb(searchParam.getPaymentFSC() != null ? searchParam.getPaymentFSC().toUpperCase() :""));
             inParameters.put("p_i_v_soc_coc", RutDatabase.stringToDb(lSocCoc));
             inParameters.put("p_i_v_component_type", RutDatabase.stringToDb(searchParam.getComponentType()!= null ?searchParam.getComponentType().toUpperCase():""));

             inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());

             outMap = execute(inParameters);
             
             
            BigDecimal count = (BigDecimal)outMap.get("p_o_v_ijs_search_count");
            System.out.print("COUNT of Records------------------->"+count);
            
            return count.intValue();
        }
    }
    
    protected class GetIjsMaintainDownloadStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_MAINTAIN_JO_DOWNLOAD = 
            "PCR_IJS_MAINTAIN_JO_ALL.MAINTAIN_JO_DOWNLOAD";
        private static final String SQL_MAINTAIN_JO_DOWNLOAD_COUNT = 
                "PCR_IJS_MAINTAIN_JO_ALL.MAINTAIN_JO_DOWNLOAD_COUNT";

        GetIjsMaintainDownloadStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, SQL_MAINTAIN_JO_DOWNLOAD);
            
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_sts", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vendor_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_country_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_loc_type", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_loc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_terminal", Types.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc_type", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_dt", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_dt", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bl_or_book_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bl_or_book_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_rout_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_contract_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_container_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_cost_val_from", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_cost_val_to", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_service_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vessel_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_voyage_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_leg_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_payment_fsc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_soc_coc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_component_type", Types.VARCHAR, 
                    rowMapper));            
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR,
                       rowMapper));
            declareParameter(new SqlOutParameter("p_io_v_err_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_user_type", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, 
                        rowMapper));
            compile();

        }
        GetIjsMaintainDownloadStoredProcedure(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, SQL_MAINTAIN_JO_DOWNLOAD_COUNT);
		
		declareParameter(new SqlInOutParameter("p_i_v_job_ord_typ", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_job_ord_sts", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_vendor_cd", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_country_cd", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_from_loc_type", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_from_loc", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_from_terminal", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_to_loc_type", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_from_dt", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_to_dt", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_bl_or_book_typ", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_bl_or_book_val", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_rout_val", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_contract_val", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_container_val", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_jo_cost_val_from", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_jo_cost_val_to", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_service_val", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_vessel_val", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_voyage_val", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_leg_typ", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_payment_fsc", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_soc_coc", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("p_i_v_component_type", Types.VARCHAR));            
		declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
		declareParameter(new SqlOutParameter("p_o_v_download_limit", Types.INTEGER));
		declareParameter(new SqlOutParameter("p_o_v_download_count", Types.INTEGER));
		declareParameter(new SqlOutParameter("p_o_v_error_cd", Types.VARCHAR));
		compile();
		
		} 
        public Map<String,String> getIjsJoMaintenanceDownloadCount(String userId, 
                IjsMaintainJOSearchParamVO searchParam) {
		Map outMap = new HashMap();
		Map inParameters = new HashMap();
		Map<String,String> resultMap=new HashMap();
		inParameters.put("p_i_v_job_ord_typ", RutDatabase.stringToDb(searchParam.getJobOrdTyp()));
		inParameters.put("p_i_v_job_ord_sts", RutDatabase.stringToDb(searchParam.getJobOrdSts()));
		inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(searchParam.getJobOrdNo()));
		inParameters.put("p_i_v_vendor_cd", RutDatabase.stringToDb(searchParam.getVendorCode()));
		inParameters.put("p_i_v_country_cd", RutDatabase.stringToDb(searchParam.getCountryCode()));
		inParameters.put("p_i_v_from_loc_type", RutDatabase.stringToDb(IjsHelper.getLocationCode(searchParam.getFromLocType())));
		inParameters.put("p_i_v_from_loc", RutDatabase.stringToDb(searchParam.getFromLocation()).toUpperCase());
		inParameters.put("p_i_v_from_terminal", RutDatabase.stringToDb(searchParam.getFromTerminal()).toUpperCase());
		inParameters.put("p_i_v_to_loc_type", RutDatabase.stringToDb(IjsHelper.getLocationCode(searchParam.getToLocType())));
		inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(searchParam.getToLocation()).toUpperCase());
		inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(searchParam.getToTerminal()).toUpperCase());
		inParameters.put("p_i_v_from_dt", RutDatabase.stringToDb(searchParam.getDateRange() != null ? searchParam.getDateRange().substring(0,searchParam.getDateRange().indexOf("-")) : ""));
		inParameters.put("p_i_v_to_dt", RutDatabase.stringToDb(searchParam.getDateRange() != null ? searchParam.getDateRange().substring(searchParam.getDateRange().indexOf("-")+1,(searchParam.getDateRange().length())): ""));
		inParameters.put("p_i_v_bl_or_book_typ", RutDatabase.stringToDb(searchParam.getBookingOrBlType()));
		inParameters.put("p_i_v_bl_or_book_val", RutDatabase.stringToDb(searchParam.getBookingOrBlValue()));
		inParameters.put("p_i_v_rout_val", RutDatabase.stringToDb(("Routing").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue() : ""));
		inParameters.put("p_i_v_contract_val", RutDatabase.stringToDb(("Contract").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue() : ""));
		inParameters.put("p_i_v_container_val", RutDatabase.stringToDb(("Container").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue() : ""));
		inParameters.put("p_i_v_jo_cost_val_from", RutDatabase.stringToDb(("JOCostFrom").equalsIgnoreCase(searchParam.getJoCostTyp()) ? searchParam.getJoCostValue() : ""));
		inParameters.put("p_i_v_jo_cost_val_to", RutDatabase.stringToDb(("JOCostTo").equalsIgnoreCase(searchParam.getJoCostTyp()) ? searchParam.getJoCostValue() : ""));
		inParameters.put("p_i_v_service_val", RutDatabase.stringToDb(searchParam.getServiceVal()));
		inParameters.put("p_i_v_vessel_val", RutDatabase.stringToDb(searchParam.getVesselVal()));
		inParameters.put("p_i_v_voyage_val", RutDatabase.stringToDb(searchParam.getVoyageVal()));
		inParameters.put("p_i_v_leg_typ", RutDatabase.stringToDb(searchParam.getLegType()));
		inParameters.put("p_i_v_payment_fsc", RutDatabase.stringToDb(searchParam.getPaymentFSC()));
		inParameters.put("p_i_v_soc_coc", RutDatabase.stringToDb(searchParam.getSocOrCoc()));
		inParameters.put("p_i_v_component_type", RutDatabase.stringToDb(searchParam.getComponentType()).toUpperCase());
		inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
		
		outMap = execute(inParameters);
		resultMap.put(ERROR_CD, (String)outMap.get("p_o_v_error_cd"));
		resultMap.put(DOWNLOAD_LIMIT, ((Integer)outMap.get("p_o_v_download_limit")).toString());
		resultMap.put(DOWNLOAD_CONTAINER, ((Integer)outMap.get("p_o_v_download_count")).toString());
		
		return resultMap;
		}
        public List<IjsMaintainJoDownloadDTO> getIjsJOMaintenanceSearchDownloadList(String userId, 
                                                                         IjsMaintainJOSearchParamVO searchParam) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();

             inParameters.put("p_i_v_job_ord_typ", RutDatabase.stringToDb(searchParam.getJobOrdTyp()));
             inParameters.put("p_i_v_job_ord_sts", RutDatabase.stringToDb(searchParam.getJobOrdSts()));
             inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(searchParam.getJobOrdNo()));
             inParameters.put("p_i_v_vendor_cd", RutDatabase.stringToDb(searchParam.getVendorCode()));
             inParameters.put("p_i_v_country_cd", RutDatabase.stringToDb(searchParam.getCountryCode()));
             inParameters.put("p_i_v_from_loc_type", RutDatabase.stringToDb(IjsHelper.getLocationCode(searchParam.getFromLocType())));
             inParameters.put("p_i_v_from_loc", RutDatabase.stringToDb(searchParam.getFromLocation()).toUpperCase());
             inParameters.put("p_i_v_from_terminal", RutDatabase.stringToDb(searchParam.getFromTerminal()).toUpperCase());
             inParameters.put("p_i_v_to_loc_type", RutDatabase.stringToDb(IjsHelper.getLocationCode(searchParam.getToLocType())));
             inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(searchParam.getToLocation()).toUpperCase());
             inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(searchParam.getToTerminal()).toUpperCase());
             inParameters.put("p_i_v_from_dt", RutDatabase.stringToDb(searchParam.getDateRange() != null ? searchParam.getDateRange().substring(0,searchParam.getDateRange().indexOf("-")) : ""));
             inParameters.put("p_i_v_to_dt", RutDatabase.stringToDb(searchParam.getDateRange() != null ? searchParam.getDateRange().substring(searchParam.getDateRange().indexOf("-")+1,(searchParam.getDateRange().length())): ""));
             inParameters.put("p_i_v_bl_or_book_typ", RutDatabase.stringToDb(searchParam.getBookingOrBlType()));
             inParameters.put("p_i_v_bl_or_book_val", RutDatabase.stringToDb(searchParam.getBookingOrBlValue()));
             inParameters.put("p_i_v_rout_val", RutDatabase.stringToDb(("Routing").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue() : ""));
             inParameters.put("p_i_v_contract_val", RutDatabase.stringToDb(("Contract").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue() : ""));
             inParameters.put("p_i_v_container_val", RutDatabase.stringToDb(("Container").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue() : ""));
             inParameters.put("p_i_v_jo_cost_val_from", RutDatabase.stringToDb(("JOCostFrom").equalsIgnoreCase(searchParam.getJoCostTyp()) ? searchParam.getJoCostValue() : ""));
             inParameters.put("p_i_v_jo_cost_val_to", RutDatabase.stringToDb(("JOCostTo").equalsIgnoreCase(searchParam.getJoCostTyp()) ? searchParam.getJoCostValue() : ""));
             inParameters.put("p_i_v_service_val", RutDatabase.stringToDb(searchParam.getServiceVal()));
             inParameters.put("p_i_v_vessel_val", RutDatabase.stringToDb(searchParam.getVesselVal()));
             inParameters.put("p_i_v_voyage_val", RutDatabase.stringToDb(searchParam.getVoyageVal()));
             inParameters.put("p_i_v_leg_typ", RutDatabase.stringToDb(searchParam.getLegType()));
             inParameters.put("p_i_v_payment_fsc", RutDatabase.stringToDb(searchParam.getPaymentFSC()));
             inParameters.put("p_i_v_soc_coc", RutDatabase.stringToDb(searchParam.getSocOrCoc()));
             inParameters.put("p_i_v_component_type", RutDatabase.stringToDb(searchParam.getComponentType()).toUpperCase());
             inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());

             outMap = execute(inParameters);
             return (List<IjsMaintainJoDownloadDTO>)outMap.get("p_o_v_ijs_mapping_list");
        }
    }

    protected class GetIjsSearchStoredProcedureContDtl extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_MAINTAIN_JO_SEARCH = 
            "PCR_IJS_MAINTAIN_JO_ALL.MAINTAIN_JO_SEARCH_CONT_DTL";

        GetIjsSearchStoredProcedureContDtl(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, SQL_MAINTAIN_JO_SEARCH);
            
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_io_v_err_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, 
                        rowMapper));
            
            compile();

        }

        public List<IjsMaintainJOSearchContDetailDTO> getIjsContractSearchListContDtl(String userId, 
                                                                         IjsMaintainJOSearchParamVO searchParam,String joNumber) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(joNumber)); //pass jo number from job order
            outMap = execute(inParameters);
            List<IjsMaintainJOSearchContDetailDTO> lstContDetailDto = (List<IjsMaintainJOSearchContDetailDTO>)outMap.get("p_o_v_ijs_mapping_list");
            return lstContDetailDto;
        }
    }
    
    protected class GetIjsDownloadStoredProcedureContDtl extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_MAINTAIN_JO_SEARCH = 
            "PCR_IJS_MAINTAIN_JO_ALL.MAINTAIN_JO_CONT_DTL_DOWNLOAD";

        GetIjsDownloadStoredProcedureContDtl(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, SQL_MAINTAIN_JO_SEARCH);
            
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_sts", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vendor_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_country_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_loc_type", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_loc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc_type", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_from_dt", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_dt", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bl_or_book_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bl_or_book_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_rout_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_contract_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_container_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_cost_val_from", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_cost_val_to", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_service_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vessel_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_voyage_val", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_leg_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_payment_fsc", Types.VARCHAR, 
                        rowMapper));
                        
           declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                       rowMapper));
            declareParameter(new SqlOutParameter("p_io_v_err_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, 
                        rowMapper));
            compile();

        }

        public List<IjsMaintainJOSearchContDetailDTO> getIjsDownloadListContDtl(String userId, 
                                                                         IjsMaintainJOSearchParamVO searchParam,String joNumber) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();

           inParameters.put("p_i_v_job_ord_typ", RutDatabase.stringToDb(IjsHelper.getJOType(searchParam.getJobOrdTyp())));
            inParameters.put("p_i_v_job_ord_sts", RutDatabase.stringToDb(searchParam.getJobOrdSts()));
            inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(joNumber)); //pass jo number from job order
            inParameters.put("p_i_v_vendor_cd", RutDatabase.stringToDb(searchParam.getVendorCode()));
            inParameters.put("p_i_v_country_cd", RutDatabase.stringToDb(searchParam.getCountryCode()));
            inParameters.put("p_i_v_from_loc_type", RutDatabase.stringToDb(IjsHelper.getLocationType(searchParam.getFromLocType())));
            inParameters.put("p_i_v_from_loc", RutDatabase.stringToDb(searchParam.getFromLocation()));
            inParameters.put("p_i_v_to_loc_type", RutDatabase.stringToDb(IjsHelper.getLocationType(searchParam.getToLocType())));
            inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(searchParam.getToLocation()));
            inParameters.put("p_i_v_from_dt", RutDatabase.stringToDb(searchParam.getDateRange() != null ? searchParam.getDateRange().substring(0,searchParam.getDateRange().indexOf("-")) : ""));
            inParameters.put("p_i_v_to_dt", RutDatabase.stringToDb(searchParam.getDateRange() != null ? searchParam.getDateRange().substring(searchParam.getDateRange().indexOf("-")+1,(searchParam.getDateRange().length())): ""));
            inParameters.put("p_i_v_bl_or_book_typ", RutDatabase.stringToDb(searchParam.getBookingOrBlType()));
            inParameters.put("p_i_v_bl_or_book_val", RutDatabase.stringToDb(searchParam.getBookingOrBlValue()));
            inParameters.put("p_i_v_rout_val", RutDatabase.stringToDb(("Routing").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue() : ""));
            inParameters.put("p_i_v_contract_val", RutDatabase.stringToDb(("Contract").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue() : ""));
            inParameters.put("p_i_v_container_val", RutDatabase.stringToDb(("Container").equalsIgnoreCase(searchParam.getRoutContractOrContType()) ? searchParam.getRoutContractOrContValue() : ""));
            inParameters.put("p_i_v_jo_cost_val_from", RutDatabase.stringToDb(("costFrom").equalsIgnoreCase(searchParam.getJoCostTyp()) ? searchParam.getJoCostValue() : ""));
            inParameters.put("p_i_v_jo_cost_val_to", RutDatabase.stringToDb(("costTo").equalsIgnoreCase(searchParam.getJoCostTyp() ) ? searchParam.getJoCostValue() : ""));
            inParameters.put("p_i_v_service_val", RutDatabase.stringToDb(searchParam.getServiceVal()));
            inParameters.put("p_i_v_vessel_val", RutDatabase.stringToDb(searchParam.getVesselVal()));
            inParameters.put("p_i_v_voyage_val", RutDatabase.stringToDb(searchParam.getVoyageVal()));
            inParameters.put("p_i_v_leg_typ", RutDatabase.stringToDb(searchParam.getLegType()));
            inParameters.put("p_i_v_payment_fsc", RutDatabase.stringToDb(searchParam.getPaymentFSC()));

            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());

            outMap = execute(inParameters);
            return (List<IjsMaintainJOSearchContDetailDTO>)outMap.get("p_o_v_ijs_mapping_list");
        }
    }
    
    private class IjsMaintainJOSearchRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsMaintainJOSearchDTO ijsMaintainJOSearchDTO = 
                new IjsMaintainJOSearchDTO();
            IjsMaintainJOSearchContDetailDTO contDataObj =new IjsMaintainJOSearchContDetailDTO();
            List<IjsMaintainJOSearchContDetailDTO> lstContData = new ArrayList<IjsMaintainJOSearchContDetailDTO>();
            try {
                ijsMaintainJOSearchDTO.setJoNumber(resultSet.getString("jo_number"));
                ijsMaintainJOSearchDTO.setOrderDate(resultSet.getString("order_date"));
                ijsMaintainJOSearchDTO.setApproveDate(resultSet.getString("approve_date"));
                ijsMaintainJOSearchDTO.setCreatedBy(resultSet.getString("created_by"));
                ijsMaintainJOSearchDTO.setVendorID(resultSet.getString("vendor_id"));
                ijsMaintainJOSearchDTO.setJobOrdType(resultSet.getString("job_ord_type"));
                ijsMaintainJOSearchDTO.setVendorName(resultSet.getString("vendor_name"));
                ijsMaintainJOSearchDTO.setDetailType(IjsHelper.getJOType(resultSet.getString("jo_type")));
                ijsMaintainJOSearchDTO.setDetailVersion(resultSet.getString("jo_version_num"));
                ijsMaintainJOSearchDTO.setFromLoaction(resultSet.getString("from_location"));
                ijsMaintainJOSearchDTO.setToLocation(resultSet.getString("to_location"));
                ijsMaintainJOSearchDTO.setFromTerminal(resultSet.getString("from_terminal"));
                ijsMaintainJOSearchDTO.setToTerminal(resultSet.getString("to_terminal"));
                ijsMaintainJOSearchDTO.setFromLocType(IjsHelper.getLocationType(resultSet.getString("from_loc_type")));
                ijsMaintainJOSearchDTO.setToLocType(IjsHelper.getLocationType(resultSet.getString("to_loc_type")));
                ijsMaintainJOSearchDTO.setTransMode(IjsHelper.getTransMode(resultSet.getString("trans_mode")));
                ijsMaintainJOSearchDTO.setStartDate(resultSet.getString("jo_start_date"));
                ijsMaintainJOSearchDTO.setCompleteDate(resultSet.getString("jo_complete_date"));
                ijsMaintainJOSearchDTO.setAmount(Double.parseDouble(resultSet.getString("jo_cost")));
                ijsMaintainJOSearchDTO.setAmountUSD(Double.parseDouble(resultSet.getString("jo_cost_usd")));
                ijsMaintainJOSearchDTO.setCurrency(resultSet.getString("currency"));
                ijsMaintainJOSearchDTO.setRoutingId(resultSet.getString("routing_id"));
                ijsMaintainJOSearchDTO.setContractId(resultSet.getString("contract_no"));//
                ijsMaintainJOSearchDTO.setStatus(resultSet.getString("job_order_status"));
                ijsMaintainJOSearchDTO.setFSC(resultSet.getString("fsc"));
                ijsMaintainJOSearchDTO.setReasonCode(resultSet.getString("reason_code"));
                ijsMaintainJOSearchDTO.setAdhoc_yn(resultSet.getString("adhoc_yn"));
                ijsMaintainJOSearchDTO.setBk_bl_ad(resultSet.getString("bk_bl_ad"));
                ijsMaintainJOSearchDTO.setSOCorCOC(resultSet.getString("soc_coc"));
                ijsMaintainJOSearchDTO.setPriority(resultSet.getString("priority"));
                
                String usertyp;
                if(resultSet.getString("user_type").equalsIgnoreCase("HQ")) {
                    usertyp="HQ";
                } else if(resultSet.getString("user_type").equalsIgnoreCase("LU")) {
                    usertyp="LOC";
                } else {
                    usertyp="OTH";
                }
                ijsMaintainJOSearchDTO.setUser_type(usertyp);

            } catch (SQLException e) {
                
                e.printStackTrace();
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

            return ijsMaintainJOSearchDTO;
        }
    }
    
    private class IjsMaintainJODownloadRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsMaintainJoDownloadDTO ijsMaintainJODownloadDTO = 
                new IjsMaintainJoDownloadDTO();
            IjsMaintainJOSearchContDetailDTO contDataObj =new IjsMaintainJOSearchContDetailDTO();
            List<IjsMaintainJOSearchContDetailDTO> lstContData = new ArrayList<IjsMaintainJOSearchContDetailDTO>();
            try {
                ijsMaintainJODownloadDTO.setJoNumber(resultSet.getString("jo_number"));
                ijsMaintainJODownloadDTO.setActivity_dt(resultSet.getString("activity_date"));
                ijsMaintainJODownloadDTO.setBkgOrBLNo(resultSet.getString("bkg_bl"));
                ijsMaintainJODownloadDTO.setEqNumber(resultSet.getString("cont_no"));
                ijsMaintainJODownloadDTO.setEquiptState(resultSet.getString("container_status"));
                
                ijsMaintainJODownloadDTO.setPurchase_term(resultSet.getString("purchase_term"));
                ijsMaintainJODownloadDTO.setVendorID(resultSet.getString("vendor_id"));
                ijsMaintainJODownloadDTO.setContract_id(resultSet.getString("contract_no"));
                ijsMaintainJODownloadDTO.setPriority(resultSet.getString("priority"));
                ijsMaintainJODownloadDTO.setVendorName(resultSet.getString("vendor_name"));
                ijsMaintainJODownloadDTO.setToLocation(resultSet.getString("to_location"));
                ijsMaintainJODownloadDTO.setFromLoaction(resultSet.getString("from_location"));
                
                ijsMaintainJODownloadDTO.setFromTerminal(resultSet.getString("from_terminal"));
                ijsMaintainJODownloadDTO.setToTerminal(resultSet.getString("to_terminal"));
                ijsMaintainJODownloadDTO.setFromLocType(resultSet.getString("from_loc_type"));
                ijsMaintainJODownloadDTO.setToLocType(resultSet.getString("to_loc_type"));
                
                ijsMaintainJODownloadDTO.setSOCorCOC((resultSet.getString("soc_coc")));
                ijsMaintainJODownloadDTO.setContSize(resultSet.getString("eq_size")!=null ? Integer.parseInt(resultSet.getString("eq_size")) : 0);
                ijsMaintainJODownloadDTO.setContType(resultSet.getString("eq_type"));
                ijsMaintainJODownloadDTO.setSpecial_handling(resultSet.getString("special_handling"));
                
                 ijsMaintainJODownloadDTO.setContEmptyOrLaden(resultSet.getString("laden_empty"));
                ijsMaintainJODownloadDTO.setCurrency(resultSet.getString("currency"));
                ijsMaintainJODownloadDTO.setAmount(resultSet.getString("TOTAL_COST"));
                ijsMaintainJODownloadDTO.setAmountUSD(resultSet.getString("TOTAL_COST_USD"));
                
                ijsMaintainJODownloadDTO.setStatus(resultSet.getString("job_order_status"));
                ijsMaintainJODownloadDTO.setJobOrdType(resultSet.getString("job_ord_type"));
                ijsMaintainJODownloadDTO.setTransMode(resultSet.getString("mode_of_transport"));
                ijsMaintainJODownloadDTO.setAfs_v(resultSet.getString("afs_v"));
                
                ijsMaintainJODownloadDTO.setAfs_voy_num(resultSet.getString("afs_v_num"));
                ijsMaintainJODownloadDTO.setIssue_dt(resultSet.getString("issued_date"));
                ijsMaintainJODownloadDTO.setStartDate(resultSet.getString("start_date"));
                ijsMaintainJODownloadDTO.setCompleteDate(resultSet.getString("completed_date"));
                
                ijsMaintainJODownloadDTO.setCreate_dt(resultSet.getString("Create_date"));
                ijsMaintainJODownloadDTO.setCreatedBy(resultSet.getString("Create_User_ID"));
                ijsMaintainJODownloadDTO.setApproval_dt(resultSet.getString("Create_Approval_Date"));
                ijsMaintainJODownloadDTO.setApproval_id(resultSet.getString("Create_Approval_ID"));
                ijsMaintainJODownloadDTO.setSizeTypeAmt(resultSet.getString("size_type_rate"));
                ijsMaintainJODownloadDTO.setSizeTypeUSDAmt(resultSet.getString("size_type_usd_rate"));
                ijsMaintainJODownloadDTO.setBkgBlAmt(resultSet.getString("BKGBL_jo_rate"));
                ijsMaintainJODownloadDTO.setBkgBlUSDAmt(resultSet.getString("BKGBL_USD_rate"));
                ijsMaintainJODownloadDTO.setLumpsumAmt(resultSet.getString("lumpsum_jo_rate"));
                ijsMaintainJODownloadDTO.setLumpsumUSDAmt(resultSet.getString("lumpsum_USD_rate"));
                
            } catch (SQLException e) {
                
                e.printStackTrace();
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

            return ijsMaintainJODownloadDTO;
        }
    }
    
    private class IjsMaintainJODownloadRowMapperContDtl implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsMaintainJOSearchContDetailDTO contDataObj =new IjsMaintainJOSearchContDetailDTO();
            try {
                        contDataObj.setEqNumber(resultSet.getString("container_number"));
                        contDataObj.setEquiptState(resultSet.getString("container_status"));
                        contDataObj.setBkgOrBLNo(resultSet.getString("bkg_bl"));
                        contDataObj.setContSize(resultSet.getInt("container_size"));
                        contDataObj.setContPercent(resultSet.getString("container_size"));
                        contDataObj.setContType(resultSet.getString("container_type"));
                        contDataObj.setContEmptyOrLaden(resultSet.getString("cont_empty_or_laden"));
                        contDataObj.setSOCorCOC(resultSet.getString("soc_coc"));
                        contDataObj.setStartDate(resultSet.getString("start_date"));
                        contDataObj.setEndDate(resultSet.getString("end_date"));
                        contDataObj.setDGorRForOG(resultSet.getString("special_handling"));
                        contDataObj.setPortClass(resultSet.getString("port_class"));
                        contDataObj.setImdgClass(resultSet.getString("imdg_class"));
                        contDataObj.setVariant(resultSet.getString("variant"));
                        contDataObj.setCurrency(resultSet.getString("currency"));
                        contDataObj.setAmount(resultSet.getString("rate_per_container"));
                        contDataObj.setAmountUSD(resultSet.getString("usd_rate_per_container"));
                        contDataObj.setSpecial_handling(resultSet.getString("special_handling"));
    
             } catch (SQLException e) {
                
                e.printStackTrace();
            } 

            return contDataObj;
        }
    }
    
    private class IjsMaintainJOSearchRowMapperContDtl implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsMaintainJOSearchContDetailDTO contDataObj =new IjsMaintainJOSearchContDetailDTO();
            
            try {
            	String spHandling=resultSet.getString("special_handling");
                    contDataObj.setEqNumber(resultSet.getString("container_number"));
                    contDataObj.setEquiptState(resultSet.getString("container_status"));
                    contDataObj.setBkgOrBLNo(resultSet.getString("bkg_bl"));
                    contDataObj.setContSize(resultSet.getInt("container_size"));
                    contDataObj.setContPercent(resultSet.getString("container_size"));
                    contDataObj.setContWeight(resultSet.getString("cntr_weight"));
                    contDataObj.setContType(resultSet.getString("container_type"));
                    contDataObj.setContEmptyOrLaden(resultSet.getString("cont_empty_or_laden"));
                     String socOrCoc = resultSet.getString("soc_coc");
                     if("S".equalsIgnoreCase(socOrCoc)) {
                         socOrCoc="SOC";
                     } else if("C".equalsIgnoreCase(socOrCoc)) {
                         socOrCoc="COC";
                     }
                    contDataObj.setSOCorCOC(socOrCoc);
                    contDataObj.setStartDate(resultSet.getString("start_date") != null ? resultSet.getString("start_date") : "");
                    contDataObj.setEndDate(resultSet.getString("end_date") != null ? resultSet.getString("end_date") : "");
                    contDataObj.setCurrency(resultSet.getString("currency"));
                    contDataObj.setAmount(resultSet.getString("size_type_rate"));
                    contDataObj.setAmountUSD(resultSet.getString("size_type_usd_rate"));
                    contDataObj.setAmountBkgBl(resultSet.getString("BKGBL_jo_rate"));
                    contDataObj.setAmountBkgBlUSD(resultSet.getString("BKGBL_USD_rate"));
                    contDataObj.setAmountLumpsum(resultSet.getString("lumpsum_jo_rate"));
                    contDataObj.setAmountLumpsumUSD(resultSet.getString("lumpsum_USD_rate"));
                    contDataObj.setDGorRForOG("Normal".equals(spHandling)?"":spHandling);
                    contDataObj.setPortClass(resultSet.getString("port_class"));
                    contDataObj.setImdgClass(resultSet.getString("imdg_class"));
                    contDataObj.setUnno(resultSet.getString("unno"));
                    contDataObj.setOlf(resultSet.getString("olf"));
                    contDataObj.setOh(resultSet.getString("oh"));
                    contDataObj.setOwr(resultSet.getString("owr"));
                    contDataObj.setOwl(resultSet.getString("owl"));
                    contDataObj.setFlashPoint(resultSet.getString("flash_point"));
                    contDataObj.setTempFrom(resultSet.getString("temp_from"));
                    contDataObj.setTempTo(resultSet.getString("temp_to"));
                    contDataObj.setVariant(resultSet.getString("variant"));
                    contDataObj.setOla(resultSet.getString("ola"));
                    contDataObj.setSpecial_handling(spHandling);
    
             } catch (SQLException e) {
                e.printStackTrace();
            } 

            return contDataObj;
        }
    }
    //##01 END
    //##03 BEGIN

    protected class IjsCompleteJOStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_CALL_IJS_COMPLETE = 
            "PCR_IJS_MAINTAIN_JO_ALL.PRR_IJS_COMPLETE";

        IjsCompleteJOStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_CALL_IJS_COMPLETE);
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            compile();

        }

        private String completeJO(List<IjsMaintainJOSearchVO> searchVo,String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            
            for (int i = 0; i < searchVo.size() ; i++) {
                
                inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(searchVo.get(i).getJoNumber()));
                
                //on the basis of action param perform action
                
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
    
                outMap = execute(inParameters);
            }
            return (String)outMap.get("p_o_v_err_cd") == null ? "MSG" : (String)outMap.get("p_o_v_err_cd");
        }
    }
    
    protected class IjsApproveCancelRejStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_IJS_CNTR_SAVE = 
            "PCR_IJS_MAINTAIN_JO_ALL.PRR_IJS_APP_CAN_COMP_REJ_JO";

        IjsApproveCancelRejStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_IJS_CNTR_SAVE);
            
            declareParameter(new SqlInOutParameter("p_i_v_reason_code", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_action", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_user_type", Types.VARCHAR));
            compile();

        }

        private String approveRejCancelJO(List<IjsMaintainJOSearchVO> searchVo,String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            
            for (int i = 0; i < searchVo.size() ; i++) {
                
                inParameters.put("p_i_v_reason_code", RutDatabase.stringToDb(searchVo.get(i).getReasonCode()));
                inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(searchVo.get(i).getJoNumber()));
                
                //on the basis of action param perform action
                inParameters.put("p_i_v_action", RutDatabase.stringToDb(action).toUpperCase());
                
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
    
                outMap = execute(inParameters);
            }
            
            return (String)outMap.get("p_o_v_err_cd") == null ? "MSG" : (String)outMap.get("p_o_v_err_cd");
        }
    }
    
    protected class IjsRemoveEqJOStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_IJS_CNTR_SAVE = 
            "PCR_IJS_MAINTAIN_JO_ALL.PRR_IJS_REMOVE_CNTR";

        IjsRemoveEqJOStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_IJS_CNTR_SAVE);
            
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cont_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_action", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_user_type", Types.VARCHAR));
            compile();

        }

        private String removeEq(List<IjsMaintainJOSearchVO> searchVo,String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            
            for (int i = 0; i < searchVo.size() ; i++) {
                
                inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(searchVo.get(i).getJoNumber()));
                inParameters.put("p_i_v_cont_no", RutDatabase.stringToDb(searchVo.get(i).getContNoToDelete()));//TODO
                
                //on the basis of action param perform action
                inParameters.put("p_i_v_action", RutDatabase.stringToDb(action).toUpperCase());
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
                    
                outMap = execute(inParameters);
            }
            
            return (String)outMap.get("p_o_v_err_cd") == null ? "MSG" : (String)outMap.get("p_o_v_err_cd");
        }
    }
    
    
    protected class IjsReplaceEqJOStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_IJS_CNTR_SAVE = 
            "PCR_IJS_MAINTAIN_JO_ALL.PRR_IJS_REPLACE_CNTR";

        IjsReplaceEqJOStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_IJS_CNTR_SAVE);
            
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cont_no_old", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cont_no_new", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cont_size", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cont_type", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_adhoc_empty_laden", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_action", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_user_type", Types.VARCHAR));
            compile();

        }

        private String replaceEq(List<IjsMaintainJOSearchVO> searchVo,String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            
            for (int i = 0; i < searchVo.size() ; i++) {
                
                inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(searchVo.get(i).getJoNumber()));
                inParameters.put("p_i_v_cont_no_old", RutDatabase.stringToDb(searchVo.get(i).getOldContNoReplace()));
                inParameters.put("p_i_v_cont_no_new", RutDatabase.stringToDb(searchVo.get(i).getNewContNoReplace()));
                 inParameters.put("p_i_v_cont_size", RutDatabase.stringToDb(searchVo.get(i).getContSize() != null ? searchVo.get(i).getContSize().toString() : ""));//TODO
                  inParameters.put("p_i_v_cont_type", RutDatabase.stringToDb(searchVo.get(i).getContType()));
                inParameters.put("p_i_v_adhoc_empty_laden", RutDatabase.stringToDb(searchVo.get(i).getContEmptyOrLaden()));
                
                //on the basis of action param perform action
                inParameters.put("p_i_v_action", RutDatabase.stringToDb(action).toUpperCase());
                
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
    
                outMap = execute(inParameters);
            }
            
            return (String)outMap.get("p_o_v_err_cd") == null ? "MSG" : (String)outMap.get("p_o_v_err_cd");
        }
    }
    
    
    //##03 END
    
    protected class IjsChangeVendorStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_IJS_CNTR_SAVE = 
            "PCR_IJS_MAINTAIN_JO_ALL.PRR_IJS_CHANGE_VENDOR";

        IjsChangeVendorStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_IJS_CNTR_SAVE);
            
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_booking_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_routing_id", Types.VARCHAR, 
                        rowMapper));//p_i_v_contract_id
             declareParameter(new SqlInOutParameter("p_i_v_contract_id", Types.VARCHAR, 
                         rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vendor_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_trans_mode", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_action", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_user_type", Types.VARCHAR));
            compile();

        }

        private String changeVendor(List<IjsMaintainJOSearchVO> searchVo,String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            
            for (int i = 0; i < searchVo.size() ; i++) {
                inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(searchVo.get(i).getJoNumber()));
                inParameters.put("p_i_v_booking_typ", RutDatabase.stringToDb(searchVo.get(i).getBk_bl_ad()));
                inParameters.put("p_i_v_routing_id", RutDatabase.stringToDb(searchVo.get(i).getRoutingId()));
                inParameters.put("p_i_v_contract_id", RutDatabase.stringToDb(searchVo.get(i).getContractId()));
                inParameters.put("p_i_v_vendor_cd", RutDatabase.stringToDb(searchVo.get(i).getVendorID()));
                inParameters.put("p_i_v_trans_mode", RutDatabase.stringToDb(IjsHelper.getTransCodeX(searchVo.get(i).getTransMode())));
                
                //on the basis of action param perform action
                inParameters.put("p_i_v_action", RutDatabase.stringToDb(action).toUpperCase());
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
                outMap = execute(inParameters);
            }
            
            return (String)outMap.get("p_o_v_err_cd") == null ? "MSG" : (String)outMap.get("p_o_v_err_cd");
        }
    }
    
    protected class IjsCostCalcStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_IJS_CNTR_SAVE = 
            "PCR_IJS_MAIN_COST_CALC.PRR_IJS_JO_COST_CALC";

        IjsCostCalcStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_IJS_CNTR_SAVE);
            declareParameter(new SqlInOutParameter("p_i_v_bkg_bl_ad_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_routing_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_soc_coc", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_date", Types.VARCHAR, 
                        rowMapper));
           
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_session_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_failed_bkg_bl", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_job_orders", Types.VARCHAR));
            
            compile();

        }
       
        private String costCalculation(List<IjsMaintainJOSearchVO> searchVo,String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            String errStr = "";
            
            for (int i = 0; i < searchVo.size() ; i++) {
            	String lSocCoc=RutDatabase.stringToDb(searchVo.get(i).getSOCorCOC());
                if(lSocCoc!=null){
                	if(lSocCoc.equalsIgnoreCase("COC")){
                		lSocCoc="C";
                	}else{
                		lSocCoc="S";
                	}
                }
                inParameters.put("p_i_v_bkg_bl_ad_typ", RutDatabase.stringToDb(searchVo.get(i).getBk_bl_ad()));
                inParameters.put("p_i_v_jo_typ", RutDatabase.stringToDb(IjsHelper.getJOCode(searchVo.get(i).getJobOrdType())).toUpperCase());
                inParameters.put("p_i_v_jo_routing_no", RutDatabase.stringToDb(searchVo.get(i).getRoutingId()));
                inParameters.put("p_i_v_jo_soc_coc", RutDatabase.stringToDb(lSocCoc));
                inParameters.put("p_i_v_jo_no", RutDatabase.stringToDb(searchVo.get(i).getJoNumber()));
                inParameters.put("p_i_v_jo_date", RutDatabase.stringToDb(searchVo.get(i).getOrderDate()));
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
                inParameters.put("p_i_v_session_id", RutDatabase.stringToDb(""));
                outMap = execute(inParameters);
                
                errStr = (String)outMap.get("p_o_v_err_cd");
            }
            
            if(errStr == null || errStr.equals(""))
            {
                errStr = "MSG";
                System.out.println("......Pass Case......"+errStr);
            }
            
            return errStr;
        }
    }
    
    
    protected class IjsSaveFSCStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_IJS_CNTR_SAVE = 
            "PCR_IJS_MAINTAIN_JO_ALL.PRR_IJS_SAVE_FSC";

        IjsSaveFSCStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_IJS_CNTR_SAVE);
            
            declareParameter(new SqlInOutParameter("p_i_v_job_ord_no", Types.VARCHAR, 
                        rowMapper));
                declareParameter(new SqlInOutParameter("p_i_v_fsc", Types.VARCHAR,
                            rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_action", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));//
             declareParameter(new SqlOutParameter("p_o_v_user_type", Types.VARCHAR));
            compile();

        }

        private String saveFSC(List<IjsMaintainJOSearchVO> searchVo,String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            for (int i = 0; i < searchVo.size() ; i++) {
                inParameters.put("p_i_v_job_ord_no", RutDatabase.stringToDb(searchVo.get(i).getJoNumber()));
                inParameters.put("p_i_v_fsc", RutDatabase.stringToDb(searchVo.get(i).getFsc().toUpperCase()));
                //on the basis of action param perform action
                inParameters.put("p_i_v_action", RutDatabase.stringToDb(action).toUpperCase());
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
                outMap = execute(inParameters);
            }
            return (String)outMap.get("p_o_v_err_cd") == null ? "MSG" : (String)outMap.get("p_o_v_err_cd");
        }
    }
    

     
    
    private class IjsCostContractDownloadRowMapper  implements RowMapper{

         public Object mapRow(ResultSet resultSet, int row) {
             IjsContractDownloadDTO ijsContractDownloadDto = new IjsContractDownloadDTO();
             try {
                 ijsContractDownloadDto.setVendorCode(resultSet.getString("VENDOR_CODE"));
                 ijsContractDownloadDto.setVendorName(resultSet.getString("VENDOR_NAME"));
                 ijsContractDownloadDto.setContractId(resultSet.getString("CONTRACT_NO"));
                 ijsContractDownloadDto.setRoutingId(resultSet.getInt("ROUTING_NO"));
                 ijsContractDownloadDto.setContractStartDate(resultSet.getString("START_DATE"));
                 ijsContractDownloadDto.setContractEndDate(resultSet.getString("END_DATE"));
                 ijsContractDownloadDto.setTransMode(resultSet.getString("MOT"));
                 ijsContractDownloadDto.setStatus(resultSet.getString("ROUTE_RATE_STATUS"));
                 ijsContractDownloadDto.setPaymentFsc(resultSet.getString("PAYMENT_FSC"));
                 ijsContractDownloadDto.setContractCurrency(resultSet.getString("CURRENCY"));
                 ijsContractDownloadDto.setPriority(resultSet.getInt("PRIORITY"));
                 ijsContractDownloadDto.setFromLocType(resultSet.getString("FROM_MODE"));
                 ijsContractDownloadDto.setFromLocation(resultSet.getString("FROM_LOCATION"));
                 ijsContractDownloadDto.setFromTerminal(resultSet.getString("FROM_TERMINAL"));
                 ijsContractDownloadDto.setToLocType(resultSet.getString("TO_MODE"));
                 ijsContractDownloadDto.setToLocation(resultSet.getString("TO_LOCATION"));
                 ijsContractDownloadDto.setToTerminal(resultSet.getString("TO_TERMINAL"));
                 ijsContractDownloadDto.setDays(resultSet.getInt("DAYS"));
                 ijsContractDownloadDto.setHours(resultSet.getInt("HOURS"));
                 ijsContractDownloadDto.setDistance(resultSet.getInt("DISTANCE"));
                 ijsContractDownloadDto.setContractUom(resultSet.getString("DISTANCE_UOM"));
                 ijsContractDownloadDto.setExempted(resultSet.getString("EXEMPTED"));
                 ijsContractDownloadDto.setCostRateSequenceNum(resultSet.getInt("COST_SEQ_NUM"));
                 ijsContractDownloadDto.setRateStartDate(resultSet.getString("COST_RATE_START_DATE"));
                 ijsContractDownloadDto.setRateEndDate(resultSet.getString("COST_RATE_END_DATE"));
                 ijsContractDownloadDto.setService(resultSet.getString("SERVICE"));
                 ijsContractDownloadDto.setRateBasis(resultSet.getString("RATE_BASIS"));
                 ijsContractDownloadDto.setEqCatq(resultSet.getString("EQUIP_CATG"));
                 ijsContractDownloadDto.setChargeCode(resultSet.getString("CHARGE_CODE"));
                 ijsContractDownloadDto.setCurrency(resultSet.getString("COST_RATE_CURRENCY"));
                 ijsContractDownloadDto.setTerm(resultSet.getString("TERM"));
                 ijsContractDownloadDto.setMtOrLaden(resultSet.getString("MT_LADEN"));
                 ijsContractDownloadDto.setRateStatus(resultSet.getString("COST_STATUS"));
                 ijsContractDownloadDto.setCalcMethod(resultSet.getString("CALCULATION_METHOD"));
                 ijsContractDownloadDto.setEqType(resultSet.getString("EQ_TYPE"));
                 ijsContractDownloadDto.setUpto(resultSet.getInt("UPTO"));
                 ijsContractDownloadDto.setUom(resultSet.getString("UNIT"));
                 ijsContractDownloadDto.setImpOrExp(resultSet.getString("IMP_OR_EXP"));
                 ijsContractDownloadDto.setSplHandling(resultSet.getString("SPL_HANDLING"));
                 ijsContractDownloadDto.setPortClassCode(resultSet.getString("PORT_CODE"));
                 ijsContractDownloadDto.setImdgDetails(resultSet.getString("IMDG_CODE"));
                 ijsContractDownloadDto.setOogSetup(resultSet.getString("OOG_CODE"));
                 ijsContractDownloadDto.setRate20(resultSet.getDouble("RATE20"));
                 ijsContractDownloadDto.setRate40(resultSet.getDouble("RATE40"));
                 ijsContractDownloadDto.setRate45(resultSet.getDouble("RATE45"));
                 ijsContractDownloadDto.setLumpSum(resultSet.getString("LUMP_SUM"));
                 ijsContractDownloadDto.setPerTrip(resultSet.getString("PER_TRIP"));
                  
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             return ijsContractDownloadDto;
         }
     }
    private class IjsBillContractDownloadRowMapper  implements RowMapper{

        public Object mapRow(ResultSet resultSet, int row) {
            IjsContractDownloadDTO ijsContractDownloadDto = new IjsContractDownloadDTO();
            try {
                ijsContractDownloadDto.setVendorCode(resultSet.getString("VENDOR_CODE"));
                ijsContractDownloadDto.setVendorName(resultSet.getString("VENDOR_NAME"));
                ijsContractDownloadDto.setContractId(resultSet.getString("CONTRACT_NO"));
                ijsContractDownloadDto.setRoutingId(resultSet.getInt("ROUTING_NO"));
                ijsContractDownloadDto.setContractStartDate(resultSet.getString("START_DATE"));
                ijsContractDownloadDto.setContractEndDate(resultSet.getString("END_DATE"));
                ijsContractDownloadDto.setTransMode(resultSet.getString("MOT"));
                ijsContractDownloadDto.setStatus(resultSet.getString("ROUTE_RATE_STATUS"));
                ijsContractDownloadDto.setPaymentFsc(resultSet.getString("PAYMENT_FSC"));
                ijsContractDownloadDto.setContractCurrency(resultSet.getString("CURRENCY"));
                ijsContractDownloadDto.setPriority(resultSet.getInt("PRIORITY"));
                ijsContractDownloadDto.setFromLocType(IjsHelper.getLocationType(resultSet.getString("FROM_MODE")));
                ijsContractDownloadDto.setFromLocation(resultSet.getString("FROM_LOCATION"));
                ijsContractDownloadDto.setFromTerminal(resultSet.getString("FROM_TERMINAL"));
                ijsContractDownloadDto.setToLocType(IjsHelper.getLocationType(resultSet.getString("TO_MODE")));
                ijsContractDownloadDto.setToLocation(resultSet.getString("TO_LOCATION"));
                ijsContractDownloadDto.setToTerminal(resultSet.getString("TO_TERMINAL"));
                ijsContractDownloadDto.setDays(resultSet.getInt("DAYS"));
                ijsContractDownloadDto.setHours(resultSet.getInt("HOURS"));
                ijsContractDownloadDto.setDistance(resultSet.getInt("DISTANCE"));
                ijsContractDownloadDto.setContractUom(resultSet.getString("DISTANCE_UOM"));
                ijsContractDownloadDto.setExempted(resultSet.getString("EXEMPTED"));
                ijsContractDownloadDto.setCostRateSequenceNum(resultSet.getInt("BILLING_SEQ_NUM"));
                ijsContractDownloadDto.setRateStartDate(resultSet.getString("BILLING_RATE_START_DATE"));
                ijsContractDownloadDto.setRateEndDate(resultSet.getString("BILLING_RATE_END_DATE"));
                ijsContractDownloadDto.setService(resultSet.getString("SERVICE"));
                ijsContractDownloadDto.setRateBasis(resultSet.getString("RATE_BASIS"));
                ijsContractDownloadDto.setEqCatq(resultSet.getString("EQUIP_CATG"));
                ijsContractDownloadDto.setChargeCode(resultSet.getString("CHARGE_CODE"));
                ijsContractDownloadDto.setCurrency(resultSet.getString("BILLING_RATE_CURRENCY"));
                ijsContractDownloadDto.setTerm(resultSet.getString("TERM"));
                ijsContractDownloadDto.setMtOrLaden(resultSet.getString("MT_LADEN"));
                ijsContractDownloadDto.setRateStatus(resultSet.getString("BILLING_STATUS"));
                ijsContractDownloadDto.setCalcMethod(resultSet.getString("CALCULATION_METHOD"));
                ijsContractDownloadDto.setEqType(resultSet.getString("EQ_TYPE"));
                ijsContractDownloadDto.setUpto(resultSet.getInt("UPTO"));
                ijsContractDownloadDto.setUom(resultSet.getString("UNIT"));
                ijsContractDownloadDto.setRate20(resultSet.getDouble("RATE20"));
                ijsContractDownloadDto.setRate40(resultSet.getDouble("RATE40"));
                ijsContractDownloadDto.setRate45(resultSet.getDouble("RATE45"));
               
                 
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ijsContractDownloadDto;
        }
    }
    @Override
	public String deleteLumpsum(List<String> joLumpsumCostIds, String userId,String componentType)
			throws IJSException {
		String costIds=null;
		String errorCode=null;
		for(String joLumpsum:joLumpsumCostIds){
			String jobOrder=null;
			int index=joLumpsum.indexOf(":");
			if(index<0){
				jobOrder=joLumpsum;
			}else{
				jobOrder=joLumpsum.substring(0,joLumpsum.indexOf(":"));
				costIds=joLumpsum.substring(joLumpsum.indexOf(":")+1);
			}
			
			errorCode= ijsDeleteLumpsumStoredProcedure.deleteLumpsum(jobOrder,costIds, userId,componentType);
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
        
        public String deleteLumpsum(String jobOrderNum,String joCostId, String userId, String componentType) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_jo_order_num", RutDatabase.stringToDb(jobOrderNum));
            inParameters.put("p_i_v_jo_cost_id", RutDatabase.stringToDb(joCostId));
            inParameters.put("p_i_v_component_type", RutDatabase.stringToDb(componentType));
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId));
            outMap = execute(inParameters);
            System.out.println((String)outMap.get("p_o_v_err_cd"));
            return (String)outMap.get("p_o_v_err_cd") == null ? "IJS_MSG_1021" : (String)outMap.get("p_o_v_err_cd");
        }
    }
	@Override
	public Map<String,String> findJODownloadLimit(String userId, IjsMaintainJOSearchParamVO searchParam) throws IJSException {
		return ijsMaintainDownloadCountStoredProcedure.getIjsJoMaintenanceDownloadCount(userId,searchParam);
//    	if(errorCode!=null && IjsErrorCode.DB_IJS_MAINT_JO_EX_20006.name().equals(errorCode)){
//    		throw new IJSException(errorCode);
//    	}
	}
	
}
