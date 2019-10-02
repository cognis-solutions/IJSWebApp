package com.rclgroup.dolphin.ijs.web.service;

//import com.rclgroup.dolphin.ijs.web.common.ReportTagConstant;
//import com.rclgroup.dolphin.ijs.web.common.GlobalConstants;
//
//import com.rclgroup.dolphin.ijs.web.common.GlobalUtil;


import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.common.excel.POIReportGenerator;
import com.rclgroup.dolphin.ijs.web.common.excel.ReportTagConstant;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractSearchDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractDownloadDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractSearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractSearchUIM;
import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSearchParamVO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


import java.util.List;
import java.util.Map;



import org.apache.poi.hssf.usermodel.HSSFWorkbook;




public class IjsContractDownloadSvc implements ReportTagConstant {

    /* class level variables starts here... */	
	

   
    private int     mintFirstDataColPosition    = 0;
    //Last Data Column No
    private int     mintLastDataColPosition     = 0;
    //Total Columns - Start to End Column in the Report template in the table section
    private int     mintTotTableCol             = (mintLastDataColPosition - mintFirstDataColPosition)+1;
    
    //Template Sheet No for temp use...
   
    private final  String DATE_FORMAT    = "DD/MM/YYYY";
    IjsContractSearchDao contractDao = null;
    private final int FIRST_ROW_POS=1;
    private final int TABLE_FIRST_COL_POS=0;
    private final int TABLE_COST_LAST_COL_POS=48;
    private final int TABLE_BILL_LAST_COL_POS=39;
    private final String COST_RATE="COST_RATE";
    private final String BILL_RATE="BILL_RATE";
    public IjsContractDownloadSvc() {
       
    }
   
    
    public String[] prepareDataForDownload(List<IjsContractDownloadDTO> costContractsForDownload,List<IjsContractDownloadDTO> billContractsForDownload,String templatePath) throws Exception {
      // populate Cost Rate
      String lstrCostTemplateName = "IJS_Contract_Download_Upload_Template_Cost.xls";
      String lstrBillTemplateName = "IJS_Contract_Download_Upload_Template_Bill.xls";
        String lstrNewPath      = templatePath+"RESULTS/";
      String[] downloadedFiles=new String[3];
        downloadedFiles[0]=lstrNewPath;
      if(costContractsForDownload!=null && costContractsForDownload.size()>0){
         downloadedFiles[1]= generateExcelForContractRate(costContractsForDownload,templatePath,COST_RATE,lstrCostTemplateName);
      }
      if(billContractsForDownload!=null && billContractsForDownload.size()>0){
         downloadedFiles[2]=generateExcelForContractRate(billContractsForDownload,templatePath,BILL_RATE,lstrBillTemplateName);
      }
      return downloadedFiles;
    }
    
