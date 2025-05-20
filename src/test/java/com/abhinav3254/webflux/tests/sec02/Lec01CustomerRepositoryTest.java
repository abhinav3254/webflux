package com.abhinav3254.webflux.tests.sec02;

import com.abhinav3254.webflux.sec02.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec01CustomerRepositoryTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec01CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Unit test method for verifying the behavior of the `findAll()` method in the {@link CustomerRepository}.
     * <p>
     * This test performs the following:
     * 1. Invokes the `findAll()` method of the `CustomerRepository`, which is expected to return a reactive stream (Flux) of customer entities.
     * 2. Logs each customer item emitted by the Flux using SLF4J (`doOnNext`) for debugging or verification purposes.
     * 3. Converts the Flux into a `StepVerifier` stream using `.as(StepVerifier::create)`, which is a Reactor testing utility for verifying reactive sequences.
     * 4. Asserts that exactly 10 items (customers) are emitted by the Flux using `expectNextCount(10)`.
     * 5. Expects the Flux to complete successfully after emitting those 10 items using `expectComplete()`.
     * 6. Triggers the verification using `verify()`, which will fail the test if any of the above expectations are not met.
     * <p>
     * This test assumes that the repository is pre-populated with exactly 10 customer records (e.g., via test data initialization).
     * If the count is incorrect or the stream fails or never completes, the test will fail.
     */
    @Test
    public void findAll() {
        this.customerRepository.findAll()
                .doOnNext(c->log.info("{}",c))
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById() {
        this.customerRepository.findById(2)
                .doOnNext(c->log.info("{}",c))
                .as(StepVerifier::create)
                .assertNext(c-> Assertions.assertEquals("mike",c.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByName() {
        this.customerRepository.findByName("jake")
                .doOnNext(c->log.info("{}",c))
                .as(StepVerifier::create)
                .assertNext(c-> Assertions.assertEquals("jake@gmail.com",c.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByEmailEndingWith() {
        this.customerRepository.findByEmailEndingWith("ke@gmail.com")
                .doOnNext(c->log.info("{}",c))
                .as(StepVerifier::create)
                .assertNext(c-> Assertions.assertEquals("mike@gmail.com",c.getEmail()))
                .assertNext(c-> Assertions.assertEquals("jake@gmail.com",c.getEmail()))
                .expectComplete()
                .verify();
    }

}
