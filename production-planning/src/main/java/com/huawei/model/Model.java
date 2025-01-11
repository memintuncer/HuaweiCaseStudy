package com.huawei.model;

import java.util.List;

import com.huawei.part.Part;
import com.huawei.project.Project;

import jakarta.persistence.*;

@Entity
public class Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private double percentage;
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name ="project_id")
	private Project project;
	
	@OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
	private List<Part> parts;

	public Model(String name, double percentage, boolean active, Project project) {
		super();
		this.name = name;
		this.percentage = percentage;
		this.active = active;
		this.project = project;
	}
	
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

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}
	
}
