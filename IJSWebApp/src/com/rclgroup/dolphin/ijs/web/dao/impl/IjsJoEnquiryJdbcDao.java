package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsMessageCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractRateDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsJoEnquiryDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsJoEnquiryDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsJoEnquirySvc;
import com.rclgroup.dolphin.ijs.web.vo.IjsExcelUploadTemplateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoEnquiryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoEnquiryVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsVendorDetails;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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

public class IjsJoEnquiryJdbcDao extends IjsBaseDao implements IjsJoEnquiryDao {
    private IjsJoEnquiryJdbcDao.GetIjsSearchStoredProcedure getIjsSearchStoredProcedure; //##01
    private IjsJoEnquiryJdbcDao.IjsContractSaveStoredProcedure ijsContractSaveStoredProcedure; //##03
    //##04 BEGIN
    private IjsJoEnquiryJdbcDao.IjsContractDeleteStoredProcedure ijsContractDeleteStoredProcedure;
//    private IjsJoEnquiryJdbcDao.IjsContractSuspendStoredProcedure ijsContractSuspendStoredProcedure;
//    private IjsJoEnquiryJdbcDao.IjsContractCompareStoredProcedure ijsContractCompareStoredProcedure;

    private IjsContractRateDao contractRateDao;
    //##04 END

