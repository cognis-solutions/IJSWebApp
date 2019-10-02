/*-----------------------------------------------------------------------------------------------------------
IjsBaseAction.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            base action class for IJS development
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.action;

import java.io.BufferedReader;
import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsUserDao;
import com.rclgroup.dolphin.ijs.web.factory.IjsDAOFactory;
import com.rclgroup.dolphin.ijs.web.service.IjsUserSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsBaseActionForm;
import com.rclgroup.dolphin.ijs.web.vo.IjsUserInfoVO;


public abstract class IjsBaseAction extends Action{
   //##01 BEGIN
   private static String TRUE="true";
   private static final String POST="POST";
   private static final String GET="GET";
   private static final String DATA="data";
   private static final String contractTemplateName="IJS_Contract_Rate_Download_Upload_Template.xls";
   private IjsBaseActionForm actionForm;
   private ServletContext servletContext;
   private File downLoadFile;
   public ActionForward execute(ActionMapping mapping, 
			                     ActionForm form,
			                     HttpServletRequest request, 
			                     HttpServletResponse response) throws Exception {
        
        try{
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
           // response.setHeader("Access-Control-Allow-Origin", "*");
          //  System.out.println("inside BaseAction execute:");
           
            unmarshalJsonRequestToJava(request);
           // HttpSession session= request.getSession(false);
           // System.out.println("Base session id="+session.getId());
          //  setServletContext(request);
           // Principal principal=request.getUserPrincipal();
          //  if(session.isNew()){
          //  	System.out.println("New session");
          //  }
            setServletContext(request);
            performAction(request,response);
            marshalJavaToJson(response);
			//take action methods from enum
			
        } catch(Exception e){
            e.printStackTrace();
            response.getWriter().write("IJS_FATAL_ERR");
        }catch(Throwable th){
            response.getWriter().write("IJS_FATAL_ERR");
        }
	return null;
	}
   protected String getJSONDataFromRequest(HttpServletRequest  request) throws Exception {
   String ijsSearchJson=null;
   String method=request.getMethod();
       if(GET.equals(method)){
           ijsSearchJson=request.getParameter(DATA);
       }else if(POST.equals(method)){
           StringBuilder buffer = new StringBuilder();
           BufferedReader reader = request.getReader();
           String line;
           while ((line = reader.readLine()) != null) {
               buffer.append(line);
           }
           ijsSearchJson = buffer.toString();
       }  
     //  System.out.println("Json Request data-------->" + ijsSearchJson);
       return ijsSearchJson;
   }
    
        
	public abstract void unmarshalJsonRequestToJava(HttpServletRequest  request) throws Exception;
	public abstract void performAction(HttpServletRequest  request,HttpServletResponse  response) throws Exception;
	public abstract void marshalJavaToJson(HttpServletResponse response) throws Exception;
    /**
     * loadUserData method loads the user data
     * @param request
     * @return
     */
	public  IjsUserInfoVO loadUserData(HttpServletRequest  request,HttpServletResponse  response){
	    System.out.println("inside loadUserDataInSession:");
	    String[]arrUserInfoa = null;
	    IjsUserInfoVO user=null;
	    org.apache.struts.util.MessageResources resource=getResources(request);
	    String userName=null;
	    String testdata=null;
		/*
		 * if(TRUE.equals(resource.getMessage("rcl.ijs.cookies.enabled"))){ //get
		 * cookies and find user name
		 * System.out.println("[IjsBaseAction][execute]: start get cookie"); Cookie[ ]
		 * cookies = request.getCookies ();// get cookie
		 * 
		 * if(cookies!=null && cookies.length>0){ testdata+="Cookies--><br>"; for(int
		 * i=0;i<cookies.length;i++){ String name = cookies[i].getName (); String value
		 * = cookies[i].getValue (); // cookies[i].setMaxAge(2); // response.addCookie(
		 * cookies[i]); testdata +=name+"="+value +"<br>";
		 * if(name.equals("RCL_AUTH_KEY")){
		 * System.out.println("[IjsBaseAction][execute]: found cookie RCL_AUTH_KEY");
		 * arrUserInfoa=value.split("~");
		 * //System.out.println("[IjsBaseAction][execute]: found cookie arrUserInfoa:"
		 * +arrUserInfoa); //
		 * System.out.println("[IjsBaseAction][execute]: found cookie userName:"
		 * +arrUserInfoa[0]); } } } }
		 * if(TRUE.equals(resource.getMessage("rcl.ijs.dev.user.enabled"))){ //
		 * System.out.println("[IjsBaseAction][execute]: start dev user");
		 * userName=resource.getMessage("rcl.ijs.dev.user.id"); }
		 * 
		 * 
		 * IjsUserSvc userSvc=null; try{ userSvc = new
		 * IjsUserSvc((IjsUserDao)getDao("userDao"));
		 * 
		 * if(arrUserInfoa!=null &&arrUserInfoa.length!=0){ userName=arrUserInfoa[0]; }
		 * if(userName!=null){ user=userSvc.findUserInfo(userName); //
		 * System.out.println("[IjsBaseAction][execute]: start user:"+user); }
		 * }catch(Exception e){ e.printStackTrace(); }
		 */	   
	    
	    System.out.println("ddddddddddddde");
	    IjsUserSvc userSvc=new IjsUserSvc((IjsUserDao)getDao("userDao"));
       userName="NIIT01";
	   user=userSvc.findUserInfo(userName);
	    return user;
	}
        
    public IjsBaseDao getDao(String requestedDao){
       IjsBaseDao dao = IjsDAOFactory.getInstance().getDao(requestedDao,getServletContext());
       return dao;
    }

    public void setActionForm(IjsBaseActionForm actionForm) {
        this.actionForm = actionForm;
    }

    public IjsBaseActionForm getActionForm() {
        return actionForm;
    }

    public void setServletContext(HttpServletRequest request) {
    	HttpSession session=request.getSession();
            this.servletContext =session.getServletContext(); 
        //}
     }

    public ServletContext getServletContext() {
        return servletContext;
    }
    //##01 END
    
    public String getTemplateName(){
        return contractTemplateName;
    }

    public void setDownLoadFile(File downLoadFile) {
        this.downLoadFile = downLoadFile;
    }

    public File getDownLoadFile() {
        return downLoadFile;
    }
}
