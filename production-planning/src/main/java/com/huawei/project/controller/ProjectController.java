package com.huawei.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	    description = "Yeni bir proje oluşturur. Proje bilgileri, modeller ve plan türüne göre kaydedilir."
    )
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
        		+ "Eğer eski değerler yoksa yeni değerler kullanılır. Eğer önceki bir plantype'a dönülmek isteniyorsa"
        		+ "sadece currentPlan bilgisi input olarak girilebilir. Yeni bir type değeri kullanılacaksa modellerin yüzdelik değerleri, "
        		+ "priyodlar input olarak girilmelidir"
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
        description = "FIXED projeler için modellerin aktiflik durumunu değiştirir. Modellerin id değerlerini input olarak alır"
    )
    public ResponseEntity<Void> toggleModelActivity(
    		@PathVariable("id") @Parameter(description = "Güncellenecek projenin ID değeri") Long id,
            @RequestBody List<Long> modelIds) {
        projectService.changeModelActivity(id, modelIds);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{projectId}/models-by-percentages")
    @Operation(
        summary = "List models by percentages",
        description = "Projede bulunan modelleri yüzdelik değerlerine göre listeler. PlanType'a bağlı olarak sıralama ve gruplama yapılır."
    )
    public ResponseEntity<List<String>> getModelsByPercentages(@PathVariable("projectId") @Parameter(description = "Projenin ID değeri") Long projectId) {
        List<String> result = projectService.getModelsByPercentages(projectId);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{projectId}/calculate-parts")
    @Operation(
	    summary = "Calculate total parts per project",
	    description = "Bir proje için toplam parça adetini hesaplar. Plan türüne (FIXED, MONTHLY, WEEKLY) göre hesaplama yapılır."
	)
    public ResponseEntity<List<String>> calculatePartQuantitiesForProject(@PathVariable("projectId") @Parameter(description = "Projenin ID değeri") Long projectId) {
        List<String> result = projectService.calculatePartQuantitiesForProject(projectId);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/soft-delete/{id}")
    @Operation(
	    summary = "Soft delete a project",
	    description = "Projeyi soft delete yapar (isDeleted = true). Proje fiziksel olarak silinmez."
    )
    public ResponseEntity<String> softDeleteProject(@PathVariable("projectId") @Parameter(description = "Silinmesi veya iptal edilmesi istenen proje id'si") Long id) {
        projectService.softDeleteProject(id);
        return ResponseEntity.ok("Project with ID " + id + " has been soft deleted.");
    }
}
