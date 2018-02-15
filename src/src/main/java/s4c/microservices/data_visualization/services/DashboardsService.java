package s4c.microservices.data_visualization.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s4c.microservices.data_visualization.model.Assets;
import s4c.microservices.data_visualization.model.entity.Actions;
import s4c.microservices.data_visualization.model.entity.Columns;
import s4c.microservices.data_visualization.model.entity.Dashboards;
import s4c.microservices.data_visualization.model.entity.Properties;
import s4c.microservices.data_visualization.model.entity.PropertyPages;
import s4c.microservices.data_visualization.model.entity.Rows;
import s4c.microservices.data_visualization.model.entity.SourceParameters;
import s4c.microservices.data_visualization.model.entity.SourceType;
import s4c.microservices.data_visualization.model.entity.Sources;
import s4c.microservices.data_visualization.model.entity.Tags;
import s4c.microservices.data_visualization.model.entity.WidgetProperties;
import s4c.microservices.data_visualization.model.entity.Widgets;
import s4c.microservices.data_visualization.model.repository.ActionsRepository;

import s4c.microservices.data_visualization.model.repository.ColumnsRepository;
import s4c.microservices.data_visualization.model.repository.DashboardsRepository;
import s4c.microservices.data_visualization.model.repository.PropertiesRepository;
import s4c.microservices.data_visualization.model.repository.PropertyPagesRepository;
import s4c.microservices.data_visualization.model.repository.RowsRepository;
import s4c.microservices.data_visualization.model.repository.SourceParametersRepository;
import s4c.microservices.data_visualization.model.repository.SourceTypeRepository;
import s4c.microservices.data_visualization.model.repository.SourcesRepository;
import s4c.microservices.data_visualization.model.repository.TagsRepository;
import s4c.microservices.data_visualization.model.repository.WidgetPropertiesRepository;
import s4c.microservices.data_visualization.model.repository.WidgetsRepository;
import s4c.microservices.data_visualization.services.external.UserManagementService;

@Service
public class DashboardsService implements IDashboardsService {

	@Autowired
	private UserManagementService userManagementService;
	
	@Autowired
	private DashboardsRepository dashboardsRepository;
	@Autowired
	private SourceTypeRepository sourceTypeRepository;
	@Autowired
	private WidgetsRepository widgetRepository;
	@Autowired
	SourceParametersRepository spRepository;
	@Autowired
	SourcesRepository sourceRepository;
	@Autowired
	WidgetPropertiesRepository wpRepository;
	@Autowired
	PropertiesRepository propertiesRepository;
	@Autowired
	PropertyPagesRepository ppRepository;
	@Autowired
	TagsRepository tagsRepository;
	@Autowired
	ActionsRepository actionsRepository;

	@Autowired
	RowsRepository rowsRepository;
	@Autowired
	ColumnsRepository columnsRepository;

	/**
	 * Returns a complete list of dashboards
	 */
	@Override
	public List<Dashboards> listDashboards() {

		
		List<Dashboards> entities = dashboardsRepository.findAll();
		for(Dashboards d : entities){		
			List<Assets> assetsList =userManagementService.getAssetsByUser(d.getOwner());
			d.setAssets(assetsList);
		}
				
		return entities;

	}

	/**
	 * Adds a new Dashboard
	 */
	@Override
	public Dashboards addDashboard(Dashboards dashboard) {
		if (dashboard != null) {
			dashboard = setRelations(dashboard, true);
			return dashboardsRepository.saveAndFlush(dashboard);
		}
		return null;
	}

	private Sources getRidOfSourceParameters(Sources source) {
		if (source.getParameters() != null) {
			ArrayList<SourceParameters> toAdd = new ArrayList<SourceParameters>();
			ArrayList<SourceParameters> toRemove = new ArrayList<SourceParameters>();

			for (SourceParameters t : source.getParameters()) {
				t.setSource(source);
				if (t.getId() != null) {
					SourceParameters sp = spRepository.findOne(t.getId());
					if (sp != null) {
						if (t.getName() != null && !sp.getName().equals(t.getName()))
							sp.setName(t.getName());
						if (t.getOperator() != null && !sp.getOperator().equals(t.getOperator()))
							sp.setOperator(t.getOperator());
						if (t.getValue() != null && !sp.getValue().equals(t.getValue()))
							sp.setValue(t.getValue());

						sp.setSource(source);
						t.setSource(null);
						toAdd.add(sp);
						toRemove.add(t);

					} else {
						t.setId(null);
					}

				}
			}

			
			for (SourceParameters s : toAdd) {
			
				source.getParameters().add(s);
			}
			
			for (SourceParameters s : toRemove) {
			
				source.getParameters().remove(s);
			}
		}
		return source;

	}

