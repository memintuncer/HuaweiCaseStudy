package com.huawei.percentage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    
    @Override
    public List<String> getFixedModels(Project project) {
        // FIXED plan için yüzdeleri al ve sırala
        List<Percentage> percentages = percentageRepository.findByProjectAndPlanType(project, PlanType.FIXED);
        percentages.sort((p1, p2) -> Float.compare(p2.getPercentage(), p1.getPercentage()));

        List<String> result = new ArrayList<>();
        result.add(project.getName() + " projesinde");
        for (Percentage percentage : percentages) {
            result.add("-" + percentage.getModel().getName() + " modeli %" + percentage.getPercentage() + " oranında üretilecektir.");
        }
        return result;
    }

    @Override
    public List<String> getGroupedModels(Project project, PlanType planType) {
        // PlanType için yüzdeleri al ve grupla
        List<Percentage> percentages = percentageRepository.findByProjectAndPlanType(project, planType);
        Map<String, List<Percentage>> grouped = percentages.stream()
                .collect(Collectors.groupingBy(Percentage::getDateRange));

        List<String> result = new ArrayList<>();
        result.add(project.getName() + " projesinde");
        grouped.forEach((dateRange, group) -> {
            result.add(dateRange +"Üretim Periyodu için");
            group.sort((p1, p2) -> Float.compare(p2.getPercentage(), p1.getPercentage()));
            for (Percentage percentage : group) {
                result.add("-" + percentage.getModel().getName() + " modeli %" + percentage.getPercentage() + " oranında üretilecektir.");
            }
        });
        return result;
    }
    
    @Override
    public List<Percentage> getPercentagesByProjectAndPlanType(Project project, PlanType planType) {
        return percentageRepository.findByProjectAndPlanType(project, planType);
    }
}
