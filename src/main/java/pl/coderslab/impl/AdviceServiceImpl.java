package pl.coderslab.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.dto.AdviceDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Advice;
import pl.coderslab.repository.AdviceRepository;
import pl.coderslab.service.AdviceService;

@Service
public class AdviceServiceImpl extends GenericServiceImpl<AdviceDto,Advice, AdviceRepository> implements AdviceService {

    public AdviceServiceImpl(AdviceRepository repository) {
        super(repository);
    }

    @Override
    public AdviceDto addRecommendationToAdvice(Long adviceId) throws EntityNotFoundException {
        AdviceDto founded = this.findById(adviceId);
        Integer recommendations = founded.getRecommendation();
        if (recommendations == null) recommendations = 0;
        founded.setRecommendation(recommendations + 1);
        return this.update(founded);
    }

}
