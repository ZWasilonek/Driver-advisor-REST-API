package pl.coderslab.service;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.model.Answer;
import pl.coderslab.service.generic.GenericService;

import java.util.Set;

public interface AnswerService extends GenericService<Answer> {

    Set<Answer> getCorrectAnswersByQuestionId(Long questionId);
    Answer createAnswer(Answer answer, MultipartFile file) throws FileUploadException;

}
