package com.huawei.log.service;

import java.util.List;

import com.huawei.log.entity.Log;
import com.huawei.log.entity.LogActions;
import com.huawei.model.entity.Model;
import com.huawei.part.entity.Part;
import com.huawei.project.entity.Project;

public interface LogService {
    Log createLog(Log log);

    void logModelAddition(Project project, Model model);

    void logPartAddition(Project project, Model model, Part part, int quantity);
    
    void logModelActivityChange(Project project, Model model, boolean newStatus);
    
    void logNewModel(Model model);
    
    void logPartQuantityChange(Model model, Part part, Integer oldQuantity, Integer newQuantity);
    
    Log getLogById(Long id);
    
    List<Log> getAllLogs();
    
    List<Log> getLogsByAction(LogActions action);
}
