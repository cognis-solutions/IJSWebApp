package com.rclgroup.dolphin.ijs.web.vo;

public class IjsCustomerLookupVO {
    private String custCode;
    private String custName;
    private String custCodeFI;
    private String city;
    private String country;
    private String status;


    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustCodeFI(String custCodeFI) {
        this.custCodeFI = custCodeFI;
    }

    public String getCustCodeFI() {
        return custCodeFI;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
