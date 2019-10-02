 /*-----------------------------------------------------------------------------------------------------------
 StringUtil.java
 -------------------------------------------------------------------------------------------------------------
 Copyright RCL Public Co., Ltd. 2007
 -------------------------------------------------------------------------------------------------------------
 Author NIIT 24/08/17
 - Change Log ------------------------------------------------------------------------------------------------
 ## DD/MM/YY -User-     -TaskRef-      -Short Description
 01 24/08/17  NIIT       IJS            StringUtil for Excel upload
 -----------------------------------------------------------------------------------------------------------*/

 
package com.rclgroup.dolphin.ijs.web.common.excel;


import java.security.MessageDigest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 *
 * Common Utility Class - contains methods related to string manipulation
 * such as htmlEncode, getByteLength, etc.
 * All methods in this class are static.
 *
 * @author NIIT
 * @version 20011205
 *
 */

public class StringUtil {

        private static final String SEPARATOR = "~";
        private static final String BLANK     = "";
    
        /* Encoding */
        public static final String ENCODING = "UTF-8";

	private static final String SOURCE_ENCODING = "ISO8859_1";
	private static final String MS	            = "MS874";

	/* hankaku characters */
	final static char HTL	   = '~';  // tilde
	final static char HNSTART  = '0';
	final static char HNEND	   = '9';
	final static char HAM	   = '@';  // atmark
	final static char HPR	   = '.';  // period
	final static char HEM	   = '!';  // exclamation mark

	/**
	 * Boundary line check.
	 * The following ASCII characters are considered illegal:<br>
	 * ! " ' ( ) , : ; < = > [ \ ^ ] `
	 *
	 * @author Ashok
	 * @version 20010101
	 * @param <code>String</code> s
	 * @return <code>boolean</code> returns true if valid, false if not
	 *
	 */
	public static boolean boundaryLineCheck(String s){

		char ch=' ';
		for (int i=0; i<s.length(); i++){
			ch = s.charAt(i);
            if ( ch < HEM || ch > HTL // Should be between "!" and "~" , OK.
                || ch == '!'  || ch == '"'
                || ch == '\'' || ch == '('
                || ch == ')'  || ch == ','
                || ch == ':'  || ch == ';'
                || ch == '<'  || ch == '='
                || ch == '>'  || ch == '['
                || ch == '\\' || ch == ']'
                || ch == '^'  || ch == '`'
		|| ch == ' '
            ){
                return false;//invalid
            }
		}
		return true;
	}

	/**
	 * @author NTTDATA toyodai@nttdata.co.jp
	 * @version 20000810
	 * @version 20010117
	 */
	public static String utfAutoToUnicode(String s){
		try{
			return new String (s.getBytes("ISO8859_1"), "UTF-8");
		} catch (Exception ex){
			return s;
		}
	}


	/**
 	 * Encodes the param using MD5.
	 * @param <code>String</code> s
	 * @return <code>String</code> the encoded string
	 * @author NTTDATA toyodai@nttdata.co.jp
	 * @version 20000927
	 */
	public static String getHashedString(String s){

		MessageDigest md5Digest;
		int unsigned;
		String hexString;
		StringBuffer hashedString = new StringBuffer();

		try{
			md5Digest = MessageDigest.getInstance("MD5");
			md5Digest.update(s.getBytes());
			byte[] md5Hash = md5Digest.digest();

			for (int i=0; i<md5Hash.length; i++){
				unsigned = 0xff & md5Hash[i];
				hexString = Integer.toHexString(unsigned);
				if(hexString.length()==1){
					hashedString.append("0");
					hashedString.append(hexString);
				} else {
					hashedString.append(hexString);
				}
			}
			return (new String(hashedString));
		} catch (Exception ex) {
			return s;
		}
	}

	/**
	 * Replaces special HTML characters with their escape characters.
	 * @param <code>String</code> s
	 * @return <code>String</code> the html - encoded string
	 * @author NTTDATA matobaa@nttdata.co.jp
	 * @version 20000927
	 */
	public static String htmlEncode(String s){
		if(s != null){
			char[] buffer = s.toCharArray();
			StringBuffer newString = new StringBuffer(buffer.length+30);
			for(int i=0; i<buffer.length; i++){
				if(buffer[i]=='<') {newString.append("&lt;");} else
				if(buffer[i]=='>') {newString.append("&gt;");} else
				if(buffer[i]=='"') {newString.append("&quot;");} else
				if(buffer[i]=='&') {newString.append("&amp;");} else			
				if(buffer[i]=='\n') {newString.append("<br>");} else
				newString.append(buffer[i]);
			}
			return newString.toString();
		}
		else
			return s;
	}

