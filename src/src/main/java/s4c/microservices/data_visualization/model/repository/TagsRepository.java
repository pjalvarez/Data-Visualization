package s4c.microservices.data_visualization.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s4c.microservices.data_visualization.model.entity.Tags;



@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {

}