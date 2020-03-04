/*-----------------------------------------------------------------------------------------------------------
IjsJOBookingBLSearchAction.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 02/11/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 02/11/17  NIIT       IJS            Booking/BL Search functionality 
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.dao.jo.impl;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.jo.IjsJOBookingBLSearchDao;
import com.rclgroup.dolphin.ijs.web.entity.jo.IjsJOBookingBLSearchDTO;

import com.rclgroup.dolphin.ijs.web.vo.jo.IjsJOBookingBLSearchVO;

import com.rclgroup.dolphin.ijs.web.util.RutDatabase;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class IjsJOBookingBLSearchJdbcDao extends IjsBaseDao implements IjsJOBookingBLSearchDao{
    private IjsJOBookingBLSearchProce ijsJOBookingBLSearchProce;  
    public void initDao() throws Exception {
        super.initDao();
        ijsJOBookingBLSearchProce = new IjsJOBookingBLSearchProce(getJdbcTemplate() 
            , new IjsJOBookingBLSearchRowMapper());
    }
    /**
     * searchJOBookingBL method for fetching booking/bl records
     * @param ijsJOBookingBLSearchVO
     * @param userInfo
     */
    public void searchJOBookingBL(IjsJOBookingBLSearchVO ijsJOBookingBLSearchVO, 
                                  String userInfo) {
        ijsJOBookingBLSearchProce.getSearchResults(ijsJOBookingBLSearchVO, userInfo);
    }
    protected class IjsJOBookingBLSearchProce extends StoredProcedure {
        /** Stored Procedure name */
        private static final String SQL_RLTD_JO_BOOKING_BL_SEARCH = 
            "PCR_IJS_JO_BKG_BL_MAINTENANACE.PRR_IJS_JO_BKG_BL_SEARCH";
        IjsJOBookingBLSearchProce(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_JO_BOOKING_BL_SEARCH);
            declareParameter(new SqlInOutParameter("p_i_v_trans_mod_type", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_booking_type", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_bkg_bl_number", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_job_order_type", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vendor", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_service", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_vessel", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_voyage", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_load_port", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_discharge_port", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_frm_loc_typ", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_frm_loc", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_frm_trm", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc_typ", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_to_trm", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_record_start", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_record_end", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_info", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_error_code", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_jo_search_list",OracleTypes.CURSOR, rowMapper));
            
        }

        private void getSearchResults(IjsJOBookingBLSearchVO ijsJOBookingBLSearchVO, 
                                      String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_trans_mod_type",RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getTransportType()).toUpperCase());
            inParameters.put("p_i_v_booking_type",RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getBookingType()).toUpperCase());
            inParameters.put("p_i_v_bkg_bl_number",RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getBkgBLNumber()).toUpperCase());
            inParameters.put("p_i_v_job_order_type",RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getJobOrderType()).toUpperCase());
            inParameters.put("p_i_v_vendor",RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getVendor()).toUpperCase());
            inParameters.put("p_i_v_service",RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getVessel()).toUpperCase());
            inParameters.put("p_i_v_vessel",RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getVoyage()).toUpperCase());
            inParameters.put("p_i_v_voyage",RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getService()).toUpperCase());
            inParameters.put("p_i_v_start_date",RutDatabase.stringToDb(IjsHelper.getDateFromRange(
                ijsJOBookingBLSearchVO.getDateRange(),"START_DATE")).toUpperCase());
            inParameters.put("p_i_v_end_date",RutDatabase.stringToDb(IjsHelper.getDateFromRange(
                ijsJOBookingBLSearchVO.getDateRange(),"END_DATE")).toUpperCase());
            inParameters.put("p_i_v_load_port",RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getLoadPort()).toUpperCase());
            inParameters.put("p_i_v_discharge_port",RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getDischargePort()).toUpperCase());
            inParameters.put("p_i_v_frm_loc_typ" , RutDatabase.stringToDb(IjsHelper.getLocationCode(
                ijsJOBookingBLSearchVO.getFromLocType())));
            inParameters.put("p_i_v_frm_loc" , RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getFromLocation()).toUpperCase());
            inParameters.put("p_i_v_frm_trm" , RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getFromTerminal()).toUpperCase());
            inParameters.put("p_i_v_to_loc_typ" , RutDatabase.stringToDb(IjsHelper.getLocationCode(
                ijsJOBookingBLSearchVO.getToLocType())));
            inParameters.put("p_i_v_to_loc" , RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getToLocation()).toUpperCase());
            inParameters.put("p_i_v_to_trm" , RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getToTerminal()).toUpperCase());
            inParameters.put("p_i_v_to_trm" , RutDatabase.stringToDb(ijsJOBookingBLSearchVO
                .getToTerminal()).toUpperCase());
            inParameters.put("p_i_v_record_start" , ijsJOBookingBLSearchVO
                .getRecordStart());
            inParameters.put("p_i_v_record_end" , ijsJOBookingBLSearchVO
                .getRecordEnd());
            inParameters.put("p_i_v_user_info" , RutDatabase.stringToDb(userInfo).toUpperCase());
            
            outMap = execute(inParameters);
        }
    }
    private class IjsJOBookingBLSearchRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsJOBookingBLSearchDTO resultVO = new IjsJOBookingBLSearchDTO();
            try {
                resultVO.setBkgOrBLNumber(resultSet.getString(""));
                resultVO.setBkgOrBLType(resultSet.getString(""));
                resultVO.setBookingStatus(resultSet.getString(""));
                resultVO.setCntSize(resultSet.getString(""));
                resultVO.setCntSplHandCount(resultSet.getInt(""));
                resultVO.setCntSplHandling(resultSet.getString(""));
                resultVO.setCntType(resultSet.getString(""));
                resultVO.setDirection(resultSet.getString(""));
                resultVO.setEmptyCntAvailable(resultSet.getInt(""));
                resultVO.setEmptyCntInJO(resultSet.getInt(""));
                resultVO.setEmptyCntTotal(resultSet.getInt(""));
                resultVO.setEndDate(resultSet.getString(""));
                resultVO.setFromTerminal(resultSet.getString(""));
                resultVO.setLadenCntAvailable(resultSet.getInt(""));
                resultVO.setLadenCntInJO(resultSet.getInt(""));
                resultVO.setLadenCntTotal(resultSet.getInt(""));
                resultVO.setService(resultSet.getString(""));
                resultVO.setStartDate(resultSet.getString(""));
                resultVO.setToTerminal(resultSet.getString(""));
                resultVO.setTransportMode(resultSet.getString(""));
                resultVO.setVessel(resultSet.getString(""));
                resultVO.setVoyage(resultSet.getString(""));
            } catch (SQLException e) {
                // TO-DO
                e.printStackTrace();
            }
            return null;
        }
    }
}
