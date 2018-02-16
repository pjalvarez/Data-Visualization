package s4c.microservices.data_visualization.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "rows")
public class Rows implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@ManyToOne
    @JoinColumn(name="dashboard_id")
	@JsonIgnore	
	private Dashboards dashboard;
	
	
	
	/**
	 * @return the columns
	 */
	public Collection<Columns> getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(Collection<Columns> columns) {
		this.columns = columns;
	}

	/**
	 * @return the dashboard
	 */
	public Dashboards getDashboard() {
		return dashboard;
	}

	/**
	 * @param dashboard the dashboard to set
	 */
	public void setDashboard(Dashboards dashboard) {
		this.dashboard = dashboard;
	}

	@OneToMany(mappedBy="row", cascade = CascadeType.ALL, orphanRemoval=true)
	public Collection<Columns> columns;


	public void removeColumn(Columns c) {
		if (this.columns!=null)
			columns.remove(c);
		
	}
	public void addColumn(Columns c) {
		if (this.columns==null)
			this.columns = new ArrayList<Columns>();
		
		columns.add(c);		
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
		
	}

	


}