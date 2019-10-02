package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;
/**
 * 
 * @author Ashish.1.Rawat
 *
 * @param <T>
 */
public class IjsBaseMessageVO<T> {

    private String errorCode;
    private List<T> messageList;

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
    public void setMessageList(List<T> messageList) {
        this.messageList = messageList;
    }

    public List<T> getMessageList() {
        return messageList;
    }
}
