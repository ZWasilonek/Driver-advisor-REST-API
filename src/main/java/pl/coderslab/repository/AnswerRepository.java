package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
