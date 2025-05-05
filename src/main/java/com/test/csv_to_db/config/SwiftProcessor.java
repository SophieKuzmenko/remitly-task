package com.test.csv_to_db.config;

import com.test.csv_to_db.model.Swift;
import com.test.csv_to_db.model.SwiftTsv;
import org.springframework.batch.item.ItemProcessor;


public class SwiftProcessor implements ItemProcessor<SwiftTsv, Swift> {

    public SwiftProcessor() {
    }

    @Override
    public Swift process(SwiftTsv item) throws Exception {
        if (item==null) throw new NullPointerException("Passed value must not be null");
        Swift tmp = Swift.fromSwiftCsv(item);
        return tmp;
    }
}
