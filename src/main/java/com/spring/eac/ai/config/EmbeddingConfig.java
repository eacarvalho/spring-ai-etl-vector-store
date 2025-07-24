package com.spring.eac.ai.config;

import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingConfig {

    /**
     * This is optional and the default implementation, check more:
     * https://docs.spring.io/spring-ai/reference/api/vectordbs.html#_default_implementation
     * @return
     */
    @Bean
    BatchingStrategy chromaBatchingStrategy() {
        return new TokenCountBatchingStrategy();
    }
}
