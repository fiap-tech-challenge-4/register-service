package br.com.register.app.usecases;

import br.com.register.app.entities.Customer;
import br.com.register.app.exception.BusinessException;
import br.com.register.app.repositories.CustomerRepository;
import br.com.register.utils.Pagination;
import br.com.register.webui.dtos.CustomerDTO;
import br.com.register.webui.dtos.response.CustomerResponse;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.register.errors.Errors.CUSTOMER_EXISTS;
import static br.com.register.errors.Errors.CUSTOMER_NOT_EXISTS;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class CustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerResponse registerCustomer(CustomerDTO customerDTO) {
        var customer = getCustomerByCpf(customerDTO.getCpf());
        if (customer != null)
            throw new BusinessException(CUSTOMER_EXISTS, UNPROCESSABLE_ENTITY);

        var entity = customerMapper.toEntity(customerDTO);
        var entityResponse = customerRepository.save(entity);
        return customerMapper.toCustomerResponse(entityResponse);
    }

    public CustomerResponse updateCustomer(Long id, CustomerDTO customerDTO) {
        var entity = getCustomerById(id);
        entity.setName(customerDTO.getName());
        entity.setPhone(customerDTO.getPhone());
        entity.setEmail(customerDTO.getEmail());
        Customer entityResponse = customerRepository.save(entity);
        return customerMapper.toCustomerResponse(entityResponse);
    }

    public void deleteCustomer(Long id) {
        var customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

    public CustomerResponse searchCustomerById(Long id) {
        return customerMapper.toCustomerResponse(getCustomerById(id));
    }

    public CustomerResponse searchCustomer(String cpf) {
        var customer = getCustomerByCpf(cpf);
        if (customer == null)
            throw new BusinessException(CUSTOMER_NOT_EXISTS, UNPROCESSABLE_ENTITY);
        return customerMapper.toCustomerResponse(customer);
    }

    public PaginationResponse<CustomerResponse> listCustomers(Integer page, Integer limit, String sort) {
        var pageable = Pagination.getPageRequest(limit, page, sort, "id");
        var customers = customerRepository.findAll(pageable);
        List<CustomerResponse> customersResponse = customerMapper.toResponseList(customers.getContent());
        return new PaginationResponse<CustomerResponse>().convertToResponse(new PageImpl(customersResponse, customers.getPageable(), 0L));
    }

    private Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CUSTOMER_NOT_EXISTS, UNPROCESSABLE_ENTITY));
    }

    private Customer getCustomerByCpf(String cpf) {
        return customerRepository.findByCpf(cpf);
    }
}
