package org.openpaas.paasta.container.platform.jenkins.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gson bean config class (GsonBuilder, Converter for GsonBuilder)
 *
 * @author CheolHan Park
 * @version 1.0
 * @since 2019.11.16
 */
@Configuration
public class GsonConfig {

    /**
     * Instantiates a new Gson builder bean (without another GsonBuilder).
     *
     * @return the gson builder
     */
    @Bean
    public GsonBuilder gsonBuilder() {
        return new GsonBuilder();
    }

    /**
     * Instantiates a new Gson builder bean (with another GsonBuilder).
     *
     * @param builder the another gson builder
     * @return the gson builder
     */
    @Bean
    @Autowired
    public Gson gson(GsonBuilder builder) {
        return builder.create();
    }
}
