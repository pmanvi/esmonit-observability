package esmonit.endpoints.exceptions;

import esmonit.product.domain.Product;

public class ProductException extends RuntimeException {
    private Product product;
    private static final String PRODUCT_NOT_FOUND ="Product not found for id %s";

}
