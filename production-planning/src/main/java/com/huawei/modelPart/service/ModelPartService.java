package com.huawei.modelPart.service;

import com.huawei.model.entity.Model;

public interface ModelPartService {
	void saveModelPart(Model model, Long partId, Integer quantity);
	void updatePartQuantity(Model model, Long partId, Integer newQuantity);
}
