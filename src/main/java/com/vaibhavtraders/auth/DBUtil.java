package com.vaibhavtraders.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaibhavtraders.dao.ApiDetailsRepository;
import com.vaibhavtraders.dao.UserDetailsRepository;
import com.vaibhavtraders.entity.ApiDetails;
import com.vaibhavtraders.entity.Auth;

@Component
public class DBUtil {
	@Autowired
    private UserDetailsRepository userApiKeyRepository;
	@Autowired
    private ApiDetailsRepository apiDetailsRepository;
	
	/**
     * Fetch user details by username.
     * 
     * @param username The username to look up.
     * @return UserApiKey object or null if not found.
     */
    public Auth getUserByUsername(String username) {
        return userApiKeyRepository.findByUsername(username);
    }

    /**
     * Add or update a user in the database.
     * 
     * @param auth The UserApiKey object to save.
     * @return Saved UserApiKey object.
     */
    public Auth saveUser(Auth auth) {
        return userApiKeyRepository.save(auth);
    }

    /**
     * Delete a user from the database by username.
     * 
     * @param id The username id to delete.
     */
    public void deleteUserByUsername(Long id) {
        userApiKeyRepository.deleteById(id);
    }
    
    /**
     * Fetch the API details and return as a HashMap.
     * 
     * @return A HashMap with the apiHeaderKey as the key and apiHeaderValue as the value.
     */
    public Map<String, String> getApiDetailsAsMap() {
        ApiDetails apiDetails = apiDetailsRepository.findTopBy();
        Map<String, String> apiDetailsMap = new HashMap<>();
        
        if (apiDetails != null) {
            apiDetailsMap.put(apiDetails.getApiHeaderKey(), apiDetails.getApiHeaderValue());
        }
        
        return apiDetailsMap;
    }
}
