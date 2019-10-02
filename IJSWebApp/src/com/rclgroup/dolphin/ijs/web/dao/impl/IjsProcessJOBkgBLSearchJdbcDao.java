package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsMessageCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsProcessJOBkgBLSearchDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOBkgBLSearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSumContDtlDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSumDtlDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSummarySearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsProcessJOBkgBLSearchSvc;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractEqTypeVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsExcelUploadTemplateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOSummaryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOBkgBLSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOBkgBLSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOListParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRoutingListParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsVendorDetails;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class IjsProcessJOBkgBLSearchJdbcDao extends IjsBaseDao implements IjsProcessJOBkgBLSearchDao {
    private IjsProcessJOBkgBLSearchJdbcDao.GetIjsSearchStoredProcedure getIjsSearchStoredProcedure; 
    private IjsProcessJOBkgBLSearchJdbcDao.IjsContractSaveStoredProcedure ijsContractSaveStoredProcedure; 
    private IjsProcessJOBkgBLSearchJdbcDao.IjsContractDeleteStoredProcedure ijsContractDeleteStoredProcedure;
    private IjsProcessJOBkgBLSearchJdbcDao.PutIjsBookingBLStoredProcedure putIjsJOBookingBL;
    private IjsProcessJOBkgBLSearchJdbcDao.PutIjsJOSummaryProcedure putIjsJOSummaryProcedure;
    private IjsProcessJOBkgBLSearchJdbcDao.ResetJobOrderProcedure resetJobOrderProcedure;
    private IjsProcessJOBkgBLSearchJdbcDao.CreateJobOrderProcedure createJobOrderProcedure;
    private IjsProcessJOBkgBLSearchJdbcDao.IjsChangeVendorStoredProcedure ijsChangeVendorStoredProcedure ;
//CR#03 START
    private IjsProcessJOBkgBLSearchJdbcDao.IjsGetMaxEquipLimitProcedure ijsGetMaxEquipLimitProcedure;
    private IjsProcessJOBkgBLSearchJdbcDao.IjsCntrEquipmentTypeProcedure ijsCntrEquipmentTypeProcedure;
//CR#03 END
    private IjsJoDeleteLumpsumProcedure ijsJoDeleteLumpsumProcedure;
    public void initDao() throws Exception {
        //##01 BEGIN
        super.initDao();
        getIjsSearchStoredProcedure = 
                new IjsProcessJOBkgBLSearchJdbcDao.GetIjsSearchStoredProcedure(getJdbcTemplate(), 
                                                                         new IjsProcessJOBkgBLSearchJdbcDao.IjsProcessJOBkgBLSearchRowMapper());
        putIjsJOBookingBL = 
                new IjsProcessJOBkgBLSearchJdbcDao.PutIjsBookingBLStoredProcedure(getJdbcTemplate(), 
                                                                         new IjsProcessJOBkgBLSearchJdbcDao.IjsProcessJOSummarySearchRowMapper());                                                                           
                                                                         
        putIjsJOSummaryProcedure = new IjsProcessJOBkgBLSearchJdbcDao.PutIjsJOSummaryProcedure(getJdbcTemplate(), 
                                                                         new IjsProcessJOBkgBLSearchJdbcDao.IjsProcessJOSummarySearchRowMapper());
        ijsContractSaveStoredProcedure = 
                new IjsProcessJOBkgBLSearchJdbcDao.IjsContractSaveStoredProcedure(getJdbcTemplate()); //##03


                 ijsChangeVendorStoredProcedure = 
                          new IjsProcessJOBkgBLSearchJdbcDao.IjsChangeVendorStoredProcedure(getJdbcTemplate(),
                                                    new IjsProcessJOBkgBLSearchJdbcDao.IjsProcessJOBkgBLSearchRowMapper()); 
                                                    
                                                    
        //##04 BEGIN
        ijsContractDeleteStoredProcedure = 
                new IjsProcessJOBkgBLSearchJdbcDao.IjsContractDeleteStoredProcedure(getJdbcTemplate());
        resetJobOrderProcedure = 
                new IjsProcessJOBkgBLSearchJdbcDao.ResetJobOrderProcedure(getJdbcTemplate());
        createJobOrderProcedure = 
                new IjsProcessJOBkgBLSearchJdbcDao.CreateJobOrderProcedure(getJdbcTemplate());
        //CR#03 START
        ijsGetMaxEquipLimitProcedure=new IjsProcessJOBkgBLSearchJdbcDao.IjsGetMaxEquipLimitProcedure(getJdbcTemplate());
        ijsCntrEquipmentTypeProcedure=new IjsProcessJOBkgBLSearchJdbcDao.IjsCntrEquipmentTypeProcedure(getJdbcTemplate(),new IjsProcessJOBkgBLSearchJdbcDao.IjsCntrEquipmentRowMapper());
        //CR#03 END
        ijsJoDeleteLumpsumProcedure=new IjsJoDeleteLumpsumProcedure(getJdbcTemplate(),new IjsProcessJOBkgBLSearchJdbcDao.IjsProcessJOSummarySearchRowMapper());
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
     public List<IjsProcessJOBkgBLSearchDTO> findBkgBL( 
                                                     String userId,
                                                     IjsProcessJOBkgBLSearchParamVO searchParam,String sessionId) throws IJSException{
         List<IjsProcessJOBkgBLSearchDTO> lstContractSearch
         = getIjsSearchStoredProcedure.getIjsContractSearchList(userId,searchParam,sessionId);
         if(lstContractSearch==null || lstContractSearch.isEmpty()){
             IJSException ijsException  =new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
             throw ijsException;
         }
         return lstContractSearch;
     }
     
     
    public String changeVendor(String bk_bl_ad,
                                String bkgblNumber,
                                String routingId,
                                String routingIdOLD,
                                String vendorID,
                                String vendorIDOLD,
                                String contractId,
                                String transMode,
                                String userId,String action) throws IJSException {
        
        //to save routing and fsc
        String errorCode =
                    ijsChangeVendorStoredProcedure.changeVendor(bk_bl_ad,
                                                bkgblNumber,
                                                routingId,
                                                routingIdOLD,
                                                vendorID,
                                                vendorIDOLD,contractId,transMode, userId,action);
                    
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
     
    public List<IjsProcessJOSumDtlDTO> searchJOSummary(String userId,
                                                    IjsProcessJOBkgBLSearchParamVO searchParam,
                                                    List<IjsJOSummaryParamVO> llstJOSummaryParam,
                                                    String processJoType,
                                                    String astrSessionId,
                                                    String transMode) throws IJSException{
                                                    
                                                    
                                                    
        List<IjsProcessJOSumDtlDTO> llstJOSummarySearch = putIjsJOBookingBL.putIjsJOBookingBL
        		(userId,searchParam, llstJOSummaryParam, processJoType, astrSessionId, transMode);
        return llstJOSummarySearch;
    }   

 public void resetJO(String userId,String astrSessionId) throws IJSException{
         resetJobOrderProcedure.resetJO(userId, astrSessionId);
         
     }
     
    public List<IjsProcessJOSumDtlDTO> searchJOSummaryAdhoc( 
                                                    String userId,
                                                    IjsRoutingListParamVO searchParamAdhoc,
                                                    String astrSessionId,
                                                    String processJoType,
                                                    String vendorCode,
                                                    List<String>  eqList,String strEqDetail) throws IJSException{
                                                    
                                                    
                                                    
    	List<IjsProcessJOSumDtlDTO> llstJOSummarySearch = putIjsJOSummaryProcedure.putIjsJOSummaryAdhoc(userId,searchParamAdhoc, astrSessionId, processJoType, vendorCode, eqList,strEqDetail);

        
        
        if(llstJOSummarySearch==null || llstJOSummarySearch.isEmpty()){
            IJSException ijsException  =new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10030.getErrorCode());
            throw ijsException;
        }
        return llstJOSummarySearch;
    }     

        public List createJob( 
                                         String userId, 
                                         List<IjsProcessJOListParamVO> processjoFieldList,
                                         String astrSessionId,
                                         String reasonCode,
                                         String transMode,
                                         String processJoType) throws IJSException{
                                                    
                                                    
                                                    
        List llstJobOrders = createJobOrderProcedure.createJobOrder(userId,processjoFieldList, reasonCode, astrSessionId, transMode, processJoType);
        return llstJobOrders;
  
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
    public String saveOrUpdateContract(IjsProcessJOBkgBLSearchVO ijsContractVO, 
                                       String userInfo, 
                                       String action) throws IJSException {
        Map outMap = 
            ijsContractSaveStoredProcedure.saveORUpdateContract(ijsContractVO, 
                                                                userInfo, 
                                                                action);
        String errorCode = (String)outMap.get("p_o_v_err_cd");
        if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10006")) {
            
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10006.getErrorCode());
        } else if (errorCode != null && 
                   errorCode.contains("DB_IJS_CNTR_EX_10007")) {
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10007.getErrorCode());
        } else if (errorCode != null && 
                   errorCode.contains("DB_IJS_CNTR_EX_10008")) {
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10008.getErrorCode());
        } else {
            if (IjsActionMethod.NEW.getAction().equals(action)) {
                return IjsMessageCode.IJS_COMM_INSERTED.getMsgCode();
            } else if (IjsActionMethod.EDIT.getAction().equals(action)) {
                return IjsMessageCode.IJS_COMM_UPDATE.getMsgCode();
            }
        }
        return null;
    }
    //##03 END
    //##04 BEGIN

    /**
     * deleteContract for delete contract
     * @param contractsList
     * @param userInfo
     * @return
     * @throws IJSException
     */
    public Map<String, String> deleteContract(List<String> contractsList, 
                                              String userInfo) throws IJSException {
        Map<String, String> response = new HashMap<String, String>();
        String errorCode = null;
        for (String contractId: contractsList) {
            errorCode = 
                    ijsContractDeleteStoredProcedure.deleteContract(contractId, 
                                                                    userInfo);
            if (errorCode != null && 
                errorCode.contains("DB_IJS_CNTR_EX_10009")) {
                response.put(contractId, IjsErrorCode.DB_IJS_CNTR_EX_10009.getErrorCode());
            } else {
                response.put(contractId, IjsMessageCode.IJS_COMM_DELETE.getMsgCode());
            }
            errorCode = null;
        }
        return response.size() > 0 ? response : null;
    }

    public Map<String, String> suspendContract(List<String> contractsList, 
                                               String userInfo) {
        return null;
    }

    public List<IjsProcessJOBkgBLSearchDTO> compareContract(IjsProcessJOBkgBLSearchVO ijsContractVO, 
                                                            String userInfo) {
        return null;
    }

    public List<IjsVendorDetails> getVendorDetails(String vendorCode, 
                                                   String userInfo) {
        return null;
    }

    public List<String> uploadContracts(List<IjsExcelUploadTemplateVO> excelUploadTemplateList, 
                                        String userInfo, 
                                        IjsProcessJOBkgBLSearchSvc lookupSvc) {
        return null;
    }

    public Map findContractsToDownload(String transMode, String dateRange, 
                                       String userId, 
                                       IjsProcessJOBkgBLSearchParamVO searchParam, 
                                       List<IjsProcessJOBkgBLSearchDTO> lstCostContractSearch, 
                                       List<IjsProcessJOBkgBLSearchDTO> lstBillContractSearch, 
                                       String sessionId) {
        return Collections.EMPTY_MAP;
    }


    //##04 END
    //##05 BEGIN

    
    //##05 END
    //##01 BEGIN

    protected class GetIjsSearchStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_PROCESS_JO_BKG_BL_SEARCH = 
            "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_SEARCH_BKG_BL";
            

        GetIjsSearchStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, SQL_PROCESS_JO_BKG_BL_SEARCH);
            
            declareParameter(new SqlInOutParameter("p_i_v_trans_mod", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bkg_or_bl", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vend_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bkg_bl_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_service", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vessel", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_voyage", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_session", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_port_lookup", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_soc_coc", Types.VARCHAR, 
                        rowMapper));
                        
            declareParameter(new SqlInOutParameter("p_i_v_fr_loc_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_fr_location", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_location", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_fr_terminal", Types.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_pptdhVal", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR, 
                        rowMapper)); 
            
            declareParameter(new SqlInOutParameter("p_i_v_sort_by", Types.VARCHAR, 
                        rowMapper)); 
            declareParameter(new SqlInOutParameter("p_i_v_order_by", Types.VARCHAR, 
                        rowMapper)); 
                        
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, 
                        rowMapper));
            compile();

        }

        public List<IjsProcessJOBkgBLSearchDTO> getIjsContractSearchList( 
                                                                   String userId, 
                                                                   IjsProcessJOBkgBLSearchParamVO searchParam,String sessionId) throws IJSException {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            String lstrTransportMode = "";
            String lstrFromLocType = "";
            String lstrToLocType = "";
            String lstrProcessJOType="";
            String processJoType=searchParam.getProcessJoType();
            
            
            
            if("Truck".equals(searchParam.getTransMode())){
                lstrTransportMode = "T";
            } else if("Barge".equals(searchParam.getTransMode())){
                lstrTransportMode = "B";
            } else if("Feeder".equals(searchParam.getTransMode())){
                lstrTransportMode = "F";
            } else {
                lstrTransportMode = "R";
            }
            
            if("Depot".equals(searchParam.getFromLocType())){
                lstrFromLocType = "Y";
            } else if("Door".equals(searchParam.getFromLocType())){
                lstrFromLocType = "D";
            } else if("Terminal".equals(searchParam.getFromLocType())){
                lstrFromLocType = "P";
            } else if("Haulage".equals(searchParam.getFromLocType())){
                lstrFromLocType = "H";
            } else if("Port".equals(searchParam.getFromLocType())){
                lstrFromLocType = "O";
            } else {
                lstrFromLocType = "";
            }
            
            if("Depot".equals(searchParam.getToLocType())){
                lstrToLocType = "Y";
            } else if("Door".equals(searchParam.getToLocType())){
                lstrToLocType = "D";
            } else if("Terminal".equals(searchParam.getToLocType())){
                lstrToLocType = "P";
            } else if("Haulage".equals(searchParam.getToLocType())){
                lstrToLocType = "H";
            } else if("Port".equals(searchParam.getFromLocType())){
            	lstrToLocType = "O";
            } else {
            	lstrToLocType = "";
            }
            
            String lstrSortBy = "";
            
            String lstrOrderBy = "";
            
            if(searchParam.getSortBy() != null && "".equals(searchParam.getSortBy())){
                lstrSortBy = searchParam.getSortBy().toUpperCase();
            }
            
            
            if("BOOKING".equals(searchParam.getBookingType().toUpperCase())){
                if(searchParam.getOrderBy() != null && "".equals(searchParam.getOrderBy())){
                    lstrOrderBy = searchParam.getOrderBy().toUpperCase();
                    if("Booking/BL".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.booking_no ";
                    }
                    
                    if("Transport Mode".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.transport_mode ";
                    }
                    
                    if("Start Date".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.sailing_date ";
                    }
                    
                    if("End Date".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.arrival_date ";
                    }
                    
                    if("From".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.from_terminal ";
                    }
                    
                    if("To".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.to_terminal ";
                    }
                }
            }
            
            if("BL".equals(searchParam.getBookingType().toUpperCase())){
                if(searchParam.getOrderBy() != null && "".equals(searchParam.getOrderBy())){
                    lstrOrderBy = searchParam.getOrderBy().toUpperCase();
                    if("Booking/BL".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.SYBLNO ";
                    }
                    
                    if("Transport Mode".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.TRANSPORT_MODE ";
                    }
                    
                    if("Start Date".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.sailing_date ";
                    }
                    
                    if("End Date".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.arrival_date ";
                    }
                    
                    if("From".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.from_terminal ";
                    }
                    
                    if("To".equals(lstrOrderBy)){
                        lstrOrderBy = " bvrd.to_terminal ";
                    }
                }
            }
            
            if("SEALEG".equals(processJoType)){
                lstrProcessJOType = "S";
            }else if("ETR".equals(processJoType)){
                lstrProcessJOType = "O";
            }else if("ITR".equals(processJoType)){
                lstrProcessJOType = "I";
            }else if("IT".equals(processJoType)){
                lstrProcessJOType = "T";
            }else if("ER".equals(processJoType)){
                lstrProcessJOType = "A";
            }else if("LAH".equals(processJoType)){
                lstrProcessJOType = "L";
            }else{
                lstrProcessJOType = "R";
            }
            

            inParameters.put("p_i_v_trans_mod", RutDatabase.stringToDb(lstrTransportMode));
            inParameters.put("p_i_v_bkg_or_bl", RutDatabase.stringToDb(searchParam.getBookingType()).toUpperCase());
            inParameters.put("p_i_v_vend_cd", RutDatabase.stringToDb(searchParam.getVendorCode()).toUpperCase());
            inParameters.put("p_i_v_jo_typ", RutDatabase.stringToDb(lstrProcessJOType));
            inParameters.put("p_i_v_bkg_bl_no", RutDatabase.stringToDb(searchParam.getBookingVal()).toUpperCase());
            inParameters.put("p_i_v_service", RutDatabase.stringToDb(searchParam.getServiceVal()).toUpperCase());
            inParameters.put("p_i_v_vessel", RutDatabase.stringToDb(searchParam.getVesselVal()).toUpperCase());
            inParameters.put("p_i_v_voyage", RutDatabase.stringToDb(searchParam.getVoyageVal()).toUpperCase());
            inParameters.put("p_i_v_session", RutDatabase.stringToDb(sessionId));
            inParameters.put("p_i_v_port_lookup", RutDatabase.stringToDb(searchParam.getPptdhVal()).toUpperCase());
            inParameters.put("p_i_v_soc_coc", RutDatabase.stringToDb(searchParam.getProcessJoCOCType()).toUpperCase());
            inParameters.put("p_i_v_fr_loc_typ", RutDatabase.stringToDb(lstrFromLocType).toUpperCase());
            inParameters.put("p_i_v_to_loc_typ", RutDatabase.stringToDb(lstrToLocType).toUpperCase());
            inParameters.put("p_i_v_fr_location", RutDatabase.stringToDb(searchParam.getFromLocation()).toUpperCase());
            inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(searchParam.getToLocation()).toUpperCase());
            inParameters.put("p_i_v_fr_terminal", RutDatabase.stringToDb(searchParam.getFromTerminal()).toUpperCase());
            inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(searchParam.getToTerminal()).toUpperCase());
            inParameters.put("p_i_v_pptdhVal", RutDatabase.stringToDb(searchParam.getPptdhVal()).toUpperCase());
            inParameters.put("p_i_v_start_date", IjsHelper.getDateFromRange(searchParam.getDateRange(),"START_DATE"));
            inParameters.put("p_i_v_end_date", IjsHelper.getDateFromRange(searchParam.getDateRange(),"END_DATE"));
            
            inParameters.put("p_i_v_sort_by", RutDatabase.stringToDb(lstrSortBy).toUpperCase());
            inParameters.put("p_i_v_order_by", RutDatabase.stringToDb(lstrOrderBy).toUpperCase());
            
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
            
            try{
            	Date start = new Date();
            	System.out.println("IjsProcessJOBkgBLSearchJdbcDao:before executing search ,begin time="+start.getTime());
            	outMap = execute(inParameters); 
            	Date end = new Date();
            	System.out.println("IjsProcessJOBkgBLSearchJdbcDao:after executing search,end time="+end.getTime());
            	long diff = end.getTime() - start.getTime();
            	long diffSeconds = diff / 1000;
            	System.out.println("IjsProcessJOBkgBLSearchJdbcDao:after executing search,difference time in sec="+diffSeconds);
            }catch(Exception e){
            	e.printStackTrace();
            }
            
           
            String errorCd=(String)outMap.get("p_o_v_err_cd");
            System.out.println("IjsProcessJOBkgBLSearchJdbcDao errorCd:"+errorCd);
            if(errorCd!=null){
            	 if(errorCd.equals(IjsErrorCode.DB_IJS_PRJ_EX_10001.name())){
            		 errorCd=IjsErrorCode.DB_IJS_PRJ_EX_10001.getErrorCode();
                }else if(errorCd.equals(IjsErrorCode.DB_IJS_PRJ_EX_10002.name())){
           		    errorCd=IjsErrorCode.DB_IJS_PRJ_EX_10002.getErrorCode();
                }else if(errorCd.equals(IjsErrorCode.DB_IJS_PRJ_EX_10003.name())){
           		    errorCd=IjsErrorCode.DB_IJS_PRJ_EX_10003.getErrorCode();
                }
            	throw new IJSException(errorCd);
            }
            return (List<IjsProcessJOBkgBLSearchDTO>)outMap.get("p_o_v_ijs_mapping_list");
        }
        
        
    }
    
    
    
    
    protected class PutIjsBookingBLStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_PROCESS_JO_BKGBL_INSERT = 
            "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_INSERT_RA_DETAIL";
            

        PutIjsBookingBLStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, SQL_PROCESS_JO_BKGBL_INSERT);
            
            declareParameter(new SqlInOutParameter("p_i_v_trans_mod", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bkg_or_bl", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vend_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_jo_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bkg_bl_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_service", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vessel", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_voyage", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_session", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_port_lookup", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_soc_coc", Types.VARCHAR, 
                        rowMapper));
                        
            declareParameter(new SqlInOutParameter("p_i_v_fr_loc_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_fr_location", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_location", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_fr_terminal", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_pptdhVal", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR, 
                        rowMapper));   
            declareParameter(new SqlInOutParameter("p_i_v_routing_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bkgbl_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_container_no_e", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_container_no_l", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_costcalflg", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_delerteflg", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_session_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cntSize", Types.VARCHAR, 
                        rowMapper));   
            declareParameter(new SqlInOutParameter("p_i_v_cntType", Types.VARCHAR, 
                        rowMapper));   
            declareParameter(new SqlInOutParameter("p_i_v_cntSplHandling", Types.VARCHAR, 
                        rowMapper));                         
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_failed_bkg_bl", Types.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, 
                        rowMapper));
            compile();

        }
        
        public List<IjsProcessJOSumDtlDTO> putIjsJOBookingBL(
                                                                   String userId, 
                                                                   IjsProcessJOBkgBLSearchParamVO searchParam,
                                                                   List<IjsJOSummaryParamVO> llstJOSummaryParam,
                                                                   String processJoType,
                                                                   String astrSessionId,
                                                                   String transMode) throws IJSException {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            String lstrTransportMode = "";
            String lstrFromLocType = "";
            String lstrToLocType = "";
            String BLANK = "";
            String COMMA = ",";
            String lstrProcessJOType = "";
            StringBuffer lsbBkgBl = new StringBuffer();

            String lstrBookingBl = null;
            
            
            if("SEALEG".equals(processJoType)){
                lstrProcessJOType = "S";
            }else if("ETR".equals(processJoType)){
                lstrProcessJOType = "O";
            }else if("ITR".equals(processJoType)){
                lstrProcessJOType = "I";
            }else if("IT".equals(processJoType)){
                lstrProcessJOType = "T";
            }else if("ER".equals(processJoType)){
                lstrProcessJOType = "A";
            }else if("LAH".equals(processJoType)){
                lstrProcessJOType = "L";
            }else{
                lstrProcessJOType = "R";
            }
            
            for(int i=0;i<llstJOSummaryParam.size();i++){
                IjsJOSummaryParamVO objJOSummaryParam  = (IjsJOSummaryParamVO)llstJOSummaryParam.get(i);
                lstrBookingBl = objJOSummaryParam.getBkgOrBLType().toUpperCase();
                
                lsbBkgBl.append(objJOSummaryParam.getBkgOrBLNumber().toUpperCase());
                lsbBkgBl.append(COMMA);
                
            }
            if(lsbBkgBl!=null){
                lsbBkgBl.replace(lsbBkgBl.lastIndexOf(","), lsbBkgBl.lastIndexOf(","), "");
            }
            
                if("Truck".equals(transMode)){
                    lstrTransportMode = "T";
                } else if("Barge".equals(transMode)){
                    lstrTransportMode = "B";
                } else if("Feeder".equals(transMode)){
                    lstrTransportMode = "F";
                } else {
                    lstrTransportMode = "R";
                }
                
            String lstrCostCalFlg = "N";
            String lstrDeleteFlg = "N";
            
       List<IjsProcessJOSummarySearchDTO> llstJOSummary =null;   
       IjsProcessJOSummarySearchDTO joSummaryDTO;
       List<IjsProcessJOSumDtlDTO> llstJOSummarySearchReturn = new ArrayList<IjsProcessJOSumDtlDTO>();
       for(int i=0;i<llstJOSummaryParam.size();i++){  
           StringBuffer lsbContainere = new StringBuffer();
           StringBuffer lsbContainerl = new StringBuffer();
           IjsJOSummaryParamVO objJOSummaryParam  = (IjsJOSummaryParamVO)llstJOSummaryParam.get(i);
           
           List lstContainerl = objJOSummaryParam.getLstContainerl();
           if(lstContainerl != null){
               for(int j=0;j<lstContainerl.size();j++){
                   lsbContainerl.append(lstContainerl.get(j));
                   lsbContainerl.append(COMMA);
               }
           }
           
           List lstContainere = objJOSummaryParam.getLstContainere();
           if(lstContainere != null){
               for(int j=0;j<lstContainere.size();j++){
                   lsbContainere.append(lstContainere.get(j));
                   lsbContainere.append(COMMA);
               }
           }
           
           if(i==(llstJOSummaryParam.size()-1)){
               lstrCostCalFlg = "Y";
           }
            if(i==0){
                lstrDeleteFlg = "Y";
            }else{
                lstrDeleteFlg = "N";
            }

            inParameters.put("p_i_v_trans_mod", RutDatabase.stringToDb(lstrTransportMode.toUpperCase()));
            inParameters.put("p_i_v_bkg_or_bl", RutDatabase.stringToDb(lstrBookingBl));
            inParameters.put("p_i_v_vend_cd", RutDatabase.stringToDb(objJOSummaryParam.getVendorCode()).toUpperCase());
            inParameters.put("p_i_v_jo_typ", RutDatabase.stringToDb(lstrProcessJOType));
            inParameters.put("p_i_v_bkg_bl_no", RutDatabase.stringToDb(BLANK));
            inParameters.put("p_i_v_service", RutDatabase.stringToDb(BLANK));
            inParameters.put("p_i_v_vessel", RutDatabase.stringToDb(BLANK));
            inParameters.put("p_i_v_voyage", RutDatabase.stringToDb(BLANK));
            inParameters.put("p_i_v_session", RutDatabase.stringToDb(BLANK));
            inParameters.put("p_i_v_port_lookup", RutDatabase.stringToDb(BLANK));
            inParameters.put("p_i_v_soc_coc", RutDatabase.stringToDb(BLANK));
            if(lstrProcessJOType.equals("T")){
            	inParameters.put("p_i_v_fr_loc_typ", RutDatabase.stringToDb(IjsHelper.getLocationCode(objJOSummaryParam.getFromLocationTyp())).toUpperCase());
            }else{
            	inParameters.put("p_i_v_fr_loc_typ", RutDatabase.stringToDb(BLANK));
            }
            if(lstrProcessJOType.equals("T")){
            	inParameters.put("p_i_v_to_loc_typ", RutDatabase.stringToDb(IjsHelper.getLocationCode(objJOSummaryParam.getToLocationTyp())).toUpperCase());
            }else{
            	inParameters.put("p_i_v_to_loc_typ", RutDatabase.stringToDb(BLANK));
            }
            if(lstrProcessJOType.equals("T")){
            	inParameters.put("p_i_v_fr_location", RutDatabase.stringToDb(objJOSummaryParam.getFromLocation()).toUpperCase());
            }else{
            	inParameters.put("p_i_v_fr_location", RutDatabase.stringToDb(BLANK));
            }
            if(lstrProcessJOType.equals("T")){
            	inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(objJOSummaryParam.getToLocation()).toUpperCase());
            }else{
            	inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(BLANK));
            }
            if(lstrProcessJOType.equals("T")){
            	 inParameters.put("p_i_v_fr_terminal", RutDatabase.stringToDb(objJOSummaryParam.getFromTerminal()).toUpperCase());
            }else{
            	inParameters.put("p_i_v_fr_terminal", RutDatabase.stringToDb(BLANK));
            }
            if(lstrProcessJOType.equals("T")){
           	 inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(objJOSummaryParam.getToTerminal()).toUpperCase());
           }else{
        	   inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(BLANK));
           }
            inParameters.put("p_i_v_pptdhVal", RutDatabase.stringToDb(BLANK));
            inParameters.put("p_i_v_start_date", RutDatabase.stringToDb(BLANK));
            inParameters.put("p_i_v_end_date", RutDatabase.stringToDb(BLANK));
            if(lstrProcessJOType.equals("T")){
            	inParameters.put("p_i_v_routing_no", RutDatabase.stringToDb(objJOSummaryParam.getRoutingNumber()));
              }else{
           	   inParameters.put("p_i_v_routing_no",RutDatabase.stringToDb(BLANK));
              }
            
            inParameters.put("p_i_v_bkgbl_no", RutDatabase.stringToDb(objJOSummaryParam.getBkgOrBLNumber().toUpperCase()));
            inParameters.put("p_i_v_container_no_e", RutDatabase.stringToDb(lsbContainere.toString()));
            inParameters.put("p_i_v_container_no_l", RutDatabase.stringToDb(lsbContainerl.toString()));
            inParameters.put("p_i_v_costcalflg", RutDatabase.stringToDb(lstrCostCalFlg));
            inParameters.put("p_i_v_delerteflg", RutDatabase.stringToDb(lstrDeleteFlg));
            inParameters.put("p_i_v_session_id", RutDatabase.stringToDb(astrSessionId));
            
            inParameters.put("p_i_v_cntSize", RutDatabase.stringToDb(objJOSummaryParam.getCntSize()));
            inParameters.put("p_i_v_cntType", RutDatabase.stringToDb(objJOSummaryParam.getCntType()));
            inParameters.put("p_i_v_cntSplHandling", RutDatabase.stringToDb(objJOSummaryParam.getCntSplHandling()));
            
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
            if("Y".equals(lstrCostCalFlg)){
              outMap = execute(inParameters); 
            }else{
               execute(inParameters); 
            }
           
       } // end of for loop
       String lstrErrorCode=(String)outMap.get("p_o_v_err_cd");
       if( lstrErrorCode != null){
       	if(lstrErrorCode != null){
           if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20001.name())){
           		lstrErrorCode=IjsErrorCode.DB_IJS_MAINT_JO_EX_20001.getErrorCode();
           }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.name())){
              	lstrErrorCode=IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.getErrorCode();
           }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.name())){
             	lstrErrorCode=IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.getErrorCode();
           }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.name())){
            	lstrErrorCode=IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.getErrorCode();
           }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10042.name())){
           	lstrErrorCode=IjsErrorCode.DB_IJS_CNTR_EX_10042.getErrorCode();
           }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10041.name())){
           	lstrErrorCode=IjsErrorCode.DB_IJS_CNTR_EX_10041.getErrorCode();
           }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10043.name())){
           	lstrErrorCode=IjsErrorCode.DB_IJS_CNTR_EX_10043.getErrorCode();
           }else{
           	lstrErrorCode=IjsErrorCode.DB_IJS_CNTR_EX_10042.getErrorCode();
           }
         }
           IjsProcessJOSumDtlDTO ijsProcessJOSumDtlDTO=new IjsProcessJOSumDtlDTO();
           ijsProcessJOSumDtlDTO.setErrorCode(lstrErrorCode);
           ijsProcessJOSumDtlDTO.setBokingBL((String)outMap.get("p_o_v_failed_bkg_bl"));
           llstJOSummarySearchReturn.add(ijsProcessJOSumDtlDTO);
           resetJO(astrSessionId,userId);
       }else{
       	return prepareJOData((List<IjsProcessJOSummarySearchDTO>)outMap.get("p_o_v_ijs_mapping_list"));
       }
       return llstJOSummarySearchReturn;
     }        
    }    
    private List<IjsProcessJOSumDtlDTO> prepareJOData(List<IjsProcessJOSummarySearchDTO> processJoSummDTO){
    	 List<IjsProcessJOSummarySearchDTO> llstJOSummarySearch =processJoSummDTO;
    	 List<IjsProcessJOSumDtlDTO> llstJOSummarySearchReturn = new ArrayList<IjsProcessJOSumDtlDTO>();
         IjsProcessJOSumContDtlDTO jobOrderSum;
         List <IjsProcessJOSumContDtlDTO> jobOrders=null;
         IjsProcessJOSumDtlDTO joSumDtlDTO=null;
         String prevJobOrderNo="";
         String nextJobOrderNo;
         for(int i=0;i< llstJOSummarySearch.size();i++){
             IjsProcessJOSummarySearchDTO joSummery = llstJOSummarySearch.get(i);
             nextJobOrderNo=joSummery.getJobOrder();
             if(!nextJobOrderNo.equals(prevJobOrderNo)){
            	 joSumDtlDTO=new IjsProcessJOSumDtlDTO();
            	 jobOrders=new ArrayList<>();
            	 joSumDtlDTO.setJobOrders(jobOrders);
            	 joSumDtlDTO.setJoSummery(joSummery);
            	 llstJOSummarySearchReturn.add(joSumDtlDTO);
             }
             if("1".equals(joSummery.getSlNumber())){
                 jobOrderSum = new IjsProcessJOSumContDtlDTO();
                 jobOrderSum.setJobOrder(nextJobOrderNo);
                 jobOrderSum.setVendorCode(joSummery.getVendorCode());
                 jobOrderSum.setContractNumber(joSummery.getContractNumber());
                 jobOrderSum.setJoDate(joSummery.getJoDate());
                 jobOrderSum.setSize(joSummery.getSize());
                 jobOrderSum.setType(joSummery.getType());
                 jobOrderSum.setOOG(joSummery.getOOG());
                 jobOrderSum.setMtOrLaden(joSummery.getMtOrLaden());
                 jobOrderSum.setRate(joSummery.getRate());
                 jobOrderSum.setQuantity(joSummery.getQuantity());
                 jobOrderSum.setAmount(joSummery.getAmount());
                 jobOrderSum.setCurrency(joSummery.getCurrency());
                 jobOrderSum.setAmountUsd(joSummery.getAmountUsd());
                 jobOrderSum.setPaymentFSC(joSummery.getPaymentFSC());
                 jobOrderSum.setSlNumber(joSummery.getSlNumber());
                 jobOrderSum.setRateBasis(joSummery.getRateBasis());
                 jobOrderSum.setLumpsumId(joSummery.getLumpsumId());
                 jobOrders.add(jobOrderSum);
             }
           if("2".equals(joSummery.getSlNumber())){
        	   joSumDtlDTO.setJoSummery(joSummery);
           }
           prevJobOrderNo=joSummery.getJobOrder();
    }
         return llstJOSummarySearchReturn;
    }
    
    protected class PutIjsJOSummaryProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_PROCESS_JO_BKGBL_INSERT = 
            "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_INSERT_ADHOC_DETAIL";
            

        PutIjsJOSummaryProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, SQL_PROCESS_JO_BKGBL_INSERT);
            
            declareParameter(new SqlInOutParameter("p_i_v_routingId", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_contractId", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_days", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_hours", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_fromLocation", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_toLocation", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_fromLocType", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_toLocType", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_fromTerminal", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_toTerminal", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_currency", Types.VARCHAR, 
                        rowMapper));
                        
            declareParameter(new SqlInOutParameter("p_i_v_legType", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_priority", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vendorCode", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_processJOType", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_eqList", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_eq_detail", Types.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_transMode", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_session_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_failed_bkg_bl", Types.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, 
                        rowMapper));
            compile();

        }

        
        public List<IjsProcessJOSumDtlDTO> putIjsJOSummaryAdhoc( 
                                                                   String userId, 
                                                                   IjsRoutingListParamVO searchParamAdhoc,
                                                                   String astrSessionId,
                                                                   String processJoType,
                                                                   String vendorCode,
                                                                   List<String>  eqList,String strEqDetail) throws IJSException {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            String lstrProcessJOType = null;
            String lstrTransportMode = null;
            
            String lstrEqList = null;      
            StringBuffer lsbEqList = new StringBuffer();
                                                  
            for(int i = 0; i<eqList.size(); i++){
              lstrEqList = (String) eqList.get(i);
              lsbEqList.append(lstrEqList);
              lsbEqList.append(",");

            }
            
            if("SEALEG".equals(processJoType)){
                lstrProcessJOType = "S";
            }else if("ETR".equals(processJoType)){
                lstrProcessJOType = "O";
            }else if("ITR".equals(processJoType)){
                lstrProcessJOType = "I";
            }else if("IT".equals(processJoType)){
                lstrProcessJOType = "T";
            }else if("ER".equals(processJoType)){
                lstrProcessJOType = "A";
            }else if("LAH".equals(processJoType)){
                lstrProcessJOType = "L";
            }else{
                lstrProcessJOType = "R";
            }
            
            if("Truck".equals(searchParamAdhoc.getTransMode())){
                lstrTransportMode = "T";
            } else if("Barge".equals(searchParamAdhoc.getTransMode())){
                lstrTransportMode = "B";
            } else if("Feeder".equals(searchParamAdhoc.getTransMode())){
                lstrTransportMode = "F";
            } else {
                lstrTransportMode = "R";
            }
            
                inParameters.put("p_i_v_routingId", RutDatabase.stringToDb(searchParamAdhoc.getRoutingId()));
                inParameters.put("p_i_v_contractId", RutDatabase.stringToDb(searchParamAdhoc.getContractId()));
                inParameters.put("p_i_v_days", RutDatabase.stringToDb(searchParamAdhoc.getDays()));
                inParameters.put("p_i_v_hours", RutDatabase.stringToDb(searchParamAdhoc.getHours()));
                inParameters.put("p_i_v_fromLocation", RutDatabase.stringToDb(searchParamAdhoc.getFromLocation()));
                inParameters.put("p_i_v_toLocation", RutDatabase.stringToDb(searchParamAdhoc.getToLocation()));
                inParameters.put("p_i_v_fromLocType", RutDatabase.stringToDb(searchParamAdhoc.getFromLocType()));
                inParameters.put("p_i_v_toLocType", RutDatabase.stringToDb(searchParamAdhoc.getToLocType()));
                inParameters.put("p_i_v_fromTerminal", RutDatabase.stringToDb(searchParamAdhoc.getFromTerminal()));
                inParameters.put("p_i_v_toTerminal", RutDatabase.stringToDb(searchParamAdhoc.getToTerminal()));
                inParameters.put("p_i_v_currency", RutDatabase.stringToDb(searchParamAdhoc.getCurrency()));
                inParameters.put("p_i_v_legType", RutDatabase.stringToDb(searchParamAdhoc.getLegType()));
                inParameters.put("p_i_v_priority", RutDatabase.stringToDb(searchParamAdhoc.getPriority()));
                inParameters.put("p_i_v_vendorCode", RutDatabase.stringToDb(searchParamAdhoc.getVendorCode()));
                inParameters.put("p_i_v_processJOType", RutDatabase.stringToDb(lstrProcessJOType));
                inParameters.put("p_i_v_eqList", RutDatabase.stringToDb(lsbEqList.toString()));
                inParameters.put("p_i_v_eq_detail", RutDatabase.stringToDb(strEqDetail));
                inParameters.put("p_i_v_transMode", RutDatabase.stringToDb(lstrTransportMode));
                inParameters.put("p_i_v_session_id", RutDatabase.stringToDb(astrSessionId));
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());

            outMap = execute(inParameters); 
            String lstrErrorCode=(String)outMap.get("p_o_v_err_cd");
            List<IjsProcessJOSumDtlDTO> llstJOSummarySearchReturn = new ArrayList<IjsProcessJOSumDtlDTO>();
            if( lstrErrorCode != null){
            	if(lstrErrorCode != null){
            		if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20001.name())){
                    	lstrErrorCode=IjsErrorCode.DB_IJS_MAINT_JO_ADHOC_20001.getErrorCode();
                    }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.name())){
                       	lstrErrorCode=IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.getErrorCode();
                    }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.name())){
                      	lstrErrorCode=IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.getErrorCode();
                    }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.name())){
                     	lstrErrorCode=IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.getErrorCode();
                    }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10042.name())){
                    	lstrErrorCode=IjsErrorCode.DB_IJS_CNTR_EX_10042.getErrorCode();
                    }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10041.name())){
                    	lstrErrorCode=IjsErrorCode.DB_IJS_CNTR_EX_10041.getErrorCode();
                    }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10043.name())){
                    	lstrErrorCode=IjsErrorCode.DB_IJS_CNTR_EX_10043.getErrorCode();
                    }else if(lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.name())){
                    	lstrErrorCode=IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.getErrorCode();
                    }else{
                    	lstrErrorCode=IjsErrorCode.DB_IJS_CNTR_EX_10042.getErrorCode();
                    }
              }
            IjsProcessJOSumDtlDTO ijsProcessJOSumDtlDTO=new IjsProcessJOSumDtlDTO();
            ijsProcessJOSumDtlDTO.setErrorCode(lstrErrorCode);
            ijsProcessJOSumDtlDTO.setBokingBL(" ");
            llstJOSummarySearchReturn.add(ijsProcessJOSumDtlDTO);
            resetJO(astrSessionId,userId);
            }else{
            	return prepareJOData((List<IjsProcessJOSummarySearchDTO>)outMap.get("p_o_v_ijs_mapping_list"));
            }
            return llstJOSummarySearchReturn;
        }        
        
    }
    
    
    protected class CreateJobOrderProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_PROCESS_JO_BKGBL_INSERT = 
            "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_PROCESS_JO";
            
        CreateJobOrderProcedure(JdbcTemplate jdbcTemplate ) {
            super(jdbcTemplate, SQL_PROCESS_JO_BKGBL_INSERT);
            
            declareParameter(new SqlInOutParameter("p_i_v_fsc_payment", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_processJOType", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_jobOrdDate", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_jobOrdNo", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_remark", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_routingId", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_session_id", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_i_v_job_order_no_return", Types.VARCHAR));
            compile();

        }

        public List createJobOrder( 
                                    String userId, 
                                    List<IjsProcessJOListParamVO> processjoFieldList,
                                    String reasonCode,
                                    String astrSessionId,
                                    String transMode,
                                    String processJoType) throws IJSException {
            Map inParameters = new HashMap();
            Map outMap = new HashMap();
            List llstJobOrders = new ArrayList();
            String lstrProcessJOType = null;
            
            if("SEALEG".equals(processJoType)){
                lstrProcessJOType = "S";
            }else if("ETR".equals(processJoType)){
                lstrProcessJOType = "O";
            }else if("ITR".equals(processJoType)){
                lstrProcessJOType = "I";
            }else if("IT".equals(processJoType)){
                lstrProcessJOType = "T";
            }else if("ER".equals(processJoType)){
                lstrProcessJOType = "A";
            }else if("LAH".equals(processJoType)){
                lstrProcessJOType = "L";
            }else{
                lstrProcessJOType = "R";
            }
 
            for(int i = 0;i<processjoFieldList.size();i++){
                IjsProcessJOListParamVO objIjsProcessJOListParamVO = (IjsProcessJOListParamVO)processjoFieldList.get(i);
                if(objIjsProcessJOListParamVO.getJobOrder()!=null){
                    inParameters.put("p_i_v_fsc_payment", RutDatabase.stringToDb(objIjsProcessJOListParamVO.getPaymentFSC()).toUpperCase());
                    inParameters.put("p_i_v_processJOType", RutDatabase.stringToDb(lstrProcessJOType).toUpperCase());
                    inParameters.put("p_i_v_jobOrdDate", RutDatabase.stringToDb(objIjsProcessJOListParamVO.getJoDate()));
                    inParameters.put("p_i_v_jobOrdNo", RutDatabase.stringToDb(objIjsProcessJOListParamVO.getJobOrder()).toUpperCase());
                    inParameters.put("p_i_v_remark", RutDatabase.stringToDb(objIjsProcessJOListParamVO.getRemark()));
                    inParameters.put("p_i_v_routingId", RutDatabase.stringToDb(objIjsProcessJOListParamVO.getRoutingId()));
                    inParameters.put("p_i_v_session_id", RutDatabase.stringToDb(astrSessionId));
                    inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
                    outMap = execute(inParameters); 
                    llstJobOrders.add(outMap.get("p_i_v_job_order_no_return"));
                }
            }
            resetJO(userId, astrSessionId);
            return llstJobOrders;
            
        }        
        
    }

    protected class ResetJobOrderProcedure extends StoredProcedure {

        private static final String SQL_PROCESS_JO_BKGBL_INSERT = 
            "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_RESET_JO";
            

        ResetJobOrderProcedure(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_PROCESS_JO_BKGBL_INSERT);
            
       
            declareParameter(new SqlInOutParameter("p_i_v_session_id", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            compile();

        }

         public void resetJO( 
                           String userId, 
                           String astrSessionId)  throws IJSException{
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            String lstrSuccess = "Y";
             try{
                inParameters.put("p_i_v_session_id", RutDatabase.stringToDb(astrSessionId));
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());

            outMap = execute(inParameters); 
             }catch (Exception e) {
                 lstrSuccess = "N";
             } 
             if("N".equals(lstrSuccess)){
                 throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10029.getErrorCode());
             }
        }        
  }
    
    private class IjsProcessJOBkgBLSearchRowMapper implements RowMapper {

    public Object mapRow(ResultSet resultSet, int row) {
        IjsProcessJOBkgBLSearchDTO ijsContractSearchDto = 
            new IjsProcessJOBkgBLSearchDTO();
  try {
 ijsContractSearchDto.setBkgOrBLNumber(resultSet.getString("BKG_BL"));

 ijsContractSearchDto.setBkgOrBLType(resultSet.getString("BK_BL_TYPE"));
 ijsContractSearchDto.setTransportMode(resultSet.getString("TRANSPORT_MODE"));
 ijsContractSearchDto.setBookingStatus(resultSet.getString("STATUS"));
 ijsContractSearchDto.setFromTerminal(resultSet.getString("FROM_TERMINAL"));
 
 ijsContractSearchDto.setFromLocation(resultSet.getString("FROM_POINT"));
 ijsContractSearchDto.setToLocation(resultSet.getString("TO_POINT"));
 
 ijsContractSearchDto.setToTerminal(resultSet.getString("TO_TERMINAL"));
 ijsContractSearchDto.setService(resultSet.getString("SERVICE"));
 ijsContractSearchDto.setVessel(resultSet.getString("VESSEL"));
 ijsContractSearchDto.setVoyage(resultSet.getString("VOYAGE"));
 ijsContractSearchDto.setDirection(resultSet.getString("DIRECTION"));

 ijsContractSearchDto.setStartDate(resultSet.getString("SAILING_DATE"));
 ijsContractSearchDto.setEndDate(resultSet.getString("ARRIVAL_DATE"));
 ijsContractSearchDto.setCntSize(resultSet.getString("EQ_SIZE"));
 ijsContractSearchDto.setCntType(resultSet.getString("EQ_TYPE"));
 ijsContractSearchDto.setRoutingNumber(resultSet.getString("ROUTING_NUMBER"));
 ijsContractSearchDto.setVendorCode(resultSet.getString("VENDORCODE"));
 ijsContractSearchDto.setCntSplHandling(resultSet.getString("SPECIAL_HANDLING"));
 ijsContractSearchDto.setCntSplHandCount(resultSet.getInt("SPECIAL_HANDLING_COUNT"));
 ijsContractSearchDto.setLadenCntTotal((resultSet.getInt("TOTAL_LADEN")));
 ijsContractSearchDto.setLadenCntAvailable(resultSet.getInt("AVAILABLE_LADEN_CNT"));
 ijsContractSearchDto.setLadenCntInJO((resultSet.getInt("IN_JO_LADEN")));
 ijsContractSearchDto.setFromLocationTyp(IjsHelper.getLocationType(resultSet.getString("FROM_LOCTP")));
 ijsContractSearchDto.setSpecialHandlingCode((resultSet.getString("SPECIAL_HANDLING_CODE")));
 ijsContractSearchDto.setToLocationTyp((IjsHelper.getLocationType(resultSet.getString("TO_LOCTP"))));
 
  ijsContractSearchDto.setPriority(resultSet.getString("PRIORITY"));
 try {
     ijsContractSearchDto.setEmptyCntAvailable(resultSet.getInt("AVAILABLE_EMPTY_CNT"));
     ijsContractSearchDto.setEmptyCntInJO(resultSet.getInt("IN_JO_EMPTY"));
     ijsContractSearchDto.setEmptyCntTotal(resultSet.getInt("TOTAL_EMPTY"));
     ijsContractSearchDto.setDgCount(resultSet.getInt("DG_COUNT"));
     ijsContractSearchDto.setOogCount(resultSet.getInt("OOG_COUNT"));
 } catch (NumberFormatException ne) {
     ne.printStackTrace();
 }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

            return ijsContractSearchDto;
        }
    }
    
    
    private class IjsProcessJOSummarySearchRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsProcessJOSummarySearchDTO ijsJOSummarySearchDto = 
                new IjsProcessJOSummarySearchDTO();
            try {
                ijsJOSummarySearchDto.setSlNumber(resultSet.getString("slNumber"));
                ijsJOSummarySearchDto.setJobOrder(resultSet.getString("jobOrder"));
                ijsJOSummarySearchDto.setVendorCode(resultSet.getString("vendorCode"));
                ijsJOSummarySearchDto.setContractNumber(resultSet.getString("contractNumber"));
                ijsJOSummarySearchDto.setJoDate(resultSet.getString("joDate"));
                ijsJOSummarySearchDto.setSize(resultSet.getString("sizecont"));
                ijsJOSummarySearchDto.setType(resultSet.getString("typecont"));
                ijsJOSummarySearchDto.setOOG(resultSet.getString("OOG"));
                ijsJOSummarySearchDto.setMtOrLaden(resultSet.getString("mtOrLaden"));
                ijsJOSummarySearchDto.setRate(resultSet.getString("rate"));
                ijsJOSummarySearchDto.setQuantity(resultSet.getString("quantity"));
                ijsJOSummarySearchDto.setAmount(resultSet.getString("amount"));
                ijsJOSummarySearchDto.setCurrency(resultSet.getString("currency"));
                ijsJOSummarySearchDto.setAmountUsd(resultSet.getString("amountUsd"));
                ijsJOSummarySearchDto.setPaymentFSC(resultSet.getString("paymentFSC"));
                ijsJOSummarySearchDto.setPriority(resultSet.getString("PRIORITY1"));
                ijsJOSummarySearchDto.setRateBasis(resultSet.getString("RATE_BASIS"));
                ijsJOSummarySearchDto.setLumpsumId(resultSet.getString("LUMPSUM_ID"));

            } catch (SQLException e) {
                // TODO
                e.printStackTrace();
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

            return ijsJOSummarySearchDto;
        }
    }
    
    //##01 END
    //##03 BEGIN

    protected class IjsContractSaveStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_IJS_CNTR_SAVE = 
            "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_SAVE";

        IjsContractSaveStoredProcedure(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, PRR_IJS_CNTR_SAVE);
            declareParameter(new SqlInOutParameter("p_i_v_action_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_vendor", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_transport_mode", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_status", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_payment_fsc", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_currency", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_priority", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_from_loc_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_from_location", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_from_terminal", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_to_location", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_i_v_hours", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_days", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_distance", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_uom", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_remarks", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_exempted", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_contract_no", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_routing_id", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            compile();

        }

        private Map saveORUpdateContract(IjsProcessJOBkgBLSearchVO ijsContractVO, 
                                         String userInfo, String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());

            outMap = execute(inParameters);

            return outMap;
        }
    }
    //##03 END
    //##04 BEGIN

    protected class IjsContractDeleteStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_IJS_CNTR_DELETE = 
            "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_DELETE";

        IjsContractDeleteStoredProcedure(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, PRR_IJS_CNTR_DELETE);
            declareParameter(new SqlInOutParameter("p_i_v_contract_no", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            compile();
        }

        private String deleteContract(String contractId, String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_contract_no", RutDatabase.stringToDb(contractId).toUpperCase());
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);

            return (String)outMap.get("p_o_v_err_cd");
        }
    }

    protected class IjsChangeVendorStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_IJS_CNTR_SAVE = 
            "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_CHANGE_VENDOR";

        IjsChangeVendorStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_IJS_CNTR_SAVE);
            
            declareParameter(new SqlInOutParameter("p_i_v_bk_bl_no", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_booking_typ", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_routing_id", Types.VARCHAR, 
                        rowMapper));//p_i_v_contract_id
            declareParameter(new SqlInOutParameter("p_i_v_routing_id_old", Types.VARCHAR, 
                                     rowMapper));
             declareParameter(new SqlInOutParameter("p_i_v_contract_id", Types.VARCHAR, 
                         rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vendor_cd", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vendor_cd_old", Types.VARCHAR,
                            rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_trans_mode", Types.VARCHAR,
            				rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_action", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            compile();

        }

        private String changeVendor(String bk_bl_ad,
                                String bkgblNumber,
                                String routingId,
                                String routingIdOLD,
                                String vendorID,
                                String vendorIDOLD,
                                String contractId,
                                String transMode,
                                String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();

                
                inParameters.put("p_i_v_bk_bl_no", RutDatabase.stringToDb(bkgblNumber));
                inParameters.put("p_i_v_booking_typ", RutDatabase.stringToDb(bk_bl_ad));
                inParameters.put("p_i_v_routing_id", RutDatabase.stringToDb(routingId));
                inParameters.put("p_i_v_routing_id_old", RutDatabase.stringToDb(routingIdOLD));
                inParameters.put("p_i_v_contract_id", RutDatabase.stringToDb(contractId));
                inParameters.put("p_i_v_vendor_cd", RutDatabase.stringToDb(vendorID));
                inParameters.put("p_i_v_vendor_cd_old", RutDatabase.stringToDb(vendorIDOLD));
                inParameters.put("p_i_v_trans_mode", RutDatabase.stringToDb(IjsHelper.getTransCodeX(transMode)));
                inParameters.put("p_i_v_action", RutDatabase.stringToDb(action).toUpperCase());
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
                outMap = execute(inParameters);

            return (String)outMap.get("p_o_v_err_cd") == null ? "MSG" : (String)outMap.get("p_o_v_err_cd");
        }
    }
    