	/**
	 * Replaces special HTML characters with their escape characters, except for new line.
	 * @param <code>String</code> s
	 * @return <code>String</code> the html - encoded string
	 * @author Manu Yadav
	 * @version 20010808
	 */
	public static String htmlEncodeNoBR(String s){
		if(s != null){
			char[] buffer = s.toCharArray();
			StringBuffer newString = new StringBuffer(buffer.length+30);
			for(int i=0; i<buffer.length; i++){
				if(buffer[i]=='<') {newString.append("&lt;");} else
				if(buffer[i]=='>') {newString.append("&gt;");} else
				if(buffer[i]=='"') {newString.append("&quot;");} else
				if(buffer[i]=='&') {newString.append("&amp;");} else			
				newString.append(buffer[i]);
			}
			return newString.toString();
		}
		else
			return s;
	}

	/**
	 * Replaces special HTML character "&" with its escape character "&amp;", 
	 * used usually for hidden field values.
	 * @param <code>String</code> s
	 * @return <code>String</code> the html - encoded string
	 * @author NTTDATA matobaa@nttdata.co.jp
	 * @version 20000927
	 */
	public static String htmlEncodeForHidden(String s){
		if(s != null){
			char[] buffer = s.toCharArray();
			StringBuffer newString = new StringBuffer(buffer.length + 30);
			for(int i=0; i<buffer.length; i++){
				if(buffer[i]=='&') {newString.append("&amp;");} else
				newString.append(buffer[i]);
			}
			return newString.toString();
		} 
		else
			return s;
	}
	
	/**
	 * Replaces special HTML characters "<" and ">" with its escape character "&lt;" and "&gt;", 
	 * used usually for text area values.
	 * @param <code>String</code> s
	 * @return <code>String</code> the html - encoded string
	 */
	public static String htmlEncodeForTextarea(String s){
		if(s != null){
			char[] buffer = s.toCharArray();
			StringBuffer newString = new StringBuffer(buffer.length+30);
			for(int i=0; i<buffer.length; i++){
				if(buffer[i]=='<') {newString.append("&lt;");} else
				if(buffer[i]=='>') {newString.append("&gt;");} else
				newString.append(buffer[i]);
			}
			return newString.toString();
		} else
			return s;
	}


    /**
     * getByteLength()
     * This method will return length of a string in bytes after encoding
     * using the ENCODING specified.
     *
     * @param astrTmp - String of whose length is to be determined.
     *
     * @return int Length of the string being passed.
     * @version 20001013
     */
	public static int getByteLength( String astrTmp ) throws java.io.UnsupportedEncodingException
    {
        return astrTmp.getBytes( ENCODING ).length;
	}


     /**
     * checkLength()
     * This method takes return true in case length of the string being passed
     * is equal to the reqired length (passed as second parameter).
     *
     * @param astrTmp - String of whose length is to be checked.
     * @param int - length to compare with.
     * @return boolean - Returns <code>true</code> in case length of string is
     *                      equal to the second argument passed else returns <code>false</code>
     * @author
     * @version 20001013
     */
	public static boolean checkLength(String astrTmp, int nLength) throws java.io.UnsupportedEncodingException
    {
		return getByteLength( astrTmp ) == nLength;

    }

	/**
	 * This method checks for either less than / equals or greater than for String passed
	 * against the length supplied as per the operation id.
     *
     * @param <code> String </code> astrAttrib, whose length is to be checked.
     * @param <code> int </code> nLength,length to compare with.
     * @param <code> int </code> nOperator,operation id.
     * @return <code> boolean </code>
	 *
     * @author NIITian
     * @version 20001110
     */
	public static boolean checkLength(String astrAttrib, int nLength, int nOperator)
							throws java.io.UnsupportedEncodingException {
		boolean bResult = true;

		switch(nOperator) {

			case 0 : bResult = (getByteLength( astrAttrib ) < nLength );
				break;

			case 1 : bResult = (getByteLength( astrAttrib ) == nLength || getByteLength( astrAttrib ) < nLength);
				break;

			case 2 : bResult = getByteLength( astrAttrib ) == nLength;
				break;

			case 3 : bResult = getByteLength( astrAttrib ) > nLength;
				break;

			case 4 : bResult = (getByteLength( astrAttrib ) == nLength || getByteLength( astrAttrib ) > nLength);
				break;
			case 5 :
				break;	
		}
		return bResult;
	}

