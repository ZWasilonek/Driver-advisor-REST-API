package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.Answer;
import pl.coderslab.repository.AnswerRepository;
import pl.coderslab.service.AnswerService;
import pl.coderslab.service.MultiTypeFileService;

import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final MultiTypeFileService fileService;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, MultiTypeFileService fileService) {
        this.answerRepository = answerRepository;
        this.fileService = fileService;
    }

    @Override
    public AnswerDto createAnswer(AnswerDto answerDto, Long fileId) throws EntityNotFoundException {
        Answer answer = convertToEntity(answerDto);
        setExistingFileIdToAnswer(fileId, answer);
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
        Answer toUpdate = convertToEntity(answerDto);
        setExistingFileIdToAnswer(fileId, toUpdate);
        return convertToObjectDTO(answerRepository.save(toUpdate));
    }

    @Override
    public boolean removeAnswerById(Long answerId) throws EntityNotFoundException {
        Answer founded = answerRepository.findById(answerId).orElseThrow(
                () -> new EntityNotFoundException(Answer.class, "id", answerId.toString()));
        if (founded != null) {
            answerRepository.delete(founded);
            answerRepository.removeAnswerFromQuestion(answerId);
            return true;
        }
        return false;
    }

    @Override
    public AnswerDto convertToObjectDTO(Answer entity) {
        AnswerDto answerDto = new ModelMapper().map(entity, AnswerDto.class);
        if (entity.getFileId() != null) {
            answerDto.setAnswerFileURL(fileService.findByFileId(entity.getFileId()).getUploadDir());
        }
        return answerDto;
    }

    @Override
    public Answer convertToEntity(AnswerDto answerDto) {
        Answer answer = new ModelMapper().map(answerDto, Answer.class);
        URL answerFileURL = answerDto.getAnswerFileURL();
        if (answerFileURL != null) {
            String fileURL = answerFileURL.toString();
            String fileId = fileURL.substring(fileURL.lastIndexOf("/") + 1);
            answer.setFileId(Long.parseLong(fileId));
        }
        return answer;
    }

    @Override
    public Set<AnswerDto> getCorrectAnswersByQuestionId(Long questionId) throws EntityNotFoundException {
        return answerRepository.findCorrectAnswersByQuestionId(questionId).stream()
                .map(entity -> convertToObjectDTO(entity))
                .collect(Collectors.toSet());
    }

    private void setExistingFileIdToAnswer(Long fileId, Answer answer) {
        if (fileId != null) {
            fileService.findByFileId(fileId);
        } else {
            if (answer.getId() != null) {
                Answer founded = answerRepository.findById(answer.getId()).orElseThrow(
                        () -> new EntityNotFoundException(Answer.class, "id", answer.getId().toString()));
                fileId = founded.getFileId();
            }
        }
        answer.setFileId(fileId);
    }

    private void checkIfAnswerExistsOrThrowException(Long answerId) throws EntityNotFoundException {
        answerRepository.findById(answerId).orElseThrow(
                () -> new EntityNotFoundException(Answer.class, "id", answerId.toString()));
    }

}
