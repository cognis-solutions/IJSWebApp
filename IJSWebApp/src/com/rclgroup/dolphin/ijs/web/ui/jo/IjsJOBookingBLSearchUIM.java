 /*-----------------------------------------------------------------------------------------------------------
 IjsJOBookingBLSearchAction.java
 -------------------------------------------------------------------------------------------------------------
 Copyright RCL Public Co., Ltd. 2007
 -------------------------------------------------------------------------------------------------------------
 Author NIIT 02/11/17
 - Change Log ------------------------------------------------------------------------------------------------
 ## DD/MM/YY -User-     -TaskRef-      -Short Description
 01 02/11/17  NIIT       IJS            Booking/BL Search functionality 
 -----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.ui.jo;

import com.rclgroup.dolphin.ijs.web.common.IjsJOSearchResults;
import com.rclgroup.dolphin.ijs.web.ui.IjsBaseActionForm;
import com.rclgroup.dolphin.ijs.web.vo.jo.IjsJOBookingBLSearchVO;

import java.util.List;

public class IjsJOBookingBLSearchUIM extends IjsBaseActionForm  {
    private IjsJOBookingBLSearchVO ijsJOBookingBLSearchVO;
    private IjsJOSearchResults<?> searchResults;
    
    
    
    public IjsJOBookingBLSearchUIM() {
    }

    public void setIjsJOBookingBLSearchVO(IjsJOBookingBLSearchVO ijsJOBookingBLSearchVO) {
        this.ijsJOBookingBLSearchVO = ijsJOBookingBLSearchVO;
    }

    public IjsJOBookingBLSearchVO getIjsJOBookingBLSearchVO() {
        return ijsJOBookingBLSearchVO;
    }


    public void setSearchResults(IjsJOSearchResults<?> searchResults) {
        this.searchResults = searchResults;
    }

    public IjsJOSearchResults<?> getSearchResults() {
        return searchResults;
    }
}
