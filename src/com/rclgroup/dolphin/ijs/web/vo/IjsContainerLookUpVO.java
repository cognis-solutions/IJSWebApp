package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContainerLookUpVO {
    private String container;
    private String containerWeight;
    private boolean selectedFlag = true;

    public boolean isSelectedFlag() {
		return selectedFlag;
	}

	public void setSelectedFlag(boolean selectedFlag) {
		this.selectedFlag = selectedFlag;
	}

	public String getContainerWeight() {
		return containerWeight;
	}

	public void setContainerWeight(String containerWeight) {
		this.containerWeight = containerWeight;
	}

	public void setContainer(String container) {
        this.container = container;
    }

    public String getContainer() {
        return container;
    }
}
