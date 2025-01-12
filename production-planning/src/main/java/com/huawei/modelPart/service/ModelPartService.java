package com.huawei.modelPart.service;

import java.util.Map;

import com.huawei.model.entity.Model;
import com.huawei.part.entity.Part;
import com.huawei.percentage.entity.Percentage;
import com.huawei.project.entity.Project;

public interface ModelPartService {
	void saveModelPart(Model model, Long partId, Integer quantity);
	void updatePartQuantity(Model model, Long partId, Integer newQuantity);
	Map<Part, Integer> calculatePartCounts(Project project, Percentage percentage);
}
