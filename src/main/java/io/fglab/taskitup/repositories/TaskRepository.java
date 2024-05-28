package io.fglab.taskitup.repositories;

import io.fglab.taskitup.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findByListIdentifierOrderByPriority(String id);

    Task findByListSequence(String sequence);
}
