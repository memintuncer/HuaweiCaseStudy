package com.huawei.percentage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.dto.ProjectRequest;
import com.huawei.dto.ProjectUpdateRequest;
import com.huawei.model.entity.Model;
import com.huawei.percentage.entity.Percentage;
import com.huawei.percentage.repository.PercentageRepository;
import com.huawei.project.entity.PlanType;
import com.huawei.project.entity.Project;

@Service
public class PercentageServiceImpl implements PercentageService {

    @Autowired
    private PercentageRepository percentageRepository;
    

    @Override
    public Percentage createPercentage(Percentage percentage) {
        return percentageRepository.save(percentage);
    }
    
    @Override
    public void addPercentagesToProject(Project project, Model model, List<ProjectRequest.PercentageRequest> percentages) {
        for (ProjectRequest.PercentageRequest percentageRequest : percentages) {
            Percentage percentage = new Percentage();
            percentage.setProject(project);
            percentage.setModel(model);
            percentage.setPercentage(percentageRequest.getPercentage());
            percentage.setDateRange(percentageRequest.getDateRange());
            percentage.setPlanType(project.getCurrentPlan());
            percentageRepository.save(percentage);
        }
    }
    
    @Override
    public List<Percentage> getExistingPercentages(Project project, Model model, PlanType planType) {
        return percentageRepository.findByProjectAndModelAndPlanType(project, model, planType);
    }

    @Override
    public void savePercentages(Project project, Model model, List<ProjectUpdateRequest.PercentageUpdateRequest> percentages, PlanType planType) {
        for (ProjectUpdateRequest.PercentageUpdateRequest percentageRequest : percentages) {
            Percentage percentage = new Percentage();
            percentage.setProject(project);
            percentage.setModel(model);
            percentage.setPercentage(percentageRequest.getPercentage());
            percentage.setDateRange(percentageRequest.getDateRange());
            percentage.setPlanType(planType);
            percentageRepository.save(percentage);
        }
    }
    
    @Override
    public List<Percentage> getExistingPercentagesForPlanType(Project project, PlanType planType) {
        return percentageRepository.findByProjectAndPlanType(project, planType);
    }
}
