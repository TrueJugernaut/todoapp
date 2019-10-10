package com.todo.todoapp.service;

import com.todo.todoapp.model.TaskModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {

    Optional<TaskModel> findById(Long id);

    List<TaskModel> findAll();

    TaskModel save(TaskModel taskModel);

    void delete(TaskModel taskModel);
}
