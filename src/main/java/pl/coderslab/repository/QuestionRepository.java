package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
