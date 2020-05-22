package pl.coderslab.service;

import pl.coderslab.dto.AdviceDto;
import pl.coderslab.model.Advice;

public interface AdviceService {

    AdviceDto createAdvice(AdviceDto adviceDto, Long fileId);
    AdviceDto findAdviceById(Long adviceId);
    AdviceDto updateAdvice(AdviceDto adviceDto, Long fileId);
    boolean removeAdviceById(Long adviceId);

    AdviceDto addRecommendationToAdvice(Long adviceId);
    AdviceDto addSharingToAdvice(Long adviceId);
    AdviceDto convertToObjectDTO(Advice entity);
    Advice convertToEntity(AdviceDto dto);

}
