/*-----------------------------------------------------------------------------------------------------------
IjsExcelTemplateConstants.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 24/08/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 20/10/17  NIIT       IJS            keeps IJS IjsExcelTemplateConstants 
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.ijs.web.constants;

public enum IjsExcelTemplateConstants {
    VENDOR(0),VENDOR_NAME(1),CONTRACT(2),ROUTING(3),START_DATE(4),END_DATE(5),TRANSPORT_MODE(6),
    STATUS(7),PAYMENT_FSC(8),CURRENCY(9),PRIORITY(10),FROM_LOC_TYPE(11),FROM_LOCATION(12),FROM_TERMINAL(13),
    TO_LOC_TYPE(14),TO_LOCATION(15),TO_TERMINAL(16),DAYS(17),HOURS(18),DISTANCE(19),UOM(20),EXCEMPTED(21),
    TERM(22),
    
    // COST RATE CONSTANTS
     COST_RATE_START_DATE(23),COST_RATE_END_DATE(24),COST_RATE_SERVICE(25),COST_RATE_VESSEL(26),COST_RATE_MT_LADEN(27),COST_RATE_BASIS(28),
     COST_RATE_EQ_CATEGORY(29),COST_RATE_STATUS(30),COST_RATE_CHARGE_CODE(31),
     //COST_RATE_TERM(31),
     COST_RATE_CALC_METHOD(32),COST_RATE_EQ_TYPE(33), COST_RATE_UPTO(34),COST_RATE_UOM(35),
     //COST_RATE_IMP_EXP(35),
     COST_RATE_SPL_HANDLING(36),COST_RATE_CURRENCY(37),COST_RATE_PORT_CLASS(38),COST_RATE_IMDG(39),
     COST_RATE_OOG(40),COST_RATE_SP_CUSTOMER(41),COST_RATE_COST20(42),
     //COST_RATE_PER_TRIP(42),
     COST_RATE_COST40(43),COST_RATE_COST45(44),COST_RATE_LUMP_SUM(45),
     COST_RATE_SEQ_NO(46),
     COST_DTL_SEQ_NOS(47),//MD
     
     // BILL RATE CONSTANTS
     BILL_RATE_START_DATE(22),BILL_RATE_END_DATE(23),BILL_RATE_SERVICE(24),BILL_RATE_MT_LADEN(25),
    BILL_RATE_RATE_BASIS(26),BILL_RATE_EQ_CATEGORY(27),BILL_RATE_RATE_STATUS(28)
    //,BILL_RATE_TERM(30),
    ,BILL_RATE_CALC_METHOD(29),BILL_RATE_EQ_TYPE(30),BILL_RATE_UPTO(31),BILL_RATE_UOM(32),BILL_RATE_CURRENCY(33),
    BILL_RATE_BILL20(34),BILL_RATE_BILL40(35),BILL_RATE_BILL45(36),BILL_RATE_SEQ_NO(37)
    ,BILL_DTL_SEQ_NOS(38);
     
    int index;
    IjsExcelTemplateConstants(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }
}
