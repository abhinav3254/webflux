package com.abhinav3254.webflux.sec02.repository;

import com.abhinav3254.webflux.sec02.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository<Customer,Integer> {
}
