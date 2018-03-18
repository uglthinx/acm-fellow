package me.xiaff.crawler.acmfellow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AcmFellowApplication {

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv6Addresses", "true");
        System.out.println("System.setProperty(\"java.net.preferIPv6Addresses\", \"true\");");
        SpringApplication.run(AcmFellowApplication.class, args);

    }
}
