package s4c.microservices.data_visualization.model.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

	
//	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="dashboard", cascade = CascadeType.ALL, orphanRemoval=true)
	public Collection<Rows> rows;
	

//	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="dashboard", cascade = CascadeType.ALL)
	public Collection<Assets> assets;

	/**
	 * @return the rows
	 */
	public Collection<Rows> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(Collection<Rows> rows) {
		this.rows = rows;
	}

	@NotBlank
	private String owner;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN")
	private Boolean _public;
	
	public String structure;
	

	/**
	 * @return the _public
	 */
	public Boolean get_public() {
		return _public;
	}

	/**
	 * @param _public the _public to set
	 */
	public void set_public(Boolean _public) {
		this._public = _public;
	}

	/**
	 * @return the structure
	 */
	public String getStructure() {
		return structure;
	}

	/**
	 * @param structure the structure to set
	 */
	public void setStructure(String structure) {
		this.structure = structure;
	}

	
	
	
	public Dashboards () {}
	
	public Dashboards (String name, boolean isPublic, String owner) {
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


	public void addAsset(Assets asset){
		if(assets == null)
			assets = new ArrayList<Assets>();
		
		assets.add(asset);
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

	public Long getId(){
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;		
	}

	public void removeAsset(Assets asset) {
		assets.remove(asset);
		
	}

	public void addRow(Rows row) {
		if(this.rows==null)
			rows = new ArrayList<Rows>();
		
		rows.add(row);		
	}
	public void removeRow(Rows row) {
		if(this.rows!=null)
			rows.remove(row);		
	}

}