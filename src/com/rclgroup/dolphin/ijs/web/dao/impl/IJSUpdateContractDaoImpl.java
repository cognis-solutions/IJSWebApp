package com.rclgroup.dolphin.ijs.web.dao.impl;

import com.rclgroup.dolphin.ijs.web.dao.IJSUpdateContractDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IJSUpdateContractDaoImpl extends IjsBaseDao implements IJSUpdateContractDao{

	public void addFalgForDeleteAndExpireQuery() {
		System.out.println("addFalgForDeleteAndExpireQuery() started in IJSUpdateContractDaoImpl class...");
		
		
		DateTimeFormatter format =
	            DateTimeFormatter.ofPattern("yyyy-MM-dd");

	        LocalDateTime now = LocalDateTime.now();
	        LocalDateTime then = now.minusDays(30);

	        String temPrivious30Date=then.format(format);
	        
	        String temTodayDate=now.format(format);
		
	        String privious30Date = temPrivious30Date.replace("-","");
	        String todayDate = temTodayDate.replace("-","");
	        
	        System.out.println("30 - "+privious30Date);
	        
	        System.out.println("today - "+todayDate);
	        
	        getJdbcTemplate().update(UPDATE_DELETE_FALG_CONATAINER , new Object[] {
	        		privious30Date});
	        getJdbcTemplate().update(UPDATE_AGREEMENT , new Object[] {
	        		privious30Date});
	        getJdbcTemplate().update(UPDATE_SYSTEM_DETLS , new Object[] {
	        		privious30Date});
	        getJdbcTemplate().update(INSERT_HISTORY_LOG , new Object[] {
	        		privious30Date});
	        
	        getJdbcTemplate().update(UPDATE_CONATAINER_STATUS_AS_EXPIRE , new Object[] {
	        		todayDate});
	        getJdbcTemplate().update(UPDATE_CONATAINER_STATUS_HISTORY_LOG);
	        
	        
	}

}
