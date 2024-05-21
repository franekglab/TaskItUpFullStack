package io.fglab.taskitup.services;

import io.fglab.taskitup.domain.Backlog;
import io.fglab.taskitup.domain.TaskList;
import io.fglab.taskitup.exceptions.ListIdException;
import io.fglab.taskitup.repositories.BacklogRepository;
import io.fglab.taskitup.repositories.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public TaskList saveOrUpdateList(TaskList taskList) {
        TaskList existingTaskList = taskListRepository.findByListIdentifier(taskList.getListIdentifier().toUpperCase());

        if (existingTaskList != null && !existingTaskList.getId().equals(taskList.getId())) {
            throw new ListIdException("List ID '" + taskList.getListIdentifier().toUpperCase() + "' already exists");
        }

        if (taskList.getId() == null) {
            Backlog backlog = new Backlog();
            taskList.setBacklog(backlog);
            backlog.setTaskList(taskList);
            backlog.setListIdentifier(taskList.getListIdentifier().toUpperCase());
        }

        if (taskList.getId() != null) {
            taskList.setBacklog(backlogRepository.findByListIdentifier(taskList.getListIdentifier().toUpperCase()));
        }


        taskList.setListIdentifier(taskList.getListIdentifier().toUpperCase());
        return taskListRepository.save(taskList);
    }

    public TaskList findListByIdentifier(String listId) {

        TaskList taskList = taskListRepository.findByListIdentifier(listId.toUpperCase());

        if (taskList == null) {
            throw new ListIdException("List ID '" + listId + "' doesn't exist");

        }

        return taskList;
    }

    public Iterable<TaskList> findAllLists() {
        return taskListRepository.findAll();
    }

    public void deleteListByIdentifier(String listId) {

        TaskList taskList = findListByIdentifier(listId.toUpperCase());

        if (taskList == null) {
            throw new ListIdException("Can't delete List with ID '" + listId + "' This List doesn't exist");
        }

        taskListRepository.delete(taskList);
    }
}
