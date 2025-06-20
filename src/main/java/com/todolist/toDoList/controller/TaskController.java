package com.todolist.toDoList.controller;



import com.todolist.toDoList.model.Task;
import com.todolist.toDoList.model.TaskStatus;
import com.todolist.toDoList.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    @Operation(summary = "Create a task", description = "Creates a new task with a title, description, and status.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task created successfully", content = @Content(schema = @Schema(implementation = Task.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Task object to be created",
        required = true,
        content = @Content(schema = @Schema(implementation = Task.class))
    )
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping
    @Operation(summary = "List all tasks", description = "Retrieves all tasks, optionally filtered by status.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of tasks", content = @Content(schema = @Schema(implementation = Task.class)))
    })
    public List<Task> getAllTasks(
            @Parameter(description = "Filter tasks by status. Possible values: TODO, IN_PROGRESS, DONE", required = false)
            @RequestParam(required = false) TaskStatus status) {
        if (status != null) return taskService.getTasksByStatus(status);
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task", description = "Updates an existing task by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task updated successfully", content = @Content(schema = @Schema(implementation = Task.class))),
        @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Updated task object",
        required = true,
        content = @Content(schema = @Schema(implementation = Task.class))
    )
    public Task updateTask(
            @Parameter(description = "ID of the task to update", required = true)
            @PathVariable Long id,
            @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task", description = "Deletes a task by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public void deleteTask(
            @Parameter(description = "ID of the task to delete", required = true)
            @PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
