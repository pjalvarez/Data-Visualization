package s4c.microservices.data_visualization.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s4c.microservices.data_visualization.model.entity.PropertyPages;



@Repository
public interface PropertyPagesRepository extends JpaRepository<PropertyPages, Long> {

}