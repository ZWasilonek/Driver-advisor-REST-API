package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.EmailMessage;

public interface EmailRepository extends JpaRepository<EmailMessage, Long> { }