	/**
	 * 
	 * @param widget
	 * @return
	 */
	private Widgets getRidOfSources(Widgets widget, boolean removeIfNotPresent) {
		if (widget.getSources() != null) {
			ArrayList<Sources> toAdd = new ArrayList<Sources>();
			ArrayList<Sources> toRemove = new ArrayList<Sources>();
			for (Sources source : widget.getSources()) {

				source.setWidget(widget);
				source = getRidOfSourceParameters(source);
				
				
				

				if (source.getId() != null) {
					Sources original = sourceRepository.findOne(source.getId());

					if (original != null) {
						if (removeIfNotPresent) {
							original = removeUselessParameters(original, source);
						}

						original.setWidget(widget);
						if (source.getUrl() != null && !source.getUrl().equals(original.getUrl())) {
							original.setUrl(source.getUrl());
						}

						if (source.getParameters() != null)
							original.getParameters().addAll(source.getParameters());

						toRemove.add(source);
						toAdd.add(original);
					} else {
						// if doesnt found, will create it!
						source.setId(null);
					}
				}
			}

			for (Sources s : toAdd)
				widget.getSources().add(s);
			for (Sources s : toRemove)
				widget.getSources().remove(s);

		}
		return widget;
	}

	private Widgets getRidOfPropertyPages(Widgets widget, boolean removeIfNotPresent) {
		if (widget.getPropertyPages() != null) {
			ArrayList<PropertyPages> toAdd = new ArrayList<PropertyPages>();
			ArrayList<PropertyPages> toRemove = new ArrayList<PropertyPages>();
			for (PropertyPages source : widget.getPropertyPages()) {

				source.setWidget(widget);
				source = getRidOfProperties(source);

				if (source.getId() != null) {
					PropertyPages original = ppRepository.findOne(source.getId());

					if (original != null) {
						if (removeIfNotPresent)
							original = removeUselessProperties(original, source);

						original.setWidget(widget);
						if (source.getDisplayName() != null
								&& !source.getDisplayName().equals(original.getDisplayName())) {
							original.setDisplayName(source.getDisplayName());
						}

						if (source.getProperties() != null)
							original.getProperties().addAll(source.getProperties());

						toRemove.add(source);
						toAdd.add(original);
					} else {
						// if doesnt found, will create it!
						source.setId(null);
					}
				}
			}

			for (PropertyPages s : toAdd)
				widget.getPropertyPages().add(s);
			for (PropertyPages s : toRemove)
				widget.getPropertyPages().remove(s);

		}
		return widget;
	}

	/**
	 * remove useless parameters before object changes
	 * 
	 * @param original
	 * @param source
	 * @return
	 */
	private Sources removeUselessParameters(Sources original, Sources source) {
		Collection<SourceParameters> ss = new ArrayList<SourceParameters>();
		if (original.getParameters() != null) {
			for (SourceParameters s : original.getParameters()) {
				boolean found = false;
				if (source.getParameters() != null) {
					for (SourceParameters s2 : source.getParameters()) {
						if (s2.getId() != null && s.getId().equals(s2.getId())) {
							found = true;
							break;
						}
					}
				}
				if (!found) {
					ss.add(s);
				}
			}
		}

		if (!ss.isEmpty()) {
			for (SourceParameters s : ss) {
				original.removeParameter(s);
			}
		}
		return original;
	}

	/**
	 * remove useless parameters before object changes
	 * 
	 * @param original
	 * @param source
	 * @return
	 */
	private PropertyPages removeUselessProperties(PropertyPages original, PropertyPages source) {
		Collection<Properties> toRemove = new ArrayList<Properties>();
		if (original.getProperties() != null) {
			for (Properties s : original.getProperties()) {
				boolean found = false;
				if (source.getProperties() != null) {
					for (Properties s2 : source.getProperties()) {
						if (s2.getId() != null && s.getId().equals(s2.getId())) {
							found = true;
							break;
						}
					}
				}
				if (!found) {
					toRemove.add(s);
				}
			}
		}

		if (!toRemove.isEmpty()) {
			for (Properties s : toRemove) {
				original.removeProperties(s);
			}
		}
		return original;
	}

