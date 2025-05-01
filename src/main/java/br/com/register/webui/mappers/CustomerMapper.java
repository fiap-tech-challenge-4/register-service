package br.com.register.webui.mappers;

import br.com.register.app.entities.Customer;
import br.com.register.webui.dtos.CustomerDTO;
import br.com.register.webui.dtos.request.UpdateCustomerRequest;
import br.com.register.webui.dtos.request.RegisterCustomerRequest;
import br.com.register.webui.dtos.response.CustomerResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toCustomerDto(RegisterCustomerRequest request);

    CustomerDTO toCustomerDto(UpdateCustomerRequest request);

    Customer toEntity(CustomerDTO customerDTO);

    CustomerResponse toCustomerResponse(Customer customer);

    List<CustomerResponse> toResponseList(List<Customer> listCustomer);
}
