 /*-----------------------------------------------------------------------------------------------------------
 GlobalConstants.java
 -------------------------------------------------------------------------------------------------------------
 Copyright RCL Public Co., Ltd. 2007
 -------------------------------------------------------------------------------------------------------------
 Author NIIT 24/08/17
 - Change Log ------------------------------------------------------------------------------------------------
 ## DD/MM/YY -User-     -TaskRef-      -Short Description
 01 24/08/17  NIIT       IJS            GlobalConstants for Excel upload
 -----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.common.excel;

/**
 * This interface contains all the constants that are global to the project
 *
 * @author NIIT
 * @version 1.0
 */
 //#1 10.06.2016 Saisuda -Add Category dropdown.

public interface GlobalConstants {

    /* spring dao config file */
    public static final String SPRING_JDBC_CONFIG  = "com/rclgroup/dolphin/ezl/resource/EZL-SpringJdbc.xml";
    
    /* Sesssion Time Out URL */
    public static final String NO_SESSION_PATH = "/pages/common/misc/sessionOut.htm";
    /* Close Me URL */
    public static final String CLOSE_ME_PATH   = "/pages/common/misc/closeMe.jsp";
    /* No Authorization URL */
    public static final String NO_AUTH_PATH    = "/pages/common/misc/noauthorization.jsp";
    /* ERROR URL */
    public static final String ERROR_PATH      = "/pages/common/error/error.jsp";
    
    /* START - KEY_USER_INFO Cookie Values Position */
    public static final int TOTAL_COOKIE_ITEMS  = 7;
    /* Login User ID from Calling Applicaiton */
    public static final int IDX_USER_ID         = 0;
    /* Login User PASSWORD from Calling Applicaiton */
    public static final int IDX_USER_PASS       = 1;
    /* Login User's FSC Code */
    public static final int IDX_USER_FSC_CD     = 2;
    /* Login User's Data Access Levels
    Format =[ Data Access Level-1~Level-2~Level-3 ] (e.g. R~I~BKK)
    Access Level-1: 
    Line or Carrier to which the User belongs (It can not be ‘*’)
    Access Level-2:
    Region or Trade to which the User belongs. Incase of normal FSC User, 
    it can not be ‘*’. But for Principal or HO user it should be ‘*’, so that 
    this user can access all data across all regions & FSCs.
    Access Level-3:
    FSC or Agent Location to which the User belongs. Incase of normal FSC User, 
    it can not be ‘***’. But for Principal or HO user it should be ‘***’, so 
    that this user can access all data across all FSCs within the its region 
    (defined by Level-2).
    */
    public static final int IDX_USER_ACCESS_LVL1= 3;
    public static final int IDX_USER_ACCESS_LVL2= 4;
    public static final int IDX_USER_ACCESS_LVL3= 5;
    /* Login User's FSC's Date Format */
    public static final int IDX_FSC_DT_FORMAT   = 6;
    /* END - KEY_USER_INFO Cookie Values Position */
    
    /* Parent Applications */
    public static final String DOLPHIN              = "Dolphin";
    public static final String BHUMNET              = "Bhumnet";
    public static final String CALL_APP_USER_ID     = "tbx_uid";
    
    /* Organization Types */   
    public static final String  ORG_TYPE_ALL        = "CVDIRS";
    public static final String  ORG_TYPE_CUSTOMER   = "C";
    public static final String  ORG_TYPE_VENDOR     = "V";
    public static final String  ORG_TYPE_DRIVER     = "D";
    public static final String  ORG_TYPE_IN_HOUSE   = "I";
    public static final String  ORG_TYPE_REPAIR     = "R";
    public static final String  ORG_TYPE_SURVEYOR   = "S";
    

    /**
     * UserAccountBean for the application
     */
    public static final String USER_ACCOUNT_BEAN = "sUserAccountBean";
    // Key used for passing around User ID
    public static final String USER_ID          = "USER_ID";
    public static final String THAI_ENCODING    = "MS874"; 
    
    //Font File Names
    public static final String FONT_ARIALUNI_TTF= "ARIALUNI.TTF";
    
    /**
     * This refers to the fully qualified class name of the UserAccountBean
     */
    public static final String USER_ACCOUNT_BEAN_CLASS = "com.niit.control.web.UserAccountBean";

