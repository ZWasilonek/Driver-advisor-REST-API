package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
