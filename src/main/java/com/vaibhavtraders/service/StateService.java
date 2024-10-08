package com.vaibhavtraders.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.vaibhavtraders.bo.StateBO;
import com.vaibhavtraders.dto.StateDTO;
import com.vaibhavtraders.entity.State;
import com.vaibhavtraders.exception.GeneralException;
import com.vaibhavtraders.response.ResponseObject;
import com.vaibhavtraders.response.StateData;

@Service
public class StateService {

	@Autowired
	StateBO stateBO;

	static Logger logger = Logger.getLogger(StateService.class);

	public ResponseObject createState(@RequestBody StateDTO stateDTO) throws GeneralException {
		logger.info("Create state method triggered inside service layer");
		logger.info("Before updating in the table : " + stateDTO);
		ResponseObject responseObject = new ResponseObject();
		try {
			State s = new State();
			s.setStateName(stateDTO.getStateName());
			State state = stateBO.insert(s);

			if (state != null) {
				stateDTO.setStateID(state.getStateID());
				stateDTO.setStateName(state.getStateName());
				responseObject.setStateDTO(stateDTO);
				responseObject.setSuccessMessage("State inserted successfully for : " + s.getStateID());
				System.out.println("State ID : " + stateDTO.getStateID());
				System.out.println("State Name : " + stateDTO.getStateName());
			} else {
				responseObject.setFailureMessage("Error while creating a state.");
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while creating a state. Contact admin.");
			logger.error(e);
		}
		return responseObject;
	}
	
	public ResponseObject findStateById(Long stateID) throws GeneralException {
		logger.info("Find state by ID method triggered.");
		ResponseObject responseObject = new ResponseObject();
		try {
			if (stateID == null) {
				responseObject.setFailureMessage("State not found for given ID : " + stateID);
				return responseObject;
			}
			State state = stateBO.getStateById(stateID);
			StateDTO stateDTO = new StateDTO();
			if(state != null) {
				stateDTO.setStateID(state.getStateID());
				stateDTO.setStateName(state.getStateName());
			}
			responseObject.setStateDTO(stateDTO);
			responseObject.setSuccessMessage("State found with given ID : " + stateID);
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while finding the State : " + e.getMessage());
		}
		return responseObject;
	}

	public ResponseObject findAllStates() throws GeneralException {
		logger.info("Find all states method triggered.");
		ResponseObject responseObject = new ResponseObject();
		try {
			List<State> stateList = stateBO.findAllStates();
			List<StateDTO> stateDTOList = new ArrayList<StateDTO>();
			if (stateList != null) {
				responseObject.setSuccessMessage("Fetching all states successful.");
				for (State state : stateList) {
					StateDTO stateDTO = new StateDTO();
					stateDTO.setStateID(state.getStateID());
					stateDTO.setStateName(state.getStateName());
					stateDTOList.add(stateDTO);
					responseObject.setStateDTOList(stateDTOList);
					System.out.println(stateDTOList);
				}
			} else {
				responseObject.setFailureMessage("Error while fetching all State List");
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while fetching all State List");
			logger.error(e);
		}
		return responseObject;
	}

	public ResponseObject deleteState(@RequestBody StateDTO stateDTO) throws GeneralException {
		logger.info("deleteState() method triggered.");
		ResponseObject responseObject = new ResponseObject();
		try {
			State state = new State();
			state.setStateID(stateDTO.getStateID());
			stateBO.deleteState(state);
			responseObject.setState(state);
			responseObject.setSuccessMessage("Deleted state with ID : " + stateDTO.getStateID());
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while deleting State with ID : " + stateDTO.getStateID());
		}
		return responseObject;
	}
	
	/**
	 * Method to return State Data for State Dropdowns
	 */
	public List<StateData> getStateData() {
		logger.info("getStateData method triggered.");
		List<StateData> stateData = new ArrayList<StateData>();
		List<State> allStates = new ArrayList<State>();
		try {
			allStates = stateBO.findAllStates();
			if(allStates.size() > 0) {
				for (State state : allStates) {
					StateData stData = new StateData();
					stData.setId(state.getStateID());
					stData.setName(state.getStateName());
					stateData.add(stData);
				}
			}
		} catch (Exception e) {
			logger.error("Error inside getStateData method in StateService", e);
			e.printStackTrace();
		}
		return stateData;
	}
}
