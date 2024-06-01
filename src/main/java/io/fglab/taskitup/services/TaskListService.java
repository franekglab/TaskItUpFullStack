package io.fglab.taskitup.services;

import io.fglab.taskitup.domain.Backlog;
import io.fglab.taskitup.domain.TaskList;
import io.fglab.taskitup.domain.User;
import io.fglab.taskitup.exceptions.ListIdException;
import io.fglab.taskitup.exceptions.ListNotFoundException;
import io.fglab.taskitup.repositories.BacklogRepository;
import io.fglab.taskitup.repositories.TaskListRepository;
import io.fglab.taskitup.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskList saveOrUpdateList(TaskList taskList, String username) {


        if (taskList.getId() != null) {
            TaskList existingTaskList = taskListRepository.findByListIdentifier(taskList.getListIdentifier());
            if (existingTaskList != null && (!existingTaskList.getListOwner().equals(username))) {
                throw new ListNotFoundException("List not found for this user");
            } else if (existingTaskList == null) {
                throw new ListNotFoundException("List with ID '" + taskList.getListIdentifier() + "' can't be updated becasue it doesn't exist");
            }
        }

//        if (existingTaskList != null && !existingTaskList.getId().equals(taskList.getId())) {
//            throw new ListIdException("List ID '" + taskList.getListIdentifier().toUpperCase() + "' already exists");
//        }
        try {

            User user = userRepository.findByUsername(username);
            taskList.setUser(user);
            taskList.setListOwner(user.getUsername());
            taskList.setListIdentifier(taskList.getListIdentifier().toUpperCase());

            if (taskList.getId() == null) {
                Backlog backlog = new Backlog();
                taskList.setBacklog(backlog);
                backlog.setTaskList(taskList);
                backlog.setListIdentifier(taskList.getListIdentifier().toUpperCase());
            }

            if (taskList.getId() != null) {
                taskList.setBacklog(backlogRepository.findByListIdentifier(taskList.getListIdentifier().toUpperCase()));
            }

            return taskListRepository.save(taskList);

        } catch (Exception e) {
            throw new ListIdException("List ID '" + taskList.getListIdentifier().toUpperCase() + "' already exists");
        }
    }

    public TaskList findListByIdentifier(String listId, String username) {

        TaskList taskList = taskListRepository.findByListIdentifier(listId.toUpperCase());

        if (taskList == null) {
            throw new ListIdException("List ID '" + listId + "' doesn't exist");
        }

        if (!taskList.getListOwner().equals(username)) {
            throw new ListNotFoundException("List not found for this user");
        }


        return taskList;
    }

    public Iterable<TaskList> findAllLists(String username) {
        return taskListRepository.findAllByListOwner(username);
    }

    public void deleteListByIdentifier(String listId, String username) {
        taskListRepository.delete(findListByIdentifier(listId, username));
    }
}
