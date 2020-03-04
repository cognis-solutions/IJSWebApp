package com.rclgroup.dolphin.ijs.web.service;

import java.util.ArrayList;
import java.util.List;

import com.rclgroup.dolphin.ijs.web.common.IjsJOLookupResult;
import com.rclgroup.dolphin.ijs.web.dao.IJSProcessNewSaveDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsMaintainSumaarryDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsProcessJOBkgBLSearchDao;
import com.rclgroup.dolphin.ijs.web.ui.IjsMaintainSumaarryUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainjoSummaryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSumarryVO;

public class IjsMaintainSumaarrySvc 
{

	IjsMaintainSumaarryDao joLookupDao = null;
	
	    String lookUpName = null;

	public IjsMaintainSumaarrySvc(IjsMaintainSumaarryDao joLookupDao 
           ) {
		    this.joLookupDao = joLookupDao;
	        this.lookUpName = lookUpName;
	       
	}
	
	
	public IjsMaintainSumaarryUIM jOSumarry(IjsMaintainJOSearchParamVO sumarryParam
			,String session,String userId)
	{
		List<?> returnList= joLookupDao.joSummary(session,userId);
		/*
		 * List l = new ArrayList(); IjsSumarryVO ijsSummary = new IjsSumarryVO() ;
		 * ijsSummary.setET("1"); ijsSummary.setExport("2"); l.add(ijsSummary);
		 */
		 
		return transform(returnList);
	}
	
	
	public IjsMaintainSumaarryUIM transform(List<?> ijsSummary)
	{
		 IjsJOLookupResult<?> searchResult = new IjsJOLookupResult<>();
		 //IjsSumarryVO ijsSummary =  new IjsSumarryVO() ;
		 IjsMaintainSumaarryUIM ijsMaintainSumaarryUIM = new IjsMaintainSumaarryUIM();
		
		 
		 searchResult.setResult(ijsSummary);
		 ijsMaintainSumaarryUIM.setLookupSearchResult(searchResult);
		return ijsMaintainSumaarryUIM;
	}
}
