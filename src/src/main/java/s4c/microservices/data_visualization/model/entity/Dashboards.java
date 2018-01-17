package s4c.microservices.data_visualization.model.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "dashboards")
@DynamicUpdate
public class Dashboards implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	public String name;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="dashboard", cascade = CascadeType.ALL)
	private Collection<Widgets> widgets;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="dashboard", cascade = CascadeType.ALL)
	public Collection<Assets> assets;

	@NotBlank
	public String owner;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN")
	public Boolean _public;
	
	
	public Dashboards () {}
	
	public Dashboards (Long id, String name, boolean isPublic, String owner) {
		this.id = id;
		this.name= name;
		this._public=isPublic;
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Collection<Assets> getAssets() {
		return assets;
	}

	public void setAssets(Collection<Assets> assets) {
		this.assets = assets;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean is_public() {
		return _public;
	}

	public void set_public(boolean _public) {
		this._public = _public;
	}

	public Collection<Widgets> getWidgets() {
		return widgets;
	}

	public void setWidgets(Collection<Widgets> widgets) {
		this.widgets = widgets;
	}
	
	public Long getId(){
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;		
	}

}