   /**
    * Replaces special HTML characters ('<','>', '"', '&') with their escape characters, except for new line.
    * @param <code>String</code> s
    * @return <code>String</code> the html - encoded string
    */    
    public static String forHidden(String s)
    {
		  char[] buffer = s.toCharArray();
		  StringBuffer newString = new StringBuffer(buffer.length+30);
		  for(int i=0; i<buffer.length; i++){
			  if(buffer[i]=='<') {newString.append("&lt;");} else
			  if(buffer[i]=='>') {newString.append("&gt;");} else
			  if(buffer[i]=='"') {newString.append("&quot;");} else
			  if(buffer[i]=='&') {newString.append("&amp;");} else
			  newString.append(buffer[i]);
		  }
		  return newString.toString();
    }

	/** 
	 * Extract tokens as a String[].
	 * Uses java.util.StringTokenizer to extract the strings using the delimiter
	 * and to populate the string array.
	 * <br>e.g.<br>
	 * astr - "niit/admin/project"<br>
	 * astrDelim - "/"<br>
	 * result string array - {"niit","admin","project"}<br>
	 *
	 * @param astr String String containing tokens
	 * @param astrDelim String String containing delimiter
	 * @return String[]
	 */
	 public static String[] extractTokens( String astr, String astrDelim )
	 {
		if (astr != null)
		{
			StringTokenizer sToken = new StringTokenizer(astr,astrDelim);
			int nLenArr = sToken.countTokens();

			String[] strArrRet = new String[nLenArr];
			for (int i = 0;i < nLenArr ; i++ )
			{
				strArrRet[i] = sToken.nextToken();
			}
			return strArrRet;
		}
		return null;
	 }
	 
	 /** 
	 * Extract tokens as a String[].
	 * Uses java.util.StringTokenizer to extract the strings using the delimiter
	 * and to populate the string array. 
	 * This is to be used (and not extractTokens) when even the spaces in the string being passed 
	 * are not to be ignored, assigns a blank("") value to such an element when added in the populated array
	 * <br>e.g.<br>
	 * astr - "niit/admin//project"<br>
	 * astrDelim - "/"<br>
	 * result string array - {"niit","admin", "", "project"}<br>
	 *
	 * @param astr String String containing tokens
	 * @param astrDelim String String containing delimiter
	 * @return String[]
	 */
	public static String[] extractTokensWithSpaces( String astr, String astrDelim )
	 {
		 if ((astr==null)||(astrDelim==null))
			return null;
		 int nEndIndex = -1;
		 int nElements = 0;
  		 int nCtr = 0;
		 String strElement = null;
		 int nDelimLength = astrDelim.length();
 		 int nStartIndex = -(nDelimLength);		 
		 int nIndex = -(nDelimLength);
		 String strLastValue = null;

		 do 
		 {
			nIndex = astr.indexOf(astrDelim,nIndex+nDelimLength);
			if (nIndex!=-1)
			{
					nElements++;
			}
		 }
		 while (nIndex!=-1);
		String[] strArrRet = new String[nElements+1];
		for (int i=0;i<strArrRet.length;i++ )
		{
			strArrRet[i]=BLANK;
		}

		do 
		{			
			nEndIndex = astr.indexOf(astrDelim,nStartIndex+nDelimLength);
			if (nEndIndex!=-1)
			{
				strElement = astr.substring(nStartIndex+nDelimLength,nEndIndex);
				if (!((strElement==null)||(strElement.equals(""))||(strElement.length()==0)))
				{
					strArrRet[nCtr] = strElement;
				}
				else{ //For null values, return blank
					strArrRet[nCtr] = BLANK;
				}
				nCtr++;
			}
			else
			{
				strLastValue = astr.substring(nStartIndex+nDelimLength,astr.length());
				if ((strLastValue!=null)&&!(strLastValue.equals("")))
					strArrRet[nCtr] = strLastValue;
			}

			nStartIndex = nEndIndex;
		 }
		 while (nEndIndex!=-1);
		 return strArrRet;
	 }
	 
	 /** 
	 * Replaces all the single quote occurences within the Source string and 
	 * replace it with two single quotes 
	 *
	 * @param <code>String</code> astrSource
	 * @return String
	 */

	public static String negateSingleQuote(String astrSource)
	{
		String strReplace = "\'";
		String strReplaceWith = "\'\'";
		if (astrSource==null)
			return null;
		else
			return replace(astrSource,strReplace,strReplaceWith);
	}

