package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderslab.dto.AdviceDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.Advice;
import pl.coderslab.repository.AdviceRepository;
import pl.coderslab.service.AdviceService;

import java.net.MalformedURLException;
import java.net.URL;

public class AdviceServiceImpl implements AdviceService {

    private final AdviceRepository adviceRepository;

    @Autowired
    public AdviceServiceImpl(AdviceRepository adviceRepository) {
        this.adviceRepository = adviceRepository;
    }

    @Override
    public AdviceDto createAdvice(AdviceDto adviceDto) {
        return convertToObjectDTO(adviceRepository.save(convertToEntity(adviceDto)));
    }

    @Override
    public AdviceDto findAdviceById(Long adviceId) throws EntityNotFoundException {
        Advice advice = adviceRepository.findById(adviceId).orElseThrow(
                () -> new EntityNotFoundException(Advice.class, "id", adviceId.toString()));
        return convertToObjectDTO(advice);
    }

    @Override
    public AdviceDto updateAdvice(AdviceDto adviceDto) throws EntityNotFoundException {
        Advice founded = adviceRepository.findById(adviceDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(Advice.class, "id", adviceDto.getId().toString()));
        AdviceDto saved = null;
        if (founded != null) {
            adviceDto.setId(founded.getId());
            saved = convertToObjectDTO(adviceRepository.save(convertToEntity(adviceDto)));
        }
        return saved;
    }

    @Override
    public void removeAdviceById(Long adviceId) throws EntityNotFoundException {
        Advice founded = adviceRepository.findById(adviceId).orElseThrow(() ->
                new EntityNotFoundException(Advice.class, "id", adviceId.toString()));
        if (founded != null) adviceRepository.deleteById(adviceId);
    }

    @Override
    public AdviceDto addRecommendationToAdvice(Long adviceId) throws EntityNotFoundException {
        AdviceDto founded = findAdviceById(adviceId);
        Integer recommendations = founded.getRecommendations();
        if (recommendations == null) recommendations = 0;
        founded.setRecommendations(recommendations + 1);
        return convertToObjectDTO(adviceRepository.save(convertToEntity(founded)));
    }

    @Override
    public AdviceDto addSharingToAdvice(Long adviceId) {
        AdviceDto founded = findAdviceById(adviceId);
        Integer numberOfShares = founded.getShares();
        if (numberOfShares == null) numberOfShares = 0;
        founded.setShares(numberOfShares + 1);
        return convertToObjectDTO(adviceRepository.save(convertToEntity(founded)));
    }

    @Override
    public AdviceDto convertToObjectDTO(Advice entity) {
        AdviceDto adviceDto = new ModelMapper().map(entity, AdviceDto.class);
        if (entity.getFileId() != null) {
            adviceDto.setAdviceFileURL(getURLtoFile(entity.getFileId()));
        }
        return adviceDto;
    }

    @Override
    public Advice convertToEntity(AdviceDto dto) {
        return new ModelMapper().map(dto, Advice.class);
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

}
