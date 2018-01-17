package s4c.microservices.data_visualization.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import s4c.microservices.data_visualization.model.entity.SourceType;

@Repository
public interface SourceTypeRepository extends JpaRepository<SourceType, Long> {

    @Query("SELECT t FROM SourceType t WHERE t.name = ?1")
	SourceType findOneByName(String name);

}