	/**
	 * 
	 * @param widget
	 * @return
	 */
	private Widgets getRidOfTags(Widgets widget) {
		if (widget.getTags() != null) {
			ArrayList<Tags> toAdd = new ArrayList<Tags>();
			ArrayList<Tags> toRemove = new ArrayList<Tags>();
			for (Tags source : widget.getTags()) {

				source.setWidget(widget);

				if (source.getId() != null) {
					Tags original = tagsRepository.findOne(source.getId());

					if (original != null) {
						original.setWidget(widget);
						if (source.getFacet() != null && !source.getFacet().equals(original.getFacet())) {
							original.setFacet(source.getFacet());
						}
						if (source.getName() != null && !source.getName().equals(original.getName())) {
							original.setName(source.getName());
						}
						toRemove.add(source);
						toAdd.add(original);

					} else {
						// if doesnt found, will create it!
						source.setId(null);
					}
				}
			}

			for (Tags s : toAdd)
				widget.getTags().add(s);
			for (Tags s : toRemove)
				widget.getTags().remove(s);

		}
		return widget;

	}

	/**
	 * 
	 * @param widget
	 * @return
	 */
	private Widgets getRidOfWidgetProperties(Widgets widget) {
		if (widget.getProperties() != null) {
			ArrayList<WidgetProperties> toAdd = new ArrayList<WidgetProperties>();
			ArrayList<WidgetProperties> toRemove = new ArrayList<WidgetProperties>();
			for (WidgetProperties source : widget.getProperties()) {

				source.setWidget(widget);

				if (source.getId() != null) {
					WidgetProperties original = wpRepository.findOne(source.getId());

					if (original != null) {
						original.setWidget(widget);

						if (source.getValue() != null && !source.getValue().equals(original.getValue())) {
							original.setValue(source.getValue());
						}
						if (source.getName() != null && !source.getName().equals(original.getName())) {
							original.setName(source.getName());
						}
						toRemove.add(source);
						toAdd.add(original);

					} else {
						// if doesnt found, will create it!
						source.setId(null);
					}
				}
			}

			for (WidgetProperties s : toAdd)
				widget.getProperties().add(s);
			for (WidgetProperties s : toRemove)
				widget.getProperties().remove(s);

		}
		return widget;

	}

	/**
	 * 
	 * @param propertyPage
	 * @return
	 */
	private PropertyPages getRidOfProperties(PropertyPages propertyPage) {
		if (propertyPage.getProperties() != null) {
			ArrayList<Properties> toAdd = new ArrayList<Properties>();
			ArrayList<Properties> toRemove = new ArrayList<Properties>();
			for (Properties source : propertyPage.getProperties()) {

				source.setPropertyPages(propertyPage);

				if (source.getId() != null) {
					Properties original = propertiesRepository.findOne(source.getId());

					if (original != null) {
						original.setPropertyPages(propertyPage);

						if (source.get_controlType() != null
								&& !source.get_controlType().equals(original.get_controlType())) {
							original.set_controlType(source.get_controlType());
						}
						if (source.get_key() != null && !source.get_key().equals(original.get_key())) {
							original.set_key(source.get_key());
						}
						if (source.get_label() != null && !source.get_label().equals(original.get_label())) {
							original.set_label(source.get_label());
						}
						if (source.get_order() != null && !source.get_order().equals(original.get_order())) {
							original.set_order(source.get_order());
						}
						if (source.get_value() != null && !source.get_value().equals(original.get_value())) {
							original.set_value(source.get_value());
						}
						if (source.get_required() != null && !source.get_required().equals(original.get_required())) {
							original.set_required(source.get_required());
						}
						toRemove.add(source);
						toAdd.add(original);

					} else {
						// if doesnt found, will create it!
						source.setId(null);
					}
				}
			}

			for (Properties s : toAdd)
				propertyPage.getProperties().add(s);
			for (Properties s : toRemove)
				propertyPage.getProperties().remove(s);

		}
		return propertyPage;

	}

