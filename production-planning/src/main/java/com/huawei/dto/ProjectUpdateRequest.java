package com.huawei.dto;

import java.util.List;

public class ProjectUpdateRequest {
    private String currentPlan;
    private List<ModelUpdateRequest> models;

    public String getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(String currentPlan) {
        this.currentPlan = currentPlan;
    }

    public List<ModelUpdateRequest> getModels() {
        return models;
    }

    public void setModels(List<ModelUpdateRequest> models) {
        this.models = models;
    }

    public static class ModelUpdateRequest {
        private Long id;
        private List<PercentageUpdateRequest> percentages;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<PercentageUpdateRequest> getPercentages() {
            return percentages;
        }

        public void setPercentages(List<PercentageUpdateRequest> percentages) {
            this.percentages = percentages;
        }
    }

    public static class PercentageUpdateRequest {
        private Float percentage;
        private String dateRange;

        public Float getPercentage() {
            return percentage;
        }

        public void setPercentage(Float percentage) {
            this.percentage = percentage;
        }

        public String getDateRange() {
            return dateRange;
        }

        public void setDateRange(String dateRange) {
            this.dateRange = dateRange;
        }
    }
}
