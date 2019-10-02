 /*-----------------------------------------------------------------------------------------------------------
 IjsContractRateJdbcDao.java
 -------------------------------------------------------------------------------------------------------------
 Copyright RCL Public Co., Ltd. 2007
 -------------------------------------------------------------------------------------------------------------
 Author NIIT 07/09/17
 - Change Log ------------------------------------------------------------------------------------------------
 ## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            Contract Rate dao  functionality for contract
02 05/10/17  NIIT       IJS            addcostrate   functionality for contract
 -----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.rclgroup.dolphin.ijs.web.common.IjsContractRateResult;
import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsMessageCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractRateDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractRateUIM;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;
import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractEqTypeVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractOogSetupVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractShipmentTermVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsCostImdgPortClassVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsCostRateSetupVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;

public class IjsContractRateJdbcDao extends IjsBaseDao  implements IjsContractRateDao{
    private IjsContractRateStoredProcedure ijsContractRateStoredProcedure;//##01
    private IjsCntrAddCostRateStrdProce ijsCntrAddCostRateStrdProce;//##02
    private IjsCntrMaintainanceStrdProce ijsCntrMaintainanceStrdProce;//##02
   // private IjsPortImdgStrdProce ijsPortImdgStrdProce;//##02
     //##03 BEGIN
    private IjsContractBillingRateStrdPrce ijsContractBillingRateStrdPrce;
    private IjsCntrBillingMaintainanceStrdProce ijsCntrBillingMaintainanceStrdProce;
    private IjsCntrAddBillingRateStrdProce ijsCntrAddBillingRateStrdProce;
    private IjsCntrBillingSplCodesStrdProce ijsCntrBillingSplCodesStrdProce;
    private IjsCntrEquipmentTypeProcedure ijsCntrEquipmentTypeProcedure;
    private IjsCntrShipmentTermProcedure ijsCntrShipmentTermProcedure ;
   // private IjsGetVesselCodesStrdProc ijsGetVesselCodesStrdProc;
    private IjsCntrExchangeRateProcedure ijsCntrExchangeRateProcedure;
    //CR#04 START
    private IjsOOGSetupProcedure ijsOOGSetupSave;
    private IjsOOGSetupProcedure ijsOOGSetupSearch;
    private IjsPortImdgSetupProcedure ijsPortImdgSetupSave;
    private IjsPortImdgSetupProcedure ijsPortImdgSetupSearch;
    private IjsOOGSetupCodeProcedure ijsOOGSetupCodeProcedure; 
    private IjsPortImdgSetupCodeProcedure ijsPortImdgSetupCodeProcedure; 
    
    //CR#04 END
    private static final String PORT_CODE="PORT_CODE";
    private static final String PORT="PORT";
    private static final String IMDG="IMDG";
    private static final String BKG_BL="B";
    private static final String LUMPSUM="L";
    private static final String NEW="N";
    private static final String UPDATE="U";
    private static final String DELETE="D";
    private static final String ASTRIC="*";
    private static final String MINUS="-1";
    

    public void initDao() throws Exception {
        super.initDao();
        ijsContractRateStoredProcedure = new IjsContractRateStoredProcedure(getJdbcTemplate()
            , new IjsContractRateRowMapper());//##01
        ijsCntrAddCostRateStrdProce = new IjsCntrAddCostRateStrdProce(getJdbcTemplate());//##02
        ijsCntrMaintainanceStrdProce = new IjsCntrMaintainanceStrdProce(getJdbcTemplate());//##02
        //ijsPortImdgStrdProce = new IjsPortImdgStrdProce(getJdbcTemplate());//##02
        //##03 BEGIN
        ijsCntrBillingMaintainanceStrdProce = new IjsCntrBillingMaintainanceStrdProce(getJdbcTemplate());
        ijsContractBillingRateStrdPrce = new IjsContractBillingRateStrdPrce(getJdbcTemplate(), new IjsCntrBillingRateRowMapper());
        ijsCntrAddBillingRateStrdProce = new IjsCntrAddBillingRateStrdProce(getJdbcTemplate());
        ijsCntrBillingSplCodesStrdProce = new IjsCntrBillingSplCodesStrdProce(getJdbcTemplate());
        ijsCntrEquipmentTypeProcedure = new IjsCntrEquipmentTypeProcedure(getJdbcTemplate()
            , new IjsCntrEquipmentRowMapper());
        ijsCntrShipmentTermProcedure = new IjsCntrShipmentTermProcedure(getJdbcTemplate()
            , new IjsCntrShipmentTermRowMapper());
        //##03 END
       //  ijsGetVesselCodesStrdProc = new IjsGetVesselCodesStrdProc(getJdbcTemplate());
         ijsCntrExchangeRateProcedure=new IjsCntrExchangeRateProcedure(getJdbcTemplate());
       //CR#04 START
         ijsOOGSetupSave=new IjsOOGSetupProcedure(getJdbcTemplate());
         ijsOOGSetupSearch=new IjsOOGSetupProcedure(getJdbcTemplate(),new IjsOOGSetupRowMapper());
         ijsPortImdgSetupSave=new IjsPortImdgSetupProcedure(getJdbcTemplate());
         ijsPortImdgSetupSearch=new IjsPortImdgSetupProcedure(getJdbcTemplate(),new IjsPortImdgSetupRowMapper());
         ijsOOGSetupCodeProcedure=new IjsOOGSetupCodeProcedure(getJdbcTemplate(),new IjsOOGSetupCodeRowMapper());
         ijsPortImdgSetupCodeProcedure=new IjsPortImdgSetupCodeProcedure(getJdbcTemplate(),new IjsPortImdgSetupCodeRowMapper());
       //CR#04 END
         
    }
    public IjsContractRateJdbcDao() {
    }
    //##01 BEGIN
    /**
     * getContractRateList method for getting contract rate list 
     * @param routngNumber
     * @param userInfo
     * @param actionType
     * @return
     * @throws IJSException
     */
    public IjsContractRateUIM getContractRateList(String terminalDepotCode,int routngNumber, 
                                    String userInfo, String actionType, boolean getEqList) throws IJSException {
        List<IjsRateVO> costRateList = null;
        List<IjsRateVO> billingRateList = null;
        List<IjsContractEqTypeVO> eqTypeList = null;
        List<IjsContractShipmentTermVO> termList = null;
        IjsContractRateUIM rateUim = new IjsContractRateUIM();
        IjsContractRateResult<IjsRateVO> rateResults = new IjsContractRateResult<IjsRateVO>();
        IjsContractRateResult<IjsRateVO> billingRateResults = new IjsContractRateResult<IjsRateVO>();
        IjsCostRateSetupVO costRateSetupVO=null;
        
        if (IjsActionMethod.SEARCH_RATES.getAction().equals(actionType)) {
            costRateList = ijsContractRateStoredProcedure.getContractRateList(routngNumber, userInfo);
            billingRateList = ijsContractBillingRateStrdPrce.getContractBillingRateList(routngNumber, userInfo);
            
            if(getEqList){
               eqTypeList = ijsCntrEquipmentTypeProcedure.getEqTypeList(userInfo);
            }
            
            termList = ijsCntrShipmentTermProcedure.getTermList(userInfo);
            costRateSetupVO=ijsPortImdgSetupCodeProcedure.getPortImdgSetupCode(terminalDepotCode);
        	costRateSetupVO.setOogCodeList(ijsOOGSetupCodeProcedure.getOOGSetupCode(terminalDepotCode));
            //##02 BEGIN
//            if (costRateList.size() > 0) {
//            	
//                for (IjsRateVO ijsRateVO : costRateList) {
//
//                    int vesselSeq = ijsRateVO.getVesselSeq();
//                    
//                    String vesselCode=ijsGetVesselCodesStrdProc.getVesselCodes(vesselSeq,routngNumber,ijsRateVO.getCostRateSequenceNum());
//                    if(vesselCode!=null){
//                        ijsRateVO.setVesselCodes(vesselCode.substring(1)); 
//                    }
//                }
//            }
//            //##02 END
            rateResults.setResults(costRateList);
            billingRateResults.setResults(billingRateList);
            rateUim.setBillingRateResults(billingRateResults);
            rateUim.setRateResults(rateResults);
            rateUim.setEqTypeList(eqTypeList);
            rateUim.setCostRateSetup(costRateSetupVO);
        }
        return rateUim;
    }


    private class IjsContractRateRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int row) {
            IjsRateVO ijsRateVO = new IjsRateVO();
            String rate20;
            String rate40;
            String rate45;
            String lumpsum;
            try {
                ijsRateVO.setCalcMethod(IjsHelper.getClacMethodType(resultSet.getString("CALC_METHOD")));
                ijsRateVO.setChargeCode(resultSet.getString("CHARGE_CODE"));
                ijsRateVO.setCurrency(resultSet.getString("CURRENCY"));
                ijsRateVO.setEndDate(resultSet.getString("END_DATE"));
                ijsRateVO.setEqCatq(IjsHelper.getEqCatelog(resultSet.getString("EQUIPMENT_CATAG")));
                ijsRateVO.setEqType(resultSet.getString("EQ_TYPE"));
                lumpsum=resultSet.getString("LUMP_SUM");
                if(lumpsum!=null){
                	ijsRateVO.setLumpSum(RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(lumpsum), null));
                	ijsRateVO.setDetailSeqNumbers(resultSet.getString("RATE_DETAIL_SEQ_NUMBER"));
                }else{
                	ijsRateVO.setDetailSeqNumbers(resultSet.getString("DETAIL_SEQ"));
                }
                ijsRateVO.setMtOrLaden(resultSet.getString("MT_LADEN"));
                ijsRateVO.setPortAndImdgSeqNum(resultSet.getInt("PORT_CLAS_CODE"));//##02
                ijsRateVO.setOogSetupSeqNum(resultSet.getInt("OOG_SETUP"));//##02
                rate20=resultSet.getString("RATE20");
                rate40=resultSet.getString("RATE40");
                rate45=resultSet.getString("RATE45");
                if(rate20!=null){
                	ijsRateVO.setRate20(RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(rate20), null));
                }
                if(rate40!=null){
                	ijsRateVO.setRate40(RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(rate40), null));
                }
                if(rate45!=null){
                	ijsRateVO.setRate45(RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(rate45), null));
                }
                
                ijsRateVO.setRateBasis(IjsHelper.getRateBaseValue(resultSet.getString("RATE_BASICS")));
                ijsRateVO.setRateStatus(IjsHelper.getRateStatus(resultSet.getString("RATE_STATUS")));
                ijsRateVO.setService(resultSet.getString("SERVICE"));
                ijsRateVO.setVesselCodes(resultSet.getString("VESSEL_CODE"));
                String spHandlingCd=resultSet.getString("SPL_HANDLING");
                ijsRateVO.setSplHandling(spHandlingCd==null?"Normal":IjsHelper.getSpHandlingValue(spHandlingCd));
                ijsRateVO.setStartDate(resultSet.getString("START_DATE"));
                ijsRateVO.setUom(resultSet.getString("UOM"));
                ijsRateVO.setUpto(resultSet.getInt("UPTO"));
                ijsRateVO.setCostRateSequenceNum(resultSet.getInt("RT_HDR_SEQ_NUMBER"));
                ijsRateVO.setCostRateDetailSeqNum(resultSet.getInt("RATE_DETAIL_SEQ_NUMBER"));
                ijsRateVO.setSplCostByBlOrBooking(resultSet.getString("SPECIAL_CUSTOMERS"));
                ijsRateVO.setOogCode(resultSet.getString("OOG_CODE"));
                ijsRateVO.setPortCode(resultSet.getString("PORT_CODE"));
                ijsRateVO.setImdgCode(resultSet.getString("IMDG_CODE"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return ijsRateVO;
        }
    }
    
    protected class IjsContractRateStoredProcedure extends StoredProcedure{
        private static final String SQL_RLTD_IJS_RATE =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_COST_RATE_SEARCH";
        IjsContractRateStoredProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_RATE);
            
            declareParameter(new SqlInOutParameter("p_i_v_routing_number", Types.NUMERIC, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_rate_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }
        
        private List<IjsRateVO> getContractRateList(int routngNumber, String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_routing_number",routngNumber);
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            return (List<IjsRateVO>)outMap.get("p_o_v_ijs_rate_list");
        }
    }
    //##01 END
    //##02 BEGIN
    /**
     * saveOrUpdateCostRate method for save or update cost rate
     * @param actionForm
     * @param userInfo
     */
    public String saveOrUpdateCostRate(IjsContractRateUIM actionForm, String userInfo) {
        String errorCode = ijsCntrAddCostRateStrdProce.addContractRate(actionForm, userInfo);
        System.out.println("Error code ---->" + errorCode);
        if (actionForm.getAction().equals(IjsActionMethod.ADD_COST_RATE.getAction())) {
                if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10014")) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10014.getErrorCode();
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10022")) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10022.getErrorCode();
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10024")) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10024.getErrorCode();
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10026")) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10026.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10016")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10016.getErrorCode();
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10018")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10018.getErrorCode();
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10019")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10019.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10020")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10020.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10021")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10021.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10022")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10022.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10023")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10023.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10024")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10024.getErrorCode();
                }
                else if (errorCode == null) {
                    return IjsMessageCode.IJS_CNTR_COST_RATE_ADDED.getMsgCode();
                }
                
            }
            
        else if (actionForm.getAction().equals(IjsActionMethod.EDIT_COST_RATE.getAction())) {
                if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10015")) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10015.getErrorCode();
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10022")) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10022.getErrorCode();
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10024")) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10024.getErrorCode();
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10026")) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10026.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10016")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10016.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10017")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10017.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10018")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10018.getErrorCode();
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10019")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10019.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10020")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10020.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10021")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10021.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10022")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10022.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10023")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10023.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10024")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10024.getErrorCode();
                }
                else if (errorCode == null) {
                    return IjsMessageCode.IJS_CNTR_COST_RATE_UPDATED.getMsgCode();
                }
            }
        return null;
    }
    /**
     * costRateMaintainance method for maintaining cost rate i.e approve,reject,delete..
     * @param costRateSeqNumList
     * @param userInfo
     * @param action
     * @return
     */
    public IjsContractRateUIM costRateMaintainance(List<Integer> costRateSeqNumList, String userInfo, 
                                      String action, IjsContractRateUIM actionForm) {
        List<IjsRateVO> ijsRateList =   actionForm.getIjsRateList();
        String errorCode = null;
        List<String> failedList = new ArrayList<String>();
        List<String> successList = new ArrayList<String>();
    
        for (IjsRateVO ijsRateVO : ijsRateList) {
        int seqNumber=ijsRateVO.getCostRateSequenceNum();
            errorCode = ijsCntrMaintainanceStrdProce.costMaintainance(seqNumber ,actionForm.getRoutingNumber(), ijsRateVO.getDetailSeqNumbers(), userInfo, action);
            if (errorCode != null && errorCode.contains("DB_IJS_COMM_EX_10001")) {
                failedList.add(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
            }
            if (action.equals(IjsActionMethod.APPROVE_COST_RATE.getAction())) {
                if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10011")) {
                    failedList.add(seqNumber + "-" + IjsErrorCode.DB_IJS_CNTR_EX_10011.getErrorCode());
                } 
                else {
                    successList.add(seqNumber + "-" + IjsMessageCode.IJS_CNTR_COST_RATE_APPROVE.getMsgCode());
                }
            }
            else if (action.equals(IjsActionMethod.DELETE_COST_RATE.getAction())) {
                if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10012")) {
                    failedList.add(seqNumber + "-" + IjsErrorCode.DB_IJS_CNTR_EX_10012.getErrorCode());
                } 
                else {
                    successList.add(seqNumber + "-" + IjsMessageCode.IJS_CNTR_COST_RATE_DELETE.getMsgCode());
                }
            }
            else if (action.equals(IjsActionMethod.REJECT_COST_RATE.getAction())) {
                if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10013")) {
                    failedList.add(seqNumber + "-" + IjsErrorCode.DB_IJS_CNTR_EX_10013.getErrorCode());
                } 
                else {
                    successList.add(seqNumber + "-" + IjsMessageCode.IJS_CNTR_COST_RATE_REJECTED.getMsgCode());
                }
            }
        }
        IjsContractRateUIM costRateUIM = new IjsContractRateUIM();
        costRateUIM.setErrorMsgList(failedList);
        costRateUIM.setSuccessMgsList(successList);
        costRateUIM.setAction(action);
        return costRateUIM;
    }
    
    
    /**
     * saveOrUpdateCostRate method for save or update cost rate
     * @param actionForm
     * @param userInfo
     */
    public IjsContractRateUIM saveOrUpdateCostRateList(IjsContractRateUIM actionForm, String userInfo) 
    throws IJSException {
    
    List<IjsRateVO> ijsRateVOEditErrLst = new ArrayList<IjsRateVO>();
    List<IjsRateVO> ijsRateVOAddErrLst = new ArrayList<IjsRateVO>();
    IjsContractRateUIM rateUim = new IjsContractRateUIM();
    boolean errorFound=false;
    String terminalDepotCode=actionForm.getCostRateSetup()!=null?actionForm.getCostRateSetup().getTerminalDepotCode():null;
    for(IjsRateVO ijsRateVO : actionForm.getIjsRateList()){ 
    	if(ijsRateVO.getLumpSum()==null && !"S".equals(ijsRateVO.getRateBasis())){
    		ijsRateVO.setLumpSum("0");
    	}
    	String errorCode =null;
        actionForm.setIjsRateVO(ijsRateVO);
        actionForm.setAction(ijsRateVO.getOperation());
        ijsRateVO.setErrorAllreadySet(false);
        ijsRateVO.setPortClassList(null);
        ijsRateVO.setImDgList(null);
        try{
        	IjsHelper.validateRequest(ijsRateVO);
        }catch(IJSException ie){
      	  ijsRateVO.setErrorMsg(ie.getMessage());
          ijsRateVO.setErrorAllreadySet(true);
          if(actionForm.getAction().equals(IjsActionMethod.ADD_COST_RATE.getAction())){
        	  ijsRateVOAddErrLst.add(ijsRateVO); 
          }else{
        	  ijsRateVOEditErrLst.add(ijsRateVO); 
          }
          
          errorFound=true;   
        }
        if(!errorFound){
        	errorCode = ijsCntrAddCostRateStrdProce.addContractRate(actionForm, userInfo);
        }
        
        System.out.println("Error code ---->" + errorCode);
        if (actionForm.getAction().equals(IjsActionMethod.ADD_COST_RATE.getAction())) {
        	if(errorCode != null){
        		if (errorCode.equals("DB_IJS_CNTR_EX_10014")) {
                    ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10014.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }
                else if (errorCode.equals("DB_IJS_CNTR_EX_10022")) {
                    ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10022.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }
                else if (errorCode.equals("DB_IJS_CNTR_EX_10024")) {
                    ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10024.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }
                else if (errorCode.equals("DB_IJS_CNTR_EX_10026")) {
                    ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10026.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                } 
                else if (errorCode.equals("DB_IJS_RATE_EX_10016")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10016.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10018")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10018.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10019")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10019.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10020")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10020.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10021")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10021.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10022")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10022.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10023")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10023.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10024")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10024.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOAddErrLst.add(ijsRateVO);
                    errorFound=true;
                }
        	}
                

        }
        else if (actionForm.getAction().equals(IjsActionMethod.EDIT_COST_RATE.getAction())) {
        	if(errorCode != null){
        		if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10015")) {
                    ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10015.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10022")) {
                    ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10022.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10024")) {
                    ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10024.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_CNTR_EX_10026")) {
                    ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10026.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }
                else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10016")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10016.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10017")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10017.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10018")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10018.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10019")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10019.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10020")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10020.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10021")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10021.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10022")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10022.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10023")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10023.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }else if (errorCode.equals("DB_IJS_RATE_EX_10024")) {
                	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10024.getErrorCode());
                    ijsRateVO.setErrorAllreadySet(true);
                    ijsRateVOEditErrLst.add(ijsRateVO);
                    errorFound=true;
                }
        	}
        }
	        if(errorFound){
	        	ijsRateVO.setEqType(IjsHelper.getEqTypWithComma(ijsRateVO.getEqTypeList()));
	        	setPortIMDGList(ijsRateVO);
	        	if(BKG_BL.equals(ijsRateVO.getRateBasis()) || LUMPSUM.equals(ijsRateVO.getRateBasis())){
	        		ijsRateVO.setEqType("**");
	        		ijsRateVO.setRateBasis(IjsHelper.getRateBaseValue(ijsRateVO.getRateBasis()));
	        		ijsRateVO.setEqCatq(IjsHelper.getEqCatelog(ijsRateVO.getEqCatq()));
	        		ijsRateVO.setSplHandling(IjsHelper.getSpHandlingValue(ijsRateVO.getSplHandling()));
	        		ijsRateVO.setCalcMethod(IjsHelper.getClacMethodType(ijsRateVO.getCalcMethod()));
	        	}
	        	errorFound=false;
	        }
        }
        
        rateUim = getContractRateList(terminalDepotCode,actionForm.getRoutingNumber(),userInfo,"rateSearch", false); 
        if(ijsRateVOAddErrLst!=null && !ijsRateVOAddErrLst.isEmpty()){
            rateUim.getRateResults().getResults().addAll(ijsRateVOAddErrLst);
        }
        List<IjsRateVO> removeDupList=new ArrayList<IjsRateVO>();
        if(ijsRateVOEditErrLst!=null && !ijsRateVOEditErrLst.isEmpty()){
            for(IjsRateVO ijsRateVO : ijsRateVOEditErrLst){
               if(rateUim.getRateResults().getResults()!=null){
                   for(IjsRateVO ijsRateVO1 : rateUim.getRateResults().getResults()){
                      if(ijsRateVO.getCostRateSequenceNum()==ijsRateVO1.getCostRateSequenceNum() && 
                    		  ijsRateVO.getDetailSeqNumbers().equalsIgnoreCase(ijsRateVO1.getDetailSeqNumbers())){
                          removeDupList.add(ijsRateVO1); 
                          break;
                      }
                   } 
               }
             }
             if(removeDupList!=null && !removeDupList.isEmpty()){
                 rateUim.getRateResults().getResults().removeAll(removeDupList);
             }
            
            rateUim.getRateResults().getResults().addAll(ijsRateVOEditErrLst);
        }
        
        
        return rateUim;
    }
    
    
    private void setPortIMDGList(IjsRateVO ijsRateVO){
    	String portNdImdgResult=null;
    	String imdgPortType=null;
    	if(ijsRateVO.getImdgDetails()!=null){
    		imdgPortType=IMDG;
    		portNdImdgResult=ijsRateVO.getImdgDetails();
    	}else if(ijsRateVO.getPortClassCode()!=null){
    		imdgPortType=PORT_CODE;
    		portNdImdgResult=ijsRateVO.getPortClassCode();
    	}
    	if (portNdImdgResult != null) {
            
            String dGImdgPortClassArr [] = portNdImdgResult.substring(0).split(",");
            List<IjsCostImdgPortClassVO> dgImdgPortClassList = new ArrayList<IjsCostImdgPortClassVO>();
            
            if (dGImdgPortClassArr.length > 0) {
            
            for (String dgImdgSetUp : dGImdgPortClassArr) {
                 IjsCostImdgPortClassVO imdgPortClassVO = new IjsCostImdgPortClassVO();
                 String dgImdgPortClassCodes[] = dgImdgSetUp.split(":");
                                        
                 if (dgImdgPortClassCodes.length > 0) {
                	 if(IMDG.equalsIgnoreCase(imdgPortType)){
                		 imdgPortClassVO.setImdgClass(dgImdgPortClassCodes[0]);
                	 }else{
                		 imdgPortClassVO.setPortClass(dgImdgPortClassCodes[0]);
                	 }
                     
                 }
                 if (dgImdgPortClassCodes.length > 1) {
                    imdgPortClassVO.setIncludeUnno(dgImdgPortClassCodes[1]);
                    //setExcludeUnno();
                 }
                 if (dgImdgPortClassCodes.length > 2) {
                    imdgPortClassVO.setExcludeUnno(dgImdgPortClassCodes[2]);
                 }
                                        
                 dgImdgPortClassList.add(imdgPortClassVO);
                 }
                 }
                                   
    
        if(PORT_CODE.equalsIgnoreCase(imdgPortType))
        {
            ijsRateVO.setPortClassList(dgImdgPortClassList);
            ijsRateVO.setPortClassCode(portNdImdgResult); 
        }
        else if (IMDG.equalsIgnoreCase(imdgPortType)){
            ijsRateVO.setImDgList(dgImdgPortClassList);
            ijsRateVO.setImdgDetails(portNdImdgResult);
        }
        
    }
    }
    

    protected class IjsCntrAddCostRateStrdProce extends StoredProcedure{
        private static final String SQL_RLTD_IJS_RATE =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_COST_RATE_SAVE";
        IjsCntrAddCostRateStrdProce(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_RLTD_IJS_RATE);
            declareParameter(new SqlInOutParameter("p_i_v_action_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_routing_no", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_service", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_mt_laden", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_rate_basis", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_eq_catelog", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_rate_status", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_charge_code", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_term", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_calc_method", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_eq_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_upto", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_uom", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_imp_exp", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_spl_handling", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_currency", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_port_class_code", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_imdg_details", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_oog_setup", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_spl_cst_by_bl_bkg", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_rate20", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_rate40", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_rate45", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_lump_sum", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_cost_seq_number", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_port_imdg_seq", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_oog_setup_seq", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_eq_type_seq", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_term_seq", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_mot", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_detail_seq_no", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_per_trip", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_vessel_seq_no", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_vessel_codes", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_oog_code", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_port_code", Types.VARCHAR));//CR#04
            declareParameter(new SqlInOutParameter("p_i_v_imdg_code", Types.VARCHAR));//CR#04
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));//CR#04
           
            compile();
        }
        //
        private String addContractRate(IjsContractRateUIM actionForm, String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            String mtLaden="";
            String equipCategory="";
            String eqType="";
            IjsRateVO ijsRateVO=actionForm.getIjsRateVO();
            inParameters.put("p_i_v_action_type", RutDatabase.stringToDb(IjsHelper.getActionType(actionForm
                .getAction())).toUpperCase());
            inParameters.put("p_i_v_routing_no",actionForm.getRoutingNumber());
            inParameters.put("p_i_v_start_date", RutDatabase.stringToDb(
                IjsHelper.getDateFormat(ijsRateVO.getStartDate())).toUpperCase());
            inParameters.put("p_i_v_end_date", RutDatabase.stringToDb(
                IjsHelper.getDateFormat(ijsRateVO.getEndDate())).toUpperCase());
            String service =     RutDatabase.stringToDb(ijsRateVO
                .getService()).toUpperCase();
            inParameters.put("p_i_v_service", ( ("").equals(service) ? "***" : service ));
            mtLaden=ijsRateVO.getMtOrLaden();
            
            String lRateBasis= RutDatabase.stringToDb(ijsRateVO
                    .getRateBasis()).toUpperCase();
            inParameters.put("p_i_v_rate_basis", RutDatabase.stringToDb(ijsRateVO
                .getRateBasis()).toUpperCase());
            equipCategory=ijsRateVO.getEqCatq();
            inParameters.put("p_i_v_rate_status", RutDatabase.stringToDb(ijsRateVO
                .getRateStatus()).toUpperCase());
            inParameters.put("p_i_v_term", RutDatabase.stringToDb(ijsRateVO
                .getTerm()).toUpperCase());
                
            String calcMethod = "4";//Default as Fix Amount ie code 4
            if(ijsRateVO.getUpto() > 0) {
                calcMethod="3"; // IF there is value in Upto then "Tier Amount" ie code 3
            }
            
            inParameters.put("p_i_v_calc_method", RutDatabase.stringToDb(calcMethod));
            if(ijsRateVO.getEqTypeList() != null && ijsRateVO.getEqTypeList().size() > 0){
            	if(ijsRateVO.getEqTypeList().size()>1 && ijsRateVO.getEqTypeList().contains("**")){
            		return IjsErrorCode.DB_IJS_RATE_EX_10020.name();
            	}
            inParameters.put("p_i_v_eq_type", "ALL".equals(RutDatabase.stringToDb(IjsHelper.convertListToString(ijsRateVO
                    .getEqTypeList())).toUpperCase())?"**":RutDatabase.stringToDb(IjsHelper.convertListToString(ijsRateVO
                    .getEqTypeList())).toUpperCase()); 
            }else if(ijsRateVO.getEqTypeList()==null && ("L".equals(lRateBasis) ||"B".equals(lRateBasis))){
            	inParameters.put("p_i_v_eq_type","");
            }
            else{
            eqType=ijsRateVO.getEqType();
            List<String> splitEqType=Arrays.asList(eqType.split(","));
            if(splitEqType.size()>1 && splitEqType.contains("**")){
        		return IjsErrorCode.DB_IJS_RATE_EX_10020.name();
        	}
            inParameters.put("p_i_v_eq_type", ("ALL".equals(RutDatabase.stringToDb(eqType).toUpperCase())  
            		||  "".equals(RutDatabase.stringToDb(eqType).toUpperCase()))?"**":RutDatabase.stringToDb(eqType).toUpperCase()); 
            }
                
            inParameters.put("p_i_v_upto", ijsRateVO
                .getUpto());
            inParameters.put("p_i_v_uom", RutDatabase.stringToDb("K"));
            inParameters.put("p_i_v_imp_exp", RutDatabase.stringToDb(ijsRateVO
                .getImpOrExp()).toUpperCase());
            String spHand=RutDatabase.stringToDb(ijsRateVO
                    .getSplHandling()).toUpperCase();
            inParameters.put("p_i_v_spl_handling", ("".equals(spHand)?"N":spHand));
            inParameters.put("p_i_v_currency", RutDatabase.stringToDb(ijsRateVO
                .getCurrency()).toUpperCase());
                
            if(ijsRateVO.getPortClassList() != null && ijsRateVO.getPortClassList().size() > 0){
            inParameters.put("p_i_v_port_class_code", RutDatabase.stringToDb(IjsHelper.convertImdgPortClass(ijsRateVO
                    .getPortClassList())).toUpperCase());    
            }
            else{
            inParameters.put("p_i_v_port_class_code", RutDatabase.stringToDb(ijsRateVO
                .getPortClassCode()).toUpperCase());
            }
        
            if(ijsRateVO.getImDgList() != null && ijsRateVO.getImDgList().size() > 0){
            inParameters.put("p_i_v_imdg_details", RutDatabase.stringToDb(IjsHelper.convertImdgPortClass(ijsRateVO
                    .getImDgList())).toUpperCase());    
            }
            else{
            inParameters.put("p_i_v_imdg_details", RutDatabase.stringToDb(ijsRateVO
                .getImdgDetails()).toUpperCase());
            }   
            
            if (ijsRateVO.getOogSetUpList() != null && ijsRateVO.getOogSetUpList().size() > 0) {
                inParameters.put("p_i_v_oog_setup", RutDatabase.stringToDb(IjsHelper.convertOogSetup(ijsRateVO
                    .getOogSetUpList())).toUpperCase());
            }
            else {
                inParameters.put("p_i_v_oog_setup", RutDatabase.stringToDb(ijsRateVO.getOogSetup()).toUpperCase());    
            }
            
            inParameters.put("p_i_v_rate20", Double.parseDouble(ijsRateVO
                .getRate20()));
            inParameters.put("p_i_v_rate40", Double.parseDouble(ijsRateVO
                .getRate40()));
            inParameters.put("p_i_v_rate45", Double.parseDouble(ijsRateVO
                .getRate45()));
            inParameters.put("p_i_v_cost_seq_number",ijsRateVO
                .getCostRateSequenceNum());  
            String lumpSum=RutDatabase.stringToDb(ijsRateVO
                    .getLumpSum()).toUpperCase();
            
            equipCategory=ijsRateVO.getEqCatq();
            if(!"S".equals(lRateBasis)){
            	inParameters.put("p_i_v_lump_sum", "".equals(lumpSum)?"0":lumpSum);
            	inParameters.put("p_i_v_mt_laden", "");
            	inParameters.put("p_i_v_eq_catelog", "*");
                inParameters.put("p_i_v_charge_code", "*");
            }else{
            	inParameters.put("p_i_v_lump_sum", "");
            	inParameters.put("p_i_v_eq_catelog", (equipCategory==null || equipCategory.isEmpty())?"B":RutDatabase.stringToDb(equipCategory).toUpperCase());
                inParameters.put("p_i_v_charge_code", RutDatabase.stringToDb(ijsRateVO
                    .getChargeCode()).toUpperCase());
                inParameters.put("p_i_v_mt_laden", (mtLaden==null || mtLaden.isEmpty())?"MT":RutDatabase.stringToDb(mtLaden).toUpperCase());
            }
            
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
            inParameters.put("p_i_v_port_imdg_seq",ijsRateVO
                .getPortAndImdgSeqNum());
            inParameters.put("p_i_v_oog_setup_seq",ijsRateVO
                .getOogSetupSeqNum());
            inParameters.put("p_i_v_eq_type_seq",ijsRateVO
                .getEqTypeSeqNum());
            inParameters.put("p_i_v_term_seq",ijsRateVO
                .getTermSeqNum());
            inParameters.put("p_i_v_mot", RutDatabase.stringToDb(IjsHelper.getTransCode(ijsRateVO
                .getMot())).toUpperCase());
            inParameters.put("p_i_v_detail_seq_no", RutDatabase.stringToDb(ijsRateVO.getDetailSeqNumbers()).toUpperCase());
            
            
            inParameters.put("p_i_v_spl_cst_by_bl_bkg",RutDatabase.stringToDb(ijsRateVO.getSplCostByBlOrBooking()).toUpperCase());
            String perTripVal = "N";
            if(ijsRateVO.getPerTrip()!=null && ijsRateVO.getPerTrip().equalsIgnoreCase("true")) {
                perTripVal="Y";
            } else if(ijsRateVO.getPerTrip()!=null && ijsRateVO.getPerTrip().equalsIgnoreCase("false")) {
                perTripVal="N";
            }
            inParameters.put("p_i_v_per_trip",RutDatabase.stringToDb(perTripVal).toUpperCase());
            inParameters.put("p_i_v_vessel_seq_no",ijsRateVO.getVesselSeq());
            
            String vessel =     RutDatabase.stringToDb(ijsRateVO.getVesselCodes()).toUpperCase();
            inParameters.put("p_i_v_vessel_codes",("".equals(vessel) ? "***" : vessel));           
          //CR#04 START
            inParameters.put("p_i_v_oog_code",RutDatabase.stringToDb(ijsRateVO.getOogCode()).toUpperCase()); 
            inParameters.put("p_i_v_port_code",RutDatabase.stringToDb(ijsRateVO.getPortCode()).toUpperCase()); 
            inParameters.put("p_i_v_imdg_code",RutDatabase.stringToDb(ijsRateVO.getImdgCode()).toUpperCase()); 
          //CR#04 END
            outMap = execute(inParameters);
            return (String)outMap.get("p_o_v_err_cd");
        }
    }
    
    protected class IjsCntrMaintainanceStrdProce extends StoredProcedure {
        private static final String SQL_RLTD_IJS_RATE_MAINT 
            =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_COST_RATE_MAINT";
        IjsCntrMaintainanceStrdProce(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_RLTD_IJS_RATE_MAINT);
            declareParameter(new SqlInOutParameter("p_i_v_action_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_cntr_routing_no", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_cost_seq_num", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_cost_detail_seq_no", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            compile();
        }

        private String costMaintainance(Integer seqNumber, String userInfo, 
                                      String action, IjsContractRateUIM actionForm) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_cntr_routing_no",actionForm.getRoutingNumber());
            inParameters.put("p_i_v_cost_seq_num",seqNumber);
            inParameters.put("p_i_v_action_type", RutDatabase.stringToDb(IjsHelper.getActionType(action).toUpperCase()));
            inParameters.put("p_i_v_cost_detail_seq_no",actionForm.getIjsRateVO().getDetailSeqNumbers());
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo));
            outMap = execute(inParameters);
            return (String) outMap.get("p_o_v_err_cd");
        }
        
        private String costMaintainance(Integer seqNumber, Integer routingNumber, String detailSeqNumber, String userInfo, 
                                      String action ) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_cntr_routing_no",routingNumber);
            inParameters.put("p_i_v_cost_seq_num",seqNumber);
            inParameters.put("p_i_v_action_type", RutDatabase.stringToDb(IjsHelper.getActionType(action).toUpperCase()));
            inParameters.put("p_i_v_cost_detail_seq_no",detailSeqNumber);
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo));
            outMap = execute(inParameters);
            return (String) outMap.get("p_o_v_err_cd");
        }
        
    }
