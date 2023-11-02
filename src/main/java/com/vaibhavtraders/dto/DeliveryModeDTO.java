package com.vaibhavtraders.dto;

public class DeliveryModeDTO {
	private Long deliveryModeID;
	private String modeName;
	public Long getDeliveryModeID() {
		return deliveryModeID;
	}
	public void setDeliveryModeID(Long deliveryModeID) {
		this.deliveryModeID = deliveryModeID;
	}
	public String getModeName() {
		return modeName;
	}
	public void setModeName(String modeName) {
		this.modeName = modeName;
	}
	@Override
	public String toString() {
		return "DeliveryModeDTO [deliveryModeID=" + deliveryModeID + ", modeName=" + modeName + "]";
	}
}
