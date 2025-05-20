package com.abhinav3254.webflux.sec02.repository;

import com.abhinav3254.webflux.sec02.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product,Integer> {

    Flux<Product> findByPriceBetween(int from,int to);

//    findAllBy and findBy are same
    Flux<Product> findBy(Pageable pageable);

}