    public static final String APPLICATION_RESOURCE    = "com/rclgroup/dolphin/ezl/resource/ApplicationResources.properties";
    public static final String PROG_INFO_RESOURCE      = "com/rclgroup/dolphin/ezl/resource/ProgramInfo.properties";

    public final static String GENERICEXCEPTION = "GenericException";

    public final static String DATE_FORMAT    = "DD/MM/YYYY";
    
    public final static String DATE_SEPARATOR = "/";
    
    public static final String KEY_SLASH = "/";
    public static final String KEY_BACK_SLASH = "\\";
    
    public final static char UNDERSCORE = '_';

    public final static String COMMA = ",";
    public static final String COLON = ":";
    public static final int   SCREEN_SIZE = 8;    

    public final static String TILDE = "~";
    public final static String PIPE  = "|";

    public final static String HASH = "#";
    
    public static final String SQL_SUCCCESS_CD    = "000000";
    
    public static final String BLANK            = "";       
    
    //One Char Space
    public static final String BLANK_SPACE      = " ";       
    
    public final static String CONNECTIONS = "ezlconn";

    public static final String CONTROLPACKAGENAME = "control";

    public static final String COMMONPACKAGENAME = "common";

    //Report Server URL
    public static final String REPORT_SERVER_URL = "REPORT_SERVER_URL";
    //Report Server Env-Config Name
    public static final String REPORT_SERVER_ENV = "REPORT_SERVER_ENV";

    //Footer Date Key for Session
    public static final String FOOTER_DATE = "FOOTER_DATE";
    
    public static final String TRUE    = "true";
    
    public final static String SUCCESS = "success";

    public final static String FAILURE = "failure";

    public final static String SYSTEMERROR = "SYSTEM_ERROR";

    public final static String ERRMSG = "system.error";
    
    public static final String APP_ERROR = "Application Error";

    public final static String STACKTRACE = "STACKTRACE";

    public static final String NO_ACCESS_PAGE = "NO_ACCESS_PAGE" ;
    
    public static final String STAR_FUNC = "****" ;    
    
    public static final String NEXT_LINE = "\n";

    public  static final String DOT = ".";    


    //key for ADD
    public static final String KEY_UPD = "KEY_UPD";
    //key for ADD
    public static final String KEY_ADD = "KEY_ADD";
    //key for ADD
    public static final String KEY_DEL = "KEY_DEL";

    //ADD Status
    public static final String ADD = "ADD";

    //UPD Status
    public static final String UPD = "UPD";

    //DEL Status
    public static final String DEL = "DEL";

    //HIDE Status
    public static final String HIDE = "HIDE";

    //KEY to get search Data
    public static final String GET_DATA = "GET_DATA";

    //KEY to send save data
    public static final String SAVE_DATA = "SAVE_DATA";
        
    // Base Template Directory
    public static final String TEMPLATE_DIR     = "TEMPLATE_DIR";    
    // Base Upload Directory
    public static final String TEMP_UPLOAD_DIR  = "TEMP_UPLOAD_DIR";    
    // Base Attachment Directory
    public static final String ATTACHMENT_DIR   = "ATTACHMENT_DIR";
    // Base CODA Download Directory
    public static final String CODA_DIR         = "CODA_DIR";
    // Base Font Directory
    public static final String FONT_DIR         = "FONT_DIR";
    // Base Download Directory
    public static final String TEMP_DOWNLOAD_DIR= "TEMP_DOWNLOAD_DIR";
    
    // File Download Temp Directory Constant
    public final static String TEMP_DIR        = "TEMP_DIR";
    // File Download Default Extension
    public final static String DEF_DWNLD_EXT   = "csv";
    // File Name Key
    public final static String FILE_NAME       = "FILE_NAME";
    // File Name Key
    public final static String FILE_PATH       = "FILE_PATH";

    public static final String RETVALUE         = "RetValue";

    //  Key used for passing around SYSDATE
    public static final String SYSDATE = "SYSDATE";

    //  Key used for passing Screen ID
    public static final String SCR_ID = "SCR_ID";
    // Key used for  getting Request Locale Language
    public static final String REQ_LOCALE = "REQ_LOCALE";

    //Total No. of Records per page
    public static final int TOT_REC_PER_PAGE = 10;

    //Total No. of Pages per Time
    public static final int TOT_PAGE_PER_SET = 10;
    
