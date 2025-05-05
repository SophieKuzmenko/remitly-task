package com.test.csv_to_db.service;

import com.test.csv_to_db.dto.BranchSwiftDTO;
import com.test.csv_to_db.dto.CountrySwiftDTO;
import com.test.csv_to_db.dto.SwiftDTO;
import com.test.csv_to_db.exception.SwiftException;
import com.test.csv_to_db.model.Swift;
import com.test.csv_to_db.repository.SwiftRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SwiftService {
    private final SwiftRepository swiftRepository;

    public SwiftService(SwiftRepository swiftRepository) {
        this.swiftRepository = swiftRepository;
    }

    public SwiftDTO getSwiftByCode(String swiftCode)
    {
        Optional<Swift> swiftOptional = swiftRepository.findBySwiftCode(swiftCode);
        if (swiftOptional.isEmpty())
            throw new SwiftException("Swift code not found");

        Swift swift = swiftOptional.get();
        SwiftDTO res = swift.toDTO();
        if (swift.getIsHeadquarter())
        {
            List<BranchSwiftDTO> branches = getAllBranches(swift);
            res.setBranches(branches);
        }
        return res;
    }

    private List<BranchSwiftDTO> getAllBranches(Swift headquarters)
    {
        List<BranchSwiftDTO> res = new ArrayList<>();
        List<Swift> branchSwifts = swiftRepository.findAllByBranchSwiftCode(headquarters.getBranchSwiftCode());
        branchSwifts.remove(headquarters);
        branchSwifts.forEach((swift) -> res.add(swift.toBranchDTO()));

        return res;
    }

    public CountrySwiftDTO getAllSwiftsForCountry(String countryISO2code)
    {
        List<Swift> foundSwifts = swiftRepository.findAllByCountryISO2(countryISO2code);
        // Verifying if the passed ISO2 code exists
        if ( foundSwifts.isEmpty())
        {
            throw new SwiftException("Country ISO2 code not found");
        }
        // Extracting country name
        String countryName = foundSwifts.get(0).getCountryName();

        List<BranchSwiftDTO> swifts = new ArrayList<>();
        foundSwifts.forEach((swift)->swifts.add(swift.toBranchDTO()));
        CountrySwiftDTO res = new CountrySwiftDTO(countryISO2code,countryName);
        res.setSwiftCodes(swifts);
        return res;
    }

    public void addSwift(SwiftDTO newSwiftCode)
    {
        Swift newSwift = new Swift(
                newSwiftCode.getAddress(),
                newSwiftCode.getBankName(),
                newSwiftCode.getCountryISO2(),
                newSwiftCode.getCountryName(),
                newSwiftCode.getIsHeadquarter(),
                newSwiftCode.getSwiftCode()
        );
        swiftRepository.save(newSwift);
    }

    public void deleteSwiftByCode(String swiftCode)
    {
        Optional<Swift> swiftOptional = swiftRepository.findBySwiftCode(swiftCode);
        if (swiftOptional.isEmpty()){
            throw new SwiftException("Swift code not found");
        }
        Swift swift = swiftOptional.get();
        swiftRepository.delete(swift);
    }
}
