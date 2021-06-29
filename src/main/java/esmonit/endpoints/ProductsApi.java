package esmonit.endpoints;

import esmonit.product.domain.Product;
import esmonit.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ProductsApi {
    @Autowired
    ProductService productService;

    // /cars/_search
    @GetMapping("/products")
    @ResponseBody
    public List<Product> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable int id){
        return productService.getProduct(id);
    }

    @PostMapping("/products")
    @ResponseBody
    public ResponseEntity<String> addProduct(@RequestParam("id") int id, @RequestParam("name") String name,
                         @RequestParam("category") String category){
        if(productService.addProduct(id,name,category) >= 1){
            return ResponseEntity.ok().body(String.valueOf(id));
        }else{
            return ResponseEntity.badRequest().body("Failed to create product");
        }
    }

    @ApiOperation(value = "Add a product")
    @PutMapping ("/products")
    public Integer create(@RequestBody Product product){
        log.debug("Creating a product -> {} ", product);
        return productService.create(product);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
    if(productService.delete(id) >= 1){
            return ResponseEntity.ok("Deleted");
        }else{
            return ResponseEntity.badRequest().body("Invalid id");
        }
    }

    @GetMapping("/products/_count")
    public Long count() {
        return productService.getTotalProducts();
    }

    private final int INSERT_BATCH_SIZE = 2;

    @PutMapping("/products/_bulk")
    public ProductBulkResponse add(List<Product> products) {

        return new ProductBulkResponse();
    }
}
