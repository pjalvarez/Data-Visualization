package s4c.microservices.data_visualization.model.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "sources")
public class Sources implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	public String url;

	@OneToMany(mappedBy="source", cascade = CascadeType.ALL)
	public Collection<SourceParameters> parameters;
	

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
	
	public Collection<SourceParameters> getParameters() {
		return parameters;
	}

	public void setParameters(Collection
			<SourceParameters> parameters) {
		this.parameters = parameters;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}