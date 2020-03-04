 /*-----------------------------------------------------------------------------------------------------------
IJSException.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            common IJS exceptions
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.exception;

public class IJSException extends Exception{
    //##01 BEGIN
    public IJSException() {
        super();
    }

   
    public IJSException(String message) {
        super(message);
    }

  
    public IJSException(String message, Throwable cause) {
        super(message, cause);
    }

  
    public IJSException(Throwable cause) {
        super(cause);
    }
    //##01 END
}
