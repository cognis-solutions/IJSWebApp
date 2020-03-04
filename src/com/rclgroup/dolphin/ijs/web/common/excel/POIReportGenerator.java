 /*
  * @(#)POIReportGenerator.java
  *
  * Copyright 2007 by NIIT,
  * All rights reserved.
  * 
  * This software is the confidential and proprietary information
  * of NIIT. ("Confidential Information").  
  */
package com.rclgroup.dolphin.ijs.web.common.excel;

import java.util.*;

import java.io.*;

import java.sql.ResultSet;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * @author NIIT
 * public class  POIReportGenerator
 * @version 1.00
 * @see
 */
public class POIReportGenerator implements ReportTagConstant {

    /* class level variables starts here... */	
	

    private int mintDtlColTagInfo[][]       = null;
    
    private HSSFWorkbook mobjWorkbook       = null;
    private Map     mmapTagHolders          = null;

    
    private boolean mblnHaveHdr             = false;
    private boolean mblnHaveTable 	    = false;
    //Total no of Data records for report (excludes Header record incase of dynamic column reports)
    private int     mintTotDataRecord       = 0;
    //Total Displayable columns in report
    private int     mintTotDataCol       	= 0;
    //First Data Row No
    private int     mintFirstDataRowPosition    = 0;
    //First Data Column No
    private int     mintFirstDataColPosition    = 0;
    //Last Data Column No
    private int     mintLastDataColPosition     = 0;
    //Total Columns - Start to End Column in the Report template in the table section
    private int     mintTotTableCol             = (mintLastDataColPosition - mintFirstDataColPosition)+1;
    
    //Template Sheet No for temp use...
    private String    mstrReportID                      = null;
    private int       mintTemplateSheetNo               = 0;
    private String    mstrTemplateSheetName             = null;
    
    
    public void openWorkBook(String astrTemplateFilePath, String astrTemplateFileName) throws Exception {

        logMe("@@@@@@@@@Method: openWorkbook - START");
        logMe("File : " + astrTemplateFilePath + astrTemplateFileName);

        try {
            mobjWorkbook = new HSSFWorkbook
                                (
                                    new POIFSFileSystem
                                    (
                                        new FileInputStream(new File(astrTemplateFilePath + astrTemplateFileName))
                                    )
                                );

        } catch (Exception e) {
            Debug.logFramework(e);
            throw e;
        }

        logMe("@@@@@@@@@Method: openWorkbook - END");

    }//end of openWorkbook
    
     
    public String saveWorkBook(String astrFinalFilePath, String astrFinalFileName, String astrUserId, String astrSysDate) 
    			throws Exception {
    		
    		String lstrFinalFileName = astrFinalFileName.substring(0,astrFinalFileName.indexOf(FILE_EXT_XLS)) + 
                                                                            FILE_NAME_SEP + astrUserId + 
                                                                            FILE_NAME_SEP + astrSysDate + 
                                                                            FILE_EXT_XLS;  
    							  
    		return saveWorkBook(astrFinalFilePath, lstrFinalFileName);
    		
    }
    

    public String saveWorkBook(String astrFinalFilePath, String astrFinalFileName) throws Exception {

        logMe("@@@@@@@@@Method: saveWorkBook - START");
        logMe("File : " + astrFinalFilePath + astrFinalFileName);
        FileOutputStream modSpreadsheet = null;

        try {        
            modSpreadsheet = new FileOutputStream(astrFinalFilePath + astrFinalFileName);
            mobjWorkbook.write(modSpreadsheet);            
        } catch (Exception e) {
            logMe(e);
            throw e;
        } finally{                    
            modSpreadsheet.close();
            logMe("@@@@@@@@@Method: saveWorkBook - END");
        }
        
        return astrFinalFileName;
        
    }//end of saveWorkBook
    
