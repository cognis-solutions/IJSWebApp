
package com.rclgroup.dolphin.ijs.web.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.rclgroup.dolphin.ijs.web.common.IjsHelper;
import com.rclgroup.dolphin.ijs.web.constants.IjsErrorCode;
import com.rclgroup.dolphin.ijs.web.dao.IJSProcessNewSaveDao;
import com.rclgroup.dolphin.ijs.web.dao.IjsBaseDao;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOBkgBLSearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSumContDtlDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSumDtlDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessJOSummarySearchDTO;
import com.rclgroup.dolphin.ijs.web.entity.IjsProcessNewSaveDTO;
import com.rclgroup.dolphin.ijs.web.exception.IJSException;
import com.rclgroup.dolphin.ijs.web.util.RutDatabase;
import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.vo.IjsEQDetailVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJORoutingLookUpVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsJOSummaryParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsLookupParamVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsProcessNewSaveVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRoutingListParamVO;

import oracle.jdbc.OracleTypes;

public class IJSProcessJONewSaveDaoImpl extends IjsBaseDao implements IJSProcessNewSaveDao {

	private IJSProcessJONewSaveDaoImpl.IjsJORoutingLookUpProcedure ijsJORoutingLookUpProcedure;
	private IJSProcessJONewSaveDaoImpl.IjsJOVendorsLookUpProcedure ijsJOVendorsLookUpProcedure;
	private IJSProcessJONewSaveDaoImpl.IjsJOCostCalcLookUpProcedure ijsCostCalcListProcedure;
	private IJSProcessJONewSaveDaoImpl.IjsJOCostCalcMultipleProcedure ijsCostCalcListMultipleProcedure;

	public void initDao() throws Exception {
		super.initDao();

		ijsJORoutingLookUpProcedure = new IJSProcessJONewSaveDaoImpl.IjsJORoutingLookUpProcedure(getJdbcTemplate(),
				new IJSProcessJONewSaveDaoImpl.IjsJORoutingLookUpRowMapper());
		ijsJOVendorsLookUpProcedure = new IJSProcessJONewSaveDaoImpl.IjsJOVendorsLookUpProcedure(getJdbcTemplate(),
				new IJSProcessJONewSaveDaoImpl.IjsJOVendorsLookUpRowMapper());
		ijsCostCalcListProcedure = new IJSProcessJONewSaveDaoImpl.IjsJOCostCalcLookUpProcedure(getJdbcTemplate(),
				new IJSProcessJONewSaveDaoImpl.IjsJOVendorsLookUpRowMapper());
		ijsCostCalcListMultipleProcedure = new IJSProcessJONewSaveDaoImpl.IjsJOCostCalcMultipleProcedure(
				getJdbcTemplate(), new IJSProcessJONewSaveDaoImpl.IjsJOVendorsLookUpRowMapper());

	}

	public IJSProcessJONewSaveDaoImpl() {

	}

	public List<?> getCostCalcdhoc(List<IjsJORoutingLookUpVO> list, IjsRoutingListParamVO ijsListParamVO,
			String session, String userInfo) throws IJSException {
		List<IjsProcessJOSumDtlDTO> listCostCalc = ijsCostCalcListProcedure.getListOfClc(list, ijsListParamVO, session,
				userInfo);
		return listCostCalc;
	}

	public List<IjsJORoutingLookUpVO> getLookupList(String lookupName, String userInfo,
			IjsProcessNewSaveVO ijsLookupParamVO, List<IjsJOSummaryParamVO> ijsJOSummaryParamVO) throws IJSException {
		List<IjsJORoutingLookUpVO> list = ijsJORoutingLookUpProcedure.getRoutingList(ijsLookupParamVO.getFindIn(),
				ijsLookupParamVO.getFindForLoc(), ijsLookupParamVO.getFindForTerminal(),
				ijsLookupParamVO.getFindForLocType(), ijsLookupParamVO.getFindForSaleDateOrJobOrdDate(),
				ijsLookupParamVO.getFindForVendorCode(), IjsHelper.getTransCode(ijsLookupParamVO.getTransMode()),
				ijsLookupParamVO.getJoType(), ijsLookupParamVO.getSameVendorInSearch(), ijsLookupParamVO.getWildCard(),
				ijsLookupParamVO.getSingleloc(), userInfo, ijsJOSummaryParamVO);
		// List<IjsJORoutingLookUpVO> vendorsList
		// =vendorCostCalc(list,ijsLookupParamVO);
		String errorCode = null;
		if (list == null || list.isEmpty()) {
			errorCode = IjsErrorCode.DB_IJS_COMM_EX_10001.getErrorCode();
		}
		if (errorCode != null) {
			throw new IJSException(errorCode);
		}

		if ("processJoMultiple".equalsIgnoreCase(ijsLookupParamVO.getComponentType())) {
			return list;
		} else {
			return transfer(list, ijsLookupParamVO.getFindIn(), ijsLookupParamVO.getFindForLoc(),
					ijsLookupParamVO.getFindForTerminal(), ijsLookupParamVO.getFindForLocType(),
					ijsLookupParamVO.getTransMode());
		}

	}

	public List<IjsJORoutingLookUpVO> transfer(List<IjsJORoutingLookUpVO> list, String findIn, String findForLoc,
			String findForTerminal, String findForLocType, String TransportMode) {

		String[] forLoc = findForLoc.split("/");

		String fromLoc = forLoc[0];
		String toLoc = forLoc[1];

		String[] forLocType = findForLocType.split("/");

		String fromLocType = forLocType[0];
		String toLocType = forLocType[1];

		String[] forTerminal = findForTerminal.split("/");
		// System.out.println(findForTerminal);
		String fromTerminal = forTerminal[0];
		String toTerminal = forTerminal[1];

		List<IjsJORoutingLookUpVO> returnList = new ArrayList<IjsJORoutingLookUpVO>();
		// IjsJOLookupResult<?> searchResult = new IjsJOLookupResult<T>();
		for (int i = 0; i < list.size(); i++) {
			IjsJORoutingLookUpVO indexValues = (IjsJORoutingLookUpVO) list.get(i);

			if (fromLoc.equalsIgnoreCase(indexValues.getFromLocation())
					&& toLoc.equalsIgnoreCase(indexValues.getToLocation())
					&& fromTerminal.equalsIgnoreCase(indexValues.getFromTerminal())
					&& toTerminal.equalsIgnoreCase(indexValues.getToTerminal())) {

				returnList.add(indexValues);

			}

		}

		return returnList;
	}

	public List<?> getVendorList(List<?> list, String userInfo, IjsProcessNewSaveVO ijsLookupParamVO,
			HttpSession session) {
		List<IjsProcessJOSumDtlDTO> listOfVendors = ijsJOVendorsLookUpProcedure.vendorList(list, userInfo,
				ijsLookupParamVO, session);
		return listOfVendors;
	}

	public List<?> getVendorCostCalc(List<?> list, String userInfo, List<IjsJOSummaryParamVO> ijsJOSummaryParamVO,
			HttpSession session, String joType) {
		List<IjsProcessJOSumDtlDTO> listOfVendors = ijsCostCalcListMultipleProcedure.vendorListCostCalc(list, userInfo,
				ijsJOSummaryParamVO, session, joType);
		return listOfVendors;
	}

	public class IjsJOCostCalcMultipleProcedure extends StoredProcedure {
		private static final String SQL_RLTD_IJS__VENDORS = "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_INSERT_RA_DETAIL";

		IjsJOCostCalcMultipleProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_RLTD_IJS__VENDORS);
			declareParameter(new SqlInOutParameter("p_i_v_trans_mod", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_bkg_or_bl", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_vend_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_jo_typ", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_bkg_bl_no", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_service", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_vessel", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_voyage", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_session", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_port_lookup", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_soc_coc", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_fr_loc_typ", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc_typ", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_fr_location", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_location", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_fr_terminal", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_pptdhVal", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_routing_no", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_bkgbl_no", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_container_no_e", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_container_no_l", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_costcalflg", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_delerteflg", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_session_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_cntSize", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_cntType", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_cntSplHandling", Types.VARCHAR, rowMapper));

			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_failed_bkg_bl", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, rowMapper));
			compile();

		}

		private List<IjsProcessJOSumDtlDTO> vendorListCostCalc(List<?> list, String userId,
				List<IjsJOSummaryParamVO> ijsJOSummaryParamVO, HttpSession session, String joType) {
			try {
				for (int i = 0; i < ijsJOSummaryParamVO.size(); i++) {

					IjsJOSummaryParamVO objJOSummaryParam = ijsJOSummaryParamVO.get(i);

					boolean cheackContainer = objJOSummaryParam.isContainerChanged();

					if (cheackContainer) {
						getJdbcTemplate().update(
								"insert into IJS_PORTPAIR_VENDORCODE (FROM_LOCATION,TO_LOCATION,VENDOR_CODE,CREATED_DATE) values (?,?,?,?)",
								new Object[] { objJOSummaryParam.getFromLocation(), objJOSummaryParam.getToLocation(),
										objJOSummaryParam.getVendorCode(), new Date() });
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<IjsProcessJOSumDtlDTO> outputList = new ArrayList<IjsProcessJOSumDtlDTO>();
			List<IjsProcessJOSumDtlDTO> llstJOSummarySearchReturn = new ArrayList<IjsProcessJOSumDtlDTO>();

			Map outMap = new HashMap();

			Map inParameters = new HashMap();

			for (int count = 0; count < ijsJOSummaryParamVO.size(); count++) {
				IjsJOSummaryParamVO objJOSummaryParam = (IjsJOSummaryParamVO) ijsJOSummaryParamVO.get(count);

				for (int i = 0; i < list.size(); i++) {
					IjsJORoutingLookUpVO ijsLookupParamVendors = (IjsJORoutingLookUpVO) list.get(i);
                   if(!ijsLookupParamVendors.getVendorCode().equalsIgnoreCase(objJOSummaryParam.getVendorCode())&&"Booking".equalsIgnoreCase(objJOSummaryParam.getBkgOrBLType().trim())) {
                   //String whereInchargeCarrier=((IjsJORoutingLookUpVO) list.get(i-1)).getVendorCode();
                	String whereInchargeCarrier= objJOSummaryParam.getVendorCode();
					String setInterCharge = ijsLookupParamVendors.getVendorCode();
					getJdbcTemplate().update(
							"update vasapps.booking_voyage_routing_dtl bvrd  set  INTERCHANGE_CARRIER =? where bvrd.booking_no=? and INTERCHANGE_CARRIER=?",
							new Object[] { setInterCharge, objJOSummaryParam.getBkgOrBLNumber(),
									whereInchargeCarrier });
					
                   }else if(!ijsLookupParamVendors.getVendorCode().equalsIgnoreCase(objJOSummaryParam.getVendorCode()) && "BL".equalsIgnoreCase(objJOSummaryParam.getBkgOrBLType().trim()))
                   {
                	  //String whereInchargeCarrier=((IjsJORoutingLookUpVO) list.get(i-1)).getVendorCode();
                	  String whereInchargeCarrier= objJOSummaryParam.getVendorCode();
   					  String setInterCharge = ijsLookupParamVendors.getVendorCode();
   					  getJdbcTemplate().update(
   							"update vasapps.idp005 bvrd  set  INTERCHANGE_CARRIER =? where bvrd.SYBLNO=? and INTERCHANGE_CARRIER=?",
   							new Object[] { setInterCharge, objJOSummaryParam.getBkgOrBLNumber(),
   									whereInchargeCarrier });
                   }
					
					String lstrSessionId = session.getId();
					// Map outMap = new HashMap();
					// Map inParameters = new HashMap();
					String lstrTransportMode = "";
					String lstrFromLocType = "";
					String lstrToLocType = "";
					String BLANK = "";
					String COMMA = ",";
					String lstrProcessJOType = "";
					StringBuffer lsbBkgBl = new StringBuffer();

					String lstrBookingBl = null;
					String transMode = ijsJOSummaryParamVO.get(0).getTransportMode();

					if ("SEALEG".equals(joType)) {
						lstrProcessJOType = "S";
					} else if ("ETR".equals(joType)) {
						lstrProcessJOType = "O";
					} else if ("ITR".equals(joType)) {
						lstrProcessJOType = "I";
					} else if ("IT".equals(joType)) {
						lstrProcessJOType = "T";
					} else if ("ER".equals(joType)) {
						lstrProcessJOType = "A";
					} else if ("LAH".equals(joType)) {
						lstrProcessJOType = "L";
					} else {
						lstrProcessJOType = "R";
					}
					// lstrProcessJOType = "T";
					lstrBookingBl = ijsJOSummaryParamVO.get(0).getBkgOrBLType().toUpperCase();

					lsbBkgBl.append(ijsJOSummaryParamVO.get(0).getBkgOrBLNumber().toUpperCase());
					lsbBkgBl.append(COMMA);

					if (lsbBkgBl != null) {
						lsbBkgBl.replace(lsbBkgBl.lastIndexOf(","), lsbBkgBl.lastIndexOf(","), "");
					}

					if ("Truck".equals(transMode)) {
						lstrTransportMode = "T";
					} else if ("Barge".equals(transMode)) {
						lstrTransportMode = "B";
					} else if ("Feeder".equals(transMode)) {
						lstrTransportMode = "F";
					} else {
						lstrTransportMode = "R";
					}

					String lstrCostCalFlg = "Y";
					String lstrDeleteFlg = "Y";

					List<IjsProcessJOSummarySearchDTO> llstJOSummary = null;
					IjsProcessJOSummarySearchDTO joSummaryDTO;
					int index = 0;
					List<IjsProcessJOSummarySearchDTO> llstJOSummarySearchReturn1 = new ArrayList<IjsProcessJOSummarySearchDTO>();

					StringBuffer lsbContainere = new StringBuffer();
					StringBuffer lsbContainerl = new StringBuffer();
					// IjsJOSummaryParamVO jJOSummaryParam = (IjsJOSummaryParamVO)((List<?>)
					// objJOSummaryParam).get(i);

					List lstContainerl = objJOSummaryParam.getLstContainerl();
					if (lstContainerl != null) {
						for (int j = 0; j < lstContainerl.size(); j++) {
							lsbContainerl.append(lstContainerl.get(j));
							lsbContainerl.append(COMMA);
						}
					}

					List lstContainere = objJOSummaryParam.getLstContainere();
					if (lstContainere != null) {
						for (int j = 0; j < lstContainere.size(); j++) {
							lsbContainere.append(lstContainere.get(j));
							lsbContainere.append(COMMA);
						}
					}

					inParameters.put("p_i_v_trans_mod", RutDatabase.stringToDb(lstrTransportMode.toUpperCase()));
					inParameters.put("p_i_v_bkg_or_bl", RutDatabase.stringToDb(lstrBookingBl));
					inParameters.put("p_i_v_vend_cd",
							RutDatabase.stringToDb(ijsLookupParamVendors.getVendorCode()).toUpperCase());
					inParameters.put("p_i_v_jo_typ", RutDatabase.stringToDb(lstrProcessJOType));
					inParameters.put("p_i_v_bkg_bl_no", RutDatabase.stringToDb(BLANK));
					inParameters.put("p_i_v_service", RutDatabase.stringToDb(BLANK));
					inParameters.put("p_i_v_vessel", RutDatabase.stringToDb(BLANK));
					inParameters.put("p_i_v_voyage", RutDatabase.stringToDb(BLANK));
					inParameters.put("p_i_v_session", RutDatabase.stringToDb(BLANK));
					inParameters.put("p_i_v_port_lookup", RutDatabase.stringToDb(BLANK));
					inParameters.put("p_i_v_soc_coc", RutDatabase.stringToDb(BLANK));

					/*
					 * inParameters.put("p_i_v_fr_loc_typ",
					 * RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsLookupParamVendors.
					 * getFromLocType())).toUpperCase());
					 * 
					 * 
					 * inParameters.put("p_i_v_to_loc_typ",
					 * RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsLookupParamVendors.
					 * getToLocType())).toUpperCase());
					 * 
					 * 
					 * inParameters.put("p_i_v_fr_location",
					 * RutDatabase.stringToDb(ijsLookupParamVendors.getFromLocation()).toUpperCase()
					 * );
					 * 
					 * 
					 * inParameters.put("p_i_v_to_location",
					 * RutDatabase.stringToDb(ijsLookupParamVendors.getToLocation()).toUpperCase());
					 * 
					 * 
					 * inParameters.put("p_i_v_fr_terminal",
					 * RutDatabase.stringToDb(ijsLookupParamVendors.getFromTerminal()).toUpperCase()
					 * );
					 * 
					 * 
					 * inParameters.put("p_i_v_to_terminal",
					 * RutDatabase.stringToDb(ijsLookupParamVendors.getToTerminal()).toUpperCase());
					 * inParameters.put("p_i_v_pptdhVal", RutDatabase.stringToDb(BLANK));
					 * inParameters.put("p_i_v_start_date", RutDatabase.stringToDb(BLANK));
					 * inParameters.put("p_i_v_end_date", RutDatabase.stringToDb(BLANK));
					 * 
					 * inParameters.put("p_i_v_routing_no",
					 * RutDatabase.stringToDb(ijsLookupParamVendors.getRoutingId()));
					 * 
					 */

					if (lstrProcessJOType.equals("T")) {
						inParameters.put("p_i_v_fr_loc_typ",
								RutDatabase
										.stringToDb(IjsHelper.getLocationCode(ijsLookupParamVendors.getFromLocType()))
										.toUpperCase());
					} else {
						inParameters.put("p_i_v_fr_loc_typ", RutDatabase.stringToDb(BLANK));
					}
					if (lstrProcessJOType.equals("T")) {
						inParameters.put("p_i_v_to_loc_typ",
								RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsLookupParamVendors.getToLocType()))
										.toUpperCase());
					} else {
						inParameters.put("p_i_v_to_loc_typ", RutDatabase.stringToDb(BLANK));
					}
					if (lstrProcessJOType.equals("T")) {
						inParameters.put("p_i_v_fr_location",
								RutDatabase.stringToDb(ijsLookupParamVendors.getFromLocation()).toUpperCase());
					} else {
						inParameters.put("p_i_v_fr_location", RutDatabase.stringToDb(BLANK));
					}
					if (lstrProcessJOType.equals("T")) {
						inParameters.put("p_i_v_to_location",
								RutDatabase.stringToDb(ijsLookupParamVendors.getToLocation()).toUpperCase());
					} else {
						inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(BLANK));
					}
					if (lstrProcessJOType.equals("T")) {
						inParameters.put("p_i_v_fr_terminal",
								RutDatabase.stringToDb(ijsLookupParamVendors.getFromTerminal()).toUpperCase());
					} else {
						inParameters.put("p_i_v_fr_terminal", RutDatabase.stringToDb(BLANK));
					}
					if (lstrProcessJOType.equals("T")) {
						inParameters.put("p_i_v_to_terminal",
								RutDatabase.stringToDb(ijsLookupParamVendors.getToTerminal()).toUpperCase());
					} else {
						inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(BLANK));
					}
					inParameters.put("p_i_v_pptdhVal", RutDatabase.stringToDb(BLANK));
					inParameters.put("p_i_v_start_date", RutDatabase.stringToDb(BLANK));
					inParameters.put("p_i_v_end_date", RutDatabase.stringToDb(BLANK));
					/*
					 * if(lstrProcessJOType.equals("T")){ inParameters.put("p_i_v_routing_no",
					 * RutDatabase.stringToDb(ijsLookupParamVendors.getRoutingId())); }else{
					 * inParameters.put("p_i_v_routing_no",RutDatabase.stringToDb(BLANK)); }
					 */

					inParameters.put("p_i_v_routing_no", RutDatabase.stringToDb(ijsLookupParamVendors.getRoutingId()));

					inParameters.put("p_i_v_bkgbl_no",
							RutDatabase.stringToDb(objJOSummaryParam.getBkgOrBLNumber().toUpperCase()));
					inParameters.put("p_i_v_container_no_e", RutDatabase.stringToDb(lsbContainere.toString()));
					inParameters.put("p_i_v_container_no_l", RutDatabase.stringToDb(lsbContainerl.toString()));
					inParameters.put("p_i_v_costcalflg", RutDatabase.stringToDb(lstrCostCalFlg));
					if (index == 0)
						inParameters.put("p_i_v_delerteflg", RutDatabase.stringToDb(lstrDeleteFlg));
					/*else
						inParameters.put("p_i_v_delerteflg", RutDatabase.stringToDb("N"));
					index++;
					*/

					inParameters.put("p_i_v_session_id", RutDatabase.stringToDb(lstrSessionId));
					inParameters.put("p_i_v_cntSize", RutDatabase.stringToDb(objJOSummaryParam.getCntSize()));
					inParameters.put("p_i_v_cntType", RutDatabase.stringToDb(objJOSummaryParam.getCntType()));
					inParameters.put("p_i_v_cntSplHandling",
							RutDatabase.stringToDb(objJOSummaryParam.getCntSplHandling()));
					inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
					if ("Y".equals(lstrCostCalFlg)) {
						outMap = execute(inParameters);
					}
					 
					
					if(!ijsLookupParamVendors.getVendorCode().equalsIgnoreCase(objJOSummaryParam.getVendorCode()) && "Booking".equalsIgnoreCase(objJOSummaryParam.getBkgOrBLType().trim())) 
					{  
							//String setInterCharge=((IjsJORoutingLookUpVO) list.get(i-1)).getVendorCode();
						     String setInterCharge= objJOSummaryParam.getVendorCode();
							String whereInchargeCarrier = ijsLookupParamVendors.getVendorCode();
							getJdbcTemplate().update(
									"update vasapps.booking_voyage_routing_dtl bvrd  set  INTERCHANGE_CARRIER =? where bvrd.booking_no=? and INTERCHANGE_CARRIER=?",
									new Object[] { setInterCharge, objJOSummaryParam.getBkgOrBLNumber(),
											whereInchargeCarrier });
							
		                   }else if(!ijsLookupParamVendors.getVendorCode().equalsIgnoreCase(objJOSummaryParam.getVendorCode()) &&"BL".equalsIgnoreCase(objJOSummaryParam.getBkgOrBLType().trim()))
		                   {
		                 	    // String setInterCharge=((IjsJORoutingLookUpVO) list.get(i-1)).getVendorCode();
		                	      String setInterCharge= objJOSummaryParam.getVendorCode();
		    					  String whereInchargeCarrier = ijsLookupParamVendors.getVendorCode();
		    					  getJdbcTemplate().update(
		    							"update vasapps.idp005 bvrd  set  INTERCHANGE_CARRIER =? where bvrd.SYBLNO=?and INTERCHANGE_CARRIER=?",
		    							new Object[] { setInterCharge, objJOSummaryParam.getBkgOrBLNumber(),
		    									whereInchargeCarrier });
		                    }
					
					 
					 
					// REMOVED THE BRACKET//
					String lstrErrorCode = (String) outMap.get("p_o_v_err_cd");
					System.out.println(lstrErrorCode);
					if (lstrErrorCode != null) {
						if (lstrErrorCode != null) {
							if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20001.name())) {
								lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20001.getErrorCode();
							} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.name())) {
								lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.getErrorCode();
							} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.name())) {
								lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.getErrorCode();
							} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.name())) {
								lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.getErrorCode();
							} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10042.name())) {
								lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10042.getErrorCode();
							} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10041.name())) {
								lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10041.getErrorCode();
							} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10043.name())) {
								lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10043.getErrorCode();
							} else {
								lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10042.getErrorCode();
							}
						}
						IjsProcessJOSumDtlDTO ijsProcessJOSumDtlDTO = new IjsProcessJOSumDtlDTO();
						ijsProcessJOSumDtlDTO.setErrorCode(lstrErrorCode);
						ijsProcessJOSumDtlDTO.setBokingBL((String) outMap.get("p_o_v_failed_bkg_bl"));
						llstJOSummarySearchReturn.add(ijsProcessJOSumDtlDTO);
						// resetJO(astrSessionId,userId);
					} else {

						outputList.addAll((List<IjsProcessJOSumDtlDTO>) outMap.get("p_o_v_ijs_mapping_list"));
						// return
						// prepareJOData((List<IjsProcessJOSummarySearchDTO>)outMap.get("p_o_v_ijs_mapping_list"));
					}
				}
			}

			if (outputList.size() == 0)
				return llstJOSummarySearchReturn;
			else
				return outputList;

			// return llstJOSummarySearchReturn;
		}

	}

	/*
	 * private List<IjsProcessJOSumDtlDTO>
	 * prepareJOData(List<IjsProcessJOSummarySearchDTO> processJoSummDTO){
	 * List<IjsProcessJOSummarySearchDTO> llstJOSummarySearch =processJoSummDTO;
	 * List<IjsProcessJOSumDtlDTO> llstJOSummarySearchReturn = new
	 * ArrayList<IjsProcessJOSumDtlDTO>(); IjsProcessJOSumContDtlDTO jobOrderSum;
	 * List <IjsProcessJOSumContDtlDTO> jobOrders=null; IjsProcessJOSumDtlDTO
	 * joSumDtlDTO=null; String prevJobOrderNo=""; String nextJobOrderNo; for(int
	 * i=0;i< llstJOSummarySearch.size();i++){ IjsProcessJOSummarySearchDTO
	 * joSummery = llstJOSummarySearch.get(i);
	 * nextJobOrderNo=joSummery.getJobOrder();
	 * if(!nextJobOrderNo.equals(prevJobOrderNo)){ joSumDtlDTO=new
	 * IjsProcessJOSumDtlDTO(); jobOrders=new ArrayList<>();
	 * joSumDtlDTO.setJobOrders(jobOrders); joSumDtlDTO.setJoSummery(joSummery);
	 * llstJOSummarySearchReturn.add(joSumDtlDTO); }
	 * if("1".equals(joSummery.getSlNumber())){ jobOrderSum = new
	 * IjsProcessJOSumContDtlDTO(); jobOrderSum.setJobOrder(nextJobOrderNo);
	 * jobOrderSum.setVendorCode(joSummery.getVendorCode());
	 * jobOrderSum.setContractNumber(joSummery.getContractNumber());
	 * jobOrderSum.setJoDate(joSummery.getJoDate());
	 * jobOrderSum.setSize(joSummery.getSize());
	 * jobOrderSum.setType(joSummery.getType());
	 * jobOrderSum.setOOG(joSummery.getOOG());
	 * jobOrderSum.setMtOrLaden(joSummery.getMtOrLaden());
	 * jobOrderSum.setRate(joSummery.getRate());
	 * jobOrderSum.setQuantity(joSummery.getQuantity());
	 * jobOrderSum.setAmount(joSummery.getAmount());
	 * jobOrderSum.setCurrency(joSummery.getCurrency());
	 * jobOrderSum.setAmountUsd(joSummery.getAmountUsd());
	 * jobOrderSum.setPaymentFSC(joSummery.getPaymentFSC());
	 * jobOrderSum.setSlNumber(joSummery.getSlNumber());
	 * jobOrderSum.setRateBasis(joSummery.getRateBasis());
	 * jobOrderSum.setLumpsumId(joSummery.getLumpsumId());
	 * jobOrders.add(jobOrderSum); } if("2".equals(joSummery.getSlNumber())){
	 * joSumDtlDTO.setJoSummery(joSummery); }
	 * prevJobOrderNo=joSummery.getJobOrder(); } return llstJOSummarySearchReturn; }
	 */

	protected class IjsJOCostCalcLookUpProcedure extends StoredProcedure {
		private static final String SQL_PROCESS_JO_BKGBL_INSERT = "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_INSERT_ADHOC_DETAIL";

		IjsJOCostCalcLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_PROCESS_JO_BKGBL_INSERT);

			declareParameter(new SqlInOutParameter("p_i_v_routingId", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_contractId", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_days", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_hours", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_fromLocation", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_toLocation", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_fromLocType", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_toLocType", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_fromTerminal", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_toTerminal", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_currency", Types.VARCHAR, rowMapper));

			declareParameter(new SqlInOutParameter("p_i_v_legType", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_priority", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_vendorCode", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_processJOType", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_eqList", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_eq_detail", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_transMode", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_session_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_failed_bkg_bl", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, rowMapper));
			compile();
		}

		private List<IjsProcessJOSumDtlDTO> getListOfClc(List<IjsJORoutingLookUpVO> list,
				IjsRoutingListParamVO ijsListParamVO, String session, String userInfo) {
			Map outMap = new HashMap();
			Map inParameters = new HashMap();
			String lstrProcessJOType = null;
			String lstrTransportMode = null;

			String lstrEqList = null;
			StringBuffer lsbEqList = new StringBuffer();
			List<IjsProcessJOSumDtlDTO> llstJOSummarySearchReturn = new ArrayList<IjsProcessJOSumDtlDTO>();

			List<IjsProcessJOSumDtlDTO> outputList = new ArrayList<IjsProcessJOSumDtlDTO>();
			for (int i = 0; i < ijsListParamVO.getEqpList().size(); i++) {
				lstrEqList = (String) ijsListParamVO.getEqpList().get(i);
				lsbEqList.append(lstrEqList);
				lsbEqList.append(",");

			}

			StringBuilder strEqDetail = new StringBuilder();
			for (IjsEQDetailVO eqDetail : ijsListParamVO.getEqDetails()) {
				strEqDetail = strEqDetail.append(eqDetail.getEqpNum()).append(":").append(eqDetail.getType())
						.append(":").append(eqDetail.getSize());
				strEqDetail = strEqDetail.append(",");
			}
			int lastIndex = strEqDetail.lastIndexOf(",");
			strEqDetail.substring(0, lastIndex);

			for (int i = 0; i < list.size(); i++) {
				IjsJORoutingLookUpVO ijsLookupParamVendors = list.get(i);

				if ("SEALEG".equals(ijsListParamVO.getLegType())) {
					lstrProcessJOType = "S";
				} else if ("ETR".equals(ijsListParamVO.getLegType())) {
					lstrProcessJOType = "O";
				} else if ("ITR".equals(ijsListParamVO.getLegType())) {
					lstrProcessJOType = "I";
				} else if ("IT".equals(ijsListParamVO.getLegType())) {
					lstrProcessJOType = "T";
				} else if ("ER".equals(ijsListParamVO.getLegType())) {
					lstrProcessJOType = "A";
				} else if ("LAH".equals(ijsListParamVO.getLegType())) {
					lstrProcessJOType = "L";
				} else {
					lstrProcessJOType = "R";
				}

				if ("Truck".equals(ijsLookupParamVendors.getTransMode())) {
					lstrTransportMode = "T";
				} else if ("Barge".equals(ijsLookupParamVendors.getTransMode())) {
					lstrTransportMode = "B";
				} else if ("Feeder".equals(ijsLookupParamVendors.getTransMode())) {
					lstrTransportMode = "F";
				} else {
					lstrTransportMode = "R";
				}

				inParameters.put("p_i_v_routingId", RutDatabase.stringToDb(ijsLookupParamVendors.getRoutingId()));
				inParameters.put("p_i_v_contractId", RutDatabase.stringToDb(ijsLookupParamVendors.getContractId()));
				inParameters.put("p_i_v_days", RutDatabase.stringToDb(ijsLookupParamVendors.getDays()));
				inParameters.put("p_i_v_hours", RutDatabase.stringToDb(ijsLookupParamVendors.getHours()));
				inParameters.put("p_i_v_fromLocation", RutDatabase.stringToDb(ijsLookupParamVendors.getFromLocation()));
				inParameters.put("p_i_v_toLocation", RutDatabase.stringToDb(ijsLookupParamVendors.getToLocation()));
				inParameters.put("p_i_v_fromLocType", RutDatabase.stringToDb(ijsLookupParamVendors.getFromLocType()));
				inParameters.put("p_i_v_toLocType", RutDatabase.stringToDb(ijsLookupParamVendors.getToLocType()));
				inParameters.put("p_i_v_fromTerminal", RutDatabase.stringToDb(ijsLookupParamVendors.getFromTerminal()));
				inParameters.put("p_i_v_toTerminal", RutDatabase.stringToDb(ijsLookupParamVendors.getToTerminal()));
				inParameters.put("p_i_v_currency", RutDatabase.stringToDb(ijsLookupParamVendors.getCurrency()));
				inParameters.put("p_i_v_legType", RutDatabase.stringToDb(ijsLookupParamVendors.getLegType()));
				inParameters.put("p_i_v_priority",
						RutDatabase.stringToDb(Integer.toString(ijsLookupParamVendors.getPriority())));
				inParameters.put("p_i_v_vendorCode", RutDatabase.stringToDb(ijsLookupParamVendors.getVendorCode()));
				inParameters.put("p_i_v_processJOType", RutDatabase.stringToDb(lstrProcessJOType));
				inParameters.put("p_i_v_eqList", RutDatabase.stringToDb(lsbEqList.toString()));
				inParameters.put("p_i_v_eq_detail", RutDatabase.stringToDb(strEqDetail.toString()));
				inParameters.put("p_i_v_transMode", RutDatabase.stringToDb(lstrTransportMode));
				inParameters.put("p_i_v_session_id", RutDatabase.stringToDb(session));
				inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo).toUpperCase());

				outMap = execute(inParameters);

				String lstrErrorCode = (String) outMap.get("p_o_v_err_cd");

				if (lstrErrorCode != null) {
					if (lstrErrorCode != null) {
						if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20001.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_ADHOC_20001.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10042.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10042.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10041.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10041.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10043.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10043.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.getErrorCode();
						} else {
							lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10042.getErrorCode();
						}
					}
					IjsProcessJOSumDtlDTO ijsProcessJOSumDtlDTO = new IjsProcessJOSumDtlDTO();
					ijsProcessJOSumDtlDTO.setErrorCode(lstrErrorCode);
					ijsProcessJOSumDtlDTO.setBokingBL(" ");
					llstJOSummarySearchReturn.add(ijsProcessJOSumDtlDTO);
					// resetJO(session,userInfo);
				} else {
					outputList.addAll((List<IjsProcessJOSumDtlDTO>) outMap.get("p_o_v_ijs_mapping_list"));
					
				}
			}
			if (outputList.size() == 0)
				return llstJOSummarySearchReturn;
			else
				return outputList;
		}
	}

	protected class IjsJORoutingLookUpProcedure extends StoredProcedure {
		private static final String SQL_RLTD_IJS_ROUTING_LOOK_UP = "PCR_IJS_CNTR_COMMON.PRR_IJS_ROUTING_LOOKUP";

		IjsJORoutingLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_RLTD_IJS_ROUTING_LOOK_UP);
			System.out.println(rowMapper);
			declareParameter(new SqlInOutParameter("p_i_v_find_in", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_find_for_loc", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_find_for_terminal", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_find_for_loc_type", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_find_for_date", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_find_for_vendor_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_trans_mode", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_jo_type", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_same_vendor_in_search", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_ijs_vendor_mapping_list", OracleTypes.CURSOR, rowMapper));
			compile();
		}

		private List<IjsJORoutingLookUpVO> getRoutingList(String findIn, String findForLoc, String findForTerminal,
				String findForLocType, String findForSaleDateOrJobOrdDate, String findForVendorCode, String transMode,
				String joType, String sameVendorInSearch, String wildCard, String singleLoc, String userInfo,
				List<IjsJOSummaryParamVO> ijsJOSummaryParamVO)

		{
			if (singleLoc.equalsIgnoreCase("single")) {

				Map outMap = new HashMap();
				Map inParameters = new HashMap();
				inParameters.put("p_i_v_find_in", RutDatabase.stringToDb(findIn).toUpperCase());
				inParameters.put("p_i_v_find_for_loc", RutDatabase.stringToDb(findForLoc).toUpperCase());
				inParameters.put("p_i_v_find_for_terminal", RutDatabase.stringToDb(findForTerminal).toUpperCase());
				inParameters.put("p_i_v_find_for_loc_type", RutDatabase.stringToDb(findForLocType).toUpperCase());
				inParameters.put("p_i_v_find_for_date", RutDatabase.stringToDb(findForSaleDateOrJobOrdDate));
				inParameters.put("p_i_v_find_for_vendor_cd", RutDatabase.stringToDb(findForVendorCode));
				inParameters.put("p_i_v_trans_mode", RutDatabase.stringToDb(transMode));
				inParameters.put("p_i_v_jo_type", RutDatabase.stringToDb(joType));
				inParameters.put("p_i_v_same_vendor_in_search", RutDatabase.stringToDb(sameVendorInSearch));

				inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo));

				outMap = execute(inParameters);

				return (List<IjsJORoutingLookUpVO>) outMap.get("p_o_v_ijs_vendor_mapping_list");
			} else {

				List<IjsJORoutingLookUpVO> outputList = new ArrayList<IjsJORoutingLookUpVO>();
				List<IjsJOSummaryParamVO> ijsJOSummaryParamVOIn = ijsJOSummaryParamVO;
				for (int i = 0; i < ijsJOSummaryParamVOIn.size(); i++) {

					findIn = ijsJOSummaryParamVOIn.get(i).getBkgOrBLType();
					findForLoc = ijsJOSummaryParamVOIn.get(i).getFromLocation() + '/'
							+ ijsJOSummaryParamVOIn.get(i).getToLocation();
					findForTerminal = ijsJOSummaryParamVOIn.get(i).getFromTerminal() + '/'
							+ ijsJOSummaryParamVOIn.get(i).getToTerminal();
					findForLocType = ijsJOSummaryParamVOIn.get(i).getFromLocationTyp() + '/'
							+ ijsJOSummaryParamVOIn.get(i).getToLocationTyp();
					findForSaleDateOrJobOrdDate = ijsJOSummaryParamVOIn.get(i).getStartDate();
					findForVendorCode = ijsJOSummaryParamVOIn.get(i).getVendorCode();
					transMode = IjsHelper.getTransCode(ijsJOSummaryParamVOIn.get(i).getTransportMode());

					Map outMap = new HashMap();
					Map inParameters = new HashMap();
					inParameters.put("p_i_v_find_in", RutDatabase.stringToDb(findIn).toUpperCase());
					inParameters.put("p_i_v_find_for_loc", RutDatabase.stringToDb(findForLoc).toUpperCase());
					inParameters.put("p_i_v_find_for_terminal", RutDatabase.stringToDb(findForTerminal).toUpperCase());
					inParameters.put("p_i_v_find_for_loc_type", RutDatabase.stringToDb(findForLocType).toUpperCase());
					inParameters.put("p_i_v_find_for_date", RutDatabase.stringToDb(findForSaleDateOrJobOrdDate));
					inParameters.put("p_i_v_find_for_vendor_cd", RutDatabase.stringToDb(findForVendorCode));
					inParameters.put("p_i_v_trans_mode", RutDatabase.stringToDb(transMode));
					inParameters.put("p_i_v_jo_type", RutDatabase.stringToDb(joType));
					inParameters.put("p_i_v_same_vendor_in_search", RutDatabase.stringToDb("Y"));

					inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userInfo));

					outMap = execute(inParameters);

					outputList.addAll((List<IjsJORoutingLookUpVO>) outMap.get("p_o_v_ijs_vendor_mapping_list"));
				}
				return outputList;
			}
		}
	}

	private class IjsJORoutingLookUpRowMapper implements RowMapper {

		public Object mapRow(ResultSet resultSet, int row) {
			IjsJORoutingLookUpVO routingModel = new IjsJORoutingLookUpVO();
			try {
				routingModel.setRoutingId(resultSet.getString("routingId"));
				routingModel.setContractId(resultSet.getString("contract_id"));
				routingModel.setDays(resultSet.getString("days"));
				routingModel.setHours(resultSet.getString("hours"));
				routingModel.setDistance(resultSet.getString("distance"));
				routingModel.setUom(resultSet.getString("uom"));
				routingModel.setFromLocation(resultSet.getString("fromLocation"));
				routingModel.setToLocation(resultSet.getString("toLocation"));
				routingModel.setFromLocType(IjsHelper.getLocationType(resultSet.getString("fromLocType")));
				routingModel.setToLocType(IjsHelper.getLocationType(resultSet.getString("toLocType")));
				routingModel.setFromTerminal(resultSet.getString("fromTerminal"));
				routingModel.setToTerminal(resultSet.getString("toTerminal"));
				routingModel.setCurrency(resultSet.getString("currency"));
				routingModel.setLegType(IjsHelper.getTransMode(resultSet.getString("transMode")));
				String priority = RutDatabase.stringToDb(resultSet.getString("priority"));
				routingModel.setPriority("".equals(priority) ? 0 : Integer.parseInt(priority));
				routingModel.setVendorCode(resultSet.getString("vendorCode"));
				routingModel.setTransMode(IjsHelper.getTransMode(resultSet.getString("transMode")));

				if (resultSet.getString("bargeValue") != null
						&& resultSet.getString("bargeValue").equalsIgnoreCase("I")) {
					routingModel.setBargeValue("International");
					// System.out.println(resultSet.getString("bargeValue") + " Dom/In From I");
				} else if (resultSet.getString("bargeValue") != null
						&& resultSet.getString("bargeValue").equalsIgnoreCase("D")) {
					routingModel.setBargeValue("Domestic");
					// System.out.println(resultSet.getString("bargeValue") + " Dom/In From D");
				} else {
					routingModel.setBargeValue("");
				}

				routingModel.setPurchaseTerm(resultSet.getString("purchaseTerm"));

			} catch (SQLException e) {
				// TO-DO
				e.printStackTrace();
			}
			return routingModel;
		}
	}

	public class IjsJOVendorsLookUpProcedure extends StoredProcedure {
		private static final String SQL_RLTD_IJS__VENDORS = "PCR_IJS_PRCC_JO_BKG_BL_SRCH.PRR_IJS_INSERT_RA_DETAIL";

		IjsJOVendorsLookUpProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, SQL_RLTD_IJS__VENDORS);
			declareParameter(new SqlInOutParameter("p_i_v_trans_mod", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_bkg_or_bl", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_vend_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_jo_typ", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_bkg_bl_no", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_service", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_vessel", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_voyage", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_session", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_port_lookup", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_soc_coc", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_fr_loc_typ", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_loc_typ", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_fr_location", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_location", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_fr_terminal", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_to_terminal", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_pptdhVal", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_start_date", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_end_date", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_routing_no", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_bkgbl_no", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_container_no_e", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_container_no_l", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_costcalflg", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_delerteflg", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_session_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_cntSize", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_cntType", Types.VARCHAR, rowMapper));
			declareParameter(new SqlInOutParameter("p_i_v_cntSplHandling", Types.VARCHAR, rowMapper));

			declareParameter(new SqlInOutParameter("p_i_v_user_id", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_err_cd", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_failed_bkg_bl", Types.VARCHAR, rowMapper));
			declareParameter(new SqlOutParameter("p_o_v_ijs_mapping_list", OracleTypes.CURSOR, rowMapper));
			compile();

		}

		private List<IjsProcessJOSumDtlDTO> vendorList(List<?> list, String userId,
				IjsProcessNewSaveVO ijsLookupParamVO, HttpSession session) {
			Map outMap = new HashMap();
			List<IjsProcessJOSumDtlDTO> outputList = new ArrayList<IjsProcessJOSumDtlDTO>();
			Map inParameters = new HashMap();

			int index = 0;

			List<IjsProcessJOSumDtlDTO> llstJOSummarySearchReturn = new ArrayList<IjsProcessJOSumDtlDTO>();
			for (int i = 0; i < list.size(); i++) {
				IjsJORoutingLookUpVO ijsLookupParamVendors = (IjsJORoutingLookUpVO) list.get(i);

				
                if(!ijsLookupParamVendors.getVendorCode().equalsIgnoreCase(ijsLookupParamVO.getFindForVendorCode()) && "Booking".equalsIgnoreCase(ijsLookupParamVO.getFindIn().trim())) {
                //String whereInchargeCarrier=((IjsJORoutingLookUpVO) list.get(i-1)).getVendorCode();
                	String whereInchargeCarrier=ijsLookupParamVO.getFindForVendorCode();
					String setInterCharge = ijsLookupParamVendors.getVendorCode();
					getJdbcTemplate().update(
							"update vasapps.booking_voyage_routing_dtl bvrd  set  INTERCHANGE_CARRIER =? where bvrd.booking_no=? and INTERCHANGE_CARRIER=?",
							new Object[] { setInterCharge, ijsLookupParamVO.getBkgOrBLNumber(),
									whereInchargeCarrier });
					
                }else if(!ijsLookupParamVendors.getVendorCode().equalsIgnoreCase(ijsLookupParamVO.getFindForVendorCode()) && "BL".equalsIgnoreCase(ijsLookupParamVO.getFindIn().trim()))
                {
             	  //String whereInchargeCarrier=((IjsJORoutingLookUpVO) list.get(i-1)).getVendorCode();
                	  String whereInchargeCarrier=ijsLookupParamVO.getFindForVendorCode();
					  String setInterCharge = ijsLookupParamVendors.getVendorCode();
					  getJdbcTemplate().update(
							"update vasapps.idp005 bvrd  set  INTERCHANGE_CARRIER =? where bvrd.SYBLNO=? and INTERCHANGE_CARRIER=?",
							new Object[] { setInterCharge, ijsLookupParamVO.getBkgOrBLNumber(),
									whereInchargeCarrier });
                }
				
				
				String lstrSessionId = session.getId();
				// Map outMap = new HashMap();
				// Map inParameters = new HashMap();
				String lstrTransportMode = "";
				String lstrFromLocType = "";
				String lstrToLocType = "";
				String BLANK = "";
				String COMMA = ",";
				String lstrProcessJOType = "";
				StringBuffer lsbBkgBl = new StringBuffer();

				String lstrBookingBl = null;
				String transMode = ijsLookupParamVO.getTransMode();

				if ("SEALEG".equals(ijsLookupParamVO.getJoType())) {
					lstrProcessJOType = "S";
				} else if ("ETR".equals(ijsLookupParamVO.getJoType())) {
					lstrProcessJOType = "O";
				} else if ("ITR".equals(ijsLookupParamVO.getJoType())) {
					lstrProcessJOType = "I";
				} else if ("IT".equals(ijsLookupParamVO.getJoType())) {
					lstrProcessJOType = "T";
				} else if ("ER".equals(ijsLookupParamVO.getJoType())) {
					lstrProcessJOType = "A";
				} else if ("LAH".equals(ijsLookupParamVO.getJoType())) {
					lstrProcessJOType = "L";
				} else {
					lstrProcessJOType = "R";
				}
				// lstrProcessJOType = "T";
				lstrBookingBl = ijsLookupParamVO.getFindIn().toUpperCase();

				lsbBkgBl.append(ijsLookupParamVO.getBkgOrBLNumber().toUpperCase());
				lsbBkgBl.append(COMMA);

				if (lsbBkgBl != null) {
					lsbBkgBl.replace(lsbBkgBl.lastIndexOf(","), lsbBkgBl.lastIndexOf(","), "");
				}

				if ("Truck".equals(transMode)) {
					lstrTransportMode = "T";
				} else if ("Barge".equals(transMode)) {
					lstrTransportMode = "B";
				} else if ("Feeder".equals(transMode)) {
					lstrTransportMode = "F";
				} else {
					lstrTransportMode = "R";
				}

				String lstrCostCalFlg = "Y";
				String lstrDeleteFlg = "Y";

				List<IjsProcessJOSummarySearchDTO> llstJOSummary = null;
				IjsProcessJOSummarySearchDTO joSummaryDTO;

				List<IjsProcessJOSummarySearchDTO> llstJOSummarySearchReturn1 = new ArrayList<IjsProcessJOSummarySearchDTO>();
				
				StringBuffer lsbContainere = new StringBuffer();
				StringBuffer lsbContainerl = new StringBuffer();
				
				List lstContainerl = ijsLookupParamVO.getLstContainerl();
				if (lstContainerl != null) {
					for (int j = 0; j < lstContainerl.size(); j++) {
						lsbContainerl.append(lstContainerl.get(j));
						lsbContainerl.append(COMMA);
					}
				}

				List lstContainere = ijsLookupParamVO.getLstContainere();
				if (lstContainere != null) {
					for (int j = 0; j < lstContainere.size(); j++) {
						lsbContainere.append(lstContainere.get(j));
						lsbContainere.append(COMMA);
					}
				}
				
				
				

				inParameters.put("p_i_v_trans_mod", RutDatabase.stringToDb(lstrTransportMode.toUpperCase()));
				inParameters.put("p_i_v_bkg_or_bl", RutDatabase.stringToDb(lstrBookingBl));
				inParameters.put("p_i_v_vend_cd",
						RutDatabase.stringToDb(ijsLookupParamVendors.getVendorCode()).toUpperCase());
				inParameters.put("p_i_v_jo_typ", RutDatabase.stringToDb(lstrProcessJOType));
				inParameters.put("p_i_v_bkg_bl_no", RutDatabase.stringToDb(BLANK));
				inParameters.put("p_i_v_service", RutDatabase.stringToDb(BLANK));
				inParameters.put("p_i_v_vessel", RutDatabase.stringToDb(BLANK));
				inParameters.put("p_i_v_voyage", RutDatabase.stringToDb(BLANK));
				inParameters.put("p_i_v_session", RutDatabase.stringToDb(BLANK));
				inParameters.put("p_i_v_port_lookup", RutDatabase.stringToDb(BLANK));
				inParameters.put("p_i_v_soc_coc", RutDatabase.stringToDb(BLANK));

				if (lstrProcessJOType.equals("T")) {
					inParameters.put("p_i_v_fr_loc_typ",
							RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsLookupParamVendors.getFromLocType()))
									.toUpperCase());
				} else {
					inParameters.put("p_i_v_fr_loc_typ", RutDatabase.stringToDb(BLANK));
				}
				if (lstrProcessJOType.equals("T")) {
					inParameters.put("p_i_v_to_loc_typ", RutDatabase
							.stringToDb(IjsHelper.getLocationCode(ijsLookupParamVendors.getToLocType())).toUpperCase());
				} else {
					inParameters.put("p_i_v_to_loc_typ", RutDatabase.stringToDb(BLANK));
				}
				if (lstrProcessJOType.equals("T")) {
					inParameters.put("p_i_v_fr_location",
							RutDatabase.stringToDb(ijsLookupParamVendors.getFromLocation()).toUpperCase());
				} else {
					inParameters.put("p_i_v_fr_location", RutDatabase.stringToDb(BLANK));
				}
				if (lstrProcessJOType.equals("T")) {
					inParameters.put("p_i_v_to_location",
							RutDatabase.stringToDb(ijsLookupParamVendors.getToLocation()).toUpperCase());
				} else {
					inParameters.put("p_i_v_to_location", RutDatabase.stringToDb(BLANK));
				}
				if (lstrProcessJOType.equals("T")) {
					inParameters.put("p_i_v_fr_terminal",
							RutDatabase.stringToDb(ijsLookupParamVendors.getFromTerminal()).toUpperCase());
				} else {
					inParameters.put("p_i_v_fr_terminal", RutDatabase.stringToDb(BLANK));
				}
				if (lstrProcessJOType.equals("T")) {
					inParameters.put("p_i_v_to_terminal",
							RutDatabase.stringToDb(ijsLookupParamVendors.getToTerminal()).toUpperCase());
				} else {
					inParameters.put("p_i_v_to_terminal", RutDatabase.stringToDb(BLANK));
				}
				inParameters.put("p_i_v_pptdhVal", RutDatabase.stringToDb(BLANK));
				inParameters.put("p_i_v_start_date", RutDatabase.stringToDb(BLANK));
				inParameters.put("p_i_v_end_date", RutDatabase.stringToDb(BLANK));
				/*
				 * if(lstrProcessJOType.equals("T")){ inParameters.put("p_i_v_routing_no",
				 * RutDatabase.stringToDb(ijsLookupParamVendors.getRoutingId())); }else{
				 * inParameters.put("p_i_v_routing_no",RutDatabase.stringToDb(BLANK)); }
				 */

				inParameters.put("p_i_v_routing_no", RutDatabase.stringToDb(ijsLookupParamVendors.getRoutingId()));
				/*
				 * inParameters.put("p_i_v_fr_loc_typ",
				 * RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsLookupParamVendors.
				 * getFromLocType())).toUpperCase());
				 * 
				 * 
				 * inParameters.put("p_i_v_to_loc_typ",
				 * RutDatabase.stringToDb(IjsHelper.getLocationCode(ijsLookupParamVendors.
				 * getToLocType())).toUpperCase());
				 * 
				 * 
				 * inParameters.put("p_i_v_fr_location",
				 * RutDatabase.stringToDb(ijsLookupParamVendors.getFromLocation()).toUpperCase()
				 * );
				 * 
				 * 
				 * inParameters.put("p_i_v_to_location",
				 * RutDatabase.stringToDb(ijsLookupParamVendors.getToLocation()).toUpperCase());
				 * 
				 * 
				 * inParameters.put("p_i_v_fr_terminal",
				 * RutDatabase.stringToDb(ijsLookupParamVendors.getFromTerminal()).toUpperCase()
				 * );
				 * 
				 * 
				 * inParameters.put("p_i_v_to_terminal",
				 * RutDatabase.stringToDb(ijsLookupParamVendors.getToTerminal()).toUpperCase());
				 * 
				 * 
				 * inParameters.put("p_i_v_pptdhVal", RutDatabase.stringToDb(BLANK));
				 * inParameters.put("p_i_v_start_date", RutDatabase.stringToDb(BLANK));
				 * inParameters.put("p_i_v_end_date", RutDatabase.stringToDb(BLANK));
				 * 
				 * inParameters.put("p_i_v_routing_no",
				 * RutDatabase.stringToDb(ijsLookupParamVendors.getRoutingId()));
				 * //inParameters.put("p_i_v_routing_no", RutDatabase.stringToDb(""));
				 * 
				 */
				// inParameters.put("p_i_v_bkgbl_no", RutDatabase.stringToDb(""));
				inParameters.put("p_i_v_bkgbl_no",
						RutDatabase.stringToDb(ijsLookupParamVO.getBkgOrBLNumber().toUpperCase()));
				inParameters.put("p_i_v_container_no_e", RutDatabase.stringToDb(lsbContainere.toString()));
				inParameters.put("p_i_v_container_no_l", RutDatabase.stringToDb(lsbContainerl.toString()));
				inParameters.put("p_i_v_costcalflg", RutDatabase.stringToDb(lstrCostCalFlg));
				if (index == 0)
					inParameters.put("p_i_v_delerteflg", RutDatabase.stringToDb(lstrDeleteFlg));
				/*else
					inParameters.put("p_i_v_delerteflg", RutDatabase.stringToDb("N"));
				index++;
				*/

				inParameters.put("p_i_v_session_id", RutDatabase.stringToDb(lstrSessionId));
				inParameters.put("p_i_v_cntSize", RutDatabase.stringToDb(ijsLookupParamVO.getCntSize()));
				inParameters.put("p_i_v_cntType", RutDatabase.stringToDb(ijsLookupParamVO.getCntType()));
				inParameters.put("p_i_v_cntSplHandling", RutDatabase.stringToDb(ijsLookupParamVO.getCntSplHandling()));
				inParameters.put("p_i_v_user_id", RutDatabase.stringToDb(userId).toUpperCase());
				if ("Y".equals(lstrCostCalFlg)) {
					outMap = execute(inParameters);
				}

				
				
			     if(!ijsLookupParamVendors.getVendorCode().equalsIgnoreCase(ijsLookupParamVO.getFindForVendorCode())&& "Booking".equalsIgnoreCase(ijsLookupParamVO.getFindIn().trim())) {
		                //String setInterCharge=((IjsJORoutingLookUpVO) list.get(i-1)).getVendorCode();
			    	        String setInterCharge=ijsLookupParamVO.getFindForVendorCode();
							String whereInchargeCarrier = ijsLookupParamVendors.getVendorCode();
							getJdbcTemplate().update(
									"update vasapps.booking_voyage_routing_dtl bvrd  set  INTERCHANGE_CARRIER =? where bvrd.booking_no=? and INTERCHANGE_CARRIER=?",
									new Object[] { setInterCharge, ijsLookupParamVO.getBkgOrBLNumber(),
											whereInchargeCarrier });
							
		                }else if(!ijsLookupParamVendors.getVendorCode().equalsIgnoreCase(ijsLookupParamVO.getFindForVendorCode())&& "BL".equalsIgnoreCase(ijsLookupParamVO.getFindIn().trim()))
		                {
		             	  //String setInterCharge=((IjsJORoutingLookUpVO) list.get(i-1)).getVendorCode();
		                	  String setInterCharge=ijsLookupParamVO.getFindForVendorCode();
							  String whereInchargeCarrier = ijsLookupParamVendors.getVendorCode();
							  getJdbcTemplate().update(
									"update vasapps.idp005 bvrd  set  INTERCHANGE_CARRIER =? where bvrd.SYBLNO=? and INTERCHANGE_CARRIER=?",
									new Object[] { setInterCharge, ijsLookupParamVO.getBkgOrBLNumber(),
											whereInchargeCarrier });
		                }
				
				
				
				String lstrErrorCode = (String) outMap.get("p_o_v_err_cd");
				System.out.println(lstrErrorCode);
				if (lstrErrorCode != null) {
					if (lstrErrorCode != null) {
						if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20001.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20001.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20002.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20003.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_MAINT_JO_EX_20004.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10042.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10042.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10041.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10041.getErrorCode();
						} else if (lstrErrorCode.equals(IjsErrorCode.DB_IJS_CNTR_EX_10043.name())) {
							lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10043.getErrorCode();
						} else {
							lstrErrorCode = IjsErrorCode.DB_IJS_CNTR_EX_10042.getErrorCode();
						}
					}
					IjsProcessJOSumDtlDTO ijsProcessJOSumDtlDTO = new IjsProcessJOSumDtlDTO();
					ijsProcessJOSumDtlDTO.setErrorCode(lstrErrorCode);
					ijsProcessJOSumDtlDTO.setBokingBL((String) outMap.get("p_o_v_failed_bkg_bl"));
					llstJOSummarySearchReturn.add(ijsProcessJOSumDtlDTO);
					// resetJO(astrSessionId,userId);
				} else {
					// System.out.println("dataFromDB"+(List<IjsProcessJOSummarySearchDTO>)outMap.get("p_o_v_ijs_mapping_list"));

					outputList.addAll((List<IjsProcessJOSumDtlDTO>) outMap.get("p_o_v_ijs_mapping_list"));
				}
			}
			if (outputList.size() == 0)
				return llstJOSummarySearchReturn;
			else
				return outputList;
		}
	}

	private class IjsJOVendorsLookUpRowMapper implements RowMapper {

		public Object mapRow(ResultSet resultSet, int row) {

			IjsJORoutingLookUpVO routingModel = new IjsJORoutingLookUpVO();
			IjsProcessJOSummarySearchDTO ijsJOSummarySearchDto = new IjsProcessJOSummarySearchDTO();
			try {
				ijsJOSummarySearchDto.setSlNumber(resultSet.getString("slNumber"));
				ijsJOSummarySearchDto.setJobOrder(resultSet.getString("jobOrder"));
				ijsJOSummarySearchDto.setVendorCode(resultSet.getString("vendorCode"));
				ijsJOSummarySearchDto.setContractNumber(resultSet.getString("contractNumber"));
				ijsJOSummarySearchDto.setJoDate(resultSet.getString("joDate"));
				ijsJOSummarySearchDto.setSize(resultSet.getString("sizecont"));
				ijsJOSummarySearchDto.setType(resultSet.getString("typecont"));
				ijsJOSummarySearchDto.setOOG(resultSet.getString("OOG"));
				ijsJOSummarySearchDto.setMtOrLaden(resultSet.getString("mtOrLaden"));
				ijsJOSummarySearchDto.setRate(resultSet.getString("rate"));
				ijsJOSummarySearchDto.setQuantity(resultSet.getString("quantity"));
				//ijsJOSummarySearchDto.setAmount(RutFormatting.getStringToDecimalFormat(resultSet.getString("amount"), null));
				ijsJOSummarySearchDto.setAmount(resultSet.getString("amount"));
			    ijsJOSummarySearchDto.setCurrency(resultSet.getString("currency"));
				ijsJOSummarySearchDto.setAmountUsd(resultSet.getString("amountUsd"));
				ijsJOSummarySearchDto.setPaymentFSC(resultSet.getString("paymentFSC"));
				ijsJOSummarySearchDto.setPriority(resultSet.getString("PRIORITY1"));
				ijsJOSummarySearchDto.setRateBasis(resultSet.getString("RATE_BASIS"));
				ijsJOSummarySearchDto.setLumpsumId(resultSet.getString("LUMPSUM_ID"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ijsJOSummarySearchDto;

		}
	}

}
