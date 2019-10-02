package com.rclgroup.dolphin.ijs.web.common;

import java.util.List;

public class IjsJOLookupResult<T> {
    //## 01 BEGIN
    List<T> result;


    public void setResult(List<?> list) {
        this.result = (List<T>) list;
    }

    public List<T> getResult() {
        return result;
    }
    //## 01 END
}
