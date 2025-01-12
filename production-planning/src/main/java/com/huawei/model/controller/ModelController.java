package com.huawei.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.model.entity.Model;
import com.huawei.model.service.ModelService;
import com.huawei.modelPart.entity.ModelPart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/models")
@Tag(name = "Models", description = "Modellerle ilgili işlemleriçin API end-pointler")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @PostMapping("/create")
    @Operation(
    	    summary = "Create a new model",
    	    description = "Yeni bir model oluşturur. Modele parçalar eklenebilir. Model parça bilgisi eklenmeden de oluşturulabilir."
    	)
    public ResponseEntity<Model> createModel(@RequestBody Model request) {
        Model createdModel = modelService.createModel(request);
        return ResponseEntity.ok(createdModel);
    }
    
    @PostMapping("/{modelId}/add-parts")
    @Operation(
    	    summary = "Add parts to a model",
    	    description = "Bir modele bir veya birden fazla parça ekler. Parçalar model_part ilişkisi üzerinden yönetilir."
    	    		+ "Model oluşturulurken parça bilgilerinin girilmesi zorunlu değildir"
    	)
    public ResponseEntity<Void> addPartsToModel(
            @PathVariable("modelId") @Parameter(description = "Güncellenecek modelin ID değeri") Long modelId,
            @RequestBody List<ModelPart> parts) {
        modelService.addPartsToModel(modelId, parts);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{modelId}/update-parts")
    @Operation(
    	    summary = "Update quantity of parts in a model",
    	    description = "Bir modelde bulunan parçaların miktarlarını günceller. Parça ID ve miktar bilgilerini alır."
    	)
    public ResponseEntity<Void> updateModelParts(
    		@PathVariable("modelId") @Parameter(description = "Güncellenecek modelin ID değeri") Long modelId,
            @RequestBody List<ModelPart> parts) {
        modelService.updateModelParts(modelId, parts);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable("id") @Parameter(description = "Sorgulanacak modelin id'si") Long id) {
        Model model = modelService.getModelById(id);
        return ResponseEntity.ok(model);
    }

    @GetMapping
    public ResponseEntity<List<Model>> getAllModels() {
        List<Model> models = modelService.getAllModels();
        return ResponseEntity.ok(models);
    }
}