	/**
	 * 
	 * @param widget
	 * @return
	 */
	private Widgets getRidOfActions(Widgets widget) {
		if (widget.getActions() != null) {
			ArrayList<Actions> toAdd = new ArrayList<Actions>();
			ArrayList<Actions> toRemove = new ArrayList<Actions>();
			for (Actions source : widget.getActions()) {

				source.addWidget(widget);

				if (source.getId() != null) {
					Actions original = actionsRepository.findOne(source.getId());

					if (original != null) {
						original.addWidget(widget);
						if (source.getName() != null && !source.getName().equals(original.getName())) {
							original.setName(source.getName());
						}
						toRemove.add(source);
						toAdd.add(original);

					} else {
						// if doesnt found, will create it!
						source.setId(null);
					}
				} else {
					if (source.getName() != null && !source.getName().isEmpty()) {
						List<Actions> action = actionsRepository.findByName(source.getName());
						if (action != null && !action.isEmpty()) {
							toRemove.add(source);
							toAdd.add(action.get(0));

						} else {

						}
					} else {

					}

				}
			}

			for (Actions s : toAdd)
				widget.getActions().add(s);
			for (Actions s : toRemove)
				widget.getActions().remove(s);

		}
		return widget;

	}

	/**
	 * Sets the correct relation between entities to persist
	 * 
	 * @param dashboard
	 *            dashboard
	 * @return dashboard
	 */
	private Dashboards setRelations(Dashboards dashboard, boolean removeIfNotPresent) {

		if (dashboard.getRows() != null) {
			ArrayList<Rows> toRemove = new ArrayList<Rows>();
			for (Rows row : dashboard.getRows()) {
				if (row.getColumns() != null && !row.getColumns().isEmpty()) {
					row.setDashboard(dashboard);
					row = getRidOfRows(row, removeIfNotPresent);
				} else {
					toRemove.add(row);
				}
			}
		}
		return dashboard;

	}

	/**
	 * 
	 * @param row
	 * @return
	 */
	private Rows getRidOfRows(Rows row, boolean removeIfNotPresent) {

		if (row.getColumns() != null) {
			// ArrayList<Columns> toRemove = new ArrayList<Columns>();
			for (Columns column : row.getColumns()) {
				if (column.getWidgets() != null && !column.getWidgets().isEmpty()) {
					column.setRow(row);
					// column.setWidgets(null);
					column = getRidOfWidgets(column, removeIfNotPresent);
				}
			}
		}
		return row;
	}

	/**
	 * 
	 * @param column
	 * @return
	 */
	private Columns getRidOfWidgets(Columns column, boolean removeIfNotPresent) {
		if (column.getWidgets() != null) {
			ArrayList<Widgets> toAdd = new ArrayList<Widgets>();
			ArrayList<Widgets> toRemove = new ArrayList<Widgets>();
			for (Widgets widget : column.getWidgets()) {
				boolean createNewWidget = false;
				if (widget.getId() == null) {
					createNewWidget = true;
				} else {
					Widgets w = this.widgetRepository.findOne(widget.getId());
					if (w != null) {
						w = this.updateWidget(w, widget, column, removeIfNotPresent);

						toAdd.add(w);
						toRemove.add(widget);

					} else {
						widget.setId(null);
						createNewWidget = true;
					}
				}

				if (createNewWidget) {
					widget = creaNuevoWidget(widget, column, removeIfNotPresent);
				}
			}
			for (Widgets w : toRemove)
				column.getWidgets().remove(w);
			for (Widgets w : toAdd)
				column.getWidgets().add(w);
		}
		return column;
	}

	/**
	 * 
	 * @param w
	 * @param widget
	 * @param column
	 * @param removeIfNotPresent
	 * @return
	 */
	public Widgets updateWidget(Widgets w, Widgets widget, Columns column, boolean removeIfNotPresent) {
		if (removeIfNotPresent) {
			w = this.removeUselessSources(w, widget);
			w = this.removeUselessTags(w, widget);
			w = this.removeUselessWidgetProperties(w, widget);
			w = this.removeUselessPropertyPages(w, widget);
		}

		if (!w.getColumns().contains(column))
			w.addColumn(column);

		if (widget.getDescription() != null && w.getDescription()!=null && !w.getDescription().equals(widget.getDescription()))
			w.setDescription(widget.getDescription());
		if (widget.getIcon() != null && w.getIcon()!=null && !w.getIcon().equals(widget.getIcon()))
			w.setIcon(widget.getIcon());
		if (widget.getName() != null && w.getName()!=null && !w.getName().equals(widget.getName()))
			w.setName(widget.getName());
		
		if (widget.getType() != null && widget.getType().getName() != null && !widget.getType().getName().isEmpty()) {
			if ((w.getType()==null) || (w.getType() != null && !w.getType().equals(widget.getType()))) {
				SourceType type = sourceTypeRepository.findOneByName(widget.getType().getName());
				if (type != null) {
					w.setType(type);
				} else {
					w.setType(null);
				}
			}
		} else
			w.setType(null);

		w.getSources().addAll(widget.getSources());		
		w = this.getRidOfSources(w, removeIfNotPresent);

		w.getTags().addAll(widget.getTags());
		w = this.getRidOfTags(w);

		w.setActions(widget.getActions());
		w = this.getRidOfActions(w);

		w.getProperties().addAll(widget.getProperties());
		w = this.getRidOfWidgetProperties(w);

		w.getPropertyPages().addAll(widget.getPropertyPages());
		w = this.getRidOfPropertyPages(w, removeIfNotPresent);
		return w;
	}

