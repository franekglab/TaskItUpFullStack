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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createNewList(@Valid @RequestBody TaskList taskList, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationError.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        TaskList newTaskList = taskListService.saveOrUpdateList(taskList);
        return new ResponseEntity<TaskList>(newTaskList, HttpStatus.CREATED);
    }

    @GetMapping("/{listId}")
    public ResponseEntity<?> getListByIdentifier(@PathVariable String listId) {

        TaskList taskList = taskListService.findListByIdentifier(listId);
        return new ResponseEntity<TaskList>(taskList, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<TaskList> getAllLists() {
        return taskListService.findAllLists();
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<?> deleteList(@PathVariable String listId) {
        taskListService.deleteListByIdentifier(listId);

        return new ResponseEntity<String>("List with ID: "+listId+"' was deleted", HttpStatus.OK);
    }
}
