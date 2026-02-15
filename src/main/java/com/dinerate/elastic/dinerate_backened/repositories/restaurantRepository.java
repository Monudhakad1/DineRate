package com.dinerate.elastic.dinerate_backened.repositories;

import com.dinerate.elastic.dinerate_backened.domains.entities.Restaurants;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface restaurantRepository extends ElasticsearchRepository<Restaurants,String> {

}
