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

import java.util.List;

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
        if (errorMap != null) return errorMap;

        Task newTask = taskService.addTask(backlog_id, task);

        return new ResponseEntity<Task>(newTask, HttpStatus.CREATED);

    }

    @GetMapping("/{backlog_id}")
    public Iterable<Task> getTaskBacklog(@PathVariable String backlog_id) {

        return taskService.findBacklogById(backlog_id);
    }

    @GetMapping("/{backlog_id}/{tl_id}")
    public ResponseEntity<?> getTask(@PathVariable String backlog_id, @PathVariable String tl_id) {
        Task task = taskService.findTLByListSequence(backlog_id, tl_id);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{tl_id}")
    public ResponseEntity<?> updateTask(@Valid @RequestBody Task task, BindingResult result, @PathVariable String backlog_id, @PathVariable String tl_id) {

        ResponseEntity<?> errorMap = mapValidationError.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Task updatedTask = taskService.updateByListSequence(task, backlog_id, tl_id);

        return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{tl_id}")
    public ResponseEntity<?> deletedeTask(@PathVariable String backlog_id, @PathVariable String tl_id) {
        taskService.deleteTLByListSequence(backlog_id, tl_id);

        return new ResponseEntity<String>("Task " + tl_id + " was deleted sucessfully", HttpStatus.OK);
    }

}
