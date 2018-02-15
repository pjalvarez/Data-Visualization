package s4c.microservices.data_visualization.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import s4c.microservices.data_visualization.model.entity.Actions;




@Repository
public interface ActionsRepository extends JpaRepository<Actions, Long> {
	@Query("SELECT t FROM Actions t WHERE UCASE(t.name) = ?1 ")
	List<Actions> findByName(String name);

}