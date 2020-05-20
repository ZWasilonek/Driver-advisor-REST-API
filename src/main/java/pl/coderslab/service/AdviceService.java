package pl.coderslab.service;

import pl.coderslab.dto.AdviceDto;
import pl.coderslab.model.Advice;
import pl.coderslab.service.generic.GenericService;

public interface AdviceService extends GenericService<AdviceDto, Advice> {

    AdviceDto addRecommendationToAdvice(Long adviceId);

    AdviceDto addSharingToAdvice(Long adviceId);

}
