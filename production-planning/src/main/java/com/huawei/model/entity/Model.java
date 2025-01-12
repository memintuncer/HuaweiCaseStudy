package com.huawei.model.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.huawei.modelPart.entity.ModelPart;
import com.huawei.projectModel.entity.ProjectModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "models")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean isActive = true;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectModel> projectModels = new HashSet<>();
    
    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ModelPart> modelParts = new HashSet<>();

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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<ProjectModel> getProjectModels() {
		return projectModels;
	}

	public void setProjectModels(Set<ProjectModel> projectModels) {
		this.projectModels = projectModels;
	}

	public Set<ModelPart> getModelParts() {
		return modelParts;
	}

	public void setModelParts(Set<ModelPart> modelParts) {
		this.modelParts = modelParts;
	}
    
	
   
}
