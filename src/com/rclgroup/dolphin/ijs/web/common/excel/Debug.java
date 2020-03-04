/*-----------------------------------------------------------------------------------------------------------
Debug.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            Helper utility for Excel upload
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.common.excel;

/*
 *	The instance variable that will be set to true in case the output ids required to
 *	be displayed on the console.
 */
public class Debug
{
        /* For Individual Screen Logging */
	private static final boolean DEBUG = true;
        /* For FRAMEWORK Logging */
	private static final boolean DEBUG_FRAMEWORK = true;
	
	/*
	 * Log method for individual screens/Items
	 *
	 * @param <code>String</code> the string that is to be displayed at the console
	 */
	public static void log(Object lstrLogMessage)
	{
            if(isDebug()) {
                    System.out.println(lstrLogMessage);//display message at the console..
            }
	}
        
        public static boolean isDebug(){
            return DEBUG;
        }
        
        /*
         * Log method for framework files 
         *
         * @param <code>String</code>
         */
        public static void logFramework(Object lstrLogMessage)
        {
            if(isDebugFramework()) {
                    System.out.println(lstrLogMessage);
            }
        }
          /*
           * Log method for framework files 
           *
           * @param <code>Exception</code>
           */
         public static void logFramework(Exception ex)
         {
             if(isDebugFramework()) {
                 ex.printStackTrace();
             }
         }
        
        public static boolean isDebugFramework(){
            return DEBUG_FRAMEWORK;
        }
}

/* Modification History
 *
 * 2005-07: Modified for Tops
 * 2009-11: Moified for EZL
 *
 */
