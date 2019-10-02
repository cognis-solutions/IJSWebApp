 /*-----------------------------------------------------------------------------------------------------------
IjsBaseDTO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            common properties for child entities
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.entity;

public class IjsBaseDTO {

    public static final String RECORD_STATUS_ACTIVE = "A";

    protected String recordStatus;
    protected String recordAddUser;
    protected String recordChangeUser;
    public IjsBaseDTO() {
    }


    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordAddUser(String recordAddUser) {
        this.recordAddUser = recordAddUser;
    }

    public String getRecordAddUser() {
        return recordAddUser;
    }

    public void setRecordChangeUser(String recordChangeUser) {
        this.recordChangeUser = recordChangeUser;
    }

    public String getRecordChangeUser() {
        return recordChangeUser;
    }
}
