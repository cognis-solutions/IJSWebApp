package com.rclgroup.dolphin.ijs.web.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IjsPaginationUtil {
    private static int NO_OF_RECORDS_PER_PAGE = 10;
    private static int NO_OF_RECORDS_PER_REQUEST = 200;
    public static Map<Integer, List<?>>generatePaginationChunks(List<?> resultList, int pageNo) {
        //generateStartAndEndPageNumbers(pageNo);
        return null;
    }

    private static void generateStartAndEndPageNumbers(int pageNo, IjsPaginationInfo info) {
        //int end = ((pageNo * NO_OF_RECORDS_PER_PAGE)/NO_OF_RECORDS_PER_REQUEST + 1) * 100;
         int end = pageNo * NO_OF_RECORDS_PER_PAGE;
      //  int start = end - 99;
        int start = end - 9;
        info.setRecordStart(start);
        info.setRecordEnd(end);
    }

    public static IjsPaginationInfo populatingPaginationInfo(int pageNo) {
        IjsPaginationInfo info = new IjsPaginationInfo();
        //int end = ((pageNo * NO_OF_RECORDS_PER_PAGE)/NO_OF_RECORDS_PER_REQUEST + 1) * 100;
         int end = pageNo * NO_OF_RECORDS_PER_PAGE;
        //  int start = end - 99;
        int start = end - 9;
        info.setRecordStart(start);
        info.setRecordEnd(end);
        return info;
    }
    
    public static int getRecordStart(int pageNo,int noOfRecPerPage) {
    if(noOfRecPerPage>0){
        return pageNo * noOfRecPerPage-(noOfRecPerPage-1);
    }else{
        return pageNo * NO_OF_RECORDS_PER_PAGE-(NO_OF_RECORDS_PER_PAGE-1); 
    }
         
    }
    
    public static int getRecordEnd(int pageNo,int noOfRecPerPage,int totalRecords) {
    if(noOfRecPerPage>0){
    	if(pageNo * noOfRecPerPage<=totalRecords){
    		return pageNo * noOfRecPerPage;
    	}else{
    		return totalRecords;
    	}
        
    }else{
    	if(pageNo * NO_OF_RECORDS_PER_PAGE<=totalRecords){
    		return pageNo * NO_OF_RECORDS_PER_PAGE;
    	}else{
    		return totalRecords;
    	}
    }
         
    }
    
//    public static void main (String args[]) {
//        System.out.println("result is " + ((11 * NO_OF_RECORDS_PER_PAGE)/NO_OF_RECORDS_PER_REQUEST + 1) * 100);
//    }

    public static List<?> populateResults(IjsPaginationInfo sessionInfo, int pageNo) {
        List<?> results = sessionInfo.getResults();
        int startNumberToShow = pageNo * NO_OF_RECORDS_PER_PAGE + 1;
        if (sessionInfo.getRecordStart() <= startNumberToShow && 
            sessionInfo.getRecordEnd() >= startNumberToShow) {
            int subListStart = ((pageNo%NO_OF_RECORDS_PER_PAGE == 0 ? 1 : pageNo%NO_OF_RECORDS_PER_PAGE) - 1) * NO_OF_RECORDS_PER_PAGE;
            int subListEnd = subListStart + (NO_OF_RECORDS_PER_PAGE);
            try {
                return results.subList(subListStart , subListEnd);        
            } catch (IndexOutOfBoundsException iob) {
                if (results.size() > subListStart) {
                    return results.subList(subListStart,results.size());
                }
                return new ArrayList<Object>();
            }
        }
        return null;
    }
}
