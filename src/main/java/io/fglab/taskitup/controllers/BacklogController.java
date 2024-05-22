package io.fglab.taskitup.controllers;

import io.fglab.taskitup.domain.Task;
import io.fglab.taskitup.services.MapValidationError;
import io.fglab.taskitup.services.TaskService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private MapValidationError mapValidationError;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addTaskToList(@Valid @RequestBody Task task, BindingResult result, @PathVariable String backlog_id) {

        ResponseEntity<?> errorMap = mapValidationError.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Task newTask = taskService.addTask(backlog_id, task);

        return new ResponseEntity<Task>(newTask, HttpStatus.CREATED);

    }

}
