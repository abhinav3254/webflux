package com.abhinav3254.webflux.sec04.service;


import com.abhinav3254.webflux.sec04.dto.CustomerDTO;
import com.abhinav3254.webflux.sec04.mapper.EntityDTOMapper;
import com.abhinav3254.webflux.sec04.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Flux<CustomerDTO> getAllCustomer() {
        return this.customerRepository.findAll()
                .map(EntityDTOMapper::toDTO);
    }

    public Flux<CustomerDTO> getAllCustomer(Integer page, Integer size) {
        return this.customerRepository.findBy(PageRequest.of(page,size))
                .map(EntityDTOMapper::toDTO);
    }

    public Mono<CustomerDTO> getCustomerById(Integer id) {
        return this.customerRepository.findById(id)
                .map(EntityDTOMapper::toDTO);
    }

    public Mono<CustomerDTO> saveCustomer(Mono<CustomerDTO> customerDTOMono) {
        return customerDTOMono.map(EntityDTOMapper::toEntity)
                .flatMap(this.customerRepository::save)
                .map(EntityDTOMapper::toDTO);
    }

    public Mono<CustomerDTO> updateCustomer(Integer id, Mono<CustomerDTO> mono) {
        // find customer by id
        return this.customerRepository.findById(id)
                // if customer found then replace that with new customer
                .flatMap(entity->mono)
                // convert it into entity
                .map(EntityDTOMapper::toEntity)
                // set the id bcz we are updating
                .doOnNext(c->c.setId(id))
                // save it
                .flatMap(this.customerRepository::save)
                // convert into dto to return
                .map(EntityDTOMapper::toDTO);
    }

    public Mono<Boolean> deleteCustomerById(Integer id) {
        return this.customerRepository.deleteCustomerById(id);
    }

}
