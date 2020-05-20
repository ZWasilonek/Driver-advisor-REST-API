package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.AdviceDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.AdviceService;
import pl.coderslab.service.MultiTypeFileService;
import pl.coderslab.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URL;

@RestController
public class AdviceController {

    private final AdviceService adviceService;
    private final MultiTypeFileService multiTypeFileService;
    private final UserService userService;

    @Autowired
    public AdviceController(AdviceService adviceService, MultiTypeFileService multiTypeFileService,UserService userService) {
        this.adviceService = adviceService;
        this.multiTypeFileService = multiTypeFileService;
        this.userService = userService;
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

    @ApiOperation(value = "Display a file by file id from URL in browser", response = URL.class)
    @GetMapping("/showFile/{id}")
    public ResponseEntity<?> displayById(@PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.loadIntoBrowser(fileId);
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

    @ApiOperation(value = "Adds a recommendation to the advice number of recommendations by its id", response = AdviceDto.class)
    @PostMapping("/sentRecommendation/{id}")
    public AdviceDto sentRecommendation(@PathVariable("id") Long adviceId) throws EntityNotFoundException {
        return adviceService.addRecommendationToAdvice(adviceId);
    }

    @ApiOperation(value = "Adds a share to the advice number of shares by its id", response = AdviceDto.class)
    @PostMapping("/share/{id}")
    public AdviceDto shareAdviceById(@PathVariable("id") Long adviceId) throws EntityNotFoundException {
        return adviceService.addSharingToAdvice(adviceId);
    }

}
