package com.rclgroup.dolphin.ijs.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.common.excel.GlobalUtil;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsCommonDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.vo.IjsBaseMessageVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContainerUploadVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsEquipmetLookUpVO;
/**
 * 
 * @author Ashish.1.Rawat
 * validate the uploaded containers
 */
public class IjsJOUploadSvc {
	IjsCommonDao commonDao=null;
    private static final String EQUIP_IN_DB="EQUIP_IN_DB";
    private static final String EQUIP_LOC="EQUIP_LOC";
    private static final String EQUIP_WITH_LOC_REVERSE="EQUIP_WITH_LOC_REVERSE";
    private static final String EQUIP_EXIST_IN_JO="EQUIP_EXIST_IN_JO";
    public IjsJOUploadSvc(IjsCommonDao commonDao) {
    	this.commonDao=commonDao;
    }
    /**
     * validate equipments
     * @param adhocType
     * @param contractId
     * @param folderPath
     * @param fileName
     * @return
     * @throws IJSException
     */
    public IjsContainerUploadVO uploadContainer(String adhocType,String contractId,String folderPath,String fileName,List contractsID) throws IJSException {
            List<String> excelEquipmentList = new ArrayList<String>();
            List<String> equipLocValidated=null;
            List<String> equipLocReverseValidated=null;
            List<String> equipLocInJOValidated=null;
            List<IjsEquipmetLookUpVO> validEquipDetail=new ArrayList<>();
            List<String> excelEquipmentOrigList ;
            excelEquipmentList = getContainerUploadRecord(folderPath,fileName);
            excelEquipmentOrigList=new ArrayList<>(excelEquipmentList);
            List<String> equipNotExistInDB=commonDao.validateEquipment(excelEquipmentList, adhocType,contractId,EQUIP_IN_DB,contractsID);
            excelEquipmentOrigList.removeAll(equipNotExistInDB);
            excelEquipmentList=new ArrayList<>(excelEquipmentOrigList);
            if(!excelEquipmentList.isEmpty()){
            	 equipLocValidated=commonDao.validateEquipment(excelEquipmentList, adhocType,contractId,EQUIP_LOC,contractsID);
            	 excelEquipmentOrigList.removeAll(equipLocValidated);
            	 excelEquipmentList=new ArrayList<>(excelEquipmentOrigList);
                 if(!excelEquipmentList.isEmpty()){
                	 equipLocReverseValidated=commonDao.validateEquipment(excelEquipmentList, adhocType,contractId,EQUIP_WITH_LOC_REVERSE,contractsID);
                	 excelEquipmentOrigList.removeAll(equipLocReverseValidated); 
                	 excelEquipmentList=new ArrayList<>(excelEquipmentOrigList);
                     if(!excelEquipmentList.isEmpty()){
                    	 equipLocInJOValidated=commonDao.validateEquipment(excelEquipmentList, adhocType,contractId,EQUIP_EXIST_IN_JO,contractsID);
                    	 excelEquipmentOrigList.removeAll(equipLocInJOValidated); 
                        
                     }
                 }
            }
            if(!excelEquipmentOrigList.isEmpty()){
           	 	validEquipDetail=commonDao.getValidEquipmentDetail(excelEquipmentOrigList, adhocType);
            }
            return transformUploadResults(validEquipDetail,equipNotExistInDB,
            		equipLocValidated,equipLocReverseValidated,equipLocInJOValidated);
     }
        
        public List<String> getContainerUploadRecord(String folderPath,String fileName) throws IJSException {
            
            GlobalUtil uitl = new GlobalUtil();
            String astrExcelFilePath = folderPath + "/" + fileName;
            
            
            String sheetName = "First Sheet"; 
            int aintFromRowNo=2;
            int aintFromColNo=1;
            int aintToColNo=1;
            boolean ablnIncludeLineNo = false;
             Set<String> excelTemplateList = new HashSet<String>();
            try {
                String[][] retArray = uitl.readExcel(astrExcelFilePath,sheetName,aintFromRowNo,aintFromColNo,aintToColNo,ablnIncludeLineNo);
                if (retArray.length > 0) {
                    int rowT = retArray.length;
                    String header[] = retArray[0];
                    for(int i=0; i < rowT; i++){
                         String containerCode = retArray[i][0];
                         if(containerCode!=null && !containerCode.isEmpty()){
                        	 excelTemplateList.add(containerCode.trim());
                         }
                         
                    }
                }
            } catch (IOException e) {
              
                e.printStackTrace();
            }
            return new ArrayList<>(excelTemplateList);
        }
        
        private IjsContainerUploadVO transformUploadResults(List<IjsEquipmetLookUpVO> results,List<String> equipNotExistInDB,List<String> equipLocValidated,
        		                                          List<String> equipLocReverseValidated,List<String> equipLocInJOValidated) {
        	IjsContainerUploadVO ijsContainerUploadVO=new IjsContainerUploadVO();
            IjsSearchResult<IjsEquipmetLookUpVO> searchResult = new IjsSearchResult<>();
            List<IjsBaseMessageVO<String>> msgVOs=new ArrayList<>();
             if (!results.isEmpty()) {
                searchResult.setResult(results);
                ijsContainerUploadVO.setSearchResult(searchResult);
                ijsContainerUploadVO.setErrorCode(IjsErrorCode.DB_IJS_CNTR_EX_10027.getErrorCode());
            }
            if(equipNotExistInDB!=null && !equipNotExistInDB.isEmpty()){
            	IjsBaseMessageVO<String> msgVO=new IjsBaseMessageVO<>();
            	msgVO.setMessageList(equipNotExistInDB);
            	msgVO.setErrorCode(IjsErrorCode.DB_IJS_PRJ_EX_10003.getErrorCode());
            	msgVOs.add(msgVO);
            }
            if(equipLocValidated!=null && !equipLocValidated.isEmpty()){
            	IjsBaseMessageVO<String> msgVO=new IjsBaseMessageVO<>();
            	msgVO.setMessageList(equipLocValidated);
            	msgVO.setErrorCode(IjsErrorCode.DB_IJS_PRJ_EX_10004.getErrorCode());
            	msgVOs.add(msgVO);
            }
            if(equipLocReverseValidated!=null && !equipLocReverseValidated.isEmpty()){
            	IjsBaseMessageVO<String> msgVO=new IjsBaseMessageVO<>();
            	msgVO.setMessageList(equipLocReverseValidated);
            	msgVO.setErrorCode(IjsErrorCode.DB_IJS_PRJ_EX_10005.getErrorCode());
            	msgVOs.add(msgVO);
            }
            if(equipLocInJOValidated!=null && !equipLocInJOValidated.isEmpty()){
            	IjsBaseMessageVO<String> msgVO=new IjsBaseMessageVO<>();
            	msgVO.setMessageList(equipLocInJOValidated);
            	msgVO.setErrorCode(IjsErrorCode.DB_IJS_PRJ_EX_10006.getErrorCode());
            	msgVOs.add(msgVO);
            }
            ijsContainerUploadVO.setMsgVOs(msgVOs);
            return ijsContainerUploadVO;
        }

}
