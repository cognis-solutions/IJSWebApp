 /*-----------------------------------------------------------------------------------------------------------
IjsContractVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            value object for user info
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.vo;

public class IjsUserInfoVO {
    private String userId;
    private String personCd;
    private String descr;
    
    private String fscCode;
    private String fscName;
    private String fscLvl1; 
    private String fscLvl2;
    private String fscLvl3;
    private String deptCode;
    private String titleCode;
    
    private String isGroupAuth;
    private String emailId1;
    private String emailId2;
    private String userType="Global";
    
    private String fscCurr;
    private String userAuthType;

    public IjsUserInfoVO() {
    }

    public void setPersonCd(String personCd) {
        this.personCd = personCd;
    }

    public String getPersonCd() {
        return personCd;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDescr() {
        return descr;
    }

    public void setFscCode(String fscCode) {
        this.fscCode = fscCode;
    }

    public String getFscCode() {
        return fscCode;
    }

    public void setFscName(String fscName) {
        this.fscName = fscName;
    }

    public String getFscName() {
        return fscName;
    }

    public void setFscLvl1(String fscLvl1) {
        this.fscLvl1 = fscLvl1;
    }

    public String getFscLvl1() {
        return fscLvl1;
    }

    public void setFscLvl2(String fscLvl2) {
        this.fscLvl2 = fscLvl2;
    }

    public String getFscLvl2() {
        return fscLvl2;
    }

    public void setFscLvl3(String fscLvl3) {
        this.fscLvl3 = fscLvl3;
    }

    public String getFscLvl3() {
        return fscLvl3;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setIsGroupAuth(String isGroupAuth) {
        this.isGroupAuth = isGroupAuth;
    }

    public String getIsGroupAuth() {
        return isGroupAuth;
    }

    public void setEmailId1(String emailId1) {
        this.emailId1 = emailId1;
    }

    public String getEmailId1() {
        return emailId1;
    }

    public void setEmailId2(String emailId2) {
        this.emailId2 = emailId2;
    }

    public String getEmailId2() {
        return emailId2;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setFscCurr(String fscCurr) {
        this.fscCurr = fscCurr;
    }

    public String getFscCurr() {
        return fscCurr;
    }

    public void setUserAuthType(String userAuthType) {
        this.userAuthType = userAuthType;
    }

    public String getUserAuthType() {
        return userAuthType;
    }
}
