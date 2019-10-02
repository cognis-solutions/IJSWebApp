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
package com.rclgroup.dolphin.ijs.web.service.jo;

import com.rclgroup.dolphin.ijs.web.common.IjsJOSearchResults;
import com.rclgroup.dolphin.ijs.web.common.IjsPaginationUtil;
import com.rclgroup.dolphin.ijs.web.dao.jo.IjsJOBookingBLSearchDao;
import com.rclgroup.dolphin.ijs.web.entity.jo.IjsJOBookingBLSearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.jo.IjsJOBookingBLSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.jo.IjsJOBookingBLSearchVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class IjsJOBookingBLSearchSvc {
    private IjsJOBookingBLSearchDao ijsJOBookingBLSearchDao;
    
    public IjsJOBookingBLSearchSvc(IjsJOBookingBLSearchDao ijsJOBookingBLSearchDao) {
        this.ijsJOBookingBLSearchDao = ijsJOBookingBLSearchDao;
    }
    /**
     * searchContract method for fetching booking/bl records
     * @param ijsJOBookingBLSearchVO
     * @param userInfo
     * @throws IJSException
     */
    public IjsJOBookingBLSearchUIM searchContract(IjsJOBookingBLSearchUIM actionForm, 
                               String userInfo, HttpServletRequest request) throws IJSException{
        HttpSession session =  request.getSession(true);
        IjsJOBookingBLSearchVO searchVO = actionForm.getIjsJOBookingBLSearchVO();
        Map<Integer , List<IjsJOBookingBLSearchDTO>> pages 
            = (Map<Integer , List<IjsJOBookingBLSearchDTO>>)session.getAttribute("joOrderChunks");
        if (pages == null || pages.size()  == 0 || !searchVO.getOrderBy().equals(session.getAttribute("orderBy"))) {
    //        ijsJOBookingBLSearchDao.searchJOBookingBL(actionForm.getIjsJOBookingBLSearchVO(), userInfo);
            List<IjsJOBookingBLSearchDTO> resultList =  getMockList();
            Map<Integer , List<?>> pageChunks =  IjsPaginationUtil
                .generatePaginationChunks(resultList, searchVO.getPageNo());
//            List<IjsJOBookingBLSearchDTO>  list = (List<IjsJOBookingBLSearchDTO>)pageChunks.get(1);
            session.setAttribute("joOrderType" , "ASC");
            session.setAttribute("joOrderChunks" , pageChunks);
            
        }
        

        
        IjsJOBookingBLSearchUIM uim = new IjsJOBookingBLSearchUIM();
        uim.setAction(actionForm.getAction());
        IjsJOSearchResults<IjsJOBookingBLSearchDTO> searchResult = new IjsJOSearchResults<IjsJOBookingBLSearchDTO>();
        searchResult.setResults(getMockList());
        uim.setSearchResults(searchResult);
        return uim;
    }
    
    private List<IjsJOBookingBLSearchDTO> getMockList() {
        List<IjsJOBookingBLSearchDTO> list = new ArrayList<IjsJOBookingBLSearchDTO>();
        
        IjsJOBookingBLSearchDTO mock1 = new IjsJOBookingBLSearchDTO();
        mock1.setBookingStatus("closed");
        mock1.setBkgOrBLType("BKG");
        mock1.setBkgOrBLNumber("BJKTC16011185");
        mock1.setCntSize("20");
        mock1.setCntSplHandCount(1);
        mock1.setCntSplHandling("DG/IMDG");
        mock1.setCntType("GP");
        mock1.setDirection("Round");
        mock1.setEmptyCntAvailable(1);
        mock1.setEmptyCntInJO(1);
        mock1.setEmptyCntTotal(2);
        mock1.setEndDate("11/10/2016");
        mock1.setFromTerminal("IDJKT");
        mock1.setLadenCntAvailable(1);
        mock1.setLadenCntInJO(0);
        mock1.setLadenCntTotal(1);
        mock1.setService("AFS");
        mock1.setStartDate("11/10/2016");
        mock1.setToTerminal("THBKK");
        mock1.setTransportMode("Barge");
        mock1.setVessel("BARGE");
        mock1.setVoyage("JKT21101");
        
        
        IjsJOBookingBLSearchDTO mock2 = new IjsJOBookingBLSearchDTO();
        mock2.setBookingStatus("closed");
        mock2.setBkgOrBLType("BKG");
        mock2.setBkgOrBLNumber("BJKTC16011184");
        mock2.setCntSize("40");
        mock2.setCntSplHandCount(2);
        mock2.setCntSplHandling("DG/IMDG");
        mock2.setCntType("HC");
        mock2.setDirection("Round");
        mock2.setEmptyCntAvailable(0);
        mock2.setEmptyCntInJO(2);
        mock2.setEmptyCntTotal(2);
        mock2.setEndDate("21/10/2016");
        mock2.setFromTerminal("IDJKT");
        mock2.setLadenCntAvailable(0);
        mock2.setLadenCntInJO(2);
        mock2.setLadenCntTotal(2);
        mock2.setService("AFS");
        mock2.setStartDate("21/10/2016");
        mock2.setToTerminal("THBKK");
        mock2.setTransportMode("Barge");
        mock2.setVessel("S1B");
        mock2.setVoyage("192N");
        
        
        IjsJOBookingBLSearchDTO mock3 = new IjsJOBookingBLSearchDTO();
        mock3.setBookingStatus("closed");
        mock3.setBkgOrBLType("BKG");
        mock3.setBkgOrBLNumber("BSUBC11002437");
        mock3.setCntSize("40");
        mock3.setCntSplHandCount(2);
        mock3.setCntSplHandling("DG/IMDG");
        mock3.setCntType("HC");
        mock3.setDirection("Round");
        mock3.setEmptyCntAvailable(0);
        mock3.setEmptyCntInJO(1);
        mock3.setEmptyCntTotal(1);
        mock3.setEndDate("13/10/2016");
        mock3.setFromTerminal("IDJKT");
        mock3.setLadenCntAvailable(0);
        mock3.setLadenCntInJO(2);
        mock3.setLadenCntTotal(2);
        mock3.setService("AFS");
        mock3.setStartDate("13/10/2016");
        mock3.setToTerminal("THBKK");
        mock3.setTransportMode("Barge");
        mock3.setVessel("BARGE");
        mock3.setVoyage("JKT21101");
        
        list.add(mock1);
        list.add(mock2);
        list.add(mock3);
        return list;
    }
}
