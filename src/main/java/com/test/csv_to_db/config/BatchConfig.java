package com.test.csv_to_db.config;

import com.test.csv_to_db.model.Swift;
import com.test.csv_to_db.model.SwiftTsv;
import com.test.csv_to_db.repository.SwiftRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    ///
    private final SwiftRepository swiftRepository;

    public BatchConfig(SwiftRepository swiftRepository) {
        this.swiftRepository = swiftRepository;
    }

    public FlatFileItemReader<SwiftTsv> itemReader(){
        FlatFileItemReader<SwiftTsv> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/swift.tsv"));
        itemReader.setName("csv-reader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<SwiftTsv> lineMapper() {
        DefaultLineMapper<SwiftTsv> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("\t");
        tokenizer.setNames(new String[]{"COUNTRY ISO2 CODE", "SWIFT CODE", "CODE TYPE", "NAME", "ADDRESS", "TOWN NAME", "COUNTRY NAME", "TIME ZONE"});
        tokenizer.setStrict(false);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new SwiftTsvFieldSetMapper());

        return lineMapper;
    }

    @Bean
    public SwiftProcessor itemProcessor(){
        return new SwiftProcessor();
    }

    @Bean
    public RepositoryItemWriter<Swift> itemWriter(){
        RepositoryItemWriter<Swift> writer = new RepositoryItemWriter<>();
        writer.setRepository(swiftRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step(JobRepository repository, PlatformTransactionManager transactionManager){
        return new StepBuilder("csv-step",repository)
                .<SwiftTsv, Swift>chunk(1,transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("csv-job",jobRepository)
                .flow(step(jobRepository,transactionManager))
                .end()
                .build();
    }
}
