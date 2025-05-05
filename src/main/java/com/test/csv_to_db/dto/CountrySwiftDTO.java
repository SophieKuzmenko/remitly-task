package com.test.csv_to_db.dto;

import com.test.csv_to_db.model.Swift;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CountrySwiftDTO {

    private final String countryISO2;
    private final String countryName;
    private List<BranchSwiftDTO> swiftCodes;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CountrySwiftDTO that = (CountrySwiftDTO) o;
        return Objects.equals(getCountryISO2(), that.getCountryISO2()) && Objects.equals(getCountryName(), that.getCountryName()) && Objects.equals(getSwiftCodes(), that.getSwiftCodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryISO2(), getCountryName(), getSwiftCodes());
    }

    public CountrySwiftDTO(String countryISO2, String countryName)
    {
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.swiftCodes = new ArrayList<>();
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public List<BranchSwiftDTO> getSwiftCodes() {
        return swiftCodes;
    }

    public void setSwiftCodes(List<BranchSwiftDTO> swiftCodes) {
        this.swiftCodes = swiftCodes;
    }
}

