package com.huawei.log.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.log.entity.Log;
import com.huawei.log.entity.LogActions;
import com.huawei.log.repository.LogRepository;
import com.huawei.model.entity.Model;
import com.huawei.part.entity.Part;
import com.huawei.project.entity.Project;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public Log createLog(Log log) {
        log.setTimestamp(LocalDateTime.now());
        return logRepository.save(log);
    }

    @Override
    public void logModelAddition(Project project, Model model) {
        Log modelLog = new Log();
        modelLog.setLogAction(LogActions.NEW_PROJECT);
        modelLog.setDetails(model.getName() + " modeli, " + project.getName() + " projesi için eklendi.");
        modelLog.setProjectId(project.getId());
        createLog(modelLog);
    }

    @Override
    public void logPartAddition(Project project, Model model, Part part, int quantity) {
        Log partLog = new Log();
        partLog.setLogAction(LogActions.NEW_PROJECT);
        partLog.setAction("Yeni Proje");
        partLog.setDetails(part.getName() + " parçası, " + project.getName() + " projesi için " + model.getName() + " modeline " + quantity + " adet olacak şekilde eklendi.");
        partLog.setProjectId(project.getId());
        createLog(partLog);
    }
    
    @Override
    public void logModelActivityChange(Project project, Model model, boolean newStatus) {
        Log log = new Log();
        log.setLogAction(LogActions.CHANGE_MODEL_ACTIVITY);
        log.setDetails(model.getName() + " modelinin aktifliği " + (newStatus ? "aktif" : "pasif") + " olarak değiştirildi.");
        log.setProjectId(project.getId());
        createLog(log);
    }
    
    @Override
    public void logNewModel(Model model) {
        Log log = new Log();
        log.setAction(LogActions.NEW_MODEL.name());
        log.setDetails(model.getName() + " modeli oluşturuldu.");
        log.setTimestamp(LocalDateTime.now());
        logRepository.save(log);
    }
    
    @Override
    public void logPartQuantityChange(Model model, Part part, Integer oldQuantity, Integer newQuantity) {
        Log log = new Log();
        log.setAction(LogActions.CHANGE_PART_QUANTITY.name());
        log.setDetails(part.getName() + " parçasının miktarı " + oldQuantity + " -> " + newQuantity + " olarak güncellendi. Model: " + model.getName());
        log.setTimestamp(LocalDateTime.now());
        logRepository.save(log);
    }
}
