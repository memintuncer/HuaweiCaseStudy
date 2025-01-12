package com.huawei.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.dto.ProjectRequest;
import com.huawei.dto.ProjectUpdateRequest;
import com.huawei.log.service.LogService;
import com.huawei.model.entity.Model;
import com.huawei.model.repository.ModelRepository;
import com.huawei.modelPart.service.ModelPartService;
import com.huawei.part.entity.Part;
import com.huawei.percentage.entity.Percentage;
import com.huawei.percentage.service.PercentageService;
import com.huawei.project.entity.PlanType;
import com.huawei.project.entity.Project;
import com.huawei.project.repository.ProjectRepository;
import com.huawei.projectModel.entity.ProjectModel;
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
    
    @Autowired
    private ModelPartService modelPartService;
    
    
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

    @Override
    public Project changeProjectType(Long projectId, ProjectUpdateRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proje bulunamadı. ID: " + projectId));

        PlanType newPlanType = PlanType.valueOf(request.getCurrentPlan());

        List<Percentage> existingPercentages = percentageService.getExistingPercentagesForPlanType(project, newPlanType);

        if (!existingPercentages.isEmpty()) {
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
    
    @Override
    public List<String> getModelsByPercentages(Long projectId) {
        // Proje kontrolü
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proje bulunamadı. ID: " + projectId));

        PlanType planType = project.getCurrentPlan();

        if (planType == PlanType.FIXED) {
            return percentageService.getFixedModels(project);
        } else {
            return percentageService.getGroupedModels(project, planType);
        }
    }

	@Override
	public List<String> calculatePartQuantitiesForProject(Long projectId) {
		Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proje bulunamadı. ID: " + projectId));

        PlanType planType = project.getCurrentPlan();
        if (planType == PlanType.FIXED) {
            return calculateFixedParts(project);
        } else if (planType == PlanType.MONTHLY) {
            return calculateGroupedParts(project, planType, 1);
        } else { 
            return calculateGroupedParts(project, planType, 4);
        }
	}
	
	private List<String> calculateFixedParts(Project project) {
		List<Percentage> percentages = percentageService.getPercentagesByProjectAndPlanType(project, PlanType.FIXED);

	    Map<Part, Integer> partCounts = new HashMap<>();
	    for (Percentage percentage : percentages) {
	        ProjectModel projectModel = projectModelService.findByProjectAndModel(project, percentage.getModel());
	        if (projectModel == null || !projectModel.getModelActive()) {
	            continue; 
	        }

	        Map<Part, Integer> modelParts = modelPartService.calculatePartCounts(project, percentage);
	        modelParts.forEach((part, count) -> partCounts.merge(part, count, Integer::sum));
	    }

	    List<Map.Entry<Part, Integer>> sortedParts = partCounts.entrySet().stream()
	            .sorted(Map.Entry.comparingByValue())
	            .toList();

	    List<String> result = new ArrayList<>();
	    result.add(project.getName() + " projesinde");
	    for (Map.Entry<Part, Integer> entry : sortedParts) {
	        result.add("-" + entry.getKey().getName() + " parçasından " + entry.getValue() + " adet üretilecektir.");
	    }
	    return result;

    }

    private List<String> calculateGroupedParts(Project project, PlanType planType, int groupFactor) {
        List<Percentage> percentages = percentageService.getPercentagesByProjectAndPlanType(project, planType);

        Map<String, Map<Part, Integer>> groupedParts = new HashMap<>();
        for (Percentage percentage : percentages) {
            String dateRange = percentage.getDateRange();
            Map<Part, Integer> partCounts = modelPartService.calculatePartCounts(project, percentage);

            groupedParts.putIfAbsent(dateRange, new HashMap<>());
            partCounts.forEach((part, count) -> groupedParts.get(dateRange).merge(part, count / groupFactor, Integer::sum));
        }

        List<String> result = new ArrayList<>();
        result.add(project.getName() + " projesinde");
        groupedParts.forEach((dateRange, parts) -> {
            result.add(dateRange + " periyodunda");
            parts.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> result.add("-" + entry.getKey().getName() + " parçasından " + entry.getValue() + " adet üretilmelidir."));
        });

        return result;
    }
    
    @Override
    public void softDeleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow();

        project.setIsDeleted(true); 
        project.setUpdatedAt(LocalDateTime.now());
        projectRepository.save(project);
    }
}
