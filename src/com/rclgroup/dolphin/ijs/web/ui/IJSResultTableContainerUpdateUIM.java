package com.rclgroup.dolphin.ijs.web.ui;

public class IJSResultTableContainerUpdateUIM extends IjsBaseActionForm{
	
	private String containerJson;
	private String container;
	private String containerWeight;
	private String oldContainerWeight;

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
