 /*-----------------------------------------------------------------------------------------------------------
IjsLookupParamVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            value object to holding request information for IJS lookup's screen
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsLookupParamVO {
    private String findIn;
    private String findFor;
    private String findForLoc;
    private String findForTerminal;
    private String findForLocType;
    private String findForSaleDateOrJobOrdDate;
    private String findForVendorCode;
    private String sortBy;
    private String orderBy;
    private String wildCard;
    private int rowStart;
    private int rowEnd;
    private int totalCount;
    private int pageNo;
    private boolean requestChanged;
    private int noOfRecPerPage;
    private String contractId;
    private String transMode;
    private String joType;
    private String sameVendorInSearch;
    private String componentType;
   //nikash
    private String bargeValue;
    private String fromLoc;
    private String toLoc;
    private String trans;
    private String portDepo;
    private String termiPoint;
    
    private List<IjsLookupParamFilterVO> findForList;
    private List<IjsLookupFeildVO> findList;
    
    private List<IjsBkGBlLookUpVO> deleteFor;
    private List<String> joLumpsumIds;
    private List raoutingId;
    
    
    
    
    
    public List getRaoutingId() {
		return raoutingId;
	}


	public void setRaoutingId(List raoutingId) {
		this.raoutingId = raoutingId;
	}


	public String getPortDepo() {
		return portDepo;
	}


	public void setPortDepo(String portDepo) {
		this.portDepo = portDepo;
	}


	public String getTermiPoint() {
		return termiPoint;
	}


	public void setTermiPoint(String termiPoint) {
		this.termiPoint = termiPoint;
	}


	public String getFromLoc() {
		return fromLoc;
	}


	public void setFromLoc(String fromLoc) {
		this.fromLoc = fromLoc;
	}


	public String getToLoc() {
		return toLoc;
	}


	public void setToLoc(String toLoc) {
		this.toLoc = toLoc;
	}


	public String getTrans() {
		return trans;
	}


	public void setTrans(String trans) {
		this.trans = trans;
	}


	public IjsLookupParamVO() {
    }


    public String getBargeValue() {
		return bargeValue;
	}


	public void setBargeValue(String bargeValue) {
		this.bargeValue = bargeValue;
	}


	public void setFindIn(String findIn) {
        this.findIn = findIn;
    }

    public String getFindIn() {
        return findIn;
    }

    public void setFindFor(String findFor) {
        this.findFor = findFor;
    }

    public String getFindFor() {
        return findFor;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setWildCard(String wildCard) {
        this.wildCard = wildCard;
    }

    public String getWildCard() {
        return wildCard;
    }

    public void setRowStart(int rowStart) {
        this.rowStart = rowStart;
    }

    public int getRowStart() {
        return rowStart;
    }

    public void setRowEnd(int rowEnd) {
        this.rowEnd = rowEnd;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageNo() {
        return pageNo;
    }
 

    public void setRequestChanged(boolean requestChanged) {
        this.requestChanged = requestChanged;
    }

    public boolean isRequestChanged() {
        return requestChanged;
    }

    public void setFindForList(List<IjsLookupParamFilterVO> findForList) {
        this.findForList = findForList;
    }

    public List<IjsLookupParamFilterVO> getFindForList() {
        return findForList;
    }

    public void setFindList(List<IjsLookupFeildVO> findList) {
        this.findList = findList;
    }

    public List<IjsLookupFeildVO> getFindList() {
        return findList;
    }

    public void setDeleteFor(List<IjsBkGBlLookUpVO> deleteFor) {
        this.deleteFor = deleteFor;
    }

    public List<IjsBkGBlLookUpVO> getDeleteFor() {
        return deleteFor;
    }

    public void setFindForLoc(String findForLoc) {
        this.findForLoc = findForLoc;
    }

    public String getFindForLoc() {
        return findForLoc;
    }

    public void setFindForTerminal(String findForTerminal) {
        this.findForTerminal = findForTerminal;
    }

    public String getFindForTerminal() {
        return findForTerminal;
    }

    public void setFindForLocType(String findForLocType) {
        this.findForLocType = findForLocType;
    }

    public String getFindForLocType() {
        return findForLocType;
    }

    public void setFindForSaleDateOrJobOrdDate(String findForSaleDateOrJobOrdDate) {
        this.findForSaleDateOrJobOrdDate = findForSaleDateOrJobOrdDate;
    }

    public String getFindForSaleDateOrJobOrdDate() {
        return findForSaleDateOrJobOrdDate;
    }

    public void setFindForVendorCode(String findForVendorCode) {
        this.findForVendorCode = findForVendorCode;
    }

    public String getFindForVendorCode() {
        return findForVendorCode;
    }

    public void setNoOfRecPerPage(int noOfRecPerPage) {
        this.noOfRecPerPage = noOfRecPerPage;
    }

    public int getNoOfRecPerPage() {
        return noOfRecPerPage;
    }


	public String getContractId() {
		return contractId;
	}


	public void setContractId(String contractId) {
		this.contractId = contractId;
	}


	public String getTransMode() {
		return transMode;
	}


	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}


	public String getJoType() {
		return joType;
	}


	public void setJoType(String joType) {
		this.joType = joType;
	}


	public String getSameVendorInSearch() {
		return sameVendorInSearch;
	}


	public void setSameVendorInSearch(String sameVendorInSearch) {
		this.sameVendorInSearch = sameVendorInSearch;
	}


	public String getComponentType() {
		return componentType;
	}


	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}


	public List<String> getJoLumpsumIds() {
		return joLumpsumIds;
	}


	public void setJoLumpsumIds(List<String> joLumpsumIds) {
		this.joLumpsumIds = joLumpsumIds;
	}


	@Override
	public String toString() {
		return "IjsLookupParamVO [findIn=" + findIn + ", findFor=" + findFor + ", findForLoc=" + findForLoc
				+ ", findForTerminal=" + findForTerminal + ", findForLocType=" + findForLocType
				+ ", findForSaleDateOrJobOrdDate=" + findForSaleDateOrJobOrdDate + ", findForVendorCode="
				+ findForVendorCode + ", sortBy=" + sortBy + ", orderBy=" + orderBy + ", wildCard=" + wildCard
				+ ", rowStart=" + rowStart + ", rowEnd=" + rowEnd + ", totalCount=" + totalCount + ", pageNo=" + pageNo
				+ ", requestChanged=" + requestChanged + ", noOfRecPerPage=" + noOfRecPerPage + ", contractId="
				+ contractId + ", transMode=" + transMode + ", joType=" + joType + ", sameVendorInSearch="
				+ sameVendorInSearch + ", componentType=" + componentType + ", bargeValue=" + bargeValue + ", fromLoc="
				+ fromLoc + ", toLoc=" + toLoc + ", trans=" + trans + ", findForList=" + findForList + ", findList="
				+ findList + ", deleteFor=" + deleteFor + ", joLumpsumIds=" + joLumpsumIds + "]";
	}
	
    
}
