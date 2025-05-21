package com.abhinav3254.webflux.sec03.controller;


import com.abhinav3254.webflux.sec03.dto.CustomerDTO;
import com.abhinav3254.webflux.sec03.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDTO> getAllCustomer() {
        return this.customerService.getAllCustomer();
    }

    @GetMapping("{id}")
    public Mono<CustomerDTO> getCustomer(@PathVariable("id") Integer id) {
        return this.customerService.getCustomerById(id);
    }

    @PostMapping
    public Mono<CustomerDTO> saveCustomer(@RequestBody Mono<CustomerDTO> customerDTO) {
        return this.customerService.saveCustomer(customerDTO);
    }

    @PutMapping("{id}")
    public Mono<CustomerDTO> updateCustomer(@PathVariable Integer id,@RequestBody Mono<CustomerDTO> customerDTOMono) {
        return this.customerService.updateCustomer(id,customerDTOMono);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return this.customerService.deleteCustomerById(id);
    }

}
