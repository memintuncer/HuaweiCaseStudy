package com.huawei.percentage.service;

import java.util.List;

import com.huawei.dto.ProjectRequest;
import com.huawei.dto.ProjectUpdateRequest;
import com.huawei.model.entity.Model;
import com.huawei.percentage.entity.Percentage;
import com.huawei.project.entity.PlanType;
import com.huawei.project.entity.Project;

public interface PercentageService {
    Percentage createPercentage(Percentage percentage);
    void addPercentagesToProject(Project project, Model model, List<ProjectRequest.PercentageRequest> percentages);
    List<Percentage> getExistingPercentages(Project project, Model model, PlanType planType);
    void savePercentages(Project project, Model model, List<ProjectUpdateRequest.PercentageUpdateRequest> percentages, PlanType planType);
    List<Percentage> getExistingPercentagesForPlanType(Project project, PlanType planType);
    List<String> getFixedModels(Project project);
    List<String> getGroupedModels(Project project, PlanType planType);
	List<Percentage> getPercentagesByProjectAndPlanType(Project project, PlanType planType);
}
