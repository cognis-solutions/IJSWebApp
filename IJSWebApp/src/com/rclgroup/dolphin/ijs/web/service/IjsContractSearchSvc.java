 /*-----------------------------------------------------------------------------------------------------------
IjsContractSearchSvc.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            contract service interacts with data layer and transform entities to value objects
02 07/09/17  NIIT       IJS            Contract Advance Search interacts with data layer and transform entities to value objects
03 21/09/17  NIIT       IJS            New Contract interacts with data layer and transform entities to value objects
04 05/09/17  NIIT       IJS            Edit,Suspend,Delete,Vendor detail,History log  interacts with data layer and transform entities to value objects
04 20/10/17  NIIT       IJS            Upload excel functionality added
  -----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.service;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.common.excel.Debug;
import com.rclgroup.dolphin.ijs.web.common.excel.GlobalUtil;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsExcelTemplateConstants;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractSearchDao;
import com.rclgroup.dolphin.ijs.web.dao.impl.IjsContractSearchJdbcDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractDownloadDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractSearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractSearchUIM;
import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.vo.IjsCntrCurrencyLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractUploadVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsExcelUploadTemplateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractHistory;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractShipmentTermVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractTransportModeVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsVendorDetails;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class IjsContractSearchSvc {
    IjsContractSearchDao contractDao = null;
    private static final String FROM_LOC_TYPE = "FROM_LOC_TYPE";
    private static final String TO_LOC_TYPE="TO_LOC_TYPE";
    private static final String FEEDER="Feeder";
    private static final String ASTRIC="**";
    public IjsContractSearchSvc(IjsContractSearchDao contractDao) {
        this.contractDao=contractDao;
    }
    
    public IjsContractSearchUIM searchContract(IjsSearchParamVO contractParam,String userInfo) throws IJSException{
        IjsContractSearchParamVO contractParamVO=(IjsContractSearchParamVO)contractParam.getSearchScreenParam();
        
        List<IjsContractSearchDTO> lstContracts=contractDao.findContracts(contractParam.getTransMode(),contractParam.getDateRange(),
                                                                          userInfo,contractParamVO);
        return transformDtoToUim(lstContracts);
    }
    
    private IjsContractSearchUIM transformDtoToUim(List<IjsContractSearchDTO> lstContracts){
       //TO-DO transform Data Object to VO 
        IjsContractSearchUIM ijsContractSearchUIM= null;
        IjsSearchResult<IjsContractVO> searchResult= null;
        List<IjsContractVO> lstSearchResult=null;
        if(lstContracts!=null){
            lstSearchResult=new ArrayList<IjsContractVO>();
            ijsContractSearchUIM= new IjsContractSearchUIM();
            searchResult=new IjsSearchResult<IjsContractVO>();
            for(IjsContractSearchDTO contract:lstContracts){
                IjsContractVO contractVo=new IjsContractVO();
                contractVo.setDays(contract.getDays());
                contractVo.setCurrency(contract.getCurrency());
                contractVo.setDistance(contract.getDistance());
                contractVo.setEndDate(contract.getEndDate());
                contractVo.setExempted(contract.isExempted());
                contractVo.setHours(contract.getHours());
                contractVo.setFromLocation(contract.getFromLocation());
                contractVo.setFromLocType(IjsHelper.getLocationType(contract.getFromLocType()));
                contractVo.setFromTerminal(contract.getFromTerminal());
                contractVo.setToLocation(contract.getToLocation());
                contractVo.setToLocType(IjsHelper.getLocationType(contract.getToLocType()));
                contractVo.setToTerminal(contract.getToTerminal());
                //contractVo.setLstBillRate(contract.getb);
               //  contractVo.setLstCostRate();
                
                //for purchase term
                contractVo.setPurchaseTerm(contract.getPurchaseTerm());
                contractVo.setPaymentFsc(contract.getPaymentFsc());
                contractVo.setPriority(contract.getPriority());
                contractVo.setRemarks(contract.getRemarks());
                contractVo.setStartDate(contract.getStartDate());
                contractVo.setStatus(contract.getStatus());
                contractVo.setTransMode(IjsHelper.getTransMode(contract.getTransMode()));
                contractVo.setUom(contract.getUom());
                contractVo.setVendorCode(contract.getVendorCode());
                contractVo.setVendorName(contract.getVendorName());
                contractVo.setContractId(contract.getContractId());
                contractVo.setRoutingId(contract.getRoutingId());
                contractVo.setTerm(contract.getTerm());//MD
                contractVo.setRate20(contract.getRate20());
                contractVo.setRate40(contract.getRate40());
                contractVo.setRate45(contract.getRate45());
                contractVo.setBillingRate20(contract.getBillingRate20());
                contractVo.setBillingRate40(contract.getBillingRate40());
                contractVo.setBillingRate45(contract.getBillingRate45());
                contractVo.setCostCurrency(contract.getCostCurrency());
                contractVo.setBillingCurrency(contract.getBillingCurrency());
                lstSearchResult.add(contractVo);
            }
            searchResult.setResult(lstSearchResult);
            //System.out.println(searchResult);
            ijsContractSearchUIM.setSearchResult(searchResult);
        }
       return ijsContractSearchUIM;
    }

    public IjsContractSearchUIM saveOrUpdateContract(IjsContractVO ijsContractVO, 
                                                     String userInfo, String action)throws IJSException  {
                                                     
        IjsHelper.validateDate(ijsContractVO.getStartDate(), ijsContractVO.getEndDate());
        
        return transform(contractDao.saveOrUpdateContract(ijsContractVO,userInfo, action), action,ijsContractVO);
    }

//    public IjsContractSearchUIM saveAndUpdatePriority(IjsContractVO ijsContractVO, 
//                                                     String userInfo, String action)throws IJSException  {
//        validateRequest(ijsContractVO);
//        action="new";
//        return transform(contractDao.saveOrUpdateContract(ijsContractVO,userInfo, action), action);
//    }
    


    private IjsContractSearchUIM transform(String result, String action,IjsContractVO ijsContractVO) {
        IjsContractSearchUIM ijsContractSearchUIM= new IjsContractSearchUIM();
        ijsContractSearchUIM.setContract(ijsContractVO);
        ijsContractSearchUIM.setErrorCode(result);
        ijsContractSearchUIM.setAction(action);
        


        if(ijsContractVO.getMsgDupRoute()!= null &&  ijsContractVO.getMsgDupRoute().getMessageList().size()> 0){

           ijsContractSearchUIM.setMsgDupRoute(ijsContractVO.getMsgDupRoute());
        }
        
//        if(ijsContractVO.getMsgPriorityOverLap().getMessageList().size()> 0){
//           ijsContractSearchUIM.setMsgPriorityOverLap(ijsContractVO.getMsgPriorityOverLap());
//        }
        
        if(ijsContractVO.getMsgSuccessVO()!=null && ijsContractVO.getMsgSuccessVO().getMessageList().size()> 0){
           ijsContractSearchUIM.setMsgSuccessVO(ijsContractVO.getMsgSuccessVO());
        }
        
//        if(!action.equalsIgnoreCase("edit")){
//        ijsContractSearchUIM.setContract(null);
//        }
        
        ijsContractSearchUIM.setAction(action);
        return ijsContractSearchUIM;
        
    }
    private IjsContractSearchUIM transformResultsForSusOrDel(String results, String action) {
        IjsContractSearchUIM ijsContractSearchUIM= new IjsContractSearchUIM();
        if(results!=null && !results.isEmpty()){
        	if(IjsActionMethod.SUSPEND_CONTRACT.getAction().equals(action)){
        		ijsContractSearchUIM.setErrorCode(IjsErrorCode.DB_IJS_CNTR_JO_EX_1001.getErrorCode());
        	}else{
        		ijsContractSearchUIM.setErrorCode(IjsErrorCode.DB_IJS_CNTR_JO_EX_1002.getErrorCode());
        	}
        	
        	results=results.substring(0, results.lastIndexOf(","));
        	ijsContractSearchUIM.setContractsResult(results);
        }
        
        ijsContractSearchUIM.setAction(action);
        return ijsContractSearchUIM;
        
    }

    public IjsContractSearchUIM deleteContract(List<String> contractsList, 
                                               String userInfo, String action) throws IJSException {
        return transformResultsForSusOrDel(contractDao.deleteContract(contractsList,userInfo),action);
    }

    public IjsContractSearchUIM suspendContract(List<String> contractsList, String userInfo, 
                                String action) throws IJSException {
        return transformResultsForSusOrDel(contractDao.suspendContract(contractsList,userInfo),action);                        
    }

    public IjsContractSearchUIM compareContract(IjsContractVO ijsContractVO, String userInfo, 
                                String action) throws IJSException {
        IjsContractSearchUIM ijsContractSearchUIM = transformDtoToUim(contractDao
            .compareContract(ijsContractVO,userInfo));
        ijsContractSearchUIM.setAction(action);
        return ijsContractSearchUIM;
    }

    public IjsContractSearchUIM getContractHistory(IjsContractVO ijsContractVO, String userInfo, 
                                   String action) throws IJSException {
        return transformHistoryList(contractDao.getContractHistory(ijsContractVO
            .getContractId(), userInfo), action);
    }
    public IjsContractSearchUIM doGenPortPair(String userId, 
            String action) throws IJSException {
    	IjsContractSearchUIM uim=new IjsContractSearchUIM();
    	String nextGPPTime=contractDao.doGenPortPair(userId);
    	if(nextGPPTime!=null){
    		uim.setErrorCode(IjsErrorCode.DB_IJS_CNTR_EX_10034.getErrorCode());
        	uim.setNextGPPTime(nextGPPTime);
    	}
    	
    	uim.setAction(action);
    	return uim;
//		return transformHistoryList(contractDao.getContractHistory(ijsContractVO
//		.getContractId(), userInfo), action);
	}
    public IjsContractSearchUIM getVendorDetails(IjsContractVO ijsContractVO, String userInfo, 
                                   String action) throws IJSException {
        return transformVendorDetailsList(contractDao
            .getVendorDetails(ijsContractVO.getVendorCode(), userInfo), action);
    }
    private IjsContractSearchUIM transformHistoryList(List<IjsContractHistory>  historyList, String action) {
        IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
        IjsSearchResult<IjsContractHistory> searchResult= new IjsSearchResult<IjsContractHistory>();
        List<IjsContractHistory> histList = new ArrayList<IjsContractHistory>();
        double activitySeq=1;
        for (IjsContractHistory cntrHistory : historyList) {
            cntrHistory.setActivityDate(convertDateToString(cntrHistory.getActivityDate()));
            cntrHistory.setActivitySeq(activitySeq);
            activitySeq=activitySeq+1;
            histList.add(cntrHistory);
        }
        searchResult.setResult(histList);
        ijsContractSearchUIM.setAction(action);
        ijsContractSearchUIM.setSearchResult(searchResult);
        return ijsContractSearchUIM;
    }

    private  String convertDateToString(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/mm/yyyy");
        Date endDate = null;
        try {
            endDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf1.format(endDate);
    }


    private IjsContractSearchUIM transformVendorDetailsList(List<IjsVendorDetails> ijsVendorDetails, String action) {
        IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
        IjsSearchResult<IjsVendorDetails> searchResult= new IjsSearchResult<IjsVendorDetails>();
        searchResult.setResult(ijsVendorDetails);
        ijsContractSearchUIM.setSearchResult(searchResult);
        ijsContractSearchUIM.setAction(action);
        return ijsContractSearchUIM;
    }
    //##05 BEGIN
    /**
     * uploadContracts method for uploading the contracts with cost and billing informaiton
     * @param actionForm
     * @param userInfo
     * @param lookupSvc
     * @return
     * @throws IJSException
     */
    
    public IjsContractSearchUIM uploadContracts(IjsContractSearchUIM actionForm, String userInfo
        , IjsContractRateSvc lookupSvc) throws IJSException {
        List<IjsExcelUploadTemplateVO> excelTemplateList = new ArrayList<IjsExcelUploadTemplateVO>();
        excelTemplateList = getContractAndCostRateUploadRecords(actionForm);
        IjsContractUploadVO results = contractDao.uploadContracts(excelTemplateList, userInfo, lookupSvc);
        return transformUploadResults(results, actionForm);
    }
   
    public String[] downloadContracts(IjsSearchParamVO contractParam,String userInfo,String path,String sessionId) throws IJSException{
         IjsContractDownloadSvc ijsContractDownloadSvc=new IjsContractDownloadSvc();
         IjsContractSearchParamVO contractParamVO=(IjsContractSearchParamVO)contractParam.getSearchScreenParam();
        List<IjsContractDownloadDTO> lstCostContractSearch=new ArrayList<IjsContractDownloadDTO>();
        List<IjsContractDownloadDTO> lstBillContractSearch=new ArrayList<IjsContractDownloadDTO>();
        Map contractsForDownload=contractDao.findContractsToDownload(contractParam.getTransMode(),contractParam.getDateRange(),
                                                                          userInfo,contractParamVO,lstCostContractSearch,lstBillContractSearch,sessionId);

        try {
           return  ijsContractDownloadSvc.prepareDataForDownload((List<IjsContractDownloadDTO>)contractsForDownload.get("p_o_v_ijs_cost_contract_list"),(List<IjsContractDownloadDTO>)contractsForDownload.get("p_o_v_ijs_bill_contract_list"),path);
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }

    public List<IjsExcelUploadTemplateVO> getContractAndCostRateUploadRecords(IjsContractSearchUIM actionForm) throws IJSException {
        
        GlobalUtil uitl = new GlobalUtil();
        String astrExcelFilePath = actionForm.getUploadVO().getFolderPath() + actionForm.getUploadVO().getFileName();
        String sheetName = "First Sheet"; 
        int aintFromRowNo=1;
        int aintFromColNo=1;
        int aintToColNo=48;
        boolean ablnIncludeLineNo = false;
        boolean isCostSheet = false;
        boolean isBillSheet = false;
        //String retArray[][];
         List<IjsExcelUploadTemplateVO> excelTemplateList = new ArrayList<IjsExcelUploadTemplateVO>();
        try {
            String[][] retArray = uitl.readExcel(astrExcelFilePath,sheetName,aintFromRowNo,aintFromColNo,aintToColNo,ablnIncludeLineNo);
            System.out.println(retArray.length);
            
            if (retArray.length > 0) {
                //int rowT = (retArray.length > 10?10:retArray.length);
            	int rowT = retArray.length ;
                String header[] = retArray[0];
                IjsExcelUploadTemplateVO excelTemplateVO = null;
                List<IjsRateVO> ijsCostRateVOList = null;
                List<IjsRateVO> ijsBillingRateVOList = null;
//                if (header[40] != null && header[40].length() > 0){
                if (header[39] != null && header[39].length() > 0){
                    isCostSheet=true;
                }else{
                    isBillSheet=true;
                }
                
                for(int i=1; i < rowT; i++){
                    //IjsExcelUploadTemplateVO excelTemplateVO;
                     String vendorCode = retArray[i][0];
                    if (vendorCode != null && vendorCode.length() > 0) {
                        excelTemplateVO = new IjsExcelUploadTemplateVO();
                        ijsCostRateVOList = new ArrayList<IjsRateVO>();
                        ijsBillingRateVOList= new ArrayList<IjsRateVO>();
                        IjsContractVO contractVO = populateContractInfo(excelTemplateVO , retArray[i], isCostSheet);
                        if (contractVO.getVendorCode() != null && contractVO.getVendorCode().length() > 0) {
                            excelTemplateVO.setIjsContractVO(contractVO);
                        }
                    }
                   // if (header[40] != null && header[40].length() > 0) {
                   if(isCostSheet){
                        IjsRateVO costRateVO = populateCostRateInfo(excelTemplateVO , retArray[i],excelTemplateVO.getIjsContractVO());
                        if(costRateVO!=null){
                        	costRateVO.setMot(excelTemplateVO.getIjsContractVO().getTransMode());
                            ijsCostRateVOList.add(costRateVO);
                            excelTemplateVO.setIjsCostRateVOList(ijsCostRateVOList);
                            excelTemplateVO.setIsCostRateUpload(true);
                        }
                        
                    }
                    if(isBillSheet){
                        IjsRateVO billingRateVO = populateBillingRateInfo(excelTemplateVO , retArray[i],excelTemplateVO.getIjsContractVO());
                        if(billingRateVO!=null){
                        	billingRateVO.setMot(excelTemplateVO.getIjsContractVO().getTransMode());
                            ijsBillingRateVOList.add(billingRateVO);
                            excelTemplateVO.setIjsBillingRateVOList(ijsBillingRateVOList);
                            excelTemplateVO.setBillingRateUpload(true);
                        }
                        
                    }
                    if (vendorCode != null && vendorCode.length() > 0) {
                        excelTemplateList.add(excelTemplateVO);
                    }
                }
            }
            
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
        return excelTemplateList;
    }
    private IjsContractVO populateContractInfo(IjsExcelUploadTemplateVO rateTemplateVO, 
                                      String[] contract , Boolean isCostSheet) throws IJSException {
        IjsContractVO contractVO = new IjsContractVO();
        if(isInValidInput(contract[IjsExcelTemplateConstants.VENDOR.getIndex()])){
        	contractVO.setInvalidContract(true);
        }else{
        	contractVO.setVendorCode(contract[IjsExcelTemplateConstants.VENDOR.getIndex()]);
        }
        
        contractVO.setVendorName(contract[IjsExcelTemplateConstants.VENDOR_NAME.getIndex()]);
        if(isInValidInput(contract[IjsExcelTemplateConstants.CONTRACT.getIndex()])){
        	contractVO.setInvalidContract(true);
        }else{
        	contractVO.setContractId(contract[IjsExcelTemplateConstants.CONTRACT.getIndex()]);
        }
        
        if (contract[IjsExcelTemplateConstants.ROUTING.getIndex()] != null 
            && contract[IjsExcelTemplateConstants.ROUTING.getIndex()].length() > 0) {
            contractVO.setRoutingId(Integer.parseInt(contract[IjsExcelTemplateConstants.ROUTING.getIndex()]));
        }
        try{
        	if (validateDateFormate(contract[IjsExcelTemplateConstants.START_DATE.getIndex()]))
                contractVO.setStartDate(contract[IjsExcelTemplateConstants.START_DATE.getIndex()]);
        }catch(IJSException ie){
        	contractVO.setInvalidContract(true);
        }
        try{
        	if (validateDateFormate(contract[IjsExcelTemplateConstants.END_DATE.getIndex()]))
                contractVO.setEndDate(contract[IjsExcelTemplateConstants.END_DATE.getIndex()]);
        }catch(IJSException ie){
        	contractVO.setInvalidContract(true);
        }
        
        //if (validateTransportMode(contract[IjsExcelTemplateConstants.TRANSPORT_MODE.getIndex()]))
        if(isInValidInput(contract[IjsExcelTemplateConstants.TRANSPORT_MODE.getIndex()])){
        	contractVO.setInvalidContract(true);
        }else{
        	contractVO.setTransMode(contract[IjsExcelTemplateConstants.TRANSPORT_MODE.getIndex()]);
        }
        //contractVO.setTransMode(contract[IjsExcelTemplateConstants.TRANSPORT_MODE.getIndex()]);
        contractVO.setStatus(contract[IjsExcelTemplateConstants.STATUS.getIndex()]);
        if(isInValidInput(contract[IjsExcelTemplateConstants.PAYMENT_FSC.getIndex()])){
        	contractVO.setInvalidContract(true);
        }else{
        	contractVO.setPaymentFsc(contract[IjsExcelTemplateConstants.PAYMENT_FSC.getIndex()]);
        }
        if(isInValidInput(contract[IjsExcelTemplateConstants.CURRENCY.getIndex()])){
        	contractVO.setInvalidContract(true);
        }else{
        	contractVO.setCurrency(contract[IjsExcelTemplateConstants.CURRENCY.getIndex()]);
        }
        
        try{
        	if (contract[IjsExcelTemplateConstants.PRIORITY.getIndex()] != null
                    && contract[IjsExcelTemplateConstants.PRIORITY.getIndex()].length() > 0 ) {
                    contractVO.setPriority(Integer.parseInt(contract[IjsExcelTemplateConstants.PRIORITY.getIndex()]));    
            }
        }catch(NumberFormatException nfe){
        	contractVO.setPriority(-1);
        }
        
        contractVO.setFromLocType(contract[IjsExcelTemplateConstants.FROM_LOC_TYPE.getIndex()]);
        contractVO.setFromLocation(contract[IjsExcelTemplateConstants.FROM_LOCATION.getIndex()]);
        contractVO.setFromTerminal(contract[IjsExcelTemplateConstants.FROM_TERMINAL.getIndex()]);
        contractVO.setToLocType(contract[IjsExcelTemplateConstants.TO_LOC_TYPE.getIndex()]);
        contractVO.setToLocation(contract[IjsExcelTemplateConstants.TO_LOCATION.getIndex()]);
        contractVO.setToTerminal(contract[IjsExcelTemplateConstants.TO_TERMINAL.getIndex()]);
        try{
        	 if (contract[IjsExcelTemplateConstants.DAYS.getIndex()] != null
        	            && contract[IjsExcelTemplateConstants.DAYS.getIndex()].length() > 0) {
        	            contractVO.setDays(Integer.parseInt(contract[IjsExcelTemplateConstants.DAYS.getIndex()]));    
        	 }
        }catch(NumberFormatException nfe){
        	contractVO.setDays(-1);
        }
        try{
        	if (contract[IjsExcelTemplateConstants.HOURS.getIndex()] != null
                    && contract[IjsExcelTemplateConstants.HOURS.getIndex()].length() > 0) {
                    contractVO.setHours(Integer.parseInt(contract[IjsExcelTemplateConstants.HOURS.getIndex()]));    
                }
       }catch(NumberFormatException nfe){
       	contractVO.setHours(-1);
       }
        try{
        	if (contract[IjsExcelTemplateConstants.DISTANCE.getIndex()] != null
                    && contract[IjsExcelTemplateConstants.DISTANCE.getIndex()].length() > 0) {
                    contractVO.setDistance(Integer.parseInt(contract[IjsExcelTemplateConstants.DISTANCE.getIndex()]));    
            }
       }catch(NumberFormatException nfe){
       	contractVO.setDistance(-1);
       }
        
        
        contractVO.setUom(contract[IjsExcelTemplateConstants.UOM.getIndex()]);
       
        contractVO.setExempted((contract[IjsExcelTemplateConstants.EXCEMPTED.getIndex()] != null && "YES".equalsIgnoreCase(contract[21])) ? true : false);
       
       if(isCostSheet){ 
        contractVO.setTermVal(contract[IjsExcelTemplateConstants.TERM.getIndex()]);//MD
       }
        
       // contractVO.setDetailSeqNum(contract[IjsExcelTemplateConstants.BILL_DTL_SEQ_NOS.getIndex()]);
        //rateTemplateVO.setIjsContractVO(contractVO);
        return contractVO;
    }

    private IjsRateVO populateCostRateInfo(IjsExcelUploadTemplateVO excelTemplateVO, String[] costRate,IjsContractVO contractVO) throws IJSException {
        IjsRateVO rateVO = new IjsRateVO();
        if(costRate[IjsExcelTemplateConstants.COST_RATE_START_DATE.getIndex()]==null || costRate[IjsExcelTemplateConstants.COST_RATE_START_DATE.getIndex()].trim().length()==0){
        	return null;
        }
        try{
        	if (validateDateFormate(costRate[IjsExcelTemplateConstants.COST_RATE_START_DATE.getIndex()]))
        		rateVO.setStartDate(costRate[IjsExcelTemplateConstants.COST_RATE_START_DATE.getIndex()]);
        }catch(IJSException ie){
        	contractVO.setInvalidContract(true);
        	return null;
        }
//        if (validateDateFormate(costRate[IjsExcelTemplateConstants.COST_RATE_START_DATE.getIndex()]))
//            rateVO.setStartDate(costRate[IjsExcelTemplateConstants.COST_RATE_START_DATE.getIndex()]);
        try{
        	if (validateDateFormate(costRate[IjsExcelTemplateConstants.COST_RATE_END_DATE.getIndex()])) 
                rateVO.setEndDate(costRate[IjsExcelTemplateConstants.COST_RATE_END_DATE.getIndex()]);
        }catch(IJSException ie){
        	contractVO.setInvalidContract(true);
        	return null;
        }
        if (!isInValidInput(costRate[IjsExcelTemplateConstants.COST_RATE_SERVICE.getIndex()])) {
        		rateVO.setService(costRate[IjsExcelTemplateConstants.COST_RATE_SERVICE.getIndex()]);
        }else{
        	contractVO.setInvalidContract(true);
        	return null;
        }
        String vesselCode=costRate[IjsExcelTemplateConstants.COST_RATE_VESSEL.getIndex()];
        if(vesselCode!=null && !vesselCode.isEmpty()){
        	rateVO.setVesselCodes(vesselCode);
        }else{
        	vesselCode=ASTRIC;
        }
        
        if (!isInValidInput(costRate[IjsExcelTemplateConstants.COST_RATE_MT_LADEN.getIndex()])) {
        		 rateVO.setMtOrLaden(costRate[IjsExcelTemplateConstants.COST_RATE_MT_LADEN.getIndex()]);
        }else{
        	contractVO.setInvalidContract(true);
        	return null;
        }
        
        if (!isInValidInput(costRate[IjsExcelTemplateConstants.COST_RATE_MT_LADEN.getIndex()])) {
   		 rateVO.setMtOrLaden(costRate[IjsExcelTemplateConstants.COST_RATE_MT_LADEN.getIndex()]);
		}else{
		   	contractVO.setInvalidContract(true);
		   	return null;
		 }
        
        if (validateRateBasis(IjsHelper.getRateBaseCd(costRate[IjsExcelTemplateConstants.COST_RATE_BASIS.getIndex()])))
            rateVO.setRateBasis(IjsHelper.getRateBaseCd(costRate[IjsExcelTemplateConstants.COST_RATE_BASIS.getIndex()]));
        
        if (validateEqCategory(IjsHelper.getEqCatelogCd(costRate[IjsExcelTemplateConstants.COST_RATE_EQ_CATEGORY.getIndex()])))
            rateVO.setEqCatq(IjsHelper.getEqCatelogCd(costRate[IjsExcelTemplateConstants.COST_RATE_EQ_CATEGORY.getIndex()]));
        
        //if (validateRateStatus(costRate[IjsExcelTemplateConstants.COST_RATE_STATUS.getIndex()])) 
            rateVO.setRateStatus(IjsHelper.getRateStatusCode(costRate[IjsExcelTemplateConstants.COST_RATE_STATUS.getIndex()]));
        
        rateVO.setChargeCode(costRate[IjsExcelTemplateConstants.COST_RATE_CHARGE_CODE.getIndex()]);    
        
        //rateVO.setTerm(costRate[IjsExcelTemplateConstants.COST_RATE_TERM.getIndex()]);//MD
        rateVO.setCalcMethod(IjsHelper.getClacMethodCode(costRate[IjsExcelTemplateConstants.COST_RATE_CALC_METHOD.getIndex()]));
        
        rateVO.setEqType(costRate[IjsExcelTemplateConstants.COST_RATE_EQ_TYPE.getIndex()]);
        try{
        	if (costRate[IjsExcelTemplateConstants.COST_RATE_UPTO.getIndex()] != null
                    && costRate[IjsExcelTemplateConstants.COST_RATE_UPTO.getIndex()].length() > 0) {
                    rateVO.setUpto(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_UPTO.getIndex()]));    
            }
       }catch(NumberFormatException nfe){
    	   rateVO.setUpto(-1);
       }
        
        
        
       // rateVO.setImpOrExp(costRate[IjsExcelTemplateConstants.COST_RATE_IMP_EXP.getIndex()]);
        
        rateVO.setSplHandling(IjsHelper.getSpHandlingCode(costRate[IjsExcelTemplateConstants.COST_RATE_SPL_HANDLING.getIndex()]));
        
        if (validateCurrency(costRate[IjsExcelTemplateConstants.COST_RATE_CURRENCY.getIndex()])) {
            rateVO.setCurrency(costRate[IjsExcelTemplateConstants.COST_RATE_CURRENCY.getIndex()]);
        }
        
        rateVO.setPortClassCode(costRate[IjsExcelTemplateConstants.COST_RATE_PORT_CLASS.getIndex()]);
        rateVO.setImdgDetails(costRate[IjsExcelTemplateConstants.COST_RATE_IMDG.getIndex()]);
        rateVO.setOogSetup(costRate[IjsExcelTemplateConstants.COST_RATE_OOG.getIndex()]);
        
        rateVO.setPortCode(costRate[IjsExcelTemplateConstants.COST_RATE_PORT_CLASS.getIndex()]);
        rateVO.setImdgCode(costRate[IjsExcelTemplateConstants.COST_RATE_IMDG.getIndex()]);
        rateVO.setOogCode(costRate[IjsExcelTemplateConstants.COST_RATE_OOG.getIndex()]);
        rateVO.setSplCostByBlOrBooking(costRate[IjsExcelTemplateConstants.COST_RATE_SP_CUSTOMER.getIndex()]);
        
        try{
        	//if (costRate[IjsExcelTemplateConstants.COST_RATE_COST20.getIndex()] != null
                 //   && costRate[IjsExcelTemplateConstants.COST_RATE_COST20.getIndex()].length() > 0) {
                   // rateVO.setRate20(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST20.getIndex()])); 
        		String rate20=RutFormatting.getStringToDecimal(costRate[IjsExcelTemplateConstants.COST_RATE_COST20.getIndex()], null);
        		Double.parseDouble(rate20);
                rateVO.setRate20(rate20);   
           // }
       }catch(NumberFormatException nfe){
    	   rateVO.setRate20("-1");
       }
        try{
        	// if (costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()] != null
        	 //           && costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()].length() > 0) {
        		 String rate40=RutFormatting.getStringToDecimal(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()], null);
        		 Double.parseDouble(rate40);
        		// Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]);
        	           // rateVO.setRate40(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]));   
        	     rateVO.setRate40(rate40);   
        	// }
       }catch(NumberFormatException nfe){
    	   rateVO.setRate40("-1");
       }
        
       try{
        	//if (costRate[IjsExcelTemplateConstants.COST_RATE_COST45.getIndex()] != null
            //        && costRate[IjsExcelTemplateConstants.COST_RATE_COST45.getIndex()].length() > 0) {
        		String rate45=RutFormatting.getStringToDecimal(costRate[IjsExcelTemplateConstants.COST_RATE_COST45.getIndex()], null);
       		 Double.parseDouble(rate45);
       		// Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]);
       	           // rateVO.setRate40(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]));   
       	     rateVO.setRate45(rate45);       
          //  }
      }catch(NumberFormatException nfe){
   	   rateVO.setRate45("-1");
      }
        try{
        	//if (costRate[IjsExcelTemplateConstants.COST_RATE_LUMP_SUM.getIndex()] != null
                //    && costRate[IjsExcelTemplateConstants.COST_RATE_LUMP_SUM.getIndex()].length() > 0) {
        		String lumpSum=RutFormatting.getStringToDecimal(costRate[IjsExcelTemplateConstants.COST_RATE_LUMP_SUM.getIndex()], null);
          		 Double.parseDouble(lumpSum);
          		// Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]);
          	           // rateVO.setRate40(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]));   
          	     rateVO.setLumpSum(lumpSum);     
          //  }
      }catch(NumberFormatException nfe){
   	   rateVO.setRate45("-1");
      }
        
        
        
        if (validateRateSequenceNumber(costRate[IjsExcelTemplateConstants.COST_RATE_SEQ_NO.getIndex()]))
            rateVO.setCostRateSequenceNum(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_SEQ_NO.getIndex()]));
        
        //rateVO.setCostDtlSeqNos(costRate[IjsExcelTemplateConstants.COST_DTL_SEQ_NOS.getIndex()]);//MD
         rateVO.setDetailSeqNumbers(costRate[IjsExcelTemplateConstants.COST_DTL_SEQ_NOS.getIndex()]);
        return rateVO; 
    }

    private IjsRateVO populateBillingRateInfo(IjsExcelUploadTemplateVO rateTemplateVO, String[] billingRate,IjsContractVO contractVO) throws IJSException {
        
        IjsRateVO rateVO = new IjsRateVO();
        if(billingRate[IjsExcelTemplateConstants.BILL_RATE_START_DATE.getIndex()]==null || billingRate[IjsExcelTemplateConstants.BILL_RATE_START_DATE.getIndex()].trim().length()==0){
        	return null;
        }
        try{
        	if (validateDateFormate(billingRate[IjsExcelTemplateConstants.BILL_RATE_START_DATE.getIndex()]))
                rateVO.setStartDate(billingRate[IjsExcelTemplateConstants.BILL_RATE_START_DATE.getIndex()]);
        }catch(IJSException ie){
        	contractVO.setInvalidContract(true);
        	return null;
        }
        try{
        	 if (validateDateFormate(billingRate[IjsExcelTemplateConstants.BILL_RATE_END_DATE.getIndex()]))
                 rateVO.setEndDate(billingRate[IjsExcelTemplateConstants.BILL_RATE_END_DATE.getIndex()]);
        }catch(IJSException ie){
        	contractVO.setInvalidContract(true);
        	return null;
        }
        
       
        if(isInValidInput(billingRate[IjsExcelTemplateConstants.BILL_RATE_SERVICE.getIndex()])){
        	contractVO.setInvalidContract(true);
        	return null;
        }else{
        	rateVO.setService(billingRate[IjsExcelTemplateConstants.BILL_RATE_SERVICE.getIndex()]);
        }
        
        
        rateVO.setMtOrLaden(billingRate[IjsExcelTemplateConstants.BILL_RATE_MT_LADEN.getIndex()]);
        if (validateRateBasis(IjsHelper.getRateBaseCd(billingRate[IjsExcelTemplateConstants.BILL_RATE_RATE_BASIS.getIndex()])))
            rateVO.setRateBasis(IjsHelper.getRateBaseCd(billingRate[IjsExcelTemplateConstants.BILL_RATE_RATE_BASIS.getIndex()]));
        
        if (validateEqCategory(IjsHelper.getEqCatelogCd(billingRate[IjsExcelTemplateConstants.BILL_RATE_EQ_CATEGORY.getIndex()])))
            rateVO.setEqCatq(IjsHelper.getEqCatelogCd(billingRate[IjsExcelTemplateConstants.BILL_RATE_EQ_CATEGORY.getIndex()]));
        
        //if (validateRateStatus(billingRate[IjsExcelTemplateConstants.BILL_RATE_RATE_STATUS.getIndex()])) 
            rateVO.setRateStatus(IjsHelper.getRateStatusCode(billingRate[IjsExcelTemplateConstants.BILL_RATE_RATE_STATUS.getIndex()]));
        
        //rateVO.setTerm(billingRate[IjsExcelTemplateConstants.BILL_RATE_TERM.getIndex()]);
        
        rateVO.setCalcMethod(IjsHelper.getClacMethodCode(billingRate[IjsExcelTemplateConstants.BILL_RATE_CALC_METHOD.getIndex()]));
        
        rateVO.setEqType(billingRate[IjsExcelTemplateConstants.BILL_RATE_EQ_TYPE.getIndex()]);
        try{
        	if (billingRate[IjsExcelTemplateConstants.BILL_RATE_UPTO.getIndex()] != null 
                    && billingRate[IjsExcelTemplateConstants.BILL_RATE_UPTO.getIndex()].length() > 0) {
                    rateVO.setUpto(Integer.parseInt(billingRate[IjsExcelTemplateConstants.BILL_RATE_UPTO.getIndex()]));    
                }
        	
      }catch(NumberFormatException nfe){
   	   rateVO.setUpto(-1);
      }
        
        
        rateVO.setUom(billingRate[IjsExcelTemplateConstants.BILL_RATE_UOM.getIndex()]);
        
        rateVO.setCurrency(billingRate[IjsExcelTemplateConstants.BILL_RATE_CURRENCY.getIndex()]);
        try{
        	if (billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL20.getIndex()] != null
                    && billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL20.getIndex()].length() > 0 ) {
        		String rate20=RutFormatting.getStringToDecimal(billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL20.getIndex()], null);
          		 Double.parseDouble(rate20);
          		// Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]);
          	           // rateVO.setRate40(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]));   
          	     rateVO.setRate20(rate20);     
            }
      }catch(NumberFormatException nfe){
   	   rateVO.setRate20("-1");
      }
        
        try{
        	if (billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL40.getIndex()] != null
                    && billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL40.getIndex()].length() > 0 ) {
        		String rate40=RutFormatting.getStringToDecimal(billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL40.getIndex()], null);
         		 Double.parseDouble(rate40);
         		// Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]);
         	           // rateVO.setRate40(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]));   
         	     rateVO.setRate40(rate40); 
                }
      }catch(NumberFormatException nfe){
   	   rateVO.setRate40("-1");
      }  
        try{
        	if (billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL45.getIndex()] != null
                    && billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL45.getIndex()].length() > 0 ) {
        		String rate45=RutFormatting.getStringToDecimal(billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL45.getIndex()], null);
        		 Double.parseDouble(rate45);
        		// Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]);
        	           // rateVO.setRate40(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]));   
        	     rateVO.setRate45(rate45); 
                }
      }catch(NumberFormatException nfe){
   	   rateVO.setRate45("-1");
      }  
        
        
        //rateTemplateVO.getIjsBillingRateVOList().add(rateVO);
        
        if (validateRateSequenceNumber(billingRate[IjsExcelTemplateConstants.BILL_RATE_SEQ_NO.getIndex()]))
            rateVO.setCostRateSequenceNum(Integer.parseInt(billingRate[IjsExcelTemplateConstants.BILL_RATE_SEQ_NO.getIndex()]));
        
        //rateVO.setBillDtlSeqNos(billingRate[IjsExcelTemplateConstants.BILL_DTL_SEQ_NOS.getIndex()]);//MD
         rateVO.setDetailSeqNumbers(billingRate[IjsExcelTemplateConstants.BILL_DTL_SEQ_NOS.getIndex()]);
        
        return rateVO;
    }

    private boolean validateDateFormate(String date) throws IJSException {
        try {
            new SimpleDateFormat("dd/mm/yyyy").parse(date.trim());
            return true;
        } catch (ParseException e) {
            throw new IJSException("Date Format is not correct");
        }
    }

