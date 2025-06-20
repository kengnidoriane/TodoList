package com.todolist.toDoList.controller;



import com.todolist.toDoList.model.Task;
import com.todolist.toDoList.model.TaskStatus;
import com.todolist.toDoList.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task API", description = "Tasks management system")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create a task")
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping
    @Operation(summary = "List all tasks")
    public List<Task> getAllTasks(@RequestParam(required = false) TaskStatus status) {
        if (status != null) return taskService.getTasksByStatus(status);
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
