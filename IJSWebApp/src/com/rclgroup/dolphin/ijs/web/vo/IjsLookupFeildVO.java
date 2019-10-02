package com.rclgroup.dolphin.ijs.web.vo;

public class IjsLookupFeildVO {
    private String key;
    private String value;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    @Override
    public boolean  equals (Object object) {
        boolean result = false;
        
            IjsLookupFeildVO ijsLookupFeildVO = (IjsLookupFeildVO) object;
            
            if (this.getKey().equals(ijsLookupFeildVO.getKey())) {
            
                result = true;
            }
        return result;
    }
}
