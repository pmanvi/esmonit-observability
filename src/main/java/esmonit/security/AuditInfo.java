package esmonit.security;

import esmonit.product.domain.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class AuditInfo {
    private String  domain;
    private String  domainId;
    private Date    createdDate;
    private Date    updatedDate;
    private String  updatedBy;
    private String  createdBy;
}