	/**
	 * 
	 * @param widget
	 * @return
	 */
	private Widgets creaNuevoWidget(Widgets widget, Columns column, boolean removeIfNotPresent) {
		widget.addColumn(column);
		widget = this.getRidOfSources(widget, removeIfNotPresent);
		widget = this.getRidOfTags(widget);
		widget = this.getRidOfActions(widget);
		widget = this.getRidOfWidgetProperties(widget);
		widget = this.getRidOfPropertyPages(widget, removeIfNotPresent);

		if (widget.getType() != null && !widget.getType().name.isEmpty()) {
			SourceType type = sourceTypeRepository.findOneByName(widget.getType().getName());
			if (type != null) {
				widget.setType(type);
			} else {
				widget.setType(null);
			}
		}
		return widget;
	}

	/**
	 * 
	 * @param original
	 * @param source
	 * @return
	 */
	private Widgets removeUselessSources(Widgets original, Widgets source) {
		ArrayList<Sources> toRemove2 = new ArrayList<Sources>();

		if (original.getSources() != null) {
			for (Sources s : original.getSources()) {
				boolean found = false;
				if (source.getSources() != null) {
					for (Sources s2 : source.getSources()) {
						if (s2.getId() != null && s2.getId().equals(s.getId())) {
							found = true;
							break;
						}
					}
				}
				if (!found)
					toRemove2.add(s);
			}
		}

		if (!toRemove2.isEmpty()) {
			for (Sources s : toRemove2) {
				original.getSources().remove(s);
			}
		}
		return original;
	}

	private Widgets removeUselessTags(Widgets original, Widgets source) {
		ArrayList<Tags> toRemove2 = new ArrayList<Tags>();
		if (original.getTags() != null) {
			for (Tags s : original.getTags()) {
				boolean found = false;
				if (source.getTags() != null) {
					for (Tags s2 : source.getTags()) {
						if (s2.getId() != null && s2.getId().equals(s.getId())) {
							found = true;
							break;
						}
					}
				}
				if (!found) {
					toRemove2.add(s);
				}
			}
		}
		if (!toRemove2.isEmpty()) {
			for (Tags s : toRemove2) {
				original.getTags().remove(s);
				// tagsRepository.delete(s);
			}
			// this.tagsRepository.flush();
		}
		return original;
	}

	private Widgets removeUselessPropertyPages(Widgets original, Widgets source) {
		ArrayList<PropertyPages> toRemove2 = new ArrayList<PropertyPages>();
		if (original.getPropertyPages() != null) {
			for (PropertyPages s : original.getPropertyPages()) {
				boolean found = false;
				if (source.getPropertyPages() != null) {
					for (PropertyPages s2 : source.getPropertyPages()) {
						if (s2.getId() != null && s2.getId().equals(s.getId())) {
							found = true;
							break;
						}
					}
				}
				if (!found) {
					toRemove2.add(s);
				}
			}
		}
		if (!toRemove2.isEmpty()) {
			for (PropertyPages s : toRemove2) {
				original.getPropertyPages().remove(s);
				// ppRepository.delete(s);
			}
			// this.ppRepository.flush();
		}
		return original;
	}

	private Widgets removeUselessWidgetProperties(Widgets original, Widgets source) {
		ArrayList<WidgetProperties> toRemove2 = new ArrayList<WidgetProperties>();
		if (original.getProperties() != null) {
			for (WidgetProperties s : original.getProperties()) {
				boolean found = false;
				if (source.getProperties() != null) {
					for (WidgetProperties s2 : source.getProperties()) {
						if (s2.getId() != null && s2.getId().equals(s.getId())) {
							found = true;
							break;
						}
					}
				}
				if (!found) {
					toRemove2.add(s);
				}
			}
		}
		if (!toRemove2.isEmpty()) {
			for (WidgetProperties s : toRemove2) {
				original.getProperties().remove(s);
				// wpRepository.delete(s);
			}
			// this.wpRepository.flush();
		}
		return original;
	}

