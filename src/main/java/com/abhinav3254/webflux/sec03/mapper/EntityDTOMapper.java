package com.abhinav3254.webflux.sec03.mapper;

import com.abhinav3254.webflux.sec03.dto.CustomerDTO;
import com.abhinav3254.webflux.sec03.entity.Customer;

public class EntityDTOMapper {

    public static Customer toEntity(CustomerDTO customerDTO) {
        var customer = new Customer();
        customer.setId(customerDTO.id());
        customer.setName(customerDTO.name());
        customer.setEmail(customerDTO.email());
        return customer;
    }

    public static CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName() ,customer.getEmail());
    }

}
