package s4c.microservices.data_visualization.model.entity;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "source_type")
public class SourceType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(unique=true)
	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}



}