    //Key for SQL - Ref Cursor
    public static final String KEY_REF_CURSOR   = "p_o_refCursor";
    public static final String KEY_REF_ERR_LOG  = "p_o_refErrorLogList";
    //Key for SQL - Prog ID
    public static final String KEY_PROG_ID      = "p_i_v_prog_id";
    //Key for SQL - File Name
    public static final String KEY_FILE_NM_ID   = "p_i_v_file_nm";    
    //Key for SQL - User ID
    public static final String KEY_USER_ID      = "p_i_v_user_id";
    //Key for SQL - User Fsc
    public static final String KEY_USER_FSC     = "p_i_v_user_fsc";
    //Key for SQL - Update Date
    public static final String KEY_UPD_DT       = "p_i_v_upd_dt";
    //Key for SQL - Total No. of Records found in search 
    public static final String KEY_TOT_REC_COUNT= "p_o_v_tot_rec";
    //Key for SQL - Current Page No
    public static final String KEY_CURR_PAGE_NO = "p_i_v_curr_page";
    //Key for SQL - Return Error Code + Data
    public static final String KEY_ERROR_DATA   = "p_o_v_error";
    //Key for SQL - Record Status
    public static final String KEY_REC_STATUS   = "p_i_v_rec_status";
    //Key for SQL - Document Status(Contract/Quotation/Booking/Job Order)
    public static final String KEY_DOC_STATUS   = "p_i_v_doc_status";
    //Key for SQL - Sort By Field
    public static final String KEY_SORT_BY      = "p_i_v_sort_by";
    //Key for SQL - Sort In Acsending/Descending
    public static final String KEY_SORT_IN      = "p_i_v_sort_in";
    //Key for SQL - Finds By Value
    public static final String KEY_FIND_VAL     = "p_i_v_find_val";
    //Key for SQL - Finds IN FIELD NAME
    public static final String KEY_FIND_IN      = "p_i_v_find_in";
    //Key for SQL - WILD CARD
    public static final String KEY_WILD_CARD    = "p_i_v_wild_card";
    
    /*Added By Ankit */
    //Key for SQL - CONTROL FSC
    public static final String KEY_IS_CONTROL_FSC = "p_o_v_is_control_fsc";
    
    /* Parameter Names used in all Reports */
    public static final String  PARAM_SYSDATE  = "P_I_V_DATE_TM";     //Always First Param
    public static final String  PARAM_USER_ID  = "P_I_V_USER_ID";     //Always Last Param
        
    // key for Validate User
    public static final String SCREEN_ACCESS = "SCREEN_ACCESS";

    //  Key used for Locale
    public static final java.util.Locale DEFAULT_LOCALE = java.util.Locale.ENGLISH ;

    // Home Directory for EZL
    public static final String BASE_BATCH_DIR   = "/ApplTop/EZL";   
    
    //Language Id
    public static final String LANG_ID = "EN";
    
    public static final String KEY_USER_INFO = "RCL_AUTH_KEY";    
    public static final String KEY_AUTH_ID   = "rclauth";
    public static final String CALLING_APP_ID= "appId";
    
    public static final String KEY_SC_INF       = "INF";
    public static final String KEY_SC_WRN       = "WRN";
    public static final String KEY_SC_ERR       = "ERR";
    
    public static final String YES              = "Y";     
    public static final String NO               = "N";
    
    public static final String COMPANY_RCL      = "RCLL";

    
    //SCREEN AUTH FLAGS
    public static final int IDX_READ_FLAG       = 0;
    public static final int IDX_NEW_FLAG        = 1;
    public static final int IDX_EDIT_FLAG       = 2;
    public static final int IDX_DEL_FLAG        = 3;
    
    
    
    //SP-PACKAGE PARAMETER POSITIONS
    public static final int IDX_PARAM_TOT       = 4;       
    public static final int IDX_PARAM_NM        = 0;
    public static final int IDX_DATA_TYP        = 1;
    public static final int IDX_IN_OUT          = 2;
    public static final int IDX_PARAM_VAL       = 3;
    
    //SP-PACKAGE PARAMETER TYPES
    public static final String PARAM_IN         = "I";
    public static final String PARAM_OUT        = "O";
    public static final String PARAM_INOUT      = "IO";
    
    /* SQL Error Prefix */
    public static final String SQL_ERR_PREFIX   ="EZL~";
    public static final String SQL_ERR_POSTFIX  ="~EZL";
    
