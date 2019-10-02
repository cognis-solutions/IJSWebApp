/*-----------------------------------------------------------------------------------------------------------
IjsContractSearchDao.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            IJS search contrat functionality 
02 07/09/17  NIIT       IJS            Contract Advance Search functionality
03 21/09/17  NIIT       IJS            New Contract functionality
04 05/09/17  NIIT       IJS            Edit,Suspend,Delete,Vendor detail,History log  Contract functionality
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.dao;

import com.rclgroup.dolphin.ijs.web.entity.IjsContractDownloadDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractSearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsContractRateSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsCntrCurrencyLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractHistory;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractShipmentTermVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractTransportModeVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractUploadVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsExcelUploadTemplateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsVendorDetails;

import java.util.List;
import java.util.Map;

public interface IjsContractSearchDao {
    public List<IjsContractSearchDTO> findContracts(String transMode,String dateRange,String userId,IjsContractSearchParamVO searchParam)throws IJSException;

    public String saveOrUpdateContract(IjsContractVO ijsContractVO, 
                                     String userInfo, String action) throws IJSException ;
    public String doGenPortPair(String userId) throws IJSException ;

    public String deleteContract(List<String> contractsList, String userInfo)throws IJSException  ;

    public String suspendContract(List<String> contractsList, String userInfo)throws IJSException  ;

    public List<IjsContractSearchDTO> compareContract(IjsContractVO ijsContractVO
        , String userInfo)throws IJSException;

    public List<IjsContractHistory> getContractHistory(String contractId, String userInfo)throws IJSException ;

    public List<IjsVendorDetails>  getVendorDetails(String vendorCode, String userInfo)throws IJSException;

    public IjsContractUploadVO uploadContracts(List<IjsExcelUploadTemplateVO> excelUploadTemplateList, 
                                String userInfo, IjsContractRateSvc lookupSvc);
    public Map findContractsToDownload(
                                 String transMode,String dateRange,String userId,IjsContractSearchParamVO searchParam,
                                 List<IjsContractDownloadDTO> lstCostContractSearch,List<IjsContractDownloadDTO> lstBillContractSearch,String sessionId)throws IJSException;
                                 
    public List<IjsContractShipmentTermVO> getTermList(String userId)throws IJSException;                                 
                                 
    public List<IjsContractTransportModeVO> getTransportMode(String userId, String vendorCode)throws IJSException;        
    
    public List<IjsCntrCurrencyLookUpVO> getCurrencyForFSC(String userId, String paymentFscCode)throws IJSException;        
    
    public IjsCntrCurrencyLookUpVO getCurrencyForLocation(String userId, String location, String fromLocationType)throws IJSException;  
    
    public String getTerminalCode(String userId, String location, String fromLocationType, String terminal)throws IJSException;  
    
    
    public String getVendorName(String userId, String vendorCode)throws IJSException;        
    
    public String validateFromToLocSetup(String fromLocation, String fromLocType,
    		                              String toLocation,String toLocType,String transMode)throws IJSException;
    public String priorityCorrection(IjsContractVO contractVO,String userId)throws IJSException;
    
}
