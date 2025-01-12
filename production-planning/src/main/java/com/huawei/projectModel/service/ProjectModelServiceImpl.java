package com.huawei.projectModel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.dto.ProjectUpdateRequest;
import com.huawei.log.service.LogService;
import com.huawei.model.entity.Model;
import com.huawei.model.repository.ModelRepository;
import com.huawei.percentage.entity.Percentage;
import com.huawei.percentage.service.PercentageService;
import com.huawei.project.entity.PlanType;
import com.huawei.project.entity.Project;
import com.huawei.projectModel.entity.ProjectModel;
import com.huawei.projectModel.repository.ProjectModelRepository;

@Service
public class ProjectModelServiceImpl implements ProjectModelService{

	@Autowired
    private ProjectModelRepository projectModelRepository;

    @Autowired
    private LogService logService;
    
    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private PercentageService percentageService;

    @Override
    public void toggleModelActivity(Long projectId, Long modelId) {
        ProjectModel projectModel = projectModelRepository.findByProjectIdAndModelId(projectId, modelId)
                .orElseThrow(() -> new IllegalArgumentException("ProjectModel kaydı bulunamadı."));

        boolean currentActiveStatus = projectModel.getModelActive();
        projectModel.setModelActive(!currentActiveStatus);
        projectModelRepository.save(projectModel);
        logService.logModelActivityChange(projectModel.getProject(), projectModel.getModel(), !currentActiveStatus);
    }
    
    @Override
    public void addModelToProject(Project project, Model model, Boolean isActive) {
        ProjectModel projectModel = new ProjectModel();
        projectModel.setProject(project);
        projectModel.setModel(model);
        projectModel.setModelActive(isActive);
        projectModelRepository.save(projectModel);
    }
    
    @Override
    public void updateModelPercentages(Project project, Long modelId, List<ProjectUpdateRequest.PercentageUpdateRequest> percentages, PlanType planType) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new IllegalArgumentException("Model bulunamadı. ID: " + modelId));

        List<Percentage> existingPercentages = percentageService.getExistingPercentages(project, model, planType);

        if (existingPercentages.isEmpty()) {
            percentageService.savePercentages(project, model, percentages, planType);
        }
    }

	@Override
	public ProjectModel findByProjectAndModel(Project project, Model model) {
		return projectModelRepository.findByProjectAndModel(project, model);
	}
}
