package com.example.EmployeeTrackingSystem.Controller;

import com.example.EmployeeTrackingSystem.model.projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.example.EmployeeTrackingSystem.model.projectMembers;
import com.example.EmployeeTrackingSystem.Repository.ProjectRepository;
import com.example.EmployeeTrackingSystem.Repository.ProjectMembersRepository;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
// java
@CrossOrigin(
        origins ="http://localhost:5000",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)


public class ProjectsController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMembersRepository membersRepository;

    @GetMapping("/allProjects")
    public List<projects> getAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    @PostMapping("/add")
    public projects addProject(@RequestBody projects project) {
        project.setStatus("Active");
        project.setProgress(0);

        List<projectMembers> members = project.getMembers();
         if (members != null) {
            for (projectMembers member : project.getMembers()) {
                member.setProject(project);
            }
        }
        return projectRepository.save(project);
    }
}
