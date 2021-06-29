package taskmanager.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskSummary {
    private String taskDescription;
    private String createDate;
    private String productOwner;
    private int    estimationInHours;


}
