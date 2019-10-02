 /*-----------------------------------------------------------------------------------------------------------
 ReportTagConstant.java
 -------------------------------------------------------------------------------------------------------------
 Copyright RCL Public Co., Ltd. 2007
 -------------------------------------------------------------------------------------------------------------
 Author NIIT 24/08/17
 - Change Log ------------------------------------------------------------------------------------------------
 ## DD/MM/YY -User-     -TaskRef-      -Short Description
 01 24/08/17  NIIT       IJS            ReportTagConstant for Excel upload
 -----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.common.excel;


/**
 * @author NIIT
 * public interface  ReportTagConstant
 * @version 1.00
 * @see
 */
public interface ReportTagConstant {
	
	public static final String  FILE_EXT_XLS        = ".xls";
	public static final String  FILE_NAME_SEP       = "_";
	
	public static final String 	BLANK	        = ""; 
	
	/* Return Values of Report Generator */
	public static final int    ZERO_RECORD          = 0;
	public static final int    SUCCESS              = 1;
	
	public static final int MAX_ROWS_PER_WORKSHEET  = 10000;
	
	/* START -----  DO NOT CHANGE ANYTHING HERE... */
	public static final String RPT_PLACE_HOLDER_TAG_PREFIX 		= ":TAG";
	public static final String RPT_PLACE_HOLDER_TAG_TBL_PREFIX	= ":TAG_COL";
	public static final String RPT_PLACE_HOLDER_TEXT_PREFIX		= ":LBL";
	public static final String RPT_PLACE_HOLDER_IMAGE_PRERIX 	= ":LBL_IMG";
	public static final String RPT_PLACE_HOLDER_HRD_COL_PREFIX	= ":LBL_HDR_COL";
		
	/* Report Table Section Area - Start */
        public static final String KEY_TOT_DATA_COL                     = "KEY_TOT_DATA_COL";
        public static final String KEY_TABLE_FIRST_ROW_POS  	        = "KEY_TABLE_FIRST_ROW_POS";
        public static final String KEY_TABLE_FIRST_COL_POS              = "KEY_TABLE_FIRST_COL_POS";
        public static final String KEY_TABLE_LAST_COL_POS               = "KEY_TABLE_LAST_COL_POS";
	/* Report Table Section Area - End */
	
        /* Detail Data Column Types Info */
        public static final int    IDX_COL_NUM                          = 0;        
        public static final int    IDX_CELL_TYPE 			= 1;
        public static final int    IDX_ROW_NUM                          = 2;
        public static final int    IDX_CELL_DATA                        = 3;
	
        /*
         * HSSFCell.CELL_TYPE_NUMERIC:0
         * HSSFCell.CELL_TYPE_STRING:1
         * HSSFCell.CELL_TYPE_FORMULA:2
         * HSSFCell.CELL_TYPE_BLANK:3
         * HSSFCell.CELL_TYPE_BOOLEAN:4
         * HSSFCell.CELL_TYPE_ERROR:5
         */
        public int CELL_TYPE_DOUBLE =   0;
        public int CELL_TYPE_STRING =   1;
        public int CELL_TYPE_FORMULA=   2;
        public int CELL_TYPE_BLANK  =   3;
        public int CELL_TYPE_BOOLEAN=   4;
        public int CELL_TYPE_ERROR  =   5;
        public int CELL_TYPE_INT    =   900;
        public int CELL_TYPE_DATE   =   901;
        public int CELL_TYPE_TIME   =   902;
        
    
	//put detail section data of the report using this below KEY
	    public static final String KEY_HEADER_DATA                      = "KEY_HEADER_DATA";	
        public static final String KEY_DETAIL_DATA                      = "KEY_DETAIL_DATA";	
        public static final String KEY_REPORT_ID 			= "KEY_REPORT_ID";
        public static final String KEY_TEMPLATE_SHEET                   = "KEY_TEMPLATE_SHEET";
	public static final String KEY_PAGEBREAK_FLG  		        = "KEY_PAGEBREAK_FLG";
	
        /* Common Labels on Report */
        public static final String LBL_PAGE_NO                          = ":LBL_PAGE_NO";
        public static final String LBL_REPORT_INFO                      = ":LBL_REPORT_INFO";
        public static final String LBL_PRINT_DT 		        = ":LBL_PRINT_DT";
        public static final String LBL_USER_ID 			        = ":LBL_USER_ID";    
        
        
        /* END -----  DO NOT CHANGE ANYTHING HERE... */
    
        

}//end of class
