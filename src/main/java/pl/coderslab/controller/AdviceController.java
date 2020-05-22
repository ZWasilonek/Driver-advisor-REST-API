package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.AdviceDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.AdviceService;
import javax.validation.Valid;

@RestController
@RequestMapping("/advice")
public class AdviceController {

    private final AdviceService adviceService;

    @Autowired
    public AdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @PostMapping("/create")
    public AdviceDto createAdvice(@Valid @RequestBody AdviceDto adviceDto,
                                  @RequestParam(required = false) Long fileId) {
        return adviceService.createAdvice(adviceDto, fileId);
    }

    @GetMapping("find/{id}")
    public AdviceDto findAdviceById(@PathVariable("id") Long adviceId) throws EntityNotFoundException {
        return adviceService.findAdviceById(adviceId);
    }

    //add file - create time null
    //update - create time -1 day ?
    @PutMapping("/update")
    public AdviceDto updateAdvice(@Valid @RequestBody AdviceDto adviceDto,
                                  @RequestParam(required = false) Long fileId) throws EntityNotFoundException {
        return adviceService.updateAdvice(adviceDto, fileId);
    }

    @DeleteMapping("/delete/{id}")
    public boolean removeAdviceById(@PathVariable("id") Long adviceId) throws EntityNotFoundException {
        return adviceService.removeAdviceById(adviceId);
    }

    @ApiOperation(value = "Adds a recommendation to the advice number of recommendations by its id", response = AdviceDto.class)
    @PostMapping("/sendRecommendation/{id}")
    public AdviceDto sendRecommendation(@PathVariable("id") Long adviceId) throws EntityNotFoundException {
        return adviceService.addRecommendationToAdvice(adviceId);
    }

    @ApiOperation(value = "Adds a share to the advice number of shares by its id", response = AdviceDto.class)
    @PostMapping("/share/{id}")
    public AdviceDto shareAdviceById(@PathVariable("id") Long adviceId) throws EntityNotFoundException {
        return adviceService.addSharingToAdvice(adviceId);
    }

    //    @ModelAttribute("userSession")
//    public UserDto getUserFromSession() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            String username = ((UserDetails) principal).getUsername();
//            return userService.findByUserName(username);
//        }
//        return null;
//    }

}