//    protected class IjsPortImdgStrdProce extends StoredProcedure {
//        private static final String SQL_RLTD_IJS_PORT_IMDG_CODES
//            =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_COST_SPL_CODES_SEARCH";
//        IjsPortImdgStrdProce(JdbcTemplate jdbcTemplate) {
//            super(jdbcTemplate, SQL_RLTD_IJS_PORT_IMDG_CODES);
//            declareParameter(new SqlInOutParameter("p_i_v_port_imdg_seq", Types.VARCHAR));
//            declareParameter(new SqlInOutParameter("p_i_v_oog_seq", Types.NUMERIC));
//            declareParameter(new SqlInOutParameter("p_i_v_eq_type_seq", Types.NUMERIC));
//            declareParameter(new SqlInOutParameter("p_i_v_term_seq", Types.NUMERIC));
//            declareParameter(new SqlInOutParameter("p_i_v_routing_no", Types.NUMERIC));
//            declareParameter(new SqlInOutParameter("p_i_v_cost_seq_no", Types.NUMERIC));
//            declareParameter(new SqlOutParameter("p_o_v_port_result", Types.VARCHAR));
//            declareParameter(new SqlOutParameter("p_o_v_oog_result", Types.VARCHAR));
//            declareParameter(new SqlOutParameter("p_o_v_eq_type_result", Types.VARCHAR));
//            declareParameter(new SqlOutParameter("p_o_v_term_result", Types.VARCHAR));
//            declareParameter(new SqlOutParameter("p_o_v_imdg_port_type", Types.VARCHAR));
//            compile();
//        }
//
//        private Map getSplHadlingCodes(int portImdgSeq, int oogSetupSeq, 
//                                        int eqTypeSeq, int termSeq, int routinNo, int costSeqNo) {
//            Map outMap = new HashMap();
//            Map inParameters = new HashMap();
//            inParameters.put("p_i_v_port_imdg_seq", portImdgSeq);
//            inParameters.put("p_i_v_oog_seq", oogSetupSeq);
//            inParameters.put("p_i_v_eq_type_seq", eqTypeSeq);
//            inParameters.put("p_i_v_term_seq", termSeq);
//            inParameters.put("p_i_v_routing_no", routinNo);
//            inParameters.put("p_i_v_cost_seq_no", costSeqNo);
//            outMap = execute(inParameters);
//            return outMap;
//        }
//    }
    //##02 END
    