     public void recalSheets(String[] aSheetNames) {

         HSSFSheet    objHSSFSheet = null;

         logMe("@@@@@@@@@Method: recalSheets - START");

         try {
            //If Null passed do it for all sheets 
            if(aSheetNames == null){
                int totSheets = mobjWorkbook.getNumberOfSheets();
                for(int nSheetIdx=0; nSheetIdx < totSheets; nSheetIdx++){
                    //dont do for the already template sheet, its done earlier
                    if(nSheetIdx != mintTemplateSheetNo){
                        objHSSFSheet = mobjWorkbook.getSheetAt(nSheetIdx);
                        objHSSFSheet.setForceFormulaRecalculation(true);
                    }
                }
            } else {
                int totSheets = aSheetNames.length;
                for(int nSheetIdx=0; nSheetIdx < totSheets; nSheetIdx++){
                    objHSSFSheet = mobjWorkbook.getSheetAt(mobjWorkbook.getSheetIndex(aSheetNames[nSheetIdx]));
                    objHSSFSheet.setForceFormulaRecalculation(true);
                }
            }
            
         } catch (Exception e) {
             Debug.logFramework(e);
         }

         logMe("@@@@@@@@@Method: recalSheets - END");

     }//end of recalSheets
     
    private HSSFSheet getWorkSheet(String strSheetName) throws Exception {
         mintTemplateSheetNo = mobjWorkbook.getSheetIndex(strSheetName);
         return getWorkSheet_(mintTemplateSheetNo);
    
    }//end of getWorkSheet      
     
    private HSSFSheet getWorkSheet_(int anSheetNo) throws Exception {

        HSSFSheet    objHSSFSheet = null;

        logMe("@@@@@@@@@Method: getWorkSheet_ - START");

        try {
            objHSSFSheet = mobjWorkbook.getSheetAt(anSheetNo);
            objHSSFSheet.setForceFormulaRecalculation(true);
        } catch (Exception e) {
            Debug.logFramework(e);
            throw e;
        }

        logMe("@@@@@@@@@Method: getWorkSheet_ - END");

        return objHSSFSheet;

    }//end of getWorkSheet_
     
     public HSSFCellStyle getCellStyleAt(short aCellNo){
         
         return mobjWorkbook.getCellStyleAt(aCellNo);
         
     }//getCellStyleAt
     
     public HSSFCellStyle createCellStyle(){
         
         return mobjWorkbook.createCellStyle();
         
     }//getCellStyle
     
     public HSSFFont createFont(){
         
         return mobjWorkbook.createFont();
         
     }//createFont
     
    public Map getPlaceHolders(String strSheetName) throws Exception {
        return getPlaceHolders(getWorkSheet(strSheetName));
    }

    public Map getTagHolders(String strSheetName) throws Exception {
        return getTagHolders(getWorkSheet(strSheetName));
    }

    public Map getPlaceHolders(HSSFSheet aObjSheet) throws Exception {

        logMe("@@@@@@@@@Method: getPlaceHolders - START");

        HSSFRow objRow      = null;
        HSSFCell objCell    = null;
        Iterator itrRows    = null;
        Iterator itrCells   = null;

        Map mapPlaceHolders = new HashMap(0);
        String lstrCellVal  = null;

        try {

            itrRows = aObjSheet.rowIterator();
            while(itrRows.hasNext()){
                objRow   = (HSSFRow)itrRows.next();
                itrCells = objRow.cellIterator();
                while(itrCells.hasNext()){
                    objCell     = (HSSFCell)itrCells.next();
                    lstrCellVal = objCell.getStringCellValue();
                    if(lstrCellVal.indexOf(RPT_PLACE_HOLDER_TEXT_PREFIX) == 0 ||
                       lstrCellVal.indexOf(RPT_PLACE_HOLDER_IMAGE_PRERIX) == 0){
                        int [] narrCellProps =
                            {
                                objCell.getCellType(),  //IDX_CELL_TYPE: Data Type of the Cell
                                objRow.getRowNum(),     //IDX_ROW_NUM: Row Index of the Cell
                                objCell.getCellNum()    //IDX_COL_NUM: Column Index Of the Cell

                            };
                        mapPlaceHolders.put(lstrCellVal, narrCellProps);
                    }

                }
            }

        } catch (Exception ex) {
            Debug.logFramework(ex);
        } finally {
            lstrCellVal = null;
            objCell     = null;
            itrCells    = null;
            objRow      = null;
            itrRows     = null;
        }

        logMe("@@@@@@@@@Method: getPlaceHolders - END");

        return mapPlaceHolders;

    } // end of getPlaceHolders.

