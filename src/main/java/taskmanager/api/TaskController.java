package taskmanager.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskmanager.mappers.TaskSummaryMapper;
import taskmanager.models.Task;
import taskmanager.models.TaskList;
import taskmanager.repositories.TaskRepository;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@Slf4j
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/")
    public String index() {
        return "Task Service is ready to serve requests";
    }
    @GetMapping("/tasks")
    public List<Task> getTasks() {
        log.info("Requested ");
        Sort sortByCreatedAtDesc= Sort.by("createdAt");
        //sortByCreatedAtDesc =  new Sort(Sort.Direction.DESC, Arrays.asList("createdAt"));
        return taskRepository.findAll(sortByCreatedAtDesc);
    }

    @GetMapping("/taskList")
    public TaskList getTasksList() {
        List<Task> tasks = getTasks();
        return TaskList.builder().count(tasks.size())
                .start(0).end(tasks.size())
                .tasks(tasks).build();
    }

    @PostMapping("/tasks")
    public Task createTodo(@Valid @RequestBody Task task) {
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    @GetMapping(value="/tasks/{id}")
    public ResponseEntity<Task> getTodoById(@PathVariable("id") String id) {
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value="/tasks/")
    public ResponseEntity<Task> getById(@Param("id") String id) {
        log.info("Requesting for tasks {} ", id);
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value="/tasks/title/")
    public ResponseEntity<Task> getByTitle(@Param("title") String title) {
        return taskRepository.findTodoByTitle(title)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping(value="/tasks/{id}")
    public ResponseEntity<Task> updateTodo(@PathVariable("id") String id,
                                           @Valid @RequestBody Task task) {
        return taskRepository.findById(id)
                .map(taskData -> {
                    taskData.setTitle(task.getTitle());
                    taskData.setCompleted(task.getCompleted());
                    Task updatedTask = taskRepository.save(taskData);
                    return ResponseEntity.ok().body(updatedTask);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value="/tasks/{id}")
    public ResponseEntity<?> removeTask(@PathVariable("id") String id) {

        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }


    @GetMapping(value = "/tasks/{id}/summary")
    public ResponseEntity<?> getTaskSummary(@PathVariable String id) {
        Task task = taskRepository.findById(id).get();
        if(task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(TaskSummaryMapper.Mapper.toSummary(task));
    }

    // we should be
}