package com.huawei.part.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.part.entity.Part;
import com.huawei.part.service.PartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/parts")
@Tag(name = "Parts", description = "Parçalarla ilgili işlemleri için API end-pointler")
public class PartController {

    @Autowired
    private PartService partService;

    @PostMapping("/create-part")
    @Operation(summary = "Create a project with models, parts, and percentages",
    description = "Mevcut model ve parçaların id'leri ve diğer proje bilgileri üzerinden yeni proje oluşturur.")
    public ResponseEntity<Part> createPart(@RequestBody Part part) {
        Part createdPart = partService.createPart(part);
        return ResponseEntity.ok(createdPart);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Part> getPartById(@PathVariable("id") @Parameter(description = "Sorgulanacak parçanın id'si") Long id) {
        Part part = partService.getPartById(id);
        return ResponseEntity.ok(part);
    }

    @GetMapping
    public ResponseEntity<List<Part>> getAllParts() {
        List<Part> parts = partService.getAllParts();
        return ResponseEntity.ok(parts);
    }
}