    public Map getTagHolders(HSSFSheet aObjSheet) throws Exception {

        logMe("@@@@@@@@@Method: getTagHolders - START");

        HSSFRow objRow      = null;
        HSSFCell objCell    = null;
        Iterator itrRows    = null;
        Iterator itrCells   = null;

        mmapTagHolders = new HashMap(0);
        String lstrCellVal  = null;

        try {

            itrRows = aObjSheet.rowIterator();
            while(itrRows.hasNext()){
                objRow   = (HSSFRow)itrRows.next();
                itrCells = objRow.cellIterator();
                while(itrCells.hasNext()){
                    objCell     = (HSSFCell)itrCells.next();
                    lstrCellVal = objCell.getStringCellValue();
                    if(lstrCellVal.indexOf(RPT_PLACE_HOLDER_TAG_PREFIX) == 0){
                        int [] narrCellProps =
                            {
                                objCell.getCellType(),  //IDX_CELL_TYPE: Data Type of the Cell
                                objRow.getRowNum(),     //IDX_ROW_NUM: Row Index of the Cell
                                objCell.getCellNum()    //IDX_COL_NUM: Column Index Of the Cell

                            };
                        mmapTagHolders.put(lstrCellVal, narrCellProps);

                    }

                }
            }

        } catch (Exception ex) {
            Debug.logFramework(ex);
        } finally {
            lstrCellVal = null;
            objCell     = null;
            itrCells    = null;
            objRow      = null;
            itrRows     = null;
        }

        logMe("@@@@@@@@@Method: getTagHolders - END");

        return mmapTagHolders;

    } // end of getTagHolders.
    
    
    public int generateReport(Map amapData) throws Exception {
        mstrReportID            = (String)amapData.get(KEY_REPORT_ID);
        mstrTemplateSheetName   = (String)amapData.get(KEY_TEMPLATE_SHEET);
    	
        //findAndReplace(getWorkSheet(mstrTemplateSheetName), amapData);
         if(amapData.containsKey(KEY_HEADER_DATA)){
            //Ok this report have header section
            mblnHaveHdr = true;
         }
         if(mblnHaveHdr) {
            //Insert Data into header detail section...
            genHdrSection(getWorkSheet(mstrTemplateSheetName), amapData);
             
         }
        
        if(amapData.containsKey(KEY_DETAIL_DATA)){
            //Ok this report have detail section
            mblnHaveTable = true;
        }
        
        if(mblnHaveTable) {
            //Insert Data into Table detail section...
            genDtlSection(getWorkSheet(mstrTemplateSheetName),amapData);
            
        }
        
        return SUCCESS;
    }
    
    
    public int  generateReport(Map amapData, ResultSet arsReportData) throws Exception {        
        mstrReportID            = (String)amapData.get(KEY_REPORT_ID);
        mstrTemplateSheetName   = (String)amapData.get(KEY_TEMPLATE_SHEET);
    	
        //findAndReplace(getWorkSheet(mstrTemplateSheetName), amapData);
         if(amapData.containsKey(KEY_HEADER_DATA)){
            //Ok this report have header section
            mblnHaveHdr = true;
         }
         if(mblnHaveHdr) {
            //Insert Data into header detail section...
            genHdrSection(getWorkSheet(mstrTemplateSheetName), amapData);
             
         }
        
        //Ok this report have detail section, because result set is passed
        mblnHaveTable = true;
        
        if(mblnHaveTable) {
        	
            //Insert Data into Table detail section...
            genDtlSection(getWorkSheet(mstrTemplateSheetName),amapData, arsReportData);
            
        }
        
        return SUCCESS;
        
    }
    
