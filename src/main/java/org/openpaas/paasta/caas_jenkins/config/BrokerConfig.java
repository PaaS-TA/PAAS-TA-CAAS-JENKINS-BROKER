package org.openpaas.paasta.caas_jenkins.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories("org.openpaas.paasta.caas_jenkins.repo")
@EntityScan(value = "org.openpaas.paasta.caas_jenkins.model")
@ComponentScan(basePackages = {"org.openpaas.paasta.caas_jenkins","org.openpaas.servicebroker"})
public class BrokerConfig {
}
