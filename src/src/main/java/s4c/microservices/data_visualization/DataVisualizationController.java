package s4c.microservices.data_visualization;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import s4c.microservices.data_visualization.model.DummieResponse;
import s4c.microservices.data_visualization.model.entity.Dashboards;
import s4c.microservices.data_visualization.model.entity.Widgets;
import s4c.microservices.data_visualization.services.DashboardsService;
import s4c.microservices.data_visualization.services.WidgetsService;

import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@Api
@RestController
@RequestMapping("data-visualization")
public class DataVisualizationController {
	
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DashboardsService dashboardService;
	@Autowired
	private WidgetsService widgetsService;

	@RequestMapping(method = RequestMethod.GET, value = "dashboards")
	@ApiOperation(value = "listDashboards", nickname = "listDashboards", response = Dashboards.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public List<Dashboards> listDashboards(
			@ApiParam(value = "request", required = false) @RequestBody(required = false) Dashboards request){
		
		return dashboardService.listDashboards();
		

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "dashboards")
	@ApiOperation(value = "addDashboards", nickname = "addDashboards", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<Dashboards> addDashboards(
			@ApiParam(value = "request", required = true) @RequestBody(required = true) Dashboards request){
		
			Dashboards dashboard = dashboardService.addDashboard(request);
			if(dashboard!=null){
				return new ResponseEntity<Dashboards>(dashboard, HttpStatus.OK);
			} else {
				return new ResponseEntity<Dashboards>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "dashboards/{dashboardId}")
	@ApiOperation(value = "dashboards", nickname = "dashboards", response = Dashboards.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<Dashboards> getDashboardById (
			@PathVariable("dashboardId") String dashboardId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) Dashboards request){
		
			Dashboards dashboard =  dashboardService.getDashboardById(dashboardId);
			if(dashboard!=null){
				return new ResponseEntity<Dashboards>(dashboard, HttpStatus.OK);
			} else {
				return new ResponseEntity<Dashboards>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "dashboards/{dashboardId}")
	@ApiOperation(value = "dashboards", nickname = "dashboards", response = Dashboards.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<Dashboards> updateDashboard (
			@PathVariable("dashboardId") String dashboardId,
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) Dashboards request){
				
		Boolean isUpdated =  dashboardService.updateDashboard(dashboardId,request);
		if(isUpdated){
			return new ResponseEntity<Dashboards>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Dashboards>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}	
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "dashboards/{dashboardId}")
	@ApiOperation(value = "deleteDashboard", nickname = "deleteDashboard", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<Dashboards> deleteDashboard (
			@PathVariable("dashboardId") String dashboardId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) Dashboards request){
		
			Boolean result = dashboardService.deleteDashboard(dashboardId);
			if(result){
				return new ResponseEntity<Dashboards>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Dashboards>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
				
		
		
	}	

	
	
	@RequestMapping(method = RequestMethod.GET, value = "dashboards/{dashboardId}/widgets")
	@ApiOperation(value = "getWidgetsInDashboard", nickname = "getWidgetsInDashboard", response = Widgets.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public List<Widgets> getWidgetsInDashboard (
			@PathVariable("dashboardId") String dashboardId, 
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) Dashboards request){
		
			return  dashboardService.getWidgetsInDashboard(dashboardId);
	}	

	
	@RequestMapping(method = RequestMethod.POST, value = "dashboards/{dashboardId}/widgets")
	@ApiOperation(value = "createWidgetsInDashboard", nickname = "createWidgetsInDashboard", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<List<Widgets>> createWidgetsInDashboard (
			@PathVariable("dashboardId") String dashboardId, 
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) Widgets widget){
		
		List<Widgets> repositories = dashboardService.createWidgetInDashboard(dashboardId, widget);
		if(repositories != null) {
			return new ResponseEntity<List<Widgets>>( repositories,HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Widgets>>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "widgets")
	@ApiOperation(value = "listWidgets", nickname = "listWidgets", response = Widgets.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public List<Widgets> listWidgets (
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) Widgets request){
		
		
			return widgetsService.listWidgets();
		
		
	}
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "dashboards/{dashboardId}/widgets/{widgetId}")
	@ApiOperation(value = "findWidgetsInDashboard", nickname = "findWidgetsInDashboard", response = Widgets.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Widgets.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<Widgets> findWidgetsInDashboard (
			@PathVariable("dashboardId") String dashboardId,
			@PathVariable("widgetId") String widgetId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) Widgets request){
		
		Widgets repository = dashboardService.findWidgetInDashboard(dashboardId, widgetId);
		if(repository != null) {
			return new ResponseEntity<Widgets>( repository,HttpStatus.OK);
		} else {
			return new ResponseEntity<Widgets>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "dashboards/{dashboardId}/widgets/{widgetId}")
	@ApiOperation(value = "updateWidgetInDashboard", nickname = "updateWidgetInDashboard", response = Widgets.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<Widgets> updateWidgetInDashboard (
			@PathVariable("dashboardId") String dashboardId,
			@PathVariable("widgetId") String widgetId,
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) Widgets widget){
		
		Boolean isUpdated = dashboardService.updateWidgetInDashboard(dashboardId, widgetId,widget);
		if(isUpdated) {
			return new ResponseEntity<Widgets>( HttpStatus.OK);
		} else {
			return new ResponseEntity<Widgets>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}		
	
	@RequestMapping(method = RequestMethod.DELETE, value = "dashboards/{dashboardId}/widgets/{widgetId}")
	@ApiOperation(value = "deleteWidgetInDashboard ", nickname = "deleteWidgetInDashboard ", response = Widgets.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<Widgets> deleteWidgetInDashboard (
			@PathVariable("dashboardId") String dashboardId,
			@PathVariable("widgetId") String widgetId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) Widgets request){
		
		Boolean isDeleted = dashboardService.deleteWidgetInDashboard(dashboardId, widgetId);
		if(isDeleted) {
			return new ResponseEntity<Widgets>( HttpStatus.OK);
		} else {
			return new ResponseEntity<Widgets>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}
}