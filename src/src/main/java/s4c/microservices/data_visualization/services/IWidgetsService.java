package s4c.microservices.data_visualization.services;

import java.util.List;

import s4c.microservices.data_visualization.model.entity.Dashboards;
import s4c.microservices.data_visualization.model.entity.Widgets;

public interface IWidgetsService {
	public List<Widgets> listWidgets();
}
