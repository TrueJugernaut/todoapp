package com.todo.todoapp.serviceImpl;

import com.todo.todoapp.dao.TaskRepository;
import com.todo.todoapp.model.TaskModel;
import com.todo.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Optional<TaskModel> findById(Long taskId) {
        return taskRepository.findById(taskId);
    }
    @Override public List<TaskModel> findAll() {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    @Override public TaskModel save(TaskModel taskModel) {
        taskModel.setDate(LocalDate.now());
        taskModel.setStatus(Integer.valueOf(1));
        return taskRepository.save(taskModel);
    }
    @Override public void delete(TaskModel taskModel) {
        taskRepository.delete(taskModel);
    }
}
