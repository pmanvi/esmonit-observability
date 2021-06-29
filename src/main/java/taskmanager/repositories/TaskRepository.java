package taskmanager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import taskmanager.models.Task;

import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    Optional<Task> findTodoByTitle(String title);
}