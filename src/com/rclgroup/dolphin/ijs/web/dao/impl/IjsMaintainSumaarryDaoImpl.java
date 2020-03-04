package com.rclgroup.dolphin.ijs.web.dao.impl;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.rclgroup.dolphin.ijs.web.dao.IJSProcessNewSaveDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsMaintainSumaarryDao;
import com.rclgroup.dolphin.ijs.web.vo.IjsJORoutingLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSumarryVO;

public class IjsMaintainSumaarryDaoImpl extends IjsBaseDao implements IjsMaintainSumaarryDao 
{
	private IjsMaintainSumaarryDaoImpl.IjsJOSumarryProcedure ijsJOSumarryProcedure;
	
	public void initDao() throws Exception {
		super.initDao();

		ijsJOSumarryProcedure = new IjsMaintainSumaarryDaoImpl.IjsJOSumarryProcedure(getJdbcTemplate(),
				new IjsMaintainSumaarryDaoImpl.IjsJOSumarryRowMapper());
		
	}
	
	
	

	@Override
	public List<?> joSummary( String session, String userId) {
	
		List<IjsSumarryVO> list = ijsJOSumarryProcedure.getSummaryList(session,userId);
		
		return list;
	}
	
	
	protected class IjsJOSumarryProcedure extends StoredProcedure
	{
		   private static final String SQL_PROCESS_JO_SUMMARY = 
		            "";
		            

		   IjsJOSumarryProcedure(JdbcTemplate jdbcTemplate, 
		                                    RowMapper rowMapper) {
		            super(jdbcTemplate, SQL_PROCESS_JO_SUMMARY);
		            
		            
		      compile();      
		   }
		   
		 private List<IjsSumarryVO>   getSummaryList(String session,String userId)
		 {
			 return null;
		 }
}
	private class IjsJOSumarryRowMapper implements RowMapper {

		public Object mapRow(ResultSet resultSet, int row) 
		{  
			IjsSumarryVO ijsSumarryVO = new IjsSumarryVO();
		
			return ijsSumarryVO;
		        }
		}
}