    public void initDao() throws Exception {
        //##01 BEGIN
        super.initDao();
        getIjsSearchStoredProcedure = 
                new IjsJoEnquiryJdbcDao.GetIjsSearchStoredProcedure(getJdbcTemplate(), 
                                                                           new IjsJoEnquiryJdbcDao.IjsProcessJOBkgBLSearchRowMapper());
        //##01 END
        ijsContractSaveStoredProcedure = 
                new IjsJoEnquiryJdbcDao.IjsContractSaveStoredProcedure(getJdbcTemplate()); //##03
        //##04 BEGIN
        ijsContractDeleteStoredProcedure = 
                new IjsJoEnquiryJdbcDao.IjsContractDeleteStoredProcedure(getJdbcTemplate());
//        ijsContractSuspendStoredProcedure = 
//                new IjsJoEnquiryJdbcDao.IjsContractSuspendStoredProcedure(getJdbcTemplate());
//        ijsContractCompareStoredProcedure = 
//                new IjsJoEnquiryJdbcDao.IjsContractCompareStoredProcedure(getJdbcTemplate(), 
                           //                                                      new IjsJoEnquiryJdbcDao.IjsProcessJOBkgBLSearchRowMapper());

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
    public List<IjsJoEnquiryDTO> findContracts(String userId, 
                                                      IjsJoEnquiryParamVO searchParam) throws IJSException {
        List<IjsJoEnquiryDTO> lstContractSearch =
            //= getIjsSearchStoredProcedure.getIjsContractSearchList(transMode,dateRange,userId,searchParam);
            getIjsSearchStoredProcedure.getIjsContractSearchList(userId, 
                                                                 searchParam);
        if (lstContractSearch == null || lstContractSearch.isEmpty()) {
            IJSException ijsException = new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
            throw ijsException;
        }
        return lstContractSearch;
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
    public String saveOrUpdateContract(IjsJoEnquiryVO ijsContractVO, 
                                       String userInfo, 
                                       String action) throws IJSException {
        Map outMap = 
            ijsContractSaveStoredProcedure.saveORUpdateContract(ijsContractVO, 
                                                                userInfo, 
                                                                action);
        String errorCode = (String)outMap.get("p_o_v_err_cd");
        if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10006")) {
            System.out.println("Error Code is  : " + errorCode);
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

    public List<IjsJoEnquiryDTO> compareContract(IjsJoEnquiryVO ijsContractVO, 
                                                        String userInfo) {
        return null;
    }

    public List<IjsVendorDetails> getVendorDetails(String vendorCode, 
                                                   String userInfo) {
        return null;
    }

    public List<String> uploadContracts(List<IjsExcelUploadTemplateVO> excelUploadTemplateList, 
                                        String userInfo, 
                                        IjsJoEnquirySvc lookupSvc) {
        return null;
    }

    public Map findContractsToDownload(String transMode, String dateRange, 
                                       String userId, 
                                       IjsJoEnquiryParamVO searchParam, 
                                       List<IjsJoEnquiryDTO> lstCostContractSearch, 
                                       List<IjsJoEnquiryDTO> lstBillContractSearch, 
                                       String sessionId) {
        return Collections.EMPTY_MAP;
    }


    /*
    /**
     * getVendorDetails for getting vendor information
     * @param vendorCode
     * @param userInfo
     * @return
     * @throws IJSException
     */
    /*
    public List<IjsVendorDetails> getVendorDetails(String vendorCode,
                                                   String userInfo) throws IJSException {
        List<IjsVendorDetails> vendorDetails =
            ijsCntrVendorDetailsStrdProce.getVendorDetailsList(vendorCode,
                                                               userInfo);
        if (vendorDetails == null || vendorDetails.isEmpty()) {
            throw new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
        }
        return vendorDetails;
    }*/


    //##04 END
    //##05 BEGIN


    //##05 END
    //##01 BEGIN

    protected class GetIjsSearchStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_PROCESS_JO_BKG_BL_SEARCH = 
            "PCR_IJS_PROCESS_JO_BKG_BL.PRR_IJS_SEARCH_BKG_BL";

        GetIjsSearchStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, SQL_PROCESS_JO_BKG_BL_SEARCH);
            declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, 
                        rowMapper));
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
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlOutParameter("p_io_v_err_cd", Types.VARCHAR, 
                        rowMapper));
            compile();

        }

        public List<IjsJoEnquiryDTO> getIjsContractSearchList(String userId, 
                                                                     IjsJoEnquiryParamVO searchParam) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();

            inParameters.put("p_i_v_trans_mod", RutDatabase.stringToDb(IjsHelper.getTransCode(searchParam.getTransMode())));
            inParameters.put("p_i_v_bkg_or_bl", RutDatabase.stringToDb(searchParam.getBookingType()).toUpperCase());
            //            inParameters.put("p_i_v_vend_cd", RutDatabase.stringToDb(searchParam.getVendorCode()).toUpperCase());
            //            inParameters.put("p_i_v_jo_typ", RutDatabase.stringToDb(searchParam.getProcessJoType()));
            //            inParameters.put("p_i_v_bkg_bl_no", RutDatabase.stringToDb(searchParam.getBookingVal()));
            //            inParameters.put("p_i_v_service", RutDatabase.stringToDb(searchParam.getServiceVal()).toUpperCase());
            //            inParameters.put("p_i_v_vessel", RutDatabase.stringToDb(searchParam.getVesselVal()));
            //            inParameters.put("p_i_v_voyage", RutDatabase.stringToDb(searchParam.getVoyageVal()));
            //            inParameters.put("p_i_v_session", RutDatabase.stringToDb(searchParam.getDateRange()));
            //            inParameters.put("p_i_v_port_lookup", RutDatabase.stringToDb(searchParam.getPptdhVal()));

            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());

            outMap = execute(inParameters);
            return (List<IjsJoEnquiryDTO>)outMap.get("p_o_v_ijs_mapping_list");
        }
    }

    private class IjsProcessJOBkgBLSearchRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsJoEnquiryDTO ijsContractSearchDto = 
                new IjsJoEnquiryDTO();
            try {
                ijsContractSearchDto.setJoNumber(resultSet.getString("JoNumber"));


                try {
                    //ijsContractSearchDto.setEmptyCntAvailable(Integer.parseInt(resultSet.getString("emptyCntTotal")));
                    //ijsContractSearchDto.setEmptyCntInJO(Integer.parseInt(resultSet.getString("emptyCntAvailable")));
                    //ijsContractSearchDto.setEmptyCntTotal(Integer.parseInt(resultSet.getString("emptyCntInJO")));
                } catch (NumberFormatException ne) {
                    ne.printStackTrace();
                }

            } catch (SQLException e) {
                // TODO
                e.printStackTrace();
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

            return ijsContractSearchDto;
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

        private Map saveORUpdateContract(IjsJoEnquiryVO ijsContractVO, 
                                         String userInfo, String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            //            inParameters.put("p_i_v_action_type", RutDatabase.stringToDb(IjsHelper.getActionType(action)).toUpperCase());
            //            inParameters.put("p_i_v_vendor", RutDatabase.stringToDb(ijsContractVO.getVendorCode()).toUpperCase());
            //            inParameters.put("p_i_v_start_date", RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getStartDate())).toUpperCase());
            //            inParameters.put("p_i_v_end_date", RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getEndDate())).toUpperCase());
            //            inParameters.put("p_i_v_transport_mode", RutDatabase.stringToDb(IjsHelper.getTransCode(ijsContractVO.getTransMode())).toUpperCase());
            //            inParameters.put("p_i_v_status", RutDatabase.stringToDb(IjsHelper.getContractStatusCode(ijsContractVO.getStatus())).toUpperCase());
            //            inParameters.put("p_i_v_payment_fsc", RutDatabase.stringToDb(ijsContractVO.getPaymentFsc()).toUpperCase());
            //            inParameters.put("p_i_v_currency", RutDatabase.stringToDb(ijsContractVO.getCurrency()).toUpperCase());
            //            inParameters.put("p_i_v_priority", ijsContractVO.getPriority());
            //            inParameters.put("p_i_v_from_loc_type", RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsContractVO.getFromLocType())).toUpperCase());
            //            inParameters.put("p_i_v_from_location", RutDatabase.stringToDb(ijsContractVO.getFromLocation()).toUpperCase());
            //            inParameters.put("p_i_v_from_terminal", RutDatabase.stringToDb(ijsContractVO.getFromTerminal()).toUpperCase());
            //            inParameters.put("p_i_v_to_loc_type", RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsContractVO.getToLocType())).toUpperCase());
            //            inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(ijsContractVO.getToLocation()).toUpperCase());
            //            inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(ijsContractVO.getToTerminal()).toUpperCase());
            //            inParameters.put("p_i_v_hours", ijsContractVO.getHours());
            //            inParameters.put("p_i_v_days", ijsContractVO.getDays());
            //            inParameters.put("p_i_v_distance", ijsContractVO.getDistance());
            //            inParameters.put("p_i_v_uom", RutDatabase.stringToDb(ijsContractVO.getUom()).toUpperCase());
            //            inParameters.put("p_i_v_remarks", RutDatabase.stringToDb(ijsContractVO.getRemarks()).toUpperCase());
            //            inParameters.put("p_i_v_exempted", RutDatabase.stringToDb(ijsContractVO.isExempted() ? 
            //                                                                      "Y" : 
            //                                                                      "N"));
            //            inParameters.put("p_i_v_contract_no", RutDatabase.stringToDb(ijsContractVO.getContractId()).toUpperCase());
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

//    protected class IjsContractSuspendStoredProcedure extends StoredProcedure {
//        /**Stored Procedure name
//         */
//        private static final String PRR_IJS_CNTR_SUSPEND = 
//            "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_SUSPEND";
//
//        IjsContractSuspendStoredProcedure(JdbcTemplate jdbcTemplate) {
//            super(jdbcTemplate, PRR_IJS_CNTR_SUSPEND);
//            declareParameter(new SqlInOutParameter("p_i_v_contract_no", Types.VARCHAR));
//            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
//            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
//            compile();
//        }
//
//        private String suspendContract(String contractId, String userInfo) {
//            Map outMap = new HashMap();
//            Map inParameters = new HashMap();
//            inParameters.put("p_i_v_contract_no", RutDatabase.stringToDb(contractId).toUpperCase());
//            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
//            outMap = execute(inParameters);
//            return (String)outMap.get("p_o_v_err_cd");
//        }
//    }
//
//    protected class IjsContractCompareStoredProcedure extends StoredProcedure {
//        /**Stored Procedure name
//         */
//        private static final String PRR_IJS_CNTR_COMPARE = 
//            "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_COMPARE";
//
//        IjsContractCompareStoredProcedure(JdbcTemplate jdbcTemplate, 
//                                          RowMapper rowMapper) {
//            super(jdbcTemplate, PRR_IJS_CNTR_COMPARE);
//            declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR, 
//                        rowMapper));
//            declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR, 
//                        rowMapper));
//            declareParameter(new SqlInOutParameter("p_i_v_vend_cd", Types.VARCHAR, 
//                        rowMapper));
//            declareParameter(new SqlInOutParameter("p_i_v_frm_loc", Types.VARCHAR, 
//                        rowMapper));
//            declareParameter(new SqlInOutParameter("p_i_v_frm_trm", Types.VARCHAR, 
//                        rowMapper));
//            declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR, 
//                        rowMapper));
//            declareParameter(new SqlInOutParameter("p_i_v_to_trm", Types.VARCHAR, 
//                        rowMapper));
//            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, 
//                        rowMapper));
//            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, 
//                        rowMapper));
//            declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, 
//                        rowMapper));
//            compile();
//        }
//
//        private List<IjsJoEnquiryDTO> getContractList(IjsJoEnquiryVO ijsContractVO, 
//                                                             String userInfo) {
//            Map outMap = new HashMap();
//            Map inParameters = new HashMap();
//            inParameters.put("p_i_v_start_date", RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getStartDate())).toUpperCase());
//            inParameters.put("p_i_v_end_date", RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getCompleteDate())).toUpperCase());
//            // inParameters.put("p_i_v_vend_cd", RutDatabase.stringToDb(ijsContractVO.getVendorCode()).toUpperCase());
//            //inParameters.put("p_i_v_frm_loc", RutDatabase.stringToDb(ijsContractVO.getFromLocation()).toUpperCase());
//            inParameters.put("p_i_v_frm_trm", RutDatabase.stringToDb(ijsContractVO.getFromTerminal()).toUpperCase());
//            //inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(ijsContractVO.getToLocation()).toUpperCase());
//            //inParameters.put("p_i_v_to_trm", RutDatabase.stringToDb(ijsContractVO.getToTerminal()).toUpperCase());
//            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
//            outMap = execute(inParameters);
//            return (List<IjsJoEnquiryDTO>)outMap.get("p_o_v_ijs_mapping_list");
//        }
//    }


    /*protected class IjsCntrVendorDetailsStrdProce extends StoredProcedure {
        private static final String SQL_RLTD_STR_FOR_VENDOR =
            "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_VENDOR_DETAILS";

        IjsCntrVendorDetailsStrdProce(JdbcTemplate jdbcTemplate,
                                      RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_STR_FOR_VENDOR);
            declareParameter(new SqlInOutParameter("p_i_v_vendor_code", Types.VARCHAR,
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR,
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR,
                        rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_vndr_details_list", OracleTypes.CURSOR,
                        rowMapper));
            compile();
        }

        private List<IjsVendorDetails> getVendorDetailsList(String vendorCode,
                                                            String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_vendor_code", RutDatabase.stringToDb(vendorCode).toUpperCase());
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            return (List<IjsVendorDetails>)outMap.get("p_o_v_ijs_vndr_details_list");
        }
    }

    private class IjsCntrVendorDetailsRowMapper implements RowMapper {

        public IjsVendorDetails mapRow(ResultSet resultSet,
                                       int i) throws SQLException {
            IjsVendorDetails details = new IjsVendorDetails();
            IjsVendorDetails.Contact contact =
                details.new IjsVendorDetails.Contact();
            contact.setEmail(resultSet.getString("email"));
            contact.setPhnExtenstion(resultSet.getString("extension"));
            contact.setPhone1(resultSet.getString("phone1"));
            contact.setPhone2(resultSet.getString("phone2"));
            details.setContact(contact);
            IjsVendorDetails.Address address =
                details.new IjsVendorDetails.Address();
            address.setAdd1(resultSet.getString("add1"));
            address.setAdd2(resultSet.getString("add2"));
            address.setAdd3(resultSet.getString("add3"));
            address.setAdd4(resultSet.getString("add4"));
            details.setAddress(address);
            details.setCity(resultSet.getString("city"));
            details.setCountry(resultSet.getString("country"));
            details.setCreditDays(resultSet.getString("credit_days"));
            details.setCurrency(resultSet.getString("currency"));
            details.setFinanceInterVndr(resultSet.getString("finance_interface_vendor"));
            details.setHeadquatersCode(resultSet.getString("headquaters_code"));
            details.setMainContact(resultSet.getString("main_contact"));
            details.setName2(resultSet.getString("name2"));
            details.setOnlineUser(resultSet.getString("online_user"));
            details.setPurchaseOrderRequired(resultSet.getString("purchase_order_required"));
            details.setResponsibleFSC(resultSet.getString("responsible_fsc"));
            details.setSCACCode(resultSet.getString("scac_code"));
            details.setState(resultSet.getString("state"));
            details.setTitle(resultSet.getString("title"));
            details.setVatRegistration(resultSet.getString("vat_registration"));
            details.setVendor(resultSet.getString("vendor"));
            details.setVendorEdiCode(resultSet.getString("vendor_edi_code"));
            details.setVendorPayToNumber(resultSet.getString("vendor_pay_to_number"));
            details.setVendorType(IjsHelper.getVendorValue(resultSet.getString("vendor_type")));
            details.setZip(resultSet.getString("zip"));
            details.setFinanceInterVndrCode(resultSet.getString("finance_interface_vendor_code"));
            details.setAbbreviation(resultSet.getString("abbrivation"));
            details.setOperatorCode(resultSet.getString("operation_code"));
            details.setNetOff_AROrAP(resultSet.getString("net_off"));
            details.setModeOfPayment(resultSet.getString("payment_mode"));
            details.setAdvancePayment(resultSet.getString("advance_payment"));
            return details;
        }
    }*/
    //##04 END


}
