/*-----------------------------------------------------------------------------------------------------------
IjsContractSearchAction.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            Contract Search functionality 
02 07/09/17  NIIT       IJS            Contract Advance Search functionality
03 21/09/17  NIIT       IJS            New Contract functionality
04 05/09/17  NIIT       IJS            Edit,Suspend,Delete,Vendor detail,History log  Contract functionality
05 20/10/17  NIIT       IJS            added upload contracts functionality 
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractRateDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractSearchDao;

import com.rclgroup.dolphin.ijs.web.exception.IJSException;

import com.rclgroup.dolphin.ijs.web.service.IjsContractRateSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsContractSearchSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractSearchUIM;

import com.rclgroup.dolphin.ijs.web.vo.IjsContractSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsLocationVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import com.rclgroup.dolphin.ijs.web.vo.IjsSearchParamVO;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

//import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IjsContractSearchAction extends IjsBaseAction {
    private static final String ZIP_TEMPLATE_DOWNLOAD_FILE_NAME="IJS_CONTRACT_DOWNLOAD_TEMPLATE.zip";
    private static final String ZIP_CONTRACT_DOWNLOAD_FILE_NAME="IJS_CONTRACT_DOWNLOAD_UPLOAD.zip";
    //## O1 BEGIN
    List<IjsContractSearchUIM> resultList;
   // final static Logger logger = Logger.getLogger(IjsContractSearchAction.class);
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        super.execute(mapping,form,request,response);
        
       // System.out.println("[IjsContractSearchAction] execute:");
        return null;
    }
    /**
     * unmarshalJsonRequestToJava method converting json to java object
     * @param request
     * @throws Exception
     */
    public void unmarshalJsonRequestToJava(HttpServletRequest request) throws Exception{
    // unmarshal json to java pojo
        try{
            //System.out.println("[IjsContractSearchAction] unmarshalJsonRequestToJava:");
            String ijsSearchJson=getJSONDataFromRequest(request);
            System.out.println("input data for search ---->" + ijsSearchJson);
            setActionForm(new IjsContractSearchUIM());
            IjsContractSearchUIM uim =  new IjsContractSearchUIM();
            //IjsContractSearchUIM actionForm=(IjsContractSearchUIM)getActionForm();
          // String jsonStr = new Gson().toJson(actionForm);
          // System.out.println("jsonStr in unmarshalling:" +jsonStr);
            IjsContractSearchUIM searchUim= new Gson().fromJson(ijsSearchJson,IjsContractSearchUIM.class);
            //System.out.println(searchUim.toString());
 //IjsContractSearchUIM searchUim= getMockContractSave();
            if(searchUim!=null){
                setActionForm(searchUim);
            }else{
                setActionForm(new IjsContractSearchUIM());
            }
        }catch(Exception e){
            throw e;
        }
    }
   /**
     * marshalJavaToJson method converting java object to json
     * @param response
     * @throws Exception
     */
    public void marshalJavaToJson(HttpServletResponse response) throws Exception {
        System.out.println("[IjsContractSearchAction] marshalJavaToJson:");
        IjsContractSearchUIM actionForm=(IjsContractSearchUIM)getActionForm();
        String jsonStr = null;
            if(IjsActionMethod.LOAD.getAction().equals(actionForm.getAction())){
                jsonStr = new Gson().toJson(actionForm.getUserInfo());
                response.getWriter().write(jsonStr);
            }else if(IjsActionMethod.GPP.getAction().equals(actionForm.getAction())){
                jsonStr = new Gson().toJson(actionForm);
                response.getWriter().write(jsonStr);
            }
            //## 02 START
            else if(IjsActionMethod.SEARCH.getAction().equals(actionForm.getAction())){
                if(actionForm.getSearchResult()!=null && actionForm.getSearchResult().getResult()!=null){
                    jsonStr = new Gson().toJson(actionForm.getSearchResult().getResult()); 
                    System.out.println(jsonStr);
                }else if(actionForm.getErrorCode()!=null){
                    jsonStr = new Gson().toJson(actionForm); 
                }
                response.getWriter().write(jsonStr);
            }
            //## 02 END
            //## 03 BEGIN
            else if(IjsActionMethod.NEW.getAction().equals(actionForm.getAction())||
            		IjsActionMethod.COPY.getAction().equals(actionForm.getAction()) ||
            IjsActionMethod.NEW_CHANGE_PRIORITY.getAction().equals(actionForm.getAction())||
            IjsActionMethod.EDIT_CHANGE_PRIORITY.getAction().equals(actionForm.getAction())
            ||IjsActionMethod.COPY_CHANGE_PRIORITY.getAction().equals(actionForm.getAction())){
                //TODO NEW Contract
                  if(actionForm.getErrorCode() != null || actionForm.getMsgDupRoute()!=null 
                     || actionForm.getMsgSuccessVO()!=null){
                     jsonStr = new Gson().toJson(actionForm); 
                     response.getWriter().write(jsonStr);
                 }
            }
           
            //## 03 END
            //##04 BEGIN
            else if(IjsActionMethod.EDIT.getAction().equals(actionForm.getAction())){
                //TODO EDIT Contract
                 if(actionForm.getErrorCode() != null){
                    jsonStr = new Gson().toJson(actionForm); 
                     response.getWriter().write(jsonStr);
                 }
            }
            else if(IjsActionMethod.DELETE.getAction().equals(actionForm.getAction())){
                //TODO DELETE Contract
               //  if(actionForm.getContractsResult() != null && actionForm.getContractsResult().length() > 0 ){
                    jsonStr = new Gson().toJson(actionForm); 
                     response.getWriter().write(jsonStr);
                // }
             }
            else if(IjsActionMethod.SUSPEND_CONTRACT.getAction().equals(actionForm.getAction())){
                //TODO  Contract history
                 //if(actionForm.getContractsResult() != null && actionForm.getContractsResult().length() > 0 ){
                    jsonStr = new Gson().toJson(actionForm); 
                     response.getWriter().write(jsonStr);
                 //}
            } 
            else if(IjsActionMethod.COMPARE.getAction().equals(actionForm.getAction())){
                //TODO  Contract history
                 if(actionForm.getSearchResult()!=null && actionForm.getSearchResult().getResult()!=null){
                     jsonStr = new Gson().toJson(actionForm.getSearchResult().getResult());  
                 }else if(actionForm.getErrorCode()!=null){
                     jsonStr = new Gson().toJson(actionForm); 
                 }
                response.getWriter().write(jsonStr);
            } 
            else if(IjsActionMethod.CONTRACT_HISTORY.getAction().equals(actionForm.getAction())){
                //TODO  Contract history
                 if(actionForm != null ){
                    jsonStr = new Gson().toJson(actionForm); 
                     response.getWriter().write(jsonStr);
                 }
                
            } 
            else if(IjsActionMethod.VENDOR_DETAILS.getAction().equals(actionForm.getAction())){
                //TODO vendor details
                 if(actionForm.getSearchResult() != null && actionForm.getSearchResult().getResult() != null ) {
                    jsonStr = new Gson().toJson(actionForm); 
                     response.getWriter().write(jsonStr);
                 }
            }
            //##04 END
            //##05 BEGIN
           else if (IjsActionMethod.CONTRACT_DOWNLOAD.getAction().equals(actionForm.getAction())) {
                downloadFilesInZip(response,actionForm.getFilesTobeDownload(),ZIP_CONTRACT_DOWNLOAD_FILE_NAME);
                //downloadFile(response,actionForm.getFilesTobeDownload()[0],actionForm.getFilesTobeDownload()[2]);
               // return;
            }else if (IjsActionMethod.CONTRACT_TEMPLATE_DOWNLOAD.getAction().equals(actionForm.getAction())) {
                downloadFilesInZip(response,actionForm.getFilesTobeDownload(),ZIP_TEMPLATE_DOWNLOAD_FILE_NAME);
              //  return;
            }else if (IjsActionMethod.GET_TRANSPORT_MODE.getAction().equals(actionForm.getAction())) {
                if (actionForm != null) {
                    jsonStr = new Gson().toJson(actionForm); 
                    response.getWriter().write(jsonStr);
                }
            } 
             else if (IjsActionMethod.GET_TERM_DATA.getAction().equals(actionForm.getAction())) {
                if (actionForm != null) {
                    jsonStr = new Gson().toJson(actionForm); 
                    System.out.println(IjsActionMethod.GET_TERM_DATA.getAction());
                    System.out.println(jsonStr);
                    response.getWriter().write(jsonStr);
                }
             } 
            else if (IjsActionMethod.CONTRACT_UPLOAD.getAction().equals(actionForm.getAction())) {
                if (actionForm != null) {
                    jsonStr = new Gson().toJson(actionForm); 
                    response.getWriter().write(jsonStr);
                }
                }
            else if (IjsActionMethod.GET_CONTRACT_DATA.getAction().equals(actionForm.getAction())) {
                if (actionForm != null) {
                    jsonStr = new Gson().toJson(actionForm); 
                    response.getWriter().write(jsonStr);
                }
            }
            else if (IjsActionMethod.GET_CONTRACT_CURRENCY.getAction().equals(actionForm.getAction())) {
                if (actionForm != null) {
                    jsonStr = new Gson().toJson(actionForm); 
                    response.getWriter().write(jsonStr); }    
                 }
            else if (IjsActionMethod.CONTRACT_PRIORITY_CORRECTION.getAction().equals(actionForm.getAction())) {
                if (actionForm != null) {
                    jsonStr = new Gson().toJson(actionForm); 
                    response.getWriter().write(jsonStr); }    
                 }
          else if (IjsActionMethod.GET_FSC_CURR_CODE.getAction().equals(actionForm.getAction())  ||
                   IjsActionMethod.GET_TERMINAL_CODE.getAction().equals(actionForm.getAction())) {
            if (actionForm != null) {
                jsonStr = new Gson().toJson(actionForm); 
                response.getWriter().write(jsonStr); }    
             }                
            else{
                actionForm=new IjsContractSearchUIM();
                actionForm.setErrorCode("Error on downloading");
                jsonStr = new Gson().toJson(actionForm); 
                response.getWriter().write(jsonStr);
            }
        //System.out.println("jsonStr:" +jsonStr);
       
       // response.getWriter().write(jsonStr);
    }
    
    private void downloadFilesInZip(HttpServletResponse response,String[] filesTobeDownloaded,String zipFileName){
        FileInputStream fileInputStream = null;
        OutputStream lobjosrw = null;
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition","attachment;filename="+zipFileName);
      //  response.setContentType("application/x-download");
       // int i=0;
            FileOutputStream fos = null;
                    ZipOutputStream zipOut = null;
                    FileInputStream fis = null;
                    String relativePath=filesTobeDownloaded[0];
                    String zipCompletePath=relativePath+"/"+zipFileName;
                    try {
                        fos = new FileOutputStream(zipCompletePath);
                        zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
                        for(int i=1;i<filesTobeDownloaded.length;i++){
                           if(filesTobeDownloaded[i] != null){
                            File input = new File(relativePath+filesTobeDownloaded[i]);
                            fis = new FileInputStream(input);
                            ZipEntry ze = new ZipEntry(input.getName());
                            System.out.println("Zipping the file: "+input.getName());
                            zipOut.putNextEntry(ze);
                            byte[] tmp = new byte[4*1024];
                            int size = 0;
                            while((size = fis.read(tmp)) != -1){
                                zipOut.write(tmp, 0, size);
                            }
                            zipOut.flush();
                            
                            fis.close();
                            input.delete();
                            }
                        } 
                        zipOut.close();
                        System.out.println("Done... Zipped the files...");
                        
                       
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally{
                        try{
                            if(fos != null) fos.close();
                        } catch(Exception ex){
                             
                        }
                    }
            
           // response.setHeader("Cache-Control", "public");
            //response.setHeader("Pragma", "public");
            try {
            // String filePath = actionForm.getDownLoadFile();
            File file=new File(zipCompletePath);
               lobjosrw=response.getOutputStream();
               fileInputStream = new FileInputStream(file);
               int content = 256;
               while ((content) >= 0) {
                  content = fileInputStream.read();
                  lobjosrw.write(content);
                }
                 // response.flushBuffer();
                lobjosrw.flush(); 
                fileInputStream.close();
                file.delete();
                System.out.println("file Download completed...");
            } catch (IOException e) {
               e.printStackTrace();
            } finally {
               if (fileInputStream != null) {
                   try {
                       fileInputStream.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
                try {
                    lobjosrw.close();
                    lobjosrw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
              

            }
           // response.setContentType("application/vnd.ms-excel");
           
           // response.setHeader("Content-Disposition", "attachment;"); 
        }
    
    private void downloadFile(HttpServletResponse response,String relativePath,String fileName){
        FileInputStream fileInputStream = null;
        OutputStream lobjosrw = null;
        response.setContentType("application/x-download");
        if(fileName!=null && fileName.length()>0){
            response.setHeader("Content-Disposition","attachment;filename="+fileName);
            response.setHeader("Cache-Control", "public");
            response.setHeader("Pragma", "public");
            try {
            // String filePath = actionForm.getDownLoadFile();
            File file=new File(relativePath+fileName);
               lobjosrw=response.getOutputStream();
               fileInputStream = new FileInputStream(file);
               int content = 256;
               while ((content) >= 0) {
                  content = fileInputStream.read();
                  lobjosrw.write(content);
                }
               // lobjosrw.flush(); 
            } catch (IOException e) {
               e.printStackTrace();
            } finally {
               if (fileInputStream != null) {
                   try {
                       fileInputStream.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
                try {
                    lobjosrw.close();
                    lobjosrw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
              

            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;"); 
        }
       
    }
    /**
     * performAction method processing request to service  and getting the result from service
     * @param request
     */
    public void performAction(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("[IjsContractSearchAction] performAction:");
        IjsContractSearchUIM actionForm=(IjsContractSearchUIM)getActionForm();
        HttpSession session = request.getSession();
        IjsContractSearchSvc contractSearchSvc=new IjsContractSearchSvc((IjsContractSearchDao)getDao("contractSearchDao"));
        
        IjsUserInfoVO userInfo=(IjsUserInfoVO)session.getAttribute("userInfo");
        //contractTemplateDownload
       // System.out.println("inside search contract ,userInfo=="+userInfo);
        if(userInfo==null){
            userInfo=loadUserData(request,response);
            session.setAttribute("userInfo",userInfo);
        }
        
        try {
            if(IjsActionMethod.LOAD.getAction().equals(actionForm.getAction())){
                actionForm.setUserInfo(loadUserData(request,response));
            }
            // 02 START
            else if(IjsActionMethod.SEARCH.getAction().equals(actionForm.getAction())){
                System.out.println("[IjsContractSearchAction] SEARCH:");
                IjsContractSearchUIM newActionForm= contractSearchSvc.searchContract(actionForm.getContractParam(),userInfo!=null?userInfo.getUserId():"");
                 newActionForm.setAction("search");
                 setActionForm(newActionForm);

            }
            // 02 END
            // 03 START
            else if(IjsActionMethod.NEW.getAction().equals(actionForm.getAction()) || 
            		IjsActionMethod.COPY.getAction().equals(actionForm.getAction()) ||
                IjsActionMethod.EDIT.getAction().equals(actionForm.getAction())||
                IjsActionMethod.NEW_CHANGE_PRIORITY.getAction().equals(actionForm.getAction())||
                IjsActionMethod.EDIT_CHANGE_PRIORITY.getAction().equals(actionForm.getAction())||
                IjsActionMethod.COPY_CHANGE_PRIORITY.getAction().equals(actionForm.getAction())) {
                //TO-DO NEW  or EDIT Contract
                 setActionForm(contractSearchSvc.saveOrUpdateContract(actionForm.getContract()
                    , userInfo!=null?userInfo.getUserId():"",actionForm.getAction()));
            }
            // 03 END
            // 04 START
            else if(IjsActionMethod.DELETE.getAction().equals(actionForm.getAction())){
                //TO-DO DELETE Contract
                if (actionForm.getContractsList().size() > 0) {
                    setActionForm(contractSearchSvc.deleteContract(actionForm.getContractsList()
                       , userInfo!=null?userInfo.getUserId():"",actionForm.getAction()));
                }
            } 
            else if(IjsActionMethod.SUSPEND_CONTRACT.getAction().equals(actionForm.getAction())){
                //TO-DO SUSPEND Contract
                 if (actionForm.getContractsList().size() > 0) {
                     setActionForm(contractSearchSvc.suspendContract(actionForm.getContractsList()
                        , userInfo!=null?userInfo.getUserId():"",actionForm.getAction()));
                 }
            }
            else if(IjsActionMethod.COMPARE.getAction().equals(actionForm.getAction())){
                //TO-DO COMPARE Contract
                 setActionForm(contractSearchSvc.compareContract(actionForm.getContract()
                    , userInfo!=null?userInfo.getUserId():"",actionForm.getAction()));
            }
            else if(IjsActionMethod.CONTRACT_HISTORY.getAction().equals(actionForm.getAction())) {
                //TO-DO COMPARE Contract
                 setActionForm(contractSearchSvc.getContractHistory(actionForm.getContract()
                    , userInfo!=null?userInfo.getUserId():"",actionForm.getAction()));
            }
            else if(IjsActionMethod.VENDOR_DETAILS.getAction().equals(actionForm.getAction())) {
                //TO-DO VENDOR_DETAILS 
                 setActionForm(contractSearchSvc.getVendorDetails(actionForm.getContract()
                    , userInfo!=null?userInfo.getUserId():"",actionForm.getAction()));
            }else if(IjsActionMethod.GPP.getAction().equals(actionForm.getAction())) {
                //GENERATE PORT PAIR
                setActionForm(contractSearchSvc.doGenPortPair(userInfo!=null?userInfo.getUserId():"",actionForm.getAction()));
           }
            // 04 END
             //##05 BEGIN

            else if (IjsActionMethod.CONTRACT_TEMPLATE_DOWNLOAD.getAction().equals(actionForm.getAction())) {
            String [] templateFiles= new String[3];
            String relativePath=getServlet().getServletContext().getRealPath("/")+"/downloadUploadTemplate/";
                  templateFiles[0]=relativePath;
                  templateFiles[1]="IJS_Contract_Download_Upload_Template_Cost.xls";
                  templateFiles[2]="IJS_Contract_Download_Upload_Template_Bill.xls";
                  actionForm.setFilesTobeDownload(templateFiles);
                  setActionForm(actionForm);
              }else if (IjsActionMethod.CONTRACT_DOWNLOAD.getAction().equals(actionForm.getAction())) {
                  String path=getServlet().getServletContext().getRealPath("/")+"/downloadUploadTemplate/";
                 actionForm.setFilesTobeDownload(contractSearchSvc.downloadContracts(actionForm.getContractParam(),userInfo!=null?userInfo.getUserId():"",path,session.getId()));
                // contractSearchSvc.downloadContracts(actionForm.getContractParam(),userInfo!=null?userInfo.getUserId():"",path,session.getId());
                 
             } 
            else if(IjsActionMethod.GET_TERM_DATA.getAction().equals(actionForm.getAction())){
                 IjsContractSearchUIM newActionForm= contractSearchSvc.getTermList(userInfo!=null?userInfo.getUserId():"");
                 
                 setActionForm(newActionForm);

            }
            else if(IjsActionMethod.GET_TRANSPORT_MODE.getAction().equals(actionForm.getAction())){
                String vendorCode = actionForm.getVendorCode();
                 IjsContractSearchUIM newActionForm= contractSearchSvc.getTransportMode(userInfo!=null?userInfo.getUserId():"", vendorCode);
                 
                 setActionForm(newActionForm);

            } 
            else if(IjsActionMethod.GET_CONTRACT_CURRENCY.getAction().equals(actionForm.getAction())){
                String paymentFscCode = actionForm.getPaymentFscCode();
                 IjsContractSearchUIM newActionForm= contractSearchSvc.getCurrencyForFSC(actionForm.getContractParam(),userInfo!=null?userInfo.getUserId():"", paymentFscCode);
                 setActionForm(newActionForm);
            } 
            else if (IjsActionMethod.GET_FSC_CURR_CODE.getAction().equals(actionForm.getAction())) {
            	String fromLocation =null; 
            	String fromLocationType =null;
            	String toLocation =null;
            	String toLocationType =null;
            	String transMode=null;
            	String locType=null;
            	IjsContractVO contractVO=actionForm.getContract();
            	if(contractVO!=null){
            		 fromLocation = contractVO.getFromLocation();
                     fromLocationType = contractVO.getFromLocType();
                     toLocation = contractVO.getToLocation();
                     toLocationType = contractVO.getToLocType();
                     transMode=contractVO.getTransMode();
                     locType=contractVO.getLocType();
                     
            	}
                       
                       IjsContractSearchUIM newActionForm= contractSearchSvc.getCurrencyForLoC(userInfo!=null?userInfo.getUserId():"", 
                    		   fromLocation, fromLocationType,toLocation, toLocationType,transMode,locType);
                       setActionForm(newActionForm);
                       
            }else if(IjsActionMethod.GET_CONTRACT_DATA.getAction().equals(actionForm.getAction())){
            	String fromLocation =null; 
            	String fromLocationType =null;
            	String toLocation =null;
            	String toLocationType =null;
            	String transMode=null;
            	String locType=null;
            	IjsContractVO contractVO=actionForm.getContract();
            	if(contractVO!=null){
            		 fromLocation = contractVO.getFromLocation();
                     fromLocationType = contractVO.getFromLocType();
                     toLocation = contractVO.getToLocation();
                     toLocationType = contractVO.getToLocType();
                     transMode=contractVO.getTransMode();
                     locType=contractVO.getLocType();
                     
            	}
            	IjsContractSearchUIM newActionForm= contractSearchSvc.getSelectedContractData(actionForm.getVendorCode(), userInfo!=null?userInfo.getUserId():"",
            			fromLocation, fromLocationType, toLocation, toLocationType, transMode, locType);
            	newActionForm.setAction(IjsActionMethod.GET_CONTRACT_DATA.getAction());
                setActionForm(newActionForm);
            }
               
            else if (IjsActionMethod.GET_TERMINAL_CODE.getAction().equals(actionForm.getAction())) {
                      
                       String location = actionForm.getLocation();
                       String locationType = actionForm.getLocationType();
                       String terminal = actionForm.getTerminal();
                       
                       IjsContractSearchUIM newActionForm= contractSearchSvc.getTerminalCode(actionForm.getContractParam(),userInfo!=null?userInfo.getUserId():"", location, locationType, terminal);
                       setActionForm(newActionForm);
                       
               } 
            else if (IjsActionMethod.CONTRACT_PRIORITY_CORRECTION.getAction().equals(actionForm.getAction())) {
                               
                IjsContractSearchUIM newActionForm= contractSearchSvc.priorityCorrection(actionForm.getContract(),userInfo!=null?userInfo.getUserId():"");
                newActionForm.setAction(IjsActionMethod.CONTRACT_PRIORITY_CORRECTION.getAction());
                setActionForm(newActionForm);
                
            } 
            else if (IjsActionMethod.CONTRACT_UPLOAD.getAction().equals(actionForm.getUploadVO().getAction())) {
                            IjsContractRateSvc lookupSvc= new IjsContractRateSvc(
                                (IjsContractRateDao)getDao("contractRateDao"));
                            String path = getServlet().getServletContext().getRealPath("/") + "/UPLOAD/";
                            actionForm.getUploadVO().setFolderPath(path);
                            setActionForm(contractSearchSvc.uploadContracts(actionForm
                                , userInfo!=null?userInfo.getUserId():"", lookupSvc));
                        }

            //##05 END
        }catch(IJSException ie){
            actionForm.setErrorCode(ie.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            actionForm.setErrorCode(IjsErrorCode.UI_GBL_IJS_EX_10001.getErrorCode());
        }
        
    }

    public void setResultList(List<IjsContractSearchUIM> resultList) {
        this.resultList = resultList;
    }

    public List<IjsContractSearchUIM> getResultList() {
        return resultList;
    }
    
    private IjsContractSearchUIM getMockContractSave(){
        IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
        ijsContractSearchUIM.setAction("new");
        ijsContractSearchUIM.setContract(getMockIjsNewContract());
        return ijsContractSearchUIM;
    }
    private IjsContractVO getMockIjsNewContract() {
        IjsContractVO searchVO= new IjsContractVO();
        searchVO.setStartDate("14/02/2018");
        searchVO.setEndDate("31/12/2018");
        searchVO.setStatus("Active");
        searchVO.setPaymentFsc("TYO");
        searchVO.setUom("KM");
        searchVO.setVendorCode("REGMEF5001");
        searchVO.setVendorName("******AL ********* *********S");
        searchVO.setTransMode("Truck");
        searchVO.setCurrency("JPY");
        searchVO.setLocations(getMockLocationList());
        return searchVO;
    }
    private List<IjsLocationVO> getMockLocationList(){
    ArrayList<IjsLocationVO> location  = new ArrayList<IjsLocationVO>();
        location.add(getMockLocation());
        location.add(getMockLocation1());
        location.add(getMockLocation2());
        location.add(getMockLocation3());
        location.add(getMockLocation4());
        location.add(getMockLocation5());
        location.add(getMockLocation6());
       // location.add(getMockLocation7());
    return location;
    }
    private IjsLocationVO getMockLocation(){
        IjsLocationVO ijsLocationVO = new IjsLocationVO();
                ijsLocationVO.setFromLocType("Door");
                ijsLocationVO.setFromLocation("JPIKD");
                //ijsLocationVO.setFromTerminal("JPIKD");
                
                ijsLocationVO.setToLocType("Door");
                ijsLocationVO.setToLocation("INDEL");
                //ijsLocationVO.setToTerminal("INDEL");        
                ijsLocationVO.setPriority(1);
                return ijsLocationVO;
    }
    private IjsLocationVO getMockLocation1(){
        IjsLocationVO ijsLocationVO = new IjsLocationVO();
                ijsLocationVO.setFromLocType("Door");
                ijsLocationVO.setFromLocation("JPIKD");
                //ijsLocationVO.setFromTerminal("JPIKD");
        
                ijsLocationVO.setToLocType("Door");
                ijsLocationVO.setToLocation("INDEL");
                //ijsLocationVO.setToTerminal("INDEL");        
                ijsLocationVO.setPriority(1);
        return ijsLocationVO;
    }
    private IjsLocationVO getMockLocation2(){
        IjsLocationVO ijsLocationVO = new IjsLocationVO();
                ijsLocationVO.setFromLocType("Terminal");
                ijsLocationVO.setFromLocation("JPIKD");
                ijsLocationVO.setFromTerminal("JPIKD");
        
                ijsLocationVO.setToLocType("Terminal");
                ijsLocationVO.setToLocation("INDEL");
                ijsLocationVO.setToTerminal("INDEL");
                ijsLocationVO.setPriority(1);
        return ijsLocationVO;
    }
    private IjsLocationVO getMockLocation3(){
        IjsLocationVO ijsLocationVO = new IjsLocationVO();
                ijsLocationVO.setFromLocType("Terminal");
                ijsLocationVO.setFromLocation("INDEL");
                ijsLocationVO.setFromTerminal("INDEL");
        
                ijsLocationVO.setToLocType("Terminal");
                ijsLocationVO.setToLocation("JPIKD");
                ijsLocationVO.setToTerminal("JPIKD");
                ijsLocationVO.setPriority(1);
        return ijsLocationVO;
    }
    
    private IjsLocationVO getMockLocation4(){
        IjsLocationVO ijsLocationVO = new IjsLocationVO();
                
                ijsLocationVO.setFromLocType("Door");
                ijsLocationVO.setFromLocation("JPIKD");
                //ijsLocationVO.setFromTerminal("JPIKD");
        
                ijsLocationVO.setToLocType("Terminal");
                ijsLocationVO.setToLocation("INDEL");
                ijsLocationVO.setToTerminal("INDEL");
                ijsLocationVO.setPriority(1);
         return ijsLocationVO;
    }
    
    private IjsLocationVO getMockLocation5(){
        IjsLocationVO ijsLocationVO = new IjsLocationVO();
                
                ijsLocationVO.setFromLocType("Terminal");
                ijsLocationVO.setFromLocation("INDEL");
                ijsLocationVO.setFromTerminal("INDEL");
        
                ijsLocationVO.setToLocType("Door");
                ijsLocationVO.setToLocation("JPIKD");
                //ijsLocationVO.setToTerminal("INDEL");
                ijsLocationVO.setPriority(1);
         return ijsLocationVO;
    }
    
    
    
    
    private IjsLocationVO getMockLocation6(){
        IjsLocationVO ijsLocationVO = new IjsLocationVO();
                
                ijsLocationVO.setFromLocType("Door");
                ijsLocationVO.setFromLocation("INDEL");
                //ijsLocationVO.setFromTerminal("INDEL");
        
                ijsLocationVO.setToLocType("Terminal");
                ijsLocationVO.setToLocation("JPIKD");
                ijsLocationVO.setToTerminal("JPIKD");
                ijsLocationVO.setPriority(1);
         return ijsLocationVO;
    }
    
    
//    private IjsLocationVO getMockLocation7(){
//        IjsLocationVO ijsLocationVO = new IjsLocationVO();
//                ijsLocationVO.setFromLocType("Door");
//                ijsLocationVO.setFromLocation("JPIKD");
//                ijsLocationVO.setFromTerminal("JPIKD");
//        
//                ijsLocationVO.setToLocType("Door");
//                ijsLocationVO.setToLocation("INDEL");
//                ijsLocationVO.setToTerminal("INDEL");
//                ijsLocationVO.setPriority(1);
//         return ijsLocationVO;
//    }
    
    
    
    private void getmockIjsContractSearchVO(IjsContractSearchUIM actionForm){
        IjsContractVO searchVO1= new IjsContractVO();
        searchVO1.setCurrency("Curr1");
        searchVO1.setDays(2);
        searchVO1.setDistance(150);
        searchVO1.setEndDate("15/09/2017");
        searchVO1.setExempted(true);
        searchVO1.setHours(15);
        searchVO1.setFromLocation("IDJKT");
        searchVO1.setFromLocType("Terminal");
        searchVO1.setFromTerminal("IDPK1");
        searchVO1.setToLocation("IDJKT");
        searchVO1.setToLocType("Depot");
        searchVO1.setToTerminal("IDADT");
        searchVO1.setPaymentFsc("FSC1");
        searchVO1.setPriority(1);
        searchVO1.setRemarks("First Record");
        searchVO1.setStartDate("09/08/2017");
        searchVO1.setStatus("A");
        
        searchVO1.setTransMode("Truck");
        searchVO1.setUom("KM");
        searchVO1.setVendorCode("VEND11");
        searchVO1.setVendorName("VEND11 Inc. Ltd.");
        actionForm.setContract(searchVO1);
        actionForm.setAction("search");
        IjsSearchParamVO<IjsContractSearchParamVO> contractParam=new IjsSearchParamVO<IjsContractSearchParamVO>();
        IjsContractSearchParamVO contractParams=new IjsContractSearchParamVO();
        contractParams.setFindIn("VendorName");
        contractParams.setFindValue("MUMBAI PORT");
        contractParam.setTransMode("Truck");
        contractParam.setStartDate("09/08/2017");
        contractParam.setEndDate("15/08/2017");
        contractParam.setSearchScreenParam(contractParams);
        actionForm.setContractParam(contractParam);
        
    }
    
    private void getMockIjsContractSearchVOs(IjsContractSearchUIM actionForm){
        IjsContractVO searchVO1= new IjsContractVO();
        IjsContractVO searchVO2= new IjsContractVO();
        IjsContractVO searchVO3= new IjsContractVO();
        IjsSearchResult<IjsContractVO> result=new IjsSearchResult<IjsContractVO>();
        List<IjsContractVO> lstContarcts= new ArrayList<IjsContractVO>();
        //FIRST RECORD
         searchVO1.setCurrency("Curr1");
         searchVO1.setDays(2);
         searchVO1.setDistance(150);
         searchVO1.setEndDate("15/09/2017");
         searchVO1.setExempted(true);
         searchVO1.setHours(15);
        searchVO1.setFromLocation("IDJKT");
        searchVO1.setFromLocType(IjsHelper.getLocationType("P"));
        searchVO1.setFromTerminal("IDPK1");
        searchVO1.setToLocation("IDJKT");
        searchVO1.setToLocType(IjsHelper.getLocationType("Y"));
        searchVO1.setToTerminal("IDADT");
         searchVO1.setPaymentFsc("FSC1");
         searchVO1.setPriority(1);
         searchVO1.setRemarks("First Record");
         searchVO1.setStartDate("09/08/2017");
         searchVO1.setStatus("A");
         searchVO1.setTransMode(IjsHelper.getTransMode("01"));
         searchVO1.setUom("KM");
         searchVO1.setVendorCode("VEND11");
        searchVO1.setVendorName("VEND11 Inc. Ltd.");
         lstContarcts.add(searchVO1);
         //SECOND RECORD
          searchVO2.setCurrency("Curr2");
          searchVO2.setDays(2);
          searchVO1.setDistance(180);
          searchVO2.setEndDate("15/09/2017");
          searchVO2.setExempted(false);
          searchVO2.setHours(25);
          searchVO1.setFromLocation("IDJKT");
          searchVO1.setFromLocType("Terminal");
          searchVO1.setFromTerminal("IDPK1");
          searchVO1.setToLocation("IDJKT");
          searchVO1.setToLocType("Depot");
          searchVO1.setToTerminal("IDADT");
          searchVO2.setPaymentFsc("FSC2");
          searchVO2.setPriority(2);
          searchVO2.setRemarks("Second Record");
          searchVO2.setStartDate("09/08/2017");
          searchVO2.setStatus("A");
          searchVO2.setTransMode("Rail");
          searchVO2.setUom("KM");
          searchVO2.setVendorCode("VEND12");
        searchVO1.setVendorName("VEND12 Inc. Ltd.");
        lstContarcts.add(searchVO2);
          //THIRD RECORD
           searchVO3.setCurrency("Curr3");
           searchVO3.setDays(4);
           searchVO3.setDistance(200);
           searchVO3.setEndDate("2017/09/10");
           searchVO3.setExempted(true);
           searchVO3.setHours(15);
           searchVO1.setFromLocation("IDJKT");
           searchVO1.setFromLocType("Terminal");
           searchVO1.setFromTerminal("IDPK1");
           searchVO1.setToLocation("IDJKT");
           searchVO1.setToLocType("Depot");
           searchVO1.setToTerminal("IDADT");
           searchVO3.setPaymentFsc("FSC3");
           searchVO3.setPriority(1);
           searchVO3.setRemarks("Third Record");
           searchVO3.setStartDate("2017/09/15");
           searchVO3.setStatus("A");
           searchVO3.setTransMode("Ship");
           searchVO3.setUom("KM");
           searchVO3.setVendorCode("VEND13");
           searchVO1.setVendorName("VEND13 Inc. Ltd.");
           lstContarcts.add(searchVO3);
          //END OF delete below code
           result.setResult(lstContarcts);
           actionForm.setSearchResult(result);
    }
    
    private void getMockIjsContractSearchParams(IjsContractSearchUIM actionForm){
        IjsSearchParamVO<IjsContractSearchParamVO> searchParam=new IjsSearchParamVO<IjsContractSearchParamVO>();
        IjsContractSearchParamVO contractParam=new IjsContractSearchParamVO();
        contractParam.setFindIn("Vendor");
        contractParam.setFindValue("VEND13");
        searchParam.setSearchScreenParam(contractParam);
        searchParam.setStartDate("10/08/2017");
        searchParam.setEndDate("15/09/2017");
        searchParam.setTransMode("truck;");
        actionForm.setContractParam(searchParam); 
    }

    private void getMockIjsNewContract(IjsContractSearchUIM actionForm) {
        IjsContractVO searchVO1= new IjsContractVO();
        searchVO1.setCurrency("ISD");
        searchVO1.setDays(2);
        searchVO1.setDistance(150);
        searchVO1.setEndDate("20170910");
        searchVO1.setExempted(true);
        searchVO1.setHours(15);
        searchVO1.setFromLocation("IDJKT");
        searchVO1.setFromLocType("P");
        searchVO1.setFromTerminal("IDPK1");
        searchVO1.setToLocation("IDJKT");
        searchVO1.setToLocType("Y");
        searchVO1.setToTerminal("IDADT");
        searchVO1.setPaymentFsc("FSC1");
        searchVO1.setPriority(1);
        searchVO1.setRemarks("First Record");
        searchVO1.setStartDate("20170915");
        searchVO1.setStatus("A");
        searchVO1.setTransMode("01");
        searchVO1.setUom("KM");
        searchVO1.setVendorCode("VEND11");
        searchVO1.setVendorName("VEND11 Inc. Ltd.");
        actionForm.setContract(searchVO1);
    }
    // 01 END
}
