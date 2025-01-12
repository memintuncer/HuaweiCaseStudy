package com.huawei.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.log.service.LogService;
import com.huawei.model.entity.Model;
import com.huawei.model.repository.ModelRepository;
import com.huawei.modelPart.entity.ModelPart;
import com.huawei.modelPart.service.ModelPartService;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ModelPartService modelPartService;

    @Autowired
    private LogService logService;
    
    @Override
    public Model createModel(Model request) {
        Model model = new Model();
        model.setName(request.getName());
        model.setIsActive(true); 
        model = modelRepository.save(model);

        if (request.getModelParts() != null && !request.getModelParts().isEmpty()) {
            for (ModelPart partRequest : request.getModelParts()) {
                modelPartService.saveModelPart(model, partRequest.getPart().getId(), partRequest.getQuantity());
            }
        }

        logService.logNewModel(model);

        return model;
    }
    
    @Override
    public void addPartsToModel(Long modelId, List<ModelPart> parts) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new IllegalArgumentException("Model bulunamadı. ID: " + modelId));

        for (ModelPart part : parts) {
            modelPartService.saveModelPart(model, part.getPart().getId(), part.getQuantity());
        }
    }
    
    @Override
    public void updateModelParts(Long modelId, List<ModelPart> parts) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new IllegalArgumentException("Model bulunamadı. ID: " + modelId));

        for (ModelPart partRequest : parts) {
            modelPartService.updatePartQuantity(model, partRequest.getPart().getId(), partRequest.getQuantity());
        }
    }
}
