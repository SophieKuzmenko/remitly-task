package com.test.csv_to_db.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SwiftDTO {
    private final String address;
    private final String bankName;
    private final String countryISO2;
    private final String countryName;
    private final Boolean isHeadquarter;
    private final String swiftCode;
    private List<BranchSwiftDTO> branches;

    public SwiftDTO(String address, String bankName, String countryISO2, String countryName, Boolean isHeadquarter, String swiftCode) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
        branches = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCountryName() {
        return countryName;
    }

    public Boolean getIsHeadquarter() {
        return isHeadquarter;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public List<BranchSwiftDTO> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchSwiftDTO> branches) {
        this.branches = branches;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SwiftDTO swiftDTO = (SwiftDTO) o;
        return Objects.equals(getAddress(), swiftDTO.getAddress()) && Objects.equals(getBankName(), swiftDTO.getBankName()) && Objects.equals(getCountryISO2(), swiftDTO.getCountryISO2()) && Objects.equals(getCountryName(), swiftDTO.getCountryName()) && Objects.equals(getIsHeadquarter(), swiftDTO.getIsHeadquarter()) && Objects.equals(getSwiftCode(), swiftDTO.getSwiftCode()) && Objects.equals(getBranches(), swiftDTO.getBranches());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getBankName(), getCountryISO2(), getCountryName(), getIsHeadquarter(), getSwiftCode(), getBranches());
    }
}
