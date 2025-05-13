package br.com.register.webui.converters;

import br.com.register.app.entities.Customer;
import br.com.register.webui.dtos.CustomerDTO;
import br.com.register.webui.dtos.request.RegisterCustomerRequest;
import br.com.register.webui.dtos.request.UpdateCustomerRequest;
import br.com.register.webui.dtos.response.CustomerResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerConvert {

    public static CustomerDTO toCustomerDto(RegisterCustomerRequest request) {
        if (request == null)
            return null;

        return CustomerDTO.builder()
                .name(request.getName())
                .cpf(request.getCpf())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
    }

    public static CustomerDTO toCustomerDto(UpdateCustomerRequest request) {
        if (request == null)
            return null;

        return CustomerDTO.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null)
            return null;

        return Customer.builder()
                .name(customerDTO.getName())
                .cpf(customerDTO.getCpf())
                .phone(customerDTO.getPhone())
                .email(customerDTO.getEmail())
                .build();
    }

    public static CustomerResponse toCustomerResponse(Customer customer) {
        if (customer == null)
            return null;

        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .cpf(customer.getCpf())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }

    public static List<CustomerResponse> toResponseList(List<Customer> listCustomer) {
        if (listCustomer == null)
            return null;

        return listCustomer.stream()
                .map(CustomerConvert::toCustomerResponse)
                .collect(Collectors.toList());
    }
}
