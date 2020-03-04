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
package com.rclgroup.dolphin.ijs.web.dao.jo;

import com.rclgroup.dolphin.ijs.web.vo.jo.IjsJOBookingBLSearchVO;

public interface IjsJOBookingBLSearchDao {

    public void searchJOBookingBL(IjsJOBookingBLSearchVO ijsJOBookingBLSearchVO, String userInfo);
}
