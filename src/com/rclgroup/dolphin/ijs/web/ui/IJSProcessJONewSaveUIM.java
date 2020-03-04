
package com.rclgroup.dolphin.ijs.web.ui;

import java.util.List;

import com.rclgroup.dolphin.ijs.web.common.IjsJOLookupResult;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessNewSaveDTO;
import com.rclgroup.dolphin.ijs.web.vo.IjsEQDetailVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJORoutingLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOSummaryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessNewSaveVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRoutingListParamVO;

public class IJSProcessJONewSaveUIM extends IjsBaseActionForm {

	private IjsProcessNewSaveVO ijsLookupParam;
	private IjsJOLookupResult<?> lookupSearchResult;
	private IjsRoutingListParamVO routingList;
	private List<IjsJORoutingLookUpVO> list;
	private List<String> eqpList;
    private List<IjsEQDetailVO> eqDetails;
   // private List<IjsJOSummaryParamVO> lstJOSummaryParam;

	
	public IJSProcessJONewSaveUIM()
	{}


	
	




	public List<IjsJORoutingLookUpVO> getList() {
		return list;
	}








	public void setList(List<IjsJORoutingLookUpVO> list) {
		this.list = list;
	}








	public List<String> getEqpList() {
		return eqpList;
	}








	public void setEqpList(List<String> eqpList) {
		this.eqpList = eqpList;
	}








	public List<IjsEQDetailVO> getEqDetails() {
		return eqDetails;
	}




	public void setEqDetails(List<IjsEQDetailVO> eqDetails) {
		this.eqDetails = eqDetails;
	}




	public IjsRoutingListParamVO getRoutingList() {
		return routingList;
	}




	public void setRoutingList(IjsRoutingListParamVO routingList) {
		this.routingList = routingList;
	}



	public IjsProcessNewSaveVO getIjsLookupParam() {
		return ijsLookupParam;
	}

	public void setIjsLookupParam(IjsProcessNewSaveVO ijsLookupParam) {
		this.ijsLookupParam = ijsLookupParam;
	}

	public IjsJOLookupResult<?> getLookupSearchResult() {
		return lookupSearchResult;
	}

	public void setLookupSearchResult(IjsJOLookupResult<?> lookupSearchResult) {
		this.lookupSearchResult = lookupSearchResult;
	}



@Override
public String toString() {
	return "IJSProcessJONewSaveUIM [ijsLookupParam=" + ijsLookupParam + ", lookupSearchResult=" + lookupSearchResult
			+ ", routingList=" + routingList + ", list=" + list + ", eqpList=" + eqpList + ", eqDetails=" + eqDetails
			+ "]";
}


}
