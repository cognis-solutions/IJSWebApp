package com.rclgroup.dolphin.ijs.web.ui;

import com.rclgroup.dolphin.ijs.web.action.IjsJOUploadBase;
import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.vo.IjsBaseMessageVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsEQDetailVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsBkgBLScreenSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContainerUploadVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractUploadVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOChangeVendorVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOSummaryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOBkgBLSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOBkgBLSearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOListParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOSumDtlVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessJOSummarySearchVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRoutingListParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSearchParamVO;

import java.util.List;
import java.util.Map;

public class IjsProcessJOBkgBLSearchUIM extends IjsBaseActionForm implements IjsJOUploadBase{
    //##01 BEGIN
    private IjsProcessJOBkgBLSearchParamVO processJoParam;
    private IjsProcessJOBkgBLSearchVO bookingBL;
    private List<String> bookingBLList; //##02
    private Map<String, String> bookingBLResult; //##02
    private IjsProcessJOSummarySearchVO joSummary;
    private List<IjsJOSummaryParamVO> lstJOSummaryParam; //##02
    private String processJoType = null;
    private String reasonCode = null;
    private List<IjsProcessJOSumDtlVO> joSummaryList;   
    private IjsRoutingListParamVO routingList;
    String [] filesTobeDownload=new String[2];
    private IjsContainerUploadVO uploadVO;
    private String vendorCode = null;
    private List<String> eqList;
    private List<IjsEQDetailVO> eqDetails;
    private List<IjsProcessJOListParamVO> processjoFieldList;
    private List<String> jobOrderLst;
    private String allJobOrders;
    private IjsJOChangeVendorVO saveChangeVendorObj;
  
    private IjsBaseMessageVO<String> msgVO;
    private List<IjsBaseMessageVO<String>> msgVOs;
    private String bk_bl_ad;
    private String bkgblNumber;
    private String routingId;
    private String routingIdOLD;
    private String vendorID;
    private String vendorIDOLD;
    private String transMode;
    private String contractId;
    private int maxEquipLimit;
    private List<String> equipList;
    private List contractsID;
    private String vendorCodeAssociated;
    
    public IjsProcessJOBkgBLSearchUIM() {
    }

    
    
    public String getVendorCodeAssociated() {
		return vendorCodeAssociated;
	}



	public void setVendorCodeAssociated(String vendorCodeAssociated) {
		this.vendorCodeAssociated = vendorCodeAssociated;
	}



	public List getContractsID() {
		return contractsID;
	}



	public void setContractsID(List contractsID) {
		this.contractsID = contractsID;
	}



	public void setBookingBL(IjsProcessJOBkgBLSearchVO bookingBL) {
        this.bookingBL = bookingBL;
    }

    public IjsProcessJOBkgBLSearchVO getBookingBL() {
        return bookingBL;
    }

    public void setBookingBLList(List<String> bookingBLList) {
        this.bookingBLList = bookingBLList;
    }

    public List<String> getBookingBLList() {
        return bookingBLList;
    }

    public void setBookingBLResult(Map<String, String> bookingBLResult) {
        this.bookingBLResult = bookingBLResult;
    }

    public Map<String, String> getBookingBLResult() {
        return bookingBLResult;
    }


    public void setProcessJoParam(IjsProcessJOBkgBLSearchParamVO processJoParam) {
        this.processJoParam = processJoParam;
    }

    public IjsProcessJOBkgBLSearchParamVO getProcessJoParam() {
        return processJoParam;
    }

  
    public List<IjsJOSummaryParamVO> getLstJOSummaryParam() {
		return lstJOSummaryParam;
	}

	public void setLstJOSummaryParam(List<IjsJOSummaryParamVO> lstJOSummaryParam) {
		this.lstJOSummaryParam = lstJOSummaryParam;
	}

	public void setProcessJoType(String processJoType) {
        this.processJoType = processJoType;
    }

    public String getProcessJoType() {
        return processJoType;
    }

    public void setJoSummary(IjsProcessJOSummarySearchVO joSummary) {
        this.joSummary = joSummary;
    }