	/** Converts a String[] to a String, with each
	 *  element separated by a delimiter. Internally it calls
	 *  ArrayToString( astrArr, "," ), i.e., the default delimiter is a comma.
	 *  <br>e.g.<br>
	 *  astrArr - {"niit","admin","project"}<br>
	 *  result string - "niit,admin,project"
	 *
	 *  @param astrArr String[] String array
	 *  @return String
	 */
	 public static String arrayToString( String[] astrArr )
	 {
		return arrayToString( astrArr, "," );
	 }

	 /** Converts a String[] to a String, with each  element
	  *  separated by a delimiter.
	  *  <br>e.g.<br> 
	  *  astrArr - {"niit","admin","project"}<br>
	  *  astrDelim - "/"<br>
	  *  result string - "niit/admin/project"<br>
	  *
	  *
	  *  @param astrArr String[] String array
	  *  @param astrDelim String String containing delimiter
	  *  @return String
	  */
	  public static String arrayToString( String[] astrArr, String astrDelim )
	  {
		StringBuffer strBufferRetVal = new StringBuffer();
		int nArrLen = astrArr.length;
		for (int i = 0;i < nArrLen ; i++ )
		{
			strBufferRetVal.append(astrArr[i]);
			if (i < nArrLen -1)
			{
				strBufferRetVal.append(astrDelim);
			}
		}
		return strBufferRetVal.toString();
	  }

	/**
	 * Replace all occurrences of <i>astrReplaceMe</i> in <i>astrOriginal</i> 
	 * with <i>astrReplacement</i>.
	 * <br>e.g.<br><br>
	 * astrOriginal - "niitADMINprojectADMIN"<br>
	 * astrReplaceMe - "ADMIN"<br>
	 * astrReplacement - "OEM"<br>
	 * result string - "niitOEMprojectOEM"<br>
	 *
	 * @param <code>String</code> astrOriginal Original string
	 * @param <code>String</code> astrReplaceMe String to be replaced
	 * @param <code>String</code> astrReplacement Replacement string
	 * @return <code>String</code> result of replace operation
	 */
	public static String replace( String astrOriginal, String astrReplaceMe,  
		String astrReplacement ){
		
		StringBuffer strBuffer = null;
		// Validate input args:
		if( astrOriginal == null || astrReplaceMe == null || 
				astrReplacement == null ) return astrOriginal;

		int nFromLen = astrReplaceMe.length();
		int nToLen = astrReplacement.length();
		
		String strNewLine = astrOriginal;
		int nPos1 = 0, nPos2 = 0;
		//Iteratively, find each occurrence of astrReplaceMe in strNewLine
		while ((nPos2 = strNewLine.indexOf(astrReplaceMe, nPos1)) >= 0)
		{
			strBuffer = new StringBuffer(strNewLine);
			//Replace occurrence of astrReplaceMe with astrReplacement.
			strNewLine = strBuffer.replace( nPos2, nPos2 + nFromLen, 
					astrReplacement).toString();
			//Reposition cursor for next search operation
			nPos1 = nPos2 + nToLen;
		}
		//Return the modified string.
		return strNewLine;
	}

	  /**
	 * Conserve all special characters within a string retaining there
	 * actual meaning  e.g a "&" is treated as a "&" 
	 * 
	 * <br>e.g.<br>
	 * astr - "[{niit,oem&admin}]"<br>
	 * result string - "\[\{niit\,oem\&admin\}\]"<br>
	 *
	 * The browser can read these special characters
	 * and display them with their actual meanings
	 *
	 *
	 *
	 * @param <code>String</code> astr Original string
	 * @return <code>String</code> Result string
	 */
     public static String specialCharsEncode(String astr){
      String strOutput = null;
      StringBuffer newString = new StringBuffer();

      if(astr!=null && !astr.equals("")){
        char[] buffer = astr.toCharArray();

          for(int i=0; i<buffer.length; i++){
            switch(buffer[i]){

			  // Special English characters
              case ',':	newString.append("\\,");
                                      break;
              case '&':	newString.append("\\&");
                                      break;
              case '?':	newString.append("\\?");
                                      break;
              case '}':	newString.append("\\}");
                                      break;
              case '{':	newString.append("\\{");
                                      break;
              case '\\':newString.append("\\\\");
                                      break;
              case ')':	newString.append("\\)");
                                      break;
              case '(':	newString.append("\\(");
                                      break;
              case ']':	newString.append("\\]");
                                      break;
              case '[':	newString.append("\\[");
                                      break;
              case '-':	newString.append("\\-");
                                      break;
              case ';':	newString.append("\\;");
                                      break;
              case '~':	newString.append("\\~");
                                      break;
              case '|':	newString.append("\\|");
                                      break;
              case '$':	newString.append("\\$");
                                      break;
              case '!':	newString.append("\\!");
                                      break;
              case '>':	newString.append("\\>");
                                      break;
              case '*':	newString.append("\\*");
                                      break;
              case '%':	newString.append("\\%");
                                      break;
              case '_':	newString.append("\\_");
                                      break;
              case '=':	newString.append("\\=");
                                      break;
               default:	newString.append(buffer[i]);
            }
          }

        strOutput = newString.toString();

		// Checking for presence of single quote (single byte)
        int index = -1;
        newString = new StringBuffer("");
        while((index = strOutput.indexOf("'")) != -1){
            newString.append(strOutput.substring(0,index));
            newString.append("'");
            newString.append(strOutput.substring(index,index+1));
            strOutput = strOutput.substring(index+1);
        }
        strOutput = newString.toString() + strOutput;

      }
      return strOutput;

    }

