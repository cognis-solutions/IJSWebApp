/*-----------------------------------------------------------------------------------------------------------  
RutFormatting.java
------------------------------------------------------------------------------------------------------------- 
Copyright RCL Public Co., Ltd. 2007 
-------------------------------------------------------------------------------------------------------------
Author Piyapong Ieumwananonthachai 21/04/08
- Change Log ------------------------------------------------------------------------------------------------  
## DD/MM/YY -User-     -TaskRef-      -Short Description  
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.util;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.Locale;


public class RutFormatting {
    public RutFormatting() {
    }
    
    public static String getDecimalFormat(Object value, String pattern){
        DecimalFormat df = null;
        try {
            df = (DecimalFormat)NumberFormat.getInstance(Locale.ENGLISH);
        }catch (ClassCastException e) {
            System.err.println(e);
        }
        df.applyPattern(pattern);
        return df.format(value);
    }
    
    
    public static String getDoubleToDecimalFormat(Double value,String pattern){
        if(value == null){
            value = 0.0;
        }
        if(pattern == null){
            pattern = "###,###,###,##0.00";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        
        return df.format(value);
    }
    
    public static String getStringToDecimalFormat(String value, String pattern) {
        if(pattern == null){
            pattern = "###,###,###,##0.00";
        }
        DecimalFormat df = null;
        value = value == null ? "0" : value.trim();
        value = value.equals("") ? "0" : value.trim();
        value = value.replaceAll(",", "");
        BigDecimal biValue = new BigDecimal(value);
        try {
            df = (DecimalFormat)NumberFormat.getInstance(Locale.ENGLISH);
        } catch (ClassCastException e) {
            System.err.println(e);
        }
        df.applyPattern(pattern);
        return df.format(biValue);
    }
    public static String getStringToDecimal(String value, String pattern) {
        if(pattern == null){
            pattern = "###,###,###,##0.00";
        }
        DecimalFormat df = null;
        value = value == null ? "0" : value.trim();
        value = value.equals("") ? "0" : value.trim();
        value = value.replaceAll(",", "");
        BigDecimal biValue = new BigDecimal(value);
        try {
            return biValue.toString();
        } catch (ClassCastException e) {
            System.err.println(e);
        }
        return "0";
    }
  public static void main(String args[]){
	  System.out.println("string to decimal:"+getStringToDecimalFormat("1,440.4",null));
	  System.out.println("double to Decimal:"+getDoubleToDecimalFormat(new Double("1441.11"),null));
	  System.out.println("double to Decimal:"+getStringToDecimal("1,440.4",null));
  }
}
