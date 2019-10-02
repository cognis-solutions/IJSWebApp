package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsCommonDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractRateDao;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;
import com.rclgroup.dolphin.ijs.web.vo.IjsEquipmetLookUpVO;

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
import org.springframework.util.StringUtils;

public class IjsCommonJdbcDao extends IjsBaseDao implements IjsCommonDao {
    public IjsCommonJdbcDao() {
    }
    private GetIjsViewJoStoredProcedure getIjsViewJoStoredProcedure;
  //CR#03 START
    private IjsCommonJdbcDao.IjsValidateEquipExistanceProcedure ijsValidateEquipExistance;
    private IjsCommonJdbcDao.IjsValidateEquipLocProcedure ijsValidateEquipLocProcedure;
    private IjsCommonJdbcDao.IjsValidateEquipReverseLocProcedure ijsValidateEquipReverseLocProcedure;
    private IjsCommonJdbcDao.IjsValidateEquipInJoProcedure ijsValidateEquipInJoProcedure;
    private IjsCommonJdbcDao.IjsGetEquipDetailProcedure ijsGetEquipDetailProcedure;
    private static final String EQUIP_IN_DB="EQUIP_IN_DB";
    private static final String EQUIP_LOC="EQUIP_LOC";
    private static final String EQUIP_WITH_LOC_REVERSE="EQUIP_WITH_LOC_REVERSE";
    private static final String EQUIP_EXIST_IN_JO="EQUIP_EXIST_IN_JO";
    //CR#03 END
    public void initDao() throws Exception {
        //##01 BEGIN
        super.initDao();
        getIjsViewJoStoredProcedure = 
                new GetIjsViewJoStoredProcedure(getJdbcTemplate());
      //CR#03 START
  		ijsValidateEquipExistance=new IjsCommonJdbcDao.IjsValidateEquipExistanceProcedure(getJdbcTemplate(),
          		new IjsCommonJdbcDao.IjsValidEquipRowMapper());
          ijsValidateEquipLocProcedure=new IjsCommonJdbcDao.IjsValidateEquipLocProcedure(getJdbcTemplate(),
          		new IjsCommonJdbcDao.IjsValidEquipRowMapper());
          ijsValidateEquipReverseLocProcedure=new IjsCommonJdbcDao.IjsValidateEquipReverseLocProcedure(getJdbcTemplate(),
          		new IjsCommonJdbcDao.IjsValidEquipRowMapper());
          ijsValidateEquipInJoProcedure=new IjsCommonJdbcDao.IjsValidateEquipInJoProcedure(getJdbcTemplate(),
          		new IjsCommonJdbcDao.IjsValidEquipRowMapper());
          ijsGetEquipDetailProcedure=new IjsCommonJdbcDao.IjsGetEquipDetailProcedure(getJdbcTemplate(),
          		new IjsCommonJdbcDao.IjsEquipmentLookUpRowMapper());
   //CR#03 END
                                                       
        }

    public String getViewJOUrl(String userId) {
        return getIjsViewJoStoredProcedure.getIjsViewJoUrl(userId);
    }

    protected class GetIjsViewJoStoredProcedure extends StoredProcedure {
        /**Stored Procedure name
         */
        private static final String SQL_IJS_VIEW_JO = 
            "PCR_IJS_CNTR_COMMON.PRR_IJS_VIEW_JO_URL";

