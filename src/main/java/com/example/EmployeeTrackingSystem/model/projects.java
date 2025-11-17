package com.example.EmployeeTrackingSystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String status;
    private int progress;
    private String startDate;
    private String endDate;
    private String projectLead;
    private int totalMembers;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<projectMembers> members = new ArrayList<>();

    // ------------------ Getters & Setters ------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProjectLead() {
        return projectLead;
    }

    public void setProjectLead(String projectLead) {
        this.projectLead = projectLead;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }

    public List<projectMembers> getMembers() {
        return members;
    }

    public void setMembers(List<projectMembers> members) {
        if (this.members == null) {
            this.members = new ArrayList<>();
        } else {
            this.members.clear();
        }

        if (members != null) {
            for (projectMembers member : members) {
                addMember(member);
            }
        }
    }

    public void addMember(projectMembers member) {
        member.setProject(this);
        this.members.add(member);
    }

    public void removeMember(projectMembers member) {
        member.setProject(null);
        this.members.remove(member);
    }
}
