package com.rclgroup.dolphin.ijs.web.service;

import java.util.List;

import com.rclgroup.dolphin.ijs.web.common.IjsSearchResult;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractDateValidateDao;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractSearchUIM;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;


public class IjsContractDateValidateSvc 
{
	IjsContractDateValidateDao dateValidateDao = null;
	
   

public IjsContractDateValidateSvc(IjsContractDateValidateDao dateValidateDao
        ) {
	    this.dateValidateDao = dateValidateDao;
        
      
        
}

public IjsContractSearchUIM	dateValidate(IjsContractVO ijsContractVO, 
    String session, 
    String userInfo
    ) throws IJSException
{

List<?> list  =dateValidateDao.dateValidateDao(ijsContractVO, 
        session,userInfo);
return transform(list);
}
	private IjsContractSearchUIM transform(List list)
	{
		IjsContractSearchUIM ijsContractSearchUIM = new IjsContractSearchUIM();
		 IjsSearchResult<IjsContractVO> searchResult = new IjsSearchResult<IjsContractVO>();
		   searchResult.setResult(list);
           //System.out.println(searchResult);
           ijsContractSearchUIM.setSearchResult(searchResult);
           
           return ijsContractSearchUIM;
	}
}
