package com.yuezhix.onlineorder.repository;


import com.yuezhix.onlineorder.entity.RestaurantEntity;
import org.springframework.data.repository.ListCrudRepository;


public interface RestaurantRepository extends ListCrudRepository<RestaurantEntity, Long> {
}
