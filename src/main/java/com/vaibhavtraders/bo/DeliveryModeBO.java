package com.vaibhavtraders.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaibhavtraders.dao.DeliveryModeRepository;
import com.vaibhavtraders.entity.DeliveryMode;
import com.vaibhavtraders.exception.GeneralException;

@Component
public class DeliveryModeBO {
	
	@Autowired
	DeliveryModeRepository deliveryModeRepository;
	
	public DeliveryMode insert(DeliveryMode d) throws GeneralException {
		try {
			if(d.getModeName() == null) {
				throw new GeneralException("Delivery Mode Name cannot be empty.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while inserting Delivery Mode.");
		}
		return deliveryModeRepository.save(d);
	}
	
	public List<DeliveryMode> findAllDeliveryModes() throws GeneralException {
		List<DeliveryMode> allDeliveryModesList = new ArrayList<DeliveryMode>();
		try {
			allDeliveryModesList = deliveryModeRepository.findAll();
			if(allDeliveryModesList == null) {
				throw new GeneralException("No countries found.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while fetching Delivery Modes.");
		}
		return allDeliveryModesList;
	}
	
	public DeliveryMode deleteDeliveryMode(DeliveryMode d) throws GeneralException {
		try {
			if(d == null) {
				throw new GeneralException("Param id DeliveryMode d is null. Can't Delete");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while deleting Specific Delivery Mode.");
		}
		deliveryModeRepository.delete(d);
		System.out.println("DeliveryMode with ID : " + d.getDeliveryModeID() + " deleted successfully.");
		return d;
	}
}
