package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Advice;

import java.util.Set;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Long> {

    @Query(value = "SELECT a.id, created, file_id, guide, recommendations, shares, title, updated, admin_id, training_id FROM advice AS a JOIN advice_tags at ON a.id = at.advice_id WHERE tag_id=:tagId", nativeQuery = true)
    Set<Advice> getAllAdviceByTagId(@Param("tagId") Long tagId);

    Advice findFirstByOrderByRecommendationsDesc();

}
