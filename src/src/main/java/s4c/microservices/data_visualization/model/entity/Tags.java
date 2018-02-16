package s4c.microservices.data_visualization.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;


@Entity
@Table(name = "tags")
public class Tags implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	private String facet;
	private String name;
	
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
	 * @return the facet
	 */
	public String getFacet() {
		return facet;
	}


	/**
	 * @param facet the facet to set
	 */
	public void setFacet(String facet) {
		this.facet = facet;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	public void setId(Long id) {
		this.id = id;
	}

}