    /* Fsc Levels */
    public static final String TRADE_ALL        ="*";
    public static final String FSC_ALL          ="***";
    /*Fsc Levels Index */
    public static final int IDX_LINE            =0;
    public static final int IDX_TRADE           =1;
    public static final int IDX_AGENT           =2;
    
    /* Service Category */
    public static final String SERVICE_A       = "A";
    public static final String SERVICE_B       = "B";
    public static final String SERVICE_C       = "C";
    
    /* Service Type : Transport Flag Y=Dolphin , Transport Flag N=EZL*/
    public static final String SERVICE_DOLPHIN = "Y";
    public static final String SERVICE_EZL     = "N";

    /* Service Location-Route Type */
    public static final String ROUTE_ALL     = "0";
    public static final String ROUTE_1       = "1";
    public static final String ROUTE_2       = "2";
    public static final String ROUTE_3       = "3";
        
    /* Sort In Order*/
    public static final String SORT_IN_ASC          = "ASC";
    public static final String SORT_IN_DESC         = "DESC";

    /* Record Status */
    public static final String REC_STATUS_ALL       = "0";
    public static final String REC_STATUS_ACTIVE    = "A";
    public static final String REC_STATUS_SUSPEND   = "S";

    /* Doc Status */
    public static final String DOC_STATUS_ALL           = "0";
    public static final String DOC_STATUS_ENTRY         = "1";
    public static final String DOC_STATUS_PENDING_APRV  = "2";
    public static final String DOC_STATUS_APPROVE       = "3";
    public static final String DOC_STATUS_CONFIRM       = "4";
    public static final String DOC_STATUS_BILLING       = "5";
    public static final String DOC_STATUS_REVIEW        = "6";
    public static final String DOC_STATUS_CANCEL        = "7";
    public static final String DOC_STATUS_PENDING_SEND  = "8";
    public static final String DOC_STATUS_FINAL         = "9";

    /* Wild Card Values*/
    public static final String WILD_CARD_ALL           = "0";
    public static final String WILD_CARD_EXACT         = "1";
    public static final String WILD_CARD_FRONT         = "2";
    public static final String WILD_CARD_BEHIND        = "3";
    
    
    /* Business Object Codes */
    public static final String BUS_OBJ_CD_FSC          = "FSC";
    public static final String BUS_OBJ_CD_CONTRACT     = "VCT";
    public static final String BUS_OBJ_CD_QUOTATION    = "VQT";
    public static final String BUS_OBJ_CD_BOOKING      = "VBK";
    public static final String BUS_OBJ_CD_JOB_ORDER    = "VJO";
    public static final String BUS_OBJ_CD_TRACK_TRACE  = "VTT";
    public static final String BUS_OBJ_CD_REPORTS      = "VRP";

    /* Quotation or Booking Types */
    public static final String DOC_TYPE_ALL            = "0";
    public static final String DOC_TYPE_EXPORT         = "E";
    public static final String DOC_TYPE_IMPORT         = "I";
    public static final String DOC_TYPE_DOMESTIC       = "D";
    public static final String DOC_TYPE_CROSS_BORDER   = "C";
    public static final String DOC_TYPE_INTRANSIT      = "I";
    public static final String DOC_TYPE_BONDED         = "B";
    
    
    public static final String KEY_COMBO_LL_STATUS     = "KEY_COMBO_LL_STATUS";
    
