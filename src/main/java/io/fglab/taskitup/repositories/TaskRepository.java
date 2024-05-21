package io.fglab.taskitup.repositories;

import io.fglab.taskitup.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