//    private boolean validateTransportMode(String mot) throws IJSException {
//        IjsHelper.getTransCode(mot);
//        if (mot.length() != 2) {
//            throw new IJSException("Mode of Transport is invalid");
//        }
//        try {
//            Integer.parseInt(mot);
//        }
//        catch(Exception ex) {
//            return false;
//        }
//        return true;
//    }

    private boolean validateRateBasis(String rateBasis) throws IJSException {
        if (IjsHelper.getRateBaseValue(rateBasis) == null)
            throw new IJSException("Rate Basis is Invalid");
        return true;
    }

    private boolean validateRateStatus(String rateStatus) throws IJSException {
        if (IjsHelper.getRateStatus(rateStatus) == null) {
            throw new IJSException("Rate status is Invalid");
        }
        return true;
    }

    private boolean validateCurrency(String currency) throws IJSException {
        if (currency.length() != 3) 
            throw new IJSException("Currency is Invalid");
        return true;
    }

    private boolean validateEqCategory(String eqCategory) throws IJSException {
        if (IjsHelper.getEqCatelog(eqCategory) == null) {
            throw new IJSException("Eq Category is Invalid");
        }
        return true;
    }

    private IjsContractSearchUIM transformUploadResults(IjsContractUploadVO results, IjsContractSearchUIM actionForm) {
        IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
        String folder = actionForm.getUploadVO().getFolderPath();
        String fileName = actionForm.getUploadVO().getFileName();
        String resultFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_RESULTS.LOG" ;
        String resultPath = folder + resultFileName;
//        if (results != null && results.size() > 0) {
//            ijsContractSearchUIM.setUploadResultMessage("Some Records failed to Insert / Update.");
//            ijsContractSearchUIM.setErrorCode(IjsErrorCode.UI_IJS_CNTR_UPLD_EX_10002.getErrorCode());
//            generateUploadResultsFile(resultPath , results);
//        }
//        else {
//            ijsContractSearchUIM.setUploadResultMessage("Records Uploaded sucessfully.");    
//            ijsContractSearchUIM.setErrorCode(IjsErrorCode.UI_IJS_CNTR_UPLD_EX_10001.getErrorCode());
//        }
        
        ijsContractSearchUIM.setAction(actionForm.getUploadVO().getAction());
        ijsContractSearchUIM.setUploadVO(results);
        return ijsContractSearchUIM;
    }
    //##05 END

    private boolean validateRateSequenceNumber(String seqNo) {
        try {
            if (seqNo != null && seqNo.length() > 0) {
                Integer.parseInt(seqNo);
                return true;
            }
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return false;
    }

    private void generateUploadResultsFile(String fileName , List<String> results) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);
            bw = new BufferedWriter(fw);
            StringBuffer content = new StringBuffer("Copyright RCL Public Co., Ltd. 2007\r\n\r\n\r\n");
            int index = 1;
            for (String restult : results) {
                content.append(index + ".  " + restult + "\r\n\r\n");
                index++;
            }
            bw.write(content.toString());
            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

