package esmonit.product.service;

import esmonit.product.dao.ProductRepository;
import esmonit.product.domain.ProductType;
import org.springframework.stereotype.Component;

@Component
public class ProductService extends ProductRepository implements ProductDao {
    public ProductType getProductType(int productId) {
        // Lengthy algorithm to cacludate one.
        return ProductType.Premium;
    }
}