	/**
	 * 
	 */
	public Dashboards getDashboardById(String dashboardId) {
		
		Dashboards dashboard = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if(dashboard != null){
			List<Assets> assetsList =userManagementService.getAssetsByUser(dashboard.getOwner());
			dashboard.setAssets(assetsList);
		}
		
		return dashboard;
	}

	/**
	 * 
	 */
	public Boolean updateDashboard(String dashboardId, Dashboards dashboard) {

		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if (original != null) {

			original.set_public(dashboard.is_public());
			original = this.getRidOfRows(dashboard, original);
			
			if (dashboard.getName() != null && !dashboard.getName().equals(original.getName()))
				original.setName(dashboard.getName());
			if (dashboard.getOwner() != null && !dashboard.getOwner().equals(original.getOwner()))
				original.setOwner(dashboard.getOwner());
			if (dashboard.getStructure() != null && !dashboard.getStructure().equals(original.getStructure()))
				original.setStructure(dashboard.getStructure());

			original = setRelations(original, true);

			dashboardsRepository.saveAndFlush(original);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param original
	 * @param dashboard
	 * @return
	 */
	private Dashboards getRidOfRows(Dashboards dashboard, Dashboards objOriginal) {

		if (dashboard.getRows() != null && !dashboard.getRows().isEmpty()) {
			ArrayList<Rows> toAdd = new ArrayList<Rows>();
			ArrayList<Rows> toRemove = new ArrayList<Rows>();
			for (Rows source : dashboard.getRows()) {

				source.setDashboard(dashboard);
								
				source = getRidOfColumns(source);
								

				if (source.getId() != null) {
					Rows original = rowsRepository.findOne(source.getId());

					if (original != null) {
						original = removeUselessColumns(original, source);
						original.setDashboard(dashboard);

						toRemove.add(source);
						toAdd.add(original);
					} else {
						// if doesnt found, will create it!
						source.setId(null);
					}
				}
			}

			for (Rows s : toAdd)
				dashboard.getRows().add(s);
			for (Rows s : toRemove)
				dashboard.getRows().remove(s);

		} else {
			// delete all rows from original object
			Dashboards original = dashboardsRepository.findOne(objOriginal.getId());
			ArrayList<Rows> rtemp = new ArrayList<Rows>();
			for (Rows r : original.getRows()) {
				ArrayList<Columns> ctemp = new ArrayList<Columns>();
				for (Columns c : r.getColumns()) {
					c.getWidgets().forEach(w -> w.removeColumn(c));
					ctemp.add(c);
				}

				ctemp.forEach(c -> c.getRow().removeColumn(c));
				rtemp.add(r);
			}

			for (Rows r : rtemp) {
				r.setDashboard(null);
				original.removeRow(r);
			}

			return original;

		}
		
		
		
		
		

		return dashboard;
	}

	private Rows removeUselessColumns(Rows original, Rows source) {

		Collection<Columns> toRemove = new ArrayList<Columns>();
		Collection<Columns> toAdd = new ArrayList<Columns>();

		if (original.getColumns() != null && !original.getColumns().isEmpty()) {
			for (Columns s : original.getColumns()) {
				boolean found = false;
				if (source.getColumns() != null) {
					for (Columns s2 : source.getColumns()) {
						if (s2.getId() != null && s.getId().equals(s2.getId())) {
							found = true;
							break;
						} else {
							toAdd.add(s2);
						}
					}
				}
				if (!found) {
					toRemove.add(s);
				}
			}
		} else {
			if (source.getColumns() != null) {
				for (Columns s2 : source.getColumns()) {
					toAdd.add(s2);
				}
			}
		}

		if (!toAdd.isEmpty()) {
			for (Columns s : toAdd) {
				original.addColumn(s);
			}
		}

		if (!toRemove.isEmpty()) {
			for (Columns s : toRemove) {
				original.removeColumn(s);
			}
		}
		return original;
	}

	private Rows getRidOfColumns(Rows row) {
		if (row.getColumns() != null) {
			ArrayList<Columns> toAdd = new ArrayList<Columns>();
			ArrayList<Columns> toRemove = new ArrayList<Columns>();
			for (Columns source : row.getColumns()) {
				source.setRow(row);				

				if (source.getId() != null) {
					Columns original = columnsRepository.findOne(source.getId());

					if (original != null) {
						if (source.getStyleClass() != null
								&& !source.getStyleClass().equals(original.getStyleClass())) {
							original.setStyleClass(source.getStyleClass());
						}
						
						original.setWidgets(source.getWidgets());
						original.setRow(row);
						toRemove.add(source);
						toAdd.add(original);
						
					} else {
						// if doesnt found, will create it!
						source.setId(null);
					}
				}
			}

			for (Columns s : toAdd) {
				row.getColumns().add(s);
			}
			for (Columns s : toRemove){
				row.getColumns().remove(s);
			}
		}

		return row;
	}

	public Boolean deleteDashboard(String dashboardId) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if (original != null) {
			for (Rows row : original.getRows()) {
				for (Columns c : row.getColumns()) {
					c.getWidgets().forEach(w -> w.removeColumn(c));
				}
			}
			dashboardsRepository.delete(original);
			return true;
		}

		return false;

	}

	public List<Widgets> getWidgetsInDashboard(String dashboardId) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if (original != null) {

			List<Widgets> widgets = new ArrayList<Widgets>();
			for (Rows row : original.getRows()) {
				for (Columns column : row.getColumns()) {
					if (column.getWidgets() != null)
						widgets.addAll(column.getWidgets());
				}
			}

			return widgets;
		}
		return null;
	}

