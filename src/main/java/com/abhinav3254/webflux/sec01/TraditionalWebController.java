package com.abhinav3254.webflux.sec01;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Flux;

import java.util.List;


@RestController
@RequestMapping("traditional")
public class TraditionalWebController {

    private static final Logger log = LoggerFactory.getLogger(TraditionalWebController.class);

    // This is new rest template
    private final RestClient restClient = RestClient.builder()
            .requestFactory(new JdkClientHttpRequestFactory())
            .baseUrl("http://localhost:7070")
            .build();

    @GetMapping("products")
    public List<Product> getProducts() {
        var list = this.restClient.get()
                .uri("/demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {
                });

        log.info("received response {}",list);
        return list;
    }


    /**
     * This is a wrong way this is not reactive programming
     */
    @GetMapping("products2")
    public Flux<Product> getProducts2() {

        // Why this wrong because see this part even you can cancel the last line (fromIterable) will understand that okay we have to cancel
        // the request but this restClient already made the request means that it will still get the response which is not correct
        var list = this.restClient.get()
                .uri("/demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {
                });

        log.info("received response {}",list);
        return Flux.fromIterable(list);
    }

}
