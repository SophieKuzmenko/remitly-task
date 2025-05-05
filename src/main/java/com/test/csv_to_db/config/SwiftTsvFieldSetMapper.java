package com.test.csv_to_db.config;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import com.test.csv_to_db.model.SwiftTsv; // Assuming this is your SwiftCsv class package

public class SwiftTsvFieldSetMapper implements FieldSetMapper<SwiftTsv> {

    @Override
    public SwiftTsv mapFieldSet(FieldSet fieldSet) throws BindException {
        SwiftTsv swiftTsv = new SwiftTsv();

        swiftTsv.setCountryISO2(fieldSet.readString("COUNTRY ISO2 CODE"));
        swiftTsv.setSwiftCode(fieldSet.readString("SWIFT CODE"));
        swiftTsv.setCodeType(fieldSet.readString("CODE TYPE"));
        swiftTsv.setBankName(fieldSet.readString("NAME"));
        swiftTsv.setAddress(fieldSet.readString("ADDRESS"));
        swiftTsv.setTownName(fieldSet.readString("TOWN NAME"));
        swiftTsv.setCountryName(fieldSet.readString("COUNTRY NAME"));
        swiftTsv.setTimeZone(fieldSet.readString("TIME ZONE"));

        return swiftTsv;
    }
}