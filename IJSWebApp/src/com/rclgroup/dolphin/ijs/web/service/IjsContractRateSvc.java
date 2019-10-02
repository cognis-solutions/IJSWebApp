/*-----------------------------------------------------------------------------------------------------------
IjsContractRateSvc.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 07/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS             Contract Rate srv functionality for getting contract cost rate and thire tranformation
02 05/10/17  NIIT       IJS             add Contract cost Rate srv functionality 
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rclgroup.dolphin.ijs.web.common.IjsContractRateResult;
import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.comparator.IjsRateComparator;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsMessageCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractRateDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractRateUIM;
import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractOogSetupVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsConvertedRateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsCostImdgPortClassVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;

public class IjsContractRateSvc {
    IjsContractRateDao ijsContractRateDao;
    public static final String COST_RATE="COST_RATE";
    public static final String BILLING_RATE="BILLING_RATE";
    public static final String BOTH="BOTH";
    public static final String APPROVED="Approved";
    public IjsContractRateSvc(IjsContractRateDao ijsContractRateDao) {
        this.ijsContractRateDao = ijsContractRateDao;
    }
    
    /**
     * getContractRateList method for getting cost rate list base on search criteria
     * @param actionForm
     * @param userInfo
     * @param actionType
     * @return
     * @throws IJSException
     */
    public IjsContractRateUIM getContractRateList(IjsContractRateUIM actionForm, 
                                    String userInfo, String actionType) throws IJSException {
    	String terminalDepotCode=actionForm.getCostRateSetup()!=null?actionForm.getCostRateSetup().getTerminalDepotCode():null;
        IjsContractRateUIM rateUim = ijsContractRateDao.getContractRateList
                                (terminalDepotCode,actionForm.getRoutingNumber(),userInfo, actionType, true);
        //CR#04 Start
        validateAndSetRatesForContractCurrency(actionForm.getRateType(),actionForm.getPaymentFSC(),rateUim.getRateResults().getResults(),rateUim.getBillingRateResults().getResults());
        //CR#04 End
         return tranformResults(rateUim, actionForm.getAction());
    }

    private IjsContractRateUIM tranformResults(IjsContractRateUIM rateUim, String actionType) {
        
        if (rateUim.getBillingRateResults() != null && rateUim.getBillingRateResults().getResults() != null) {
            for (IjsRateVO ijsRateVO : rateUim.getBillingRateResults().getResults()) {
                if(ijsRateVO.getOperation()==null){
                    ijsRateVO.setStartDate(dateFormatConvertion(ijsRateVO.getStartDate()));
                    ijsRateVO.setEndDate(dateFormatConvertion(ijsRateVO.getEndDate()));
                    if (!dateExpiredCheck(ijsRateVO.getEndDate()) && !ijsRateVO.isErrorAllreadySet()) {
                        ijsRateVO.setErrorMsg(IjsMessageCode.IJS_CNTR_DATE_EXPIRED.getMsgCode());
                    }
                }
            }
        }
        if (rateUim.getRateResults() != null && rateUim.getRateResults().getResults() != null) {
            for (IjsRateVO ijsRateVO : rateUim.getRateResults().getResults()) {
                if(ijsRateVO.getOperation()==null){
                    ijsRateVO.setStartDate(dateFormatConvertion(ijsRateVO.getStartDate()));
                    ijsRateVO.setEndDate(dateFormatConvertion(ijsRateVO.getEndDate()));
                    if (!dateExpiredCheck(ijsRateVO.getEndDate()) && !ijsRateVO.isErrorAllreadySet()) {
                        ijsRateVO.setErrorMsg(IjsMessageCode.IJS_CNTR_DATE_EXPIRED.getMsgCode());
                    } 
                }
               
            }
        }
        rateUim.setAction(actionType);
        return rateUim;
    }

    private  String dateFormatConvertion(String date) {
        //20170930
        if (date != null) {
            return   date.substring(6) + "/" +date.substring(4,6) + "/" +date.substring(0,4);    
        }
        return null;
    }
    private  boolean dateExpiredCheck(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date currentDate = null;
        Date endDate = null;
        try {
            endDate = sdf.parse(date);
             currentDate=new Date();
            
        } catch (ParseException e) { 
            // TO-DO
            e.printStackTrace();
        }
        return currentDate.before(endDate);
    }
    //##02 BEGIN
    /**
     * saveOrUpdateCostRate method for adding cost rate record for contract
     * @param actionForm
     * @param userInfo
     */
    public IjsContractRateUIM saveOrUpdateCostRate(IjsContractRateUIM actionForm, String userInfo) throws IJSException {
        validateRequest(actionForm.getIjsRateVO());
        return transformRateResults(ijsContractRateDao
            .saveOrUpdateCostRate(actionForm, userInfo),actionForm);
    }
    /**
     * costRateDelOrAppOrRej method for delete , reject,approve cost rate
     * @param i
     * @param string
     * @param string1
     * @throws IJSException 
     */
    public IjsContractRateUIM costRateMaintainance(List<Integer> costRateSeqNumList, String userInfo, String action, IjsContractRateUIM actionForm) throws IJSException {
    	
    	return ijsContractRateDao.costRateMaintainance(costRateSeqNumList,userInfo,action,actionForm);
    }

    //##02 END


    private IjsContractRateUIM transformRateResults(String errorCode, IjsContractRateUIM actionForm) {
        IjsContractRateUIM ijsContractRateUIM = new IjsContractRateUIM();
        ijsContractRateUIM.setErrorCode(errorCode);
        ijsContractRateUIM.setAction(actionForm.getAction());
        ijsContractRateUIM.setRoutingNumber(actionForm.getRoutingNumber());
        return ijsContractRateUIM;
    }

    public IjsContractRateUIM billingRateMaintainance(List<Integer> bilingRateSeqNumList, String userInfo, 
                                        String action, IjsContractRateUIM actionForm) throws IJSException {
    	
        return ijsContractRateDao.billingRateMaintainance(bilingRateSeqNumList,userInfo,action, actionForm);  
    }

    public IjsContractRateUIM saveOrUpdateBillingRate(IjsContractRateUIM actionForm, 
                                        String userInfo) throws IJSException {
        validateRequest(actionForm.getIjsRateVO());
        return transformRateResults(ijsContractRateDao.saveOrUpdateBillingRate(actionForm, userInfo),actionForm);
    }

    public IjsContractRateDao getIjsContractRateDao() {
        return ijsContractRateDao;
    }

    private void validateRequest(IjsRateVO ijsRateVO) throws IJSException {
        // validation for rate (rates should not be negative)
    	String rate20=RutFormatting.getStringToDecimal(ijsRateVO.getRate20(), null);
    	String rate40=RutFormatting.getStringToDecimal(ijsRateVO.getRate40(), null);
    	String rate45=RutFormatting.getStringToDecimal(ijsRateVO.getRate45(), null);
    	String lumpsum=RutFormatting.getStringToDecimal(ijsRateVO.getLumpSum(), null);
    	ijsRateVO.setLumpSum(lumpsum);
        if (Double.parseDouble(rate20) < 0 || Double.parseDouble(rate40) < 0 || Double.parseDouble(rate45) < 0) {
        	
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10024.getErrorCode());
        }else{
        	ijsRateVO.setRate20(rate20);
        	ijsRateVO.setRate40(rate40);
        	ijsRateVO.setRate45(rate45);
        }
        
        IjsHelper.validateDate(ijsRateVO.getStartDate(), ijsRateVO.getEndDate());  
    }


    /**
     * saveOrUpdateCostRate method for adding cost rate record for contract
     * @param actionForm
     * @param userInfo
     */
    public IjsContractRateUIM saveOrUpdateCostRateList(IjsContractRateUIM actionForm, String userInfo) throws IJSException {
    	//CR#04 Start
    	IjsContractRateUIM rateUim=ijsContractRateDao.saveOrUpdateCostRateList(actionForm, userInfo);
    	validateAndSetRatesForContractCurrency(COST_RATE,actionForm.getPaymentFSC(),rateUim.getRateResults().getResults(),rateUim.getBillingRateResults().getResults());
        //CR#04 End
        return tranformResults(rateUim,"addEditCostRateList");
    }
    
    /**
     * saveOrUpdateCostRate method for adding cost rate record for contract
     * @param actionForm
     * @param userInfo
     */
    public IjsContractRateUIM saveOrUpdateBillingRateList(IjsContractRateUIM actionForm, String userInfo) throws IJSException {
    	//CR#04 Start
    	IjsContractRateUIM rateUim=ijsContractRateDao.saveOrUpdateBillRateList(actionForm, userInfo);
    	validateAndSetRatesForContractCurrency(BILLING_RATE,actionForm.getPaymentFSC(),rateUim.getRateResults().getResults(),rateUim.getBillingRateResults().getResults());
        //CR#04 End
        return tranformResults(rateUim,"addEditBillRateList");
    }
    /**
     * CR#04 OOG setup
     * @param terminalDepotCd
     * @param oogSetupVO
     * @param userInfo
     * @return
     * @throws IJSException
     */
    public IjsContractRateUIM saveOrUpdateOOGSetupList(String terminalDepotCd,List<IjsContractOogSetupVO> oogSetupVO, String userInfo) throws IJSException {
        	IjsContractRateUIM rateUim=new IjsContractRateUIM();
        	rateUim.setCostRateSetup(ijsContractRateDao.saveOrUpdateOOGSetup(terminalDepotCd,oogSetupVO));
        	rateUim.setAction(IjsActionMethod.SAVE_OOG_SETUP.getAction());
            return rateUim;
    }
    /**
     * CR#04 search OOG setup
     * @param terminalDepotCd
     * @param userInfo
     * @return
     * @throws IJSException
     */
    public IjsContractRateUIM searchOOGSetup(String terminalDepotCd , String userInfo) throws IJSException {
    	IjsContractRateUIM rateUim=new IjsContractRateUIM();
    	rateUim.setCostRateSetup(ijsContractRateDao.searchOOGSetup(terminalDepotCd));
    	rateUim.setAction(IjsActionMethod.SEARCH_OOG_SETUP.getAction());
        return rateUim;
    }
    /**
     * CR#04 save OOG setup
     * @param portImdgType
     * @param terminalDepotCd
     * @param portImdgSetupVO
     * @param userInfo
     * @return
     * @throws IJSException
     */
    public IjsContractRateUIM saveOrUpdatePortImdgSetup(String portImdgType,String terminalDepotCd,List<IjsCostImdgPortClassVO> portImdgSetupVO, String userInfo) throws IJSException {
    	IjsContractRateUIM rateUim=new IjsContractRateUIM();
    	rateUim.setCostRateSetup(ijsContractRateDao.saveOrUpdatePortImdgSetup(terminalDepotCd,portImdgType,portImdgSetupVO));
    	rateUim.setAction(IjsActionMethod.SAVE_IMDG_SETUP.getAction());
        return rateUim;
	}
    /**
     * search port/imdg setup
     * @param portImdgType
     * @param terminalDepotCd
     * @param userInfo
     * @return
     * @throws IJSException
     */
	public IjsContractRateUIM searchPortImdgSetup(String portImdgType,String terminalDepotCd , String userInfo) throws IJSException {
		IjsContractRateUIM rateUim=new IjsContractRateUIM();
		rateUim.setCostRateSetup(ijsContractRateDao.searchPortImdgSetup(terminalDepotCd,portImdgType));
		rateUim.setAction(IjsActionMethod.SEARCH_PORT_SETUP.getAction());
	    return rateUim;
	}
   
   private void validateAndSetRatesForContractCurrency(String rateType,String fscCode,List<IjsRateVO> costRateList,List<IjsRateVO> billingRateList) throws IJSException{
	  
	   switch(rateType){
	   case COST_RATE:
		   setBillingRateForCost(fscCode,costRateList,billingRateList); 
	       break;
	   case BILLING_RATE:
		   setCostRateForBilling(fscCode,costRateList,billingRateList); 
	       break;
	   case BOTH:
		   setBillingRateForCost(fscCode,costRateList,billingRateList); 
		   setCostRateForBilling(fscCode,costRateList,billingRateList); 
	       break;
	   }
   }
   private void setBillingRateForCost(String fscCode,List<IjsRateVO> costRateList,List<IjsRateVO> billingRateList) throws IJSException{
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	   List<IjsConvertedRateVO> effectiveBillingRatesForCurrentDate=null;
	   List<IjsConvertedRateVO> effectiveBillingRatesForOutRange=null;
	   List<IjsConvertedRateVO> effectiveBillingRatesForCost=null;
	   Map<String,Double> currencyExchangeMap=new HashMap<>();
	   Map<String,Map<String,Map<String,IjsConvertedRateVO>>> effectiveBillingRateMap=new HashMap<>();
	   
	   if(costRateList!=null && !costRateList.isEmpty() && billingRateList!=null && !billingRateList.isEmpty()){
		   Map<String,IjsConvertedRateVO> dateRangeCurrMap=null;
		   Date currentDate = new Date();
		   Date modifiedCurrentDate=null;
		   try {
			  modifiedCurrentDate= sdf.parse(new SimpleDateFormat("yyyyMMdd").format(currentDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		   for(IjsRateVO costRate:costRateList){
			   String dateRangeKey=costRate.getStartDate()+":"+costRate.getEndDate();
			   boolean billingFoundForCurrentDate=false;
			   
			   Map<String,Map<String,IjsConvertedRateVO>> dateRangeRateData=effectiveBillingRateMap.get(costRate.getMtOrLaden());
			   if(!APPROVED.equals(costRate.getRateStatus())){
				   continue;
			   }
			   if(dateRangeRateData==null ){
				   dateRangeRateData=new HashMap<>();
				   dateRangeCurrMap=null;
			   }else{
				   dateRangeCurrMap=dateRangeRateData.get(dateRangeKey);
			   }
				   if(dateRangeCurrMap==null 
					   ||dateRangeCurrMap.get(costRate.getCurrency())==null){
					   effectiveBillingRatesForCurrentDate=new ArrayList<>();
					   effectiveBillingRatesForCost=new ArrayList<>();
					   effectiveBillingRatesForOutRange=new ArrayList<>();
					   IjsConvertedRateVO ijsConvertedRateVO=new IjsConvertedRateVO();
					   Date costEffectiveDate=null;
					   Date costExpiryDate=null;
					   String costMTLaden=costRate.getMtOrLaden();
					   if(costMTLaden==null){
						   continue;
					   }
					   try {
						   if(costRate.isErrorAllreadySet()){
							   continue;
						   }else{
							   costEffectiveDate=sdf.parse(costRate.getStartDate());
							   costExpiryDate=sdf.parse(costRate.getEndDate());
						   }
					} catch (ParseException e) {
						e.printStackTrace();
					}
					   
					   for(IjsRateVO billRate:billingRateList){ 
						   Date billingEffectiveDate=null;
						   Date billingExpiryDate=null;
						   String billingMTLaden=billRate.getMtOrLaden();
						   try {
							  billingEffectiveDate = sdf.parse(billRate.getStartDate());
							  billingExpiryDate=sdf.parse(billRate.getEndDate()); 
						   } catch (ParseException e) {
								e.printStackTrace();
						   }
						   if(billingEffectiveDate!=null && billingExpiryDate!=null && costMTLaden!=null && billingMTLaden!=null && APPROVED.equals(billRate.getRateStatus())){
							   if( (billingEffectiveDate.before(costExpiryDate)||billingEffectiveDate.equals(costExpiryDate))
									   && (billingExpiryDate.after(costEffectiveDate)||billingExpiryDate.equals(costEffectiveDate))
									    && costMTLaden.equalsIgnoreCase(billingMTLaden) ){
								  
								   IjsConvertedRateVO convertedRate=new IjsConvertedRateVO();
								   double rate20=Double.parseDouble(RutFormatting.getStringToDecimal(billRate.getRate20(), null));
								   double rate40=Double.parseDouble(RutFormatting.getStringToDecimal(billRate.getRate40(), null));
								   double rate45=Double.parseDouble(RutFormatting.getStringToDecimal(billRate.getRate45(), null));
								   double exchangeRate=0;
								   String billingCurrency=billRate.getCurrency();
								   if(currencyExchangeMap.get(costRate.getCurrency()+":"+billingCurrency)==null){
									   try{
										   exchangeRate=getExchangeRateAfterCurrencyConversion(fscCode,billingCurrency,costRate.getCurrency());
									   }catch(IJSException ie){
										   System.out.println("IjsContractRateSvc:setBillingRateForCost:"+ie.getMessage());
										   exchangeRate=0;
										   int index=ie.getMessage().lastIndexOf(':');
										   String message=ie.getMessage().substring(index+1);
										   costRate.setExchangeError(message);
									   }
									   
									   currencyExchangeMap.put(costRate.getCurrency()+":"+billingCurrency, exchangeRate);
								   }else{
									   exchangeRate=currencyExchangeMap.get(costRate.getCurrency()+":"+billingCurrency);
								   }
								   rate20=rate20*exchangeRate;
								   rate40=rate40*exchangeRate;
								   rate45=rate45*exchangeRate;
								   convertedRate.setRate20(rate20);
								   convertedRate.setRate40(rate40);
								   convertedRate.setRate45(rate45);
								   if((modifiedCurrentDate.before(billingExpiryDate) || modifiedCurrentDate.equals(billingExpiryDate))
										   &&((modifiedCurrentDate.after(billingEffectiveDate) 
												   || modifiedCurrentDate.equals(billingEffectiveDate))
												   )){
								   
								   effectiveBillingRatesForCurrentDate.add(convertedRate);
								   billingFoundForCurrentDate=true;
								   }else if(!billingFoundForCurrentDate){
									   if(effectiveBillingRatesForOutRange==null){
										   effectiveBillingRatesForOutRange=new ArrayList<>();
									   }
									   effectiveBillingRatesForOutRange.add(convertedRate);
								   }
							   }  //
						   }
						   
					   }//inner for loop
					   if(!effectiveBillingRatesForCurrentDate.isEmpty()){
						   effectiveBillingRatesForCost.addAll(effectiveBillingRatesForCurrentDate);
					   }else{
						   effectiveBillingRatesForCost.addAll(effectiveBillingRatesForOutRange);
					   }
					   
					   if(!effectiveBillingRatesForCost.isEmpty()){
						   Collections.sort(effectiveBillingRatesForCost,new IjsRateComparator("RATE20"));
						   Double effectiveRate20=effectiveBillingRatesForCost.get(0).getRate20();
						   Double effectiveRate40=null;
						   Double effectiveRate45=null;
						   costRate.setEffectiveBillingRate20(RutFormatting.getDoubleToDecimalFormat(effectiveRate20,null));
						   ijsConvertedRateVO.setRate20(effectiveRate20);
						   Collections.sort(effectiveBillingRatesForCost,new IjsRateComparator("RATE40"));
						   effectiveRate40=effectiveBillingRatesForCost.get(0).getRate40();
						   costRate.setEffectiveBillingRate40(RutFormatting.getDoubleToDecimalFormat(effectiveRate40,null));
						   ijsConvertedRateVO.setRate40(effectiveRate40);
						   Collections.sort(effectiveBillingRatesForCost,new IjsRateComparator("RATE45"));
						   effectiveRate45=effectiveBillingRatesForCost.get(0).getRate45();
						   costRate.setEffectiveBillingRate45(RutFormatting.getDoubleToDecimalFormat(effectiveRate45,null));
						   ijsConvertedRateVO.setRate45(effectiveRate45);
						   if(dateRangeCurrMap==null){
							   dateRangeCurrMap=new HashMap<>();
						   }
						   dateRangeCurrMap.put(costRate.getCurrency(), ijsConvertedRateVO);
						   dateRangeRateData.put(dateRangeKey,dateRangeCurrMap);
						   effectiveBillingRateMap.put(costRate.getMtOrLaden(),dateRangeRateData);
					   }
					  
				   } else{
				   IjsConvertedRateVO dateRangeRate=dateRangeRateData.get(dateRangeKey).get(costRate.getCurrency());
				   costRate.setEffectiveBillingRate20(RutFormatting.getDoubleToDecimalFormat(dateRangeRate.getRate20(),null));
				   costRate.setEffectiveBillingRate40(RutFormatting.getDoubleToDecimalFormat(dateRangeRate.getRate40(),null));
				   costRate.setEffectiveBillingRate45(RutFormatting.getDoubleToDecimalFormat(dateRangeRate.getRate45(),null));
			   }
		   }
	   }
	   
	   
   }
   private double getExchangeRateAfterCurrencyConversion(String fscCode,String fromCurrency,String toCurrency) throws IJSException{
	   return ijsContractRateDao.getExchangeRate(fscCode,fromCurrency,toCurrency);
   }
   private void setCostRateForBilling(String fscCode,List<IjsRateVO> costRateList,List<IjsRateVO> billingRateList) throws IJSException{
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	   List<IjsConvertedRateVO> effectiveCostRatesForCurrentDate=null;
	   List<IjsConvertedRateVO> effectiveCostRatesForOutRange=null;
	   List<IjsConvertedRateVO> effectiveCostRatesForBilling=null;
	   
	   Map<String,Double> currencyExchangeMap=new HashMap<>();
	   Map<String,Map<String,Map<String,IjsConvertedRateVO>>> effectiveCostRateMap=new HashMap<>();
	   
	   if(costRateList!=null && !costRateList.isEmpty() && billingRateList!=null && !billingRateList.isEmpty()){
		   Map<String,IjsConvertedRateVO> dateRangeCurrMap=null;
		   for(IjsRateVO billingRate:billingRateList){
			   boolean costFoundForCurrentDate =false;
			   String dateRangeKey=billingRate.getStartDate()+":"+billingRate.getEndDate();
			   if(!APPROVED.equals(billingRate.getRateStatus())){
				   continue;
			   }
			   Map<String,Map<String,IjsConvertedRateVO>> dateRangeRateData=effectiveCostRateMap.get(billingRate.getMtOrLaden());
			   
			   if(dateRangeRateData==null ){
				   dateRangeRateData=new HashMap<>();
				   dateRangeCurrMap=null;
			   }else{
				   dateRangeCurrMap=dateRangeRateData.get(dateRangeKey);
			   }
				   if(dateRangeCurrMap==null 
					   ||dateRangeCurrMap.get(billingRate.getCurrency())==null){
					   effectiveCostRatesForCurrentDate=new ArrayList<>();
					   effectiveCostRatesForOutRange=new ArrayList<>();
					   effectiveCostRatesForBilling=new ArrayList<>();
					   IjsConvertedRateVO ijsConvertedRateVO=new IjsConvertedRateVO();
					   Date billingEffectiveDate=null;
					   Date billingExpiryDate=null;
					   String billingMTLaden=billingRate.getMtOrLaden();
					   try {
						   if(billingRate.isErrorAllreadySet()){
							   continue;
						   }else{
							   billingEffectiveDate=sdf.parse(billingRate.getStartDate());
							   billingExpiryDate=sdf.parse(billingRate.getEndDate());
						   }
						   
					} catch (ParseException e) {
						e.printStackTrace();
					}
					   
					   for(IjsRateVO costRate:costRateList){ 
						   Date costEffectiveDate=null;
						   Date costExpiryDate=null;
						   String costMTLaden=costRate.getMtOrLaden();
						   try {
							   costEffectiveDate = sdf.parse(costRate.getStartDate());
							   costExpiryDate=sdf.parse(costRate.getEndDate()); 
						   } catch (ParseException e) {
								e.printStackTrace();
						   }
						   if(costEffectiveDate!=null && costExpiryDate!=null && costMTLaden!=null && billingMTLaden!=null 
								   && APPROVED.equals(costRate.getRateStatus())){
							   if( (costEffectiveDate.before(billingExpiryDate)||costEffectiveDate.equals(billingExpiryDate))
									   && (costExpiryDate.after(billingEffectiveDate)||costExpiryDate.equals(billingEffectiveDate))
									    && costMTLaden.equalsIgnoreCase(billingMTLaden)  ){
								   Date currentDate = new Date();
								   Date modifiedCurrentDate=null;
								   IjsConvertedRateVO convertedRate=new IjsConvertedRateVO();
								   double rate20=Double.parseDouble(RutFormatting.getStringToDecimal(costRate.getRate20(), null));
								   double rate40=Double.parseDouble(RutFormatting.getStringToDecimal(costRate.getRate40(), null));
								   double rate45=Double.parseDouble(RutFormatting.getStringToDecimal(costRate.getRate45(), null));
								   double exchangeRate=0;
								   String billingCurrency=billingRate.getCurrency();
								   if(currencyExchangeMap.get(costRate.getCurrency()+":"+billingCurrency)==null){
									   try{
										   exchangeRate=getExchangeRateAfterCurrencyConversion(fscCode,costRate.getCurrency(),billingCurrency);  
									   }catch(IJSException ie){
										   System.out.println("IjsContractRateSvc:setCostRateForBilling:"+ie.getMessage());
										   exchangeRate=0;
										   int index=ie.getMessage().lastIndexOf(':');
										   String message=ie.getMessage().substring(index+1);
										   billingRate.setExchangeError(message);
									   }
									   
									   currencyExchangeMap.put(costRate.getCurrency()+":"+billingCurrency, exchangeRate);
								   }else{
									   exchangeRate=currencyExchangeMap.get(costRate.getCurrency()+":"+billingCurrency);
								   }
								   rate20=rate20*exchangeRate;
								   rate40=rate40*exchangeRate;
								   rate45=rate45*exchangeRate;
								   convertedRate.setRate20(rate20);
								   convertedRate.setRate40(rate40);
								   convertedRate.setRate45(rate45);
								   try {
									  modifiedCurrentDate= sdf.parse(new SimpleDateFormat("yyyyMMdd").format(currentDate));
									} catch (ParseException e) {
										e.printStackTrace();
									}
								   if((modifiedCurrentDate.before(costExpiryDate) || modifiedCurrentDate.equals(costExpiryDate))
										   &&((modifiedCurrentDate.after(costEffectiveDate) 
												   || modifiedCurrentDate.equals(costEffectiveDate))
												   )){
								  
									   effectiveCostRatesForCurrentDate.add(convertedRate);
									   costFoundForCurrentDate=true;
									   }else if(!costFoundForCurrentDate){
										   if(effectiveCostRatesForOutRange==null){
											   effectiveCostRatesForOutRange=new ArrayList<>();
										   }
										   effectiveCostRatesForOutRange.add(convertedRate);
									   }
							   }  
						   }
					   }//inner for loop
					   if(!effectiveCostRatesForCurrentDate.isEmpty()){
						   effectiveCostRatesForBilling.addAll(effectiveCostRatesForCurrentDate);
					   }else{
						   effectiveCostRatesForBilling.addAll(effectiveCostRatesForOutRange);
					   }
					   if(!effectiveCostRatesForBilling.isEmpty()){
						   int totalRates=effectiveCostRatesForBilling.size();
						   Collections.sort(effectiveCostRatesForBilling,new IjsRateComparator("RATE20"));
						   Double effectiveRate20=effectiveCostRatesForBilling.get(totalRates-1).getRate20();
						   Double effectiveRate40=null;
						   Double effectiveRate45=null;
						   billingRate.setEffectiveCostRate20(RutFormatting.getDoubleToDecimalFormat(effectiveRate20,null));
						   ijsConvertedRateVO.setRate20(effectiveRate20);
						   Collections.sort(effectiveCostRatesForBilling,new IjsRateComparator("RATE40"));
						   effectiveRate40=effectiveCostRatesForBilling.get(totalRates-1).getRate40();
						   billingRate.setEffectiveCostRate40(RutFormatting.getDoubleToDecimalFormat(effectiveRate40,null));
						   ijsConvertedRateVO.setRate40(effectiveRate40);
						   Collections.sort(effectiveCostRatesForBilling,new IjsRateComparator("RATE45"));
						   effectiveRate45=effectiveCostRatesForBilling.get(totalRates-1).getRate45();
						   billingRate.setEffectiveCostRate45(RutFormatting.getDoubleToDecimalFormat(effectiveRate45,null));
						   ijsConvertedRateVO.setRate45(effectiveRate45);
						   if(dateRangeCurrMap==null){
							   dateRangeCurrMap=new HashMap<>();
						   }
						   dateRangeCurrMap.put(billingRate.getCurrency(), ijsConvertedRateVO);
						   dateRangeRateData.put(dateRangeKey,dateRangeCurrMap);
						   effectiveCostRateMap.put(billingRate.getMtOrLaden(),dateRangeRateData);
					   }
					  
				   } else{
				   IjsConvertedRateVO dateRangeRate=dateRangeRateData.get(dateRangeKey).get(billingRate.getCurrency());
				   billingRate.setEffectiveCostRate20(RutFormatting.getDoubleToDecimalFormat(dateRangeRate.getRate20(),null));
				   billingRate.setEffectiveCostRate40(RutFormatting.getDoubleToDecimalFormat(dateRangeRate.getRate40(),null));
				   billingRate.setEffectiveCostRate45(RutFormatting.getDoubleToDecimalFormat(dateRangeRate.getRate45(),null));
			   }
		   }
	   }
   }
}
