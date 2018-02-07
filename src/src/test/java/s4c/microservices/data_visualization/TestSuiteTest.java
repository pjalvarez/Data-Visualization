package s4c.microservices.data_visualization;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import s4c.microservices.data_visualization.model.entity.Dashboards;
import s4c.microservices.data_visualization.model.entity.Widgets;
import s4c.microservices.data_visualization.model.repository.DashboardsRepository;
import s4c.microservices.data_visualization.model.repository.SourceParametersRepository;
import s4c.microservices.data_visualization.model.repository.SourcesRepository;
import s4c.microservices.data_visualization.model.repository.WidgetPropertiesRepository;
import s4c.microservices.data_visualization.model.repository.WidgetsRepository;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestSuiteTest {
	static final String dashboard_json = "{\"name\":\"Dashboard#1\",\"owner\":\"Emergya\",\"_public\": true}";
	static final String dashboard_complete_json = "{\"name\":\"Dashboard#1\",\"owner\":\"Emergya\",\"_public\": true,\"assets\":[{\"asset\":\"Asset A\"},{\"asset\":\"Asset B\"}],\"widgets\":[{\"name\":\"Widget A\",\"sources\": [{\"url\":\"http://google.es\",\"parameters\":[{\"name\":\"parameter_a\",\"operator\":\"operator_a\",\"value\":\"value_a\"},{\"name\":\"parameter_b\",\"operator\":\"operator_b\",\"value\":\"value_b\"}]}],\"type\":{\"name\":\"pieChart\"},\"properties\": [{\"name\" :\"width\", \"value\":\"220px\"},{\"name\" :\"height\", \"value\":\"220px\"},{\"name\" :\"position\", \"value\":\"top\"}]}]}";
	static final String widget_json = "{\"name\":\"Widget B\",\"sources\":[{\"url\":\"http://google..es\",\"parameters\":[{\"name\":\"parameter_a\",\"operator\":\"operator_a\",\"value\":\"value_a\"},{\"name\":\"parameter_b\",\"operator\":\"operator_b\",\"value\":\"value_b\"}]}],\"type\":{\"name\":\"pieChart\"},\"properties\": [{\"name\" :\"width\", \"value\":\"220px\"},{\"name\" :\"height\", \"value\":\"220px\"},{\"name\" :\"position\", \"value\":\"top\"}]}";
	
    private MockMvc mockMvc;
    private List<Dashboards> dashboardList = new ArrayList<>();
    private HttpMessageConverter<?> mappingJackson2HttpMessageConverter;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    
    @Autowired 
    private DashboardsRepository dashboardsRepository;
    @Autowired 
    private WidgetsRepository widgetsRepository;
    @Autowired 
    private WidgetPropertiesRepository wpPrepository;
    @Autowired 
    private SourcesRepository sourcesRepository;
    @Autowired 
    private SourceParametersRepository spRepository;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
    @After
    public void ends(){
    	this.dashboardsRepository.deleteAll();
    }
    
    @Before
    public void setup() throws Exception {    	
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.spRepository.deleteAllInBatch();
        this.sourcesRepository.deleteAllInBatch();
        this.wpPrepository.deleteAllInBatch();
        this.widgetsRepository.deleteAllInBatch();
        this.dashboardsRepository.deleteAllInBatch();
        
        try {
        	
        	Dashboards dashCompleto = new Dashboards(3L,"Dashboard Completo", true, "Emergya");
        	Widgets widget = new Widgets(1L,"Widget A");
        	widget.setDashboard(dashCompleto);
        	ArrayList<Widgets> aWidgets=new ArrayList<>();
        	aWidgets.add(widget);
        	dashCompleto.setWidgets((aWidgets));
        	
	        this.dashboardList.add(dashboardsRepository.save(new Dashboards(1L,"Dashboard A", true, "Emergya")));
	        this.dashboardList.add(dashboardsRepository.save(new Dashboards(2L,"Dashboard B", false, "Emergya")));
	        this.dashboardList.add(dashboardsRepository.save(dashCompleto));
        } catch (DataIntegrityViolationException e){
        	this.dashboardList.add(dashboardsRepository.findOne(1L));
        	this.dashboardList.add(dashboardsRepository.findOne(2L));
        }
        
    }

	@Test
	public void listDashboardsTest() throws Exception {
		String url="/data-visualization/dashboards";
		mockMvc.perform(get(url)).andExpect(status().isOk());
	}

	@Test
	public void addDashboardsTest() throws Exception {
		
		String url = "/data-visualization/dashboards/";
		mockMvc.perform(post(url)
				.content(dashboard_json)
				.contentType(contentType)
				).andExpect(status().isOk());
	}

	
	@Test
	public void getDashboardsByIdTest() throws Exception {
		 String url="/data-visualization/dashboards/" + 
		 this.dashboardList.get(0).getId();		 
		 mockMvc.perform(get(url)).andExpect(status().isOk());
	}
	
	@Test
	public void updateDashboardsTest() throws Exception {
		String url="/data-visualization/dashboards/" + 
				 this.dashboardList.get(1).getId();
		
		mockMvc.perform(put(url)
				.content(dashboard_json)
				.contentType(contentType)
				).andExpect(status().isOk());
	}

	@Test
	public void deleteDashboardsTest() throws Exception {
		
		String url="/data-visualization/dashboards/" + 
				 this.dashboardList.get(1).getId();
		
		mockMvc.perform(delete(url)				
				.contentType(contentType)
				).andExpect(status().isOk());		

	}

	@Test
	public void getWidgetsInDashboardsTest() throws Exception {
		String url = "/data-visualization/dashboards/" + this.dashboardList.get(0).getId() + "/widgets";
		mockMvc.perform(get(url)).andExpect(status().isOk());
	}

	@Test
	public void createWidgetsInDashboardsTest() throws Exception {
		String url = "/data-visualization/dashboards/" + this.dashboardList.get(0).getId() + "/widgets";

		mockMvc.perform(post(url)
				.content(widget_json)
				.contentType(contentType)
				).andExpect(status().isOk());		
	}
	
	@Test
	public void findWidgetsInDashboardsTest() throws Exception {
		String url = "/data-visualization/dashboards/" 
	+ this.dashboardList.get(2).getId() 
	+ "/widgets/" 
	+ this.dashboardList.get(2).getWidgets().iterator().next().getId() ;
		
		mockMvc.perform(get(url)				
				.contentType(contentType)
				).andExpect(status().isOk());

	}

	@Test
	public void updateWidgetsInDashboardsTest() throws Exception {
		String url = "/data-visualization/dashboards/" 
				+ this.dashboardList.get(2).getId() 
				+ "/widgets/" 
				+ this.dashboardList.get(2).getWidgets().iterator().next().getId() ;
		
		mockMvc.perform(put(url)
				.content(widget_json)
				.contentType(contentType)
				).andExpect(status().isOk());
	}

	@Test
	public void deleteWidgetsInDashboardsTest() throws Exception {
		String url = "/data-visualization/dashboards/" 
				+ this.dashboardList.get(2).getId() 
				+ "/widgets/" 
				+ this.dashboardList.get(2).getWidgets().iterator().next().getId() ;
		
		mockMvc.perform(delete(url)				
				.contentType(contentType)
				).andExpect(status().isOk());		

	}
	
	@Test
	public void listWidgetsTest() throws Exception {
		String url = "/data-visualization//widgets";
		mockMvc.perform(get(url)).andExpect(status().isOk());
	}
}
