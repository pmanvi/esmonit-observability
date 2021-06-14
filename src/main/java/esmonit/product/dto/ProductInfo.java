package esmonit.product.dto;

import esmonit.product.domain.ProductType;
import esmonit.security.AuditInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductInfo {
    private int             id;
    private String          name;
    private String          category;
    private ProductType     type;
    private AuditInfo       auditInfo;
    private LocalDateTime   servedAt;
}
