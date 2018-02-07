package s4c.microservices.data_visualization.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "assets")
public class Assets {	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String asset;
	
	
	public Assets(){}
	
	public Assets(long id, String asset){
		this.id=id;
		this.asset=asset;
	}
	
	@ManyToOne
    @JoinColumn(name="dashboard_id")
	@JsonIgnore	
	private Dashboards dashboard;
	
    public Dashboards getDashboard() { return dashboard; }
	
	public void setDashboard(Dashboards dashboard){
		this.dashboard =  dashboard;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}
	
	public Long getId(){
		return this.id;
	}

}
