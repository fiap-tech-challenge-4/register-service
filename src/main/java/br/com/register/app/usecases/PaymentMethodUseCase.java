package br.com.register.app.usecases;

import br.com.register.app.repositories.PaymentMethodRepository;
import br.com.register.utils.Pagination;
import br.com.register.webui.dtos.response.PaginationResponse;
import br.com.register.webui.dtos.response.PaymentMethodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.register.webui.converters.PaymentMethodConvert.toResponseList;

@Service
@RequiredArgsConstructor
public class PaymentMethodUseCase {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaginationResponse<PaymentMethodResponse> listPaymentMethod(Integer page, Integer limit, String sort) {
        var pageable = Pagination.getPageRequest(limit, page, sort, "id");
        var listPaymentMethods = paymentMethodRepository.findAll(pageable);
        List<PaymentMethodResponse> paymentMethodResponse = toResponseList(listPaymentMethods.getContent());
        return new PaginationResponse<>().convertToResponse(new PageImpl(paymentMethodResponse, listPaymentMethods.getPageable(), 0L));
    }
}
