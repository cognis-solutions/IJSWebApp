package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsUserRole {
    //## 01 BEGIN
    READ_ONLY("V","readOnly"),MODIFY_ONLY("M","modifyOnly"),GLOBAL("A","Global");
    
    String authorizeCode;
    String authorizeDesc;
    
   IjsUserRole(String authorizeCode,String authorizeDesc) {
        this.authorizeCode = authorizeCode;
        this.authorizeDesc = authorizeDesc;
    }
    public String getAuthorizeCode() {
        return authorizeCode;
    }
    public String getAuthorizeDesc() {
        return authorizeDesc;
    }
    //## 01 END
}
