package org.jarvis.datasource.tx;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.datasource.tx.program.ProgramTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author:tennyson  date:2020/7/5
 */
@SpringBootApplication
@Slf4j
public class TransactionDemoApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(TransactionDemoApplication.class, args);
    }

    @Autowired
    private ProgramTransaction programTransaction;

    @Override
    public void run(String... args) {
        programTransaction.transaction();
    }


}