    public void genHdrSection(HSSFSheet aObjSheet, Map amapData) throws Exception {

        logMe("@@@@@@@@@Method: genHdrSection - START");

        HSSFRow objRow      = null;
        HSSFCell objCell    = null;
        
        String lstrCellVal        = null;
        String[][] larrHdrData    = null;

        int    lintTotDataFields  = 0;
        
        try {
            
            //Get the HEADER data to be replaced...
            larrHdrData     = (String[][])amapData.get(KEY_HEADER_DATA);
            //logMe("larrHdrData="+larrHdrData);
            if(larrHdrData == null || larrHdrData.length == 0){
                return;
            }
            
            //Total Data Col for Report
            lintTotDataFields = larrHdrData.length;
            logMe("lintTotDataFields="+lintTotDataFields);
            
            for(int iData = 0; iData < lintTotDataFields; iData++){                
                objRow = aObjSheet.getRow(Integer.parseInt(larrHdrData[iData][IDX_ROW_NUM]));
                objCell = objRow.getCell(Integer.parseInt(larrHdrData[iData][IDX_COL_NUM]));
                lstrCellVal = larrHdrData[iData][IDX_CELL_DATA];
                if(lstrCellVal != null && lstrCellVal.length() >0) {                        
                    setCellValue(objCell, Integer.parseInt(larrHdrData[iData][IDX_CELL_TYPE]), lstrCellVal);                        
                }
            }//for(int iData = 0; iData < mintTotDataRecord; iData++){
        } catch (Exception ex) {
            logMe(ex);
        } finally {
            lstrCellVal = null;
            larrHdrData = null;
            objCell     = null;
            objRow      = null;
        }

        logMe("@@@@@@@@@Method: genHdrSection - END");

    } // end of genHdrSection - map param
        
    public void findAndReplace(HSSFSheet aObjSheet, Map amapData) throws Exception {

        logMe("@@@@@@@@@Method: findAndReplace - START");

        HSSFRow objRow      = null;
        HSSFCell objCell    = null;
        Iterator itrRows    = null;
        Iterator itrCells   = null;

        String lstrCellVal  = null;
        Object lobjNewData  = null;        

        try {

            itrRows = aObjSheet.rowIterator();
            while(itrRows.hasNext()){
                objRow   = (HSSFRow)itrRows.next();
                itrCells = objRow.cellIterator();
                while(itrCells.hasNext()){
                    objCell     = (HSSFCell)itrCells.next();
                    lstrCellVal = objCell.getStringCellValue();
                    if(lstrCellVal.indexOf(RPT_PLACE_HOLDER_IMAGE_PRERIX) == 0){
                        lobjNewData = amapData.get(lstrCellVal);
                        logMe("111111111Replace Field=[" + lstrCellVal + "] With New Value=["+ lobjNewData + "]");
                        //Remove Place Holder
                        objCell.setCellValue("");
                        if(lobjNewData != null){
                            addImage(aObjSheet, (ByteArrayOutputStream)lobjNewData, objRow.getRowNum(),objCell.getCellNum());
                        }
                    }else if(lstrCellVal.indexOf(RPT_PLACE_HOLDER_TEXT_PREFIX) == 0){
                        lobjNewData = amapData.get(lstrCellVal);
                        if(lobjNewData == null){
                            lobjNewData = "";
                        }
                        logMe("###########Replace Field=[" + lstrCellVal + "] With New Value=["+ lobjNewData + "]");
                        setCellValue(objCell, objCell.getCellType(), (String) lobjNewData);                       

                    } 

                }//while(itrCells.hasNext())
                 
            }//while(itrRows.hasNext())

        } catch (Exception ex) {
            logMe(ex);
        } finally {
            lstrCellVal = null;
            lobjNewData = null;
            objCell     = null;
            itrCells    = null;
            objRow      = null;
            itrRows     = null;
        }

        logMe("@@@@@@@@@Method: findAndReplace - END");

    } // end of findAndReplace

     /*
      * 1 - CELL TYPE
      * 0 - CELL POSITION FOR THE ABOVE COL CELL - IDX_COL_NUM
      */     
    public void setTableDataColInfo(int aColInfo[][]){
        mintDtlColTagInfo = (int[][])aColInfo.clone();
    }

