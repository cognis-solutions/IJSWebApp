/*-----------------------------------------------------------------------------------------------------------

        IjsHelper.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            Helper utility for IJS
02 07/09/17  NIIT       IJS            Added method for converting date formate
03 05/10/17  NIIT       IJS            Added COST RATE related code conversions
04 20/10/17  NIIT       IJS            Added BILL RATE related code conversions
05 20/10/17  NIIT       IJS            Added BILL RATE related code conversions
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.common;

import com.rclgroup.dolphin.ijs.web.constants.ContractStatus;
import com.rclgroup.dolphin.ijs.web.constants.CustomerType;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsEqCatalog;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsRateBasis;
import com.rclgroup.dolphin.ijs.web.constants.IjsRateCalcMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsRateStatus;
import com.rclgroup.dolphin.ijs.web.constants.IjsSpHandling;
import com.rclgroup.dolphin.ijs.web.constants.IjsUserRole;
import com.rclgroup.dolphin.ijs.web.constants.IjsVendorType;
import com.rclgroup.dolphin.ijs.web.constants.LocationType;
import com.rclgroup.dolphin.ijs.web.constants.TransportMode;
import com.rclgroup.dolphin.ijs.web.constants.jo.IjsJobOrderTypeCodes;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractOogSetupVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractSplCostByBlOrBkVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsCostImdgPortClassVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class IjsHelper {
    // O1## BEGIN

    public static ApplicationContext getApplicationContext(ServletContext servletContext) {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    }

    public static String getLocationType(String locCode){
       if(LocationType.DEPOT.getLocCode().equals(locCode)){
           return LocationType.DEPOT.getLocType(); 
       }else if(LocationType.DOOR.getLocCode().equals(locCode)){
           return LocationType.DOOR.getLocType();
       }else if(LocationType.HAULAGE.getLocCode().equals(locCode)){
           return LocationType.HAULAGE.getLocType();
       }else if(LocationType.TERMINAL.getLocCode().equals(locCode)){
           return LocationType.TERMINAL.getLocType();
       }
          
        return null;
    }
    
    public static String getLocationCode(String locType){
       if(LocationType.DEPOT.getLocType().equals(locType)){
           return LocationType.DEPOT.getLocCode(); 
       }else if(LocationType.DOOR.getLocType().equals(locType)){
           return LocationType.DOOR.getLocCode();
       }else if(LocationType.HAULAGE.getLocType().equals(locType)){
           return LocationType.HAULAGE.getLocCode();
       }else if(LocationType.TERMINAL.getLocType().equals(locType)){
           return LocationType.TERMINAL.getLocCode();
       }
          
        return null;
    }
    
    public static String getTransMode(String transCode){
    
        if(TransportMode.BARGE.getTransCode().equals(transCode)){
            return TransportMode.BARGE.getTransMode(); 
        }else if(TransportMode.FEEDER.getTransCode().equals(transCode)){
            return TransportMode.FEEDER.getTransMode();
        }else if(TransportMode.RAIL.getTransCode().equals(transCode)){
            return TransportMode.RAIL.getTransMode();
        }else if(TransportMode.TRUCK.getTransCode().equals(transCode)){
            return TransportMode.TRUCK.getTransMode();
        }
        return null;
    }
    
    public static String getTransCode(String transCode){
    
        if(TransportMode.BARGE.getTransMode().equalsIgnoreCase(transCode)){
            return TransportMode.BARGE.getTransCode(); 
        }else if(TransportMode.FEEDER.getTransMode().equalsIgnoreCase(transCode)){
            return TransportMode.FEEDER.getTransCode();
        }else if(TransportMode.RAIL.getTransMode().equalsIgnoreCase(transCode)){
            return TransportMode.RAIL.getTransCode();
        }else if(TransportMode.TRUCK.getTransMode().equalsIgnoreCase(transCode)){
            return TransportMode.TRUCK.getTransCode();
        }
        return null;
    }
    
    public static String getTransCodeX(String transCode){
        
        if(TransportMode.BARGE.getTransMode().equalsIgnoreCase(transCode)){
            return TransportMode.BARGE.getTransCodeX(); 
        }else if(TransportMode.FEEDER.getTransMode().equalsIgnoreCase(transCode)){
            return TransportMode.FEEDER.getTransCodeX();
        }else if(TransportMode.RAIL.getTransMode().equalsIgnoreCase(transCode)){
            return TransportMode.RAIL.getTransCodeX();
        }else if(TransportMode.TRUCK.getTransMode().equalsIgnoreCase(transCode)){
            return TransportMode.TRUCK.getTransCodeX();
        }
        return null;
    }
    
    public static String getContractStatus(String statusCode){
    
        if(ContractStatus.ACTIVE.getStatusCode().equals(statusCode)){
            return ContractStatus.ACTIVE.getStatus(); 
        }else if(ContractStatus.ENTRY.getStatusCode().equals(statusCode)){
            return ContractStatus.ENTRY.getStatus();
        }else if(ContractStatus.PENDING.getStatusCode().equals(statusCode)){
            return ContractStatus.PENDING.getStatus();
        }else if(ContractStatus.SUSPENDED.getStatusCode().equals(statusCode)){
            return ContractStatus.SUSPENDED.getStatus();
        }
        return null;
    }
    public static String getContractStatusCode(String status){
    
        if(ContractStatus.ACTIVE.getStatus().equals(status)){
            return ContractStatus.ACTIVE.getStatusCode(); 
        }else if(ContractStatus.ENTRY.getStatus().equals(status)){
            return ContractStatus.ENTRY.getStatusCode();
        }else if(ContractStatus.PENDING.getStatus().equals(status)){
            return ContractStatus.PENDING.getStatusCode();
        }else if(ContractStatus.SUSPENDED.getStatus().equals(status)){
            return ContractStatus.SUSPENDED.getStatusCode();
        }
        return null;
    }
    //##01 END
    //## 02 BEING
    public static String getDateFromRange(String dateRange,String dateType){
        String[] dates = null;
        if(dateRange!=null){
            dates = dateRange.split("-");
            if("START_DATE".equals(dateType)){
                return dates[0];
            }else{
                return dates[1];
            }  
        }
       return null;
    }
    /**
     * getDateFormat method for converting date into dd/mm/yyyy formate.
     * @param date
     * @return
     */
    public static String getDateFormat(String date){
        String[] dateFragments = null;
        if(date!=null){
            // date will come in dd/mm/yyyy format and db is expecting in the format of yyyy/mm/dd
            dateFragments = date.split("/");
            if(dateFragments.length == 3) {
                return dateFragments[0] + "" + dateFragments[1] + "" + dateFragments[2];
            }
        }
       return null;
    }
    /**
     * getActionType method for getting action code.
     * @param action
     * @return
     */
    public static String getActionType(String action) {
        if (IjsActionMethod.NEW.getAction().equals(action)) {
            return IjsActionCode.NEW.getActionCode();       
        }
        else if (IjsActionMethod.EDIT.getAction().equals(action)) {
            return IjsActionCode.EDIT.getActionCode();       
        }
        else if (IjsActionMethod.COPY.getAction().equals(action)) {
            return IjsActionCode.COPY.getActionCode();       
        }
        else if (IjsActionMethod.NEW_CHANGE_PRIORITY.getAction().equals(action)) {
            return IjsActionCode.NEWCHANGE.getActionCode();       
        }
        else if (IjsActionMethod.EDIT_CHANGE_PRIORITY.getAction().equals(action)) {
            return IjsActionCode.EDITCHANGE.getActionCode();       
        }
        else if (IjsActionMethod.COPY_CHANGE_PRIORITY.getAction().equals(action)) {
            return IjsActionCode.NEWCHANGE.getActionCode();       
        }
        
        //##03 BEGIN
        else if (IjsActionMethod.APPROVE_COST_RATE.getAction().equals(action) ||
            IjsActionMethod.APPROVE_BILLING_RATE.getAction().equals(action)) {
            return IjsActionCode.COST_APPROVE.getActionCode();       
        }
        else if (IjsActionMethod.DELETE_COST_RATE.getAction().equals(action) ||
            IjsActionMethod.DELETE_BILLING_RATE.getAction().equals(action)) {
            return IjsActionCode.COST_DELETE.getActionCode();       
        }
        else if (IjsActionMethod.REJECT_COST_RATE.getAction().equals(action) ||
            IjsActionMethod.REJECT_BILLING_RATE.getAction().equals(action) ) {
            return IjsActionCode.COST_REJECT.getActionCode();       
        }
        else if (IjsActionMethod.ADD_COST_RATE.getAction().equals(action) || 
            IjsActionMethod.ADD_BILLING_RATE.getAction().equals(action)) {
            return IjsActionCode.NEW.getActionCode();       
        }
        else if (IjsActionMethod.EDIT_COST_RATE.getAction().equals(action) || 
            IjsActionMethod.EDIT_BILLING_RATE.getAction().equals(action)) {
            return IjsActionCode.EDIT.getActionCode();       
        }
        //##03 END
        return null;
    }
    //## O2 END
    //##03 BEGIN
    /**
     * getRateBaseCode method for returning the rate base code
     * @param rateBasis
     * @return
     */
    public static String getRateBaseValue(String rateBasis) {
        if (IjsRateBasis.Size_Type.getRateBasisCode().equals(rateBasis)) {
            return IjsRateBasis.Size_Type.getRateBasisValue();    
        }
        else if (IjsRateBasis.Lump_Sum.getRateBasisCode().equals(rateBasis)) {
            return IjsRateBasis.Lump_Sum.getRateBasisValue();    
        }
        else if (IjsRateBasis.BKG_BL.getRateBasisCode().equals(rateBasis)) {
            return IjsRateBasis.BKG_BL.getRateBasisValue();    
        }
        return null;
    }
    
    public static String getRateBaseCd(String rateBasisValue) {
        if (IjsRateBasis.Size_Type.getRateBasisValue().equals(rateBasisValue)) {
            return IjsRateBasis.Size_Type.getRateBasisCode();    
        }
        else if (IjsRateBasis.Lump_Sum.getRateBasisValue().equals(rateBasisValue)) {
            return IjsRateBasis.Lump_Sum.getRateBasisCode();    
        }
        else if (IjsRateBasis.BKG_BL.getRateBasisValue().equals(rateBasisValue)) {
            return IjsRateBasis.BKG_BL.getRateBasisCode();    
        }
        return null;
    }
    /**
     * getRateStatus method for returning the rate status based on rate status code
     * @param rateStatusCode
     * @return
     */
    public static String getRateStatus(String rateStatusCode) {
        if (IjsRateStatus.APPROVED.getRateStatusCode().equals(rateStatusCode)) {
            return IjsRateStatus.APPROVED.getRateStatus();
        }
        else if (IjsRateStatus.OPEN.getRateStatusCode().equals(rateStatusCode)) {
            return IjsRateStatus.OPEN.getRateStatus();
        }
        else if (IjsRateStatus.PENDING.getRateStatusCode().equals(rateStatusCode)) {
            return IjsRateStatus.PENDING.getRateStatus();
        }
        else if (IjsRateStatus.REJECTED.getRateStatusCode().equals(rateStatusCode)) {
            return IjsRateStatus.REJECTED.getRateStatus();
        }
        return null;
    }
    
    public static String getRateStatusCode(String rateStatus) {
        if (IjsRateStatus.APPROVED.getRateStatus().equals(rateStatus)) {
            return IjsRateStatus.APPROVED.getRateStatusCode();
        }
        else if (IjsRateStatus.OPEN.getRateStatus().equals(rateStatus)) {
            return IjsRateStatus.OPEN.getRateStatusCode();
        }
        else if (IjsRateStatus.PENDING.getRateStatus().equals(rateStatus)) {
            return IjsRateStatus.PENDING.getRateStatusCode();
        }
        else if (IjsRateStatus.REJECTED.getRateStatus().equals(rateStatus)) {
            return IjsRateStatus.REJECTED.getRateStatusCode();
        }
        return null;
    }
    /**
     * getEqCatelog method for returning eqCatelog Value based on eqCatelog code.
     * @param eqCatelogCode
     * @return
     */
    public static String getEqCatelog(String eqCatelogCode) {
        if (IjsEqCatalog.Chassis_trailer.getCatalogCode().equals(eqCatelogCode)) {
            return IjsEqCatalog.Chassis_trailer.getCatalogValue();
        }
        else if (IjsEqCatalog.Box.getCatalogCode().equals(eqCatelogCode)) {
            return IjsEqCatalog.Box.getCatalogValue();
        }
        else if (IjsEqCatalog.Genset.getCatalogCode().equals(eqCatelogCode)) {
            return IjsEqCatalog.Genset.getCatalogValue();
        }
        else if (IjsEqCatalog.ASTERISK.getCatalogCode().equals(eqCatelogCode)) {
            return IjsEqCatalog.ASTERISK.getCatalogValue();
        }
        return null;
    }
    
    public static String getEqCatelogCd(String eqCatelogValue) {
        if (IjsEqCatalog.Chassis_trailer.getCatalogValue().equals(eqCatelogValue)) {
            return IjsEqCatalog.Chassis_trailer.getCatalogCode();
        }
        else if (IjsEqCatalog.Box.getCatalogValue().equals(eqCatelogValue)) {
            return IjsEqCatalog.Box.getCatalogCode();
        }
        else if (IjsEqCatalog.Genset.getCatalogValue().equals(eqCatelogValue)) {
            return IjsEqCatalog.Genset.getCatalogCode();
        }
        else if (IjsEqCatalog.ASTERISK.getCatalogValue().equals(eqCatelogValue)) {
            return IjsEqCatalog.ASTERISK.getCatalogCode();
        }
        return null;
    }
    /**
     * getClacMethodType method for returning calculation method type based on value
     * @param calcMethodValue
     * @return
     */
    public static String getClacMethodType(String calcMethodValue) {
        if (IjsRateCalcMethod.Fix_Amount.getCalcMethodValue().equals(calcMethodValue)) {
            return IjsRateCalcMethod.Fix_Amount.getCalcMethodType();
        }
        else if (IjsRateCalcMethod.Fix_UOM.getCalcMethodValue().equals(calcMethodValue)) {
            return IjsRateCalcMethod.Fix_UOM.getCalcMethodType();
        }
        else if (IjsRateCalcMethod.Tier_Amount.getCalcMethodValue().equals(calcMethodValue)) {
            return IjsRateCalcMethod.Tier_Amount.getCalcMethodType();
        }
        else if (IjsRateCalcMethod.Tier_UOM.getCalcMethodValue().equals(calcMethodValue)) {
            return IjsRateCalcMethod.Tier_UOM.getCalcMethodType();
        }
        return null;
    }
    
    public static String getClacMethodCode(String calcMethodType) {
        if (IjsRateCalcMethod.Fix_Amount.getCalcMethodType().equals(calcMethodType)) {
            return IjsRateCalcMethod.Fix_Amount.getCalcMethodValue();
        }
        else if (IjsRateCalcMethod.Fix_UOM.getCalcMethodType().equals(calcMethodType)) {
            return IjsRateCalcMethod.Fix_UOM.getCalcMethodValue();
        }
        else if (IjsRateCalcMethod.Tier_Amount.getCalcMethodType().equals(calcMethodType)) {
            return IjsRateCalcMethod.Tier_Amount.getCalcMethodValue();
        }
        else if (IjsRateCalcMethod.Tier_UOM.getCalcMethodType().equals(calcMethodType)) {
            return IjsRateCalcMethod.Tier_UOM.getCalcMethodValue();
        }
        return null;
    }
    
    /**
     * getVendorValue method for returning vendor value
     * @param vendorCode
     * @return
     */
    public static String getVendorValue(String vendorCode) {
        if (IjsVendorType.NON_ASSOCIATE.getVendorCode().equals(vendorCode)) {
            return IjsVendorType.NON_ASSOCIATE.getVendorValue();
        }
        else if (IjsVendorType.ASSOCIATES.getVendorCode().equals(vendorCode)) {
            return IjsVendorType.ASSOCIATES.getVendorValue();
        }
        else if (IjsVendorType.OTHERS.getVendorCode().equals(vendorCode)) {
            return IjsVendorType.OTHERS.getVendorValue();
        }
        else if (IjsVendorType.PARTNERS.getVendorCode().equals(vendorCode)) {
            return IjsVendorType.PARTNERS.getVendorValue();
        }
        else if (IjsVendorType.SUSBSIDIARY.getVendorCode().equals(vendorCode)) {
            return IjsVendorType.SUSBSIDIARY.getVendorValue();
        }
        else if (IjsVendorType.THIRD_PARTY.getVendorCode().equals(vendorCode)) {
            return IjsVendorType.THIRD_PARTY.getVendorValue();
        }
        else if (IjsVendorType.NON_SUSIDIARY.getVendorCode().equals(vendorCode)) {
            return IjsVendorType.NON_SUSIDIARY.getVendorValue();
        }
        return null;
    }
    
    public static String getVendorTypeCd(String vendorTypeValue) {
        if (IjsVendorType.NON_ASSOCIATE.getVendorValue().equals(vendorTypeValue)) {
            return IjsVendorType.NON_ASSOCIATE.getVendorCode();
        }
        else if (IjsVendorType.ASSOCIATES.getVendorValue().equals(vendorTypeValue)) {
            return IjsVendorType.ASSOCIATES.getVendorCode();
        }
        else if (IjsVendorType.OTHERS.getVendorValue().equals(vendorTypeValue)) {
            return IjsVendorType.OTHERS.getVendorCode();
        }
        else if (IjsVendorType.PARTNERS.getVendorValue().equals(vendorTypeValue)) {
            return IjsVendorType.PARTNERS.getVendorCode();
        }
        else if (IjsVendorType.SUSBSIDIARY.getVendorValue().equals(vendorTypeValue)) {
            return IjsVendorType.SUSBSIDIARY.getVendorCode();
        }
        else if (IjsVendorType.THIRD_PARTY.getVendorCode().equals(vendorTypeValue)) {
            return IjsVendorType.THIRD_PARTY.getVendorCode();
        }
        else if (IjsVendorType.NON_SUSIDIARY.getVendorCode().equals(vendorTypeValue)) {
            return IjsVendorType.NON_SUSIDIARY.getVendorCode();
        }
        return null;
    }
    //##03 END
     //##04 BEGIN
     public static String convertOogSetup(List<IjsContractOogSetupVO> oogSetUpList) {
         StringBuffer oogSetUp = new StringBuffer();
         if (oogSetUpList != null) {
             for (IjsContractOogSetupVO oogSetupVO : oogSetUpList) {
                 oogSetUp.append(oogSetupVO.getOogSetupCode()).append(":");
                 oogSetUp.append("*".equals(oogSetupVO.getMinOverHeight())?"-1":oogSetupVO.getMinOverHeight()).append(":");
                 oogSetUp.append("*".equals(oogSetupVO.getMaxOverHeight())?"-1":oogSetupVO.getMaxOverHeight()).append(":");
                 oogSetUp.append("*".equals(oogSetupVO.getMinOverLength())?"-1":oogSetupVO.getMinOverLength()).append(":");
                 oogSetUp.append("*".equals(oogSetupVO.getMaxOverLength())?"-1":oogSetupVO.getMaxOverLength()).append(":");
                 oogSetUp.append("*".equals(oogSetupVO.getMinOverWidth())?"-1":oogSetupVO.getMinOverWidth()).append(":");
                 oogSetUp.append("*".equals(oogSetupVO.getMaxOverWidth())?"-1":oogSetupVO.getMaxOverWidth()).append(":");
                 
                 oogSetUp.append(",");
                 //return oogSetUp.toString().substring(0,oogSetUp.toString().length() - 1);
             }
         }
         int lastIndex=oogSetUp.lastIndexOf(",");
        
        
         return  oogSetUp.substring(0,lastIndex-1);
     }
     //##04 END
     //##05 BEGIN
     public static String getJOType(String joCode) {
         if (IjsJobOrderTypeCodes.ADHOC_EMPTY.getJoCode().equals(joCode)) {
             return IjsJobOrderTypeCodes.ADHOC_EMPTY.getJoType();
         }
         if (IjsJobOrderTypeCodes.ADHOC_LADEN.getJoCode().equals(joCode)) {
             return IjsJobOrderTypeCodes.ADHOC_LADEN.getJoType();
         }
         if (IjsJobOrderTypeCodes.EXPORT.getJoCode().equals(joCode)) {
             return IjsJobOrderTypeCodes.EXPORT.getJoType();
         }
         if (IjsJobOrderTypeCodes.IMPORT.getJoCode().equals(joCode)) {
             return IjsJobOrderTypeCodes.IMPORT.getJoType();
         }
         if (IjsJobOrderTypeCodes.INTER_TERMINAL.getJoCode().equals(joCode)) {
             return IjsJobOrderTypeCodes.INTER_TERMINAL.getJoType();
         }
         if (IjsJobOrderTypeCodes.SEA_LEG.getJoCode().equals(joCode)) {
             return IjsJobOrderTypeCodes.SEA_LEG.getJoType();
         }
         return null;
         
     }
     
    public static String getJOCode(String joType) {
        if (IjsJobOrderTypeCodes.ADHOC_EMPTY.getJoType().equals(joType)) {
            return IjsJobOrderTypeCodes.ADHOC_EMPTY.getJoCode();
        }
        if (IjsJobOrderTypeCodes.ADHOC_LADEN.getJoType().equals(joType)) {
            return IjsJobOrderTypeCodes.ADHOC_LADEN.getJoCode();
        }
        if (IjsJobOrderTypeCodes.EXPORT.getJoType().equals(joType)) {
            return IjsJobOrderTypeCodes.EXPORT.getJoCode();
        }
        if (IjsJobOrderTypeCodes.IMPORT.getJoType().equals(joType)) {
            return IjsJobOrderTypeCodes.IMPORT.getJoCode();
        }
        if (IjsJobOrderTypeCodes.INTER_TERMINAL.getJoType().equals(joType)) {
            return IjsJobOrderTypeCodes.INTER_TERMINAL.getJoCode();
        }
        if (IjsJobOrderTypeCodes.SEA_LEG.getJoType().equals(joType)) {
            return IjsJobOrderTypeCodes.SEA_LEG.getJoCode();
        }
        return null;
        
    }
    
    public static String getAuthorizationType(String authorizationCode){
       if(IjsUserRole.READ_ONLY.getAuthorizeCode().equals(authorizationCode)){
           return IjsUserRole.READ_ONLY.getAuthorizeDesc(); 
       }else if(IjsUserRole.MODIFY_ONLY.getAuthorizeCode().equals(authorizationCode)){
           return IjsUserRole.MODIFY_ONLY.getAuthorizeDesc();
       }else if(IjsUserRole.GLOBAL.getAuthorizeCode().equals(authorizationCode)){
           return IjsUserRole.GLOBAL.getAuthorizeDesc();
       }
          
        return null;
    }
    
    public static String convertSpCost(List<IjsContractSplCostByBlOrBkVO> spCostList) {
        StringBuffer spCost = new StringBuffer();
        if (spCostList != null) {
            for (IjsContractSplCostByBlOrBkVO spCostListVO : spCostList) {
                spCost.append(spCostListVO.getBkgBlNum()).append(":");
                spCost.append(spCostListVO.getCharge()).append(",");
            }
        }
        return spCost.substring(0,spCost.length() - 1);
    }
    
    public static String convertListToString(List<String> eqList) {
    
     if(eqList!=null && eqList.size()==0){
        return "**";
     }
     else{
     StringBuffer eqBuff = new StringBuffer();
        if (eqList != null) {
            for (String localString : eqList) {
                eqBuff.append(localString.equalsIgnoreCase("All")?"**":localString).append(",");
                
            }
        }
        return eqBuff.substring(0,eqBuff.length() - 1);
        }
    }
    
    public static List<String> convertStringToList(String InpStringComma) {
    List<String> retList = new ArrayList<String>();
     
     if (InpStringComma != null) {
                retList = new ArrayList<String>(Arrays.asList(InpStringComma.split(",")));
        }
        return retList;
        
    }
    
    public static String getCustomerTypeCode(String custType){
       if(CustomerType.SHIPPER.getCustType().equalsIgnoreCase(custType)){
           return CustomerType.SHIPPER.getCustCode(); 
       }else if(CustomerType.CONSIGNEE.getCustType().equalsIgnoreCase(custType)){
           return CustomerType.CONSIGNEE.getCustCode(); 
       }
          
        return null;
    }
    
    public static String getCustomerType(String custCode){
       if(CustomerType.SHIPPER.getCustCode().equalsIgnoreCase(custCode)){
           return CustomerType.SHIPPER.getCustType(); 
       }else if(CustomerType.CONSIGNEE.getCustCode().equalsIgnoreCase(custCode)){
           return CustomerType.CONSIGNEE.getCustType(); 
       }
          
        return null;
    }
    
    public static String convertImdgPortClass(List<IjsCostImdgPortClassVO> imDgPostClassList) {
        StringBuffer imdgPort = new StringBuffer();
        if (imDgPostClassList != null) {
            for (IjsCostImdgPortClassVO imdgPortClassVO : imDgPostClassList) {
                imdgPort.append(imdgPortClassVO.getImdgClass()).append(":");
                imdgPort.append(imdgPortClassVO.getIncludeUnno()).append(":");
                imdgPort.append(imdgPortClassVO.getExcludeUnno()).append(":");
                
                
                imdgPort.append(",");
                //return oogSetUp.toString().substring(0,oogSetUp.toString().length() - 1);
            }
        }
        int lastIndex=imdgPort.lastIndexOf(",");
        return  imdgPort.substring(0,lastIndex-1);
    }
    
    public static String getSpHandlingValue(String spHandlingCd){
      
       if(IjsSpHandling.BBK.getSpHandlingCd().equals(spHandlingCd)){
           return IjsSpHandling.BBK.getSpHandlingValue();
       }else if(IjsSpHandling.NORMAL.getSpHandlingCd().equals(spHandlingCd)){
           return IjsSpHandling.NORMAL.getSpHandlingValue();
       }else if(IjsSpHandling.REEFER_DG.getSpHandlingCd().equals(spHandlingCd)){
           return IjsSpHandling.REEFER_DG.getSpHandlingValue();
       }else if(IjsSpHandling.OOG_DG.getSpHandlingCd().equals(spHandlingCd)){
           return IjsSpHandling.OOG_DG.getSpHandlingValue();
       }else if(IjsSpHandling.RF.getSpHandlingCd().equals(spHandlingCd)){
           return IjsSpHandling.RF.getSpHandlingValue();
       }else if(IjsSpHandling.OOG.getSpHandlingCd().equals(spHandlingCd)){
           return IjsSpHandling.OOG.getSpHandlingValue();
       }else if(IjsSpHandling.DG.getSpHandlingCd().equals(spHandlingCd)){
           return IjsSpHandling.DG.getSpHandlingValue();
       }else if(IjsSpHandling.DOOR_AJAR.getSpHandlingCd().equals(spHandlingCd)){
           return IjsSpHandling.DOOR_AJAR.getSpHandlingValue();
       }else if(IjsSpHandling.OPEN_DOOR.getSpHandlingCd().equals(spHandlingCd)){
           return IjsSpHandling.OPEN_DOOR.getSpHandlingValue();
       }else{
           return null;
       }
                
    }
    
    public static String getSpHandlingCode(String spHandlingVal){
        
        if(IjsSpHandling.BBK.getSpHandlingValue().equals(spHandlingVal)){
            return IjsSpHandling.BBK.getSpHandlingCd();
        }else if(IjsSpHandling.NORMAL.getSpHandlingValue().equals(spHandlingVal)){
            return IjsSpHandling.NORMAL.getSpHandlingCd();
        }else if(IjsSpHandling.REEFER_DG.getSpHandlingValue().equals(spHandlingVal)){
            return IjsSpHandling.REEFER_DG.getSpHandlingCd();
        }else if(IjsSpHandling.OOG_DG.getSpHandlingValue().equals(spHandlingVal)){
            return IjsSpHandling.OOG_DG.getSpHandlingCd();
        }else if(IjsSpHandling.RF.getSpHandlingValue().equals(spHandlingVal)){
            return IjsSpHandling.RF.getSpHandlingCd();
        }else if(IjsSpHandling.OOG.getSpHandlingValue().equals(spHandlingVal)){
            return IjsSpHandling.OOG.getSpHandlingCd();
        }else if(IjsSpHandling.DG.getSpHandlingValue().equals(spHandlingVal)){
            return IjsSpHandling.DG.getSpHandlingCd();
        }else if(IjsSpHandling.DOOR_AJAR.getSpHandlingValue().equals(spHandlingVal)){
            return IjsSpHandling.DOOR_AJAR.getSpHandlingCd();
        }else if(IjsSpHandling.OPEN_DOOR.getSpHandlingValue().equals(spHandlingVal)){
            return IjsSpHandling.OPEN_DOOR.getSpHandlingCd();
        }else{
            return null;
        }
                 
     }
    
    public static void validateRequest(IjsRateVO ijsRateVO) throws IJSException {
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
        
        validateDate(ijsRateVO.getStartDate(), ijsRateVO.getEndDate());  
    }
    
    public static void validateDate(String startDate, String endDate) throws IJSException {
        //startDate = ijsContractVO.getStartDate();
        //String endDate = ijsContractVO.getEndDate();
        Date sDate = null;
        Date eDate = null;
        Date currentDate=null;
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 

        try {
            sDate = sdf.parse(startDate);
            eDate = sdf.parse(endDate);
            currentDate=sdf.parse(sdf.format(new Date()));
            if (sDate.after(eDate)) {
                throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10026.getErrorCode());
            }
            if (currentDate.after(eDate)) {
                throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10032.getErrorCode());
            }
            
        } catch (ParseException e) {
            // TO-DO
            throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10045.getErrorCode());
        }
    }
    
    public static String getEqTypWithComma(List<String> eqTypes){
    	String lEqTypes=new String();
    	if(eqTypes!=null){
    		for(String eqType:eqTypes){
    			lEqTypes=eqType.concat(",");
    		}
    		lEqTypes=lEqTypes.substring(0, lEqTypes.lastIndexOf(","));
    		return lEqTypes;
    	}
    	return null;
    }
    
}


