package guru.springframework.restmvc.mappers;

import guru.springframework.restmvc.entities.Customer;
import guru.springframework.restmvc.model.CustomerDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO customerToCustomerDTO(Customer customer);
    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
