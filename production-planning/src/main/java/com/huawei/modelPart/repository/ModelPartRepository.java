package com.huawei.modelPart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huawei.modelPart.entity.ModelPart;

@Repository
public interface ModelPartRepository extends JpaRepository<ModelPart, Long> {
	@Query("SELECT mp FROM ModelPart mp WHERE mp.model.id = :modelId AND mp.part.id = :partId")
    Optional<ModelPart> findByModelAndPartId(@Param("modelId") Long modelId, @Param("partId") Long partId);
}
