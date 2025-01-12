package com.huawei.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.log.entity.Log;
import com.huawei.log.service.LogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/logs")
@Tag(name = "Logs", description = "Log kayıt işlemleri için API end-pointler")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/create-log")
    @Operation(summary = "Create a new log", description = "İşlemler sonrası yeni bir log kaydı oluşturur")
    public ResponseEntity<Log> createLog(@RequestBody Log log) {
        Log createdLog = logService.createLog(log);
        return ResponseEntity.ok(createdLog);
    }
}
