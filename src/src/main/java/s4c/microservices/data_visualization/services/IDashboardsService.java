package s4c.microservices.data_visualization.services;

import java.util.List;

import s4c.microservices.data_visualization.model.entity.Dashboards;
import s4c.microservices.data_visualization.model.entity.Widgets;

public interface IDashboardsService {
	public List<Dashboards> listDashboards();
	public Dashboards addDashboard(Dashboards dashboard);
	public Dashboards getDashboardById(String dashboardId);
	public Boolean updateDashboard(String dashboardId, Dashboards dashboard);
	public Boolean  deleteDashboard(String dashboardId);
	public List<Widgets> getWidgetsInDashboard(String dashboardId);
	public List<Widgets>  createWidgetInDashboard(String dashboardId, Widgets widget);
	public Widgets  findWidgetInDashboard(String dashboardId, String widgetId);
	public Boolean  updateWidgetInDashboard(String dashboardId, String widgetId, Widgets newWidget);
	public Boolean  deleteWidgetInDashboard(String dashboardId, String widgetId);
}
