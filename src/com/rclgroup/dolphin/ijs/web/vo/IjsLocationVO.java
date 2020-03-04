package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;
import com.google.gson.Gson;

public class IjsLocationVO implements Cloneable{
    public IjsLocationVO() {
    }
        private String fromLocType;
        private String fromLocation;
        private String fromTerminal;
        private String toLocType;
        private String toLocation;
        private String toTerminal;
        private int priority;
        private String currency;
        private String paymentFsc;
        private String bargeValue;
        
        
        

    public String getBargeValue() {
			return bargeValue;
		}

		public void setBargeValue(String bargeValue) {
			this.bargeValue = bargeValue;
		}

	public void setFromLocType(String fromLocType) {
        this.fromLocType = fromLocType;
    }

    public String getFromLocType() {
        return fromLocType;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromTerminal(String fromTerminal) {
        this.fromTerminal = fromTerminal;
    }

    public String getFromTerminal() {
        return fromTerminal;
    }

    public void setToLocType(String toLocType) {
        this.toLocType = toLocType;
    }

    public String getToLocType() {
        return toLocType;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToTerminal(String toTerminal) {
        this.toTerminal = toTerminal;
    }

    public String getToTerminal() {
        return toTerminal;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }


    public void setPaymentFsc(String paymentFsc) {
        this.paymentFsc = paymentFsc;
    }

    public String getPaymentFsc() {
        return paymentFsc;
    }
    
    
    public boolean compareVO(IjsLocationVO ijsLocation) {
        System.out.println("Comparing");
        boolean returnFlag = false;
        
        try{
        
        IjsLocationVO ijsLocationVO = (IjsLocationVO)ijsLocation.clone();
        
        ijsLocationVO.setCurrency(null);
        ijsLocationVO.setPaymentFsc(null);
        ijsLocationVO.setPriority(null);
        
        IjsLocationVO ijsLocationCurrVO = (IjsLocationVO)this.clone();
        ijsLocationCurrVO.setCurrency(null);
        ijsLocationCurrVO.setPaymentFsc(null);
        ijsLocationCurrVO.setPriority(null);
        
        String objectString1 = new Gson().toJson(ijsLocationCurrVO);
        String objectString2 = new Gson().toJson(ijsLocationVO);
        
        System.out.println("objectString1    :"+objectString1);
        System.out.println("objectString2    :"+objectString2);

        if(objectString1.equals(objectString2)){
            returnFlag = true;
        }
        else{
            returnFlag = false;
        }
        
        }
        catch(CloneNotSupportedException c){
        return false;
        }
 return returnFlag;
    }
    
 @Override   
 protected Object clone() throws CloneNotSupportedException  
 {
     return super.clone(); 
 }

  
}
