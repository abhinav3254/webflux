package com.abhinav3254.webflux.sec02.repository;

import com.abhinav3254.webflux.sec02.entity.CustomerOder;
import com.abhinav3254.webflux.sec02.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;


@Repository
public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOder, UUID> {

    @Query("""
            SELECT
            p.*
            FROM
            customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON co.product_id = p.id
                    WHERE
            c.name = :name
            """)
    Flux<Product> getProductsOrderByCustomer(String name);

}