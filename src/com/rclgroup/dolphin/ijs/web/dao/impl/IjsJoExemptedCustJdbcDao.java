package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsJoExemptedCustDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsJoExemptedCustSearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoExemptedCustSearchParamVO;
//import com.rclgroup.dolphin.ijs.web.vo.IjsJoExemptedCustSearchVO;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;

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

public class IjsJoExemptedCustJdbcDao extends IjsBaseDao implements IjsJoExemptedCustDao {
    private IjsJoExemptedCustJdbcDao.GetIjsSearchStoredProcedure getIjsSearchStoredProcedure; //##01
    
    private IjsJoExemptedCustJdbcDao.IjsAddCustStoredProcedure ijsAddCustStoredProcedure; //##03
     private IjsJoExemptedCustJdbcDao.IjsEditCustStoredProcedure ijsEditCustStoredProcedure; //##03
      private IjsJoExemptedCustJdbcDao.IjsDeleteCustStoredProcedure ijsDeleteCustStoredProcedure; //##03
    
    
    public void initDao() throws Exception {
        //##01 BEGIN
        super.initDao();
        getIjsSearchStoredProcedure = 
                new IjsJoExemptedCustJdbcDao.GetIjsSearchStoredProcedure(getJdbcTemplate(), 
                                                                               new IjsJoExemptedCustJdbcDao.IjsSearchCustSearchRowMapper());
       
                                                                                
        ijsAddCustStoredProcedure = 
                new IjsJoExemptedCustJdbcDao.IjsAddCustStoredProcedure(getJdbcTemplate(),
                                          new IjsJoExemptedCustJdbcDao.IjsSearchCustSearchRowMapper()); //##03
                                          
       ijsEditCustStoredProcedure = 
               new IjsJoExemptedCustJdbcDao.IjsEditCustStoredProcedure(getJdbcTemplate(),
                                         new IjsJoExemptedCustJdbcDao.IjsSearchCustSearchRowMapper()); //##03
      
      ijsDeleteCustStoredProcedure = 
              new IjsJoExemptedCustJdbcDao.IjsDeleteCustStoredProcedure(getJdbcTemplate(),
                                        new IjsJoExemptedCustJdbcDao.IjsSearchCustSearchRowMapper()); //##03
                                          
                                                                     
        
        }
    //##01 BEGIN

     
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
    public String addEditCust(IjsJoExemptedCustSearchParamVO searchVo,String userId,String action) throws IJSException {
        String errorCode = "";
        
        if(("exemptAdd").equalsIgnoreCase(action)) {
            errorCode =
                        ijsAddCustStoredProcedure.addSpCustomers(searchVo,userId,action);
        } else if (("exemptEdit").equalsIgnoreCase(action)) {
            errorCode =
                        ijsEditCustStoredProcedure.editJO(searchVo,userId,action);
        }
        if (errorCode != null && errorCode.contains("DB_IJS_CUST_EX_10016")) {
            System.out.println("Error Code is  : " + errorCode);
            throw new IJSException(IjsErrorCode.DB_IJS_RATE_EX_10016.getErrorCode());
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
    public String deleteCust(List<IjsJoExemptedCustSearchParamVO> searchVo,String userId,String action) throws IJSException {
        String errorCode = "";
        
        if (("exemptDelete").equalsIgnoreCase(action)) {
            errorCode =
                        ijsDeleteCustStoredProcedure.deleteJO(searchVo,userId,action); 
        }
        //String errorCode = (String)outMap.get("p_o_v_err_cd");
        if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10006")) {
            System.out.println("Error Code is  : " + errorCode);
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

    public List<IjsJoExemptedCustSearchDTO> findAllCust(String userId, 
                                                         IjsJoExemptedCustSearchParamVO searchParam) throws IJSException {
        List<IjsJoExemptedCustSearchDTO> ijsJoExemptedCustSearchDTO =
            
            getIjsSearchStoredProcedure.findAllCust(userId, 
                                                                 searchParam);
            
        if (ijsJoExemptedCustSearchDTO == null || ijsJoExemptedCustSearchDTO.isEmpty()) {
            IJSException ijsException = new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
            throw ijsException;
        }
        return ijsJoExemptedCustSearchDTO;
    }
    //##03 END
    //##04 BEGIN

     

    


    //##05 END
    //##01 BEGIN

    protected class GetIjsSearchStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_CUST_JO_SEARCH = 
            "PCR_IJS_CNTR_EXM_CUST.PRR_IJS_CNT_CST_EXM_CST_SRCH";

        GetIjsSearchStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, SQL_CUST_JO_SEARCH);
            
            declareParameter(new SqlInOutParameter("p_i_v_routing_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_contract_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cost_hdr_seq", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cost_dtl_seq", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_customer_id", Types.VARCHAR, 
                        rowMapper));
                        
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR,
                       rowMapper));
            declareParameter(new SqlOutParameter("p_io_v_err_cd", Types.VARCHAR, 
                        rowMapper));

//            declareParameter(new SqlOutParameter("p_o_v_user_type", Types.VARCHAR, 
//                        rowMapper));//used for user authorisation
            declareParameter(new SqlOutParameter("p_o_v_ijs_cust_list", OracleTypes.CURSOR, 
                        rowMapper));
            compile();

        }

     public List<IjsJoExemptedCustSearchDTO> findAllCust(String userId, 
                                                                         IjsJoExemptedCustSearchParamVO searchParam) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();

            inParameters.put("p_i_v_routing_id", RutDatabase.integerToDb(searchParam.getRoutingNumber().toString()));
            inParameters.put("p_i_v_contract_id", RutDatabase.stringToDb(searchParam.getContractId()));
            inParameters.put("p_i_v_cost_hdr_seq", RutDatabase.integerToDb(searchParam.getCostRateSequenceNum().toString()));
            inParameters.put("p_i_v_cost_dtl_seq", RutDatabase.stringToDb(searchParam.getDetailSeqNumbers()));
            inParameters.put("p_i_v_customer_id", RutDatabase.stringToDb(searchParam.getCustId()));
            
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());

             outMap = execute(inParameters);
             return (List<IjsJoExemptedCustSearchDTO>)outMap.get("p_o_v_ijs_cust_list");
        }
    }
    
    private class IjsSearchCustSearchRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsJoExemptedCustSearchDTO ijsJoExemptedCustSearchDTO = 
                new IjsJoExemptedCustSearchDTO();
            
            try {
                ijsJoExemptedCustSearchDTO.setCustId(resultSet.getString("CUST_CD"));
                ijsJoExemptedCustSearchDTO.setCustName(resultSet.getString("CUST_NAME"));
                ijsJoExemptedCustSearchDTO.setCustType(IjsHelper.getCustomerType(resultSet.getString("CUST_TYPE")));
                String stsValue = "";
                if(resultSet.getString("STATUS").toString().equalsIgnoreCase("A")) {
                    stsValue="Active";
                } else if(resultSet.getString("STATUS").toString().equalsIgnoreCase("S")) {
                    stsValue="Suspended"; 
                }
                ijsJoExemptedCustSearchDTO.setRemark(resultSet.getString("REMARK"));
                ijsJoExemptedCustSearchDTO.setStatus(stsValue);
                ijsJoExemptedCustSearchDTO.setRoutingId(resultSet.getString("ROUTING_NO"));
                ijsJoExemptedCustSearchDTO.setCost_seq_no(resultSet.getString("COST_SEQ_NUM"));
           
            } catch (SQLException e) {
                
                e.printStackTrace();
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

            return ijsJoExemptedCustSearchDTO;
        }
    }
    
    
    
    //##03 BEGIN

    protected class IjsAddCustStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_CALL_IJS_ADD_SP_CUSTOMERS = 
            "PCR_IJS_CNTR_EXM_CUST.PRR_IJS_CNT_CST_EXM_CST_ADD";

        IjsAddCustStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_CALL_IJS_ADD_SP_CUSTOMERS);
            declareParameter(new SqlInOutParameter("p_i_v_routing_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cost_hdr_seq", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cust_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_contract_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cust_type", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_status", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_remark", Types.VARCHAR, 
                        rowMapper));
            
            declareParameter(new SqlInOutParameter("p_i_v_cost_detail_ids", Types.VARCHAR, 
                    rowMapper));
                    
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR,
                       rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, 
                        rowMapper));
            compile();

        }

        private String addSpCustomers(IjsJoExemptedCustSearchParamVO searchVo,String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            
            //for (int i = 0; i < searchVo.size() ; i++) {
                
            inParameters.put("p_i_v_routing_id", RutDatabase.integerToDb(searchVo.getRoutingNumber().toString()));
            inParameters.put("p_i_v_cost_hdr_seq", RutDatabase.integerToDb(searchVo.getCostRateSequenceNum().toString()));
            inParameters.put("p_i_v_cust_id", RutDatabase.stringToDb(searchVo.getCustId()));
            inParameters.put("p_i_v_contract_id", RutDatabase.stringToDb(searchVo.getContractId()));
            inParameters.put("p_i_v_cust_type", RutDatabase.stringToDb(IjsHelper.getCustomerTypeCode(searchVo.getCustType())));
            inParameters.put("p_i_v_status", RutDatabase.stringToDb(searchVo.getStatus()));
            inParameters.put("p_i_v_remark", RutDatabase.stringToDb(searchVo.getRemark()));
            inParameters.put("p_i_v_cost_detail_ids", RutDatabase.stringToDb(searchVo.getDetailSeqNumbers()));
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            //}
            return (String)outMap.get("p_o_v_err_cd") == null ? "MSG" : (String)outMap.get("p_o_v_err_cd");
        }
    }
    
    protected class IjsEditCustStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_CALL_IJS_EDIT = 
            "PCR_IJS_CNTR_EXM_CUST.PRR_IJS_CNT_CST_EXM_CST_EDIT";

        IjsEditCustStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_CALL_IJS_EDIT);
            declareParameter(new SqlInOutParameter("p_i_v_routing_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cost_hdr_seq", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cust_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_contract_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cust_type", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_status", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_remark", Types.VARCHAR, 
                        rowMapper));
            
            declareParameter(new SqlInOutParameter("p_i_v_cost_detail_ids", Types.VARCHAR, 
                    rowMapper));           
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR,
                       rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, 
                        rowMapper));
            compile();

        }

        private String editJO(IjsJoExemptedCustSearchParamVO searchVo,String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            
            //for (int i = 0; i < searchVo.size() ; i++) {
                
            inParameters.put("p_i_v_routing_id", RutDatabase.integerToDb(searchVo.getRoutingNumber().toString()));
            inParameters.put("p_i_v_cost_hdr_seq", RutDatabase.integerToDb(searchVo.getCostRateSequenceNum().toString()));
            inParameters.put("p_i_v_cust_id", RutDatabase.stringToDb(searchVo.getCustId()));
            inParameters.put("p_i_v_contract_id", RutDatabase.stringToDb(searchVo.getContractId()));
            inParameters.put("p_i_v_cust_type", RutDatabase.stringToDb(IjsHelper.getCustomerTypeCode(searchVo.getCustType())));
            inParameters.put("p_i_v_status", RutDatabase.stringToDb(searchVo.getStatus()));
            inParameters.put("p_i_v_remark", RutDatabase.stringToDb(searchVo.getRemark()));
            inParameters.put("p_i_v_cost_detail_ids", RutDatabase.stringToDb(searchVo.getDetailSeqNumbers()));
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
    
            outMap = execute(inParameters);
            //}
            return (String)outMap.get("p_o_v_err_cd") == null ? "MSG" : (String)outMap.get("p_o_v_err_cd");
        }
    }
    
    protected class IjsDeleteCustStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String PRR_CALL_IJS_DELETE = 
            "PCR_IJS_CNTR_EXM_CUST.PRR_IJS_CNT_CST_EXM_CST_DEL";

        IjsDeleteCustStoredProcedure(JdbcTemplate jdbcTemplate, 
                                    RowMapper rowMapper) {
            super(jdbcTemplate, PRR_CALL_IJS_DELETE);
            declareParameter(new SqlInOutParameter("p_i_v_routing_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cost_hdr_seq", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cust_id", Types.VARCHAR, 
                        rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cust_type", Types.VARCHAR, 
                    rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_cst_rate_dtl_seq", Types.VARCHAR, 
                    rowMapper));
                        
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR,
                       rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, 
                        rowMapper));
            compile();

        }

        private String deleteJO(List<IjsJoExemptedCustSearchParamVO> searchVo,String userInfo,String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            
            for (int i = 0; i < searchVo.size() ; i++) {
                
                inParameters.put("p_i_v_routing_id", RutDatabase.integerToDb(searchVo.get(i).getRoutingNumber().toString()));
                inParameters.put("p_i_v_cost_hdr_seq", RutDatabase.integerToDb(searchVo.get(i).getCostRateSequenceNum().toString()));
                inParameters.put("p_i_v_cust_id", RutDatabase.stringToDb(searchVo.get(i).getCustId()));
                inParameters.put("p_i_v_cust_type", RutDatabase.stringToDb(IjsHelper.getCustomerTypeCode(searchVo.get(i).getCustType())));
                inParameters.put("p_i_v_cst_rate_dtl_seq", RutDatabase.stringToDb(searchVo.get(i).getDetailSeqNumbers()));
                
                inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
    
            outMap = execute(inParameters);
            }
            return (String)outMap.get("p_o_v_err_cd") == null ? "MSG" : (String)outMap.get("p_o_v_err_cd");
        }
    }
    
    

    
    
}