	/**
	 * Replace all occurrences of &quot; in <i>astrOriginal</i> 
	 * with HTML quote string.	 
	 *
	 * @param <code>String</code> astrOriginal Original string	 
	 * @return <code>String</code> replaced string
	 */
	public static String replaceForHTMLDisplay(String astrOriginal)
	{
		if (astrOriginal != null){
			char[] buffer = astrOriginal.toCharArray();
			StringBuffer newString = new StringBuffer(buffer.length+30);
			for(int i=0; i<buffer.length; i++){
				if(buffer[i]=='"') {
					newString.append("&quot;");
				} else
				newString.append(buffer[i]);
		}
			return newString.toString();
		}else{
			return null;
		}
	}
	

	/**
	 * Replace all occurrences of '\\'; in <i>astrOriginal</i> 
	 * with HTML '\\\\' string.	 
	 *
	 * @param <code>String</code> astrOriginal Original string	 
	 * @return <code>String</code> replaced string
	 */
	public static String replaceForParameters(String astrOriginal)
	{

		String strStore = "";
		String strTemp = "";
		String strReturn = "";
		strStore = astrOriginal;
		strTemp = astrOriginal;

		if (astrOriginal != null){
			Debug.log("strTemp : "+strReturn);
			while(strTemp.endsWith("\\")){
				strStore = strStore.substring(0,strStore.lastIndexOf("\\"));
				strTemp = strStore;

				strReturn = strStore + "\\\\";

			}

			return strReturn;
		}else{
			return null;
		}		
	}	

	/**
	 * Extracts a 2DArray from a "~" separated String.
	 * <b>Note:</b>If any value is null/blank, "" will be returned.
	 *
	 * @param <code>String</code> astrData Input String
	 * @param <code>int</code> anNoOfCols Number of Columns
	 * @return <code>String[][]</code> 2D String array containing [rows][cols] values.
	 *
	 *
	 * Usage: extract2DArrayFromString("1~2~3~4~~6", 2) will return a 2D String array
	 * [0][0] =	"1"		[0][1] = "2"
	 * [1][0] =	"3"		[1][1] = "4"
	 * [2][0] =	""		[2][1] = "6"
	 */

    public static String[][] extract2DArrayFromString(String astrData,int anNoOfCols )
	{
		// convert the string into a array.
		String[] strArrayOfString = extractTokensWithSpaces(astrData,SEPARATOR);
		
		//calculate the size of 2D array.
		int nNoOfRows = strArrayOfString.length/anNoOfCols;
		
		String [][] strArrayData = new String[nNoOfRows][anNoOfCols];

		// now we divide  this array according to the no. of columns.
		int nArrayIndex = 0;
		for(int i=0;i<nNoOfRows; i++)
		{
			System.arraycopy(strArrayOfString,nArrayIndex,strArrayData[i],0,anNoOfCols);

			nArrayIndex +=anNoOfCols;
		}
		
		return strArrayData;
	}
	
	/**
     * This method converts a 2D Object Array in to a string object. 
	 * <b>Note:</b>If any value is blank, "" will be addeded to string.
	 * Uses:	
	 *
	 * -----------------
	 *	2D Object Array
	 * -----------------	
	 * {
	 *	  {"1","2","3"},
	 *	  {" ","5","6"},		= 1~2~3~~5~6~7~~9
	 *	  {"7","","9"}
	 * }
	 *
     * @return String (every element is separated by "~" char.)
	 * @args String[][] <code>aobjArrayData</code>
	 * 
	 */
	public static String convert2DArrayToString(Object[][] aobjArrayData)
	{
		// don't remove any column so pass -1 as column no.
		return convert2DArrayToStringWithoutFlag(aobjArrayData,-1);
	}


