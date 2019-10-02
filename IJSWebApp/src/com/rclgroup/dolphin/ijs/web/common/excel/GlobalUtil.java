 /*-----------------------------------------------------------------------------------------------------------
 GlobalUtil.java
 -------------------------------------------------------------------------------------------------------------
 Copyright RCL Public Co., Ltd. 2007
 -------------------------------------------------------------------------------------------------------------
 Author NIIT 24/08/17
 - Change Log ------------------------------------------------------------------------------------------------
 ## DD/MM/YY -User-     -TaskRef-      -Short Description
 01 24/08/17  NIIT       IJS            GlobalUtil for Excel upload
 -----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.common.excel;


import java.io.FileInputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * This is a utility class which will provide common operations like checking
 * for valid date, time, telephone number, etc.
 * 
 * All the methods in this class are static.
 * 
 * @version 20010501
 * @author NIIT
 */

public class GlobalUtil {
	/* ext to create key */
	private final static String ADD_EXT = "_ADD";

	private final static String DEL_EXT = "_DEL";

	private final static String UPD_EXT = "_UPD";

        private final static String BLANK   = "";
    
        private final static char HOLIDAY_REP='0';

	/* emailCheck return value */
	public final static int VALID = 0;

	public final static int SYNTAX_ERR = 1;

	public final static int INVALID_CHAR = 2;

	/* Email check error codes */
	public final static int NO_ATMARK = 3;

	public final static int EXCESS_ATMARK = 4;

	/* hankaku characters */
	final static char HTL = '~'; // tilde

	final static char HNSTART = '0';

	final static char HNEND = '9';

	final static char HAM = '@'; // atmark

	final static char HPR = '.'; // period

	final static char HEM = '!'; // exclamation mark
	
        final static NumberFormat NUMBER_FORMATER = new DecimalFormat("#########.##");
	

	/**
	 * Removes duplicate values from an array and returns a new array having the
	 * unique values e.g astrArr - {"niit","niit","admin","niit","project"} <br>
	 * result string - {"niit","admin","project"} <br>
	 * 
	 * 
	 * @param astrArr
	 *            String[] String array
	 * @return String[]
	 */
	public static String[] removeDuplicates(String[] astrArr) {
		String strPrevVal;
		String[] strRetVal;
		Vector vtRet = new Vector();
		int nArrLen = astrArr.length;

		Arrays.sort(astrArr);

		strPrevVal = astrArr[0];
		vtRet.addElement(astrArr[0]);

		for (int i = 1; i < nArrLen; i++) {
			if (strPrevVal.equals(astrArr[i]))
				continue;
			else
				vtRet.addElement(astrArr[i]);
			strPrevVal = astrArr[i];
		}

		strRetVal = new String[vtRet.size()];
		strRetVal = (String[]) vtRet.toArray(strRetVal);
		return strRetVal;
	}

	/**
	 * Filters null and empty values (i.e., "") from a String[]. A value of "
	 * "[space(s)] will not be filtered. <br>
	 * e.g <br>
	 * astrarrValue - {"niit","","oem","","admin","","project"} <br>
	 * result array - {"niit","oem","admin","project"} <br>
	 * This function is very useful to remove empty values from an array. This
	 * frees memory occupied otherwise unnecesarily
	 * 
	 * @param astrArrValues
	 *            String[] String array. If this value is null then the method
	 *            will return null.
	 * @return String[]
	 */
	public static String[] filterEmptyValues(String[] astrarrValues) {
		String[] strarrRetVal = null;
		if (astrarrValues == null)
			return null; //Check if input is null
		int nArrLen = astrarrValues.length; //Length of the array:

		if (nArrLen > 0) {
			Vector vtRet = new Vector();
			//For every element in the array.Ignore those values which are null
			// or empty
			for (int i = 0; i < nArrLen; i++) {
				if (astrarrValues[i] == null || astrarrValues[i].equals("")) {
					continue;
				} else {
					vtRet.addElement(astrarrValues[i]);
					//Add non-null values in the vector
				}
			}
			strarrRetVal = new String[vtRet.size()];
			//Convert vector to array..
			strarrRetVal = (String[]) vtRet.toArray(strarrRetVal);
		} else {
			return null; //If there is nothing in the array..
		}

		return strarrRetVal;
	}

	/**
	 * This method is used to determine whether a String is <i>null </i> or
	 * <i>blank </i>
	 * 
	 * @param <code>String</code> astrString
	 * @return <code>Boolean</code> true if empty, false otherwise
	 */
	public static boolean isEmpty(String astrString) {

		if (astrString == null || astrString.trim().length() == 0)
			return true;

		return false;
	}

	/**
	 * Wraps a long string by appending a Break Tag, "\n" or any string
	 * specified in astrAppend input argument. The Break Tag string will cause
	 * the string to wrap around in html pages. <BR>
	 * <br>
	 * e.g. <br>
	 * astrVal - <i>"niit admin oem admin project admin" </i> <br>
	 * anLenWrap - <i>2 </i> <br>
	 * astrAppend - <i>"\n" </i> <br>
	 * result string - <i>"niit admin <br>
	 * oem admin <br>
	 * project admin" </i> <br>
	 * 
	 * @param <code>String</code> astrVal String containing tokens. If astrVal is
	 *            specified as null or "", the same value will be ret
	 * @param</code>
	 *            int</code> anLenWrap Word Wrap Length
	 * @param <code>
	 *            String</code> astrAppend String to append
	 * 
	 * @return String
	 */
	public static String wrapString(String astrVal, int anLenWrap,
			String astrAppend) {
		//Default values
		if (astrVal == null || astrVal.length() == 0) {
			return astrVal;
		}
		astrAppend = (astrAppend != null && astrAppend.length() > 0 ? astrAppend
				: "<BR>");
		astrAppend = astrAppend.trim();
		//***************************

		String strDelimiter = " ";
		StringTokenizer sToken = new StringTokenizer(astrVal, strDelimiter);
		int nLenArr = sToken.countTokens();
		int nLenTemp = 0;
		int nLenStr = 0;
		StringBuffer strbufFinal = new StringBuffer();
		String strTemp = "";
		for (int i = 0; i < nLenArr; i++) {
			strTemp = sToken.nextToken();
			nLenTemp = strTemp.length();
			//If length reached append the terminator
			if ((anLenWrap <= nLenStr + nLenTemp) && (i > 0)) {
				strbufFinal = strbufFinal.append(astrAppend);
				nLenStr = 0;
			}
			strbufFinal = strbufFinal.append(strTemp);
			strbufFinal = strbufFinal.append(strDelimiter);
			nLenStr = nLenStr + nLenTemp;
		}
		return strbufFinal.toString().trim();

	}

	/**
	 * Convenience method to call wrapString() with one argument instead of
	 * three. <br>
	 * Internally it calls wrapString( String astrVal, int anLenWrap, String
	 * astrAppend ) method Here anLenWrap - 38 and astrAppend is a Break Tag by
	 * default
	 *  
	 */
	public static String wrapString(String astrVal) {
		return wrapString(astrVal, 38, "<BR>");
	}

	/**
	 * Convenience method to call wrapString() with two arguments instead of
	 * three. Internally it calls wrapString( String astrVal, int anLenWrap,
	 * String astrAppend ) method Here astrAppend is a Break Tag by default
	 */
	public static String wrapString(String astrVal, int anLenWrap) {
		return wrapString(astrVal, anLenWrap, "<BR>");
	}

	/**
	 * Method to encode SQL keywords. Here from a given string any SQL keyword
	 * encountered is picked up and appended to the string containing all these
	 * keywords <br>
	 * e.g. <br>
	 * astr - "niit AND oem NEAR zero defect AND in SYN" <br>
	 * result string - "{AND} {NEAR} {AND} {SYN}" <br>
	 * 
	 * @param <code>String</code> astr Original string
	 * @return <code>String</code> Result string
	 */
	public static String keyWordsSQLEncode(String astr) {
		StringBuffer newString = new StringBuffer();
		if (astr != null && !astr.equals("")) {
			StringTokenizer stS = new StringTokenizer(astr);
			String strTemp;
			while (stS.hasMoreTokens()) {
				strTemp = stS.nextToken();
				if (strTemp.equalsIgnoreCase("ABOUT")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("ACCUM")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("AND")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("BT")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("BTG")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("BTI")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("BTP")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("MINUS")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("NEAR")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("NOT")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("NT")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("NTG")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("NTI")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("NTP")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("OR")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("PT")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("RT")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("SQE")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("SYN")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("TR")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("TRSYN")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("TT")) {
					newString.append("{" + strTemp + "} ");
				} else if (strTemp.equalsIgnoreCase("WITHIN")) {
					newString.append("{" + strTemp + "} ");
				} else
					newString.append(strTemp + " ");
			}
			if (!astr.endsWith(" ")) {
				newString.deleteCharAt(newString.length() - 1);
			}
		}
		return newString.toString();
	}

	/**
	 * Gives the starting row number if the startr page number and the rows per
	 * page is given.
	 * 
	 * @param <code>String</code> strPageNumber
	 * @param <code>String</code> strRowsPerPage
	 * @return String
	 */
	public static String getStartRow(String strPageNumber, String strRowsPerPage) {
		int iPageNumber = (new Integer(strPageNumber)).intValue();
		int iRowsPerPage = (new Integer(strRowsPerPage)).intValue();
		int iStartRow = (iPageNumber - 1) * iRowsPerPage + 1;
		Integer objStartRow = new Integer(iStartRow);
		return objStartRow.toString();
	}

	/**
	 * Gives the end row number if the startr page number and the rows per page
	 * is given.
	 * 
	 * @param <code>String</code> strPageNumber
	 * @param <code>String</code> strRowsPerPage
	 * @return String
	 */
	public static String getEndRow(String strPageNumber, String strRowsPerPage) {
		int iPageNumber = (new Integer(strPageNumber)).intValue();
		int iRowsPerPage = (new Integer(strRowsPerPage)).intValue();
		int iEndRow = iPageNumber * iRowsPerPage;
		Integer objEndRow = new Integer(iEndRow);
		return objEndRow.toString();
	}

	/**
	 * This method is used to validate the format of Date as well as date
	 * itself.
	 * 
	 * @method isValid(String,int)
	 * @param <code>String</code> astrDate The Date to be validated.
	 * @param <code>int</code> anFormat The code for the format against which
	 *            date needs to be validated 1 for 'DD/MM/YYYY', 2 for
	 *            'MM/DD/YYYY', 3 for 'DD/MM/YYYY HH:MM:SS' and 4 for
	 *            'MM/DD/YYYY HH:MM:SS'
	 * @return <code>boolean</code> Returns true if valid date against the
	 *         format specified else retuns false.
	 */

