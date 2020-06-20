package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.TagDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.TagService;

import javax.validation.Valid;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/create")
    public TagDto createTag(@Valid @RequestBody TagDto tagDto) {
        return tagService.createTag(tagDto);
    }

    @GetMapping("/find/{id}")
    public TagDto findTagById(@PathVariable("id") Long tagId) throws EntityNotFoundException {
        return tagService.findTagById(tagId);
    }

    @PutMapping("/update")
    public TagDto updateTag(@Valid @RequestBody TagDto tagDto) throws EntityNotFoundException {
        return tagService.updateTag(tagDto);
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeTagById(@PathVariable("id") Long tagId) throws EntityNotFoundException {
        return tagService.removeTagById(tagId);
    }

}