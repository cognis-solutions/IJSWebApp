/*-----------------------------------------------------------------------------------------------------------
IjsContractTerminalLookUpVO.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 07/09/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 07/09/17  NIIT       IJS            value object for terminal lookup screen
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.vo;

public class IjsContractTerminalLookUpVO {
    private String terminal;
    private String terminalName;
    private String port;
    private String fsc;
    private String status;
    private String countryName;
    private String currencyCode;
    
    public IjsContractTerminalLookUpVO() {
    }


    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setFsc(String fsc) {
        this.fsc = fsc;
    }

    public String getFsc() {
        return fsc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
