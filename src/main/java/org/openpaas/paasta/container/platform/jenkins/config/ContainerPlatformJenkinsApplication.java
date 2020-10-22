package org.openpaas.paasta.container.platform.jenkins.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@ComponentScan
public class ContainerPlatformJenkinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainerPlatformJenkinsApplication.class, args);
    }
}

