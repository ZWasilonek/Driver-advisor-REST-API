package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.MultiTypeFile;

@Repository
public interface MultiTypeFileRepository extends JpaRepository<MultiTypeFile, Long> {

    MultiTypeFile findByFileName(String fileName);

}
