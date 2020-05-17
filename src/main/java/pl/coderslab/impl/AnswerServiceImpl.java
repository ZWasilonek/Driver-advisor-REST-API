package pl.coderslab.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Answer;
import pl.coderslab.model.MultiTypeFile;
import pl.coderslab.repository.AnswerRepository;
import pl.coderslab.service.AnswerService;

import java.util.Set;

@Service
public class AnswerServiceImpl extends GenericServiceImpl<Answer, AnswerRepository> implements AnswerService {

    private final MultiTypeFileServiceImpl multiTypeFileService;

    @Autowired
    public AnswerServiceImpl(AnswerRepository repository, MultiTypeFileServiceImpl multiTypeFileService) {
        super(repository);
        this.multiTypeFileService = multiTypeFileService;
    }

    @Override
    public Set<Answer> getCorrectAnswersByQuestionId(Long questionId) throws EntityNotFoundException {
        return repository.findCorrectAnswersByQuestionId(questionId);
    }

    public Answer createAnswer(Answer answer, MultipartFile file) {
        MultiTypeFile multiTypeFile = multiTypeFileService.saveFile(file);
        answer.setMultiTypeFile(multiTypeFile);
        return repository.save(answer);
    }

}
