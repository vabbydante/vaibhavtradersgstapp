package com.vaibhavtraders.dto;

public class StateDTO {
	private Long stateID;
    private String stateName;
	public Long getStateID() {
		return stateID;
	}
	public void setStateID(Long stateID) {
		this.stateID = stateID;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	@Override
	public String toString() {
		return "StateDTO [stateID=" + stateID + ", stateName=" + stateName + "]";
	}
}
