package br.com.register.errors;

public class Errors {

    // GENERIC
    public static final String FIELD_REQUIRED = "Field is required";
    public static final String QUERY_PARAMS_REQUERIDO = "Parameter is required";
    public static final String VALOR_MAIOR_QUE_0 = "The value must be greater than zero";

    // Company
    public static final String CNPJ_INVALIDO = "The provided CNPJ must be exactly 14 characters long";
    public static final String EMPRESA_JA_CADASTRADA = "Company already registered";
    public static final String EMPRESA_NAO_ENCONTRADA = "Company not found";

    // Product
    public static final String UNIT_AMOUNT_FORMAT_INVALID = "Unit price must have a maximum of 10 integer digits and 2 decimal places";
    public static final String CATEGORY_NOT_EXISTS = "Category does not exist";
    public static final String CUSTOMER_EXISTS = "The provided CPF is already registered";
    public static final String CUSTOMER_NOT_EXISTS = "Client not found";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String PEDIDO_NAO_ENCONTRADO = "Order not found";
    public static final String FORMA_PAGAMENTO_NAO_ENCONTRADO = "Payment method not found";

    // Order
    public static final String PEDIDO_VALOR_TOTAL_ZERO = "Order has zero payment amount";
    public static final String FORMA_PAGAMENTO_NAO_DISPONIVEL = "Payment method is not available";
    public static final String PEDIDO_STATUS_DIFERENTE_RECEBIDO = "Order status is different from 'received'";
    public static final String VALOR_TOTAL_PEDIDO_DIFERENTE_SOMATORIO_ITENS = "Total order value is different from the sum of item values";
    public static final String LISTA_ITENS_DEVE_TER_UM_ELEMENTO = "Item list must contain at least one element";
    public static final String STATUS_INVALIDO = "Order must be 'in preparation' to move to 'ready', or 'ready' to move to 'finished'";
    public static final String PEDIDO_PAGAMENTO_INVALIDO = "Payment not found";

    // Pagination
    public static final String PAGE_MIN = "Minimum page number is 1";

    // Client
    public static final String CLIENTE_NAO_ENCONTRADO = "Client not found";

    // ENUMS
    public static final String ENUM_ATIVO_INVALIDO = "Invalid value. Allowed values: [S, N]";

}