	/**
     * This method converts a 2D Object Array in to a string object with out
	 * elements of flag column.
	 * <b>Note:</b>If any value is blank, "" will be addeded to string.
	 * Uses:	
	 *
	 * ---------------------
	 *	  2D String Array
	 * --------------------	
	 * {
	 *	  {"ADD","1","2","3"},
	 *	  {"DEL"," ","5","6"},	and nColWithFlag = 0	===> 1~2~3~~5~6~7~~9
	 *	  {"DEL","7","","9"}
	 * }
	 *
     * @return String (every element is separated by "~" char.)
	 * @args String[][] <code>aobjArrayData</code>
	 * 
	 */
	public static String convert2DArrayToStringWithoutFlag(Object[][] aobjArrayData,int nColWithFlag)
	{
		String strFinalString =null;
		StringBuffer sbString = new StringBuffer();
		int nInnerArrayLength ;
		String strValue; 

		int nArrayLength = aobjArrayData.length;
		for(int i=0;i<nArrayLength; i++)
		{
			nInnerArrayLength = aobjArrayData[i].length;
			for(int j=0;j<nInnerArrayLength; j++)
			{
				if(j != nColWithFlag) //[i][nColWithFlag] element is a flag. 
				{
					if(aobjArrayData[i][j] != null) // check for null.
					{
						strValue = aobjArrayData[i][j].toString();
						if(!strValue.equals(""))
						{
							sbString.append(strValue);			
						}
					}
					sbString.append(SEPARATOR);
				}
			}
		}
		
		String strNowString = sbString.toString();
		
		//remove the last char.
		if(strNowString.length()>1)
		{
			strFinalString = strNowString.substring(0,strNowString.length()-1);
		}
		return strFinalString;
	}

	/**
	/* Convert the Input String into Thai String			*/
	/* @param <code>String</code> astrData Original string	*/ 
	/* @return <code>String</code> Converted Thai String	*/
	
	public static String getThai(String astrData)
	{
		String lstrTemp = null;
		try{
			lstrTemp = new String(astrData.getBytes(SOURCE_ENCODING),MS);
		}catch(Exception e){}
		return lstrTemp;
	}

