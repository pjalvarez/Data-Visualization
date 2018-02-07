package s4c.microservices.data_visualization.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s4c.microservices.data_visualization.model.entity.Assets;
import s4c.microservices.data_visualization.model.entity.Dashboards;
import s4c.microservices.data_visualization.model.entity.SourceParameters;
import s4c.microservices.data_visualization.model.entity.SourceType;
import s4c.microservices.data_visualization.model.entity.Sources;
import s4c.microservices.data_visualization.model.entity.WidgetProperties;
import s4c.microservices.data_visualization.model.entity.Widgets;
import s4c.microservices.data_visualization.model.repository.AssetsRepository;
import s4c.microservices.data_visualization.model.repository.DashboardsRepository;
import s4c.microservices.data_visualization.model.repository.SourceParametersRepository;
import s4c.microservices.data_visualization.model.repository.SourceTypeRepository;
import s4c.microservices.data_visualization.model.repository.SourcesRepository;
import s4c.microservices.data_visualization.model.repository.WidgetPropertiesRepository;
import s4c.microservices.data_visualization.model.repository.WidgetsRepository;

@Service
public class DashboardsService implements IDashboardsService {

	@Autowired
	private DashboardsRepository dashboardsRepository;
	@Autowired
	private SourceTypeRepository sourceTypeRepository;
	@Autowired
	private WidgetsRepository widgetRepository;
	@Autowired
	private AssetsRepository assetsRepository;
	@Autowired
	SourceParametersRepository spRepository;
	@Autowired
	SourcesRepository sourceRepository;
	@Autowired
	WidgetPropertiesRepository wpRepository;

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
		if (dashboard.getAssets() != null){
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

	private Dashboards getRidOfAssets(Dashboards original, Dashboards dashboard) {
		if (dashboard.getAssets() != null) {
			ArrayList<Assets> newAssets = new ArrayList<Assets>();
			ArrayList<Assets> toRemoveAssets = new ArrayList<Assets>();
			for (Assets asset_original : original.getAssets()) {
				boolean hasAsset = false;
				for (Assets asset : dashboard.getAssets()) {
					if (asset_original.getAsset().toLowerCase().equals(asset.getAsset().toLowerCase())) {
						hasAsset = true;
						break;
					}
				}
				if (hasAsset) {
					newAssets.add(asset_original);
				} else {
					asset_original.setDashboard(null);
					toRemoveAssets.add(asset_original);
				}
			}

			for (Assets asset : dashboard.getAssets()) {
				boolean hasAsset = false;
				for (Assets asset_original : original.getAssets()) {
					if (asset_original.getAsset().toLowerCase().equals(asset.getAsset().toLowerCase())) {
						hasAsset = true;
						break;
					}
				}
				if (!hasAsset) {
					asset.setDashboard(original);
					newAssets.add(asset);
				}
			}

			original.getAssets().removeAll(toRemoveAssets);
			original.setAssets(newAssets);

			// Delete old entities.
			for (Assets asset : toRemoveAssets) {
				this.assetsRepository.delete(asset);
			}
			assetsRepository.flush();

		}
		return original;
	}

	/**
	 * 
	 * @param original
	 *            original
	 * @param dashboard
	 *            dashboard
	 * @return original
	 */
	private Dashboards getRidOfWidgets(Dashboards original, Dashboards dashboard) {
		Consumer<Sources> consumerSource = new Consumer<Sources>() {
			public void accept(Sources source) {
				if (source.getParameters() != null)
					source.getParameters().forEach(parameter -> parameter.setSource(source));
			}
		};

		if (dashboard.getWidgets() != null) {
			ArrayList<Widgets> news = new ArrayList<Widgets>();
			ArrayList<Widgets> toRemove = new ArrayList<Widgets>();

			for (Widgets widget : original.getWidgets()) {
				boolean hasWidget = false;
				for (Widgets new_widget : dashboard.getWidgets()) {
					if (new_widget.getName().toLowerCase().equals(widget.getName().toLowerCase())) {
						hasWidget = true;
						
						if(new_widget.getProperties()!=null){
							List<WidgetProperties> news2 = getRidOfProperties(new_widget.getProperties(),widget.getProperties(),widget);
							widget.setProperties(news2);
						}
						if(new_widget.getSources()!=null){
							List<Sources> news2 = getRidOfSources(new_widget.getSources(),widget.getSources(),widget);
							widget.setSources(news2);
						}
						
						break;
					}
				}
				if (hasWidget) {
					widget.setDashboard(original);
					news.add(widget);
				} else {
					toRemove.add(widget);
				}
			}

			for (Widgets new_widget : dashboard.getWidgets()) {
				boolean hasWidget = false;
				for (Widgets widget : original.getWidgets()) {
					if (new_widget.getName().toLowerCase().equals(widget.getName().toLowerCase())) {
						hasWidget = true;
						break;
					}
				}
				if (!hasWidget) {
					new_widget.setDashboard(original);
					news.add(new_widget);
				}
			}

			// Delete useless entities
			original.getWidgets().removeAll(toRemove);
			if (!toRemove.isEmpty()) {
				for (Widgets w : toRemove) {
					this.widgetRepository.delete(w);
				}
				this.widgetRepository.flush();
			}

			// register new set of widgets
			for (Widgets widget : news) {
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
			}
			original.setWidgets(news);
		}

		return original;
	}

	/**
	 * Sets the correct relation between entities to persist
	 * 
	 * @param original
	 *            original
	 * @param dashboard
	 *            new_data
	 * @return dashboard
	 */
	private Dashboards setRelations(Dashboards original, Dashboards dashboard) {

		original = this.getRidOfAssets(original, dashboard);
		original = this.getRidOfWidgets(original, dashboard);

		return original;

	}

	public Dashboards getDashboardById(String dashboardId) {
		return dashboardsRepository.findOne(Long.parseLong(dashboardId));
	}

	public Boolean updateDashboard(String dashboardId, Dashboards dashboard) {

		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if (original != null) {
			if (dashboard.getName() != null)
				original.setName(dashboard.getName());
			if (dashboard.getOwner() != null)
				original.setOwner(dashboard.getOwner());
			original.set_public(dashboard.is_public());

			dashboard = setRelations(original, dashboard);
			dashboardsRepository.saveAndFlush(dashboard);
			return true;
		}
		return false;
	}

	public Boolean deleteDashboard(String dashboardId) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if (original != null) {
			dashboardsRepository.delete(original);
			return true;
		}

		return false;

	}