//     protected class IjsGetVesselCodesStrdProc extends StoredProcedure {
//         private static final String SQL_IJS_COST_VESSEL_CODES_SEARCH
//             =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_VESSEL_CODES";
//         IjsGetVesselCodesStrdProc(JdbcTemplate jdbcTemplate) {
//             super(jdbcTemplate, SQL_IJS_COST_VESSEL_CODES_SEARCH);
//             declareParameter(new SqlInOutParameter("p_i_v_vessel_seq", Types.VARCHAR));
//             declareParameter(new SqlInOutParameter("p_i_v_routing_no", Types.NUMERIC));
//             declareParameter(new SqlInOutParameter("p_i_v_cost_seq_no", Types.NUMERIC));
//             declareParameter(new SqlOutParameter("p_o_v_vessel_codes", Types.VARCHAR));
//             compile();
//         }
//
//         private String getVesselCodes(int vesselSeq, int routingNo, 
//                                         int costSeqNo) {
//             String vesselCodes;
//             Map outMap = new HashMap();
//             Map inParameters = new HashMap();
//             inParameters.put("p_i_v_vessel_seq", vesselSeq);
//             inParameters.put("p_i_v_routing_no", routingNo);
//             inParameters.put("p_i_v_cost_seq_no", costSeqNo);
//             outMap = execute(inParameters);
//             vesselCodes = (String)outMap.get("p_o_v_vessel_codes");
//             return vesselCodes;
//         }
//     }
     //##03 BEGIN
    protected class IjsCntrBillingMaintainanceStrdProce extends StoredProcedure {
        private static final String SQL_RLTD_IJS_RATE_MAINT 
                =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_BILLING_RATE_MAINT";
        IjsCntrBillingMaintainanceStrdProce(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_RLTD_IJS_RATE_MAINT);
            declareParameter(new SqlInOutParameter("p_i_v_action_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_cntr_routing_no", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_billing_seq_num", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_bill_detail_seq_no", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            compile();
        }

        private String billingMaintainance(Integer seqNumber, String userInfo, 
                                      String action, IjsContractRateUIM actionForm) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_action_type",actionForm.getAction());
            inParameters.put("p_i_v_cntr_routing_no",actionForm.getRoutingNumber());
            inParameters.put("p_i_v_billing_seq_num",seqNumber);
            inParameters.put("p_i_v_bill_detail_seq_no",actionForm.getIjsRateVO().getDetailSeqNumbers());
            inParameters.put("p_i_v_action_type", RutDatabase.stringToDb(IjsHelper.getActionType(action).toUpperCase()));
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo));
            outMap = execute(inParameters);
            return (String) outMap.get("p_o_v_err_cd");
        }
        
        private String billingMaintainance(Integer seqNumber, Integer routingNumber, String detailSeqNumbers, String userInfo, 
                                      String action) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_cntr_routing_no",routingNumber);
            inParameters.put("p_i_v_billing_seq_num",seqNumber);
            inParameters.put("p_i_v_bill_detail_seq_no",detailSeqNumbers);
            inParameters.put("p_i_v_action_type", RutDatabase.stringToDb(IjsHelper.getActionType(action).toUpperCase()));
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo));
            outMap = execute(inParameters);
            return (String) outMap.get("p_o_v_err_cd");
        }
    }
    
   
    
     /**
     * billingRateMaintainance for maintaining the billing cost rate
     * @param bilingRateSeqNumList
     * @param userInfo
     * @param action
     * @return
     */
     public IjsContractRateUIM billingRateMaintainance(List<Integer> bilingRateSeqNumList, 
                                                       String userInfo, 
                                                       String action,
                                                       IjsContractRateUIM actionForm) {
                                                       
         List<IjsRateVO> ijsRateList =   actionForm.getIjsRateList();                                                       
         String errorCode = null;
         List<String> failedList = new ArrayList<String>();
         List<String> successList = new ArrayList<String>();
         
         for (IjsRateVO ijsRateVO : ijsRateList) {
             int seqNumber=ijsRateVO.getCostRateSequenceNum();
             
              errorCode = ijsCntrBillingMaintainanceStrdProce.billingMaintainance(seqNumber , actionForm.getRoutingNumber(), ijsRateVO.getDetailSeqNumbers(), userInfo, action);

             if (errorCode != null && errorCode.contains("DB_IJS_COMM_EX_10001")) {
                 failedList.add(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
             }
             if (action.equals(IjsActionMethod.APPROVE_BILLING_RATE.getAction())) {
                 if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10016")) {
                     failedList.add(seqNumber + "-" + IjsErrorCode.DB_IJS_CNTR_EX_10016.getErrorCode());
                 } 
                 else {
                     successList.add(seqNumber + "-" + IjsMessageCode.IJS_CNTR_BILL_RATE_APPROVE.getMsgCode());
                 }
             }
             else if (action.equals(IjsActionMethod.DELETE_BILLING_RATE.getAction())) {
                 if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10017")) {
                     failedList.add(seqNumber + "-" + IjsErrorCode.DB_IJS_CNTR_EX_10017.getErrorCode());
                 } 
                 else {
                     successList.add(seqNumber + "-" + IjsMessageCode.IJS_CNTR_BILL_RATE_DELETE.getMsgCode());
                 }
             }
             else if (action.equals(IjsActionMethod.REJECT_BILLING_RATE.getAction())) {
                 if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10018")) {
                     failedList.add(seqNumber + "-" + IjsErrorCode.DB_IJS_CNTR_EX_10018.getErrorCode());
                 } 
                 else {
                     successList.add(seqNumber + "-" + IjsMessageCode.IJS_CNTR_BILL_RATE_REJECTED.getMsgCode());
                 }
             }
         }
         IjsContractRateUIM billingRateUIM = new IjsContractRateUIM();
         billingRateUIM.setErrorMsgList(failedList);
         billingRateUIM.setSuccessMgsList(successList);
         billingRateUIM.setAction(action);
         return billingRateUIM;
     }
     
    protected class IjsContractBillingRateStrdPrce extends StoredProcedure {
        private static final String SQL_RLTD_IJS_RATE =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_BILLING_RATE_SEARCH";
        IjsContractBillingRateStrdPrce(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_RATE);
            
            declareParameter(new SqlInOutParameter("p_i_v_routing_number", Types.NUMERIC, rowMapper));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
            declareParameter(new SqlOutParameter("p_o_v_ijs_rate_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }
        
        private List<IjsRateVO> getContractBillingRateList(int routngNumber, String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_routing_number",routngNumber);
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
            outMap = execute(inParameters);
            return (List<IjsRateVO>)outMap.get("p_o_v_ijs_rate_list");
        }
    }
    private class IjsCntrBillingRateRowMapper implements RowMapper {
        public Object mapRow(ResultSet resultSet, int row) {
            IjsRateVO ijsRateVO = new IjsRateVO();
            String rate20;
            String rate40;
            String rate45;
            try {
                ijsRateVO.setCalcMethod(IjsHelper.getClacMethodType(resultSet.getString("CALC_METHOD")));
                ijsRateVO.setChargeCode(resultSet.getString("CHARGE_CODE"));
                ijsRateVO.setCurrency(resultSet.getString("CURRENCY"));
                ijsRateVO.setEndDate(resultSet.getString("END_DATE"));
                ijsRateVO.setEqCatq(IjsHelper.getEqCatelog(resultSet.getString("EQUIPMENT_CATAG")));
                ijsRateVO.setEqType(resultSet.getString("EQ_TYPE"));
                ijsRateVO.setMtOrLaden(resultSet.getString("MT_LADEN"));
               
                rate20=resultSet.getString("RATE20");
                rate40=resultSet.getString("RATE40");
                rate45=resultSet.getString("RATE45");
                if(rate20!=null){
                	ijsRateVO.setRate20(RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(rate20), null));
                }
                if(rate40!=null){
                	ijsRateVO.setRate40(RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(rate40), null));
                }
                if(rate45!=null){
                	ijsRateVO.setRate45(RutFormatting.getDoubleToDecimalFormat(Double.parseDouble(rate45), null));
                }
               
                ijsRateVO.setRateBasis(IjsHelper.getRateBaseValue(resultSet.getString("RATE_BASICS")));
                ijsRateVO.setRateStatus(IjsHelper.getRateStatus(resultSet.getString("RATE_STATUS")));
                ijsRateVO.setService(resultSet.getString("SERVICE"));
                ijsRateVO.setStartDate(resultSet.getString("START_DATE"));
                ijsRateVO.setUom(resultSet.getString("UOM"));
                ijsRateVO.setUpto(resultSet.getInt("UPTO"));
                ijsRateVO.setCostRateSequenceNum(resultSet.getInt("SEQUENCE_NUMBER"));
                ijsRateVO.setDetailSeqNumbers(resultSet.getString("DETAIL_SEQ"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return ijsRateVO;
        }
    }
    /**
     * saveOrUpdateBillingRate TO save billing cost rate
     * @param actionForm
     * @param userInfo
     * @return
     */
    public String saveOrUpdateBillingRate(IjsContractRateUIM actionForm, 
                                          String userInfo) {
        String errorCode = ijsCntrAddBillingRateStrdProce.addContractRate(actionForm, userInfo);
        if (actionForm.getAction().equals(IjsActionMethod.ADD_BILLING_RATE.getAction())) {
            if (errorCode != null) {
                if (IjsErrorCode.DB_IJS_CNTR_EX_10019.name().equals(errorCode)) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10019.getErrorCode();
                }
                else if (IjsErrorCode.DB_IJS_CNTR_EX_10023.name().equals(errorCode)) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10023.getErrorCode();
                }else if (IjsErrorCode.DB_IJS_CNTR_EX_10025.name().equals(errorCode)) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10025.getErrorCode();
                }else if (IjsErrorCode.DB_IJS_RATE_EX_10016.name().equals(errorCode)) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10016.getErrorCode();
                }else if (IjsErrorCode.DB_IJS_RATE_EX_10020.name().equals(errorCode)) {
                	return IjsErrorCode.DB_IJS_RATE_EX_10020.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10021")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10021.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10023")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10023.getErrorCode();
                }
                 
            }
            else if (errorCode == null) {
                return IjsMessageCode.IJS_CNTR_BILL_RATE_ADDED.getMsgCode();
            }
            System.out.println("Error code is -->" + errorCode);
        }
        else if (actionForm.getAction().equals(IjsActionMethod.EDIT_BILLING_RATE.getAction())) {
            if (errorCode != null) {
            	if (IjsErrorCode.DB_IJS_CNTR_EX_10020.name().equals(errorCode)) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10020.getErrorCode();
                }
                else if (IjsErrorCode.DB_IJS_CNTR_EX_10023.name().equals(errorCode)) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10023.getErrorCode();
                }else if (IjsErrorCode.DB_IJS_CNTR_EX_10025.name().equals(errorCode)) {
                    return IjsErrorCode.DB_IJS_CNTR_EX_10025.getErrorCode();
                }else if (IjsErrorCode.DB_IJS_RATE_EX_10016.name().equals(errorCode)) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10016.getErrorCode();
                }else if (IjsErrorCode.DB_IJS_RATE_EX_10020.name().equals(errorCode)) {
                	return IjsErrorCode.DB_IJS_RATE_EX_10020.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10021")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10021.getErrorCode();
                }else if (errorCode != null && errorCode.equals("DB_IJS_RATE_EX_10023")) {
                    return IjsErrorCode.DB_IJS_RATE_EX_10023.getErrorCode();
                }
            }
            else if (errorCode == null) {
                return IjsMessageCode.IJS_CNTR_BILL_RATE_UPDATED.getMsgCode();
            }
            System.out.println("Error code is -->" + errorCode);
        }
        return null;
    }
    
    public IjsContractRateUIM saveOrUpdateBillRateList(IjsContractRateUIM actionForm, 
                                          String userInfo)  throws IJSException{
        List<IjsRateVO> ijsRateVOEditErrLst = new ArrayList<IjsRateVO>();
        List<IjsRateVO> ijsRateVOAddErrLst = new ArrayList<IjsRateVO>();
        IjsContractRateUIM rateUim ;
        String terminalDepotCode=actionForm.getCostRateSetup()!=null?actionForm.getCostRateSetup().getTerminalDepotCode():null;
        boolean errorFound=false;
        String errorCode = null;
        for(IjsRateVO ijsRateVO : actionForm.getIjsRateList()){ 
        
        actionForm.setIjsRateVO(ijsRateVO);
        actionForm.setAction(ijsRateVO.getOperation());
        ijsRateVO.setErrorAllreadySet(false);
	        try{
	        	IjsHelper.validateRequest(ijsRateVO);
	        }catch(IJSException ie){
	      	  ijsRateVO.setErrorMsg(ie.getMessage());
	          ijsRateVO.setErrorAllreadySet(true);
	          if(ijsRateVO.getOperation().equalsIgnoreCase("editBillingRate")){
	        	  ijsRateVOEditErrLst.add(ijsRateVO);
	          }else{
	        	  ijsRateVOAddErrLst.add(ijsRateVO);
	          }
	          
	          errorFound=true;   
	        }
	        if(!errorFound){
	        	errorCode = ijsCntrAddBillingRateStrdProce.addContractRate(actionForm, userInfo);
	        }
	            
	        if (errorCode != null) {
	            if (IjsErrorCode.DB_IJS_CNTR_EX_10019.name().equals(errorCode)) {
	                ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10019.getErrorCode());
	                errorFound=true;
	            }
	            else if (IjsErrorCode.DB_IJS_CNTR_EX_10023.name().equals(errorCode)) {
	                ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10023.getErrorCode());
	                errorFound=true;
	            }
	            else if (IjsErrorCode.DB_IJS_CNTR_EX_10025.name().equals(errorCode)) {
	                ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_CNTR_EX_10025.getErrorCode());
	                errorFound=true;
	            } else if ( IjsErrorCode.DB_IJS_RATE_EX_10016.name().equals(errorCode)) {
	                ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10016.getErrorCode());
	                errorFound=true;
	            }else if (IjsErrorCode.DB_IJS_RATE_EX_10017.name().equals(errorCode)) {
	            	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10017.getErrorCode());
	                errorFound=true;
	            }else if (IjsErrorCode.DB_IJS_RATE_EX_10020.name().equals(errorCode)) {
	            	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10020.getErrorCode());
	                errorFound=true;
	            }else if (IjsErrorCode.DB_IJS_RATE_EX_10021.name().equals(errorCode)) {
	            	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10021.getErrorCode());
	                errorFound=true;
	            }else if (IjsErrorCode.DB_IJS_RATE_EX_10023.name().equals(errorCode)) {
	            	ijsRateVO.setErrorMsg(IjsErrorCode.DB_IJS_RATE_EX_10023.getErrorCode());
	                errorFound=true;
	            }
	        }
	        if(errorFound){
	        	ijsRateVO.setEqType(IjsHelper.getEqTypWithComma(ijsRateVO.getEqTypeList()));
	        	ijsRateVO.setErrorAllreadySet(true);
	            if(ijsRateVO.getOperation().equalsIgnoreCase("editBillingRate")){
	            	ijsRateVOEditErrLst.add(ijsRateVO);
	            }else{
	            	ijsRateVOAddErrLst.add(ijsRateVO);
	            }
	        	errorFound=false;
	        }
            
        }
        rateUim = getContractRateList(terminalDepotCode,actionForm.getRoutingNumber(),userInfo,"rateSearch", false); 
        if(ijsRateVOAddErrLst!=null && !ijsRateVOAddErrLst.isEmpty()){
            rateUim.getBillingRateResults().getResults().addAll(ijsRateVOAddErrLst);
        }
        List<IjsRateVO> removeDupList=new ArrayList<IjsRateVO>();
        if(ijsRateVOEditErrLst!=null && !ijsRateVOEditErrLst.isEmpty()){
            for(IjsRateVO ijsRateVO : ijsRateVOEditErrLst){
               if(rateUim.getBillingRateResults().getResults()!=null){
                   for(IjsRateVO ijsRateVO1 : rateUim.getBillingRateResults().getResults()){
                      if(ijsRateVO.getCostRateSequenceNum()==ijsRateVO1.getCostRateSequenceNum() && 
                    		  ijsRateVO.getDetailSeqNumbers().equalsIgnoreCase(ijsRateVO1.getDetailSeqNumbers())){
                          removeDupList.add(ijsRateVO1); 
                          break;
                      }
                   } 
               }
             }
             if(removeDupList!=null && !removeDupList.isEmpty()){
                 rateUim.getBillingRateResults().getResults().removeAll(removeDupList);
             }
            
            rateUim.getBillingRateResults().getResults().addAll(ijsRateVOEditErrLst);
        }
        return rateUim;
    }
    
    
    protected class IjsCntrAddBillingRateStrdProce extends StoredProcedure {
        private static final String SQL_RLTD_IJS_RATE =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_BILLING_RATE_SAVE";
        IjsCntrAddBillingRateStrdProce(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_RLTD_IJS_RATE);
            declareParameter(new SqlInOutParameter("p_i_v_action_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_routing_no", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_service", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_mt_laden", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_rate_basis", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_eq_catelog", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_rate_status", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_charge_code", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_term", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_calc_method", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_eq_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_upto", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_uom", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_currency", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_rate20", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_rate40", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_rate45", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_bill_seq_number", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_mot", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_eq_type_seq", Types.NUMERIC));
            declareParameter(new SqlInOutParameter("p_i_v_bill_detail_seq_no", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_per_trip", Types.VARCHAR));
            
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            
            compile();
        }
        
        private String addContractRate(IjsContractRateUIM actionForm, String userInfo) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            String mtLaden="";
            String equipCategory="";
            String eqType="";
            inParameters.put("p_i_v_action_type", RutDatabase.stringToDb(IjsHelper.getActionType(actionForm
                .getAction())).toUpperCase());
            inParameters.put("p_i_v_routing_no",actionForm.getRoutingNumber());
            inParameters.put("p_i_v_start_date", RutDatabase.stringToDb(
                IjsHelper.getDateFormat(actionForm.getIjsRateVO().getStartDate())).toUpperCase());
            inParameters.put("p_i_v_end_date", RutDatabase.stringToDb(
                IjsHelper.getDateFormat(actionForm.getIjsRateVO().getEndDate())).toUpperCase());
            
            String service =     RutDatabase.stringToDb(actionForm.getIjsRateVO()
                .getService()).toUpperCase();    
            inParameters.put("p_i_v_service", ("".equals(service) ? "***" : service ));
            
            inParameters.put("p_i_v_mt_laden", RutDatabase.stringToDb(actionForm.getIjsRateVO()
                .getMtOrLaden()).toUpperCase());
            inParameters.put("p_i_v_rate_basis", RutDatabase.stringToDb(actionForm.getIjsRateVO()
                .getRateBasis()).toUpperCase());
            inParameters.put("p_i_v_eq_catelog", RutDatabase.stringToDb(actionForm.getIjsRateVO()
                .getEqCatq()).toUpperCase());
            inParameters.put("p_i_v_rate_status", RutDatabase.stringToDb(actionForm.getIjsRateVO()
                .getRateStatus()).toUpperCase());
            inParameters.put("p_i_v_charge_code", RutDatabase.stringToDb(actionForm.getIjsRateVO()
                .getChargeCode()).toUpperCase());
            inParameters.put("p_i_v_term", RutDatabase.stringToDb(actionForm.getIjsRateVO()
                .getTerm()).toUpperCase());
                
            String calcMethod = "4";//Default as Fix Amount ie code 4
            if(actionForm.getIjsRateVO().getUpto() > 0) {
                calcMethod="3";// IF there is value in Upto then "Tier Amount" ie code 3
            }
            inParameters.put("p_i_v_calc_method", RutDatabase.stringToDb(calcMethod));
            
            if(actionForm.getIjsRateVO().getEqTypeList() != null && actionForm.getIjsRateVO().getEqTypeList().size() > 0){
            	if(actionForm.getIjsRateVO().getEqTypeList().size()>1 && actionForm.getIjsRateVO().getEqTypeList().contains("**")){
            		return IjsErrorCode.DB_IJS_RATE_EX_10020.name();
            	}
            inParameters.put("p_i_v_eq_type", "ALL".equals(RutDatabase.stringToDb(IjsHelper.convertListToString(actionForm.getIjsRateVO()
                        .getEqTypeList())).toUpperCase())?"**":RutDatabase.stringToDb(IjsHelper.convertListToString(actionForm.getIjsRateVO()
                        .getEqTypeList())).toUpperCase()); 
            }
            else{
            	eqType=actionForm.getIjsRateVO().getEqType();
                List<String> splitEqType=Arrays.asList(eqType.split(","));
                if(splitEqType.size()>1 && splitEqType.contains("**")){
            		return IjsErrorCode.DB_IJS_RATE_EX_10020.name();
            	}    
                inParameters.put("p_i_v_eq_type", ("ALL".equals(RutDatabase.stringToDb(actionForm.getIjsRateVO()
                        .getEqType()).toUpperCase())  ||  "".equals(RutDatabase.stringToDb(actionForm.getIjsRateVO()
                        .getEqType()).toUpperCase()))?"**":RutDatabase.stringToDb(actionForm.getIjsRateVO()
                        .getEqType()).toUpperCase()); 
            }    
                
                
            inParameters.put("p_i_v_upto", actionForm.getIjsRateVO()
                .getUpto());
            inParameters.put("p_i_v_uom", RutDatabase.stringToDb("K"));
            inParameters.put("p_i_v_currency", RutDatabase.stringToDb(actionForm.getIjsRateVO()
                .getCurrency()).toUpperCase());
            inParameters.put("p_i_v_rate20", Double.parseDouble(actionForm.getIjsRateVO()
                .getRate20()));
            inParameters.put("p_i_v_rate40", Double.parseDouble(actionForm.getIjsRateVO()
                .getRate40()));
            inParameters.put("p_i_v_rate45", Double.parseDouble(actionForm.getIjsRateVO()
                .getRate45()));
            inParameters.put("p_i_v_bill_seq_number",actionForm.getIjsRateVO()
                .getCostRateSequenceNum());    
            inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
            inParameters.put("p_i_v_mot", RutDatabase.stringToDb(IjsHelper.getTransCode(actionForm.getIjsRateVO()
                .getMot())).toUpperCase());
            inParameters.put("p_i_v_eq_type_seq",actionForm.getIjsRateVO()
                .getEqTypeSeqNum());
            inParameters.put("p_i_v_bill_detail_seq_no", RutDatabase.stringToDb(actionForm.getIjsRateVO().getDetailSeqNumbers()).toUpperCase());
            inParameters.put("p_i_v_per_trip",RutDatabase.stringToDb(actionForm.getIjsRateVO().getPerTrip()).toUpperCase());
            
            outMap = execute(inParameters);
            return (String)outMap.get("p_o_v_err_cd");
        }
    }
    protected class IjsCntrBillingSplCodesStrdProce extends StoredProcedure {
        private static final String SQL_RLTD_IJS_RATE =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_BILL_SPL_CODES_SEARCH";
        IjsCntrBillingSplCodesStrdProce(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_RLTD_IJS_RATE);
            declareParameter(new SqlInOutParameter("p_i_v_eq_type_seq", Types.NUMERIC));
            declareParameter(new SqlOutParameter("p_o_v_eq_type_result", Types.VARCHAR));
            compile();
        }
        private String getSplHadlingCodes(int portClassCode) {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_eq_type_seq", portClassCode);
            outMap = execute(inParameters);
            return (String)outMap.get("p_o_v_eq_type_result");
        }
    }
    protected class IjsCntrExchangeRateProcedure extends StoredProcedure {
        private static final String SQL_IJS_GET_EXCHANGE_RATE =  "PCR_IJS_CNTR_COMMON.PRR_IJS_GET_EXCHANGE_RATE";
        IjsCntrExchangeRateProcedure(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_IJS_GET_EXCHANGE_RATE);
            declareParameter(new SqlInOutParameter("p_i_v_fsc_code", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_rate_from_currency", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_rate_to_currency", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_exchange_rate", Types.DOUBLE));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            compile();
        }
        private Double getExchangeRate(String fscCode,String fromCurrency,String toCurrency) throws IJSException {
            Map outMap = new HashMap();
            Map inParameters = new HashMap();
            inParameters.put("p_i_v_fsc_code", fscCode);
            inParameters.put("p_i_v_rate_from_currency", fromCurrency);
            inParameters.put("p_i_v_rate_to_currency", toCurrency);
            outMap = execute(inParameters);
            String errorMsg=(String)outMap.get("p_o_v_err_cd");
            if(errorMsg!=null){
            	throw new IJSException(errorMsg);
            }
            return (Double)outMap.get("p_o_v_exchange_rate");
        }
    }
    protected class IjsCntrEquipmentTypeProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_EQ_TYPE =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_EQ_TYPE_SEARCH";
        IjsCntrEquipmentTypeProcedure(JdbcTemplate jdbcTemplate , RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_EQ_TYPE);
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_ijs_eq_type_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }
        private List<IjsContractEqTypeVO> getEqTypeList(String userInfo) {
        Map outMap = new HashMap();
        Map inParameters = new HashMap();
        inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
        outMap = execute(inParameters);
        
        return (List<IjsContractEqTypeVO>) outMap.get("p_o_v_ijs_eq_type_list");
        }
        
    }
    private class IjsCntrEquipmentRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int i) {
            IjsContractEqTypeVO eqType = new IjsContractEqTypeVO();
            try {
                eqType.setDescription(resultSet.getString("DESCRIPTON"));
                eqType.setEqType(resultSet.getString("EQ_Type"));
            } catch (SQLException e) {
                e.printStackTrace();
           }
            
            return eqType;
        }
    }
    
    protected class IjsCntrShipmentTermProcedure extends StoredProcedure {
        private static final String SQL_RLTD_IJS_TERM =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_TERM_SEARCH";
        IjsCntrShipmentTermProcedure(JdbcTemplate jdbcTemplate , RowMapper rowMapper) {
            super(jdbcTemplate, SQL_RLTD_IJS_TERM);
            declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_ijs_term_list", 
                                                 OracleTypes.CURSOR, 
                                                 rowMapper));
            compile();
        }
        private List<IjsContractShipmentTermVO> getTermList(String userInfo) {
        Map outMap = new HashMap();
        Map inParameters = new HashMap();
        inParameters.put("p_i_v_user_id" , RutDatabase.stringToDb(userInfo).toUpperCase());
        outMap = execute(inParameters);
        
        return (List<IjsContractShipmentTermVO>) outMap.get("p_o_v_ijs_term_list");
        }
        
    }
    private class IjsCntrShipmentTermRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int i) {
            IjsContractShipmentTermVO term = new IjsContractShipmentTermVO();
            try {
                term.setDescription(resultSet.getString("DESCRIPTON"));
                term.setTermCode(resultSet.getString("TERM_CODE"));
            } catch (SQLException e) {
                e.printStackTrace();
           }
            
            return term;
        }
    }
    //CR#04 START
	@Override
	public Double getExchangeRate(String fscCode, String fromCurrency,
			String toCurrency) throws IJSException {
		return ijsCntrExchangeRateProcedure.getExchangeRate(fscCode, fromCurrency, toCurrency);
	}
	@Override
	public IjsCostRateSetupVO saveOrUpdateOOGSetup(String terminalDepotCd,List<IjsContractOogSetupVO> oogSetupList) throws IJSException {
		if(oogSetupList!=null && !oogSetupList.isEmpty()){
			for(IjsContractOogSetupVO oogSetup:oogSetupList){
				ijsOOGSetupSave.saveOOGSetup(terminalDepotCd,oogSetup);
			}
		}
		IjsCostRateSetupVO oogSetupVO=new IjsCostRateSetupVO();
		oogSetupVO.setOogSetUpList(ijsOOGSetupSearch.searchOOGSetup(terminalDepotCd));
		return oogSetupVO;
	}
	 protected class IjsOOGSetupProcedure extends StoredProcedure {
	        private static final String SQL_CNTR_OOG_SETUP_SAVE =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_OOG_SETUP_SAVE";
	        private static final String SQL_CNTR_OOG_SETUP_SEARCH =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_OOG_SETUP_SEARCH";
	        IjsOOGSetupProcedure(JdbcTemplate jdbcTemplate) {
	            super(jdbcTemplate, SQL_CNTR_OOG_SETUP_SAVE);
	            declareParameter(new SqlInOutParameter("p_i_v_action_cd", Types.VARCHAR));
	            declareParameter(new SqlInOutParameter("p_i_v_terminal_depot_cd", Types.VARCHAR));
	            declareParameter(new SqlInOutParameter("p_i_v_old_oog_cd", Types.VARCHAR));
	            declareParameter(new SqlInOutParameter("p_i_v_oog_cd", Types.VARCHAR));
	            declareParameter(new SqlInOutParameter("p_i_v_min_over_length", Types.VARCHAR));
	            declareParameter(new SqlInOutParameter("p_i_v_max_over_length", Types.VARCHAR));
	            declareParameter(new SqlInOutParameter("p_i_v_min_over_width", Types.VARCHAR));
	            declareParameter(new SqlInOutParameter("p_i_v_max_over_width", Types.VARCHAR));
	            declareParameter(new SqlInOutParameter("p_i_v_min_over_height", Types.VARCHAR));
	            declareParameter(new SqlInOutParameter("p_i_v_max_over_height", Types.VARCHAR));
	            declareParameter(new SqlOutParameter("p_o_v_error_cd", Types.VARCHAR));
	            compile();
	        }
	        IjsOOGSetupProcedure(JdbcTemplate jdbcTemplate , RowMapper rowMapper) {
	            super(jdbcTemplate, SQL_CNTR_OOG_SETUP_SEARCH);
	            declareParameter(new SqlInOutParameter("p_i_v_terminal_depot_cd", Types.VARCHAR));
	            declareParameter(new SqlOutParameter("p_o_v_error_cd", Types.VARCHAR));
	            declareParameter(new SqlOutParameter("p_o_v_oog_list", OracleTypes.CURSOR,rowMapper));
	            compile();
	        }
	        private String saveOOGSetup(String terminalDepotCd,IjsContractOogSetupVO oogSetup) {
		        Map outMap = new HashMap();
		        Map inParameters = new HashMap();
		        inParameters.put("p_i_v_action_cd" , RutDatabase.stringToDb(oogSetup.getAction()).toUpperCase());
		        inParameters.put("p_i_v_terminal_depot_cd" , RutDatabase.stringToDb(terminalDepotCd).toUpperCase());
		        inParameters.put("p_i_v_old_oog_cd" , RutDatabase.stringToDb(oogSetup.getOldOogSetupCode()).toUpperCase());
		        inParameters.put("p_i_v_oog_cd" , RutDatabase.stringToDb(oogSetup.getOogSetupCode()).toUpperCase());
		        inParameters.put("p_i_v_min_over_length" , ASTRIC.equals(oogSetup.getMinOverLength())?-1:oogSetup.getMinOverLength());
		        inParameters.put("p_i_v_max_over_length" , ASTRIC.equals(oogSetup.getMaxOverLength())?-1:oogSetup.getMaxOverLength());
		        inParameters.put("p_i_v_min_over_width" , ASTRIC.equals(oogSetup.getMinOverWidth())?-1:oogSetup.getMinOverWidth());
		        inParameters.put("p_i_v_max_over_width" , ASTRIC.equals(oogSetup.getMaxOverWidth())?-1:oogSetup.getMaxOverWidth());
		        inParameters.put("p_i_v_min_over_height" , ASTRIC.equals(oogSetup.getMinOverHeight())?-1:oogSetup.getMinOverHeight());
		        inParameters.put("p_i_v_max_over_height" , ASTRIC.equals(oogSetup.getMaxOverHeight())?-1:oogSetup.getMaxOverHeight());
		        outMap = execute(inParameters);
		        return (String) outMap.get("p_o_v_error_cd");
	        }
	        private List<IjsContractOogSetupVO>  searchOOGSetup(String terminalDepotCd) {
		        Map outMap = new HashMap();
		        Map inParameters = new HashMap();
		        inParameters.put("p_i_v_terminal_depot_cd" , RutDatabase.stringToDb(terminalDepotCd).toUpperCase());
		        outMap = execute(inParameters);
		        return (List<IjsContractOogSetupVO> ) outMap.get("p_o_v_oog_list");
		    }
	    } 
	 private class IjsOOGSetupRowMapper implements RowMapper {

	        public Object mapRow(ResultSet resultSet, int i) {
	        	IjsContractOogSetupVO oogSetupVO = new IjsContractOogSetupVO();
	        	int usedCount=0;
	            try {
	            	oogSetupVO.setOogSetupCode(resultSet.getString("OOG_SETUP_CODE"));
	            	oogSetupVO.setOldOogSetupCode(resultSet.getString("OOG_SETUP_CODE"));
	            	usedCount=resultSet.getInt("USED_COUNT");
	            	oogSetupVO.setUsedByContract(usedCount>1?true:false);
	            	oogSetupVO.setMaxOverHeight(MINUS.equals(resultSet.getString("MAX_OVER_HEIGHT"))?"*":resultSet.getString("MAX_OVER_HEIGHT"));
	            	oogSetupVO.setMaxOverLength(MINUS.equals(resultSet.getString("MAX_OVER_LENGTH"))?"*":resultSet.getString("MAX_OVER_LENGTH"));
	            	oogSetupVO.setMaxOverWidth(MINUS.equals(resultSet.getString("MAX_OVER_WIDTH"))?"*":resultSet.getString("MAX_OVER_WIDTH"));
	            	oogSetupVO.setMinOverHeight(MINUS.equals(resultSet.getString("MIN_OVER_HEIGHT"))?"*":resultSet.getString("MIN_OVER_HEIGHT"));
	            	oogSetupVO.setMinOverLength(MINUS.equals(resultSet.getString("MIN_OVER_LENGTH"))?"*":resultSet.getString("MIN_OVER_LENGTH"));
	            	oogSetupVO.setMinOverWidth(MINUS.equals(resultSet.getString("MIN_OVER_WIDTH"))?"*":resultSet.getString("MIN_OVER_WIDTH"));
	            } catch (SQLException e) {
	                e.printStackTrace();
	           }
	            
	            return oogSetupVO;
	        }
	    }
	@Override
	public IjsCostRateSetupVO saveOrUpdatePortImdgSetup(String terminalDepotCd,String portImdgType,
			List<IjsCostImdgPortClassVO> portImdgSetupList) throws IJSException {
		String errorCd=null;
		List<IjsCostImdgPortClassVO> failedList=new ArrayList<>();
		List<IjsCostImdgPortClassVO> searchedList=new ArrayList<>();
		List<IjsCostImdgPortClassVO> finalPortImdgList=new ArrayList<>();
		IjsCostRateSetupVO portImdgSetupVO=new IjsCostRateSetupVO();
		StringBuilder failedPortImdg=new StringBuilder();
		boolean failedRecordFound=false;
		if(portImdgSetupList!=null && !portImdgSetupList.isEmpty()){
			for(IjsCostImdgPortClassVO portImdgSetup:portImdgSetupList){
				errorCd=ijsPortImdgSetupSave.savePortImdgSetup(portImdgType,terminalDepotCd,portImdgSetup);
				if(errorCd!=null){
					if(IjsErrorCode.DB_IJS_RATE_EX_10018.name().equals(errorCd)){
						portImdgSetup.setErrorMsgCd(IjsErrorCode.DB_IJS_RATE_EX_10018.getErrorCode());
					}else{
						portImdgSetup.setErrorMsgCd(IjsErrorCode.DB_IJS_RATE_EX_10019.getErrorCode());
					}
					failedList.add(portImdgSetup);
					if(failedPortImdg.length()==0){
						if(portImdgSetup.getOldPortImdgCode()!=null){
							//failedPortImdg=failedPortImdg.append(portImdgSetup.getOldPortImdgCode());
							//if(PORT.equals(portImdgType)){
								failedPortImdg=failedPortImdg.append(portImdgSetup.getPortImdgSeqNo());
							//}else{
								//failedPortImdg=failedPortImdg.append(portImdgSetup.getPortImdgSeqNo());
							//}
							
						}
						
					}else{
						if(portImdgSetup.getOldPortImdgCode()!=null){
						//	if(PORT.equals(portImdgType)){
								//failedPortImdg=failedPortImdg.append(portImdgSetup.getOldPortImdgCode()).append(",");
								failedPortImdg=failedPortImdg.append(portImdgSetup.getPortImdgSeqNo()).append(",");
							//}else{
							//	failedPortImdg=failedPortImdg.append(portImdgSetup.getPortImdgSeqNo()).append(",");
							//}
							
						}
						
					}
					
				}
			}
		}
		searchedList=ijsPortImdgSetupSearch.searchPortImdgSetup(terminalDepotCd, portImdgType,failedPortImdg.toString());
		if(failedList.size()>0){
			for(IjsCostImdgPortClassVO searchedVO:searchedList){
				for(IjsCostImdgPortClassVO failedVO:failedList){
					if(searchedVO.getPortImdgSeqNo().equals(failedVO.getPortImdgSeqNo())){
						
						failedRecordFound=true;
						break;
					}
				}
				if(!failedRecordFound){
					finalPortImdgList.add(searchedVO);
				}else{
					failedRecordFound=false;
				}
			}
			finalPortImdgList.addAll(failedList);
		}
		
		
		if(PORT.equals(portImdgType)){
			if(finalPortImdgList.size()>0){
				portImdgSetupVO.setPortList(finalPortImdgList);
			}else{
				portImdgSetupVO.setPortList(searchedList);
			}
			
		}else{
			if(finalPortImdgList.size()>0){
				portImdgSetupVO.setImdgList(finalPortImdgList);
			}else{
				portImdgSetupVO.setImdgList(searchedList);
			}
			
		}
		
		return portImdgSetupVO;
	}
	
	protected class IjsPortImdgSetupProcedure extends StoredProcedure {
        private static final String SQL_CNTR_PORT_IMDG_SETUP_SAVE =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_PORT_IMDG_SETUP_SAVE";
        private static final String SQL_CNTR_PORT_IMDG_SETUP_SEARCH =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_PORT_IMDG_SETUP_SEARCH";
        IjsPortImdgSetupProcedure(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate, SQL_CNTR_PORT_IMDG_SETUP_SAVE);
            declareParameter(new SqlInOutParameter("p_i_v_action_cd", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_terminal_depot_cd", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_old_port_imdg_cd", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_port_imdg_cd", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_port_imdg_class_cd", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_port_imdg_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_include_unno", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_exclude_unno", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_port_imdg_seq_no", Types.NUMERIC));
          //  declareParameter(new SqlInOutParameter("p_i_v_imdg_seq_no", Types.NUMERIC));
            declareParameter(new SqlOutParameter("p_o_v_error_cd", Types.VARCHAR));
            compile();
        }
        IjsPortImdgSetupProcedure(JdbcTemplate jdbcTemplate , RowMapper rowMapper) {
            super(jdbcTemplate, SQL_CNTR_PORT_IMDG_SETUP_SEARCH);
            declareParameter(new SqlInOutParameter("p_i_v_terminal_depot_cd", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_port_imdg_type", Types.VARCHAR));
            declareParameter(new SqlInOutParameter("p_i_v_port_imdg_class_code", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_error_cd", Types.VARCHAR));
            declareParameter(new SqlOutParameter("p_o_v_port_imdg_list", OracleTypes.CURSOR,rowMapper));
            compile();
        }
        private String savePortImdgSetup(String portImdgType,String terminalDepotCd,IjsCostImdgPortClassVO portImdgSetup) {
	        Map outMap = new HashMap();
	        Map inParameters = new HashMap();
	        inParameters.put("p_i_v_action_cd" , RutDatabase.stringToDb(portImdgSetup.getAction()).toUpperCase());
	        inParameters.put("p_i_v_terminal_depot_cd" , RutDatabase.stringToDb(terminalDepotCd).toUpperCase());
	        inParameters.put("p_i_v_old_port_imdg_cd" , RutDatabase.stringToDb(portImdgSetup.getOldPortImdgCode()).toUpperCase());
	        inParameters.put("p_i_v_port_imdg_cd" , RutDatabase.stringToDb(portImdgSetup.getPortImdgCode()).toUpperCase());
	        inParameters.put("p_i_v_port_imdg_class_cd" , RutDatabase.stringToDb(PORT.equals(portImdgType)?portImdgSetup.getPortClass():portImdgSetup.getImdgClass()));
	        inParameters.put("p_i_v_port_imdg_type" , RutDatabase.stringToDb(portImdgType).toUpperCase());
	        inParameters.put("p_i_v_include_unno" , RutDatabase.stringToDb(portImdgSetup.getIncludeUnno()).toUpperCase());
	        inParameters.put("p_i_v_exclude_unno" , RutDatabase.stringToDb(portImdgSetup.getExcludeUnno()).toUpperCase());
	        inParameters.put("p_i_v_port_imdg_seq_no" , portImdgSetup.getPortImdgSeqNo());
	        //inParameters.put("p_i_v_imdg_seq_no" , portImdgSetup.getPortImdgSeqNo());
	        
	        outMap = execute(inParameters);
	        return (String) outMap.get("p_o_v_error_cd");
        }
        private List<IjsCostImdgPortClassVO>  searchPortImdgSetup(String terminalDepotCd,String type,String portImdgCodes) {
	        Map outMap = new HashMap();
	        Map inParameters = new HashMap();
	        inParameters.put("p_i_v_terminal_depot_cd" , RutDatabase.stringToDb(terminalDepotCd).toUpperCase());
	        inParameters.put("p_i_v_port_imdg_type" , RutDatabase.stringToDb(type).toUpperCase());
	        inParameters.put("p_i_v_port_imdg_class_code" , RutDatabase.stringToDb(portImdgCodes).toUpperCase());
	        outMap = execute(inParameters);
	        return (List<IjsCostImdgPortClassVO> ) outMap.get("p_o_v_port_imdg_list");
	    }
    } 
		private class IjsPortImdgSetupRowMapper implements RowMapper {

        public Object mapRow(ResultSet resultSet, int i) {
        	IjsCostImdgPortClassVO portImdgVO = new IjsCostImdgPortClassVO();
        	int usedCount=0;
        	String portImdgType=null;
            try {
            	portImdgType=resultSet.getString("PORT_IMDG_TYPE");
            	if(PORT.equals(portImdgType)){
            		portImdgVO.setPortClass(resultSet.getString("PORT_CLASS"));
            		portImdgVO.setPortImdgSeqNo(resultSet.getInt("PORT_SEQ_NO"));
            	}else{
            		portImdgVO.setImdgClass(resultSet.getString("IMDG_CLASS"));
            		portImdgVO.setPortImdgSeqNo(resultSet.getInt("IMDG_SEQ_NO"));
            	}
            	
            	usedCount=resultSet.getInt("USED_COUNT");
            	portImdgVO.setUsedByContract(usedCount>1?true:false);
            	portImdgVO.setPortImdgCode(resultSet.getString("PORT_IMDG_CD"));
            	portImdgVO.setOldPortImdgCode(resultSet.getString("PORT_IMDG_CD"));
            	portImdgVO.setIncludeUnno(resultSet.getString("INCLUDE_UNNO"));
            	portImdgVO.setExcludeUnno(resultSet.getString("EXCLUDE_UNNO"));
            } catch (SQLException e) {
                e.printStackTrace();
           }
            
            return portImdgVO;
        }
    }
		@Override
		public IjsCostRateSetupVO searchOOGSetup(String terminalDepotCd)
				throws IJSException {
			IjsCostRateSetupVO oogSetupVO=new IjsCostRateSetupVO();
			oogSetupVO.setOogSetUpList(ijsOOGSetupSearch.searchOOGSetup(terminalDepotCd));
			return oogSetupVO;
		}
		@Override
		public IjsCostRateSetupVO searchPortImdgSetup(String terminalDepotCd,String portImdgType)
				throws IJSException {
			IjsCostRateSetupVO portImdgSetupVO=new IjsCostRateSetupVO();
			if(PORT.equals(portImdgType)){
				portImdgSetupVO.setPortList(ijsPortImdgSetupSearch.searchPortImdgSetup(terminalDepotCd,portImdgType,null));
			}else{
				portImdgSetupVO.setImdgList(ijsPortImdgSetupSearch.searchPortImdgSetup(terminalDepotCd,portImdgType,null));
			}
			
			return portImdgSetupVO;
		}
		protected class IjsOOGSetupCodeProcedure extends StoredProcedure {
		        private static final String SQL_CNTR_OOG_CODE_SEARCH =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_OOG_SETUP_CODE";
		        IjsOOGSetupCodeProcedure(JdbcTemplate jdbcTemplate , RowMapper rowMapper) {
		            super(jdbcTemplate, SQL_CNTR_OOG_CODE_SEARCH);
		            declareParameter(new SqlInOutParameter("p_i_v_terminal_depot_cd", Types.VARCHAR));
		            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
		            declareParameter(new SqlOutParameter("p_o_v_oog_list", 
		                                                 OracleTypes.CURSOR, 
		                                                 rowMapper));
		            compile();
		        }
		        private List<String> getOOGSetupCode(String terminalDepotCd) {
		        Map outMap = new HashMap();
		        Map inParameters = new HashMap();
		        inParameters.put("p_i_v_terminal_depot_cd" , RutDatabase.stringToDb(terminalDepotCd).toUpperCase());
		        outMap = execute(inParameters);
		        
		        return (List<String>) outMap.get("p_o_v_oog_list");
		        }
		        
		    }
		    private class IjsOOGSetupCodeRowMapper implements RowMapper {

		        public Object mapRow(ResultSet resultSet, int i) {
		            String oogCode = new String();
		            try {
		            	oogCode=resultSet.getString("OOG_SETUP_CODE");
		            } catch (SQLException e) {
		                e.printStackTrace();
		           }
		            
		            return oogCode;
		        }
	   }
	    protected class IjsPortImdgSetupCodeProcedure extends StoredProcedure {
	        private static final String SQL_IJS_CNTR_PORT_IMDG_SETUP_CODE =  "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_PORT_IMDG_SETUP_CODE";
	        IjsPortImdgSetupCodeProcedure(JdbcTemplate jdbcTemplate , RowMapper rowMapper) {
	            super(jdbcTemplate, SQL_IJS_CNTR_PORT_IMDG_SETUP_CODE);
	            declareParameter(new SqlInOutParameter("p_i_v_terminal_depot_cd", Types.VARCHAR));
	            declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
	            declareParameter(new SqlOutParameter("p_o_v_port_cd_list",OracleTypes.CURSOR, rowMapper));
	            declareParameter(new SqlOutParameter("p_o_v_imdg_cd_list",OracleTypes.CURSOR, rowMapper));
	            compile();
	        }
	        private IjsCostRateSetupVO getPortImdgSetupCode(String terminalDepotCd) {
	        Map outMap = new HashMap();
	        Map inParameters = new HashMap();
	        inParameters.put("p_i_v_terminal_depot_cd" , RutDatabase.stringToDb(terminalDepotCd).toUpperCase());
	        outMap = execute(inParameters);
	        IjsCostRateSetupVO setupVo=new IjsCostRateSetupVO();
	        setupVo.setPortCodeList((List<String>) outMap.get("p_o_v_port_cd_list"));
	        setupVo.setImdgCodeList((List<String>) outMap.get("p_o_v_imdg_cd_list"));
	        return setupVo;
	        }
	        
	    }
	    private class IjsPortImdgSetupCodeRowMapper implements RowMapper {

	        public Object mapRow(ResultSet resultSet, int i) {
	            String portImdgCode = new String();
	            try {
	            	portImdgCode=resultSet.getString("PORT_IMDG_CD");
	            } catch (SQLException e) {
	                e.printStackTrace();
	           }
	            
	            return portImdgCode;
	        }
   }
		  //CR#04 END
	   
}
