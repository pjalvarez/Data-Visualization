package s4c.microservices.data_visualization.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s4c.microservices.data_visualization.model.entity.Rows;



@Repository
public interface RowsRepository extends JpaRepository<Rows, Long> {

}