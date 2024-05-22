package io.fglab.taskitup.services;

import io.fglab.taskitup.domain.Backlog;
import io.fglab.taskitup.domain.Task;
import io.fglab.taskitup.repositories.BacklogRepository;
import io.fglab.taskitup.repositories.TaskListRepository;
import io.fglab.taskitup.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Task addTask(String listIdentifier, Task task) {

        Backlog backlog = backlogRepository.findByListIdentifier(listIdentifier);

        task.setBacklog(backlog);

        Integer BacklogSequence = backlog.getTlSequence();

        BacklogSequence++;

        backlog.setTlSequence(BacklogSequence);

        task.setTaskSequence(backlog.getListIdentifier() + "-" + BacklogSequence);
        task.setListIdentifier(listIdentifier);

        if (task.getPriority() == null) {
            task.setPriority(3);
        }

        if (task.getStatus() == "" || task.getStatus() == null) {
            task.setStatus("TO_DO");
        }

        return taskRepository.save(task);
    }

}
