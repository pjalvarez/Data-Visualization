package s4c.microservices.data_visualization.services;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s4c.microservices.data_visualization.model.entity.Widgets;
import s4c.microservices.data_visualization.model.repository.WidgetsRepository;

@Service
public class WidgetsService implements IWidgetsService {

	@Autowired
	private WidgetsRepository widgetRepository;

	/**
	 * Returns a complete list of widgets
	 */
	@Override
	public List<Widgets> listWidgets() {

		return widgetRepository.findAll();

	}	
}
