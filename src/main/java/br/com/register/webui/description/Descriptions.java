package br.com.register.webui.description;

public class Descriptions {

    // Error Response Class
    public static final String HTTP_CODE = "HTTP status code of the error";
    public static final String HTTP_DESCRIPTION = "Description of the HTTP error";
    public static final String MESSAGE = "Error message";
    public static final String PATH = "Request path";
    public static final String TIMESTAMP = "Timestamp of the error";
    public static final String FIELDS = "List of fields with errors";
    public static final String FIELD = "Name of the field with error";
    public static final String FIELD_MESSAGE = "Error message for the field";

    public static final String RAZAO_SOCIAL = "Company's legal name";
    public static final String NOME_FANTANSIA = "Trade name";
    public static final String CNPJ = "CNPJ (Brazilian company ID)";
    public static final String ID = "Unique identifier";
    public static final String ATIVO = "Active indicator. Allowed values: (S=YES / N=NO)";
    public static final String PAGE = "Page number";
    public static final String LIMIT = "Number of records per page";
    public static final String SORT = "Sorting order of the list";
    public static final String DESCRIPTION_PRODUCT = "Product description";
    public static final String DESCRIPTION_CATEGORY = "Category description";
    public static final String ID_PRODUTO = "Unique identifier of the product";
    public static final String ID_CLIENTE = "Unique identifier of the client";
    public static final String ID_EMPRESA = "Unique identifier of the company";
    public static final String OBSERVACAO = "Free-text field for order notes";
    public static final String NAME_CUSTOMER = "Client name";
    public static final String CPF_CUSTOMER = "Client CPF (Brazilian ID)";
    public static final String PHONE_CUSTOMER = "Phone number";
    public static final String EMAIL_CUSTOMER = "Client's email address";
    public static final String UNIT_PRICE_PRODUCT = "Unit price of the product";
    public static final String CATEGORY_PRODUCT = "Category associated with the product";
    public static final String CATEGORY_ID = "Unique identifier of the category";
    public static final String IMAGE_PRODUCT = "Image to display the product";
    public static final String DESCRIPTION_PAYMENT_METHOD = "Description of the payment method";
    public static final String PAYMENT_TYPE_PAYMENT_METHOD = "Type of payment method. Allowed values: [\"DEBITO\", \"CREDITO\", \"PIX\"]";
    public static final String LISTA_ITENS_PEDIDO = "List of items in the order";
    public static final String QTD_PRODUTO = "Quantity of products";
    public static final String DATA_PEDIDO = "Date when the order was placed. Format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String BLOCO_EMPRESA = "Company information block";
    public static final String BLOCO_CLIENTE = "Client information block";
    public static final String VLR_TOTAL = "Total order amount";
    public static final String STATUS_PEDIDO = "Order status. Available values: RECEIVED, IN_PREPARATION, READY, COMPLETED";
    public static final String PEDIDO_ID = "Unique identifier of the order";
    public static final String FORMA_PAGAMENTO_ID = "Unique identifier of the payment method";
    public static final String QRCODE = "Payment QRCode";
    public static final String IDENTIFICADOR_PAGAMENTO = "Unique payment identifier";
    public static final String VLR_PAGAMENTO = "Amount of the payment made";
    public static final String STATUS_PAGAMENTO = "Payment status. Available values: PENDING, CONFIRMED, DECLINED";

    // Pagination
    public static final String HAS_NEXT = "Indicates if there is a next page with elements";
    public static final String HAS_PREVIOUS = "Indicates if there is a previous page";
    public static final String PAGE_NUMBER = "Current page number";
    public static final String PAGE_SIZE = "Number of elements on the current page";
    public static final String ITEMS = "List of items on the page";
}
