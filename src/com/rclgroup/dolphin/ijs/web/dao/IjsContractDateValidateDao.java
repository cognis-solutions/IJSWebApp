package com.rclgroup.dolphin.ijs.web.dao;

import java.util.List;

import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;

public interface  IjsContractDateValidateDao 
{

	public List<?> dateValidateDao(IjsContractVO ijsContractVO, String session, String userInfo);


}
