package com.rclgroup.dolphin.ijs.web.ui;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchVO;

import java.util.List;
import java.util.Map;

public class IjsMaintainJOSearchUIM extends IjsBaseActionForm {
    //##01 BEGIN
    private IjsMaintainJOSearchParamVO maintainJoParam;
    private List<IjsMaintainJOSearchVO> contractsList;//For cancel etc
    private List<IjsMaintainJOSearchVO> joSaveList;
    
    
   
    
    private IjsMaintainJOSearchVO joSaveListAll;
    
    //private IjsSearchResult<?> searchResult;
    private IjsMaintainJOSearchVO maintainSearch;
    private List<String> maintainSearchList; //##02
    private Map<String, String> maintainSearchResult; //##02
    
    String [] filesTobeDownload=new String[3];
    private int totalRecords;
    private List<String> joDeleteLumpsumList;
    private int downloadLimit;
    private int downloadContainer;
    
    public IjsMaintainJOSearchUIM() {
    }
  
    public void setMaintainJoParam(IjsMaintainJOSearchParamVO maintainJoParam) {
        this.maintainJoParam = maintainJoParam;
    }

    public IjsMaintainJOSearchParamVO getMaintainJoParam() {
        return maintainJoParam;
    }

    public void setMaintainSearch(IjsMaintainJOSearchVO maintainSearch) {
        this.maintainSearch = maintainSearch;
    }

    public IjsMaintainJOSearchVO getMaintainSearch() {
        return maintainSearch;
    }

    public void setMaintainSearchList(List<String> maintainSearchList) {
        this.maintainSearchList = maintainSearchList;
    }

    public List<String> getMaintainSearchList() {
        return maintainSearchList;
    }

    public void setMaintainSearchResult(Map<String, String> maintainSearchResult) {
        this.maintainSearchResult = maintainSearchResult;
    }

    public Map<String, String> getMaintainSearchResult() {
        return maintainSearchResult;
    }

    public void setFilesTobeDownload(String[] filesTobeDownload) {
        this.filesTobeDownload = filesTobeDownload;
    }

    public String[] getFilesTobeDownload() {
        return filesTobeDownload;
    }


    public void setContractsList(List<IjsMaintainJOSearchVO> contractsList) {
        this.contractsList = contractsList;
    }

    public List<IjsMaintainJOSearchVO> getContractsList() {
        return contractsList;
    }

    public void setJoSaveList(List<IjsMaintainJOSearchVO> joSaveList) {
        this.joSaveList = joSaveList;
    }

    public List<IjsMaintainJOSearchVO> getJoSaveList() {
        return joSaveList;
    }

    

    public void setJoSaveListAll(IjsMaintainJOSearchVO joSaveListAll) {
        this.joSaveListAll = joSaveListAll;
    }

    public IjsMaintainJOSearchVO getJoSaveListAll() {
        return joSaveListAll;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

	public List<String> getJoDeleteLumpsumList() {
		return joDeleteLumpsumList;
	}

	public void setJoDeleteLumpsumList(
			List<String> joDeleteLumpsumList) {
		this.joDeleteLumpsumList = joDeleteLumpsumList;
	}

	public int getDownloadLimit() {
		return downloadLimit;
	}

	public void setDownloadLimit(int downloadLimit) {
		this.downloadLimit = downloadLimit;
	}

	public int getDownloadContainer() {
		return downloadContainer;
	}

	public void setDownloadContainer(int downloadContainer) {
		this.downloadContainer = downloadContainer;
	}

	    
}
