package com.example.EmployeeTrackingSystem.Service;

import com.example.EmployeeTrackingSystem.Repository.ProjectRepository;
import com.example.EmployeeTrackingSystem.model.projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<projects> getProjectsByMemberName(String username) {
        return projectRepository.findByMembers_Name(username);
    }

    public List<projects> getAllProjects() {
        return projectRepository.findAll();
    }

    public projects saveProject(projects project) {
        return projectRepository.save(project);
    }

    public projects getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }
}
