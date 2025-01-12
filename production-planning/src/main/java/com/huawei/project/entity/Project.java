package com.huawei.project.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.huawei.projectModel.entity.ProjectModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer totalProductionGoal;

    @Enumerated(EnumType.STRING)
    private PlanType currentPlan;

    private Boolean isDeleted;
    private String projectRange;
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ProjectModel> projectModels = new HashSet<>();

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

	public Integer getTotalProductionGoal() {
		return totalProductionGoal;
	}

	public void setTotalProductionGoal(Integer totalProductionGoal) {
		this.totalProductionGoal = totalProductionGoal;
	}

	public PlanType getCurrentPlan() {
		return currentPlan;
	}

	public void setCurrentPlan(PlanType currentPlan) {
		this.currentPlan = currentPlan;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public String getProjectRange() {
		return projectRange;
	}

	public void setProjectRange(String projectRange) {
		this.projectRange = projectRange;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set<ProjectModel> getProjectModels() {
		return projectModels;
	}

	public void setProjectModels(Set<ProjectModel> projectModels) {
		this.projectModels = projectModels;
	}
    
}
