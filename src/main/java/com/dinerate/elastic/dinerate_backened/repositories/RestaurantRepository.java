package com.dinerate.elastic.dinerate_backened.repositories;

import org.springframework.data.domain.Page;
import com.dinerate.elastic.dinerate_backened.domains.entities.Restaurants;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends ElasticsearchRepository<Restaurants, String> {

    Page<Restaurants> findByAverageRatingGreaterThanEqual(Float minRating, Pageable pageable);


    @Query("""
    {
      "bool": {
        "must": [
          {
            "range": {
              "averageRating": {
                "gte": ?1
              }
            }
          }
        ],
        "should": [
          {
            "fuzzy": {
              "name": {
                "value": "?0",
                "fuzziness": "AUTO"
              }
            }
          },
          {
            "fuzzy": {
              "cuisineType": {
                "value": "?0",
                "fuzziness": "AUTO"
              }
            }
          }
        ],
        "minimum_should_match": 1
      }
    }
    """)
    Page<Restaurants> findByQueryAndMinRating(String query, Float minRating, Pageable pageable);

    Page<Restaurants> findByGeoLocationNear(Float latitude, Float longitude, Float radiusKm,Pageable pageable);

}
