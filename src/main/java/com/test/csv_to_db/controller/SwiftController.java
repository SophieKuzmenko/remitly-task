package com.test.csv_to_db.controller;

import com.test.csv_to_db.dto.CountrySwiftDTO;
import com.test.csv_to_db.dto.DeleteSwiftDTO;
import com.test.csv_to_db.dto.SwiftDTO;
import com.test.csv_to_db.service.SwiftService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/swift-codes")
@RestController
public class SwiftController {
    private final SwiftService swiftService;
    private final JobLauncher jobLauncher;
    private final Job job;

    public SwiftController(SwiftService swiftService, JobLauncher jobLauncher, Job job) {
        this.swiftService = swiftService;
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    // TODO: possibly add to the constructor? Not to ask the user to first send the request to load the database
    @GetMapping(value="/start")
    public BatchStatus startBatch() throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException,
            JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        JobExecution run = jobLauncher.run(job, jobParameters);
        return run.getStatus();
    }

    @GetMapping("/{swiftCode}")
    public ResponseEntity<SwiftDTO> getSwiftCode(@PathVariable String swiftCode){
        return ResponseEntity.ok(swiftService.getSwiftByCode(swiftCode));
    }

    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<CountrySwiftDTO> getSwiftsForCountry(@PathVariable String countryISO2code) {
        return ResponseEntity.ok(swiftService.getAllSwiftsForCountry(countryISO2code));
    }

    @PostMapping("")
    public ResponseEntity<String> addSwiftCode(@RequestBody SwiftDTO swiftDTO){
        swiftService.addSwift(swiftDTO);
        return ResponseEntity.ok(String.format("Swift code %s successfully added",swiftDTO.getSwiftCode()));
    }

    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<DeleteSwiftDTO> deleteSwift(@PathVariable String swiftCode) {
        swiftService.deleteSwiftByCode(swiftCode);
        var deleteSwiftDTO = new DeleteSwiftDTO(String.format("Swift code %s successfully deleted", swiftCode));
        return ResponseEntity.ok(deleteSwiftDTO);
    }

}
