package pl.coderslab.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Answer;
import pl.coderslab.repository.AnswerRepository;
import pl.coderslab.service.AnswerService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl extends GenericServiceImpl<AnswerDto, Answer, AnswerRepository> implements AnswerService {

    @Autowired
    public AnswerServiceImpl(AnswerRepository repository) {
        super(repository);
    }

    @Override
    public Set<AnswerDto> getCorrectAnswersByQuestionId(Long questionId) throws EntityNotFoundException {
        return repository.findCorrectAnswersByQuestionId(questionId).stream()
                .map(entity -> convertToObjectDTO(entity, AnswerDto.class))
                .collect(Collectors.toSet());
    }

//    public AnswerDto createAnswer(AnswerDto dto) {
//        AnswerDto savedDto = this.create(dto);
//        savedDto.setFileURL();
//    }

}