//##CR 03 START
    @Override
	public int getMaxEquipLimit() throws IJSException{
		try{
		int maxEquipLimit=ijsGetMaxEquipLimitProcedure.getMaxEquipmentLimit();
		return maxEquipLimit;
		}catch(Exception e){
			throw new IJSException(IjsErrorCode.UI_GBL_IJS_EX_10001.getErrorCode());
		}
		
	}
	
	protected class IjsGetMaxEquipLimitProcedure extends StoredProcedure {
        private static final String SQL_IJS_MAX_EQUIP_LIMIT = "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_MAX_EQUIP_LIMIT";

        IjsGetMaxEquipLimitProcedure(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate,SQL_IJS_MAX_EQUIP_LIMIT);
            declareParameter(new SqlOutParameter("p_o_v_max_equip_limit", Types.INTEGER));
            compile();
        	}

            private Integer getMaxEquipmentLimit() {

            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            outMap = execute(inParameters);
            return (Integer) outMap.get("p_o_v_max_equip_limit");
        }
    }
	 protected class IjsCntrEquipmentTypeProcedure extends StoredProcedure {
	        private static final String SQL_RLTD_IJS_EQ_TYPE =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_EQ_TYPE_SEARCH";
	        IjsCntrEquipmentTypeProcedure(JdbcTemplate jdbcTemplate , RowMapper rowMapper) {
	            super(jdbcTemplate, SQL_RLTD_IJS_EQ_TYPE);
	            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
	            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
	            declareParameter(new SqlOutParameter("p_o_v_ijs_eq_type_list", 
	                                                 OracleTypes.CURSOR, 
	                                                 rowMapper));
	            compile();
	        }
	        private List<String> getEqTypeList(String userInfo) {
	        Map outMap = new HashMap();
	        Map inParameters = new HashMap();
	        inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
	        outMap = execute(inParameters);
	        
	        return (List<String>) outMap.get("p_o_v_ijs_eq_type_list");
	        }
	        
	    }
	    private class IjsCntrEquipmentRowMapper implements RowMapper {

	        public Object mapRow(ResultSet resultSet, int i) {
	            String eqType=null;
	            try {
	            	eqType=resultSet.getString("EQ_Type");
	            } catch (SQLException e) {
	                e.printStackTrace();
	           }
	            
	            return eqType;
	        }
	    }
    //##CR 03 END
		@Override
		public List<String> getEqTypeList(String userId) throws IJSException {
			try{
				return ijsCntrEquipmentTypeProcedure.getEqTypeList(userId);	
			}catch(Exception e){
				throw new IJSException(IjsErrorCode.UI_GBL_IJS_EX_10001.getErrorCode());
			}
		}



		@Override
		public List<IjsProcessJOSumDtlDTO> deleteLumpsum(
				List<String> lstJobOrders,String allJobOrders, String userId,String sessionId) throws IJSException {
			int joSize=lstJobOrders.size();
			int count=1;
			String lastJO="N";
			String lumpsumIds=null;
			for(String joLumpsum:lstJobOrders){
				String jobOrder=joLumpsum.substring(0,joLumpsum.indexOf(":"));
				lumpsumIds=joLumpsum.substring(joLumpsum.indexOf(":")+1);
				if(count==joSize){
					lastJO="Y";	
				}
				count++;
				List<IjsProcessJOSumDtlDTO> resultSummary=ijsJoDeleteLumpsumProcedure.deleteLumpsum(jobOrder,lumpsumIds,allJobOrders, lastJO, userId,sessionId);
				if("Y".equals(lastJO)){
					return resultSummary;
				}
				
			}
			
			return null;
		}
		 protected class IjsJoDeleteLumpsumProcedure extends StoredProcedure {
		        private static final String SQL_IJS_DELETE_LUMPSUM =  "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_DELETE_LUMPSUM";
		        IjsJoDeleteLumpsumProcedure(JdbcTemplate jdbcTemplate , RowMapper rowMapper) {
		            super(jdbcTemplate, SQL_IJS_DELETE_LUMPSUM);
		            declareParameter(new SqlInOutParameter("p_i_v_jo_order", Types.VARCHAR));
		            declareParameter(new SqlInOutParameter("p_i_v_jo_lumpsum", Types.VARCHAR));
		            declareParameter(new SqlInOutParameter("p_i_v_jo_orders", Types.VARCHAR));
		            declareParameter(new SqlInOutParameter("p_i_v_jo_last", Types.VARCHAR));
		            declareParameter(new SqlInOutParameter("p_i_v_session_id", Types.VARCHAR));
		            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
		            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
		            declareParameter(new SqlOutParameter("p_o_v_ijs_jo_summary_list", 
		                                                 OracleTypes.CURSOR, 
		                                                 rowMapper));
		            compile();
		        }
		        private List<IjsProcessJOSumDtlDTO> deleteLumpsum(String jobOrder,String joLumpsum,String jobOrders,String lastJO,String userInfo,String sessionId) throws IJSException {
		        Map outMap = new HashMap();
		        Map inParameters = new HashMap();
		        inParameters.put("p_i_v_jo_order" , RutDatabase.stringToDb(jobOrder).toUpperCase());
		        inParameters.put("p_i_v_jo_lumpsum" , RutDatabase.stringToDb(joLumpsum).toUpperCase());
		        inParameters.put("p_i_v_jo_orders" , RutDatabase.stringToDb(jobOrders).toUpperCase());
		        inParameters.put("p_i_v_jo_last" , RutDatabase.stringToDb(lastJO).toUpperCase());
		        inParameters.put("p_i_v_session_id" , RutDatabase.stringToDb(sessionId));
		        inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
		        outMap = execute(inParameters);
		        String errorCd=(String)outMap.get("p_o_v_err_cd");
		        if("NO_RATE_AVAILABLE".equals(errorCd)){
		        	throw new IJSException("NO_RATE_AVAILABLE");
		        }
		        if("Y".equals(lastJO)){
		        	return prepareJOData((List<IjsProcessJOSummarySearchDTO>)outMap.get("p_o_v_ijs_jo_summary_list"));
		        }else{
		        	return null;
		        }
		        
		        }
		        
		    }
}
