package com.huawei.modelPart.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.log.service.LogService;
import com.huawei.model.entity.Model;
import com.huawei.modelPart.entity.ModelPart;
import com.huawei.modelPart.repository.ModelPartRepository;
import com.huawei.part.entity.Part;
import com.huawei.part.repository.PartRepository;

@Service
public class ModelPartServiceImpl implements ModelPartService {
	
	@Autowired
    private PartRepository partRepository;

    @Autowired
    private ModelPartRepository modelPartRepository;
    
    @Autowired
    private LogService logService;
	@Override
    public void saveModelPart(Model model, Long partId, Integer quantity) {
        Optional<Part> partOptional = partRepository.findById(partId);

        if (partOptional.isPresent()) {
            Part part = partOptional.get();
            ModelPart modelPart = new ModelPart();
            modelPart.setModel(model);
            modelPart.setPart(part);
            modelPart.setQuantity(quantity);
            modelPartRepository.save(modelPart);
        } else {
            throw new IllegalArgumentException("Parça bulunamadı. ID: " + partId);
        }
    }
	
	@Override
    public void updatePartQuantity(Model model, Long partId, Integer quantity) {
        ModelPart modelPart = modelPartRepository.findByModelAndPartId(model.getId(), partId)
                .orElseThrow(() -> new IllegalArgumentException("ModelPart bulunamadı. Model ID: " + model.getId() + ", Part ID: " + partId));

        Integer oldQuantity = modelPart.getQuantity();

        modelPart.setQuantity(quantity);
        modelPartRepository.save(modelPart);

        logService.logPartQuantityChange(model, modelPart.getPart(), oldQuantity, quantity);
    }
}
