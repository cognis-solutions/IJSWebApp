
package com.rclgroup.dolphin.ijs.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.rclgroup.dolphin.ijs.web.common.IjsJOLookupResult;
import com.rclgroup.dolphin.ijs.web.dao.IJSProcessNewSaveDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsProcessJOBkgBLSearchDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IJSProcessJONewSaveUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsEQDetailVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJORoutingLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOSummaryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessNewSaveVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRoutingListParamVO;

public class  IJSProcessJONewSaveSvc {

	IJSProcessNewSaveDao joLookupDao = null;
	
	    String lookUpName = null;

	public IJSProcessJONewSaveSvc(IJSProcessNewSaveDao joLookupDao
            ) {
		    this.joLookupDao = joLookupDao;
	        
	      
	        
	}

public IJSProcessJONewSaveUIM	jOCostCalcAdhoc(IjsProcessNewSaveVO ijsProcessNewSaveVO,
		IjsRoutingListParamVO ijsListParamVO,
		List<IjsJOSummaryParamVO> ijsJOSummaryParamVO,
		String lookupName,
        String session, 
        String userInfo
        ) throws IJSException
{
	
	List<IjsJORoutingLookUpVO> list  =joLookupDao.getLookupList(lookupName, 
              userInfo, 
            ijsProcessNewSaveVO,ijsJOSummaryParamVO);
	

	return transformAdc(list,ijsListParamVO,session,userInfo ,ijsProcessNewSaveVO);
	
}





	
private<T> IJSProcessJONewSaveUIM transformAdc(List<IjsJORoutingLookUpVO> list,IjsRoutingListParamVO ijsListParamVO,String session,String userInfo,IjsProcessNewSaveVO ijsProcessNewSaveVO) throws IJSException
{
	
	
	List<?> vendosList =joLookupDao.getCostCalcdhoc(list,ijsListParamVO,
	        session, 
	       userInfo);
	
	IJSProcessJONewSaveUIM ijsNewSaveLookupUIM = new IJSProcessJONewSaveUIM();
	 IjsJOLookupResult<?> searchResult = new IjsJOLookupResult<T>();
	
	List returnList =new ArrayList();
	//vendosList.contains(arg0)
	Map map = new HashMap();
	map.put("vendor",list );
	map.put("vendorAfterCalc", vendosList);

	returnList.add(map);
	System.out.println("Vendors"+ list);
	System.out.println("vendorAfterCalc"+ vendosList);
	 searchResult.setResult(returnList);
    ijsNewSaveLookupUIM.setLookupSearchResult(searchResult);
	 return ijsNewSaveLookupUIM;
	}




private<T> IJSProcessJONewSaveUIM transformAdhoc(List Vendors,IjsRoutingListParamVO ijsListParamVO)
{
	IJSProcessJONewSaveUIM ijsNewSaveLookupUIM = new IJSProcessJONewSaveUIM();
	 IjsJOLookupResult<?> searchResult = new IjsJOLookupResult<T>();
	
	List returnList =new ArrayList();
	//vendosList.contains(arg0)
	Map map = new HashMap();
	map.put("vendor",ijsListParamVO );
	map.put("vendorAfterCalc", Vendors);
	
	returnList.add(map);
	System.out.println("Vendors"+ Vendors);
	System.out.println("vendorAfterCalc"+ ijsListParamVO);
	 searchResult.setResult(returnList);
     ijsNewSaveLookupUIM.setLookupSearchResult(searchResult);
	 return ijsNewSaveLookupUIM;
}
	

	public IJSProcessJONewSaveUIM getNewSaveJson(IjsProcessNewSaveVO ijsLookupParamVO,
			List<IjsJOSummaryParamVO> ijsJOSummaryParamVO, 
            String lookupName, 
            String userInfo, 
            HttpSession session)
			throws IJSException {
		
             
		List<?> list  =joLookupDao.getLookupList(lookupName, 
                userInfo, 
           ijsLookupParamVO,ijsJOSummaryParamVO);
		
		//System.out.println("returnScv>>>" + transform(saveNewData));
		return transform(list,ijsLookupParamVO, userInfo, 
	             session, ijsLookupParamVO.getFindForLoc(),
              	ijsLookupParamVO.getFindForLocType(),
            	ijsLookupParamVO.getFindForTerminal(),
            	ijsLookupParamVO.getFindForVendorCode(),
            	ijsLookupParamVO.getJoType(),
            	ijsLookupParamVO.getFindIn(),
            	ijsLookupParamVO.getTransMode(),
            	ijsLookupParamVO.getComponentType(),ijsJOSummaryParamVO);
	}

