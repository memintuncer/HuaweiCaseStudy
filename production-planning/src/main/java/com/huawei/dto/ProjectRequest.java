package com.huawei.dto;

import java.util.List;

public class ProjectRequest {
    private String name;
    private Integer totalProductionGoal;
    private String currentPlan;
    private Boolean isDeleted;
    private List<ModelRequest> models;
    private String planType;
    private String projectRange;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalProductionGoal() {
		return totalProductionGoal;
	}

	public void setTotalProductionGoal(Integer totalProductionGoal) {
		this.totalProductionGoal = totalProductionGoal;
	}

	public String getCurrentPlan() {
		return currentPlan;
	}

	public void setCurrentPlan(String currentPlan) {
		this.currentPlan = currentPlan;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<ModelRequest> getModels() {
		return models;
	}

	public void setModels(List<ModelRequest> models) {
		this.models = models;
	}
	

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}
	

	public String getProjectRange() {
		return projectRange;
	}

	public void setProjectRange(String projectRange) {
		this.projectRange = projectRange;
	}


	public static class ModelRequest {
		private Long id;
		private Boolean isActive;
	    private List<PartRequest> parts;
	    private List<PercentageRequest> percentages;
	    
	    public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Boolean getIsActive() {
			return isActive;
		}
		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}
		public List<PartRequest> getParts() {
			return parts;
		}
		public void setParts(List<PartRequest> parts) {
			this.parts = parts;
		}
		public List<PercentageRequest> getPercentages() {
			return percentages;
		}
		public void setPercentages(List<PercentageRequest> percentages) {
			this.percentages = percentages;
		}
	
	    // Getters and Setters
	}
	
	public static class PartRequest {
		private Long id;
	    private String name;
	    private Integer quantity;
	    
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
	
	}
	
	public static class PercentageRequest {
	    private Float percentage;
	    private String dateRange;
	    private String planType;
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
		public String getPlanType() {
			return planType;
		}
		public void setPlanType(String planType) {
			this.planType = planType;
		}

	}

}
