/*-----------------------------------------------------------------------------------------------------------
IjsContractSearchJdbcDao.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 24/08/17  NIIT       IJS            search contract for IJS
02 07/09/17  NIIT       IJS            Contract Advance Search functionality
03 21/09/17  NIIT       IJS            New Contract functionality
04 05/09/17  NIIT       IJS            Edit,Suspend,Delete,Vendor detail,History log  Contract functionality
04 20/10/17  NIIT       IJS            Upload excel functionality added
-----------------------------------------------------------------------------------------------------------*/
package com.rclgroup.dolphin.ijs.web.dao.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.constants.IjsActionMethod;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.constants.IjsMessageCode;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractRateDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsContractSearchDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractBillingDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractCostDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractDownloadDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsContractSearchDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.service.IjsContractRateSvc;
import com.rclgroup.dolphin.ijs.web.ui.IjsContractRateUIM;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;
import com.rclgroup.dolphin.ijs.web.vo.IjsBaseMessageVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsCntrCurrencyLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractHistory;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractMessageVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractSearchParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractShipmentTermVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractTransportModeVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractUploadVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsContractVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsExcelUploadTemplateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsLocationVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsVendorDetails;

import oracle.jdbc.OracleTypes;

public class IjsContractSearchJdbcDao extends IjsBaseDao implements IjsContractSearchDao {
	private Map<String, List<IjsContractSearchDTO>> mapOfRes = new HashMap<String, List<IjsContractSearchDTO>>();
	private GetIjsSearchStoredProcedure getIjsSearchStoredProcedure; // ##01
	private IjsContractSaveStoredProcedure ijsContractSaveStoredProcedure; // ##03
	// ##04 BEGIN
	private IjsContractDeleteStoredProcedure ijsContractDeleteStoredProcedure;
	private IjsContractSuspendStoredProcedure ijsContractSuspendStoredProcedure;
	private IjsContractCompareStoredProcedure ijsContractCompareStoredProcedure;
	private IjsContractHistoryStoredProcedure ijsContractHistoryStoredProcedure;
	private IjsContractGPPStoredProcedure gppStoredProc;
	private IjsCntrVendorDetailsStrdProce ijsCntrVendorDetailsStrdProce;

	private GetIjsContractDownloadStoredProcedure getIjsContractDownloadStoredProcedure; // ##05
	private GetIjstermListStoredProcedure getIjstermListStoredProcedure;
	private GetIjsTransportModeListStoredProcedure getIjsTransportModeListStoredProcedure;

	private IjsCostRateStoredProcedure ijsCostRateStoredProcedure;
	private IjsBillingRateStoredProcedure ijsBillingRateStoredProcedure;

	private IjsContractRateDao contractRateDao;

	private IjsCntrCurrencyForContractProcedure ijsCntrCurrencyForContractProcedure;
	// private IjsCntrCurrencyForUserProcedure ijsCntrCurrencyForUserProcedure;
	private IjsContractPriorityValidationProcedure ijsContractPriorityValidationProcedure;
	private IjsMaxContractPriorityProcedure ijsMaxContractPriorityProcedure;
	private IjsContractVendorValidationProcedure ijsContractVendorValidationProcedure;
	private IjsContractUserValidationProcedure ijsContractUserValidationProcedure;
	private IjsContractForSameVendorValidationProcedure ijsContractForSameVendorValidationProcedure;
	private IjsCntrCurrencyForLocationProcedure ijsCntrCurrencyForLocationProcedure;
	private IjsCntrVendorNameProcedure ijsCntrVendorNameProcedure;
	private IjsCntrValidateTerminalProcedure ijsCntrValidateTerminalProcedure;
	private IjsCntrValidateLocationSetupProcedure ijsvalidateLocSetupProcedure;
	private IjsCostSetupCheckProcedure ijsCostSetupCheckProcedure;
	private IjsContractPriorityCorrectionProcedure ijsContractPriorityCorrectionProcedure;

	// ##04 END

	public void initDao() throws Exception {
		// ##01 BEGIN
		super.initDao();
		getIjsSearchStoredProcedure = new GetIjsSearchStoredProcedure(getJdbcTemplate(),
				new IjsContractSearchRowMapper());
		// ##01 END
		ijsContractSaveStoredProcedure = new IjsContractSaveStoredProcedure(getJdbcTemplate()); // ##03
		// ##04 BEGIN
		ijsContractDeleteStoredProcedure = new IjsContractDeleteStoredProcedure(getJdbcTemplate());
		ijsContractSuspendStoredProcedure = new IjsContractSuspendStoredProcedure(getJdbcTemplate());
		ijsContractCompareStoredProcedure = new IjsContractCompareStoredProcedure(getJdbcTemplate(),
				new IjsContractSearchRowMapper());
		ijsContractHistoryStoredProcedure = new IjsContractHistoryStoredProcedure(getJdbcTemplate(),
				new IjsContractHistoryRowMapper());
		ijsCntrVendorDetailsStrdProce = new IjsCntrVendorDetailsStrdProce(getJdbcTemplate(),
				new IjsCntrVendorDetailsRowMapper());
		contractRateDao = new IjsContractRateJdbcDao();
		// ##04 END

		getIjsContractDownloadStoredProcedure = new GetIjsContractDownloadStoredProcedure(getJdbcTemplate(),
				new IjsCostContractDownloadRowMapper(), new IjsBillContractDownloadRowMapper());

		getIjstermListStoredProcedure = new GetIjstermListStoredProcedure(getJdbcTemplate(),
				new IjsTermListRowMapper());

		ijsCostRateStoredProcedure = new IjsCostRateStoredProcedure(getJdbcTemplate(), new IjsCostRateRowMapper());
		ijsBillingRateStoredProcedure = new IjsBillingRateStoredProcedure(getJdbcTemplate(),
				new IjsBillingRateRowMapper());

		getIjsTransportModeListStoredProcedure = new GetIjsTransportModeListStoredProcedure(getJdbcTemplate(),
				new IjsPaymentMethodListRowMapper());

		ijsCntrCurrencyForContractProcedure = new IjsCntrCurrencyForContractProcedure(getJdbcTemplate(),
				new IjsContCurrencyRowMapper());

		ijsContractPriorityValidationProcedure = new IjsContractPriorityValidationProcedure(getJdbcTemplate());
		ijsMaxContractPriorityProcedure = new IjsMaxContractPriorityProcedure(getJdbcTemplate());

		// ijsCntrCurrencyForUserProcedure = new
		// IjsCntrCurrencyForUserProcedure(getJdbcTemplate()
		// , new IjsUserCurrencyRowMapper());
		ijsContractVendorValidationProcedure = new IjsContractVendorValidationProcedure(getJdbcTemplate());
		ijsContractUserValidationProcedure = new IjsContractUserValidationProcedure(getJdbcTemplate());
		ijsContractForSameVendorValidationProcedure = new IjsContractForSameVendorValidationProcedure(
				getJdbcTemplate());

		ijsCntrCurrencyForLocationProcedure = new IjsCntrCurrencyForLocationProcedure(getJdbcTemplate());

		ijsCntrVendorNameProcedure = new IjsCntrVendorNameProcedure(getJdbcTemplate());

		ijsCntrValidateTerminalProcedure = new IjsCntrValidateTerminalProcedure(getJdbcTemplate());
		gppStoredProc = new IjsContractGPPStoredProcedure(getJdbcTemplate());
		ijsvalidateLocSetupProcedure = new IjsCntrValidateLocationSetupProcedure(getJdbcTemplate());
		// CR#04 Start
		ijsCostSetupCheckProcedure = new IjsCostSetupCheckProcedure(getJdbcTemplate());
		// CR#04 End
		ijsContractPriorityCorrectionProcedure = new IjsContractPriorityCorrectionProcedure(getJdbcTemplate());
	}

	public List<IjsContractShipmentTermVO> getTermList(String userId) throws IJSException {

		List<IjsContractShipmentTermVO> lstTerrmList = getIjstermListStoredProcedure.getTermList(userId);
		return lstTerrmList;
	}

	public List<IjsContractTransportModeVO> getTransportMode(String userId, String vendorCode) throws IJSException {

		List<IjsContractTransportModeVO> lstTransportModeList = getIjsTransportModeListStoredProcedure
				.getTransportMode(userId, vendorCode);
		return lstTransportModeList;
	}

	public List<IjsCntrCurrencyLookUpVO> getCurrencyForFSC(String userId, String paymentFscCode) throws IJSException {
		List<IjsCntrCurrencyLookUpVO> listCurrencyForFSC = ijsCntrCurrencyForContractProcedure
				.getCurrencyForContract(userId, paymentFscCode);
		return listCurrencyForFSC;
	}

	public IjsCntrCurrencyLookUpVO getCurrencyForLocation(String userId, String location, String locationType)
			throws IJSException {
		IjsCntrCurrencyLookUpVO listCurrencyForLocation = new IjsCntrCurrencyLookUpVO();
		Map outMap = ijsCntrCurrencyForLocationProcedure.getCurrencyForLocation(userId, location, locationType);
		String fscCode = (String) outMap.get("p_o_v_ijs_fsc");
		String curCode = (String) outMap.get("p_o_v_ijs_currency");
		listCurrencyForLocation.setCurrencyCode(curCode);
		listCurrencyForLocation.setCurrencyName(fscCode);
		return listCurrencyForLocation;
	}

	public String getTerminalCode(String userId, String location, String locationType, String terminal)
			throws IJSException {

		IjsCntrCurrencyLookUpVO listCurrencyForLocation = new IjsCntrCurrencyLookUpVO();
		Map outMap = ijsCntrValidateTerminalProcedure.getLocationValidity(userId, location, locationType, terminal);

		String terminalValid = (String) outMap.get("p_o_v_location");

		return terminalValid;

	}

	public String getVendorName(String userId, String vendorCode) throws IJSException {
		String vendorName = ijsCntrVendorNameProcedure.getVendorName(userId, vendorCode);
		;
		return vendorName;
	};

//    else if (IjsActionMethod.GET_USER_CURRENCY.getAction().equals(lookupName) || IjsActionMethod.GET_CONTRACT_CURRENCY.getAction().equals(lookupName) ) {
//        List<IjsCntrCurrencyLookUpVO> list = null;
//        
//        if(IjsActionMethod.GET_CONTRACT_CURRENCY.getAction().equals(lookupName)){
//        list = ijsCntrCurrencyForContractProcedure.getCurrencyForContract(ijsLookupParamVO.getFindIn()
//            , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(),userInfo);
//        }

	// if(IjsActionMethod.GET_USER_CURRENCY.getAction().equals(lookupName)){
	// list =
	// ijsCntrCurrencyForUserProcedure.getCurrencyForUser(ijsLookupParamVO.getFindIn()
	// , ijsLookupParamVO.getFindFor(), ijsLookupParamVO.getWildCard(),userInfo);
	// }

	// ##01 BEGIN

	/**
	 * findContracts method for getting contracts based on search criteria
	 * 
	 * @param transMode
	 * @param dateRange
	 * @param userId
	 * @param searchParam
	 * @return
	 * @throws IJSException
	 */
	public List<IjsContractSearchDTO> findContracts(String transMode, String dateRange, String userId,
			IjsContractSearchParamVO searchParam) throws IJSException {
		List<IjsContractSearchDTO> lstContractSearch = getIjsSearchStoredProcedure.getIjsContractSearchList(transMode,
				dateRange, userId, searchParam);
		if (lstContractSearch == null || lstContractSearch.isEmpty()) {
			IJSException ijsException = new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
			throw ijsException;
		}
		return lstContractSearch;
	}

	public Map findContractsToDownload(String transMode, String dateRange, String userId,
			IjsContractSearchParamVO searchParam, List<IjsContractDownloadDTO> lstCostContractSearch,
			List<IjsContractDownloadDTO> lstBillContractSearch, String sessionId) throws IJSException {
		Map lstContractSearch = getIjsContractDownloadStoredProcedure.getIjsContractDownloadList(transMode, dateRange,
				userId, searchParam, lstCostContractSearch, lstBillContractSearch, sessionId);
		if (lstContractSearch == null || lstContractSearch.isEmpty()) {
			IJSException ijsException = new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
			throw ijsException;
		}
		return lstContractSearch;
	}
	// ##01 END
	// ##03 BEGIN

