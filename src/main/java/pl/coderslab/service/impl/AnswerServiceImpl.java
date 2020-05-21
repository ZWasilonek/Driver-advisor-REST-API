package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.Answer;
import pl.coderslab.model.MultiTypeFile;
import pl.coderslab.repository.AnswerRepository;
import pl.coderslab.repository.MultiTypeFileRepository;
import pl.coderslab.service.AnswerService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final MultiTypeFileRepository multiTypeFileRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, MultiTypeFileRepository multiTypeFileRepository) {
        this.answerRepository = answerRepository;
        this.multiTypeFileRepository = multiTypeFileRepository;
    }

    @Override
    public AnswerDto createAnswer(AnswerDto answerDto, Long fileId) throws EntityNotFoundException {
        if (fileId != null) findFileIdInDB(fileId);
        Answer answer = convertToEntity(answerDto);
        if (fileId != null) answer.setFileId(fileId);
        answerRepository.save(answer);
        return convertToObjectDTO(answer);
    }

    @Override
    public AnswerDto findAnswerById(Long answerId) throws EntityNotFoundException {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new EntityNotFoundException(Answer.class, "id", answerId.toString()));
        return convertToObjectDTO(answer);
    }

    @Override
    public AnswerDto updateAnswer(AnswerDto answerDto, Long fileId) throws EntityNotFoundException {
        checkIfAnswerExistsOrThrowException(answerDto.getId());
        if (fileId != null) findFileIdInDB(fileId);
        AnswerDto saved = null;
//        if ( != null) {
//            answerDto.setId(founded.getId());
//            saved = convertToObjectDTO(answerRepository.save(convertToEntity(answerDto)));
//        }
        return saved;
    }

    @Override
    public void removeAnswerById(Long answerId) throws EntityNotFoundException {
        checkIfAnswerExistsOrThrowException(answerId);
        answerRepository.deleteById(answerId);
    }

    @Override
    public AnswerDto convertToObjectDTO(Answer entity) {
        AnswerDto answerDto = new ModelMapper().map(entity, AnswerDto.class);
        if (entity.getFileId() != null) {
            answerDto.setAnswerFileURL(getURLtoFile(entity.getFileId()));
        }
        return answerDto;
    }

    @Override
    public Answer convertToEntity(AnswerDto answerDto) {
        return new ModelMapper().map(answerDto, Answer.class);
    }

    @Override
    public Set<AnswerDto> getCorrectAnswersByQuestionId(Long questionId) throws EntityNotFoundException {
        return answerRepository.findCorrectAnswersByQuestionId(questionId).stream()
                .map(entity -> convertToObjectDTO(entity))
                .collect(Collectors.toSet());
    }

    private URL getURLtoFile(Long fileId) {
        URL fileURL = null;
        try {
            fileURL = new URL(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/file/showFile/")
                    .path(fileId.toString())
                    .toUriString());
        } catch (MalformedURLException e) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return fileURL;
    }

    private void findFileIdInDB(Long fileId) {
        if (fileId != null) {
            multiTypeFileRepository.findById(fileId).orElseThrow(
                    () -> new EntityNotFoundException(MultiTypeFile.class, "file id for answer", fileId.toString()));
        }
    }

    private void checkIfAnswerExistsOrThrowException(Long answerId) throws EntityNotFoundException {
        answerRepository.findById(answerId).orElseThrow(
                () -> new EntityNotFoundException(Answer.class, "id", answerId.toString()));
    }

}
