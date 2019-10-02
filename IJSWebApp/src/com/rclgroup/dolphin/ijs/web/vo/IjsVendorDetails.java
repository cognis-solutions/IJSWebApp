 /*-----------------------------------------------------------------------------------------------------------
IjsVendorDetails.java
-------------------------------------------------------------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
-------------------------------------------------------------------------------------------------------------
Author NIIT 05/10/17
- Change Log ------------------------------------------------------------------------------------------------
## DD/MM/YY -User-     -TaskRef-      -Short Description
01 05/10/17  NIIT       IJS            value object for vendor details screen
-----------------------------------------------------------------------------------------------------------*/


package com.rclgroup.dolphin.ijs.web.vo;

public class IjsVendorDetails {
    public IjsVendorDetails() {
    }
    private String vendor;
    private Contact contact;
    private String vendorType;
    private Address address;
    private String currency;
    private String name2;
    private String country;
    private String mainContact;
    private String state;
    private String title;
    private String city;
    private String sCACCode;
    private String zip;
    private String vendorPayToNumber;
    private String responsibleFSC;
    private String purchaseOrderRequired;
    private String onlineUser;
    private String creditDays;
    private String vatRegistration;
    private String vendorEdiCode;
    private String headquatersCode;
    private String financeInterVndr;
    private String financeInterVndrCode;
    private String abbreviation;
    private String operatorCode;
    private String netOff_AROrAP;
    private String modeOfPayment;
    private String advancePayment;



    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor() {
        return vendor;
    }

   

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getVendorType() {
        return vendorType;
    }

   

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName2() {
        return name2;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setMainContact(String mainContact) {
        this.mainContact = mainContact;
    }

    public String getMainContact() {
        return mainContact;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setSCACCode(String sCACCode) {
        this.sCACCode = sCACCode;
    }

    public String getSCACCode() {
        return sCACCode;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setVendorPayToNumber(String vendorPayToNumber) {
        this.vendorPayToNumber = vendorPayToNumber;
    }

    public String getVendorPayToNumber() {
        return vendorPayToNumber;
    }

    public void setResponsibleFSC(String responsibleFSC) {
        this.responsibleFSC = responsibleFSC;
    }

    public String getResponsibleFSC() {
        return responsibleFSC;
    }

    public void setPurchaseOrderRequired(String purchaseOrderRequired) {
        this.purchaseOrderRequired = purchaseOrderRequired;
    }

    public String getPurchaseOrderRequired() {
        return purchaseOrderRequired;
    }

    public void setOnlineUser(String onlineUser) {
        this.onlineUser = onlineUser;
    }

    public String getOnlineUser() {
        return onlineUser;
    }

    public void setCreditDays(String creditDays) {
        this.creditDays = creditDays;
    }

    public String getCreditDays() {
        return creditDays;
    }

    public void setVatRegistration(String vatRegistration) {
        this.vatRegistration = vatRegistration;
    }

    public String getVatRegistration() {
        return vatRegistration;
    }

    public void setVendorEdiCode(String vendorEdiCode) {
        this.vendorEdiCode = vendorEdiCode;
    }

    public String getVendorEdiCode() {
        return vendorEdiCode;
    }

    public void setHeadquatersCode(String headquatersCode) {
        this.headquatersCode = headquatersCode;
    }

    public String getHeadquatersCode() {
        return headquatersCode;
    }


    public void setFinanceInterVndr(String financeInterVndr) {
        this.financeInterVndr = financeInterVndr;
    }

    public String getFinanceInterVndr() {
        return financeInterVndr;
    }

    public void setFinanceInterVndrCode(String financeInterVndrCode) {
        this.financeInterVndrCode = financeInterVndrCode;
    }

    public String getFinanceInterVndrCode() {
        return financeInterVndrCode;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setNetOff_AROrAP(String netOff_AROrAP) {
        this.netOff_AROrAP = netOff_AROrAP;
    }

    public String getNetOff_AROrAP() {
        return netOff_AROrAP;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setAdvancePayment(String advancePayment) {
        this.advancePayment = advancePayment;
    }

    public String getAdvancePayment() {
        return advancePayment;
    }

    public void setContact(IjsVendorDetails.Contact contact) {
        this.contact = contact;
    }

    public IjsVendorDetails.Contact getContact() {
        return contact;
    }

    public void setAddress(IjsVendorDetails.Address address) {
        this.address = address;
    }

    public IjsVendorDetails.Address getAddress() {
        return address;
    }

    public  class Contact {
        private String phone1;
        private String phone2;
        private String email;
        private String phnExtenstion;
        
        public String getPhone1() {
            return phone1;
        }

        public void setPhone1(String phone1) {
            this.phone1 = phone1;
        }
        
        public String getPhone2() {
            return phone2;
        }

        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPhnExtenstion() {
            return phnExtenstion;
        }

        public void setPhnExtenstion(String phnExtenstion) {
            this.phnExtenstion = phnExtenstion;
        }
        
    }
    public  class Address {
        private String add1;
        private String add2;
        private String add3;
        private String add4;
        
        
        public String getAdd1() {
            return add1;
        }

        public void setAdd1(String add1) {
            this.add1 = add1;
        }
        
        public String getAdd2() {
            return add2;
        }

        public void setAdd2(String add2) {
            this.add2 = add2;
        }
        public String getAdd3() {
            return add3;
        }

        public void setAdd3(String add3) {
            this.add3 = add3;
        }
        
        public String getAdd4() {
            return add4;
        }

        public void setAdd4(String add4) {
            this.add4 = add4;
        }
    }
}