	@Override
	public List<Widgets> createWidgetInDashboard(String dashboardId, Widgets widget, String rowId, String colId) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		if (original != null) {

			Rows row = rowsRepository.findOne(Long.parseLong(rowId));
			if (row != null) {
				Columns column = columnsRepository.findOne(Long.parseLong(rowId));
				if (column != null && row.getColumns().contains(column)) {
					if (column.getWidgets() == null)
						column.setWidgets(new ArrayList<Widgets>());

					column.addWidget(creaNuevoWidget(widget, column, true));

					widgetRepository.saveAndFlush(widget);

					return (List<Widgets>) column.getWidgets();

				}
			}
		}
		return null;
	}

	/**
	 * 
	 */
	public Widgets findWidgetInDashboard(String dashboardId, String widgetId) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		Widgets widget = widgetRepository.findOne(Long.parseLong(widgetId));
		if ((original != null) && (widget != null)) {
			if (isWidgetInDashboard(original, widget)) {
				return widget;
			}

		}
		return null;
	}

	/**
	 * 
	 * @param dashboard
	 * @param widget
	 * @return
	 */
	private boolean isWidgetInDashboard(Dashboards dashboard, Widgets widget) {
		boolean is = false;
		for (Rows row : dashboard.getRows()) {
			for (Columns col : row.getColumns()) {
				if (col.getWidgets().contains(widget)) {
					is = true;

				}
			}
		}
		return is;
	}

	private Columns getColumnWidgetInDashboard(Dashboards dashboard, Widgets widget) {
		Columns column = null;
		for (Rows row : dashboard.getRows()) {
			for (Columns col : row.getColumns()) {
				if (col.getWidgets().contains(widget)) {
					column = col;
					break;
				}
			}
		}
		return column;
	}

	public Boolean updateWidgetInDashboard(String dashboardId, String widgetId, Widgets newWidget) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		Widgets widget = widgetRepository.findOne(Long.parseLong(widgetId));
		if ((original != null) && (widget != null)) {

			if (isWidgetInDashboard(original, widget)) {
				Columns column = getColumnWidgetInDashboard(original, widget);
				widget = this.updateWidget(widget, newWidget, column, true);

				widgetRepository.saveAndFlush(widget);

				return true;
			}

		}
		return false;

	}

	public Boolean deleteWidgetInDashboard(String dashboardId, String widgetId) {
		Dashboards original = dashboardsRepository.findOne(Long.parseLong(dashboardId));
		Widgets widget = widgetRepository.findOne(Long.parseLong(widgetId));
		if ((original != null) && (widget != null)) {
			Columns column = getColumnWidgetInDashboard(original, widget);
			if (column != null) {
				column.getWidgets().remove(widget);
				widget.getColumns().remove(column);
				widgetRepository.delete(widget);
				return true;

			}
		}
		return false;
	}

}
