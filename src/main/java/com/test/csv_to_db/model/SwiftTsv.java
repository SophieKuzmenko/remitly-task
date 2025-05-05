package com.test.csv_to_db.model;

public class SwiftTsv {
    private String countryISO2;
    private String swiftCode;
    private String CodeType;
    private String bankName;
    private String address;
    private String townName;
    private String countryName;
    private String timeZone;

    public SwiftTsv(String countryISO2, String swiftCode, String codeType, String address, String bankName, String townName, String countryName, String timeZone) {
        this.countryISO2 = countryISO2;
        this.swiftCode = swiftCode;
        CodeType = codeType;
        this.address = address;
        this.bankName = bankName;
        this.townName = townName;
        this.countryName = countryName;
        this.timeZone = timeZone;
    }

    public SwiftTsv() {
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public String getCodeType() {
        return CodeType;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAddress() {
        return address;
    }

    public String getTownName() {
        return townName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public void setCodeType(String codeType) {
        CodeType = codeType;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
