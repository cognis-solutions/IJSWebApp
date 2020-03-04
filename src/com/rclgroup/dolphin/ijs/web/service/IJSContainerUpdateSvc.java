package com.rclgroup.dolphin.ijs.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.rclgroup.dolphin.ijs.web.action.ResultTableAction;
import com.rclgroup.dolphin.ijs.web.dao.impl.IJSResultContainerUpdateDaoImpl;
import com.rclgroup.dolphin.ijs.web.factory.IjsDAOFactory;
import com.rclgroup.dolphin.ijs.web.ui.IJSResultTableContainerUpdateUIM;



public class IJSContainerUpdateSvc {
	private IJSResultContainerUpdateDaoImpl daoImpl = null;
	
	public IJSContainerUpdateSvc(IJSResultContainerUpdateDaoImpl daoImpl)
	{
		this.daoImpl=daoImpl;
	}
	public int getContainerUpdateJson(IJSResultTableContainerUpdateUIM objForm) throws ParseException {
		
		org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
		JSONArray vararry = (org.json.simple.JSONArray) parser.parse(objForm.getContainerJson());
		System.out.println(vararry);
		
		
		for(Object count: vararry) {
			//IJSResultContainerUpdateDaoImpl daoImpl = IjsDAOFactory.getInstance().getDao("containerUpdateDao", servletContext);
			
			JSONObject contObject = (JSONObject) count;
			System.out.println(contObject);
			System.out.println();
			Map<String, String> mapToDao  = getListOfContainerData(contObject);
			
			if(!mapToDao.get("compairflag").equalsIgnoreCase("compare"))
			{
				daoImpl.updateContainerWeight(mapToDao);
			}
			else 
			{
				return daoImpl.comparePortVender(mapToDao);
			}
			
			
			
		}
		
		return 0;
	}
	
	
	public Map<String, String> getListOfContainerData(JSONObject obj) {
		
		Map<String, String> containerMap = new HashMap<String, String>();
		containerMap.put("container", String.valueOf(obj.get("container")));
		containerMap.put("actualContainerWeight", String.valueOf(obj.get("actualContainerWeight")));
		containerMap.put("oldContainerWeight", String.valueOf(obj.get("oldContainerWeight")));
		containerMap.put("containerSize", String.valueOf(obj.get("containerSize")));
		containerMap.put("containerType", String.valueOf(obj.get("containerType")));
		containerMap.put("specialHandling", String.valueOf(obj.get("specialHandling")));
		containerMap.put("bookingOrBLNumber", String.valueOf(obj.get("bookingOrBLNumber")));
		containerMap.put("bookingOrBLType", String.valueOf(obj.get("bookingOrBLType")));
		containerMap.put("fromLocation", String.valueOf(obj.get("fromLocation")));
		containerMap.put("fromTerminal", String.valueOf(obj.get("fromTerminal")));
		containerMap.put("toLocation", String.valueOf(obj.get("toLocation")));
		containerMap.put("toTerminal", String.valueOf(obj.get("toTerminal")));
		containerMap.put("vendorCode", String.valueOf(obj.get("vendorCode")));
		containerMap.put("compairflag", String.valueOf(obj.get("compairflag")));
		//mapToDao.get("compairflag")

		return containerMap;
	}

}
