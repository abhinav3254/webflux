package com.abhinav3254.webflux.tests.sec03;

import com.abhinav3254.webflux.sec03.dto.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec03")
public class CustomerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void allCustomers() {
        this.webTestClient.get()
                // no need to add localhost:8080 because we are using AutoConfigureWebTestClient
                .uri("/customer")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomerDTO.class)
                .value(list -> logger.info("{}",list))
                .hasSize(10);
    }

    @Test
    public void paginatedCustomers() {
        this.webTestClient.get()
                // no need to add localhost:8080 because we are using AutoConfigureWebTestClient
                .uri("/customer/paginated?page=3&size=2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(r-> logger.info("{}",new String(r.getResponseBody())))
                .jsonPath("$.length()")
                .isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(7)
                .jsonPath("$[1].id").isEqualTo(8);
    }

}
