package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.dto.MultiTypeFileDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import org.springframework.core.io.Resource;
import pl.coderslab.service.AnswerService;
import pl.coderslab.service.MultiTypeFileService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

@RestController
@RequestMapping("/answer")
public class AnswerRESTController {

    private final AnswerService answerService;
    private final MultiTypeFileService multiTypeFileService;

    @Autowired
    public AnswerRESTController(AnswerService answerService, MultiTypeFileService multiTypeFileService) {
        this.answerService = answerService;
        this.multiTypeFileService = multiTypeFileService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public AnswerDto createAnswer(@Valid @RequestBody AnswerDto answerDto) {
        return answerService.create(answerDto);
    }

    @GetMapping("/find/{id}")
    public AnswerDto findAnswerById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        return answerService.findById(answerId);
    }

    @PutMapping("/update")
    public AnswerDto update(@RequestBody AnswerDto answerDto) {
        return answerService.update(answerDto);
    }

    @DeleteMapping("/delete/{id}")
    public void removeAnswerById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        answerService.removeById(answerId);
    }

    @ApiOperation(value = "View a set of correct answers by question id", response = Set.class)
    @GetMapping("/findCorrect/{id}")
    public Set<AnswerDto> findCorrectAnswersByQuestionId(@PathVariable("id") Long questionId) throws EntityNotFoundException {
        return answerService.getCorrectAnswersByQuestionId(questionId);
    }

    @ApiOperation(value = "Upload a file and receive url for view", response = URL.class)
    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
    public URL uploadFile(@RequestParam("file") MultipartFile file) throws MalformedURLException {
        MultiTypeFileDto fileDto = multiTypeFileService.saveFile(file);
        String createdURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/answer/showFile/")
                .path(fileDto.getId().toString())
                .toUriString();
        return new URL(createdURL);
    }

    @ApiOperation(value = "Display a file by file id from URL in browser", response = URL.class)
    @GetMapping("/showFile/{id}")
    public ResponseEntity<?> displayById(@PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.loadIntoBrowser(fileId);
    }

    @ApiOperation(value = "Download a file by file id", response = URL.class)
    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long fileId,
                                          HttpServletRequest request) {
        Resource resource = multiTypeFileService.loadFileAsResource(fileId);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
