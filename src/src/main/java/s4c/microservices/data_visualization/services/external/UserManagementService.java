package s4c.microservices.data_visualization.services.external;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import s4c.microservices.data_visualization.model.Assets;

@Service
public class UserManagementService {
 
    @Autowired
    protected RestTemplate restTemplate;
    protected String serviceUrl;
 
    public UserManagementService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl: "http://" + serviceUrl;
    }
    
    
    public List<Assets> getAssetsByUser (Long userId) {
    	ResponseEntity<Assets[]> response=   restTemplate.getForEntity(serviceUrl + "/users/assets/{userId}/user",Assets[].class, userId);
    	if(response.getStatusCode().equals(HttpStatus.OK))
    		return  new ArrayList<Assets>(Arrays.asList(((Assets[])response.getBody())));
    	
    	return new ArrayList<Assets>();
    }

    
}