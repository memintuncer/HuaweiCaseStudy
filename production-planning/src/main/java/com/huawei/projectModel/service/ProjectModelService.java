package com.huawei.projectModel.service;

import java.util.List;

import com.huawei.dto.ProjectUpdateRequest;
import com.huawei.model.entity.Model;
import com.huawei.project.entity.PlanType;
import com.huawei.project.entity.Project;

public interface ProjectModelService {
	void toggleModelActivity(Long projectId, Long modelId);
	void addModelToProject(Project project, Model model, Boolean isActive);
	void updateModelPercentages(Project project, Long modelId, List<ProjectUpdateRequest.PercentageUpdateRequest> percentages, PlanType planType);
}