	private<T> IJSProcessJONewSaveUIM transform(List<?> list,
			IjsProcessNewSaveVO ijsLookupParamVO,String userInfo, 
            HttpSession session, String findForLoc,
            String findForLocType,
      	    String findForTerminal,
      	    String indForVendorCode,
      	    String joType,
      	    String findIn,
      	    String tranportMode,
      	    String checkComponent,
      	  List<IjsJOSummaryParamVO> ijsJOSummaryParamVO
      	     ) throws IJSException {
		
		
		IJSProcessJONewSaveUIM ijsNewSaveLookupUIM = new IJSProcessJONewSaveUIM();
		 IjsJOLookupResult<?> searchResult = new IjsJOLookupResult<T>();
		if(checkComponent.equalsIgnoreCase("adhoc")) 
    	{
    		
    		
			String[] forLoc = findForLoc.split("/");
	    	
	    	String fromLoc = forLoc[0];
	    	String toLoc = forLoc[1];
	    	
	    	String[] forLocType = findForLocType.split("/");
	    	
	    	String fromLocType = forLocType[0];
	    	String toLocType = forLocType[1];
	    	
	    	String[] forTerminal = findForTerminal.split("/");
	    	//System.out.println(findForTerminal);
	    	String fromTerminal = forTerminal[0];
	    	String toTerminal = forTerminal[1];
	    	
	    	IjsJOLookupResult<?> searchResultAdHoc = new IjsJOLookupResult<T>();
	    	
	    	List returnList = new ArrayList();
	        //IjsJOLookupResult<?> searchResult = new IjsJOLookupResult<T>();
	    	for(int i =0;i<list.size();i++)
	    	{
	    	  IjsJORoutingLookUpVO indexValues =   (IjsJORoutingLookUpVO) list.get(i);
	    		
	       if(fromLoc.equalsIgnoreCase(indexValues.getFromLocation())
	    		   && toLoc.equalsIgnoreCase(indexValues.getToLocation()) &&
	    		   fromTerminal.equalsIgnoreCase(indexValues.getFromTerminal())
	    		   && toTerminal.equalsIgnoreCase(indexValues.getToTerminal()))
	    	{     
	    	   
	    	    	returnList.add(indexValues);
	    	    	
	    		 
	        }
	    	  
	    	}
	    	
		     System.out.println("returnList"+returnList);
	    	 searchResultAdHoc.setResult(returnList);
	    	 ijsNewSaveLookupUIM.setLookupSearchResult(searchResultAdHoc);
	
	}else if(checkComponent.equalsIgnoreCase("processJoMultiple")) 
	{
List<?> vendosList =joLookupDao.getVendorCostCalc(list, userInfo,ijsJOSummaryParamVO,session,joType);
	
        List returnList =new ArrayList();
	    Map map = new HashMap();
		map.put("vendor", list);
		map.put("vendorAfterCalc", vendosList);
	    returnList.add(map);
		System.out.println("Vendors"+ list);
		System.out.println("vendorAfterCalcMultiple"+ vendosList);
		
		 searchResult.setResult(returnList);
         ijsNewSaveLookupUIM.setLookupSearchResult(searchResult);

	}else {
		
		List<?> vendosList =joLookupDao.getVendorList(list, userInfo,ijsLookupParamVO,session);
	
		
		
		List returnList =new ArrayList();
		Map map = new HashMap();
		map.put("vendor", list);
		map.put("vendorAfterCalc", vendosList);
		
		returnList.add(map);
		System.out.println("Vendors"+ list);
		System.out.println("vendorAfterCalc"+ vendosList);
		searchResult.setResult(returnList);
        ijsNewSaveLookupUIM.setLookupSearchResult(searchResult);
	}
         return ijsNewSaveLookupUIM;


	}

}