	public List<Widgets> getWidgetsInDashboard(String dashboardId) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if (original != null) {
			return (List<Widgets>) original.getWidgets();
		}
		return null;
	}

	@Override
	public List<Widgets> createWidgetInDashboard(String dashboardId, Widgets widget) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if (original != null) {
			if (original.getWidgets() == null)
				original.setWidgets(new ArrayList<Widgets>());

			widget.setDashboard(original);
			original.getWidgets().add(widget);

			original = setRelations(original);

			dashboardsRepository.saveAndFlush(original);
			return (List<Widgets>) original.getWidgets();

		}
		return null;
	}

	public Widgets findWidgetInDashboard(String dashboardId, String widgetId) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		Widgets widget = widgetRepository.findOne(Long.parseLong(widgetId));
		if ((original != null) && (widget != null)) {
			if (original.getWidgets().contains(widget)) {
				return widget;
			}
		}
		return null;
	}
	
	
	
	

	public Boolean updateWidgetInDashboard(String dashboardId, String widgetId, Widgets newWidget) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		Widgets widget = widgetRepository.findOne(Long.parseLong(widgetId));
		if ((original != null) && (widget != null)) {
			if (original.getWidgets().contains(widget)) {
				if(newWidget.getName()!=null)
					widget.setName(newWidget.getName());
				
				if (newWidget.getType() != null && !newWidget.getType().name.isEmpty()) {
					SourceType type = sourceTypeRepository.findOneByName(newWidget.getType().getName());
					if (type != null) {
						widget.setType(type);
					} else {
						widget.setType(null);
					}
				}
				
				if(newWidget.getProperties()!=null){
					List<WidgetProperties> news = getRidOfProperties(newWidget.getProperties(),widget.getProperties(),widget);
					widget.setProperties(news);
				}
				if(newWidget.getSources()!=null){
					List<Sources> news = getRidOfSources(newWidget.getSources(),widget.getSources(),widget);
					widget.setSources(news);
				}
				
				original = setRelations(original);
				dashboardsRepository.saveAndFlush(original);
				return true;
			}
		}
		return false;

	}

	
	/**
	 * 
	 * @param news
	 * @param original
	 * @return
	 */
	private List<SourceParameters> getRidOfSourcesParameters(Collection<SourceParameters> news, Collection<SourceParameters> original, Sources original_source) {
		List<SourceParameters> np = new ArrayList<SourceParameters>();
		List<SourceParameters> toRemove = new ArrayList<SourceParameters>();
		for(SourceParameters wp : original){
			boolean has = false;
			for(SourceParameters nw : news){
				if(nw.getName().toLowerCase().equals(wp.getName().toLowerCase())){
					has = true;
					if(!wp.getOperator().equals(nw.getOperator())){
						wp.setOperator(nw.getOperator());
					}
					if(!wp.getValue().equals(nw.getValue())){
						wp.setValue(nw.getValue());
					}
					break;
				}
			}
			if(has){
				np.add(wp);
			} else {
				toRemove.add(wp);
			}
		}
		
		for(SourceParameters nw : news){
			boolean has = false;
			for(SourceParameters wp : original){
				if(nw.getName().toLowerCase().equals(wp.getName().toLowerCase())){
					has = true;
					break;
				}
			}
			if(!has){
				nw.setSource(original_source);
				np.add(nw);
			}
		}
		
		//delete useless entities
		if(!toRemove.isEmpty()){
			for(SourceParameters nw : toRemove){
				nw.getSource().getParameters().remove(nw);
				this.spRepository.delete(nw);
			}
			spRepository.flush();
		}
		return np;		
	}
	
	
	/**
	 * 
	 * @param news
	 * @param original
	 * @return
	 */
	private List<Sources> getRidOfSources(Collection<Sources> news, Collection<Sources> original, Widgets widget) {
		List<Sources> np = new ArrayList<Sources>();
		List<Sources> toRemove = new ArrayList<Sources>();
		for(Sources wp : original){
			boolean has = false;
			for(Sources nw : news){
				if(nw.getUrl().toLowerCase().equals(wp.getUrl().toLowerCase())){
					has = true;
					wp.setParameters(this.getRidOfSourcesParameters(nw.getParameters(), wp.getParameters(),wp));
					break;
				}
			}
			if(has){
				np.add(wp);
			} else {
				toRemove.add(wp);
			}
		}
		
		for(Sources nw : news){
			boolean has = false;
			for(Sources wp : original){
				if(nw.getUrl().toLowerCase().equals(wp.getUrl().toLowerCase())){
					has = true;
					break;
				}
			}
			if(!has){
				nw.setWidget(widget);
				np.add(nw);
			}
		}
		
		//delete useless entities
		if(!toRemove.isEmpty()){
			for(Sources nw : toRemove){	
				System.out.println("x:"+nw.getUrl());
				nw.getWidget().getSources().remove(nw);				
				this.sourceRepository.delete(nw);
			}
			sourceRepository.flush();
		}
		return np;	
	}
	
	private List<WidgetProperties> getRidOfProperties(Collection<WidgetProperties> news, Collection<WidgetProperties> original, Widgets original_widget) {

		List<WidgetProperties> np = new ArrayList<WidgetProperties>();
		List<WidgetProperties> toRemove = new ArrayList<WidgetProperties>();
		for(WidgetProperties wp : original){
			boolean has = false;
			for(WidgetProperties nw : news){
				if(nw.getName().toLowerCase().equals(wp.getName().toLowerCase())){
					has = true;
					if(!nw.getValue().equals(wp.getValue())){
						wp.setValue(nw.getValue());
					}
					break;
				}
			}
			if(has){
				np.add(wp);
			} else {
				toRemove.add(wp);
			}
		}
		
		for(WidgetProperties nw : news){
			boolean has = false;
			for(WidgetProperties wp : original){
				if(nw.getName().toLowerCase().equals(wp.getName().toLowerCase())){
					has = true;
					break;
				}
			}
			if(!has){	
				nw.setWidget(original_widget);
				np.add(nw);
			}
		}
		
		//delete useless entities
		if(!toRemove.isEmpty()){
			for(WidgetProperties nw : toRemove){
				nw.getWidget().getProperties().remove(nw);				
				this.wpRepository.delete(nw);
			}
			wpRepository.flush();
		}
		return np;
		
	}

	public Boolean deleteWidgetInDashboard(String dashboardId, String widgetId) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		Widgets widget = widgetRepository.findOne(Long.parseLong(widgetId));
		if ((original != null) && (widget != null)) {
			if (original.getWidgets().contains(widget)) {
				original.getWidgets().remove(widget);
				widget.setDashboard(null);
				widgetRepository.delete(widget);
				return true;
			}
		}
		return false;
	}

}
