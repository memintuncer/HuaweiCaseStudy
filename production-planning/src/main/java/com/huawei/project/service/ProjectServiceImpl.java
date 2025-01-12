package com.huawei.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.dto.ProjectRequest;
import com.huawei.dto.ProjectUpdateRequest;
import com.huawei.log.service.LogService;
import com.huawei.model.entity.Model;
import com.huawei.model.repository.ModelRepository;
import com.huawei.percentage.entity.Percentage;
import com.huawei.percentage.repository.PercentageRepository;
import com.huawei.percentage.service.PercentageService;
import com.huawei.project.entity.PlanType;
import com.huawei.project.entity.Project;
import com.huawei.project.repository.ProjectRepository;
import com.huawei.projectModel.service.ProjectModelService;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private LogService logService;
    @Autowired
    private ProjectModelService projectModelService;
    
    @Autowired
    private PercentageService percentageService;
    
    
    @Override
    public Project createProject(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setTotalProductionGoal(request.getTotalProductionGoal());
        project.setCurrentPlan(PlanType.valueOf(request.getCurrentPlan()));
        project.setIsDeleted(request.getIsDeleted());
        project.setProjectRange(request.getProjectRange());
        project = projectRepository.save(project);

        for (ProjectRequest.ModelRequest modelRequest : request.getModels()) {
            Optional<Model> modelOptional = modelRepository.findById(modelRequest.getId());
            if (modelOptional.isPresent()) {
                Model model = modelOptional.get();
                projectModelService.addModelToProject(project, model, modelRequest.getIsActive());
                percentageService.addPercentagesToProject(project, model, modelRequest.getPercentages());
                logService.logModelAddition(project, model);
            }
        }

        return project;
    }
    
//    @Override
//    public Project changeProjectType(Long projectId, ProjectUpdateRequest request) {
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new IllegalArgumentException("Proje bulunamadı. ID: " + projectId));
//
//        PlanType newPlanType = PlanType.valueOf(request.getCurrentPlan());
//
//        for (ProjectUpdateRequest.ModelUpdateRequest modelUpdateRequest : request.getModels()) {
//            projectModelService.updateModelPercentages(
//                project, 
//                modelUpdateRequest.getId(), 
//                modelUpdateRequest.getPercentages(), 
//                newPlanType
//            );
//        }
//
//        project.setCurrentPlan(newPlanType);
//        projectRepository.save(project);
//
//        return project;
//    }
    @Override
    public Project changeProjectType(Long projectId, ProjectUpdateRequest request) {
        // Proje kontrolü
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proje bulunamadı. ID: " + projectId));

        PlanType newPlanType = PlanType.valueOf(request.getCurrentPlan());

        List<Percentage> existingPercentages = percentageService.getExistingPercentagesForPlanType(project, newPlanType);

        if (!existingPercentages.isEmpty()) {
            // PlanType zaten mevcutsa, sadece güncellenir
            project.setCurrentPlan(newPlanType);
            projectRepository.save(project);
            return project;
        }

        if (request.getModels() == null || request.getModels().isEmpty()) {
            throw new IllegalArgumentException("Lütfen güncel yüzdelik değer bilgilerini giriniz.");
        }

        for (ProjectUpdateRequest.ModelUpdateRequest modelRequest : request.getModels()) {
            projectModelService.updateModelPercentages(project, modelRequest.getId(), modelRequest.getPercentages(), newPlanType);
        }

        project.setCurrentPlan(newPlanType);
        projectRepository.save(project);

        return project;
    }
    
    
    @Override
    public void changeModelActivity(Long projectId, List<Long> modelIds) {
        
    	Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proje bulunamadı."));

        if (!project.getCurrentPlan().equals(PlanType.FIXED)) {
        	throw new UnsupportedOperationException("Projede bulunan modellerin aktifliği sadece sabit projelerde değiştirilebilir.");
        }

        for (Long modelId : modelIds) {
            projectModelService.toggleModelActivity(projectId, modelId);
        }
    }
    
    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proje bulunamadı."));
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