    private String generateExcelForContractRate(List<IjsContractDownloadDTO> costContractsForDownload,String templatePath,String reportType,String templateName) throws Exception{
        Map mapFields     = new HashMap();
        POIReportGenerator reportGenerator=new POIReportGenerator();
        mapFields.put(KEY_TEMPLATE_SHEET,"Template");
       // String lstrTemplatePath = "D:\\Ashish\\RCL_SVN_Local_New\\IJS\\DownloadExcel\\";
        String lstrTemplatePath = templatePath;
       
      //  String lstrNewPath      = "D:\\Ashish\\RCL_SVN_Local_New\\IJS\\DownloadExcel\\RESULTS\\";
        String lstrNewPath      = templatePath+"RESULTS/";
        String[][] larrDtlData    = new String[costContractsForDownload.size()][TABLE_COST_LAST_COL_POS+1];
       // if(COST_RATE.equals(reportType)){
            reportGenerator.openWorkBook(lstrTemplatePath, templateName);
       // }
       
        int i=0;
        for(IjsContractDownloadDTO contractDto:costContractsForDownload){
                larrDtlData[i][0] = contractDto.getVendorCode();
                larrDtlData[i][1] = contractDto.getVendorName();
                larrDtlData[i][2] = contractDto.getContractId();
                larrDtlData[i][3] = contractDto.getRoutingId().toString();
                larrDtlData[i][4] = contractDto.getContractStartDate();
                larrDtlData[i][5] = contractDto.getContractEndDate();
                larrDtlData[i][6] = IjsHelper.getTransMode(contractDto.getTransMode());
                larrDtlData[i][7] = IjsHelper.getContractStatus(contractDto.getStatus());
                larrDtlData[i][8] = contractDto.getPaymentFsc();
                larrDtlData[i][9] = contractDto.getContractCurrency();
                larrDtlData[i][10] =contractDto.getPriority().toString();
                larrDtlData[i][11] = IjsHelper.getLocationType(contractDto.getFromLocType());
                larrDtlData[i][12] = contractDto.getFromLocation();
                larrDtlData[i][13] = contractDto.getFromTerminal();
                larrDtlData[i][14] = IjsHelper.getLocationType(contractDto.getToLocType());
                larrDtlData[i][15] = contractDto.getToLocation();
                larrDtlData[i][16] = contractDto.getToTerminal();
                larrDtlData[i][17] = contractDto.getDays().toString();
                larrDtlData[i][18] = contractDto.getHours().toString();
                larrDtlData[i][19] = contractDto.getDistance().toString();
                larrDtlData[i][20] = contractDto.getContractUom();
                larrDtlData[i][21] = contractDto.getExemptedCustomer();
            if(COST_RATE.equals(reportType)){    
                larrDtlData[i][22] = contractDto.getTerm();
                larrDtlData[i][23] = contractDto.getRateStartDate();
                larrDtlData[i][24] = contractDto.getRateEndDate();
                larrDtlData[i][25] = contractDto.getService();
                larrDtlData[i][26] = contractDto.getVesselCode();
                String rateBasis=contractDto.getRateBasis();
                if("L".equals(rateBasis) || "B".equals(rateBasis)){
                	larrDtlData[i][27] = "*";
                }else{
                	larrDtlData[i][27] = contractDto.getMtOrLaden();
                }
                
                larrDtlData[i][28] = IjsHelper.getRateBaseValue(contractDto.getRateBasis());
                larrDtlData[i][29] = IjsHelper.getEqCatelog(contractDto.getEqCatq());
                larrDtlData[i][30] = IjsHelper.getRateStatus(contractDto.getRateStatus());
               
//            if(COST_RATE.equals(reportType)){
                //larrDtlData[i][30] = contractDto.getTerm();
                larrDtlData[i][31] = contractDto.getChargeCode();
                larrDtlData[i][32] = IjsHelper.getClacMethodType(contractDto.getCalcMethod());
                larrDtlData[i][33] = contractDto.getEqType();
                larrDtlData[i][34] = contractDto.getUpto().toString();
                larrDtlData[i][35] = (contractDto.getUom()!=null && "K".equals(contractDto.getUom()))?"KILO":contractDto.getUom();
                //larrDtlData[i][35] = contractDto.getImpOrExp();
                larrDtlData[i][36] = IjsHelper.getSpHandlingValue(contractDto.getSplHandling()) ;
                larrDtlData[i][37] = contractDto.getCurrency();
                larrDtlData[i][38] = contractDto.getPortClassCode();
                larrDtlData[i][39] = contractDto.getImdgDetails();
                larrDtlData[i][40] = contractDto.getOogSetup();
                larrDtlData[i][41] = contractDto.getSpCustomers();
                if("L".equals(rateBasis) || "B".equals(rateBasis)){
                	larrDtlData[i][42] = "";
                }else{
                	larrDtlData[i][42] = RutFormatting.getDoubleToDecimalFormat(contractDto.getRate20(),null);
                }
                if("L".equals(rateBasis) || "B".equals(rateBasis)){
                	larrDtlData[i][43] = "";
                }else{
                	larrDtlData[i][43] = RutFormatting.getDoubleToDecimalFormat(contractDto.getRate40(),null);
                }
                if("L".equals(rateBasis) || "B".equals(rateBasis)){
                	larrDtlData[i][44] = "";
                }else{
                	larrDtlData[i][44] = RutFormatting.getDoubleToDecimalFormat(contractDto.getRate45(),null);
                }
                //larrDtlData[i][42] = contractDto.getPerTrip();
                
                if("L".equals(rateBasis) || "B".equals(rateBasis)){
                	 larrDtlData[i][45] = RutFormatting.getStringToDecimalFormat(contractDto.getLumpSum(),null);
                }else{
                	larrDtlData[i][45] ="";
                }
               
                larrDtlData[i][46] = contractDto.getCostRateSequenceNum().toString();
                larrDtlData[i][47] = contractDto.getDetailSeqNum();
                
            }else{
                larrDtlData[i][22] = contractDto.getRateStartDate();
                larrDtlData[i][23] = contractDto.getRateEndDate();
                larrDtlData[i][24] = contractDto.getService();
                larrDtlData[i][25] = contractDto.getMtOrLaden();
                larrDtlData[i][26] = IjsHelper.getRateBaseValue(contractDto.getRateBasis());
                larrDtlData[i][27] = IjsHelper.getEqCatelog(contractDto.getEqCatq());
                larrDtlData[i][28] = IjsHelper.getRateStatus(contractDto.getRateStatus());
                larrDtlData[i][29] = IjsHelper.getClacMethodType(contractDto.getCalcMethod());
                larrDtlData[i][30] = contractDto.getEqType();
                larrDtlData[i][31] = contractDto.getUpto().toString();
                larrDtlData[i][32] = (contractDto.getUom()!=null && "K".equals(contractDto.getUom()))?"KILO":contractDto.getUom();
                larrDtlData[i][33] = contractDto.getCurrency();
                larrDtlData[i][34] = RutFormatting.getDoubleToDecimalFormat(contractDto.getRate20(),null);
                larrDtlData[i][35] = RutFormatting.getDoubleToDecimalFormat(contractDto.getRate40(),null);
                larrDtlData[i][36] = RutFormatting.getDoubleToDecimalFormat(contractDto.getRate45(),null);
                larrDtlData[i][37] = contractDto.getCostRateSequenceNum().toString(); 
                larrDtlData[i][38] = contractDto.getDetailSeqNum();
            }
            
           i++;
           // larrDtlData[i][45] = contractDto.getb;
        }
            //step-4
             /* total 9 data columns with cell position and data type */
             int lintDtlColTagInfo[][]           = new int [TABLE_COST_LAST_COL_POS+1][2];
             
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
             lintDtlColTagInfo[4][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //Sixth Data Col Information
             lintDtlColTagInfo[5][IDX_COL_NUM]   = 5;
             lintDtlColTagInfo[5][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //Seventh Data Col Information
             lintDtlColTagInfo[6][IDX_COL_NUM]   = 6;
             lintDtlColTagInfo[6][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //Eight Data Col Information
             lintDtlColTagInfo[7][IDX_COL_NUM]   = 7;
             lintDtlColTagInfo[7][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //Ninth Data Col Information
             lintDtlColTagInfo[8][IDX_COL_NUM]   = 8;
             lintDtlColTagInfo[8][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //Tenth Data Col Information
             lintDtlColTagInfo[9][IDX_COL_NUM]   = 9;
             lintDtlColTagInfo[9][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //11th Data Col Information
             lintDtlColTagInfo[10][IDX_COL_NUM]   = 10;
             lintDtlColTagInfo[10][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //12th Data Col Information
             lintDtlColTagInfo[11][IDX_COL_NUM]   = 11;
             lintDtlColTagInfo[11][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //13th Data Col Information
             lintDtlColTagInfo[12][IDX_COL_NUM]   = 12;
             lintDtlColTagInfo[12][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //14th Data Col Information
             lintDtlColTagInfo[13][IDX_COL_NUM]   = 13;
             lintDtlColTagInfo[13][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //15th Data Col Information
             lintDtlColTagInfo[14][IDX_COL_NUM]   = 14;
             lintDtlColTagInfo[14][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             //Last Data Col Information
             lintDtlColTagInfo[15][IDX_COL_NUM]   = 15;
             lintDtlColTagInfo[15][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             
            lintDtlColTagInfo[16][IDX_COL_NUM]   = 16;
            lintDtlColTagInfo[16][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[17][IDX_COL_NUM]   = 17;
            lintDtlColTagInfo[17][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[18][IDX_COL_NUM]   = 18;
            lintDtlColTagInfo[18][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[19][IDX_COL_NUM]   = 19;
            lintDtlColTagInfo[19][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[20][IDX_COL_NUM]   = 20;
            lintDtlColTagInfo[20][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[21][IDX_COL_NUM]   = 21;
            lintDtlColTagInfo[21][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[22][IDX_COL_NUM]   = 22;
            lintDtlColTagInfo[22][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[23][IDX_COL_NUM]   = 23;
            lintDtlColTagInfo[23][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[24][IDX_COL_NUM]   = 24;
            lintDtlColTagInfo[24][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[25][IDX_COL_NUM]   = 25;
            lintDtlColTagInfo[25][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[26][IDX_COL_NUM]   = 26;
            lintDtlColTagInfo[26][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[27][IDX_COL_NUM]   = 27;
            lintDtlColTagInfo[27][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[28][IDX_COL_NUM]   = 28;
            lintDtlColTagInfo[28][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[29][IDX_COL_NUM]   = 29;
            lintDtlColTagInfo[29][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[30][IDX_COL_NUM]   = 30;
            lintDtlColTagInfo[30][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[31][IDX_COL_NUM]   = 31;
            lintDtlColTagInfo[31][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[32][IDX_COL_NUM]   = 32;
            lintDtlColTagInfo[32][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[33][IDX_COL_NUM]   = 33;
            lintDtlColTagInfo[33][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[34][IDX_COL_NUM]   = 34;
            lintDtlColTagInfo[34][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[35][IDX_COL_NUM]   = 35;
            lintDtlColTagInfo[35][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[36][IDX_COL_NUM]   = 36;
            lintDtlColTagInfo[36][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[37][IDX_COL_NUM]   = 37;
            lintDtlColTagInfo[37][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            lintDtlColTagInfo[38][IDX_COL_NUM]   = 38;
            lintDtlColTagInfo[38][IDX_CELL_TYPE] = CELL_TYPE_STRING;
             if(COST_RATE.equals(reportType)){
               lintDtlColTagInfo[39][IDX_COL_NUM]   = 39;
               lintDtlColTagInfo[39][IDX_CELL_TYPE] = CELL_TYPE_STRING;
               lintDtlColTagInfo[40][IDX_COL_NUM]   = 40;
               lintDtlColTagInfo[40][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            
//            if(COST_RATE.equals(reportType)){
                lintDtlColTagInfo[41][IDX_COL_NUM]   = 41;
                lintDtlColTagInfo[41][IDX_CELL_TYPE] = CELL_TYPE_STRING;
                lintDtlColTagInfo[42][IDX_COL_NUM]   = 42;
                lintDtlColTagInfo[42][IDX_CELL_TYPE] = CELL_TYPE_STRING;
                lintDtlColTagInfo[43][IDX_COL_NUM]   = 43;
                lintDtlColTagInfo[43][IDX_CELL_TYPE] = CELL_TYPE_STRING;
                lintDtlColTagInfo[44][IDX_COL_NUM]   = 44;
                lintDtlColTagInfo[44][IDX_CELL_TYPE] = CELL_TYPE_STRING;
                lintDtlColTagInfo[45][IDX_COL_NUM]   = 45;
                lintDtlColTagInfo[45][IDX_CELL_TYPE] = CELL_TYPE_STRING;
                lintDtlColTagInfo[46][IDX_COL_NUM]   = 46;
                lintDtlColTagInfo[46][IDX_CELL_TYPE] = CELL_TYPE_STRING;
                lintDtlColTagInfo[47][IDX_COL_NUM]   = 47;
                lintDtlColTagInfo[47][IDX_CELL_TYPE] = CELL_TYPE_STRING;
                lintDtlColTagInfo[48][IDX_COL_NUM]   = 48;
                lintDtlColTagInfo[48][IDX_CELL_TYPE] = CELL_TYPE_STRING;
            }
            
        mapFields.put(KEY_DETAIL_DATA,larrDtlData);
        if(COST_RATE.equals(reportType)){
            mapFields.put(KEY_TOT_DATA_COL,48);
            mapFields.put(KEY_TABLE_LAST_COL_POS,TABLE_COST_LAST_COL_POS);//index of last data col
        }else{
            mapFields.put(KEY_TOT_DATA_COL,39);
            mapFields.put(KEY_TABLE_LAST_COL_POS,TABLE_BILL_LAST_COL_POS);//index of last data col 
        }
       
        mapFields.put(KEY_TABLE_FIRST_ROW_POS,FIRST_ROW_POS);//index of first data row
        mapFields.put(KEY_TABLE_FIRST_COL_POS,TABLE_FIRST_COL_POS);//index of first data col
        
        
         //step-5
         reportGenerator.setTableDataColInfo(lintDtlColTagInfo);
         
         //step-6
         reportGenerator.generateReport(mapFields);
               
         //step-7
         //finally save the workbook
          Date date= new Date();
          SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");  
          String strDate= formatter.format(date);  
          System.out.println(strDate);  
          return reportGenerator.saveWorkBook(lstrNewPath,templateName,"",strDate);
    }
//    private void generateExcelForContractBillRate(List<IjsContractDownloadDTO> billContractsForDownload) throws Exception{
//        Map mapFields     = new HashMap();
//        POIReportGenerator reportGenerator=new POIReportGenerator();
//        mapFields.put(KEY_TEMPLATE_SHEET,"Template");
//        String lstrTemplatePath = "D:\\Ashish\\RCL_SVN_Local_New\\IJS\\DownloadExcel\\";
//        String lstrTemplateName = "IJS_Contract_Rate_Download_Upload_Template.xls";
//        String lstrNewPath      = "D:\\Ashish\\RCL_SVN_Local_New\\IJS\\DownloadExcel\\RESULTS\\";
//        String[][] larrDtlData    = new String[billContractsForDownload.size()][TABLE_BILL_LAST_COL_POS+1];
//        reportGenerator.openWorkBook(lstrTemplatePath, lstrTemplateName);
//        int i=0;
//        for(IjsContractDownloadDTO contractDto:billContractsForDownload){
//           larrDtlData[i][0] = contractDto.getVendorCode();
//           larrDtlData[i][1] = contractDto.getVendorName();
//           larrDtlData[i][2] = contractDto.getContractId();
//           larrDtlData[i][3] = contractDto.getRoutingId().toString();
//           larrDtlData[i][4] = contractDto.getContractStartDate();
//           larrDtlData[i][5] = contractDto.getContractEndDate();
//           larrDtlData[i][6] = contractDto.getTransMode();
//           larrDtlData[i][7] = contractDto.getStatus();
//           larrDtlData[i][8] = contractDto.getPaymentFsc();
//           larrDtlData[i][9] = contractDto.getContractCurrency();
//           larrDtlData[i][10] =contractDto.getPriority().toString();
//           larrDtlData[i][11] = contractDto.getFromLocType();
//           larrDtlData[i][12] = contractDto.getFromLocation();
//           larrDtlData[i][13] = contractDto.getFromTerminal();
//           larrDtlData[i][14] = contractDto.getToLocType();
//           larrDtlData[i][15] = contractDto.getToLocation();
//           larrDtlData[i][16] = contractDto.getToTerminal();
//           larrDtlData[i][17] = contractDto.getDays().toString();
//           larrDtlData[i][18] = contractDto.getHours().toString();
//           larrDtlData[i][19] = contractDto.getDistance().toString();
//           larrDtlData[i][20] = contractDto.getContractUom();
//           larrDtlData[i][21] = "";
//           larrDtlData[i][22] = contractDto.getRateStartDate();
//           larrDtlData[i][23] = contractDto.getRateEndDate();
//           larrDtlData[i][24] = contractDto.getService();
//           larrDtlData[i][25] = contractDto.getMtOrLaden();
//           larrDtlData[i][26] = contractDto.getRateBasis();
//           larrDtlData[i][27] = contractDto.getEqCatq();
//           larrDtlData[i][28] = contractDto.getRateStatus();
//           larrDtlData[i][29] = contractDto.getChargeCode();
//           larrDtlData[i][30] = contractDto.getTerm();
//           larrDtlData[i][31] = contractDto.getCalcMethod();
//           larrDtlData[i][32] = contractDto.getEqType();
//           larrDtlData[i][33] = contractDto.getUpto().toString();
//           larrDtlData[i][34] = contractDto.getUom();
//           larrDtlData[i][35] = contractDto.getCurrency();
//           larrDtlData[i][36] = contractDto.getRate20().toString();
//           larrDtlData[i][37] = contractDto.getRate40().toString();
//           larrDtlData[i][38] = contractDto.getRate45().toString();
//           larrDtlData[i][39] =contractDto.getCostRateSequenceNum().toString();
//           
//           
//           // larrDtlData[i][45] = contractDto.getb;
//        }
//            //step-4
//             /* total 9 data columns with cell position and data type */
//             int lintDtlColTagInfo[][]           = new int [TABLE_BILL_LAST_COL_POS+1][2];
//             
//             //First Data Col Information
//             lintDtlColTagInfo[0][IDX_COL_NUM]   = 0;
//             lintDtlColTagInfo[0][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //Second Data Col Information
//             lintDtlColTagInfo[1][IDX_COL_NUM]   = 1;
//             lintDtlColTagInfo[1][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //Third Data Col Information
//             lintDtlColTagInfo[2][IDX_COL_NUM]   = 2;
//             lintDtlColTagInfo[2][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //Fourth Data Col Information
//             lintDtlColTagInfo[3][IDX_COL_NUM]   = 3;
//             lintDtlColTagInfo[3][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //Fifth Data Col Information
//             lintDtlColTagInfo[4][IDX_COL_NUM]   = 4;
//             lintDtlColTagInfo[4][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //Sixth Data Col Information
//             lintDtlColTagInfo[5][IDX_COL_NUM]   = 5;
//             lintDtlColTagInfo[5][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //Seventh Data Col Information
//             lintDtlColTagInfo[6][IDX_COL_NUM]   = 6;
//             lintDtlColTagInfo[6][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //Eight Data Col Information
//             lintDtlColTagInfo[7][IDX_COL_NUM]   = 7;
//             lintDtlColTagInfo[7][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //Ninth Data Col Information
//             lintDtlColTagInfo[8][IDX_COL_NUM]   = 8;
//             lintDtlColTagInfo[8][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //Tenth Data Col Information
//             lintDtlColTagInfo[9][IDX_COL_NUM]   = 9;
//             lintDtlColTagInfo[9][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //11th Data Col Information
//             lintDtlColTagInfo[10][IDX_COL_NUM]   = 10;
//             lintDtlColTagInfo[10][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //12th Data Col Information
//             lintDtlColTagInfo[11][IDX_COL_NUM]   = 11;
//             lintDtlColTagInfo[11][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //13th Data Col Information
//             lintDtlColTagInfo[12][IDX_COL_NUM]   = 12;
//             lintDtlColTagInfo[12][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //14th Data Col Information
//             lintDtlColTagInfo[13][IDX_COL_NUM]   = 13;
//             lintDtlColTagInfo[13][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //15th Data Col Information
//             lintDtlColTagInfo[14][IDX_COL_NUM]   = 14;
//             lintDtlColTagInfo[14][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             //Last Data Col Information
//             lintDtlColTagInfo[15][IDX_COL_NUM]   = 15;
//             lintDtlColTagInfo[15][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//             
//            lintDtlColTagInfo[16][IDX_COL_NUM]   = 16;
//            lintDtlColTagInfo[16][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[17][IDX_COL_NUM]   = 17;
//            lintDtlColTagInfo[17][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[18][IDX_COL_NUM]   = 18;
//            lintDtlColTagInfo[18][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[19][IDX_COL_NUM]   = 19;
//            lintDtlColTagInfo[19][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[20][IDX_COL_NUM]   = 20;
//            lintDtlColTagInfo[20][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[21][IDX_COL_NUM]   = 21;
//            lintDtlColTagInfo[21][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[22][IDX_COL_NUM]   = 22;
//            lintDtlColTagInfo[22][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[23][IDX_COL_NUM]   = 23;
//            lintDtlColTagInfo[23][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[24][IDX_COL_NUM]   = 24;
//            lintDtlColTagInfo[24][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[25][IDX_COL_NUM]   = 25;
//            lintDtlColTagInfo[25][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[26][IDX_COL_NUM]   = 26;
//            lintDtlColTagInfo[26][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[27][IDX_COL_NUM]   = 27;
//            lintDtlColTagInfo[27][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[28][IDX_COL_NUM]   = 28;
//            lintDtlColTagInfo[28][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[29][IDX_COL_NUM]   = 29;
//            lintDtlColTagInfo[29][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[30][IDX_COL_NUM]   = 30;
//            lintDtlColTagInfo[30][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[31][IDX_COL_NUM]   = 31;
//            lintDtlColTagInfo[31][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[32][IDX_COL_NUM]   = 32;
//            lintDtlColTagInfo[32][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[33][IDX_COL_NUM]   = 33;
//            lintDtlColTagInfo[33][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[34][IDX_COL_NUM]   = 34;
//            lintDtlColTagInfo[34][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[35][IDX_COL_NUM]   = 35;
//            lintDtlColTagInfo[35][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[36][IDX_COL_NUM]   = 36;
//            lintDtlColTagInfo[36][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[37][IDX_COL_NUM]   = 37;
//            lintDtlColTagInfo[37][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[38][IDX_COL_NUM]   = 38;
//            lintDtlColTagInfo[38][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//            lintDtlColTagInfo[39][IDX_COL_NUM]   = 39;
//            lintDtlColTagInfo[39][IDX_CELL_TYPE] = CELL_TYPE_STRING;
//           
//            
//        mapFields.put(KEY_DETAIL_DATA,larrDtlData);
//        mapFields.put(KEY_TOT_DATA_COL,39);
//        mapFields.put(KEY_TABLE_FIRST_ROW_POS,FIRST_ROW_POS);//index of first data row
//        mapFields.put(KEY_TABLE_FIRST_COL_POS,TABLE_FIRST_COL_POS);//index of first data col
//        mapFields.put(KEY_TABLE_LAST_COL_POS,TABLE_BILL_LAST_COL_POS);//index of last data col
//        
//         //step-5
//         reportGenerator.setTableDataColInfo(lintDtlColTagInfo);
//         
//         //step-6
//         reportGenerator.generateReport(mapFields);
//               
//         //step-7
//         //finally save the workbook
//          Date date= new Date();
//          SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");  
//          String strDate= formatter.format(date);  
//          System.out.println(strDate);  
//         String lstrFinalFileName = reportGenerator.saveWorkBook(lstrNewPath,templateName,"",strDate);
//    }
    public IjsContractSearchUIM searchContract(IjsSearchParamVO contractParam,String userInfo) throws IJSException{
        IjsContractSearchParamVO contractParamVO=(IjsContractSearchParamVO)contractParam.getSearchScreenParam();
        List<IjsContractSearchDTO> lstContracts=contractDao.findContracts(contractParam.getTransMode(),contractParam.getDateRange(),
                                                                          userInfo,contractParamVO);
        //return transformDtoToUim(lstContracts);
        return null;
    }
   

}//end of class POIReportGenerator