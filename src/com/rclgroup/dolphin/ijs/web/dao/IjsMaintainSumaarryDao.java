package com.rclgroup.dolphin.ijs.web.dao;

import java.util.List;

import com.rclgroup.dolphin.ijs.web.vo.IjsMaintainJOSearchParamVO;

public interface IjsMaintainSumaarryDao {

	List<?> joSummary( String session, String userId);

}
