package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.AdviceDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.AdviceService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URL;

@Controller
public class AdviceController {

    private final AdviceService adviceService;

    @Autowired
    public AdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @PostMapping("/create")
    public AdviceDto createAdvice(@Valid @RequestBody AdviceDto adviceDto) {
        return adviceService.create(adviceDto);
    }

    @GetMapping("find/{id}")
    public AdviceDto findAdviceById(@PathVariable("id") Long adviceId) throws EntityNotFoundException {
        return adviceService.findById(adviceId);
    }

    @PutMapping("/update")
    public AdviceDto updateAdvice(@Valid @RequestBody AdviceDto adviceDto) throws EntityNotFoundException {
        return adviceService.update(adviceDto);
    }

    @DeleteMapping("/delete/{id}")
    public void removeAdviceById(@PathVariable("id") Long adviceId) throws EntityNotFoundException {
        adviceService.removeById(adviceId);
    }

    @GetMapping("/getURLtoFile")
    public URL getURLtoFileByAnswerId(HttpServletRequest request, Long answerId) throws EntityNotFoundException {
        return adviceService.getURLForFile(answerId, request);
    }

}
