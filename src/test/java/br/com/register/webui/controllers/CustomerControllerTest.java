package br.com.register.webui.controllers;

import br.com.register.app.usecases.CustomerUseCase;
import br.com.register.webui.dtos.request.RegisterCustomerRequest;
import br.com.register.webui.dtos.request.UpdateCustomerRequest;
import br.com.register.webui.dtos.response.CustomerResponse;
import br.com.register.webui.dtos.response.PaginationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerUseCase customerUseCase;

    private CustomerResponse customerResponse;

    @BeforeEach
    public void setUp() {
        customerResponse = new CustomerResponse();
        customerResponse.setId(1L);
        customerResponse.setName("John Doe");
        customerResponse.setCpf("12345678900");
        customerResponse.setPhone("123456789");
        customerResponse.setEmail("john@example.com");
    }

    @Test
    public void shouldSuccessfully_registerCustomer() {
        RegisterCustomerRequest request = new RegisterCustomerRequest();
        request.setName("John Doe");
        request.setCpf("12345678900");
        request.setPhone("123456789");
        request.setEmail("john@example.com");

        when(customerUseCase.registerCustomer(any())).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.registerCustomer(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals("12345678900", response.getBody().getCpf());
        verify(customerUseCase).registerCustomer(any());
    }

    @Test
    public void shouldSuccessfully_updateCustomer() {
        Long customerId = 1L;
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setName("John Updated");

        var objResponse = customerResponse;
        objResponse.setName("John Updated");

        when(customerUseCase.updateCustomer(eq(customerId), any())).thenReturn(objResponse);

        ResponseEntity<CustomerResponse> response = customerController.updateCustomer(customerId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Updated", response.getBody().getName());
        verify(customerUseCase).updateCustomer(eq(customerId), any());
    }

    @Test
    public void shouldSuccessfully_deleteCustomer() {
        Long customerId = 1L;

        ResponseEntity<Void> response = customerController.deletaProduto(customerId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(customerUseCase).deleteCustomer(customerId);
    }

    @Test
    public void shouldSuccessfully_searchCustomerById() {
        Long customerId = 1L;

        when(customerUseCase.searchCustomerById(customerId)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.searchCustomerById(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
        verify(customerUseCase).searchCustomerById(customerId);
    }

    @Test
    public void shouldSuccessfully_searchCustomerByCpf() {
        String cpf = "12345678900";

        when(customerUseCase.searchCustomer(cpf)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.searchCustomer(cpf);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
        verify(customerUseCase).searchCustomer(cpf);
    }

    @Test
    public void shouldSuccessfully_listCustomers() {
        
        Integer page = 1;
        Integer limit = 25;
        String sort = "DESC";

        List<CustomerResponse> customerResponses = List.of(customerResponse);
        PaginationResponse<CustomerResponse> paginationResponse = new PaginationResponse<>();
        paginationResponse.setItems(customerResponses);
        paginationResponse.setPageNumber(page);
        paginationResponse.setPageSize(limit);
        paginationResponse.setHasNext(true);
        paginationResponse.setHasPrevious(false);

        when(customerUseCase.listCustomers(page, limit, sort)).thenReturn(paginationResponse);

        ResponseEntity<PaginationResponse<CustomerResponse>> response = customerController.listCustomers(page, limit, sort);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getItems().size());
        assertEquals("John Doe", response.getBody().getItems().get(0).getName());
        verify(customerUseCase).listCustomers(page, limit, sort);
    }

}