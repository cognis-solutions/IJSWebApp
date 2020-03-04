package com.rclgroup.dolphin.ijs.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.rclgroup.dolphin.ijs.web.common.IjsPaginationUtil;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsMaintainJOSearchDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsMaintainJOSearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsMaintainJoDownloadDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsMaintainJOSearchUIM;
import com.rclgroup.dolphin.ijs.web.util.RutDate;
import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchContDetailVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainSaveVO;


public class IjsMaintainJOSearchSvc {
    IjsMaintainJOSearchDao ijsMaintainJOSearchDao = null;
    private static final String ZERO="0";

    public IjsMaintainJOSearchSvc(IjsMaintainJOSearchDao ijsMaintainJOSearchDao) {
        this.ijsMaintainJOSearchDao = ijsMaintainJOSearchDao;
    }

    public IjsMaintainJOSearchUIM searchMaintainJO(IjsMaintainJOSearchParamVO mainatainJoParam, 
                                               String userInfo,HttpSession session) throws IJSException {
        
         Integer resultCountForJobOrders = (Integer)session.getAttribute("resultCountForJobOrders");
         if (resultCountForJobOrders == null || mainatainJoParam.isRequestChanged()) {
             resultCountForJobOrders = ijsMaintainJOSearchDao.getTotalResultCountForJO(userInfo, mainatainJoParam);
             System.out.println("Total records found for the given search criteria :-  " + resultCountForJobOrders);
             session.setAttribute("resultCountForJobOrders", resultCountForJobOrders);
         }
         if(resultCountForJobOrders==0){
        	 throw new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
         }
         
       // IjsPaginationInfo sessionInfo = (IjsPaginationInfo)session.getAttribute("paginatoinInfo");
        if (mainatainJoParam.getPageNo() == 0 ) {
            throw new IJSException("Invalid Page Number");
        }
        
         return getSearchResults(mainatainJoParam, session, resultCountForJobOrders.intValue(), userInfo);
        
    }
    
 private IjsMaintainJOSearchUIM getSearchResults(IjsMaintainJOSearchParamVO mainatainJoParam, 
                                               HttpSession session,
                                               int totalRecords,String userInfo) throws IJSException {
     int recordStart=IjsPaginationUtil.getRecordStart(mainatainJoParam.getPageNo(),mainatainJoParam.getNoOfRecPerPage());
     int recordEnd=IjsPaginationUtil.getRecordEnd(mainatainJoParam.getPageNo(),mainatainJoParam.getNoOfRecPerPage(),totalRecords);    
     if(recordEnd<recordStart){
    	 throw new IJSException("IJS_MAINTAIN_JO_PAGINATION_ERROR");
     }
     mainatainJoParam.setRowStart(recordStart);
     
     mainatainJoParam.setRowEnd(recordEnd);
     
     System.out.println("********   Inside getLookUpResults() :-- Method for getting records  *******");    
     List<IjsMaintainJOSearchDTO> lstBkgBL = ijsMaintainJOSearchDao.findJobOrder( userInfo, mainatainJoParam);
     
     System.out.println("List size return from DB :-   " + lstBkgBL.size() + " In between of " 
         + mainatainJoParam.getRowStart()
         + " - " + mainatainJoParam.getRowEnd());
       
 
     
     return transformDtoToUim(lstBkgBL, totalRecords);
     
 }
    
