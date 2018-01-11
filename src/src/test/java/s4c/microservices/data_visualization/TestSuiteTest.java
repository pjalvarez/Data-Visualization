package s4c.microservices.data_visualization;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import s4c.microservices.model.DummieResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestSuiteTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void listDashboardsTest() {
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity("/data-visualization/dashboards",
				DummieResponse.class, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void addDashboardsTest() {
		String json = "{\"id\":\"666\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<DummieResponse> response = this.restTemplate.postForEntity("/data-visualization/dashboards/",
				request, DummieResponse.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	
	@Test
	public void getDashboardsByIdTest() {
		 HashMap<String, String> urlVariables = new HashMap<String, String>();
		 urlVariables.put("id", "44");		 
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity("/data-visualization/dashboards/{id}",
				 DummieResponse.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void updateDashboardsTest() {
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");

		String json = "{\"nombre\":\"Fede\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.exchange("/data-visualization/dashboards/{id}", HttpMethod.PUT,
				request, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deleteDashboardsTest() {
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");		
		ResponseEntity<String> response = restTemplate.exchange("/data-visualization/dashboards/{id}", HttpMethod.DELETE,
				null, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	
	@Test
	public void getWidgetsInDashboardsTest() {
		String url = "/data-visualization/dashboards/{id}/widgets";
		 HashMap<String, String> urlVariables = new HashMap<String, String>();
		 urlVariables.put("id", "44");		 
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				 DummieResponse.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void createWidgetsInDashboardsTest() {
		String url = "/data-visualization/dashboards/{id}/widgets";
		 HashMap<String, String> urlVariables = new HashMap<String, String>();
		 urlVariables.put("id", "44");
		String json = "{\"id\":\"666\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		
		ResponseEntity<DummieResponse> response = this.restTemplate.postForEntity(url,
				request, DummieResponse.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	@Test
	public void findWidgetsInDashboardsTest() {
		String url = "/data-visualization/dashboards/{id}/widgets/{widgetId}";
		 HashMap<String, String> urlVariables = new HashMap<String, String>();
		 urlVariables.put("id", "44");
		 urlVariables.put("widgetId", "44");	
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				 DummieResponse.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void updateWidgetsInDashboardsTest() {
		String url = "/data-visualization/dashboards/{id}/widgets/{widgetId}";
		 HashMap<String, String> urlVariables = new HashMap<String, String>();
		 urlVariables.put("id", "44");
		 urlVariables.put("widgetId", "44");

		String json = "{\"nombre\":\"Fede\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
				request, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void deleteWidgetsInDashboardsTest() {
		String url = "/data-visualization/dashboards/{id}/widgets/{widgetId}";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");
		urlVariables.put("widgetId", "44");	
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE,
				null, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void listWidgetsTest() {
		String url = "/data-visualization//widgets";	
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				 DummieResponse.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
