package com.abhinav3254.webflux.tests.sec02;

import com.abhinav3254.webflux.sec02.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec02ProductRepositoryTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec02ProductRepositoryTest.class);

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findByPriceRange() {
        this.productRepository.findByPriceBetween(750,1000)
                    .doOnNext(p->log.info("{}",p))
                    .as(StepVerifier::create)
                    .expectNextCount(3)
                    .expectComplete()
                    .verify();
    }

}
