package com.rclgroup.dolphin.ijs.web.comparator;

import java.util.Comparator;

import com.rclgroup.dolphin.ijs.web.util.RutFormatting;
import com.rclgroup.dolphin.ijs.web.vo.IjsConvertedRateVO;
import com.rclgroup.dolphin.ijs.web.vo.IjsRateVO;
/**
 * 
 * @author Ashish.1.Rawat
 *
 */
public class IjsRateComparator implements Comparator<IjsConvertedRateVO>{
	String rateType;
	public static final String RATE20="RATE20";
	public static final String RATE40="RATE40";
	public static final String RATE45="RATE45";
	public IjsRateComparator(String rateType){
		this.rateType=rateType;
	}
	@Override
	public int compare(IjsConvertedRateVO ijsRateVO1, IjsConvertedRateVO ijsRateVO2) {
		double rate1=0;
		double rate2=0;
		switch(this.rateType){
		   case RATE20:
			   rate1=ijsRateVO1.getRate20();
			   rate2=ijsRateVO2.getRate20();
			   break;
		   case RATE40:
			   rate1=ijsRateVO1.getRate40();
			   rate2=ijsRateVO2.getRate40();
			   break;
		   case RATE45:
			   rate1=ijsRateVO1.getRate45();
			   rate2=ijsRateVO2.getRate45();
			   break;
		}
		if(rate1<rate2){
			return -1;
		}else if(rate1>rate2){
			return 1;
		}else{
			return 0;
		}
	}

}
