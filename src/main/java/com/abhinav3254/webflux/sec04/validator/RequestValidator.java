package com.abhinav3254.webflux.sec04.validator;

import com.abhinav3254.webflux.sec04.dto.CustomerDTO;
import com.abhinav3254.webflux.sec04.exceptions.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<CustomerDTO>> validate() {
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationExceptions.missingName())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationExceptions.missingValidEmail());
    }

    private static Predicate<CustomerDTO> hasName() {
        return dto -> Objects.nonNull(dto.name());
    }

    private static Predicate<CustomerDTO> hasValidEmail() {
        return dto -> Objects.nonNull(dto.email()) && dto.email().contains("@");
    }

}