    /* Common Combo or Master ID Keys */
    public static final String KEY_COMBO_CLR           = "CLR";
    public static final String KEY_COMBO_DL_STATUS     = "KEY_COMBO_DL_STATUS";
    public static final String KEY_COMBO_BOOKING_IN    = "KEY_COMBO_BOOKING_IN";
    public static final String KEY_COMBO_BOOKING_ORDER = "KEY_COMBO_BOOKING_ORDER";
    public static final String KEY_COMBO_LOCAL_CONT_STS= "KEY_COMBO_LOCAL_CONT_STS";
    public static final String KEY_COMBO_MIDSTREAM     = "KEY_COMBO_MIDSTREAM";
    public static final String KEY_COMBO_LOAD_COND     = "KEY_COMBO_LOAD_COND";
    public static final String KEY_COMBO_DL_STS        = "KEY_COMBO_DL_STS";
    public static final String KEY_COMBO_DAMAGED       = "KEY_COMBO_DAMAGED";
    public static final String KEY_COMBO_SWAP_CONN     = "KEY_COMBO_SWAP_CONN";
    public static final String KEY_COMBO_TIGHT_CONN_POD= "KEY_COMBO_TIGHT_CONN_POD";
    public static final String KEY_COMBO_FLASH_UNIT    = "KEY_COMBO_FLASH_UNIT";
    public static final String KEY_COMBO_FUMIGATION_ONLY= "KEY_COMBO_FUMIGATION_ONLY";
    public static final String KEY_COMBO_REEFER_TMP_UNIT= "KEY_COMBO_REEFER_TMP_UNIT";
    public static final String KEY_COMBO_OVERLANDED_IN = "KEY_COMBO_OVERLANDED_IN";
    public static final String KEY_COMBO_OVERLANDED_ORDER = "KEY_COMBO_OVERLANDED_ORDER";
    public static final String KEY_COMBO_SIZE          = "KEY_COMBO_SIZE";
    public static final String KEY_COMBO_FULL_MT       = "KEY_COMBO_FULL_MT";
    public static final String KEY_COMBO_BOOKING_TYPE  = "KEY_COMBO_BOOKING_TYPE";
    public static final String KEY_COMBO_SOC_COC       = "KEY_COMBO_SOC_COC";
    public static final String KEY_COMBO_POD_STATUS    = "KEY_COMBO_POD_STATUS";
    public static final String KEY_COMBO_DL_STS_OL     = "KEY_COMBO_DL_STS_OL";
    public static final String KEY_COMBO_SPECIAL_HNDL  = "KEY_COMBO_SPECIAL_HNDL";
    public static final String KEY_COMBO_RESIDUE       = "KEY_COMBO_RESIDUE";
    public static final String KEY_COMBO_RESTOW_IN     = "KEY_COMBO_RESTOW_IN";
    public static final String KEY_COMBO_RESTOW_ORDER  = "KEY_COMBO_RESTOW_ORDER";
    public static final String KEY_COMBO_RESTOW_STATUS = "KEY_COMBO_RESTOW_STATUS";
    /* for load list */    
    public static final String KEY_COMBO_LOAD_LIST_STATUS   = "KEY_COMBO_LOAD_LIST_STATUS";
    public static final String KEY_LOADING_STATUS_VALUE     = "KEY_LOADING_STATUS_VALUE";
    public static final String KEY_LL_BOOKING_IN_VALUE      = "KEY_LL_BOOKING_IN_VALUE";
    public static final String KEY_LL_BOOKING_ORD_VALUE     = "KEY_LL_BOOKING_ORD_VALUE";
    public static final String KEY_LL_BOOKING_ORD2ORD_VALUE = "KEY_LL_BOOKING_ORD2ORD_VALUE";
    public static final String  KEY_COMBO_OVERSHIPPED_IN      = "KEY_COMBO_OVERSHIPPED_IN";
    public static final String  KEY_COMBO_OVERSHIPPED_ORD     = "KEY_COMBO_OVERSHIPPED_ORD";
    public static final String  KEY_COMBO_OVERSHIPPED_LOADING = "KEY_COMBO_OVERSHIPPED_LOADING";
    public static final String  KEY_COMBO_RESTOWED_IN         = "KEY_COMBO_RESTOWED_IN";
    public static final String  KEY_COMBO_RESTOWED_ORD        = "KEY_COMBO_RESTOWED_ORD";
    

    public static final String KEY_COMBO_CONTRACT       = "CONTRACT";
    public static final String KEY_COMBO_QUOTATION      = "QUOTATION";
    public static final String KEY_COMBO_BOOKING        = "BOOKING";
    public static final String KEY_COMBO_JOB_ORDER      = "JOB_ORDER";
    public static final String KEY_COMBO_VENDOR         = "VENDOR";
    public static final String KEY_COMBO_CUSTOMER       = "CUSTOMER";
    public static final String KEY_COMBO_FSC            = "FSC";
    public static final String KEY_COMBO_SHIP_TERM      = "SHIP_TERM";
    public static final String KEY_COMBO_LOCATION       = "LOCATION";
    public static final String KEY_COMBO_COMODITY_GROUP = "COMODITY_GROUP";
    public static final String KEY_COMBO_COMODITY       = "COMODITY";
    public static final String KEY_COMBO_PRINT_ID       = "PRINT_ID";
    public static final String KEY_COMBO_COUNTRY        = "COUNTRY";
    public static final String KEY_COMBO_CATEGORY       = "CATEGORY";
    public static final String KEY_COMBO_PRODUCT        = "PRODUCT";
    public static final String KEY_COMBO_DOC_TYPE       = "DOC_TYPE";
    public static final String KEY_COMBO_REASON         = "REASON";
    public static final String KEY_COMBO_PACK_TYPE      = "PACK_TYPE";
    public static final String KEY_COMBO_EVENT          = "EVENT";
    public static final String KEY_REPORTS_INFO         = "KEY_REPORTS_INFO";
    public static final String KEY_CRANE_DESC           = "KEY_CRANE_DESC";
    public static final String KEY_COMBO_CATEGORY_DESC = "KEY_COMBO_CATEGORY_DESC";
    
    
    /* Print or Send Flag */
    public static final String PRINT_FLAG               = "P";
    public static final String SEND_FLAG                = "S";
     
