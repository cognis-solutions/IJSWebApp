package com.rclgroup.dolphin.ijs.web.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.rclgroup.dolphin.ijs.web.entity.IjsProcessNewSaveDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.vo.IjsJORoutingLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOSummaryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessNewSaveVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRoutingListParamVO;

public interface IJSProcessNewSaveDao 
{

	   List<IjsJORoutingLookUpVO> getLookupList(String lookupName, String userInfo, 
			   IjsProcessNewSaveVO ijsLookupParamVO,
			   List<IjsJOSummaryParamVO> ijsJOSummaryParamVO) throws IJSException;
	   
	   List<?> getVendorList(List<?>list, 
               String userInfo, 
               IjsProcessNewSaveVO ijsLookupParamVO,HttpSession session)throws IJSException;

	   List<?>getCostCalcdhoc(List<IjsJORoutingLookUpVO> list, IjsRoutingListParamVO ijsListParamVO,
		        String session, 
		        String userInfo)throws IJSException;
	   
	   
	   List<?>getVendorCostCalc(List<?>list, 
               String userInfo, 
               List<IjsJOSummaryParamVO> ijsJOSummaryParamVO,HttpSession session,String joType)throws IJSException;

	   

}
