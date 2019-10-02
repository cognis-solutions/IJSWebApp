package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IJSResultContainerUpdateDaoImpl extends IjsBaseDao{
	
	public static final String  query = " UPDATE VASAPPS.BKP009 SET MET_WEIGHT = ? "
			+ " where BICTRN = ? AND MET_WEIGHT = ? ";
	
	
	
	
			 
	public void updateContainerWeight(Map outMap) {
		System.out.println("updateContainerWeight() strted."+outMap);
		String ContainerNO = (String) outMap.get("container");
		String oldContainerWeight = (String) outMap.get("oldContainerWeight");
		String newContainerWeight = (String) outMap.get("actualContainerWeight");
		String containerSize =(String) outMap.get("containerSize");
		String containerType = (String) outMap.get("containerType");
		String specialHandling = (String) outMap.get("specialHandling");
		String bookingOrBLNumber = (String) outMap.get("bkgOrBLNumber");
		String bookingOrBLType = (String) outMap.get("bookingOrBLType");
		String fromLocation = (String) outMap.get("fromLocation");
		String fromTerminal = (String) outMap.get("fromTerminal");
		String toLocation = (String) outMap.get("toLocation");
		String toTerminal = (String) outMap.get("toTerminal");
		String vendorCode = (String) outMap.get("vendorCode");
		
		if("BL".equals(bookingOrBLType.trim())) {
			System.out.println("inside bl");
		}
		else if("Booking".equals(bookingOrBLType.trim())) {
			System.out.println("inside booking");
		}
		
		System.out.println(query);
		getJdbcTemplate().update(query, new Object[] { newContainerWeight,ContainerNO,oldContainerWeight});
		//getJdbcTemplate().query(QUERY_SELECT_NET_WEIGHT, new Object[] {  });
		System.out.println("DONE");
	}
	
	private class IJSContainerUpdateRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			
			return null;
		}
		
	}

}
