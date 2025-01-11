package com.huawei.project;

import java.util.List;

import com.huawei.model.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	 @Enumerated(EnumType.STRING)
	 private ConfigurationType configurationType;
	 
	 private boolean active;
	 
	 @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	 private List<Model> models;
	 
	 public Project () {};
	 
	 public Project (String name, ConfigurationType configurationType, boolean active) {
		 this.name = name;
		 this.configurationType = configurationType;
		 this.active = active;
	 };
	 
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
	 public ConfigurationType getConfigurationType() {
         return configurationType;
     }

     public void setConfigurationType(ConfigurationType configurationType) {
         this.configurationType = configurationType;
     }

     public boolean isActive() {
         return active;
     }

     public void setActive(boolean active) {
         this.active = active;
     }

     public List<Model> getModels() {
         return models;
     }

     public void setModels(List<Model> models) {
         this.models = models;
     }
}
