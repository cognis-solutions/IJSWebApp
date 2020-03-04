package com.rclgroup.dolphin.ijs.web.dao;

import com.rclgroup.dolphin.ijs.web.entity.IjsJoEnquiryDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;

import com.rclgroup.dolphin.ijs.web.vo.IjsJoEnquiryParamVO;

import com.rclgroup.dolphin.ijs.web.vo.IjsJoEnquiryVO;

import java.util.List;
import java.util.Map;

public interface IjsJoEnquiryDao {
    public List<IjsJoEnquiryDTO> findContracts(String userId, 
                                                      IjsJoEnquiryParamVO searchParam) throws IJSException;

    public String saveOrUpdateContract(IjsJoEnquiryVO ijsContractVO, 
                                       String userInfo, 
                                       String action) throws IJSException;

    public Map<String, String> deleteContract(List<String> contractsList, 
                                              String userInfo) throws IJSException;


}