        GetIjsViewJoStoredProcedure(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_IJS_VIEW_JO);
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));//used for user authorisation
            declareParameter(new SqlOutParameter("p_o_v_error_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_jo_url", Types.VARCHAR));
            compile();

        }

        public String getIjsViewJoUrl(String userId) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
            outMap = execute(inParameters);
            String joUrl = (String)outMap.get("p_o_v_jo_url");
            System.out.print("joUrl------------------->"+joUrl);
            
            return "http://"+joUrl;
        }
    }
    //##CR 03 START
    protected class IjsValidateEquipExistanceProcedure extends StoredProcedure {
        private static final String SQL_IJS_EQUIP_EXIST_VALIDATION = "PCR_IJS_CNTR_COMMON.PRR_IJS_EQUIP_EXIST_VALIDATION";

        IjsValidateEquipExistanceProcedure(JdbcTemplate jdbcTemplate, 
                                         RowMapper rowMapper) {
            super(jdbcTemplate, SQL_IJS_EQUIP_EXIST_VALIDATION);
            declareParameter(new SqlInOutParameter("p_i_v_equip_ids", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_empty_laden", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_equip_valid", OracleTypes.CURSOR, rowMapper));
            compile();
        }

            private List<String> getValidEquipmentList(List<String> equipLst, String adhocType) {

            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_equip_ids", RutDatabase.stringToDb(StringUtils.collectionToCommaDelimitedString(equipLst)).toUpperCase());
            inParameters.put("p_i_v_empty_laden", RutDatabase.stringToDb(adhocType).toUpperCase());
            outMap = execute(inParameters);
            return (List<String>)outMap.get("p_o_v_ijs_equip_valid");
        }
    }
    protected class IjsValidateEquipLocProcedure extends StoredProcedure {
        private static final String SQL_IJS_VALIDATE_EQUIP_LOC = "PCR_IJS_CNTR_COMMON.PRR_IJS_VALIDATE_EQUIP_LOC";

        IjsValidateEquipLocProcedure(JdbcTemplate jdbcTemplate, 
                                         RowMapper rowMapper) {
            super(jdbcTemplate, SQL_IJS_VALIDATE_EQUIP_LOC);
            declareParameter(new SqlInOutParameter("p_i_v_equip_ids", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_empty_laden", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_contract_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_equip_valid", OracleTypes.CURSOR, rowMapper));
            compile();
        	}

            private List<String> getValidEquipmentList(List<String> equipLst, String adhocType,String contractId) {

            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_equip_ids", RutDatabase.stringToDb(StringUtils.collectionToCommaDelimitedString(equipLst)).toUpperCase());
            inParameters.put("p_i_v_empty_laden", RutDatabase.stringToDb(adhocType).toUpperCase());
            inParameters.put("p_i_v_contract_id", RutDatabase.stringToDb(contractId).toUpperCase());
            outMap = execute(inParameters);
            return (List<String>)outMap.get("p_o_v_ijs_equip_valid");
        }
    }
    protected class IjsValidateEquipReverseLocProcedure extends StoredProcedure {
        private static final String SQL_IJS_VAL_LOC_OF_EQUIP_REVERSE = "PCR_IJS_CNTR_COMMON.PRR_IJS_VAL_LOC_OF_EQUIP_REVERSE";

        IjsValidateEquipReverseLocProcedure(JdbcTemplate jdbcTemplate, 
                                         RowMapper rowMapper) {
            super(jdbcTemplate, SQL_IJS_VAL_LOC_OF_EQUIP_REVERSE);
            declareParameter(new SqlInOutParameter("p_i_v_equip_ids", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_empty_laden", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_contract_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_equip_valid", OracleTypes.CURSOR, rowMapper));
            compile();
        	}

            private List<String> getValidEquipmentList(List<String> equipLst, String adhocType,String contractId) {

            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_equip_ids", RutDatabase.stringToDb(StringUtils.collectionToCommaDelimitedString(equipLst)).toUpperCase());
            inParameters.put("p_i_v_empty_laden", RutDatabase.stringToDb(adhocType).toUpperCase());
            inParameters.put("p_i_v_contract_id", RutDatabase.stringToDb(contractId).toUpperCase());
            outMap = execute(inParameters);
            return (List<String>)outMap.get("p_o_v_ijs_equip_valid");
        }
    }
    protected class IjsValidateEquipInJoProcedure extends StoredProcedure {
        private static final String SQL_IJS_VALIDATE_EQUIP_IN_JO = "PCR_IJS_CNTR_COMMON.PRR_IJS_VALIDATE_EQUIP_IN_JO";

        IjsValidateEquipInJoProcedure(JdbcTemplate jdbcTemplate, 
                                         RowMapper rowMapper) {
            super(jdbcTemplate, SQL_IJS_VALIDATE_EQUIP_IN_JO);
            declareParameter(new SqlInOutParameter("p_i_v_equip_ids", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_empty_laden", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_equip_invalid", OracleTypes.CURSOR, rowMapper));
            compile();
        	}

            private List<String> getInValidEquipmentList(List<String> equipLst, String adhocType) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_equip_ids", RutDatabase.stringToDb(StringUtils.collectionToCommaDelimitedString(equipLst)).toUpperCase());
            inParameters.put("p_i_v_empty_laden", RutDatabase.stringToDb(adhocType).toUpperCase());
            outMap = execute(inParameters);
            return (List<String>)outMap.get("p_o_v_ijs_equip_invalid");
        }
    }
    protected class IjsGetEquipDetailProcedure extends StoredProcedure {
        private static final String SQL_IJS_IJS_EQUIP_DETAIL = "PCR_IJS_CNTR_COMMON.PRR_IJS_EQUIP_DETAIL";

        IjsGetEquipDetailProcedure(JdbcTemplate jdbcTemplate, 
                                         RowMapper rowMapper) {
            super(jdbcTemplate, SQL_IJS_IJS_EQUIP_DETAIL);
            declareParameter(new SqlInOutParameter("p_i_v_equip_ids", Types.VARCHAR, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_empty_laden", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_equip_detail", OracleTypes.CURSOR, rowMapper));
            compile();
        	}

            private List<IjsEquipmetLookUpVO> getValidEquipmentDetailList(List<String> equipLst, String adhocType) {

            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_equip_ids", RutDatabase.stringToDb(StringUtils.collectionToCommaDelimitedString(equipLst)).toUpperCase());
            inParameters.put("p_i_v_empty_laden", RutDatabase.stringToDb(adhocType).toUpperCase());
            outMap = execute(inParameters);
            return (List<IjsEquipmetLookUpVO>) outMap.get("p_o_v_ijs_equip_detail");
        }
    }
    private class IjsValidEquipRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            String validEqNo = new String();
               
            try {
            	validEqNo=resultSet.getString("EQUIP_NO");
            } catch (SQLException e) {
                //TO-DO                
                e.printStackTrace();
            }
            return validEqNo;
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
              
            } catch (SQLException e) {
                //TO-DO                
                e.printStackTrace();
            }
            return vendorModel;
        }
}
	//##CR 03 START
		@Override
		public List<String> validateEquipment(
			  List<String> excelUploadTemplateList, String adhocType,String contractId,String validate) {
			  List<String> validEquip=new ArrayList<>();
			  List<String> inValidEquip=new ArrayList<>();
			  switch(validate){
			  case EQUIP_IN_DB: 
				  validEquip=ijsValidateEquipExistance.getValidEquipmentList(excelUploadTemplateList, adhocType);
				  break;
			  case EQUIP_LOC: 
				  validEquip=ijsValidateEquipLocProcedure.getValidEquipmentList(excelUploadTemplateList, adhocType,contractId);
				  break;
			  case EQUIP_WITH_LOC_REVERSE: 
				  validEquip=ijsValidateEquipReverseLocProcedure.getValidEquipmentList(excelUploadTemplateList, adhocType,contractId);
				  break;
			  case EQUIP_EXIST_IN_JO: 
				  inValidEquip=ijsValidateEquipInJoProcedure.getInValidEquipmentList(excelUploadTemplateList, adhocType);
				  return inValidEquip;
			  }
	          excelUploadTemplateList.removeAll(validEquip);
	         return excelUploadTemplateList;
		}

		@Override
		public List<IjsEquipmetLookUpVO> getValidEquipmentDetail(
				List<String> excelUploadTemplateList, String adhocType) {
			return ijsGetEquipDetailProcedure.getValidEquipmentDetailList(excelUploadTemplateList, adhocType);
		}
	    //##CR 03 END
}
