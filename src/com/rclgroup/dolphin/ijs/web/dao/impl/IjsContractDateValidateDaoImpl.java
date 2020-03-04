package com.rclgroup.dolphin.ijs.web.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.gson.Gson;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractDateValidateDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSumDtlDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSummarySearchDTO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJORoutingLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOSummaryParamVO;

import oracle.jdbc.OracleTypes;

public class IjsContractDateValidateDaoImpl extends IjsBaseDao implements IjsContractDateValidateDao {

	//private IjsContractDateValidateDaoImpl.IjsContractDateValidateProcedure ijsContractDateValidateProcedure;
	
	public static final String JOB_ORDER_NUMBER ="select MAX(FK_JO_NUMBER) from rcltbls.ijs_jo_ra_leg_detail where fk_contract_no = ? ";
	public static final String JOB_ORDER_DATE = "select JO_DATE from rcltbls.ijs_jo_header where PK_JO_NUMBER=?";
	public void initDao() throws Exception {
		super.initDao();

	}

	public IjsContractDateValidateDaoImpl() {

	}
    @Override
	public List<?> dateValidateDao(IjsContractVO ijsContractVO, String session, String userInfo) {
		List<?> list=getDate(ijsContractVO,session,userInfo);
		return list;
	}
	
         private List<?> getDate(IjsContractVO ijsContractVO, 
				String session,String userInfo
	            )
      {     
			String date=null;
			IjsContractVO ijsContractVOVO = new IjsContractVO();
			List ijsContractVOReturn = new ArrayList();
            String contractId = ijsContractVO.getContractId();
		
		
		
		  String jobOrderNum = (String) getJdbcTemplate().queryForObject(
				  JOB_ORDER_NUMBER, new Object[] { contractId }, String.class);
		  
		    
		    if(jobOrderNum!=null) {
		    int  joDate = getJdbcTemplate().queryForInt(JOB_ORDER_DATE, 
					new Object[] {jobOrderNum});
		    System.out.println(joDate);
			
		    date=String.valueOf(joDate);
		    String year = date.substring(0, 4);
		    String month = date.substring(4, 6);
		    String day = date.substring(6, 8);
		    String finalDate= day+"/"+month+"/"+year;
		  
	            // date will come in dd/mm/yyyy format and db is expecting in the format of yyyy/mm/dd
	       
	      
		    ijsContractVOVO.setEndDate(finalDate);
		    ijsContractVOReturn.add(ijsContractVOVO);
		    }
         return ijsContractVOReturn;
		}
		
	}
	
