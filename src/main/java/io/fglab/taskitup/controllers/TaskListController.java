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

import java.security.Principal;
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
    public ResponseEntity<?> createNewList(@Valid @RequestBody TaskList taskList, BindingResult result, Principal principal) {

        ResponseEntity<?> errorMap = mapValidationError.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        TaskList newTaskList = taskListService.saveOrUpdateList(taskList, principal.getName());
        return new ResponseEntity<TaskList>(newTaskList, HttpStatus.CREATED);
    }

    @GetMapping("/{listId}")
    public ResponseEntity<?> getListByIdentifier(@PathVariable String listId, Principal principal) {

        TaskList taskList = taskListService.findListByIdentifier(listId, principal.getName());
        return new ResponseEntity<TaskList>(taskList, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<TaskList> getAllLists(Principal principal) {
        return taskListService.findAllLists(principal.getName());
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<?> deleteList(@PathVariable String listId, Principal principal) {
        taskListService.deleteListByIdentifier(listId, principal.getName());

        return new ResponseEntity<String>("List with ID: "+listId+"' was deleted", HttpStatus.OK);
    }
}
