package com.abhinav3254.webflux.sec04.controller;


import com.abhinav3254.webflux.sec04.dto.CustomerDTO;
import com.abhinav3254.webflux.sec04.exceptions.ApplicationExceptions;
import com.abhinav3254.webflux.sec04.service.CustomerService;
import com.abhinav3254.webflux.sec04.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDTO> getAllCustomer() {
        return this.customerService.getAllCustomer();
    }

    @GetMapping("paginated")
    public Mono<List<CustomerDTO>> getAllCustomer(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "3") Integer size
    ) {
        return this.customerService.getAllCustomer(page,size)
                .collectList();
    }

    @GetMapping("{id}")
    public Mono<CustomerDTO> getCustomer(@PathVariable("id") Integer id) {
        return this.customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @PostMapping
    public Mono<CustomerDTO> saveCustomer(@RequestBody Mono<CustomerDTO> customerDTO) {
        return customerDTO.transform(RequestValidator.validate())
                .as(this.customerService::saveCustomer);
    }

    @PutMapping("{id}")
    public Mono<CustomerDTO> updateCustomer(@PathVariable Integer id, @RequestBody Mono<CustomerDTO> customerDTOMono) {
        return customerDTOMono.transform(RequestValidator.validate())
                .as(validReq -> this.customerService.updateCustomer(id, validReq))
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return this.customerService.deleteCustomerById(id)
                .filter(b->b)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .then();
    }

}
