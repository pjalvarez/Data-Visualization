package s4c.microservices.data_visualization.model.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "actions")
public class Actions implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	
	@ManyToMany
	@JsonIgnore
	private Collection<Widgets> widget;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	public Collection<Widgets> getWidget() {
		return widget;
	}


	/**
	 * @param widget the widget to set
	 */
	public void setWidget(Collection<Widgets> widget) {
		this.widget = widget;
	}

	public void removeWidget(Widgets widget2) {
		if(this.widget!=null)
			this.widget.remove(widget2);		
	}

	public void addWidget(Widgets widget2) {
		if(this.widget==null)
			this.widget = new ArrayList<Widgets>();
		this.widget.add(widget2);		
	}



	public void setId(Long id) {
		this.id = id;		
	}

}