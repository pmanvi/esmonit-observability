package esmonit.product.service;

import esmonit.product.domain.Product;
import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();
    Product getProduct(Integer productId);
    Integer addProduct(Integer id, String name, String category);
    Integer delete(Integer id);
    Integer create(Product product);
    Long getTotalProducts();
}
