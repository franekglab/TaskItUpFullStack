package io.fglab.taskitup.services;

import io.fglab.taskitup.domain.TaskList;
import io.fglab.taskitup.exceptions.ListIdException;
import io.fglab.taskitup.repositories.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;

    public TaskList saveOrUpdateProject(TaskList taskList) {

        try{
            taskList.setListIdentifier(taskList.getListIdentifier().toUpperCase());
            return taskListRepository.save(taskList);
        } catch (Exception e) {
            throw new ListIdException("List ID '"+taskList.getListIdentifier().toUpperCase()+"' already exists");
        }
    }
}
