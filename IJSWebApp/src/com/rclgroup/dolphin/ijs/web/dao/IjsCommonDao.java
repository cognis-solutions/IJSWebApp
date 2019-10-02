package com.rclgroup.dolphin.ijs.web.dao;

import java.util.List;

import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.vo.IjsEquipmetLookUpVO;

public interface IjsCommonDao {
      
    public String getViewJOUrl(String userIds) throws IJSException;
  //CR#03 START
    public List<String> validateEquipment(List<String> excelUploadTemplateList, String adhocType,String contractId,String validate);
    public List<IjsEquipmetLookUpVO> getValidEquipmentDetail(List<String> excelUploadTemplateList, String adhocType);
//CR#03 END
}
