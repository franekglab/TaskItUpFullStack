package io.fglab.taskitup.services;

import io.fglab.taskitup.domain.Backlog;
import io.fglab.taskitup.domain.Task;
import io.fglab.taskitup.domain.TaskList;
import io.fglab.taskitup.exceptions.ListNotFoundException;
import io.fglab.taskitup.repositories.BacklogRepository;
import io.fglab.taskitup.repositories.TaskListRepository;
import io.fglab.taskitup.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.ListenerNotFoundException;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskListService taskListService;

    public Task addTask(String listIdentifier, Task task, String username) {

        Backlog backlog = taskListService.findListByIdentifier(listIdentifier, username).getBacklog();

        task.setBacklog(backlog);

        Integer BacklogSequence = backlog.getTlSequence();

        BacklogSequence++;

        backlog.setTlSequence(BacklogSequence);

        task.setListSequence(backlog.getListIdentifier() + "-" + BacklogSequence);
        task.setListIdentifier(listIdentifier);

        if (task.getPriority() == null || task.getPriority() == 0) {
            task.setPriority(3);
        }

        if (task.getStatus() == "" || task.getStatus() == null) {
            task.setStatus("TO_DO");
        }

        return taskRepository.save(task);

    }

    public Iterable<Task> findBacklogById(String id, String username) {

        taskListService.findListByIdentifier(id, username);

        return taskRepository.findByListIdentifierOrderByPriority(id);
    }

    public Task findTLByListSequence(String backlog_id, String tl_id, String username) {

        //sprawdza czy lista istnieje
        taskListService.findListByIdentifier(backlog_id, username);



        //sprawdza czy task istnieje
        Task task = taskRepository.findByListSequence(tl_id);

        if (task == null) {
            throw new ListNotFoundException("Task '" + tl_id + "' not found");
        }

        //sprawdza czy parametr listy podany w pathie jest zgodny z tym w jakiej liscie utworzylismy zadanie
        if (!task.getListIdentifier().equals(backlog_id)) {
            throw new ListNotFoundException("Task '" + tl_id + "' does not exist in List" + backlog_id);
        }


        return task;
    }

    public Task updateByListSequence(Task updatedTask, String backlog_id, String tl_id, String username) {
        Task task = findTLByListSequence(backlog_id, tl_id, username);

        task = updatedTask;

        return taskRepository.save(task);
    }

    public void deleteTLByListSequence(String backlog_id, String tl_id, String username) {
        Task task = findTLByListSequence(backlog_id, tl_id, username);

        taskRepository.delete(task);
    }
}
