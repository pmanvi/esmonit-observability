package taskmanager.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Period;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskList {
    private int         count;
    private List<Task> tasks;
    private int         start;
    private int         end;

    // Facets
    private List<String> allUsers;
    private Period       range;
}
