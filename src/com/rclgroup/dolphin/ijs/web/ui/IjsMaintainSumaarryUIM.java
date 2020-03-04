package com.rclgroup.dolphin.ijs.web.ui;

import java.util.List;

import com.rclgroup.dolphin.ijs.web.common.IjsJOLookupResult;
import com.rclgroup.dolphin.ijs.web.entity.IjsJoSummaryDTO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainjoSummaryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsSumarryVO;

public class IjsMaintainSumaarryUIM extends  IjsBaseActionForm 
{
	private IjsMaintainJOSearchParamVO joSummary;
	private  List<IjsJoSummaryDTO> ijsJoSummarryDTO;
	private  List<IjsSumarryVO> ijsSumarryVO;
	private IjsJOLookupResult<?> lookupSearchResult;
	

	public IjsMaintainJOSearchParamVO getJoSummary() {
		return joSummary;
	}

	public void setJoSummary(IjsMaintainJOSearchParamVO joSummary) {
		this.joSummary = joSummary;
	}

	public List<IjsJoSummaryDTO> getIjsJoSummarryDTO() {
		return ijsJoSummarryDTO;
	}

	public void setIjsJoSummarryDTO(List<IjsJoSummaryDTO> ijsJoSummarryDTO) {
		this.ijsJoSummarryDTO = ijsJoSummarryDTO;
	}

	public List<IjsSumarryVO> getIjsSumarryVO() {
		return ijsSumarryVO;
	}

	public void setIjsSumarryVO(List<IjsSumarryVO> ijsSumarryVO) {
		this.ijsSumarryVO = ijsSumarryVO;
	}

	public IjsJOLookupResult<?> getLookupSearchResult() {
		return lookupSearchResult;
	}

	public void setLookupSearchResult(IjsJOLookupResult<?> lookupSearchResult) {
		this.lookupSearchResult = lookupSearchResult;
	}


	
	
}