    public static final String DUMMY_MARGIN_PERC        = "99999";
    public static final String DUMMY_ROUTE_CD           = "-";
    
    /* Laden Empty Type */
    public static final String TYPE_EMPTY               = " ";
    public static final String TYPE_LADEN               = "L";
    
    /* LCL/FCL Type */
    public static final String TYPE_FCL                 = "FCL";
    public static final String TYPE_LCL                 = "LCL";
        
    /* Transport Modes*/
    public static final String TRANSPORT_SEA            = "S";
    public static final String TRANSPORT_AIR            = "A";
    public static final String TRANSPORT_LAND           = "L";
    public static final String TRANSPORT_MULTI          = "M";
    
    /* Transport Modes*/
    public static final String DIRECTION_EAST           = "E";
    public static final String DIRECTION_WEST           = "W";
    public static final String DIRECTION_NORTH          = "N";
    public static final String DIRECTION_SOUTH          = "S";
    
    /* FG/Bulk/BigBag Type */
    public static final String GOODS_TYPE_FG            = "FG";
    public static final String GOODS_TYPE_BULK          = "BULK";
    public static final String GOODS_TYPE_BIGBAG        = "BB";
     
     /* some common error codes */
    //Database error occurred
    public static final String GE0001   ="ECM.GE0001";
    //Record locked by another user
    public static final String GE0002   ="ECM.GE0002";
    //Divide by zero occurred
    public static final String GE0003   ="ECM.GE0003";
    //No Record Found
    public static final String GE0004   ="ECM.GE0004";
    //Record delete by another user
    public static final String GE0005   ="ECM.GE0005";
    //Record updated by another user
    public static final String GE0006   ="ECM.GE0006";
    //Please select a row
    public static final String GE0007   ="ECM.GE0007";
    //Invalid Date Format
    public static final String GE0008   ="ECM.GE0008";
    //Please select a .xls file
    public static final String GE0009   ="ECM.GE0009";
    //Mandatory value not selected
    public static final String GE0010   ="ECM.GE0010";
    //Mandatory value not entered
    public static final String GE0011   ="ECM.GE0011";
    //Please enter a valid number
    public static final String GE0012   ="ECM.GE0012";
    //Please Save changes first
    public static final String GE0013   ="ECM.GE0013";
    //No changes to Save
    public static final String GE0014   ="ECM.GE0014";
    //Invalid Master Code
    public static final String GE0015   ="ECM.GE0015";
    //Executable file cannot be uploaded
    public static final String GE0016   ="ECM.GE0016";
    //Error While File Upload
    public static final String GE0017   ="ECM.GE0017";
    //File name more than 50 characters
    public static final String GE0018   ="ECM.GE0018";
    //Uploaded Directory does not exist
    public static final String GE0019   ="ECM.GE0019";
    //File Size should not be greater than {0}MB 
    public static final String GE0020   ="ECM.GE0020";
    //Invalid Template File
    public static final String GE0021   ="ECM.GE0021";
    //File does not exist on Server
    public static final String GE0022   ="ECM.GE0022";
    //Please select a File
    public static final String GE0023   ="ECM.GE0023";
    
    /* some common information codes */
    //Process successful message code : Ready
    public static final String GI0001    = "ECM.GI0001";
    //Process successful message code : {n} rows fetched.
    public static final String GI0002    = "ECM.GI0002";
    //Save successfully
    public static final String GI0003    = "ECM.GI0003";    
    
}


/* Modification History
 *
 * 2002-01: Modified for OEM
 * 2004-05: Modified for DOT
 * 2005-07: Modified for Tops
 * 2009-11: Moified for EZL
 *
 */