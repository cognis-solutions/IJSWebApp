package com.rclgroup.dolphin.ijs.web.dao;

public interface IJSUpdateContractDao {

	final String UPDATE_DELETE_FALG_CONATAINER="UPDATE RCLTBLS.IJS_ROUTE_DETAIL SET DEL_CON_FLG = 'D' WHERE RECORD_STATUS='E' AND RECORD_ADD_DATE<=? " + 
			"and not exists\r\n" + 
			"(Select * from RCLTBLS.IJS_JO_RA_LEG_DETAIL LEG, RCLTBLS.IJS_JO_HEADER HDR " + 
			"where LEG.FK_ROUTING_NO = ROUTING_NO and HDR.PK_JO_NUMBER = LEG.FK_JO_NUMBER " + 
			"AND (HDR.JO_STATUS <> 'C' or HDR.JO_CANCEL_YN <> 'Y'))";
	
	final String UPDATE_AGREEMENT="	UPDATE RCLTBLS.IJS_AGREEMENT_HEADER SET ROUTE_RATE_STATUS = 'S', RECORD_STATUS = 'S' " + 
			"			WHERE CONTRACT_NO in (Select CONTRACT_NO FROM RCLTBLS.IJS_ROUTE_DETAIL  " + 
			"			    WHERE RECORD_STATUS='E' AND RECORD_ADD_DATE<=?  " + 
			"			           and not exists(Select * from RCLTBLS.IJS_JO_RA_LEG_DETAIL LEG,  RCLTBLS.IJS_JO_HEADER HDR  " + 
			"			                where LEG.FK_ROUTING_NO = ROUTING_NO and  HDR.PK_JO_NUMBER = LEG.FK_JO_NUMBER  " + 
			"			                  AND  (HDR.JO_STATUS <> 'C' or  HDR.JO_CANCEL_YN <> 'Y'))  " + 
			"			           AND NOT exists (Select * from SEALINER.BOOKING_VOYAGE_ROUTING_DTL where ROUTING_REFNO = ROUTING_NO) )";
	
	final String UPDATE_SYSTEM_DETLS="UPDATE RCLTBLS.IJS_ROUTE_DETAIL " + 
			"   SET RECORD_CHANGE_USER = 'SYSTEM', RECORD_CHANGE_DATE = to_char(SYSDATE, 'YYYYMMDD'), RECORD_CHANGE_TIME = to_char(SYSDATE, 'HH24MI') " + 
			"         WHERE RECORD_STATUS='E' AND RECORD_ADD_DATE<=? " + 
			"           and not exists(Select * from RCLTBLS.IJS_JO_RA_LEG_DETAIL LEG,  RCLTBLS.IJS_JO_HEADER HDR " + 
			"                where LEG.FK_ROUTING_NO = ROUTING_NO and  HDR.PK_JO_NUMBER = LEG.FK_JO_NUMBER " + 
			"                  AND  (HDR.JO_STATUS <> 'C' or  HDR.JO_CANCEL_YN <> 'Y')) " + 
			"           AND NOT exists (Select * from SEALINER.BOOKING_VOYAGE_ROUTING_DTL where ROUTING_REFNO = ROUTING_NO) ";
	
	final String INSERT_HISTORY_LOG="INSERT INTO VASAPPS.IJS_CNTR_HISTORY_LOG  " + 
			"(Select CONTRACT_NO, sysdate,'SYSTEM', 'CONTRACT DELETED', IJS_CNTR_HISTORY_SEQ.nextval " + 
			"from RCLTBLS.IJS_ROUTE_DETAIL WHERE RECORD_STATUS='E' AND RECORD_ADD_DATE<=? " + 
			"and not exists(Select * from RCLTBLS.IJS_JO_RA_LEG_DETAIL LEG, RCLTBLS.IJS_JO_HEADER HDR " + 
			"where LEG.FK_ROUTING_NO = ROUTING_NO and HDR.PK_JO_NUMBER = LEG.FK_JO_NUMBER " + 
			"AND (HDR.JO_STATUS <> 'C' or HDR.JO_CANCEL_YN <> 'Y')) " + 
			"AND NOT exists (Select * from SEALINER.BOOKING_VOYAGE_ROUTING_DTL where ROUTING_REFNO = ROUTING_NO))";
	
	final String UPDATE_CONATAINER_STATUS_AS_EXPIRE="UPDATE RCLTBLS.IJS_ROUTE_DETAIL " + 
			"			SET RECORD_STATUS = 'R', " + 
			"                RECORD_CHANGE_USER = 'SYSTEM',  " + 
			"                RECORD_CHANGE_DATE = to_char(SYSDATE, 'YYYYMMDD'), " + 
			"                RECORD_CHANGE_TIME = to_char(SYSDATE, 'HH24MI')   " + 
			"			WHERE RCLTBLS.IJS_ROUTE_DETAIL.RECORD_STATUS='A' and RCLTBLS.IJS_ROUTE_DETAIL.CONTRACT_NO in (  " + 
			"			select h.CONTRACT_NO from RCLTBLS.IJS_AGREEMENT_HEADER h where h.END_DATE<?)";
	
	final String UPDATE_CONATAINER_STATUS_HISTORY_LOG="INSERT INTO VASAPPS.IJS_CNTR_HISTORY_LOG " + 
			"(Select CONTRACT_NO, sysdate,'SYSTEM', 'CONTRACT EXPIRED', IJS_CNTR_HISTORY_SEQ.nextval " + 
			"from RCLTBLS.IJS_ROUTE_DETAIL WHERE RECORD_STATUS = 'R' and " + 
			"RECORD_CHANGE_USER = 'SYSTEM' and " + 
			"RECORD_CHANGE_DATE = to_char(SYSDATE, 'YYYYMMDD') " + 
			"and not exists (Select * from VASAPPS.IJS_CNTR_HISTORY_LOG where CONTRACT_ID = CONTRACT_ID " + 
			"and ACTIVITY = 'CONTRACT EXPIRED' and to_char(ACTIVITY_DATE, 'YYYYMMDD') = to_char(SYSDATE, 'YYYYMMDD')))";
	
	void addFalgForDeleteAndExpireQuery();
	
}