	public static boolean isValid(String astrDate, int anFormat) {
		SimpleDateFormat obFormatter;
		java.util.Date dtTempDate;
		try {
			String strDateFormat = getDateFormat(anFormat);
			if (strDateFormat.length() != astrDate.length())
				return false;
			Integer.parseInt(astrDate.substring(strDateFormat.indexOf("y"),
					strDateFormat.lastIndexOf("y") + 1));
			obFormatter = new SimpleDateFormat(strDateFormat);
			obFormatter.setLenient(false);
			dtTempDate = obFormatter.parse(astrDate);
			return true;
		} catch (java.text.ParseException e) {
			return false;
		} catch (java.lang.NumberFormatException e) {
			return false;
		} finally {
			obFormatter = null;
			dtTempDate = null;
		}
	} //end of isValid

	/**
	 * This method is used to validate the format of date as well as date
	 * itself.
	 * 
	 * @method isValid(String,String)
	 * @param <code>String</code> astrDate The Date to be validated.
	 * @param <code>String</code> astrFormat The code for the format against
	 *            which date needs to be validated. Valid values are
	 *            'DD/MM/YYYY' or 'MM/DD/YYYY'
	 * @return <code>boolean</code> Returns true if valid date against the
	 *         format specified else returns false.
	 */

	public static boolean isValid(String astrDate, String astrFormat) {
		int nFormat = 0;

		try {
			if (astrFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				nFormat = 1;
			} else if (astrFormat.trim().equalsIgnoreCase(
					"MM" + GlobalConstants.DATE_SEPARATOR + "DD" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				nFormat = 2;
			} else if (astrFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY HH:MM:SS")) {
				nFormat = 3;
			} else if (astrFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY HH:MM")) {
				nFormat = 5;
			}
			return isValid(astrDate, nFormat);
		} catch (Exception ex) {
			return false;
		}
	} //end of isValid

	/**
	 * This method validates and converts the Date of type <b>java.util.Date
	 * </b>, <b>java.sql.Date </b> or <b>java.sql.TimeStamp </b> into specified
	 * String Date format.
	 * 
	 * @method changeDateFormat(java.util.Date,String,boolean)
	 * @param <code>java.util.Date</code> adtDate The Date to be converted.
	 * @param <code>String</code> astrRequiredFormat The code for the format
	 *            according to which date needs to be formatted valid values are
	 *            'DD/MM/YYYY' and 'MM/DD/YYYY'
	 * @param <code>boolean</code> abTimeReq This flag is specifies whether time
	 *            is also required along with date or not. For time to be
	 *            suffixed along with date set it to true else false
	 * @return <code>String</code> Returns null if some exception occurs or
	 *         invalid date else returns the formatted date.
	 */

	public static String changeDateFormat(java.util.Date adtDate,
			String astrRequiredFormat, boolean abTimeReq) {
		String strDate = null;
		//java.util.Date adtDate = null;
		int nday = 0;
		int nmonth = 0;
		int nyear = 0;
		int nhrs = 0;
		int nmin = 0;
		int nsec = 0;

		try {
			Calendar clnCalendar = new GregorianCalendar();
			clnCalendar.setTime(adtDate);

			nday = clnCalendar.get(Calendar.DAY_OF_MONTH);
			nmonth = clnCalendar.get(Calendar.MONTH) + 1;
			nyear = clnCalendar.get(Calendar.YEAR);
			nhrs = clnCalendar.get(Calendar.HOUR);
			nmin = clnCalendar.get(Calendar.MINUTE);
			nsec = clnCalendar.get(Calendar.SECOND);

			strDate = ((nday <= 9) ? ("0" + nday) : String.valueOf(nday));
			strDate += GlobalConstants.DATE_SEPARATOR;
			strDate += ((nmonth <= 9) ? ("0" + nmonth) : String.valueOf(nmonth));
			strDate += GlobalConstants.DATE_SEPARATOR;
			if (nyear > 999) {
				strDate += nyear;
			}
			if ((nyear <= 999) && (nyear > 99)) {
				strDate += ("0" + nyear);
			} else if ((nyear <= 99) && (nyear > 9)) {
				strDate += ("00" + nyear);
			} else if (nyear <= 9) {
				strDate += ("000" + nyear);
			}

			if (abTimeReq) {
				strDate += " ";
				strDate += ((nhrs <= 9) ? ("0" + nhrs) : String.valueOf(nhrs))
						+ ":";
				strDate += ((nmin <= 9) ? ("0" + nmin) : String.valueOf(nmin))
						+ ":";
				strDate += ((nsec <= 9) ? ("0" + nsec) : String.valueOf(nsec));
			}
		} catch (Exception ex) {
			//Debug.log(ex.toString());
			return null;
		}
		//Debug.logFramework("date from dateformat for util date : " + strDate);
		return changeDateFormat(strDate, astrRequiredFormat, abTimeReq);
	} //end of changeDateFormat


	/**
	 * This method validates and converts the date from one String date format
	 * to another format.
	 * 
	 * @method changeDateFormat(astrDate,String,String,boolean)
	 * @param <code>String</code> astrDate The Date to be converted.
	 * @param <code>String</code> astrCurrentFormat The code for the format in
	 *            which the date is existing. valid values are 'DD/MM/YYYY' and
	 *            'MM/DD/YYYY'
	 * @param <code>String</code> astrRequiredFormat The code for the format
	 *            according to which date needs to be formatted. valid values
	 *            are 'DD/MM/YYYY' and 'MM/DD/YYYY'
	 * @param <code>boolean</code> abTimeReq This flag is specifies whether time
	 *            is also required along with date or not. For time to be
	 *            suffixed along with date set it to true else false
	 * @return <code>String</code> Returns null if some exception occurs or
	 *         invalid date else returns the formatted date.
	 */

	public static String changeDateFormat(String astrDate,
			String astrRequiredFormat, boolean abTimeReq) {
		// The current format would always be DD/MM/YYYY as this is the format
		// decided for the database
		String strCurrentFormat = "DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY";
		int nCurrentFormat = 0;
		int nRequiredFormat = 0;
		int nDateLength = 0;

		if ((astrDate == null) || (strCurrentFormat == null)
				|| (astrRequiredFormat == null)) {
			return null;
		} else {
			astrDate = astrDate.trim();
			if (!(isValid(astrDate, strCurrentFormat)))
				return null;
			astrRequiredFormat = astrRequiredFormat.toUpperCase();
			nDateLength = astrDate.length();
			if (strCurrentFormat.equalsIgnoreCase("DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (nDateLength == 10)
					nCurrentFormat = 1;
				else if (nDateLength == 19)
					nCurrentFormat = 3;
				else
					return null;
			} else
				return null;

			if (astrRequiredFormat.equalsIgnoreCase("DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (abTimeReq)
					nRequiredFormat = 1;
				else
					nRequiredFormat = 3;
			} else if (astrRequiredFormat.equalsIgnoreCase("MM" + GlobalConstants.DATE_SEPARATOR + "DD" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (abTimeReq)
					nRequiredFormat = 2;
				else
					nRequiredFormat = 4;
			} else
				return null;
		}
		return changeDateFormat(astrDate, nCurrentFormat, nRequiredFormat,
				abTimeReq);
	}

	/**
	 * This method validates and converts the date from String date format to
	 * java.sql.Timestamp type. The value is retrieved as <code>String</code>.
	 * 
	 * @method changeDateFormat(String,String)
	 * @param <code>String</code> astrDate The Date to be converted.
	 * @param <code>String</code> astrCurrentFormat The code for the format in
	 *            which the date is existing. The valid values are 'DD/MM/YYYY'
	 *            and 'MM/DD/YYYY'
	 * @return <code>String</code> Returns null if some exception occurs or
	 *         invalid date else the date in java.sql.Timestamp type.
	 */
	public static java.sql.Timestamp changeStrDateToTimestamp(String astrDate,
			String astrCurrentFormat) {
		SimpleDateFormat obFormatter;
		java.util.Date dtDate;
		int nFormat = 0;
		try {
			astrDate = astrDate.trim();
			if (!(isValid(astrDate, astrCurrentFormat)))
				return null;
			if (astrCurrentFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (astrDate.length() > 10)
					nFormat = 3;
				else {
					nFormat = 1;
					astrDate = astrDate + " 00:00:00";
				}
			} else if (astrCurrentFormat.trim().equalsIgnoreCase(
					"MM" + GlobalConstants.DATE_SEPARATOR + "DD" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (astrDate.length() > 10)
					nFormat = 4;
				else {
					nFormat = 2;
					astrDate = astrDate + " 00:00:00";
				}
			} else if (astrCurrentFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY HH:MM:SS")) {
				nFormat = 3;
			} else if (astrCurrentFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY HH:MM")) {
				nFormat = 5;
			}
			obFormatter = new SimpleDateFormat(getDateFormat(nFormat));
			obFormatter.setLenient(false);

			dtDate = obFormatter.parse(astrDate);
			return new java.sql.Timestamp(dtDate.getTime());
		} catch (Exception ex) {
			//ex.printStackTrace()
			return null;
		}

	} // end of changeDateFormat

	/**
	 * This method validates and converts the date from one String date format
	 * to another format. The value is retrieved as <code>String</code>.
	 * 
	 * @method changeDateFormat(String,String,String,boolean)
	 * @param <code>String</code> astrDate The Date to be converted.
	 * @param <code>String</code> anCurrentFormat The code for the format in
	 *            which the date is existing. The valid values are 1 for
	 *            'DD/MM/YYYY', 2 for 'MM/DD/YYYY', 3 for 'DD/MM/YYYY HH:MM:SS'
	 *            and 4 for 'MM/DD/YYYY HH:MM:SS'
	 * @param <code>String</code> anRequiredFormat The code for the format
	 *            according to which date needs to be formatted. The valid
	 *            values are 1 for 'DD/MM/YYYY', 2 for 'MM/DD/YYYY', 3 for
	 *            'DD/MM/YYYY HH:MM:SS' and 4 for 'MM/DD/YYYY HH:MM:SS'
	 * @param <code>boolean</code> abTimeReq This flag is specifies whether time
	 *            is also required along with date or not. For time to be
	 *            suffixed along with date set it to true else false
	 * @return <code>String</code> Returns null if some exception occurs or
	 *         invalid date else the formatted date.
	 */
	private static String changeDateFormat(String astrDate,
			int anCurrentFormat, int anRequiredFormat, boolean abTimeReq) {

		String strDay = null;
		String strMonth = null;
		String strYear = null;
		String strTime = "";
		String strMyDate;

		//Debug.logFramework((astrDate + " : " + anCurrentFormat + " : " + anRequiredFormat + " : " + abTimeReq).toString());

		try {
			if (!(isValid(astrDate, anCurrentFormat))) {
				return null;
			}
			if (abTimeReq) {
				if (anCurrentFormat < 3) {
					strTime = "00:00:00";
				} else {
					strTime = astrDate.substring(astrDate.indexOf(" ") + 1,
							astrDate.length());
				}
			}
			//Debug.logFramework("strTime : " + strTime + " nCurrentFormat : " + anCurrentFormat);
			switch (anCurrentFormat) {
			case 3: {

			}
			case 1: {
				strDay = astrDate.substring(0, 2);
				strMonth = astrDate.substring(3, 5);
				strYear = astrDate.substring(6, 10);
				break;
			}
			case 4: {

			}
			case 2: {
				strDay = astrDate.substring(3, 5);
				strMonth = astrDate.substring(0, 2);
				strYear = astrDate.substring(6, 10);
				break;
			}

			default: {
				break;
			}
			}
			switch (anRequiredFormat) {
			case 3: {

			}
			case 1: {
				strMyDate = strDay + GlobalConstants.DATE_SEPARATOR + strMonth +GlobalConstants.DATE_SEPARATOR + strYear + " "
						+ strTime;
				break;
			}
			case 4: {

			}
			case 2: {
				strMyDate = strMonth + GlobalConstants.DATE_SEPARATOR + strDay +GlobalConstants.DATE_SEPARATOR + strYear + " "
						+ strTime;
				break;
			}
			default: {
				strMyDate = null;
				break;
			}
			}

		} catch (Exception exExp) {
			strMyDate = null;
		}
		//Debug.logFramework(strMyDate);
		return strMyDate;
	} // end of changeDateFormat

	/**
	 * This method validates and converts the date from String date format to
	 * java.util.Timestamp type. The value is retrieved as <code>String</code>.
	 * 
	 * @method changeStringToDate(String,String)
	 * @param <code>String</code> astrDate The Date to be converted.
	 * @param <code>String</code> astrCurrentFormat The code for the format in
	 *            which the date is existing. The valid values are 'DD/MM/YYYY'
	 *            and 'MM/DD/YYYY'
	 * @return <code>java.util.Date</code> Returns null if some exception
	 *         occurs or invalid date else the date in java.util.Date type.
	 */
	public static java.util.Date changeStrDateToDate(String astrDate,
			String astrCurrentFormat) {
		SimpleDateFormat obFormatter;
		java.util.Date dtDate;
		int nFormat = 0;
		try {
			astrDate = astrDate.trim();
			if (!(isValid(astrDate, astrCurrentFormat)))
				return null;
			if (astrCurrentFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (astrDate.length() > 10)
					nFormat = 3;
				else {
					nFormat = 1;
					astrDate = astrDate + " 00:00:00";
				}
			} else if (astrCurrentFormat.trim().equalsIgnoreCase(
					"MM" + GlobalConstants.DATE_SEPARATOR + "DD" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (astrDate.length() > 10)
					nFormat = 4;
				else {
					nFormat = 2;
					astrDate = astrDate + " 00:00:00";
				}
			} else if (astrCurrentFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY HH:MM:SS")) {
				nFormat = 3;
			} else if (astrCurrentFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY HH:MM")) {
				nFormat = 5;
			}
			obFormatter = new SimpleDateFormat(getDateFormat(nFormat));
			obFormatter.setLenient(false);
			dtDate = obFormatter.parse(astrDate);
			return dtDate;
		} catch (Exception ex) {
			//ex.printStackTrace()
			return null;
		}

	} // end of changeStrDateToDate

	/*
	 * public static void main(String args[]) {
	 * //System.out.println(isValid(args[0],Integer.parseInt(args[1]))); boolean
	 * flag = false; //int requiredformat = 0; //int currentformat = 0; String
	 * requiredformat = null; String currentformat = null; Timestamp ts = null;
	 * Timestamp ts1 = null; int len = args.length; //String date = null;
	 * java.sql.Date date = null; switch(len) { case 5: { //date = args[4]; }
	 * case 4: { /*if (date!=null) date = args[3] + " " + date; else date =
	 * args[3]; date = new java.sql.Date(01,12,12); ts = new
	 * Timestamp(101,02,05,11,23,23,0); } default: { if
	 * (args[2].equalsIgnoreCase("Y")) { flag = true; } else { flag = false; }
	 * requiredformat = args[0]; currentformat = args[1]; //requiredformat =
	 * Integer.parseInt(args[0]); //currentformat = Integer.parseInt(args[1]);
	 * break; } } 
	 * Debug.log(changeDateFormat(date,requiredformat,flag)); 
	 * Debug.log(changeDateFormat(ts,requiredformat,flag)); ts1 =
	 * changeDateFormat(changeDateFormat(ts,requiredformat,flag),requiredformat);
	 * if (ts1==null) Debug.log("~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * failed -------"); else Debug.log(ts1.toString());
	 * //System.out.println(changeDateFormat(date,requiredformat,currentformat,flag));
	 *  }
	 */

	// code for date formats
	// 1 DD/MM/YYYY
	// 2 MM/DD/YYYY
	// 3 DD/MM/YYYY hh:mm:ss
	// 4 MM/DD/YYYY hh:mm:ss
	private static String getDateFormat(int anFormat) {
		String strFormat = null;
		switch (anFormat) {
		case 1: {
			strFormat = "dd" + GlobalConstants.DATE_SEPARATOR + "MM" + GlobalConstants.DATE_SEPARATOR + "yyyy";
			break;
		}
		case 2: {
			strFormat = "MM" + GlobalConstants.DATE_SEPARATOR + "dd" + GlobalConstants.DATE_SEPARATOR + "yyyy";
			break;
		}
		case 3: {
			strFormat = "dd" + GlobalConstants.DATE_SEPARATOR + "MM" + GlobalConstants.DATE_SEPARATOR + "yyyy HH:mm:ss";
			break;
		}
		case 4: {
			strFormat = "MM" + GlobalConstants.DATE_SEPARATOR + "dd" + GlobalConstants.DATE_SEPARATOR + "yyyy HH:mm:ss";
			break;
		}
		case 5: {
			strFormat = "dd" + GlobalConstants.DATE_SEPARATOR + "MM" + GlobalConstants.DATE_SEPARATOR + "yyyy HH:mm";
			break;
		}
		default: {
			strFormat = "";
			break;
		}
		}
		return strFormat;
	} //end of getDateFormat

	/**
	 * This method compares two String dates. The value is retrieved as
	 * <code>String</code>.
	 * 
	 * @param <code>String</code> astrDate1 The First Date to be compared.
	 * @param <code>String</code> astrDate2 The Second Date to be compared.
	 * @param <code>String</code> astrFormat The Date Format of the dates.
	 * @return <code>int</code> Returns -1 if first date is prior to second
	 *         date, 0 if both the dates are equal and 1 if first date is after
	 *         second date. and 100 in case there is any error, like the date
	 *         values passed are not valid
	 */
	public static int compareStrDate(String astrDate1, String astrDate2,
			String astrFormat) {
		try {
			Timestamp tsDate1, tsDate2;
			astrDate1 = astrDate1.trim();
			astrDate2 = astrDate2.trim();
			astrFormat = astrFormat.trim();
			if ((!(isValid(astrDate1, astrFormat)))
					|| (!(isValid(astrDate2, astrFormat))))
				throw new Exception(
						"Exception in compareStrDate : Invalid Dates or DateFormat ");
			else {
				tsDate1 = changeStrDateToTimestamp(astrDate1, astrFormat);
				tsDate2 = changeStrDateToTimestamp(astrDate2, astrFormat);
			}
			if ((tsDate1 == null) || (tsDate2 == null))
				throw new Exception("Unable to convert the Date in Timestamp ");
			else if (tsDate1.after(tsDate2))
				return 1;
			else if (tsDate1.equals(tsDate2))
				return 0;
			else if (tsDate1.before(tsDate2))
				return -1;
			else
				throw new Exception("Unable to identify error ");

		} catch (Exception ex) {
                        ex.printStackTrace();
			return 100;
			//return null;
		}
	} // end of compareStrDate

	// **** End of the date handling methods ****

	/**
	 * Method to replace a string containing any of the characters ()`!' or
	 * space with a underscore
	 * 
	 * @param astrArr
	 *            String[] String string containing the above mentioned
	 *            characters
	 * @return String String with underscores
	 */
	public static String replaceWithUnderscore(String astr) {
		astr = astr.replace(' ', GlobalConstants.UNDERSCORE);
		astr = astr.replace('`', GlobalConstants.UNDERSCORE);
		astr = astr.replace('\'', GlobalConstants.UNDERSCORE);
		astr = astr.replace(';', GlobalConstants.UNDERSCORE);
		astr = astr.replace('!', GlobalConstants.UNDERSCORE);
		astr = astr.replace('(', GlobalConstants.UNDERSCORE);
		astr = astr.replace(')', GlobalConstants.UNDERSCORE);
		return astr;

	}

	/**
	 * This method returns encrypted data for FTP
	 * 
	 * @method encodeFTPData(String)
	 * 
	 * @param <code>String</code> astrEncode
	 * 
	 * @return <code>String</code> Encoded String
	 *  
	 */

	public static String encodeFTPData(String astrEncode) {
		char charArrEncode[] = new char[astrEncode.length()];
		int nEncodeLen = astrEncode.length();
		int nEncode;
		for (int j = 0; nEncodeLen > j; j++) {
			nEncode = astrEncode.charAt(j) ^ 0xFF;
			charArrEncode[j] = (char) nEncode;
		}
		String strEncode = new String(charArrEncode);
		return strEncode;

	} //end encode()

	/**
	 * This method returns an encoded Password
	 * 
	 * @method encodePassword(String)
	 * 
	 * @param <code>String</code> astrArg
	 * 
	 * @return <code>String</code> Encoded String
	 *  
	 */

	public static String encodePassword(String astrArg) {

		// code characters for values 0..63
		boolean bQuad = false;
		boolean bTrip = false;
		int nVal;
		char[] cArrAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
				.toCharArray();
		char[] cArrData = astrArg.toCharArray();
		char[] cArrOut = new char[((cArrData.length + 2) / 3) * 4];

		// 3 characters encode to 4 chars. Output is always an even
		// multiple of 4 characters.

		for (int nCount = 0, nIndex = 0; nCount < cArrData.length; nCount += 3, nIndex += 4) {

			bQuad = false;
			bTrip = false;

			nVal = (0xFF & (int) cArrData[nCount]);
			nVal <<= 8;
			if ((nCount + 1) < cArrData.length) {
				nVal |= (0xFF & (int) cArrData[nCount + 1]);
				bTrip = true;
			}
			nVal <<= 8;
			if ((nCount + 2) < cArrData.length) {
				nVal |= (0xFF & (int) cArrData[nCount + 2]);
				bQuad = true;
			}
			cArrOut[nIndex + 3] = cArrAlphabet[(bQuad ? (nVal & 0x3F) : 64)];
			nVal >>= 6;
			cArrOut[nIndex + 2] = cArrAlphabet[(bTrip ? (nVal & 0x3F) : 64)];
			nVal >>= 6;
			cArrOut[nIndex + 1] = cArrAlphabet[nVal & 0x3F];
			nVal >>= 6;
			cArrOut[nIndex + 0] = cArrAlphabet[nVal & 0x3F];
		} //end for

		return String.valueOf(cArrOut);
	} //end encodePassword()

	/**
	 * Decodes a encoded Password to recover the Original Password String. White
	 * space before and after will be trimmed away, but no other manipulation of
	 * the input will be performed.
	 * 
	 * @method decodePassword(String)
	 * 
	 * @param <code>String</code> astrArg
	 * 
	 * @return <code>String</code> decoded String
	 *  
	 */

	public static String decodePassword(String astrArg) {

		// lookup table for converting Encrypted characters to value in range
		// 0..63
		int[] nArrCodes = new int[256];
		int nValue;

		for (int nCount = 0; nCount < 256; nCount++)
			nArrCodes[nCount] = -1;
		for (int nCount = 'A'; nCount <= 'Z'; nCount++)
			nArrCodes[nCount] = (nCount - 'A');
		for (int nCount = 'a'; nCount <= 'z'; nCount++)
			nArrCodes[nCount] = (26 + nCount - 'a');
		for (int nCount = '0'; nCount <= '9'; nCount++)
			nArrCodes[nCount] = (52 + nCount - '0');
		nArrCodes['+'] = 62;
		nArrCodes['/'] = 63;

		char[] cArrData = astrArg.toCharArray(); //String convert to array
		int nTempLen = cArrData.length; //Length of Array
		for (int nCount = 0; nCount < cArrData.length; nCount++) {
			// ignore non-valid chars and padding
			if ((cArrData[nCount] > 255) || (nArrCodes[cArrData[nCount]] < 0)) {
				--nTempLen;
			}
		} //end for
		// calculate required length:
		//  -- 3 chars for every 4 valid Encrypted chars
		//  -- plus 2 chars if there are 3 extra Encrypted chars,
		//     or plus 1 char if there are 2 extra.

		int nLen = (nTempLen / 4) * 3;
		if ((nTempLen % 4) == 3)
			nLen += 2;
		if ((nTempLen % 4) == 2)
			nLen += 1;

		char[] cArrOut = new char[nLen]; //output char array

		int nShift = 0; // # of excess bits stored in nAccum
		int nAccum = 0; // excess bits
		int nIndex = 0; //Array Index

		// we now go through the entire array (NOT using the 'nTempLen' value)
		for (int nCount = 0; nCount < cArrData.length; nCount++) {
			nValue = (cArrData[nCount] > 255) ? -1
					: nArrCodes[cArrData[nCount]];
			//Char Value

			if (nValue >= 0) // skip over non-code
			{
				nAccum <<= 6; // bits shift up by 6 each time thru
				nShift += 6; // loop, with new bits being put in
				nAccum |= nValue; // at the bottom.
				if (nShift >= 8) // whenever there are 8 or more shifted in,
				{
					nShift -= 8; // write them out (from the top, leaving any
					cArrOut[nIndex++] =
					// excess at the bottom for next iteration.
					(char) ((nAccum >> nShift) & 0xff);
				}
			}
		}
		return String.valueOf(cArrOut);
	} //end decodePassword()

	public static String changeDateFormat(String astrDate,
			String astrCurrentFormat, String astrRequiredFormat,
			boolean abTimeReq) {
		int nCurrentFormat = 0;
		int nRequiredFormat = 0;
		int nDateLength = 0;

		if ((astrDate == null) || (astrCurrentFormat == null)
				|| (astrRequiredFormat == null)) {
			return null;
		} else {
			nDateLength = astrDate.length();
			astrDate = astrDate.trim();
			astrCurrentFormat = astrCurrentFormat.toUpperCase();
			astrRequiredFormat = astrRequiredFormat.toUpperCase();

			if (astrCurrentFormat.equalsIgnoreCase("DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (nDateLength == 10)
					nCurrentFormat = 1;
				else if (nDateLength == 19)
					nCurrentFormat = 3;
				else
					return null;
			} else if (astrCurrentFormat.equalsIgnoreCase("MM" + GlobalConstants.DATE_SEPARATOR + "DD" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (nDateLength == 10)
					nCurrentFormat = 2;
				else if (nDateLength == 19)
					nCurrentFormat = 4;
				else
					return null;
			} else if (astrCurrentFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY HH:MM:SS")) {
				nCurrentFormat = 3;
			} else if (astrCurrentFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY HH:MM")) {
				nCurrentFormat = 5;
			}
            else
				return null;

			if (astrRequiredFormat.equalsIgnoreCase("DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (abTimeReq)
					nRequiredFormat = 1;
				else
					nRequiredFormat = 3;
			} else if (astrRequiredFormat.equalsIgnoreCase("MM" + GlobalConstants.DATE_SEPARATOR + "DD" +GlobalConstants.DATE_SEPARATOR + "YYYY")) {
				if (abTimeReq)
					nRequiredFormat = 2;
				else
					nRequiredFormat = 4;
			}  else if (astrRequiredFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY HH:MM:SS")) {
				nRequiredFormat = 3;
			} else if (astrRequiredFormat.trim().equalsIgnoreCase(
					"DD" + GlobalConstants.DATE_SEPARATOR + "MM" +GlobalConstants.DATE_SEPARATOR + "YYYY HH:MM")) {
				nRequiredFormat = 5;
			}else
				return null;
		}
		return changeDateFormat(astrDate, nCurrentFormat, nRequiredFormat,
				abTimeReq);
	}

	/**
	 * Compare any two given dates.
	 * 
	 * @param astrDD1
	 *            day part of first date
	 * @param astrMM1
	 *            month part of the first date
	 * @param astrYYYY1
	 *            year part ofr the first date
	 * @param astrDD2
	 *            day part of second date
	 * @param astrMM21
	 *            month part of the second date
	 * @param astrYYYY2
	 *            year part ofr the second date
	 * @return -1 if Date1 < Date2, 1 if Date1 > Date2, 0 if Date1 = Date2.
	 * @author Jitin Sood
	 * @version 20001020
	 */
	public static int compareDates(String astrDD1, String astrMM1,
			String astrYYYY1, String astrDD2, String astrMM2, String astrYYYY2) {
		int nDD1, nDD2, nMM1, nMM2, nYYYY1, nYYYY2;

		nYYYY2 = Integer.parseInt(astrYYYY2);
		nYYYY1 = Integer.parseInt(astrYYYY1);

		if (nYYYY2 > nYYYY1)
			return -1;
		else if (nYYYY2 < nYYYY1)
			return 1;

		nMM1 = Integer.parseInt(astrMM1);
		nMM2 = Integer.parseInt(astrMM2);

		if (nMM2 > nMM1)
			return -1;
		else if (nMM2 < nMM1)
			return 1;

		nDD1 = Integer.parseInt(astrDD1);
		nDD2 = Integer.parseInt(astrDD2);

		if (nDD2 > nDD1)
			return -1;
		else if (nDD2 < nDD1)
			return 1;

		return 0;
	}

	/**
	 * validate URL.
	 * 
	 * @param astrURL
	 *            URL
	 * @return true if valid.
	 * @author Manu Yadav
	 * @version 20000925
	 */
	public static boolean isValidURL(String astrURL) {
		URL url;
		try {
			url = new java.net.URL(astrURL);
			return true;
		} catch (MalformedURLException e) {
			return false;
		} finally {
			url = null;
		}
	}

	/**
	 * validate Time.
	 * 
	 * @param astrHH
	 *            Hours value between 0 and 24
	 * @param astrMM
	 *            Minutes value between 0 and 60
	 * @param astrSS
	 *            Seconds value between 0 and 60
	 * @return true if valid.
	 * @author Manu Yadav
	 * @version 20000925
	 */
	public static boolean isValidTime(String astrHH, String astrMM,
			String astrSS) {
		try {
			int iHH = Integer.parseInt(astrHH);
			int iMM = Integer.parseInt(astrMM);
			int iSS = Integer.parseInt(astrSS);
			return (iHH >= 0 && iHH < 24 && iMM >= 0 && iMM < 60 && iSS >= 0 && iSS < 60) ? true
					: false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * validate telephone number.
	 * 
	 * @param astrTel1
	 *            Must contain 3 characters
	 * @param astrTel2
	 *            Must contain 3 characters
	 * @param astrTel3
	 *            Must contain 4 characters
	 * @return true if valid.
	 * @author Manu Yadav
	 * @version 20000925
	 */
	public static boolean isValidTelPhoneNo(String astrTel1, String astrTel2,
			String astrTel3) {
		try {
			return (Integer.parseInt(astrTel1) > 0 && astrTel1.length() == 3
					&& Integer.parseInt(astrTel2) > 0 && astrTel2.length() == 3
					&& Integer.parseInt(astrTel3) > 0 && astrTel3.length() == 4) ? true
					: false;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * validate telephone number.
	 * 
	 * @param <code>String</code> astrTelNo Must contain 12 characters in the
	 *            format - "999-999-9999"
	 * 
	 * @return true if valid.
	 * @author Vidya Mani
	 * @version 20001114
	 */
	public static boolean isValidTelPhoneNo(String astrTelNo) {
		try {
			if ((astrTelNo.charAt(3) == '-') && (astrTelNo.charAt(7) == '-')) {
				return isValidTelPhoneNo(astrTelNo.substring(0, 3), astrTelNo
						.substring(4, 7), astrTelNo.substring(8));
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * validate HTML extension.
	 * 
	 * @param astrFileURL
	 *            URL extension to be .htm/.html only
	 * @return true if valid.
	 * @author Manu Yadav
	 * @version 20000925
	 */
	public static boolean isValidHTMLExt(String astrFileURL) {
		try {
			return (astrFileURL.toLowerCase().endsWith(".htm") || astrFileURL
					.toLowerCase().endsWith(".html")) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * validate GIF extension.
	 * 
	 * @param astrFileURL
	 *            URL extension to be .gif only
	 * @return true if valid.
	 * @author Manu Yadav
	 * @version 20000925
	 */
	public static boolean isValidGIFExt(String astrFileURL) {
		try {
			return (astrFileURL.toLowerCase().endsWith(".gif")) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Method to validate the date passed.
	 * 
	 * @param <code>String</code> astrDD Day
	 * @param <code>String</code> astrMM Month
	 * @param <code>String</code> astrYYYY Year
	 * @return <code>boolean</code> true if valid.
	 * @author Manu Yadav
	 * @version 20000925
	 */
	public static boolean isValidDate(String astrDD, String astrMM,
			String astrYYYY) {
		SimpleDateFormat formatter;
		java.util.Date dt;
		try {
			formatter = new SimpleDateFormat("yyyyMMdd");
			formatter.setLenient(false); //Strict interpretation!

			dt = formatter.parse(astrYYYY + astrMM + astrDD);
			return true;
		} catch (java.text.ParseException e) {
			return false;
		} finally {
			formatter = null;
			dt = null;
		}
	}

	/**
	 * password string validator
	 * 
	 * @author NTTDATA toyodai@nttdata.co.jp
	 * @version 20000810
	 */
	public static boolean passwordCheck(String s) {
		char ch = ' ';
		if (s.length() < 5 || s.length() > 21) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if (ch < HEM || ch > HTL) { // if between "!" and "~" , OK.
				return false;
			}
		}
		return true;
	}

	/**
	 * RFC822 Mail Address Validator
	 * 
	 * RFC822 addr-spec is complex and over-spec for us. I add following some
	 * conditions to reject. control-code \0 - \31 quoted-string or pair
	 * snum-concatinated domain string like 192.163.1.250 sharpsign-leading
	 * domain string like #[nnn.nnn.nnn.nnn]
	 * 
	 * now, the logic becomes very simple. :-)
	 * 
	 * @param str
	 *            pseudo string
	 * @see <A href="http://www.csl.sony.co.jp/rfc/cache/rfc822.txt.html">
	 *      RFC822 </A>
	 * @author NTTDATA matobaa@coe.nttdata.co.jp
	 * @version 20000811 (by toyodai) - change return value from boolean to int
	 * @version 20000808 - change the method signature from protected boolean
	 *          isValidSyntaxInternal(String) to public static boolean
	 *          isValidSyntax(String) - change getBytes("ISO8859_1") to
	 *          getBytes();
	 * 
	 * @version 20000713 - first release; My 30th birthday...sigh.
	 */
	public static int emailCheck(String s) {
		boolean atmark = false; // a flag meaning atmark found or not
		boolean period = false; // a flag meaning last char is period
		char ch = ' ';

		if (s.indexOf("@") == -1) {
			return NO_ATMARK;
		}
		if (s.indexOf("@", s.indexOf("@") + 1) > -1) {
			return EXCESS_ATMARK;
		}
		for (int i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if (ch == HAM) {
				if (atmark // atmark can appear only once
						|| period // local-part cannot end with period
						|| i == 0 // cannot start with atmark
						|| i == s.length() - 1) // cannot end with atmark
					return SYNTAX_ERR;
				atmark = true;
				period = true; // tricky code; domain cannot start with period
			} else if (ch == HPR) {
				if (period // period cannot appear continuously
						|| i == 0 // cannot start with period
						|| i == s.length() - 1) // cannot end with period
					return SYNTAX_ERR;
				period = true;
			} else if (ch > HTL || ch < HEM // including space charcter :-)
					|| ch == '(' || ch == ')' || ch == '<' || ch == '>'
					|| ch == '[' || ch == ']' || ch == ',' || ch == ';'
					|| ch == ':' || ch == '\\' || ch == '"') { // illegal
															   // charcters
				return INVALID_CHAR;
			} else if (ch == '#' && atmark) { // # cannot appear in domain part
				return INVALID_CHAR;
			} else { // It seems to be valid char,
				period = false;
			}
		}

		// if there is no atmark, atmark == false :-)
		// if there a period is not found then it is an error.
		return (atmark & s.indexOf(HPR) > 0 ? VALID : SYNTAX_ERR);
	}

	/**
	 * Check the param is a valid signed long
	 * 
	 * @param <code>String</code> s
	 * @return true : the param is a valid signed long <br>
	 *         false: otherwise
	 * @author Prakash S Kaul
	 * @version 20010609
	 */
	public static boolean isNumber(String s) {
		try {
			Long.parseLong(s);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Check the param is a valid positive long
	 * 
	 * @param <code>String</code> s
	 * @return true : the param is a valid positive long <br>
	 *         false: otherwise
	 * @author Prakash S Kaul
	 * @version 20010801
	 */
	public static boolean isNumberPositive(String s) {
		try {
			long l = Long.parseLong(s);
			if (l >= 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * check the param is composed of letter or digit
	 * 
	 * @return true : the param is composed of letter or digit <br>
	 *         false: not composed
	 * @author NTTDATA toyodai@nttdata.co.jp
	 * @version 20000927
	 */
	public static boolean isLetterOrDigit(String s) {
		char ch = ' ';
		for (int i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if (!(Character.isLetterOrDigit(ch))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method sorts a 2D array according to there [nRow][0] element. where
	 * nRow = 0 to n. Uses:
	 * 
	 * INPUT: ------------------------ -------------------------------- 2D
	 * Object Array sorted 2D Object Array ------------------------
	 * -------------------------------- { { {"UPD","1","2","3"},
	 * {"ADD","4","","6"}, {"ADD","4","","6"}, {"ADD","13","14","15"},
	 * {"UPD","7","","9"}, = {"DEL","10","11","12"}, {"DEL","10","11","12"},
	 * {"DEL","16","17","18"}, {"ADD","13","14","15"}, {"UPD","1","2","3"},
	 * {"DEL","16","17","18"} {"UPD","7","","9"} } }
	 * 
	 * @return void
	 * @args Object[][] <code>aobjArrayData</code>
	 */
	public static void sort(Object[][] aobjArrayData) {
		Object[][] objArrayData = (Object[][]) aobjArrayData.clone();
		mergeSort(objArrayData, aobjArrayData, 0, aobjArrayData.length);
	}

	/**
	 * This method sorts the specified elements of a 2D array according to there
	 * [nRow][0] element. where nRow = 0 to n. Uses:
	 * 
	 * INPUT: ------------------------ -------------------------------- 2D
	 * Object Array sorted 2D Object Array ------------------------
	 * -------------------------------- { { {"UPD","1","2","3"},
	 * {"ADD","4","","6"}, {"ADD","4","","6"}, {"ADD","13","14","15"},
	 * {"UPD","7","","9"}, = {"DEL","10","11","12"}, {"DEL","10","11","12"},
	 * {"DEL","16","17","18"}, {"ADD","13","14","15"}, {"UPD","1","2","3"},
	 * {"DEL","16","17","18"} {"UPD","7","","9"} } }
	 * 
	 * @return void
	 * @args Object[][] <code>aobjArraySrc</code> source array.
	 * @args Object[][] <code>aobjArrayDest</code> destination array.
	 * @args int <code>nlow</code> index from where method 'll start sorting.
	 * @args int <code>nhigh</code> index till there method 'll do sorting.
	 */
	private static void mergeSort(Object aobjArraySrc[][],
			Object aobjArrayDest[][], int nlow, int nhigh) {
		// calculate the length of elements.
		int length = nhigh - nlow;

		// for the efficient sorting effience of the alogo must be "Order of
		// log(n)"
		// and Insertion sort are best on smallest arrays and gives "Order of
		// log(n)" results.
		// so i am hard coding the maxi length of smallest arrays ie "7".
		if (length < 7) {
			for (int i = nlow; i < nhigh; i++) {
				for (int j = i; j > nlow
						&& ((Comparable) aobjArrayDest[j - 1][0])
								.compareTo((Comparable) aobjArrayDest[j][0]) > 0; j--) {
					swap(aobjArrayDest, j, j - 1);
				}
			}
			return;
		}

		// if array is big then Insertion sort on the complete array would not
		// effiencen
		//
		// ie no. of records (is directlly perposnal to) effience
		//		
		//so break the array from mid and do recursively sorting of
		// aobjArrayDest into aobjArraySrc
		int mid = (nlow + nhigh) / 2;
		mergeSort(aobjArrayDest, aobjArraySrc, nlow, mid);
		mergeSort(aobjArrayDest, aobjArraySrc, mid, nhigh);

		// If list is already sorted, just copy from aobjArraySrc to
		// aobjArrayDest. This is an
		// optimization that results in faster sorts for nearly ordered lists.
		if (((Comparable) aobjArraySrc[mid - 1][0])
				.compareTo((Comparable) aobjArraySrc[mid][0]) <= 0) {
			System.arraycopy(aobjArraySrc, nlow, aobjArrayDest, nlow, length);
			return;
		}

		// Merge sorted values (now in aobjArraySrc) into aobjArrayDest
		for (int i = nlow, p = nlow, q = mid; i < nhigh; i++) {
			if (q >= nhigh
					|| p < mid
					&& ((Comparable) aobjArraySrc[p][0])
							.compareTo(aobjArraySrc[q][0]) <= 0)
				aobjArrayDest[i] = aobjArraySrc[p++];
			else
				aobjArrayDest[i] = aobjArraySrc[q++];
		}
	}

	/**
	 * This method swap two elements of a given array on given indexs.
	 * 
	 * @return void
	 * @args Object <code>aObjArray</code>
	 * @args int <code>nMeTo</code>
	 * @args int <code>nThis</code>
	 */
	private static void swap(Object aObj2DArray[][], int nMeTo, int nThis) {
		Object[] objArray = aObj2DArray[nMeTo];
		aObj2DArray[nMeTo] = aObj2DArray[nThis];
		aObj2DArray[nThis] = objArray;
	}

	/**
	 * This method returns index of <code>key</code> Object from a given 2D
	 * Array OR returns -1 if object dosen't found in 2D Array. NOTE : if 2D
	 * array is a sorted array and contains duplicate elements then it returns
	 * the index of first element.
	 * 
	 * Uses1:
	 * 
	 * INPUT: ------------------------ sorted 2D Object Array
	 * ------------------------ { {"ADD","4","","6"}, {"ADD","13","14","15"},
	 * {"DEL","10","11","12"}, and key = "DEL" and nstartIndex = 0
	 * {"DEL","16","17","18"} {"UPD","1","2","3"}, {"UPD","7","","9"}, } OUTPUT:
	 * 2 Uses2:
	 * 
	 * INPUT: ------------------------ sorted 2D Object Array
	 * ------------------------ { {"ADD","4","","6"}, {"DEL","10","11","12"},
	 * and key = "DEL" and nstartIndex = 0 {"UPD","1","2","3"},
	 * {"UPD","7","","9"}, } OUTPUT: 1
	 * 
	 * @return int
	 * @args Object[][] <code>aobjArrayData</code>
	 * @args int <code>nstartIndex</code> from which position it should start
	 *       searching. default value is 0.
	 * @args String <code>astrIdentifier</code> table name.
	 */
	public static int searchFirstIndex(Object[][] aobjArrayData,
			int nstartIndex, Object key) {
		int index = -1; // key not found.
		Object midVal = null;
		int cmp = -1;

		int low = 0;
		if (nstartIndex != -1) {
			low = nstartIndex;
		}
		int high = aobjArrayData.length - 1;

		while (low <= high) {
			midVal = aobjArrayData[low][0];
			cmp = ((Comparable) midVal).compareTo(key);

			if (cmp == 0) {
				index = low; // key found
				break;
			}
			low++;
		} //end while
		return (index);
	}

	/**
	 * This method returns a HashMap that contains ~ separated string object
	 * according to there corresponding operations. Uses:
	 * 
	 * ------------------------ ----------------------------------- 2D Object
	 * Array HashMap ------------------------
	 * ----------------------------------- { {"UPD","1","2","3"}, tableName_ADD =
	 * 4~~6~13~14~15 {"ADD","4","","6"}, = tableName_DEL = 10~11~12~16~17~18
	 * {"UPD","7","","9"}, tableName_UPD = 1~2~3~7~~9 {"DEL","10","11","12"},
	 * {"ADD","13","14","15"}, {"DEL","16","17","18"} }
	 * 
	 * @return HashMap
	 * @args Object[][] <code>aobjArrayData</code> all records with status
	 *       flag.
	 * @args String <code>astrIdentifier</code> table name.
	 */
	public static HashMap extractRecordStringAccordingOprn(
			Object[][] aobjArrayTableData, String astrIdentifier) {
		// structure to return final results.
		HashMap hResult = new HashMap();

		// if array OR identifier name is null the return without doing
		// anything.
		if (aobjArrayTableData == null || astrIdentifier == null)
			return null;

		if (aobjArrayTableData.length > 0)
		//check is there any record in 2D array.
		{
			// sort the 2D array according to String[nRow][0] element for
			// efficient manipulation.
			sort(aobjArrayTableData);
			//for(int i=0 ; i<aobjArrayTableData.length ; i++)
			//System.out.println("Data at "+i+" : "+aobjArrayTableData[i][0]);

			// get the index of first element of the group of elements which
			// start with ADD flag.
			int addIndex = searchFirstIndex(aobjArrayTableData, 0, "ADD");
			// get the index of first element of the group of elements which
			// start with DEL flag.
			int delIndex = searchFirstIndex(aobjArrayTableData, addIndex, "DEL");
			// get the index of first element of the group of elements which
			// start with UPD flag.
			// but this time we will start searching from delIndex,because we
			// know,we are
			// working with a sorted array and updt will be next group from DEL
			// group so why?
			// we shoult waste our time in searching from start index(0).
			int updtIndex = searchFirstIndex(aobjArrayTableData, delIndex,
					"UPD");

			//System.out.println("addIndex = "+addIndex);
			//System.out.println("delIndex = "+delIndex);
			//System.out.println("updIndex = "+updtIndex);

			// now get the size of elements in groups.
			int addSize = 0; //default
			int delSize = 0; //default
			int updtSize = 0; //default

			// check for the add records if found :
			// 1. check for the add record exists if found
			//	  then addSize will be delIndex - addIndex
			// 2. otherwise(only add and update records exists in 2D Array.)
			//	  so addSize will be updtIndex - addIndex
			// 3. Oh!! only add records exists in 2D Array.
			//    so addSize will be array length - addIndex
			if (addIndex > -1) {
				if (delIndex > -1) {
					addSize = delIndex - addIndex;
				} else if (updtIndex > -1) {
					addSize = updtIndex - addIndex;
				} else {
					addSize = aobjArrayTableData.length - addIndex;
				}
			} else {
				// there is no add record exists in this sorted 2D array.
				addSize = 0;
			}

			// NOTE: delSize in not depends on addIndex,Because if add would not
			// be in array
			// then delIndex would be 0. so don't bother about it.
			// check for the del records exists if found :
			// 1. check for the upd record exists if found
			//	  then delSize will be uptIndex - delIndex
			// 3. Oh!! update dosen't exists in 2D Array. ie Del group is last
			// group in 2D Array
			//    so delSize will be array length - delIndex
			if (delIndex > -1) {
				if (updtIndex > -1) {
					delSize = updtIndex - delIndex;
				} else {
					delSize = aobjArrayTableData.length - delIndex;
				}

			} else {
				// there is no del record exists in this sorted 2D array.
				delSize = 0;
			}

			// because update is last group in 2D array so it is not depent on
			// the index of
			// add and del groups. so
			// uptSize will be array length - UpdtIndex
			if (updtIndex > -1) {
				//updtSize = aobjArrayTableData.length - delSize - addSize;
				updtSize = aobjArrayTableData.length - updtIndex;
			} else {
				// there is no updt record exists in this sorted 2D array.
				updtSize = 0;
			}

			//System.out.println("addSize = "+addSize);
			//System.out.println("delSize = "+delSize);
			//System.out.println("updSize = "+updtSize);

			if (addIndex > -1) // check for the add records
			{
				// create a 2D array for add group.
				Object[][] strArrayForAdd = new Object[addSize][aobjArrayTableData.length];
				// copy the add records from sorted array.
				System.arraycopy(aobjArrayTableData, addIndex, strArrayForAdd,
						0, addSize);
				// create key for add
				StringBuffer sbKey = new StringBuffer(astrIdentifier);
				sbKey.append(ADD_EXT);
				// convert the array into string and put it into a hashMap
				hResult.put(sbKey.toString(), StringUtil.convert2DArrayToStringWithoutFlag(strArrayForAdd, 0));
			}

			if (delIndex > -1) // check for the del records
			{
				// create a 2D array for del group.
				Object[][] strArrayForDel = new Object[delSize][aobjArrayTableData.length];
				// copy the del records from sorted array.
				System.arraycopy(aobjArrayTableData, delIndex, strArrayForDel,
						0, delSize);
				// create key for del
				StringBuffer sbKey = new StringBuffer(astrIdentifier);
				sbKey.append(DEL_EXT);
				// convert the array into string and put it into a hashMap
				hResult.put(sbKey.toString(), StringUtil.convert2DArrayToStringWithoutFlag(strArrayForDel, 0));
			}

			if (updtIndex > -1) // check for the upt records
			{
				// create a 2D array for upt group.
				Object[][] strArrayForUpt = new Object[updtSize][aobjArrayTableData.length];
				// copy the upt records from sorted array.
				System.arraycopy(aobjArrayTableData, updtIndex, strArrayForUpt,
						0, updtSize);
				// create key for del
				StringBuffer sbKey = new StringBuffer(astrIdentifier);
				sbKey.append(UPD_EXT);
				// convert the array into string and put it into a hashMap
				hResult.put(sbKey.toString(), StringUtil.convert2DArrayToStringWithoutFlag(strArrayForUpt, 0));
			}

		}
		return hResult;
	}

	/**
	 * Constructs and returns a GregorianCalendar object
	 * 
	 * @param astrDate
	 *            in 'YYYYMMDDHH24MISS'/'YYYYMMDD' format
	 * @return GregorianCalendar
	 */
	private static GregorianCalendar contructDate_(String astrDate,
			String astrBaseDateFormat) {
		/*
		 * // Format Expected : 'YYYYMMDDHH24MISS'/'YYYYMMDD' int lintYear =
		 * Integer.parseInt(astrDate.substring(0, 4)); int lintMonth =
		 * Integer.parseInt(astrDate.substring(4, 6)); int lintDate =
		 * Integer.parseInt(astrDate.substring(6, 8)); int lintHour = 0; int
		 * lintMin = 0; try { lintHour = Integer.parseInt(astrDate.substring(8,
		 * 10)); lintMin = Integer.parseInt(astrDate.substring(10, 12)); } catch
		 * (StringIndexOutOfBoundsException indexExcep ) { lintHour = 0; lintMin =
		 * 0; } return new GregorianCalendar( lintYear, lintMonth - 1, lintDate,
		 * lintHour, lintMin);
		 */
		astrBaseDateFormat = astrBaseDateFormat == null ? "yyyymmddhhmiss"
				: astrBaseDateFormat;
		DateFormat lobjDateFormat = getFormat_(astrBaseDateFormat);
		GregorianCalendar obj = new GregorianCalendar();
		try {
			obj.setTime(lobjDateFormat.parse(astrDate));
		} catch (ParseException pse) {
			throw new IllegalArgumentException("Invalid Base Date format");
		}
		return obj;
	}

	/**
	 * gets format for getting date
	 * 
	 * @param astrFormat :
	 *            CASE INSENSITIVE date Format For month use mm/MM; for minuites
	 *            use mi/MI
	 * @return DateFormat
	 */
	private static DateFormat getFormat_(String astrFormat) {
		astrFormat = astrFormat.toLowerCase();
		//astrFormat = astrFormat.replaceAll("mm", "MM");
		astrFormat = StringUtil.replace(astrFormat, "mm", "MM");
		//astrFormat = astrFormat.replaceAll("hh", "HH");
		astrFormat = StringUtil.replace(astrFormat, "hh", "HH");
		//astrFormat = astrFormat.replaceAll("mi", "mm");
		astrFormat = StringUtil.replace(astrFormat, "mi", "MI");
		DateFormat lobjDateFormat = new java.text.SimpleDateFormat(astrFormat);
		return lobjDateFormat;

	}

	/**
	 * gets Calendar options based on format.
	 * 
	 * @param astrFormat :
	 *            CASE INSENSITIVE date Format Supports only dd , mm and yyyy
	 * @return int
	 */
	private static int getOption_(String astrFormat) {
		int lintOpt = -1;
		astrFormat = astrFormat.toLowerCase();
		if (astrFormat.equals("dd")) {
			lintOpt = Calendar.DATE;
		} else if (astrFormat.equals("mm")) {
			lintOpt = Calendar.MONTH;
		} else if (astrFormat.equals("yyyy")) {
			lintOpt = Calendar.YEAR;
		}
		return lintOpt;
	}

	/**
	 * returns a new date relative to <b>astrBaseDate </b>.
	 * 
	 * @param astrFormat :
	 *            CASE INSENSITIVE Calendar Format. Valid values are 'dd','mm',
	 *            and 'yyyy'
	 * @param aintDiff :
	 *            diff to be calculated
	 * @param astrRetFmt :
	 *            CASE INSENSITIVE date Format
	 * @param astrBaseDate :
	 *            Date on which operation is to be done, in
	 *            YYYYMMDDHH24MISS/YYYYMMDD format
	 * @return String[]
	 * @see getOption_()
	 */
	public static String getNewDate(String astrFormat, int aintDiff,
			String astrRetFmt, String astrBaseDate) {
		return getNewDate(astrFormat, aintDiff, astrRetFmt, astrBaseDate, null);
	}

	/**
	 * returns a new date relative to <b>astrBaseDate </b>.
	 * 
	 * @param astrFormat :
	 *            CASE INSENSITIVE Calendar Format. Valid values are 'dd','mm',
	 *            and 'yyyy'
	 * @param aintDiff :
	 *            diff to be calculated
	 * @param astrRetFmt :
	 *            CASE INSENSITIVE date Format
	 * @param astrBaseDate :
	 *            Date on which operation is to be done
	 * @param astrBaseDateFormat :
	 *            Format of the base date
	 * @return String[]
	 * @see getOption_()
	 */
	public static String getNewDate(String astrFormat, int aintDiff,
			String astrRetFmt, String astrBaseDate, String astrBaseDateFormat) {
		astrFormat = astrFormat.toLowerCase();
		GregorianCalendar lobjTmp = contructDate_(astrBaseDate,
				astrBaseDateFormat);
		// make copy of date obj
		int lintOpt = getOption_(astrFormat);
		// get option to decide what to manipulate
		lobjTmp.add(lintOpt, aintDiff); // add the diffrence to option
		DateFormat lobjDateFormat = getFormat_(astrRetFmt);
		return lobjDateFormat.format(lobjTmp.getTime());
	}

	/**
	 * returns a string array of dates relative to <b>astrBaseDate </b>. Size of
	 * array returned is equals to <b>aintDiff </b>.
	 * 
	 * @param astrFormat :
	 *            CASE INSENSITIVE Calendar Format. Valid values are 'dd','mm',
	 *            and 'yyyy'
	 * @param aintDiff :
	 *            diff to be calculated
	 * @param astrRetFmt :
	 *            CASE INSENSITIVE date Format
	 * @param astrBaseDate :
	 *            Date on which operation is to be done, in
	 *            YYYYMMDDHH24MISS/YYYYMMDD format
	 * @return String[]
	 * @see getOption_()
	 */
	public static String[] getDateArray(String astrFormat, int aintDiff,
			String astrRetFmt, String astrBaseDate) {
		return getDateArray(astrFormat, aintDiff, astrRetFmt, astrBaseDate,
				null);
	}

	/**
	 * returns a string array of dates relative to <b>astrBaseDate </b>. Size of
	 * array returned is equals to <b>aintDiff </b>.
	 * 
	 * @param astrFormat :
	 *            CASE INSENSITIVE Calendar Format. Valid values are 'dd','mm',
	 *            and 'yyyy'
	 * @param aintDiff :
	 *            diff to be calculated
	 * @param astrRetFmt :
	 *            CASE INSENSITIVE date Format
	 * @param astrBaseDate :
	 *            Date on which operation is to be done
	 * @param astrBaseDateFormat :
	 *            Format of the base date
	 * @return String[]
	 * @see getOption_()
	 */
	public static String[] getDateArray(String astrFormat, int aintDiff,
			String astrRetFmt, String astrBaseDate, String astrBaseDateFormat) {
		int lintSize = Math.abs(aintDiff); // to decide on array size
		String[] larrstrRet = new String[lintSize];
		astrFormat = astrFormat.toLowerCase();
		GregorianCalendar lobjTmp = contructDate_(astrBaseDate,
				astrBaseDateFormat);
		int lintIncr = aintDiff < 0 ? -1 : 1; // initialise increment variable
		int lintOpt = getOption_(astrFormat);
		// get option to decide swhat to manipulate
		DateFormat lobjDateFormat = getFormat_(astrRetFmt);
		for (int lintX = 0; lintX < lintSize; lintX++) { // init array...
			larrstrRet[lintX] = lobjDateFormat.format(lobjTmp.getTime());
			lobjTmp.add(lintOpt, lintIncr); // incr/decr date based on option
		}
		return larrstrRet;
	}
	
	
	public static java.sql.Date dateConvert(String date) {
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//String dateString = formatter.format(date);
		String dateString = date;
		dateString = dateString.replace('/','-');
		java.sql.Date dat = java.sql.Date.valueOf(dateString);
		return (dat);
	}

	
	public static HashMap getRowsAccOperation(List alstDatatosave) {
		HashMap lhsmData = new HashMap();
		ArrayList lstAdd = new ArrayList();
		ArrayList lstDel = new ArrayList();
		ArrayList lstUpd = new ArrayList();
		
		BaseVO lobjVO = null;
		
		int lintSize = alstDatatosave.size();
		if(lintSize == 0) {
			return null;
		}
		
				
		for(int lintI = 0;lintI < lintSize ; lintI++) {
			lobjVO = (BaseVO)alstDatatosave.get(lintI);
			if(lobjVO.getStatus().equals(GlobalConstants.HIDE) || lobjVO.getStatus().equals("")) {
				continue;
			}

			if(lobjVO.getStatus().equals(GlobalConstants.ADD)){
				lstAdd.add(lobjVO);
			} else if(lobjVO.getStatus().equals(GlobalConstants.DEL)) {
				lstDel.add(lobjVO);
			} else if(lobjVO.getStatus().equals(GlobalConstants.UPD)) {
				lstUpd.add(lobjVO);
			}
		}
		if(lstAdd.size() > 0){
			lhsmData.put(GlobalConstants.KEY_ADD, lstAdd);
		}
		if(lstDel.size() > 0){
			lhsmData.put(GlobalConstants.KEY_DEL, lstDel);
		}
		if(lstUpd.size() > 0){
			lhsmData.put(GlobalConstants.KEY_UPD, lstUpd);
		}
		return lhsmData;

	}

   /*
	* Method isInteger . This method is used to check the input string 
	* is intger or not 
	* @see none
	*/
	public static boolean isInteger( String aStrVal, String astrNegative, String astrZero) 
	{

		long lresult;

		/* Return true if given string is blank else false  */
		try{
			 lresult = Long.parseLong( aStrVal );
		}catch (Exception e){
			return false ; }
      
      // if negative then error
      if(astrNegative.equals("N")) {
         if(lresult < 0) {
            return false;
         }
      }
      // if zero then error
      if(astrZero.equals("N")) {
         if(lresult == 0) {
            return false;
         }
      }

		return true  ;
	}

   /*
	* Method aStrVal . This method is used to check the input string 
	* is Zero or not 
	* @see none
	*/
	public static boolean isZero( String astrVal) 
	{
		double ldblValue;

      try{
			 ldblValue = Double.parseDouble(astrVal);
		}catch (Exception e){
			return false;
      }
      
      if(ldblValue == 0) {
         return true;
      }
      return false;
   }


    /*
	* Method isDecimal . This method is used to check the input string 
	* is double or not .
	* @see none
	*/
	public static boolean isDecimal( String astrVal , String astrFormat,
                                    String astrNegative, String astrZero) 
	{

		double lresult ;

		int    lintValLen    =  astrVal.length()    ;
		int    lintFormatLen =  astrFormat.length() ;

		int    lintValDecPOs = astrVal.indexOf(".") ;
		int    lintFormatDecPOs = astrFormat.indexOf(".") ;

        if ( lintValDecPOs != -1 )
        {
			if ( (astrVal.substring(0,lintValDecPOs)).length() > (astrFormat.substring(0,lintFormatDecPOs)).length())
			{
				return false ;
			}
			if ( (astrVal.substring(lintValDecPOs)).length() > (astrFormat.substring(lintFormatDecPOs)).length())
			{
				return false ;
			}
        }
        else
        {
        	if ( lintValLen > ( lintFormatLen - lintFormatDecPOs + 1) ) 
			{
				return false ;
			}      	
        }
		/* Return true if given string is blank else false  */
		try{
			 lresult = Double.parseDouble( astrVal );
		}catch (Exception e){
			return false ;
		}

      // if negative then error
      if(astrNegative.equals("N")) {
         if(lresult < 0) {
            return false;
         }
      }
      // if zero then error
      if(astrZero.equals("N")) {
         if(lresult == 0) {
            return false;
         }
      }

		return true ;

	} /* End of isDecimal */

    /*
	* Method isCorrectFormat . This method is used to check the input string 
	* is double or not .
	* @see none
	*/
	public static boolean isCorrectFormat( String astrVal , String astrFormat ) 
	{

      int lintLenD   = astrVal.length()    ;
      int lintLenF   = astrFormat.length() ;
		int lintKount  =  0 ;
		int lintD      = -1 ;
		int lintF      = -1 ;

        
		if ( lintLenF  ==  0 ) 
      {
         return true  ;

      }else if ( lintLenD != lintLenF ) 
      {
         return false  ;
      }

		while( lintKount < lintLenF  ) 
      {
         lintD = ( int)astrVal.charAt(lintKount);
         lintF = ( int)astrFormat.charAt(lintKount);
         lintKount++;
         if( !(( lintF >= 48 &&  lintF <= 57 ) || 
          ( lintF >= 65 &&  lintF <= 90 ) || ( lintF >= 97 &&  lintF <= 122 )) )
         {
            if ( lintD != lintF )
            {
               return false ;
            }
         }
      }

		return true ;
	} 

	/**
	 * Method rtrim.
	 * Removes trailing white spaces.
	 * @param String
	 * @return String
	*/
	public static String rtrim(String astrData)
    {
		if(astrData == null) {
			return astrData;
		}
        while(astrData.length() > 0) 
        {
            char ch = astrData.charAt(astrData.length() - 1);
            if(ch == ' ')
                astrData = astrData.substring(0, astrData.length() - 1);
            else
                return astrData;
        }
        return astrData;
   }

	

 	/**
   * Method setFormatMask, Sets the Date Format just as util convention
	* @param String, The Date Format
	* @return String[], Formatted String array
	* @see none
	*/
	public static String[] getFormatMask(String astrFormat) {
		StringBuffer lstbBufCase = new StringBuffer();
		String lstrSep = "";
		/* Default date format values */
		String lstrRet[] = { "DDYYYY", "/" };
		for (int lintCtr = 0; lintCtr < astrFormat.length(); lintCtr++) {
			String lstrTemp = astrFormat.substring(lintCtr, lintCtr + 1);
			if (lstrTemp.equalsIgnoreCase("D")
				|| lstrTemp.equalsIgnoreCase("Y")) {
				lstbBufCase.append(lstrTemp.toLowerCase());
			} else if (lstrTemp.equalsIgnoreCase("M")) {
				lstbBufCase.append(lstrTemp.toUpperCase());
			} else {
				lstbBufCase.append(lstrTemp);
				lstrSep = lstrTemp;
			}
		}
		lstrRet[0] = lstbBufCase.toString();
		lstrRet[1] = lstrSep;
		return lstrRet;
	}
   
    /** Read The Line from CSV and return the tilda saprated String			  */
	/** @method readCSV_												      */
	/** @param  astrLine : String in CSV Format								  */
	/** @return String : Tilda Saprated Strig								  */
	public static String readCSV_(String astrLine) {
		StringBuffer lstbRow = new StringBuffer();
		char[] larrchrLine = astrLine.toCharArray();
		int lintLn = larrchrLine.length;
		boolean lblnQOpen = false;
		boolean lblnCharFound = false;
		char lchrTilda = '~';
		char lchrQuait = '"';
		char lchrComa = ',';
		// Main Loop 
		for (int lintI = 0; lintI < lintLn; lintI++) {
			// Column Value is not present in duoble quit
			if (!lblnQOpen) {
				// Duble Quit Found at the frist char of column
				if (larrchrLine[lintI] == lchrQuait && !lblnCharFound) {
					lblnQOpen = true;
				} else {
					if (larrchrLine[lintI] == lchrComa) {
						lblnCharFound = false;
						lstbRow.append(lchrTilda);
					} else {
						lblnCharFound = true;
						lstbRow.append(larrchrLine[lintI]);
					}
				}
			} else {
				if (larrchrLine[lintI] == lchrQuait) {
					if (lintI + 1 < lintLn) {
						if (larrchrLine[lintI + 1] == lchrQuait) {
							lstbRow.append(lchrQuait);
							lintI++;
						} else if (larrchrLine[lintI + 1] == lchrComa) {
							lstbRow.append(lchrTilda);
							lblnCharFound = false;
							lblnQOpen = false;
							lintI++;
						} else {
							lblnQOpen = false;
						}

					} else {
						lblnQOpen = false;
					}
					// just append the char	regardless coma		
				} else {
					lstbRow.append(larrchrLine[lintI]);
				}
			}
		}
		return lstbRow.toString();
	}

    /**
    * This function is used to get Holiday for a month and year
    * @method    getHolidays(int,int,String)
    * @param    <code>int</code> aintMonth:Month for which holidays are to be found
    * @param    <code>int</code> aintYear:Year for which holidays are to be found
    * @param    <code>String</code> astrData:String from the database to be read for the month and year
    * @return    <code>String[]</code>:Array of dates in format (DDMMYYYYY)
    * @see       none
    */
    public static String[] getHolidays(int aintMonth, int aintYear, String astrData)
    {
        if(astrData==null){
            return null;
        }
        ArrayList larlDates=new ArrayList();
        String lstrDate;
        for(int lintCounter=0;lintCounter<astrData.length();lintCounter++){
            if(astrData.charAt(lintCounter)==HOLIDAY_REP)
            {
                lstrDate=((lintCounter<=8)? "0":BLANK )+ (lintCounter + 1);
                lstrDate=lstrDate + ((aintMonth<10)?"0":BLANK) + aintMonth;
                lstrDate=lstrDate + aintYear;
                larlDates.add(lstrDate);
            }
        }
        String[] larrstrDates =  new String[larlDates.size()];
        for(int lintCount=0;lintCount <larlDates.size();lintCount++ ){
            larrstrDates[lintCount]=(String)larlDates.get(lintCount);
        }
        return larrstrDates;
    }//end of getHolidays

     /** Read The Line from Excel and return the tilda saprated String   */
     /** @method readExcel                                               */
     /** @param  astrExcelFilePath : File Path name                      */
     /** @param  aintFromRowNo : Row Number from to read                 */
     /** @param  aintFromColNo : Column Number from to read              */
     /** @param  aintToColNo   : Column Number To to read                */     
     /** @param  aintFromRowNo : Row Number from to read                 */     
     /** @param  ablnIncludeLineNo: true:include line no in arry index=0 */
     /** @return String[][] : content of excel                           */
     public  String[][] readExcel(String astrExcelFilePath,
                                        String sheetName, 
                                        int aintFromRowNo,
                                        int aintFromColNo,
                                        int aintToColNo, 
                                        boolean ablnIncludeLineNo) throws IOException{
         FileInputStream fin = null;
         POIFSFileSystem fs = null;
         HSSFWorkbook wb = null;
         HSSFSheet xlsSheet = null;
         HSSFRow xlsRow = null;
         HSSFCell xlsCell = null;
         String[][] larrstrReturn = null;
         ArrayList arlData = null;
         String[] larrRow = null;
         int lintTotalRows = 0;
         int lintTotColumns= (aintToColNo - aintFromColNo) + 1;
         try {
             fin = new FileInputStream(astrExcelFilePath);
             fs = new POIFSFileSystem(fin);
             wb = new HSSFWorkbook(fs);

             xlsSheet = wb.getSheetAt(0);

             lintTotalRows = xlsSheet.getPhysicalNumberOfRows();
             System.out.println("lintTotalRows---------->" + lintTotalRows);
//             if (lintTotalRows == 1) {
//                 xlsSheet = wb.getSheetAt(1);
//                 lintTotalRows = xlsSheet.getPhysicalNumberOfRows();
//             }
             arlData = new ArrayList(0);

             for (int rows = (aintFromRowNo-1), intIdx = 0; rows < lintTotalRows; rows++) {
                 xlsRow = xlsSheet.getRow(rows);
                 if (xlsRow != null) {
                     if (ablnIncludeLineNo) {
                         larrRow = new String[lintTotColumns + 1];
                         larrRow[0] = (rows + 1) + BLANK;
                     } else {
                         larrRow = new String[lintTotColumns];
                     }
                     for (int cell = (aintFromColNo-1); cell < aintToColNo; cell++) {
                        //System.out.println("Cell value is " + xlsRow.getCell(cell));
                    	 xlsCell = xlsRow.getCell(cell);
                         if (xlsCell != null) {
                        	 if (ablnIncludeLineNo) {
                                 larrRow[cell + 1] = getValueString(xlsCell);//xlsCell.toString();
                             } else {
                                 larrRow[cell] = getValueString(xlsCell);//xlsCell.toString();
                             }
                         } else {
                        	 if (ablnIncludeLineNo) {
                                 larrRow[cell + 1] = BLANK;
                             } else {
                                 larrRow[cell] = BLANK;
                             }
                         }
                     }
                     arlData.add(intIdx, larrRow);
                     intIdx++;
                 }
             }
             lintTotalRows = arlData.size();
             larrstrReturn = new String[lintTotalRows][lintTotColumns];

             for (int iRow = 0; iRow < lintTotalRows; iRow++) {
                 larrstrReturn[iRow] = (String[])arlData.get(iRow);
             }

         } catch (IOException ioe) {
             ioe.printStackTrace();
             throw ioe;
         } catch (Exception e) {
             e.printStackTrace();              
         } finally {
             try {
                 fin.close();
             } catch (Exception e) {
             }
             fin = null;
             fs = null;
             wb = null;
         }

         return larrstrReturn;
     }
     
    /*
     * convert excel data to String
     */
    public static String getValueString(HSSFCell xlsCell){
        String lstrValues   = null;
        int lintType        = 0;        
        lintType = xlsCell.getCellType();       
//        boolean isDate = false;
//        try{
//        	isDate = DateUtil.isCellDateFormatted(xlsCell);
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        if(isDate){
//        	lintType = ReportTagConstant.CELL_TYPE_DATE;
//        }
        switch (lintType)
        {
        case ReportTagConstant.CELL_TYPE_DATE :
        	 if(xlsCell.getDateCellValue() != null){
        		 lstrValues = changeDateFormat(xlsCell.getDateCellValue(), GlobalConstants.DATE_FORMAT,false);
        	 } else {
        		 lstrValues = BLANK;
        	 }
        	 break;
        case HSSFCell.CELL_TYPE_NUMERIC :
        	 //lstrValues = String.valueOf(xlsCell.getNumericCellValue());
        	 lstrValues = NUMBER_FORMATER.format(xlsCell.getNumericCellValue());
             break;
        case HSSFCell.CELL_TYPE_STRING :
             lstrValues = xlsCell.getRichStringCellValue().getString();
             break;
        case HSSFCell.CELL_TYPE_FORMULA :
        	 lstrValues = BLANK;//xlsCell.getCellFormula();
             break;
        case HSSFCell.CELL_TYPE_BLANK :
        	 lstrValues = BLANK;
             break;
        case HSSFCell.CELL_TYPE_BOOLEAN :
             lstrValues = xlsCell.toString();//String.valueOf(xlsCell.getBooleanCellValue());
             break;
        case HSSFCell.CELL_TYPE_ERROR :
            lstrValues = BLANK;
            break;
        default :
        	lstrValues = xlsCell.getRichStringCellValue().toString();
        
        }
        if(isCellWithFormula(lstrValues)){
        	lstrValues = BLANK;
        }
        return lstrValues;
    }
    
    public static boolean isCellWithFormula(String astrCellContent){
    	if(astrCellContent != null && astrCellContent.length() > 0){
    		if(astrCellContent.indexOf("IF")==0 || astrCellContent.indexOf("If")==0 
    		|| astrCellContent.indexOf("iF")==0 || astrCellContent.indexOf("if")==0){
    			return true;
    		}
    		if(astrCellContent.indexOf("=")>=0){
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
    
    public static void main (String args[]){
    	try{
    	    String astrExcelFilePath = "D:\\output.xls";
    	    String sheetName = "First Sheet"; 
    	    int aintFromRowNo=1;
    	    int aintFromColNo=1;
    	    int aintToColNo=38;
    	    boolean ablnIncludeLineNo = false;
	    	String retArray[][] = new GlobalUtil().readExcel(astrExcelFilePath,sheetName,aintFromRowNo,aintFromColNo,aintToColNo,ablnIncludeLineNo);
	    	int rowT = (retArray.length > 10?10:retArray.length);
	    	for(int i=0;i < rowT;i++){
                    for(int j=0;j < retArray[i].length;j++){
                            Debug.logFramework("####Data for Row["+i+"]Col["+j+"]{"+retArray[i][j]+"}");
                    }
	    	}
    	}catch (Exception ex){
    		ex.printStackTrace();
    	}
    	
    }
    
}    

 /* Modification History
  *
  * 2003-04: Modified for OEM
  * 2005-12: Moified for Tops
  *
  */
