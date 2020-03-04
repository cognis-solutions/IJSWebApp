package com.rclgroup.dolphin.ijs.web.ui;

import com.rclgroup.dolphin.ijs.web.vo.IJSCompareVO;

public class IJSResultTableContainerUpdateUIM extends IjsBaseActionForm{
	
	private String containerJson;
	private String container;
	private String containerWeight;
	private String oldContainerWeight;
	
	private int count;
	
	private IJSCompareVO ijsLookupParam;

	
	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public IJSCompareVO getIjsLookupParam() {
		return ijsLookupParam;
	}

	public void setIjsLookupParam(IJSCompareVO ijsLookupParam) {
		this.ijsLookupParam = ijsLookupParam;
	}

	public String getOldContainerWeight() {
		return oldContainerWeight;
	}

	public void setOldContainerWeight(String oldContainerWeight) {
		this.oldContainerWeight = oldContainerWeight;
	}

	public String getContainerJson() {
		return containerJson;
	}

	public void setContainerJson(String containerJson) {
		this.containerJson = containerJson;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getContainerWeight() {
		return containerWeight;
	}

	public void setContainerWeight(String containerWeight) {
		this.containerWeight = containerWeight;
	}
	

}
