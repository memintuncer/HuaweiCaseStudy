package com.huawei.part.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.huawei.modelPart.entity.ModelPart;

@Entity
@Table(name = "parts")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
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

	public Set<ModelPart> getModelParts() {
		return modelParts;
	}

	public void setModelParts(Set<ModelPart> modelParts) {
		this.modelParts = modelParts;
	}
    
    
}
