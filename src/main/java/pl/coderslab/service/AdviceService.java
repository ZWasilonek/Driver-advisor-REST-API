package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.dto.AdviceDto;
import pl.coderslab.model.Advice;

@Service
public interface AdviceService {

    AdviceDto createAdvice(AdviceDto adviceDto);
    AdviceDto findAdviceById(Long adviceId);
    AdviceDto updateAdvice(AdviceDto adviceDto);
    void removeAdviceById(Long adviceId);

    AdviceDto addRecommendationToAdvice(Long adviceId);
    AdviceDto addSharingToAdvice(Long adviceId);
    AdviceDto convertToObjectDTO(Advice entity);
    Advice convertToEntity(AdviceDto dto);

}
