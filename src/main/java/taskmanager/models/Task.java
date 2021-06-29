package taskmanager.models;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Tasks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Task {
    @Id
    private String id;

    @Size(max = 100)
    //@Indexed(unique = true)
    private String title;

    private List<String> squad;
    private String owner;

    @Size(max = 256)
    private String description;

    private Boolean completed;

    private Date createdAt;
    private Date startDate;
    private Date endDate;
    private List<String> tags;

    List<Task> subTasks;
    List<TaskComment> comments;

    public static void main(String[] args) {
        log.info("Showcasing the lombok integration");

        Task task = Task.builder().title("Test").id("23123").completed(true)
                .owner("Operative").build();

        log.info("Testing {}", task);

    }

    public void tags(String... args) {
        this.tags = Arrays.asList(args);
    }
    public void comments(TaskComment... args) {
        this.comments = Arrays.asList(args);
    }

}