package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Answer;

import java.util.Set;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value = "SELECT a.id, a.description, a.is_correct FROM question_answers JOIN answers a on question_answers.answer_id = a.id WHERE question_id=:questionId AND a.is_correct=1", nativeQuery = true)
    Set<Answer> findCorrectAnswersByQuestionId(@Param("questionId") Long questionId);

}
