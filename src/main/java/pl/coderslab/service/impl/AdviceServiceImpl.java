package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.AdviceDto;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.Advice;
import pl.coderslab.model.Training;
import pl.coderslab.repository.AdviceRepository;
import pl.coderslab.service.AdviceService;
import pl.coderslab.service.MultiTypeFileService;
import pl.coderslab.service.TrainingService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdviceServiceImpl implements AdviceService {

    private final AdviceRepository adviceRepository;
    private final MultiTypeFileService fileService;
    private final TrainingService trainingService;

    @Autowired
    public AdviceServiceImpl(AdviceRepository adviceRepository, MultiTypeFileService fileService, TrainingService trainingService) {
        this.adviceRepository = adviceRepository;
        this.fileService = fileService;
        this.trainingService = trainingService;
    }

    @Override
    public AdviceDto createAdvice(AdviceDto adviceDto, Long fileId) throws EntityNotFoundException {
        Advice advice = convertToEntity(adviceDto);
        setExistingFileIdToAdvice(fileId, advice);
        adviceRepository.save(advice);
        return convertToObjectDTO(advice);
    }

    @Override
    public AdviceDto findAdviceById(Long adviceId) throws EntityNotFoundException {
        Advice advice = adviceRepository.findById(adviceId).orElseThrow(
                () -> new EntityNotFoundException(Advice.class, "id", adviceId.toString()));
        return convertToObjectDTO(advice);
    }

    @Override
    public AdviceDto updateAdvice(AdviceDto adviceDto, Long fileId) throws EntityNotFoundException {
        checkIfAdviceExistsOrThrowException(adviceDto.getId());
        Advice toUpdate = convertToEntity(adviceDto);
        setExistingFileIdToAdvice(fileId, toUpdate);
        return convertToObjectDTO(adviceRepository.save(toUpdate));
    }

    @Override
    public boolean removeAdviceById(Long adviceId) throws EntityNotFoundException {
        Advice founded = adviceRepository.findById(adviceId).orElseThrow(
                () -> new EntityNotFoundException(Advice.class, "id", adviceId.toString()));
        if (founded != null) {
            adviceRepository.delete(founded);
            return true;
        }
        return false;
    }

    @Override
    public AdviceDto addRecommendationToAdvice(Long adviceId) throws EntityNotFoundException {
        Advice founded = adviceRepository.findById(adviceId).orElseThrow(
                () -> new EntityNotFoundException(Advice.class, "id", adviceId.toString()));
        Integer recommendations = founded.getRecommendations();
        if (recommendations == null) recommendations = 0;
        founded.setRecommendations(recommendations + 1);
        return convertToObjectDTO(adviceRepository.save(founded));
    }

    @Override
    public AdviceDto addSharingToAdvice(Long adviceId) {
        Advice founded = adviceRepository.findById(adviceId).orElseThrow(
                () -> new EntityNotFoundException(Advice.class, "id", adviceId.toString()));
        Integer numberOfShares = founded.getShares();
        if (numberOfShares == null) numberOfShares = 0;
        founded.setShares(numberOfShares + 1);
        return convertToObjectDTO(adviceRepository.save(founded));
    }

    @Override
    public AdviceDto convertToObjectDTO(Advice entity) {
        AdviceDto adviceDto = new ModelMapper().map(entity, AdviceDto.class);
        if (entity.getFileId() != null) {
            adviceDto.setAdviceFileURL(fileService.findByFileId(entity.getFileId()).getUploadDir());
        }
        Training adviceTraining = entity.getTraining();
        if (adviceTraining != null) {
            TrainingDto trainingWithAnswersFileURL = trainingService.convertToObjectDTO(adviceTraining);
            adviceDto.setTraining(trainingWithAnswersFileURL);
        }
        return adviceDto;
    }

    @Override
    public Advice convertToEntity(AdviceDto dto) {
        return new ModelMapper().map(dto, Advice.class);
    }

    @Override
    public Set<AdviceDto> findAllAdviceByTagId(Long tagId) throws EntityNotFoundException {
        return adviceRepository.getAllAdviceByTagId(tagId).stream()
                .map(advice -> convertToObjectDTO(advice)).collect(Collectors.toSet());
    }

    @Override
    public AdviceDto findAdviceOfTheWeek() {
        return convertToObjectDTO(adviceRepository.findFirstByOrderByRecommendationsDesc());
    }

    private void setExistingFileIdToAdvice(Long fileId, Advice advice) throws EntityNotFoundException {
        if (fileId != null) {
            fileService.findByFileId(fileId);
        } else {
            if (advice.getId() != null) {
                Advice founded = adviceRepository.findById(advice.getId()).orElseThrow(
                        () -> new EntityNotFoundException(Advice.class, "id", advice.getId().toString()));
                fileId = founded.getFileId();
            }
        }
        advice.setFileId(fileId);
    }

    private void checkIfAdviceExistsOrThrowException(Long adviceId) throws EntityNotFoundException {
        adviceRepository.findById(adviceId).orElseThrow(
                () -> new EntityNotFoundException(Advice.class, "id", adviceId.toString()));
    }

}
