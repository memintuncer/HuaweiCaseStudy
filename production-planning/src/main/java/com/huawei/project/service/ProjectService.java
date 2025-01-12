package com.huawei.project.service;

import java.util.List;

import com.huawei.dto.ProjectRequest;
import com.huawei.dto.ProjectUpdateRequest;
import com.huawei.project.entity.Project;

public interface ProjectService {
    Project createProject(ProjectRequest request);
    public Project changeProjectType(Long projectId, ProjectUpdateRequest request);
    void changeModelActivity(Long projectId, List<Long> modelIds);
    Project getProjectById(Long projectId);
    List<Project> getAllProjects();
    List<String> getModelsByPercentages(Long projectId);
	List<String> calculatePartQuantitiesForProject(Long projectId);
}