	/**
    * This method check the validity of Date in any format
    * @param String date Value
    * @return String date format
    * @return boolean - true/false
	 * @see replace
	 */
	public static boolean  isDateValid ( String aDateValue , String aDateFmt ) 
	{

		Calendar lcal = Calendar.getInstance(TimeZone.getDefault());

		int[]  lmonthMaxday = {31,28,31,30,31,30,31,31,30,31,30,31 } ;
		HashMap hsm = new HashMap();
		hsm.put("JAN","1");
		hsm.put("FEB","2");
		hsm.put("MAR","3");
		hsm.put("APR","4");
		hsm.put("MAY","5");
		hsm.put("JUN","6");
		hsm.put("JUL","7");
		hsm.put("AUG","8");
		hsm.put("SEP","9");
		hsm.put("OCT","10");
		hsm.put("NOV","11");
		hsm.put("DEC","12");

		String lDateValue   = null  ; 
		String lDateFmt     = null  ;

		String lmonMonth = null ;
		int    lmmMonth  = 0    ;
		int    lyear     = 0    ;
		int    lday      = 0    ;
		int    lhour     = 0    ;
		int    lminute   = 0    ;
		int    lsecond   = 0    ;
		
		int    lyyPos    = 0 ;
		int    lmmPos    = 0 ;
		int    lddPos    = 0 ;
		int    lhhPos    = 0 ;
		int    lmiPos    = 0 ;
		int    lssPos    = 0 ;
		int    lmmLen    = 0 ;
		int    lyyLen    = 0 ;

		/* If date is null, return false , Invalid date */ 
		lDateValue = aDateValue.trim() ;
		if ( lDateValue.length() == 0  )
		   return false ;

		/* Remove all separator from format mask */
		lDateFmt = replace( aDateFmt, "12" , "" ) ;
		lDateFmt = replace( aDateFmt, "24" , "" ) ;

		/* If length of Date & Format not same then Invalid date   */
		if ( lDateValue.length() !=  lDateFmt.length() )
		   return false ;

		lDateFmt   = replace(lDateFmt  ,"/", "") ;
		lDateValue = replace(aDateValue,"/", "") ;
		/* If length of Date & Format not same then Invalid date   */
		if ( lDateValue.length() !=  lDateFmt.length() )
		   return false ;

		lDateFmt   = replace(lDateFmt  ,":", "") ;
		lDateValue = replace(lDateValue,":", "") ;
		/* If length of Date & Format not same then Invalid date   */
		if ( lDateValue.length() !=  lDateFmt.length() )
		   return false ;

		lDateFmt   = replace(lDateFmt  ," ", "") ;
		lDateValue = replace(lDateValue," ", "") ;
		if ( lDateValue.length() !=  lDateFmt.length() )
		   return false ;

		lDateFmt   = replace(lDateFmt  ,".", "") ;
		lDateValue = replace(lDateValue,".", "") ;
		if ( lDateValue.length() !=  lDateFmt.length() )
		   return false ;

		lDateFmt   = replace(lDateFmt  ,"-", "") ;
		lDateValue = replace(lDateValue,"-", "") ;
		/* If length of Date & Format not same then Invalid date   */
		if ( lDateValue.length() !=  lDateFmt.length() )
		   return false ;
		
		/* Calculate the postion of Year/Month/Day/Hour/Minute/Second */  
		lyyPos = lDateFmt.indexOf("YYYY") ;
		lyyLen = 4  ;

		if ( lyyPos == -1 )
		{
			lyyPos = lDateFmt.indexOf("YY") ;
			lyyLen = 2  ;
		}
		
		lmmPos = lDateFmt.indexOf("MON") ;
		lmmLen = 3  ;

		if ( lmmPos == -1 )
		{
			lmmPos = lDateFmt.indexOf("MM") ;
			lmmLen = 2  ;
		}

		lddPos = lDateFmt.indexOf("DD") ;

		lhhPos = lDateFmt.indexOf("HH") ;

		lmiPos = lDateFmt.indexOf("MI") ;

		lssPos = lDateFmt.indexOf("SS") ;

		try
		{
			if ( lyyPos > -1 )
			{
				lyear = Integer.parseInt(
						lDateValue.substring( lyyPos, lyyPos + lyyLen )) ;
				if ( lyyLen == 2  )
				{
					lyear = ((int) lcal.get(Calendar.YEAR)/100 )*100 + lyear ;
				}
				//System.out.println(" lyear " + lyear ) ;
				/* Perform Year validation */
				if ( ! ( ( lyear > 999 ) && ( lyear < 4048 ) ) )
				{
					//System.out.println(" Year Faiure " ) ;
					return false ;
				}
			}else
			{
				lyear = lcal.get(Calendar.YEAR) ;
			}

			if ( lmmPos > -1 && lmmLen == 2  )
			{
				lmmMonth = Integer.parseInt(
						   lDateValue.substring( lmmPos, lmmPos + lmmLen ));
				/* Perform Month validation */
				if ( ! ( ( lmmMonth > 0 ) && ( lmmMonth < 13 ) ) )
				{
					 //System.out.println(" Month Faiure " ) ;
					 return false ;
				}
			}else if ( ( lmmPos > -1 ) && ( lmmLen == 3  ) )
			{
				lmonMonth = lDateValue.substring( lmmPos, lmmPos + lmmLen ) ;
				lmmMonth  =  Integer.parseInt( 
							 (String)hsm.get(lmonMonth.toUpperCase())) ;

				/* Perform Month validation */
				if ( ! ( ( lmmMonth > 0 ) && ( lmmMonth < 13 ) ) )
				{
					 //System.out.println(" Month ( Mon ) Faiure " ) ;
					 return false ;
				}
			}else
			{
				lmmMonth =  lcal.get(Calendar.MONTH) ;

			}
			
			/* Check Leap year if yes then set max day of FEB as 29 days */
			if ( lyear % 4 == 0 && lyear % 100 != 0 || lyear % 400 == 0 )
			{
				lmonthMaxday[1] = 29  ;
			}

			/* Perform day validation */    
			if ( lddPos > -1 )
			{
				lday = Integer.parseInt(
						lDateValue.substring( lddPos, lddPos + 2 )) ;

				/* Perform Month validation */
				if ( ! ( ( lday > 0 ) && ( lday <= lmonthMaxday[lmmMonth-1] ) ) )
				{
					//System.out.println(" Day Faiure " +lday) ;
					return false ;
				}
			}
			if ( lhhPos > -1 )
			{
				lhour = Integer.parseInt(
						lDateValue.substring( lhhPos, lhhPos + 2 )) ;
				/* Perform Hour validation */
				if (! ( ( lhour >= 0 ) && ( lhour < 24 ) ) )
				{
					//System.out.println(" Hour Faiure " ) ;
					return false ;
				}
			}
			if ( lmiPos > -1 )
			{
				lminute = Integer.parseInt(
						lDateValue.substring( lmiPos, lmiPos + 2 )) ;
				/* Perform Month validation */
				if (! ( ( lminute >= 0 ) && ( lminute < 60 ) ) )
				{
					//System.out.println(" Min Faiure " ) ;
					return false ;
				}
			}
			if ( lssPos > -1 )
			{
				lsecond = Integer.parseInt(
						lDateValue.substring( lssPos, lssPos + 2 )) ;
				/* Perform Month validation */
				if (! ( ( lsecond >= 0 ) && ( lsecond < 60 ) ) )
				{
					//System.out.println(" Second Faiure " ) ;
					return false ;
				}
			}
			
		}
		catch ( Exception e )
		{
			//System.out.println("Exception ...");
			return false  ;
		}

		return true  ;

	} /* End of isDateValid */


