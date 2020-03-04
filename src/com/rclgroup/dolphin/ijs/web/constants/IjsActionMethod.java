/*-----------------------------------------------------------------------------------------------------------
IjsActionMethod.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            keeps ijs actions 
02 07/09/17  NIIT       IJS            added actions for lookup's
03 21/09/17  NIIT       IJS            added actions for vendor lookup, costRateSearch
04 05/10/17  NIIT       IJS            added actions reject,edit,add,approve,delete for cost rate section
05 20/10/17  NIIT       IJS            added actions reject,edit,add,approve,delete for billing rate section
06 02/11/17  NIIT       IJS            added actions service vessel voyage direction popup
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsActionMethod {
    // ## O1 BEGIN
    LOAD("load"),SEARCH("search"),
    //## O1 END
    
    NEW("new"),//## 02
    //## 04 BEGIN
    EDIT("edit"),
    DELETE("delete"),
    COPY("copy"),
    SUSPEND_CONTRACT("suspend"),
    COMPARE("compare"),
    CONTRACT_HISTORY("history"),
    GPP("genPortPair"),
    VENDOR_DETAILS("vendor_details"),
    // ## 04 END
    // ## 02 BEGIN
    SEARCH_TERMINAL("Terminal"),
    SEARCH_DEPOT("Depot"),
    SEARCH_HAULAGE("Haulage"),
    SEARCH_DOOR("Door"),
    SEARCH_COUNTRY("Country"),
    SEARCH_PORT("Port"),
    // ## 02 END
    SEARCH_VENDOR("Vendor"),//##03
    SEARCH_RATES("rateSearch"),//##03
     //##04 BEGIN
    ADD_COST_RATE("addCostRate"),
    EDIT_COST_RATE("editCostRate"),
    APPROVE_COST_RATE("approveCostRate"),
    DELETE_COST_RATE("deleteCostRate"),
    REJECT_COST_RATE("rejectCostRate"),
    //##04 END
     //##05 BEGIN
     ADD_BILLING_RATE("addBillingRate"),
     EDIT_BILLING_RATE("editBillingRate"),
     APPROVE_BILLING_RATE("approveBillingRate"),
     DELETE_BILLING_RATE("deleteBillingRate"),
     REJECT_BILLING_RATE("rejectBillingRate"),
     SEARCH_SERVICE("service"),
     SEARCH_CURRENCY("currency"),
     CONTRACT_UPLOAD("upload"),
     CONTRACT_DOWNLOAD("contractDownload"),
     CONTRACT_TEMPLATE_DOWNLOAD("contractTemplateDownload"),
     //Start: Added by Tanveer
     SEARCH_EQUIPMENT("getJOEquioment"),
     SEARCH_CONTAINER("getJOContainer"),
     SEARCH_DGIMDG("getDgImdg"),
     SEARCH_REASON_CODE("getReasonCd"),
     SEARCH_BL_BKG_POPUP("getBkgBlPopUp"),
     SEARCH_JO_LOG_POPUP("getJOLog"),
     DELETE_BKG_BL_POPUP("delBkgBl"),
     BOOKING_BL_DELETE_LUMPSUM("delBookingBlLumpsum"),
     CONTAINER_TEMPLETE_DOWNLOAD("containerTemplateDownload"),  
     CONTAINER_UPLOAD("upload"),
     NEW_CHANGE_PRIORITY("newchangepriority"),
     EDIT_CHANGE_PRIORITY("editchangepriority"),
     COPY_CHANGE_PRIORITY("copychangepriority"),
     GET_USER_CURRENCY("getUserCurrency"),
     GET_CONTRACT_CURRENCY("getContractCurrency"),
     GET_FSC_CURR_CODE("getfscCurrencyCode"),
     GET_TERMINAL_CODE("validateTerminal"),
     GET_CONTRACT_DATA("getSelectedContractData"),
     CONTRACT_PRIORITY_CORRECTION("priorityCorrection"),
     //End: Added by Tanveer
     //##05 END
     //##06BEGIN
     SEARCH_JOB_ORDER_SERVICE("serviceVesselLookup"),
    
    //START : Added By Madhuri
     CUSTOMER_LOOKUP_SERVICE("exemptCustomerLookup"),
     SEARCH_ROUTE_LIST("lookupRouteList"),
    PROCESS_JO_SEARCH_BOOKING_BL("joSearch"),
    PROCESS_JO_SUMMARY_BOOKING_BL("joSummary"),
    PROCESS_JO_SAVE_BOOKING_BL("joSaveBookingBL"),
    PROCESS_JO_EQUIPMENT_LIMIT("adhocEquipLimit"),
    PROCESS_JO_EQUIPMENT_LIST("adhocEquipList"),
    PROCESS_JO_DELETE_LUMPSUM("deleteLumpsum"),
    MAINTAIN_JO_SEARCH("maintainJoSearch"),
    MAINTAIN_JO_COMPLETE_JO("completeJo"),
    MAINTAIN_JO_DOWNLOAD_JO("maintainJoDownload"),
    MAINTAIN_JO_CANCEL_JO("maintainJoCancel"),
    MAINTAIN_JO_APPROVE_JO("maintainJoApprove"),
    MAINTAIN_JO_REJECT_JO("maintainJoReject"),
    MAINTAIN_JO_SAVE_JO("maintainJoSave"),
    MAINTAIN_DOWNLOAD("maintainDownload"),
    MAINTAIN_TEMPLATE_DOWNLOAD("maintainJoTemplateDownload"),
    MAINTAIN_JO_REMOVE_EQUIPMENT("maintainRemoveEq"),
    MAINTAIN_JO_DELETE_LUMPSUM("deleteLumpsum"),
    MAINTAIN_JO_JO_DOWNLOAD_LIMIT("joDownloadLimit"),
    ADHOC_JO_SUMMARY("joSummeryAdhoc"),
    SEARCH_FSC_LIST("fsc"),
    RESET_JOB_ORDER("resetJoSummury"),
    CREATE_JOB("createJob"),
    CREATE_JOB_ADHOC("createJoAdhoc"),
    CHANGE_VENDOR("saveChangeVendor"),
    GET_TERM_DATA("getTermData"),
    GET_TRANSPORT_MODE("getTransportMode"),
    EXEMPTED_CUST_SEARCH("exemptSearch"),
    EXEMPTED_CUST_ADD("exemptAdd"),
    EXEMPTED_CUST_EDIT("exemptEdit"),
    EXEMPTED_CUST_DELETE("exemptDelete"),
    SEARCH_VESSEL("vesselLookup"),
    ADD_EDIT_COST_RATE_LIST("addEditCostRateList"),
    ADD_BILLING_RATE_LIST("addEditBillRateList"),
    SAVE_OOG_SETUP("saveOOGSetup"),
    SEARCH_OOG_SETUP("searchOOGSetup"),
    SAVE_PORT_SETUP("savePortSetup"),
    SEARCH_PORT_SETUP("searchPortSetup"),
    SAVE_IMDG_SETUP("saveImdgSetup"),
    SEARCH_IMDG_SETUP("searchImdgSetup");
    
    
    
    //END : Added By Madhuri
    //##03
    String action;
    
    IjsActionMethod(String action){
        this.action=action;
    }
    public String getAction(){
        return action;
    }
}
