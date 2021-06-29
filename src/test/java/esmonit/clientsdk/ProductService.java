package esmonit.clientsdk;

import esmonit.product.domain.Product;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProductService {

    @GET("/products")
    Call<List<Product>> getAllProducts();

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") Integer productId);

    @POST("/products")
    Call<Integer> addProduct(@Query("id") Integer Integer,
                             @Query("name") String name,
                             @Query("category")String category);

    @PUT("/products")
    Call<Integer> create(@Body Product product);

    @DELETE("/products/{id}")
    Call<Integer> delete(@Path("id") Integer id);

    @GET("/products/_count")
    Call<Long> count();
}
