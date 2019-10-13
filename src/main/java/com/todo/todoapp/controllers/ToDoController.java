package com.todo.todoapp.controllers;

import com.todo.todoapp.model.TaskModel;
import com.todo.todoapp.service.TaskService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ToDoController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/show_form")
    public String showForm(TaskModel taskModel) {
        return "tasklist";
    }

    @GetMapping("/view/{id}")
    public String showTask(@PathVariable("id") long id, Model model) {
        taskService.findById(Long.valueOf(id)).ifPresent(o -> model.addAttribute("detail", o));
        return "detailview";
    }

    @GetMapping("/board")
    public String showBoard(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "general_list";
    }

    @PostMapping("/createtask")
    public String createTask(@Valid TaskModel taskModel, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "tasklist";
        }
        taskService.save(taskModel);
        model.addAttribute("tasks", taskService.findAll());
        return "redirect:/board";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        TaskModel taskModel = taskService.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));

        model.addAttribute("task", taskModel);
        return "updatetask";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @Valid TaskModel taskModel,
                             BindingResult result) {
        if (result.hasErrors()) {
            taskModel.setId(Long.valueOf(id));
            return "redirect:/board";
        }

        taskService.save(taskModel);
        return "redirect:/board";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        TaskModel taskModel = taskService.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        taskService.delete(taskModel);
        model.addAttribute("tasks", taskService.findAll());
        return "redirect:/board";
    }
}