    public IjsProcessJOSummarySearchVO getJoSummary() {
        return joSummary;
    }

    public void setJoSummaryList(List<IjsProcessJOSumDtlVO> joSummaryList) {
        this.joSummaryList = joSummaryList;
    }

    public List<IjsProcessJOSumDtlVO> getJoSummaryList() {
        return joSummaryList;
    }

    public void setRoutingList(IjsRoutingListParamVO routingList) {
        this.routingList = routingList;
    }

    public IjsRoutingListParamVO getRoutingList() {
        return routingList;
    }
    public void setFilesTobeDownload(String[] filesTobeDownload) {
        this.filesTobeDownload = filesTobeDownload;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setEqList(List<String> eqList) {
        this.eqList = eqList;
    }

    public List<String> getEqList() {
        return eqList;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setProcessjoFieldList(List<IjsProcessJOListParamVO> processjoFieldList) {
        this.processjoFieldList = processjoFieldList;
    }

    public List<IjsProcessJOListParamVO> getProcessjoFieldList() {
        return processjoFieldList;
    }


    public String[] getFilesTobeDownload() {
        return filesTobeDownload;
    }

    public void setUploadVO(IjsContainerUploadVO uploadVO) {
        this.uploadVO = uploadVO;
    }

    public IjsContainerUploadVO getUploadVO() {
        return uploadVO;
    }

    public List<String> getJobOrderLst() {
		return jobOrderLst;
	}

	public void setJobOrderLst(List<String> jobOrderLst) {
		this.jobOrderLst = jobOrderLst;
	}

	public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public String getTransMode() {
        return transMode;
    }

    public void setSaveChangeVendorObj(IjsJOChangeVendorVO saveChangeVendorObj) {
        this.saveChangeVendorObj = saveChangeVendorObj;
    }

    public IjsJOChangeVendorVO getSaveChangeVendorObj() {
        return saveChangeVendorObj;
    }

    public void setBk_bl_ad(String bk_bl_ad) {
        this.bk_bl_ad = bk_bl_ad;
    }

    public String getBk_bl_ad() {
        return bk_bl_ad;
    }

    public void setBkgblNumber(String bkgblNumber) {
        this.bkgblNumber = bkgblNumber;
    }

    public String getBkgblNumber() {
        return bkgblNumber;
    }

    public void setRoutingId(String routingId) {
        this.routingId = routingId;
    }

    public String getRoutingId() {
        return routingId;
    }

    public void setRoutingIdOLD(String routingIdOLD) {
        this.routingIdOLD = routingIdOLD;
    }

    public String getRoutingIdOLD() {
        return routingIdOLD;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorIDOLD(String vendorIDOLD) {
        this.vendorIDOLD = vendorIDOLD;
    }

    public String getVendorIDOLD() {
        return vendorIDOLD;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

	public List<IjsEQDetailVO> getEqDetails() {
		return eqDetails;
	}

	public void setEqDetails(List<IjsEQDetailVO> eqDetails) {
		this.eqDetails = eqDetails;
	}

	public IjsBaseMessageVO<String> getMsgVO() {
		return msgVO;
	}

	public void setMsgVO(IjsBaseMessageVO<String> msgVO) {
		this.msgVO = msgVO;
	}

	public List<IjsBaseMessageVO<String>> getMsgVOs() {
		return msgVOs;
	}

	public void setMsgVOs(List<IjsBaseMessageVO<String>> msgVOs) {
		this.msgVOs = msgVOs;
	}

	public int getMaxEquipLimit() {
		return maxEquipLimit;
	}

	public void setMaxEquipLimit(int maxEquipLimit) {
		this.maxEquipLimit = maxEquipLimit;
	}

	public List<String> getEquipList() {
		return equipList;
	}

	public void setEquipList(List<String> equipList) {
		this.equipList = equipList;
	}

	public String getAllJobOrders() {
		return allJobOrders;
	}

	public void setAllJobOrders(String allJobOrders) {
		this.allJobOrders = allJobOrders;
	}

	
}
