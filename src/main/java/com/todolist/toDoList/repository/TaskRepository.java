package com.todolist.toDoList.repository;

import com.todolist.toDoList.model.Task;
import com.todolist.toDoList.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
   List<Task> findByStatus(TaskStatus status);
}