    public int genDtlSection(HSSFSheet aObjSheet, Map amapData) throws Exception {

        logMe("@@@@@@@@@Method: genDtlSection - START");

        HSSFRow objRow      = null;
        HSSFCell objCell    = null;
        
        String lstrCellVal        = null;
        String[][] larrDtlData    = null;

        int    lintLastColumnNo   = 0;
        
        try {
            
            //Get all the Report Tags (header, footer, table start row, col positons)
            //getTagHolders(aObjSheet);
            
            //Get the Table data to be replaced...
            larrDtlData     = (String[][])amapData.get(KEY_DETAIL_DATA);
            //logMe("larrDtlData="+larrDtlData);
            if(larrDtlData == null || larrDtlData.length == 0){
            	return ZERO_RECORD;
            }
            
            //Total Data Col for Report
            mintTotDataRecord = larrDtlData.length;
            logMe("mintTotDataRecord="+mintTotDataRecord);
            try{
                mintTotDataCol              = (Integer)amapData.get(KEY_TOT_DATA_COL);
                logMe("mintTotDataCol="+mintTotDataCol);
                mintFirstDataRowPosition    = (Integer)amapData.get(KEY_TABLE_FIRST_ROW_POS);
                logMe("mintFirstDataRowPosition="+mintFirstDataRowPosition);
                mintFirstDataColPosition    = (Integer)amapData.get(KEY_TABLE_FIRST_COL_POS);
                logMe("mintFirstDataColPosition="+mintFirstDataColPosition);
                mintLastDataColPosition     = (Integer)amapData.get(KEY_TABLE_LAST_COL_POS);
                logMe("mintLastDataColPosition="+mintLastDataColPosition);
                mintTotTableCol             = (mintLastDataColPosition - mintFirstDataColPosition) + 1;
                logMe("mintTotTableCol="+mintTotTableCol);
            }catch(NumberFormatException ex){
                logMe(ex);
                return ZERO_RECORD;
            }            
            
            int iRptRow     = 0;
            int lintNewRowNo= 0;
            for(int iRow = 0; iRow < mintTotDataRecord; iRow++){                
                lintNewRowNo = mintFirstDataRowPosition + iRow;
                //logMe("lintNewRowNo="+lintNewRowNo);
                objRow = aObjSheet.getRow(lintNewRowNo);
                if(objRow == null){
                    //logMe("New Row Created="+lintNewRowNo);
                    objRow = aObjSheet.createRow(lintNewRowNo);
                }
                for(int iCol = 0; iCol < mintTotDataCol; iCol++){
                    //Data cell to be created...
                    int lintNewColNo = mintDtlColTagInfo[iCol][IDX_COL_NUM];                	
                    //logMe("lintNewColNo="+lintNewColNo);
                    objCell     = objRow.getCell(lintNewColNo);
                    if(objCell == null){
                        //logMe("New Column Created="+lintNewColNo);
                        objCell = objRow.createCell((short)lintNewColNo);
                    }
                    //logMe("objCell.getCellType()="+objCell.getCellType());
                    lstrCellVal = larrDtlData[iRow][iCol];
                    //logMe("lstrCellVal="+lstrCellVal);
                    if(lstrCellVal != null && lstrCellVal.length() >0) {
                        setCellValue(objCell, mintDtlColTagInfo[iCol][IDX_CELL_TYPE], lstrCellVal);
                    }
                }//for(int iCol = 0; iCol < lintTotalDataCol; iCol++)
                
            }//for(int iRow = 0; iRow < mintTotDataRecord; iRow++)
        } catch (Exception ex) {
            logMe(ex);
        } finally {
            lstrCellVal = null;
            larrDtlData = null;
            objCell     = null;
            objRow      = null;
        }

        logMe("@@@@@@@@@Method: genDtlSection - END");

        return lintLastColumnNo;

    } // end of genDtlSection - map param
    
    
    public int genDtlSection(HSSFSheet aObjSheet, Map amapData, ResultSet arsReportData) throws Exception {

    	logMe("@@@@@@@@@Method: genDtlSection - START");

        HSSFRow objRow      = null;
        HSSFCell objCell    = null;

        String lstrCellVal        = null;
        
        int    lintLastColumnNo   = 0;
        
        try {
            mintTotDataRecord = 0;
        	
            //Get all the Report Tags (header, footer, table start row, col positons)
            //getTagHolders(aObjSheet);
            	
            logMe("arsReportData="+arsReportData);
            //Check for null result set and Fetch the first record of the cursor
            if(arsReportData == null || (!arsReportData.next())){
            	return ZERO_RECORD;
            }
            
            try{
                 mintTotDataCol              = (Integer)amapData.get(KEY_TOT_DATA_COL);
                 logMe("mintTotDataCol="+mintTotDataCol);
                 mintFirstDataRowPosition    = (Integer)amapData.get(KEY_TABLE_FIRST_ROW_POS);
                 logMe("mintFirstDataRowPosition="+mintFirstDataRowPosition);
                 mintFirstDataColPosition    = (Integer)amapData.get(KEY_TABLE_FIRST_COL_POS);
                 logMe("mintFirstDataColPosition="+mintFirstDataColPosition);
                 mintLastDataColPosition     = (Integer)amapData.get(KEY_TABLE_LAST_COL_POS);
                 logMe("mintLastDataColPosition="+mintLastDataColPosition);
                 mintTotTableCol             = (mintLastDataColPosition - mintFirstDataColPosition) + 1;
                 logMe("mintTotTableCol="+mintTotTableCol);
            }catch(NumberFormatException ex){
                logMe(ex);
                return ZERO_RECORD;
            }
            
            int iRptRow      = 0;
            int lintNewRowNo = 0;            
            int iRow = -1;
            do {
            	iRow++;
            	
                lintNewRowNo = mintFirstDataRowPosition + iRow;
                //logMe("lintNewRowNo="+lintNewRowNo);
                
                objRow = aObjSheet.getRow(lintNewRowNo);
                
                for(int iCol = 0; iCol < mintTotDataCol; iCol++){
                    //Data cell to be created...
                    int lintNewColNo = mintDtlColTagInfo[iCol][IDX_COL_NUM];                	
                    //logMe("lintNewColNo="+lintNewColNo);
                    objCell     = objRow.getCell(lintNewColNo);
                    
                    lstrCellVal = arsReportData.getString(iCol + 1);
                    //logMe("lstrCellVal="+lstrCellVal);     
                    setCellValue(objCell, mintDtlColTagInfo[iCol][IDX_CELL_TYPE], lstrCellVal);                    
                }//for(int iCol = 0; iCol < lintTotalDataCol; iCol++)
                
            }while(arsReportData.next());

        } catch (Exception ex) {
            logMe(ex);
        } finally {
            lstrCellVal = null;
            objCell     = null;
            objRow      = null;
        }

        logMe("@@@@@@@@@Method: genDtlSection - END");

        return lintLastColumnNo;

    } // end of genDtlSection - result set param
    

