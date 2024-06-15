package eu.finbite.finbitecli;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@Slf4j
@ConfigurationPropertiesScan
@SpringBootApplication
public class FinbiteCliApplication  {

    public static void main(String[] args) {
        log.info("Starting FinbiteCliApplication");
        SpringApplication.run(FinbiteCliApplication.class, args);
        log.info("Finished FinbiteCliApplication");
    }

}
