package com.abhinav3254.webflux.sec03.repository;

import com.abhinav3254.webflux.sec03.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer,Integer> {


}
