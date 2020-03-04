package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsMaintainJOSearchDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsMaintainJOSearchSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsMaintainJOSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsBkgBLScreenSearchParamVO;//can be used same
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IjsMaintainJOSearchAction extends IjsBaseAction {
    private static final String ZIP_TEMPLATE_DOWNLOAD_FILE_NAME="IJS_CONTRACT_DOWNLOAD_TEMPLATE.zip";
    private static final String ZIP_MAINTAIN_DOWNLOAD_FILE_NAME="IJS_MAINTAIN_DOWNLOAD_UPLOAD.zip";
    private static final String DOWNLOAD_LIMIT="DOWNLOAD_LIMIT";
    private static final String ERROR_CD="ERROR_CD";
    private static final String DOWNLOAD_CONTAINER="DOWNLOAD_CONTAINER";
    
    List<IjsMaintainJOSearchUIM> resultList;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        super.execute(mapping, form, request, response);
        return null;
    }

    /**
     * unmarshalJsonRequestToJava method converting json to java object
     * @param request
     * @throws Exception
     */
    public void unmarshalJsonRequestToJava(HttpServletRequest request) throws Exception {
        // unmarshal json to java pojo
        try {
            String ijsSearchJson = getJSONDataFromRequest(request);
           // System.out.println("input data for search ---->" + ijsSearchJson);
            setActionForm(new IjsMaintainJOSearchUIM());
            IjsMaintainJOSearchUIM uim = new IjsMaintainJOSearchUIM();
            IjsMaintainJOSearchUIM actionForm = 
                (IjsMaintainJOSearchUIM)getActionForm();
            String jsonStr = new Gson().toJson(actionForm);
          //  System.out.println("jsonStr in unmarshalling:" + jsonStr);
            
            IjsMaintainJOSearchUIM searchUim = 
                        new Gson().fromJson(ijsSearchJson, 
                                            IjsMaintainJOSearchUIM.class);
            if (searchUim != null) {
                setActionForm(searchUim);
            } else {
                setActionForm(new IjsMaintainJOSearchUIM());
            }
               
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * marshalJavaToJson method converting java object to json
     * @param response
     * @throws Exception
     */
    public void marshalJavaToJson(HttpServletResponse response) throws Exception {
        // marshal java pojo to Json
        System.out.println("[IjsMaintainJOSearchUIM] marshalJavaToJson:");
        IjsMaintainJOSearchUIM actionForm = 
            (IjsMaintainJOSearchUIM)getActionForm();
        String jsonStr = null;
        if (IjsActionMethod.MAINTAIN_JO_SEARCH.getAction().equals(actionForm.getAction())) {
            if (actionForm.getSearchResult() != null && 
                actionForm.getSearchResult().getResult() != null) {
                jsonStr = new Gson().toJson(actionForm);


            } else if (actionForm.getErrorCode() != null) {
                jsonStr = new Gson().toJson(actionForm);
            }
            response.getWriter().write(jsonStr);
         }  else if (IjsActionMethod.MAINTAIN_JO_CANCEL_JO.getAction().equals(actionForm.getAction())
                    || IjsActionMethod.MAINTAIN_JO_APPROVE_JO.getAction().equals(actionForm.getAction())
                    || IjsActionMethod.MAINTAIN_JO_REJECT_JO.getAction().equals(actionForm.getAction())
                    || IjsActionMethod.MAINTAIN_JO_COMPLETE_JO.getAction().equals(actionForm.getAction())) {
                    if (actionForm.getSearchResult() != null && 
                        actionForm.getSearchResult().getResult() != null) {
                        jsonStr = 
                                new Gson().toJson(actionForm.getSearchResult().getResult());
                    } else if (actionForm.getErrorCode() != null) {
                        jsonStr = new Gson().toJson(actionForm);
                    }
                    response.getWriter().write(jsonStr);
            }   else if (IjsActionMethod.MAINTAIN_JO_SAVE_JO.getAction().equals(actionForm.getAction())) {
             if (actionForm.getSearchResult() != null && 
                 actionForm.getSearchResult().getResult() != null) {
                 jsonStr = 
                         new Gson().toJson(actionForm.getSearchResult().getResult());
             } else if (actionForm.getErrorCode() != null) {
                 jsonStr = new Gson().toJson(actionForm);
             } else  {
                 actionForm.setErrorCode("MSG");
                 jsonStr = new Gson().toJson(actionForm); // in case save clicked
             }
             response.getWriter().write(jsonStr);
        }else if(IjsActionMethod.MAINTAIN_JO_JO_DOWNLOAD_LIMIT.getAction().equals(actionForm.getAction())) {
        	 jsonStr = new Gson().toJson(getActionForm());
             response.setContentType("application/json");
             response.getWriter().write(jsonStr);
        }
        else if (IjsActionMethod.MAINTAIN_DOWNLOAD.getAction().equals(actionForm.getAction())) {
        		downloadFilesInZip(response,actionForm.getFilesTobeDownload(),ZIP_MAINTAIN_DOWNLOAD_FILE_NAME);
        }
	    else {
		    actionForm = new IjsMaintainJOSearchUIM();
		    actionForm.setErrorCode("Error on downloading");
		    jsonStr = new Gson().toJson(actionForm);
		    response.getWriter().write(jsonStr);
        }
      //  System.out.println("jsonStr:" + jsonStr);

    }

    
    /**
     * performAction method processing request to service  and getting the result from service
     * @param request
     */
    public void performAction(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("[IjsMaintainJoSearchAction] performAction:");
        IjsMaintainJOSearchUIM actionForm = 
            (IjsMaintainJOSearchUIM)getActionForm();
        IjsMaintainJOSearchSvc ijsMaintainJOSearchSvc = 
            new IjsMaintainJOSearchSvc((IjsMaintainJOSearchDao)getDao("maintainJOSearchDao"));
        HttpSession session = request.getSession();
        IjsUserInfoVO userInfo = 
            (IjsUserInfoVO)session.getAttribute("userInfo");
        //contractTemplateDownload
        if (userInfo == null) {
            userInfo = loadUserData(request,response);
            session.setAttribute("userInfo", userInfo);
        }

        try {
            if (IjsActionMethod.LOAD.getAction().equals(actionForm.getAction())) {
                actionForm.setUserInfo(loadUserData(request,response));
            }
            // 02 START
            else if (IjsActionMethod.MAINTAIN_JO_SEARCH.getAction().equals(actionForm.getAction())) {
                System.out.println("[IjsContractSearchAction] SEARCH:");
                System.out.println("MD123" + actionForm.getMaintainJoParam());
                IjsMaintainJOSearchUIM newActionForm = 
                    ijsMaintainJOSearchSvc.searchMaintainJO(actionForm.getMaintainJoParam(), 
                                                   userInfo != null ? 
                                                   userInfo.getUserId() : "",session);
                newActionForm.setAction("maintainJoSearch");
                setActionForm(newActionForm);

            }
            // 02 END
            // 03 START
            else if (IjsActionMethod.MAINTAIN_JO_COMPLETE_JO.getAction().equals(actionForm.getAction())
                            || IjsActionMethod.MAINTAIN_JO_CANCEL_JO.getAction().equals(actionForm.getAction())
                            || IjsActionMethod.MAINTAIN_JO_APPROVE_JO.getAction().equals(actionForm.getAction())
                            || IjsActionMethod.MAINTAIN_JO_REJECT_JO.getAction().equals(actionForm.getAction())) {
                //TO-DO NEW  or EDIT Contract
                setActionForm(ijsMaintainJOSearchSvc.completeJO(actionForm.getContractsList(), 
                                                                  userInfo != 
                                                                  null ? 
                                                                  userInfo.getUserId() : 
                                                                  "", 
                                                                  actionForm.getAction()));
           }             else if (IjsActionMethod.MAINTAIN_JO_SAVE_JO.getAction().equals(actionForm.getAction())) {
                //save FSC
                if(actionForm.getJoSaveListAll().getJoSaveFscList()!=null && actionForm.getJoSaveListAll().getJoSaveFscList().size() > 0) {
                    setActionForm(ijsMaintainJOSearchSvc.saveFSC(actionForm.getJoSaveListAll().getJoSaveFscList(), 
                                                                      userInfo != 
                                                                      null ? 
                                                                      userInfo.getUserId() : 
                                                                      "", 
                                                                      actionForm.getAction()));
                }
               //Remove Equipment 
               if(actionForm.getJoSaveListAll().getJoRemoveCntrList()!=null && actionForm.getJoSaveListAll().getJoRemoveCntrList().size() > 0) {
                   setActionForm(ijsMaintainJOSearchSvc.removeEquipmentJO(actionForm.getJoSaveListAll().getJoRemoveCntrList(), 
                                                                     userInfo != 
                                                                     null ? 
                                                                     userInfo.getUserId() : 
                                                                     "", 
                                                                     actionForm.getAction()));
               }
               //Replace Equipment
               if(actionForm.getJoSaveListAll().getJoReplaceCntrList()!=null && actionForm.getJoSaveListAll().getJoReplaceCntrList().size() > 0) {
                   setActionForm(ijsMaintainJOSearchSvc.replaceEquipmentJO(actionForm.getJoSaveListAll().getJoReplaceCntrList(), 
                                                                     userInfo != 
                                                                     null ? 
                                                                     userInfo.getUserId() : 
                                                                     "", 
                                                                     actionForm.getAction()));
               }
               //change Vendor
               if(actionForm.getJoSaveListAll().getJoChangeVendorList()!=null && actionForm.getJoSaveListAll().getJoChangeVendorList().size() > 0) {
                   setActionForm(ijsMaintainJOSearchSvc.changeVendor(actionForm.getJoSaveListAll().getJoChangeVendorList(), 
                                                                     userInfo != 
                                                                     null ? 
                                                                     userInfo.getUserId() : 
                                                                     "", 
                                                                     actionForm.getAction()));
               }
                
           } else if(IjsActionMethod.MAINTAIN_JO_JO_DOWNLOAD_LIMIT.getAction().equals(actionForm.getAction())) {
        	   Map<String,String> resultMap=ijsMaintainJOSearchSvc.findJoDownloadLimit(userInfo!=null?userInfo.getUserId():"",actionForm.getMaintainJoParam());
        	   IjsMaintainJOSearchUIM newActionForm = new IjsMaintainJOSearchUIM();
        	   newActionForm.setErrorCode(IjsErrorCode.DB_IJS_MAINT_JO_EX_20006.name().equals(resultMap.get(ERROR_CD))?IjsErrorCode.DB_IJS_MAINT_JO_EX_20006.getErrorCode():null);
        	   newActionForm.setDownloadLimit(Integer.parseInt(resultMap.get(DOWNLOAD_LIMIT)));
        	   newActionForm.setDownloadContainer(Integer.parseInt(resultMap.get(DOWNLOAD_CONTAINER)));
        	   newActionForm.setAction(IjsActionMethod.MAINTAIN_JO_JO_DOWNLOAD_LIMIT.getAction());
        	   setActionForm(newActionForm);
           }
           else if (IjsActionMethod.MAINTAIN_DOWNLOAD.getAction().equals(actionForm.getAction())) {
              String path=getServlet().getServletContext().getRealPath("/")+"/downloadUploadTemplate/";
              System.out.println("File Path:"+path);
              actionForm.setFilesTobeDownload(ijsMaintainJOSearchSvc.downloadContracts(actionForm.getMaintainJoParam(),userInfo!=null?userInfo.getUserId():"",path,session.getId()));
           }else if (IjsActionMethod.MAINTAIN_JO_DELETE_LUMPSUM.getAction().equals(actionForm.getAction())) {
               System.out.println("[IjsContractSearchAction] DELETE LUMPSUM:");
               IjsMaintainJOSearchUIM newActionForm = 
                   ijsMaintainJOSearchSvc.deleteLumpsum(actionForm.getJoDeleteLumpsumList(), 
                                                  userInfo != null ? 
                                                  userInfo.getUserId() : "","JO_MAINTENANCE");
               newActionForm.setAction("maintainJoSearch");
               setActionForm(newActionForm);
           }
           
        } catch (IJSException ie) {
            actionForm.setErrorCode(ie.getMessage());
            setActionForm(actionForm);
        } catch (Exception e) {
        	actionForm.setErrorCode(e.getMessage());
            setActionForm(actionForm);
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String path = new IjsMaintainJOSearchAction().getServlet().getServletContext().getRealPath("/");System.out.println("Pathe " + 
                                                                                                                           path);
    }

    public void setResultList(List<IjsMaintainJOSearchUIM> resultList) {
        this.resultList = resultList;
    }

    public List<IjsMaintainJOSearchUIM> getResultList() {
        return resultList;
    }
    
    private void downloadFilesInZip(HttpServletResponse response,String[] filesTobeDownloaded,String zipFileName){
        FileInputStream fileInputStream = null;
        //OutputStream lobjosrw = null;
        ServletOutputStream lobjosrw = null;
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition","attachment;filename="+zipFileName);
      //  response.setContentType("application/x-download");
       // int i=0;
            FileOutputStream fos = null;
                    ZipOutputStream zipOut = null;
                    FileInputStream fis = null;
                    ByteArrayOutputStream baos=null;
                    String relativePath=filesTobeDownloaded[0];
                    String zipCompletePath=relativePath+"/"+zipFileName;
                    try {
                        fos = new FileOutputStream(zipCompletePath);
                        baos=new ByteArrayOutputStream();
                        zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
                        //zipOut = new ZipOutputStream(baos);
                        for(int i=1;i<filesTobeDownloaded.length;i++){
                       
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
                        zipOut.close();
                      //  baos.close();
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
            int content = 256;
            try {
            // String filePath = actionForm.getDownLoadFile();
            File file=new File(zipCompletePath);
               lobjosrw=response.getOutputStream();
               System.out.println("file creation...");
               fileInputStream = new FileInputStream(file);
               
               System.out.println("file Download before writing...");
               while ((content) >= 0) {
                  content = fileInputStream.read();
                  lobjosrw.write(content);
                  lobjosrw.flush(); 
                }
               fileInputStream.close();
               lobjosrw.close();
               
               file.delete();
            	//lobjosrw = response.getOutputStream();
            	//lobjosrw.write(baos.toByteArray());
            	//lobjosrw.flush();
            	 // baos.close();
            	 // zipOut.close();
                 
                 // response.flushBuffer();
             //  System.out.println("file Download before flushing...");
                //lobjosrw.flush(); 
                System.out.println("file Download completed...");
            } catch (IOException e) {
            	System.out.println("IJS Inside IO Exception:content:"+content);
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
          // delete file from server
        }

    private void getmockIjsContractSearchVO(IjsMaintainJOSearchUIM actionForm) {
        IjsMaintainJOSearchVO searchVO1 = new IjsMaintainJOSearchVO();

        actionForm.setMaintainSearch(searchVO1);
        actionForm.setAction("search");
        IjsBkgBLScreenSearchParamVO<IjsMaintainJOSearchParamVO> contractParam = 
            new IjsBkgBLScreenSearchParamVO<IjsMaintainJOSearchParamVO>();
        IjsMaintainJOSearchParamVO contractParams = 
            new IjsMaintainJOSearchParamVO();
        //contractParams.setFindIn("VendorName");
        //contractParams.setFindValue("MUMBAI PORT");
        contractParam.setTransType("Truck");
        contractParam.setStartDate("09/08/2017");
        contractParam.setEndDate("15/08/2017");
        contractParam.setProcessJoParam(contractParams);
        //        /actionForm.setProcessJoParam(contractParam);

    }

    private void getMockIjsContractSearchVOs(IjsMaintainJOSearchUIM actionForm) {
        IjsMaintainJOSearchVO searchVO1 = new IjsMaintainJOSearchVO();
        IjsMaintainJOSearchVO searchVO2 = new IjsMaintainJOSearchVO();
        IjsMaintainJOSearchVO searchVO3 = new IjsMaintainJOSearchVO();
        IjsSearchResult<IjsMaintainJOSearchVO> result = 
            new IjsSearchResult<IjsMaintainJOSearchVO>();
        List<IjsMaintainJOSearchVO> lstContarcts = 
            new ArrayList<IjsMaintainJOSearchVO>();
        //FIRST RECORD

        lstContarcts.add(searchVO3);
        //END OF delete below code
        result.setResult(lstContarcts);
        actionForm.setSearchResult(result);
    }

    private void getMockIjsContractSearchParams(IjsMaintainJOSearchUIM actionForm) {
        IjsBkgBLScreenSearchParamVO<IjsMaintainJOSearchParamVO> searchParam = 
            new IjsBkgBLScreenSearchParamVO<IjsMaintainJOSearchParamVO>();
        IjsMaintainJOSearchParamVO contractParam = 
            new IjsMaintainJOSearchParamVO();
        //contractParam.setFindIn("Vendor");
        //contractParam.setFindValue("VEND13");
        searchParam.setProcessJoParam(contractParam);
        searchParam.setStartDate("10/08/2017");
        searchParam.setEndDate("15/09/2017");
        searchParam.setTransType("truck;");
        //actionForm.setProcessJoParam(searchParam);
    }

    private void getMockIjsMaintenece(IjsMaintainJOSearchUIM actionForm) {
        IjsMaintainJOSearchVO searchVO1 = new IjsMaintainJOSearchVO();
        IjsMaintainJOSearchVO searchVO3 = new IjsMaintainJOSearchVO();
        searchVO1.setJoNumber("ABC");
        searchVO1.setFsc("MAA");
        List<IjsMaintainJOSearchVO> lst = new ArrayList<IjsMaintainJOSearchVO>();
       // IjsMaintainJOSearchVO jos1=new IjsMaintainJOSearchVO();
        //jos1.set
        List<IjsMaintainJOSearchVO> lst1 = new ArrayList<IjsMaintainJOSearchVO>();
        List<IjsMaintainJOSearchVO> lst2 = new ArrayList<IjsMaintainJOSearchVO>();
        List<IjsMaintainJOSearchVO> lst3 = new ArrayList<IjsMaintainJOSearchVO>();
        lst.add(searchVO1);
        searchVO3.setJoSaveFscList(lst);
        searchVO3.setJoRemoveCntrList(lst1);
        searchVO3.setJoReplaceCntrList(lst2);
        searchVO3.setJoChangeVendorList(lst3);
        
        actionForm.setAction("maintainJoSave");
        actionForm.setJoSaveListAll(searchVO3);
        
    }
    // 01 END
    
    
}
