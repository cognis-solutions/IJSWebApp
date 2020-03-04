/*-----------------------------------------------------------------------------------------------------------
IjsErrorCode.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            keeps IJS error code 
02 21/09/17  NIIT       IJS            added error codes for update contract
03 05/10/17  NIIT       IJS            added error codes for suspend and delete contracts
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.constants;

/**
 * 
 */
public enum IjsErrorCode {
	DB_IJS_CNTR_EX_99999("IJS_CNTR_EX_99999"),
    DB_IJS_COMM_EX_10001("IJS_EX_10001"),//##01
    //##02 BEGIN
    DB_IJS_CNTR_EX_10006("IJS_EX_10006"),
    DB_IJS_CNTR_EX_10007("IJS_EX_10007"),
    DB_IJS_CNTR_EX_10008("IJS_EX_10008"),
    //##02 END
    //##03 BEGIN
    DB_IJS_CNTR_EX_10009("IJS_EX_10009"),
    DB_IJS_CNTR_EX_10010("IJS_EX_10010"),
    //
    DB_IJS_CNTR_EX_10011("IJS_EX_10011"),
    DB_IJS_CNTR_EX_10012("IJS_EX_10012"),
    DB_IJS_CNTR_EX_10013("IJS_EX_10013"),
    DB_IJS_CNTR_EX_10014("IJS_EX_10014"),
    DB_IJS_CNTR_EX_10015("IJS_EX_10015"),
    //##03 END
    //##04 BEGIN
     DB_IJS_CNTR_EX_10016("IJS_EX_10016"),
     DB_IJS_CNTR_EX_10017("IJS_EX_10017"),
     DB_IJS_CNTR_EX_10018("IJS_EX_10018"),
     DB_IJS_CNTR_EX_10019("IJS_EX_10019"),
     DB_IJS_CNTR_EX_10020("IJS_EX_10020"),
    DB_IJS_CNTR_EX_10022("IJS_EX_10022"),
    DB_IJS_CNTR_EX_10023("IJS_EX_10023"),
    DB_IJS_CNTR_EX_10024("IJS_EX_10024"),
    DB_IJS_CNTR_EX_10025("IJS_EX_10025"),
    DB_IJS_CNTR_EX_10026("IJS_EX_10026"),
    DB_IJS_CNTR_EX_10027("IJS_EX_10027"),
    DB_IJS_CNTR_EX_10028("IJS_EX_10028"),
    DB_IJS_CNTR_EX_10029("IJS_EX_10029"),
    DB_IJS_CNTR_EX_10030("IJS_EX_10030"),
    DB_IJS_CNTR_EX_10031("IJS_CNTR_EX_10031"),
    DB_IJS_CNTR_EX_10032("IJS_CNTR_EX_10032"),
    DB_IJS_CNTR_EX_10033("IJS_CNTR_EX_10033"),
    DB_IJS_CNTR_EX_10034("IJS_CNTR_EX_10034"),
    DB_IJS_CNTR_JO_EX_1001("IJS_CNTR_JO_EX_1001"),
    DB_IJS_CNTR_JO_EX_1002("IJS_CNTR_JO_EX_1002"),
    DB_IJS_MAINT_JO_EX_20001("IJS_EX_10031"),
    DB_IJS_MAINT_JO_ADHOC_20001("IJS_ADHOC_10031"),
    DB_IJS_MAINT_JO_EX_20002("IJS_EX_10032"),
    DB_IJS_MAINT_JO_ADHOC_20002("IJS_ADHOC_10032"),
    DB_IJS_MAINT_JO_EX_20003("IJS_EX_10033"),
    DB_IJS_MAINT_JO_ADHOC_20003("IJS_ADHOC_10033"),
    DB_IJS_MAINT_JO_EX_20004("IJS_EX_10034"),
    DB_IJS_MAINT_JO_ADHOC_20004("IJS_ADHOC_10034"),
    DB_IJS_MAINT_JO_EX_20005("IJS_EX_10035"),
    DB_IJS_MAINT_JO_EX_20006("IJS_EX_10036"),
    DB_IJS_MAINT_JO_EX_20007("IJS_EX_10037"),
	DB_IJS_CNTR_EX_10041("IJS_EX_10041"),
    DB_IJS_CNTR_EX_10042("IJS_EX_10042"),
    DB_IJS_CNTR_EX_10043("IJS_EX_10043"),
    DB_IJS_CNTR_EX_10044("IJS_EX_10044"),
    DB_IJS_CNTR_EX_10045("IJS_EX_10045"),
    DB_IJS_CNTR_EX_10046("IJS_EX_10046"),
    DB_IJS_CNTR_EX_10047("IJS_EX_10047"),
    DB_IJS_CNTR_EX_10048("IJS_EX_10048"),
    DB_IJS_CNTR_EX_10049("IJS_EX_10049"),
    DB_IJS_RATE_EX_10016("IJS_RATE_EX_10016"),
    DB_IJS_RATE_EX_10017("IJS_RATE_EX_10017"),
    DB_IJS_RATE_EX_10018("IJS_RATE_EX_10018"),
    DB_IJS_RATE_EX_10019("IJS_RATE_EX_10019"),
    DB_IJS_RATE_EX_10020("IJS_RATE_EX_10020"),
    DB_IJS_RATE_EX_10021("IJS_RATE_EX_10021"),
    DB_IJS_RATE_EX_10022("IJS_RATE_EX_10022"),
    DB_IJS_RATE_EX_10023("IJS_RATE_EX_10023"),
    DB_IJS_RATE_EX_10024("IJS_RATE_EX_10024"),
    UI_IJS_CNTR_UPLD_EX_10001("UI_IJS_UPLD_EX_10001"),
    UI_IJS_CNTR_UPLD_EX_10002("UI_IJS_UPLD_EX_10002"),
    UI_GBL_IJS_EX_10001("GBL_IJS_EX_10001"),
    DB_IJS_PRJ_EX_10001("IJS_PRJ_EX_10001"),
    DB_IJS_PRJ_EX_10002("IJS_PRJ_EX_10002"),
    DB_IJS_PRJ_EX_10003("IJS_PRJ_EX_10003"),//CR#03
    DB_IJS_PRJ_EX_10004("IJS_PRJ_EX_10004"),//CR#03
    DB_IJS_PRJ_EX_10005("IJS_PRJ_EX_10005"),//CR#03
    DB_IJS_PRJ_EX_10006("IJS_PRJ_EX_10006"),//CR#03
    DB_IJS_COMM_SETUP_EX_1001("IJS_COMM_SETUP_EX_1001");
    String errorCode;
    
    IjsErrorCode(String errorCode){
      this.errorCode=  errorCode;
     
    }
    
    public String getErrorCode(){
        return errorCode;
    }
   
}
