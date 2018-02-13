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

import s4c.microservices.data_visualization.model.entity.Columns;
import s4c.microservices.data_visualization.model.entity.Dashboards;
import s4c.microservices.data_visualization.model.entity.Rows;
import s4c.microservices.data_visualization.model.entity.Widgets;
import s4c.microservices.data_visualization.model.repository.ActionsRepository;
import s4c.microservices.data_visualization.model.repository.ColumnsRepository;
import s4c.microservices.data_visualization.model.repository.DashboardsRepository;
import s4c.microservices.data_visualization.model.repository.PropertiesRepository;
import s4c.microservices.data_visualization.model.repository.PropertyPagesRepository;
import s4c.microservices.data_visualization.model.repository.RowsRepository;
import s4c.microservices.data_visualization.model.repository.SourceParametersRepository;
import s4c.microservices.data_visualization.model.repository.SourcesRepository;
import s4c.microservices.data_visualization.model.repository.TagsRepository;
import s4c.microservices.data_visualization.model.repository.WidgetPropertiesRepository;
import s4c.microservices.data_visualization.model.repository.WidgetsRepository;
import s4c.microservices.data_visualization.services.DashboardsService;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestSuiteTest {
	static final String dashboard_json = "{\"name\":\"Dashboard#1\",\"structure\":\"8-4-4/8-4-4\",\"rows\":[{\"columns\":[{\"styleClass\":\"eight wide\",\"widgets\":[{\"id\":\"110\",\"description\":\"none\",\"icon\":\"none.png\",\"name\":\"Widget Test\",\"actions\":[{\"name\":\"Add\"}],\"tags\":[{\"facet\":\"Grahphic Charts\",\"name\":\"Line Chart\"}],\"propertyPages\":[{\"displayName\":\"Configuration\",\"properties\":[{\"_controlType\":\"textbox55\",\"_key\":\"title55\",\"_label\":\"Title55\",\"_value\":\"Temperature from Sensor55\",\"_required\":false,\"_order\":15},{\"_controlType\":\"textbox\",\"_key\":\"instanceId\",\"_label\":\"Widget id\",\"_value\":123,\"_required\":true,\"_order\":-1}]}],\"sources\":[{\"id\":42,\"url\":\"http://www.google.co\",\"parameters\":[{\"id\":80,\"name\":\"parameter_XMM\",\"value\":\"value_x\",\"operator\":\"operator_X\"},{\"name\":\"parameter_Y\",\"value\":\"value_b\",\"operator\":\"operator_b\"}]}],\"type\":{\"name\":\"pieChart\"},\"properties\":[{\"name\":\"width\",\"value\":\"220px\"},{\"name\":\"height\",\"value\":\"220px\"},{\"name\":\"position\",\"value\":\"top\"}]}]}]}],\"assets\":[{\"asset\":\"AA\"},{\"asset\":\"BB\"}],\"owner\":\"Emergya\",\"_public\":true}";
	static final String dashboard_complete_json = "{\"name\":\"Dashboard#1\",\"owner\":\"Emergya\",\"_public\": true,\"assets\":[{\"asset\":\"Asset A\"},{\"asset\":\"Asset B\"}],\"widgets\":[{\"name\":\"Widget A\",\"sources\": [{\"url\":\"http://google.es\",\"parameters\":[{\"name\":\"parameter_a\",\"operator\":\"operator_a\",\"value\":\"value_a\"},{\"name\":\"parameter_b\",\"operator\":\"operator_b\",\"value\":\"value_b\"}]}],\"type\":{\"name\":\"pieChart\"},\"properties\": [{\"name\" :\"width\", \"value\":\"220px\"},{\"name\" :\"height\", \"value\":\"220px\"},{\"name\" :\"position\", \"value\":\"top\"}]}]}";
	static final String widget_json = "{\"name\":\"Widget Test\",\"sources\":[{\"id\":8,\"url\":\"http://www.google.co\",\"parameters\":[{\"id\":15,\"name\":\"parameter_XMM\",\"value\":\"value_x\",\"operator\":\"operator_X\"},{\"id\":16,\"name\":\"parameter_Y\",\"value\":\"value_b\",\"operator\":\"operator_b\"}]}],\"type\":{\"id\":4,\"name\":\"pieChart\"},\"properties\":[{\"id\":22,\"name\":\"width\",\"value\":\"220px\"},{\"id\":23,\"name\":\"height\",\"value\":\"220px\"},{\"id\":24,\"name\":\"position\",\"value\":\"top\"}],\"description\":\"none\",\"icon\":\"none.png\",\"tags\":[{\"id\":15,\"facet\":\"Grahphic Charts\",\"name\":\"Line Chart\"}],\"propertyPages\":[{\"id\":8,\"displayName\":\"Configuration\",\"properties\":[{\"id\":15,\"_controlType\":\"textbox55\",\"_key\":\"title55\",\"_label\":\"Title55\",\"_value\":\"Temperature from Sensor55\",\"_required\":false,\"_order\":15},{\"id\":16,\"_controlType\":\"textbox\",\"_key\":\"instanceId\",\"_label\":\"Widget id\",\"_value\":\"123\",\"_required\":true,\"_order\":-1}]}],\"actions\":[{\"id\":1,\"name\":\"Add\"}]}";
	
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
    private ColumnsRepository cRepository;
    @Autowired 
    private RowsRepository rRepository;
    @Autowired 
    private ActionsRepository actionRepository;
    @Autowired 
    private PropertyPagesRepository ppRepository;
    @Autowired 
    private PropertiesRepository pRepository;
    @Autowired 
    private TagsRepository tagsRepository;
    
    @Autowired 
    private DashboardsService dservice;
    
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
    	
//    	for(Dashboards d : this.dashboardList)
//    		dservice.deleteDashboard(d.getId().toString());
//    	
//    	this.pRepository.deleteAllInBatch();
//    	this.ppRepository.deleteAllInBatch();
//    	this.tagsRepository.deleteAllInBatch();
//    	this.actionRepository.deleteAllInBatch();
//    	this.wpPrepository.deleteAllInBatch();
//    	this.spRepository.deleteAllInBatch();
//    	this.sourcesRepository.deleteAllInBatch();    	
//    	this.widgetsRepository.deleteAllInBatch();
//    	this.cRepository.deleteAllInBatch();
//    	this.rRepository.deleteAllInBatch();
//    	this.dashboardsRepository.deleteAllInBatch();
    }
    
    @Before
    public void setup() throws Exception {    	
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

//        this.rRepository.deleteAllInBatch();
//        this.cRepository.deleteAllInBatch();
//        this.spRepository.deleteAllInBatch();
//        this.sourcesRepository.deleteAllInBatch();
//        this.wpPrepository.deleteAllInBatch();
//        this.widgetsRepository.deleteAllInBatch();
//        this.dashboardsRepository.deleteAllInBatch();
        
        try {
        	
//        	Dashboards dashCompleto = new Dashboards(3L,"Dashboard Completo", true, "Emergya");
//        	Widgets widget = new Widgets(1L,"Widget A");
//        	Columns col = new Columns();
//        	Rows row = new Rows();
//        	
//        	widget.addColumn(col);
//        	col.addWidget(widget);
//        	row.addColumn(col);
//        	dashCompleto.addRow(row);
        	
        	
        	Widgets widget = new Widgets("Widget A");
        	Dashboards ds = new Dashboards("Dashboard A", true, "Emergya");
        	Rows row = new Rows();
        	Columns col = new Columns();
        	
        	col.addWidget(widget);
        	widget.addColumn(col);
        	
        	row.addColumn(col);
        	
        	col.setRow(row);
        	row.setDashboard(ds);
        	ds.addRow(row);
        	
        	
	        this.dashboardList.add(dashboardsRepository.save(ds));
	        this.dashboardList.add(dashboardsRepository.save(new Dashboards("Dashboard B", false, "Emergya")));
	        this.dashboardList.add(dashboardsRepository.save(ds));
	        
	        //this.dashboardList.add(dashboardsRepository.save(dashCompleto));
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
		Dashboards dd =this.dashboardList.get(2);
		Rows r = (Rows) dd.getRows().toArray()[0];
		Columns c = (Columns) r.getColumns().toArray()[0];
		String url = "/data-visualization/dashboards/" + dd.getId()+"/"+r.getId()+"/"+c.getId()+"/widgets";

		
		mockMvc.perform(post(url)
				.content(widget_json)
				.contentType(contentType)
				).andExpect(status().isOk());		
	}
	
	@Test
	public void findWidgetsInDashboardsTest() throws Exception {	
		
		Rows r =(Rows) this.dashboardList.get(0).getRows().toArray()[0];
		Columns c =(Columns) r.getColumns().toArray()[0];
		Widgets w = (Widgets) c.getWidgets().toArray()[0];
		String url = "/data-visualization/dashboards/" + this.dashboardList.get(0).getId()+ "/widgets/" + w.getId();
		
		mockMvc.perform(get(url)				
				.contentType(contentType)
				).andExpect(status().isOk());

	}

	@Test
	public void updateWidgetsInDashboardsTest() throws Exception {
		Rows r =(Rows) this.dashboardList.get(0).getRows().toArray()[0];
		Columns c =(Columns) r.getColumns().toArray()[0];
		Widgets w = (Widgets) c.getWidgets().toArray()[0];
		String url = "/data-visualization/dashboards/" + this.dashboardList.get(0).getId()+ "/widgets/" + w.getId();
		
		mockMvc.perform(put(url)
				.content(widget_json)
				.contentType(contentType)
				).andExpect(status().isOk());
	}

	@Test
	public void deleteWidgetsInDashboardsTest() throws Exception {
		Rows r =(Rows) this.dashboardList.get(0).getRows().toArray()[0];
		Columns c =(Columns) r.getColumns().toArray()[0];
		Widgets w = (Widgets) c.getWidgets().toArray()[0];
		String url = "/data-visualization/dashboards/" + this.dashboardList.get(0).getId()+ "/widgets/" + w.getId();
		
		mockMvc.perform(delete(url)				
				.contentType(contentType)
				).andExpect(status().isOk());		

	}
	
	@Test
	public void listWidgetsTest() throws Exception {
		String url = "/data-visualization/widgets";
		mockMvc.perform(get(url)).andExpect(status().isOk());
	}
}
