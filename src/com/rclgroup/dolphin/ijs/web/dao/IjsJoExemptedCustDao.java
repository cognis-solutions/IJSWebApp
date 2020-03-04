package com.rclgroup.dolphin.ijs.web.dao;

import com.rclgroup.dolphin.ijs.web.entity.IjsJoExemptedCustSearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.vo.IjsJoExemptedCustSearchParamVO;

import com.rclgroup.dolphin.ijs.web.vo.IjsJoExemptedCustSearchVO;

import java.util.List;

public interface IjsJoExemptedCustDao {
    
    public List<IjsJoExemptedCustSearchDTO> findAllCust(String userId, 
                                                     IjsJoExemptedCustSearchParamVO searchParam) throws IJSException;

    public String addEditCust(IjsJoExemptedCustSearchParamVO searchVo, 
                             String userId, String action) throws IJSException;
                             
    public String deleteCust(List<IjsJoExemptedCustSearchParamVO> searchVo, 
                             String userId, String action) throws IJSException;

    


}
