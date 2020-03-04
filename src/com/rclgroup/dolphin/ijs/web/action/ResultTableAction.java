package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;
import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsCommonDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsProcessJOBkgBLSearchDao;
import com.rclgroup.dolphin.ijs.web.dao.impl.IJSResultContainerUpdateDaoImpl;
import com.rclgroup.dolphin.ijs.web.dao.impl.IjsJoEnquiryJdbcDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IJSContainerUpdateSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsCommonSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsJOUploadSvc;
import com.rclgroup.dolphin.ijs.web.service.IjsProcessJOBkgBLSearchSvc;
import com.rclgroup.dolphin.ijs.web.ui.IJSResultTableContainerUpdateUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsJoEnquiryUIM;
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

public class ResultTableAction extends IjsBaseAction {
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
			/*
			 * setActionForm(new IJSResultTableContainerUpdateUIM());
			 * 
			 * 
			 * IJSResultTableContainerUpdateUIM uim = new
			 * IJSResultTableContainerUpdateUIM(); IJSResultTableContainerUpdateUIM
			 * actionForm = (IJSResultTableContainerUpdateUIM)getActionForm();
			 */
            IJSResultTableContainerUpdateUIM resultTablecontainerUpdateUIM = new IJSResultTableContainerUpdateUIM();
            resultTablecontainerUpdateUIM.setContainerJson(ijsSearchJson);
            
         
            System.out.println(resultTablecontainerUpdateUIM.getContainerJson());
            
            setServletContext(request);
            IJSContainerUpdateSvc contUpdate = new IJSContainerUpdateSvc((IJSResultContainerUpdateDaoImpl)getDao("containerUpdateDao"));
            contUpdate.getContainerUpdateJson(resultTablecontainerUpdateUIM);
			/*
			 * IjsProcessJOBkgBLSearchUIM a = new IjsProcessJOBkgBLSearchUIM();
			 * a.setContainer(ijsSearchJson);
			 */
            
            
			/*
			 * IjsProcessJOBkgBLSearchUIM searchUim = new Gson().fromJson(ijsSearchJson,
			 * IjsProcessJOBkgBLSearchUIM.class);
			 * 
			 * if (searchUim != null) { setActionForm(searchUim); } else { setActionForm(new
			 * IjsProcessJOBkgBLSearchUIM()); }
			 */
        } catch (Exception e) {
           throw e;
        }
    }

	@Override
	public void performAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println();
		
	}

	@Override
	public void marshalJavaToJson(HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println();
		
	}

}