    private IjsMaintainJOSearchUIM transformDtoToUim(List<IjsMaintainJOSearchDTO> lstMaintainJo, Integer totalCount){
        IjsMaintainJOSearchUIM ijsMaintainJOSearchUIM= null;
        IjsSearchResult<IjsMaintainJOSearchVO> searchResult= null;
        List<IjsMaintainJOSearchVO> lstSearchResult=null;
        
        if(lstMaintainJo!=null){
            lstSearchResult=new ArrayList<IjsMaintainJOSearchVO>();
            ijsMaintainJOSearchUIM= new IjsMaintainJOSearchUIM();
            searchResult=new IjsSearchResult<IjsMaintainJOSearchVO>();
            for(IjsMaintainJOSearchDTO maintainJo:lstMaintainJo){
            	String amount=RutFormatting.getDoubleToDecimalFormat(maintainJo.getAmount(),null);
            	String amountUSD=RutFormatting.getDoubleToDecimalFormat(maintainJo.getAmountUSD(),null);
                IjsMaintainJOSearchVO maintainJoSearchVO=new IjsMaintainJOSearchVO();
                maintainJoSearchVO.setJoNumber(maintainJo.getJoNumber());
                maintainJoSearchVO.setOrderDate(maintainJo.getOrderDate());
                maintainJoSearchVO.setApproveDate(maintainJo.getApproveDate());
                maintainJoSearchVO.setCreatedBy(maintainJo.getCreatedBy());
                maintainJoSearchVO.setVendorID(maintainJo.getVendorID());
                maintainJoSearchVO.setJobOrdType(maintainJo.getJobOrdType());
                maintainJoSearchVO.setVendorName(maintainJo.getVendorName());
                maintainJoSearchVO.setDetailType(maintainJo.getDetailType());
                maintainJoSearchVO.setDetailVersion(maintainJo.getDetailVersion());
                maintainJoSearchVO.setFromLoaction(maintainJo.getFromLoaction());
                maintainJoSearchVO.setToLocation(maintainJo.getToLocation());
                maintainJoSearchVO.setFromTerminal(maintainJo.getFromTerminal());
                maintainJoSearchVO.setToTerminal(maintainJo.getToTerminal());
                maintainJoSearchVO.setFromLocType(maintainJo.getFromLocType());
                maintainJoSearchVO.setToLocType(maintainJo.getToLocType());
                maintainJoSearchVO.setTransMode(maintainJo.getTransMode());
                maintainJoSearchVO.setStartDate(maintainJo.getStartDate());
                maintainJoSearchVO.setCompleteDate(maintainJo.getCompleteDate());
                maintainJoSearchVO.setAmount(amount);
                maintainJoSearchVO.setAmountNum(maintainJo.getAmount());
                maintainJoSearchVO.setAmountUSD(amountUSD);
                maintainJoSearchVO.setCurrency(maintainJo.getCurrency());
                maintainJoSearchVO.setRoutingId(maintainJo.getRoutingId());
                maintainJoSearchVO.setContractId(maintainJo.getContractId());
                maintainJoSearchVO.setStatus(maintainJo.getStatus());
                maintainJoSearchVO.setFSC(maintainJo.getFSC());
                maintainJoSearchVO.setAdhoc_yn(maintainJo.getAdhoc_yn());
                maintainJoSearchVO.setReasonCode(maintainJo.getReasonCode());
                maintainJoSearchVO.setBk_bl_ad(maintainJo.getBk_bl_ad());
                maintainJoSearchVO.setUser_type(maintainJo.getUser_type());
                maintainJoSearchVO.setSOCorCOC(maintainJo.getSOCorCOC());
                maintainJoSearchVO.setPriority(maintainJo.getPriority());
               //nikash
                maintainJoSearchVO.setBarge(maintainJo.getBarge());
                IjsMaintainJOSearchContDetailVO contDataObj;
                List<IjsMaintainJOSearchContDetailVO> lstContData = new ArrayList<IjsMaintainJOSearchContDetailVO>();
                for(int i=0;i<maintainJo.getContDetailJO().size();i++) {
                    String startDate=maintainJo.getContDetailJO().get(i).getStartDate();
                    String endDate=maintainJo.getContDetailJO().get(i).getEndDate();
                    if(startDate!=null){
                    	startDate=RutDate.toDateFormatFromYYYYMMDD(startDate);
                    }else{
                    	startDate="";
                    }
                    if(endDate!=null){
                    	endDate=RutDate.toDateFormatFromYYYYMMDD(endDate);
                    }else{
                    	endDate="";
                    }
                    String cntSizeAmount=maintainJo.getContDetailJO().get(i).getAmount();
                    cntSizeAmount=ZERO.equals(cntSizeAmount)?null:cntSizeAmount;
                	String cntSizeAmountUSD=maintainJo.getContDetailJO().get(i).getAmountUSD();
                	cntSizeAmountUSD=ZERO.equals(cntSizeAmountUSD)?null:cntSizeAmountUSD;
                	String cntBkgBlAmount=maintainJo.getContDetailJO().get(i).getAmountBkgBl();
                	cntBkgBlAmount=ZERO.equals(cntBkgBlAmount)?null:cntBkgBlAmount;
                	String cntBkgBlAmountUSD=maintainJo.getContDetailJO().get(i).getAmountBkgBlUSD();
                	cntBkgBlAmountUSD=ZERO.equals(cntBkgBlAmountUSD)?null:cntBkgBlAmountUSD;
                	String cntLumpsumAmount=maintainJo.getContDetailJO().get(i).getAmountLumpsum();
                	cntLumpsumAmount=ZERO.equals(cntLumpsumAmount)?null:cntLumpsumAmount;
                	String cntLumpsumAmountUSD=maintainJo.getContDetailJO().get(i).getAmountLumpsumUSD();
                	cntLumpsumAmountUSD=ZERO.equals(cntLumpsumAmountUSD)?null:cntLumpsumAmountUSD;
                	if(cntSizeAmount!=null && !cntSizeAmount.isEmpty()){
                		cntSizeAmount=RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(cntSizeAmount),null);
                	}
                	if(cntSizeAmountUSD!=null && !cntSizeAmountUSD.isEmpty()){
                		cntSizeAmountUSD=RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(cntSizeAmountUSD),null);
                	}
                	if(cntBkgBlAmount!=null && !cntBkgBlAmount.isEmpty()){
                		cntBkgBlAmount=RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(cntBkgBlAmount),null);
                	}
                	if(cntBkgBlAmountUSD!=null && !cntBkgBlAmountUSD.isEmpty()){
                		cntBkgBlAmountUSD=RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(cntBkgBlAmountUSD),null);
                	}
                	if(cntLumpsumAmount!=null && !cntLumpsumAmount.isEmpty()){
                		cntLumpsumAmount=RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(cntLumpsumAmount),null);
                	}
                	if(cntLumpsumAmountUSD!=null && !cntLumpsumAmountUSD.isEmpty()){
                		cntLumpsumAmountUSD=RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(cntLumpsumAmountUSD),null);
                	}
                    contDataObj =new IjsMaintainJOSearchContDetailVO();
                    contDataObj.setEqNumber(maintainJo.getContDetailJO().get(i).getEqNumber());
                    contDataObj.setOldContainerNo(maintainJo.getContDetailJO().get(i).getEqNumber());
                    contDataObj.setEquiptState(maintainJo.getContDetailJO().get(i).getEquiptState());
                    contDataObj.setBkgOrBLNo(maintainJo.getContDetailJO().get(i).getBkgOrBLNo());
                    contDataObj.setContSize(maintainJo.getContDetailJO().get(i).getContSize());
                    contDataObj.setContPercent(maintainJo.getContDetailJO().get(i).getContPercent());
                    contDataObj.setContWeight(maintainJo.getContDetailJO().get(i).getContWeight());
                    contDataObj.setContType(maintainJo.getContDetailJO().get(i).getContType());
                    contDataObj.setContEmptyOrLaden(maintainJo.getContDetailJO().get(i).getContEmptyOrLaden());
                    contDataObj.setSOCorCOC(maintainJo.getContDetailJO().get(i).getSOCorCOC());
                    contDataObj.setStartDate(startDate);
                    contDataObj.setEndDate(endDate);
                    contDataObj.setCurrency(maintainJo.getContDetailJO().get(i).getCurrency());
                    contDataObj.setAmount(cntSizeAmount);
                    contDataObj.setAmountUSD(cntSizeAmountUSD);
                    contDataObj.setDGorRForOG(maintainJo.getContDetailJO().get(i).getDGorRForOG());
                    contDataObj.setPortClass(maintainJo.getContDetailJO().get(i).getPortClass());
                    contDataObj.setImdgClass(maintainJo.getContDetailJO().get(i).getImdgClass());
                    contDataObj.setUnno(maintainJo.getContDetailJO().get(i).getUnno());
                    contDataObj.setOlf(maintainJo.getContDetailJO().get(i).getOlf());
                    contDataObj.setOh(maintainJo.getContDetailJO().get(i).getOh());
                    contDataObj.setOwr(maintainJo.getContDetailJO().get(i).getOwr());
                    contDataObj.setOwl(maintainJo.getContDetailJO().get(i).getOwl());
                    contDataObj.setFlashPoint(maintainJo.getContDetailJO().get(i).getFlashPoint());
                    contDataObj.setTempFrom(maintainJo.getContDetailJO().get(i).getTempFrom());
                    contDataObj.setTempTo(maintainJo.getContDetailJO().get(i).getTempTo());
                    contDataObj.setVariant(maintainJo.getContDetailJO().get(i).getVariant());
                    contDataObj.setOla(maintainJo.getContDetailJO().get(i).getOla());
                    contDataObj.setSpHandling(maintainJo.getContDetailJO().get(i).getSpecial_handling());
                    contDataObj.setAmountLumpsum(cntLumpsumAmount);
                    contDataObj.setAmountLumpsumUSD(cntLumpsumAmountUSD);
                    contDataObj.setAmountBkgBl(cntBkgBlAmount);
                    contDataObj.setAmountBkgBlUSD(cntBkgBlAmountUSD);
                    lstContData.add(contDataObj);
                }
                maintainJoSearchVO.setContDetailJO(lstContData);
                lstSearchResult.add(maintainJoSearchVO);
            }
            searchResult.setResult(lstSearchResult);
            ijsMaintainJOSearchUIM.setSearchResult(searchResult);
            ijsMaintainJOSearchUIM.setTotalRecords(totalCount);
        }
       return ijsMaintainJOSearchUIM;
    }

    public IjsMaintainJOSearchUIM completeJO(List<IjsMaintainJOSearchVO> ijsMaintainJOSearchVO, 
                                                     String userInfo, 
                                                     String action) throws IJSException {
        String lsterrorCd = 
            ijsMaintainJOSearchDao.completeJO(ijsMaintainJOSearchVO,userInfo, action);
        return transform(lsterrorCd,action);
    }
    
    public IjsMaintainJOSearchUIM saveFSC(List<IjsMaintainJOSearchVO> ijsMaintainJOSearchVO, 
                                                     String userInfo, 
                                                     String action) throws IJSException {
        String lsterrorCd = 
            ijsMaintainJOSearchDao.saveFSC(ijsMaintainJOSearchVO,userInfo, action);
        return transform(lsterrorCd,action);
    }
    
    public IjsMaintainJOSearchUIM removeEquipmentJO(List<IjsMaintainJOSearchVO> ijsMaintainJOSearchVO, 
                                                     String userInfo, 
                                                     String action) throws IJSException {
        String lsterrorCd = 
            ijsMaintainJOSearchDao.removeEquipmentJO(ijsMaintainJOSearchVO,userInfo, action);
        return transform(lsterrorCd,action);
    }
    
    public IjsMaintainJOSearchUIM replaceEquipmentJO(List<IjsMaintainJOSearchVO> ijsMaintainJOSearchVO, 
                                                     String userInfo, 
                                                     String action) throws IJSException {
        String lsterrorCd = 
            ijsMaintainJOSearchDao.replaceEquipmentJO(ijsMaintainJOSearchVO,userInfo, action);
        return transform(lsterrorCd,action);
    }
    public IjsMaintainJOSearchUIM saveNewAddedROw(List<IjsMaintainJOSearchVO> ijsMaintainJOSearchVO, 
    		String userInfo, 
    		String action,String session,IjsMaintainSaveVO ijsMaintainSaveVO) throws IJSException {
    	String lsterrorCd = 
    			ijsMaintainJOSearchDao.saveNewAddedRow(ijsMaintainJOSearchVO,userInfo, action,session,ijsMaintainSaveVO);
    	return transform(lsterrorCd,action);
    }
    
    public IjsMaintainJOSearchUIM changeVendor(List<IjsMaintainJOSearchVO> ijsMaintainJOSearchVO, 
                                                     String userInfo, 
                                                     String action) throws IJSException {
        String lsterrorCd = 
            ijsMaintainJOSearchDao.changeVendor(ijsMaintainJOSearchVO,userInfo, action);
        return transform(lsterrorCd,action);
    }
    public IjsMaintainJOSearchUIM deleteLumpsum(List<String> ijsLumpsumCostIdLst, 
            String userInfo, 
            String componentType) throws IJSException {
    	String lsterrorCd = 
    			ijsMaintainJOSearchDao.deleteLumpsum(ijsLumpsumCostIdLst,userInfo, componentType);
    	return transform(lsterrorCd,IjsActionMethod.MAINTAIN_JO_DELETE_LUMPSUM.getAction());
	}
    public String[] downloadContracts(IjsMaintainJOSearchParamVO contractParam,String userInfo,String path,String sessionId) throws IJSException{
        IjsMaintainJoDownloadSvc ijsMaintainJoDownloadSvc=new IjsMaintainJoDownloadSvc();
        
        try {
        	List<IjsMaintainJoDownloadDTO> joForDownload = ijsMaintainJOSearchDao.findJobOrderToDownload(userInfo,contractParam);
           return  ijsMaintainJoDownloadSvc.prepareDataForDownload(joForDownload,path,contractParam.getComponentType());
        } catch(IJSException e){
        	throw e;
        }
        catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }


    private IjsMaintainJOSearchUIM transform(String result, String action) {
        IjsMaintainJOSearchUIM ijsMaintainJOSearchUIM = new IjsMaintainJOSearchUIM();
        ijsMaintainJOSearchUIM.setErrorCode(result);
        ijsMaintainJOSearchUIM.setAction(action);
        return ijsMaintainJOSearchUIM;

    }
   
    private void validateRequest(IjsMaintainJOSearchVO ijsMaintainJOSearchVO) throws IJSException {
        String startDate = ijsMaintainJOSearchVO.getStartDate();
        String endDate = ijsMaintainJOSearchVO.getCompleteDate();
        Date sDate = null;
        Date eDate = null;

        try {
            sDate = new SimpleDateFormat("dd/mm/yyyy").parse(startDate);
            eDate = new SimpleDateFormat("dd/mm/yyyy").parse(endDate);
            if (sDate.after(eDate)) {
                throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10026.getErrorCode());
            }
        } catch (ParseException e) {
            throw new IJSException("Date is not valid");
        }
    }
    
    public Map<String,String> findJoDownloadLimit(String userId,IjsMaintainJOSearchParamVO mainatainJoParam) throws IJSException{
    	return ijsMaintainJOSearchDao.findJODownloadLimit(userId,mainatainJoParam);
    }

}
