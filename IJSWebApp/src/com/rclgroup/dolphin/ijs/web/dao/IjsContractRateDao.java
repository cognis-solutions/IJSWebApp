 /*-----------------------------------------------------------------------------------------------------------
 IjsContractRateDao.java
 -------------------------------------------------------------------------------------------------------------
 Copyright RCL Public Co., Ltd. 2007
 -------------------------------------------------------------------------------------------------------------
 Author NIIT 07/09/17
 - Change Log ------------------------------------------------------------------------------------------------
 ## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            Contract Rate dao  functionality for contract
02 05/10/17  NIIT       IJS            added Contract Rate   functionality for contract
 -----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.dao;

import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractRateUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractOogSetupVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsCostImdgPortClassVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsCostRateSetupVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;

import java.util.List;

public interface IjsContractRateDao {

    public IjsContractRateUIM getContractRateList(String terminalDepotCode,int routngNumber, 
                                    String userInfo, String actionType, boolean getEqList) throws IJSException;

    public String saveOrUpdateCostRate(IjsContractRateUIM actionForm, String userInfo);

    public IjsContractRateUIM costRateMaintainance(List<Integer> costRateSeqNumList, String userInfo, 
                                      String action, IjsContractRateUIM actionForm);

    public IjsContractRateUIM billingRateMaintainance(List<Integer> bilingRateSeqNumList, 
                                                      String userInfo, 
                                                      String action,
                                                      IjsContractRateUIM actionForm);

    public String saveOrUpdateBillingRate(IjsContractRateUIM actionForm, 
                                        String userInfo);

    public IjsContractRateUIM saveOrUpdateCostRateList(IjsContractRateUIM actionForm, String userInfo) throws IJSException;                                        
    
    public IjsContractRateUIM saveOrUpdateBillRateList(IjsContractRateUIM actionForm, String userInfo) throws IJSException; 
    public Double getExchangeRate(String fscCode,String fromCurrency,String toCurrency) throws IJSException; 
    //CR#04 START
    public IjsCostRateSetupVO saveOrUpdateOOGSetup(String terminalDepotCd,List<IjsContractOogSetupVO> oogSetup) throws IJSException; 
    public IjsCostRateSetupVO saveOrUpdatePortImdgSetup(String terminalDepotCd,String portImdgType,List<IjsCostImdgPortClassVO> portImdgSetupList) throws IJSException;
    public IjsCostRateSetupVO searchOOGSetup(String terminalDepotCd) throws IJSException;
    public IjsCostRateSetupVO searchPortImdgSetup(String terminalDepotCd,String portImdgType) throws IJSException;
    //CR#04 END
                                        
}
