package com.rclgroup.dolphin.ijs.web.dao;

import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;

import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;

import java.util.List;

public interface IjsJOLookupDao {

    List<?> getLookupList(String lookupName, String userInfo, 
                          IjsLookupParamVO ijsLookupParamVO) throws IJSException;

    public int getLookUpCount(String lookupName, String userInfo, 
                              IjsLookupParamVO ijsLookupParamVO);
                              
   public String updateBkgBl(IjsLookupParamVO ijsLookupParamVO,String userId ,String action) throws IJSException;
   public String deleteLumpsum(List<String> joLumpsumIds,String userId) throws IJSException; 
}
