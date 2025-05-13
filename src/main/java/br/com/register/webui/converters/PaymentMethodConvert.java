package br.com.register.webui.converters;

import br.com.register.app.entities.PaymentMethod;
import br.com.register.webui.dtos.response.PaymentMethodResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentMethodConvert {

    public static List<PaymentMethodResponse> toResponseList(List<PaymentMethod> content) {
        if (content == null)
            return null;

        return content.stream()
                .map(paymentMethod -> PaymentMethodResponse.builder()
                        .id(paymentMethod.getId())
                        .description(paymentMethod.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
