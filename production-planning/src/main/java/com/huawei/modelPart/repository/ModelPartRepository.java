package com.huawei.modelPart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huawei.model.entity.Model;
import com.huawei.modelPart.entity.ModelPart;
import com.huawei.project.entity.Project;

@Repository
public interface ModelPartRepository extends JpaRepository<ModelPart, Long> {
	@Query("SELECT mp FROM ModelPart mp WHERE mp.model.id = :modelId AND mp.part.id = :partId")
    Optional<ModelPart> findByModelAndPartId(@Param("modelId") Long modelId, @Param("partId") Long partId);
	
	@Query("SELECT mp FROM ModelPart mp WHERE mp.model = :model AND mp.model.isActive = true")
    List<ModelPart> findByModelAndProject(@Param("model") Model model);

    @Query("SELECT mp FROM ModelPart mp JOIN mp.model m JOIN m.projectModels pm WHERE pm.project = :project AND pm.modelActive = true")
    List<ModelPart> findByProjectAndActiveModels(@Param("project") Project project);
}