	/**
        * This method gives the array after removing the last element from the array passed
        * @param String[] 1D String Array
        * @return String[] 1D String Array with length one less from the Parameter Array's Length
	 * @see none
	 */
	public static String[]  getArrayAftRemLastElemnt( String[] astrarrParam) 
	{
        String[] lstrarrNewArray = null;
        if(astrarrParam == null) {
            return lstrarrNewArray;
        }
        lstrarrNewArray = new String[astrarrParam.length - 1];
        for(int i=0;i<astrarrParam.length - 1; i++) {
            lstrarrNewArray[i] = astrarrParam[i];
        }
        return lstrarrNewArray;
    }
    
    /*
     * @param : String of Error(s) & Error Data(s) in a format as below
     * (Each error code+data set separated by Tilde and each element inside
     * an error code+data set will be separated by Hash) ex. below:
     *                      ErrorCode1~
     *                      ErrorCode2#ErrorData2~
     *                      ErrorCode3#ErrorData3.1#ErrorData3.2
     *                      
     * @return : MessageHolder Object                      
     */
    public static MessageHolder createMsgHolder(String astrErrors){
        Debug.logFramework("Original astrErrors="+astrErrors);
        if(astrErrors.indexOf(GlobalConstants.SQL_ERR_PREFIX) > 0){
            astrErrors = astrErrors.substring(
                            astrErrors.indexOf(GlobalConstants.SQL_ERR_PREFIX) + GlobalConstants.SQL_ERR_PREFIX.length(),
                            astrErrors.indexOf(GlobalConstants.SQL_ERR_POSTFIX));
        }
        Debug.logFramework("Modified astrErrors="+astrErrors);
        MessageHolder lmsgHolder = new MessageHolder();
        String[] arrErrors = extractTokensWithSpaces(astrErrors, GlobalConstants.TILDE);
        String lstrErrorId = null;
        String[] larrErrorData = null;
        for(int i=0; i < arrErrors.length; i++){
            //if the error code have some related error data for place holder
            if(arrErrors[i].indexOf(GlobalConstants.HASH) > 0){
                lstrErrorId = arrErrors[i].substring(0,arrErrors[i].indexOf(GlobalConstants.HASH));
                larrErrorData = extractTokensWithSpaces(arrErrors[i].substring(arrErrors[i].indexOf(GlobalConstants.HASH)+1),GlobalConstants.HASH);
                lmsgHolder.add(lstrErrorId, larrErrorData, MessageHolder.ERROR_INT);
            }else{//No related error data for the error code
                lstrErrorId = arrErrors[i];
                lmsgHolder.add(lstrErrorId, null, MessageHolder.ERROR_INT);
            }
            System.out.println("lstrErrorId="+lstrErrorId+"-larrErrorData="+larrErrorData);            
        }
        
        return lmsgHolder;
    }
}


/* Modification History
 *
 * 2002-01: Modified for OEM
 * 2005-10: Moified for Tops
 *
 */
 
/*
  Modification History;

  Modified by	Date 		Description:
  ----------- 	---  		-----------	
  Amit Rathi	10/12/2001  extract2DArrayFromString() added, 
							extractTokensWithSpaces() modified to return "" for null/blank values.

  Amit Rathi	12/11/01	convert2DArrayToString() added.
  
  Amit Rathi	12/17/01	convert2DArrayToStringWithoutFlag() added.
*/