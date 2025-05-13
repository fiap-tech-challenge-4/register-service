package br.com.register.app.usecases;

import br.com.register.app.entities.PaymentMethod;
import br.com.register.app.repositories.PaymentMethodRepository;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.dtos.response.PaymentMethodResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentMethodUseCaseTest {

    @InjectMocks
    private PaymentMethodUseCase paymentMethodUseCase;
    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Test
    public void shouldSuccessfully_listPaymentMethod() {
        int page = 1;
        int limit = 2;
        String sort = "DESC";

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "id"));
        List<PaymentMethod> paymentMethods = List.of(
                createPaymentMethod(1L, "Credit Card", "CREDIT"),
                createPaymentMethod(2L, "PIX", "PIX")
        );

        Page<PaymentMethod> pagePaymentMethods = new PageImpl<>(paymentMethods, pageable, paymentMethods.size());

        List<PaymentMethodResponse> responseList = List.of(
                createPaymentMethodResponse(1L, "Credit Card", "CREDIT"),
                createPaymentMethodResponse(2L, "PIX", "PIX")
        );

        when(paymentMethodRepository.findAll(pageable)).thenReturn(pagePaymentMethods);

        PaginationResponse<PaymentMethodResponse> result = paymentMethodUseCase.listPaymentMethod(page, limit, sort);

        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getItems().get(0).getDescription()).isEqualTo("Credit Card");
        assertThat(result.getPageNumber()).isEqualTo(1);
        assertThat(result.getPageSize()).isEqualTo(limit);
        assertThat(result.getHasNext()).isTrue();
        assertThat(result.getHasPrevious()).isFalse();

        verify(paymentMethodRepository).findAll(pageable);
    }

    private PaymentMethod createPaymentMethod(Long id, String description, String type) {
        PaymentMethod method = new PaymentMethod();
        method.setId(id);
        method.setDescription(description);
        method.setPaymentType(type);
        return method;
    }

    private PaymentMethodResponse createPaymentMethodResponse(Long id, String description, String type) {
        PaymentMethodResponse response = new PaymentMethodResponse();
        response.setId(id);
        response.setDescription(description);
        response.setPaymentType(type);
        return response;
    }
}