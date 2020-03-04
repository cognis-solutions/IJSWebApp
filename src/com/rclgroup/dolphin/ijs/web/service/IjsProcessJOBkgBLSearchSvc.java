package com.rclgroup.dolphin.ijs.web.service;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.common.excel.GlobalUtil;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsExcelTemplateConstants;
import com.rclgroup.dolphin.ijs.web.dao.IjsProcessJOBkgBLSearchDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsMaintainJOSearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOBkgBLSearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSumContDtlDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSumDtlDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSummarySearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsMaintainJOSearchUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsProcessJOBkgBLSearchUIM;
import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.vo.IjsBaseMessageVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsBkgBLScreenSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContainerUploadVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsEQDetailVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsEquipmetLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsExcelUploadTemplateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOChangeVendorVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOSummaryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchContDetailVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOBkgBLSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOBkgBLSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOListParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOSumContDtlVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOSumDtlVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOSummarySearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRoutingListParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSearchParamVO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class IjsProcessJOBkgBLSearchSvc {
    IjsProcessJOBkgBLSearchDao ijsProcessJOBkgBLDao = null;
    IjsJOUploadSvc ijsUploadSvc;
//##CR 03 START
    private static final String EQUIP_IN_DB="EQUIP_IN_DB";
    private static final String EQUIP_LOC="EQUIP_LOC";
    private static final String EQUIP_WITH_LOC_REVERSE="EQUIP_WITH_LOC_REVERSE";
    private static final String EQUIP_EXIST_IN_JO="EQUIP_EXIST_IN_JO";
//##CR 03 END   
    public IjsProcessJOBkgBLSearchSvc(IjsProcessJOBkgBLSearchDao ijsProcessJOBkgBLDao) {
        this.ijsProcessJOBkgBLDao = ijsProcessJOBkgBLDao;
    }
 public IjsProcessJOBkgBLSearchSvc(IjsProcessJOBkgBLSearchDao ijsProcessJOBkgBLDao,IjsJOUploadSvc ijsUploadSvc) {
        this.ijsProcessJOBkgBLDao = ijsProcessJOBkgBLDao;
        this.ijsUploadSvc=ijsUploadSvc;
    }

    public IjsProcessJOBkgBLSearchUIM searchBookingBL(IjsProcessJOBkgBLSearchParamVO bkgBLParam, 
                                               String userInfo,String sessionId) throws IJSException {
        
        List<IjsProcessJOBkgBLSearchDTO> lstBkgBL = 
            ijsProcessJOBkgBLDao.findBkgBL( userInfo, 
                                      bkgBLParam,sessionId);
        return transformDtoToUim(lstBkgBL);
    }
    
    public IjsProcessJOBkgBLSearchUIM changeVendor(String bk_bl_ad,
                                                    String bkgblNumber,
                                                    String routingId,
                                                    String routingIdOLD,
                                                    String vendorID,
                                                    String vendorIDOLD,
                                                    String contractId,
                                                    String transMode,
                                                     String userInfo, 
                                                     String action) throws IJSException {
        String lsterrorCd = 
            ijsProcessJOBkgBLDao.changeVendor(bk_bl_ad,
                                                bkgblNumber,
                                                routingId,
                                                routingIdOLD,
                                                vendorID,
                                                vendorIDOLD,
                                                contractId,transMode,userInfo, action);
        return transform(lsterrorCd,action);
    }
    
    private IjsProcessJOBkgBLSearchUIM transformDtoToUim(List<IjsProcessJOBkgBLSearchDTO> lstbookingBL){
       //TO-DO transform Data Object to VO 
        IjsProcessJOBkgBLSearchUIM ijsProcessJOBookingBLSearchUIM= null;
        IjsSearchResult<IjsProcessJOBkgBLSearchVO> searchResult= null;
        List<IjsProcessJOBkgBLSearchVO> lstSearchResult=null;
        System.out.println("IjsProcessJOBkgBLSearchSvc: before transformation");
        if(lstbookingBL!=null){
            lstSearchResult=new ArrayList<IjsProcessJOBkgBLSearchVO>();
            ijsProcessJOBookingBLSearchUIM= new IjsProcessJOBkgBLSearchUIM();
            searchResult=new IjsSearchResult<IjsProcessJOBkgBLSearchVO>();
            for(IjsProcessJOBkgBLSearchDTO bookingBL:lstbookingBL){
                IjsProcessJOBkgBLSearchVO bkgBLSearchVO=new IjsProcessJOBkgBLSearchVO();
                bkgBLSearchVO.setBkgOrBLNumber(bookingBL.getBkgOrBLNumber());
                bkgBLSearchVO.setBkgOrBLType(bookingBL.getBkgOrBLType());
                bkgBLSearchVO.setTransportMode(bookingBL.getTransportMode());
                bkgBLSearchVO.setBookingStatus(bookingBL.getBookingStatus());
                bkgBLSearchVO.setFromTerminal(bookingBL.getFromTerminal());
                bkgBLSearchVO.setToTerminal(bookingBL.getToTerminal());
                bkgBLSearchVO.setService(bookingBL.getService());
                bkgBLSearchVO.setVessel(bookingBL.getVessel());
                bkgBLSearchVO.setVoyage(bookingBL.getVoyage());
                bkgBLSearchVO.setDirection(bookingBL.getDirection());
                bkgBLSearchVO.setStartDate(bookingBL.getStartDate());
                bkgBLSearchVO.setEndDate(bookingBL.getEndDate());
                bkgBLSearchVO.setCntSize(bookingBL.getCntSize());
                bkgBLSearchVO.setCntType(bookingBL.getCntType());
                bkgBLSearchVO.setCntSplHandling(bookingBL.getCntSplHandling());
                bkgBLSearchVO.setCntSplHandCount(bookingBL.getCntSplHandCount());
                bkgBLSearchVO.setLadenCntTotal(bookingBL.getLadenCntTotal());
                bkgBLSearchVO.setLadenCntAvailable(bookingBL.getLadenCntAvailable());
                bkgBLSearchVO.setLadenCntInJO(bookingBL.getLadenCntInJO());
                bkgBLSearchVO.setEmptyCntTotal(bookingBL.getEmptyCntTotal());
                bkgBLSearchVO.setEmptyCntAvailable(bookingBL.getEmptyCntAvailable());
                bkgBLSearchVO.setEmptyCntInJO(bookingBL.getEmptyCntInJO());
                bkgBLSearchVO.setFromLocation(bookingBL.getFromLocation());
                bkgBLSearchVO.setToLocation(bookingBL.getToLocation());
                bkgBLSearchVO.setRoutingNumber(bookingBL.getRoutingNumber());
                bkgBLSearchVO.setVendorCode(bookingBL.getVendorCode());
                bkgBLSearchVO.setFromLocationTyp(bookingBL.getFromLocationTyp());
                bkgBLSearchVO.setToLocationTyp(bookingBL.getToLocationTyp());
                bkgBLSearchVO.setSpecialHandlingCode(bookingBL.getSpecialHandlingCode());
                bkgBLSearchVO.setPriority(bookingBL.getPriority());
                bkgBLSearchVO.setDgCount(bookingBL.getDgCount());
                bkgBLSearchVO.setOogCount(bookingBL.getOogCount());
                bkgBLSearchVO.setNumContainer(Integer.parseInt(bookingBL.getNumContainer()));
                lstSearchResult.add(bkgBLSearchVO);
            }
            searchResult.setResult(lstSearchResult);
            ijsProcessJOBookingBLSearchUIM.setSearchResult(searchResult);
        }
        System.out.println("IjsProcessJOBkgBLSearchSvc: after transformation");
       return ijsProcessJOBookingBLSearchUIM;
    }    
    
    public IjsProcessJOBkgBLSearchUIM searchJOSummary(IjsProcessJOBkgBLSearchParamVO joSummaryParam, String transMode,
                                                      List<IjsJOSummaryParamVO> llstJOSummaryParam,
                                                      String processJoType,
                                                      String astrSessionId,
                                               String userInfo) throws IJSException {
        

        List<IjsProcessJOSumDtlDTO> lstJOSummarySearch = 
            ijsProcessJOBkgBLDao.searchJOSummary(userInfo, joSummaryParam, llstJOSummaryParam, processJoType, astrSessionId, transMode);
        return transformJOSummaryDtoToUim(lstJOSummarySearch);
    }
    
    
    public IjsProcessJOBkgBLSearchUIM searchJOSummaryAdhoc(IjsRoutingListParamVO joSummaryAdhocParam, 
                                                           String processJoType,
                                                           String vendorCode,
                                                           List<String>  eqList,
                                                           List<IjsEQDetailVO> eqDetails,
                                                           String astrSessionId,
                                                           String userInfo) throws IJSException {
    	String strDetail=transformEQDetailsToString(eqDetails);
        List<IjsProcessJOSumDtlDTO> lstJOSummarySearch = 
            ijsProcessJOBkgBLDao.searchJOSummaryAdhoc( userInfo, joSummaryAdhocParam, astrSessionId, processJoType, vendorCode, eqList,strDetail);
        return transformJOSummaryDtoToUim(lstJOSummarySearch);
    }
    private String transformEQDetailsToString(List<IjsEQDetailVO> eqDetails){
    	StringBuilder strEqDetail=new StringBuilder();
    	for(IjsEQDetailVO eqDetail:eqDetails){
    		strEqDetail=strEqDetail.append(eqDetail.getEqNumber()).append(":").append(eqDetail.getEqType()).append(":").append(eqDetail.getEqSize());
    		strEqDetail=strEqDetail.append(",");
    	}
    	 int lastIndex=strEqDetail.lastIndexOf(",");
         return  strEqDetail.substring(0,lastIndex);
    }
    public IjsProcessJOBkgBLSearchUIM createJob(List<IjsProcessJOListParamVO> processjoFieldList, 
                                                           String reasonCode,
                                                           String transMode,
                                                           String processJoType,
                                                           String astrSessionId,
                                                           String rautingId,
                                                           String userInfo) throws IJSException {
                                                           
        IjsProcessJOBkgBLSearchUIM ijsProcessJOBookingBLSearchUIM = new IjsProcessJOBkgBLSearchUIM();
        IjsSearchResult<String> searchResult = null;
                  searchResult = new IjsSearchResult<String>();        

            List<String> llstJobOrders = ijsProcessJOBkgBLDao.createJob( userInfo, processjoFieldList, astrSessionId, reasonCode, transMode, processJoType);
        searchResult.setResult(llstJobOrders);

            
        ijsProcessJOBookingBLSearchUIM.setSearchResult(searchResult);
            
            return ijsProcessJOBookingBLSearchUIM;
        
    }
    
    public IjsProcessJOBkgBLSearchUIM resetJO(String astrSessionId, String userInfo) throws IJSException {
    
        IjsProcessJOBkgBLSearchUIM ijsProcessJOBookingBLSearchUIM = new IjsProcessJOBkgBLSearchUIM();
        IjsSearchResult<String> searchResult = null;
                  searchResult = new IjsSearchResult<String>(); 
                  
                  List llstJobOrders = new ArrayList();

        ijsProcessJOBkgBLDao.resetJO( userInfo, astrSessionId);
        
        searchResult.setResult(llstJobOrders);

            
        ijsProcessJOBookingBLSearchUIM.setSearchResult(searchResult);
            
            return ijsProcessJOBookingBLSearchUIM;
        
    }
    
    private IjsProcessJOBkgBLSearchUIM transformJOSummaryDtoToUim(List<IjsProcessJOSumDtlDTO> alstJOSummary){
       //TO-DO transform Data Object to VO 
        IjsProcessJOBkgBLSearchUIM ijsProcessJOBookingBLSearchUIM= null;
        IjsSearchResult<IjsProcessJOSumDtlVO> searchResult = null;
        List<IjsProcessJOSumDtlVO> lstSearchResult=null;
        IjsProcessJOSumDtlVO lobjIjsProcessJOSumDtlVO = null;
        
        
        if(alstJOSummary!=null){
            lstSearchResult=new ArrayList<IjsProcessJOSumDtlVO>();
            ijsProcessJOBookingBLSearchUIM = new IjsProcessJOBkgBLSearchUIM();
            searchResult=new IjsSearchResult<IjsProcessJOSumDtlVO>();
            for(IjsProcessJOSumDtlDTO summaryJO:alstJOSummary){
                lobjIjsProcessJOSumDtlVO = new IjsProcessJOSumDtlVO();
                IjsProcessJOSummarySearchVO joSearchSummaryVO = new IjsProcessJOSummarySearchVO();
                IjsProcessJOSummarySearchDTO jobSummary =  summaryJO.getJoSummery();
                if(jobSummary != null){
                    joSearchSummaryVO.setJobOrder(jobSummary.getJobOrder());
                    joSearchSummaryVO.setVendorCode(jobSummary.getVendorCode());
                    joSearchSummaryVO.setContractNumber(jobSummary.getContractNumber());
                    joSearchSummaryVO.setJoDate(jobSummary.getJoDate());
                    joSearchSummaryVO.setSize(jobSummary.getSize());
                    joSearchSummaryVO.setType(jobSummary.getType());
                    joSearchSummaryVO.setOOG(jobSummary.getOOG());
                    joSearchSummaryVO.setMtOrLaden(jobSummary.getMtOrLaden());
                    //joSearchSummaryVO.setRate(jobSummary.getRate());
                    joSearchSummaryVO.setRate(RutFormatting.getStringToDecimalFormat(jobSummary.getRate(), null));
                    joSearchSummaryVO.setQuantity(jobSummary.getQuantity());
                    
                    //joSearchSummaryVO.setAmount(jobSummary.getAmount());
                    joSearchSummaryVO.setAmount(RutFormatting.getStringToDecimalFormat(jobSummary.getAmount(), null));
                    joSearchSummaryVO.setCurrency(jobSummary.getCurrency());
                   // joSearchSummaryVO.setAmountUsd(jobSummary.getAmountUsd());
                    joSearchSummaryVO.setAmountUsd(RutFormatting.getStringToDecimalFormat(jobSummary.getAmountUsd(), null));
                    joSearchSummaryVO.setPaymentFSC(jobSummary.getPaymentFSC());
                    joSearchSummaryVO.setPriority(jobSummary.getPriority());
                    joSearchSummaryVO.setRateBasis(jobSummary.getRateBasis());
                }
                
                 lobjIjsProcessJOSumDtlVO.setJoSummery(joSearchSummaryVO);
                 lobjIjsProcessJOSumDtlVO.setErrorCode(summaryJO.getErrorCode());
                 lobjIjsProcessJOSumDtlVO.setBokingBL(summaryJO.getBokingBL());
                List<IjsProcessJOSumContDtlDTO> lstContData = summaryJO.getJobOrders(); 

                
                List<IjsProcessJOSumContDtlVO> listVO = new ArrayList<IjsProcessJOSumContDtlVO>();
                IjsProcessJOSumContDtlDTO lobjIjsProcessJOSumContDtlDTO = null;
                
                if(lstContData != null){

                    for(int i=0;i<lstContData.size();i++) {
                    
                        IjsProcessJOSumContDtlVO contDataObj = new IjsProcessJOSumContDtlVO();
                        
                        lobjIjsProcessJOSumContDtlDTO = (IjsProcessJOSumContDtlDTO)lstContData.get(i);
                        contDataObj.setJobOrder(lobjIjsProcessJOSumContDtlDTO.getJobOrder());
                        contDataObj.setVendorCode(lobjIjsProcessJOSumContDtlDTO.getVendorCode());
                        contDataObj.setContractNumber(lobjIjsProcessJOSumContDtlDTO.getContractNumber());
                        contDataObj.setJoDate(lobjIjsProcessJOSumContDtlDTO.getJoDate());
                        contDataObj.setSize(lobjIjsProcessJOSumContDtlDTO.getSize());
                        contDataObj.setType(lobjIjsProcessJOSumContDtlDTO.getType());
                        contDataObj.setOOG(lobjIjsProcessJOSumContDtlDTO.getOOG());
                        contDataObj.setMtOrLaden(lobjIjsProcessJOSumContDtlDTO.getMtOrLaden());
                        contDataObj.setRate(RutFormatting.getStringToDecimalFormat(lobjIjsProcessJOSumContDtlDTO.getRate(), null));
                        contDataObj.setQuantity(lobjIjsProcessJOSumContDtlDTO.getQuantity());
                        contDataObj.setAmount(RutFormatting.getStringToDecimalFormat(lobjIjsProcessJOSumContDtlDTO.getAmount(), null));
                        contDataObj.setCurrency(lobjIjsProcessJOSumContDtlDTO.getCurrency());
                        contDataObj.setAmountUsd(RutFormatting.getStringToDecimalFormat(lobjIjsProcessJOSumContDtlDTO.getAmountUsd(), null));
                        contDataObj.setPaymentFSC(lobjIjsProcessJOSumContDtlDTO.getPaymentFSC());
                        contDataObj.setPriority(lobjIjsProcessJOSumContDtlDTO.getPriority());
                        contDataObj.setRateBasis(lobjIjsProcessJOSumContDtlDTO.getRateBasis());
                        contDataObj.setLumpsumId(lobjIjsProcessJOSumContDtlDTO.getLumpsumId());
                        listVO.add(contDataObj);

                        lobjIjsProcessJOSumDtlVO.setJobOrders(listVO);
                    }
                }
                  
                lstSearchResult.add(lobjIjsProcessJOSumDtlVO);


            }
            searchResult.setResult(lstSearchResult);
            ijsProcessJOBookingBLSearchUIM.setSearchResult(searchResult);
        }
        
        
        
        
       return ijsProcessJOBookingBLSearchUIM;
    }
    


    public IjsProcessJOBkgBLSearchUIM saveOrUpdateContract(IjsProcessJOBkgBLSearchVO ijsProcessJOBkgBLVO, 
                                                     String userInfo, 
                                                     String action) throws IJSException {
        validateRequest(ijsProcessJOBkgBLVO);
        return transform(ijsProcessJOBkgBLDao.saveOrUpdateContract(ijsProcessJOBkgBLVO, 
                                                          userInfo, action), 
                         action);
    }


    private IjsProcessJOBkgBLSearchUIM transform(String result, String action) {
        IjsProcessJOBkgBLSearchUIM ijsProcessJOBookingBLSearchUIM = new IjsProcessJOBkgBLSearchUIM();
        ijsProcessJOBookingBLSearchUIM.setErrorCode(result);
        ijsProcessJOBookingBLSearchUIM.setAction(action);
        return ijsProcessJOBookingBLSearchUIM;

    }

    private IjsProcessJOBkgBLSearchUIM transformResultsForSusOrDel(Map<String, String> results, 
                                                             String action) {
        IjsProcessJOBkgBLSearchUIM ijsProcessJOBookingBLSearchUIM = new IjsProcessJOBkgBLSearchUIM();
        ijsProcessJOBookingBLSearchUIM.setBookingBLResult(results);
        ijsProcessJOBookingBLSearchUIM.setAction(action);
        return ijsProcessJOBookingBLSearchUIM;

    }


    

    
    private String convertDateToString(String date) {
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

    //##05 BEGIN

    

    private IjsProcessJOBkgBLSearchVO populateContractInfo(IjsExcelUploadTemplateVO rateTemplateVO, 
                                               String[] contract) throws IJSException {
        IjsProcessJOBkgBLSearchVO contractVO = new IjsProcessJOBkgBLSearchVO();
        
        return contractVO;
    }

//    private IjsRateVO populateCostRateInfo(IjsExcelUploadTemplateVO excelTemplateVO, 
//                                           String[] costRate) throws IJSException {
//        IjsRateVO rateVO = new IjsRateVO();
//        if (validateDateFormate(costRate[IjsExcelTemplateConstants.COST_RATE_START_DATE.getIndex()]))
//            rateVO.setStartDate(costRate[IjsExcelTemplateConstants.COST_RATE_START_DATE.getIndex()]);
//
//        if (validateDateFormate(costRate[IjsExcelTemplateConstants.COST_RATE_END_DATE.getIndex()]))
//            rateVO.setEndDate(costRate[IjsExcelTemplateConstants.COST_RATE_END_DATE.getIndex()]);
//
//        rateVO.setService(costRate[IjsExcelTemplateConstants.COST_RATE_SERVICE.getIndex()]);
//
//        rateVO.setMtOrLaden(costRate[IjsExcelTemplateConstants.COST_RATE_MT_LADEN.getIndex()]);
//
//        if (validateRateBasis(costRate[IjsExcelTemplateConstants.COST_RATE_BASIS.getIndex()]))
//            rateVO.setRateBasis(costRate[IjsExcelTemplateConstants.COST_RATE_BASIS.getIndex()]);
//
//        if (validateEqCategory(costRate[IjsExcelTemplateConstants.COST_RATE_EQ_CATEGORY.getIndex()]))
//            rateVO.setEqCatq(costRate[IjsExcelTemplateConstants.COST_RATE_EQ_CATEGORY.getIndex()]);
//
//        if (validateRateStatus(costRate[IjsExcelTemplateConstants.COST_RATE_STATUS.getIndex()]))
//            rateVO.setRateStatus(costRate[IjsExcelTemplateConstants.COST_RATE_STATUS.getIndex()]);
//
//        rateVO.setChargeCode(costRate[IjsExcelTemplateConstants.COST_RATE_CHARGE_CODE.getIndex()]);
//
//        rateVO.setTerm(costRate[IjsExcelTemplateConstants.COST_RATE_TERM.getIndex()]);
//
//        rateVO.setCalcMethod(costRate[IjsExcelTemplateConstants.COST_RATE_CALC_METHOD.getIndex()]);
//
//        rateVO.setEqType(costRate[IjsExcelTemplateConstants.COST_RATE_EQ_TYPE.getIndex()]);
//
//        if (costRate[IjsExcelTemplateConstants.COST_RATE_UPTO.getIndex()] != 
//            null && costRate[IjsExcelTemplateConstants.COST_RATE_UPTO.getIndex()].length() > 
//            0) {
//            rateVO.setUpto(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_UPTO.getIndex()]));
//        }
//
//        rateVO.setImpOrExp(costRate[IjsExcelTemplateConstants.COST_RATE_IMP_EXP.getIndex()]);
//
//        rateVO.setSplHandling(costRate[IjsExcelTemplateConstants.COST_RATE_SPL_HANDLING.getIndex()]);
//
//        if (validateCurrency(costRate[IjsExcelTemplateConstants.COST_RATE_CURRENCY.getIndex()])) {
//            rateVO.setCurrency(costRate[IjsExcelTemplateConstants.COST_RATE_CURRENCY.getIndex()]);
//        }
//
//        rateVO.setPortClassCode(costRate[IjsExcelTemplateConstants.COST_RATE_PORT_CLASS.getIndex()]);
//
//        rateVO.setImdgDetails(costRate[IjsExcelTemplateConstants.COST_RATE_IMDG.getIndex()]);
//
//        rateVO.setOogSetup(costRate[IjsExcelTemplateConstants.COST_RATE_OOG.getIndex()]);
//
//        if (costRate[IjsExcelTemplateConstants.COST_RATE_COST20.getIndex()] != 
//            null && costRate[IjsExcelTemplateConstants.COST_RATE_COST20.getIndex()].length() > 
//            0) {
//            rateVO.setRate20(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST20.getIndex()]));
//        }
//        rateVO.setPerTrip(costRate[IjsExcelTemplateConstants.COST_RATE_PER_TRIP.getIndex()]);
//
//        if (costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()] != 
//            null && costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()].length() > 
//            0) {
//            rateVO.setRate40(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST40.getIndex()]));
//        }
//        if (costRate[IjsExcelTemplateConstants.COST_RATE_COST45.getIndex()] != 
//            null && costRate[IjsExcelTemplateConstants.COST_RATE_COST45.getIndex()].length() > 
//            0) {
//            rateVO.setRate45(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_COST45.getIndex()]));
//        }
//        rateVO.setLumpSum(costRate[IjsExcelTemplateConstants.COST_RATE_LUMP_SUM.getIndex()]);
//
//        if (validateRateSequenceNumber(costRate[IjsExcelTemplateConstants.COST_RATE_SEQ_NO.getIndex()]))
//            rateVO.setCostRateSequenceNum(Integer.parseInt(costRate[IjsExcelTemplateConstants.COST_RATE_SEQ_NO.getIndex()]));
//        return rateVO;
//    }
//
//    private IjsRateVO populateBillingRateInfo(IjsExcelUploadTemplateVO rateTemplateVO, 
//                                              String[] billingRate) throws IJSException {
//
//        IjsRateVO rateVO = new IjsRateVO();
//
//        if (validateDateFormate(billingRate[IjsExcelTemplateConstants.BILL_RATE_START_DATE.getIndex()]))
//            rateVO.setStartDate(billingRate[IjsExcelTemplateConstants.BILL_RATE_START_DATE.getIndex()]);
//
//        if (validateDateFormate(billingRate[IjsExcelTemplateConstants.BILL_RATE_END_DATE.getIndex()]))
//            rateVO.setEndDate(billingRate[IjsExcelTemplateConstants.BILL_RATE_END_DATE.getIndex()]);
//
//        rateVO.setService(billingRate[IjsExcelTemplateConstants.BILL_RATE_SERVICE.getIndex()]);
//
//        rateVO.setMtOrLaden(billingRate[IjsExcelTemplateConstants.BILL_RATE_MT_LADEN.getIndex()]);
//
//        if (validateRateBasis(billingRate[IjsExcelTemplateConstants.BILL_RATE_RATE_BASIS.getIndex()]))
//            rateVO.setRateBasis(billingRate[IjsExcelTemplateConstants.BILL_RATE_RATE_BASIS.getIndex()]);
//
//        if (validateEqCategory(billingRate[IjsExcelTemplateConstants.BILL_RATE_EQ_CATEGORY.getIndex()]))
//            rateVO.setEqCatq(billingRate[IjsExcelTemplateConstants.BILL_RATE_EQ_CATEGORY.getIndex()]);
//
//        if (validateRateStatus(billingRate[IjsExcelTemplateConstants.BILL_RATE_RATE_STATUS.getIndex()]))
//            rateVO.setRateStatus(billingRate[IjsExcelTemplateConstants.BILL_RATE_RATE_STATUS.getIndex()]);
//
//        rateVO.setTerm(billingRate[IjsExcelTemplateConstants.BILL_RATE_TERM.getIndex()]);
//
//        rateVO.setCalcMethod(billingRate[IjsExcelTemplateConstants.BILL_RATE_CALC_METHOD.getIndex()]);
//
//        rateVO.setEqType(billingRate[IjsExcelTemplateConstants.BILL_RATE_EQ_TYPE.getIndex()]);
//
//        if (billingRate[IjsExcelTemplateConstants.BILL_RATE_UPTO.getIndex()] != 
//            null && billingRate[IjsExcelTemplateConstants.BILL_RATE_UPTO.getIndex()].length() > 
//            0) {
//            rateVO.setUpto(Integer.parseInt(billingRate[IjsExcelTemplateConstants.BILL_RATE_UPTO.getIndex()]));
//        }
//
//        rateVO.setUom(billingRate[IjsExcelTemplateConstants.BILL_RATE_UOM.getIndex()]);
//
//        rateVO.setCurrency(billingRate[IjsExcelTemplateConstants.BILL_RATE_CURRENCY.getIndex()]);
//
//        if (billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL20.getIndex()] != 
//            null && billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL20.getIndex()].length() > 
//            0) {
//            rateVO.setRate20(Integer.parseInt(billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL20.getIndex()]));
//        }
//
//        if (billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL40.getIndex()] != 
//            null && billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL40.getIndex()].length() > 
//            0) {
//            rateVO.setRate40(Integer.parseInt(billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL40.getIndex()]));
//        }
//
//        if (billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL45.getIndex()] != 
//            null && billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL45.getIndex()].length() > 
//            0) {
//            rateVO.setRate45(Integer.parseInt(billingRate[IjsExcelTemplateConstants.BILL_RATE_BILL45.getIndex()]));
//        }
//        //rateTemplateVO.getIjsBillingRateVOList().add(rateVO);
//
//        if (validateRateSequenceNumber(billingRate[IjsExcelTemplateConstants.BILL_RATE_SEQ_NO.getIndex()]))
//            rateVO.setCostRateSequenceNum(Integer.parseInt(billingRate[IjsExcelTemplateConstants.BILL_RATE_SEQ_NO.getIndex()]));
//
//        return rateVO;
//    }

    private boolean validateDateFormate(String date) throws IJSException {
        try {
            new SimpleDateFormat("dd/mm/yyyy").parse(date);
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

    

    private boolean validateRateSequenceNumber(String seqNo) {
        try {
            if (seqNo != null && seqNo.length() > 0) {
                Integer.parseInt(seqNo);
                return true;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return false;
    }

    

    private void validateRequest(IjsProcessJOBkgBLSearchVO ijsProcessJOBkgBLVO) throws IJSException {
        String startDate = ijsProcessJOBkgBLVO.getStartDate();
        String endDate = ijsProcessJOBkgBLVO.getEndDate();
        Date sDate = null;
        Date eDate = null;

        try {
            sDate = new SimpleDateFormat("dd/mm/yyyy").parse(startDate);
            eDate = new SimpleDateFormat("dd/mm/yyyy").parse(endDate);
            if (sDate.after(eDate)) {
                throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10026.getErrorCode());
            }
        } catch (ParseException e) {
            // TO-DO
            throw new IJSException("Date is not valid");
        }
    }
    
    
    public IjsProcessJOBkgBLSearchUIM uploadContainer(IjsProcessJOBkgBLSearchUIM actionForm, String userInfo
        , String adhocType,String folderPath,String fileName,String contractId,List contractsIds) throws IJSException {
    	//##CR 03 START
        IjsContainerUploadVO ijsContainerUploadVO=ijsUploadSvc.uploadContainer(adhocType, contractId, folderPath, fileName,contractsIds);
        IjsProcessJOBkgBLSearchUIM ijsProcessJOBkgBLSearchUIM = new IjsProcessJOBkgBLSearchUIM();
        if(ijsContainerUploadVO.getSearchResult()!=null){
        	
             ijsProcessJOBkgBLSearchUIM.setSearchResult(ijsContainerUploadVO.getSearchResult());
             ijsProcessJOBkgBLSearchUIM.setErrorCode(IjsErrorCode.DB_IJS_CNTR_EX_10027.getErrorCode());
        }
        ijsProcessJOBkgBLSearchUIM.setMsgVOs(ijsContainerUploadVO.getMsgVOs());
        ijsProcessJOBkgBLSearchUIM.setAction(IjsActionMethod.CONTAINER_UPLOAD.getAction());
        //##CR 03 END
        return ijsProcessJOBkgBLSearchUIM;
    }
    
    public List<String> getContainerUploadRecord(IjsProcessJOBkgBLSearchUIM actionForm) throws IJSException {
        
        GlobalUtil uitl = new GlobalUtil();
        String astrExcelFilePath = actionForm.getUploadVO().getFolderPath() + "/" + actionForm.getUploadVO().getFileName();
        
        
        String sheetName = "First Sheet"; 
        int aintFromRowNo=2;
        int aintFromColNo=1;
        int aintToColNo=1;
        boolean ablnIncludeLineNo = false;
         List<String> excelTemplateList = new ArrayList<String>();
        try {
            String[][] retArray = uitl.readExcel(astrExcelFilePath,sheetName,aintFromRowNo,aintFromColNo,aintToColNo,ablnIncludeLineNo);
            if (retArray.length > 0) {
                int rowT = retArray.length;
                String header[] = retArray[0];
                for(int i=0; i < rowT; i++){
                     String containerCode = retArray[i][0];
                     excelTemplateList.add(containerCode);
                }
            }
        } catch (IOException e) {
          
            e.printStackTrace();
        }
        return excelTemplateList;
    }
    
    /**
     * CR#03
     * @return
     * @throws IJSException
     */
    public int getMaxEquipLimit() throws IJSException{
    		return ijsProcessJOBkgBLDao.getMaxEquipLimit();
    }
    public List<String> getEqTypeList(String userId) throws IJSException{
    	return ijsProcessJOBkgBLDao.getEqTypeList(userId);
    }
    public IjsProcessJOBkgBLSearchUIM deleteLumpsum(List<String> jobOrders,String allJobOrders,String userId,String sessionId) throws IJSException{
    	//ijsProcessJOBkgBLDao.deleteLumpsum(jobOrders, userId,sessionId);
    	return transformJOSummaryDtoToUim(ijsProcessJOBkgBLDao.deleteLumpsum(jobOrders,allJobOrders, userId,sessionId));
    }
    public static void main(String args[]){
    	String s1="abc";
    	int index=s1.indexOf(":");
    	//System.out.println("substring1:"+s1.substring(0,s1.indexOf(":")));
    	//System.out.println("substring2:"+s1.substring(s1.indexOf(":")+1));
    }
}
