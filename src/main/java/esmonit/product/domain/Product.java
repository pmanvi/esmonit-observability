package esmonit.product.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String  name;
    private String  category;
}
