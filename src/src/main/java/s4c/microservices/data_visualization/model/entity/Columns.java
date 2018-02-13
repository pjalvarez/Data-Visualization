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
@Table(name = "columns")
public class Columns implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name="row_id", nullable=true)
	@JsonIgnore
	private Rows row;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy="columns" ,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
	private Collection<Widgets> widgets;
	
	
	private String styleClass;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the columns
	 */
	public Rows getRow() {
		return row;
	}


	/**
	 * @param columns the columns to set
	 */
	public void setRow(Rows row) {
		this.row = row;
	}
	
	


	/**
	 * @return the styleClass
	 */
	public String getStyleClass() {
		return styleClass;
	}


	/**
	 * @param styleClass the styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
	public Collection<Widgets> getWidgets() {
		return widgets;
	}

	public void setWidgets(Collection<Widgets> widgets) {
		this.widgets = widgets;
	}
	
	public void addWidget(Widgets widget){
		if(this.widgets==null)
			this.widgets =  new ArrayList<Widgets>();
		
		widgets.add(widget);
	}
	
	public void removeWidget(Widgets widget){
		if(this.widgets!=null)
			widgets.remove(widget);
	}


}