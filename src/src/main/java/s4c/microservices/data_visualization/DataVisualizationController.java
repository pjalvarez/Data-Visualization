package s4c.microservices.data_visualization;

import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import s4c.microservices.model.DummieRequest;
import s4c.microservices.model.DummieResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api
@RestController
@RequestMapping("data-visualization")
public class DataVisualizationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(method = RequestMethod.GET, value = "dashboards")
	@ApiOperation(value = "dashboards", nickname = "dashboards", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse listDashboards(
			@ApiParam(value = "request", required = false) @RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (listDashboards)");
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "dashboards")
	@ApiOperation(value = "dashboards", nickname = "dashboards", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse addDashboards(
			@ApiParam(value = "request", required = true) @RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (addDashboard)");
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "dashboards/{dashboardId}")
	@ApiOperation(value = "dashboards", nickname = "dashboards", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse getDashboardById (
			@PathVariable("dashboardId") String dashboardId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (getDashboardById)");
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "dashboards/{dashboardId}")
	@ApiOperation(value = "dashboards", nickname = "dashboards", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse updateDashboard (
			@PathVariable("dashboardId") String dashboardId,
			@ApiParam(value = "request", required = true) @RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (updateDashboard) " + dashboardId);
	}	
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "dashboards/{dashboardId}")
	@ApiOperation(value = "deleteDashboard", nickname = "deleteDashboard", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse deleteDashboard (
			@PathVariable("dashboardId") String dashboardId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (deleteDashboard)" + dashboardId);
	}	

	
	
	@RequestMapping(method = RequestMethod.GET, value = "dashboards/{dashboardId}/widgets")
	@ApiOperation(value = "getWidgetsInDashboard", nickname = "getWidgetsInDashboard", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse getWidgetsInDashboard (
			@PathVariable("dashboardId") String dashboardId, 
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (getWidgetsInDashboard) " + dashboardId);
	}	

	
	@RequestMapping(method = RequestMethod.POST, value = "dashboards/{dashboardId}/widgets")
	@ApiOperation(value = "createWidgetsInDashboard", nickname = "createWidgetsInDashboard", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse createWidgetsInDashboard (
			@PathVariable("dashboardId") String dashboardId, 
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (createWidgetsInDashboard) " + dashboardId);
	}	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "widgets")
	@ApiOperation(value = "listWidgets", nickname = "listWidgets", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse listWidgets (
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (listWidgets) ");
	}
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "dashboards/{dashboardId}/widgets/{widgetId}")
	@ApiOperation(value = "findWidgetsInDashboard", nickname = "findWidgetsInDashboard", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse findWidgetsInDashboard (
			@PathVariable("dashboardId") String dashboardId,
			@PathVariable("widgetId") String widgetId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (findWidgetsInDashboard) " + dashboardId + " widget: " + widgetId);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "dashboards/{dashboardId}/widgets/{widgetId}")
	@ApiOperation(value = "updateWidgetInDashboard", nickname = "updateWidgetInDashboard", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse updateWidgetInDashboard (
			@PathVariable("dashboardId") String dashboardId,
			@PathVariable("widgetId") String widgetId,
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (updateWidgetInDashboard) " + dashboardId + " widget: " + widgetId);
	}		
	
	@RequestMapping(method = RequestMethod.DELETE, value = "dashboards/{dashboardId}/widgets/{widgetId}")
	@ApiOperation(value = "deleteWidgetInDashboard ", nickname = "deleteWidgetInDashboard ", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse deleteWidgetInDashboard (
			@PathVariable("dashboardId") String dashboardId,
			@PathVariable("widgetId") String widgetId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (deleteWidgetInDashboard ) " + dashboardId + " widget: " + widgetId);
	}
}