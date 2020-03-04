/*-----------------------------------------------------------------------------------------------------------
IjsUserAction.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            Getting logged user information  functionality
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.action;

import com.google.gson.Gson;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.dao.IjsUserDao;
import com.rclgroup.dolphin.ijs.web.service.IjsUserSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractSearchUIM;
import com.rclgroup.dolphin.ijs.web.ui.IjsUserUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IjsUserAction extends IjsBaseAction{
    //##01 BEGIN
    private static String TRUE="true";
    public IjsUserAction() {
    }
    /**
     * unmarshalJsonRequestToJava method for converting json to java object
     * @param request
     * @throws Exception
     */
    public void unmarshalJsonRequestToJava(HttpServletRequest request) throws Exception{
    setActionForm(new IjsUserUIM());
    }
    /**
     * performAction method for getting user information
     * @param request
     * @throws Exception
     */
    public void performAction(HttpServletRequest request,HttpServletResponse response) throws Exception{
    
        IjsUserUIM actionForm=(IjsUserUIM)getActionForm();
        HttpSession session = request.getSession();
        IjsUserInfoVO userInfo=(IjsUserInfoVO)session.getAttribute("userInfo");
       
            userInfo=loadUserData(request,response);
            session.setAttribute("userInfo",userInfo);
        actionForm.setUserInfo(userInfo);
       
    }
    /**
     * marshalJavaToJson method for converting java object to json
     * @param response
     * @throws Exception
     */
    public void marshalJavaToJson(HttpServletResponse response) throws Exception{
        String jsonStr = null;
        IjsUserUIM actionForm=(IjsUserUIM)getActionForm();
        jsonStr = new Gson().toJson(actionForm.getUserInfo());
        try {
            response.getWriter().write(jsonStr);
        } catch (IOException e) {
            System.out.println("Exception in marshalling");
            throw e;
        }
    }
    //## 01 END
}
