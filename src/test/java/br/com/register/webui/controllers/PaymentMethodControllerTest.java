package br.com.register.webui.controllers;

import br.com.register.app.usecases.PaymentMethodUseCase;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.dtos.response.PaymentMethodResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentMethodControllerTest {

    @InjectMocks
    private PaymentMethodController controller;
    @Mock
    private PaymentMethodUseCase paymentMethodUseCase;

    private PaymentMethodResponse paymentMethodResponse;
    private PaginationResponse<PaymentMethodResponse> paginationResponse;

    @BeforeEach
    void setup() {
        paymentMethodResponse = new PaymentMethodResponse();
        paymentMethodResponse.setId(1L);
        paymentMethodResponse.setDescription("Cartão de Crédito");
        paymentMethodResponse.setPaymentType("CREDIT");

        paginationResponse = new PaginationResponse<>();
        paginationResponse.setItems(List.of(paymentMethodResponse));
        paginationResponse.setPageNumber(1);
        paginationResponse.setPageSize(25);
        paginationResponse.setHasNext(false);
        paginationResponse.setHasPrevious(false);
    }

    @Test
    public void shouldReturnSuccessfully_listPaymentMethod() {
        Integer page = 1;
        Integer limit = 25;
        String sort = "DESC";

        when(paymentMethodUseCase.listPaymentMethod(page, limit, sort)).thenReturn(paginationResponse);

        ResponseEntity<PaginationResponse<PaymentMethodResponse>> response =
                controller.listPaymentMethod(page, limit, sort);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getItems().size());
        assertEquals("Cartão de Crédito", response.getBody().getItems().get(0).getDescription());
        verify(paymentMethodUseCase).listPaymentMethod(page, limit, sort);
    }
}