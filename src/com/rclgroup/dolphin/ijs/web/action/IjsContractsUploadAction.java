/*-----------------------------------------------------------------------------------------------------------
IjsContractsUploadAction.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 20/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 20/10/17  NIIT       IJS            For uploading  contracts
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;

import com.rclgroup.dolphin.ijs.web.ui.IjsContractExcelUploadForm;


import com.rclgroup.dolphin.ijs.web.ui.IjsContractUploadUIM;

import com.rclgroup.dolphin.ijs.web.vo.IjsContractUploadVO;

import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import java.io.File;

import java.io.FileOutputStream;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class IjsContractsUploadAction extends IjsBaseAction{
    private static String EXTENSION = "xls";
    IjsContractUploadVO uploadVO = new IjsContractUploadVO();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {
	    super.execute(mapping,form,request,response);
	   
            
           
            HttpSession session = request.getSession();
	    IjsUserInfoVO userInfo=(IjsUserInfoVO)session.getAttribute("userInfo");
	    if(userInfo==null){
	        userInfo=loadUserData(request,response);
	        session.setAttribute("userInfo",userInfo);
	    }
            String user = userInfo.getUserId();
	   
	    IjsContractExcelUploadForm fileUploadForm = (IjsContractExcelUploadForm)form;

	    FormFile file = fileUploadForm.getFile();
            
	    String fileName = file.getFileName();
            
	    //Get the servers upload directory real path name
	     String extension = FilenameUtils.getExtension(fileName);
             if (!extension.equals(EXTENSION))  {
                 uploadVO.setResultMessage("upload file with .xls format");
                 uploadVO.setIsFailed(true);
                 //return null;
             }
             if (!uploadVO.isIsFailed()) {
                 String filePath = getServlet().getServletContext().getRealPath("/") + "/UPLOAD";
                 //create the upload folder if not exists
                 File folder = new File(filePath);
                 System.out.println("folder" + folder.getAbsolutePath());
                 if(!folder.exists()){
                     folder.mkdir();
                 }

                 //String fileName = fileName;
                 
                 if(!("").equals(fileName)){
                     fileName = user + "_" + fileName; 
                     System.out.println("Server path:" +filePath);
                     File newFile = new File(filePath, fileName);
                     newFile.delete();
                     FileOutputStream fos = new FileOutputStream(newFile);
                     fos.write(file.getFileData());
                       
                     uploadVO.setFileName(fileName);
                     uploadVO.setResultMessage("success");
                     fos.flush();
                     fos.close();
                     request.setAttribute("uploadedFilePath",newFile.getAbsoluteFile());
                     request.setAttribute("uploadedFileName",newFile.getName());
                 }
                 if (!"success".equalsIgnoreCase(uploadVO.getResultMessage())) {
                     uploadVO.setIsFailed(true);
                 }
             }
             
             
	    IjsContractUploadUIM actionForm=(IjsContractUploadUIM)getActionForm();
	    actionForm.setAction("upload");
	    actionForm.setUploadVO(uploadVO);
	    String jsonStr = new Gson().toJson(actionForm); 
	   // System.out.println("Result in json formate  ---  " + jsonStr);
	    response.getWriter().write(jsonStr);
             
            //return mapping.findForward("success");
            return null;
	}

    public void unmarshalJsonRequestToJava(HttpServletRequest request) {
        setActionForm(new IjsContractUploadUIM());
    }

    public void performAction(HttpServletRequest request,HttpServletResponse response) {
        
        
    }
    /**
     * marshalJavaToJson METHOD for converting java object to json
     * @param response
     * @throws IOException
     */
    public void marshalJavaToJson(HttpServletResponse response) throws IOException {
        
    }
}
