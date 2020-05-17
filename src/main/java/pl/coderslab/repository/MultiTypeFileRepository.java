package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.MultiTypeFile;

@Repository
public interface MultiTypeFileRepository extends JpaRepository<MultiTypeFile, Long> {

    MultiTypeFile findByFileName(String fileName);

    @Query(value = "select f.id, f.data, f.file_name, f.file_type, f.size from files as f join answers on f.id = answers.file_id where answers.id=:answerId", nativeQuery = true)
    MultiTypeFile findByAnswerId(@Param("answerId") Long answerId);

    @Query(value = "select f.id, f.data, f.file_name, f.file_type, f.size from files as f join training on f.id = training.file_id where training.id=trainingId", nativeQuery = true)
    MultiTypeFile findByTrainingId(@Param("trainingId") Long trainingId);

    @Query(value = "select f.id, f.data, f.file_name, f.file_type, f.size from files as f join advice on f.id = advice.file_id where advice.id=:adviceId", nativeQuery = true)
    MultiTypeFile findByAdviceId(@Param("adviceId") Long adviceId);

}
