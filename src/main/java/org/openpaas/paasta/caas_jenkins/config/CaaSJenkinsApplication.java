package org.openpaas.paasta.caas_jenkins.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@ComponentScan
public class CaaSJenkinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaaSJenkinsApplication.class, args);
    }
}

