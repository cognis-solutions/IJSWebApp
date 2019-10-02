package com.rclgroup.dolphin.ijs.web.service;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.dao.impl.IjsJoExemptedCustJdbcDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsJoExemptedCustSearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsJoExemptedCustUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoExemptedCustSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoExemptedCustSearchVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IjsJoExemptedCustSvc {
    IjsJoExemptedCustJdbcDao ijsJoExemptedCustJdbcDao = null;

    public IjsJoExemptedCustSvc(IjsJoExemptedCustJdbcDao ijsMaintainJOSearchDao) {
        this.ijsJoExemptedCustJdbcDao = ijsMaintainJOSearchDao;
    }

    public IjsJoExemptedCustUIM findAllCust(IjsJoExemptedCustSearchParamVO mainatainJoParam, 
                                            String userInfo) throws IJSException {

        //        IjsProcessJOBkgBLSearchParamVO bkgBLParamVO = 
        //            (IjsProcessJOBkgBLSearchParamVO)bkgBLParam.getProcessJoParam();

        List<IjsJoExemptedCustSearchDTO> lstBkgBL = 
            ijsJoExemptedCustJdbcDao.findAllCust(userInfo, mainatainJoParam);
        return transformDtoToUim(lstBkgBL);
    }

    private IjsJoExemptedCustUIM transformDtoToUim(List<IjsJoExemptedCustSearchDTO> lstMaintainJo) {
        //TO-DO transform Data Object to VO 
        IjsJoExemptedCustUIM ijsJoExemptedCustUIM = null;
        IjsSearchResult<IjsJoExemptedCustSearchVO> searchResult = null;
        List<IjsJoExemptedCustSearchVO> lstSearchResult = null;

        if (lstMaintainJo != null) {
            lstSearchResult = new ArrayList<IjsJoExemptedCustSearchVO>();
            ijsJoExemptedCustUIM = new IjsJoExemptedCustUIM();
            searchResult = new IjsSearchResult<IjsJoExemptedCustSearchVO>();
            for (IjsJoExemptedCustSearchDTO maintainJo: lstMaintainJo) {
                IjsJoExemptedCustSearchVO exemptedCustSearchVO = 
                    new IjsJoExemptedCustSearchVO();
                    exemptedCustSearchVO.setCustId(maintainJo.getCustId()); //TODO
                 exemptedCustSearchVO.setCustName(maintainJo.getCustName());
                exemptedCustSearchVO.setCustType(maintainJo.getCustType());
                exemptedCustSearchVO.setRemark(maintainJo.getRemark());
                exemptedCustSearchVO.setStatus(maintainJo.getStatus());


                lstSearchResult.add(exemptedCustSearchVO);
            }
            searchResult.setResult(lstSearchResult);
            ijsJoExemptedCustUIM.setSearchResult(searchResult);
        }
        return ijsJoExemptedCustUIM;
    }

    public IjsJoExemptedCustUIM addEditJO(IjsJoExemptedCustSearchParamVO ijsJoExemptedCustSearchVO, 
                                       String userInfo, 
                                       String action) throws IJSException {
        String lsterrorCd = 
            ijsJoExemptedCustJdbcDao.addEditCust(ijsJoExemptedCustSearchVO, 
                                                 userInfo, action);
        return transform(lsterrorCd, action);
    }

    public IjsJoExemptedCustUIM deleteCust(List<IjsJoExemptedCustSearchParamVO> ijsJoExemptedCustSearchVO, 
                                         String userInfo, 
                                         String action) throws IJSException {

        String lsterrorCd = 
            ijsJoExemptedCustJdbcDao.deleteCust(ijsJoExemptedCustSearchVO, 
                                                userInfo, action);

        return transform(lsterrorCd, action);
    }


    private IjsJoExemptedCustUIM transform(String result, String action) {
        IjsJoExemptedCustUIM ijsJoExemptedCustUIM = new IjsJoExemptedCustUIM();
        ijsJoExemptedCustUIM.setErrorCode(result);
        ijsJoExemptedCustUIM.setAction(action);
        return ijsJoExemptedCustUIM;

    }

}
