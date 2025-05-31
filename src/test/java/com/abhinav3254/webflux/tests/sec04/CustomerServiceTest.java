package com.abhinav3254.webflux.tests.sec04;

import com.abhinav3254.webflux.sec04.dto.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec04")
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
                .consumeWith(r-> logger.info("{}",new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.length()")
                .isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(7)
                .jsonPath("$[1].id").isEqualTo(8);
    }

    @Test
    public void customerById() {
        this.webTestClient.get()
                // no need to add localhost:8080 because we are using AutoConfigureWebTestClient
                .uri("/customer/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(r-> logger.info("{}",new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("sam")
                .jsonPath("$.email").isEqualTo("sam@gmail.com");
    }

    @Test
    public void createAndDeleteCustomer() {
        var dto = new CustomerDTO(null,"abhinav","abhinav@gmail.com");
        this.webTestClient.post()
                .uri("/customer")
                .bodyValue(dto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(r-> logger.info("{}",new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(11)
                .jsonPath("$.name").isEqualTo("abhinav")
                .jsonPath("$.email").isEqualTo("abhinav@gmail.com");

        // delete
        this.webTestClient.delete()
                .uri("/customer/11")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .isEmpty();
    }

    @Test
    public void updateCustomer() {
        var dto = new CustomerDTO(null,"jha","jha@gmail.com");
        this.webTestClient.put()
                .uri("/customer/10")
                .bodyValue(dto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(r-> logger.info("{}",new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(10)
                .jsonPath("$.name").isEqualTo("jha")
                .jsonPath("$.email").isEqualTo("jha@gmail.com");

    }


    @Test
    public void customerNotFound() {
        // get

        this.webTestClient.get()
                .uri("/customer/11")
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Customer [id=11] is not found");

        // delete

        this.webTestClient.delete()
                .uri("/customer/11")
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Customer [id=11] is not found");

        // put
        this.webTestClient.put()
                .uri("/customer/11")
                .bodyValue(new CustomerDTO(null,"jha","jha@gmail.com"))
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Customer [id=11] is not found");

    }

    /**
     * We use bodyValue when we have to use plain java object
     * but body we use when we have Mono<Object> then we use it.(Publisher Type)
     */

    @Test
    public void invalidInputTest() {
        // missing name scenario
        var missingName = new CustomerDTO(null,null,"noel@gmail.com");
        this.webTestClient.post()
                .uri("/customer")
                .bodyValue(missingName)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Name is required");

        // Missing email
        var missingEmail = new CustomerDTO(null,"noel",null);
        this.webTestClient.post()
                .uri("/customer")
                .bodyValue(missingEmail)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Valid email is required");

        // Invalid email
        var invalidEmail = new CustomerDTO(null,"noel","abhinav");
        this.webTestClient.put()
                .uri("/customer/10")
                .bodyValue(invalidEmail)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Valid email is required");
    }


}
