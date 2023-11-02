package com.vaibhavtraders.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.vaibhavtraders.bo.DeliveryModeBO;
import com.vaibhavtraders.dto.DeliveryModeDTO;
import com.vaibhavtraders.entity.DeliveryMode;
import com.vaibhavtraders.exception.GeneralException;
import com.vaibhavtraders.response.ResponseObject;

@Service
public class DeliveryModeService {

	@Autowired
	DeliveryModeBO deliveryModeBO;

	static Logger logger = Logger.getLogger(DeliveryModeService.class);

	public ResponseObject createDeliveryMode(@RequestBody DeliveryModeDTO deliveryModeDTO) throws GeneralException {
		logger.info("Create Delivery Mode method triggered inside Service Layer");
		logger.info("Before updating in the table : " + deliveryModeDTO);
		ResponseObject responseObject = new ResponseObject();
		try {
			DeliveryMode d = new DeliveryMode();
			d.setModeName(deliveryModeDTO.getModeName());
			DeliveryMode deliveryMode = deliveryModeBO.insert(d);

			if (deliveryMode != null) {
				deliveryModeDTO.setDeliveryModeID(deliveryMode.getDeliveryModeID());
				deliveryModeDTO.setModeName(deliveryMode.getModeName());
				responseObject.setDeliveryModeDTO(deliveryModeDTO);
				responseObject.setSuccessMessage("Delivery Mode inserted successfully for : " + d.getDeliveryModeID());
				System.out.println("Delivery Mode ID : " + deliveryModeDTO.getDeliveryModeID());
				System.out.println("Delivery Mode Name : " + deliveryModeDTO.getModeName());
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while creating delivery mode. Contact admin.");
			logger.error(e);
		}
		return responseObject;
	}

	public ResponseObject findAllDeliveryModes() throws GeneralException {
		logger.info("Find all delivery modes method triggered.");
		ResponseObject responseObject = new ResponseObject();
		try {
			List<DeliveryMode> deliveryModeList = deliveryModeBO.findAllDeliveryModes();
			List<DeliveryModeDTO> deliveryModeDTOList = new ArrayList<DeliveryModeDTO>();
			if(deliveryModeList != null) {
				responseObject.setSuccessMessage("Fetching all delivery modes successful.");
				for(DeliveryMode deliveryMode : deliveryModeList) {
					DeliveryModeDTO deliveryModeDTO = new DeliveryModeDTO();
					deliveryModeDTO.setDeliveryModeID(deliveryMode.getDeliveryModeID());
					deliveryModeDTO.setModeName(deliveryMode.getModeName());
					deliveryModeDTOList.add(deliveryModeDTO);
					responseObject.setDeliveryModeDTOList(deliveryModeDTOList);
					System.out.println(deliveryModeDTOList);
				}
			} else {
				responseObject.setFailureMessage("Error while fetching all Delivery Modes.");
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while fetching all Delivery Modes.");
			logger.error(e);
		}
		return responseObject;
	}
	
	public ResponseObject deleteDeliveryMode(@RequestBody DeliveryModeDTO deliveryModeDTO) throws GeneralException {
		logger.info("deleteDeliveryMode() method triggered.");
		ResponseObject responseObject = new ResponseObject();
		try {
			DeliveryMode deliveryMode = new DeliveryMode();
			deliveryMode.setDeliveryModeID(deliveryModeDTO.getDeliveryModeID());
			deliveryModeBO.deleteDeliveryMode(deliveryMode);
			responseObject.setDeliveryMode(deliveryMode);
			responseObject.setSuccessMessage("Deleted Delivery Mode with ID : " + deliveryModeDTO.getDeliveryModeID());
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while deleting Delivery Mode with ID : " + deliveryModeDTO.getDeliveryModeID());
		}
		return responseObject;
	}
}
