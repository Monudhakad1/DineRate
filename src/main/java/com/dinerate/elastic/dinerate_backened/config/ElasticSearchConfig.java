package com.dinerate.elastic.dinerate_backened.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(
        basePackages = "com.dinerate.elastic.dinerate_backened.repositories"
)
public class ElasticSearchConfig {
}
