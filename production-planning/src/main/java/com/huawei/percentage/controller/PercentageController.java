package com.huawei.percentage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.percentage.entity.Percentage;
import com.huawei.percentage.service.PercentageService;

@RestController
@RequestMapping("/percentages")
public class PercentageController {

    @Autowired
    private PercentageService percentageService;

    @PostMapping
    public ResponseEntity<Percentage> createPercentage(@RequestBody Percentage percentage) {
        Percentage createdPercentage = percentageService.createPercentage(percentage);
        return ResponseEntity.ok(createdPercentage);
    }
}
