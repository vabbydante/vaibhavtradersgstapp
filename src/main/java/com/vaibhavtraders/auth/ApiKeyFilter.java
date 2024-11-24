package com.vaibhavtraders.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vaibhavtraders.enums.AuthConstant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
	
	@Autowired
	DBUtil dbUtil;
	
	private Map<String, String> extractApiDetails() {
		Map<String, String> apiDetails = new HashMap<>();
		apiDetails = dbUtil.getApiDetailsAsMap();
		if(!apiDetails.isEmpty()) {
			return apiDetails;
		} else {
			apiDetails.put(AuthConstant.ERROR, AuthConstant.API_RETRIEVAL_ERROR);
		}
		return apiDetails;
	}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	Map<String, String> apiDetails = extractApiDetails();
    	String apiXKey = null;
        String apiXKeyValue = null;
    	if (apiDetails.size() == 1) {
            for (Map.Entry<String, String> entry : apiDetails.entrySet()) {
            	apiXKey = entry.getKey();
            	apiXKeyValue = entry.getValue();
            }
    	}
        String apiKey = request.getHeader(apiXKey);
        if (apiXKeyValue.equals(apiKey)) {
            filterChain.doFilter(request, response); // Continue if valid
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized access");
        }
    }
}
