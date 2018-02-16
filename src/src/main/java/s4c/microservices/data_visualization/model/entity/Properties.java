package s4c.microservices.data_visualization.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "properties")
public class Properties implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	private String _controlType;
	
	private String _key;
	private String _label;
	private String _value;
	
	@Column(columnDefinition = "BOOLEAN")
	private Boolean _required;
	private Integer _order ;	
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="propertyPages_id", nullable=false)
	private PropertyPages propertyPages;
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}



	/**
	 * @return the _controlType
	 */
	public String get_controlType() {
		return _controlType;
	}

	/**
	 * @param _controlType the _controlType to set
	 */
	public void set_controlType(String _controlType) {
		this._controlType = _controlType;
	}

	/**
	 * @return the _key
	 */
	public String get_key() {
		return _key;
	}

	/**
	 * @param _key the _key to set
	 */
	public void set_key(String _key) {
		this._key = _key;
	}

	/**
	 * @return the _label
	 */
	public String get_label() {
		return _label;
	}

	/**
	 * @param _label the _label to set
	 */
	public void set_label(String _label) {
		this._label = _label;
	}

	/**
	 * @return the _value
	 */
	public String get_value() {
		return _value;
	}

	/**
	 * @param _value the _value to set
	 */
	public void set_value(String _value) {
		this._value = _value;
	}

	/**
	 * @return the _required
	 */
	public Boolean get_required() {
		return _required;
	}

	/**
	 * @param _required the _required to set
	 */
	public void set_required(Boolean _required) {
		this._required = _required;
	}

	/**
	 * @return the _order
	 */
	public Integer get_order() {
		return _order;
	}

	/**
	 * @param _order the _order to set
	 */
	public void set_order(Integer _order) {
		this._order = _order;
	}

	/**
	 * @return the propertyPages
	 */
	public PropertyPages getPropertyPages() {
		return propertyPages;
	}

	/**
	 * @param propertyPages the propertyPages to set
	 */
	public void setPropertyPages(PropertyPages propertyPages) {
		this.propertyPages = propertyPages;
	}

	public void setId(Long id) {
		this.id = id;
		
	}




}