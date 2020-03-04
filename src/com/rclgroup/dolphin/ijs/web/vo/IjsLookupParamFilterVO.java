 /*-----------------------------------------------------------------------------------------------------------
IjsLookupParamVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            value object to holding request information for IJS lookup's screen
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.vo;

import java.util.List;

public class IjsLookupParamFilterVO {
    private String selectedDropDown1;
    private String selectedDropDown2;
    private String textValue;
    private String selectedDropDown3;


    private void setSelectedDropDown1(String selectedDropDown1) {
        this.selectedDropDown1 = selectedDropDown1;
    }

    public String getSelectedDropDown1() {
        return selectedDropDown1;
    }

    public void setSelectedDropDown2(String selectedDropDown2) {
        this.selectedDropDown2 = selectedDropDown2;
    }

    public String getSelectedDropDown2() {
        return selectedDropDown2;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setSelectedDropDown3(String selectedDropDown3) {
        this.selectedDropDown3 = selectedDropDown3;
    }

    public String getSelectedDropDown3() {
        return selectedDropDown3;
    }
    
    @Override
    public String toString(){
        StringBuilder whereClause=null;
        if(selectedDropDown1!= null){
            whereClause = new StringBuilder(" "+selectedDropDown1);
        }
        
        if(selectedDropDown2!=null){
          if(selectedDropDown2.equals("equals")){
              whereClause = whereClause.append(new StringBuilder(" = ")).append(new StringBuilder("'")).append(new StringBuilder(textValue)).append(new StringBuilder("'"));
          } 
          if(selectedDropDown2.equals("!equals")){
                whereClause = whereClause.append(new StringBuilder(" != ")).append(new StringBuilder("'")).append(new StringBuilder(textValue)).append(new StringBuilder("'"));
            } 
          if(selectedDropDown2.equals("like")){
                whereClause = whereClause.append(new StringBuilder(" LIKE ")).append(new StringBuilder("'%")).append(new StringBuilder(textValue)).append(new StringBuilder("%'"));
            } 
        }
        
        if(selectedDropDown3!=null){
            
            if(selectedDropDown3.endsWith("AND")){
            whereClause = whereClause.append(new StringBuilder(" AND"));
            }
            
            if(selectedDropDown3.endsWith("OR")){
            whereClause = whereClause.append(new StringBuilder(" OR"));
            }
        }
        
        return whereClause.toString();
    }
}
