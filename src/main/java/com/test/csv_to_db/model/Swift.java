package com.test.csv_to_db.model;

import com.test.csv_to_db.dto.BranchSwiftDTO;
import com.test.csv_to_db.dto.SwiftDTO;
import com.test.csv_to_db.exception.InvalidArgumentException;
import com.test.csv_to_db.exception.NullFieldException;
import jakarta.persistence.*;

import java.util.Objects;

@Table(name="SWIFT_CODES", indexes ={
        @Index(name="iso2Index", columnList = "COUNTRYISO2"),
        @Index(name= "swiftIndex", columnList = "SWIFT_CODE", unique = true),
        @Index(name="branchIndex", columnList = "BRANCH_SWIFT")
})
@Entity
public class Swift {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;

    private String bankName;

    @Column(nullable = false)
    private String countryISO2;

    @Column(nullable = false)
    private String countryName;

    @Column(nullable = false)
    private Boolean isHeadquarter;

    @Column(unique = true, nullable = false)
    private String swiftCode;

    @Column(nullable = false)
    private String branchSwiftCode;



    public Swift() {
    }

    public Swift(String address, String bankName, String countryISO2, String countryName, Boolean isHeadquarter, String swiftCode) {

        verifyArguments(address, bankName, countryISO2, countryName, isHeadquarter,swiftCode);
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
        this.branchSwiftCode = swiftCode.substring(0,8);
    }

    private void verifyArguments(String address, String bankName, String countryISO2, String countryName, Boolean isHeadquarter, String swiftCode) {
        if ((swiftCode == null))
            throw new NullFieldException("Swift code must not be null");
        if (isHeadquarter == null)
            throw new NullFieldException("IsHeadquarter must be null");
        if (countryISO2 == null)
            throw new NullFieldException("Country ISO2 code must be null");
        if (countryName == null)
            throw new NullFieldException("Country name must not be null");
        if (countryISO2.length()!=2)
            throw new InvalidArgumentException("Country ISO2 code must contain only two letters");
        if (!(swiftCode.length()==11))
            throw new InvalidArgumentException("Swift code must contain 11 symbols");
        if ((isHeadquarter && !swiftCode.endsWith("XXX")) || (!isHeadquarter && swiftCode.endsWith("XXX"))  )
            throw new InvalidArgumentException("Headquarters swift code must end with 'XXX'");
    }


    public static Swift fromSwiftCsv(SwiftTsv swiftTsv)
    {
        String code = swiftTsv.getSwiftCode();
        return new Swift(swiftTsv.getAddress(),
                swiftTsv.getBankName(),
                swiftTsv.getCountryISO2(),
                swiftTsv.getCountryName(),
                code.endsWith("XXX"),
                code);
    }
    public SwiftDTO toDTO(){
        return new SwiftDTO(address,
                bankName,
                countryISO2,
                countryName,
                isHeadquarter,
                swiftCode
        );
    }
    public BranchSwiftDTO toBranchDTO() {
        return new BranchSwiftDTO(address,
                bankName,
                countryISO2,
                isHeadquarter,
                swiftCode

        );
    }

    public Long getId() {
        return id;
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

    public String getCountryName() {
        return countryName;
    }

    public Boolean getIsHeadquarter() {
        return isHeadquarter;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public String getBranchSwiftCode() {
        return branchSwiftCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Swift swift = (Swift) o;
        return Objects.equals(getId(), swift.getId()) && Objects.equals(getAddress(), swift.getAddress()) && Objects.equals(getBankName(), swift.getBankName()) && Objects.equals(getCountryISO2(), swift.getCountryISO2()) && Objects.equals(getCountryName(), swift.getCountryName()) && Objects.equals(getIsHeadquarter(), swift.getIsHeadquarter()) && Objects.equals(getSwiftCode(), swift.getSwiftCode()) && Objects.equals(getBranchSwiftCode(), swift.getBranchSwiftCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getBankName(), getCountryISO2(), getCountryName(), getIsHeadquarter(), getSwiftCode(), getBranchSwiftCode());
    }
}
