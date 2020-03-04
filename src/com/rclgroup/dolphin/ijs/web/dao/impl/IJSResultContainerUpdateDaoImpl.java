package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;

import java.util.Date;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class IJSResultContainerUpdateDaoImpl extends IjsBaseDao{
	
	private static final String trial = "update vasapps.bkp009 cnt set cnt.met_weight = ? "
			+ "where cnt.bictrn = ?"
			+ " and cnt.met_weight = ?";
	
	private static final String COUNT_BOOKING_CONT_NUMBER = "select count(*) from vasapps.bkp009 cntr "
			+ "where cntr.bictrn = ? ";
	
	private static final String  BOOKING_UPDATE_QUERY = " UPDATE vasapps.bkp009 cnt" + 
			" SET" + 
			"    cnt.met_weight = ?" + 
			" WHERE" + 
			"    EXISTS (" + 
			"        SELECT" + 
			"            CASE" + 
			"                WHEN cnt.bictrn IS NULL THEN" + 
			"                    cnt.bicsze" + 
			"                    || cnt.bicntp" + 
			"                    || cnt.supplier_sqno" + 
			"                    || cnt.biseqn" + 
			"                ELSE" + 
			"                    cnt.bictrn" + 
			"            END AS container_no," + 
			"            cnt.met_weight AS net_weight" + 
			"        FROM" + 
			"            booking_supplier_detail   sd" + 
			"        WHERE" + 
			"            sd.booking_no = cnt.bibkno" + 
			"            AND sd.supplier_sqno = cnt.supplier_sqno" + 
			"            AND sd.special_handling = cnt.special_handling" + 
			"            AND sd.eq_size = cnt.bicsze" + 
			"            AND sd.eq_type = cnt.bicntp" + 
			"            AND sd.full_qty > 0" + 
			"            AND sd.eq_size = ? " + 
			"            AND sd.eq_type = ? " + 
			"            AND sd.special_handling = ? " + 
			"            AND sd.booking_no = ? ";
	
	private static final String NULL_BICTRN = " AND cnt.bicsze" + 
			"            || cnt.bicntp" + 
			"            || cnt.supplier_sqno" + 
			"            || cnt.biseqn = ? )";
	
	private static final String NOT_NULL_BICTRN = " AND CNT.bictrn = ? )";
	
	private static final String  BL_UPDATE_QUERY = "update vasapps.idp055 cntr "
			+ " set cntr.NET_WEIGHT_METRIC = ? " + 
			" where exists (select DISTINCT cntr.eyeqno AS CONTAINER_NO, " + 
			" CNTR.NET_WEIGHT_METRIC as NET_WEIGHT " + 
			" FROM SEALINER.idp013 sup " + 
			" WHERE  SUP.BSBLNO = CNTR.EYBLNO " + 
			" AND SUP.special_handling = CNTR.special_handling " + 
			" AND SUP.BSEQSZ = CNTR.EYEQSZ " + 
			" AND SUP.BSEQTP = CNTR.EYEQTP " + 
			" AND cntr.eyeqno IS NOT NULL " + 
			" AND sup.BSEFUL > 0 " + 
			" AND SUP.special_handling = ? " + 
			" AND SUP.BSEQSZ = ? " + 
			" AND SUP.BSEQTP = ? " + 
			" AND SUP.BSBLNO = ? " + 
			" AND cntr.eyeqno = ? ) ";
	
	
	
	public int comparePortVender(Map outMap)
	{
		String vendorCode = (String) outMap.get("vendorCode");
		String fromLocation = (String) outMap.get("fromLocation");
		String toLocation = (String) outMap.get("toLocation");
		
		
	int count=	getJdbcTemplate().queryForInt(
				" select count(*) as count from IJS_PORTPAIR_VENDORCODE where FROM_LOCATION=? and TO_LOCATION = ? and VENDOR_CODE =?",
				new Object[] { fromLocation,toLocation, vendorCode });
	
		return count;
	}
	
	
			 
	public void updateContainerWeight(Map outMap) {
		System.out.println("updateContainerWeight() strted."+outMap);
		String ContainerNO = (String) outMap.get("container");
		String oldContainerWeight = (String) outMap.get("oldContainerWeight");
		String newContainerWeight = (String) outMap.get("actualContainerWeight");
		String containerSize =(String) outMap.get("containerSize");
		String containerType = (String) outMap.get("containerType");
		String specialHandling = (String) outMap.get("specialHandling");
		specialHandling = specialHandling.substring(0, 1);
		String bookingOrBLNumber = (String) outMap.get("bookingOrBLNumber");
		String bookingOrBLType = (String) outMap.get("bookingOrBLType");
		String fromLocation = (String) outMap.get("fromLocation");
		String fromTerminal = (String) outMap.get("fromTerminal");
		String toLocation = (String) outMap.get("toLocation");
		String toTerminal = (String) outMap.get("toTerminal");
		String vendorCode = (String) outMap.get("vendorCode");
		
		if("BL".equals(bookingOrBLType.trim())) {
			System.out.println("inside bl");
			getJdbcTemplate().update(BL_UPDATE_QUERY, 
					new Object[] { newContainerWeight,specialHandling,containerSize,
							containerType, bookingOrBLNumber, ContainerNO });
			
			System.out.println("DONE");
		}
		else if("Booking".equals(bookingOrBLType.trim())) {
			System.out.println("inside booking");
			
			int val = getJdbcTemplate().queryForInt(COUNT_BOOKING_CONT_NUMBER, 
					new Object[] {ContainerNO});
			
			System.out.println(val);
			
			if(val == 0) {
				getJdbcTemplate().update(BOOKING_UPDATE_QUERY + NULL_BICTRN, new Object[] {
						newContainerWeight,containerSize, containerType, specialHandling,
						bookingOrBLNumber, ContainerNO });
			}
			else {
				getJdbcTemplate().update(BOOKING_UPDATE_QUERY + NOT_NULL_BICTRN, new Object[] {
						newContainerWeight,containerSize, containerType, specialHandling,
						bookingOrBLNumber, ContainerNO });
			}
			
			 
			
			
			System.out.println("DONE");
			
			//getJdbcTemplate().update(sql, args, argTypes)
			
		}
	}
	
	private class IJSContainerUpdateRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			
			return null;
		}
		
	}

}
