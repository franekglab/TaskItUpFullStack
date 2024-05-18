package io.fglab.taskitup.controllers;


import io.fglab.taskitup.domain.TaskList;
import io.fglab.taskitup.services.MapValidationError;
import io.fglab.taskitup.services.TaskListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/tasklist")
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @Autowired
    private MapValidationError mapValidationError;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody TaskList taskList, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationError.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        TaskList newTaskList = taskListService.saveOrUpdateProject(taskList);
        return new ResponseEntity<TaskList>(newTaskList, HttpStatus.CREATED);
    }
}
