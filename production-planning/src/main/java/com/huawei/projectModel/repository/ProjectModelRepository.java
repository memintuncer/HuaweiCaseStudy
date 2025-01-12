package com.huawei.projectModel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huawei.model.entity.Model;
import com.huawei.project.entity.Project;
import com.huawei.projectModel.entity.ProjectModel;

@Repository
public interface ProjectModelRepository extends JpaRepository<ProjectModel, Long> {
	@Query("SELECT pm FROM ProjectModel pm WHERE pm.project.id = :projectId AND pm.model.id = :modelId")
    Optional<ProjectModel> findByProjectIdAndModelId(@Param("projectId") Long projectId, @Param("modelId") Long modelId);
	
	@Query("SELECT pm FROM ProjectModel pm WHERE pm.project = :project AND pm.model = :model")
	ProjectModel findByProjectAndModel(@Param("project") Project project, @Param("model") Model model);
}
