package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;

import com.rclgroup.dolphin.ijs.web.dao.IjsCommonDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsCommonSvc;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IjsCommonAction extends IjsBaseAction {
    public IjsCommonAction() {
    }
    private String viewJOUrl;
    public void unmarshalJsonRequestToJava(HttpServletRequest request) {
    }

    public void performAction(HttpServletRequest request, 
                              HttpServletResponse response) {
        HttpSession session = request.getSession();
        IjsUserInfoVO userInfo = 
            (IjsUserInfoVO)session.getAttribute("userInfo");
        
        if (userInfo == null) {
            userInfo = loadUserData(request,response);
            session.setAttribute("userInfo", userInfo);
        } 
        IjsCommonSvc userSvc=new IjsCommonSvc((IjsCommonDao)getDao("commonDao"));
        try{
            viewJOUrl=userSvc.getIjsViewJoUrl(userInfo != null ? userInfo.getUserId() : ""); 
            System.out.println("viewJOUrl=="+viewJOUrl);
        }catch(IJSException ie){
            ie.printStackTrace();
        }
        
       
    }

    public void marshalJavaToJson(HttpServletResponse response)throws Exception {
        String jsonStr = null;
       
        jsonStr = new Gson().toJson(getViewJOUrl());
        try {
            response.getWriter().write(jsonStr);
        } catch (IOException e) {
            System.out.println("Exception in marshalling");
            throw e;
        }
    }

    public String getViewJOUrl() {
        return viewJOUrl;
    }
}
