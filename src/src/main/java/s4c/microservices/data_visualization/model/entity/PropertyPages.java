package s4c.microservices.data_visualization.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "property_pages")
public class PropertyPages implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String displayName;
	
	@OneToMany(mappedBy="propertyPages", cascade = {CascadeType.ALL} , orphanRemoval=true)
	private Collection<Properties> properties;
	
	@ManyToOne
	@JoinColumn(name="widget_id", nullable=false)
	@JsonIgnore
	private Widgets widget;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}




	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}




	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}




	/**
	 * @return the properties
	 */
	public Collection<Properties> getProperties() {
		return properties;
	}




	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Collection<Properties> properties) {
		this.properties = properties;
	}



	/**
	 * @return the widget
	 */
	public Widgets getWidget() {
		return widget;
	}


	/**
	 * @param widget the widget to set
	 */
	public void setWidget(Widgets widget) {
		this.widget = widget;
	}


	public void removeProperties(Properties p) {
		if(this.properties!=null)
			properties.remove(p);		
	}


	public void addProperties(Properties p) {
		if(this.properties==null)
			this.properties=new ArrayList<Properties>();
		
		properties.add(p);		
	}




	public void setId(Long id) {
		this.id = id;		
	}

}