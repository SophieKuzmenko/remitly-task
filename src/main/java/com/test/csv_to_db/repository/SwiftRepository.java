package com.test.csv_to_db.repository;

import com.test.csv_to_db.model.Swift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface SwiftRepository extends JpaRepository<Swift, Long> {
    Optional<Swift> findBySwiftCode(String swiftCode);
    List<Swift> findAllByCountryISO2(String countryISO2);
    Optional<Swift> findByCountryISO2(String countryISO2);
    List<Swift> findAllByBranchSwiftCode(String branchSwiftCode);
}
