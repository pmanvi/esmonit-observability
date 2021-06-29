package taskmanager.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class TaskComment {
    @Id
    private String id;
    private String email;
    private String author;
    private Date   creationDate;
    private boolean isActive;
    private String description;

    public TaskComment() {
        this.id = UUID.randomUUID().toString();
        creationDate = new Date();
    }
}
