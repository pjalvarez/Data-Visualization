package s4c.microservices.data_visualization.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "widget_properties")
public class WidgetProperties {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String name;
	@NotBlank
	private String value;
	
	@ManyToOne
    @JoinColumn(name="widget_id", nullable=false)
	@JsonIgnore
	private Widgets widget;

	public Widgets getWidget() {
		return widget;
	}

	public void setWidget(Widgets widget) {
		this.widget = widget;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
