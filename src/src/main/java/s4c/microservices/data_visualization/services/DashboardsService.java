package s4c.microservices.data_visualization.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s4c.microservices.data_visualization.model.entity.Dashboards;

import s4c.microservices.data_visualization.model.entity.SourceType;
import s4c.microservices.data_visualization.model.entity.Sources;

import s4c.microservices.data_visualization.model.entity.Widgets;
import s4c.microservices.data_visualization.model.repository.DashboardsRepository;
import s4c.microservices.data_visualization.model.repository.SourceTypeRepository;
import s4c.microservices.data_visualization.model.repository.WidgetsRepository;

@Service
public class DashboardsService implements IDashboardsService {

	@Autowired
	private DashboardsRepository dashboardsRepository;
	@Autowired
	private SourceTypeRepository sourceTypeRepository;
	@Autowired
	private WidgetsRepository widgetRepository;

	/**
	 * Returns a complete list of dashboards
	 */
	@Override
	public List<Dashboards> listDashboards() {

		return dashboardsRepository.findAll();

	}

	/**
	 * Adds a new Dashboard
	 */
	@Override
	public Dashboards addDashboard(Dashboards dashboard) {
		if (dashboard != null)
			return dashboardsRepository.saveAndFlush(setRelations(dashboard));
		else
			return dashboard;
	}

	/**
	 * Sets the correct relation between entities to persist
	 * 
	 * @param dashboard
	 *            dashboard
	 * @return dashboard
	 */
	private Dashboards setRelations(Dashboards dashboard) {
		if (dashboard.getAssets() != null)
			dashboard.getAssets().forEach(asset -> asset.setDashboard(dashboard));

		Consumer<Sources> consumerSource = new Consumer<Sources>() {
			public void accept(Sources source) {
				if (source.getParameters() != null)
					source.getParameters().forEach(parameter -> parameter.setSource(source));
			}
		};

		Consumer<Widgets> consumerWidget = new Consumer<Widgets>() {
			@Override
			public void accept(Widgets widget) {
				widget.setDashboard(dashboard);
				if (widget.getSources() != null) {
					widget.getSources().forEach(source -> source.setWidget(widget));
					widget.getSources().forEach(consumerSource);
				}
				if (widget.getProperties() != null)
					widget.getProperties().forEach(prop -> prop.setWidget(widget));

				if (widget.getType() != null && !widget.getType().name.isEmpty()) {
					SourceType type = sourceTypeRepository.findOneByName(widget.getType().getName());
					if (type != null) {
						widget.setType(type);
					} else {
						widget.setType(null);
					}
				}

			};
		};

		if (dashboard.getWidgets() != null)
			dashboard.getWidgets().forEach(consumerWidget);

		return dashboard;

	}

	public Dashboards getDashboardById(String dashboardId) {
		return dashboardsRepository.findOne(Long.parseLong(dashboardId));
	}

	public Boolean updateDashboard(String dashboardId, Dashboards dashboard) {

		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if (original != null) {
			dashboardsRepository.delete(original);
			dashboard = setRelations(dashboard);
			dashboardsRepository.saveAndFlush(dashboard);
			return true;
		} 
		return false;
	}
	
	
	public Boolean deleteDashboard(String dashboardId){
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if (original != null) {
			dashboardsRepository.delete(original);
			return true;
		}
		
		return false;
		
	}
	
	
	public List<Widgets> getWidgetsInDashboard(String dashboardId){
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if(original!=null){
			return (List<Widgets>) original.getWidgets();
		}
		return null;
	}
	
	@Override
	public  List<Widgets> createWidgetInDashboard(String dashboardId, Widgets widget){
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if(original!=null){
			if(original.getWidgets()==null)				
				original.setWidgets(new ArrayList<Widgets>());
			
			widget.setDashboard(original);
			original.getWidgets().add(widget);
			
			original = setRelations(original);
			
			dashboardsRepository.saveAndFlush(original);
			return (List<Widgets>) original.getWidgets();
			
		}		
		return null;		
	}
	
	public Widgets  findWidgetInDashboard(String dashboardId, String widgetId){
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		Widgets widget = widgetRepository.findOne(Long.parseLong(widgetId));
		if((original!=null) && (widget!=null)){
			if(original.getWidgets().contains(widget)){
				return widget;
			} 	
		}
		return null;
	}
	
	public Boolean  updateWidgetInDashboard(String dashboardId, String widgetId, Widgets newWidget){
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		Widgets widget = widgetRepository.findOne(Long.parseLong(widgetId));
		if((original!=null) && (widget!=null)){
			if(original.getWidgets().contains(widget)){
				original.getWidgets().remove(widget);
				widgetRepository.delete(widget);
				newWidget.setDashboard(original);
				original.getWidgets().add(newWidget);
				original = setRelations(original);
				dashboardsRepository.saveAndFlush(original);
				return true;
			}
		}
		return false;
		
	}
	
	public Boolean  deleteWidgetInDashboard(String dashboardId, String widgetId){
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		Widgets widget = widgetRepository.findOne(Long.parseLong(widgetId));
		if((original!=null) && (widget!=null)){
			if(original.getWidgets().contains(widget)){
				original.getWidgets().remove(widget);
				widget.setDashboard(null);
				widgetRepository.delete(widget);
				return true;
			}
		}
		return false;
	}


	
}
