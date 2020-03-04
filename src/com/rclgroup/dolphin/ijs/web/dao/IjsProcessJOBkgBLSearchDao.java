package com.rclgroup.dolphin.ijs.web.dao;

//import com.rclgroup.dolphin.ijs.web.entity.IjsContractDownloadDTO;
//import com.rclgroup.dolphin.ijs.web.entity.IjsContractSearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOBkgBLSearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSumDtlDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSummarySearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
//import com.rclgroup.dolphin.ijs.web.service.IjsContractRateSvc;
//import com.rclgroup.dolphin.ijs.web.vo.IjsContractHistory;
//import com.rclgroup.dolphin.ijs.web.vo.IjsContractSearchParamVO;
//import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.service.IjsProcessJOBkgBLSearchSvc;
import com.rclgroup.dolphin.ijs.web.vo.IjsEquipmetLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsExcelUploadTemplateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOChangeVendorVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOSummaryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOBkgBLSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOBkgBLSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOListParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOSummarySearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRoutingListParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsVendorDetails;

import java.util.List;
import java.util.Map;

public interface IjsProcessJOBkgBLSearchDao {
    public List<IjsProcessJOBkgBLSearchDTO> findBkgBL( 
                                                    String userId, 
                                                    IjsProcessJOBkgBLSearchParamVO searchParam,String sessionId) throws IJSException;
                                                    
    public List<IjsProcessJOSumDtlDTO> searchJOSummary(String userId, 
                                                    IjsProcessJOBkgBLSearchParamVO searchParam,
                                                    List<IjsJOSummaryParamVO> llstJOSummaryParam,
                                                    String processJoType,
                                                    String astrSessionId,
                                                    String transMode) throws IJSException;        
                                                    
    public List<IjsProcessJOSumDtlDTO> searchJOSummaryAdhoc( 
                                                    String userId, 
                                                    IjsRoutingListParamVO searchParamAdhoc,
                                                    String astrSessionId,
                                                    String processJoType,
                                                    String vendorCode,
                                                    List<String>  eqList,String strDetail) throws IJSException; 
                                                    
    public List createJob(   String userId, 
                                                    List<IjsProcessJOListParamVO> processjoFieldList,
                                                    String astrSessionId,
                                                    String reasonCode,
                                                    String transMode,
                                                    String processJoType) throws IJSException;                                                     
                                                    
    public void resetJO(String userInfo,String astrSessionId) throws IJSException;

    public String saveOrUpdateContract(IjsProcessJOBkgBLSearchVO ijsContractVO, 
                                       String userInfo, 
                                       String action) throws IJSException;

    public Map<String, String> deleteContract(List<String> contractsList, 
                                              String userInfo) throws IJSException;
                                              
    public String changeVendor(String bk_bl_ad,
                                String bkgblNumber,
                                String routingId,
                                String routingIdOLD,
                                String vendorID,
                                String vendorIDOLD,
                                String contractId,String transMode,String userId,String action) throws IJSException;               
    
    
//CR#03 START
    public int getMaxEquipLimit() throws IJSException;
    public List<String> getEqTypeList(String userId) throws IJSException;
//CR#03 END
  //CR#04 START
    public  List<IjsProcessJOSumDtlDTO> deleteLumpsum(List<String> jobOrders,String allJobOrders,String userId,String sessionId) throws IJSException;  
  //CR#03 END
    
}
