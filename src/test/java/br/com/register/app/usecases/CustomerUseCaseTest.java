package br.com.register.app.usecases;

import br.com.register.app.entities.Customer;
import br.com.register.app.exception.BusinessException;
import br.com.register.app.repositories.CustomerRepository;
import br.com.register.webui.dtos.CustomerDTO;
import br.com.register.webui.dtos.response.CustomerResponse;
import br.com.register.webui.dtos.response.PaginationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static br.com.register.errors.Errors.CUSTOMER_EXISTS;
import static br.com.register.errors.Errors.CUSTOMER_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerUseCaseTest {

    @InjectMocks
    private CustomerUseCase customerUseCase;
    @Mock
    private CustomerRepository customerRepository;

    private Customer customer;
    private CustomerDTO customerDTO;
    private CustomerResponse customerResponse;

    @BeforeEach
    public void setUp() {
        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("Test User");
        customerDTO.setCpf("12345678900");
        customerDTO.setPhone("123456789");
        customerDTO.setEmail("test@example.com");

        customer = new Customer();
        customer.setId(1L);
        customer.setName("Test User");
        customer.setCpf("12345678900");
        customer.setPhone("123456789");
        customer.setEmail("test@example.com");

        customerResponse = new CustomerResponse();
        customerResponse.setId(1L);
        customerResponse.setName("Test User");
        customerResponse.setCpf("12345678900");
        customerResponse.setPhone("123456789");
        customerResponse.setEmail("test@example.com");
    }

    @Test
    public void testRegisterCustomerSuccess() {
        when(customerRepository.findByCpf(any())).thenReturn(null);
        when(customerRepository.save(any())).thenReturn(customer);

        CustomerResponse response = customerUseCase.registerCustomer(customerDTO);

        assertNotNull(response);
        assertEquals(customerDTO.getCpf(), response.getCpf());
    }

    @Test
    public void testRegisterCustomerAlreadyExists() {
        when(customerRepository.findByCpf(any())).thenReturn(customer);

        var exception = assertThrows(BusinessException.class, () -> customerUseCase.registerCustomer(customerDTO));
        assertEquals(CUSTOMER_EXISTS, exception.getMessage());
    }

    @Test
    public void testUpdateCustomerSuccess() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(customer);

        CustomerResponse response = customerUseCase.updateCustomer(1L, customerDTO);

        assertNotNull(response);
        assertEquals(customerDTO.getEmail(), response.getEmail());
    }

    @Test
    public void testDeleteCustomerSuccess() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        assertDoesNotThrow(() -> customerUseCase.deleteCustomer(1L));
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void testSearchCustomerByIdSuccess() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        CustomerResponse response = customerUseCase.searchCustomerById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    public void testSearchCustomerByIdNotFound() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        var exception = assertThrows(BusinessException.class, () -> customerUseCase.searchCustomerById(1L));
        assertEquals(CUSTOMER_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void testSearchCustomerByCpfSuccess() {
        when(customerRepository.findByCpf(any())).thenReturn(customer);

        CustomerResponse response = customerUseCase.searchCustomer(customerDTO.getCpf());

        assertNotNull(response);
        assertEquals(customerDTO.getCpf(), response.getCpf());
    }

    @Test
    public void testSearchCustomerByCpfNotFound() {
        when(customerRepository.findByCpf(any())).thenReturn(null);

        var exception = assertThrows(BusinessException.class, () -> customerUseCase.searchCustomer(customerDTO.getCpf()));
        assertEquals(CUSTOMER_NOT_EXISTS, exception.getMessage());
    }

    @Test
    public void testListCustomers() {
        var pageable = PageRequest.of(0, 10, Sort.by("id"));
        var page = new PageImpl<>(List.of(customer), pageable, 1L);

        when(customerRepository.findAll(any(Pageable.class))).thenReturn(page);

        PaginationResponse<CustomerResponse> response = customerUseCase.listCustomers(0, 10, "asc");

        assertNotNull(response);
        assertEquals(1, response.getItems().size());
    }
}