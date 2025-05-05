package com.test.csv_to_db.service;

import com.test.csv_to_db.dto.BranchSwiftDTO;
import com.test.csv_to_db.dto.CountrySwiftDTO;
import com.test.csv_to_db.exception.InvalidArgumentException;
import com.test.csv_to_db.exception.NullFieldException;
import com.test.csv_to_db.exception.SwiftException;
import com.test.csv_to_db.model.Swift;
import com.test.csv_to_db.repository.SwiftRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

// Unit tests for SwiftService
public class SwiftServiceTest {
    private SwiftRepository swiftRepository;
    private SwiftService swiftService;

    // Some data for tests
    private Swift swift = new Swift(
            "HYRJA 3 RR. DRITAN HOXHA ND. 11 TIRANA, TIRANA, 1023",
            "UNITED BANK OF ALBANIA SH.A",
            "AL",
            "ALBANIA",
            true,
            "AAISALTRXXX"
    );
    private Swift swift1 = new Swift(
            "HYRJA 31 RR. DRITAN HOXHA ND. 34 TIRANA, TIRANA, 1023",
            "UNITED BANK OF ALBANIA SH.A",
            "AL",
            "ALBANIA",
            false,
            "AAISALTR123"
    );
    private Swift swift2 = new Swift(
            "HYRJA 32 RR. DRITAN HOXHA ND. 21 TIRANA, TIRANA, 1023",
            "UNITED BANK OF ALBANIA SH.A",
            "AL",
            "ALBANIA",
            false,
            "AAISALTR234"
    );

    @BeforeEach
    void setup(){
        swiftRepository = mock(SwiftRepository.class);
        swiftService = new SwiftService(swiftRepository);
    }

    @Test
    void getSwiftByCodeTest()
    {
        // Given
        var swiftList = new ArrayList<Swift>();
        swiftList.add(swift);
        swiftList.add(swift1);
        swiftList.add(swift2);
        var expected = swift.toDTO();
        var branchSwifts = new ArrayList<BranchSwiftDTO>();
        swiftList.forEach(sw->branchSwifts.add(sw.toBranchDTO()));
        branchSwifts.remove(swift.toBranchDTO());
        expected.setBranches(branchSwifts);
        String notPresentSwiftCode = "123456789ABC";

        given(swiftRepository.findBySwiftCode(swift.getSwiftCode())).willReturn(Optional.of(swift));
        given(swiftRepository.findAllByBranchSwiftCode(swift.getBranchSwiftCode())).willReturn(swiftList);
        given(swiftRepository.findBySwiftCode(notPresentSwiftCode)).willReturn(Optional.empty());
        // When
        var res = swiftService.getSwiftByCode(swift.getSwiftCode());
        var ex =  Assertions.assertThrows(SwiftException.class,
                ()-> {swiftService.getSwiftByCode(notPresentSwiftCode);});
        // Then
        Assertions.assertEquals(expected, res);
        Assertions.assertEquals("Swift code not found", ex.getMessage());
        verify(swiftRepository).findBySwiftCode(swift.getSwiftCode());
        verify(swiftRepository).findBySwiftCode(notPresentSwiftCode);
        verify(swiftRepository).findAllByBranchSwiftCode(swift.getBranchSwiftCode());
    }

    @Test
    void getAllSwiftsForCountryTest(){
        // Given
        String notPresentCountryISO2 = "AS";
        given(swiftRepository.findAllByCountryISO2(swift.getCountryISO2())).willReturn(List.of(swift,swift1,swift2));
        given(swiftRepository.findAllByCountryISO2(notPresentCountryISO2)).willReturn(List.of());
        // When
        var res = swiftService.getAllSwiftsForCountry(swift.getCountryISO2());
        var ex =  Assertions.assertThrows(SwiftException.class,
                ()-> {swiftService.getAllSwiftsForCountry(notPresentCountryISO2);});
        // Then
        var expected = new CountrySwiftDTO(swift.getCountryISO2(),swift.getCountryName());
        expected.setSwiftCodes(List.of(swift.toBranchDTO(), swift1.toBranchDTO(), swift2.toBranchDTO()));
        Assertions.assertEquals(expected, res);
        Assertions.assertEquals("Country ISO2 code not found", ex.getMessage());
        verify(swiftRepository).findAllByCountryISO2(swift.getCountryISO2());
        verify(swiftRepository).findAllByCountryISO2(notPresentCountryISO2);
    }

    @Test
    void addSwiftTest(){
        // Given
        String invalidSwift = "12341234";
        Boolean nullIsHeadquarter = null;
        given(swiftRepository.save(swift)).willReturn(swift);
        // When
        var ex1 = Assertions.assertThrows(InvalidArgumentException.class,
                ()-> {new Swift("HYRJA 3 RR. DRITAN HOXHA ND. 11 TIRANA, TIRANA, 1023",
                        "UNITED BANK OF ALBANIA SH.A",
                        "AL",
                        "ALBANIA",
                        true,
                        invalidSwift);});
        var ex2 = Assertions.assertThrows(InvalidArgumentException.class,
                ()-> {new Swift("HYRJA 3 RR. DRITAN HOXHA ND. 11 TIRANA, TIRANA, 1023",
                        "UNITED BANK OF ALBANIA SH.A",
                        "AL",
                        "ALBANIA",
                        false,
                        "AAISALTRXXX");});
        var ex3 = Assertions.assertThrows(NullFieldException.class,
                ()-> {new Swift("HYRJA 3 RR. DRITAN HOXHA ND. 11 TIRANA, TIRANA, 1023",
                        "UNITED BANK OF ALBANIA SH.A",
                        "AL",
                        "ALBANIA",
                        nullIsHeadquarter,
                        "AAISALTRXXX");});
        // Then
        Assertions.assertDoesNotThrow(()-> swiftService.addSwift(swift.toDTO()));
        verify(swiftRepository).save(swift);
        Assertions.assertEquals("Swift code must contain 11 symbols", ex1.getMessage());
        Assertions.assertEquals("Headquarters swift code must end with 'XXX'", ex2.getMessage());
        Assertions.assertEquals("IsHeadquarter must be null", ex3.getMessage());
    }

    @Test
    void deleteSwiftByCodeTest(){
        // Given
        String notPresentSwiftCode = "123456789ABC";
        given(swiftRepository.findBySwiftCode(swift.getSwiftCode())).willReturn(Optional.of(swift));
        given(swiftRepository.findBySwiftCode(notPresentSwiftCode)).willReturn(Optional.empty());
        // When
        var ex = Assertions.assertThrows(SwiftException.class, ()->swiftService.deleteSwiftByCode(notPresentSwiftCode));
        // Then
        Assertions.assertDoesNotThrow(()-> swiftService.deleteSwiftByCode(swift.getSwiftCode()));
        Assertions.assertEquals("Swift code not found", ex.getMessage());
        verify(swiftRepository).delete(swift);
    }
}
