package com.huawei.model.service;

import java.util.List;

import com.huawei.model.entity.Model;
import com.huawei.modelPart.entity.ModelPart;

public interface ModelService {
	Model createModel(Model request);
	void addPartsToModel(Long modelId, List<ModelPart> parts);
	void updateModelParts(Long modelId, List<ModelPart> parts);
	Model getModelById(Long id);
    List<Model> getAllModels();
}
