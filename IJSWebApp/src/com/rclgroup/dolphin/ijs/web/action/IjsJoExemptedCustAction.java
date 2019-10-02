package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.dao.IjsJoExemptedCustDao;
import com.rclgroup.dolphin.ijs.web.dao.impl.IjsJoExemptedCustJdbcDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsJoExemptedCustSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsJoExemptedCustUIM;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IjsJoExemptedCustAction extends IjsBaseAction {
    private static final String ZIP_TEMPLATE_DOWNLOAD_FILE_NAME = 
        "IJS_CONTRACT_DOWNLOAD_TEMPLATE.zip";
    private static final String ZIP_MAINTAIN_DOWNLOAD_FILE_NAME = 
        "IJS_MAINTAIN_DOWNLOAD_UPLOAD.zip";

    List<IjsJoExemptedCustUIM> resultList;

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
        try {
            String ijsSearchJson = getJSONDataFromRequest(request);
            System.out.println("input data for search ---->" + ijsSearchJson);
            setActionForm(new IjsJoExemptedCustUIM());
            IjsJoExemptedCustUIM uim = new IjsJoExemptedCustUIM();
            IjsJoExemptedCustUIM actionForm = 
                (IjsJoExemptedCustUIM)getActionForm();
            String jsonStr = new Gson().toJson(actionForm);
          //  System.out.println("jsonStr in unmarshalling:" + jsonStr);

            IjsJoExemptedCustUIM searchUim = 
                new Gson().fromJson(ijsSearchJson, 
                                    IjsJoExemptedCustUIM.class);
            if (searchUim != null) {
                setActionForm(searchUim);
            } else {
                setActionForm(new IjsJoExemptedCustUIM());
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
        System.out.println("[IjsMaintainJOSearchUIM] marshalJavaToJson:");
        IjsJoExemptedCustUIM actionForm = 
            (IjsJoExemptedCustUIM)getActionForm();
        String jsonStr = null;
        if (IjsActionMethod.EXEMPTED_CUST_SEARCH.getAction().equals(actionForm.getAction())) {
            if (actionForm.getSearchResult() != null && 
                actionForm.getSearchResult().getResult() != null) {
                jsonStr = 
                        new Gson().toJson(actionForm.getSearchResult().getResult());
            } else if (actionForm.getErrorCode() != null) {
                jsonStr = new Gson().toJson(actionForm);
            }
            response.getWriter().write(jsonStr);
        }else if (IjsActionMethod.EXEMPTED_CUST_ADD.getAction().equals(actionForm.getAction())
        || IjsActionMethod.EXEMPTED_CUST_EDIT.getAction().equals(actionForm.getAction())
        || IjsActionMethod.EXEMPTED_CUST_DELETE.getAction().equals(actionForm.getAction())) {
            if (actionForm.getSearchResult() != null && 
                actionForm.getSearchResult().getResult() != null) {
                jsonStr = 
                        new Gson().toJson(actionForm.getSearchResult().getResult());
            } else if (actionForm.getErrorCode() != null) {
                jsonStr = new Gson().toJson(actionForm);
            } else {
                actionForm.setErrorCode("MSG");
                jsonStr = 
                        new Gson().toJson(actionForm); // in case save clicked
            }
            response.getWriter().write(jsonStr);
        } else {
            actionForm = new IjsJoExemptedCustUIM();
            actionForm.setErrorCode("Error on downloading");
            jsonStr = new Gson().toJson(actionForm);
            response.getWriter().write(jsonStr);
        }
      //  System.out.println("jsonStr:" + jsonStr);

        // response.getWriter().write(jsonStr);
    }


    /**
     * performAction method processing request to service  and getting the result from service
     * @param request
     */
    public void performAction(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("[IjsMaintainJoSearchAction] performAction:");
        IjsJoExemptedCustUIM actionForm = 
            (IjsJoExemptedCustUIM)getActionForm();
        IjsJoExemptedCustSvc ijsJoExemptedCustSvc = 
            new IjsJoExemptedCustSvc((IjsJoExemptedCustJdbcDao)getDao("joExemptedCustJdbcDao"));
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
            else if (IjsActionMethod.EXEMPTED_CUST_SEARCH.getAction().equals(actionForm.getAction())) {
                System.out.println("[IjsContractSearchAction] SEARCH:");
                IjsJoExemptedCustUIM newActionForm = 
                    ijsJoExemptedCustSvc.findAllCust(actionForm.getSearchParam(), 
                                                   userInfo != null ? 
                                                   userInfo.getUserId() : "");
                newActionForm.setAction("exemptSearch");
                setActionForm(newActionForm);

            }
            // 02 END
            // 03 START
            else if (IjsActionMethod.EXEMPTED_CUST_ADD.getAction().equals(actionForm.getAction())
                    || IjsActionMethod.EXEMPTED_CUST_EDIT.getAction().equals(actionForm.getAction())) {
                //TO-DO NEW  or EDIT Contract
                setActionForm(ijsJoExemptedCustSvc.addEditJO(actionForm.getCustSaveList(), 
                                                                userInfo != 
                                                                null ? 
                                                                userInfo.getUserId() : 
                                                                "", 
                                                                actionForm.getAction()));
            }  else if (IjsActionMethod.EXEMPTED_CUST_DELETE.getAction().equals(actionForm.getAction())) {
                //TO-DO NEW  or EDIT Contract
                setActionForm(ijsJoExemptedCustSvc.deleteCust(actionForm.getCustDelList(), 
                                                                userInfo != 
                                                                null ? 
                                                                userInfo.getUserId() : 
                                                                "", 
                                                                actionForm.getAction()));
            }  

        } catch (IJSException ie) {
            actionForm.setErrorCode(ie.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String path = new IjsJoExemptedCustAction().getServlet().getServletContext().getRealPath("/");System.out.println("Pathe " + 
                                                                                                                         path);
    }

    public void setResultList(List<IjsJoExemptedCustUIM> resultList) {
        this.resultList = resultList;
    }

    public List<IjsJoExemptedCustUIM> getResultList() {
        return resultList;
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
        String zipCompletePath = relativePath + "\\" + zipFileName;
        try {
            fos = new FileOutputStream(zipCompletePath);
            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
            for (int i = 1; i < filesTobeDownloaded.length; i++) {

                File input = new File(relativePath + filesTobeDownloaded[i]);
                fis = new FileInputStream(input);
                ZipEntry ze = new ZipEntry(input.getName());
                System.out.println("Zipping the file: " + input.getName());
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
            System.out.println("Done... Zipped the files...");
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

    }



}
