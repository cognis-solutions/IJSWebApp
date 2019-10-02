package com.rclgroup.dolphin.ijs.web.service;

import com.rclgroup.dolphin.ijs.web.common.excel.POIReportGenerator;
import com.rclgroup.dolphin.ijs.web.common.excel.ReportTagConstant;
import com.rclgroup.dolphin.ijs.web.dao.IjsMaintainJOSearchDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsMaintainJOSearchContDetailDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsMaintainJoDownloadDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsMaintainJOSearchUIM;
import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSearchParamVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IjsMaintainJoDownloadSvc implements ReportTagConstant {

    /* class level variables starts here... */


    private int mintFirstDataColPosition = 0;
    //Last Data Column No
    private int mintLastDataColPosition = 0;
    //Total Columns - Start to End Column in the Report template in the table section
    private int mintTotTableCol = 
        (mintLastDataColPosition - mintFirstDataColPosition) + 1;

    //Template Sheet No for temp use...

    private final String DATE_FORMAT = "DD/MM/YYYY";
    IjsMaintainJOSearchDao contractDao = null;
    private final int FIRST_ROW_POS = 1;
    private final int TABLE_FIRST_COL_POS = 0;
    private final int TABLE_COST_LAST_COL_POS = 39;
    private final int TABLE_BILL_LAST_COL_POS = 38;
    private final String COST_RATE = "COST_RATE";
    private final String BILL_RATE = "BILL_RATE";
    private final String JO_MAINTAIN_DOWNLOAD_FILE_NAME="IJS_JO_Maintenance_Download_Template.xls";
    private final String JO_INQUIRY_DOWNLOAD_FILE_NAME="IJS_JO_Inquiry_Download_Template.xls";
    private final String INQUIRY_DOWNLOAD_TYPE="joinquiry";

    public IjsMaintainJoDownloadSvc() {

    }


    public String[] prepareDataForDownload(List<IjsMaintainJoDownloadDTO> costContractsForDownload,
                                           String templatePath,String downloadType) throws Exception {
    	String lstrCostTemplateName = INQUIRY_DOWNLOAD_TYPE.equalsIgnoreCase(downloadType)?JO_INQUIRY_DOWNLOAD_FILE_NAME:JO_MAINTAIN_DOWNLOAD_FILE_NAME;
        String lstrNewPath = templatePath + "RESULTS/";
        String[] downloadedFiles = new String[2];
        downloadedFiles[0] = lstrNewPath;
        if (costContractsForDownload != null && 
            costContractsForDownload.size() > 0) {
            downloadedFiles[1] = 
                    generateExcelForContractRate(costContractsForDownload, 
                                                 templatePath, COST_RATE, 
                                                 lstrCostTemplateName);
        }
        return downloadedFiles;
    }

    private String generateExcelForContractRate(List<IjsMaintainJoDownloadDTO> maintainForDownload, 
                                                String templatePath, 
                                                String reportType, 
                                                String templateName) throws Exception {
        Map mapFields = new HashMap();
        POIReportGenerator reportGenerator = new POIReportGenerator();
        mapFields.put(KEY_TEMPLATE_SHEET, "Template");
        String lstrTemplatePath = templatePath;

        String lstrNewPath = templatePath + "RESULTS/";
        String[][] larrDtlData = 
                new String[maintainForDownload.size()][TABLE_COST_LAST_COL_POS +1];
        reportGenerator.openWorkBook(lstrTemplatePath, templateName);
        // }

        int i = 0;
        for (IjsMaintainJoDownloadDTO maintainDto: maintainForDownload) {
            larrDtlData[i][0] = maintainDto.getJoNumber();
            larrDtlData[i][1] = maintainDto.getActivity_dt();
            larrDtlData[i][2] = maintainDto.getBkgOrBLNo();
            larrDtlData[i][3] = maintainDto.getEqNumber();
            larrDtlData[i][4] = maintainDto.getEquiptState();
            larrDtlData[i][5] = maintainDto.getPurchase_term();
            larrDtlData[i][6] = maintainDto.getVendorID();
            larrDtlData[i][7] = maintainDto.getContract_id();
            larrDtlData[i][8] = maintainDto.getPriority();
            larrDtlData[i][9] = maintainDto.getVendorName();
            larrDtlData[i][10] = maintainDto.getFromLocType();
            larrDtlData[i][11] = maintainDto.getFromLoaction();
            larrDtlData[i][12] = maintainDto.getFromTerminal();
            larrDtlData[i][13] = maintainDto.getToLocType();
            larrDtlData[i][14] = maintainDto.getToLocation();
            larrDtlData[i][15] = maintainDto.getToTerminal();
            larrDtlData[i][16] = maintainDto.getSOCorCOC();
            larrDtlData[i][17] = maintainDto.getContSize() != null ? maintainDto.getContSize().toString() : "";
            larrDtlData[i][18] = maintainDto.getContType();
            larrDtlData[i][19] = maintainDto.getSpecial_handling();
            larrDtlData[i][20] = maintainDto.getContEmptyOrLaden();
            larrDtlData[i][21] = maintainDto.getCurrency();
            larrDtlData[i][22] = RutFormatting.getStringToDecimalFormat(maintainDto.getSizeTypeAmt(), null); 
            larrDtlData[i][23] = RutFormatting.getStringToDecimalFormat(maintainDto.getSizeTypeUSDAmt(),null);
            larrDtlData[i][24] = RutFormatting.getStringToDecimalFormat(maintainDto.getBkgBlAmt(), null); 
            larrDtlData[i][25] = RutFormatting.getStringToDecimalFormat(maintainDto.getBkgBlUSDAmt(),null);
            larrDtlData[i][26] = RutFormatting.getStringToDecimalFormat(maintainDto.getLumpsumAmt(), null); 
            larrDtlData[i][27] = RutFormatting.getStringToDecimalFormat(maintainDto.getLumpsumUSDAmt(),null);
            larrDtlData[i][28] = maintainDto.getStatus();
            larrDtlData[i][29] = maintainDto.getJobOrdType();//D
            larrDtlData[i][30] = maintainDto.getTransMode();
            larrDtlData[i][31] = maintainDto.getAfs_v();
            larrDtlData[i][32] = maintainDto.getAfs_voy_num();
            larrDtlData[i][33] = maintainDto.getIssue_dt();
            larrDtlData[i][34] = maintainDto.getStartDate();
            larrDtlData[i][35] = maintainDto.getCompleteDate();
            larrDtlData[i][36] = maintainDto.getCreate_dt();
            larrDtlData[i][37] = maintainDto.getCreatedBy();
            larrDtlData[i][38] = maintainDto.getApproval_dt();
            larrDtlData[i][39] = maintainDto.getApproval_id();
                i++;
           // }
            
        }
        //step-4
        /* total 9 data columns with cell position and data type */
        int lintDtlColTagInfo[][] = new int[TABLE_COST_LAST_COL_POS + 1][2];

        //First Data Col Information
        lintDtlColTagInfo[0][IDX_COL_NUM] = 0;
        lintDtlColTagInfo[0][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //Second Data Col Information
        lintDtlColTagInfo[1][IDX_COL_NUM] = 1;
        lintDtlColTagInfo[1][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //Third Data Col Information
        lintDtlColTagInfo[2][IDX_COL_NUM] = 2;
        lintDtlColTagInfo[2][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //Fourth Data Col Information
        lintDtlColTagInfo[3][IDX_COL_NUM] = 3;
        lintDtlColTagInfo[3][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //Fifth Data Col Information
        lintDtlColTagInfo[4][IDX_COL_NUM] = 4;
        lintDtlColTagInfo[4][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //Sixth Data Col Information
        lintDtlColTagInfo[5][IDX_COL_NUM] = 5;
        lintDtlColTagInfo[5][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //Seventh Data Col Information
        lintDtlColTagInfo[6][IDX_COL_NUM] = 6;
        lintDtlColTagInfo[6][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //Eight Data Col Information
        lintDtlColTagInfo[7][IDX_COL_NUM] = 7;
        lintDtlColTagInfo[7][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //Ninth Data Col Information
        lintDtlColTagInfo[8][IDX_COL_NUM] = 8;
        lintDtlColTagInfo[8][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //Tenth Data Col Information
        lintDtlColTagInfo[9][IDX_COL_NUM] = 9;
        lintDtlColTagInfo[9][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //11th Data Col Information
        lintDtlColTagInfo[10][IDX_COL_NUM] = 10;
        lintDtlColTagInfo[10][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //12th Data Col Information
        lintDtlColTagInfo[11][IDX_COL_NUM] = 11;
        lintDtlColTagInfo[11][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //13th Data Col Information
        lintDtlColTagInfo[12][IDX_COL_NUM] = 12;
        lintDtlColTagInfo[12][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //14th Data Col Information
        lintDtlColTagInfo[13][IDX_COL_NUM] = 13;
        lintDtlColTagInfo[13][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //15th Data Col Information
        lintDtlColTagInfo[14][IDX_COL_NUM] = 14;
        lintDtlColTagInfo[14][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        //Last Data Col Information
        lintDtlColTagInfo[15][IDX_COL_NUM] = 15;
        lintDtlColTagInfo[15][IDX_CELL_TYPE] = CELL_TYPE_STRING;

        lintDtlColTagInfo[16][IDX_COL_NUM] = 16;
        lintDtlColTagInfo[16][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[17][IDX_COL_NUM] = 17;
        lintDtlColTagInfo[17][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[18][IDX_COL_NUM] = 18;
        lintDtlColTagInfo[18][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[19][IDX_COL_NUM] = 19;
        lintDtlColTagInfo[19][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[20][IDX_COL_NUM] = 20;
        lintDtlColTagInfo[20][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[21][IDX_COL_NUM] = 21;
        lintDtlColTagInfo[21][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[22][IDX_COL_NUM] = 22;
        lintDtlColTagInfo[22][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[23][IDX_COL_NUM] = 23;
        lintDtlColTagInfo[23][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[24][IDX_COL_NUM] = 24;
        lintDtlColTagInfo[24][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[25][IDX_COL_NUM] = 25;
        lintDtlColTagInfo[25][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[26][IDX_COL_NUM] = 26;
        lintDtlColTagInfo[26][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[27][IDX_COL_NUM] = 27;
        lintDtlColTagInfo[27][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[28][IDX_COL_NUM] = 28;
        lintDtlColTagInfo[28][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[29][IDX_COL_NUM] = 29;
        lintDtlColTagInfo[29][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[30][IDX_COL_NUM] = 30;
        lintDtlColTagInfo[30][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[31][IDX_COL_NUM] = 31;
        lintDtlColTagInfo[31][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[32][IDX_COL_NUM] = 32;
        lintDtlColTagInfo[32][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[33][IDX_COL_NUM] = 33;
        lintDtlColTagInfo[33][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[34][IDX_COL_NUM] = 34;
        lintDtlColTagInfo[34][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[35][IDX_COL_NUM] = 35;
        lintDtlColTagInfo[35][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[36][IDX_COL_NUM] = 36;
        lintDtlColTagInfo[36][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[37][IDX_COL_NUM] = 37;
        lintDtlColTagInfo[37][IDX_CELL_TYPE] = CELL_TYPE_STRING;
        lintDtlColTagInfo[38][IDX_COL_NUM] = 38;
        lintDtlColTagInfo[38][IDX_CELL_TYPE] = CELL_TYPE_STRING;

        mapFields.put(KEY_DETAIL_DATA, larrDtlData);
        if (COST_RATE.equals(reportType)) {
            mapFields.put(KEY_TOT_DATA_COL, 38);
            mapFields.put(KEY_TABLE_LAST_COL_POS, 
                          TABLE_COST_LAST_COL_POS); //index of last data col
        } 

        mapFields.put(KEY_TABLE_FIRST_ROW_POS, 
                      FIRST_ROW_POS); //index of first data row
        mapFields.put(KEY_TABLE_FIRST_COL_POS, 
                      TABLE_FIRST_COL_POS); //index of first data col


        //step-5
        reportGenerator.setTableDataColInfo(lintDtlColTagInfo);

        //step-6
        reportGenerator.generateReport(mapFields);

        //step-7
        //finally save the workbook
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyhhmm");
        String strDate = formatter.format(date);
        return reportGenerator.saveWorkBook(lstrNewPath, templateName, "", 
                                            strDate);
    }

    public IjsMaintainJOSearchUIM searchContract(IjsSearchParamVO contractParam, 
                                               String userInfo) throws IJSException {
        IjsMaintainJOSearchParamVO contractParamVO = 
            (IjsMaintainJOSearchParamVO)contractParam.getSearchScreenParam();
        return null;
    }


}