//    private void validateRequest(IjsContractVO ijsContractVO) throws IJSException {
//        String startDate = ijsContractVO.getStartDate();
//        String endDate = ijsContractVO.getEndDate();
//        Date sDate = null;
//        Date eDate = null;
//
//        try {
//            sDate = new SimpleDateFormat("dd/mm/yyyy").parse(startDate);
//            eDate = new SimpleDateFormat("dd/mm/yyyy").parse(endDate);
//            if (sDate.after(eDate)) {
//                throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10026.getErrorCode());
//            }
//        } catch (ParseException e) {
//            // TO-DO
//            throw new IJSException("Date is not valid");
//        }
//    }
    
    
    public IjsContractSearchUIM getTermList(String userInfo) throws IJSException{
        List<IjsContractShipmentTermVO> lstTermList=contractDao.getTermList(userInfo);
        return transformTermListoToUim(lstTermList);
    }
    
    public IjsContractSearchUIM getTransportMode(String userInfo, String vendorCode) throws IJSException{
        List<IjsContractTransportModeVO> lstTransportModeList=contractDao.getTransportMode(userInfo, vendorCode);
        String lstrVendorName = contractDao.getVendorName(userInfo, vendorCode);
        return transformTransportModeListoToUim(lstTransportModeList, lstrVendorName);
    }    
    
    public IjsContractSearchUIM getCurrencyForFSC(IjsSearchParamVO contractParam,String userInfo, String paymentFscCode) throws IJSException{
        List<IjsCntrCurrencyLookUpVO> lstTransportModeList=contractDao.getCurrencyForFSC(userInfo, paymentFscCode);
        return transformFSCCurrencyToUim(lstTransportModeList);
    }    
    
    public IjsContractSearchUIM getCurrencyForLoC(String userInfo, String fromLocation, String fromLocType,
                                                  String toLocation, String toLocType,String transMode,String locType)  throws IJSException {
    IjsCntrCurrencyLookUpVO lstTransportModeVO=contractDao.getCurrencyForLocation(userInfo, !FROM_LOC_TYPE.equals(locType)?toLocation:fromLocation,
    		                                         !FROM_LOC_TYPE.equals(locType)?toLocType:fromLocType);
    String msgCode =null;
    if(!FEEDER.equalsIgnoreCase(transMode) && fromLocation!=null && toLocation!=null &&
    		(lstTransportModeVO.getCurrencyCode()!=null)){
    	msgCode =contractDao.validateFromToLocSetup(fromLocation, fromLocType, toLocation, toLocType, transMode);
    }
    
    
    return transformLOCCurrencyToUim(lstTransportModeVO,msgCode);
    }
    
    public IjsContractSearchUIM getTerminalCode(IjsSearchParamVO<IjsContractSearchParamVO> ijsContractSearchParamVO, 
                                                  String userInfo, String fromLocation, String fromLocationType, String terminal)  throws IJSException {
    
    String  terminalValid =contractDao.getTerminalCode(userInfo, fromLocation, fromLocationType, terminal);
    
    return transformLOCCurrencyToUim(terminalValid, fromLocationType);
    }
    
    public IjsContractSearchUIM getSelectedContractData(String vendorCode,String userInfo, String fromLocation, String fromLocType,
            String toLocation, String toLocType,String transMode,String locType)  throws IJSException {
     IjsContractSearchUIM currencyForLocUIM=getCurrencyForLoC(userInfo,fromLocation,fromLocType,toLocation,toLocType,transMode,locType);	
     IjsContractSearchUIM transModeUIM=getTransportMode(userInfo, vendorCode);
     
     currencyForLocUIM.setTransportModeList(transModeUIM.getTransportModeList());
     currencyForLocUIM.setVendorName(transModeUIM.getVendorName());
     if(transModeUIM.getErrorCode()!=null){
    	 if(currencyForLocUIM.getErrorCode()!=null){
    		 currencyForLocUIM.setErrorCode("IJS_TRANS_CURR_ERROR");  
    	 }else{
    		 currencyForLocUIM.setErrorCode(transModeUIM.getErrorCode());  
    	 }
    	 
     }
     
     IjsContractSearchUIM termUIM=getTermList(userInfo); 
     currencyForLocUIM.setTermList(termUIM.getTermList());
    return currencyForLocUIM;
}
    
    public IjsContractSearchUIM priorityCorrection(IjsContractVO contractVO,String userId) throws IJSException {
            String errorCode = contractDao.priorityCorrection(contractVO,userId);
            IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
            ijsContractSearchUIM.setErrorCode(errorCode);
            return ijsContractSearchUIM;
        }
    private IjsContractSearchUIM transformTermListoToUim(List<IjsContractShipmentTermVO> results) {
        IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
        ijsContractSearchUIM.setTermList(results);
        ijsContractSearchUIM.setAction("getTermData");
        return ijsContractSearchUIM;
    }
    
    private IjsContractSearchUIM transformTransportModeListoToUim(List<IjsContractTransportModeVO> results, String vendorName) {
        IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
        if(results!=null && results.size()>0 && vendorName!=null && vendorName.length()>0 ){
        ijsContractSearchUIM.setTransportModeList(results);
            ijsContractSearchUIM.setVendorName(vendorName); 
        }
        else{
            ijsContractSearchUIM.setErrorCode("IJS_EX_10047");
        }
        
        
        ijsContractSearchUIM.setAction("getTransportMode");
        return ijsContractSearchUIM;
    }
    
    private IjsContractSearchUIM transformFSCCurrencyToUim(List<IjsCntrCurrencyLookUpVO> results) {
        IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
        if(results!=null && results.size()>0){
        ijsContractSearchUIM.setFscCurrencyList(results);
        }
        else{
            ijsContractSearchUIM.setErrorCode("IJS_EX_10048");
        }
        ijsContractSearchUIM.setAction("getContractCurrency");
        return ijsContractSearchUIM;
    }


    private IjsContractSearchUIM transformLOCCurrencyToUim(IjsCntrCurrencyLookUpVO lstTransportModeList,String msgCode) {
        IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
        ijsContractSearchUIM.setFscCurrencyCode(lstTransportModeList.getCurrencyCode());
        ijsContractSearchUIM.setFscCode(lstTransportModeList.getCurrencyName());
        ijsContractSearchUIM.setErrorCode(msgCode);
        ijsContractSearchUIM.setAction("getfscCurrencyCode");
        return ijsContractSearchUIM;
    }
    
    private IjsContractSearchUIM transformLOCCurrencyToUim(String terminalValid, String fromLocationType) {
        IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
        if(terminalValid != null &&  !("").equals(terminalValid) && !("0").equals(terminalValid))
          ijsContractSearchUIM.setTerminalValid(terminalValid);
        else if(("0").equals(terminalValid))
          ijsContractSearchUIM.setErrorCode(IjsErrorCode.DB_IJS_CNTR_EX_10049.getErrorCode());
          
        ijsContractSearchUIM.setAction("validateTerminal");
        return ijsContractSearchUIM;
    }
    
    private boolean isInValidInput(String input){
    	if(input==null ||input.isEmpty()){
    		return true;
    	}
    	return false;
    }
}
