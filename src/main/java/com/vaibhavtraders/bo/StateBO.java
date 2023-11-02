package com.vaibhavtraders.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaibhavtraders.dao.StateRepository;
import com.vaibhavtraders.entity.State;
import com.vaibhavtraders.exception.GeneralException;

@Component
public class StateBO {
	
	@Autowired
	StateRepository stateRepository;
	
	public State insert(State c) throws GeneralException {
		try {
			if(c.getStateName() == null) {
				throw new GeneralException("State name cannot be blank.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while saving State.");
		}
		return stateRepository.save(c);
	}
	
	public List<State> findAllStates() throws GeneralException {
		List<State> allStatesList = new ArrayList<State>();
		try {
			allStatesList = stateRepository.findAll();
			if(allStatesList == null) {
				throw new GeneralException("No States found.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while fetching states.");
		}
		return allStatesList;
	}
	
	public State deleteState(State s) throws GeneralException {
		try {
			if(s == null) {
				throw new GeneralException("Param ID State s is null.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while deleting Specific State");
		}
		stateRepository.delete(s);
		System.out.println("State with ID : " + s.getStateID());
		return s;
	}
}
