package com.rclgroup.dolphin.ijs.web.service;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsExcelTemplateConstants;
import com.rclgroup.dolphin.ijs.web.dao.IjsJoEnquiryDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsJoEnquiryDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsJoEnquiryUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsExcelUploadTemplateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoEnquiryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoEnquiryVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IjsJoEnquirySvc {
    IjsJoEnquiryDao ijsMaintainJOSearchDao = null;

    public IjsJoEnquirySvc(IjsJoEnquiryDao ijsMaintainJOSearchDao) {
        this.ijsMaintainJOSearchDao = ijsMaintainJOSearchDao;
    }

    public IjsJoEnquiryUIM searchBookingBL(IjsJoEnquiryParamVO bkgBLParam, 
                                                  String userInfo) throws IJSException {

        //        IjsProcessJOBkgBLSearchParamVO bkgBLParamVO = 
        //            (IjsProcessJOBkgBLSearchParamVO)bkgBLParam.getProcessJoParam();

        List<IjsJoEnquiryDTO> lstBkgBL = 
            ijsMaintainJOSearchDao.findContracts(userInfo, bkgBLParam);
        return transformDtoToUim(lstBkgBL);
    }

    private IjsJoEnquiryUIM transformDtoToUim(List<IjsJoEnquiryDTO> lstMaintainJo) {
        //TO-DO transform Data Object to VO 
        IjsJoEnquiryUIM ijsJoEnquiryUIM = null;
        IjsSearchResult<IjsJoEnquiryVO> searchResult = null;
        List<IjsJoEnquiryVO> lstSearchResult = null;
        if (lstMaintainJo != null) {
            lstSearchResult = new ArrayList<IjsJoEnquiryVO>();
            ijsJoEnquiryUIM = new IjsJoEnquiryUIM();
            searchResult = new IjsSearchResult<IjsJoEnquiryVO>();
            for (IjsJoEnquiryDTO maintainJo: lstMaintainJo) {
                IjsJoEnquiryVO ijsJoEnquiryVO = 
                    new IjsJoEnquiryVO();
                ijsJoEnquiryVO.setJoNumber(maintainJo.getJoNumber());


                lstSearchResult.add(ijsJoEnquiryVO);
            }
            searchResult.setResult(lstSearchResult);
            ijsJoEnquiryUIM.setSearchResult(searchResult);
        }
        return ijsJoEnquiryUIM;
    }

    public IjsJoEnquiryUIM saveOrUpdateContract(IjsJoEnquiryVO ijsMaintainJOSearchVO, 
                                                       String userInfo, 
                                                       String action) throws IJSException {
        validateRequest(ijsMaintainJOSearchVO);
        return transform(ijsMaintainJOSearchDao.saveOrUpdateContract(ijsMaintainJOSearchVO, 
                                                                     userInfo, 
                                                                     action), 
                         action);
    }


    private IjsJoEnquiryUIM transform(String result, String action) {
        IjsJoEnquiryUIM ijsJoEnquiryUIM = 
            new IjsJoEnquiryUIM();
        ijsJoEnquiryUIM.setErrorCode(result);
        ijsJoEnquiryUIM.setAction(action);
        return ijsJoEnquiryUIM;

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


    private IjsJoEnquiryVO populateContractInfo(IjsExcelUploadTemplateVO rateTemplateVO, 
                                                       String[] contract) throws IJSException {
        IjsJoEnquiryVO maintainJoVO = new IjsJoEnquiryVO();

        return maintainJoVO;
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
//        //rateVO.setTerm(costRate[IjsExcelTemplateConstants.COST_RATE_TERM.getIndex()]);
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


    private void validateRequest(IjsJoEnquiryVO ijsJoEnquiryVO) throws IJSException {
        String startDate = ijsJoEnquiryVO.getStartDate();
        String endDate = ijsJoEnquiryVO.getCompleteDate();
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

}
