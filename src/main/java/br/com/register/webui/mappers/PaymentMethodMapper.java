package br.com.register.webui.mappers;

import br.com.register.app.entities.PaymentMethod;
import br.com.register.webui.dtos.response.PaymentMethodResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    List<PaymentMethodResponse> toResponseList(List<PaymentMethod> content);
}
