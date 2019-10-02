package com.rclgroup.dolphin.ijs.web.dao;

import com.rclgroup.dolphin.ijs.web.entity.IjsMaintainJOSearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsMaintainJoDownloadDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;

import java.util.List;
import java.util.Map;

public interface IjsMaintainJOSearchDao {
    public List<IjsMaintainJOSearchDTO> findJobOrder(String userId, 
                                                          IjsMaintainJOSearchParamVO searchParam) throws IJSException;

    public String completeJO(List<IjsMaintainJOSearchVO> searchVo,String userId,String action) throws IJSException;
                                       
    public List<IjsMaintainJoDownloadDTO> findJobOrderToDownload(String userId, 
                                                              IjsMaintainJOSearchParamVO searchParam) throws IJSException;
                                                              
    public String saveFSC(List<IjsMaintainJOSearchVO> searchVo,String userId,String action) throws IJSException;
    
    public String removeEquipmentJO(List<IjsMaintainJOSearchVO> searchVo,String userId,String action) throws IJSException;
    
    public String replaceEquipmentJO(List<IjsMaintainJOSearchVO> searchVo,String userId,String action) throws IJSException;
    
    public String changeVendor(List<IjsMaintainJOSearchVO> searchVo,String userId,String action) throws IJSException;

    public int getTotalResultCountForJO(String userInfo, IjsMaintainJOSearchParamVO mainatainJoParam) throws IJSException;
    public String deleteLumpsum(List<String> joLumpsumIds,String userId,String componentType) throws IJSException; 
    public Map<String,String> findJODownloadLimit(String userId, IjsMaintainJOSearchParamVO searchParam) throws IJSException;
   
}