     public void addImage(HSSFSheet aObjSheet, ByteArrayOutputStream aobjStream,int aintRowNum,int aintColumn)
                             throws IOException {

         HSSFPatriarch objPatriarch = aObjSheet.createDrawingPatriarch();

         HSSFClientAnchor objClientAnchor = new HSSFClientAnchor();

         HSSFCell cell1 = aObjSheet.getRow(aintRowNum).getCell(aintColumn);

         HSSFComment comment1 = objPatriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short)0, 0, (short)0 ,0));

         comment1.setString(new HSSFRichTextString("IMAGE"));

         cell1.setCellComment(comment1);

         HSSFComment comment=cell1.getCellComment();
         
         objClientAnchor.setCol1((short)aintColumn);
         objClientAnchor.setCol2((short)(aintColumn + 10));
         objClientAnchor.setRow1(aintRowNum);
         objClientAnchor.setRow2(aintRowNum+1);
         HSSFSimpleShape objShape = objPatriarch.createPicture(objClientAnchor, loadImage(aobjStream));
         objShape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_PICTURE);
     } // end of addImage

    public int loadImage(ByteArrayOutputStream lobjStream)
             throws IOException {

        int pictureIndex = mobjWorkbook.addPicture(lobjStream.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);
        logMe("pictureIndex"+pictureIndex);
        return pictureIndex;

    }// end of loadImage

     /*
      * sets excel data
      */
     public void setCellValue(HSSFCell xlsCell, int aintType, String astrValues){
         if(astrValues == null  || astrValues.length() == 0){
        return;
         }
         switch (aintType)
         {
             case CELL_TYPE_STRING:
                 xlsCell.setCellValue(astrValues);
                 break;
             case CELL_TYPE_DOUBLE:
                 xlsCell.setCellValue(Double.parseDouble(astrValues));
                 xlsCell.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat("(###0.00_);[Red](###0.00)"));
                 break;
             case CELL_TYPE_INT:
                 xlsCell.setCellValue(Double.parseDouble(astrValues));
                 xlsCell.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat("(###0);[Red](###0.00)"));
                 break;
             case CELL_TYPE_BLANK:
                 xlsCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
                 break;
             case CELL_TYPE_BOOLEAN:
                 xlsCell.setCellValue(Boolean.parseBoolean(astrValues));
                 break;
             case CELL_TYPE_DATE:
                 xlsCell.setCellValue(GlobalUtil.changeStrDateToDate(astrValues, GlobalConstants.DATE_FORMAT));
                 break;
             case CELL_TYPE_TIME:
                 xlsCell.setCellValue(astrValues);
                 break;                 
             default :
                 xlsCell.setCellValue(astrValues);        
         }
     }
         
    public void logMe(Object obj){
        Debug.logFramework(obj);
    }
    
    public void logMe(Exception ex){
        Debug.logFramework(ex);
    }

    public void removeSheet(String aSheetName){
        System.out.println("###########mobjWorkbook.getSheetIndex(aSheetName)------" + mobjWorkbook.getSheetIndex(aSheetName));
        mobjWorkbook.removeSheetAt(mobjWorkbook.getSheetIndex(aSheetName));
    }

    public static void main(String[] args){

        POIReportGenerator   objPOIReportGenerator  = null;

        String lstrTemplatePath = "D:\\ECLIPSE_JEE_TEST\\VAS_XLS\\TEMPLATES\\";
        String lstrTemplateName = "S1. Allocation_Analysis_Act_DP_week.xls";

        String lstrNewPath      = "D:\\ECLIPSE_JEE_TEST\\VAS_XLS\\RESULTS\\";
        
        String lstrUserID       = "NIIT01";
        String lstrSysdate      = "20100217101010";//YYYYMMDDHH24MISS
        String lstrReportDate   = "17/02/2010 10:30";//DD/YY/MM HH24:MI


        String lstrSheetName    = "Template";
        Map       mapFields     = null;
        
        Debug.logFramework("@@@@@@@@@Method: main - START");
        int totDataCol = 16;
        try{
            //step-1
            objPOIReportGenerator   = new POIReportGenerator();
            
            //step-2
            objPOIReportGenerator.openWorkBook(lstrTemplatePath, lstrTemplateName);
			
            //step-3
            /* Sample code for find place-holders and replace with values*/
            mapFields = new HashMap();
            
            mapFields.put(KEY_REPORT_ID,"VRP001");
            mapFields.put(KEY_TEMPLATE_SHEET,"Template");
            
            /* for header fields -total 9 data fields example
             * 0. col index of cell
             * 1. cell value
             * 2. row index of cell
             * 3. cell data type
             */
            String [][]larrHdrData = new String[9][4];
            larrHdrData[0] = new String []{"4",""+CELL_TYPE_INT,"0","2009"};
            larrHdrData[1] = new String []{"9",""+CELL_TYPE_INT,"0","2009"};
            larrHdrData[2] = new String []{"4",""+CELL_TYPE_INT,"1","38"};
            larrHdrData[3] = new String []{"9",""+CELL_TYPE_INT,"1","53"};
            larrHdrData[4] = new String []{"4",""+CELL_TYPE_INT,"2","53"};
            larrHdrData[5] = new String []{"9",""+CELL_TYPE_INT,"2","2010"};
            larrHdrData[6] = new String []{"4",""+CELL_TYPE_INT,"3","16"};
            larrHdrData[7] = new String []{"9",""+CELL_TYPE_INT,"3","15"};
            larrHdrData[8] = new String []{"4",""+CELL_TYPE_INT,"4","22"};
            
            mapFields.put(KEY_HEADER_DATA,larrHdrData);
            
            mapFields.put(KEY_TOT_DATA_COL,totDataCol);
            mapFields.put(KEY_TABLE_FIRST_ROW_POS,9);//index of first data row
            mapFields.put(KEY_TABLE_FIRST_COL_POS,0);//index of first data col
            mapFields.put(KEY_TABLE_LAST_COL_POS,15);//index of last data col
             /* dummy data for 2 records */
             String [][]larrDtlData = new String[2][totDataCol];
             larrDtlData[0][0] = "BP";
             larrDtlData[0][1] = "ANL";
             larrDtlData[0][2] = "PG";
             larrDtlData[0][3] = "Lae";
             larrDtlData[0][4] = "10";
             larrDtlData[0][5] = "10";
             larrDtlData[0][6] = "30";
             larrDtlData[0][7] = "4";
             larrDtlData[0][8] = "4";
             larrDtlData[0][9] = "12";
             larrDtlData[0][10] = "4";
             larrDtlData[0][11] = "2";
             larrDtlData[0][12] = "8";
             larrDtlData[0][13] = "9";
             larrDtlData[0][14] = "1";
             larrDtlData[0][15] = "11";
             
             larrDtlData[1][0] = "OSD";
             larrDtlData[1][1] = "ANL";
             larrDtlData[1][2] = "PG";
             larrDtlData[1][3] = "Lae";
             larrDtlData[1][4] = "20";
             larrDtlData[1][5] = "5";
             larrDtlData[1][6] = "30";
             larrDtlData[1][7] = "8";
             larrDtlData[1][8] = "2";
             larrDtlData[1][9] = "12";
             larrDtlData[1][10] = "0";
             larrDtlData[1][11] = "0";
             larrDtlData[1][12] = "0";
             larrDtlData[1][13] = "7";
             larrDtlData[1][14] = "2";
             larrDtlData[1][15] = "11";
            
            mapFields.put(KEY_DETAIL_DATA,larrDtlData);
            
            //step-4
             /* total 9 data columns with cell position and data type */
             int lintDtlColTagInfo[][]           = new int [totDataCol][2];
             
             //First Data Col Information
             lintDtlColTagInfo[0][IDX_COL_NUM]   = 0;
             lintDtlColTagInfo[0][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //Second Data Col Information
             lintDtlColTagInfo[1][IDX_COL_NUM]   = 1;
             lintDtlColTagInfo[1][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //Third Data Col Information
             lintDtlColTagInfo[2][IDX_COL_NUM]   = 2;
             lintDtlColTagInfo[2][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //Fourth Data Col Information
             lintDtlColTagInfo[3][IDX_COL_NUM]   = 3;
             lintDtlColTagInfo[3][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //Fifth Data Col Information
             lintDtlColTagInfo[4][IDX_COL_NUM]   = 4;
             lintDtlColTagInfo[4][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //Sixth Data Col Information
             lintDtlColTagInfo[5][IDX_COL_NUM]   = 5;
             lintDtlColTagInfo[5][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //Seventh Data Col Information
             lintDtlColTagInfo[6][IDX_COL_NUM]   = 6;
             lintDtlColTagInfo[6][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //Eight Data Col Information
             lintDtlColTagInfo[7][IDX_COL_NUM]   = 7;
             lintDtlColTagInfo[7][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //Ninth Data Col Information
             lintDtlColTagInfo[8][IDX_COL_NUM]   = 8;
             lintDtlColTagInfo[8][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //Tenth Data Col Information
             lintDtlColTagInfo[9][IDX_COL_NUM]   = 9;
             lintDtlColTagInfo[9][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //11th Data Col Information
             lintDtlColTagInfo[10][IDX_COL_NUM]   = 10;
             lintDtlColTagInfo[10][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //12th Data Col Information
             lintDtlColTagInfo[11][IDX_COL_NUM]   = 11;
             lintDtlColTagInfo[11][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //13th Data Col Information
             lintDtlColTagInfo[12][IDX_COL_NUM]   = 12;
             lintDtlColTagInfo[12][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //14th Data Col Information
             lintDtlColTagInfo[13][IDX_COL_NUM]   = 13;
             lintDtlColTagInfo[13][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //15th Data Col Information
             lintDtlColTagInfo[14][IDX_COL_NUM]   = 14;
             lintDtlColTagInfo[14][IDX_CELL_TYPE] = CELL_TYPE_INT;
             //Last Data Col Information
             lintDtlColTagInfo[15][IDX_COL_NUM]   = 15;
             lintDtlColTagInfo[15][IDX_CELL_TYPE] = CELL_TYPE_INT;
            
            //step-5
            objPOIReportGenerator.setTableDataColInfo(lintDtlColTagInfo);
            
	    //step-6
            objPOIReportGenerator.generateReport(mapFields);
                  
            //step-7
            //finally save the workbook
            String lstrFinalFileName = objPOIReportGenerator.saveWorkBook(lstrNewPath,lstrTemplateName,lstrUserID,lstrSysdate);
            
            Debug.logFramework("@@@@@@@@@Method: main - lstrFinalFileName="+lstrFinalFileName);
        } catch(Exception ex) {
            Debug.logFramework(ex);
        }

        Debug.logFramework("@@@@@@@@@Method: main - END");

    }//end of main

}//end of class POIReportGenerator

