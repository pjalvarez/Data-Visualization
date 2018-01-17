package s4c.microservices.data_visualization.model.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
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

	@OneToMany(mappedBy="widget", cascade = CascadeType.ALL)	
	private Collection<Sources> sources;
	
	@OneToOne
	private SourceType type;
	
	@OneToMany(mappedBy="widget", cascade = CascadeType.ALL)
	private Collection<WidgetProperties> properties;

	@ManyToOne
    @JoinColumn(name="dashboard_id", nullable=false)
	@JsonIgnore
	private Dashboards dashboard;
	
    public Dashboards getDashboard() { return dashboard; }
    public void setDashboard(Dashboards dashboard) { this.dashboard = dashboard; }
	
    public Widgets() {}
    public Widgets(Long id, String name) {
    	this.id = id;
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
	
	

}