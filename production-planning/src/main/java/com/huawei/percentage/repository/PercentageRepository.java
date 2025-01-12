package com.huawei.percentage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huawei.model.entity.Model;
import com.huawei.percentage.entity.Percentage;
import com.huawei.project.entity.PlanType;
import com.huawei.project.entity.Project;

@Repository
public interface PercentageRepository extends JpaRepository<Percentage, Long> {
	
	@Query("SELECT p FROM Percentage p WHERE p.project.id = :projectId AND p.planType = :planType")
    List<Percentage> findByProjectIdAndPlanType(@Param("projectId") Long projectId, @Param("planType") PlanType planType);
	@Query("SELECT p FROM Percentage p WHERE p.project = :project AND p.model = :model AND p.planType = :planType")
    List<Percentage> findByProjectAndModelAndPlanType(@Param("project") Project project,
                                                      @Param("model") Model model,
                                                      @Param("planType") PlanType planType);
	
	@Query("SELECT p FROM Percentage p WHERE p.project = :project AND p.planType = :planType")
    List<Percentage> findByProjectAndPlanType(@Param("project") Project project,
                                              @Param("planType") PlanType planType);
}
