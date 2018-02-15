package s4c.microservices.data_visualization.model.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "widgets")
public class Widgets implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String name;

	@OneToMany(mappedBy="widget", cascade = CascadeType.ALL, orphanRemoval=true)	
	private Collection<Sources> sources = new ArrayList<Sources>();
	
	@OneToOne
	private SourceType type;
	
	@OneToMany(mappedBy="widget", cascade = CascadeType.ALL, orphanRemoval=true)
	private Collection<WidgetProperties> properties =new ArrayList<WidgetProperties>();

	@ManyToMany(cascade = {CascadeType.ALL})
	@JsonIgnore
	private Collection<Columns> columns;
	
	
	private String description;
	private String icon;	
	
	@OneToMany(mappedBy="widget", cascade = CascadeType.ALL, orphanRemoval=true)
	private Collection<Tags> tags =new ArrayList<Tags>();
	
	@OneToMany(mappedBy="widget", cascade = CascadeType.ALL, orphanRemoval=true)
	private Collection<PropertyPages> propertyPages = new ArrayList<PropertyPages>();
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private Collection<Actions> actions  = new ArrayList<Actions>();
	
    /**
	 * @return the columns
	 */
	public Collection<Columns> getColumns() {
		return columns;
	}
	/**
	 * @param columns the columns to set
	 */
	public void setColumns(Collection<Columns> columns) {
		this.columns = columns;
	}
	
	
	public void addColumn(Columns column){
		if(this.columns==null)
			this.columns = new ArrayList<Columns>();
		
		this.columns.add(column);
	}
	
	public Widgets() {}
    public Widgets( String name) {    	
    	this.name = name;
    }

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Sources> getSources() {
		return sources;
	}

	public void setSources(Collection<Sources> sources) {
		this.sources = sources;
	}

	public SourceType getType() {
		return type;
	}

	public void setType(SourceType type) {
		this.type = type;
	}

	public Collection<WidgetProperties> getProperties() {
		return properties;
	}

	public void setProperties(Collection<WidgetProperties> properties) {
		this.properties = properties;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @return the tags
	 */
	public Collection<Tags> getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(Collection<Tags> tags) {
		this.tags = tags;
	}
	/**
	 * @return the propertyPages
	 */
	public Collection<PropertyPages> getPropertyPages() {
		return propertyPages;
	}
	/**
	 * @param propertyPages the propertyPages to set
	 */
	public void setPropertyPages(Collection<PropertyPages> propertyPages) {
		this.propertyPages = propertyPages;
	}
	/**
	 * @return the actions
	 */
	public Collection<Actions> getActions() {
		return actions;
	}
	/**
	 * @param actions the actions to set
	 */
	public void setActions(Collection<Actions> actions) {
		this.actions = actions;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	public void removeColumn(Columns col) {
		if(this.columns!=null)
			this.columns.remove(col);
		
	}
	
	

}