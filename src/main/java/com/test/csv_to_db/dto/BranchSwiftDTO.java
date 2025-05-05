package com.test.csv_to_db.dto;

import java.util.Objects;

public class BranchSwiftDTO {
    private final String address;
    private final String bankName;
    private final String countryISO2;
    private final Boolean isHeadquarter;
    private final String swiftCode;

    public BranchSwiftDTO(String address, String bankName, String countryISO2, Boolean isHeadquarter, String swiftCode) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }

    public String getAddress() {
        return address;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public Boolean getIsHeadquarter() {
        return isHeadquarter;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BranchSwiftDTO that = (BranchSwiftDTO) o;
        return Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getBankName(), that.getBankName()) && Objects.equals(getCountryISO2(), that.getCountryISO2()) && Objects.equals(getIsHeadquarter(), that.getIsHeadquarter()) && Objects.equals(getSwiftCode(), that.getSwiftCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getBankName(), getCountryISO2(), getIsHeadquarter(), getSwiftCode());
    }
}
