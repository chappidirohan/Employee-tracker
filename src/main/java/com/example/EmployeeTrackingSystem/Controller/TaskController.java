package com.example.EmployeeTrackingSystem.Controller;

import com.example.EmployeeTrackingSystem.model.Tasks;
import com.example.EmployeeTrackingSystem.Repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5174") // frontend URL
public class TaskController {

    private final TaskRepository tasksRepo;



    public TaskController(TaskRepository tasksRepo) {
        this.tasksRepo = tasksRepo;
    }

    // Get all tasks
    @GetMapping
    public List<Tasks> getAllTasks() {
        return tasksRepo.findAll();
    }

    // Get tasks for a specific employee
    @GetMapping("/employee/{empId}")
    public List<Tasks> getTasksByEmployee(@PathVariable Long empId) {
        return tasksRepo.findByEmployeeId(empId);
    }
}
