package br.com.register.errors;

public class Errors {

    // GENERIC
    public static final String FIELD_REQUIRED = "Field is required";
    public static final String QUERY_PARAMS_REQUERIDO = "Parameter is required";

    // Product
    public static final String UNIT_AMOUNT_FORMAT_INVALID = "Unit price must have a maximum of 10 integer digits and 2 decimal places";
    public static final String CATEGORY_NOT_EXISTS = "Category does not exist";
    public static final String CUSTOMER_EXISTS = "The provided CPF is already registered";
    public static final String PRODUCT_NOT_FOUND = "Product not found";

    // Pagination
    public static final String PAGE_MIN = "Minimum page number is 1";

    // Client
    public static final String CUSTOMER_NOT_EXISTS = "Client not found";

}
