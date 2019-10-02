 /*-----------------------------------------------------------------------------------------------------------

 BaseVO.java
 -------------------------------------------------------------------------------------------------------------
 Copyright RCL Public Co., Ltd. 2007
 -------------------------------------------------------------------------------------------------------------
 Author NIIT 20/10/17
 - Change Log ------------------------------------------------------------------------------------------------
 ## DD/MM/YY -User-     -TaskRef-      -Short Description
 01 20/10/17  NIIT       IJS            Helper utility for Excel upload
 -----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.common.excel;

/**
 * @author NIIT
 *
 * This is the base VO object. 
 * All VO objects will implement this class.
 */
public class BaseVO implements java.io.Serializable {

    /* Seq No or Searial No */
    protected int srlNo = 0;
    /* Record Status - A:Active S:Suspended */
    protected String recStatus = null;
    /* Document Status - 1/2/3/... (ex. 1:Entry, 2:Final, etc)*/
    protected String docStatus = null;
    /* select checkbox for delete or print */
    protected String selected = null;
    /* select checkbox disable or enable for delete or print */
    protected String selectedState = null;
    /* ROW ID : PK field of the record (Ex. PK_CONTRACT_ID or PK_SERVICE_ID)*/
    protected String rowId  = null;
    /* Last Update Date Time in YYYYMMDDHH24MISS format */
    protected String updTime = null;
    /* Record Update status : UPD/DEL/ADD */
    protected String status = null;
    
    protected String frmFwFrcCd  = null;
    protected String frmFwFmtMsk = null;
            
    /**
     * Constructor Profile.
     */
    public BaseVO() {
    }


    /**
     * Returns the rowId.
     * @return String
     */
    public String getRowId() {
        return rowId;
    }

    /**
     * Sets the rowId.
     * @param astrRowId
     */
    public void setRowId(String astrRowId) {
        this.rowId = astrRowId;
    }
    
    /**
     * Returns the selected.
     * @return String
     */
    public String getSelected() {
        return selected;
    }

    /**
     * Sets the selected.
     * @param selected The selected to set
     */
    public void setSelected(String astrSelected) {
        this.selected = astrSelected;
    }
                
    /**
     * Sets the updTime.
     * @param astrUpdTime
     */
    public void setUpdTime(String astrUpdTime) {
        this.updTime = astrUpdTime;
    }

    /**
     * Returns the updTime_.
     * @return String
     */
    public String getUpdTime() {
        return updTime;
    }

    /**
     * Sets the status.
     * @param status
     */
    public void setStatus(String astrStatus) {
        this.status = astrStatus;
    }

    /**
     * Returns the status.
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
    * @return Returns the frmFwFrcCd.
    */
    public String getFrmFwFrcCd() {
        return frmFwFrcCd;
    }

    /**
    * @param frmFwFrcCd The frmFwFrcCd to set.
    */
    public void setFrmFwFrcCd(String frmFwFrcCd) {
        this.frmFwFrcCd = frmFwFrcCd;
    }

    /**
    * @return Returns the frmFwFrcCd.
    */
    public String getFrmFwFmtMsk() {
        return frmFwFmtMsk;
    }

    /**
    * @param frmFwFrcCd The frmFwFrcCd to set.
    */
    public void setFrmFwFmtMsk(String frmFwFmtMsk) {
        this.frmFwFmtMsk = frmFwFmtMsk;
    }

    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    public String getRecStatus() {
        return recStatus;
    }

    public String getRecStatusDesc() {
        if(recStatus == null){
            return "";
        } else if(recStatus.equals(GlobalConstants.REC_STATUS_ACTIVE)){
            return "Active";
        } else if(recStatus.equals(GlobalConstants.REC_STATUS_SUSPEND)){
            return "Suspended";
        }
        return "";
    }
    
    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getDocStatus() {
        return docStatus;
    }
    
    public String getDocStatusDesc() {
        if(docStatus == null){
            return "";
        } else if(docStatus.equals(GlobalConstants.DOC_STATUS_ENTRY)){
            return "Entry";
        } else if(docStatus.equals(GlobalConstants.DOC_STATUS_PENDING_APRV)){
            return "Pending for Approval";
        } else if(docStatus.equals(GlobalConstants.DOC_STATUS_APPROVE)){
            return "Approved";
        } else if(docStatus.equals(GlobalConstants.DOC_STATUS_CONFIRM)){
            return "Confirm";
        } else if(docStatus.equals(GlobalConstants.DOC_STATUS_BILLING)){
            return "Billing";
        } else if(docStatus.equals(GlobalConstants.DOC_STATUS_REVIEW)){
            return "Review";
        } else if(docStatus.equals(GlobalConstants.DOC_STATUS_CANCEL)){
            return "Cancel";
        } else if(docStatus.equals(GlobalConstants.DOC_STATUS_PENDING_SEND)){
            return "Pending for Send";
        } else if(docStatus.equals(GlobalConstants.DOC_STATUS_FINAL)){
            return "Final";
        }
        return "";
    }

    public void setSelectedState(String selectedState) {
        this.selectedState = selectedState;
    }

    public String getSelectedState() {
        if(selectedState == null){
            if(recStatus != null && recStatus.equals(GlobalConstants.REC_STATUS_SUSPEND)){
                selectedState = "true";//disable
            } else {
                selectedState = "false";//enable
            }
        }
        return selectedState;
    }

    public void setSrlNo(int srlNo) {
        this.srlNo = srlNo;
    }

    public int getSrlNo() {
        return srlNo;
    }
}
