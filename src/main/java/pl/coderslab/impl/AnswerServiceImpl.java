package pl.coderslab.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.dto.MultiTypeFileDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Answer;
import pl.coderslab.repository.AnswerRepository;
import pl.coderslab.service.AnswerService;
import pl.coderslab.service.MultiTypeFileService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl extends GenericServiceImpl<AnswerDto, Answer, AnswerRepository> implements AnswerService {

    private final MultiTypeFileService multiTypeFileService;

    @Autowired
    public AnswerServiceImpl(AnswerRepository repository, MultiTypeFileService multiTypeFileService) {
        super(repository);
        this.multiTypeFileService = multiTypeFileService;
    }

    @Override
    public Set<AnswerDto> getCorrectAnswersByQuestionId(Long questionId) throws EntityNotFoundException {
        return repository.findCorrectAnswersByQuestionId(questionId).stream()
                .map(entity -> convertToObjectDTO(entity, AnswerDto.class))
                .collect(Collectors.toSet());
    }

    //AnswerDto po wyjściu z bazy danych traci URL
    @Override
    public URL getURLByFileId(Long answerId) throws EntityNotFoundException {
        AnswerDto founded = this.findById(answerId);
        return founded.getFileURL();
    }

    @Override
    public AnswerDto updateAnswer(AnswerDto dto) throws EntityNotFoundException {
        Long fileId = dto.getFileId();
        if (fileId != null) {
            MultiTypeFileDto fileDto = multiTypeFileService.findById(fileId);
            try {
                URL urlToFile = new URL(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/answer/showFile/")
                        .path(fileDto.getId().toString())
                        .toUriString());
                AnswerDto savedDto = this.update(dto);
                savedDto.setFileURL(urlToFile);
                return savedDto;
            } catch (MalformedURLException e) {
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return this.update(dto);
    }

    //    public AnswerDto createAnswer(AnswerDto dto) {
//        AnswerDto savedDto = this.create(dto);
//        savedDto.setFileURL();
//    }

}
