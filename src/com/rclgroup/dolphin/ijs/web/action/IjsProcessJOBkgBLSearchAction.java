package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;
import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsCommonDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsProcessJOBkgBLSearchDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsCommonSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsJOUploadSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsProcessJOBkgBLSearchSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsProcessJOBkgBLSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsBkgBLScreenSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOBkgBLSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOBkgBLSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IjsProcessJOBkgBLSearchAction extends IjsBaseAction {
    private static final String ZIP_TEMPLATE_DOWNLOAD_FILE_NAME = 
        "IJS_CONTAINER_DOWNLOAD_TEMPLATE.zip";
    //## O1 BEGIN
    List<IjsProcessJOBkgBLSearchUIM> resultList;

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
            
            setActionForm(new IjsProcessJOBkgBLSearchUIM());
            
            IjsProcessJOBkgBLSearchUIM searchUim = 
                new Gson().fromJson(ijsSearchJson, IjsProcessJOBkgBLSearchUIM.class);
            
            if (searchUim != null) {
                setActionForm(searchUim);
            } else {
                setActionForm(new IjsProcessJOBkgBLSearchUIM());
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
        
        IjsProcessJOBkgBLSearchUIM actionForm = 
            (IjsProcessJOBkgBLSearchUIM)getActionForm();
        String jsonStr = null;
        if (IjsActionMethod.PROCESS_JO_SEARCH_BOOKING_BL.getAction().equals(actionForm.getAction())) {
            if (actionForm.getSearchResult() != null && 
                actionForm.getSearchResult().getResult() != null) {
                jsonStr = 
                        new Gson().toJson(actionForm.getSearchResult().getResult());
            } else if (actionForm.getErrorCode() != null) {
                jsonStr = new Gson().toJson(actionForm);
            }
            response.getWriter().write(jsonStr);
        }
        //## 02 END
         else if (IjsActionMethod.CREATE_JOB.getAction().equals(actionForm.getAction())) {
             if (actionForm.getSearchResult() != null && 
                 actionForm.getSearchResult().getResult() != null) {
                 jsonStr = 
                         new Gson().toJson(actionForm.getSearchResult().getResult());
             } else if (actionForm.getErrorCode() != null) {
                 jsonStr = new Gson().toJson(actionForm);
             }
             response.getWriter().write(jsonStr);
         } else if (IjsActionMethod.CREATE_JOB_ADHOC.getAction().equals(actionForm.getAction())) {
            if (actionForm.getSearchResult() != null && 
                actionForm.getSearchResult().getResult() != null) {
                jsonStr = 
                        new Gson().toJson(actionForm.getSearchResult());
            } else if (actionForm.getErrorCode() != null) {
                jsonStr = new Gson().toJson(actionForm);
            }
            response.getWriter().write(jsonStr);
        } 
        else if (IjsActionMethod.RESET_JOB_ORDER.getAction().equals(actionForm.getAction())) {
            if (actionForm.getErrorCode() != null) {
                jsonStr = new Gson().toJson(actionForm);
            }
            response.getWriter().write(jsonStr);

        }
         else if (IjsActionMethod.PROCESS_JO_SUMMARY_BOOKING_BL.getAction().equals(actionForm.getAction())
        		 || IjsActionMethod.ADHOC_JO_SUMMARY.getAction().equals(actionForm.getAction())
        		 || IjsActionMethod.PROCESS_JO_DELETE_LUMPSUM.getAction().equals(actionForm.getAction())) {
             if (actionForm.getSearchResult() != null && 
                 actionForm.getSearchResult().getResult() != null) {
                 jsonStr = 
                         new Gson().toJson(actionForm.getSearchResult());
             } else if (actionForm.getErrorCode() != null) {
                 jsonStr = new Gson().toJson(actionForm);
             }
             response.getWriter().write(jsonStr);
         }
        //##04 BEGIN
        else if (IjsActionMethod.EDIT.getAction().equals(actionForm.getAction())) {
            // EDIT Contract
            if (actionForm.getErrorCode() != null) {
                jsonStr = new Gson().toJson(actionForm);
                response.getWriter().write(jsonStr);
            }
        } else if (IjsActionMethod.DELETE.getAction().equals(actionForm.getAction())) {
            // DELETE Contract
            if (actionForm.getBookingBLResult() != null && 
                actionForm.getBookingBLResult().size() > 0) {
                jsonStr = new Gson().toJson(actionForm);
                response.getWriter().write(jsonStr);
            }
        }  else if (IjsActionMethod.CONTAINER_TEMPLETE_DOWNLOAD.getAction().equals(actionForm.getAction())) {
                downloadFilesInZip(response,actionForm.getFilesTobeDownload(),ZIP_TEMPLATE_DOWNLOAD_FILE_NAME);
              //  return;
        }  else if (IjsActionMethod.CONTAINER_UPLOAD.getAction().equals(actionForm.getAction())) {
                    jsonStr = new Gson().toJson(actionForm); 
                    response.getWriter().write(jsonStr);
        }else if (IjsActionMethod.PROCESS_JO_EQUIPMENT_LIMIT.getAction().equals(actionForm.getAction())) {
                jsonStr = new Gson().toJson(actionForm.getMaxEquipLimit()); 
                response.getWriter().write(jsonStr);
        }else if (IjsActionMethod.PROCESS_JO_EQUIPMENT_LIST.getAction().equals(actionForm.getAction())) {
        	if(actionForm.getEqList()!=null || !actionForm.getEqList().isEmpty()){
        		jsonStr = new Gson().toJson(actionForm.getEqList());
        	}else{
        		 actionForm = new IjsProcessJOBkgBLSearchUIM();
                 actionForm.setErrorCode(IjsErrorCode.UI_GBL_IJS_EX_10001.getErrorCode());
                 jsonStr = new Gson().toJson(actionForm);
        	}
             
            response.getWriter().write(jsonStr);
        } else {
            actionForm = new IjsProcessJOBkgBLSearchUIM();
            actionForm.setErrorCode("Error on downloading");
            jsonStr = new Gson().toJson(actionForm);
            response.getWriter().write(jsonStr);
        }
        // response.getWriter().write(jsonStr);
    }

    private void downloadFilesInZip(HttpServletResponse response, 
                                    String[] filesTobeDownloaded, 
                                    String zipFileName) {
        FileInputStream fileInputStream = null;
        OutputStream lobjosrw = null;
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", 
                           "attachment;filename=" + zipFileName);
        //  response.setContentType("application/x-download");
        // int i=0;
        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
        String relativePath = filesTobeDownloaded[0];
        String zipCompletePath = relativePath + "/" + zipFileName;
        try {
            fos = new FileOutputStream(zipCompletePath);
            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
            for (int i = 1; i < filesTobeDownloaded.length; i++) {

                File input = new File(relativePath + filesTobeDownloaded[i]);
                fis = new FileInputStream(input);
                ZipEntry ze = new ZipEntry(input.getName());
                
                zipOut.putNextEntry(ze);
                byte[] tmp = new byte[4 * 1024];
                int size = 0;
                while ((size = fis.read(tmp)) != -1) {
                    zipOut.write(tmp, 0, size);
                }
                zipOut.flush();
                fis.close();
            }
            zipOut.close();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception ex) {

            }
        }

        // response.setHeader("Cache-Control", "public");
        //response.setHeader("Pragma", "public");
        try {
            // String filePath = actionForm.getDownLoadFile();
            File file = new File(zipCompletePath);
            lobjosrw = response.getOutputStream();
            fileInputStream = new FileInputStream(file);
            int content = 256;
            while ((content) >= 0) {
                content = fileInputStream.read();
                lobjosrw.write(content);
            }
            // response.flushBuffer();
            lobjosrw.flush();
            
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

    private void downloadFile(HttpServletResponse response, 
                              String relativePath, String fileName) {
        FileInputStream fileInputStream = null;
        OutputStream lobjosrw = null;
        response.setContentType("application/x-download");
        if (fileName != null && fileName.length() > 0) {
            response.setHeader("Content-Disposition", 
                               "attachment;filename=" + fileName);
            response.setHeader("Cache-Control", "public");
            response.setHeader("Pragma", "public");
            try {
                // String filePath = actionForm.getDownLoadFile();
                File file = new File(relativePath + fileName);
                lobjosrw = response.getOutputStream();
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
        
        IjsProcessJOBkgBLSearchUIM actionForm = 
            (IjsProcessJOBkgBLSearchUIM)getActionForm();
        IjsProcessJOBkgBLSearchSvc bkgBLSearchSvc = 
            new IjsProcessJOBkgBLSearchSvc((IjsProcessJOBkgBLSearchDao)getDao("ProcessJOBkgBLSearchDao"),new IjsJOUploadSvc((IjsCommonDao)getDao("commonDao")));
      //  IjsJOUploadSvc uploadSvc=new IjsJOUploadSvc((IjsCommonDao)getDao("commonDao"));
        HttpSession session = request.getSession();
        IjsUserInfoVO userInfo = 
            (IjsUserInfoVO)session.getAttribute("userInfo");
        //contractTemplateDownload
        if (userInfo == null) {
            userInfo = loadUserData(request,response);
            session.setAttribute("userInfo", userInfo);
        }
        
        String lstrSessionId = session.getId();

        try {
            if (IjsActionMethod.LOAD.getAction().equals(actionForm.getAction())) {
                actionForm.setUserInfo(loadUserData(request,response));
            }
            // 02 START
            else if (IjsActionMethod.PROCESS_JO_SEARCH_BOOKING_BL.getAction().equals(actionForm.getAction())) {
                
                
                IjsProcessJOBkgBLSearchUIM newActionForm = 
                    bkgBLSearchSvc.searchBookingBL(actionForm.getProcessJoParam(), 
                                                     userInfo != null ? 
                                                     userInfo.getUserId() : 
                                                     "",session.getId());
                newActionForm.setAction("joSearch");
                setActionForm(newActionForm);

            }
            else if (IjsActionMethod.PROCESS_JO_SUMMARY_BOOKING_BL.getAction().equals(actionForm.getAction())) {
                
                
                IjsProcessJOBkgBLSearchUIM newActionForm = 
                    bkgBLSearchSvc.searchJOSummary(actionForm.getProcessJoParam(), actionForm.getTransMode(),
                                                    actionForm.getLstJOSummaryParam(),
                                                    actionForm.getProcessJoType(),
                                                     lstrSessionId,
                                                     userInfo != null ? 
                                                     userInfo.getUserId() : 
                                                     "");
                newActionForm.setAction("joSummary");
                setActionForm(newActionForm);

            }    
            else if(IjsActionMethod.CHANGE_VENDOR.getAction().equals(actionForm.getAction())) {
                        setActionForm(bkgBLSearchSvc.changeVendor(actionForm.getBk_bl_ad(),
                                                                  actionForm.getBkgblNumber(),
                                                                  actionForm.getRoutingId(),
                                                                    actionForm.getRoutingIdOLD(),
                                                                    actionForm.getVendorID(),
                                                                  actionForm.getVendorIDOLD(), 
                                                                  actionForm.getContractId(),
                                                                  actionForm.getTransMode(),
                                                                          userInfo != 
                                                                          null ? 
                                                                          userInfo.getUserId() : 
                                                                          "", 
                                                                          actionForm.getAction()));
                    }
            else if (IjsActionMethod.ADHOC_JO_SUMMARY.getAction().equals(actionForm.getAction())) {
                
                
                IjsProcessJOBkgBLSearchUIM newActionForm = 
                    bkgBLSearchSvc.searchJOSummaryAdhoc(actionForm.getRoutingList(), 
                                                        actionForm.getProcessJoType(),
                                                        actionForm.getVendorCode(),
                                                        actionForm.getEqList(),
                                                        actionForm.getEqDetails(),
                                                     lstrSessionId,
                                                     userInfo != null ? 
                                                     userInfo.getUserId() : 
                                                     "");
                newActionForm.setAction("joSummeryAdhoc");
                setActionForm(newActionForm);

            }   
            else if (IjsActionMethod.CREATE_JOB.getAction().equals(actionForm.getAction())) {
                
                IjsProcessJOBkgBLSearchUIM newActionForm = 
                    bkgBLSearchSvc.createJob(actionForm.getProcessjoFieldList(), 
                                                        actionForm.getReasonCode(),
                                                        actionForm.getTransMode(),
                                                        actionForm.getProcessJoType(),
                                                     lstrSessionId,actionForm.getRoutingId(),
                                                     userInfo != null ? 
                                                     userInfo.getUserId() : 
                                                     "");
                newActionForm.setAction("createJob");
                setActionForm(newActionForm);

            }   
            else if (IjsActionMethod.CREATE_JOB_ADHOC.getAction().equals(actionForm.getAction())) {
                
                
                IjsProcessJOBkgBLSearchUIM newActionForm = 
                    bkgBLSearchSvc.createJob(actionForm.getProcessjoFieldList(), actionForm.getTransMode(),
                                                        actionForm.getReasonCode(),
                                                         actionForm.getProcessJoType(),
                                                     lstrSessionId,actionForm.getRoutingId(),
                                                     userInfo != null ? 
                                                     userInfo.getUserId() : 
                                                     "");
                newActionForm.setAction("createJob");
                setActionForm(newActionForm);

            }               
            else if (IjsActionMethod.RESET_JOB_ORDER.getAction().equals(actionForm.getAction())) {
                
                IjsProcessJOBkgBLSearchUIM newActionForm = 
                    bkgBLSearchSvc.resetJO(lstrSessionId,
                                                     userInfo != null ? 
                                                     userInfo.getUserId() : 
                                                     "");
                newActionForm.setAction("resetJoSummury");
                newActionForm.setErrorCode("IJS_MSG_JO_RESET_SUCCESS");
                setActionForm(newActionForm);

            }             
            // 02 END
            // 03 START
            else if (IjsActionMethod.NEW.getAction().equals(actionForm.getAction()) || IjsActionMethod.EDIT.getAction().equals(actionForm.getAction())) {
                //TO-DO NEW  or EDIT Contract
                setActionForm(bkgBLSearchSvc.saveOrUpdateContract(actionForm.getBookingBL(), 
                                                                     userInfo != 
                                                                     null ? 
                                                                     userInfo.getUserId() : 
                                                                     "", 
                                                                     actionForm.getAction()));
            }
            
            else if (IjsActionMethod.CONTAINER_TEMPLETE_DOWNLOAD.getAction().equals(actionForm.getAction())) {
	            String [] templateFiles= new String[2];
	            String relativePath=getServlet().getServletContext().getRealPath("/")+"/downloadUploadTemplate/";
	            templateFiles[0]=relativePath;
	            templateFiles[1]="IJS_Container_Download_Upload_Template.xls";
	            actionForm.setFilesTobeDownload(templateFiles);
	            setActionForm(actionForm);
            }
            else if (IjsActionMethod.PROCESS_JO_EQUIPMENT_LIMIT.getAction().equals(actionForm.getAction())) {
            	actionForm.setMaxEquipLimit(bkgBLSearchSvc.getMaxEquipLimit());
                setActionForm(actionForm);
            }  else if (IjsActionMethod.PROCESS_JO_EQUIPMENT_LIST.getAction().equals(actionForm.getAction())) {
            	actionForm.setEqList(bkgBLSearchSvc.getEqTypeList(userInfo.getUserId()));
                setActionForm(actionForm);
            }  
            else if (IjsActionMethod.PROCESS_JO_DELETE_LUMPSUM.getAction().equals(actionForm.getAction())) {
            	IjsProcessJOBkgBLSearchUIM uim=bkgBLSearchSvc.deleteLumpsum(actionForm.getJobOrderLst(),actionForm.getAllJobOrders(),userInfo.getUserId(),lstrSessionId);
            	uim.setAction(IjsActionMethod.PROCESS_JO_DELETE_LUMPSUM.getAction());
                setActionForm(uim);
            }  
            else if (IjsActionMethod.CONTAINER_UPLOAD.getAction().equals(actionForm.getUploadVO().getAction())) {
                String path = getServlet().getServletContext().getRealPath("/") + "/UPLOAD";
                actionForm.getUploadVO().setFolderPath(path);
                setActionForm(bkgBLSearchSvc.uploadContainer(actionForm, userInfo!=null?userInfo.getUserId():"",actionForm.getUploadVO().getAddhocType(),
                actionForm.getUploadVO().getFolderPath(),actionForm.getUploadVO().getFileName(),actionForm.getUploadVO().getContractId(), actionForm.getUploadVO().getContractsID()));
            }
            // 03 END

        } catch (IJSException ie) {
            actionForm.setErrorCode(ie.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String path = new IjsProcessJOBkgBLSearchAction().getServlet().getServletContext().getRealPath("/");
        
    }

    public void setResultList(List<IjsProcessJOBkgBLSearchUIM> resultList) {
        this.resultList = resultList;
    }

    public List<IjsProcessJOBkgBLSearchUIM> getResultList() {
        return resultList;
    }

    private void getmockIjsContractSearchVO(IjsProcessJOBkgBLSearchUIM actionForm){
        IjsProcessJOBkgBLSearchVO searchVO1= new IjsProcessJOBkgBLSearchVO();
        
        actionForm.setBookingBL(searchVO1);
        actionForm.setAction("search");
        IjsBkgBLScreenSearchParamVO<IjsProcessJOBkgBLSearchParamVO> contractParam=new IjsBkgBLScreenSearchParamVO<IjsProcessJOBkgBLSearchParamVO>();
        IjsProcessJOBkgBLSearchParamVO contractParams=new IjsProcessJOBkgBLSearchParamVO();
        //contractParams.setFindIn("VendorName");
        //contractParams.setFindValue("MUMBAI PORT");
        contractParam.setTransType("Truck");
        contractParam.setStartDate("09/08/2017");
        contractParam.setEndDate("15/08/2017");
        contractParam.setProcessJoParam(contractParams);
//        /actionForm.setProcessJoParam(contractParam);
        
    }

    private void getMockIjsContractSearchVOs(IjsProcessJOBkgBLSearchUIM actionForm) {
        IjsProcessJOBkgBLSearchVO searchVO1 = new IjsProcessJOBkgBLSearchVO();
        IjsProcessJOBkgBLSearchVO searchVO2 = new IjsProcessJOBkgBLSearchVO();
        IjsProcessJOBkgBLSearchVO searchVO3 = new IjsProcessJOBkgBLSearchVO();
        IjsSearchResult<IjsProcessJOBkgBLSearchVO> result = 
            new IjsSearchResult<IjsProcessJOBkgBLSearchVO>();
        List<IjsProcessJOBkgBLSearchVO> lstContarcts = new ArrayList<IjsProcessJOBkgBLSearchVO>();
        //FIRST RECORD
        
        lstContarcts.add(searchVO3);
        //END OF delete below code
        result.setResult(lstContarcts);
        actionForm.setSearchResult(result);
    }

    private void getMockIjsContractSearchParams(IjsProcessJOBkgBLSearchUIM actionForm) {
        IjsBkgBLScreenSearchParamVO<IjsProcessJOBkgBLSearchParamVO> searchParam = 
            new IjsBkgBLScreenSearchParamVO<IjsProcessJOBkgBLSearchParamVO>();
        IjsProcessJOBkgBLSearchParamVO contractParam = 
            new IjsProcessJOBkgBLSearchParamVO();
        //contractParam.setFindIn("Vendor");
        //contractParam.setFindValue("VEND13");
        searchParam.setProcessJoParam(contractParam);
        searchParam.setStartDate("10/08/2017");
        searchParam.setEndDate("15/09/2017");
        searchParam.setTransType("truck;");
        //actionForm.setProcessJoParam(searchParam);
    }

    private void getMockIjsNewContract(IjsProcessJOBkgBLSearchUIM actionForm) {
        IjsProcessJOBkgBLSearchVO searchVO1 = new IjsProcessJOBkgBLSearchVO();
        
        actionForm.setBookingBL(searchVO1);
    }
    // 01 END
}