	/**
	 * saveOrUpdateContract for saving contract
	 * 
	 * @param ijsContractVO
	 * @param userInfo
	 * @param action
	 * @return
	 * @throws IJSException
	 */
	public String saveOrUpdateContract(IjsContractVO ijsContractVO, String userInfo, String action)
			throws IJSException {

		Map outMap = null;
		ArrayList<IjsLocationVO> lstLocation = (ArrayList<IjsLocationVO>) ijsContractVO.getLocations();
//        ArrayList<IjsContractMessageVO> lstSuccessMsg = new ArrayList<IjsContractMessageVO>();
//        ArrayList<IjsContractMessageVO> lstErrorMsg = new ArrayList<IjsContractMessageVO>();
		for (IjsLocationVO ijsLocationVO : lstLocation) {
			String errorCode = ijsContractUserValidationProcedure.validateUserToCreateContract(userInfo,
					ijsLocationVO.getFromLocation(), ijsLocationVO.getToLocation(), ijsContractVO.getVendorCode());
			if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_99999")) {
				throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_99999.getErrorCode());
			}
		}
		if (IjsActionMethod.NEW.getAction().equals(action) || IjsActionMethod.COPY.getAction().equals(action)) {
			for (IjsLocationVO ijsLocationVO : lstLocation) {
				for (IjsLocationVO ijsLocationInnerVO : lstLocation) {
					System.out.println(lstLocation.indexOf(ijsLocationVO));
					System.out.println(lstLocation.indexOf(ijsLocationInnerVO));
					System.out.println(lstLocation.indexOf(ijsLocationVO) != lstLocation.indexOf(ijsLocationInnerVO));
					if (lstLocation.indexOf(ijsLocationVO) != lstLocation.indexOf(ijsLocationInnerVO)) {
						if (ijsLocationVO.compareVO(ijsLocationInnerVO)) {
							throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10044.getErrorCode());
						}
					}
				}
			}

			// Validate Vendor
			// if(IjsActionMethod.NEW.getAction().equals(action)){
			for (IjsLocationVO ijsLocationVO : lstLocation) {
				outMap = ijsContractVendorValidationProcedure.validateVendor(ijsContractVO.getVendorCode());
				String errorCode = (String) outMap.get("p_o_v_err_cd");
				if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10008")) {
					throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10008.getErrorCode());
				}
			}
			// Validate existance of contract for same Vendor
			for (IjsLocationVO ijsLocationVO : lstLocation) {
				outMap = ijsContractForSameVendorValidationProcedure.validateContractForSameVendor(ijsContractVO,
						ijsLocationVO);
				String errorCode = (String) outMap.get("p_o_v_err_cd");
				if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10046")) {
					throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10046.getErrorCode());
				}
			}

			// Validate Priority overlapped
			for (IjsLocationVO ijsLocationVO : lstLocation) {
				/*
				 * Changes to fix priority issue,If priority is zero ,it means user haven't
				 * input a priority get max priority
				 */
				if (ijsLocationVO.getPriority() == 0) {
					outMap = ijsMaxContractPriorityProcedure.getMaxContractPriorty(ijsContractVO, ijsLocationVO);
					Integer maxPriority = (Integer) outMap.get("p_o_v_max_priority");
					String errorCode = (String) outMap.get("p_o_v_err_cd");
					if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10031")) {
						throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10031.getErrorCode());
					}
					ijsLocationVO.setPriority(maxPriority);
				} else {
					outMap = ijsContractPriorityValidationProcedure.validateContractPriorty(ijsContractVO,
							ijsLocationVO);
				}

				String errorCode = (String) outMap.get("p_o_v_err_cd");
				if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10007")) {
					throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10007.getErrorCode());
				}
			}
		}

		if (IjsActionMethod.NEW.getAction().equals(action)
				|| IjsActionMethod.NEW_CHANGE_PRIORITY.getAction().equals(action)
				|| IjsActionMethod.COPY_CHANGE_PRIORITY.getAction().equals(action)
				|| IjsActionMethod.COPY.getAction().equals(action)) {
			IjsBaseMessageVO msgDupRoute = new IjsBaseMessageVO();
			// IjsBaseMessageVO msgPrriorityOverLap = new IjsBaseMessageVO();
			IjsBaseMessageVO msgSuccessVO = new IjsBaseMessageVO();
			msgDupRoute.setErrorCode(IjsErrorCode.DB_IJS_CNTR_EX_10006.getErrorCode());
			msgDupRoute.setMessageList(new ArrayList());
			msgSuccessVO.setErrorCode(IjsMessageCode.IJS_COMM_INSERTED.getMsgCode());
			msgSuccessVO.setMessageList(new ArrayList());

			for (IjsLocationVO ijsLocationVO : lstLocation) {

				IjsContractMessageVO msgVO = new IjsContractMessageVO();
				outMap = ijsContractSaveStoredProcedure.saveORUpdateContracts(ijsContractVO, ijsLocationVO, userInfo,
						action);
				String errorCode = (String) outMap.get("p_o_v_err_cd");
				msgVO.setFromLocation(ijsLocationVO.getFromLocation());
				msgVO.setToLocation(ijsLocationVO.getToLocation());
				if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10006")) {
					msgVO.setMessage(IjsErrorCode.DB_IJS_CNTR_EX_10006.getErrorCode());
					msgDupRoute.getMessageList().add(msgVO);
				} else if (errorCode != null && errorCode.contains("DB_IJS_RATE_EX_10023")) {
					throw new IJSException(IjsErrorCode.DB_IJS_RATE_EX_10023.getErrorCode());
				}
//             else if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10008")) {
//                 throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10008.getErrorCode());
//                 msgVO.setMessage(IjsErrorCode.DB_IJS_CNTR_EX_10007.getErrorCode());
//                 lstErrorMsg.add(msgVO);
//             } 
				else if (errorCode == null && (IjsActionMethod.NEW.getAction().equals(action)
						|| IjsActionMethod.NEW_CHANGE_PRIORITY.getAction().equals(action)
						|| IjsActionMethod.COPY.getAction().equals(action)
						|| IjsActionMethod.COPY_CHANGE_PRIORITY.getAction().equals(action))) {
					String contractId = (String) outMap.get("p_o_v_contract_id");
					// ijsContractVO.setContractId(contractId);
					msgVO.setContractId(contractId);
					msgVO.setMessage(IjsMessageCode.IJS_COMM_INSERTED.getMsgCode());
					msgSuccessVO.getMessageList().add(msgVO);
				}
			}

			ijsContractVO.setMsgDupRoute(msgDupRoute);
			// ijsContractVO.setMsgPriorityOverLap(msgPrriorityOverLap);
			ijsContractVO.setMsgSuccessVO(msgSuccessVO);
		}

		else if (IjsActionMethod.EDIT.getAction().equals(action)
				|| IjsActionMethod.EDIT_CHANGE_PRIORITY.getAction().equals(action)) {
			for (IjsLocationVO ijsLocationVO : lstLocation) {
				/*
				 * Changes to fix priority issue,If priority is zero ,it means user haven't
				 * input a priority get max priority
				 */
				if (ijsLocationVO.getPriority() == 0) {
					outMap = ijsMaxContractPriorityProcedure.getMaxContractPriorty(ijsContractVO, ijsLocationVO);
					Integer maxPriority = (Integer) outMap.get("p_o_v_max_priority");
					/*
					 * String errorCode = (String)outMap.get("p_o_v_err_cd"); if(errorCode != null
					 * && errorCode.contains("DB_IJS_CNTR_EX_10031")) {
					 * ijsLocationVO.setPriority(99); }
					 */
					ijsLocationVO.setPriority(maxPriority);
				}
			}
			outMap = ijsContractSaveStoredProcedure.saveORUpdateContracts(ijsContractVO,
					ijsContractVO.getLocations().get(0), userInfo, action);
			String errorCode = (String) outMap.get("p_o_v_err_cd");
			if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10006")) {
				System.out.println("Error Code is  : " + errorCode);
				throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10006.getErrorCode());
			} else if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10007")) {
				throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10007.getErrorCode());
			} else if (errorCode != null && errorCode.contains("DB_IJS_CNTR_EX_10008")) {
				throw new IJSException(IjsErrorCode.DB_IJS_CNTR_EX_10008.getErrorCode());
			} else if (errorCode != null && errorCode.contains("DB_IJS_CNTR_JO_EX_1001")) {
				throw new IJSException(IjsErrorCode.DB_IJS_CNTR_JO_EX_1001.getErrorCode());
			} else if (errorCode != null && errorCode.contains("DB_IJS_RATE_EX_10023")) {
				throw new IJSException(IjsErrorCode.DB_IJS_RATE_EX_10023.getErrorCode());
			} else {
				return IjsMessageCode.IJS_COMM_UPDATE.getMsgCode();
			}
		}
		return null;
	}
	// ##03 END
	// ##04 BEGIN

	/**
	 * deleteContract for delete contract
	 * 
	 * @param contractsList
	 * @param userInfo
	 * @return
	 * @throws IJSException
	 */
	public String deleteContract(List<String> contractsList, String userInfo) throws IJSException {
		String errorCode = null;
		StringBuilder failedContracts = new StringBuilder();
		for (String contractId : contractsList) {
			errorCode = ijsContractDeleteStoredProcedure.deleteContract(contractId, userInfo);
			if (errorCode != null && errorCode.contains("DB_IJS_CNTR_JO_EX_1002")) {
				failedContracts.append(contractId).append(",");
				/*
				 * response.put(contractId, IjsErrorCode.DB_IJS_CNTR_EX_10010.getErrorCode());
				 */
			}
		}

		return failedContracts.toString();
	}

	/**
	 * suspendContract method for suspend contract
	 * 
	 * @param contractsList
	 * @param userInfo
	 * @return
	 * @throws IJSException
	 */
	public String suspendContract(List<String> contractsList, String userInfo) throws IJSException {
		String errorCode = null;
		StringBuilder failedContracts = new StringBuilder();
		for (String contractId : contractsList) {
			errorCode = ijsContractSuspendStoredProcedure.suspendContract(contractId, userInfo);
			if (errorCode != null && errorCode.contains("DB_IJS_CNTR_JO_EX_1001")) {
				failedContracts.append(contractId).append(",");
				/*
				 * response.put(contractId, IjsErrorCode.DB_IJS_CNTR_EX_10010.getErrorCode());
				 */
			}
		}

		return failedContracts.toString();
	}

	/**
	 * compareContract comparing contracts
	 * 
	 * @param ijsContractVO
	 * @param userInfo
	 * @return
	 * @throws IJSException
	 */
	public List<IjsContractSearchDTO> compareContract(IjsContractVO ijsContractVO, String userInfo)
			throws IJSException {

		// TO-DO
		List<IjsContractSearchDTO> contractList = ijsContractCompareStoredProcedure.getContractList(ijsContractVO,
				userInfo);
		if (contractList == null || contractList.isEmpty()) {
			throw new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
		} else if (contractList.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date currentDate = new Date();
			Date modifiedCurrentDate = null;
			try {
				modifiedCurrentDate = sdf.parse(new SimpleDateFormat("yyyyMMdd").format(currentDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (IjsContractSearchDTO ijsContractSearchDTO : contractList) {
				List<IjsContractCostDTO> outList = ijsCostRateStoredProcedure
						.getCostRate(ijsContractSearchDTO.getRoutingId(), userInfo);
				List<IjsContractCostDTO> rateFilteredList = null;
				List<IjsContractBillingDTO> billingList = ijsBillingRateStoredProcedure
						.getBillingRate(ijsContractSearchDTO.getRoutingId(), userInfo);
				String lstrRateLaden20 = "";
				String lstrRateLaden40 = "";
				String lstrRateLaden45 = "";
				String lstrRateMT20 = "";
				String lstrRateMT40 = "";
				String lstrRateMT45 = "";
				String lstrExpiredRateLaden20 = "";
				String lstrExpiredRateLaden40 = "";
				String lstrExpiredRateLaden45 = "";
				String lstrExpiredRateMT20 = "";
				String lstrExpiredRateMT40 = "";
				String lstrExpiredRateMT45 = "";
				String billingRateLaden20 = "";
				String billingRateLaden40 = "";
				String billingRateLaden45 = "";
				String billingRateMT20 = "";
				String billingRateMT40 = "";
				String billingRateMT45 = "";
				String expiredBillingRateLaden20 = "";
				String expiredBillingRateLaden40 = "";
				String expiredBillingRateLaden45 = "";
				String expiredBillingRateMT20 = "";
				String expiredBillingRateMT40 = "";
				String expiredBillingRateMT45 = "";
				String costCurrency = "";
				String billingCurrency = "";
				boolean mtFound = false;
				boolean ladenFound = false;
				boolean mtExpiredFound = false;
				boolean ladenExpiredFound = false;
				for (IjsContractCostDTO ijsContractCostDTO : outList) {
					Date costEffectiveDate = null;
					Date costExpiryDate = null;
					if (!"A".equals(ijsContractCostDTO.getRecordStatus())) {
						continue;
					}
					try {
						costEffectiveDate = sdf.parse(ijsContractCostDTO.getEffectiveDate());
						costExpiryDate = sdf.parse(ijsContractCostDTO.getExpiryDate());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if ((modifiedCurrentDate.before(costExpiryDate) || modifiedCurrentDate.equals(costExpiryDate))
							&& (modifiedCurrentDate.after(costEffectiveDate)
									|| modifiedCurrentDate.equals(costEffectiveDate))) {
						switch (ijsContractCostDTO.getMtLaden()) {
						case "LADEN": {
							lstrRateLaden20 = ijsContractCostDTO.getRate20();
							lstrRateLaden40 = ijsContractCostDTO.getRate40();
							lstrRateLaden45 = ijsContractCostDTO.getRate45();
							costCurrency = ijsContractCostDTO.getCurrency();
							ladenFound = true;
							break;
						}
						case "MT": {
							if (!mtFound) {
								lstrRateMT20 = ijsContractCostDTO.getRate20();
								lstrRateMT40 = ijsContractCostDTO.getRate40();
								lstrRateMT45 = ijsContractCostDTO.getRate45();
								costCurrency = ijsContractCostDTO.getCurrency();
								mtFound = true;
								continue;
							}

						}
						}
						if (ladenFound) {
							break;
						}
					} else if (modifiedCurrentDate.after(costExpiryDate)
							&& modifiedCurrentDate.after(costEffectiveDate)) {
						switch (ijsContractCostDTO.getMtLaden()) {
						case "LADEN": {
							lstrExpiredRateLaden20 = ijsContractCostDTO.getRate20();
							lstrExpiredRateLaden40 = ijsContractCostDTO.getRate40();
							lstrExpiredRateLaden45 = ijsContractCostDTO.getRate45();
							costCurrency = ijsContractCostDTO.getCurrency();
							ladenExpiredFound = true;
							continue;
						}
						case "MT": {
							if (!mtExpiredFound) {
								lstrExpiredRateMT20 = ijsContractCostDTO.getRate20();
								lstrExpiredRateMT40 = ijsContractCostDTO.getRate40();
								lstrExpiredRateMT45 = ijsContractCostDTO.getRate45();
								costCurrency = ijsContractCostDTO.getCurrency();
								mtExpiredFound = true;
								continue;
							}

						}
						}
					}
				}
				ladenFound = false;
				mtFound = false;
				ladenExpiredFound = false;
				mtExpiredFound = false;
				for (IjsContractBillingDTO ijsContractBillingDTO : billingList) {
					Date billingEffectiveDate = null;
					Date billingExpiryDate = null;
					if (!"A".equals(ijsContractBillingDTO.getRecordStatus())) {
						continue;
					}
					try {
						billingEffectiveDate = sdf.parse(ijsContractBillingDTO.getEffectiveDate());
						billingExpiryDate = sdf.parse(ijsContractBillingDTO.getExpiryDate());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if ((modifiedCurrentDate.before(billingExpiryDate) || modifiedCurrentDate.equals(billingExpiryDate))
							&& (modifiedCurrentDate.after(billingEffectiveDate)
									|| modifiedCurrentDate.equals(billingEffectiveDate))) {
						switch (ijsContractBillingDTO.getMtLaden()) {
						case "LADEN": {
							billingRateLaden20 = ijsContractBillingDTO.getRate20();
							billingRateLaden40 = ijsContractBillingDTO.getRate40();
							billingRateLaden45 = ijsContractBillingDTO.getRate45();
							billingCurrency = ijsContractBillingDTO.getCurrency();
							ladenFound = true;
							break;
						}
						case "MT": {
							if (!mtFound) {
								billingRateMT20 = ijsContractBillingDTO.getRate20();
								billingRateMT40 = ijsContractBillingDTO.getRate40();
								billingRateMT45 = ijsContractBillingDTO.getRate45();
								billingCurrency = ijsContractBillingDTO.getCurrency();
								mtFound = true;
								break;
							}

						}
						}
						if (ladenFound) {
							break;
						}
					} else if (modifiedCurrentDate.after(billingExpiryDate)
							&& modifiedCurrentDate.after(billingEffectiveDate)) {
						switch (ijsContractBillingDTO.getMtLaden()) {
						case "LADEN": {
							expiredBillingRateLaden20 = ijsContractBillingDTO.getRate20();
							expiredBillingRateLaden40 = ijsContractBillingDTO.getRate40();
							expiredBillingRateLaden45 = ijsContractBillingDTO.getRate45();
							billingCurrency = ijsContractBillingDTO.getCurrency();
							ladenExpiredFound = true;
							continue;
						}
						case "MT": {
							if (!mtExpiredFound) {
								expiredBillingRateMT20 = ijsContractBillingDTO.getRate20();
								expiredBillingRateMT40 = ijsContractBillingDTO.getRate40();
								expiredBillingRateMT45 = ijsContractBillingDTO.getRate45();
								billingCurrency = ijsContractBillingDTO.getCurrency();
								mtExpiredFound = true;
								continue;
							}

						}
						}
					}
				}
				if ((lstrRateLaden20 != null && !lstrRateLaden20.isEmpty())
						|| (lstrRateLaden40 != null && !lstrRateLaden40.isEmpty())
						|| (lstrRateLaden45 != null && !lstrRateLaden45.isEmpty())
						|| (lstrRateMT20 != null && !lstrRateMT20.isEmpty())
						|| (lstrRateMT40 != null && !lstrRateMT40.isEmpty())
						|| (lstrRateMT45 != null && !lstrRateMT45.isEmpty())) {
					ijsContractSearchDTO
							.setRate20((lstrRateLaden20 != null && !lstrRateLaden20.isEmpty() && lstrRateLaden20 != "0")
									? lstrRateLaden20
									: lstrRateMT20);
					ijsContractSearchDTO
							.setRate40((lstrRateLaden40 != null && !lstrRateLaden40.isEmpty() && lstrRateLaden40 != "0")
									? lstrRateLaden40
									: lstrRateMT40);
					ijsContractSearchDTO
							.setRate45((lstrRateLaden45 != null && !lstrRateLaden45.isEmpty() && lstrRateLaden45 != "0")
									? lstrRateLaden45
									: lstrRateMT45);
					ijsContractSearchDTO.setCostCurrency(costCurrency);
				} else if ((lstrExpiredRateLaden20 != null && !lstrExpiredRateLaden20.isEmpty())
						|| (lstrExpiredRateLaden40 != null && !lstrExpiredRateLaden40.isEmpty())
						|| (lstrExpiredRateLaden45 != null && !lstrExpiredRateLaden45.isEmpty())
						|| (lstrExpiredRateMT20 != null && !lstrExpiredRateMT20.isEmpty())
						|| (lstrExpiredRateMT40 != null && !lstrExpiredRateMT40.isEmpty())
						|| (lstrExpiredRateMT45 != null && !lstrExpiredRateMT45.isEmpty())) {
					ijsContractSearchDTO.setRate20((lstrExpiredRateLaden20 != null && !lstrExpiredRateLaden20.isEmpty())
							? lstrExpiredRateLaden20
							: lstrExpiredRateMT20);
					ijsContractSearchDTO.setRate40((lstrExpiredRateLaden40 != null && !lstrExpiredRateLaden40.isEmpty())
							? lstrExpiredRateLaden40
							: lstrExpiredRateMT40);
					ijsContractSearchDTO.setRate45((lstrExpiredRateLaden45 != null && !lstrExpiredRateLaden45.isEmpty())
							? lstrExpiredRateLaden45
							: lstrExpiredRateMT45);
					ijsContractSearchDTO.setCostCurrency(costCurrency);
				}

				if ((billingRateLaden20 != null && !billingRateLaden20.isEmpty())
						|| (billingRateLaden40 != null && !billingRateLaden40.isEmpty())
						|| (billingRateLaden45 != null && !billingRateLaden45.isEmpty())
						|| (billingRateMT20 != null && !billingRateMT20.isEmpty())
						|| (billingRateMT40 != null && !billingRateMT40.isEmpty())
						|| (billingRateMT45 != null && !billingRateMT45.isEmpty())) {
					ijsContractSearchDTO.setBillingRate20(
							(billingRateLaden20 != null && !billingRateLaden20.isEmpty()) ? billingRateLaden20
									: billingRateMT20);
					ijsContractSearchDTO.setBillingRate40(
							(billingRateLaden40 != null && !billingRateLaden40.isEmpty()) ? billingRateLaden40
									: billingRateMT40);
					ijsContractSearchDTO.setBillingRate45(
							(billingRateLaden45 != null && !billingRateLaden45.isEmpty()) ? billingRateLaden45
									: billingRateMT45);
					ijsContractSearchDTO.setBillingCurrency(billingCurrency);
				} else if ((expiredBillingRateLaden20 != null && !expiredBillingRateLaden20.isEmpty())
						|| (expiredBillingRateLaden40 != null && !expiredBillingRateLaden40.isEmpty())
						|| (expiredBillingRateLaden45 != null && !expiredBillingRateLaden45.isEmpty())
						|| (expiredBillingRateMT20 != null && !expiredBillingRateMT20.isEmpty())
						|| (expiredBillingRateMT40 != null && !expiredBillingRateMT40.isEmpty())
						|| (expiredBillingRateMT45 != null && !expiredBillingRateMT45.isEmpty())) {
					ijsContractSearchDTO.setBillingRate20(
							(expiredBillingRateLaden20 != null && !expiredBillingRateLaden20.isEmpty())
									? expiredBillingRateLaden20
									: expiredBillingRateMT20);
					ijsContractSearchDTO.setBillingRate40(
							(expiredBillingRateLaden40 != null && !expiredBillingRateLaden40.isEmpty())
									? expiredBillingRateLaden40
									: expiredBillingRateMT40);
					ijsContractSearchDTO.setBillingRate45(
							(expiredBillingRateLaden45 != null && !expiredBillingRateLaden45.isEmpty())
									? expiredBillingRateLaden45
									: expiredBillingRateMT45);
					ijsContractSearchDTO.setBillingCurrency(billingCurrency);
				}
			}
		}

		return contractList;
	}

	/**
	 * getContractHistory for getting history of contract
	 * 
	 * @param contractId
	 * @param userInfo
	 * @return
	 * @throws IJSException
	 */
	public List<IjsContractHistory> getContractHistory(String contractId, String userInfo) throws IJSException {
		List<IjsContractHistory> historyList = ijsContractHistoryStoredProcedure.getHistoryList(contractId, userInfo);
		if (historyList == null || historyList.isEmpty()) {
			IJSException ijsException = new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
			throw ijsException;
		}
		return historyList;
	}

	/**
	 * getVendorDetails for getting vendor information
	 * 
	 * @param vendorCode
	 * @param userInfo
	 * @return
	 * @throws IJSException
	 */
	public List<IjsVendorDetails> getVendorDetails(String vendorCode, String userInfo) throws IJSException {
		List<IjsVendorDetails> vendorDetails = ijsCntrVendorDetailsStrdProce.getVendorDetailsList(vendorCode, userInfo);
		if (vendorDetails == null || vendorDetails.isEmpty()) {
			throw new IJSException(IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode());
		}
		return vendorDetails;
	}

	// ##04 END
	// ##05 BEGIN

	/**
	 * uploadContracts method for uploading contracts .
	 * 
	 * @param excelUploadTemplateList
	 * @param userInfo
	 * @param rateSrv
	 * @return
	 */
	public IjsContractUploadVO uploadContracts(List<IjsExcelUploadTemplateVO> excelUploadTemplateList, String userInfo,
			IjsContractRateSvc rateSrv) {
		List<String> updatedContracts = new ArrayList<String>();
		List<String> failedContracts = new ArrayList<String>();
		List<String> newContracts = new ArrayList<String>();
		List<String> partialSuccessful = new ArrayList<String>();
		Map<String, String> errorMap = new HashMap<>();
		IjsContractUploadVO ijsContractUploadVO = new IjsContractUploadVO();
		String errorCode = null;
		String setupError = null;
		String isValid1 = null;
		String isValid2 = null;
		boolean transCodeFound = false;
		for (IjsExcelUploadTemplateVO excelUploadTemplate : excelUploadTemplateList) {
			IjsContractVO contractVO = excelUploadTemplate.getIjsContractVO();
			String action = "new";
			if (contractVO.getContractId() != null && contractVO.getContractId().length() > 0) {
				action = "edit";
			}
			if (contractVO.isInvalidContract()) {
				failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
						? contractVO.getVendorCode()
						: contractVO.getContractId());
				errorMap.put(contractVO.getContractId(), IjsErrorCode.DB_IJS_CNTR_EX_10049.getErrorCode());
				continue;
			}
			// Date validation
			try {
				IjsHelper.validateDate(contractVO.getStartDate(), contractVO.getEndDate());
			} catch (IJSException ie) {
				failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
						? contractVO.getVendorCode()
						: contractVO.getContractId());
				errorMap.put(contractVO.getContractId(), ie.getMessage());
				continue;
			}

			if (contractVO.getDays() < 0 || contractVO.getDistance() < 0 || contractVO.getHours() < 0
					|| contractVO.getPriority() < 0) {

				failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
						? contractVO.getVendorCode()
						: contractVO.getContractId());
				errorMap.put(contractVO.getContractId(), "IJS_COMM_EX_1");
				continue;
			}
			try {
				getVendorDetails(contractVO.getVendorCode(), userInfo);
			} catch (IJSException e) {
				errorCode = e.getMessage();
				failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
						? contractVO.getVendorCode()
						: contractVO.getContractId());
				errorMap.put(contractVO.getContractId(), errorCode);
				continue;
			}
			try {
				isValid1 = getTerminalCode(userInfo, contractVO.getFromLocation(), contractVO.getFromLocType(),
						contractVO.getFromTerminal());
				isValid2 = getTerminalCode(userInfo, contractVO.getToLocation(), contractVO.getToLocType(),
						contractVO.getToTerminal());
				if (("0").equals(isValid1) || ("0").equals(isValid2)) {
					failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
							? contractVO.getVendorCode()
							: contractVO.getContractId());
					errorMap.put(contractVO.getContractId(), IjsErrorCode.DB_IJS_CNTR_EX_10049.getErrorCode());
					continue;
				}
			} catch (IJSException e) {
				failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
						? contractVO.getVendorCode()
						: contractVO.getContractId());
				errorMap.put(contractVO.getContractId(), IjsErrorCode.DB_IJS_CNTR_EX_10049.getErrorCode());
				continue;
			}
			List<IjsContractTransportModeVO> transMods;
			try {
				transMods = getTransportMode(userInfo, contractVO.getVendorCode());
				if (transMods == null || transMods.isEmpty()) {
					failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
							? contractVO.getVendorCode()
							: contractVO.getContractId());
					errorMap.put(contractVO.getContractId(), "IJS_EX_10047");
					continue;
				} else {
					for (IjsContractTransportModeVO transVO : transMods) {
						if (transVO.getCode().equalsIgnoreCase(IjsHelper.getTransCode(contractVO.getTransMode()))) {
							transCodeFound = true;
							break;
						}
					}
					if (!transCodeFound) {
						failedContracts
								.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
										? contractVO.getVendorCode()
										: contractVO.getContractId());
						errorMap.put(contractVO.getContractId(), "IJS_EX_10047");
						continue;
					}
				}
			} catch (IJSException e) {
				failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
						? contractVO.getVendorCode()
						: contractVO.getContractId());
				errorMap.put(contractVO.getContractId(), "IJS_EX_10047");
				System.err.println(e.getMessage());
				continue;
			}
			try {
				IjsCntrCurrencyLookUpVO fscCurrVO = getCurrencyForLocation(userInfo, contractVO.getFromLocation(),
						contractVO.getFromLocType());
				if (fscCurrVO.getCurrencyCode() == null || fscCurrVO.getCurrencyCode().isEmpty()) {
					failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
							? contractVO.getVendorCode()
							: contractVO.getContractId());
					errorMap.put(contractVO.getContractId(), "IJS_EX_10047");
					continue;
				}
				if (fscCurrVO.getCurrencyName() == null || fscCurrVO.getCurrencyName().isEmpty()) {
					failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
							? contractVO.getVendorCode()
							: contractVO.getContractId());
					errorMap.put(contractVO.getContractId(), "IJS_EX_10047");
					continue;
				}
			} catch (IJSException e) {
				failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
						? contractVO.getVendorCode()
						: contractVO.getContractId());
				errorMap.put(contractVO.getContractId(), "IJS_EX_10047");
				System.err.println(e.getMessage());
				continue;
			}
			try {
				List<IjsCntrCurrencyLookUpVO> listCurrencyForFSC = getCurrencyForFSC(userInfo,
						contractVO.getPaymentFsc());
				if (listCurrencyForFSC == null || listCurrencyForFSC.isEmpty()) {
					failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
							? contractVO.getVendorCode()
							: contractVO.getContractId());
					errorMap.put(contractVO.getContractId(), "IJS_EX_10048");
					continue;
				}
			} catch (IJSException e) {
				failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
						? contractVO.getVendorCode()
						: contractVO.getContractId());
				errorMap.put(contractVO.getContractId(), "IJS_EX_10048");
				System.err.println(e.getMessage());
				continue;
			}
			Map outMap = null;
			try {
				outMap = ijsContractSaveStoredProcedure.saveORUpdateContract(contractVO, userInfo, action);
			} catch (Exception e) {
				failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
						? contractVO.getVendorCode()
						: contractVO.getContractId());
				continue;
			}
			errorCode = (String) outMap.get("p_o_v_err_cd");
			String routinNumber = (String) outMap.get("p_o_v_routing_id");
			String contractNumber = (String) outMap.get("p_o_v_contract_id");
			if (errorCode != null) {
				failedContracts.add((contractVO.getContractId() == null || contractVO.getContractId().equals(""))
						? contractVO.getVendorCode()
						: contractVO.getContractId());
				continue;
			} else if (routinNumber != null) {
				if (contractNumber != null) {
					// updatedContracts.add(contractNumber);
				}

				if (excelUploadTemplate.isIsCostRateUpload()) {

					List<IjsRateVO> rateVOList = excelUploadTemplate.getIjsCostRateVOList();
					int costSize = rateVOList.size();
					int successCost = 0;
					for (IjsRateVO rateVO : rateVOList) {
						String costRateAction = "addCostRate";
						if (rateVO.getCostRateSequenceNum() > 0) {
							costRateAction = "editCostRate";
						}
						IjsContractRateUIM actionForm = new IjsContractRateUIM();
						actionForm.setRoutingNumber(Integer.parseInt(routinNumber));
						actionForm.setAction(costRateAction);
						actionForm.setIjsRateVO(rateVO);
						String costRateResult = null;
						if (Double.parseDouble(rateVO.getRate20()) < 0 || Double.parseDouble(rateVO.getRate40()) < 0
								|| Double.parseDouble(rateVO.getRate45()) < 0 || rateVO.getUpto() < 0) {
							// failedContracts.add((contractVO.getContractId()==null ||
							// contractVO.getContractId().equals(""))?contractVO.getVendorCode():contractVO.getContractId());
							break;
						}
						try {
							IjsHelper.validateDate(rateVO.getStartDate(), rateVO.getEndDate());
							// failedContracts.add((contractVO.getContractId()==null ||
							// contractVO.getContractId().equals(""))?contractVO.getVendorCode():contractVO.getContractId());
						} catch (IJSException ie) {
							break;
						}
						errorCode = ijsCostSetupCheckProcedure.validateCostRateSetup(contractVO.getFromTerminal(),
								rateVO.getOogCode(), rateVO.getPortCode(), rateVO.getImdgCode());
						if (errorCode != null) {
							break;
						}
						try {
							costRateResult = rateSrv.getIjsContractRateDao().saveOrUpdateCostRate(actionForm, userInfo);
						} catch (NumberFormatException nfe) {
							break;
						} catch (Exception e) {
							break;
						}
						System.out.println("costRateResult --" + costRateResult);
						if (costRateResult != null && !costRateResult.contains("IJS_MSG")) {
							continue;
						}
						successCost = successCost + 1;
					}
					if (successCost == 0) {
						if (!failedContracts.contains(contractVO.getContractId())) {
							failedContracts.add(contractVO.getContractId());
						}
					} else if (successCost == costSize) {
						updatedContracts.add(contractVO.getContractId());
					} else {
						partialSuccessful.add(contractVO.getContractId());
					}
				} else if (excelUploadTemplate.isBillingRateUpload()) {
					List<IjsRateVO> rateVOList = excelUploadTemplate.getIjsBillingRateVOList();
					int billingSize = rateVOList.size();
					int successBilling = 0;
					if (rateVOList != null) {
						for (IjsRateVO rateVO : rateVOList) {
							String costRateAction = "addBillingRate";
							if (rateVO.getCostRateSequenceNum() > 0) {
								costRateAction = "editBillingRate";
							}
							IjsContractRateUIM actionForm = new IjsContractRateUIM();
							actionForm.setRoutingNumber(Integer.parseInt(routinNumber));
							actionForm.setAction(costRateAction);
							actionForm.setIjsRateVO(rateVO);
							String billRateResult = null;
							if (Double.parseDouble(rateVO.getRate20()) < 0 || Double.parseDouble(rateVO.getRate40()) < 0
									|| Double.parseDouble(rateVO.getRate45()) < 0 || rateVO.getUpto() < 0) {
								// failedContracts.add((contractVO.getContractId()==null ||
								// contractVO.getContractId().equals(""))?contractVO.getVendorCode():contractVO.getContractId());
								break;
							}
							try {
								IjsHelper.validateDate(rateVO.getStartDate(), rateVO.getEndDate());
							} catch (IJSException ie) {
								break;
							}
							try {
								billRateResult = rateSrv.getIjsContractRateDao().saveOrUpdateBillingRate(actionForm,
										userInfo);
							} catch (NumberFormatException nfe) {
								// failedContracts.add(contractVO.getContractId());
								break;
							} catch (Exception e) {
								// failedContracts.add(contractVO.getContractId());
								break;
							}

							System.out.println("costRateResult --" + billRateResult);
							if (billRateResult != null && !billRateResult.contains("IJS_MSG")) {
								continue;
							}
							successBilling = successBilling + 1;
						}
						if (successBilling == 0) {
							if (!failedContracts.contains(contractVO.getContractId())) {
								failedContracts.add(contractVO.getContractId());
							}
						} else if (successBilling == billingSize) {
							updatedContracts.add(contractVO.getContractId());
						} else {
							partialSuccessful.add(contractVO.getContractId());
						}
					}

				} else {
					updatedContracts.add(contractVO.getContractId());
				}

			}

		}
		ijsContractUploadVO.setFailedContracts(failedContracts);
		ijsContractUploadVO.setUpdatedContracts(updatedContracts);
		ijsContractUploadVO.setNewContracts(newContracts);
		ijsContractUploadVO.setPartialSuccessful(partialSuccessful);
		ijsContractUploadVO.setErrorMap(errorMap);
		return ijsContractUploadVO;
	}
	// ##05 END
	// ##01 BEGIN

	protected class GetIjsSearchStoredProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String SQL_RLTD_CST_FOR_GST_SEARCH = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_MAIN_SEARCH";

		GetIjsSearchStoredProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_RLTD_CST_FOR_GST_SEARCH);
			declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_trans_mod", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_find_val", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_vend_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_frm_loc_typ", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_frm_loc", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_frm_trm", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc_typ", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_trm", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_cnt_rout", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_country_code", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_priority", Types.VARCHAR, rowMapper)); // ROOMY
			declareParameter(new SqlInOutParameter("p_i_v_status", Types.VARCHAR, rowMapper)); // ROOMY
			declareParameter(new SqlInOutParameter("p_i_v_pur_term", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_filter_by", Types.VARCHAR, rowMapper));

			declareParameter(new SqlInOutParameter("p_i_v_Dom_Inn", Types.VARCHAR, rowMapper));

			declareParameter(new SqlOutParameter("p_io_v_err_cd", Types.VARCHAR, rowMapper));

			compile();

		}

		public List<IjsContractSearchDTO> getIjsContractSearchList(String transMode, String dateRange, String userId,

				IjsContractSearchParamVO searchParam) {
			mapOfRes = new HashMap<String, List<IjsContractSearchDTO>>();
			Map outMap = new HashMap();
			Map inParameters = new HashMap();

			inParameters.put("p_i_v_trans_mod", RutDatabase.stringToDb(IjsHelper.getTransCode(transMode)));
			inParameters.put("p_i_v_start_date",
					RutDatabase.stringToDb(IjsHelper.getDateFromRange(dateRange, "START_DATE")).toUpperCase());
			inParameters.put("p_i_v_end_date",
					RutDatabase.stringToDb(IjsHelper.getDateFromRange(dateRange, "END_DATE")).toUpperCase());
			inParameters.put("p_i_v_find_in", RutDatabase.stringToDb(searchParam.getFindIn()).toUpperCase());
			inParameters.put("p_i_v_find_val", RutDatabase.stringToDb(searchParam.getFindValue()).toUpperCase());
			inParameters.put("p_i_v_vend_cd", RutDatabase.stringToDb(searchParam.getVendorCode()).toUpperCase());
			inParameters.put("p_i_v_frm_loc_typ",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(searchParam.getFromLocType())));
			inParameters.put("p_i_v_frm_loc", RutDatabase.stringToDb(searchParam.getFromLocation()).toUpperCase());
			inParameters.put("p_i_v_frm_trm", RutDatabase.stringToDb(searchParam.getFromTerminal()).toUpperCase());
			inParameters.put("p_i_v_to_loc_typ",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(searchParam.getToLocType())));
			inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(searchParam.getToLocation()).toUpperCase());
			inParameters.put("p_i_v_to_trm", RutDatabase.stringToDb(searchParam.getToTerminal()).toUpperCase());
			inParameters.put("p_i_v_cnt_rout", RutDatabase.stringToDb(searchParam.getContractNumber()).toUpperCase()); // roomy
			inParameters.put("p_i_v_country_code", RutDatabase.stringToDb(searchParam.getCountryCode()).toUpperCase());
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
			// roomy start
			inParameters.put("p_i_v_priority", RutDatabase.stringToDb(searchParam.getPriority()).toUpperCase());

			if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("suspend")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("S").toUpperCase());
			} else if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("active")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("A"));
			} else if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("Entry")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("E"));
			} else if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("Pending")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("P"));
			} else if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("expired")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("R"));
			} else if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("all")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb(""));
			} else {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("A"));
			}
			inParameters.put("p_i_v_pur_term", RutDatabase.stringToDb(searchParam.getPurchaseTerm()).toUpperCase());
			if ("All".equalsIgnoreCase(searchParam.getPurchaseTerm())) {
				inParameters.put("p_i_v_pur_term", RutDatabase.stringToDb(null));
			}

			inParameters.put("p_i_v_filter_by", RutDatabase.stringToDb(searchParam.getFilterBy()).toUpperCase());
		
			if (searchParam.getDomInn() != null && searchParam.getDomInn().equalsIgnoreCase("Domestic")) {
				inParameters.put("p_i_v_Dom_Inn", RutDatabase.stringToDb("D").toUpperCase());
			} else if (searchParam.getDomInn() != null && searchParam.getDomInn().equalsIgnoreCase("International")) {
				inParameters.put("p_i_v_Dom_Inn", RutDatabase.stringToDb("I"));
			} else {
				inParameters.put("p_i_v_Dom_Inn", RutDatabase.stringToDb(""));
			}
			outMap = execute(inParameters);
			// sorting logic

			Comparator<IjsContractSearchDTO> ladentComp = new Comparator<IjsContractSearchDTO>() {
					@Override
					public int compare(IjsContractSearchDTO e1, IjsContractSearchDTO e2) {
						if(e1==null && e2==null){
							return 0;
					    }
						if(e1==null || e2==null){
							return 0;
					    }
						if (e1.getLaden20() == null) {
							e1.setLaden20("-2");
						}
						if (e2.getLaden20() == null) {
							e2.setLaden20("-2");
						}
						
						if (e1.getLaden40() == null) {
							e1.setLaden40("-2");
						}
						if (e2.getLaden40() == null) {
							e2.setLaden40("-2");
						}
						
						if (e1.getLaden45() == null) {
							e1.setLaden45("-2");
						}
						if (e2.getLaden45() == null) {
							e2.setLaden45("-2");
						}
						
						
						if(!"-2".equals(e1.getLaden20()) && "-2".equals(e2.getLaden20())){
						       return -1;
					}else if(!"-2".equals(e1.getLaden20()) && "-2".equals(e2.getLaden20())) { 
				    Float a1= Float.parseFloat((e1.getLaden20()));
					Float a2= Float.parseFloat((e2.getLaden20()));
					return  a1.compareTo(a2);
					}
					else {
					Float  a1= Float.parseFloat(e1.getLaden20());
					Float a2= Float.parseFloat(e2.getLaden20());
					
					
					   return  a1.compareTo(a2);
					}
		
					}
				};
			
			Comparator<IjsContractSearchDTO> mtComp = new Comparator<IjsContractSearchDTO>() {
					@Override
					public int compare(IjsContractSearchDTO e1, IjsContractSearchDTO e2) {
						if(e1==null && e2==null){
							return 0;
					    }
						if(e1==null || e2==null){
							return 0;
					    }
						if (e1.getMt20() == null) {
							e1.setMt20("-1");
						}
						if (e2.getMt20() == null) {
							e2.setMt20("-1");
						}
						
						if (e1.getmT40() == null) {
							e1.setmT40("-1");
						}
						if (e2.getmT40() == null) {
							e2.setmT40("-1");
						}
						
						if (e1.getmT45() == null) {
							e1.setmT45("-1");
						}
						if (e2.getmT45() == null) {
							e2.setmT45("-1");
						}
						
						if(!"-1".equals(e1.getMt20()) && "-1".equals(e2.getMt20())){
							       return -1;
						}else if(!"-1".equals(e1.getMt20()) && !"-1".equals(e2.getMt20())) { 
					    Float a1= Float.parseFloat(e1.getMt20());
						Float a2= Float.parseFloat(e2.getMt20());
						Integer comp = a1.compareTo(a2);
						return comp;
						}
						else {
						Float  a1= Float.parseFloat(e1.getmT40());
						Float a2= Float.parseFloat(e2.getmT40());
						
						
						   return  a1.compareTo(a2);
						} 
				
					}
				};
			List<IjsContractSearchDTO> listOfLaden = mapOfRes.get("laden");

			if (listOfLaden != null) {

				try {
				Collections.sort(listOfLaden, ladentComp);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
			}

			List<IjsContractSearchDTO> listOfMt = mapOfRes.get("mt");

			if (listOfMt != null) {

				try {
				Collections.sort(listOfMt, mtComp);
				}catch(Exception e)
				{e.printStackTrace();
				}
			}

			List<IjsContractSearchDTO> bothAreNotNull = mapOfRes.get("bothAreNotNull");

			if (bothAreNotNull != null) {

				 try {
				 
				Collections.sort(bothAreNotNull, ladentComp);
				 }catch(Exception  e)
				 {
					 e.printStackTrace();
				 }
			}
			List<IjsContractSearchDTO> finalList = new ArrayList<IjsContractSearchDTO>();
			if (listOfLaden != null && listOfLaden.size() != 0) {
				
				finalList.addAll(listOfLaden);
			}
			if (listOfMt != null && listOfMt.size() != 0) {
				finalList.addAll(listOfMt);
			}
			if (bothAreNotNull != null && bothAreNotNull.size() != 0) {

				finalList.addAll(bothAreNotNull);
			}
			if (mapOfRes.get("bothNull") != null && mapOfRes.get("bothNull").size() != 0) {
				finalList.addAll(mapOfRes.get("bothNull"));
			}
			if ("MT".equalsIgnoreCase(searchParam.getSortIn())) {
				
				if (listOfMt != null) {
  
					Collections.sort(listOfMt, mtComp);
				}
				
				
				finalList = new ArrayList<IjsContractSearchDTO>();
				if (listOfMt != null && listOfMt.size() != 0) {
					finalList.addAll(listOfMt);
				}
				if (bothAreNotNull != null && bothAreNotNull.size() != 0) {

					finalList.addAll(bothAreNotNull);
				}

				if (listOfLaden != null && listOfLaden.size() != 0) {
					finalList.addAll(listOfLaden);
				}

				if (mapOfRes.get("bothNull") != null && mapOfRes.get("bothNull").size() != 0) {
					finalList.addAll(mapOfRes.get("bothNull"));
				}
			
				
			}
			//lp-mt
			
			if("lp".equalsIgnoreCase(searchParam.getOrderBy())&&"MT".equalsIgnoreCase(searchParam.getSortIn()))
			{
		     if (listOfMt != null) {
  
					Collections.sort(listOfMt, mtComp);
				
				
				
				finalList = new ArrayList<IjsContractSearchDTO>();
				if (listOfMt != null && listOfMt.size() != 0) {
					Collections.reverse(listOfMt);
					finalList.addAll(listOfMt);
					
				}
				if (bothAreNotNull != null && bothAreNotNull.size() != 0) {
					Collections.reverse(listOfMt);
					finalList.addAll(bothAreNotNull);
				}

				if (listOfLaden != null && listOfLaden.size() != 0) {
					Collections.reverse(listOfMt);
					finalList.addAll(listOfLaden);
				}

				if (mapOfRes.get("bothNull") != null && mapOfRes.get("bothNull").size() != 0) {
					Collections.reverse(listOfMt);
					finalList.addAll(mapOfRes.get("bothNull"));
				}
		     }
			
				//return finalList=orderByLP(listOfLaden, listOfMt, bothAreNotNull);
				return finalList;
			}
			
			
			  if("lp".equalsIgnoreCase(searchParam.getOrderBy())) 
			  {
				 return finalList=orderByLP(listOfLaden, listOfMt, bothAreNotNull);
			  }
			
			 
			return finalList;
			// return (List<IjsContractSearchDTO>)outMap.get("p_o_v_ijs_mapping_list");
		}	
	}
	
	
	

	private class IjsContractSearchRowMapper implements RowMapper {

		public Object mapRow(ResultSet resultSet, int row) {
			IjsContractSearchDTO ijsContractSearchDto = new IjsContractSearchDTO();

			try {

				ijsContractSearchDto.setCurrency(resultSet.getString("CURRENCY"));

				ijsContractSearchDto.setEndDate(resultSet.getString("END_DATE"));
				ijsContractSearchDto.setExempted("Y".equals(resultSet.getString("EXEMPTED")) ? true : false);
				ijsContractSearchDto.setHours(resultSet.getInt("HOURS"));
				ijsContractSearchDto.setFromLocType(resultSet.getString("FROM_LOC_TYP"));
				ijsContractSearchDto.setFromLocation(resultSet.getString("FROM_LOCATION"));
				ijsContractSearchDto.setFromTerminal(resultSet.getString("FROM_TERMINAL"));
				ijsContractSearchDto.setToLocType(resultSet.getString("TO_LOC_TYP"));
				ijsContractSearchDto.setToLocation(resultSet.getString("TO_LOCATION"));
				ijsContractSearchDto.setToTerminal(resultSet.getString("TO_TERMINAL"));
				ijsContractSearchDto.setPaymentFsc(resultSet.getString("CREATE_FSC"));

				ijsContractSearchDto.setRemarks(resultSet.getString("COMMENTS"));
				ijsContractSearchDto.setStartDate(resultSet.getString("START_DATE"));
				ijsContractSearchDto.setStatus(IjsHelper.getContractStatus(resultSet.getString("STATUS")));
				ijsContractSearchDto.setTransMode(resultSet.getString("TRANSPORT_MODE"));
				ijsContractSearchDto.setUom(resultSet.getString("DISTANCE_UOM"));
				ijsContractSearchDto.setVendorCode(resultSet.getString("VENDOR_CODE"));
				ijsContractSearchDto.setVendorName(resultSet.getString("VENDOR_NAME"));

				ijsContractSearchDto.setContractId(resultSet.getString("CONTRACT_NO"));
				ijsContractSearchDto.setRoutingId(resultSet.getInt("ROUTING_NO"));
				ijsContractSearchDto.setTerm(resultSet.getString("TERM"));// MD
				ijsContractSearchDto.setPurchaseTerm(resultSet.getString("SHIPMENT_TERMS"));

				ijsContractSearchDto.setLaden(resultSet.getString("LADEN"));
				ijsContractSearchDto.setLaden20(resultSet.getString("LADEN_20"));
				ijsContractSearchDto.setLaden40(resultSet.getString("LADEN_40"));
				ijsContractSearchDto.setLaden45(resultSet.getString("LADEN_45"));
				ijsContractSearchDto.setmT(resultSet.getString("MT"));
				ijsContractSearchDto.setMt20(resultSet.getString("MT_20"));
				ijsContractSearchDto.setmT40(resultSet.getString("MT_40"));
				ijsContractSearchDto.setmT45(resultSet.getString("MT_45"));
				if (ijsContractSearchDto.getLaden() == null && ijsContractSearchDto.getmT() == null) {
					if (mapOfRes.get("bothNull") == null) {
						mapOfRes.put("bothNull", new ArrayList<IjsContractSearchDTO>());
					}
					mapOfRes.get("bothNull").add(ijsContractSearchDto);
					// mapOfRes.computeIfPresent("bothNull", )
				} else if (ijsContractSearchDto.getLaden() != null && ijsContractSearchDto.getmT() != null) {
					if (mapOfRes.get("bothAreNotNull") == null) {
						mapOfRes.put("bothAreNotNull", new ArrayList<IjsContractSearchDTO>());
					}
					mapOfRes.get("bothAreNotNull").add(ijsContractSearchDto);
					// mapOfRes.computeIfPresent("bothNull", )
				} else if (ijsContractSearchDto.getLaden() == null && ijsContractSearchDto.getmT() != null) {
					if (mapOfRes.get("mt") == null) {
						mapOfRes.put("mt", new ArrayList<IjsContractSearchDTO>());
					}
					mapOfRes.get("mt").add(ijsContractSearchDto);
					// mapOfRes.computeIfPresent("bothNull", )
				} else if (ijsContractSearchDto.getLaden() != null && ijsContractSearchDto.getmT() == null) {
					if (mapOfRes.get("laden") == null) {
						mapOfRes.put("laden", new ArrayList<IjsContractSearchDTO>());
					}
					mapOfRes.get("laden").add(ijsContractSearchDto);
					// mapOfRes.computeIfPresent("bothNull", )
				}

				/*
				 * if(resultSet.getString("BARGE_VALUE")!=null) {
				 * if(resultSet.getString("BARGE_VALUE").equalsIgnoreCase("I")) {
				 * ijsContractSearchDto.setDomInn("International"); } else
				 * if(resultSet.getString("BARGE_VALUE").equalsIgnoreCase("D")) {
				 * ijsContractSearchDto.setDomInn("Domestic"); } else {
				 * ijsContractSearchDto.setDomInn(""); } }
				 * 
				 * else{ ijsContractSearchDto.setDomInn(""); }
				 */

				if (resultSet.getString("BARGE_VALUE") != null
						&& resultSet.getString("BARGE_VALUE").equalsIgnoreCase("I")) {
					ijsContractSearchDto.setDomInn("International");
					// System.out.println(resultSet.getString("BARGE_VALUE")+" Dom/In From I");
				} else if (resultSet.getString("BARGE_VALUE") != null
						&& resultSet.getString("BARGE_VALUE").equalsIgnoreCase("D")) {
					ijsContractSearchDto.setDomInn("Domestic");
					// System.out.println(resultSet.getString("BARGE_VALUE")+" Dom/In From D");
				} else {
					ijsContractSearchDto.setDomInn("");
				}
				try {
					ijsContractSearchDto.setPriority(Integer.parseInt(resultSet.getString("PRIORITY")));
					ijsContractSearchDto.setDays(Integer.parseInt(resultSet.getString("DAYS")));
					ijsContractSearchDto.setDistance(Integer.parseInt(resultSet.getString("DISTANCE")));
				} catch (NumberFormatException ne) {

				}

			} catch (SQLException e) {
				// TODO
				e.printStackTrace();
			} catch (NumberFormatException nfe) {

			}

			return ijsContractSearchDto;
		}
	}
	
	
	private List<IjsContractSearchDTO> orderByLP(List<IjsContractSearchDTO> listOfLaden ,List<IjsContractSearchDTO> listOfMt,List<IjsContractSearchDTO> 
	bothAreNotNull)
	{
		List<IjsContractSearchDTO> finalList = new ArrayList<IjsContractSearchDTO>();
		  Comparator<IjsContractSearchDTO> ladentCompReverse = new Comparator<IjsContractSearchDTO>() {
				@Override
				public int compare(IjsContractSearchDTO e1, IjsContractSearchDTO e2) {
					if (e1.getLaden20() == null) {
						e1.setLaden20("null");
					}
					if (e2.getLaden20() == null) {
						e2.setLaden20("null");
					}
					
					if (e1.getLaden40() == null) {
						e1.setLaden40("null");
					}
					if (e2.getLaden40() == null) {
						e2.setLaden40("null");
					}
					
					if (e1.getLaden45() == null) {
						e1.setLaden45("null");
					}
					if (e2.getLaden45() == null) {
						e2.setLaden45("null");
					}
					
					Float a1= Float.parseFloat(e1.getLaden20());
					Float a2= Float.parseFloat(e2.getLaden20());
					int comp = a2.compareTo(a1);
			        if (comp != 0) {
					   return comp;
					} 
					 a1= Float.parseFloat(e1.getLaden40());
					 a2= Float.parseFloat(e2.getLaden40());
					comp = a2.compareTo(a1);
					if (comp != 0) {
					   return comp;
					} 
					a1= Float.parseFloat(e1.getLaden45());
					 a2= Float.parseFloat(e2.getLaden45());
					comp = a2.compareTo(a1);
					if (comp != 0) {
					   return comp;
					} 
					return comp;
				}
			};
		
		Comparator<IjsContractSearchDTO> mtCompReverse = new Comparator<IjsContractSearchDTO>() {
				@Override
				public int compare(IjsContractSearchDTO e1, IjsContractSearchDTO e2) {
					if (e1.getMt20() == null) {
						e1.setMt20("null");
					}
					if (e2.getMt20() == null) {
						e2.setMt20("null");
					}
					
					if (e1.getmT40() == null) {
						e1.setmT40("null");
					}
					if (e2.getmT40() == null) {
						e2.setmT40("null");
					}
					
					if (e1.getmT45() == null) {
						e1.setmT45("null");
					}
					if (e2.getmT45() == null) {
						e2.setmT45("null");
					}
					
					Float a1= Float.parseFloat(e1.getMt20());
					Float a2= Float.parseFloat(e2.getMt20());
					Integer comp = a2.compareTo(a1);
					if (comp != 0) {
					   return comp;
					} 
					 a1= Float.parseFloat(e1.getmT40());
					 a2= Float.parseFloat(e2.getmT40());
					comp = a2.compareTo(a1);
					if (comp != 0) {
					   return comp;
					} 
					a1= Float.parseFloat(e1.getmT45());
					a2= Float.parseFloat(e2.getmT45());
					comp = a2.compareTo(a1);
					if (comp != 0) {
					   return comp;
					}
					
                   Float prioriryladen20= Float.parseFloat(e2.getLaden20());

					 Float prioriryladen40= Float.parseFloat(e2.getLaden40());
					 Float prioriryladen45= Float.parseFloat(e2.getLaden45());

					 e2.setSumOfCostLaden(prioriryladen20+prioriryladen40+prioriryladen45);
					
					 Float prioriryMt20= Float.parseFloat(e1.getMt20());
					 Float prioriryMt40= Float.parseFloat(e1.getmT40());
					 Float prioriryMt45= Float.parseFloat(e1.getmT45());

					 e1.setSumOfCostmT(prioriryMt20+prioriryMt20+prioriryMt20);
					
					
					 
					return comp;
				}
		};
		  
		  
		  
		  finalList = new ArrayList<IjsContractSearchDTO>();
		  
		 
		  
		  if (listOfLaden != null && listOfLaden.size() != 0) {
			  Collections.sort(listOfLaden, ladentCompReverse);
			  finalList.addAll(listOfLaden);
		    }
		  if (bothAreNotNull != null && bothAreNotNull.size() != 0) {
			  Collections.sort(bothAreNotNull, ladentCompReverse);
				finalList.addAll(bothAreNotNull);
			}
		  
			if (listOfMt != null && listOfMt.size() != 0) {
				Collections.sort(listOfMt, mtCompReverse);
				finalList.addAll(listOfMt);
			}
	      
		 // Collections.reverse(finalList);
		  
		  if (mapOfRes.get("bothNull") != null && mapOfRes.get("bothNull").size() != 0) {
				finalList.addAll(mapOfRes.get("bothNull"));
			}
		  
		  
		  
		  
	  return finalList;
	  
	}
	
	// ##01 END
	// ##03 BEGIN

	protected class IjsContractSaveStoredProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String PRR_IJS_CNTR_SAVE = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_SAVE";

		IjsContractSaveStoredProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PRR_IJS_CNTR_SAVE);
			declareParameter(new SqlInOutParameter("p_i_v_action_type", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_vendor", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_transport_mode", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_status", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_payment_fsc", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_currency", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_priority", Types.NUMERIC));
			declareParameter(new SqlInOutParameter("p_i_v_from_loc_type", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_from_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_from_terminal", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc_type", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_hours", Types.NUMERIC));
			declareParameter(new SqlInOutParameter("p_i_v_days", Types.NUMERIC));
			declareParameter(new SqlInOutParameter("p_i_v_distance", Types.NUMERIC));
			declareParameter(new SqlInOutParameter("p_i_v_uom", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_remarks", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_exempted", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_contract_no", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
			// nikash
			declareParameter(new SqlInOutParameter("p_i_v_barge", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_term_code", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_routing_id", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_contract_id", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			compile();

		}

		private Map saveORUpdateContracts(IjsContractVO ijsContractVO, IjsLocationVO ijsLocationVO, String userInfo,
				String action) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_action_type",
					RutDatabase.stringToDb(IjsHelper.getActionType(action)).toUpperCase());
			inParameters.put("p_i_v_vendor", RutDatabase.stringToDb(ijsContractVO.getVendorCode()).toUpperCase());
			inParameters.put("p_i_v_start_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getStartDate())).toUpperCase());
			inParameters.put("p_i_v_end_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getEndDate())).toUpperCase());
			inParameters.put("p_i_v_transport_mode",
					RutDatabase.stringToDb(IjsHelper.getTransCode(ijsContractVO.getTransMode())).toUpperCase());
			inParameters.put("p_i_v_status",
					RutDatabase.stringToDb(IjsHelper.getContractStatusCode(ijsContractVO.getStatus())).toUpperCase());
			inParameters.put("p_i_v_payment_fsc", RutDatabase.stringToDb(ijsLocationVO.getPaymentFsc()).toUpperCase());
			inParameters.put("p_i_v_currency", RutDatabase.stringToDb(ijsLocationVO.getCurrency()).toUpperCase());
			inParameters.put("p_i_v_priority", ijsLocationVO.getPriority());

			inParameters.put("p_i_v_from_loc_type",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsLocationVO.getFromLocType())).toUpperCase());
			inParameters.put("p_i_v_from_location",
					RutDatabase.stringToDb(ijsLocationVO.getFromLocation()).toUpperCase());
			inParameters.put("p_i_v_from_terminal",
					RutDatabase.stringToDb(ijsLocationVO.getFromTerminal()).toUpperCase());
			inParameters.put("p_i_v_to_loc_type",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsLocationVO.getToLocType())).toUpperCase());
			inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(ijsLocationVO.getToLocation()).toUpperCase());
			inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(ijsLocationVO.getToTerminal()).toUpperCase());

			inParameters.put("p_i_v_hours", ijsContractVO.getHours());
			inParameters.put("p_i_v_days", ijsContractVO.getDays());
			inParameters.put("p_i_v_distance", ijsContractVO.getDistance());
			inParameters.put("p_i_v_uom", RutDatabase.stringToDb(ijsContractVO.getUom()).toUpperCase());
			inParameters.put("p_i_v_remarks", RutDatabase.stringToDb(ijsContractVO.getRemarks()).toUpperCase());
			inParameters.put("p_i_v_exempted", RutDatabase.stringToDb(ijsContractVO.isExempted() ? "Y" : "N"));
			inParameters.put("p_i_v_contract_no", RutDatabase.stringToDb(ijsContractVO.getContractId()).toUpperCase());
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			// nikash
			// inParameters.put("p_i_v_barge" ,
			// RutDatabase.stringToDb(ijsContractVO.getBargeValue()).toUpperCase());
		if (ijsLocationVO.getBargeValue() != null && ijsLocationVO.getBargeValue().equalsIgnoreCase("Domestic")) {
				inParameters.put("p_i_v_barge", RutDatabase.stringToDb("D").toUpperCase());
			} else if (ijsLocationVO.getBargeValue() != null
					&& ijsLocationVO.getBargeValue().equalsIgnoreCase("International")) {
				inParameters.put("p_i_v_barge", RutDatabase.stringToDb("I"));
			}else {
				inParameters.put("p_i_v_barge", "");
			}
			if (ijsContractVO.getTermVal() != null) {
				inParameters.put("p_i_v_term_code", RutDatabase.stringToDb(ijsContractVO.getTermVal()).toUpperCase());
			} else if (ijsContractVO.getTerm() != null) {
				inParameters.put("p_i_v_term_code", RutDatabase.stringToDb(ijsContractVO.getTerm()).toUpperCase());
			} else {
				inParameters.put("p_i_v_term_code", "");
			}

			/*
			 * else if(ijsContractVO.getTerm().size()>0){ inParameters.put("p_i_v_term_code"
			 * ,
			 * RutDatabase.stringToDb(IjsHelper.convertListToString(ijsContractVO.getTerm())
			 * ).toUpperCase()); } else{ inParameters.put("p_i_v_term_code" , ""); }
			 */

			outMap = execute(inParameters);

			return outMap;
		}

		private Map saveORUpdateContract(IjsContractVO ijsContractVO, String userInfo, String action) {
			Map outMap;
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_action_type",
					RutDatabase.stringToDb(IjsHelper.getActionType(action)).toUpperCase());
			inParameters.put("p_i_v_vendor", RutDatabase.stringToDb(ijsContractVO.getVendorCode()).toUpperCase());
			inParameters.put("p_i_v_start_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getStartDate())).toUpperCase());
			inParameters.put("p_i_v_end_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getEndDate())).toUpperCase());
			inParameters.put("p_i_v_transport_mode",
					RutDatabase.stringToDb(IjsHelper.getTransCode(ijsContractVO.getTransMode())).toUpperCase());
			inParameters.put("p_i_v_status",
					RutDatabase.stringToDb(IjsHelper.getContractStatusCode(ijsContractVO.getStatus())).toUpperCase());
			inParameters.put("p_i_v_payment_fsc", RutDatabase.stringToDb(ijsContractVO.getPaymentFsc()).toUpperCase());
			inParameters.put("p_i_v_currency", RutDatabase.stringToDb(ijsContractVO.getCurrency()).toUpperCase());
			inParameters.put("p_i_v_priority", ijsContractVO.getPriority());
			inParameters.put("p_i_v_from_loc_type",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsContractVO.getFromLocType())).toUpperCase());
			inParameters.put("p_i_v_from_location",
					RutDatabase.stringToDb(ijsContractVO.getFromLocation()).toUpperCase());
			inParameters.put("p_i_v_from_terminal",
					RutDatabase.stringToDb(ijsContractVO.getFromTerminal()).toUpperCase());
			inParameters.put("p_i_v_to_loc_type",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsContractVO.getToLocType())).toUpperCase());
			inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(ijsContractVO.getToLocation()).toUpperCase());
			inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(ijsContractVO.getToTerminal()).toUpperCase());
			inParameters.put("p_i_v_hours", ijsContractVO.getHours());
			inParameters.put("p_i_v_days", ijsContractVO.getDays());
			inParameters.put("p_i_v_distance", ijsContractVO.getDistance());
			inParameters.put("p_i_v_uom", RutDatabase.stringToDb(ijsContractVO.getUom()).toUpperCase());
			inParameters.put("p_i_v_remarks", RutDatabase.stringToDb(ijsContractVO.getRemarks()).toUpperCase());
			inParameters.put("p_i_v_exempted", RutDatabase.stringToDb(ijsContractVO.isExempted() ? "Y" : "N"));
			inParameters.put("p_i_v_contract_no", RutDatabase.stringToDb(ijsContractVO.getContractId()).toUpperCase());
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			// nikash
			// inParameters.put("p_i_v_barge" ,
			// RutDatabase.stringToDb(ijsContractVO.getBargeValue()).toUpperCase());

			if (ijsContractVO.getBargeValue() != null && ijsContractVO.getBargeValue().equalsIgnoreCase("Domestic")) {
				inParameters.put("p_i_v_barge", RutDatabase.stringToDb("D").toUpperCase());
			} else if (ijsContractVO.getBargeValue() != null
					&& ijsContractVO.getBargeValue().equalsIgnoreCase("International")) {
				inParameters.put("p_i_v_barge", RutDatabase.stringToDb("I"));
			}else {
				inParameters.put("p_i_v_barge", "");
			}

			if (ijsContractVO.getTermVal() != null) {
				inParameters.put("p_i_v_term_code", RutDatabase.stringToDb(ijsContractVO.getTermVal()).toUpperCase());
			} else if (ijsContractVO.getTerm() != null) {
				inParameters.put("p_i_v_term_code", RutDatabase.stringToDb(ijsContractVO.getTerm()).toUpperCase());
			} else {
				inParameters.put("p_i_v_term_code", "");
			}

			/*
			 * else if(ijsContractVO.getTerm().size()>0){ inParameters.put("p_i_v_term_code"
			 * ,
			 * RutDatabase.stringToDb(IjsHelper.convertListToString(ijsContractVO.getTerm())
			 * ).toUpperCase()); } else{ inParameters.put("p_i_v_term_code" , ""); }
			 */

			outMap = execute(inParameters);

			return outMap;
		}
	}
	// ##03 END
	// ##04 BEGIN

	protected class IjsContractPriorityValidationProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String PRR_IJS_CNTR_VAL = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_VAL";

		IjsContractPriorityValidationProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PRR_IJS_CNTR_VAL);
			declareParameter(new SqlInOutParameter("p_i_v_priority", Types.NUMERIC));
			declareParameter(new SqlInOutParameter("p_i_v_from_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_from_terminal", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_transport_mode", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_vendor", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			compile();
		}

		private Map validateContractPriorty(IjsContractVO ijsContractVO, IjsLocationVO ijsLocationVO) {
			Map outMap;
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_priority", ijsLocationVO.getPriority());
			inParameters.put("p_i_v_from_location",
					RutDatabase.stringToDb(ijsLocationVO.getFromLocation()).toUpperCase());
			inParameters.put("p_i_v_from_terminal",
					RutDatabase.stringToDb(ijsLocationVO.getFromTerminal()).toUpperCase());
			inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(ijsLocationVO.getToLocation()).toUpperCase());
			inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(ijsLocationVO.getToTerminal()).toUpperCase());
			inParameters.put("p_i_v_transport_mode",
					RutDatabase.stringToDb(IjsHelper.getTransCode(ijsContractVO.getTransMode())).toUpperCase());
			inParameters.put("p_i_v_start_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getStartDate())).toUpperCase());
			inParameters.put("p_i_v_end_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getEndDate())).toUpperCase());
			inParameters.put("p_i_v_vendor", RutDatabase.stringToDb(ijsContractVO.getVendorCode()).toUpperCase());
			outMap = execute(inParameters);
			return outMap;
		}

	}

	protected class IjsMaxContractPriorityProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String PRR_IJS_CNTR_MAX_PRIORITY = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_MAX_PRIORITY";

		IjsMaxContractPriorityProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PRR_IJS_CNTR_MAX_PRIORITY);
			declareParameter(new SqlInOutParameter("p_i_v_from_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_from_terminal", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_transport_mode", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_vendor", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_max_priority", Types.INTEGER));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			compile();
		}

		private Map getMaxContractPriorty(IjsContractVO ijsContractVO, IjsLocationVO ijsLocationVO) {
			Map outMap;
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_from_location",
					RutDatabase.stringToDb(ijsLocationVO.getFromLocation()).toUpperCase());
			inParameters.put("p_i_v_from_terminal",
					RutDatabase.stringToDb(ijsLocationVO.getFromTerminal()).toUpperCase());
			inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(ijsLocationVO.getToLocation()).toUpperCase());
			inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(ijsLocationVO.getToTerminal()).toUpperCase());
			inParameters.put("p_i_v_transport_mode",
					RutDatabase.stringToDb(IjsHelper.getTransCode(ijsContractVO.getTransMode())).toUpperCase());
			inParameters.put("p_i_v_start_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getStartDate())).toUpperCase());
			inParameters.put("p_i_v_end_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getEndDate())).toUpperCase());
			inParameters.put("p_i_v_vendor", RutDatabase.stringToDb(ijsContractVO.getVendorCode()).toUpperCase());
			outMap = execute(inParameters);
			return outMap;
		}
	}

	protected class IjsContractForSameVendorValidationProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String PRR_IJS_CNTR_VAL = "PCR_IJS_CNTR_MAINTENANCE.CNTR_CHECK_SAME_VENDOR";

		IjsContractForSameVendorValidationProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PRR_IJS_CNTR_VAL);
			declareParameter(new SqlInOutParameter("p_i_v_from_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_from_terminal", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_transport_mode", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_vendor", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_barge", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			compile();
		}

		private Map validateContractForSameVendor(IjsContractVO ijsContractVO, IjsLocationVO ijsLocationVO) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_from_location",
					RutDatabase.stringToDb(ijsLocationVO.getFromLocation()).toUpperCase());
			inParameters.put("p_i_v_from_terminal",
					RutDatabase.stringToDb(ijsLocationVO.getFromTerminal()).toUpperCase());
			inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(ijsLocationVO.getToLocation()).toUpperCase());
			inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(ijsLocationVO.getToTerminal()).toUpperCase());
			inParameters.put("p_i_v_transport_mode",
					RutDatabase.stringToDb(IjsHelper.getTransCode(ijsContractVO.getTransMode())).toUpperCase());
			inParameters.put("p_i_v_start_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getStartDate())).toUpperCase());
			inParameters.put("p_i_v_end_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getEndDate())).toUpperCase());
			inParameters.put("p_i_v_vendor", RutDatabase.stringToDb(ijsContractVO.getVendorCode()).toUpperCase());
			
			if (ijsLocationVO.getBargeValue() != null && ijsLocationVO.getBargeValue().equalsIgnoreCase("Domestic")) {
				inParameters.put("p_i_v_barge", RutDatabase.stringToDb("D").toUpperCase());
			} else if (ijsLocationVO.getBargeValue() != null
					&& ijsLocationVO.getBargeValue().equalsIgnoreCase("International")) {
				inParameters.put("p_i_v_barge", RutDatabase.stringToDb("I"));
			}else {
				inParameters.put("p_i_v_barge", "");
			}
			//inParameters.put("p_i_v_barge", RutDatabase.stringToDb(ijsLocationVO.getBargeValue()).toUpperCase());
			outMap = execute(inParameters);
			return outMap;
		}
	}

	protected class IjsContractVendorValidationProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String PRR_IJS_VND_VAL = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_VND_VAL";

		IjsContractVendorValidationProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PRR_IJS_VND_VAL);
			declareParameter(new SqlInOutParameter("p_i_v_vendor", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			compile();
		}

		private Map validateVendor(String vendorID) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_vendor", RutDatabase.stringToDb(vendorID).toUpperCase());
			outMap = execute(inParameters);
			return outMap;
		}
	}

	protected class IjsContractUserValidationProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String PRR_IJS_USR_VAL = "PCR_IJS_CNTR_MAINTENANCE.CNTR_LU_USR_CNTR_CREATION_CHK";

		IjsContractUserValidationProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PRR_IJS_USR_VAL);
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_from_loc", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_vendor_cd", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_error_cd", Types.VARCHAR));

			compile();
		}

		private String validateUserToCreateContract(String userId, String fromLoc, String toLoc, String vendorCd) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
			inParameters.put("p_i_v_from_loc", RutDatabase.stringToDb(fromLoc).toUpperCase());
			inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(toLoc).toUpperCase());
			inParameters.put("p_i_v_vendor_cd", RutDatabase.stringToDb(vendorCd).toUpperCase());
			outMap = execute(inParameters);
			return (String) outMap.get("p_o_v_error_cd");
		}
	}

	protected class IjsContractDeleteStoredProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String PRR_IJS_CNTR_DELETE = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_DELETE";

		IjsContractDeleteStoredProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PRR_IJS_CNTR_DELETE);
			declareParameter(new SqlInOutParameter("p_i_v_contract_no", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			compile();
		}

		private String deleteContract(String contractId, String userInfo) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_contract_no", RutDatabase.stringToDb(contractId).toUpperCase());
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			outMap = execute(inParameters);

			return (String) outMap.get("p_o_v_err_cd");
		}
	}

	protected class IjsContractSuspendStoredProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String PRR_IJS_CNTR_SUSPEND = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_SUSPEND";

		IjsContractSuspendStoredProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PRR_IJS_CNTR_SUSPEND);
			declareParameter(new SqlInOutParameter("p_i_v_contract_no", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			compile();
		}

		private String suspendContract(String contractId, String userInfo) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_contract_no", RutDatabase.stringToDb(contractId).toUpperCase());
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			outMap = execute(inParameters);
			return (String) outMap.get("p_o_v_err_cd");
		}
	}

	protected class IjsContractCompareStoredProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String PRR_IJS_CNTR_COMPARE = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_COMPARE";

		IjsContractCompareStoredProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, PRR_IJS_CNTR_COMPARE);
			declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_vend_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_frm_loc", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_frm_trm", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_trm", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, rowMapper));
			compile();
		}

		private List<IjsContractSearchDTO> getContractList(IjsContractVO ijsContractVO, String userInfo) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_start_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getStartDate())).toUpperCase());
			inParameters.put("p_i_v_end_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getEndDate())).toUpperCase());
			inParameters.put("p_i_v_vend_cd", RutDatabase.stringToDb(ijsContractVO.getVendorCode()).toUpperCase());
			inParameters.put("p_i_v_frm_loc", RutDatabase.stringToDb(ijsContractVO.getFromLocation()).toUpperCase());
			inParameters.put("p_i_v_frm_trm", RutDatabase.stringToDb(ijsContractVO.getFromTerminal()).toUpperCase());
			inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(ijsContractVO.getToLocation()).toUpperCase());
			inParameters.put("p_i_v_to_trm", RutDatabase.stringToDb(ijsContractVO.getToTerminal()).toUpperCase());
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			outMap = execute(inParameters);
			return (List<IjsContractSearchDTO>) outMap.get("p_o_v_ijs_mapping_list");
		}
	}

	protected class IjsContractHistoryStoredProcedure extends StoredProcedure {
		private static final String SQL_RLTD_STR_FOR_HISTORY = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_HISTORY";

		IjsContractHistoryStoredProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_RLTD_STR_FOR_HISTORY);
			declareParameter(new SqlInOutParameter("p_i_v_contract_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_ijs_cntr_hsty__list", OracleTypes.CURSOR, rowMapper));
			compile();
		}

		private List<IjsContractHistory> getHistoryList(String contractId, String userInfo) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_contract_id", RutDatabase.stringToDb(contractId).toUpperCase());
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			outMap = execute(inParameters);
			return (List<IjsContractHistory>) outMap.get("p_o_v_ijs_cntr_hsty__list");
		}
	}

	private class IjsContractHistoryRowMapper implements RowMapper {
		public IjsContractHistory mapRow(ResultSet resultSet, int row) throws SQLException {
			IjsContractHistory history = new IjsContractHistory();
			history.setActivity(resultSet.getString("ACTIVITY"));
			history.setActivityDate(resultSet.getString("ACTIVITY_DATE"));
			history.setActivityUser(resultSet.getString("LOGGED_IN_USER"));
			history.setActivitySeq(Double.parseDouble(resultSet.getString("CREATED_SEQ")));
			return history;
		}
	}

	protected class IjsContractGPPStoredProcedure extends StoredProcedure {
		private static final String SQL_GPP = "PCR_IJS_CNTR_MAINTENANCE.CNTR_GEN_PORT_PAIR";

		IjsContractGPPStoredProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, SQL_GPP);
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_next_time", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			compile();
		}

		private String doGenPortPair(String userId) throws IJSException {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
			outMap = execute(inParameters);
			String errorCd = (String) outMap.get("p_o_v_err_cd");
			if (errorCd != null && !IjsErrorCode.DB_IJS_CNTR_EX_10034.name().equals(errorCd)) {
				throw new IJSException(errorCd);
			}
			return (String) outMap.get("p_o_v_next_time");
		}
	}

	protected class IjsCntrVendorDetailsStrdProce extends StoredProcedure {
		private static final String SQL_RLTD_STR_FOR_VENDOR = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_VENDOR_DETAILS";

		IjsCntrVendorDetailsStrdProce(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_RLTD_STR_FOR_VENDOR);
			declareParameter(new SqlInOutParameter("p_i_v_vendor_code", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_ijs_vndr_details_list", OracleTypes.CURSOR, rowMapper));
			compile();
		}

		private List<IjsVendorDetails> getVendorDetailsList(String vendorCode, String userInfo) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_vendor_code", RutDatabase.stringToDb(vendorCode).toUpperCase());
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			outMap = execute(inParameters);
			return (List<IjsVendorDetails>) outMap.get("p_o_v_ijs_vndr_details_list");
		}
	}

	private class IjsCntrVendorDetailsRowMapper implements RowMapper {

		public IjsVendorDetails mapRow(ResultSet resultSet, int i) throws SQLException {
			IjsVendorDetails details = new IjsVendorDetails();
			IjsVendorDetails.Contact contact = details.new Contact();
			contact.setEmail(resultSet.getString("email"));
			contact.setPhnExtenstion(resultSet.getString("extension"));
			contact.setPhone1(resultSet.getString("phone1"));
			contact.setPhone2(resultSet.getString("phone2"));
			details.setContact(contact);
			IjsVendorDetails.Address address = details.new Address();
			address.setAdd1(resultSet.getString("add1"));
			address.setAdd2(resultSet.getString("add2"));
			address.setAdd3(resultSet.getString("add3"));
			address.setAdd4(resultSet.getString("add4"));
			details.setAddress(address);
			details.setCity(resultSet.getString("city"));
			details.setCountry(resultSet.getString("country"));
			details.setCreditDays(resultSet.getString("credit_days"));
			details.setCurrency(resultSet.getString("currency"));
			details.setFinanceInterVndr(resultSet.getString("finance_interface_vendor"));
			details.setHeadquatersCode(resultSet.getString("headquaters_code"));
			details.setMainContact(resultSet.getString("main_contact"));
			details.setName2(resultSet.getString("name2"));
			details.setOnlineUser(resultSet.getString("online_user"));
			details.setPurchaseOrderRequired(resultSet.getString("purchase_order_required"));
			details.setResponsibleFSC(resultSet.getString("responsible_fsc"));
			details.setSCACCode(resultSet.getString("scac_code"));
			details.setState(resultSet.getString("state"));
			details.setTitle(resultSet.getString("title"));
			details.setVatRegistration(resultSet.getString("vat_registration"));
			details.setVendor(resultSet.getString("vendor"));
			details.setVendorEdiCode(resultSet.getString("vendor_edi_code"));
			details.setVendorPayToNumber(resultSet.getString("vendor_pay_to_number"));
			details.setVendorType(IjsHelper.getVendorValue(resultSet.getString("vendor_type")));
			details.setZip(resultSet.getString("zip"));
			details.setFinanceInterVndrCode(resultSet.getString("finance_interface_vendor_code"));
			details.setAbbreviation(resultSet.getString("abbrivation"));
			details.setOperatorCode(resultSet.getString("operation_code"));
			details.setNetOff_AROrAP(resultSet.getString("net_off"));
			details.setModeOfPayment(resultSet.getString("payment_mode"));
			details.setAdvancePayment(resultSet.getString("advance_payment"));
			return details;
		}
	}
	// ##04 END

	protected class GetIjsContractDownloadStoredProcedure extends StoredProcedure {
		/** Stored Procedure name */
		private static final String SQL_IJS_CNTR_MAIN_DOWNLOAD = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_MAIN_DOWNLOAD";

		GetIjsContractDownloadStoredProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapperForCostRate,
				RowMapper rowMapperForBillRate) {
			super(jdbcTemplate, SQL_IJS_CNTR_MAIN_DOWNLOAD);
			declareParameter(
					new SqlOutParameter("p_o_v_ijs_cost_contract_list", OracleTypes.CURSOR, rowMapperForCostRate));
			declareParameter(
					new SqlOutParameter("p_o_v_ijs_bill_contract_list", OracleTypes.CURSOR, rowMapperForBillRate));
			declareParameter(new SqlInOutParameter("p_i_v_trans_mod", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_find_val", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_vend_cd", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_frm_loc_typ", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_frm_loc", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_frm_trm", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc_typ", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_to_trm", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_cnt_rout", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_country_code", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_session_id", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapperForCostRate));

			declareParameter(new SqlInOutParameter("p_i_v_priority", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlInOutParameter("p_i_v_status", Types.VARCHAR, rowMapperForCostRate));

			declareParameter(new SqlOutParameter(" p_i_v_pur_term", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlOutParameter(" p_i_v_filter_by", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlOutParameter(" p_i_v_Dom_Inn", Types.VARCHAR, rowMapperForCostRate));
			declareParameter(new SqlOutParameter("p_io_v_err_cd", Types.VARCHAR, rowMapperForCostRate));
			compile();

		}

		public Map getIjsContractDownloadList(String transMode, String dateRange, String userId,
				IjsContractSearchParamVO searchParam, List<IjsContractDownloadDTO> costRateResult,
				List<IjsContractDownloadDTO> billRateResult, String sessionId) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			System.out.println("download>..>>>>>>" + searchParam);
			inParameters.put("p_i_v_trans_mod", RutDatabase.stringToDb(IjsHelper.getTransCode(transMode)));
			inParameters.put("p_i_v_start_date",
					RutDatabase.stringToDb(IjsHelper.getDateFromRange(dateRange, "START_DATE")).toUpperCase());
			inParameters.put("p_i_v_end_date",
					RutDatabase.stringToDb(IjsHelper.getDateFromRange(dateRange, "END_DATE")).toUpperCase());
			inParameters.put("p_i_v_find_in", RutDatabase.stringToDb(searchParam.getFindIn()).toUpperCase());
			inParameters.put("p_i_v_find_val", RutDatabase.stringToDb(searchParam.getFindValue()).toUpperCase());
			inParameters.put("p_i_v_vend_cd", RutDatabase.stringToDb(searchParam.getVendorCode()).toUpperCase());
			inParameters.put("p_i_v_frm_loc_typ",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(searchParam.getFromLocType())));
			inParameters.put("p_i_v_frm_loc", RutDatabase.stringToDb(searchParam.getFromLocation()).toUpperCase());
			inParameters.put("p_i_v_frm_trm", RutDatabase.stringToDb(searchParam.getFromTerminal()).toUpperCase());
			inParameters.put("p_i_v_to_loc_typ",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(searchParam.getToLocType())));
			inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(searchParam.getToLocation()).toUpperCase());
			inParameters.put("p_i_v_to_trm", RutDatabase.stringToDb(searchParam.getToTerminal()).toUpperCase());
			inParameters.put("p_i_v_cnt_rout", RutDatabase.stringToDb(searchParam.getContractNumber()).toUpperCase());
			inParameters.put("p_i_v_country_code", RutDatabase.stringToDb(searchParam.getCountryCode()).toUpperCase());
			inParameters.put("p_i_v_session_id", sessionId);
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
			inParameters.put("p_i_v_priority", RutDatabase.stringToDb(searchParam.getPriority()).toUpperCase());

			if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("suspend")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("S").toUpperCase());
			} else if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("active")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("A"));
			} else if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("Entry")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("E"));
			} else if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("Pending")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("P"));
			} else if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("expired")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("R"));
			} else if (searchParam.getStatus() != null && searchParam.getStatus().equalsIgnoreCase("all")) {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb(""));
			} else {
				inParameters.put("p_i_v_status", RutDatabase.stringToDb("A"));
			}
			inParameters.put("p_i_v_pur_term", RutDatabase.stringToDb(searchParam.getPurchaseTerm()).toUpperCase());
			inParameters.put("p_i_v_filter_by", RutDatabase.stringToDb(searchParam.getFilterBy()).toUpperCase());
			if (searchParam.getDomInn() != null && searchParam.getDomInn().equalsIgnoreCase("Domestic")) {
				inParameters.put("p_i_v_Dom_Inn", RutDatabase.stringToDb("D").toUpperCase());
			} else if (searchParam.getDomInn() != null && searchParam.getDomInn().equalsIgnoreCase("International")) {
				inParameters.put("p_i_v_Dom_Inn", RutDatabase.stringToDb("I"));
			} else {
				inParameters.put("p_i_v_Dom_Inn", RutDatabase.stringToDb(""));
			}

			outMap = execute(inParameters);
			costRateResult = (List<IjsContractDownloadDTO>) outMap.get("p_o_v_ijs_cost_contract_list");
			billRateResult = (List<IjsContractDownloadDTO>) outMap.get("p_o_v_ijs_bill_contract_list");
			return outMap;
		}
	}

	private class IjsCostContractDownloadRowMapper implements RowMapper {

		public Object mapRow(ResultSet resultSet, int row) {
			IjsContractDownloadDTO ijsContractDownloadDto = new IjsContractDownloadDTO();
			try {
				ijsContractDownloadDto.setVendorCode(resultSet.getString("VENDOR_CODE"));
				ijsContractDownloadDto.setVendorName(resultSet.getString("VENDOR_NAME"));
				ijsContractDownloadDto.setContractId(resultSet.getString("CONTRACT_NO"));
				ijsContractDownloadDto.setRoutingId(resultSet.getInt("ROUTING_NO"));
				ijsContractDownloadDto.setContractStartDate(resultSet.getString("START_DATE"));
				ijsContractDownloadDto.setContractEndDate(resultSet.getString("END_DATE"));
				ijsContractDownloadDto.setTransMode(resultSet.getString("MOT"));
				ijsContractDownloadDto.setStatus(resultSet.getString("ROUTE_RATE_STATUS"));
				ijsContractDownloadDto.setPaymentFsc(resultSet.getString("PAYMENT_FSC"));
				ijsContractDownloadDto.setContractCurrency(resultSet.getString("CURRENCY"));
				ijsContractDownloadDto.setPriority(resultSet.getInt("PRIORITY"));
				ijsContractDownloadDto.setFromLocType(resultSet.getString("FROM_MODE"));
				ijsContractDownloadDto.setFromLocation(resultSet.getString("FROM_LOCATION"));
				ijsContractDownloadDto.setFromTerminal(resultSet.getString("FROM_TERMINAL"));
				ijsContractDownloadDto.setToLocType(resultSet.getString("TO_MODE"));
				ijsContractDownloadDto.setToLocation(resultSet.getString("TO_LOCATION"));
				ijsContractDownloadDto.setToTerminal(resultSet.getString("TO_TERMINAL"));
				ijsContractDownloadDto.setDays(resultSet.getInt("DAYS"));
				ijsContractDownloadDto.setHours(resultSet.getInt("HOURS"));
				ijsContractDownloadDto.setDistance(resultSet.getInt("DISTANCE"));
				ijsContractDownloadDto.setContractUom(resultSet.getString("DISTANCE_UOM"));
				ijsContractDownloadDto.setExempted(resultSet.getString("EXEMPTED"));

				ijsContractDownloadDto.setCostRateSequenceNum(resultSet.getInt("COST_SEQ_NUM"));

				ijsContractDownloadDto.setRateStartDate(resultSet.getString("COST_RATE_START_DATE"));
				ijsContractDownloadDto.setRateEndDate(resultSet.getString("COST_RATE_END_DATE"));
				ijsContractDownloadDto.setService(resultSet.getString("SERVICE"));
				ijsContractDownloadDto.setVesselCode(resultSet.getString("VESSEL"));
				ijsContractDownloadDto.setRateBasis(resultSet.getString("RATE_BASIS"));
				ijsContractDownloadDto.setEqCatq(resultSet.getString("EQUIP_CATG"));
				ijsContractDownloadDto.setChargeCode(resultSet.getString("CHARGE_CODE"));
				ijsContractDownloadDto.setCurrency(resultSet.getString("COST_RATE_CURRENCY"));
				// ijsContractDownloadDto.setTerm(resultSet.getString("TERM"));
				ijsContractDownloadDto.setMtOrLaden(resultSet.getString("MT_LADEN"));
				ijsContractDownloadDto.setRateStatus(resultSet.getString("COST_STATUS"));
				ijsContractDownloadDto.setCalcMethod(resultSet.getString("CALCULATION_METHOD"));
				ijsContractDownloadDto.setEqType(resultSet.getString("EQ_TYPE"));
				ijsContractDownloadDto.setUpto(resultSet.getInt("UPTO"));
				ijsContractDownloadDto.setUom(resultSet.getString("UNIT"));
				ijsContractDownloadDto.setImpOrExp(resultSet.getString("IMP_OR_EXP"));
				ijsContractDownloadDto.setSplHandling(resultSet.getString("SPL_HANDLING"));
				ijsContractDownloadDto.setPortClassCode(resultSet.getString("PORT_CODE"));
				ijsContractDownloadDto.setImdgDetails(resultSet.getString("IMDG_CODE"));
				ijsContractDownloadDto.setOogSetup(resultSet.getString("OOG_CODE"));
				ijsContractDownloadDto.setRate20(resultSet.getDouble("RATE20"));
				ijsContractDownloadDto.setRate40(resultSet.getDouble("RATE40"));
				ijsContractDownloadDto.setRate45(resultSet.getDouble("RATE45"));
				ijsContractDownloadDto.setLumpSum(resultSet.getString("LUMP_SUM"));
				ijsContractDownloadDto.setPerTrip(resultSet.getString("PER_TRIP"));
				// ijsContractDownloadDto.setSplCostByBlOrBooking(resultSet.getString("CURRENCY"));
				ijsContractDownloadDto.setTerm(resultSet.getString("TERM"));
				ijsContractDownloadDto.setDetailSeqNum(resultSet.getString("COST_DETAIL_SEQ"));
				ijsContractDownloadDto.setSpCustomers(resultSet.getString("SP_CST_BY_CUST"));
				ijsContractDownloadDto.setpTerm(resultSet.getString("TERM"));
				ijsContractDownloadDto.setFilterBy(resultSet.getString("TERM"));

			} catch (SQLException e) {
				// TODO
				e.printStackTrace();
			}
			return ijsContractDownloadDto;
		}
	}

	private class IjsBillContractDownloadRowMapper implements RowMapper {

		public Object mapRow(ResultSet resultSet, int row) {
			IjsContractDownloadDTO ijsContractDownloadDto = new IjsContractDownloadDTO();
			try {
				ijsContractDownloadDto.setVendorCode(resultSet.getString("VENDOR_CODE"));
				ijsContractDownloadDto.setVendorName(resultSet.getString("VENDOR_NAME"));
				ijsContractDownloadDto.setContractId(resultSet.getString("CONTRACT_NO"));
				ijsContractDownloadDto.setRoutingId(resultSet.getInt("ROUTING_NO"));
				ijsContractDownloadDto.setContractStartDate(resultSet.getString("START_DATE"));
				ijsContractDownloadDto.setContractEndDate(resultSet.getString("END_DATE"));
				ijsContractDownloadDto.setTransMode(resultSet.getString("MOT"));
				ijsContractDownloadDto.setStatus(resultSet.getString("ROUTE_RATE_STATUS"));
				ijsContractDownloadDto.setPaymentFsc(resultSet.getString("PAYMENT_FSC"));
				ijsContractDownloadDto.setContractCurrency(resultSet.getString("CURRENCY"));
				ijsContractDownloadDto.setPriority(resultSet.getInt("PRIORITY"));
				ijsContractDownloadDto.setFromLocType(resultSet.getString("FROM_MODE"));
				ijsContractDownloadDto.setFromLocation(resultSet.getString("FROM_LOCATION"));
				ijsContractDownloadDto.setFromTerminal(resultSet.getString("FROM_TERMINAL"));
				ijsContractDownloadDto.setToLocType(resultSet.getString("TO_MODE"));
				ijsContractDownloadDto.setToLocation(resultSet.getString("TO_LOCATION"));
				ijsContractDownloadDto.setToTerminal(resultSet.getString("TO_TERMINAL"));
				ijsContractDownloadDto.setDays(resultSet.getInt("DAYS"));
				ijsContractDownloadDto.setHours(resultSet.getInt("HOURS"));
				ijsContractDownloadDto.setDistance(resultSet.getInt("DISTANCE"));
				ijsContractDownloadDto.setContractUom(resultSet.getString("DISTANCE_UOM"));
				ijsContractDownloadDto.setExempted(resultSet.getString("EXEMPTED"));
				ijsContractDownloadDto.setCostRateSequenceNum(resultSet.getInt("BILLING_SEQ_NUM"));

				ijsContractDownloadDto.setRateStartDate(resultSet.getString("BILLING_RATE_START_DATE"));
				ijsContractDownloadDto.setRateEndDate(resultSet.getString("BILLING_RATE_END_DATE"));
				ijsContractDownloadDto.setService(resultSet.getString("SERVICE"));
				ijsContractDownloadDto.setRateBasis(resultSet.getString("RATE_BASIS"));
				ijsContractDownloadDto.setEqCatq(resultSet.getString("EQUIP_CATG"));
				ijsContractDownloadDto.setChargeCode(resultSet.getString("CHARGE_CODE"));
				ijsContractDownloadDto.setCurrency(resultSet.getString("BILLING_RATE_CURRENCY"));
				ijsContractDownloadDto.setTerm(resultSet.getString("TERM"));
				ijsContractDownloadDto.setMtOrLaden(resultSet.getString("MT_LADEN"));
				ijsContractDownloadDto.setRateStatus(resultSet.getString("BILLING_STATUS"));
				ijsContractDownloadDto.setCalcMethod(resultSet.getString("CALCULATION_METHOD"));
				ijsContractDownloadDto.setEqType(resultSet.getString("EQ_TYPE"));
				ijsContractDownloadDto.setUpto(resultSet.getInt("UPTO"));
				ijsContractDownloadDto.setUom(resultSet.getString("UNIT"));
				ijsContractDownloadDto.setRate20(resultSet.getDouble("RATE20"));
				ijsContractDownloadDto.setRate40(resultSet.getDouble("RATE40"));
				ijsContractDownloadDto.setRate45(resultSet.getDouble("RATE45"));
				ijsContractDownloadDto.setDetailSeqNum(resultSet.getString("BILLING_DETAIL_SEQ"));
				ijsContractDownloadDto.setpTerm(resultSet.getString("TERM"));
				ijsContractDownloadDto.setFilterBy(resultSet.getString("TERM"));

			} catch (SQLException e) {
				// TODO
				e.printStackTrace();
			}
			return ijsContractDownloadDto;
		}
	}

	protected class GetIjstermListStoredProcedure extends StoredProcedure {
		private static final String SQL_RLTD_IJS_TERM = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_TERM_SEARCH";

		GetIjstermListStoredProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_RLTD_IJS_TERM);
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_ijs_term_list", OracleTypes.CURSOR, rowMapper));
			compile();
		}

		private List<IjsContractShipmentTermVO> getTermList(String userInfo) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			outMap = execute(inParameters);

			return (List<IjsContractShipmentTermVO>) outMap.get("p_o_v_ijs_term_list");
		}

	}

	protected class GetIjsTransportModeListStoredProcedure extends StoredProcedure {
		private static final String SQL_RLTD_IJS_PAYMENT_METHOD = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_TRANSPORT_MODE";

		GetIjsTransportModeListStoredProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_RLTD_IJS_PAYMENT_METHOD);
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_vendor_cd", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_ijs_term_list", OracleTypes.CURSOR, rowMapper));
			compile();
		}

		private List<IjsContractTransportModeVO> getTransportMode(String userInfo, String vendorCode) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			inParameters.put("p_i_v_vendor_cd", RutDatabase.stringToDb(vendorCode).toUpperCase());
			outMap = execute(inParameters);

			return (List<IjsContractTransportModeVO>) outMap.get("p_o_v_ijs_term_list");
		}

	}

	private class IjsTermListRowMapper implements RowMapper {
		public Object mapRow(ResultSet resultSet, int i) {
			IjsContractShipmentTermVO term = new IjsContractShipmentTermVO();
			try {
				term.setDescription(resultSet.getString("DESCRIPTON"));
				term.setTermCode(resultSet.getString("TERM_CODE"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return term;
		}
	}

	private class IjsPaymentMethodListRowMapper implements RowMapper {
		public Object mapRow(ResultSet resultSet, int i) {
			IjsContractTransportModeVO transportMode = new IjsContractTransportModeVO();
			try {
//                transportMode.setLabel(resultSet.getString("DESCRIPTON"));
//                transportMode.setValue(resultSet.getString("DESCRIPTON"));
//                transportMode.setCode(resultSet.getString("TERM_CODE"));
				transportMode.setLabel(resultSet.getString("DESCRIPTON"));
				transportMode.setValue(resultSet.getString("DESCRIPTON"));
				transportMode.setCode(resultSet.getString("TERM_CODE"));
				if ("Feeder".equals(transportMode.getValue())) {
					transportMode.setCode("02");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return transportMode;
		}
	}

	protected class IjsCostRateStoredProcedure extends StoredProcedure {
		private static final String SQL_RLTD_IJS_RATE = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_COST_RATE_SEARCH";

		IjsCostRateStoredProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_RLTD_IJS_RATE);
			declareParameter(new SqlInOutParameter("p_i_v_routing_number", Types.NUMERIC, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_ijs_rate_list", OracleTypes.CURSOR, rowMapper));
			compile();
		}

		private List<IjsContractCostDTO> getCostRate(int routngNumber, String userInfo) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_routing_number", routngNumber);
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			outMap = execute(inParameters);
			List outList = (List<IjsContractCostDTO>) outMap.get("p_o_v_ijs_rate_list");

			return outList;
		}
	}

	private class IjsCostRateRowMapper implements RowMapper {

		public Object mapRow(ResultSet resultSet, int row) {
			IjsContractCostDTO ijsContractCostDTO = new IjsContractCostDTO();
			String mtLaden = null;
			try {
				mtLaden = resultSet.getString("MT_LADEN");
				if (mtLaden != null) {
					ijsContractCostDTO.setRate20(resultSet.getString("RATE20"));
					ijsContractCostDTO.setRate40(resultSet.getString("RATE40"));
					ijsContractCostDTO.setRate45(resultSet.getString("RATE45"));
					ijsContractCostDTO.setEffectiveDate(resultSet.getString("START_DATE"));
					ijsContractCostDTO.setExpiryDate(resultSet.getString("END_DATE"));
					ijsContractCostDTO.setSplHandling(resultSet.getString("SPL_HANDLING"));
					ijsContractCostDTO.setCurrency(resultSet.getString("CURRENCY"));
					ijsContractCostDTO.setMtLaden(mtLaden);
					ijsContractCostDTO.setRecordStatus(resultSet.getString("RATE_STATUS"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ijsContractCostDTO;
		}
	}

	protected class IjsBillingRateStoredProcedure extends StoredProcedure {
		private static final String SQL_RLTD_IJS_RATE = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_BILLING_RATE_SEARCH";

		IjsBillingRateStoredProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_RLTD_IJS_RATE);
			declareParameter(new SqlInOutParameter("p_i_v_routing_number", Types.NUMERIC, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_ijs_rate_list", OracleTypes.CURSOR, rowMapper));
			compile();
		}

		private List<IjsContractBillingDTO> getBillingRate(int routngNumber, String userInfo) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_routing_number", routngNumber);
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());
			outMap = execute(inParameters);
			List outList = (List<IjsContractBillingDTO>) outMap.get("p_o_v_ijs_rate_list");

			return outList;
		}
	}

	private class IjsBillingRateRowMapper implements RowMapper {

		public Object mapRow(ResultSet resultSet, int row) {
			IjsContractBillingDTO ijsContractBillingDTO = new IjsContractBillingDTO();
			try {

				ijsContractBillingDTO.setRate20(resultSet.getString("RATE20"));
				ijsContractBillingDTO.setRate40(resultSet.getString("RATE40"));
				ijsContractBillingDTO.setRate45(resultSet.getString("RATE45"));
				ijsContractBillingDTO.setEffectiveDate(resultSet.getString("START_DATE"));
				ijsContractBillingDTO.setExpiryDate(resultSet.getString("END_DATE"));
				ijsContractBillingDTO.setCurrency(resultSet.getString("CURRENCY"));
				ijsContractBillingDTO.setMtLaden(resultSet.getString("MT_LADEN"));
				ijsContractBillingDTO.setRecordStatus(resultSet.getString("RATE_STATUS"));

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ijsContractBillingDTO;
		}
	}

	protected class IjsCntrCurrencyForContractProcedure extends StoredProcedure {
		private static final String SQL_PRR_IJS_CONT_CURR_LOOKUP = "PCR_IJS_CNTR_COMMON.PRR_IJS_CONT_CURR_LOOKUP";

		IjsCntrCurrencyForContractProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_PRR_IJS_CONT_CURR_LOOKUP);

			declareParameter(new SqlInOutParameter("p_i_v_find_for", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_ijs_currency_list", OracleTypes.CURSOR, rowMapper));
			compile();
		}

		private List<IjsCntrCurrencyLookUpVO> getCurrencyForContract(String userId, String paymentFscCode) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_find_for", RutDatabase.stringToDb(paymentFscCode).toUpperCase());
			outMap = execute(inParameters);

			return (List<IjsCntrCurrencyLookUpVO>) outMap.get("p_o_v_ijs_currency_list");
		}
	}

	private class IjsContCurrencyRowMapper implements RowMapper {

		public Object mapRow(ResultSet resultSet, int row) {
			IjsCntrCurrencyLookUpVO currencyLookUp = new IjsCntrCurrencyLookUpVO();
			try {
				currencyLookUp.setCurrencyCode(resultSet.getString("CURRENCY"));
				// vesselLookUp.setCurrencyName(resultSet.getString("CURRENCY_NAME"));

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return currencyLookUp;
		}
	}

	protected class IjsCntrCurrencyForLocationProcedure extends StoredProcedure {
		private static final String SQL_PRR_IJS_CONT_CURR_LOOKUP = "PCR_IJS_CNTR_COMMON.PRR_IJS_CURR_FOR_LOC";

		IjsCntrCurrencyForLocationProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, SQL_PRR_IJS_CONT_CURR_LOOKUP);
			declareParameter(new SqlInOutParameter("p_i_v_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_location_type", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_ijs_currency", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_ijs_fsc", Types.VARCHAR));
			compile();
		}

		private Map getCurrencyForLocation(String userId, String locationCode, String locationType) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_location", RutDatabase.stringToDb(locationCode).toUpperCase());
			inParameters.put("p_i_v_location_type",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(locationType)).toUpperCase());

			return outMap = execute(inParameters);
			// return (String)outMap.get("p_o_v_ijs_currency");
		}
	}

	protected class IjsCntrVendorNameProcedure extends StoredProcedure {
		private static final String SQL_PRR_IJS_CONT_CURR_LOOKUP = "PCR_IJS_CNTR_COMMON.PRR_IJS_VEND_NAME";

		IjsCntrVendorNameProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, SQL_PRR_IJS_CONT_CURR_LOOKUP);
			declareParameter(new SqlInOutParameter("p_i_v_vendor_no", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_ijs_vendor_name", Types.VARCHAR));
			compile();
		}

		private String getVendorName(String userId, String locationCode) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_vendor_no", RutDatabase.stringToDb(locationCode).toUpperCase());
			outMap = execute(inParameters);
			return (String) outMap.get("p_o_v_ijs_vendor_name");
		}
	}

	protected class IjsCntrValidateTerminalProcedure extends StoredProcedure {
		private static final String SQL_PRR_IJS_VAL_TERMINAL = "PCR_IJS_CNTR_COMMON.PRR_IJS_VALIDATE_LOC";

		IjsCntrValidateTerminalProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, SQL_PRR_IJS_VAL_TERMINAL);

			declareParameter(new SqlInOutParameter("p_i_v_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_location_type", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_terminal", Types.VARCHAR));

			declareParameter(new SqlOutParameter("p_o_v_location", Types.VARCHAR));
			compile();
		}

		private Map getLocationValidity(String userId, String locationCode, String locationType, String terminal) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_location", RutDatabase.stringToDb(locationCode).toUpperCase());
			inParameters.put("p_i_v_location_type",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(locationType)).toUpperCase());
			inParameters.put("p_i_v_terminal", RutDatabase.stringToDb(terminal).toUpperCase());
			return outMap = execute(inParameters);

		}
	}

	@Override
	public String doGenPortPair(String userId) throws IJSException {
		return gppStoredProc.doGenPortPair(userId);
	}

	@Override
	public String validateFromToLocSetup(String fromLocation, String fromLocType, String toLocation, String toLocType,
			String transMode) throws IJSException {
		String msgCode = ijsvalidateLocSetupProcedure.validateLocationSetup(fromLocType, fromLocation, toLocType,
				toLocation, transMode);
		if (msgCode != null && IjsErrorCode.DB_IJS_COMM_SETUP_EX_1001.name().equals(msgCode)) {
			return IjsErrorCode.DB_IJS_COMM_SETUP_EX_1001.getErrorCode();
		}
		return null;
		// return ijsvalidateLocSetupProcedure.validateLocationSetup(fromLocType,
		// fromLocation, toLocType, toLocation, transMode);
	}

	protected class IjsCntrValidateLocationSetupProcedure extends StoredProcedure {
		private static final String SQL_PRR_IJS_VAL_LOC_COUNTRY_SETUP = "PCR_IJS_CNTR_COMMON.PRR_VALIDATE_LOC_COUNTRY_SETUP";

		IjsCntrValidateLocationSetupProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, SQL_PRR_IJS_VAL_LOC_COUNTRY_SETUP);

			declareParameter(new SqlInOutParameter("p_i_v_from_loc", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_trans_mode", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_error_cd", Types.VARCHAR));
			compile();
		}

		private String validateLocationSetup(String fromLocType, String fromLocation, String toLocType,
				String toLocation, String transMode) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_from_loc", RutDatabase.stringToDb(fromLocation).toUpperCase());
			inParameters.put("p_i_v_to_loc_type",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(toLocType)).toUpperCase());
			inParameters.put("p_i_v_to_loc", RutDatabase.stringToDb(toLocation).toUpperCase());
			inParameters.put("p_i_v_trans_mode",
					RutDatabase.stringToDb(IjsHelper.getTransCode(transMode)).toUpperCase());
			inParameters.put("p_i_v_user_id", "");
			outMap = execute(inParameters);
			return (String) outMap.get("p_o_v_error_cd");

		}
	}

	protected class IjsCostSetupCheckProcedure extends StoredProcedure {
		private static final String SQL_IJS_CNTR_OOG_PORT_IMDG_SETUP_CHECK = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CNTR_OOG_PORT_IMDG_SETUP_CHECK";

		IjsCostSetupCheckProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, SQL_IJS_CNTR_OOG_PORT_IMDG_SETUP_CHECK);
			declareParameter(new SqlInOutParameter("p_i_v_terminal_depot_cd", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_oog_cd", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_port_cd", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_imdg_cd", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			compile();
		}

		private String validateCostRateSetup(String terminalDepotCd, String oogCode, String portCode, String imdgCode) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_terminal_depot_cd", RutDatabase.stringToDb(terminalDepotCd).toUpperCase());
			inParameters.put("p_i_v_oog_cd", RutDatabase.stringToDb(oogCode).toUpperCase());
			inParameters.put("p_i_v_port_cd", RutDatabase.stringToDb(portCode).toUpperCase());
			inParameters.put("p_i_v_imdg_cd", RutDatabase.stringToDb(imdgCode).toUpperCase());
			outMap = execute(inParameters);
			return (String) outMap.get("p_o_v_err_cd");
		}

	}

	@Override
	public String priorityCorrection(IjsContractVO contractVO, String userId) throws IJSException {
		String errorCode = ijsContractPriorityCorrectionProcedure.priorityCorrection(contractVO, userId);
		return errorCode;
	}

	protected class IjsContractPriorityCorrectionProcedure extends StoredProcedure {
		private static final String SQL_IJS_CONTRACT_PRIORITY_CORRECTION = "PCR_IJS_CNTR_MAINTENANCE.PRR_IJS_CONTRACT_PRIORITY_CORRECTION";

		IjsContractPriorityCorrectionProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, SQL_IJS_CONTRACT_PRIORITY_CORRECTION);
			declareParameter(new SqlInOutParameter("p_i_v_contract_no", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_eq_type", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_from_loc_type", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_from_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_from_terminal", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc_type", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_location", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_transport_mode", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_vendor", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR));
			compile();
		}

		private String priorityCorrection(IjsContractVO ijsContractVO, String userId) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			inParameters.put("p_i_v_contract_no", RutDatabase.stringToDb(ijsContractVO.getContractId()).toUpperCase());
			inParameters.put("p_i_v_eq_type", RutDatabase.stringToDb("GP").toUpperCase());
			inParameters.put("p_i_v_from_loc_type",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsContractVO.getFromLocType())).toUpperCase());
			inParameters.put("p_i_v_from_location",
					RutDatabase.stringToDb(ijsContractVO.getFromLocation()).toUpperCase());
			inParameters.put("p_i_v_from_terminal",
					RutDatabase.stringToDb(ijsContractVO.getFromTerminal()).toUpperCase());
			inParameters.put("p_i_v_to_loc_type",
					RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsContractVO.getToLocType())).toUpperCase());
			inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(ijsContractVO.getToLocation()).toUpperCase());
			inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(ijsContractVO.getToTerminal()).toUpperCase());
			inParameters.put("p_i_v_transport_mode",
					RutDatabase.stringToDb(IjsHelper.getTransCode(ijsContractVO.getTransMode())).toUpperCase());
			inParameters.put("p_i_v_start_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getStartDate())).toUpperCase());
			inParameters.put("p_i_v_end_date",
					RutDatabase.stringToDb(IjsHelper.getDateFormat(ijsContractVO.getEndDate())).toUpperCase());
			inParameters.put("p_i_v_vendor", RutDatabase.stringToDb(ijsContractVO.getVendorCode()).toUpperCase());
			inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
			outMap = execute(inParameters);
			return (String) outMap.get("p_o_v_err_cd");
		}

	}
}
