package com.huawei.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.dto.ProjectRequest;
import com.huawei.dto.ProjectUpdateRequest;
import com.huawei.project.entity.Project;
import com.huawei.project.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projects", description = "Projelerle ilgili işlemleri yöneten API end-pointleri")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    @Operation(
    		summary = "Create a new project", 
    		description = "Yeni bir proje oluşturur")
    public ResponseEntity<Project> createProject(@RequestBody ProjectRequest request) {
        
    	Project createdProject = projectService.createProject(request);
        return ResponseEntity.ok(createdProject);
       
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Get a specific project by ID",
        description = "Belirtilen ID'ye sahip projeyi getirir."
    )
    public ResponseEntity<Project> getProject(
            @PathVariable("id") @Parameter(description = "Getirilecek projenin ID değeri") Long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }
    
    @GetMapping("/all")
    @Operation(
        summary = "Get all projects",
        description = "Sistemdeki tüm projeleri getirir."
    )
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
    
    @PutMapping("/change-type/{id}")
    @Operation(
        summary = "Change a project type with models and percentages",
        description = "Mevcut bir projenin plan türünü günceller. Yeni bir type girildiğinde, eski değerler mevcutsa kullanılır."
        		+ "Eğer eski değerler yoksa yeni değerler kullanılır"
    )
    public ResponseEntity<Project> changeProjectType(
            @PathVariable("id") @Parameter(description = "Güncellenecek projenin ID değeri") Long id,
            @RequestBody ProjectUpdateRequest request) {
        Project updatedProject = projectService.changeProjectType(id, request);
        return ResponseEntity.ok(updatedProject);
    }
    
    @PutMapping("/{id}/toggle-models")
    @Operation(
        summary = "Change activity for models in project",
        description = "FIXED projeler için modellerin aktiflik durumunu değiştirir"
    )
    public ResponseEntity<Void> toggleModelActivity(
    		@PathVariable("id") @Parameter(description = "Güncellenecek projenin ID değeri") Long id,
            @RequestBody List<Long> modelIds) {
        projectService.changeModelActivity(id, modelIds);
        return ResponseEntity.ok().build();
    }
}
